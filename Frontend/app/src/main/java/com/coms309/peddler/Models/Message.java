package com.coms309.peddler.Models;

public class Message {
    private String msg;
    private String senderId;
    private String recId;

    public Message(String senderId, String recId, String msg) {
        this.senderId = senderId;
        this.recId = recId;
        this.msg = msg;
    }

    public String getSenderID() {
        return senderId;
    }
    public String getRecId() {
        return recId;
    }
    public String getMsg() {
        return msg;
    }
}
