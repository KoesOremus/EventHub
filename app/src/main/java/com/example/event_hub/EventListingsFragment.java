package com.example.event_hub;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class EventListingsFragment extends Fragment {

    private ImageView posterImage;
    private List<Event> eventList;
    private int currentIndex = 0;
    private GestureDetector gestureDetector;
    private float scrollStartX;
    private boolean swipeHandled = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_listings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // load the poster image view
        posterImage = view.findViewById(R.id.posterTaylorSwift);

        // get all events
        eventList = EventRepository.getEvents();

        // get the saved index if user comes back from cart
        int savedIndex = CartManager.getSelectedEventIndex();
        if (savedIndex >= 0 && savedIndex < eventList.size()) {
            currentIndex = savedIndex;
        }

        // show the current event
        showEvent(currentIndex);

        // detect swipe gestures
        gestureDetector = new GestureDetector(requireContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                scrollStartX = e.getX();
                swipeHandled = false;
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float diffX = e2.getX() - scrollStartX;

                // make sure only one swipe is handled per scroll
                if (!swipeHandled && Math.abs(diffX) > 150) {
                    if (diffX > 0) {
                        // swipe right = open description
                        openDescription();
                    } else {
                        // swipe left = next poster
                        showNextPoster();
                    }
                    swipeHandled = true;
                    return true;
                }
                return false;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                swipeHandled = false;
                return super.onSingleTapUp(e);
            }
        });

        // handle touch on full view
        view.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                swipeHandled = false;
            }
            return gestureDetector.onTouchEvent(event);
        });
    }

    // show poster of selected event
    private void showEvent(int index) {
        if (index >= 0 && index < eventList.size()) {
            Event event = eventList.get(index);
            posterImage.setImageResource(event.getImageResource());
        }
    }

    // show next event poster
    private void showNextPoster() {
        currentIndex = (currentIndex + 1) % eventList.size();
        showEvent(currentIndex);
    }

    // open event description page for current event
    private void openDescription() {
        Event event = eventList.get(currentIndex);

        // save this index for cart return
        CartManager.setSelectedEventIndex(currentIndex);

        EventDetailsFragment detailsFragment = new EventDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("imageResId", event.getImageResource());
        bundle.putInt("headerResId", event.getHeaderResId());
        bundle.putString("title", event.getTitle());
        bundle.putString("description", event.getDescription());
        bundle.putString("date", event.getDate());
        bundle.putString("location", event.getLocation());
        bundle.putDouble("price", event.getPrice());
        detailsFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
