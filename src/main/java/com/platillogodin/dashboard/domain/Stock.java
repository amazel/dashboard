package com.platillogodin.dashboard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ingredient_id"})})
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Ingredient ingredient;
    private Integer quantity;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastSupplyDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expirationDate;
}
