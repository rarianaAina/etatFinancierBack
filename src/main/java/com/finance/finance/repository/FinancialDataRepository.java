package com.finance.finance.repository;

import com.finance.finance.entity.FinancialData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import jakarta.persistence.Tuple;

public interface FinancialDataRepository extends JpaRepository<FinancialData, Long> {

    // Requête personnalisée pour retourner une valeur agrégée et l'entité FinancialData
    @Query("SELECT SUM(f.amount), f.accountName FROM FinancialData f WHERE f.accountName = :currentAssets GROUP BY f.accountName")
    Tuple findSumByAccountName(String currentAssets);


}
