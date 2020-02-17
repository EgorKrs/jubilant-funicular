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

        <li>"<fmt:message key="login"/>: <c:out value="${user.login}"/></li>
        <li>"<fmt:message key="type"/>: <c:out value="${user.type}"/></li>
        <li>"<fmt:message key="lastUpdate"/> : <c:out value="${user.lastUpdate}"/></li>
        <li>"<fmt:message key="createDate"/>  : <c:out value="${user.createDate}"/></li>
        <li>"<fmt:message key="avatar"/> : <c:out value="${user.avatarId}"/></li>
         <form method="post" action="">
                    <input type="text" hidden name="command" value="deleteCurrentUser" />
                    <input type="number" hidden name="id" value="${user.id}" />
                    <input type="submit" name="delete" value=""<fmt:message key="delete"/>"/>
                </form>

</body>