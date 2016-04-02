package com.github.sourcecase.chat.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sourcecase.chat.persistence.entities.ChatUserEntity;

public interface ChatUserRepository extends JpaRepository<ChatUserEntity, Long> {

	List<ChatUserEntity> findByName(String name);
	
}
