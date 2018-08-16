package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.domain.MenuCategory;
import com.platillogodin.dashboard.exceptions.ExistingReferencesException;
import com.platillogodin.dashboard.services.MenuCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class MenuCategoryController {

    public static final String FORM_URL = "categories/menus/menu_category_form";
    public static final String LIST_URL = "categories/menus/list";

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
    public ModelAndView deleteMenuCategory(@PathVariable Long id, Model model) {
        MenuCategory rc = menuCategoryService.findById(id);
        try {
            menuCategoryService.delete(rc);
        } catch (ExistingReferencesException ere) {

            model.addAttribute("deleteError",
                    "Error al eliminar " + rc.getName() + ", existen menus asociados a esta categoría.");
            return new ModelAndView("forward:/categories/menus", model.asMap());
        }
        model.addAttribute("deleteMessage", "La categoria " + rc.getName() + " fue eliminada correctamente");
        return new ModelAndView("forward:/categories/menus", model.asMap());
    }

    @PostMapping("/categories/menu")
    public String saveOrUpdateMenuCategory(@ModelAttribute("menuCategory") MenuCategory menuCategory, BindingResult bindingResult) {
        log.info("Saving menuCategory");
        MenuCategory saved = menuCategoryService.saveMenuCategory(menuCategory);
        return "redirect:/categories/menus/";
    }
}
