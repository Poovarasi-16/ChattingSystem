<!DOCTYPE html>
<html>
<head>
<title>Chatting System</title>
</head>
<body>

<h2>Simple Chatting System</h2>

Username:
<input type="text" id="username">
<br><br>

Receiver:
<input type="text" id="receiver">
<br><br>

Message:
<input type="text" id="message">

<button onclick="sendMessage()">Send</button>

<hr>

<div id="chat"></div>

<script>

function sendMessage() {

    var sender = document.getElementById("username").value;
    var receiver = document.getElementById("receiver").value;
    var message = document.getElementById("message").value;

    fetch("message", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: "sender=" + encodeURIComponent(sender)
            + "&receiver=" + encodeURIComponent(receiver)
            + "&message=" + encodeURIComponent(message)
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        document.getElementById("message").value = "";
        loadMessages();
    });
}

function loadMessages() {

    var sender = document.getElementById("username").value;
    var receiver = document.getElementById("receiver").value;

    fetch("message?sender=" + encodeURIComponent(sender)
        + "&receiver=" + encodeURIComponent(receiver))
    .then(response => response.text())
    .then(data => {
        document.getElementById("chat").innerHTML = data;
    });
}

</script>

</body>
</html>