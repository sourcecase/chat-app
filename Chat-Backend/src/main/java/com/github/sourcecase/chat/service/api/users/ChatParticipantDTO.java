package com.github.sourcecase.chat.service.api.users;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.sourcecase.chat.service.api.ChatDTO;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = ChatParticipantDTOImpl.class, name = "participant") })
public interface ChatParticipantDTO extends ChatDTO {

	long getId();

	String getName();

}
