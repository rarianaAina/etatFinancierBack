// src/main/java/com/finance/finance/dto/IncomeStatementDTO.java
package com.finance.finance.dto;

import java.util.List;

public class IncomeStatementDTO {
    private List<AccountBalanceDTO> revenues;
    private List<AccountBalanceDTO> expenses;
    private Double totalRevenues;
    private Double totalExpenses;
    private Double netIncome;

    // Constructeurs, getters et setters
    public IncomeStatementDTO() {}

    public List<AccountBalanceDTO> getRevenues() { return revenues; }
    public void setRevenues(List<AccountBalanceDTO> revenues) { this.revenues = revenues; }
    public List<AccountBalanceDTO> getExpenses() { return expenses; }
    public void setExpenses(List<AccountBalanceDTO> expenses) { this.expenses = expenses; }
    public Double getTotalRevenues() { return totalRevenues; }
    public void setTotalRevenues(Double totalRevenues) { this.totalRevenues = totalRevenues; }
    public Double getTotalExpenses() { return totalExpenses; }
    public void setTotalExpenses(Double totalExpenses) { this.totalExpenses = totalExpenses; }
    public Double getNetIncome() { return netIncome; }
    public void setNetIncome(Double netIncome) { this.netIncome = netIncome; }
}
