package com.github.sourcecase.chat.service.api.groups;

public interface ChatGroupService {

	ChatGroupListDTO getAllChatGroups();

	ChatGroupDTO getGroup(String groupName);

}
