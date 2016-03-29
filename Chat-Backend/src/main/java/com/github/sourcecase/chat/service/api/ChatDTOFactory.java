package com.github.sourcecase.chat.service.api;

import java.sql.Time;
import java.util.List;

import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionDTO;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;

public interface ChatDTOFactory {

	<T> T createFromJson(Class<T> dtoType, String json);

	ChatGroupDTO createChatGroupDTO(long id, String name);

	ChatParticipantDTO createChatParticipantDTO(long id, String name);

	ChatMessageDTO createChatMessageDTO(long id, String text, Time time, ChatParticipantDTO sender);

	ChatDiscussionDTO createChatDiscussionDTO(ChatGroupDTO chatGroupDTO, List<ChatMessageDTO> chatMessageDTOs);

}
