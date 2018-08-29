package com.platillogodin.dashboard.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
public class UserProfile {
    private Long id;
    private String username;
    private String password;
}
