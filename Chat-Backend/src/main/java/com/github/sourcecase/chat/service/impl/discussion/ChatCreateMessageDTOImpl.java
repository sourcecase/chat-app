package com.github.sourcecase.chat.service.impl.discussion;

import com.github.sourcecase.chat.service.api.discussion.ChatCreateMessageDTO;
import com.github.sourcecase.chat.service.impl.AbstractChatDTO;

public class ChatCreateMessageDTOImpl extends AbstractChatDTO implements ChatCreateMessageDTO {

    private String text;
    private String group;
    private String senderName;

    public ChatCreateMessageDTOImpl() {
    }

    public ChatCreateMessageDTOImpl(String text, String group, String senderName, String action) {
        this.text = text;
        this.group = group;
        this.senderName = senderName;
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

}
