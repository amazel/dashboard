package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Stock;
import com.platillogodin.dashboard.domain.StockEntry;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock findById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No hay registro para este ingrediente"));
    }

    @Override
    public Stock findByIdFiltered(Long id) {
        return stockRepository.findByIdFiltered(id)
                .orElse(stockRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("No hay registro para este ingrediente")));
    }

    @Override
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock saveStockEntry(Stock stock, StockEntry entry) {
        if (entry.getId() == null) {
            entry.setOriginalQty(entry.getCurrentQty());
        }
        entry.setExpirationDate(entry.getSupplyDate().plusDays(stock.getIngredient().getExpirationTime()));
        stock.setTotal(stock.getTotal() + entry.getCurrentQty());
        if (stock.getLastSupplyDate() == null || entry.getSupplyDate().isAfter(stock.getLastSupplyDate())) {
            stock.setLastSupplyDate(entry.getSupplyDate());
        }
        if (stock.getNextExpirationDate() == null || entry.getExpirationDate().isBefore(stock.getNextExpirationDate())) {
            stock.setNextExpirationDate(entry.getExpirationDate());
        }
        stock.addStockEntry(entry);
        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }
}
