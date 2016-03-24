<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<title>Simple Chat System</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="//code.jquery.com/jquery-1.12.2.min.js"></script>
        <script src="/chat/javascript/chatAuthentication.js"></script>
        <script src="/chat/javascript/chatWebsocket.js"></script>
        <script src="/chat/javascript/chatViewHandler.js"></script>
</head>
    <body>
    	<h2>Welcome to the Chat</h2>
    	<div id="chatMenu">
    		<a onclick="showLogin()" href="#">Login</a>
    		<a onclick="showLogout()" href="#">Logout</a>
    		<a onclick="showChatMain()" href="#">Chat</a>
    	</div>
    	<br />
    	<div id="showLogin">
			<form id="loginForm">
				<div>
					<label>username</label>
					<input id="username" type="text" name="name">
				</div>
				<div>
					<label>password</label>
					<input id="password" type="password" name="password">		
				</div>
				<div>
					<button type="button" value="Login" onclick=validateLogin()>login</button>
				</div>
			</form>
			<div id="showLoginError" style="color: red;">
				Not logged in.
			</div>
		</div>
		<div id="showChatMain">
			<div id="wrapper">
	            <div id="writeMessage">
	                <form id="newMessageForm">
	                    <h4>Write new message</h4>
	                    <span>Name: <input type="text" name="nick_name" id="nick_name"></span>
	                    <span>Group: 
	                        <select id="chat_room"></select>
	                    </span>
						<br />
	                    <span>Message:
	                        <input type="text" name="description" id="new_message" onkeydown="if (event.keyCode == 13) document.getElementById('send_new_message_button').click()">
	                    </span>
	
	                    <input id="send_new_message_button" type="button" class="button" value="Send" onclick=formSubmit()>
	                </form>
	            </div>
	        </div>
	        
	        <div id="newChatMessage">
	        </div>
		</div>
    </body>
</html>