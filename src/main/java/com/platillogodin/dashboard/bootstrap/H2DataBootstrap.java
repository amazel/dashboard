package com.platillogodin.dashboard.bootstrap;

import com.platillogodin.dashboard.domain.*;
import com.platillogodin.dashboard.repositories.*;
import com.platillogodin.dashboard.services.StockService;
import com.platillogodin.dashboard.services.UserService;
import com.platillogodin.dashboard.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Component
@Profile("default")
public class H2DataBootstrap implements CommandLineRunner {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeCategoryRepository recipeCategoryRepository;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuRepository menuRepository;
    private final UserService userService;
    private final StockService stockService;


    public H2DataBootstrap(IngredientRepository ingredientRepository,
                           RecipeRepository recipeRepository,
                           RecipeCategoryRepository recipeCategoryRepository,
                           IngredientCategoryRepository ingredientCategoryRepository,
                           MenuCategoryRepository menuCategoryRepository,
                           MenuRepository menuRepository,
                           UserService userService,
                           StockService stockService) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.menuCategoryRepository = menuCategoryRepository;
        this.menuRepository = menuRepository;
        this.userService = userService;
        this.stockService = stockService;
    }

    private final List<RecipeCategory> recipeCategories = new ArrayList<>();
    private final List<IngredientCategory> ingredientCategories = new ArrayList<>();
    private final List<MenuCategory> menuCategories = new ArrayList<>();
    private final List<Recipe> recipes = new ArrayList<>();


    @Transactional
    @Override
    public void run(String... args) {
        log.info("Loading bootstrap data");
        loadDefaultUsers();
        loadCategories();
        loadData();

    }

    private void loadDefaultUsers() {
        User user1 = new User();
        user1.setUsername("Hugo");
        user1.setRole(UserRole.ROLE_USER);

        User user2 = new User();
        user2.setUsername("Cesar");
        user2.setRole(UserRole.ROLE_ADMIN);

        userService.saveUser(user1);
        userService.saveUser(user2);
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
                new IngredientCategory(null, "Lácteos", "")));
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
                new MenuCategory(null, "Especial", "Menu Especial")
        ));
    }

    private void loadData() {
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

        createStock(stockService.saveStock(new Stock(cebolla, new BigDecimal("0.010"))));
        createStock(stockService.saveStock(new Stock(agua, new BigDecimal("0.002"))));
        createStock(stockService.saveStock(new Stock(mango, new BigDecimal("0.01357"))));
        stockService.saveStock(new Stock(caldo_de_pollo, new BigDecimal("0.022")));
        createStock(stockService.saveStock(new Stock(pechugaPollo, new BigDecimal("0.08"))));
        createStock(stockService.saveStock(new Stock(arroz, new BigDecimal("0.0172"))));

        Stock stockJitomate = stockService.saveStock(new Stock(jitomate, new BigDecimal("0.0181")));
        Stock stockPasta = stockService.saveStock(new Stock(pasta, new BigDecimal("0.0029")));
        createStock(stockJitomate);
        createStock(stockJitomate);
        createStock(stockJitomate);
        createStock(stockPasta);
        createStock(stockPasta);
        createStock(stockPasta);

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
        recipes.add(polloCacahuate);
        recipeRepository.save(polloCacahuate);

        Recipe arrozBlanco = new Recipe();
        arrozBlanco.setCookTime(15);
        arrozBlanco.setName("Arroz blanco");
        arrozBlanco.setPrepTime(20);
        arrozBlanco.setServings(10);

        arrozBlanco.getIngredientList().add(new RecipeIngredient(null, arrozBlanco, cebolla, 150));
        arrozBlanco.getIngredientList().add(new RecipeIngredient(null, arrozBlanco, arroz, 200));
        arrozBlanco.setRecipeCategory(recipeCategories.get(4));
        recipes.add(arrozBlanco);
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
        recipes.add(sopaCaracol);
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
        recipes.add(sopaLetras);
        recipeRepository.save(sopaLetras);

        Recipe aguaMango = new Recipe();
        aguaMango.setCookTime(0);
        aguaMango.setName("Agua de mango");
        aguaMango.setPrepTime(15);
        aguaMango.setServings(10);


        aguaMango.getIngredientList().add(new RecipeIngredient(null, aguaMango, agua, 1000));
        aguaMango.getIngredientList().add(new RecipeIngredient(null, aguaMango, mango, 200));
        aguaMango.setRecipeCategory(recipeCategories.get(1));
        recipes.add(aguaMango);
        recipeRepository.save(aguaMango);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String nextMonday = Utils.getNextMonday(LocalDate.now()).format(formatter);
        createMenu(nextMonday, LocalDate.now());

        LocalDate lastMonday = Utils.getNextMonday(LocalDate.now()).minusDays(14);
        createMenu(lastMonday.format(formatter), lastMonday);

        lastMonday = lastMonday.plusDays(1);
        createMenu(lastMonday.format(formatter), lastMonday);
        lastMonday = lastMonday.plusDays(1);
        createMenu(lastMonday.format(formatter), lastMonday);
        lastMonday = lastMonday.plusDays(1);
        createMenu(lastMonday.format(formatter), lastMonday);
        lastMonday = lastMonday.plusDays(1);
        createMenu(lastMonday.format(formatter), lastMonday);

    }

    private void createStock(Stock stock) {
        Random r = new Random();
        StockEntry entry = new StockEntry();
        entry.setCurrentQty((r.nextInt(100) * 10) + 1);
        entry.setOriginalQty(entry.getCurrentQty() + 100);
        entry.setPrice(BigDecimal.valueOf(r.nextInt(1000) + 250));
        entry.setSupplyDate(LocalDate.now().minusDays(r.nextInt(8)));
        stockService.saveStockEntry(stock, entry);
    }

    public void createMenu(String menuId, LocalDate date) {
        Random r = new Random();
        Menu menu = new Menu(menuId);
        menu.setDate(date);
        menu.setWeekNumber(Utils.findWeekNumber(menu.getDate()));

        menu = createMenuCategory(menu, menuCategories.get(0), MenuOptionType.MAIN);
        menu = createMenuCategory(menu, menuCategories.get(0), MenuOptionType.SIDE);
        menu = createMenuCategory(menu, menuCategories.get(0), MenuOptionType.SIDE);
        menu = createMenuCategory(menu, menuCategories.get(1), MenuOptionType.MAIN);
        menu = createMenuCategory(menu, menuCategories.get(1), MenuOptionType.SIDE);
        menu = createMenuCategory(menu, menuCategories.get(1), MenuOptionType.SIDE);
        menu = createMenuCategory(menu, menuCategories.get(2), MenuOptionType.MAIN);
        menu = createMenuCategory(menu, menuCategories.get(2), MenuOptionType.SIDE);
        menu = createMenuCategory(menu, menuCategories.get(2), MenuOptionType.SIDE);
        menu = createMenuCategory(menu, menuCategories.get(r.nextInt(menuCategories.size())), MenuOptionType.values()[r.nextInt(MenuOptionType.values().length - 1) + 1]);
        menu = createMenuCategory(menu, menuCategories.get(r.nextInt(menuCategories.size())), MenuOptionType.values()[r.nextInt(MenuOptionType.values().length - 1) + 1]);
        menu = createMenuCategory(menu, menuCategories.get(r.nextInt(menuCategories.size())), MenuOptionType.values()[r.nextInt(MenuOptionType.values().length - 1) + 1]);
        menu = createMenuCategory(menu, menuCategories.get(r.nextInt(menuCategories.size())), MenuOptionType.values()[r.nextInt(MenuOptionType.values().length - 1) + 1]);

        menuRepository.save(menu);
    }

    public Menu createMenuCategory(Menu menu, MenuCategory menuCategory, MenuOptionType menuOptionType) {
        Random r = new Random();
        MenuOption op1 = new MenuOption();
        op1.setMenu(menu);
        op1.setMenuCategory(menuCategory);
        op1.setMenuOptionType(menuOptionType);
        op1.setForecastQuantity(r.nextInt(100) + 15);
        op1.setRecipe(recipes.get(r.nextInt(recipes.size())));
        if (menu.getDate().isBefore(LocalDate.now())) {
            op1.setActualQuantity(op1.getForecastQuantity() - 3);
            op1.setCost(BigDecimal.valueOf(r.nextInt(1000) + 50));
        }
        menu.addMenuOption(op1);
        return menu;
    }

}
