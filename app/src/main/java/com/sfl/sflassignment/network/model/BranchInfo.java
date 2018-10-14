package com.sfl.sflassignment.network.model;

import java.util.List;
import java.util.Map;

public class BranchInfo {
    private int head;
    private Map<String, String> title;
    private Map<String, String> address;
    private Map<String, Double> location;
    private String contacts;
    private List<WorkHours> workhours;

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public Map<String, String> getTitle() {
        return title;
    }

    public void setTitle(Map<String, String> title) {
        this.title = title;
    }

    public Map<String, String> getAddress() {
        return address;
    }

    public void setAddress(Map<String, String> address) {
        this.address = address;
    }

    public Map<String, Double> getLocation() {
        return location;
    }

    public void setLocation(Map<String, Double> location) {
        this.location = location;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public List<WorkHours> getWorkhours() {
        return workhours;
    }

    public void setWorkhours(List<WorkHours> workhours) {
        this.workhours = workhours;
    }
}
