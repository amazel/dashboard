package com.platillogodin.dashboard.commands;

import com.platillogodin.dashboard.domain.IngredientCategory;
import com.platillogodin.dashboard.domain.UnitOfMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Hugo Lezama on August - 2018
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class IngredientCommand {
    private Long id;
    private String name;
    private IngredientCategoryCommand category;
    private UnitOfMeasure uom;

}
