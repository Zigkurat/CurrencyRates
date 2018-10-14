package com.sfl.sflassignment.ui.constant;

public enum CurrencyType {
    USD, EUR, RUR, GEL, GBP, CHF, CAD, AUD, JPY, XAU;

    public static String[] stringValues() {
        CurrencyType types[] = values();
        String stringValues[] = new String[types.length];

        for (int i = 0; i < types.length; i++) {
            stringValues[i] = types[i].toString();
        }

        return stringValues;
    }
}
