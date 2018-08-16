package com.platillogodin.dashboard.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
    private List<MenuOption> options = new ArrayList<>();
}

