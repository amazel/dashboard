package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.commands.CategoryTotal;
import com.platillogodin.dashboard.commands.DailySaleCommand;
import com.platillogodin.dashboard.services.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class DashboardRestController {

    private final DashboardService dashboardService;

    public DashboardRestController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/daily-sales")
    public ResponseEntity<?> getDailySales() {
        log.info("REST //api/daily-sales");

        List<CategoryTotal> lista = dashboardService.getTotalsByCategory();

        for (CategoryTotal o : lista) {
            log.info("O {}", o);
        }

        DailySaleCommand dailySales = new DailySaleCommand();

        LinkedHashSet<String> days =
                lista.stream().map(categoryTotal -> {
                    String mes = categoryTotal.getMenuId().substring(4, 6);
                    String dia = categoryTotal.getMenuId().substring(6);
                    return dia.concat("/").concat(mes);
                }).collect(Collectors.toCollection(LinkedHashSet::new));
        dailySales.getLabels().addAll(days);


        for (CategoryTotal o : lista) {
            List<Integer> countValues = dailySales.getCountData().getOrDefault(o.getCategory(), new ArrayList<>());
            countValues.add(o.getQuantity().intValue());
            dailySales.getCountData().put(o.getCategory(), countValues);

            List<BigDecimal> costValues = dailySales.getCostData().getOrDefault(o.getCategory(), new ArrayList<>());
            costValues.add(o.getPrice());
            dailySales.getCostData().put(o.getCategory(), costValues);
        }


        return ResponseEntity.ok(dailySales);
    }

}
