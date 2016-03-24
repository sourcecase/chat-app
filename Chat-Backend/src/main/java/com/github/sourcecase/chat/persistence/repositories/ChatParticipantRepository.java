package com.github.sourcecase.chat.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sourcecase.chat.persistence.entities.ChatParticipantEntity;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipantEntity, Long> {

	List<ChatParticipantEntity> findByName(String name);
	
}
