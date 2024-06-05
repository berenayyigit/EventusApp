package com.example.eventusapp;

public class Org {
    private String name;
    public Org() {
        // No-argument constructor required for serialization
    }
    public Org(String name) {
        // No-argument constructor required for serialization

        this.name = name;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
