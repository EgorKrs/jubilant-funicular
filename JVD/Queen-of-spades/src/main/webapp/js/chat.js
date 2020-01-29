let userName = null;
let websocket = null;

function init(login) {
	if ("WebSocket" in window) {
		userName=login;
        console.log("User is set.");

		websocket = new WebSocket('ws://localhost:9080/' + userName);
		websocket.onopen = function(data) {
			document.getElementById("main").style.display = "block";
			console.log("open");
		};

		websocket.onmessage = function(data) {
			setMessage(JSON.parse(data.data));
			console.log("message");
		};

		websocket.onerror = function(e) {
			alert('An error occured, closing application');
            console.log("error");
			cleanUp();
		};

		websocket.onclose = function(data) {
			cleanUp();
			console.log("close");
			let reason = (data.reason && data.reason !== null) ? data.reason : 'Goodbye';
			console.log(reason);
		};

	}
	else {
		alert("Websockets not supported");
	}
}

function cleanUp() {
	document.getElementById("main").style.display = "none";

	userName = null;
	websocket = null;
}

function sendMessage() {
	let messageContent = document.getElementById("message").value;

	let message = buildMessage(userName, messageContent);

	document.getElementById("message").value = '';

	setMessage(message);
	websocket.send(JSON.stringify(message));
}

function buildMessage(userName, message) {
	return {
		username : userName,
		message : message,

	};
}

function setMessage(msg) {
       console.log(msg);
	let currentHTML = document.getElementById('scrolling-messages').innerHTML;
	let newElem;

	if (msg.username === userName) {
		newElem = '<p style="background: #ebebe0;"><span>' + msg.username
				+ ' : ' + msg.message + '</span></p>';
	} else {
		newElem = '<p><span>' + msg.username + ' : ' + msg.message
				+ '</span></p>';
	}

	document.getElementById('scrolling-messages').innerHTML = currentHTML
			+ newElem;

}