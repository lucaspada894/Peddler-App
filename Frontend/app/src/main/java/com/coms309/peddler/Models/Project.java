package com.coms309.peddler.Models;

public class Project {
    private String id;
    private String name;
    private User users[];

    public Project(String id, String name, User users[]) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Project(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public User[] getUsers() {
        return users;
    }
}
