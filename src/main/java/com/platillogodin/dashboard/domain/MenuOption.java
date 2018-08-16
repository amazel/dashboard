package com.platillogodin.dashboard.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menuOption")
    private List<MenuDish> dishes;
}
