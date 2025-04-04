package com.example.event_hub;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Task 1: Free Community announcement board for events
                selectedFragment = new EventBoardFragment();
            } else if (itemId == R.id.nav_events) {
                // Task 2: Comprehensive event listings with integrated maps
                selectedFragment = new EventListingsFragment();
            } else if (itemId == R.id.nav_maps) {
                // Task 2 (continued): Maps integration
                selectedFragment = new MapFragment();
            } else if (itemId == R.id.nav_tickets) {
                // Task 3: Event Registration (ticket management)
                selectedFragment = new TicketManagementFragment();
            } else if (itemId == R.id.nav_profile) {
                // Access to Tasks 4-5 through profile settings
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }

            return false;
        });

        // Load default fragment - Community Announcement Board
        loadFragment(new EventBoardFragment());
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        int backStackEntryCount = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntryCount > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}