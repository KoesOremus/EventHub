package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommunityBoardFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private Button addAnnouncementButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_board, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_community_events);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        addAnnouncementButton = view.findViewById(R.id.button_add_announcement);
        addAnnouncementButton.setOnClickListener(v -> {
            navigateToAddAnnouncement();
        });

        List<Event> eventList = getCommunityEvents();

        eventAdapter = new EventAdapter(eventList, event -> {
            navigateToEventDetails(event);
        });

        recyclerView.setAdapter(eventAdapter);

        return view;
    }

    private List<Event> getCommunityEvents() {
        List<Event> eventList = new ArrayList<>();
        eventList.add(new Event("Local Art Exhibition", "Community Center", R.drawable.event_placeholder));
        eventList.add(new Event("Farmers Market", "Downtown Plaza", R.drawable.event_placeholder));
        eventList.add(new Event("Charity Run", "City Park", R.drawable.event_placeholder));
        eventList.add(new Event("Book Club Meeting", "Public Library", R.drawable.event_placeholder));
        return eventList;
    }

    private void navigateToAddAnnouncement() {
        Toast.makeText(getContext(), "Add community announcement", Toast.LENGTH_SHORT).show();

        AddAnnouncementFragment addAnnouncementFragment = new AddAnnouncementFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, addAnnouncementFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToEventDetails(Event event) {
        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("event_title", event.getTitle());
        bundle.putString("event_location", event.getLocation());
        bundle.putInt("event_image", event.getImageResource());
        bundle.putBoolean("is_community_event", true); // Flag to indicate it's a community event
        eventDetailsFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, eventDetailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
