package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.commands.CategoryTotal;
import com.platillogodin.dashboard.commands.ChartsCommand;
import com.platillogodin.dashboard.services.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
        log.debug("REST /api/daily-sales");

        List<CategoryTotal> lista = dashboardService.getTotalsByCategory(15);

        ChartsCommand chartsCommand = new ChartsCommand();

        LinkedHashSet<String> days =
                lista.stream().map(categoryTotal -> {
                    String mes = categoryTotal.getMenuId().substring(4, 6);
                    String dia = categoryTotal.getMenuId().substring(6);
                    return dia.concat("/").concat(mes);
                }).collect(Collectors.toCollection(LinkedHashSet::new));
        chartsCommand.getLabels().addAll(days);


        for (CategoryTotal o : lista) {
            log.debug("O {}", o);
            List<Integer> countValues = chartsCommand.getCountData().getOrDefault(o.getCategory(), new ArrayList<>());
            countValues.add(o.getQuantity().intValue());
            chartsCommand.getCountData().put(o.getCategory(), countValues);

            List<BigDecimal> costValues = chartsCommand.getCostData().getOrDefault(o.getCategory(), new ArrayList<>());
            costValues.add(o.getPrice());
            chartsCommand.getCostData().put(o.getCategory(), costValues);

            List<BigDecimal> unitCosts = chartsCommand.getUnitCostData().getOrDefault(o.getCategory(), new ArrayList<>());
            unitCosts.add(o.getPrice().divide(BigDecimal.valueOf(o.getQuantity()), 2, RoundingMode.HALF_UP));
            chartsCommand.getUnitCostData().put(o.getCategory(), unitCosts);
        }


        return ResponseEntity.ok(chartsCommand);
    }

}
