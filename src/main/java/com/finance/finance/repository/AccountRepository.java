package com.finance.finance.repository;

import com.finance.finance.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountCode(String accountCode);

    @Query("SELECT a.accountCode FROM Account a")
    List<String> findAllAccountCodes();

    @Query(value = "SELECT c.name FROM accounts a " +
            "JOIN account_categories ac ON a.account_id = ac.account_id " +
            "JOIN categories c ON ac.category_id = c.id " +
            "WHERE a.account_id = :accountId", nativeQuery = true)
    String findCategoryTypeByAccountId(@Param("accountId") Long accountId);
}


