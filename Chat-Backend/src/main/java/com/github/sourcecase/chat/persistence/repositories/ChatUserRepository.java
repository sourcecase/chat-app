package com.github.sourcecase.chat.persistence.repositories;

import com.github.sourcecase.chat.persistence.entities.ChatUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUserEntity, Long> {

	List<ChatUserEntity> findByName(String name);
	
}
