<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>USER</title>

</head>
<body>
<h1>"nav bar"</h1>



                <form method="post" action="MagicServlet">
                                    <input type="text" hidden name="command" value="newsRead" />
                                    <input type="submit" name="newsRead" value="news Read"/>
                                </form>
 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="startChat" />
                    <input type="submit" name="chat" value="сообщения"/>
                </form>
<form method="post" action="../game/game.jsp">
                    <input type="text" hidden name="command" value="startGame" />
                    <input type="submit" name="game" value="play"/>
                </form>

 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="logout" />
                    <input type="submit" name="logout" value="logout"/>
                </form>
</body>
</html>