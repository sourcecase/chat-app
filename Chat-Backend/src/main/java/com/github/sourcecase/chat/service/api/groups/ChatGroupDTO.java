package com.github.sourcecase.chat.service.api.groups;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.sourcecase.chat.service.api.ChatDTO;
import com.github.sourcecase.chat.service.impl.groups.ChatGroupDTOImpl;

/**
 * 
 * @author chsch
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @Type(value = ChatGroupDTOImpl.class, name = "group") })
public interface ChatGroupDTO extends ChatDTO {

	long getId();

	String getName();

}
