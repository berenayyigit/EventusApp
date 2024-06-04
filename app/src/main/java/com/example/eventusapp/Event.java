package com.example.eventusapp;

import java.io.Serializable;

public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String intro;
    private String org;
    private String loc;
    private String eventDate;
    private String eventTime;
    //private String imageUrl;

    // Default constructor
    public Event() {
        // No-argument constructor required for serialization
    }

    // Parameterized constructor
    public Event(String id, String name, String intro, String org, String loc, String eventDate, String eventTime /*,String imageUrl*/) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.org = org;
        this.loc = loc;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        //this.imageUrl = imageUrl;
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

    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime;}
    //public String getImageUrl() { return imageUrl; }
    //public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

}