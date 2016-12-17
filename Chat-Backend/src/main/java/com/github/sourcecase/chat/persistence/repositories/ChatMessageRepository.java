package com.github.sourcecase.chat.persistence.repositories;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.entities.ChatMessageEntity;
import com.github.sourcecase.chat.persistence.entities.ChatUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

	List<ChatMessageEntity> findBySender(ChatUserEntity sender);

	List<ChatMessageEntity> findByGroup(ChatGroupEntity group);

}
