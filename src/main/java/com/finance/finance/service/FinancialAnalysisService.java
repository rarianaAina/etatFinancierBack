package com.finance.finance.service;

import com.finance.finance.repository.FinancialDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialAnalysisService {

    @Autowired
    private FinancialDataRepository repository;

    public double calculateLiquidityRatio() {
        double currentAssets = repository.findSumByAccountType("Current Assets");
        double currentLiabilities = repository.findSumByAccountType("Current Liabilities");
        return currentAssets / currentLiabilities;
    }

    public double calculateNetProfitMargin() {
        double revenue = repository.findSumByAccountType("Revenue");
        double expenses = repository.findSumByAccountType("Expenses");
        return (revenue - expenses) / revenue * 100;
    }
}
