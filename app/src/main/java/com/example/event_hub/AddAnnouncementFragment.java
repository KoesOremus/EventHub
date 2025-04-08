package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddAnnouncementFragment extends Fragment {

    private EditText editEventName;
    private EditText editEventLocation;
    private EditText editEventDate;
    private EditText editEventTime;
    private EditText editEventDescription;
    private Button buttonSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_announcement, container, false);


        editEventName = view.findViewById(R.id.edit_event_name);
        editEventLocation = view.findViewById(R.id.edit_event_location);
        editEventDate = view.findViewById(R.id.edit_event_date);
        editEventTime = view.findViewById(R.id.edit_event_time);
        editEventDescription = view.findViewById(R.id.edit_event_description);
        buttonSubmit = view.findViewById(R.id.button_submit_announcement);

        buttonSubmit.setOnClickListener(v -> {
            if (validateForm()) {
                submitAnnouncement();
            }
        });

        return view;
    }

    private boolean validateForm() {
        boolean valid = true;

        if (editEventName.getText().toString().trim().isEmpty()) {
            editEventName.setError("Event name is required");
            valid = false;
        }

        if (editEventLocation.getText().toString().trim().isEmpty()) {
            editEventLocation.setError("Event location is required");
            valid = false;
        }

        if (editEventDate.getText().toString().trim().isEmpty()) {
            editEventDate.setError("Event date is required");
            valid = false;
        }

        return valid;
    }

    private void submitAnnouncement() {
        Toast.makeText(getContext(), "Community event announcement submitted!", Toast.LENGTH_SHORT).show();

        getParentFragmentManager().popBackStack();
    }
}

