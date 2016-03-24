var CHAT_LOGIN_VALIDATE_URL = "/chat/login/validate";
var CHAT_LOGOUT_URL = "/chat/logout/perform";

var chatParticipantAuthenticated = null;

function addCSRFHeader(http) {
	
	var metaValues = document.getElementsByTagName("META");
    var _csrf = metaValues[0].content;
    var _csrf_header = metaValues[1].content;
    console.log("_csrf : " +_csrf );
    console.log("_csrf_header : " +_csrf_header );
    
    http.setRequestHeader(_csrf_header, _csrf);
    
}

function showErrorToUser(error) {
	$( "#showLoginError" ).html("Error " + error);
	$( "#showLoginError" ).hide();
	$( "#showLoginError" ).show();
}

function validateLogin() {
	
	console.log("validateLogin");
	
	var http = new XMLHttpRequest();
	http.open("POST", CHAT_LOGIN_VALIDATE_URL, true);
	
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.setRequestHeader("Connection", "close");
    addCSRFHeader(http);
	
    // Body
	var form = document.getElementById("loginForm");
    var username = form.elements["username"].value;
    var password = form.elements["password"].value;
    var userDetails = {"username": username, "password": password};
    var paramsJson = JSON.stringify(userDetails);
    console.log(paramsJson);
    
    http.setRequestHeader("Content-length", paramsJson.length);
    
    http.onreadystatechange = function() {//Call a function when the state changes.
        if(http.readyState == 4 && http.status == 200) {
        	console.log("Login response came logged in now.");
        	chatParticipantAuthenticated = userDetails;
        } else {
        	if(http.status != 200) {
        		console.log("Login response came error: " + http.status);
        		showErrorToUser(http.status);
        	}
        }
    }
    http.send(paramsJson);
    
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
