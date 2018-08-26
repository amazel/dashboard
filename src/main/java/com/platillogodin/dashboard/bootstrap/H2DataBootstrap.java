package com.platillogodin.dashboard.bootstrap;

import com.platillogodin.dashboard.domain.*;
import com.platillogodin.dashboard.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Component
@Profile("default")
public class H2DataBootstrap implements CommandLineRunner {

    private IngredientRepository ingredientRepository;
    private RecipeRepository recipeRepository;
    private RecipeCategoryRepository recipeCategoryRepository;
    private IngredientCategoryRepository ingredientCategoryRepository;
    private StockRepository stockRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;

    public H2DataBootstrap(IngredientRepository ingredientRepository,
                           RecipeRepository recipeRepository,
                           RecipeCategoryRepository recipeCategoryRepository,
                           IngredientCategoryRepository ingredientCategoryRepository,
                           StockRepository stockRepository, MenuCategoryRepository menuCategoryRepository, MenuRepository menuRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.stockRepository = stockRepository;
        this.menuCategoryRepository = menuCategoryRepository;
        this.menuRepository = menuRepository;
    }

    private List<RecipeCategory> recipeCategories = new ArrayList<>();
    private List<IngredientCategory> ingredientCategories = new ArrayList<>();
    private List<MenuCategory> menuCategories = new ArrayList<>();


    @Transactional
    @Override
    public void run(String... args) {
        log.info("Loading bootstrap data");
        loadCategories();
        loadData();

    }

    private void loadCategories() {
        recipeCategories.add(recipeCategoryRepository.save(
                new RecipeCategory(null, "Sopas", "Sopas y caldos")));
        recipeCategories.add(recipeCategoryRepository.save(
                new RecipeCategory(null, "Aguas", "Aguas y jugos")));
        recipeCategories.add(recipeCategoryRepository.save(
                new RecipeCategory(null, "Pollo", "Recetas con pollo")));
        recipeCategories.add(recipeCategoryRepository.save(
                new RecipeCategory(null, "Ensaladas", "Ensaladas")));
        recipeCategories.add(recipeCategoryRepository.save(
                new RecipeCategory(null, "Guarniciones", "Guarniciones")));

        ingredientCategories.add(ingredientCategoryRepository.save(
                new IngredientCategory(null, "Frutas", "")));
        ingredientCategories.add(ingredientCategoryRepository.save(
                new IngredientCategory(null, "Verduras", "")));
        ingredientCategories.add(ingredientCategoryRepository.save(
                new IngredientCategory(null, "Lacteos", "")));
        ingredientCategories.add(ingredientCategoryRepository.save(
                new IngredientCategory(null, "Res", "Carne de res y derivados")));
        ingredientCategories.add(ingredientCategoryRepository.save(
                new IngredientCategory(null, "Pollo", "Carne de pollo y derivados")));
        ingredientCategories.add(ingredientCategoryRepository.save(
                new IngredientCategory(null, "Cerdo", "Carne de cerdo y derivados")));
        ingredientCategories.add(ingredientCategoryRepository.save(
                new IngredientCategory(null, "Cereales y Pasta", "Cereales y pasta")));

        menuCategories.add(menuCategoryRepository.save(
                new MenuCategory(null, "Gordin", "Menu Gordin")
        ));
        menuCategories.add(menuCategoryRepository.save(
                new MenuCategory(null, "Caseron", "Menu Caseron")
        ));

        menuCategories.add(menuCategoryRepository.save(
                new MenuCategory(null, "Comun", "Menus comunes")
        ));
    }

