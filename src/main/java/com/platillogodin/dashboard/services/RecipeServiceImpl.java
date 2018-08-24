package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Hugo Lezama on August - 2018
 */

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La receta "+id+" no existe"));
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public Map<String, List<Recipe>> findAllByCategory() {

        return recipeRepository.findAll().stream().collect(Collectors.groupingBy(o -> o.getRecipeCategory().getName()));
    }
}
