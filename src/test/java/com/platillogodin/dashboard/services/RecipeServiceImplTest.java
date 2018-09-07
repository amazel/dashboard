package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void findAll() {
        //  GIVEN
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(new Recipe()));

        //  WHEN
        List<Recipe> recipes = recipeService.findAll();

        //  THEN
        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void findById() {
    }

    @Test
    public void saveRecipe() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findAllByCategory() {
    }
}