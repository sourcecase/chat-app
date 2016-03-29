package com.github.sourcecase.chat.service.api.discussion;

import java.util.List;

import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;

public interface ChatDiscussionService {

	void addMessage(String text, String group, String senderName);

	List<ChatMessageDTO> getAllChatMessages(ChatGroupDTO chatGroupDTO);

}
