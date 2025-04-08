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


        if (getArguments() != null) {
            selectedEvent = getArguments().getParcelable("selected_event");
        }


        if (selectedEvent == null) {
            selectedEvent = CartManager.getSelectedEvent();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);


        cardNumberEditText = view.findViewById(R.id.edit_card_number);
        cardNameEditText = view.findViewById(R.id.edit_card_name);
        expiryEditText = view.findViewById(R.id.edit_expiry);
        cvvEditText = view.findViewById(R.id.edit_cvv);
        addressEditText = view.findViewById(R.id.edit_address);
        cityEditText = view.findViewById(R.id.edit_city);


        eventNameTextView = view.findViewById(R.id.text_event_name);
        totalPriceTextView = view.findViewById(R.id.text_total);


        if (selectedEvent != null) {
            updateEventDetails();
        } else {

            Toast.makeText(getContext(), "No event selected", Toast.LENGTH_SHORT).show();

            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            }
        }


        Button payButton = view.findViewById(R.id.button_pay_now);
        payButton.setOnClickListener(v -> processPayment());

        return view;
    }

    private void updateEventDetails() {
        if (selectedEvent != null) {

            eventNameTextView.setText(String.format("%s - %d ticket(s)",
                    selectedEvent.getTitle(), ticketQuantity));


            double totalPrice = selectedEvent.getPrice() * ticketQuantity;
            totalPriceTextView.setText(String.format("Total: $%.2f", totalPrice));
        }
    }

    private void processPayment() {

        if (selectedEvent == null) {
            Toast.makeText(getContext(), "No event selected", Toast.LENGTH_SHORT).show();
            return;
        }

        if (validatePaymentDetails()) {

            Ticket ticket = generateTicket();


            FeedbackFragment feedbackFragment = new FeedbackFragment();


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

        if (selectedEvent == null) {
            throw new IllegalStateException("No event selected");
        }


        Ticket ticket = new Ticket();
        ticket.setEventName(selectedEvent.getTitle());
        ticket.setQuantity(ticketQuantity);


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