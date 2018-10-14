package com.sfl.sflassignment.ui.rates.view.model;

import android.support.annotation.Nullable;

public class RateItem {
    private String identifier;
    private String title;
    private Float distance;
    private float buyPrice;
    private float sellPrice;

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof RateItem && identifier.equals(((RateItem) obj).identifier);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }
}
