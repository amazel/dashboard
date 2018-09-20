package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.commands.CategoryTotal;
import com.platillogodin.dashboard.commands.IngredientForecast;
import com.platillogodin.dashboard.commands.WeeklyCosts;

import java.util.List;

/**
 * Created by Hugo Lezama on September - 2018
 */
public interface DashboardService {
    List<IngredientForecast> getIngredientForecast();

    WeeklyCosts getWeeklyCostsForecast();

    List<CategoryTotal> getTotalsByCategory();
}
