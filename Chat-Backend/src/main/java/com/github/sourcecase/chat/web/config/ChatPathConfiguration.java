package com.github.sourcecase.chat.web.config;

import com.github.sourcecase.chat.web.ChatWebAppInitializer;

public final class ChatPathConfiguration {

	public static String LOGIN_VALIDATE_URL = ChatWebAppInitializer.CHAT_ROOT_URL_PATH + "/login/validate";
	public static String LOGOUT_PERFORM_URL = ChatWebAppInitializer.CHAT_ROOT_URL_PATH + "/logout/perform";
	public static String LOGIN_URL = ChatWebAppInitializer.CHAT_ROOT_URL_PATH + "/login";
	public static String CHAT_WEB_SOCKET = ChatWebAppInitializer.CHAT_ROOT_URL_PATH + "/ChatWebSocketNeu";
	public static String CHAT_INDEX = ChatWebAppInitializer.CHAT_ROOT_URL_PATH + "/";

}
