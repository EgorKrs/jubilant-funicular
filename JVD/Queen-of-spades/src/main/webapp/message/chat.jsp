<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.type ==  'ADMIN'}">
    <%@include file="../user/admin.jsp" %>
</c:if>
<c:if test="${sessionScope.type == 'USER'}">
    <%@include file="../user/user.jsp" %>
</c:if>
<!DOCTYPE html>
<link href="<c:url value='../css/chatStyle.css' />" rel="stylesheet">
<script src="<c:url value='../js/chat.js' />"></script>
<html>
<head>

    <meta charset="UTF-8">
    <title>Chat </title>


    <script>
        init(`${requestScope.login}`);
    </script>
</head>
<body>
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
<c:forEach var="msg" items="${requestScope.messages}">
    <script>
        setMessage(buildMessage(`${msg.userName}`, `${msg.message}`));
    </script>
</c:forEach>
</body>
</html>