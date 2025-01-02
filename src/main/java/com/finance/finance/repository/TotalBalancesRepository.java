package com.finance.finance.repository;

import com.finance.finance.entity.CategoryBalances;
import com.finance.finance.entity.TotalBalances;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalBalancesRepository extends JpaRepository<TotalBalances, Long> {

    @Query("SELECT tb FROM TotalBalances tb")
    TotalBalances findTotalBalances();
}
