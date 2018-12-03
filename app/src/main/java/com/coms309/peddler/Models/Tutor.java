package com.coms309.peddler.Models;

import java.io.Serializable;

public class Tutor implements Serializable {

    private Integer tutorID;
    private Integer userID;
    private Integer sharedID;
    private String tutorTitle;
    private String tutorSubject;
    private String tutorDescription;
    private String tutorTimes;


    public Tutor(Integer tutorID, Integer userID, Integer sharedID, String tutorTitle, String tutorSubject, String tutorDescription, String tutorTimes) {
        this.tutorID = tutorID;
        this.userID = userID;
        this.sharedID = sharedID;
        this.tutorTitle = tutorTitle;
        this.tutorSubject = tutorSubject;
        this.tutorDescription = tutorDescription;
        this.tutorTimes = tutorTimes;
    }


    public Integer getTutorID() { return tutorID; }
    public Integer getUserID() { return userID; }
    public Integer getSharedID() { return sharedID; }
    public String getTutorTitle() { return tutorTitle; }
    public String getTutorSubject() { return tutorSubject; }
    public String getTutorDescription() { return tutorDescription; }
    public String getTutorTimes() { return tutorTimes; }

}