package com.hackathon.torun;

import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import org.springframework.data.annotation.Id;

/**
 * Created by piotr on 20.06.15.
 */
public class FBEvent {

    @Id
    private String id;

    private String event_id;
    private String event_name;
    private String event_description;
    private String event_category;
    private String event_owner;
    private String start_time;
    private String picture_url;
    private String location_place;
    private String location_city;
    private String location_street;
    private double latitude = 0.0;
    private double longitude = 0.0;

    public FBEvent(String category, JsonObject jsonEvent) {

        this.event_id = jsonEvent.getString("eid");
        this.event_owner = jsonEvent.getString("creator");
        this.event_name = jsonEvent.getString("name");
        this.event_description = jsonEvent.getString("description");
        this.event_category = category;
        this.start_time = jsonEvent.getString("start_time");
        this.picture_url = jsonEvent.getString("pic_small");
        this.location_place = jsonEvent.getString("location");

        try {
            JsonObject venue = jsonEvent.getJsonObject("venue");

            this.location_city = venue.getString("city");
            this.location_street = venue.getString("street");

            this.latitude = venue.getDouble("latitude");
            this.longitude = venue.getDouble("longitude");

        } catch(JsonException e) {
            this.location_city = "";
            this.location_street = "";
        }
    }

    public String getEventID() {
        return this.event_id;
    }

    public String getEventOwner() {
        return this.event_owner;
    }

    public String getEventName() {
        return this.event_name;
    }

    public String getEventDescription() {
        return this.event_description;
    }

    public String getEventCategory() {
        return this.event_category;
    }

    public String getStartTime() {
        return this.start_time;
    }

    public String getPictureURL() {
        return this.picture_url;
    }

    public String getLocationPlace() {
        return this.location_place;
    }

    public String getLocationCity() {
        return this.location_city;
    }

    public String getLocationStreet() {
        return this.location_street;
    }

    public double distanceFromPoint(double lat, double lon) {
        double dx = this.latitude - lat;
        double dy = this.longitude - lon;

        return Math.sqrt(dx*dx + dy*dy);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", event_id='" + event_id + '\'' +
                ", event_name='" + event_name + '\'' +
                ", event_description='" + event_description + '\'' +
                ", event_category='" + event_category + '\'' +
                ", event_owner='" + event_owner + '\'' +
                ", start_time='" + start_time + '\'' +
                ", picture_url='" + picture_url + '\'' +
                ", location_place='" + location_place + '\'' +
                ", location_city='" + location_city + '\'' +
                ", location_street='" + location_street + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
