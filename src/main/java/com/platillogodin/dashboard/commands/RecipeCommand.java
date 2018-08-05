package com.platillogodin.dashboard.commands;

import com.platillogodin.dashboard.domain.RecipeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RecipeCommand {

    private Long id;
    private String name;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private Map<IngredientCommand, Integer> ingredients = new HashMap<>();
    private RecipeCategoryCommand recipeCategory;
    private Byte[] image;
}