    void loadData() {
        Ingredient pasta = ingredientRepository.save(
                new Ingredient(null, "Pasta fideo", ingredientCategories.get(6), UnitOfMeasure.GR, 365));
        Ingredient jitomate = ingredientRepository.save(
                new Ingredient(null, "Jitomate", ingredientCategories.get(1), UnitOfMeasure.GR, 5));
        Ingredient cebolla = ingredientRepository.save(
                new Ingredient(null, "Cebolla", ingredientCategories.get(1), UnitOfMeasure.GR, 5));
        Ingredient agua = ingredientRepository.save(
                new Ingredient(null, "Agua", ingredientCategories.get(1), UnitOfMeasure.ML, 365));
        Ingredient mango = ingredientRepository.save(
                new Ingredient(null, "Mango", ingredientCategories.get(0), UnitOfMeasure.GR, 5));
        Ingredient caldo_de_pollo = ingredientRepository.save(
                new Ingredient(null, "Caldo de pollo", ingredientCategories.get(4), UnitOfMeasure.ML, 3));
        Ingredient pechugaPollo = ingredientRepository.save(
                new Ingredient(null, "Pechuga de pollo", ingredientCategories.get(4), UnitOfMeasure.GR, 5));
        Ingredient arroz = ingredientRepository.save(
                new Ingredient(null, "Arroz", ingredientCategories.get(6), UnitOfMeasure.GR, 365));

        stockRepository.save(
                new Stock(null, pasta, 5000, LocalDate.now(), LocalDate.now().plusDays(pasta.getExpirationTime())));
        stockRepository.save(
                new Stock(null, jitomate, 10000, LocalDate.now(), LocalDate.now().plusDays(jitomate.getExpirationTime())));
        stockRepository.save(
                new Stock(null, caldo_de_pollo, 500, LocalDate.now(),
                        LocalDate.now().plusDays(caldo_de_pollo.getExpirationTime())));


        Recipe polloCacahuate = new Recipe();
        polloCacahuate.setCookTime(15);
        polloCacahuate.setName("Pollo encacahuatado");
        polloCacahuate.setPrepTime(20);
        polloCacahuate.setServings(10);

        polloCacahuate.getIngredientList().add(new RecipeIngredient(null, polloCacahuate, cebolla, 150));
        polloCacahuate.getIngredientList().add(new RecipeIngredient(null, polloCacahuate, jitomate, 200));
        polloCacahuate.getIngredientList().add(new RecipeIngredient(null, polloCacahuate, pechugaPollo, 500));
        polloCacahuate.getIngredientList().add(new RecipeIngredient(null, polloCacahuate, caldo_de_pollo, 100));
        polloCacahuate.setRecipeCategory(recipeCategories.get(2));
        recipeRepository.save(polloCacahuate);

        Recipe arrozBlanco = new Recipe();
        arrozBlanco.setCookTime(15);
        arrozBlanco.setName("Arroz blanco");
        arrozBlanco.setPrepTime(20);
        arrozBlanco.setServings(10);

        arrozBlanco.getIngredientList().add(new RecipeIngredient(null, arrozBlanco, cebolla, 150));
        arrozBlanco.getIngredientList().add(new RecipeIngredient(null, arrozBlanco, arroz, 200));
        arrozBlanco.setRecipeCategory(recipeCategories.get(4));
        recipeRepository.save(arrozBlanco);

        Recipe sopaCaracol = new Recipe();
        sopaCaracol.setCookTime(10);
        sopaCaracol.setName("Sopa de caracol");
        sopaCaracol.setPrepTime(15);
        sopaCaracol.setServings(6);
        sopaCaracol.getIngredientList().add(new RecipeIngredient(null, sopaCaracol, pasta, 145));
        sopaCaracol.getIngredientList().add(new RecipeIngredient(null, sopaCaracol, jitomate, 345));
        sopaCaracol.getIngredientList().add(new RecipeIngredient(null, sopaCaracol, caldo_de_pollo, 340));
        sopaCaracol.setRecipeCategory(recipeCategories.get(0));
        recipeRepository.save(sopaCaracol);

        Recipe sopaLetras = new Recipe();
        sopaLetras.setCookTime(5);
        sopaLetras.setName("Sopa de letras");
        sopaLetras.setPrepTime(25);
        sopaLetras.setServings(50);
        sopaLetras.getIngredientList().add(new RecipeIngredient(null, sopaLetras, pasta, 1500));
        sopaLetras.getIngredientList().add(new RecipeIngredient(null, sopaLetras, jitomate, 20));
        sopaLetras.getIngredientList().add(new RecipeIngredient(null, sopaLetras, caldo_de_pollo, 50));
        sopaLetras.setRecipeCategory(recipeCategories.get(0));
        recipeRepository.save(sopaLetras);

        Recipe aguaMango = new Recipe();
        aguaMango.setCookTime(0);
        aguaMango.setName("Agua de mango");
        aguaMango.setPrepTime(15);
        aguaMango.setServings(10);


        aguaMango.getIngredientList().add(new RecipeIngredient(null, aguaMango, agua, 1000));
        aguaMango.getIngredientList().add(new RecipeIngredient(null, aguaMango, mango, 200));
        aguaMango.setRecipeCategory(recipeCategories.get(1));
        recipeRepository.save(aguaMango);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String todayString = LocalDate.now().format(formatter);
        Menu today = new Menu(todayString);
        today.setDate(LocalDate.now());
        MenuOption op1 = new MenuOption();
        op1.setMenu(today);
        op1.setMenuCategory(menuCategories.get(0));
        op1.setMenuOptionType(MenuOptionType.SIDE);
        op1.setForecastQuantity(40);
        op1.setRecipe(arrozBlanco);

        MenuOption op2 = new MenuOption();
        op2.setMenu(today);
        op2.setMenuCategory(menuCategories.get(0));

        op2.setMenuOptionType(MenuOptionType.MAIN);
        op2.setForecastQuantity(50);
        op2.setRecipe(polloCacahuate);

        today.addMenuOption(op1);
        today.addMenuOption(op2);

        MenuOption op3 = new MenuOption();
        op3.setMenu(today);
        op3.setMenuCategory(menuCategories.get(1));
        op3.setMenuOptionType(MenuOptionType.STARTER);
        op3.setForecastQuantity(40);
        op3.setActualQuantity(45);
        op3.setRecipe(sopaCaracol);

        today.addMenuOption(op3);


        MenuOption op4 = new MenuOption();
        op4.setMenu(today);
        op4.setMenuCategory(menuCategories.get(2));
        op4.setMenuOptionType(MenuOptionType.BEVERAGE);
        op4.setForecastQuantity(80);
        op4.setRecipe(aguaMango);

        today.addMenuOption(op4);

        menuRepository.save(today);

    }


}
