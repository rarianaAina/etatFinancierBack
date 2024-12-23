package com.finance.finance.repository;

import com.finance.finance.entity.FinancialData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialDataRepository extends JpaRepository<FinancialData, Long> {
    double findSumByAccountType(String currentAssets);
}

