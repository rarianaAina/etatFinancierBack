package com.finance.finance.dto;

public class RatioDTO {
    private double currentRatio;
    private double quickRatio;
    private double debtEquityRatio;
    private double returnOnEquity;
    private double returnOnAssets;

    public double getCurrentRatio() {
        return currentRatio;
    }

    public void setCurrentRatio(double currentRatio) {
        this.currentRatio = currentRatio;
    }

    public double getQuickRatio() {
        return quickRatio;
    }

    public void setQuickRatio(double quickRatio) {
        this.quickRatio = quickRatio;
    }

    public double getDebtEquityRatio() {
        return debtEquityRatio;
    }

    public void setDebtEquityRatio(double debtEquityRatio) {
        this.debtEquityRatio = debtEquityRatio;
    }

    public double getReturnOnEquity() {
        return returnOnEquity;
    }

    public void setReturnOnEquity(double returnOnEquity) {
        this.returnOnEquity = returnOnEquity;
    }

    public double getReturnOnAssets() {
        return returnOnAssets;
    }

    public void setReturnOnAssets(double returnOnAssets) {
        this.returnOnAssets = returnOnAssets;
    }

    // Getters et Setters
    // ... (générer les getters et setters pour tous les champs)
}