package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.services.RecipeCategoryService;
import com.platillogodin.dashboard.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class RecipeController {

    private static final String FORM_URL = "recipes/recipe_form";
    private static final String SHOW_URL = "recipes/show";
    private static final String LIST_URL = "recipes/list";

    private final RecipeService recipeService;
    private final RecipeCategoryService recipeCategoryService;

    public RecipeController(RecipeService recipeService, RecipeCategoryService recipeCategoryService) {
        this.recipeService = recipeService;
        this.recipeCategoryService = recipeCategoryService;
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model) {

        List<Recipe> recipeList = recipeService.findAll();
        model.addAttribute("recipeList", recipeList);
        return LIST_URL;
    }

    @GetMapping("recipes/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new Recipe());
        model.addAttribute("recipeCategories", recipeCategoryService.findAll());
        return FORM_URL;
    }

    @GetMapping("/recipes/{id}/show")
    public String showRecipe(Model model, @PathVariable Long id) {
        Recipe recipe = recipeService.findById(id);
        model.addAttribute("recipe", recipe);
        return SHOW_URL;
    }

    @GetMapping("/recipes/{id}/edit")
    public String updateRecipe(@PathVariable Long id, Model model) {
        log.info("Edit recipe");
        Recipe recipe = recipeService.findById(id);
        log.info(recipe.toString());
        model.addAttribute("recipe", recipe);
        model.addAttribute("recipeCategories", recipeCategoryService.findAll());
        return FORM_URL;
    }

    @GetMapping("/recipes/{id}/delete")
    public String deleteRecipe(@PathVariable Long id, Model model, RedirectAttributes ra) {
        log.info("Deleting recipe");
        Recipe recipe = recipeService.findById(id);
        try {
            recipeService.delete(recipe);
            ra.addFlashAttribute("deleteMessage", "La receta " + recipe.getName() + " fue eliminada correctamente");
        } catch (DataIntegrityViolationException ere) {
            ra.addFlashAttribute("deleteError",
                    "Error al eliminar " + recipe.getName() + ", la receta ha sido usada en menus.");
        }
        return "redirect:/recipes";
    }

    @PostMapping(value = "recipe")
    public String saveOrUpdateRecipe(@ModelAttribute("recipe") Recipe recipe) {
        log.info("Saving recipe");
        Recipe saved = recipeService.saveRecipe(recipe);
        log.info(saved.toString());
        return "redirect:/recipes/" + saved.getId() + "/show";
    }
}
