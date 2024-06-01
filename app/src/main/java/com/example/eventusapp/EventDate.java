package com.example.eventusapp;

import java.io.Serializable;

public class EventDate implements Serializable {
    //private static final long serialVersionUID = 1L;

    private String year;
    private String month;
    private String day;
    private String hour;
    private String minute;
    private String time;

    // Default constructor
    public EventDate() {
        // No-argument constructor required for serialization
    }

    // Parameterized constructor
    public EventDate(String year, String month, String day, String hour, String minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.time = year + "-" + month + "-" + day + "-" + hour + "-" + minute;
    }

    // Getters and setters
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
