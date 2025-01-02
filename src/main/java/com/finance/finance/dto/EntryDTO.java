package com.finance.finance.dto;

import lombok.Data;

import java.time.LocalDate;


public class EntryDTO {
    private LocalDate date;
    private Double amount_debit;
    private Double amount_credit;
    private String accountCode;

    private String description;

    public Double getAmount_debit() {
        return amount_debit;
    }

    public void setAmount_debit(Double amount_debit) {
        this.amount_debit = amount_debit;
    }

    public Double getAmount_credit() {
        return amount_credit;
    }

    public void setAmount_credit(Double amount_credit) {
        this.amount_credit = amount_credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountId) {
        this.accountCode = accountId;
    }

}