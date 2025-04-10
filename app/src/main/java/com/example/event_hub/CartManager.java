package com.example.event_hub;

import android.os.Bundle;

public class CartManager {

    // keeps track of whether the cart is empty
    private static boolean isCartEmpty = true;

    // total price of items in the cart
    private static double totalAmount;

    // number of tickets
    private static int number;

    // the selected event for checkout
    private static Event selectedEvent;

    // index of the selected event (used for swiping or going back)
    private static int selectedEventIndex = -1;

    // ticket count saved for persistence
    private static int ticketCount = 1;

    // checks if cart is empty
    public static boolean isCartEmpty() {
        return isCartEmpty;
    }

    // saves the selected event when user checks out
    public static void setSelectedEvent(Event event) {
        selectedEvent = event;
    }

    // returns the event stored in the cart
    public static Event getSelectedEvent() {
        return selectedEvent;
    }

    // updates cart status manually (true or false)
    public static void setCartEmpty(boolean empty) {
        isCartEmpty = empty;
    }

    // clears everything in the cart
    public static void clear() {
        selectedEvent = null;
        selectedEventIndex = -1;
        isCartEmpty = true;
        ticketCount = 1;
    }

    // saves the index of event when swiped
    public static void setSelectedEventIndex(int index) {
        selectedEventIndex = index;
    }

    // gets the index of swiped event
    public static int getSelectedEventIndex() {
        return selectedEventIndex;
    }

    // saves the total price
    public static void setTotal(double total) {
        totalAmount = total;
    }

    // returns the saved total price
    public static double getTotal() {
        return totalAmount;
    }

    // saves number of tickets (can be used in payment)
    public static void setNumber(int totalNumber) {
        number = totalNumber;
    }

    // gets the saved number of tickets
    public static int getNumber() {
        return number;
    }

    // sets the current ticket count so we can restore it later
    public static void setTicketCount(int count) {
        ticketCount = count;
    }

    // gets the saved ticket count for restoring attendee blocks
    public static int getTicketCount() {
        return ticketCount;
    }

    // builds a bundle with all event data to pass between screens
    public static Bundle buildBundleFromEvent() {
        Bundle bundle = new Bundle();
        if (selectedEvent != null) {
            bundle.putInt("imageResId", selectedEvent.getImageResource());
            bundle.putInt("headerResId", selectedEvent.getHeaderResId());
            bundle.putString("title", selectedEvent.getTitle());
            bundle.putString("description", selectedEvent.getDescription());
            bundle.putString("date", selectedEvent.getDate());
            bundle.putString("location", selectedEvent.getLocation());
            bundle.putDouble("price", selectedEvent.getPrice());
        }
        return bundle;
    }
}
