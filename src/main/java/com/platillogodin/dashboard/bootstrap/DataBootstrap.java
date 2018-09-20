package com.platillogodin.dashboard.bootstrap;

import com.platillogodin.dashboard.domain.*;
import com.platillogodin.dashboard.repositories.IngredientCategoryRepository;
import com.platillogodin.dashboard.repositories.MenuCategoryRepository;
import com.platillogodin.dashboard.repositories.RecipeCategoryRepository;
import com.platillogodin.dashboard.repositories.UserRepository;
import com.platillogodin.dashboard.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Component
@Profile({"dev", "prod"})
public class DataBootstrap implements CommandLineRunner {
    private final RecipeCategoryRepository recipeCategoryRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    public DataBootstrap(RecipeCategoryRepository recipeCategoryRepository, IngredientCategoryRepository ingredientCategoryRepository, MenuCategoryRepository menuCategoryRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, UserRepository userRepository1, UserService userService) {
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.menuCategoryRepository = menuCategoryRepository;
        this.userRepository = userRepository1;

        this.userService = userService;
    }


    @Transactional
    @Override
    public void run(String... args) {
        log.info("Loading bootstrap data");
        if (userRepository.count() == 0L) {
            loadUsers();
        }
        if (menuCategoryRepository.count() == 0L) {
            loadMenuCategories();
        }
        if (ingredientCategoryRepository.count() == 0L) {
            loadIngredientCategories();
        }
        if (recipeCategoryRepository.count() == 0L) {
            loadRecipeCategories();
        }

    }

    private void loadUsers() {
        User user = new User();
        user.setUsername("hugo");
        user.setRole(UserRole.ROLE_USER);
        userService.saveUser(user);

        User user2 = new User();
        user2.setUsername("cesar");
        user2.setRole(UserRole.ROLE_ADMIN);
        userService.saveUser(user2);

        User user3 = new User();
        user3.setUsername("maru");
        user3.setRole(UserRole.ROLE_ADMIN);
        userService.saveUser(user3);

        User user4 = new User();
        user4.setUsername("Yas");
        user4.setRole(UserRole.ROLE_USER);
        userService.saveUser(user4);
    }

    private void loadRecipeCategories() {
        recipeCategoryRepository.save(
                new RecipeCategory(null, "Sopas", "Sopas, caldos y cremas"));
        recipeCategoryRepository.save(
                new RecipeCategory(null, "Aguas", "Aguas y jugos"));
        recipeCategoryRepository.save(
                new RecipeCategory(null, "Pollo", "Recetas con pollo y/o pavo"));
        recipeCategoryRepository.save(
                new RecipeCategory(null, "Ensaladas", "Ensaladas"));
        recipeCategoryRepository.save(
                new RecipeCategory(null, "Arroz", "Arroz"));
        recipeCategoryRepository.save(
                new RecipeCategory(null, "Pastas", "Pastas"));
        recipeCategoryRepository.save(
                new RecipeCategory(null, "Pescados y mariscos", "Recetas con pescados y/o mariscos"));
        recipeCategoryRepository.save(
                new RecipeCategory(null, "Carnes", "Recetas con carne de res y/o cerdo"));

    }

    private void loadIngredientCategories() {
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Frutas", ""));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Verduras", ""));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Lácteos", ""));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Res", "Carne de res y derivados"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Pollo", "Carne de pollo y derivados"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Cerdo", "Carne de cerdo y derivados"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Cereales", "Cereales"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Pastas", "Pastas"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Líquidos", "Líquidos"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Pescados", "Pescados"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Mariscos", "Mariscos"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Desechables", "Productos desechables"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Especias", "Especias"));
        ingredientCategoryRepository.save(
                new IngredientCategory(null, "Legumbres", "Legumbres"));
    }

    private void loadMenuCategories() {
        menuCategoryRepository.save(
                new MenuCategory(null, "Gordin", "Menú Gordin"));
        menuCategoryRepository.save(
                new MenuCategory(null, "Caserón", "Menú Caserón"));
        menuCategoryRepository.save(
                new MenuCategory(null, "Especial", "Menús especiales"));
    }
}
