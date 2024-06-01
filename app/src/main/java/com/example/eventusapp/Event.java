package com.example.eventusapp;
import java.io.Serializable;


public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String intro;

    private String org;
    private String loc;
    private EventDate date;
    private String eventTime;


    // Default constructor
    public Event() {
        // No-argument constructor required for serialization
    }

    // Parameterized constructor
    public Event(String name, String intro, String org, String loc, String eventTime) {

        this.name = name;
        this.intro = intro;
        this.org = org;
        this.loc = loc;
        this.eventTime = eventTime;


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

    public String getOrg() {
        return org;
    }
    public void setOrg(String org) {
        this.org = org;
    }

    public String getLoc() {
        return loc;
    }
    public void setLoc(String loc) {
        this.loc = loc;
    }

    public EventDate getDate() {
        return date;
    }
    public void setDate(EventDate date) {
        this.date = date;
    }


    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }
}