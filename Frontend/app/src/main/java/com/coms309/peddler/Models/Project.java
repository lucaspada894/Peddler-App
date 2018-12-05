package com.coms309.peddler.Models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Project implements Serializable {
    private String id;
    private String name;
    private String desc;
    private String major;
    String owner_id;
    String requestorId;
    public ArrayList<User> users;
    public ArrayList<User> requests;

    public Project(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public Project(String id, String name, String major, String desc, String owner, String requesterID) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.major = major;
        this.owner_id = owner;
        this.requestorId = requesterID;
    }

    public Project(String id, String name, String major, String desc, String owner_id, String requesterID, ArrayList<User> users, ArrayList<User> requests) {
        this.id = id;
        this.name = name;
        this.major =  major;
        this.desc = desc;
        this.owner_id = owner_id;
        this.users = users;
        this.requests = requests;
        this.requestorId = requesterID;
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
    public ArrayList<User> getUsers() { return users; }
    public ArrayList<User> getRequests() {
        return requests;
    }
}
