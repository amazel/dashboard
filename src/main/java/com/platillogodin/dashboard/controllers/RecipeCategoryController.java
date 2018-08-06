package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.RecipeCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.services.RecipeCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class RecipeCategoryController {

    private final RecipeCategoryService recipeCategoryService;

    public RecipeCategoryController(RecipeCategoryService recipeCategoryService) {
        this.recipeCategoryService = recipeCategoryService;
    }

    public static final String RECIPE_FORM_URL = "categories/recipes/recipe_category_form";
    public static final String RECIPE_SHOW_URL = "categories/recipes/show";
    public static final String RECIPES_LIST_URL = "categories/recipes/list";

    @GetMapping("/categories/recipes")
    public String listRecipeCategories(Model model) {
        List<RecipeCategory> recipeCategoryList = recipeCategoryService.findAll();
        model.addAttribute("recipeCategoryList", recipeCategoryList);
        return RECIPES_LIST_URL;
    }

    @GetMapping("/categories/recipes/new")
    public String newRecipeCategory(Model model) {
        model.addAttribute("recipeCategory", new RecipeCategory());
        return RECIPE_FORM_URL;
    }

    @GetMapping("/categories/recipes/{id}/show")
    public String showCategoryRecipe(Model model, @PathVariable Long id) {
        RecipeCategory recipeCategory = recipeCategoryService.findById(id);
        log.info(recipeCategory.toString());
        model.addAttribute("recipeCategory", recipeCategory);
        return RECIPE_SHOW_URL;
    }

    @GetMapping("/categories/recipes/{id}/edit")
    public String updateCategoryRecipe(@PathVariable Long id, Model model) {
        RecipeCategory recipeCategory = recipeCategoryService.findById(id);
        model.addAttribute("recipeCategory", recipeCategory);
        return RECIPE_FORM_URL;
    }

    @GetMapping("/categories/recipes/{id}/delete")
    public String deleteCategoryRecipe(@PathVariable Long id, Model model) {
        try {
            recipeCategoryService.deleteById(id);
        } catch (ExistingReferencesException ere) {
            RecipeCategory rc = recipeCategoryService.findById(id);
            model.addAttribute("deleteError",
                    "Error al eliminar " + rc.getName() + ", existen recetas asociadas a esta categoría.");
            model.addAttribute("recipeCategoryList", recipeCategoryService.findAll());
            return RECIPES_LIST_URL;
        }
        return "redirect:/categories/recipes";
    }

    @PostMapping("/categories/recipe")
    public String saveOrUpdateRecipeCategory(@ModelAttribute("recipeCategory") RecipeCategory recipeCategory, BindingResult bindingResult) {
        log.info("Saving recipeCategory");
        RecipeCategory saved = recipeCategoryService.saveRecipeCategory(recipeCategory);
        return "redirect:/categories/recipes/" + saved.getId() + "/show";
    }
}
