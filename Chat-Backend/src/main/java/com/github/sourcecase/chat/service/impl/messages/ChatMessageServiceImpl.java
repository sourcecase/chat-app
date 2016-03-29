package com.github.sourcecase.chat.service.impl.messages;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.entities.ChatMessageEntity;
import com.github.sourcecase.chat.persistence.entities.ChatParticipantEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatGroupRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatMessageRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatParticipantRepository;
import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.messages.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.messages.ChatMessageService;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.impl.groups.ChatGroupDTOImpl;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

	private static final Logger logger = Logger.getLogger(ChatMessageServiceImpl.class.getName());

	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	private ChatGroupRepository chatGroupRepository;

	@Autowired
	private ChatParticipantRepository chatParticipantRepository;

	@Autowired
	private ChatDTOFactory chatDTOFactory;

	@Override
	public void addMessage(String text, String group, String senderName) {
		ChatGroupEntity chatGroupEntity = chatGroupRepository.findByName(group).get(0);

		List<ChatParticipantEntity> chatParticipantEntityQuery = chatParticipantRepository.findByName(senderName);

		if (chatParticipantEntityQuery.size() != 1) {
			logger.log(Level.SEVERE, "non single user");
		} else {
			ChatParticipantEntity chatParticipantEntity = chatParticipantEntityQuery.get(0);
			chatMessageRepository.saveAndFlush(new ChatMessageEntity(text, chatParticipantEntity, chatGroupEntity));
		}

		String serializeToJson = new ChatGroupDTOImpl(2, "aa").serializeToJson();
		logger.log(Level.INFO, serializeToJson);
		ChatGroupDTO groupDes = chatDTOFactory.createFromJson(ChatGroupDTOImpl.class, serializeToJson);
		logger.log(Level.INFO, groupDes.getName());

		String serializeToJson2 = new ChatParticipantDTOImpl(4, "me").serializeToJson();
		logger.log(Level.INFO, serializeToJson2);
		ChatParticipantDTO groupDes2 = chatDTOFactory.createFromJson(ChatParticipantDTOImpl.class, serializeToJson2);
		logger.log(Level.INFO, groupDes2.getName());

		String serializeToJson3 = new ChatMessageDTOImpl(5, "hello me", new Time(2, 4, 5), groupDes2).serializeToJson();
		logger.log(Level.INFO, serializeToJson3);
		ChatMessageDTO groupDes3 = chatDTOFactory.createFromJson(ChatMessageDTOImpl.class, serializeToJson3);
		logger.log(Level.INFO, "blubb " + groupDes3.getSender().getName());
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
