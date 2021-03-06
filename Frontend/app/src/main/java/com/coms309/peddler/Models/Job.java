package com.coms309.peddler.Models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Job implements Serializable {
    private String id;
    private String name;
    private String desc;
    private String major;
    String owner_id;
    String requestorId;
    ArrayList<String> users;
    ArrayList<Message> requests;

    public Job(String id, String name, String major, String desc, String owner, String requesterID) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.major = major;
        this.owner_id = owner;
        this.requestorId = requesterID;
    }

    public Job(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public Job(String id, String name, String desc, String owner_id, ArrayList<String> users, ArrayList<Message> requests) {
        //super(owner_id, users, requests);
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public String getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDesc() {
        return desc;
    }
    public String getMajor() { return major; }
    public String getOwnerID() { return owner_id; }
    public String getRequestorId() { return requestorId; }
}
