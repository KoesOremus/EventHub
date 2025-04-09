package com.example.event_hub;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TicketManagementFragment extends Fragment {

    // ui elements
    private ImageView headerImage;
    private TextView titleText, ticketCountText, subtotalText, taxText, totalText;
    private LinearLayout attendeeContainer;

    // variables to manage tickets
    private int ticketCount = 1;
    private double ticketPrice = 0.0;
    private Event selectedEvent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // loading the screen layout
        View view = inflater.inflate(R.layout.fragment_ticket_management, container, false);

        // connecting ui elements with java
        headerImage = view.findViewById(R.id.headerKhushi2);
        titleText = view.findViewById(R.id.titleKhushi2);
        ticketCountText = view.findViewById(R.id.ticketCount);
        subtotalText = view.findViewById(R.id.subtotalPriceKhushi);
        taxText = view.findViewById(R.id.feesTaxPriceKhushi);
        totalText = view.findViewById(R.id.totalPriceKhushi);
        attendeeContainer = view.findViewById(R.id.attendeeContainer);

        // plus and minus button for ticket count
        ImageView plusBtn = view.findViewById(R.id.plusBtn);
        ImageView minusBtn = view.findViewById(R.id.minusBtn);

        // getting event details from cart
        selectedEvent = CartManager.getSelectedEvent();
        if (selectedEvent != null) {
            headerImage.setImageResource(selectedEvent.getHeaderResId());
            titleText.setText(selectedEvent.getTitle());
            ticketPrice = selectedEvent.getPrice();
        }

        // show the first attendee input
        addAttendeeBlock();

        // when plus is clicked add ticket and attendee input
        plusBtn.setOnClickListener(v -> {
            ticketCount++;
            ticketCountText.setText(String.valueOf(ticketCount));
            addAttendeeBlock();
            updatePrices();
        });

        // when minus is clicked remove ticket or go back if only 1
        minusBtn.setOnClickListener(v -> {
            if (ticketCount == 1) {
                CartManager.clear();
                Toast.makeText(requireContext(), "your cart is now empty", Toast.LENGTH_SHORT).show();

                // go back to event details page
                if (selectedEvent != null) {
                    EventDetailsFragment detailsFragment = new EventDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("imageResId", selectedEvent.getImageResource());
                    bundle.putInt("headerResId", selectedEvent.getHeaderResId());
                    bundle.putString("title", selectedEvent.getTitle());
                    bundle.putString("description", selectedEvent.getDescription());
                    bundle.putString("date", selectedEvent.getDate());
                    bundle.putString("location", selectedEvent.getLocation());
                    bundle.putDouble("price", selectedEvent.getPrice());
                    detailsFragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, detailsFragment)
                            .commit();
                }
            } else {
                ticketCount--;
                ticketCountText.setText(String.valueOf(ticketCount));
                removeLastAttendeeBlock();
                updatePrices();
            }
        });

        // show price values
        updatePrices();

        // when buy now is clicked move to payment if validation passes
        Button nextButton = view.findViewById(R.id.descriptionsButtonKhushi2);
        nextButton.setOnClickListener(v -> {
            if (validateAllAttendees()) {
                PaymentFragment paymentFragment = new PaymentFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, paymentFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    // this adds one attendee block with name, email and phone input
    private void addAttendeeBlock() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View attendeeView = inflater.inflate(R.layout.attendees, attendeeContainer, false);

        TextView attendeeLabel = attendeeView.findViewById(R.id.textAttendees);
        attendeeLabel.setText("Attendee " + ticketCount);

        EditText phoneField = attendeeView.findViewById(R.id.editPhoneKhushi);
        EditText emailField = attendeeView.findViewById(R.id.editEmailKhushi);

        // phone field accepts only numbers and max 10 digits
        phoneField.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        phoneField.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(10),
                (source, start, end, dest, dstart, dend) -> {
                    for (int i = start; i < end; i++) {
                        if (!Character.isDigit(source.charAt(i))) {
                            return "";
                        }
                    }
                    return null;
                }
        });

        attendeeContainer.addView(attendeeView);
    }

    // this checks if name, email, phone are valid for each attendee
    private boolean validateAllAttendees() {
        for (int i = 0; i < attendeeContainer.getChildCount(); i++) {
            View attendeeView = attendeeContainer.getChildAt(i);

            EditText name = attendeeView.findViewById(R.id.editNameKhushi);
            EditText email = attendeeView.findViewById(R.id.editEmailKhushi);
            EditText phone = attendeeView.findViewById(R.id.editPhoneKhushi);

            String nameText = name.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String phoneText = phone.getText().toString().trim();

            // if name is empty show toast and stop
            if (nameText.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
                return false;
            }

            // if email is not valid show toast and stop
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return false;
            }

            // if phone is not 10 digits show toast and stop
            if (phoneText.length() != 10) {
                Toast.makeText(requireContext(), "Phone must have 10 digits", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    // this removes the last attendee input block
    private void removeLastAttendeeBlock() {
        if (attendeeContainer.getChildCount() > 0) {
            attendeeContainer.removeViewAt(attendeeContainer.getChildCount() - 1);
        }
    }

    // this calculates subtotal, tax and total
    private void updatePrices() {
        double subtotal = ticketPrice * ticketCount;
        double tax = subtotal * 0.12;
        double total = subtotal + tax;

        CartManager.setTotal(total);
        CartManager.setNumber(ticketCount);

        subtotalText.setText(String.format("$%.2f", subtotal));
        taxText.setText(String.format("$%.2f", tax));
        totalText.setText(String.format("$%.2f", total));
    }
}
