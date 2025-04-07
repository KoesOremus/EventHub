package com.example.event_hub;

public class Event {
    private String title;
    private String location;
    private int imageResource;

    private String description;



    public Event(String title, String location, int imageResource, String description) {
        this.title = title;
        this.location = location;
        this.imageResource = imageResource;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public int getImageResource() {
        return imageResource;
    }
}
