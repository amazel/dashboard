package com.platillogodin.dashboard.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"ingredient_id"})})
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @OneToOne
    private Ingredient ingredient;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stock", fetch = FetchType.LAZY)
    private List<StockEntry> stockEntries = new ArrayList<>();

    private Integer total = 0;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastSupplyDate;

    public Stock addStockEntry(StockEntry stockEntry) {
        stockEntry.setStock(this);
        this.getStockEntries().add(stockEntry);
        return this;
    }
}
