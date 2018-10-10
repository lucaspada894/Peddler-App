package com.coms309.peddler.Models;

public class Project {
    private String id;
    private String name;
    private String desc;
    private User users[];

    public Project(String id, String name, String desc, User users[]) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.users = users;
    }

    public Project(String id, String name, String desc) {
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
    public User[] getUsers() {
        return users;
    }
}
