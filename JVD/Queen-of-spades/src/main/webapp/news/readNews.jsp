<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>news crud</title>
    <%@include file="../user/user.jsp"%>
 <script>
  <%@include file="/js/news.js"%>
 </script>
</head>
<body>

<h1>news</h1><br />
<h2>Все новости</h2><br />

<c:forEach var="news" items="${requestScope.news}">
    <ul>
        дата: <c:out value="${news.last_update}"/><p></p>
        <c:out value="${news.text}"/><p>
     </ul>

    <hr />

</c:forEach>



</body>
</html>