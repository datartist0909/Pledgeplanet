package com.example.pledgeplanet;

public class Challenge {
    private String name;
    private String description;
    private boolean completed;


    public Challenge(String name, String description) {
        this.name = name;
        this.description = description;
        this.completed = false;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}