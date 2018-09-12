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
public class WeeklyCostItem {
    private String menuCategory;
    private BigDecimal mondayTotal = new BigDecimal("0");
    private BigDecimal tuesdayTotal = new BigDecimal("0");
    private BigDecimal wednesdayTotal = new BigDecimal("0");
    private BigDecimal thursdayTotal = new BigDecimal("0");
    private BigDecimal fridayTotal = new BigDecimal("0");
}
