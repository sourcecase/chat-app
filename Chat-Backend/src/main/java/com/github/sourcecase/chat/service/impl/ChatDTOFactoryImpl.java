package com.github.sourcecase.chat.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sourcecase.chat.service.api.ChatDTOFactory;

@Service
public class ChatDTOFactoryImpl implements ChatDTOFactory {

	@Override
	public <T> T createFromJson(Class<T> dtoType, String json) {
		ObjectMapper mapper = new ObjectMapper();

		T result = null;
		try {
			result = (T) mapper.readValue(json, dtoType);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
