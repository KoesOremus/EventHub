package com.example.event_hub;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap googleMap;
    private SearchView searchView;
    private String autoSearchLocation = null;
    private boolean isMapReady = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mapView = view.findViewById(R.id.mapView);
        searchView = view.findViewById(R.id.searchView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // observe shared viewmodel to get location passed from other fragments
        SharedLocationViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedLocationViewModel.class);
        viewModel.getLocation().observe(getViewLifecycleOwner(), location -> {
            if (location != null && !location.isEmpty()) {
                autoSearchLocation = location;
                if (isMapReady) {
                    searchView.setQuery(location, true); // triggers search
                }
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        isMapReady = true;

        // initialize map and set hybrid view
        MapsInitializer.initialize(requireContext());

        // expand search view and hide keyboard
        searchView.setIconified(false);
        searchView.clearFocus();

        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        }

        // handle manual search input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                locateAndMark(query.trim());
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // if location was passed from another screen, trigger it
        if (autoSearchLocation != null && !autoSearchLocation.isEmpty()) {
            mapView.postDelayed(() -> {
                if (isMapReady) {
                    searchView.setQuery(autoSearchLocation, true); // triggers search
                }
            }, 600); // delay to ensure map is ready
        }
    }

    // find location by name and place a marker
    private void locateAndMark(String locationName) {
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationName, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                // clear previous markers and add new one
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title(locationName));

                // animate camera to location
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            } else {
                // optional: toast for no results
                // Toast.makeText(getContext(), "no location found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("MapFragment", "geocoding failed: " + e.getMessage());
        }
    }

    // handle map lifecycle
    @Override public void onResume() { super.onResume(); if (mapView != null) mapView.onResume(); }
    @Override public void onPause() { super.onPause(); if (mapView != null) mapView.onPause(); }
    @Override public void onDestroy() { super.onDestroy(); if (mapView != null) mapView.onDestroy(); }
    @Override public void onLowMemory() { super.onLowMemory(); if (mapView != null) mapView.onLowMemory(); }
}
