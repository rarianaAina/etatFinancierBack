package com.finance.finance.controller;

import com.finance.finance.dto.DateRangeDTO;
import com.finance.finance.dto.RatioDTO;
import com.finance.finance.service.FinancialRatioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/ratios")
public class FinancialRatioController {

    @Autowired
    private FinancialRatioService ratioService;

    @PostMapping
    public ResponseEntity<RatioDTO> getRatios(@RequestBody DateRangeDTO dateRangeDTO) {
        try {
            RatioDTO ratios = ratioService.calculateRatios(
                    dateRangeDTO.getStartDate(),
                    dateRangeDTO.getEndDate()
            );
            return ResponseEntity.ok(ratios);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}