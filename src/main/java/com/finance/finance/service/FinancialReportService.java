package com.finance.finance.service;

import com.finance.finance.entity.FinancialReport;
import com.finance.finance.repository.FinancialReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialReportService {

    @Autowired
    private FinancialReportRepository reportRepository;

    public FinancialReport getReportById(Long reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }
}

