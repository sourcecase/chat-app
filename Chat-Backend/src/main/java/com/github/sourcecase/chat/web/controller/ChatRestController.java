package com.github.sourcecase.chat.web.controller;

import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.spi.JsonProvider;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupService;
import com.github.sourcecase.chat.service.api.messages.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.messages.ChatMessageService;

@RestController
public class ChatRestController {

	private ChatGroupService chatGroupService;
	private ChatMessageService chatMessageService;

	@Autowired
	public ChatRestController(ChatGroupService chatGroupService, ChatMessageService chatMessageService) {
		this.chatGroupService = chatGroupService;
		this.chatMessageService = chatMessageService;
	}

	@RequestMapping(path = "RestChatRooms", method = RequestMethod.GET)
	public ResponseEntity<String> sayHello(ModelMap model, ServletRequest servletRequest, HttpSession httpSession) {
		JsonArray createChatRoomsResponse = createChatRoomsResponse();
		String string = createChatRoomsResponse.toString();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setCacheControl("no-cache");
		return new ResponseEntity<String>(string, httpHeaders, HttpStatus.OK);
	}

	private JsonArray createChatRoomsResponse() {
		JsonProvider provider = JsonProvider.provider();

		JsonArrayBuilder createArrayBuilder = provider.createArrayBuilder();

		for (ChatGroupDTO chatRoom : chatGroupService.getAllChatGroups()) {
			createArrayBuilder.add(chatRoom.getName());
		}

		JsonArray chatRoomsJson = createArrayBuilder.build();
		return chatRoomsJson;
	}

	@RequestMapping(path = "/group/{groupName}", method = RequestMethod.GET)
	public ResponseEntity<String> getGroupMessages(@PathVariable String groupName) {

		ChatGroupDTO chatGroupDTO = chatGroupService.getGroup(groupName);
		List<ChatMessageDTO> allChatMessages = chatMessageService.getAllChatMessages(chatGroupDTO);
		JsonArray createChatRoomsResponse = convertGroup(allChatMessages);

		String string = createChatRoomsResponse.toString();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setCacheControl("no-cache");
		return new ResponseEntity<String>(string, httpHeaders, HttpStatus.OK);
	}

	private JsonArray convertGroup(List<ChatMessageDTO> chatMessages) {
		JsonProvider provider = JsonProvider.provider();

		JsonArrayBuilder createArrayBuilder = provider.createArrayBuilder();

		for (ChatMessageDTO message : chatMessages) {
			createArrayBuilder.add(message.getText());
		}

		JsonArray chatRoomsJson = createArrayBuilder.build();
		return chatRoomsJson;
	}

}