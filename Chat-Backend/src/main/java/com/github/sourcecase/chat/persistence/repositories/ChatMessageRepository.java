package com.github.sourcecase.chat.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.entities.ChatMessageEntity;
import com.github.sourcecase.chat.persistence.entities.ChatParticipantEntity;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

	List<ChatMessageEntity> findBySender(ChatParticipantEntity sender);

	List<ChatMessageEntity> findByGroup(ChatGroupEntity group);

}
