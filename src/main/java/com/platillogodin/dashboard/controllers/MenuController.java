package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.Menu;
import com.platillogodin.dashboard.services.MenuCategoryService;
import com.platillogodin.dashboard.services.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class MenuController {

    private static final String MENU_URL = "menus/menu_planner";

    private final MenuService menuService;
    private final MenuCategoryService menuCategoryService;

    public MenuController(MenuService menuService, MenuCategoryService menuCategoryService) {
        this.menuService = menuService;
        this.menuCategoryService = menuCategoryService;
    }

    @GetMapping("/menus")
    public String showMenuPlanner(Model model) {
        log.info("Show menu planner");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        String todayString = LocalDate.now().format(formatter);
//        log.info(todayString);
//        model.addAttribute("menu", menuService.findById(todayString));
        model.addAttribute("menu", new Menu());
        model.addAttribute("categories", menuCategoryService.findAll());
        return MENU_URL;
    }

    @GetMapping("/api/menus")
    public String getMenu(@RequestParam("menuId") String menuId, Model model) {
        log.info(menuId);
        model.addAttribute("menu", menuService.findById(menuId));
        model.addAttribute("categories", menuCategoryService.findAll());
        return "menus/menu_planner :: content";
    }

}
