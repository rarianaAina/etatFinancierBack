package com.finance.finance.controller;

import com.finance.finance.dto.EntryDTO;
import com.finance.finance.entity.FinancialEntry;
import com.finance.finance.entity.Account;
import com.finance.finance.entity.FinancialReport;
import com.finance.finance.service.FinancialEntryService;
import com.finance.finance.service.AccountService;
import com.finance.finance.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/add-entry")
public class FinancialEntryController {

    @Autowired
    private FinancialEntryService entryService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FinancialReportService reportService;

    @PostMapping("/add")
    public ResponseEntity<String> addEntry(@RequestBody EntryDTO entryDTO) {
        try {
            // Vérification de l'existence de l'account et du financialReport
            Account account = accountService.getAccountById(entryDTO.getAccountId());
            FinancialReport report = reportService.getReportById(entryDTO.getFinancialReportId());

            if (account == null) {
                return ResponseEntity.badRequest().body("Compte non trouvé.");
            }

            if (report == null) {
                return ResponseEntity.badRequest().body("Rapport financier non trouvé.");
            }

            // Création de l'objet FinancialEntry
            FinancialEntry entry = new FinancialEntry();
            entry.setDate(entryDTO.getDate());
            entry.setAmount(entryDTO.getAmount());
            entry.setAccount(account);
            entry.setFinancialReport(report);

            // Enregistrement dans la base
            entryService.addEntry(entry);

            return ResponseEntity.ok("Entrée financière ajoutée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout de l'entrée financière : " + e.getMessage());
        }
    }
}
