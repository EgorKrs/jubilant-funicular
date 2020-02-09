<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>ADMIN</title>
</head>
<body>
<script>
function startChat() {


let formData=new FormData();

formData.append('command', 'startChat');
console.log(formData.getAll('command'));
let xhr = new XMLHttpRequest();
xhr.open("POST", '/', true);



xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
xhr.send(formData);
console.log(xhr.response);
}
</script>
<h1>Hello ADMIN!</h1>


<form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="userCRUD" />
                    <input type="submit" name="CRUD_user" value="CRUD user"/>
                </form>
                <form method="post" action="MagicServlet">
                                    <input type="text" hidden name="command" value="newsCRUD" />
                                    <input type="submit" name="CRUD_news" value="CRUD news"/>
                                </form>
 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="startChat" />
                    <input type="submit" name="chat" value="сообщения"/>
                </form>

<a href="<c:url value="../game/game.jsp"/>">play</a>
 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="logout" />
                    <input type="submit" name="logout" value="logout"/>
                </form>
</body>
</html>