package com.finance.finance.dto;

public class AccountBalanceDTO {
    private String accountCode;
    private String accountName;
    private Double debitTotal;
    private Double creditTotal;
    private Double balance;

    // Constructeurs, getters et setters
    public AccountBalanceDTO(String accountCode, String accountName, Double debitTotal, Double creditTotal) {
        this.accountCode = accountCode;
        this.accountName = accountName;
        this.debitTotal = debitTotal;
        this.creditTotal = creditTotal;
        this.balance = (debitTotal != null ? debitTotal : 0.0) - (creditTotal != null ? creditTotal : 0.0);
    }

    // Getters et setters
    public String getAccountCode() { return accountCode; }
    public void setAccountCode(String accountCode) { this.accountCode = accountCode; }
    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }
    public Double getDebitTotal() { return debitTotal; }
    public void setDebitTotal(Double debitTotal) { this.debitTotal = debitTotal; }
    public Double getCreditTotal() { return creditTotal; }
    public void setCreditTotal(Double creditTotal) { this.creditTotal = creditTotal; }
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}
