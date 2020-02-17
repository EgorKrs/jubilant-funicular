<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:if test = "${sessionScope.language ==  'ru'}">
               <fmt:setLocale value="ru"/>
</c:if>
<c:if test = "${sessionScope.language ==  'en'}">
               <fmt:setLocale value="en"/>
</c:if>

<fmt:setBundle basename="bundles.bundle" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>

</head>
<body>

    <div class="form">

        <h1> <fmt:message key="login.welcome"/></h1><br>
 <form method="post" action="MagicServlet">
          <input type="text" hidden name="command" value="setLanguage_RU" />
         <input class="button" type="submit" value="RU">
        </form>
        <form method="post" action="MagicServlet">
          <input type="text" hidden name="command" value="setLanguage_EN" />
                 <input class="button" type="submit" value="EN">
         </form>
        <form method="post" action="MagicServlet">
            <input type="text" required placeholder='<fmt:message key="login"/>' name="login"><br>
            <input type="password" required placeholder='<fmt:message key="password"/>' name="password"><br><br>
            <input class="button" type="submit" value='<fmt:message key="login.enter"/>'>
        </form>




    </div>
</body>
</html>