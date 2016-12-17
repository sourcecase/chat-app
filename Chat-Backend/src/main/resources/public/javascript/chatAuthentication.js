var CHAT_LOGIN_VALIDATE_URL = "/login/validate";
var CHAT_REGISTRATION_URL = "/register/perform";
var CHAT_LOGOUT_URL = "/logout/perform";

var chatParticipantAuthenticated = null;

function addCSRFHeader(http) {

	/*
	var metaValues = document.getElementsByTagName("META");
    var _csrf = metaValues[0].content;
    var _csrf_header = metaValues[1].content;
    console.log("_csrf : " +_csrf );
    console.log("_csrf_header : " +_csrf_header );
    
    http.setRequestHeader(_csrf_header, _csrf);
    */
    
}

function validateLogin() {
	
	console.log("validateLogin " + CHAT_LOGIN_VALIDATE_URL);
	
	var http = new XMLHttpRequest();
	http.open("POST", CHAT_LOGIN_VALIDATE_URL, true);

    http.setRequestHeader("Content-Type", "application/json");
    http.setRequestHeader("Connection", "close");
    addCSRFHeader(http);
	
    // Body
	var form = document.getElementById("loginForm");
    var name = form.elements["username"].value;
    var password = form.elements["password"].value;
    var userLoginDtoJson = createChatUserLoginDtoJson(name, password);
    console.log(userLoginDtoJson);
    
    http.setRequestHeader("Content-length", userLoginDtoJson.length);
    
    http.onreadystatechange = function() {//Call a function when the state changes.
        if(http.readyState == 4 && http.status == 200) {
        	console.log("Login response came logged in noww.");
            var principal = createChatUserLoginDto(name, password);
            console.log("principal=" + JSON.stringify(principal));
        	chatParticipantAuthenticated = principal;
        	showChatMain();
        } else {
        	if(http.status != 200) {
        		console.log("Login response came error: " + http.status);
        		showLoginError(http.status);
        	}
        }
    }
    http.send(userLoginDtoJson);
    
}

function performRegistration() {
	
	console.log("performRegistration");
	
	var http = new XMLHttpRequest();
	http.open("POST", CHAT_REGISTRATION_URL, true);
	
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.setRequestHeader("Connection", "close");
    addCSRFHeader(http);
	
    // Body
	var form = document.getElementById("registerForm");
    var name = form.elements["username"].value;
    var password = form.elements["password"].value;
    var passwordAgain = form.elements["passwordAgain"].value;
    var userLoginDtoJson = createChatUserLoginDtoJson(name, password);
    console.log(userLoginDtoJson);
    
    http.setRequestHeader("Content-length", userLoginDtoJson.length);
    
    http.onreadystatechange = function() {//Call a function when the state changes.
        if(http.readyState == 4 && http.status == 200) {
        	console.log("Register response came.");
        	showLogin();
        	showLoginError("You are registered now.");
        } else {
        	if(http.status != 200) {
        		console.log("Register response came error: " + http.status);
        		showLoginError(http.status);
        	}
        }
    }
    http.send(userLoginDtoJson);
    
}

function performLogout() {
	
	console.log("performLogout");
    var http = new XMLHttpRequest();
    
    http.open("POST", CHAT_LOGOUT_URL, true);

    //Send the proper header information along with the request
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.setRequestHeader("Connection", "close");
    addCSRFHeader(http);

    http.onreadystatechange = function() {//Call a function when the state changes.
        if(http.readyState == 4 && http.status == 200) {
        	console.log("Logout response came logged out now.");
        	chatParticipantAuthenticated = null;
        }
    }
    
    http.send();
    
}
