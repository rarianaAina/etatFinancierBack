package com.finance.finance.controller;

import com.finance.finance.entity.TotalBalances;
import com.finance.finance.service.TotalBalancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TotalBalancesController {

    @Autowired
    private TotalBalancesService service;

    @GetMapping("/api/total-balances")
    public TotalBalances getTotalBalances() {
        return service.getTotalBalances();
    }
}
