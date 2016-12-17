var WEB_SOCKET_URL = "ws://localhost:8080/chatWebSocket";
var socket = null;
var stompClient = null;
var receivedMessages = [];

function onMessage(event) {
    var device = JSON.parse(event.data);
    if (device.action === "newChatMessage") {
        printNewMessage(device.text);
    }
}

function sendMessage(text, group, senderName) {
    var newCreateMessage = createChatCreateMessageDtoJson(text, group, senderName);
    console.log(newCreateMessage);
    sendNewMessageStomp(newCreateMessage);
}

function sendNewMessageStomp(newMessage) {
    stompClient.send("/sendMessage", {}, newMessage);
}

function showForm() {
    document.getElementById("writeMessage").style.display = '';
}

function formSubmit() {
    var form = document.getElementById("newMessageForm");
    form.elements["nick_name"].value = chatParticipantAuthenticated.name;
    var senderName = form.elements["nick_name"].value;
    var group = form.elements["chat_room"].value;
    var text = form.elements["new_message"].value;
    sendMessage(text, group, senderName);
    document.getElementById("new_message").value = "";
}
var req;



function initWebSocket() {
	console.log("initWebSocket");
	connect();
}

function connect() {
    var socket = new SockJS('/chatWebSocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/newMessages', function (greeting) {
            console.log("chatWebSocket/newMessages: " + greeting.body);
            var chatMessage = JSON.parse(greeting.body);
            printNewMessage(chatMessage);
        });
    });
}

function printNewMessage(chatMessage) {
	receivedMessages.push(chatMessage);
	printMessages(receivedMessages);
}

function printMessages(allMessages) {
	var content = document.getElementById("newChatMessage");
	var messageList = "<ul>";

	var i = 0;
	for (i = 0; i < allMessages.length; i++) {
	    var currentMessage = allMessages[i];
	    var messageText = currentMessage.text;
        var messageTime = currentMessage.time;
        var senderName = currentMessage.sender.name;
		messageList = messageList + "<li><i>" + messageTime + "</i> " + senderName + ": " + messageText + "</li>";
	}

	messageList = messageList + "</ul>";
	content.innerHTML = messageList;
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

function chatGroupsCallback() {
	if (req.readyState == 4) {
        if (req.status == 200) {
        	console.log("hello chatRoomsCallback");
        	console.log(req.responseText);
        	console.log(req);

        	var chatGroupListDTO = JSON.parse(req.responseText);
        	var chatGroupArray = chatGroupListDTO.chatGroups;

        	var htmlChatRooms = "";
        	var i = 0;
        	for (i = 0; i < chatGroupArray.length; i++) {
        		var chatGroupName = chatGroupArray[i].name;
        		htmlChatRooms = htmlChatRooms + "<option name='type' value='" + chatGroupName + "'>" + chatGroupName + "</option>";
        	}

        	var form = document.getElementById("chat_room");
        	form.innerHTML = htmlChatRooms;
        }
	}
}

function startChatting() {
	console.log("startChatting");
	if(socket == null) {
		console.log("startChatting init");
		var form = document.getElementById("newMessageForm");
	    form.elements["nick_name"].value = chatParticipantAuthenticated.name;
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
