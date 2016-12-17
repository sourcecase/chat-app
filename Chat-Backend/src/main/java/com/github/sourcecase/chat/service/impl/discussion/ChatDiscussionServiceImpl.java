package com.github.sourcecase.chat.service.impl.discussion;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.entities.ChatMessageEntity;
import com.github.sourcecase.chat.persistence.entities.ChatUserEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatGroupRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatMessageRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatUserRepository;
import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.discussion.ChatCreateMessageDTO;
import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionService;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ChatDiscussionServiceImpl implements ChatDiscussionService {

	private static final Logger logger = Logger.getLogger(ChatDiscussionServiceImpl.class.getName());

	private final ChatMessageRepository chatMessageRepository;

	private final ChatGroupRepository chatGroupRepository;

	private final ChatUserRepository chatParticipantRepository;

	private final ChatDTOFactory chatDTOFactory;

	public ChatDiscussionServiceImpl(@Autowired
									 ChatMessageRepository chatMessageRepository,
									 @Autowired
									 ChatGroupRepository chatGroupRepository,
									 @Autowired
									 ChatUserRepository chatParticipantRepository,
									 @Autowired
									 ChatDTOFactory chatDTOFactory) {

		this.chatMessageRepository = chatMessageRepository;
		this.chatGroupRepository = chatGroupRepository;
		this.chatParticipantRepository = chatParticipantRepository;
		this.chatDTOFactory = chatDTOFactory;
	}

	@Override
	public ChatMessageDTO addMessage(ChatCreateMessageDTO chatCreateMessageDTO) {
		ChatGroupEntity chatGroupEntity = chatGroupRepository.findByName(chatCreateMessageDTO.getGroup()).get(0);
		List<ChatUserEntity> chatParticipantEntityQuery = chatParticipantRepository.findByName(chatCreateMessageDTO.getSenderName());
		if (chatParticipantEntityQuery.size() != 1) {
			logger.log(Level.SEVERE, "non single user");
			throw new WebApplicationException("non single user");
		} else {
			ChatUserEntity chatParticipantEntity = chatParticipantEntityQuery.get(0);
			ChatMessageEntity chatMessageEntity = chatMessageRepository.saveAndFlush(new ChatMessageEntity(chatCreateMessageDTO.getText(), chatParticipantEntity, chatGroupEntity));
			return newChatMessageDTOFrom(chatMessageEntity);
		}
	}

	@Override
	public List<ChatMessageDTO> getAllChatMessages(ChatGroupDTO chatGroupDTO) {
		ChatGroupEntity chatGroupEntity = chatGroupRepository.findByName(chatGroupDTO.getName()).get(0);
		List<ChatMessageEntity> allMessages = chatMessageRepository.findByGroup(chatGroupEntity);
		List<ChatMessageDTO> result = new ArrayList<>();
		for (ChatMessageEntity messageEntity : allMessages) {
			result.add(newChatMessageDTOFrom(messageEntity));
		}
		return result;
	}

	private ChatMessageDTO newChatMessageDTOFrom(ChatMessageEntity chatMessageEntity) {
		ChatParticipantDTO chatParticipantDTO =  chatDTOFactory.createChatParticipantDTO(
				chatMessageEntity.getSender().getId(),
				chatMessageEntity.getSender().getName());
		return chatDTOFactory.createChatMessageDTO(
				chatMessageEntity.getId(),
				chatMessageEntity.getText(),
				chatMessageEntity.getTime(),
				chatParticipantDTO);
	}
}
