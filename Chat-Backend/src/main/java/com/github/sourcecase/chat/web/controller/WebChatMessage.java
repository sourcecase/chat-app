package com.github.sourcecase.chat.web.controller;

/**
 * Created by chris on 12.12.2016.
 */
public class WebChatMessage {

    private String text;
    private String group;
    private String senderName;
    private String action;

    public WebChatMessage() {
    }

    public WebChatMessage(String text, String group, String senderName, String action) {
        this.text = text;
        this.group = group;
        this.senderName = senderName;
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
