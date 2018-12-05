package org.springframework.samples.peddler.projects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProjectNotifications {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private int id;
	private int userId;
	private int projectId;
	String notification;
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public String getNotification() {
		return notification;
	}
	
	public void setNotification(String notification) {
		this.notification = notification;
	}
}
