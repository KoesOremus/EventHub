package com.example.event_hub;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_descriptions extends AppCompatActivity {

    private int imageResId, headerResId;
    private String title, description, date, location;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_descriptions);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        imageResId = intent.getIntExtra("imageResId", 0);
        headerResId = intent.getIntExtra("headerResId", 0);
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        date = intent.getStringExtra("date");
        location = intent.getStringExtra("location");
        price = intent.getDoubleExtra("price", 0.0);

        ImageView header = findViewById(R.id.headerKhushi);
        TextView titleText = findViewById(R.id.titleKhushi);
        TextView descText = findViewById(R.id.descriptionsKhushi);
        TextView dateText = findViewById(R.id.textDateKhushi);
        TextView locationText = findViewById(R.id.textLocationKhushi);
        TextView priceText = findViewById(R.id.textTicketKhushi);

        header.setImageResource(headerResId);
        titleText.setText(title);
        descText.setText(description);
        dateText.setText("Date: " + date);
        locationText.setText("Location: " + location);
        priceText.setText("Price: $" + String.format("%.2f", price));

        Button checkoutButton = findViewById(R.id.descriptionsButtonKhushi);
        ImageView cartIcon = findViewById(R.id.iconCartKhushi);

        checkoutButton.setOnClickListener(v -> {
            Intent ticketIntent = new Intent(this, activity_ticket_management.class);
            ticketIntent.putExtra("headerResId", headerResId);
            ticketIntent.putExtra("title", title);
            ticketIntent.putExtra("price", price);
            startActivity(ticketIntent);
        });

        cartIcon.setOnClickListener(v -> {
            Intent ticketIntent = new Intent(this, activity_ticket_management.class);
            ticketIntent.putExtra("headerResId", headerResId);
            ticketIntent.putExtra("title", title);
            ticketIntent.putExtra("price", price);
            startActivity(ticketIntent);
        });
    }
}