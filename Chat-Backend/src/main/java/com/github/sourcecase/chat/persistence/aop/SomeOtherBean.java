package com.github.sourcecase.chat.persistence.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SomeOtherBean {

	public SomeOtherBean() {
		Logger.getLogger(SomeOtherBean.class.getName()).log(Level.SEVERE, "SomeOtherBean constructor");
	}

	@Autowired
    public void setChatRoomTable(ChatRoomTable chatRoomTable) {
		Logger.getLogger(SomeOtherBean.class.getName()).log(Level.SEVERE, "SomeOtherBean setChatRoomTable called.");
		ChatRoomTable chatRoomTable1 = chatRoomTable;
        chatRoomTable1.storeNewChatRoom("Spring Bean Test Room");
    }
	

}
