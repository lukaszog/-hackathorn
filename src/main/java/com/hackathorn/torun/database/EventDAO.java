package com.hackathorn.torun.database;

import com.hackathon.torun.database.Event;

import java.util.List;

/**
 * Created by lukasz on 20.06.15.
 */
public interface EventDAO {

    public void create(Event p);

    public Event readById(String id);
    public Event readByName(String name);
    public void update(Event p);
    public List<Event> findAll();
    public int deleteById(String id);
}