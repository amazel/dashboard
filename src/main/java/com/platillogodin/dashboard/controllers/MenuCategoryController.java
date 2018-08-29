package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.MenuCategory;
import com.platillogodin.dashboard.services.MenuCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class MenuCategoryController {

    private static final String FORM_URL = "categories/menus/menu_category_form";
    private static final String LIST_URL = "categories/menus/list";

    private final MenuCategoryService menuCategoryService;

    public MenuCategoryController(MenuCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }

    @GetMapping("/categories/menus")
    public String listMenuCategories(Model model) {
        List<MenuCategory> menuCategoryList = menuCategoryService.findAll();
        model.addAttribute("menuCategoryList", menuCategoryList);
        return LIST_URL;
    }

    @GetMapping("/categories/menus/new")
    public String newMenuCategory(Model model) {
        model.addAttribute("menuCategory", new MenuCategory());
        return FORM_URL;
    }

    @GetMapping("/categories/menus/{id}/edit")
    public String updateMenuCategory(@PathVariable Long id, Model model) {
        MenuCategory menuCategory = menuCategoryService.findById(id);
        model.addAttribute("menuCategory", menuCategory);
        return FORM_URL;
    }

    @GetMapping("/categories/menus/{id}/delete")
    public String deleteMenuCategory(@PathVariable Long id, RedirectAttributes ra) {
        MenuCategory cat = menuCategoryService.findById(id);
        try {
            menuCategoryService.delete(cat);
            ra.addFlashAttribute("deleteMessage", "La categoría " + cat.getName() + " fue eliminada correctamente");
        } catch (Exception ere) {
            ra.addFlashAttribute("deleteError",
                    "Error al eliminar " + cat.getName() + ", existen menus asociados a esta categoría.");
        }

        return "redirect:/categories/menus";
    }

    @PostMapping("/categories/menu")
    public String saveOrUpdateMenuCategory(@ModelAttribute("menuCategory") MenuCategory menuCategory) {
        log.info("Saving menuCategory");
        menuCategoryService.saveMenuCategory(menuCategory);
        return "redirect:/categories/menus/";
    }
}
