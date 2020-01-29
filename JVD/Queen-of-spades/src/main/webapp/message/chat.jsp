<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chat - Websockets</title>
    <style>
    <%@include file="/css/chatStyle.css"%>
    </style>
   <script type="text/javascript">
    <%@include file="/js/chat.js"%>
   </script>
</head>
<body onload="init('${requestScope.login}')">
<div id="main" class="main" style="display: none">
    <div id="scrolling-messages" class="scrolling-messages"></div>
    <div class="message-label">
        <span>Enter message:</span>
    </div>
    <div class="message-section">
        <div>
            <textarea id="message"></textarea>
        </div>
        <div style="float: right">
            <input type="button" value="submit" onclick="sendMessage();"
                   class="button" />
        </div>
    </div>
</div>
</body>
</html>