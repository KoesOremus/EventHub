package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

        Event event = EventRepository.getEventByTitle(ticket.getEventName());
        if (event != null) {
            TextView eventFeedback = view.findViewById(R.id.text_event_details);
            ImageView eventHeader = view.findViewById(R.id.feedbackImage);

            eventFeedback.setText(event.getTitle()+"\n"+event.getDate()+"\n"+event.getLocation());
            eventHeader.setImageResource(event.getImageResource());
            }
        }
        Button submitFeedback = view.findViewById(R.id.button_submit_feedback);
        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        RatingBar ratingBar = view.findViewById(R.id.rating_bar);

        submitFeedback.setOnClickListener(v->{
            int selectedId = radioGroup.getCheckedRadioButtonId();
            float rating = ratingBar.getRating();
            if(rating == 0.0f){
                Toast.makeText(getActivity(), "Please set a rating for this event", Toast.LENGTH_SHORT).show();
            }else if(selectedId==-1){
                Toast.makeText(getActivity(), "Select a recommendation for this Event", Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(getActivity(), "Feedback and checkout Confirmed", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                EventBoardFragment eventBoardFragment = new EventBoardFragment();
                transaction.replace(R.id.fragment_container, eventBoardFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}