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

        <form method="post" action="">
                    <input type="text" hidden name="command" value="updateCurrentUser" />
                    <input type="text" hidden name="id" value="${user.id}" />
                    <li><fmt:message key="login"/>  <input type="text" name="newLogin" value="${user.login}" /> </li>
                    <li><fmt:message key="password"/>  <input type="text" name="newPassword" value="${user.password}" /></li>
                    <li><fmt:message key="type"/>  <input type="text" name="newType" value="${user.type}" /></li>
                    <li><fmt:message key="avatar"/>   <input type="text" name="newAvatar" value="${user.avatarId}" /></li>
                    <input type="submit" name="update" value="<fmt:message key="update"/>"/>
        </form>
</body>