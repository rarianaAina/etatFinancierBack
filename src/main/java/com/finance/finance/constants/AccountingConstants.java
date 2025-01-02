package com.finance.finance.constants;

public class AccountingConstants {
    // Comptes d'actif
    public static final String ACCOUNT_TYPE_ASSET = "Actifs";
    public static final String ACCOUNT_TYPE_CURRENT_ASSET = "Actifs courants";
    public static final String ACCOUNT_TYPE_NON_CURRENT_ASSET = "Actifs non courants";

    // Comptes de passif
    public static final String ACCOUNT_TYPE_LIABILITY = "Passifs";
    public static final String ACCOUNT_TYPE_CURRENT_LIABILITY = "Passifs courants";
    public static final String ACCOUNT_TYPE_NON_CURRENT_LIABILITY = "Passifs non courants";

    // Comptes de charges et produits
    public static final String ACCOUNT_TYPE_EXPENSE = "Charges";
    public static final String ACCOUNT_TYPE_INCOME = "Produits";

    // Messages d'erreur
    public static final String ERROR_INVALID_ENTRY = "Écriture invalide pour le compte %s";
    public static final String ERROR_ACCOUNT_NOT_FOUND = "Compte non trouvé: %s";
}