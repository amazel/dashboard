package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Stock;
import com.platillogodin.dashboard.domain.StockEntry;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.services.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class StockRestController {

    private final StockService stockService;

    public StockRestController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/api/stock/entry")
    public ResponseEntity<?> saveStockEntry(@Valid @RequestBody StockEntry stockEntry, Model model) {
        log.info("/api/stock/entry - Saving Stock Entry");
        log.debug(stockEntry.toString());

        if (stockEntry.getStock() == null || stockEntry.getStock().getId() == null) {
            return ResponseEntity.badRequest().body("Missing Stock ID");
        }
        Stock stock = stockService.findById(stockEntry.getStock().getId());

        if (stockEntry.getId() != null) {
            StockEntry oldEntry =
                    stock.getStockEntries()
                            .stream()
                            .filter(stockEntry1 -> stockEntry1.getId().equals(stockEntry.getId()))
                            .findFirst()
                            .orElseThrow(NotFoundException::new);
            stock.setTotal(stock.getTotal()-oldEntry.getCurrentQty());
            stockEntry.setOriginalQty(oldEntry.getOriginalQty());
            stock.getStockEntries().remove(oldEntry);
        }
        stockService.saveStockEntry(stock, stockEntry);

        return ResponseEntity.ok("OK");
    }

}
