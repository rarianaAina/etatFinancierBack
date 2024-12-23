package com.finance.finance.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "financial_reports")
public class FinancialReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private LocalDate createdDate;

    @OneToMany(mappedBy = "financialReport")
    private List<FinancialEntry> financialEntries;
}
