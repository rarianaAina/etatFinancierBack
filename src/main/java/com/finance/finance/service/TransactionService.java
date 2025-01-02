package com.finance.finance.service;

import com.finance.finance.entity.FinancialEntry;
import com.finance.finance.entity.Transaction;
import com.finance.finance.repository.FinancialEntryrepository;
import com.finance.finance.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;

    public void addTransaction(Transaction transaction) {
        repository.save(transaction);
    }
}
