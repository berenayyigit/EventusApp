package com.example.eventusapp;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Token implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;
    private LocalDateTime timeOut;

    // Default constructor
    public Token() {
        // No-argument constructor required for serialization
    }

    // Parameterized constructor
    public Token(String token, LocalDateTime timeOut) {
        this.token = token;
        this.timeOut = timeOut;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(LocalDateTime timeOut) {
        this.timeOut = timeOut;
    }
}

