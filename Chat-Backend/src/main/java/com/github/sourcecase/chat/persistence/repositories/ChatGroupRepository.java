package com.github.sourcecase.chat.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;

public interface ChatGroupRepository extends JpaRepository<ChatGroupEntity, Long> {

	List<ChatGroupEntity> findByName(String name);
	
}
