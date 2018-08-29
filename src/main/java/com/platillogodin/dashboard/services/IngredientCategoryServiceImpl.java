package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.IngredientCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.IngredientCategoryRepository;
import com.platillogodin.dashboard.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */

@Service
public class IngredientCategoryServiceImpl implements IngredientCategoryService {

    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientCategoryServiceImpl(IngredientCategoryRepository ingredientCategoryRepository, IngredientRepository ingredientRepository) {
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientCategory> findAll() {
        return ingredientCategoryRepository.findAll();
    }

    @Override
    public IngredientCategory findById(Long id) {
        return ingredientCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La categoría de ingrediente " + id + " no existe"));
    }

    @Override
    public IngredientCategory saveIngredientCategory(IngredientCategory ingredientCategory) {
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public void delete(IngredientCategory ingredientCategory) {

        Integer count = ingredientRepository.countAllByCategory(this.findById(ingredientCategory.getId()));
        if (count > 0) {
            throw new ExistingReferencesException();
        }
        ingredientCategoryRepository.delete(ingredientCategory);
    }
}
