package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.IngredientCategory;
import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */

public interface RecipeCategoryService {
    List<RecipeCategory> findAll();

    RecipeCategory findById(Long id);

    RecipeCategory saveRecipeCategory(RecipeCategory recipeCategory);

    void deleteById(Long id) throws ExistingReferencesException;
}
