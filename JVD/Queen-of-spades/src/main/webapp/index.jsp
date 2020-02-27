<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="en"/>
<c:if test = "${sessionScope.language ==  'ru'}">
               <fmt:setLocale value="ru"/>
</c:if>
<c:if test="${sessionScope.language ==  'en'}">
    <fmt:setLocale value="en"/>
</c:if>

<fmt:setBundle basename="bundles.bundle"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>authorization</title>
    <script src="<c:url value='js/index.js' />"></script>
</head>
<body>
<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="setLanguage_RU"/>
    <input class="button" type="submit" value="RU">
</form>
<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="setLanguage_EN"/>
    <input class="button" type="submit" value="EN">
</form>
<h1><fmt:message key="login.welcome"/></h1><br>

<div class="form">
    <h1><p onclick="hide('registration')">
        <fmt:message key="authorization"/>
        <p onclick="hide('authorization')"> <fmt:message key="registration"/></h1>
    <br>
    <form method="post" action="MagicServlet" id="authorization">
        <input type="text" required placeholder='<fmt:message key="login"/>' name="login"><br>
        <input type="password" required placeholder='<fmt:message key="password"/>' name="password"><br>
        <input class="button" type="submit" value='<fmt:message key="login.enter"/>'>
    </form>
    <form method="post" action="MagicServlet" id="registration" hidden>
        <input type="text" hidden name="command" value="createNewUser"/>
        <input type="text" required placeholder='<fmt:message key="login"/>' name="login"><br>
        <input type="password" required placeholder='<fmt:message key="password"/>' name="password"><br>
        <input class="button" type="submit" value='<fmt:message key="registration"/>'>
    </form>
    <c:if test="${sessionScope.error == 'CAN_NOT_CREATE_SUCH_USER'}">
        <h1><fmt:message key="CAN_NOT_CREATE_SUCH_USER"/></h1><br>
    </c:if>
</div>

</body>
</html>