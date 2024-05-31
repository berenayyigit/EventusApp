package com.example.eventusapp;
import java.io.Serializable;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String intro;

    private Organization org;
    private Location loc;
    private EventDate date;

    // Default constructor
    public Event(String name, String intro, String org, String loc, String date) {
        // No-argument constructor required for serialization
    }

    // Parameterized constructor
    public Event(String name, String intro, Organization org, Location loc, EventDate date) {
        this.name = name;
        this.intro = intro;
        this.org = org;
        this.loc = loc;
        this.date = date;
    }

    // Getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }
    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Organization getOrg() {
        return org;
    }
    public void setOrg(Organization org) {
        this.org = org;
    }

    public Location getLoc() {
        return loc;
    }
    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public EventDate getDate() {
        return date;
    }
    public void setDate(EventDate date) {
        this.date = date;
    }
}