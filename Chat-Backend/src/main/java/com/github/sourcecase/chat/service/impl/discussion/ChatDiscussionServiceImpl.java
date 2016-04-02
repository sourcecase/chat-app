package com.github.sourcecase.chat.service.impl.discussion;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.entities.ChatMessageEntity;
import com.github.sourcecase.chat.persistence.entities.ChatUserEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatGroupRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatMessageRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatUserRepository;
import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionService;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;

@Service
public class ChatDiscussionServiceImpl implements ChatDiscussionService {

	private static final Logger logger = Logger.getLogger(ChatDiscussionServiceImpl.class.getName());

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private ChatGroupRepository chatGroupRepository;

	@Autowired
	private ChatUserRepository chatParticipantRepository;

	@Override
	public void addMessage(String text, String group, String senderName) {
		ChatGroupEntity chatGroupEntity = chatGroupRepository.findByName(group).get(0);

		List<ChatUserEntity> chatParticipantEntityQuery = chatParticipantRepository.findByName(senderName);

		if (chatParticipantEntityQuery.size() != 1) {
			logger.log(Level.SEVERE, "non single user");
		} else {
			ChatUserEntity chatParticipantEntity = chatParticipantEntityQuery.get(0);
			chatMessageRepository.saveAndFlush(new ChatMessageEntity(text, chatParticipantEntity, chatGroupEntity));
		}

	}

	@Override
	public List<ChatMessageDTO> getAllChatMessages(ChatGroupDTO chatGroupDTO) {

		ChatGroupEntity chatGroupEntity = chatGroupRepository.findByName(chatGroupDTO.getName()).get(0);
		List<ChatMessageEntity> allMessages = chatMessageRepository.findByGroup(chatGroupEntity);

		List<ChatMessageDTO> result = new ArrayList<>();

		for (ChatMessageEntity messageEntity : allMessages) {

			ChatParticipantDTO chatParticipantDTO = new ChatParticipantDTOImpl(messageEntity.getSender().getId(),
					messageEntity.getSender().getName());

			result.add(new ChatMessageDTOImpl(messageEntity.getId(), messageEntity.getText(), messageEntity.getTime(),
					chatParticipantDTO));
		}

		return result;
	}

}
