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

function createChatMessageDto(text, chatParticipantDto) {
	return {"type":"message","id":-1, "text": text, "time":"19:09:52", "sender": chatParticipantDto};
}

function createChatMessageDtoJson(text, chatParticipantDto) {
	var chatMessageDto = createChatMessageDto(text, chatParticipantDto);
    return JSON.stringify(chatMessageDto);
}