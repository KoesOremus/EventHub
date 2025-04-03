package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EventListingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_listings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Handle event click to navigate to event details
        view.findViewById(R.id.card_squid_game).setOnClickListener(v -> {
            navigateToEventDetails("Squid Game Live", "New York, NY", R.drawable.event_squid);
        });
    }

    private void navigateToEventDetails(String title, String location, int imageResource) {
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("event_title", title);
        bundle.putString("event_location", location);
        bundle.putInt("event_image", imageResource);
        eventDetailsFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, eventDetailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
