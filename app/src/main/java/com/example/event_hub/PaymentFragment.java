package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentFragment extends Fragment {

    private EditText cardNumberEditText;
    private EditText cardNameEditText;
    private EditText expiryEditText;
    private EditText cvvEditText;
    private EditText addressEditText;
    private EditText cityEditText;
    private TextView eventNameTextView;
    private TextView totalPriceTextView;

    private Event selectedEvent;
    private int ticketQuantity = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Try to get selected event from arguments first
        if (getArguments() != null) {
            selectedEvent = getArguments().getParcelable("selected_event");
        }

        // If not in arguments, try to get from CartManager
        if (selectedEvent == null) {
            selectedEvent = CartManager.getSelectedEvent();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // Initialize EditText fields
        cardNumberEditText = view.findViewById(R.id.edit_card_number);
        cardNameEditText = view.findViewById(R.id.edit_card_name);
        expiryEditText = view.findViewById(R.id.edit_expiry);
        cvvEditText = view.findViewById(R.id.edit_cvv);
        addressEditText = view.findViewById(R.id.edit_address);
        cityEditText = view.findViewById(R.id.edit_city);

        // Initialize event details views
        eventNameTextView = view.findViewById(R.id.text_event_name);
        totalPriceTextView = view.findViewById(R.id.text_total);

        // Set event details if an event was selected
        if (selectedEvent != null) {
            updateEventDetails();
        } else {
            // Handle case where no event is selected
            Toast.makeText(getContext(), "No event selected", Toast.LENGTH_SHORT).show();
            // Optionally, navigate back or handle the error
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            }
        }

        // Handle payment button click
        Button payButton = view.findViewById(R.id.button_pay_now);
        payButton.setOnClickListener(v -> processPayment());

        return view;
    }

    private void updateEventDetails() {
        if (selectedEvent != null) {
            // Update event name
            eventNameTextView.setText(String.format("%s - %d ticket(s)",
                    selectedEvent.getTitle(), ticketQuantity));

            // Calculate and update total price
            double totalPrice = selectedEvent.getPrice() * ticketQuantity;
            totalPriceTextView.setText(String.format("Total: $%.2f", totalPrice));
        }
    }

    private void processPayment() {
        // Validate input fields
        if (selectedEvent == null) {
            Toast.makeText(getContext(), "No event selected", Toast.LENGTH_SHORT).show();
            return;
        }

        if (validatePaymentDetails()) {
            // Generate ticket
            Ticket ticket = generateTicket();

            // Navigate to Feedback Fragment
            FeedbackFragment feedbackFragment = new FeedbackFragment();

            // Pass ticket information to feedback fragment
            Bundle args = new Bundle();
            args.putParcelable("ticket", ticket);
            feedbackFragment.setArguments(args);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, feedbackFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private boolean validatePaymentDetails() {
        // Perform basic validation
        if (cardNumberEditText.getText().toString().trim().isEmpty()) {
            cardNumberEditText.setError("Card number is required");
            return false;
        }

        if (cardNameEditText.getText().toString().trim().isEmpty()) {
            cardNameEditText.setError("Name on card is required");
            return false;
        }

        if (expiryEditText.getText().toString().trim().isEmpty()) {
            expiryEditText.setError("Expiry date is required");
            return false;
        }

        if (cvvEditText.getText().toString().trim().isEmpty()) {
            cvvEditText.setError("CVV is required");
            return false;
        }

        if (addressEditText.getText().toString().trim().isEmpty()) {
            addressEditText.setError("Address is required");
            return false;
        }

        if (cityEditText.getText().toString().trim().isEmpty()) {
            cityEditText.setError("City is required");
            return false;
        }

        return true;
    }

    private Ticket generateTicket() {
        // Ensure an event is selected (additional safeguard)
        if (selectedEvent == null) {
            throw new IllegalStateException("No event selected");
        }

        // Create a new ticket with event details
        Ticket ticket = new Ticket();
        ticket.setEventName(selectedEvent.getTitle());
        ticket.setQuantity(ticketQuantity);

        // Calculate total price
        double totalPrice = selectedEvent.getPrice() * ticketQuantity;
        ticket.setPrice(totalPrice);

        ticket.setCustomerName(cardNameEditText.getText().toString());
        ticket.setBillingAddress(
                addressEditText.getText().toString() + ", " +
                        cityEditText.getText().toString()
        );

        return ticket;
    }
}