package com.platillogodin.dashboard.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Menu {
    @Id
    @NonNull
    private String id;

    private LocalDate date;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
    private List<MenuOption> options = new ArrayList<>();

    public Menu addMenuOption(MenuOption menuOption){
        menuOption.setMenu(this);
        this.options.add(menuOption);
        return this;
    }
}

