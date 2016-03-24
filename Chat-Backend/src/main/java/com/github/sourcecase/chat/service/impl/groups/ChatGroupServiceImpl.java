package com.github.sourcecase.chat.service.impl.groups;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatGroupRepository;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupService;

@Service
public class ChatGroupServiceImpl implements ChatGroupService {

	@Autowired
	private ChatGroupRepository chatGroupRepository;

	@Override
	public List<ChatGroupDTO> getAllChatGroups() {

		List<ChatGroupEntity> allGroups = chatGroupRepository.findAll();

		List<ChatGroupDTO> result = new ArrayList<>();

		for (ChatGroupEntity groupEntity : allGroups) {
			result.add(new ChatGroupDTOImpl(groupEntity.getId(), groupEntity.getName()));
		}

		return result;
	}

	@Override
	public ChatGroupDTO getGroup(String groupName) {
		ChatGroupEntity chatGroupEntity = chatGroupRepository.findByName(groupName).get(0);
		return new ChatGroupDTOImpl(chatGroupEntity.getId(), groupName);
	}

}
