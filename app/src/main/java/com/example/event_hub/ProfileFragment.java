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

        TextView editProfileOption = view.findViewById(R.id.text_edit_profile);
        TextView changePasswordOption = view.findViewById(R.id.text_change_password);

        TextView notificationsOption = view.findViewById(R.id.text_notifications);
        TextView languageOption = view.findViewById(R.id.text_language);
        TextView themeOption = view.findViewById(R.id.text_theme);
        Button logoutButton = view.findViewById(R.id.button_logout);


        editProfileOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Edit Profile selected", Toast.LENGTH_SHORT).show();

        });

        changePasswordOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Change Password selected", Toast.LENGTH_SHORT).show();

        });


        notificationsOption.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Notifications selected", Toast.LENGTH_SHORT).show();

        });


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


        TextView feedbackOption = view.findViewById(R.id.text_feedback);
        if (feedbackOption != null) {
            feedbackOption.setOnClickListener(v -> {
                navigateToFeedbackSystem();
            });
        }

        return view;
    }

    private void navigateToPaymentSystem() {

        PaymentSystemFragment paymentSystemFragment = new PaymentSystemFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, paymentSystemFragment)
                .addToBackStack(null)
                .commit();
    }

    private void navigateToFeedbackSystem() {

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

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new EventBoardFragment())
                .commit();
    }
}
