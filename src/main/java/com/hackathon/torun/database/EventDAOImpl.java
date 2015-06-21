package com.hackathorn.torun.database;

/**
 * Created by lukasz on 20.06.15.
 */

import com.hackathon.torun.database.Event;
import com.mongodb.WriteResult;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class EventDAOImpl implements com.hackathorn.torun.database.EventDAO {

    private MongoOperations mongoOps;
    private static final String PERSON_COLLECTION = "Person";

    public EventDAOImpl(MongoOperations mongoOps){
        this.mongoOps=mongoOps;
    }

    public void create(Event p) {
        this.mongoOps.insert(p, PERSON_COLLECTION);
    }

    public Event readById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        return this.mongoOps.findOne(query, Event.class, PERSON_COLLECTION);
    }
    public Event readByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return this.mongoOps.findOne(query, Event.class, PERSON_COLLECTION);
    }
    public List<Event> findAll(){
        List<Event> allPersons = mongoOps.findAll(Event.class);
        System.out.println("===========" + allPersons.size());
        return allPersons;
    }
    public void update(Event p) {
        this.mongoOps.save(p, PERSON_COLLECTION);
    }

    public int deleteById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        WriteResult result = this.mongoOps.remove(query, Event.class, PERSON_COLLECTION);
        return result.getN();
    }

}