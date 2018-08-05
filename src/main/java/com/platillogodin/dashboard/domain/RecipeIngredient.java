package com.platillogodin.dashboard.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Hugo Lezama on August - 2018
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"recipe"})
@ToString(exclude = {"recipe"})
@Entity
public class RecipeIngredient {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Recipe recipe;

    @OneToOne
    private Ingredient ingredient;

    private Integer quantity;

}