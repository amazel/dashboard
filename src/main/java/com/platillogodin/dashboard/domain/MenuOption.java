package com.platillogodin.dashboard.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@Entity
public class MenuOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Menu menu;

    @OneToOne
    private MenuCategory menuCategory;

    @Enumerated(value = EnumType.STRING)
    private MenuOptionType menuOptionType;

    @OneToOne
    private Recipe recipe;

    private Integer forecastQuantity;
    private Integer actualQuantity;
}
