package com.github.sourcecase.chat.service.api.groups;

import java.util.List;

public interface ChatGroupService {

	List<ChatGroupDTO> getAllChatGroups();

	ChatGroupDTO getGroup(String groupName);

}
