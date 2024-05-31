package com.example.eventusapp;

import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;

    private String loc;

    // Default constructor
    public Location() {
    }

    // Parameterized constructor
    public Location(String loc) {
        this.loc = loc;
    }

    // Getters and setters
    public String getLocation() {
        return loc;
    }

    public void setLocation(String loc) {
        this.loc = loc;
    }
}
