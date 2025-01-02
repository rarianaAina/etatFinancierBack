package com.finance.finance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "total_balances")
public class TotalBalances {

    @Id
    private Long id;  // Clé primaire venant de la vue

    private Double totalAssetsAndCharges;
    private Double totalLiabilitiesAndIncome;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalAssetsAndCharges() {
        return totalAssetsAndCharges;
    }

    public void setTotalAssetsAndCharges(Double totalAssetsAndCharges) {
        this.totalAssetsAndCharges = totalAssetsAndCharges;
    }

    public Double getTotalLiabilitiesAndIncome() {
        return totalLiabilitiesAndIncome;
    }

    public void setTotalLiabilitiesAndIncome(Double totalLiabilitiesAndIncome) {
        this.totalLiabilitiesAndIncome = totalLiabilitiesAndIncome;
    }
}
