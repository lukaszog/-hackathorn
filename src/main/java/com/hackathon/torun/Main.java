package com.hackathon.torun;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ryan
 * JavaFX Tax Calculator Form Layout
 * and Basic JavaFX Button Event Handler
 */
public class Main {


    public static void main(String[] args) {

        String TOKEN = "CAACEdEose0cBAD3U1Ny10s4xMZBKL4OO8L0wPUmcgchUTgbykZCpZBxMzBkxYuwSHR9Dvhe2eEoTKyfYCoNUMjrXZC0MOkEJ58CIouPW5PfIUZCGieYV1RCYbXHnHNtQGik2j3xYxPUAQJka2Fc44dHxMLC195AebIjWejmfW8ZCijAQFwXsWRzm9cxNIuvurQIsZCxL4LIBk7hZA9H7AHpL7l219RbcGhcZD";

        // search tool
        FBFetcher fbFetcher = new FBFetcher(TOKEN);

        // search arguments
        String category = "IT";
        String city = "Torun";
        String[] searchTags = {"JUG", "Szkoła", "Copernicon"};

        // perform search
        List<FBEvent> events = fbFetcher.fetchIncomingEvents(category, city, searchTags);

        // do something with results...
        for(FBEvent ev: events) {
            System.out.println(ev.getEventID() + " || "  + ev.getEventName());
        }

    }
}
