package com.hackathon.torun;

import com.hackathon.torun.database.Event;
import com.mongodb.*;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author ryan
 * JavaFX Tax Calculator Form Layout
 * and Basic JavaFX Button Event Handler
 */
public class Main {


    public static void main(String[] args) throws UnknownHostException {

        String TOKEN = "CAACEdEose0cBAJQ3ZARmxwrRnuK9xfGTsaxbVgejTeFXomAY8OYnjoK8ASAZBZBXzwixqlWQ7E6CIdJeOGkFBW6xNHZA0Dckog42X7fthKfYIR2vZBDuBZBpvU0h67JE3tCZBi4MkE2hBKpP4MrKF5NkdXnCvZACIKfhZBAtciUvxZBEDWwd2z1yBfvvjlFlsnlm4ZD";
        // search tool
        FBFetcher fbFetcher = new FBFetcher(TOKEN);

        String categoriesDir = "/home/piotr/Programowanie/Hackaton/JUG/tags/";

        MongoOperations mongoOperation = new MongoTemplate(new SimpleMongoDbFactory(new MongoClientURI("mongodb://admin:d41d8cd98f00b204e9800998ecf8427e@ds036648.mongolab.com:36648/facebookevent")));
        Collection<Event> eve = new LinkedList<Event>();
        try {
            // For every category file
            for(Path categoryFile: Files.newDirectoryStream(Paths.get(categoriesDir))){
                System.out.println(categoryFile.toString());

                // Read category
                FBEventCategory fbCategory = new FBEventCategory(categoryFile);

                // Search events in category
                List<FBEvent> events = fbFetcher.fetchIncomingEvents(fbCategory);

                for(FBEvent ev: events) {
                    //System.out.println(ev.getEventID() + " || "  + ev.getLocationCity() + " || " + ev.getEventName());

                    eve.add(new Event(ev.getEventID(),ev.getEventName(),
                            ev.getEventDescription(),ev.getEventCategory(),
                            ev.getEventOwner(),ev.getStartTime(),
                            ev.getPictureURL(),ev.getLocationPlace(),
                            ev.getLocationCity(),ev.getLocationStreet(),"IT"
                    ));
                }

                // Save data in database
                mongoOperation.insert(eve, Event.class);
                eve.clear();
                Thread.sleep(1000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
