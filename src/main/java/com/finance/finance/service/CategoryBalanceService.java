package com.finance.finance.service;

import com.finance.finance.entity.CategoryBalances;
import com.finance.finance.repository.CategoryBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBalanceService {

    private final CategoryBalanceRepository categoryBalanceRepository;

    @Autowired
    public CategoryBalanceService(CategoryBalanceRepository categoryBalanceRepository) {
        this.categoryBalanceRepository = categoryBalanceRepository;
    }

    public List<CategoryBalances> getAllCategoryBalances() {
        return categoryBalanceRepository.findAll();
    }
}
