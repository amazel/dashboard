package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.IngredientCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.services.IngredientCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class IngredientCategoryController {

    public static final String FORM_URL = "categories/ingredients/ingredient_category_form";
    public static final String LIST_URL = "categories/ingredients/list";

    private final IngredientCategoryService ingredientCategoryService;

    public IngredientCategoryController(IngredientCategoryService ingredientCategoryService) {
        this.ingredientCategoryService = ingredientCategoryService;
    }

    @GetMapping("/categories/ingredients")
    public String listIngredientCategories(Model model) {
        List<IngredientCategory> ingredientCategoryList = ingredientCategoryService.findAll();
        model.addAttribute("ingredientCategoryList", ingredientCategoryList);
        return LIST_URL;
    }

    @GetMapping("/categories/ingredients/new")
    public String newIngredientCategory(Model model) {
        model.addAttribute("ingredientCategory", new IngredientCategory());
        return FORM_URL;
    }

    @GetMapping("/categories/ingredients/{id}/edit")
    public String updateIngredientCategory(@PathVariable Long id, Model model) {
        IngredientCategory ingredientCategory = ingredientCategoryService.findById(id);
        model.addAttribute("ingredientCategory", ingredientCategory);
        return FORM_URL;
    }

    @GetMapping("/categories/ingredients/{id}/delete")
    public ModelAndView deleteIngredientCategory(@PathVariable Long id, Model model) {
        try {
            ingredientCategoryService.deleteById(id);
        } catch (ExistingReferencesException ere) {
            IngredientCategory rc = ingredientCategoryService.findById(id);
            model.addAttribute("deleteError",
                    "Error al eliminar " + rc.getName() + ", existen ingredientes asociados a esta categoría.");
            return new ModelAndView("forward:/categories/ingredients", model.asMap());
        }
        model.addAttribute("deleteMessage", "La categoria fue eliminada correctamente");
        return new ModelAndView("forward:/categories/ingredients", model.asMap());
    }

    @PostMapping("/categories/ingredient")
    public String saveOrUpdateIngredientCategory(@ModelAttribute("ingredientCategory") IngredientCategory ingredientCategory, BindingResult bindingResult) {
        log.info("Saving ingredientCategory");
        IngredientCategory saved = ingredientCategoryService.saveIngredientCategory(ingredientCategory);
        return "redirect:/categories/ingredients/";
    }
}
