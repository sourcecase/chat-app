package com.github.sourcecase.chat.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ChatGroupEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;

	protected ChatGroupEntity() {
	}

	public ChatGroupEntity(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("ChatGroupEntity[id=%d, name='%s']", id, name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

}
