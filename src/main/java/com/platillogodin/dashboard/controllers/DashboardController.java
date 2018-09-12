package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.commands.IngredientForecast;
import com.platillogodin.dashboard.commands.WeeklyCosts;
import com.platillogodin.dashboard.services.DashboardService;
import com.platillogodin.dashboard.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;

/**
 * Created by Hugo Lezama on September - 2018
 */
@Slf4j
@CrossOrigin(origins = "*")
@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
        model.addAttribute("ingredientForecastList", new ArrayList<IngredientForecast>());
        model.addAttribute("weekNumber", "");
        model.addAttribute("mondayDate", "");
        model.addAttribute("fridayDate", "");
        model.addAttribute("weeklyCosts", new WeeklyCosts());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "403";
    }

    @GetMapping("/api/ingredient-forecast")
    public String getIngredientForecast(Model model) {
        model.addAttribute("ingredientForecastList", dashboardService.getIngredientForecast());
        LocalDate nextMonday = Utils.getNextMonday(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        model.addAttribute("weekNumber", nextMonday.get(WeekFields.ISO.weekOfWeekBasedYear()));
        model.addAttribute("mondayDate", formatter.format(nextMonday));
        model.addAttribute("fridayDate", formatter.format(nextMonday.plusDays(4)));

        return "index :: ingredientForecastFragment";
    }


    @GetMapping("/api/weekly-costs-forecast")
    public String weeklyCostsForecast(Model model) {
        model.addAttribute("weeklyCosts", dashboardService.getWeeklyCostsForecast());
        LocalDate nextMonday = Utils.getNextMonday(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy");
        model.addAttribute("weekNumber", nextMonday.get(WeekFields.ISO.weekOfWeekBasedYear()));
        model.addAttribute("mondayDate", formatter.format(nextMonday));
        model.addAttribute("fridayDate", formatter.format(nextMonday.plusDays(4)));
        return "index :: weeklyCostsFragment";
    }
}
