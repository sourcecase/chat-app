package com.github.sourcecase.chat.service.api.discussion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.sourcecase.chat.service.api.ChatDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.impl.discussion.ChatDiscussionDTOImpl;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = ChatDiscussionDTOImpl.class, name = "discussion") })
public interface ChatDiscussionDTO extends ChatDTO {

	ChatGroupDTO getChatGroup();

	List<ChatMessageDTO> getChatMessages();

}
