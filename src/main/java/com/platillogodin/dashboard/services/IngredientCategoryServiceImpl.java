package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.IngredientCategory;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.IngredientCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */

@Service
public class IngredientCategoryServiceImpl implements IngredientCategoryService {

    private final IngredientCategoryRepository ingredientCategoryRepository;

    public IngredientCategoryServiceImpl(IngredientCategoryRepository ingredientCategoryRepository) {
        this.ingredientCategoryRepository = ingredientCategoryRepository;
    }

    @Override
    public List<IngredientCategory> findAll() {
        return ingredientCategoryRepository.findAll();
    }

    @Override
    public IngredientCategory findById(Long id) {
        return ingredientCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La categoria de ingrediente "+id+" no existe"));
    }

    @Override
    public IngredientCategory saveIngredientCategory(IngredientCategory ingredientCategory) {
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public void deleteById(Long id) {
        ingredientCategoryRepository.deleteById(id);
    }
}
