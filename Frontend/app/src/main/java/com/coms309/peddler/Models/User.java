package com.coms309.peddler.Models;

import android.media.Image;

import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String university;
    private String year;
    private String projectId;
    public Image pic;

    public User(String id, String email, String firstName, String lastName, String password, String phoneNumber, String university, String year) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.university = university;
        this.year = year;
    }

    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String firstName, String lastName, String id, boolean is) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPassword() {
        return password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getUniversity() {
        return university;
    }
    public String getYear() {
        return year;
    }
    public String getProjectId() { return projectId; }

    public void setProjectID(String id) { this.projectId = id; }
}
