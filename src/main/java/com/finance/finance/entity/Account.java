package com.finance.finance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_code")
    private String accountCode;

    @OneToMany(mappedBy = "account")
    private List<FinancialEntry> financialEntries;

    @ManyToMany
    @JoinTable(
            name = "account_categories",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }
}

