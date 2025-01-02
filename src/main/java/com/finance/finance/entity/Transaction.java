package com.finance.finance.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private Double amount; // Ajout du champ amount

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<FinancialEntry> financialEntries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<FinancialEntry> getFinancialEntries() {
        return financialEntries;
    }

    public void setFinancialEntries(List<FinancialEntry> financialEntries) {
        this.financialEntries = financialEntries;
    }
}
