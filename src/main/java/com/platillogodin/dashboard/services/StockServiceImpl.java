package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Stock;
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
    public void saveStockItem(Stock stock) {
        stock.setExpirationDate(stock.getLastSupplyDate().plusDays(stock.getIngredient().getExpirationTime()));
        stockRepository.save(stock);
    }

    @Override
    public List<Stock> findAll() {
        return stockRepository.findAll();
    }
}
