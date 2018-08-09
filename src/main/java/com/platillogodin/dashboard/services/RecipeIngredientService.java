package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.domain.RecipeIngredient;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface RecipeIngredientService {
    List<RecipeIngredient> findAllByRecipeId(Long id);

    RecipeIngredient findById(Long riId);

    void deleteById(Long riId);

    RecipeIngredient saveRecipeIngredient(RecipeIngredient recipeIngredient);
}
