package com.github.sourcecase.chat.persistence.entities;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ChatMessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getId() {
		return id;
	}

	public Time getTime() {
		return time;
	}

	public ChatUserEntity getSender() {
		return sender;
	}

	public ChatGroupEntity getGroup() {
		return group;
	}

	private Time time;

	@ManyToOne(optional = false, targetEntity = ChatUserEntity.class)
	private ChatUserEntity sender;

	@ManyToOne(optional = false, targetEntity = ChatGroupEntity.class)
	private ChatGroupEntity group;

	protected ChatMessageEntity() {
	}

	public ChatMessageEntity(String text, ChatUserEntity sender, ChatGroupEntity group) {
		this.text = text;
		this.time = new Time(System.currentTimeMillis());
		this.sender = sender;
		this.group = group;
	}

	@Override
	public String toString() {
		return String.format("ChatMessageEntity[id=%d, text='%s', time='%s', sender='%s', group='%s']", id, text, time,
				sender, group);
	}

}
