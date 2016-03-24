package com.github.sourcecase.chat.persistence.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class StoreData implements ChatRoomTable {

	
	private HashMap<String, String> rooms = new HashMap<String, String>();
	 
	public StoreData() {
		Logger.getLogger(StoreData.class.getName()).log(Level.SEVERE, "StoreData constructor");
		rooms.put("Ulitmate_Frisbee","Ulitmate Frisbee");
		rooms.put("Klettersteigen","Klettersteigen");
		rooms.put("Ajax","Ajax");
		rooms.put("J2EE","J2EE");
	}
	
	@Override
	public void storeNewChatRoom(String room) {
		Logger.getLogger(StoreData.class.getName()).log(Level.SEVERE, "Storing chat room: " + room);	
	}

	@Override
	public Set<String> getListOfChatRooms() {
		return rooms.keySet();
	}
	
	@Override
	public Map<String, String> getMapOfChatRooms() {
		return rooms;
	}

}
