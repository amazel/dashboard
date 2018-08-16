package com.platillogodin.dashboard.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@Entity
public class MenuDish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private MenuOption menuOption;

    @Enumerated(value = EnumType.STRING)
    private MenuDishType menuDishType;

    @OneToOne
    private Recipe recipe;

    private Integer forecastQuantity;
    private Integer actualQuantity;
}
