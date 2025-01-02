package com.finance.finance.service;

import com.finance.finance.entity.Account;
import com.finance.finance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public Account getAccountByCode(String accountCode) {
        return accountRepository.findByAccountCode(accountCode);
    }

    public String getCategoryTypeByAccountId(Long accountId) {
        // Appel du repository pour obtenir la catégorie du compte
        return accountRepository.findCategoryTypeByAccountId(accountId);
    }
}
