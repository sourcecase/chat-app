package com.github.sourcecase.chat.persistence.repositories;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatGroupRepository extends JpaRepository<ChatGroupEntity, Long> {

	List<ChatGroupEntity> findByName(String name);
	
}
