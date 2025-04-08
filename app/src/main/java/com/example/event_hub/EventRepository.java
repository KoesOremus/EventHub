package com.example.event_hub;

import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    // returns a list of all available events
    public static List<Event> getEvents() {
        List<Event> events = new ArrayList<>();

        // event 1 - taylor swift karaoke
        events.add(new Event(
                R.drawable.poster_taylorswift,
                R.drawable.header_taylorswift,
                "Taylor Swift Eras Tour Karaoke",
                "Get ready to relive the magic of Taylor Swift’s Eras Tour, one iconic era at a time.\n" +
                        "Sing your heart out with fellow Swifties, dress in your favorite era, and swap friendship bracelets.\n" +
                        "It’s not just a singalong, it’s a night of memories, music, and magical moments.",
                "13-11-2025",
                "BC Place Stadium, Vancouver",
                13.99
        ));

        // event 2 - squid games live
        events.add(new Event(
                R.drawable.poster_squidgames,
                R.drawable.header_squidgames,
                "Squid Games : Live Play",
                "Think you have what it takes to survive the Squid Games?" +
                        "Join us for a real-life, high-stakes experience where you'll compete in classic challenges inspired by the hit show. " +
                        "Outsmart, outplay, and outlast your opponents — because only one will come out on top. " +
                        "Strategy, nerve, and a little luck will decide your fate. Let the games begin.",
                "05-04-2025",
                "The Well, UBCO, Kelowna",
                5.00
        ));

        // event 3 - mystery movie night
        events.add(new Event(
                R.drawable.poster_movienight,
                R.drawable.header_movienight,
                "Movie Night : Thriller Edition",
                "It’s movie night, but the title stays a secret until the screen lights up.\n" +
                        "Grab your popcorn, get comfy, and take a chance on a surprise cinematic ride.\n" +
                        "No spoilers, no hints—just vibes, snacks, and suspense.",
                "25-04-2025",
                "Landmark Cinemas, McCurdy, Kelowna",
                9.99
        ));

        events.add(new Event(
                R.drawable.poster_musicfestival,
                R.drawable.header_musicfestival,
                "Summer Beats Music Festival",
                "Join us for a day-long celebration of music featuring top artists from around the globe.\n" +
                        "Multiple stages, food trucks, and activities for all ages.",
                "20-06-2025",
                "Central Park, Kelowna",
                49.99
        ));

        // Event 5 - Art Exhibition
        events.add(new Event(
                R.drawable.poster_artexhibition,
                R.drawable.header_artexhibition,
                "Modern Art Exhibition",
                "Explore contemporary artworks from emerging and established artists.\n" +
                        "Interactive installations and guided tours available.",
                "10-07-2025",
                "Kelowna Art Gallery",
                15.00
        ));

        // Event 6 - Food Truck Festival
        events.add(new Event(
                R.drawable.poster_foodtruck,
                R.drawable.header_foodtruck,
                "Food Truck Fiesta",
                "Savor a variety of cuisines from the best food trucks in the region.\n" +
                        "Live music and family-friendly activities throughout the day.",
                "15-08-2025",
                "Waterfront Park, Kelowna",
                5.00
        ));

        // return the full list of events
        return events;
    }
}
