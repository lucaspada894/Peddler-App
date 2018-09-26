package com.coms309.peddler.Models;

public class User {
    private String id;
    private String name;
    private String category;

    public User(String id, String name, String category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public String getID() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
}
