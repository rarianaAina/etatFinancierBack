package com.finance.finance.controller;

import com.finance.finance.entity.CategoryBalances;
import com.finance.finance.entity.CategoryBalances;
import com.finance.finance.service.CategoryBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CategoryBalanceController {

    private final CategoryBalanceService categoryBalanceService;

    @Autowired
    public CategoryBalanceController(CategoryBalanceService categoryBalanceService) {
        this.categoryBalanceService = categoryBalanceService;
    }

    @GetMapping("/category-balances")
    public ResponseEntity<List<CategoryBalances>> getCategoryBalances() {
        List<CategoryBalances> categoryBalances = categoryBalanceService.getAllCategoryBalances();
        return ResponseEntity.ok(categoryBalances);
    }
}
