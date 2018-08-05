package com.platillogodin.dashboard.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Controller
public class IndexController {

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        log.debug("Getting Index page");

        //  model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
    }
}
