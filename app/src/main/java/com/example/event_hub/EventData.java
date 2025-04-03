package com.example.event_hub;

public class EventData {
    public int imageResId,headerResId;
    public String title, description,date,location;
    public double price;

    public EventData(int imageResId, int headerResId, String title, String description, String date, String location, double price) {
        this.imageResId = imageResId;
        this.headerResId = headerResId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.price = price;
    }
}
