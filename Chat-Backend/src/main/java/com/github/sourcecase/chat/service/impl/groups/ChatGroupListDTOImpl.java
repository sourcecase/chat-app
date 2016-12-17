package com.github.sourcecase.chat.service.impl.groups;

import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupListDTO;
import com.github.sourcecase.chat.service.impl.AbstractChatDTO;

import java.util.List;

public class ChatGroupListDTOImpl extends AbstractChatDTO implements ChatGroupListDTO {

	private List<ChatGroupDTO> chatGroups;

	public ChatGroupListDTOImpl() {

	}

	public ChatGroupListDTOImpl(List<ChatGroupDTO> chatGroups) {
		this.chatGroups = chatGroups;
	}

	public void setChatGroups(List<ChatGroupDTO> chatGroups) {
		this.chatGroups = chatGroups;
	}

	@Override
	public List<ChatGroupDTO> getChatGroups() {
		return chatGroups;
	}

}
