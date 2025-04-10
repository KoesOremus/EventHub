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

    // ui elements on the screen
    private ImageView headerImage;
    private TextView titleText, ticketCountText, subtotalText, taxText, totalText;
    private LinearLayout attendeeContainer;

    // variables to handle ticket logic
    private int ticketCount = 1;
    private double ticketPrice = 0.0;
    private Event selectedEvent;

    // this runs when the screen is created
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // inflates the layout from xml file
        View view = inflater.inflate(R.layout.fragment_ticket_management, container, false);

        // connecting ui elements with variables
        headerImage = view.findViewById(R.id.headerKhushi2);
        titleText = view.findViewById(R.id.titleKhushi2);
        ticketCountText = view.findViewById(R.id.ticketCount);
        subtotalText = view.findViewById(R.id.subtotalPriceKhushi);
        taxText = view.findViewById(R.id.feesTaxPriceKhushi);
        totalText = view.findViewById(R.id.totalPriceKhushi);
        attendeeContainer = view.findViewById(R.id.attendeeContainer);

        // buttons for changing ticket count
        ImageView plusBtn = view.findViewById(R.id.plusBtn);
        ImageView minusBtn = view.findViewById(R.id.minusBtn);

        // get the selected event from the cart
        selectedEvent = CartManager.getSelectedEvent();
        ticketCount = CartManager.getTicketCount(); // get saved ticket count

        // if there is a selected event, show its info
        if (selectedEvent != null) {
            headerImage.setImageResource(selectedEvent.getHeaderResId());
            titleText.setText(selectedEvent.getTitle());
            ticketPrice = selectedEvent.getPrice();
        }

        // restore saved number of attendee blocks
        restoreAttendees();

        // when plus is clicked, add one more ticket and attendee input
        plusBtn.setOnClickListener(v -> {
            ticketCount++;
            CartManager.setTicketCount(ticketCount);
            ticketCountText.setText(String.valueOf(ticketCount));
            addAttendeeBlock();
            updatePrices();
        });

        // when minus is clicked, remove a ticket or go back to event page
        minusBtn.setOnClickListener(v -> {
            if (ticketCount == 1) {
                CartManager.clear();
                Toast.makeText(requireContext(), "Your cart is now empty", Toast.LENGTH_SHORT).show();

                // go back to the event description page
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
                CartManager.setTicketCount(ticketCount);
                ticketCountText.setText(String.valueOf(ticketCount));
                removeLastAttendeeBlock();
                updatePrices();
            }
        });

        // when buy now button is clicked
        Button nextButton = view.findViewById(R.id.descriptionsButtonKhushi2);
        nextButton.setOnClickListener(v -> {
            if (validateAllAttendees()) {
                // go to the payment screen if all inputs are valid
                PaymentFragment paymentFragment = new PaymentFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, paymentFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        // show price values based on current ticket count
        updatePrices();
        return view;
    }

    // adds attendee input blocks based on saved ticket count
    private void restoreAttendees() {
        ticketCountText.setText(String.valueOf(ticketCount));
        attendeeContainer.removeAllViews(); // clear if reloaded
        for (int i = 0; i < ticketCount; i++) {
            addAttendeeBlock();
        }
    }

    // adds a new attendee block with phone and email fields
    private void addAttendeeBlock() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View attendeeView = inflater.inflate(R.layout.attendees, attendeeContainer, false);

        TextView attendeeLabel = attendeeView.findViewById(R.id.textAttendees);
        attendeeLabel.setText("Attendee " + (attendeeContainer.getChildCount() + 1));

        EditText phoneField = attendeeView.findViewById(R.id.editPhoneKhushi);
        EditText emailField = attendeeView.findViewById(R.id.editEmailKhushi);

        // make sure phone only accepts digits and max 10 digits
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

    // checks if all fields (name, email, phone) are valid
    private boolean validateAllAttendees() {
        for (int i = 0; i < attendeeContainer.getChildCount(); i++) {
            View attendeeView = attendeeContainer.getChildAt(i);

            EditText name = attendeeView.findViewById(R.id.editNameKhushi);
            EditText email = attendeeView.findViewById(R.id.editEmailKhushi);
            EditText phone = attendeeView.findViewById(R.id.editPhoneKhushi);

            String nameText = name.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String phoneText = phone.getText().toString().trim();

            // check if name is empty
            if (nameText.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
                return false;
            }

            // check if email is valid
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(requireContext(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return false;
            }

            // check if phone has exactly 10 digits
            if (phoneText.length() != 10) {
                Toast.makeText(requireContext(), "Phone must have 10 digits", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    // removes the last added attendee block
    private void removeLastAttendeeBlock() {
        if (attendeeContainer.getChildCount() > 0) {
            attendeeContainer.removeViewAt(attendeeContainer.getChildCount() - 1);
        }
    }

    // calculates subtotal, tax and total price
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
