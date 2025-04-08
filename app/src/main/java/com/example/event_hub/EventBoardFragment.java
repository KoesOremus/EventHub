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

        // Load full event list from repository
        List<Event> fullEventList = EventRepository.getEvents();

        // Initialize adapter with click listener
        eventAdapter = new EventAdapter(fullEventList, event -> {
            // Find the full event from repository based on title
            Event fullEvent = null;
            for (Event e : EventRepository.getEvents()) {
                if (e.getTitle().equals(event.getTitle())) {
                    fullEvent = e;
                    break;
                }
            }

            if (fullEvent == null) return; // safety check

            // prepare bundle with correct keys
            Bundle bundle = new Bundle();
            bundle.putInt("imageResId", fullEvent.getImageResource());
            bundle.putInt("headerResId", fullEvent.getHeaderResId());
            bundle.putString("title", fullEvent.getTitle());
            bundle.putString("description", fullEvent.getDescription());
            bundle.putString("date", fullEvent.getDate());
            bundle.putString("location", fullEvent.getLocation());
            bundle.putDouble("price", fullEvent.getPrice());

            // Pass to EventDetailsFragment
            EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
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