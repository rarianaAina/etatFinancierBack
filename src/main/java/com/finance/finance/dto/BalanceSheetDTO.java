package com.finance.finance.dto;

import java.util.List;
import java.util.Map;

public class BalanceSheetDTO {
    private Map<String, List<AccountBalanceDTO>> assets;
    private Map<String, List<AccountBalanceDTO>> liabilities;
    private Double totalAssets;
    private Double totalLiabilities;

    // Constructeurs, getters et setters
    public BalanceSheetDTO() {}

    public Map<String, List<AccountBalanceDTO>> getAssets() { return assets; }
    public void setAssets(Map<String, List<AccountBalanceDTO>> assets) { this.assets = assets; }
    public Map<String, List<AccountBalanceDTO>> getLiabilities() { return liabilities; }
    public void setLiabilities(Map<String, List<AccountBalanceDTO>> liabilities) { this.liabilities = liabilities; }
    public Double getTotalAssets() { return totalAssets; }
    public void setTotalAssets(Double totalAssets) { this.totalAssets = totalAssets; }
    public Double getTotalLiabilities() { return totalLiabilities; }
    public void setTotalLiabilities(Double totalLiabilities) { this.totalLiabilities = totalLiabilities; }
}
