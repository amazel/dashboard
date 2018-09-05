package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Ingredient;
import com.platillogodin.dashboard.domain.Stock;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final StockService stockService;


    public IngredientServiceImpl(IngredientRepository ingredientRepository, StockService stockService) {
        this.ingredientRepository = ingredientRepository;
        this.stockService = stockService;
    }

    @Override
    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("El ingrediente " + id + " no existe"));
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
        Ingredient saved = ingredientRepository.save(ingredient);
        Stock stock = new Stock();
        stock.setIngredient(saved);
        stockService.saveStock(stock);
        return saved;
    }

    @Override
    public void delete(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }
}
