package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TicketManagementFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_management, container, false);

        // Handle ticket click to navigate to ticket details
        view.findViewById(R.id.card_ticket).setOnClickListener(v -> {
            TicketDetailsFragment ticketDetailsFragment = new TicketDetailsFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ticketDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
