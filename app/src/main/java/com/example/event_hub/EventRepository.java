package com.example.event_hub;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {
    public static List<EventData> getEvents() {
        List<EventData> events = new ArrayList<>();

        events.add(new EventData(
                R.drawable.poster_taylorswift,
                R.drawable.header_taylorswift,
                "Taylor Swift Eras Tour Karaoke Night",
                "Get ready to relive the magic of Taylor Swift’s Eras Tour, one iconic era at a time.\n" +
                        "Sing your heart out with fellow Swifties, dress in your favorite era, and swap friendship bracelets.\n" +
                        "It’s not just a singalong, it’s a night of memories, music, and magical moments.",
                "13-11-2025",
                "BC Place Stadium, Vancouver",
                13.99
                ));

        events.add(new EventData(
                R.drawable.poster_squidgames,
                R.drawable.header_squidgames,
                "Taylor Swift Eras Tour Karaoke Night",
                "Get ready to relive the magic of Taylor Swift’s Eras Tour—one iconic era at a time.\n" +
                        "Sing your heart out with fellow Swifties, dress in your favorite era, and swap friendship bracelets.\n" +
                        "It’s not just a singalong, it’s a night of memories, music, and magical moments.",
                "05-04-2025",
                "The Well, UBCO, Kelowna",
                5.00
        ));

        events.add(new EventData(
                R.drawable.poster_movienight,
                R.drawable.header_movienight,
                "Taylor Swift Eras Tour Karaoke Night",
                "It’s movie night, but the title stays a secret until the screen lights up.\n" +
                        "Grab your popcorn, get comfy, and take a chance on a surprise cinematic ride.\n" +
                        "No spoilers, no hints—just vibes, snacks, and suspense.",
                "25-04-2025",
                "Landmark Cinemas, McCurdy, Kelowna",
                9.99
        ));
        return events;
    }
}
