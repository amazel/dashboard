package com.platillogodin.dashboard.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne
    private IngredientCategory category;

    @Enumerated(EnumType.STRING)
    private UnitOfMeasure uom;

    private Integer expirationTime;

}
