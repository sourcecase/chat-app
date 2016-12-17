function createChatUserLoginDto(name, password) {
	return {"type":"userLogin", "name": name, "password": password};
}

function createChatUserLoginDtoJson(username, password) {
	var userChatLoginDto = createChatUserLoginDto(username, password);
    return JSON.stringify(userChatLoginDto);
}

function createChatParticipantDto(id, name) {
	return {"type":"participant", "id": id, "name": name};
}

function createChatParticipantDtoJson(username, password) {
	var chatParticipantDto = createChatUserLoginDto(username, password);
    return JSON.stringify(chatParticipantDto);
}

function createChatCreateMessageDto(text, group, senderName) {
	return {"type":"createMessage", "text": text, "group": group, "senderName": senderName};
}

function createChatCreateMessageDtoJson(text, group, senderName) {
	var chatCreateMessageDto = createChatCreateMessageDto(text, group, senderName);
    return JSON.stringify(chatCreateMessageDto);
}