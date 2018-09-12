package com.platillogodin.dashboard.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Created by Hugo Lezama on September - 2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyRecipeCosts {
    private String day;
    private String menuCategory;
    private String recipeName;
    private Integer estimatedQty;
    private BigDecimal total = BigDecimal.ZERO;
}
