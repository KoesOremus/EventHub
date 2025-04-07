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

        // find UI elements
        imageHeader = view.findViewById(R.id.headerKhushi);
        titleText = view.findViewById(R.id.titleKhushi);
        descriptionText = view.findViewById(R.id.descriptionsKhushi);
        dateText = view.findViewById(R.id.textDateKhushi);
        locationText = view.findViewById(R.id.textLocationKhushi);
        priceText = view.findViewById(R.id.textTicketKhushi);

        // get event data from previous fragment
        Bundle args = getArguments();
        if (args != null) {
            imageHeader.setImageResource(args.getInt("headerResId"));
            titleText.setText(args.getString("title"));
            descriptionText.setText(args.getString("description"));
            dateText.setText(" " + args.getString("date"));
            locationText.setText(" " + args.getString("location"));
            priceText.setText(" Price: $" + String.format("%.2f", args.getDouble("price")));
        }

        // handle checkout button click
        Button checkoutBtn = view.findViewById(R.id.descriptionsButtonKhushi);
        checkoutBtn.setOnClickListener(v -> {
            // create and save selected event
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

            // go to ticket page using method from main activity
            MainActivity.navigateToCartFromDetails(requireActivity());
        });
    }
}
