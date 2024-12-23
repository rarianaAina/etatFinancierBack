package com.finance.finance.repository;

import com.finance.finance.entity.FinancialReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialReportRepository extends JpaRepository<FinancialReport, Long> {
    // Méthodes personnalisées si nécessaire
}
