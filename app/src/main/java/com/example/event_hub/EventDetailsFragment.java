package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EventDetailsFragment extends Fragment {

    private ImageView imageHeader;
    private TextView titleText, descriptionText, dateText, locationText, priceText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find ui elements
        imageHeader = view.findViewById(R.id.headerKhushi);
        titleText = view.findViewById(R.id.titleKhushi);
        descriptionText = view.findViewById(R.id.descriptionsKhushi);
        dateText = view.findViewById(R.id.textDateKhushi);
        locationText = view.findViewById(R.id.textLocationKhushi);
        priceText = view.findViewById(R.id.textTicketKhushi);

        // get event data from bundle
        Bundle args = getArguments();
        if (args != null) {
            imageHeader.setImageResource(args.getInt("headerResId"));
            titleText.setText(args.getString("title"));
            descriptionText.setText(args.getString("description"));
            dateText.setText(" " + args.getString("date"));
            locationText.setText(" " + args.getString("location"));
            priceText.setText(" Price: $" + String.format("%.2f", args.getDouble("price")));
        }

        // checkout button logic
        Button checkoutBtn = view.findViewById(R.id.descriptionsButtonKhushi);
        checkoutBtn.setOnClickListener(v -> {
            Event selectedEvent = new Event(
                    args.getInt("imageResId"),
                    args.getInt("headerResId"),
                    args.getString("title"),
                    args.getString("description"),
                    args.getString("date"),
                    args.getString("location"),
                    args.getDouble("price")
            );

            CartManager.setSelectedEvent(selectedEvent);
            CartManager.setCartEmpty(false);

            MainActivity.navigateToCartFromDetails(requireActivity());
        });

        // location click opens map with selected location
        locationText.setOnClickListener(v -> {
            String eventLocation = args.getString("location");

            // create map fragment and send location in bundle
            MapFragment mapFragment = new MapFragment();
            Bundle bundle = new Bundle();
            bundle.putString("location_name", eventLocation);
            mapFragment.setArguments(bundle);

            // navigate to map fragment
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mapFragment)
                    .addToBackStack(null)
                    .commit();

            // highlight third icon (map) in bottom nav
            BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
            bottomNav.setSelectedItemId(R.id.nav_maps);
        });

    }
}
