window.onload = init;

function init() {
	console.log("init");
	showLogin();
}

function showChatMain() {
	console.log("showChatMain");
	if(chatParticipantAuthenticated !== null) {
		startChatting();
		$( "#showLogin" ).hide();
		$( "#showChatMain" ).show();
		$( "#showLoginError" ).hide();
	} else {
		showLogin();
		$( "#showLoginError" ).show();
	}
}

function showLogin() {
	console.log("showLogin");
	$( "#showLogin" ).show();
	$( "#showChatMain" ).hide();
	$( "#showLoginError" ).hide();
}

function showLogout() {
	console.log("showLogout");
	stopChatting();
	performLogout();
	showLogin();
}