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

    <%@include file="../user/user.jsp"%>
 <script>
  <%@include file="/js/news.js"%>
 </script>
</head>
<body>


<h2><fmt:message key="allNews"/></h2><br />

<c:forEach var="news" items="${requestScope.news}">
    <ul>
        <fmt:message key="lastUpdate"/>: <c:out value="${news.last_update}"/><p></p>
        <c:out value="${news.text}"/><p>
     </ul>

    <hr />

</c:forEach>



</body>
</html>