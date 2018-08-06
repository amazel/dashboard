package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.domain.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Integer countAllByRecipeCategory(RecipeCategory recipeCategory);
}
