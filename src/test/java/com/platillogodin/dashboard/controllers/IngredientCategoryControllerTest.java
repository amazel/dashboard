package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.IngredientCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.services.IngredientCategoryService;
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

public class IngredientCategoryControllerTest {

    private static final String FORM_URL = "categories/ingredients/ingredient_category_form";
    private static final String LIST_URL = "categories/ingredients/list";
    private static final String BASE_URL = "/categories/ingredients";


    IngredientCategoryController ingredientCategoryController;

    @Mock
    IngredientCategoryService ingredientCategoryService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientCategoryController = new IngredientCategoryController(ingredientCategoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientCategoryController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void listIngredientCategories() throws Exception {
        when(ingredientCategoryService.findAll()).thenReturn(Arrays.asList(new IngredientCategory()));
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_URL))
                .andExpect(model().attributeExists("ingredientCategoryList"));
    }

    @Test
    public void newIngredientCategory() throws Exception {
        mockMvc.perform(get(BASE_URL + "/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("ingredientCategory"));
    }

    @Test
    public void updateIngredientCategory() throws Exception {
        when(ingredientCategoryService.findById(anyLong())).thenReturn(new IngredientCategory());
        mockMvc.perform(get(BASE_URL + "/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("ingredientCategory"));
    }

    @Test
    public void deleteIngredientCategory() throws Exception {
        when(ingredientCategoryService.findById(anyLong())).thenReturn(new IngredientCategory());
        mockMvc.perform(get(BASE_URL + "/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/ingredients"))
                .andExpect(flash().attributeExists("deleteMessage"));
    }

    @Test
    public void deleteIngredientCategoryError() throws Exception {
        when(ingredientCategoryService.findById(anyLong())).thenReturn(new IngredientCategory());
        doThrow(new ExistingReferencesException("")).when(ingredientCategoryService).delete(any());
        mockMvc.perform(get(BASE_URL + "/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/ingredients"))
                .andExpect(flash().attributeExists("deleteError"));
    }

    @Test
    public void saveOrUpdateIngredientCategory() throws Exception {
        IngredientCategory saved = new IngredientCategory();
        saved.setId(200L);
        when(ingredientCategoryService.saveIngredientCategory(any())).thenReturn(saved);

        mockMvc.perform(post("/categories/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/ingredients/"));
    }
}