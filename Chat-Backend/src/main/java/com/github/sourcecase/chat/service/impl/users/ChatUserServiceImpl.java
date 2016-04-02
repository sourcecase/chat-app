package com.github.sourcecase.chat.service.impl.users;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.sourcecase.chat.persistence.entities.ChatParticipantEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatParticipantRepository;
import com.github.sourcecase.chat.service.api.users.ChatUserService;
import com.github.sourcecase.chat.service.api.users.ChatUserLoginDTO;

public class ChatUserServiceImpl implements ChatUserService {

	private final ChatParticipantRepository chatParticipantRepository;

	@Autowired
	public ChatUserServiceImpl(ChatParticipantRepository chatParticipantRepository) {
		this.chatParticipantRepository = chatParticipantRepository;
	}

	@Override
	public void performRegistration(ChatUserLoginDTO userLoginDTO) {
		ChatParticipantEntity chatParticipantEntity = new ChatParticipantEntity(userLoginDTO.getName());
		this.chatParticipantRepository.saveAndFlush(chatParticipantEntity);
	}

}
