package com.platillogodin.dashboard.services;

import com.platillogodin.dashboard.commands.IngredientForecast;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hugo Lezama on September - 2018
 */
@Service
public class DashboardServiceImpl implements DashboardService {
    @Override
    public List<IngredientForecast> getIngredientForecast() {
        return null;
    }
}
