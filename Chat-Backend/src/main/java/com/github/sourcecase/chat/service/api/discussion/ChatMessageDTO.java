package com.github.sourcecase.chat.service.api.discussion;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.sourcecase.chat.service.api.ChatDTO;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.impl.discussion.ChatMessageDTOImpl;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = ChatMessageDTOImpl.class, name = "message") })
public interface ChatMessageDTO extends ChatDTO {

	long getId();

	String getText();

	Time getTime();

	ChatParticipantDTO getSender();

}
