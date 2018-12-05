package org.springframework.samples.peddler.projects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProjectUsers {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
		
	private int id;
	private int userId;
	private int projectId;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int id) {
		this.userId = id;
	}
	
	public int getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(int id) {
		this.projectId = id;
	}
	

}
