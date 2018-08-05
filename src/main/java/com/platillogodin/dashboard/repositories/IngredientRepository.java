package com.platillogodin.dashboard.repositories;

import com.platillogodin.dashboard.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
