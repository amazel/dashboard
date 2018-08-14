package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Stock;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface StockService {
    Stock findById(Long id);

    void saveStockItem(Stock stock);

    List<Stock> findAll();
}
