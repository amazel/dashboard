package com.platillogodin.dashboard.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created by Hugo Lezama on September - 2018
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryTotal {
    private String menuId;
    private String category;
    private Long quantity;
    private BigDecimal price;
}
