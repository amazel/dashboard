package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.MenuCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.services.MenuCategoryService;
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

public class MenuCategoryControllerTest {

    private static final String FORM_URL = "categories/menus/menu_category_form";
    private static final String LIST_URL = "categories/menus/list";
    private static final String BASE_URL = "/categories/menus";


    MenuCategoryController menuCategoryController;

    @Mock
    MenuCategoryService menuCategoryService;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        menuCategoryController = new MenuCategoryController(menuCategoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(menuCategoryController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }


    @Test
    public void listMenuCategories() throws Exception {
        when(menuCategoryService.findAll()).thenReturn(Arrays.asList(new MenuCategory()));
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LIST_URL))
                .andExpect(model().attributeExists("menuCategoryList"));
    }

    @Test
    public void newMenuCategory() throws Exception {
        mockMvc.perform(get(BASE_URL + "/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("menuCategory"));
    }

    @Test
    public void updateCategoryMenu() throws Exception {
        when(menuCategoryService.findById(anyLong())).thenReturn(new MenuCategory());
        mockMvc.perform(get(BASE_URL + "/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(FORM_URL))
                .andExpect(model().attributeExists("menuCategory"));
    }

    @Test
    public void deleteCategoryMenu() throws Exception {
        when(menuCategoryService.findById(anyLong())).thenReturn(new MenuCategory());
        mockMvc.perform(get(BASE_URL + "/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/menus"))
                .andExpect(flash().attributeExists("deleteMessage"));
    }

    @Test
    public void deleteCategoryMenuError() throws Exception {
        when(menuCategoryService.findById(anyLong())).thenReturn(new MenuCategory());
        doThrow(new ExistingReferencesException("")).when(menuCategoryService).delete(any());
        mockMvc.perform(get(BASE_URL + "/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/menus"))
                .andExpect(flash().attributeExists("deleteError"));
    }


    @Test
    public void saveOrUpdateMenuCategory() throws Exception {
        MenuCategory saved = new MenuCategory();
        saved.setId(200L);
        when(menuCategoryService.saveMenuCategory(any())).thenReturn(saved);

        mockMvc.perform(post("/categories/menu")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories/menus/"));
    }
}