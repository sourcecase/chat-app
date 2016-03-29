package com.github.sourcecase.chat.service.impl.discussion;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionDTO;
import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionService;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.impl.groups.ChatGroupDTOImpl;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;

@Service
public class ChatDiscussionServiceImpl implements ChatDiscussionService {

	private static final Logger logger = Logger.getLogger(ChatDiscussionServiceImpl.class.getName());

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

		Calendar cal = Calendar.getInstance();

		// set Date portion to January 1, 1970
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MILLISECOND, 0);

		String serializeToJson3 = new ChatMessageDTOImpl(5, "hello me", new Time(cal.getTime().getTime()), groupDes2)
				.serializeToJson();
		logger.log(Level.INFO, serializeToJson3);
		ChatMessageDTO groupDes3 = chatDTOFactory.createFromJson(ChatMessageDTOImpl.class, serializeToJson3);
		logger.log(Level.INFO, "blubb " + groupDes3.getSender().getName());

		String serializeToJson4 = new ChatMessageDTOImpl(6, "hello you", new Time(cal.getTime().getTime()), groupDes2)
				.serializeToJson();
		logger.log(Level.INFO, serializeToJson4);
		ChatMessageDTO groupDes4 = chatDTOFactory.createFromJson(ChatMessageDTOImpl.class, serializeToJson4);
		logger.log(Level.INFO, "blubb " + groupDes4.getSender().getName());

		List<ChatMessageDTO> chatMessages = new ArrayList<>();
		chatMessages.add(groupDes3);
		chatMessages.add(groupDes4);

		String serializeToJson5 = chatDTOFactory.createChatDiscussionDTO(groupDes, chatMessages).serializeToJson();
		logger.log(Level.INFO, serializeToJson5);
		ChatDiscussionDTO groupDes5 = chatDTOFactory.createFromJson(ChatDiscussionDTO.class, serializeToJson5);
		logger.log(Level.INFO, "blubb3 " + groupDes5.getChatMessages().get(0).getText());
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
