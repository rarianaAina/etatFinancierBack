package com.finance.finance.service;

import com.finance.finance.repository.FinancialDataRepository;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinancialAnalysisService {

    @Autowired
    private FinancialDataRepository repository;

    public double calculateLiquidityRatio() {
        Tuple currentAssetsTuple = repository.findSumByAccountName("Current Assets");
        Tuple currentLiabilitiesTuple = repository.findSumByAccountName("Current Liabilities");

        if (currentAssetsTuple == null || currentLiabilitiesTuple == null) {
            // Gérer le cas où il n'y a pas de résultats (par exemple, retourner 0 ou lancer une exception)
            throw new IllegalStateException("No data found for current assets or current liabilities.");
        }

        double currentAssets = (Double) currentAssetsTuple.get(0); // Supposons que la somme est à l'index 0
        double currentLiabilities = (Double) currentLiabilitiesTuple.get(0);

        return currentAssets / currentLiabilities;
    }



    public double calculateNetProfitMargin() {
        // Récupérer les valeurs des revenus et des dépenses à partir des Tuples
        Tuple revenueTuple = repository.findSumByAccountName("Revenue");
        Tuple expensesTuple = repository.findSumByAccountName("Expenses");

        // Extraire les valeurs des Tuples (assurez-vous que l'index et le type sont corrects)
        Double revenue = (Double) revenueTuple.get(0);  // Index 0 pour la somme des "Revenue"
        Double expenses = (Double) expensesTuple.get(0);  // Index 0 pour la somme des "Expenses"

        // Vérifier si les valeurs sont non nulles et non égales à zéro avant de diviser
        if (revenue != null && revenue != 0) {
            return (revenue - expenses) / revenue * 100;  // Calcul de la marge nette
        } else {
            // Si le revenu est nul ou égal à zéro, retourner un pourcentage invalide ou gérer l'erreur
            return 0.0;
        }
    }

}
