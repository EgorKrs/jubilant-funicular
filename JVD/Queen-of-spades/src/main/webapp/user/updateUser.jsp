<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>

        <form method="post" action="">
                    <input type="text" hidden name="command" value="updateCurrentUser" />
                    <input type="text" hidden name="id" value="${user.id}" />
                    <li>login  <input type="text" name="newLogin" value="${user.login}" /> </li>
                    <li>password  <input type="text" name="newPassword" value="${user.password}" /></li>
                    <li>type  <input type="text" name="newType" value="${user.type}" /></li>
                    <li>avatar   <input type="text" name="newAvatar" value="${user.avatarId}" /></li>
                    <input type="submit" name="update" value="обновить"/>
        </form>
</body>