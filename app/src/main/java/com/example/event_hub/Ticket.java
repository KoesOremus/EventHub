package com.example.event_hub;

import android.os.Parcel;
import android.os.Parcelable;

public class Ticket implements Parcelable {
    private String eventName;
    private int quantity;
    private double price;
    private String customerName;
    private String billingAddress;

    // Default constructor
    public Ticket() {}

    // Getters and Setters
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    // Parcelable implementation
    protected Ticket(Parcel in) {
        eventName = in.readString();
        quantity = in.readInt();
        price = in.readDouble();
        customerName = in.readString();
        billingAddress = in.readString();
    }

    public static final Creator<Ticket> CREATOR = new Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeInt(quantity);
        dest.writeDouble(price);
        dest.writeString(customerName);
        dest.writeString(billingAddress);
    }
}