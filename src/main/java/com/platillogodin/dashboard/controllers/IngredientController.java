package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Ingredient;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.services.IngredientCategoryService;
import com.platillogodin.dashboard.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
public class IngredientController {

    private static final String FORM_URL = "ingredients/ingredient_form";
    private static final String LIST_URL = "ingredients/list";
    private static final String REDIRECT_LIST_URL = "redirect:/ingredients";


    private final IngredientService ingredientService;
    private final IngredientCategoryService ingredientCategoryService;

    public IngredientController(IngredientService ingredientService, IngredientCategoryService ingredientCategoryService) {
        this.ingredientService = ingredientService;
        this.ingredientCategoryService = ingredientCategoryService;
    }

    @GetMapping("/ingredients")
    public String listIngredients(Model model) {

        List<Ingredient> ingredientList = ingredientService.findAll();
        model.addAttribute("ingredientList", ingredientList);
        return "ingredients/list";
    }

    @GetMapping("ingredients/new")
    public String newIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("ingredientCategories", ingredientCategoryService.findAll());
        return FORM_URL;
    }

    @GetMapping("/ingredients/{id}/edit")
    public String updateIngredient(@PathVariable Long id, Model model) {
        log.info("Edit ingredient");
        Ingredient ingredient = ingredientService.findById(id);
        log.info(ingredient.toString());
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("ingredientCategories", ingredientCategoryService.findAll());
        return FORM_URL;
    }

    @GetMapping("/ingredients/{id}/delete")
    public ModelAndView deleteIngredient(@PathVariable Long id, Model model) {
        log.info("Deleting ingredient");
        Ingredient ingredient = ingredientService.findById(id);
        try {
            ingredientService.delete(ingredient);
        } catch (DataIntegrityViolationException ere) {
            model.addAttribute("deleteError",
                    "Error al eliminar " + ingredient.getName() + ", existen recetas que contienen este ingrediente.");
            return new ModelAndView("forward:/ingredients", model.asMap());
        }
        model.addAttribute("deleteMessage", "El ingrediente " + ingredient.getName() + " fue eliminado correctamente");
        return new ModelAndView("forward:/ingredients", model.asMap());
    }

    @PostMapping(value = "ingredient")
    public String saveOrUpdateIngredient(@ModelAttribute("ingredient") Ingredient ingredient, BindingResult bindingResult) {
        log.info("Saving ingredient");
        log.info(ingredient.toString());
        Ingredient saved = ingredientService.saveIngredient(ingredient);
        log.info(saved.toString());
        return REDIRECT_LIST_URL;
    }
}
