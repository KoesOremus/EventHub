package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TicketPurchaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_purchase, container, false);

        // Handle next button click
        Button nextButton = view.findViewById(R.id.button_next);
        nextButton.setOnClickListener(v -> {
            PaymentFragment paymentFragment = new PaymentFragment();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, paymentFragment)
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}
