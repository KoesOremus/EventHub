package com.example.event_hub;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class activity_event_swipe extends AppCompatActivity {

    private ImageView posterImage;
    private List<EventData> eventList;
    private int currentIndex = 0;
    private GestureDetector gestureDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_swipe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        posterImage = findViewById(R.id.posterTaylorSwift);
        eventList = EventRepository.getEvents();
        showEvent(currentIndex);

        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(e2.getY() - e1.getY())) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            openDescription();
                        } else {
                            nextEvent();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    private void showEvent(int index) {
        if (index >= 0 && index < eventList.size()) {
            EventData event = eventList.get(index);
            posterImage.setImageResource(event.imageResId);
        }
    }
    private void nextEvent() {
        if (currentIndex < eventList.size() - 1) {
            currentIndex++;
            showEvent(currentIndex);
        }
    }

    private void openDescription() {
        EventData event = eventList.get(currentIndex);
        Intent intent = new Intent(this, activity_descriptions.class);
        intent.putExtra("imageResId", event.imageResId);
        intent.putExtra("headerResId", event.headerResId);
        intent.putExtra("title", event.title);
        intent.putExtra("description", event.description);
        intent.putExtra("date", event.date);
        intent.putExtra("location", event.location);
        intent.putExtra("price", event.price);
        startActivity(intent);
    }
}