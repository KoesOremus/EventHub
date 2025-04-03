package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class EventBoardFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_board, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Sample data for events
        List<Event> eventList = new ArrayList<>();
        eventList.add(new Event("Taylor Swift: The Eras Tour", "Boston, MA", R.drawable.event_taylor));
        eventList.add(new Event("Squid Game Live", "New York, NY", R.drawable.event_squid));
        // Add more events as needed

        eventAdapter = new EventAdapter(eventList, event -> {
            // Navigate to event details
            EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("event_title", event.getTitle());
            bundle.putString("event_location", event.getLocation());
            bundle.putInt("event_image", event.getImageResource());
            eventDetailsFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, eventDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(eventAdapter);

        return view;
    }
}
