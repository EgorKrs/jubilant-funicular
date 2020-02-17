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

<body>
<h1><fmt:message key="menu"/></h1>


<form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="userCRUD" />
                    <input type="submit" name="CRUD_user" value='<fmt:message key="workWithUser"/>'/>
                </form>
                <form method="post" action="MagicServlet">
                                    <input type="text" hidden name="command" value="newsCRUD" />
                                    <input type="submit" name="CRUD_news" value='<fmt:message key="workWithNews"/>'/>
                                </form>
 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="startChat" />
                    <input type="submit" name="chat" value='<fmt:message key="chat"/>'/>
                </form>
<form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="prepareGame" />
                    <input type="submit" name="game" value='<fmt:message key="play"/>'/>
                </form>

 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="logout" />
                    <input type="submit" name="logout" value='<fmt:message key="logout"/>'/>
                </form>
</body>
</html>