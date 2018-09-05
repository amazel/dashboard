package com.platillogodin.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@Data
@Entity
public class StockEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Stock stock;

    private Integer currentQty;

    private Integer originalQty;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate supplyDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate expirationDate;

    private BigDecimal price;

    private String note;
}
