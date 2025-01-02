package com.finance.finance.controller;

import com.finance.finance.dto.EntryDTO;
import com.finance.finance.entity.FinancialEntry;
import com.finance.finance.entity.Account;
import com.finance.finance.entity.Transaction;
import com.finance.finance.repository.AccountRepository;
import com.finance.finance.service.FinancialEntryService;
import com.finance.finance.service.AccountService;
import com.finance.finance.service.TransactionService;
import com.finance.finance.constants.AccountingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/entry")
public class FinancialEntryController {

    @Autowired
    private FinancialEntryService entryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<String> addEntries(@RequestBody List<EntryDTO> entryDTOs) {
        try {
            if (entryDTOs.isEmpty()) {
                return ResponseEntity.badRequest().body("La liste des entrées est vide.");
            }

            validateBalancedTransaction(entryDTOs);

            Transaction transaction = new Transaction();
            transaction.setDescription("Transaction pour plusieurs Financial Entries");
            transaction.setAmount(0.0);
            transactionService.addTransaction(transaction);

            for (EntryDTO entryDTO : entryDTOs) {
                Account account = accountService.getAccountByCode(entryDTO.getAccountCode());

                if (account == null) {
                    throw new IllegalArgumentException(
                            String.format(AccountingConstants.ERROR_ACCOUNT_NOT_FOUND, entryDTO.getAccountCode())
                    );
                }

                FinancialEntry entry = createFinancialEntry(entryDTO, account);
                entryService.addEntry(entry, transaction);
            }

            return ResponseEntity.ok("Entrées financières ajoutées avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout des entrées financières : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<EntryDTO>> getAllFinancialEntries() {
        // Récupérer toutes les entités FinancialEntry
        List<FinancialEntry> entries = entryService.getAllFinancialEntries();

        // Convertir chaque FinancialEntry en EntryDTO
        List<EntryDTO> entryDTOs = entries.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        // Retourner la liste de EntryDTO
        return ResponseEntity.ok(entryDTOs);
    }
    private void validateBalancedTransaction(List<EntryDTO> entries) {
        double totalDebit = entries.stream()
                .mapToDouble(e -> e.getAmount_debit() != null ? e.getAmount_debit() : 0.0)
                .sum();

        double totalCredit = entries.stream()
                .mapToDouble(e -> e.getAmount_credit() != null ? e.getAmount_credit() : 0.0)
                .sum();

        if (Math.abs(totalDebit - totalCredit) > 0.001) { // Using epsilon for floating point comparison
            throw new IllegalArgumentException("La transaction n'est pas équilibrée. Total débit: " +
                    totalDebit + ", Total crédit: " + totalCredit);
        }
    }

    private FinancialEntry createFinancialEntry(EntryDTO entryDTO, Account account) {
        FinancialEntry entry = new FinancialEntry();
        entry.setDate(entryDTO.getDate());
        entry.setAmountDebit(entryDTO.getAmount_debit());
        entry.setAmountCredit(entryDTO.getAmount_credit());
        entry.setAccount(account);
        entry.setDescription(entryDTO.getDescription());
        return entry;
    }

    private EntryDTO convertToDTO(FinancialEntry entry) {
        EntryDTO dto = new EntryDTO();
        dto.setDate(entry.getDate());
        dto.setAmount_debit(entry.getAmountDebit());
        dto.setAmount_credit(entry.getAmountCredit());
        dto.setAccountCode(entry.getAccount().getAccountCode());  // Utilisation de l'objet Account
        dto.setDescription(entry.getDescription());
        return dto;
    }
}