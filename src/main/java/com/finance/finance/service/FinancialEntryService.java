package com.finance.finance.service;

import com.finance.finance.entity.FinancialEntry;
import com.finance.finance.repository.FinancialDataRepository;
import com.finance.finance.repository.FinancialEntryrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialEntryService {

    @Autowired
    private FinancialEntryrepository repository;

    public void addEntry(FinancialEntry entry) {
        repository.save(entry);
    }

}
