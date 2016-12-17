window.onload = init;

function init() {
	console.log("init");
	showLogin();
}

function showLoginError(errorMessage) {
	$( "#showLoginError" ).html(errorMessage);
	$( "#showLoginError" ).show();
}

function showHideAll() {
	console.log("clean view");
	$( "#showLogin" ).hide();
	$( "#showRegister" ).hide();
	$( "#showChatMain" ).hide();
	$( "#showLoginError" ).hide();
}

function showChatMain() {
	showHideAll();
	console.log("showChatMain");
	if(chatParticipantAuthenticated !== null) {
		startChatting();
		$( "#showChatMain" ).show();
	} else {
		showLogin();
		showLoginError("Please login first.");
	}
}

function showLogin() {
	showHideAll();
	console.log("showLogin");
	$( "#showLogin" ).show();
}

function showRegister() {
	showHideAll();
	console.log("showRegister");
	$( "#showRegister" ).show();
}

function showLogout() {
	console.log("showLogout");
	stopChatting();
	performLogout();
	showLogin();
}