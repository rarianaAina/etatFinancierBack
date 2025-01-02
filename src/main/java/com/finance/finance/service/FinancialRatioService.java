package com.finance.finance.service;

import com.finance.finance.dto.AccountBalanceDTO;
import com.finance.finance.dto.RatioDTO;
import com.finance.finance.repository.FinancialEntryrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class FinancialRatioService {

    @Autowired
    private FinancialEntryrepository entryRepository;

    @Autowired
    private FinancialReportService reportService;

    public RatioDTO calculateRatios(LocalDate startDate, LocalDate endDate) {
        RatioDTO ratios = new RatioDTO();

        // Récupérer les données nécessaires
        var balanceSheet = reportService.generateBalanceSheet(startDate, endDate);
        var incomeStatement = reportService.generateIncomeStatement(startDate, endDate);

        // Calculer les totaux nécessaires
        double currentAssets = getTotalForCategory(balanceSheet.getAssets(), "Stocks")
                + getTotalForCategory(balanceSheet.getAssets(), "Créances")
                + getTotalForCategory(balanceSheet.getAssets(), "Trésorerie");

        double currentLiabilities = getTotalForCategory(balanceSheet.getLiabilities(), "Dettes");
        double totalAssets = balanceSheet.getTotalAssets();
        double totalLiabilities = balanceSheet.getTotalLiabilities();
        double equity = totalAssets - totalLiabilities;
        double netIncome = incomeStatement.getNetIncome();

        // Calculer les ratios
        ratios.setCurrentRatio(currentLiabilities != 0 ? currentAssets / currentLiabilities : 0);
        ratios.setQuickRatio((currentAssets - getTotalForCategory(balanceSheet.getAssets(), "Stocks"))
                / (currentLiabilities != 0 ? currentLiabilities : 1));
        ratios.setDebtEquityRatio(equity != 0 ? totalLiabilities / equity : 0);
        ratios.setReturnOnEquity(equity != 0 ? (netIncome / equity) * 100 : 0);
        ratios.setReturnOnAssets(totalAssets != 0 ? (netIncome / totalAssets) * 100 : 0);

        return ratios;
    }

    private double getTotalForCategory(Map<String, List<AccountBalanceDTO>> accounts, String category) {
        return accounts.getOrDefault(category, Collections.emptyList())
                .stream()
                .mapToDouble(AccountBalanceDTO::getBalance)
                .sum();
    }
}
