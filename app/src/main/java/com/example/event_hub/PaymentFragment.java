package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // Handle payment button click
        Button payButton = view.findViewById(R.id.button_pay_now);
        payButton.setOnClickListener(v -> {
            FeedbackFragment feedbackFragment = new FeedbackFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, feedbackFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
