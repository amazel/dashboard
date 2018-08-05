package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.domain.RecipeCategory;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */

public interface RecipeCategoryService {
    List<RecipeCategory> findAll();
}
