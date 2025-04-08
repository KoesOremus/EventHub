package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedbackFragment extends Fragment {

    private Ticket ticket;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            ticket = getArguments().getParcelable("ticket");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);


        if (ticket != null) {
            TextView ticketDetailsTextView = view.findViewById(R.id.text_ticket_details);

            String ticketDetails = String.format(
                    "Event: %s\n" +
                            "Quantity: %d\n" +
                            "Price: $%.2f\n" +
                            "Customer: %s\n" +
                            "Billing Address: %s",
                    ticket.getEventName(),
                    ticket.getQuantity(),
                    ticket.getPrice(),
                    ticket.getCustomerName(),
                    ticket.getBillingAddress()
            );

            ticketDetailsTextView.setText(ticketDetails);
        }

        return view;
    }
}