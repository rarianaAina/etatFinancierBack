package com.finance.finance.controller;

import com.finance.finance.dto.BalanceSheetDTO;
import com.finance.finance.dto.DateRangeDTO;
import com.finance.finance.dto.IncomeStatementDTO;
import com.finance.finance.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin
@RequestMapping("/api/reports")
public class FinancialReportController {

    @Autowired
    private FinancialReportService reportService;

    @PostMapping("/balance-sheet")
    public ResponseEntity<BalanceSheetDTO> getBalanceSheet(@RequestBody DateRangeDTO dateRangeDTO) {
        try {
            LocalDate startDate = dateRangeDTO.getStartDate();
            LocalDate endDate = dateRangeDTO.getEndDate();

            BalanceSheetDTO balanceSheet = reportService.generateBalanceSheet(startDate, endDate);
            return ResponseEntity.ok(balanceSheet);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/income-statement")
    public ResponseEntity<IncomeStatementDTO> getIncomeStatement(@RequestBody DateRangeDTO dateRangeDTO) {
        try {
            LocalDate startDate = dateRangeDTO.getStartDate();
            LocalDate endDate = dateRangeDTO.getEndDate();
            IncomeStatementDTO incomeStatement = reportService.generateIncomeStatement(startDate, endDate);
            return ResponseEntity.ok(incomeStatement);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}