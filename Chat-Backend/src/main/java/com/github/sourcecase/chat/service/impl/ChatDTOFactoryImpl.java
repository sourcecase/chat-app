package com.github.sourcecase.chat.service.impl;

import java.io.IOException;
import java.sql.Time;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionDTO;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.api.users.ChatUserLoginDTO;
import com.github.sourcecase.chat.service.impl.discussion.ChatDiscussionDTOImpl;
import com.github.sourcecase.chat.service.impl.discussion.ChatMessageDTOImpl;
import com.github.sourcecase.chat.service.impl.groups.ChatGroupDTOImpl;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;
import com.github.sourcecase.chat.service.impl.users.ChatUserLoginDTOImpl;

@Service
public class ChatDTOFactoryImpl implements ChatDTOFactory {

	@Override
	public <T> T createFromJson(Class<T> dtoType, String json) {
		ObjectMapper mapper = new ObjectMapper();

		T result = null;
		try {
			result = (T) mapper.readValue(json, dtoType);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ChatGroupDTO createChatGroupDTO(long id, String name) {
		return new ChatGroupDTOImpl(id, name);
	}

	@Override
	public ChatParticipantDTO createChatParticipantDTO(long id, String name) {
		return new ChatParticipantDTOImpl(id, name);
	}

	@Override
	public ChatMessageDTO createChatMessageDTO(long id, String text, Time time, ChatParticipantDTO sender) {
		return new ChatMessageDTOImpl(id, text, time, sender);
	}

	@Override
	public ChatDiscussionDTO createChatDiscussionDTO(ChatGroupDTO chatGroupDTO, List<ChatMessageDTO> chatMessageDTOs) {
		return new ChatDiscussionDTOImpl(chatGroupDTO, chatMessageDTOs);
	}

	@Override
	public ChatUserLoginDTO createChatUserLoginDTO(String name, String password) {
		return new ChatUserLoginDTOImpl(name, password);
	}

}
