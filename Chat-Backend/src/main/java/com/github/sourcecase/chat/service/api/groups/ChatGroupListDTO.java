package com.github.sourcecase.chat.service.api.groups;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.sourcecase.chat.service.api.ChatDTO;
import com.github.sourcecase.chat.service.impl.groups.ChatGroupDTOImpl;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = ChatGroupDTOImpl.class, name = "groupList") })
public interface ChatGroupListDTO extends ChatDTO {

	List<ChatGroupDTO> getChatGroups();

}
