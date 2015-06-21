package com.hackathon.torun;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.json.JsonObject;
//import facebook4j.*;
//import facebook4j.internal.http.RequestMethod;
//import facebook4j.internal.org.json.JSONException;
//import org.json.JSONObject;

import java.util.*;

/**
 * Created by piotr on 20.06.15.
 */
public class FBFetcher {

    private String accessToken;
//    private Facebook facebook;
//
//    public FBFetcher(Facebook facebook) {
//        this.facebook = facebook;
//    }

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

        return (queryStatement + whereStatement).replace("\n", "").replace("\r", "");

    }

    public List<FBEvent> fetchIncomingEvents(FBEventCategory fbCategory) {

        // Build fql query
        String fbQuery = this.buildQuery(fbCategory.getSearchTags());

        System.out.println(fbQuery);
        //System.exit(0);

        // Connect to fb
        FacebookClient fbc = new DefaultFacebookClient(this.accessToken, Version.VERSION_2_0);

        // Execute query
        List<JsonObject> objList = fbc.executeFqlQuery(fbQuery, JsonObject.class);
        List<FBEvent> eventsList = new LinkedList<FBEvent>();

        // Filter results
        for(JsonObject jso: objList) {
            FBEvent fbe = new FBEvent(fbCategory.getCategoryName(), jso);

            if(fbe.distanceFromPoint(53.02, 18.609) <= 0.075) eventsList.add(fbe);
        }

        return eventsList;

/// <---------------------------------------------------

//        String searchQuery = "search?q=" + fbCategory.getSearchTagsString().replace(',', '|') + "&type=event" +
//                "&center=53.020,18.609&distance=7000";


//        System.out.println(searchQuery);
//
//        // Prepare and execute query
//        BatchRequests<BatchRequest> batch = new BatchRequests<BatchRequest>();
//        batch.add(new BatchRequest(RequestMethod.GET, searchQuery));
//
//        List<BatchResponse> results = null;
//        try {
//            results = this.facebook.executeBatch(batch);
//
//        } catch (FacebookException e) {
//
//        }
//
//
//        System.out.println(results.size());
//
//        for(BatchResponse res : results){
//            try {
//                System.out.println(res.asJSONObject().get("data"));
//            } catch (JSONException e) {
//            } catch (FacebookException e) {}
//        }
//
//        return null;
    }

}
