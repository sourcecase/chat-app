package com.github.sourcecase.chat.service.impl.groups;

import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.impl.AbstractChatDTO;

public class ChatGroupDTOImpl extends AbstractChatDTO implements ChatGroupDTO {

	private long id;
	private String name;

	public ChatGroupDTOImpl() {

	}

	public ChatGroupDTOImpl(long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("ChatMessageDTO[id=%d, name='%s'", id, name);
	}

}
