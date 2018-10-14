package com.sfl.sflassignment.ui.constant;

public enum TransactionType {
    CASH("0"), CASHLESS("1");

    private String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransactionType valueFrom(String value) {
        for (TransactionType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        return null;
    }

    public static String[] stringValues() {
        TransactionType types[] = values();
        String stringValues[] = new String[types.length];

        for (int i = 0; i < types.length; i++) {
            stringValues[i] = types[i].toString();
        }

        return stringValues;
    }
}
