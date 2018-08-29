package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.exceptions.NotFoundException;
import com.platillogodin.dashboard.repositories.RecipeCategoryRepository;
import com.platillogodin.dashboard.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Service
public class RecipeCategoryServiceImpl implements RecipeCategoryService {

    private final RecipeCategoryRepository recipeCategoryRepository;
    private final RecipeRepository recipeRepository;

    public RecipeCategoryServiceImpl(RecipeCategoryRepository recipeCategoryRepository, RecipeRepository recipeRepository) {
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<RecipeCategory> findAll() {
        return recipeCategoryRepository.findAll();
    }

    @Override
    public RecipeCategory findById(Long id) {
        return recipeCategoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("La categoría de receta "+id+" no existe"));
    }

    @Override
    public RecipeCategory saveRecipeCategory(RecipeCategory recipeCategory) {
        return recipeCategoryRepository.save(recipeCategory);
    }

    @Override
    public void delete(RecipeCategory recipeCategory) {
        Integer count = recipeRepository.countAllByRecipeCategory(this.findById(recipeCategory.getId()));
        if(count > 0){
            throw new ExistingReferencesException();
        }
        recipeCategoryRepository.delete(recipeCategory);
    }
}
