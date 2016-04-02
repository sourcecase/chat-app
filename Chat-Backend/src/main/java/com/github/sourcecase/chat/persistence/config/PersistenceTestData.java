package com.github.sourcecase.chat.persistence.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.entities.ChatMessageEntity;
import com.github.sourcecase.chat.persistence.entities.ChatParticipantEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatGroupRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatMessageRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatParticipantRepository;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;

@Component
public class PersistenceTestData {

	private ChatParticipantRepository chatParticipantRepository;
	private ChatGroupRepository chatGroupRepository;
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	public PersistenceTestData(ChatParticipantRepository chatParticipantRepository,
			ChatGroupRepository chatGroupRepository, ChatMessageRepository chatMessageRepository) {
		this.chatParticipantRepository = chatParticipantRepository;
		this.chatGroupRepository = chatGroupRepository;
		this.chatMessageRepository = chatMessageRepository;
		this.addTestData();
	}

	public void addTestData() {
		Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE, "Starting JPA test");
		this.chatParticipantRepository.save(new ChatParticipantEntity("Chris"));
		this.chatParticipantRepository.save(new ChatParticipantEntity("Alice"));

		this.chatGroupRepository.save(new ChatGroupEntity("J2EE"));
		this.chatGroupRepository.save(new ChatGroupEntity("Hibernate"));
		this.chatGroupRepository.save(new ChatGroupEntity("Ultimate Frisbee"));
		this.chatGroupRepository.save(new ChatGroupEntity("Klettersteigen"));

		ChatParticipantEntity chris = this.chatParticipantRepository.findByName("Chris").get(0);
		ChatParticipantEntity alice = this.chatParticipantRepository.findByName("Alice").get(0);

		ChatGroupEntity hibernate = this.chatGroupRepository.findByName("Hibernate").get(0);

		this.chatMessageRepository.save(new ChatMessageEntity("Hello Alice", chris, hibernate));
		this.chatMessageRepository.save(new ChatMessageEntity("Hello Chris", alice, hibernate));

		for (ChatParticipantEntity chatParticipant : this.chatParticipantRepository.findAll()) {
			Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE, chatParticipant.toString());
		}

		for (ChatMessageEntity chatMessage : this.chatMessageRepository.findByGroup(hibernate)) {
			Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE,
					chatMessage.getSender().getName() + ": " + chatMessage.getText());
		}

	}

}
