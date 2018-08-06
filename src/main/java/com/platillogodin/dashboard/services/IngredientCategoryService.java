package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.IngredientCategory;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */

public interface IngredientCategoryService {
    List<IngredientCategory> findAll();

    IngredientCategory findById(Long id);

    IngredientCategory saveIngredientCategory(IngredientCategory ingredientCategory);

    void deleteById(Long id);
}
