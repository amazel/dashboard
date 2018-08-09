package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.services.IngredientService;
import com.platillogodin.dashboard.services.RecipeCategoryService;
import com.platillogodin.dashboard.services.RecipeService;
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
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final RecipeCategoryService recipeCategoryService;

    public RecipeController(RecipeService recipeService, IngredientService ingredientService, RecipeCategoryService recipeCategoryService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.recipeCategoryService = recipeCategoryService;
    }

    public static final String RECIPE_FORM_URL = "recipes/recipe_form";
    public static final String RECIPE_SHOW_URL = "recipes/show";

    @GetMapping("/recipes")
    public String listRecipes(Model model) {

        List<Recipe> recipeList = recipeService.findAll();
        model.addAttribute("recipeList", recipeList);
        return "recipes/list";
    }

    @GetMapping("recipes/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("recipeCategories", recipeCategoryService.findAll());
        return RECIPE_FORM_URL;
    }

    @GetMapping("/recipes/{id}/show")
    public String showRecipe(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return RECIPE_SHOW_URL;
    }

    @GetMapping("/recipes/{id}/edit")
    public String updateRecipe(@PathVariable Long id, Model model) {
        log.info("Edit recipe");
        Recipe recipe = recipeService.findById(id);
        log.info(recipe.toString());
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeCategories", recipeCategoryService.findAll());
        return RECIPE_FORM_URL;
    }

    @GetMapping("/recipes/{id}/delete")
    public String deleteRecipe(@PathVariable Long id, Model model) {
        recipeService.deleteById(id);
        return "redirect:/recipes";
    }

    @PostMapping(value = "recipe")
    public String saveOrUpdateRecipe(@ModelAttribute("recipe") Recipe recipe, BindingResult bindingResult) {
        log.info("Saving recipe");
        log.info(recipe.toString());
        Recipe saved = recipeService.saveRecipe(recipe);
        log.info(saved.toString());
        return "redirect:/recipes/" + saved.getId() + "/show";
    }
}
