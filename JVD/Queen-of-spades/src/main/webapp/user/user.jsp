<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="en"/>
<c:if test="${sessionScope.language ==  'ru'}">
    <fmt:setLocale value="ru"/>
</c:if>
<c:if test="${sessionScope.language ==  'en'}">
    <fmt:setLocale value="en"/>
</c:if>

<fmt:setBundle basename="bundles.bundle"/>
<html>
<head>
    <title>USER</title>

</head>
<body>
<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="setLanguage_RU"/>
    <input class="button" type="submit" value="RU">
</form>
<form method="post" action="MagicServlet">
    <input type="text" hidden name="command" value="setLanguage_EN"/>
    <input class="button" type="submit" value="EN">
</form>
<h1><fmt:message key="menu"/></h1>
                <form method="post" action="MagicServlet">
                                    <input type="text" hidden name="command" value="newsRead" />
                                    <input type="submit" name="newsRead" value="<fmt:message key="newsRead"/>"/>
                                </form>
 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="startChat" />
                    <input type="submit" name="chat" value="<fmt:message key="chat"/>"/>
                </form>
<form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="prepareGame" />
                    <input type="submit" name="game" value='<fmt:message key="play"/>'/>
                </form>

 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="logout" />
                    <input type="submit" name="logout" value="<fmt:message key="logout"/>"/>
                </form>
</body>
</html>