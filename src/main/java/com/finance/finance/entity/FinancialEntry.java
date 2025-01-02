package com.finance.finance.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "financial_entries")
public class FinancialEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(name = "amount_debit")
    private Double amountDebit;

    @Column(name = "amount_credit")
    private Double amountCredit;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_transaction") // FK vers Transaction
    private Transaction transaction;

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

    public Double getAmountDebit() {
        return amountDebit;
    }

    public void setAmountDebit(Double amountDebit) {
        this.amountDebit = amountDebit;
    }

    public Double getAmountCredit() {
        return amountCredit;
    }

    public void setAmountCredit(Double amountCredit) {
        this.amountCredit = amountCredit;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
