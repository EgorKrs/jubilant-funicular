<%--
  Created by IntelliJ IDEA.
  User: egor-
  Date: 21.02.2020
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${sessionScope.language ==  'ru'}">
    <fmt:setLocale value="ru"/>
</c:if>
<c:if test="${sessionScope.language ==  'en'}">
    <fmt:setLocale value="en"/>
</c:if>

<fmt:setBundle basename="bundles.bundle"/>
<html>
<head>
    <title><fmt:message key="addScore"/></title>
    <%@include file="../user/admin.jsp" %>
</head>
<body>
<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="addScore"/>
    <input type="number" name="sumOfMoney"/>
    <input class="button" type="submit" value="<fmt:message key="submit"/>">
</form>
<c:if test="${requestScope.status == '-1'}">
    <fmt:message key="SUM_OF_MONEY_MUST_BE_POSITIVE"/>"
</c:if>
<c:if test="${requestScope.status == '1'}">
    <fmt:message key="TRANSACTION_SUCCESS"/>
</c:if>
<c:if test="${requestScope.status == '2'}">
    <fmt:message key="exception"/>
</c:if>
<c:if test="${requestScope.status == '3'}">
    <fmt:message key="CAN_NOT_UPDATE_ACCOUNT"/>
</c:if>
</body>
</html>
