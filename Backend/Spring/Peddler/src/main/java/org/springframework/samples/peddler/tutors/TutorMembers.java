package org.springframework.samples.peddler.tutors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TutorMembers {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private int id;
	private int tutorId;
	private int studentId;
	
	public void setTutorId(int tutorId) {
		this.tutorId = tutorId;
	}
	
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public int getTutorId() {
		return tutorId;
	}
	
	public int getStudentId() {
		return studentId;
	}
	
	public int getId() {
		return id;
	}
}
