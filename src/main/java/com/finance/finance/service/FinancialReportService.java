package com.finance.finance.service;

import com.finance.finance.dto.AccountBalanceDTO;
import com.finance.finance.dto.BalanceSheetDTO;
import com.finance.finance.dto.IncomeStatementDTO;
import com.finance.finance.repository.FinancialEntryrepository;
import com.finance.finance.utils.AccountingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.finance.finance.utils.AccountingUtils.isAssetAccount;
import static com.finance.finance.utils.AccountingUtils.isLiabilityAccount;

@Service
public class FinancialReportService {

    @Autowired
    private FinancialEntryrepository entryRepository;

    public BalanceSheetDTO generateBalanceSheet(LocalDate startDate, LocalDate endDate) {
        List<AccountBalanceDTO> allBalances = entryRepository.getAccountBalances(startDate, endDate);
        BalanceSheetDTO balanceSheet = new BalanceSheetDTO();

        Map<String, List<AccountBalanceDTO>> assets = allBalances.stream()
                .filter(AccountingUtils::isAssetAccount)
                .collect(Collectors.groupingBy(this::getAssetCategory));

        Map<String, List<AccountBalanceDTO>> liabilities = allBalances.stream()
                .filter(AccountingUtils::isLiabilityAccount)
                .collect(Collectors.groupingBy(this::getLiabilityCategory));

        balanceSheet.setAssets(assets);
        balanceSheet.setLiabilities(liabilities);
        balanceSheet.setTotalAssets(calculateAssetsTotal(assets));
        balanceSheet.setTotalLiabilities(calculateLiabilitiesTotal(liabilities));

        return balanceSheet;
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

/*    private Double calculateAssetsTotal(Map<String, List<AccountBalanceDTO>> assets) {
        return assets.values().stream()
                .flatMap(List::stream)
                .mapToDouble(AccountBalanceDTO::getBalance)
                .sum();
    }

    private Double calculateLiabilitiesTotal(Map<String, List<AccountBalanceDTO>> liabilities) {
        return liabilities.values().stream()
                .flatMap(List::stream)
                .mapToDouble(AccountBalanceDTO::getBalance)
                .sum();
    }*/

    private Double calculateAssetsTotal(Map<String, List<AccountBalanceDTO>> assets) {
        return assets.values().stream()
                .flatMap(List::stream)
                .mapToDouble(account -> {
                    // Vérifie si le compte est un actif et calcule la balance en fonction de cela
                    if (isAssetAccount(account)) {
                        return account.getDebitTotal() - account.getCreditTotal(); // Actifs : débit - crédit
                    } else {
                        return 0.0; // Ignore les comptes qui ne sont pas des actifs
                    }
                })
                .sum();
    }

    private Double calculateLiabilitiesTotal(Map<String, List<AccountBalanceDTO>> liabilities) {
        return liabilities.values().stream()
                .flatMap(List::stream)
                .mapToDouble(account -> {
                    // Vérifie si le compte est un passif et calcule la balance en fonction de cela
                    if (isLiabilityAccount(account)) {
                        return account.getCreditTotal() - account.getDebitTotal(); // Passifs : crédit - débit
                    } else {
                        return 0.0; // Ignore les comptes qui ne sont pas des passifs
                    }
                })
                .sum();
    }


    public IncomeStatementDTO generateIncomeStatement(LocalDate startDate, LocalDate endDate) {
        List<AccountBalanceDTO> allBalances = entryRepository.getAccountBalances(startDate, endDate);
        IncomeStatementDTO incomeStatement = new IncomeStatementDTO();

        List<AccountBalanceDTO> revenues = allBalances.stream()
                .filter(b -> b.getAccountCode().startsWith("7"))
                .collect(Collectors.toList());

        List<AccountBalanceDTO> expenses = allBalances.stream()
                .filter(b -> b.getAccountCode().startsWith("6"))
                .collect(Collectors.toList());

        Double totalRevenues = revenues.stream()
                .mapToDouble(balance -> -balance.getBalance())
                .sum();

        Double totalExpenses = expenses.stream()
                .mapToDouble(AccountBalanceDTO::getBalance)
                .sum();

        incomeStatement.setRevenues(revenues);
        incomeStatement.setExpenses(expenses);
        incomeStatement.setTotalRevenues(totalRevenues);
        incomeStatement.setTotalExpenses(totalExpenses);
        incomeStatement.setNetIncome(totalRevenues - totalExpenses);

        return incomeStatement;
    }
}
