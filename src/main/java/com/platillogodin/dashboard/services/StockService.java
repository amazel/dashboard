package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Stock;
import com.platillogodin.dashboard.domain.StockEntry;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface StockService {
    Stock findById(Long id);
    Stock findByIdFiltered(Long id);

    Stock saveStock(Stock stock);

    List<Stock> findAll();

    Stock saveStockEntry(Stock stock, StockEntry entry);
}
