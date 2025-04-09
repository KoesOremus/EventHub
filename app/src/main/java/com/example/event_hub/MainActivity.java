package com.example.event_hub;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static boolean navigateFromCheckout = false;
    public static void navigateToCartFromDetails(FragmentActivity activity) {
        navigateFromCheckout = true;
        BottomNavigationView nav = activity.findViewById(R.id.bottom_navigation);
        nav.setSelectedItemId(R.id.nav_tickets);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize bottom navigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // set up navigation item selection listener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {

                selectedFragment = new EventBoardFragment();
            } else if (itemId == R.id.nav_events) {

                selectedFragment = new EventListingsFragment();
            } else if (itemId == R.id.nav_maps) {

                selectedFragment = new MapFragment();
            } else if (itemId == R.id.nav_tickets) {

                if (navigateFromCheckout) {
                    navigateFromCheckout = false;
                    selectedFragment = new TicketManagementFragment();
                    selectedFragment.setArguments(CartManager.buildBundleFromEvent());
                } else if (CartManager.getSelectedEvent() == null) {
                    Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    selectedFragment = new TicketManagementFragment();
                    selectedFragment.setArguments(CartManager.buildBundleFromEvent());
                }

            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                return true;
            }

            return false;
        });

        // load default fragment - Community Announcement Board
        loadFragment(new EventBoardFragment());
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new EventBoardFragment())
                    .commit();
        }

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