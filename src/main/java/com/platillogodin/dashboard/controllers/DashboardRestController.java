package com.platillogodin.dashboard.controllers;

import com.platillogodin.dashboard.commands.DailySaleCommand;
import com.platillogodin.dashboard.services.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class DashboardRestController {



    @GetMapping("/api/daily-sales")
    public ResponseEntity<?> getDailySales() {
        log.info("REST //api/daily-sales");

        DailySaleCommand dailySales = new DailySaleCommand();

        dailySales.setLabels(Arrays.asList("13/04", "14/04", "15/04", "16/04", "17/04"));

        dailySales.getData().put("Gordin", Arrays.asList(20, 25, 30, 19, 22));
        dailySales.getData().put("Caseron", Arrays.asList(20, 15, 12, 29, 20));
        dailySales.getData().put("Especiales", Arrays.asList(5, 4, 8, 9, 2));
        dailySales.getData().put("Total", Arrays.asList(45, 44, 50, 57, 44));

        return ResponseEntity.ok(dailySales);
    }

}
