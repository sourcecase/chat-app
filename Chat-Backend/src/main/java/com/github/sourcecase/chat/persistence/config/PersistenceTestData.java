package com.github.sourcecase.chat.persistence.config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.entities.ChatMessageEntity;
import com.github.sourcecase.chat.persistence.entities.ChatUserEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatGroupRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatMessageRepository;
import com.github.sourcecase.chat.persistence.repositories.ChatUserRepository;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;

@Component
public class PersistenceTestData {

	private ChatUserRepository chatUserRepository;
	private ChatGroupRepository chatGroupRepository;
	private ChatMessageRepository chatMessageRepository;

	@Autowired
	public PersistenceTestData(ChatUserRepository chatParticipantRepository, ChatGroupRepository chatGroupRepository,
			ChatMessageRepository chatMessageRepository) {
		this.chatUserRepository = chatParticipantRepository;
		this.chatGroupRepository = chatGroupRepository;
		this.chatMessageRepository = chatMessageRepository;
		this.addTestData();
	}

	public void addTestData() {
		Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE, "Starting JPA test");
		this.chatUserRepository.save(new ChatUserEntity("Chris", "asdf"));
		this.chatUserRepository.save(new ChatUserEntity("Alice", "asdf"));

		this.chatGroupRepository.save(new ChatGroupEntity("J2EE"));
		this.chatGroupRepository.save(new ChatGroupEntity("Hibernate"));
		this.chatGroupRepository.save(new ChatGroupEntity("Ultimate Frisbee"));
		this.chatGroupRepository.save(new ChatGroupEntity("Klettersteigen"));

		ChatUserEntity chris = this.chatUserRepository.findByName("Chris").get(0);
		ChatUserEntity alice = this.chatUserRepository.findByName("Alice").get(0);

		ChatGroupEntity hibernate = this.chatGroupRepository.findByName("Hibernate").get(0);

		this.chatMessageRepository.save(new ChatMessageEntity("Hello Alice", chris, hibernate));
		this.chatMessageRepository.save(new ChatMessageEntity("Hello Chris", alice, hibernate));

		for (ChatUserEntity chatParticipant : this.chatUserRepository.findAll()) {
			Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE, chatParticipant.toString());
		}

		for (ChatMessageEntity chatMessage : this.chatMessageRepository.findByGroup(hibernate)) {
			Logger.getLogger(ChatParticipantDTOImpl.class.getName()).log(Level.SEVERE,
					chatMessage.getSender().getName() + ": " + chatMessage.getText());
		}

	}

}
