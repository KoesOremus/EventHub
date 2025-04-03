package com.example.event_hub;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        TextView editProfileOption = view.findViewById(R.id.text_edit_profile);
        TextView changePasswordOption = view.findViewById(R.id.text_change_password);
        TextView paymentMethodsOption = view.findViewById(R.id.text_payment_methods);
        TextView notificationsOption = view.findViewById(R.id.text_notifications);
        TextView languageOption = view.findViewById(R.id.text_language);
        TextView themeOption = view.findViewById(R.id.text_theme);
        Button logoutButton = view.findViewById(R.id.button_logout);

        // Set click listeners for each option
        editProfileOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit Profile selected", Toast.LENGTH_SHORT).show();
            // Navigation to profile editing screen would go here
        });

        changePasswordOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Change Password selected", Toast.LENGTH_SHORT).show();
            // Navigation to password change screen would go here
        });

        // Task 4: Integration of Digital Wallets/Payment System
        paymentMethodsOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Payment Methods selected", Toast.LENGTH_SHORT).show();
            navigateToPaymentSystem();
        });

        notificationsOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Notifications selected", Toast.LENGTH_SHORT).show();
            // Navigation to notifications settings would go here
        });

        // Additional options for language and theme
        languageOption.setOnClickListener(v -> {
            showLanguageSelectionDialog();
        });

        themeOption.setOnClickListener(v -> {
            showThemeSelectionDialog();
        });

        logoutButton.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Logging out...", Toast.LENGTH_SHORT).show();
            performLogout();
        });

        // Add a feedback/ratings button to access Task 5
        TextView feedbackOption = view.findViewById(R.id.text_feedback);
        if (feedbackOption != null) {
            feedbackOption.setOnClickListener(v -> {
                navigateToFeedbackSystem();
            });
        }

        return view;
    }

    private void navigateToPaymentSystem() {
        // Task 4: Integration of Digital Wallets/Payment System
        PaymentSystemFragment paymentSystemFragment = new PaymentSystemFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, paymentSystemFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToFeedbackSystem() {
        // Task 5: Feedback system (post event ratings)
        FeedbackListFragment feedbackListFragment = new FeedbackListFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, feedbackListFragment)
                .addToBackStack(null)
                .commit();
    }

    private void showLanguageSelectionDialog() {
        String[] languages = {"English", "Spanish", "French", "German", "Chinese"};

        new AlertDialog.Builder(getContext())
                .setTitle("Select Language")
                .setItems(languages, (dialog, which) -> {
                    String selectedLanguage = languages[which];
                    Toast.makeText(getContext(), "Language changed to " + selectedLanguage, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showThemeSelectionDialog() {
        String[] themes = {"Light", "Dark", "System Default"};

        new AlertDialog.Builder(getContext())
                .setTitle("Select Theme")
                .setItems(themes, (dialog, which) -> {
                    String selectedTheme = themes[which];
                    Toast.makeText(getContext(), "Theme changed to " + selectedTheme, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performLogout() {
        // In a real app, clear user session data, tokens, etc.

        // Navigate back to the community board
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new EventBoardFragment())
                .commit();
    }
}
