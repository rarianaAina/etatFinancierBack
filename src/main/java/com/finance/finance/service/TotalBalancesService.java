package com.finance.finance.service;

import com.finance.finance.entity.TotalBalances;
import com.finance.finance.repository.TotalBalancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TotalBalancesService {

    @Autowired
    private TotalBalancesRepository repository;

    public TotalBalances getTotalBalances() {
        return repository.findTotalBalances();
    }
}
