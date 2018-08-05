package com.platillogodin.dashboard.commands;

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
public class RecipeCategoryCommand {
    private Long id;
    private String name;
    private String description;
}
