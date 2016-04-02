package com.github.sourcecase.chat.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.spi.JsonProvider;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
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

import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionService;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupListDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupService;
import com.github.sourcecase.chat.service.api.users.ChatUserLoginDTO;
import com.github.sourcecase.chat.service.api.users.ChatUserService;
import com.github.sourcecase.chat.web.ChatPathConfiguration;

@RestController
public class ChatRestController {

	private static final Logger logger = Logger.getLogger(ChatRestController.class.getName());

	private final ChatGroupService chatGroupService;
	private final ChatDiscussionService chatMessageService;
	private final ChatDTOFactory chatDTOFactory;
	private final ChatUserService chatUserService;

	@Autowired
	public ChatRestController(ChatGroupService chatGroupService, ChatDiscussionService chatMessageService,
			ChatDTOFactory chatDTOFactory, ChatUserService chatUserService) {
		this.chatGroupService = chatGroupService;
		this.chatMessageService = chatMessageService;
		this.chatDTOFactory = chatDTOFactory;
		this.chatUserService = chatUserService;
	}

	@RequestMapping(path = ChatPathConfiguration.REST_CHAT_GROUPS, method = RequestMethod.GET)
	public ResponseEntity<String> sayHello(ModelMap model, ServletRequest servletRequest, HttpSession httpSession) {
		ChatGroupListDTO chatGroups = chatGroupService.getAllChatGroups();
		String chatGroupsJson = chatGroups.serializeToJson();
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setCacheControl("no-cache");
		return new ResponseEntity<String>(chatGroupsJson, httpHeaders, HttpStatus.OK);
	}

	@RequestMapping(path = ChatPathConfiguration.REST_REGISTER_PERFORM_URL, method = RequestMethod.POST)
	public ResponseEntity<String> performRegister(ServletRequest servletRequest) {
		/*
		 * TODO: Add plausibility check for inputStream size
		 */
		;
		try {
			InputStream bodyStream = servletRequest.getInputStream();
			String body = IOUtils.toString(bodyStream);
			ChatUserLoginDTO userLoginDto = chatDTOFactory.createFromJson(ChatUserLoginDTO.class, body);
			logger.info("registering retrieved username:" + userLoginDto.getName() + " password:"
					+ userLoginDto.getPassword());
			chatUserService.performRegistration(userLoginDto);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e.getCause());
		}

		return new ResponseEntity<String>("registering complete", null, HttpStatus.OK);
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