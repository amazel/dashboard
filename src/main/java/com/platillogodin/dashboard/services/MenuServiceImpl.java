package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.*;
import com.platillogodin.dashboard.exceptions.GenericException;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.MenuRepository;
import com.platillogodin.dashboard.repositories.StockRepository;
import com.platillogodin.dashboard.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final StockRepository stockRepository;

    public MenuServiceImpl(MenuRepository menuRepository, StockRepository stockRepository) {
        this.menuRepository = menuRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public Menu findById(String id) {
        return menuRepository.findById(id)
                .orElseGet(() -> save(new Menu(id)));
    }

    @Transactional
    public Menu save(Menu menu) {
        log.info("Saving menu");
        if (menu.getDate() == null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            menu.setDate(LocalDate.parse(menu.getId(), formatter));
            menu.setWeekNumber(Utils.findWeekNumber(menu.getDate()));
        }
        return menuRepository.save(menu);
    }

    @Transactional
    @Override
    public void processMenuDay(Menu menu) throws GenericException {
        BigDecimal totalMenuCost = BigDecimal.ZERO;
        for (MenuOption option : menu.getOptions()) {
            if (option.getActualQuantity() == null) {
                throw new GenericException("Debes asignar la cantidad real a todos los platillos");
            }
            List<Ingredient> ingredients =
                    option.getRecipe().getIngredientList()
                            .stream()
                            .map(recipeIngredient -> recipeIngredient.getIngredient())
                            .collect(Collectors.toList());

            List<Stock> stockList = stockRepository.findAllByIngredientIn(ingredients);

            BigDecimal menuOptionCost = BigDecimal.ZERO;

            for (RecipeIngredient ri : option.getRecipe().getIngredientList()) {
                BigDecimal unitQty = BigDecimal.valueOf(ri.getQuantity() / option.getRecipe().getServings());
                BigDecimal neededQty = unitQty.multiply(BigDecimal.valueOf(option.getActualQuantity()));
                Stock ingredientStock = stockList
                        .stream()
                        .filter(stock -> stock.getIngredient().getId().equals(ri.getIngredient().getId()))
                        .findFirst()
                        .orElseThrow(() -> new NotFoundException("No se encontró registro en el inventario para el " +
                                "ingrediente: " + ri.getIngredient().getName()));
                List<StockEntry> filteredStockEntries = ingredientStock.getStockEntries()
                        .stream()
                        .filter(stockEntry -> stockEntry.getCurrentQty() > 0)
                        .sorted((s1, s2) -> s1.getSupplyDate().compareTo(s2.getSupplyDate())).collect(Collectors.toList());
                boolean breakFor = false;

                BigDecimal ingredientCost = BigDecimal.ZERO;
                for (StockEntry entry : filteredStockEntries) {
                    BigDecimal unitCost = entry.getPrice().divide(BigDecimal.valueOf(entry.getOriginalQty()));
                    log.info("Unit cost for entry ID {}: {}", entry.getId(), unitCost.toString());
                    if (BigDecimal.valueOf(entry.getCurrentQty()).compareTo(neededQty) < 0) {
                        log.info("NO HAY SUFICIENTE EN LA ENTRY: {}, SE NECESITA {}", entry.getCurrentQty(), neededQty);
                        neededQty = neededQty.subtract(BigDecimal.valueOf(entry.getCurrentQty()));
                        ingredientStock.setTotal(ingredientStock.getTotal() - entry.getCurrentQty());
                        ingredientCost =
                                ingredientCost.add(unitCost.multiply(BigDecimal.valueOf(entry.getCurrentQty())));
                        entry.setCurrentQty(0);

                    } else {
                        log.info("SI HAY SUFICIENTE EN LA ENTRY: {}, SE NECESITA {}", entry.getCurrentQty(), neededQty);
                        BigDecimal reminderInEntry = BigDecimal.valueOf(entry.getCurrentQty()).subtract(neededQty);
                        ingredientStock.setTotal(ingredientStock.getTotal() - neededQty.intValue());
                        ingredientCost =
                                ingredientCost.add(unitCost.multiply(neededQty));
                        neededQty = BigDecimal.ZERO;
                        entry.setCurrentQty(reminderInEntry.intValue());
                        breakFor = true;
                    }
                    ingredientStock.getStockEntries().removeIf(stockEntry -> stockEntry.getId().equals(entry.getId()));
                    ingredientStock.addStockEntry(entry);
                    stockRepository.save(ingredientStock);
                    if (breakFor) break;
                }
                log.info("Cost for ingredient {}: {}", ri.getIngredient().getName(), ingredientCost);
                menuOptionCost = menuOptionCost.add(ingredientCost);
                if (neededQty.compareTo(BigDecimal.ZERO) != 0) {
                    throw new GenericException("No hay suficiente stock de: " + ri.getIngredient().getName() + " para procesar " +
                            "este dia");
                }
            }
            option.setCost(menuOptionCost);
            log.info("Total cost for Menu option {}: {}", option.getRecipe().getName(),
                    menuOptionCost);
            totalMenuCost = totalMenuCost.add(menuOptionCost);
        }
        menu.setProcessed(true);
        menu.setTotalCost(totalMenuCost);
        log.info("Costo total del menu {}: {}", menu.getId(), totalMenuCost);
        menuRepository.save(menu);
    }
}
