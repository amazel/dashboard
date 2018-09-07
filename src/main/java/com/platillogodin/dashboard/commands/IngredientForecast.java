package com.platillogodin.dashboard.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Hugo Lezama on September - 2018
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IngredientForecast {
    private String ingredient;
    private String actual;
    private String forecast;
    private String difference;
    private String className;
}
