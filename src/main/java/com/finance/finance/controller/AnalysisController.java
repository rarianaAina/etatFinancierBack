package com.finance.finance.controller;

import com.finance.finance.service.FinancialAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private FinancialAnalysisService analysisService;

    @GetMapping("/ratios")
    public ResponseEntity<Map<String, Double>> getRatios() {
        Map<String, Double> ratios = new HashMap<>();
        ratios.put("Liquidity Ratio", analysisService.calculateLiquidityRatio());
        ratios.put("Net Profit Margin", analysisService.calculateNetProfitMargin());
        return ResponseEntity.ok(ratios);
    }
}
