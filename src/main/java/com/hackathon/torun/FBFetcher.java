package com.hackathon.torun;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by piotr on 20.06.15.
 */
public class FBFetcher {

    private String accessToken;

    public FBFetcher(String accessToken) {
        this.accessToken = accessToken;
    }


    private Date getCurrentDate() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTime();
    }

    private String buildQuery(String[] searchTags) {

        String queryStatement = "SELECT eid, name, location, start_time, description, pic_small, creator, venue FROM event ";
        String whereStatement = "WHERE start_time > \"" + this.getCurrentDate() + "\" ";

        if (searchTags != null && searchTags.length > 0) {

            String mapTags = (String)Arrays.stream(searchTags)
                    .map(tag -> "CONTAINS(\"" + tag + "\")")
                    .reduce((tag1, tag2) -> tag1 + " OR " + tag2).get();

            whereStatement += " AND (" + mapTags + ")";
        }

        return queryStatement + whereStatement;
    }


    public List<FBEvent> fetchIncomingEvents(String category, String[] searchTags) {

        // Build fql query
        String fbQuery = this.buildQuery(searchTags);

        System.out.println(fbQuery);
        //System.exit(0);

        // Connect to fb
        FacebookClient fbc = new DefaultFacebookClient(this.accessToken, Version.VERSION_2_0);

        // Execute query
        List<JsonObject> objList = fbc.executeFqlQuery(fbQuery, JsonObject.class);
        List<FBEvent> eventsList = new LinkedList<FBEvent>();

        // Filter results
        for(JsonObject jso: objList) {
            FBEvent fbe = new FBEvent(category, jso);

            if(fbe.distanceFromPoint(53.02, 18.609) <= 0.075) eventsList.add(fbe);
        }

        return eventsList;
    }

}
