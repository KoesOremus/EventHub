package com.example.event_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaymentSystemFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_system, container, false);

        RadioGroup paymentMethodGroup = view.findViewById(R.id.radio_group_payment);
        Button saveButton = view.findViewById(R.id.button_save_payment);

        saveButton.setOnClickListener(v -> {
            int selectedId = paymentMethodGroup.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(getContext(), "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedRadioButton = view.findViewById(selectedId);
            String selectedPaymentMethod = selectedRadioButton.getText().toString();

            Toast.makeText(getContext(), selectedPaymentMethod + " set as default payment method", Toast.LENGTH_SHORT).show();

            getParentFragmentManager().popBackStack();
        });

        // reminder Setup add new payment method button
        Button addNewButton = view.findViewById(R.id.button_add_payment);
        addNewButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Add new payment method selected", Toast.LENGTH_SHORT).show();

        });

        return view;
    }
}
