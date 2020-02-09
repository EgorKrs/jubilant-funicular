<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Title</title>
<%@include file="../user/admin.jsp"%>
</head>
<body>

        <li>Логин: <c:out value="${user.login}"/></li>
        <li>тип: <c:out value="${user.type}"/></li>
        <li>последнее изменение : <c:out value="${user.lastUpdate}"/></li>
        <li>дата создания  : <c:out value="${user.createDate}"/></li>
        <li>аватар : <c:out value="${user.avatarId}"/></li>
         <form method="post" action="">
                    <input type="text" hidden name="command" value="deleteCurrentUser" />
                    <input type="number" hidden name="id" value="${user.id}" />
                    <input type="submit" name="delete" value="Удалить"/>
                </form>

</body>