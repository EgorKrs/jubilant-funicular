<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setBundle basename="bundles.bundle" />
<c:if test = "${sessionScope.language ==  'ru'}">
               <fmt:setLocale value="ru"/>
</c:if>
<c:if test = "${sessionScope.language ==  'en'}">
               <fmt:setLocale value="en"/>
</c:if>
<html>
<head>

<%@include file="../user/admin.jsp"%>
</head>
<body>

<h1><fmt:message key="workWithUser"/></h1><br />

<h2><fmt:message key="allUsers"/></h2><br />

<c:forEach var="user" items="${requestScope.users}">
    <ul>

        <li><fmt:message key="login"/>: <c:out value="${user.login}"/></li>
        <li><fmt:message key="type"/>: <c:out value="${user.type}"/></li>
        <li><fmt:message key="lastUpdate"/> : <c:out value="${user.lastUpdate}"/></li>
        <li><fmt:message key="createDate"/>  : <c:out value="${user.createDate}"/></li>

        <form method="post" action="">
            <input type="text" hidden name="command" value="delete" />
            <input type="number" hidden name="id" value="${user.id}" />
            <input type="submit" name="delete" value="Удалить"/>
        </form>

        <form method="post" action="">
            <input type="text" hidden name="command" value="update" />
            <input type="number" hidden name="id" value="${user.id}" />
            <input type="submit" value="Редактированть"/>
        </form>
    </ul>
    <hr />

</c:forEach>

<h2><fmt:message key="createNewUser"/></h2><br />

<form method="post" action="">

    <input type="text" hidden name="command" value="create" />
    <label><input type="text" name="login"></label><fmt:message key="login"/><br>
    <label><input type="text" name="password"></label><fmt:message key="password"/><br>
    <label><input type="text" name="type"></label><fmt:message key="type"/><br>
    <input type="submit" value="Ok" name="<fmt:message key="submit"/>"><br>
</form>

</body>
</html>