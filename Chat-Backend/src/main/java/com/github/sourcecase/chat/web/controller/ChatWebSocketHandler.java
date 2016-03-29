package com.github.sourcecase.chat.web.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.github.sourcecase.chat.service.api.discussion.ChatDiscussionService;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	private ChatDiscussionService chatMessageService;

	@Autowired
	public ChatWebSocketHandler(ChatDiscussionService chatMessageService) {
		this.chatMessageService = chatMessageService;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Logger.getLogger(ChatWebSocketHandler.class.getName()).log(Level.SEVERE,
				"New session opened : " + session.getId());
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {

		Logger.getLogger(ChatWebSocketHandler.class.getName()).log(Level.SEVERE, "handleTextMessage start");
		try (JsonReader reader = Json.createReader(new StringReader(message.getPayload()))) {
			JsonObject jsonMessage = reader.readObject();

			Logger.getLogger(ChatWebSocketHandler.class.getName()).log(Level.SEVERE, jsonMessage.toString());

			if ("chatMessage".equals(jsonMessage.getString("action"))) {
				String text = jsonMessage.getString("text");
				String group = jsonMessage.getString("group");
				String senderName = jsonMessage.getString("senderName");
				Logger.getLogger(ChatWebSocketHandler.class.getName()).log(Level.SEVERE,
						"text: " + text + "group: " + group + "senderName: " + senderName);

				// Store message
				chatMessageService.addMessage(text, group, senderName);

				// Send response to all all clients
				JsonObject newChatMesssageResponse = createChatMessageResponse(text);
				sendToAllConnectedSessions(newChatMesssageResponse);
			}
		}
	}

	private JsonObject createChatMessageResponse(String text) {
		JsonProvider provider = JsonProvider.provider();
		JsonObject newChatMessageResponse = provider.createObjectBuilder().add("action", "newChatMessage")
				.add("text", text).build();
		return newChatMessageResponse;
	}

	private void sendToAllConnectedSessions(JsonObject message) {
		for (WebSocketSession session : sessions) {
			sendToSession(session, message);
		}
	}

	private void sendToSession(WebSocketSession session, JsonObject message) {
		try {
			String newMessage = message.toString();
			Logger.getLogger(ChatWebSocketHandler.class.getName()).log(Level.SEVERE,
					"Sending new message: " + newMessage);
			session.sendMessage(new TextMessage(newMessage));

		} catch (IOException ex) {
			sessions.remove(session);
			Logger.getLogger(ChatWebSocketHandler.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
