package com.sfl.sflassignment.ui.branches.view.model;

import java.util.Map;

public class Header {
    private String title;
    private String city;
    private String address;
    private String phoneNumber;
    private Map<String, String> workingDays;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Map<String, String> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Map<String, String> workingDays) {
        this.workingDays = workingDays;
    }
}
