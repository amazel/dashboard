package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Ingredient;
import com.platillogodin.dashboard.domain.IngredientCategory;
import com.platillogodin.dashboard.services.IngredientCategoryService;
import com.platillogodin.dashboard.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

@Slf4j
public class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;
    @Mock
    IngredientCategoryService ingredientCategoryService;

    IngredientController ingredientController;
    MockMvc mockMvc;

    private static final String FORM_URL = "ingredients/ingredient_form";
    private static final String LIST_URL = "ingredients/list";
    private static final String REDIRECT_LIST_URL = "redirect:/ingredients";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ingredientController = new IngredientController(ingredientService, ingredientCategoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void listIngredients() throws Exception {

        when(ingredientService.findAll()).thenReturn(Arrays.asList(new Ingredient()));

        mockMvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_URL))
                .andExpect(model().attributeExists("ingredientList"));
    }

    @Test
    public void newIngredient() throws Exception {

        when(ingredientCategoryService.findAll()).thenReturn(Arrays.asList(new IngredientCategory()));
        mockMvc.perform(get("/ingredients/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("ingredientCategories"));

    }

    @Test
    public void updateIngredient() throws Exception {
        when(ingredientService.findById(anyLong())).thenReturn(new Ingredient());

        mockMvc.perform(get("/ingredients/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("ingredientCategories"));
    }

    @Test
    public void deleteIngredient() throws Exception {
        when(ingredientService.findById(anyLong())).thenReturn(new Ingredient());

        mockMvc.perform(get("/ingredients/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ingredients"))
                .andExpect(flash().attributeExists("deleteMessage"));
    }

    @Test
    public void deleteIngredientError() throws Exception {
        when(ingredientService.findById(anyLong())).thenReturn(new Ingredient());

        doThrow(new DataIntegrityViolationException("")).when(ingredientService).delete(any());

        mockMvc.perform(get("/ingredients/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/ingredients"))
                .andExpect(flash().attributeExists("deleteError"));
    }


    @Test
    public void saveOrUpdateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(200L);
        when(ingredientService.saveIngredient(any(Ingredient.class), any())).thenReturn(ingredient);
        mockMvc.perform(post("/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(REDIRECT_LIST_URL));
    }
}