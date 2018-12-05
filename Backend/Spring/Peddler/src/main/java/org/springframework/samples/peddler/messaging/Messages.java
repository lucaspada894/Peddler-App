package org.springframework.samples.peddler.messaging;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.sql.Blob;
import java.sql.Timestamp;

@Entity
public class Messages {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int creatorId;
	private int recipientId;
	private String createDate;
	private String actualMessage;

	
	public int getId() {
		return id;
	}
	
	public int getRecipientId() {
		return recipientId;
	}
	
	public String getActualMessage() {
		return actualMessage;
	}
	
	public int getCreatorId() {
		return creatorId;
	}
	
	public String getDate() {
		return createDate;
	}
	
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	
	public void setDate(String createDate) {
		this.createDate = createDate;
	}
	
	public void setActualMessage(String actualmsg) {
		this.actualMessage = actualmsg;
	}
	
	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}
	
	
}
