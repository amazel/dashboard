package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.RecipeIngredient;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.RecipeIngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Service
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeService recipeService;

    public RecipeIngredientServiceImpl(RecipeIngredientRepository recipeIngredientRepository, RecipeService recipeService) {
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.recipeService = recipeService;
    }

    @Override
    public List<RecipeIngredient> findAllByRecipeId(Long id) {
        return recipeIngredientRepository.findAllByRecipe(recipeService.findById(id));
    }

    @Override
    public RecipeIngredient findById(Long riId) {
        return recipeIngredientRepository.findById(riId)
                .orElseThrow(() -> new NotFoundException("El ingrediente buscado no existe"));
    }

    @Override
    public void deleteById(Long riId) {
        recipeIngredientRepository.deleteById(riId);
    }

    @Override
    public RecipeIngredient saveRecipeIngredient(RecipeIngredient recipeIngredient) {
        return recipeIngredientRepository.save(recipeIngredient);
    }
}
