package com.github.sourcecase.chat.persistence.aop;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SomeOtherBean {
	
	private ChatRoomTable chatRoomTable;
	
	public SomeOtherBean() {
		Logger.getLogger(SomeOtherBean.class.getName()).log(Level.SEVERE, "SomeOtherBean constructor");
	}

	@Autowired
    public void setChatRoomTable(ChatRoomTable chatRoomTable) {
		Logger.getLogger(SomeOtherBean.class.getName()).log(Level.SEVERE, "SomeOtherBean setChatRoomTable called.");
        this.chatRoomTable = chatRoomTable;
        this.chatRoomTable.storeNewChatRoom("Spring Bean Test Room");
    }
	

}
