package com.platillogodin.dashboard.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hugo Lezama on August - 2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartsCommand {
    List<String> labels = new ArrayList<>();
    Map<String, List<Integer>> countData = new HashMap<>();
    Map<String, List<BigDecimal>> costData = new HashMap<>();
    Map<String, List<BigDecimal>> unitCostData = new HashMap<>();
}