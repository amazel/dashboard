package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.commands.IngredientForecast;
import com.platillogodin.dashboard.domain.*;
import com.platillogodin.dashboard.repositories.MenuRepository;
import com.platillogodin.dashboard.repositories.StockRepository;
import com.platillogodin.dashboard.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public DashboardServiceImpl(MenuRepository menuRepository, StockRepository stockRepository) {
        this.menuRepository = menuRepository;
        this.stockRepository = stockRepository;
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
            Integer diff = stock.getTotal() - ingredientMap.get(i);
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
}
