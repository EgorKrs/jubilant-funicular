<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>news crud</title>
    <%@include file="../user/admin.jsp"%>
 <script>
  <%@include file="/js/news.js"%>
 </script>
</head>
<body>

<h1>news CRUD</h1><br />

<h2>Создание новой новости</h2><br />

<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="createNews" />
    новость<br><textarea name="text" cols="40" rows="40" ></textarea></p>
    <input type="submit" value="Ok" name="Ok"><br>
</form>
<h2>Все новости</h2><br />

<c:forEach var="news" items="${requestScope.news}">
    <ul>
        дата: <c:out value="${news.last_update}"/><p></p>
        <textarea id="text${news.id}" name="text" cols="40" rows="40" >${news.text}</textarea></p>
     </ul>

<form method="post" onsubmit="fill(${news.id})" action="MagicServlet">
    <input type="text" hidden name="command" value="updateNews" />
    <input type="text" hidden name="id" value="${news.id}" />
    <input type="text" id="newText${news.id}" hidden name="text" value=document.getElementById('text${news.id}').value; />
    <input type="submit" value="обновить" name="обновить"><br>
</form>
<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="deleteNews" />
    <input type="text" hidden name="id" value="${news.id}" />
    <input type="submit" value="удалить" name="удалить"><br>
</form>
    <hr />

</c:forEach>



</body>
</html>