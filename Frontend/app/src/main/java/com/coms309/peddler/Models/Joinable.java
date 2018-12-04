package com.coms309.peddler.Models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Joinable implements Serializable {
    private String owner;
    private ArrayList<String> member_ids;
    private ArrayList<Message> join_requests;

    public Joinable(String owner, ArrayList<String> member_ids, ArrayList<Message> join_requests) {
        this.owner = owner;
        this.member_ids = member_ids;
        this.join_requests = join_requests;
    }

    public String getOwner() { return owner; }
    public ArrayList<String> getMembers() { return member_ids; }
    public ArrayList<Message> getRequests() { return join_requests; }
}
