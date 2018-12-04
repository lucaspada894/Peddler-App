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
	private Timestamp createDate;
	private String messageBody;
	private String actualMessage;
	private int recipientId;
	private int type;
	
	
	public int getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
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
	
	public Timestamp getDate() {
		return createDate;
	}
	
	public String getMessageBody() {
		return messageBody;
	}
	
	public void setMessageBody(String message) {
		this.messageBody = message;
	}
	
	public void setCreatorId(int id) {
		this.creatorId = id;
	}
	
	public void setDate(Timestamp datetime) {
		this.createDate = datetime;
	}
	
	public void setActualMessage(String actualmsg) {
		this.actualMessage = actualmsg;
	}
	
	public void setRecipientId(int recipientId) {
		this.recipientId = recipientId;
	}
	
	
}
