package com.github.sourcecase.chat.service.impl;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sourcecase.chat.service.api.ChatDTO;

public class AbstractChatDTO implements ChatDTO {

	@Override
	public String serializeToJson() {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter stringWriter = new StringWriter();

		try {
			mapper.writeValue(stringWriter, this);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return stringWriter.toString();
	}

}
