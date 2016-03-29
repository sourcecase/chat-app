package com.github.sourcecase.chat.service.impl.messages;

import java.sql.Time;

import com.github.sourcecase.chat.service.api.messages.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.impl.AbstractChatDTO;

public class ChatMessageDTOImpl extends AbstractChatDTO implements ChatMessageDTO {

	private long id;
	private String text;
	private Time time;
	private ChatParticipantDTO sender;

	public ChatMessageDTOImpl() {

	}

	public ChatMessageDTOImpl(long id, String text, Time time, ChatParticipantDTO sender) {
		this.id = id;
		this.text = text;
		this.time = time;
		this.sender = sender;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	@Override
	public ChatParticipantDTO getSender() {
		return sender;
	}

	public void setChatParticipantDTO(ChatParticipantDTO chatParticipantDTO) {
		this.sender = chatParticipantDTO;
	}

	@Override
	public String toString() {
		return String.format("ChatMessageDTO[id=%d, text='%s', time='%s',sender='%s'", id, text, time, sender);
	}

}
