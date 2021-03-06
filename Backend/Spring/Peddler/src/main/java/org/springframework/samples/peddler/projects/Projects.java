package org.springframework.samples.peddler.projects;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Projects {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Integer id;
	private String title;
	private String major;
	private String description;
	private Integer ownerId;	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerID(Integer ownerID) {
		this.ownerId = ownerID;
	}
	

	
	




	
	
	
}
