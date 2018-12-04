package com.coms309.peddler.Models;

public class Message {
    private String msg;
    private String senderId;
    private String recId;
    private String time;
    public boolean sent = false;

    public Message(String senderId, String recId, String msg, String time, boolean sent) {
        this.senderId = senderId;
        this.recId = recId;
        this.msg = msg;
        this.time = time;
        this.sent = sent;
    }

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
    public String getTime() { return msg; }
}
