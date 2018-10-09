package com.coms309.peddler.Models;

public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String university;
    private String year;

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
}
