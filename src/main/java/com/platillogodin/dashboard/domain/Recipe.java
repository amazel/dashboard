package com.platillogodin.dashboard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Hugo Lezama on August - 2018
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<RecipeIngredient> ingredientList = new ArrayList<>();

    @OneToOne
    private RecipeCategory recipeCategory;

    @Lob
    private Byte[] image;

}
