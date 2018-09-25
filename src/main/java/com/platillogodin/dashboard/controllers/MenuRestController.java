package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Menu;
import com.platillogodin.dashboard.domain.MenuCategory;
import com.platillogodin.dashboard.domain.MenuOption;
import com.platillogodin.dashboard.domain.Recipe;
import com.platillogodin.dashboard.exceptions.GenericException;
import com.platillogodin.dashboard.services.MenuCategoryService;
import com.platillogodin.dashboard.services.MenuService;
import com.platillogodin.dashboard.services.RecipeCategoryService;
import com.platillogodin.dashboard.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/api/menu_option/delete")
    public ResponseEntity<?> deleteMenuOption(@RequestParam("menuOptionId") Long menuOptionId,
                                              @RequestParam("menuId") String menuId) {
        log.info("Deleting option: {} from Menu {}", menuOptionId, menuId);
        Menu menu = menuService.findById(menuId);
        log.info("menu: {}", menu.getOptions().size());
        menu.getOptions().removeIf(menuOption -> menuOption.getId().equals(menuOptionId));
        log.info("menu2: {}", menu.getOptions().size());
        menuService.save(menu);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/api/menu_option")
    public ResponseEntity<?> saveMenuOption(@Valid @RequestBody MenuOption menuOption) {
        log.info("/api/menu_option");
        log.info(menuOption.toString());
        Menu menu = menuService.findById(menuOption.getMenu().getId());
        Recipe recipe = recipeService.findById(menuOption.getRecipe().getId());
        MenuCategory menuCategory = menuCategoryService.findById(menuOption.getMenuCategory().getId());
        menuOption.setRecipe(recipe);
        menuOption.setMenuCategory(menuCategory);
        if (menuOption.getId() != null) {
            menu.getOptions()
                    .removeIf(menuOption1 -> menuOption1.getId().equals(menuOption.getId()));
        }
        menu.addMenuOption(menuOption);
        Menu saved = menuService.save(menu);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/api/menu/process-day")
    public ResponseEntity<?> processDay(@RequestParam("menuId") String menuId) {
        log.info("/api/menu/process-day");
        log.info(menuId);
        Menu menu = menuService.findById(menuId);
        if (menu.getOptions().size() == 0) {
            return ResponseEntity.badRequest().body("No hay platillos en este dia para procesar");
        }
        try {
            menuService.processMenuDay(menu);
        } catch (GenericException ge) {
            return ResponseEntity.badRequest().body(ge.getMessage());
        }
        return ResponseEntity.ok("El menú fue procesado con éxito!");
    }
}
