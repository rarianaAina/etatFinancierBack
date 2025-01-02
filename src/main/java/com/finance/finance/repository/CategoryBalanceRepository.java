package com.finance.finance.repository;

import com.finance.finance.entity.CategoryBalances;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryBalanceRepository extends JpaRepository<CategoryBalances, Long> {
}
