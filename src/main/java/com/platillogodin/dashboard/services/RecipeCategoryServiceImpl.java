package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.repositories.RecipeCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Service
public class RecipeCategoryServiceImpl implements RecipeCategoryService {

    private RecipeCategoryRepository recipeCategoryRepository;

    public RecipeCategoryServiceImpl(RecipeCategoryRepository recipeCategoryRepository) {
        this.recipeCategoryRepository = recipeCategoryRepository;
    }

    @Override
    public List<RecipeCategory> findAll() {
        return recipeCategoryRepository.findAll();
    }
}
