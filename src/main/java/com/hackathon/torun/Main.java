package com.hackathon.torun;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import java.util.List;

/**
 *
 * @author ryan
 * JavaFX Tax Calculator Form Layout
 * and Basic JavaFX Button Event Handler
 */
public class Main {


    public static void main(String[] args) {
        //launch(args);
        String query = "SELECT name, start_time, venue FROM event WHERE CONTAINS(\"programowanie\")";
        FacebookClient fbc = new DefaultFacebookClient("CAACEdEose0cBAFodaW5KuVFtnOogMOfHqAFW6Bixzr9vFt89gIp2o8xpo4LPPa3a6fWBj2EHsWNuEYZBRVzQqiZAlSSRElLf3Sdtz4nerwYMbTQfdqyHm1YDaBnUVwHkEXhid0mzLjqo5ZB9SJlI0qAGmk3UDkZAwELFMAsGhdei8TRhYFZAPIZCFG09MRyzYRMJI8HZC8teiaz5bPMZBbvt", Version.VERSION_2_0);
        List<JsonObject> objList = fbc.executeFqlQuery(query, JsonObject.class);

        for(JsonObject data: objList){
            System.out.println(data.toString());
        }
    }
}
