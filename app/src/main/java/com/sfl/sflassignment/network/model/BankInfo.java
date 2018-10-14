package com.sfl.sflassignment.network.model;

import java.util.Map;

public class BankInfo {
    private double date;
    private Map<String, BranchInfo> list;

    public double getDate() {
        return date;
    }

    public void setDate(double date) {
        this.date = date;
    }

    public Map<String, BranchInfo> getList() {
        return list;
    }

    public void setList(Map<String, BranchInfo> list) {
        this.list = list;
    }
}
