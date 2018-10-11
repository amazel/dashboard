package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Ingredient;
import com.platillogodin.dashboard.services.IngredientCategoryService;
import com.platillogodin.dashboard.services.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
        return LIST_URL;
    }

    @GetMapping("ingredients/new")
    public String newIngredient(Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("ingredientCategories", ingredientCategoryService.findAll());
        return FORM_URL;
    }

    @GetMapping("/ingredients/{id}/edit")
    public String updateIngredient(@PathVariable Long id, Model model) {
        Ingredient ingredient = ingredientService.findById(id);
        log.info(ingredient.toString());
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("ingredientCategories", ingredientCategoryService.findAll());
        return FORM_URL;
    }

    @GetMapping("/ingredients/{id}/delete")
    public String deleteIngredient(@PathVariable Long id, Model model, RedirectAttributes ra, HttpServletRequest req) {
        log.info("Deleting ingredient {}, user= {}", id, req.getSession(false).getAttribute("user"));
        Ingredient ingredient = ingredientService.findById(id);
        log.info("Ingredient name= {}", ingredient.getName());
        try {
            ingredientService.delete(ingredient);
            ra.addFlashAttribute("deleteMessage", "El ingrediente " + ingredient.getName() + " fue eliminado correctamente");
        } catch (DataIntegrityViolationException ere) {
            ra.addFlashAttribute("deleteError",
                    "Error al eliminar " + ingredient.getName() + ", existen recetas que contienen este ingrediente.");
        }
        return "redirect:/ingredients";
    }

    @PostMapping("ingredients")
    public String saveOrUpdateIngredient(@ModelAttribute("ingredient") Ingredient ingredient,
                                         @RequestParam(value = "initialPrice", required = false) String price,
                                         HttpServletRequest req) {
        log.info("Saving ingredient, user= {}", req.getSession(false).getAttribute("user"));
        log.info("Ingredient= {}", ingredient);
        Ingredient saved = ingredientService.saveIngredient(ingredient, price);
        return REDIRECT_LIST_URL;
    }
}
