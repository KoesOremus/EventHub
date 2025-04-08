package com.example.event_hub;

import android.os.Bundle;

public class CartManager {

    // checks if cart has anything or not
    private static boolean isCartEmpty = true;
    private static double totalAmount;
    private static int number;

    // stores the selected event
    private static Event selectedEvent;

    // stores the index of the event in the swipe list
    private static int selectedEventIndex = -1;

    // used in nav bar to decide whether to show toast or not
    public static boolean isCartEmpty() {
        return isCartEmpty;
    }

    // saves selected event when checkout happens
    public static void setSelectedEvent(Event event) {
        selectedEvent = event;
    }

    // used to get the event from anywhere
    public static Event getSelectedEvent() {
        return selectedEvent;
    }

    // manually updates cart state
    public static void setCartEmpty(boolean empty) {
        isCartEmpty = empty;
    }

    // resets all cart values
    public static void clear() {
        selectedEvent = null;
        selectedEventIndex = -1;
        isCartEmpty = true;
    }

    // saves the index of the event when swiped
    public static void setSelectedEventIndex(int index) {
        selectedEventIndex = index;
    }

    // used to go back to the same index when needed
    public static int getSelectedEventIndex() {
        return selectedEventIndex;
    }

    public static void setTotal(double total) {
        totalAmount = total;
    }

    public static double getTotal() {
        return totalAmount;
    }

    public static void setNumber(int totalNumber) {
        number = totalNumber;
    }

    public static int getNumber() {
        return number;
    }

    // makes a bundle with all event info (to pass across screens)
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
