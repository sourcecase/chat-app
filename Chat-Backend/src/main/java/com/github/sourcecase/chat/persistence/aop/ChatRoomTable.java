package com.github.sourcecase.chat.persistence.aop;

import java.util.Map;
import java.util.Set;

public interface ChatRoomTable {
	
	
	void storeNewChatRoom(String room);
	
	Set<String> getListOfChatRooms();
	
	Map<String, String> getMapOfChatRooms();

}
