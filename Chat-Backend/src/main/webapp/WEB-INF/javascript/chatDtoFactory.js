function createUserLoginDto(username, password) {
	return {"type":"userLogin", "name": username, "password": password};
}

function createUserLoginDtoJson(username, password) {
	var userLoginDto = createUserLoginDto(username, password);
    return JSON.stringify(userLoginDto);
}