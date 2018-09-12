package com.platillogodin.dashboard.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Hugo Lezama on September - 2018
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyCostItem {
    private String menuCategory;
    private String recipeName;
    private String quantity;
    private String price;
}
