package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.services.RecipeCategoryService;
import com.platillogodin.dashboard.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    RecipeCategoryService recipeCategoryService;
    RecipeController recipeController;
    MockMvc mockMvc;

    private static final String FORM_URL = "recipes/recipe_form";
    private static final String SHOW_URL = "recipes/show";
    private static final String LIST_URL = "recipes/list";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService, recipeCategoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void listRecipes() throws Exception {

        when(recipeService.findAll()).thenReturn(Arrays.asList(new Recipe()));

        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_URL))
                .andExpect(model().attributeExists("recipeList"));
    }

    @Test
    public void newRecipe() throws Exception {

        when(recipeCategoryService.findAll()).thenReturn(Arrays.asList(new RecipeCategory()));
        mockMvc.perform(get("/recipes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("recipeCategories"));

    }

    @Test
    public void showRecipe() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(new Recipe());

        mockMvc.perform(get("/recipes/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name(SHOW_URL))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void updateRecipe() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(new Recipe());

        mockMvc.perform(get("/recipes/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("recipe"))
                .andExpect(model().attributeExists("recipeCategories"));
    }

    @Test
    public void deleteRecipe() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(new Recipe());

        mockMvc.perform(get("/recipes/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes"))
                .andExpect(flash().attributeExists("deleteMessage"));
    }

    @Test
    public void deleteRecipeError() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(new Recipe());

        doThrow(new DataIntegrityViolationException("")).when(recipeService).delete(any());

        mockMvc.perform(get("/recipes/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes"))
                .andExpect(flash().attributeExists("deleteError"));
    }


    @Test
    public void saveOrUpdateRecipe() throws Exception {
        Recipe saved = new Recipe();
        saved.setId(200L);
        when(recipeService.saveRecipe(any())).thenReturn(saved);

        mockMvc.perform(post("/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/200/show"));
    }
}