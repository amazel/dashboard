package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.domain.RecipeCategory;

import java.util.List;
import java.util.Map;

/**
 * Created by Hugo Lezama on August - 2018
 */

public interface RecipeService {
    List<Recipe> findAll();

    Recipe findById(Long id);

    Recipe saveRecipe(Recipe recipe);

    void deleteById(Long id);

    Map<String, List<Recipe>> findAllByCategory();
}
