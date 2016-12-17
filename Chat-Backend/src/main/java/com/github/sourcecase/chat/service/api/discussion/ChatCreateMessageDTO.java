package com.github.sourcecase.chat.service.api.discussion;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.sourcecase.chat.service.api.ChatDTO;
import com.github.sourcecase.chat.service.impl.discussion.ChatCreateMessageDTOImpl;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = ChatCreateMessageDTOImpl.class, name = "createMessage") })
public interface ChatCreateMessageDTO extends ChatDTO {

    String getText();

    String getGroup();

    String getSenderName();

}

