package com.example.event_hub;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
    private List<Event> fullEventList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_board, container, false);

        EditText searchBar = view.findViewById(R.id.searchBar);
        recyclerView = view.findViewById(R.id.recycler_view_events);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));



        List<Event> fullEventList = EventRepository.getEvents();

        // Load full event list from repository
        fullEventList = EventRepository.getEvents();



        eventAdapter = new EventAdapter(fullEventList, event -> {

            Event fullEvent = null;
            for (Event e : EventRepository.getEvents()) {
                if (e.getTitle().equals(event.getTitle())) {
                    fullEvent = e;
                    break;
                }
            }

            if (fullEvent == null) return;

            Bundle bundle = new Bundle();
            bundle.putInt("imageResId", fullEvent.getImageResource());
            bundle.putInt("headerResId", fullEvent.getHeaderResId());
            bundle.putString("title", fullEvent.getTitle());
            bundle.putString("description", fullEvent.getDescription());
            bundle.putString("date", fullEvent.getDate());
            bundle.putString("location", fullEvent.getLocation());
            bundle.putDouble("price", fullEvent.getPrice());


            EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();
            eventDetailsFragment.setArguments(bundle);

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, eventDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        recyclerView.setAdapter(eventAdapter);

        // Setup search filter
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase();
                List<Event> filteredList = new ArrayList<>();

                for (Event event : fullEventList) {
                    if (event.getTitle().toLowerCase().contains(query)) {
                        filteredList.add(event);
                    }
                }

                eventAdapter.updateList(filteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }
}
