package com.hackathon.torun;

import com.hackathon.torun.database.Event;
import com.mongodb.*;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


//import facebook4j.*;
//import facebook4j.auth.AccessToken;
//import facebook4j.internal.http.RequestMethod;

/**
 *
 * @author ryan
 * JavaFX Tax Calculator Form Layout
 * and Basic JavaFX Button Event Handler
 */
public class Main {

    private static String categoriesDir;

    public static void main(String[] args) throws UnknownHostException {

        if (args.length < 1) {

            StackTraceElement[] stack = Thread.currentThread ().getStackTrace ();
            StackTraceElement main = stack[stack.length - 1];
            String mainClass = main.getClassName ();

            System.out.println("Usage: java " + mainClass + " /categiries/dir/");

            Main.categoriesDir = "/home/piotr/Programowanie/Hackaton/JUG/tags/";

            //System.exit(1);

        } else {
            Main.categoriesDir = args[0];
        }


//        String appId = "828403643923031";
//        String appSecret = "2c1f506ffc32ba6e794553a87ec1c5fc";
//
//        Facebook facebook = new FacebookFactory().getInstance();
//
//        facebook.setOAuthAppId(appId, appSecret);
//        String accessTokenString = "CAALxbcDtOlcBADoDUVPGapUu8KCH9xfzSHiPqDiz1nZAgpWO2WLWML1aTPTjDW00f43tZCUBkkL5LBVgiuGHmzeGJjfCb3QrmCJBPkXApfZAeXi2R9NvTH94XHY673XnLZAZBvAMGuxipCj6mwLdzHdxrWZCctc613gYmEmZBUy9RiVH24Vr3KZApoqJPPbTZCDnadF6Tc4shx8Oe0w3CRnGf";
//        AccessToken at = new AccessToken(accessTokenString);
//        facebook.setOAuthAccessToken(at);
//
//
//        FBFetcher fbFetcher = new FBFetcher(facebbok);


//        System.exit(0);


        String TOKEN = "CAACEdEose0cBANeeBU7i7ZAeBYcsAqn91TpZBYj5nogDozfK91rO8J4EyZCxoS0utaYAHkNwS0mnZCKZBdlVoKgsju80ZCpZB3q0PxfYQ0e50C63AbZAwAZCOnd6zn8m5xR6EEZBdivsohDnZBFwcrijVAgF89LyqpQ23S2ZCpXlMsrZAMZAy1gZCcIvdcUoLPBwT6Tg88ZD";

        FBFetcher fbFetcher = new FBFetcher(TOKEN);

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
                            ev.getLocationCity(),ev.getLocationStreet(),"IT"));

                    try {
                        // Save data in database
                        mongoOperation.insert(eve, Event.class);
                    } catch(DuplicateKeyException dk) {System.err.println("* Duplicat event id:" + ev.getEventID());}
                    eve.clear();
                }

                Thread.sleep(1000);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}