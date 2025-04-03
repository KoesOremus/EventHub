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

public class EventDetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        // Get arguments
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("event_title", "");
            String location = args.getString("event_location", "");
            int imageResource = args.getInt("event_image", R.drawable.event_placeholder);

            // Set event details
            TextView titleTextView = view.findViewById(R.id.text_event_title);
            TextView locationTextView = view.findViewById(R.id.text_event_location);
            ImageView imageView = view.findViewById(R.id.image_event);

            titleTextView.setText(title);
            locationTextView.setText(location);
            imageView.setImageResource(imageResource);
        }

        // Handle buy ticket button click
        Button buyTicketButton = view.findViewById(R.id.button_buy_ticket);
        buyTicketButton.setOnClickListener(v -> {
            TicketPurchaseFragment ticketPurchaseFragment = new TicketPurchaseFragment();
            if (args != null) {
                ticketPurchaseFragment.setArguments(args);
            }

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ticketPurchaseFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
