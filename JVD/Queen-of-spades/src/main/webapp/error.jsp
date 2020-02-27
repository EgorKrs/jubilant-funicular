
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test = "${sessionScope.language ==  'ru'}">
               <fmt:setLocale value="ru"/>
</c:if>
<c:if test = "${sessionScope.language ==  'en'}">
               <fmt:setLocale value="en"/>
</c:if>
<fmt:setBundle basename="bundles.bundle"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exception</title>
</head>
<body>
<h2><fmt:message key="exception"/></h2>
<p>Message:
    <c:if test="${requestScope.exception == 'ERROR_IN_CREATE'}">
        <fmt:message key="ERROR_IN_CREATE"/>
    </c:if>
    <c:if test="${requestScope.exception == 'ERROR_IN_UPDATE'}">
        <fmt:message key="ERROR_IN_UPDATE"/>
    </c:if>
    <c:if test="${requestScope.exception == 'ERROR_IN_DELETE'}">
        <fmt:message key="ERROR_IN_DELETE"/>
    </c:if>
    <c:if test="${requestScope.exception == 'ERROR_IN_RECEIVE'}">
        <fmt:message key="ERROR_IN_RECEIVE"/>
    </c:if>
    <c:if test="${requestScope.exception == 'ERROR_IN_RECEIVE_ALL'}">
        <fmt:message key="ERROR_IN_RECEIVE_ALL"/>
    </c:if>
</body>
</html>
