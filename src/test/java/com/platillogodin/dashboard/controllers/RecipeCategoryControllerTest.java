package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.services.RecipeCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

public class RecipeCategoryControllerTest {

    private static final String FORM_URL = "categories/recipes/recipe_category_form";
    private static final String LIST_URL = "categories/recipes/list";
    private static final String BASE_URL = "/categories/recipes";


    RecipeCategoryController recipeCategoryController;

    @Mock
    RecipeCategoryService recipeCategoryService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeCategoryController = new RecipeCategoryController(recipeCategoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeCategoryController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }


    @Test
    public void listRecipeCategories() throws Exception {
        when(recipeCategoryService.findAll()).thenReturn(Arrays.asList(new RecipeCategory()));
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_URL))
                .andExpect(model().attributeExists("recipeCategoryList"));
    }

    @Test
    public void newRecipeCategory() throws Exception {
        mockMvc.perform(get(BASE_URL + "/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("recipeCategory"));
    }

    @Test
    public void updateCategoryRecipe() throws Exception {
        when(recipeCategoryService.findById(anyLong())).thenReturn(new RecipeCategory());
        mockMvc.perform(get(BASE_URL + "/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("recipeCategory"));
    }

    @Test
    public void deleteCategoryRecipe() throws Exception {
        when(recipeCategoryService.findById(anyLong())).thenReturn(new RecipeCategory());
        mockMvc.perform(get(BASE_URL + "/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/recipes"))
                .andExpect(flash().attributeExists("deleteMessage"));
    }

    @Test
    public void deleteCategoryRecipeError() throws Exception {
        when(recipeCategoryService.findById(anyLong())).thenReturn(new RecipeCategory());
        doThrow(new ExistingReferencesException("")).when(recipeCategoryService).delete(any());
        mockMvc.perform(get(BASE_URL + "/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/recipes"))
                .andExpect(flash().attributeExists("deleteError"));
    }


    @Test
    public void saveOrUpdateRecipeCategory() throws Exception {
        RecipeCategory saved = new RecipeCategory();
        saved.setId(200L);
        when(recipeCategoryService.saveRecipeCategory(any())).thenReturn(saved);

        mockMvc.perform(post("/categories/recipe")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/recipes/"));
    }
}