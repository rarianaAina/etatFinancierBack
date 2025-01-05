package com.finance.finance.utils;

import com.finance.finance.dto.AccountBalanceDTO;
import java.util.Arrays;

public class AccountingUtils {
    private static final String[] ASSET_ACCOUNTS = {"411", "425", "441", "462", "467"};
    private static final String[] LIABILITY_ACCOUNTS = {"401", "404", "421", "431", "443", "444", "448"};

    // Liste des comptes avec exceptions à ne pas classer comme actifs
    private static final String[] SPECIAL_ASSET_ACCOUNTS = {"445"}; // TVA à décaisser

    // Liste des comptes avec exceptions à ne pas classer comme passifs
    private static final String[] SPECIAL_LIABILITY_ACCOUNTS = {"445", "431", "441", "443", "421"}; // TVA collectée, Salaires, CNaPS, Organismes sociaux

    public static boolean isAssetAccount(AccountBalanceDTO balance) {
        String code = balance.getAccountCode();

        // Si le compte fait partie des comptes spéciaux, il ne doit pas être classé comme actif
        if (Arrays.asList(SPECIAL_ASSET_ACCOUNTS).contains(code)) {
            return false;
        }

        // Logique générale pour classer les comptes d'actifs
        return code.startsWith("2") ||
                code.startsWith("3") ||
                code.startsWith("5") ||
                (code.startsWith("4") && Arrays.asList(ASSET_ACCOUNTS).contains(code));
    }

    public static boolean isLiabilityAccount(AccountBalanceDTO balance) {
        String code = balance.getAccountCode();

        // Si le compte fait partie des comptes spéciaux, il doit être classé comme passif
        if (Arrays.asList(SPECIAL_LIABILITY_ACCOUNTS).contains(code)) {
            return true;
        }

        // Logique générale pour classer les comptes de passifs
        return code.startsWith("1") ||
                (code.startsWith("4") && Arrays.asList(LIABILITY_ACCOUNTS).contains(code));
    }
}
