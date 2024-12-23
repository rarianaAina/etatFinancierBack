package com.finance.finance.service;

import com.finance.finance.entity.FinancialData;
import com.finance.finance.repository.FinancialDataRepository;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class FileImportService {

    @Autowired
    private FinancialDataRepository repository;

    public void importExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Sauter l'en-tête

            String accountName = row.getCell(0).getStringCellValue();
            Double amount = row.getCell(1).getNumericCellValue();
            LocalDate date = row.getCell(2).getLocalDateTimeCellValue().toLocalDate();

            FinancialData data = new FinancialData(accountName, amount, date);
            repository.save(data);
        }

        workbook.close();
    }
}
