package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.RecipeIngredient;
import com.platillogodin.dashboard.services.IngredientService;
import com.platillogodin.dashboard.services.RecipeIngredientService;
import com.platillogodin.dashboard.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class RecipeIngredientController {

    private static final String ADD_URL = "recipes/ingredients/addIngredient";
    private static final String LIST_URL = "recipes/ingredients/list";

    private final RecipeService recipeService;
    private final RecipeIngredientService recipeIngredientService;
    private final IngredientService ingredientService;


    public RecipeIngredientController(RecipeService recipeService, RecipeIngredientService recipeIngredientService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.recipeIngredientService = recipeIngredientService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipes/{recipeId}/ingredients")
    public String listIngredients(Model model, @PathVariable Long recipeId) {
        model.addAttribute("recipe", recipeService.findById(recipeId));
        model.addAttribute("recipeIngredientList", recipeIngredientService.findAllByRecipeId(recipeId));
        return LIST_URL;
    }

    @GetMapping("/recipes/{recipeId}/ingredients/add")
    public String newIngredient(Model model, @PathVariable Long recipeId,
                                @RequestParam(required = false) Long ingredientId) {

        model.addAttribute("ingredientList", ingredientService.findAll());
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipeService.findById(recipeId));
        if (ingredientId != null) {
            recipeIngredient.setIngredient(ingredientService.findById(ingredientId));
        }
        model.addAttribute("recipeIngredient", recipeIngredient);
        return ADD_URL;
    }


    @GetMapping("/recipes/{recipeId}/ingredients/{riId}/delete")
    public String deleteIngredient(Model model, @PathVariable Long recipeId, @PathVariable Long riId) {

        recipeIngredientService.deleteById(riId);

        return "redirect:/recipes/" + recipeId + "/show";

    }

    @GetMapping("/recipes/{recipeId}/ingredients/{riId}/edit")
    public String editIngredient(Model model, @PathVariable Long recipeId, @PathVariable Long riId) {
        model.addAttribute("ingredientList", ingredientService.findAll());
        model.addAttribute("recipeIngredient", recipeIngredientService.findById(riId));
        return ADD_URL;
    }

    @PostMapping("/recipes/{recipeId}/ingredient")
    public ModelAndView saveOrUpdateIngredient(@PathVariable Long recipeId,
                                               @ModelAttribute("recipeIngredient") RecipeIngredient recipeIngredient,
                                               BindingResult bindingResult) {
        log.info("Saving recipe ingredient");
        log.info(recipeIngredient.toString());
        recipeIngredient.setRecipe(recipeService.findById(recipeId));
        ModelAndView modelAndView =  new ModelAndView();
        try {
            recipeIngredientService.saveRecipeIngredient(recipeIngredient);
        }catch (Exception e){
            log.info("Catching exception {}",bindingResult.getModel().keySet());
            bindingResult.rejectValue("ingredient.name","error.name","Ya existe el ingrediente en la receta, " +
                            "selecciona otro!");
            modelAndView.addAllObjects(bindingResult.getModel());
            modelAndView.setViewName(ADD_URL);

            return modelAndView;

        }
        modelAndView.setViewName("redirect:/recipes/" + recipeId + "/show");
        return modelAndView;

    }
}
