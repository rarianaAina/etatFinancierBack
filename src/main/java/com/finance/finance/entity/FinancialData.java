package com.finance.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

import java.time.LocalDate;

@Entity
public class FinancialData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;
    private Double amount;
    private LocalDate date;

    // Getters, setters, et annotations Lombok
    @Builder
    public FinancialData(String accountName, Double amount, LocalDate date) {
        this.accountName = accountName;
        this.amount = amount;
        this.date = date;
    }
}
