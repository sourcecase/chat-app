package com.github.sourcecase.chat.service.api;

public interface ChatDTOFactory {

	<T> T createFromJson(Class<T> dtoType, String json);
}
