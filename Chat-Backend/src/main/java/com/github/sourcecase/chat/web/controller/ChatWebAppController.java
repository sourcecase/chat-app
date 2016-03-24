package com.github.sourcecase.chat.web.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ChatWebAppController {

	private static final Logger logger = Logger.getLogger(ChatWebSocketHandler.class.getName());

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(ModelMap model, ServletRequest servletRequest, HttpSession httpSession) {
		logger.log(Level.SEVERE, "index");
		return "index";
	}

}