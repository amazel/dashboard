package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.IngredientCategory;
import com.platillogodin.dashboard.domain.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {
}
