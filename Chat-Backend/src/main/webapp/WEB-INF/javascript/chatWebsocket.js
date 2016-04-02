var socket = null;
var receivedMessages = [];

function onMessage(event) {
    var device = JSON.parse(event.data);
    if (device.action === "newChatMessage") {
        printNewMessage(device.text);
    }
}

function sendMessage(senderName, text, group) {
	console.log("sendMessage senderName: " + senderName + " text:" + text + " group:" + group);
    var MessageAction = {
    		"action":"chatMessage", 
    		"text":text,
    		"group":group,
    		"senderName": senderName
    };
    socket.send(JSON.stringify(MessageAction));
}

function printNewMessage(text) {
	console.log(text);
	receivedMessages.push(text);
	printMessages(receivedMessages);
}

function printMessages(allMessages) {
	var content = document.getElementById("newChatMessage");
	var messageList = "<ul>";
	
	var i = 0;
	for (i = 0; i < allMessages.length; i++) {
		messageList = messageList + "<li>" + allMessages[i] + "</li>";
	}
	
	messageList = messageList + "</ul>";
	content.innerHTML = messageList;
}

function showForm() {
    document.getElementById("writeMessage").style.display = '';
}

function formSubmit() {
    var form = document.getElementById("newMessageForm");
    form.elements["nick_name"].value = chatParticipantAuthenticated.username;
    var senderName = form.elements["nick_name"].value;
    var group = form.elements["chat_room"].value;
    var text = form.elements["new_message"].value;
    sendMessage(senderName, text, group);
    document.getElementById("new_message").value = "";
}
var req;

function chatGroupsCallback() {
	if (req.readyState == 4) {
        if (req.status == 200) {
        	console.log("hello chatRoomsCallback");
        	console.log(req.responseText);
        	console.log(req);
        	
        	var chatRooms = JSON.parse(req.responseText);
        	
        	var htmlChatRooms = "";
        	var i = 0;
        	for (i = 0; i < chatRooms.length; i++) {
        		htmlChatRooms = htmlChatRooms + "<option name='type' value='" + chatRooms[i] + "'>" + chatRooms[i] + "</option>";
        	}
        	
        	var form = document.getElementById("chat_room");
        	form.innerHTML = htmlChatRooms;
        }
	}
}

function initWebSocket() {
	console.log("initWebSocket");
	socket = new WebSocket("ws://localhost:8080/chat/ChatWebSocketNeu")
	socket.onmessage = onMessage;
}

function retrievChatGroups() {
	console.log("retrievChatGroups");
	var url = "chatGroups";
	if (typeof XMLHttpRequest != "undefined") {
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	req.open("GET", url, true);
	req.onreadystatechange = chatGroupsCallback;
	req.send(null);
}

function startChatting() {
	console.log("startChatting");
	if(socket == null) {
		console.log("startChatting init");
		var form = document.getElementById("newMessageForm");
	    form.elements["nick_name"].value = chatParticipantAuthenticated.username;
		retrievChatGroups();
		initWebSocket();
	} else {
		console.log("chat already running");
	}
}

function stopChatting() {
	console.log("stopChatting");
	if(socket !== null) {
		socket.close();
		socket = null;
	}
}
