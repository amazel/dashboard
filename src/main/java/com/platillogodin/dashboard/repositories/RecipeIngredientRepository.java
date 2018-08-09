package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.domain.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    List<RecipeIngredient> findAllByRecipe(Recipe recipe);
}
