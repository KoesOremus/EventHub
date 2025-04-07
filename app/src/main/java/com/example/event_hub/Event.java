package com.example.event_hub;

public class Event {
    public int imageResource, headerResId;
    public String title, description, date, location;
    public double price;


    // constructor for basic card (event board)
    public Event(String title, String location, int imageResource) {
        this.title = title;
        this.location = location;
        this.imageResource = imageResource;
    }

    // full constructor used for event listings and details
    public Event(int imageResource, int headerResId, String title, String description, String date, String location, double price) {
        this.imageResource = imageResource;
        this.headerResId = headerResId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.price = price;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getHeaderResId() {
        return headerResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public double getPrice() {
        return price;
    }
}
