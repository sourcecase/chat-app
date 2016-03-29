package com.github.sourcecase.chat.service.impl.discussion;

import java.util.List;

import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionDTO;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.impl.AbstractChatDTO;

public class ChatDiscussionDTOImpl extends AbstractChatDTO implements ChatDiscussionDTO {

	private ChatGroupDTO chatGroup;
	private List<ChatMessageDTO> chatMessages;

	public ChatDiscussionDTOImpl() {
	}

	public ChatDiscussionDTOImpl(ChatGroupDTO chatGroup, List<ChatMessageDTO> chatMessages) {
		this.setChatGroup(chatGroup);
		this.setChatMessages(chatMessages);
	}

	public ChatGroupDTO getChatGroup() {
		return chatGroup;
	}

	public void setChatGroup(ChatGroupDTO chatGroup) {
		this.chatGroup = chatGroup;
	}

	public List<ChatMessageDTO> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(List<ChatMessageDTO> chatMessages) {
		this.chatMessages = chatMessages;
	}

}
