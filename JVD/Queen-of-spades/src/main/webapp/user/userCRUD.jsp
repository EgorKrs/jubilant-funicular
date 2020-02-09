<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
<%@include file="../user/admin.jsp"%>
</head>
<body>

<h1>User CRUD</h1><br />

<h2>Все пользователи</h2><br />

<c:forEach var="user" items="${requestScope.users}">
    <ul>

        <li>Логин: <c:out value="${user.login}"/></li>
        <li>тип: <c:out value="${user.type}"/></li>
        <li>последнее изменение : <c:out value="${user.lastUpdate}"/></li>
        <li>дата создания  : <c:out value="${user.createDate}"/></li>

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

<h2>Создание нового пользователя</h2><br />

<form method="post" action="">

    <input type="text" hidden name="command" value="create" />
    <label><input type="text" name="login"></label>логин<br>
    <label><input type="text" name="password"></label>пароль<br>
    <label><input type="text" name="type"></label>тип<br>
    <input type="submit" value="Ok" name="Ok"><br>
</form>

</body>
</html>