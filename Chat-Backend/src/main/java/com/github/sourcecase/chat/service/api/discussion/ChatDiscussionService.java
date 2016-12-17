package com.github.sourcecase.chat.service.api.discussion;

import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;

import java.util.List;

public interface ChatDiscussionService {

	ChatMessageDTO addMessage(ChatCreateMessageDTO chatCreateMessageDTO);

	List<ChatMessageDTO> getAllChatMessages(ChatGroupDTO chatGroupDTO);

}
