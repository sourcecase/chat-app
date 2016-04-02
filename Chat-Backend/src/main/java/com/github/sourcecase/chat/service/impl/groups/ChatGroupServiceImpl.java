package com.github.sourcecase.chat.service.impl.groups;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sourcecase.chat.persistence.entities.ChatGroupEntity;
import com.github.sourcecase.chat.persistence.repositories.ChatGroupRepository;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupListDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupService;

@Service
public class ChatGroupServiceImpl implements ChatGroupService {

	@Autowired
	private ChatGroupRepository chatGroupRepository;

	@Override
	public ChatGroupListDTO getAllChatGroups() {

		List<ChatGroupEntity> allGroups = chatGroupRepository.findAll();

		List<ChatGroupDTO> listOfChatGroups = new ArrayList<>();

		for (ChatGroupEntity groupEntity : allGroups) {
			listOfChatGroups.add(new ChatGroupDTOImpl(groupEntity.getId(), groupEntity.getName()));
		}

		ChatGroupListDTO result = new ChatGroupListDTOImpl(listOfChatGroups);

		return result;
	}

	@Override
	public ChatGroupDTO getGroup(String groupName) {
		ChatGroupEntity chatGroupEntity = chatGroupRepository.findByName(groupName).get(0);
		return new ChatGroupDTOImpl(chatGroupEntity.getId(), groupName);
	}

}
