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

        String TOKEN = "CAACEdEose0cBAM10dzSZC3GWQHWZAduuqAuGHU07uGXvJ0Fe9HwZBpkM2ZBDH7gRtIlu5KfPR2N01ilKDMFu8Fmk3BN6TQFZBH3W4sMZBkW60RrGGxDpQE5ascgskvZBuZCBLgbEZCZBgiesm7KjZCVtrRvDclUHuC26I9ZCmfepXHW8ouNQu4wDumucSTfxbRhgE8oZD";



        // search tool
        FBFetcher fbFetcher = new FBFetcher(TOKEN);

        // search arguments
        String category = "IT";
        String[] searchTags = { "IT", "komputery", "programowie", "technologie", "dev", "php", "sql", "java",
                                "python", "c#", "javascript", "techklub", "hardware", "geek", "mission torun",
                                "hackaton", "hackathon", "ataki sieciowe", "sieci", "smartspace", "Informat",
                                "internet", "nowoczesn", "jug", "plssug", "Informatyki", "roboty", "3d"};

        // perform search
        List<FBEvent> events = fbFetcher.fetchIncomingEvents(category, searchTags);

        // do something with results...
        for(FBEvent ev: events) {
            System.out.println(ev.getEventID() + " || "  + ev.getLocationCity() + " || " + ev.getEventName());
        }

    }
}
