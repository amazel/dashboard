package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Stock;
import com.platillogodin.dashboard.services.IngredientService;
import com.platillogodin.dashboard.services.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class StockController {

    private static final String ADD_URL = "stock/addItem";
    private static final String LIST_URL = "stock/list";

    private final StockService stockService;
    private final IngredientService ingredientService;


    public StockController(StockService stockService, IngredientService ingredientService) {
        this.stockService = stockService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/stock")
    public String listStockItems(Model model) {
        log.info("Listing all stocks");
        List<Stock> stockItems = stockService.findAll();
        model.addAttribute("stockItems", stockItems);
        return LIST_URL;
    }

    @GetMapping("/stock/add")
    public String addItem(Model model, @RequestParam(required = false) Long ingredientId) {
        Stock stockItem = new Stock();
        if (ingredientId != null) {
            stockItem.setIngredient(ingredientService.findById(ingredientId));

        }
        model.addAttribute("stockItem", stockItem);
        model.addAttribute("ingredientList", ingredientService.findAll());
        return ADD_URL;
    }


    @GetMapping("/stock/{stockId}/edit")
    public String editItem(Model model, @PathVariable Long stockId) {
        model.addAttribute("stockItem", stockService.findById(stockId));
        model.addAttribute("ingredientList", ingredientService.findAll());
        return ADD_URL;
    }

    @PostMapping("/stock")
    public ModelAndView saveOrUpdateItem(@ModelAttribute("stockItem") Stock stock, BindingResult bindingResult) {
        log.info("Saving stock");
        log.info(stock.toString());
        ModelAndView modelAndView = new ModelAndView();
        try {
            stockService.saveStockItem(stock);
        } catch (Exception e) {
            log.info("Catching exception {}", bindingResult.getModel().keySet());
            bindingResult.rejectValue("ingredient.name", "error.name", "Ya existe el ingrediente en el inventario.");
            modelAndView.addAllObjects(bindingResult.getModel());
            modelAndView.setViewName(ADD_URL);
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/stock");
        return modelAndView;
    }
}
