package org.springframework.samples.peddler.tutors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tutors {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Integer tutorID;
	private Integer userID;
	private Integer sharedID;
	private String tutorTitle;
	private String tutorSubject;
	private String tutorDescription;
	private String tutorTimes;
	
	
	public Integer getTutorID() {
		return tutorID;
	}
	
	public void setTutorID(Integer tutorID) {
		this.tutorID = tutorID;
	}
	
	public Integer getUserID() {
		return userID;
	}
	
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	public Integer getSharedID() {
		return sharedID;
	}
	
	public void setSharedID(Integer sharedID) {
		this.sharedID = sharedID;
	}
	
	public String getTutorTitle() {
		return tutorTitle;
	}
	
	public void setTutorTitle(String tutorTitle) {
		this.tutorTitle = tutorTitle;
	}
	
	public String getTutorSubject() {
		return tutorSubject;
	}
	
	public void setTutorSubject(String tutorSubject) {
		this.tutorSubject = tutorSubject;
	}
	
	public String getTutorDescription() {
		return tutorDescription;
	}
	
	public void setTutorDescription(String tutorDescription) {
		this.tutorDescription = tutorDescription;
	}
	
	public String getTutorTimes() {
		return tutorTimes;
	}
	
	public void setTutorTimes(String tutorTimes) {
		this.tutorTimes = tutorTimes;
	}

	
	
	
}
