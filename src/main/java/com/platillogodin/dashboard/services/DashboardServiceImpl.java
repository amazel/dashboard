package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.commands.*;
import com.platillogodin.dashboard.domain.*;
import com.platillogodin.dashboard.repositories.MenuCategoryRepository;
import com.platillogodin.dashboard.repositories.MenuOptionRepository;
import com.platillogodin.dashboard.repositories.MenuRepository;
import com.platillogodin.dashboard.repositories.StockRepository;
import com.platillogodin.dashboard.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hugo Lezama on September - 2018
 */
@Slf4j
@Service
public class DashboardServiceImpl implements DashboardService {

    private final MenuRepository menuRepository;
    private final StockRepository stockRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuOptionRepository menuOptionRepository;


    public DashboardServiceImpl(MenuRepository menuRepository, StockRepository stockRepository, MenuCategoryRepository menuCategoryRepository, MenuOptionRepository menuOptionRepository) {
        this.menuRepository = menuRepository;
        this.stockRepository = stockRepository;
        this.menuCategoryRepository = menuCategoryRepository;
        this.menuOptionRepository = menuOptionRepository;
    }

    @Override
    public List<IngredientForecast> getIngredientForecast() {

        List<String> menuIdList = new ArrayList<>();
        LocalDate nextMonday = Utils.getNextMonday(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        for (int i = 0; i < 5; i++) {
            menuIdList.add(formatter.format(nextMonday.plusDays(i)));
        }

        List<Menu> menus = menuRepository.findAllById(menuIdList);

        Map<Ingredient, Integer> ingredientMap = new HashMap<>();

        for (Menu menu : menus) {
            for (MenuOption option : menu.getOptions()) {
                for (RecipeIngredient recipeIngredient : option.getRecipe().getIngredientList()) {
                    Ingredient ingredient = recipeIngredient.getIngredient();
                    Integer qty = ingredientMap.getOrDefault(ingredient, 0);
                    Integer total = (recipeIngredient.getQuantity() / option.getRecipe().getServings()) * option.getForecastQuantity();
                    ingredientMap.put(ingredient, (qty + total));
                }
            }
        }

        List<IngredientForecast> forecast = new ArrayList<>();

        for (Ingredient i : ingredientMap.keySet()) {
            log.info("ingrediente: {}, cantidad: {}", i.getName(), ingredientMap.get(i));
            Stock stock = stockRepository.findByIngredient(i);

            NumberFormat nf = NumberFormat.getIntegerInstance();
            String forecastQty = nf.format(ingredientMap.get(i));
            String actualQty = nf.format(stock.getTotal());
            int diff = stock.getTotal() - ingredientMap.get(i);
            String difference = nf.format(diff);

            IngredientForecast ingredientForecast = new IngredientForecast();
            ingredientForecast.setIngredient(i.getName());
            ingredientForecast.setForecast(forecastQty + " " + i.getUom().name().toLowerCase());
            ingredientForecast.setActual(actualQty + " " + i.getUom().name().toLowerCase());
            ingredientForecast.setDifference(difference + " " + i.getUom().name().toLowerCase());
            ingredientForecast.setClassName(diff < 0 ? "alert-danger" :
                    (stock.getNextExpirationDate().isBefore(nextMonday.plusDays(4)) ?
                            "alert-warning" : "alert-success"));
            forecast.add(ingredientForecast);

        }

        return forecast;
    }

    @Override
    public WeeklyCosts getWeeklyCostsForecast() {
        List<WeeklyRecipeCosts> weeklyRecipeCosts = new ArrayList<>();
        LocalDate nextMonday = Utils.getNextMonday(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        String[] days = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        BigDecimal totalCost = BigDecimal.ZERO;

        Map<String, List<BigDecimal>> totals = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            BigDecimal dayTotalCost = BigDecimal.ZERO;
            Map<String, BigDecimal> dayCostsByCategory = new HashMap<>();
            String menuId = formatter.format(nextMonday.plusDays(i));
            Menu menu = menuRepository.findById(menuId).orElse(new Menu(menuId));


            for (MenuOption option : menu.getOptions()) {
                BigDecimal categoryTotal = dayCostsByCategory.getOrDefault(option.getMenuCategory().getName(), BigDecimal.ZERO);
                BigDecimal categoryCost = BigDecimal.ZERO;
                BigDecimal recipeCost = BigDecimal.ZERO;
                for (RecipeIngredient recipeIngredient : option.getRecipe().getIngredientList()) {
                    Stock stock = stockRepository.findByIngredient(recipeIngredient.getIngredient());
                    BigDecimal oneServing =
                            BigDecimal.valueOf(recipeIngredient.getQuantity() / option.getRecipe().getServings());
                    BigDecimal recipeIngredientCost =
                            oneServing.multiply(BigDecimal.valueOf(option.getForecastQuantity())).multiply(stock.getLastPrice());
                    recipeCost = recipeCost.add(recipeIngredientCost);
                    categoryCost = categoryCost.add(recipeIngredientCost);
                    totalCost = totalCost.add(recipeIngredientCost);
                }

                weeklyRecipeCosts.add(
                        new WeeklyRecipeCosts(days[i], option.getMenuCategory().getName(),
                                option.getRecipe().getName(), option.getForecastQuantity(), recipeCost));

                dayCostsByCategory.put(option.getMenuCategory().getName(), categoryTotal.add(categoryCost));
                dayTotalCost = dayTotalCost.add(categoryTotal.add(categoryCost));
            }
            for (MenuCategory cat : menuCategoryRepository.findAll()) {
                List<BigDecimal> totalsByCat = totals.getOrDefault(cat.getName(), new ArrayList<>());
                totalsByCat.add(dayCostsByCategory.get(cat.getName()));
                totals.put(cat.getName(), totalsByCat);
            }
        }

        WeeklyCosts weeklyCosts = new WeeklyCosts();
        weeklyCosts.setWeeklyTotal(totalCost);
        weeklyCosts.setWeeklyRecipeCosts(weeklyRecipeCosts);

        WeeklyCostItem totalRecord = new WeeklyCostItem();
        totalRecord.setMenuCategory("TOTAL");
        for (String cat : totals.keySet()) {
            WeeklyCostItem item = new WeeklyCostItem();
            List<BigDecimal> catTotal = totals.get(cat);
            item.setMenuCategory(cat);

            BigDecimal monday = catTotal.get(0) != null ? catTotal.get(0) : new BigDecimal("0");
            BigDecimal tuesday = catTotal.get(1) != null ? catTotal.get(1) : new BigDecimal("0");
            BigDecimal wednesday = catTotal.get(2) != null ? catTotal.get(2) : new BigDecimal("0");
            BigDecimal thursday = catTotal.get(3) != null ? catTotal.get(3) : new BigDecimal("0");
            BigDecimal friday = catTotal.get(4) != null ? catTotal.get(4) : new BigDecimal("0");

            item.setMondayTotal(monday);
            item.setTuesdayTotal(tuesday);
            item.setWednesdayTotal(wednesday);
            item.setThursdayTotal(thursday);
            item.setFridayTotal(friday);

            totalRecord.setMondayTotal(totalRecord.getMondayTotal().add(monday));
            totalRecord.setTuesdayTotal(totalRecord.getTuesdayTotal().add(tuesday));
            totalRecord.setWednesdayTotal(totalRecord.getWednesdayTotal().add(wednesday));
            totalRecord.setThursdayTotal(totalRecord.getThursdayTotal().add(thursday));
            totalRecord.setFridayTotal(totalRecord.getFridayTotal().add(friday));

            weeklyCosts.getWeeklyCostItems().add(item);
        }
        weeklyCosts.getWeeklyCostItems().add(totalRecord);

        return weeklyCosts;
    }

    @Override
    public List<CategoryTotal> getTotalsByCategory(Integer days) {
        return menuOptionRepository.getTotalsByCategory(PageRequest.of(0, days));
    }
}
