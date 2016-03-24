package com.github.sourcecase.chat.service.api.messages;

import java.sql.Time;

import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;

public interface ChatMessageDTO {
	
	long getId();
	
	String getText();
	
	Time getTime();
	
	ChatParticipantDTO getSender();

}
