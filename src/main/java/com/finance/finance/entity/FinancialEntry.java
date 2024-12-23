package com.finance.finance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Table(name = "financial_entries")
public class FinancialEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private FinancialReport financialReport;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public FinancialReport getFinancialReport() {
        return financialReport;
    }

    public void setFinancialReport(FinancialReport financialReport) {
        this.financialReport = financialReport;
    }
}
