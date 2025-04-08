package com.example.event_hub;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private int posterResourceId;
    private int headerResourceId;
    private String title;
    private String description;
    private String date;
    private String location;
    private double price;

    // Full constructor
    public Event(int posterResourceId, int headerResourceId, String title,
                 String description, String date, String location, double price) {
        this.posterResourceId = posterResourceId;
        this.headerResourceId = headerResourceId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
        this.price = price;
    }

    // Community events constructor
    public Event(String title, String location, int imageResource) {
        this.title = title;
        this.location = location;
        this.posterResourceId = imageResource;
        this.headerResourceId = imageResource;
        this.description = "";
        this.date = "";
        this.price = 0.0;
    }

    // Getters
    public int getImageResource() {
        return posterResourceId;
    }

    public int getHeaderResId() {
        return headerResourceId;
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


    protected Event(Parcel in) {
        posterResourceId = in.readInt();
        headerResourceId = in.readInt();
        title = in.readString();
        description = in.readString();
        date = in.readString();
        location = in.readString();
        price = in.readDouble();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(posterResourceId);
        dest.writeInt(headerResourceId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(location);
        dest.writeDouble(price);
    }
}