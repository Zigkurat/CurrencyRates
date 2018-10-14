package com.sfl.sflassignment.network.model;

import java.util.Map;

public class ExchangeRates {
    private String title;
    private long date;
    private String logo;
    private Map<String, Map<String, ExchangeRate>> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Map<String, Map<String, ExchangeRate>> getList() {
        return list;
    }

    public void setList(Map<String, Map<String, ExchangeRate>> list) {
        this.list = list;
    }
}