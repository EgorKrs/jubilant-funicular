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
 <script>
  <%@include file="/js/news.js"%>
 </script>
</head>
<body>

<h1><fmt:message key="workWithNews"/></h1><br />

<h2><fmt:message key="createNewNews"/></h2><br />

<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="createNews" />
    <fmt:message key="news"/><br><textarea name="text" cols="40" rows="40" ></textarea></p>
    <input type="submit" value="Ok" name="<fmt:message key="submit"/>"><br>
</form>
<h2><fmt:message key="allNews"/></h2><br />

<c:forEach var="news" items="${requestScope.news}">
    <ul>
        <fmt:message key="lastUpdate"/>: <c:out value="${news.last_update}"/><p></p>
        <textarea id="text${news.id}" name="text" cols="40" rows="40" >${news.text}</textarea></p>
     </ul>

<form method="post" onsubmit="fill(${news.id})" action="MagicServlet">
    <input type="text" hidden name="command" value="updateNews" />
    <input type="text" hidden name="id" value="${news.id}" />
    <input type="text" id="newText${news.id}" hidden name="text" value=document.getElementById('text${news.id}').value; />
    <input type="submit" value="обновить" name="<fmt:message key="update"/>"><br>
</form>
<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="deleteNews" />
    <input type="text" hidden name="id" value="${news.id}" />
    <input type="submit" value="удалить" name="<fmt:message key="delete"/>"><br>
</form>
    <hr />

</c:forEach>



</body>
</html>