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
        //launch(args);

        String TOKEN = "CAACEdEose0cBANbEQTKScaZBWLznwO9HSBXB19sC2OXkJfZBFLgTcC6BRIJNPglOby7HenCSKgx3uKuNxP9E06nlNrJRFXg4nvMMV2SFkLZA4cypmPLSFw7DkiftnU5JTF3ImfUllMerOPU7T6KdMIxFAEuEfa26ZBHmeMgYzsuSyy7ld5l1jBFcXwjhBmcZD";


        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.MILLISECOND, 0);



        String query = "SELECT eid, name, location, start_time, description, pic_small, venue FROM event WHERE  CONTAINS(\"Toruń\")  and  start_time > \"" + today.getTime() + "\""; //   CONTAINS(\"Toruń\") or CONTAINS(\"Bydgoszcz\")";
        FacebookClient fbc = new DefaultFacebookClient(TOKEN, Version.VERSION_2_0);


        List<JsonObject> objList = fbc.executeFqlQuery(query, JsonObject.class);

        for(JsonObject data: objList){

            String event_id = data.getString("eid");
            String name = data.getString("name");
            String desc = data.getString("description");
            String picurl = data.getString("pic_small");
            String starttime = data.getString("start_time");
            String location = data.getString("location");

            String city = "";
            String street = "";

            try {
                JsonObject venue = data.getJsonObject("venue");

                street = venue.getString("street");
                city = venue.getString("city");

            } catch(JsonException e) {}


            System.out.println(event_id + " || " +
                    starttime + " || " +
                    name + " || " +
                    city + " || " +
                    street + " || " );
            /*+
                    location + " || " +
                    picurl + " || " +
                    desc);
                    */
        }

        System.out.println(objList.size());
    }
}
