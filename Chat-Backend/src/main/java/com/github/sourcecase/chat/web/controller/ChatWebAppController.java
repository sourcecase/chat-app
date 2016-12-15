package com.github.sourcecase.chat.web.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.sourcecase.chat.service.api.ChatDTOFactory;
import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionDTO;
import com.github.sourcecase.chat.service.api.discussion.ChatMessageDTO;
import com.github.sourcecase.chat.service.api.groups.ChatGroupDTO;
import com.github.sourcecase.chat.service.api.users.ChatParticipantDTO;
import com.github.sourcecase.chat.service.api.users.ChatUserLoginDTO;
import com.github.sourcecase.chat.service.impl.discussion.ChatMessageDTOImpl;
import com.github.sourcecase.chat.service.impl.groups.ChatGroupDTOImpl;
import com.github.sourcecase.chat.service.impl.users.ChatParticipantDTOImpl;
import com.github.sourcecase.chat.service.impl.users.ChatUserLoginDTOImpl;
import com.github.sourcecase.chat.web.ChatPathConfiguration;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ChatWebAppController {

	private static final Logger LOGGER = Logger.getLogger(ChatWebAppController.class.getName());
	private final ChatDiscussionService chatMessageService;

	@Autowired
	private ChatDTOFactory chatDTOFactory;

	ChatWebAppController(@Autowired ChatDiscussionService chatMessageService) {

		this.chatMessageService = chatMessageService;
	}

	@MessageMapping("/chat/chatWebSocket")
	@SendTo("/chat/chatWebSocket")
	public WebChatMessage sendStomp(WebChatMessage message) throws Exception {
		LOGGER.info("sendStomp called " + message.getText() + message.getGroup() + message.getSenderName());
		return message;
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(ModelMap model, ServletRequest servletRequest, HttpSession httpSession) {
		LOGGER.log(Level.SEVERE, "index");
		return "index";
	}

	@RequestMapping("/greeting")
	public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "greetingview";
	}

	@ResponseBody
	@RequestMapping(path = "/blubb", method = RequestMethod.POST)
	public String blubb(ModelMap model, ServletRequest servletRequest, HttpSession httpSession) {
		LOGGER.log(Level.SEVERE, "blubb");
		return "{blubb}";
	}

	@RequestMapping(path = ChatPathConfiguration.CHAT_TEST, method = RequestMethod.GET)
	public String test(ModelMap model, ServletRequest servletRequest, HttpSession httpSession) {
		LOGGER.log(Level.SEVERE, "index");
		String chatGroupDTOJson = new ChatGroupDTOImpl(2, "aa").serializeToJson();
		LOGGER.log(Level.INFO, chatGroupDTOJson);
		ChatGroupDTO chatGroupDTO = chatDTOFactory.createFromJson(ChatGroupDTOImpl.class, chatGroupDTOJson);
		LOGGER.log(Level.INFO, chatGroupDTO.getName());

		String chatParticipantDTOJson = new ChatParticipantDTOImpl(4, "me").serializeToJson();
		LOGGER.log(Level.INFO, chatParticipantDTOJson);
		ChatParticipantDTO chatParticipantDTO = chatDTOFactory.createFromJson(ChatParticipantDTOImpl.class,
				chatParticipantDTOJson);
		LOGGER.log(Level.INFO, chatParticipantDTO.getName());

		String userLoginDTOJson = new ChatUserLoginDTOImpl("Alice1", "asdf").serializeToJson();
		LOGGER.log(Level.INFO, "Alice json: " + userLoginDTOJson);
		ChatUserLoginDTO userLoginDTO = chatDTOFactory.createFromJson(ChatUserLoginDTO.class, userLoginDTOJson);
		LOGGER.log(Level.INFO, userLoginDTO.getName());

		Calendar cal = Calendar.getInstance();

		// set Date portion to January 1, 1970
		cal.set(Calendar.YEAR, 1970);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.MILLISECOND, 0);

		String chatMessageDTOJson1 = new ChatMessageDTOImpl(5, "hello me", new Time(cal.getTime().getTime()),
				chatParticipantDTO).serializeToJson();
		LOGGER.log(Level.INFO, chatMessageDTOJson1);
		ChatMessageDTO chatMessageDTO1 = chatDTOFactory.createFromJson(ChatMessageDTOImpl.class, chatMessageDTOJson1);
		LOGGER.log(Level.INFO, "chatMessageDTO1 " + chatMessageDTO1.getSender().getName());

		String chatMessageDTOJson2 = new ChatMessageDTOImpl(6, "hello you", new Time(cal.getTime().getTime()),
				chatParticipantDTO).serializeToJson();
		LOGGER.log(Level.INFO, chatMessageDTOJson2);
		ChatMessageDTO chatMessageDTO2 = chatDTOFactory.createFromJson(ChatMessageDTOImpl.class, chatMessageDTOJson2);
		LOGGER.log(Level.INFO, "chatMessageDTO2 " + chatMessageDTO2.getSender().getName());

		List<ChatMessageDTO> chatMessages = new ArrayList<>();
		chatMessages.add(chatMessageDTO1);
		chatMessages.add(chatMessageDTO2);

		String chatDiscussionDTOJson = chatDTOFactory.createChatDiscussionDTO(chatGroupDTO, chatMessages)
				.serializeToJson();
		LOGGER.log(Level.INFO, chatDiscussionDTOJson);
		ChatDiscussionDTO chatDiscussionDTO = chatDTOFactory.createFromJson(ChatDiscussionDTO.class,
				chatDiscussionDTOJson);
		LOGGER.log(Level.INFO, "chatDiscussionDTO " + chatDiscussionDTO.getChatMessages().get(0).getText());
		return "index";
	}

}