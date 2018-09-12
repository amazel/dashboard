package com.platillogodin.dashboard.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hugo Lezama on September - 2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyCosts {
    private BigDecimal weeklyTotal;
    private List<WeeklyCostItem> weeklyCostItems = new ArrayList<>();
    private Map<String, DailyCostItem> dailyCostItems = new HashMap<>();
    private List<WeeklyRecipeCosts> weeklyRecipeCosts = new ArrayList<>();

}
