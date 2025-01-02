package com.finance.finance.repository;

import com.finance.finance.dto.AccountBalanceDTO;
import com.finance.finance.entity.FinancialEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<FinancialEntry, Long> {

}