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
    ArrayList<String> users;
    ArrayList<Message> requests;

    public Project(String id, String name, String major, String desc, String owner) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.major = major;
        this.owner_id = owner;
    }

    public Project(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public Project(String id, String name, String desc, String owner_id, ArrayList<String> users, ArrayList<Message> requests) {
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
}
