package com.example.eventusapp;

import java.io.Serializable;

public class EventPayload implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orgid;
    private String name;
    private String intro;
    private Location loc;
    private EventDate date;

    // Default constructor
    public EventPayload() {
        // No-argument constructor required for serialization
    }

    // Parameterized constructor
    public EventPayload(String orgid, String name, String intro, Location loc, EventDate date) {
        this.orgid = orgid;
        this.name = name;
        this.intro = intro;
        this.loc = loc;
        this.date = date;
    }

    // Getters and setters
    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
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

