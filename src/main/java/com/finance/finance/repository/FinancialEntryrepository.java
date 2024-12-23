package com.finance.finance.repository;

import com.finance.finance.entity.FinancialData;
import com.finance.finance.entity.FinancialEntry;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialEntryrepository extends JpaRepository<FinancialEntry, Long> {

}