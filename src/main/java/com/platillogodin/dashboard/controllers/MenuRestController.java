package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Menu;
import com.platillogodin.dashboard.domain.MenuCategory;
import com.platillogodin.dashboard.domain.MenuOption;
import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.services.MenuCategoryService;
import com.platillogodin.dashboard.services.MenuService;
import com.platillogodin.dashboard.services.RecipeCategoryService;
import com.platillogodin.dashboard.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class MenuRestController {
    private final RecipeService recipeService;
    private final RecipeCategoryService recipeCategoryService;
    private final MenuService menuService;
    private final MenuCategoryService menuCategoryService;

    public MenuRestController(RecipeService recipeService, RecipeCategoryService recipeCategoryService, MenuService menuService, MenuCategoryService menuCategoryService) {
        this.recipeService = recipeService;
        this.recipeCategoryService = recipeCategoryService;
        this.menuService = menuService;
        this.menuCategoryService = menuCategoryService;
    }


    @GetMapping("/api/recipes_map")
    public ResponseEntity<?> getRecipesMap() {
        log.info("REST //api/recipes_map");
        return ResponseEntity.ok(recipeService.findAllByCategory());
    }

    @GetMapping("/api/recipes")
    public ResponseEntity<?> getRecipes() {
        log.info("REST //api/recipes ");
        return ResponseEntity.ok(recipeService.findAll());
    }

    @GetMapping("/api/categories/recipes")
    public ResponseEntity<?> getRecipeCategories() {
        log.info("REST //api/categories/recipes ");
        return ResponseEntity.ok(recipeCategoryService.findAll());
    }

    @PostMapping("/api/menu_option")
    public ResponseEntity<?> saveMenuOption(@Valid @RequestBody MenuOption menuOption, Model model) {
        log.info("/api/menu_option");
        log.info(menuOption.toString());
        Menu menu = menuService.findById(menuOption.getMenu().getId());
        Recipe recipe = recipeService.findById(menuOption.getRecipe().getId());
        MenuCategory menuCategory = menuCategoryService.findById(menuOption.getMenuCategory().getId());
        menuOption.setRecipe(recipe);
        menuOption.setMenuCategory(menuCategory);
        if (menuOption.getId() != null) {
            log.info("UPDATING MENU OPTION {}", menuOption.getId());
            log.info("MenuOptions size: {}", menu.getOptions().size());
            menu.getOptions()
                    .removeIf(menuOption1 -> menuOption1.getId().equals(menuOption.getId()));
            log.info("MenuOptions size2: {}", menu.getOptions().size());
        }
        menu.addMenuOption(menuOption);
        Menu saved = menuService.save(menu);

        return ResponseEntity.ok("OK");
    }

}
