package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Recipe;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */

public interface RecipeService {
    List<Recipe> findAll();

    Recipe findById(Long id);

    Recipe saveRecipe(Recipe recipe);

    void deleteById(Long id);

}
