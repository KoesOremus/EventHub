package com.example.event_hub;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedLocationViewModel extends ViewModel {
    private final MutableLiveData<String> selectedLocation = new MutableLiveData<>();

    //getters and setters for location
    public void setLocation(String location) {
        selectedLocation.setValue(location);
    }

    public LiveData<String> getLocation() {
        return selectedLocation;
    }
}