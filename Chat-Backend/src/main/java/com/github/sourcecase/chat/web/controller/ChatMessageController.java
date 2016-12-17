package com.github.sourcecase.chat.web.controller;

import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.discussion.ChatCreateMessageDTO;
import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionService;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.web.config.ChatPathConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@Controller
public class ChatMessageController {

    private static final Logger LOGGER = Logger.getLogger(ChatMessageController.class.getName());
    private final ChatDiscussionService chatMessageService;

    @Autowired
    private ChatDTOFactory chatDTOFactory;

    ChatMessageController(@Autowired ChatDiscussionService chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping(ChatPathConfiguration.CHAT_WEB_SOCKET_CREATE_MESSAGE)
    @SendTo(ChatPathConfiguration.CHAT_WEB_SOCKET_RECEIVE_MESSAGE)
    public ChatMessageDTO sendStomp(ChatCreateMessageDTO message) throws Exception {
        LOGGER.info("sendStomp called " + message.getText() + " " +  message.getGroup() + " " + message.getSenderName());
        ChatMessageDTO chatMessageDTO = chatMessageService.addMessage(message);
        LOGGER.info("sendStomp called responding with:" + chatMessageDTO.toString() );
        return chatMessageDTO;
    }
}
