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
public interface FinancialEntryrepository extends JpaRepository<FinancialEntry, Long> {

    @Query("SELECT NEW com.finance.finance.dto.AccountBalanceDTO(" +
            "a.accountCode, a.accountName, " +
            "SUM(fe.amountDebit), SUM(fe.amountCredit)) " +
            "FROM FinancialEntry fe " +
            "JOIN fe.account a " +
            "WHERE fe.date BETWEEN :startDate AND :endDate " +
            "GROUP BY a.accountCode, a.accountName " +
            "ORDER BY a.accountCode")
    List<AccountBalanceDTO> getAccountBalances(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );


}
