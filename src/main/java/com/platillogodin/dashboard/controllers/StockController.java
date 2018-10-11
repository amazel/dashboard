package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Stock;
import com.platillogodin.dashboard.services.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class StockController {

    private static final String LIST_URL = "stock/list";
    private static final String SHOW_URL = "stock/show";

    private final StockService stockService;


    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stock")
    public String listStockItems(Model model) {
        List<Stock> stockItems = stockService.findAll();
        model.addAttribute("stockItems", stockItems);
        return LIST_URL;
    }

    @GetMapping("/stock/{stockId}/show")
    public String showStock(Model model, @PathVariable Long stockId, @RequestParam(required = false) String showAll) {
        model.addAttribute("stockItem",
                (showAll != null && showAll.equals("true")) ?
                        stockService.findById(stockId) :
                        stockService.findByIdFiltered(stockId));
        model.addAttribute("showAll", showAll);
        return SHOW_URL;
    }

    @GetMapping("/stock/{stockId}/entry/add")
    public String addEntry(Model model, @PathVariable Long stockId) {
        model.addAttribute("stockItem", stockService.findById(stockId));
        return SHOW_URL;
    }
}
