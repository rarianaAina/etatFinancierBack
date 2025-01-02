package com.finance.finance.service;

import com.finance.finance.entity.Account;
import com.finance.finance.entity.FinancialEntry;
import com.finance.finance.entity.Transaction;
import com.finance.finance.repository.AccountRepository;
import com.finance.finance.repository.EntryRepository;
import com.finance.finance.repository.FinancialEntryrepository;
import com.finance.finance.rules.AccountingRules;
import com.finance.finance.constants.AccountingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FinancialEntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private FinancialEntryrepository repository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public void addEntry(FinancialEntry entry, Transaction transaction) {
        Account account = entry.getAccount();
        String accountCategory = accountService.getCategoryTypeByAccountId(account.getAccountId());

        validateEntry(account, entry);

        repository.save(entry);

        if (transaction == null) {
            transaction = new Transaction();
            transaction.setDescription("Transaction pour Financial Entry");
            transaction.setAmount(0.0);
        }

        double entryAmount = calculateEntryAmount(entry);
        transaction.setAmount(transaction.getAmount() + entryAmount);

        entry.setTransaction(transaction);
        repository.save(entry);
    }

    public List<FinancialEntry> getAllFinancialEntries() {
        return entryRepository.findAll();
    }
    private void validateEntry(Account account, FinancialEntry entry) {
        String accountCode = account.getAccountCode();

        if (!AccountingRules.isValidEntry(accountCode, entry.getAmountDebit(), entry.getAmountCredit())) {
            throw new IllegalArgumentException(
                    String.format(AccountingConstants.ERROR_INVALID_ENTRY, accountCode)
            );
        }

        validateBalanceRules(account, entry);
    }

    private void validateBalanceRules(Account account, FinancialEntry entry) {
        String accountCategory = accountService.getCategoryTypeByAccountId(account.getAccountId());
        String accountCode = account.getAccountCode();

        // Validation des règles spécifiques pour les comptes d'actif
        if (isAssetAccount(accountCategory)) {
            validateAssetAccountRules(accountCode, entry);
        }
        // Validation des règles spécifiques pour les comptes de passif
        else if (isLiabilityAccount(accountCategory)) {
            validateLiabilityAccountRules(accountCode, entry);
        }
        // Validation des règles pour les comptes de charges
        else if (isExpenseAccount(accountCategory)) {
            validateExpenseAccountRules(entry);
        }
        // Validation des règles pour les comptes de produits
        else if (isIncomeAccount(accountCategory)) {
            validateIncomeAccountRules(entry);
        }
    }

    private boolean isAssetAccount(String category) {
        return category.equals(AccountingConstants.ACCOUNT_TYPE_CURRENT_ASSET) ||
                category.equals(AccountingConstants.ACCOUNT_TYPE_NON_CURRENT_ASSET);
    }

    private boolean isLiabilityAccount(String category) {
        return category.equals(AccountingConstants.ACCOUNT_TYPE_CURRENT_LIABILITY) ||
                category.equals(AccountingConstants.ACCOUNT_TYPE_NON_CURRENT_LIABILITY);
    }

    private boolean isExpenseAccount(String category) {
        return category.equals(AccountingConstants.ACCOUNT_TYPE_EXPENSE);
    }

    private boolean isIncomeAccount(String category) {
        return category.equals(AccountingConstants.ACCOUNT_TYPE_INCOME);
    }

    private void validateAssetAccountRules(String accountCode, FinancialEntry entry) {
        if (!AccountingRules.isReversedAssetAccount(accountCode)) {
            if (entry.getAmountCredit() != null && entry.getAmountCredit() > 0) {
                // Vérifier si le solde global reste débiteur
                double currentBalance = calculateAccountBalance(accountCode);
                if ((currentBalance - entry.getAmountCredit()) < 0) {
                    throw new IllegalArgumentException(
                            "Le solde d'un compte d'actif ne peut pas être créditeur sauf exception"
                    );
                }
            }
        }
    }

    private void validateLiabilityAccountRules(String accountCode, FinancialEntry entry) {
        if (!AccountingRules.isReversedLiabilityAccount(accountCode)) {
            if (entry.getAmountDebit() != null && entry.getAmountDebit() > 0) {
                // Vérifier si le solde global reste créditeur
                double currentBalance = calculateAccountBalance(accountCode);
                if ((currentBalance + entry.getAmountDebit()) > 0) {
                    throw new IllegalArgumentException(
                            "Le solde d'un compte de passif ne peut pas être débiteur sauf exception"
                    );
                }
            }
        }
    }

    private void validateExpenseAccountRules(FinancialEntry entry) {
        if (entry.getAmountCredit() != null && entry.getAmountCredit() > 0) {
            throw new IllegalArgumentException(
                    "Un compte de charge ne peut pas être crédité"
            );
        }
    }

    private void validateIncomeAccountRules(FinancialEntry entry) {
        if (entry.getAmountDebit() != null && entry.getAmountDebit() > 0) {
            throw new IllegalArgumentException(
                    "Un compte de produit ne peut pas être débité"
            );
        }
    }

    private double calculateEntryAmount(FinancialEntry entry) {
        return (entry.getAmountDebit() != null ? entry.getAmountDebit() : 0)
                + (entry.getAmountCredit() != null ? entry.getAmountCredit() : 0);
    }

    private double calculateAccountBalance(String accountCode) {
        // Cette méthode devrait être implémentée pour calculer le solde actuel du compte
        // en tenant compte de toutes les écritures existantes
        return 0.0; // À implémenter selon vos besoins
    }
}