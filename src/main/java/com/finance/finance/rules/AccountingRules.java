package com.finance.finance.rules;

import java.util.Set;
import java.util.HashSet;

public class AccountingRules {
    // Comptes d'actif qui peuvent avoir un solde créditeur
    private static final Set<String> REVERSED_ASSET_ACCOUNTS = new HashSet<>(Set.of(
            "512", // Banque
            "411", // Clients
            "416", // Clients douteux
            "417", // Créances sur travaux non encore facturables
            "418", // Clients - Produits non encore facturés
            "409", // Fournisseurs débiteurs
            "471", // Comptes d'attente
            "486", // Charges constatées d'avance
            "467", // Autres comptes débiteurs ou créditeurs
            "478", // Autres comptes transitoires
            "506", // Obligations
            "507", // Bons du Trésor
            "508", // Valeurs mobilières de placement
            "509", // Versements restant à effectuer sur VMP non libérées
            "514", // Chèques postaux
            "515", // Caisses du Trésor et des établissements publics
            "516", // Sociétés de bourse
            "517", // Autres organismes financiers
            "518", // Intérêts courus
            "519", // Concours bancaires courants
            "531", // Caisse
            "532", // Caisse succursale
            "54"   // Régies d'avance et accréditifs
    ));

    // Comptes de passif qui peuvent avoir un solde débiteur
    private static final Set<String> REVERSED_LIABILITY_ACCOUNTS = new HashSet<>(Set.of(
            "401", // Fournisseurs
            "403", // Effets à payer
            "404", // Fournisseurs d'immobilisations
            "405", // Fournisseurs d'immobilisations - Effets à payer
            "408", // Fournisseurs - Factures non parvenues
            "419", // Clients créditeurs
            "421", // Personnel - Rémunérations dues
            "422", // Comités d'entreprise, d'établissement
            "424", // Participation des salariés aux résultats
            "427", // Personnel - Oppositions
            "428", // Personnel - Charges à payer et produits à recevoir
            "431", // Sécurité sociale
            "437", // Autres organismes sociaux
            "438", // Organismes sociaux - Charges à payer et produits à recevoir
            "442", // État - Impôts et taxes recouvrables sur des tiers
            "443", // Opérations particulières avec l'État
            "444", // État - Impôts sur les bénéfices
            "445", // État - Taxes sur le chiffre d'affaires
            "447", // Autres impôts, taxes et versements assimilés
            "448", // État - Charges à payer et produits à recevoir
            "487", // Produits constatés d'avance
            "491", // Provisions pour dépréciation des comptes de clients
            "495", // Provisions pour dépréciation des comptes du groupe
            "496", // Provisions pour dépréciation des comptes de débiteurs divers
            "498"  // Provisions pour dépréciation des comptes de créances diverses
    ));

    // Comptes qui ne peuvent jamais être débités
    private static final Set<String> CREDIT_ONLY_ACCOUNTS = new HashSet<>(Set.of(
            "101", // Capital
            "102", // Fonds propres
            "105", // Écarts de réévaluation
            "106", // Réserves
            "11",  // Report à nouveau
            "12",  // Résultat de l'exercice
            "13",  // Subventions d'investissement
            "14",  // Provisions réglementées
            "15"   // Provisions
    ));

    // Comptes qui ne peuvent jamais être crédités
    private static final Set<String> DEBIT_ONLY_ACCOUNTS = new HashSet<>(Set.of(
            "6",   // Tous les comptes de charges
            "212", // Agencements, aménagements de terrains
            "213", // Constructions
            "215", // Installations techniques, matériels et outillages
            "218", // Autres immobilisations corporelles
            "231", // Immobilisations corporelles en cours
            "232", // Immobilisations incorporelles en cours
            "237", // Avances et acomptes versés sur immobilisations incorporelles
            "238"  // Avances et acomptes versés sur commandes d'immobilisations corporelles
    ));

    public static boolean isReversedAssetAccount(String accountCode) {
        return REVERSED_ASSET_ACCOUNTS.contains(accountCode);
    }

    public static boolean isReversedLiabilityAccount(String accountCode) {
        return REVERSED_LIABILITY_ACCOUNTS.contains(accountCode);
    }

    public static boolean isCreditOnlyAccount(String accountCode) {
        return CREDIT_ONLY_ACCOUNTS.contains(accountCode) ||
                CREDIT_ONLY_ACCOUNTS.contains(accountCode.substring(0, 2)) ||
                CREDIT_ONLY_ACCOUNTS.contains(accountCode.substring(0, 1));
    }

    public static boolean isDebitOnlyAccount(String accountCode) {
        return DEBIT_ONLY_ACCOUNTS.contains(accountCode) ||
                DEBIT_ONLY_ACCOUNTS.contains(accountCode.substring(0, 2)) ||
                DEBIT_ONLY_ACCOUNTS.contains(accountCode.substring(0, 1));
    }

    public static boolean isValidEntry(String accountCode, Double debitAmount, Double creditAmount) {
        if (isCreditOnlyAccount(accountCode) && debitAmount != null && debitAmount > 0) {
            return false;
        }
        if (isDebitOnlyAccount(accountCode) && creditAmount != null && creditAmount > 0) {
            return false;
        }
        return true;
    }
}