package com.sfl.sflassignment.network.rubish.constant;

public enum ExchangeType {

    CASH("0"), CASHLESS("1");

    String value;

    ExchangeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ExchangeType valueFrom(String value) {
        for (ExchangeType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }

        return null;
    }
}
