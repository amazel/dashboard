package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Ingredient;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface IngredientService {

    List<Ingredient> findAll();

    Ingredient findById(Long id);

    Ingredient saveIngredient(Ingredient ingredient);

    void delete(Ingredient ingredient);
}
