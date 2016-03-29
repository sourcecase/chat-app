package com.github.sourcecase.chat.service.impl.users;

import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.impl.AbstractChatDTO;

public class ChatParticipantDTOImpl extends AbstractChatDTO implements ChatParticipantDTO {

	private long id;
	private String name;

	public ChatParticipantDTOImpl() {

	}

	public ChatParticipantDTOImpl(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return String.format("ChatParticipant[id=%d, firstName='%s']", id, name);
	}

}
