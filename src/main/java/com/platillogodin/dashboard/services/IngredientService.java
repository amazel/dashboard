package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Ingredient;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
public interface IngredientService {

    List<Ingredient> findAll();
}
