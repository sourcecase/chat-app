package com.github.sourcecase.chat.service.impl.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sourcecase.chat.persistence.entities.ChatUserEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatUserRepository;
import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.api.users.ChatUserLoginDTO;
import com.github.sourcecase.chat.service.api.users.ChatUserService;

@Service
public class ChatUserServiceImpl implements ChatUserService {

	private final ChatUserRepository chatParticipantRepository;
	private final ChatDTOFactory chatDTOFactory;

	@Autowired
	public ChatUserServiceImpl(ChatUserRepository chatParticipantRepository, ChatDTOFactory chatDTOFactory) {
		this.chatParticipantRepository = chatParticipantRepository;
		this.chatDTOFactory = chatDTOFactory;
	}

	@Override
	public void performRegistration(ChatUserLoginDTO userLoginDTO) {
		ChatUserEntity chatParticipantEntity = new ChatUserEntity(userLoginDTO.getName(), userLoginDTO.getPassword());
		this.chatParticipantRepository.saveAndFlush(chatParticipantEntity);
	}

	@Override
	public ChatParticipantDTO validateLogin(ChatUserLoginDTO userLoginDTO) {
		List<ChatUserEntity> findByName = this.chatParticipantRepository.findByName(userLoginDTO.getName());
		if (findByName == null || findByName.size() != 1) {
			return null;
		}

		ChatUserEntity chatParticipantEntity = findByName.get(0);
		if (chatParticipantEntity.getPassword().equals(userLoginDTO.getPassword())) {
			return chatDTOFactory.createChatParticipantDTO(chatParticipantEntity.getId(),
					chatParticipantEntity.getName());
		} else {
			return null;
		}

	}

}
