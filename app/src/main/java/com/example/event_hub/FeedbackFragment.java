package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FeedbackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        RatingBar ratingBar = view.findViewById(R.id.rating_bar);
        Button submitButton = view.findViewById(R.id.button_submit);

        submitButton.setOnClickListener(v -> {
            // Handle rating submission
            float rating = ratingBar.getRating();
            // Save rating to database

            // Return to event board
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EventBoardFragment())
                    .commit();
        });

        return view;
    }
}
