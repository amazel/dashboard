package com.platillogodin.dashboard.bootstrap;

import com.platillogodin.dashboard.domain.*;
import com.platillogodin.dashboard.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Component
public class DataBootstrap implements CommandLineRunner {

    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;
    private RecipeCategoryRepository recipeCategoryRepository;
    private IngredientCategoryRepository ingredientCategoryRepository;
    private StockRepository stockRepository;

    public DataBootstrap(IngredientRepository ingredientRepository,
                         RecipeRepository recipeRepository,
                         RecipeCategoryRepository recipeCategoryRepository,
                         IngredientCategoryRepository ingredientCategoryRepository,
                         StockRepository stockRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.stockRepository = stockRepository;
    }

    private List<RecipeCategory> recipeCategories = new ArrayList<>();
    private List<IngredientCategory> ingredientCategories = new ArrayList<>();

    @Transactional
    @Override
    public void run(String... args) {
        log.info("Loading bootstrap data");
        loadCategories();
        loadRecipe();

    }

    private void loadCategories() {
        recipeCategories.add(recipeCategoryRepository.save(new RecipeCategory(null, "Gordin", "Menu Gordin")));
        recipeCategories.add(recipeCategoryRepository.save(new RecipeCategory(null, "Caseron", "Menu Caseron")));

        ingredientCategories.add(ingredientCategoryRepository.save(new IngredientCategory(null, "Frutas", "")));
        ingredientCategories.add(ingredientCategoryRepository.save(new IngredientCategory(null, "Verduras", "")));
        ingredientCategories.add(ingredientCategoryRepository.save(new IngredientCategory(null, "Lacteos", "")));
        ingredientCategories.add(ingredientCategoryRepository.save(new IngredientCategory(null, "Res", "Carne de res y derivados")));
        ingredientCategories.add(ingredientCategoryRepository.save(new IngredientCategory(null, "Pollo", "Carne de pollo y derivados")));
        ingredientCategories.add(ingredientCategoryRepository.save(new IngredientCategory(null, "Cerdo", "Carne de cerdo y derivados")));
        ingredientCategories.add(ingredientCategoryRepository.save(
                new IngredientCategory(null, "Cereales y Pasta", "Cereales y pasta")));

    }

    private void loadRecipe() {
        Ingredient pasta = ingredientRepository.save(
                new Ingredient(null, "Pasta fideo", ingredientCategories.get(6), UnitOfMeasure.GR, 365));
        Ingredient jitomate = ingredientRepository.save(
                new Ingredient(null, "Jitomate", ingredientCategories.get(1), UnitOfMeasure.GR, 5));
        Ingredient caldo_de_pollo = ingredientRepository.save(
                new Ingredient(null, "Caldo de Pollo", ingredientCategories.get(4), UnitOfMeasure.ML, 3));

        stockRepository.save(new Stock(null, pasta, 5000, LocalDate.now()));
        stockRepository.save(new Stock(null, jitomate, 10000, LocalDate.now()));
        stockRepository.save(new Stock(null, caldo_de_pollo, 500, LocalDate.now()));


        Recipe sopaFideo = new Recipe();
        sopaFideo.setCookTime(15);
        sopaFideo.setName("Sopa de fideo");
        sopaFideo.setPrepTime(5);
        sopaFideo.setServings(5);


        sopaFideo.getIngredientList().add(new RecipeIngredient(null,sopaFideo, pasta, 150));
        sopaFideo.getIngredientList().add(new RecipeIngredient(null,sopaFideo, jitomate, 200));
        sopaFideo.getIngredientList().add(new RecipeIngredient(null,sopaFideo, caldo_de_pollo, 100));
        sopaFideo.setRecipeCategory(recipeCategories.get(0));
        recipeRepository.save(sopaFideo);


        Recipe sopaCaracol = new Recipe();
        sopaCaracol.setCookTime(10);
        sopaCaracol.setName("Sopa de caracol");
        sopaCaracol.setPrepTime(15);
        sopaCaracol.setServings(6);
        sopaCaracol.getIngredientList().add(new RecipeIngredient(null,sopaCaracol, pasta, 145));
        sopaCaracol.getIngredientList().add(new RecipeIngredient(null,sopaCaracol, jitomate, 345));
        sopaCaracol.getIngredientList().add(new RecipeIngredient(null,sopaCaracol, caldo_de_pollo, 340));
        sopaCaracol.setRecipeCategory(recipeCategories.get(1));
        recipeRepository.save(sopaCaracol);

        Recipe sopaLetras = new Recipe();
        sopaLetras.setCookTime(5);
        sopaLetras.setName("Sopa de letras");
        sopaLetras.setPrepTime(25);
        sopaLetras.setServings(50);
        sopaLetras.getIngredientList().add(new RecipeIngredient(null,sopaLetras, pasta, 1500));
        sopaLetras.getIngredientList().add(new RecipeIngredient(null,sopaLetras, jitomate, 20));
        sopaLetras.getIngredientList().add(new RecipeIngredient(null,sopaLetras, caldo_de_pollo, 50));
        sopaLetras.setRecipeCategory(recipeCategories.get(0));
        recipeRepository.save(sopaLetras);
    }


}
