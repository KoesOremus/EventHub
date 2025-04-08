package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TicketManagementFragment extends Fragment {

    // UI components
    private ImageView headerImage;
    private TextView titleText, ticketCountText, subtotalText, taxText, totalText;
    private LinearLayout attendeeContainer;

    // logic variables
    private int ticketCount = 1;
    private double ticketPrice = 0.0;
    private Event selectedEvent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // load the screen layout
        View view = inflater.inflate(R.layout.fragment_ticket_management, container, false);

        // connect xml ui to java code
        headerImage = view.findViewById(R.id.headerKhushi2);
        titleText = view.findViewById(R.id.titleKhushi2);
        ticketCountText = view.findViewById(R.id.ticketCount);
        subtotalText = view.findViewById(R.id.subtotalPriceKhushi);
        taxText = view.findViewById(R.id.feesTaxPriceKhushi);
        totalText = view.findViewById(R.id.totalPriceKhushi);
        attendeeContainer = view.findViewById(R.id.attendeeContainer);

        ImageView plusBtn = view.findViewById(R.id.plusBtn);
        ImageView minusBtn = view.findViewById(R.id.minusBtn);

        // get event details from cart
        selectedEvent = CartManager.getSelectedEvent();

        // show event details on screen
        if (selectedEvent != null) {
            headerImage.setImageResource(selectedEvent.getHeaderResId());
            titleText.setText(selectedEvent.getTitle());
            ticketPrice = selectedEvent.getPrice();
        }

        // show first attendee input block
        addAttendeeBlock();

        // increase ticket count when plus is clicked
        plusBtn.setOnClickListener(v -> {
            ticketCount++;
            ticketCountText.setText(String.valueOf(ticketCount));
            addAttendeeBlock();
            updatePrices();
        });

        // decrease ticket count when minus is clicked
        minusBtn.setOnClickListener(v -> {
            if (ticketCount == 1) {
                // if only one ticket left, clear cart and go back to event page
                CartManager.clear();
                Toast.makeText(requireContext(), "Your cart is now empty", Toast.LENGTH_SHORT).show();

                if (selectedEvent != null) {
                    EventDetailsFragment detailsFragment = new EventDetailsFragment();

                    // send the same event info back to event description
                    Bundle bundle = new Bundle();
                    bundle.putInt("imageResId", selectedEvent.getImageResource());
                    bundle.putInt("headerResId", selectedEvent.getHeaderResId());
                    bundle.putString("title", selectedEvent.getTitle());
                    bundle.putString("description", selectedEvent.getDescription());
                    bundle.putString("date", selectedEvent.getDate());
                    bundle.putString("location", selectedEvent.getLocation());
                    bundle.putDouble("price", selectedEvent.getPrice());
                    detailsFragment.setArguments(bundle);

                    // go back to event details screen
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, detailsFragment)
                            .commit();
                }
            } else {
                // if more than one ticket, just reduce the count
                ticketCount--;
                ticketCountText.setText(String.valueOf(ticketCount));
                removeLastAttendeeBlock();
                updatePrices();
            }
        });

        // show prices for subtotal, tax, total
        updatePrices();

        // move to payment page when "buy now" is clicked
        Button nextButton = view.findViewById(R.id.descriptionsButtonKhushi2);
        nextButton.setOnClickListener(v -> {
            PaymentFragment paymentFragment = new PaymentFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, paymentFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    // add new name/email/phone fields for each attendee
    private void addAttendeeBlock() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View attendeeView = inflater.inflate(R.layout.attendees, attendeeContainer, false);

        TextView attendeeLabel = attendeeView.findViewById(R.id.textAttendees);
        attendeeLabel.setText("Attendee " + ticketCount);

        attendeeContainer.addView(attendeeView);
    }

    // remove the last attendee input fields
    private void removeLastAttendeeBlock() {
        if (attendeeContainer.getChildCount() > 0) {
            attendeeContainer.removeViewAt(attendeeContainer.getChildCount() - 1);
        }
    }

    // calculate and show updated prices
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
