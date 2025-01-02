// src/main/java/com/finance/finance/service/FinancialReportService.java
package com.finance.finance.service;

import com.finance.finance.dto.AccountBalanceDTO;
import com.finance.finance.dto.BalanceSheetDTO;
import com.finance.finance.dto.IncomeStatementDTO;
import com.finance.finance.repository.FinancialEntryrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinancialReportService {

    @Autowired
    private FinancialEntryrepository entryRepository;

    public BalanceSheetDTO generateBalanceSheet(LocalDate startDate, LocalDate endDate) {
        List<AccountBalanceDTO> allBalances = entryRepository.getAccountBalances(startDate, endDate);

        BalanceSheetDTO balanceSheet = new BalanceSheetDTO();

        // Grouper les comptes d'actif
        Map<String, List<AccountBalanceDTO>> assets = allBalances.stream()
                .filter(b -> b.getAccountCode().startsWith("2") ||
                        b.getAccountCode().startsWith("3") ||
                        b.getAccountCode().startsWith("4") ||
                        b.getAccountCode().startsWith("5"))
                .collect(Collectors.groupingBy(this::getAssetCategory));

        // Grouper les comptes de passif
        Map<String, List<AccountBalanceDTO>> liabilities = allBalances.stream()
                .filter(b -> b.getAccountCode().startsWith("1") ||
                        b.getAccountCode().startsWith("4") && isLiabilityAccount(b.getAccountCode()))
                .collect(Collectors.groupingBy(this::getLiabilityCategory));

        balanceSheet.setAssets(assets);
        balanceSheet.setLiabilities(liabilities);

        // Calculer les totaux
        balanceSheet.setTotalAssets(calculateTotal(assets));
        balanceSheet.setTotalLiabilities(calculateTotal(liabilities));

        return balanceSheet;
    }

    public IncomeStatementDTO generateIncomeStatement(LocalDate startDate, LocalDate endDate) {
        List<AccountBalanceDTO> allBalances = entryRepository.getAccountBalances(startDate, endDate);

        IncomeStatementDTO incomeStatement = new IncomeStatementDTO();

        // Filtrer les produits (classe 7)
        List<AccountBalanceDTO> revenues = allBalances.stream()
                .filter(b -> b.getAccountCode().startsWith("7"))
                .collect(Collectors.toList());

        // Filtrer les charges (classe 6)
        List<AccountBalanceDTO> expenses = allBalances.stream()
                .filter(b -> b.getAccountCode().startsWith("6"))
                .collect(Collectors.toList());

        incomeStatement.setRevenues(revenues);
        incomeStatement.setExpenses(expenses);

        // Calculer les totaux
        Double totalRevenues = revenues.stream()
                .mapToDouble(balance -> -balance.getBalance()) // Inverser le signe pour les produits
                .sum();
        Double totalExpenses = expenses.stream()
                .mapToDouble(AccountBalanceDTO::getBalance) // Garder le signe tel quel pour les charges
                .sum();

        incomeStatement.setTotalRevenues(totalRevenues);
        incomeStatement.setTotalExpenses(totalExpenses);
        incomeStatement.setNetIncome(totalRevenues - totalExpenses);


        return incomeStatement;
    }

    private String getAssetCategory(AccountBalanceDTO balance) {
        String code = balance.getAccountCode();
        if (code.startsWith("2")) return "Immobilisations";
        if (code.startsWith("3")) return "Stocks";
        if (code.startsWith("4")) return "Créances";
        if (code.startsWith("5")) return "Trésorerie";
        return "Autres actifs";
    }

    private String getLiabilityCategory(AccountBalanceDTO balance) {
        String code = balance.getAccountCode();
        if (code.startsWith("1")) return "Capitaux propres";
        if (code.startsWith("4")) return "Dettes";
        return "Autres passifs";
    }

    private boolean isLiabilityAccount(String accountCode) {
        // Liste des comptes de passif commençant par 4
        return Arrays.asList("401", "404", "408", "419", "421", "428", "431", "438", "444", "445", "448")
                .contains(accountCode);
    }

    private Double calculateTotal(Map<String, List<AccountBalanceDTO>> accounts) {
        return accounts.values().stream()
                .flatMap(List::stream)
                .mapToDouble(balance -> {
                    String code = balance.getAccountCode();
                    // Pour les comptes de trésorerie (classe 5), on prend le solde tel quel
                    if (code.startsWith("5")) {
                        return balance.getBalance();
                    }
                    // Pour les autres comptes d'actif (classes 2, 3, 4)
                    else if (code.matches("^[234].*")) {
                        // On ne prend que les soldes débiteurs
                        return Math.max(balance.getBalance(), 0);
                    }
                    return balance.getBalance();
                })
                .sum();
    }


}
