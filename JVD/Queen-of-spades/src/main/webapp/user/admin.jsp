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
xhr.open("POST", '', true);



xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
xhr.send(formData);
console.log(xhr.response);
}
</script>
<h1>Hello ADMIN!</h1>

<a href="<c:url value='user/userCRUD'/>">CRUD user</a>

 <form method="post" action="MagicServlet">
                    <input type="text" hidden name="command" value="startChat" />
                    <input type="submit" name="chat" value="сообщения"/>
                </form>

<a href="<c:url value="../game/game.jsp"/>">play</a>
<a href="<c:url value='/logout' />">Logout</a>
</body>
</html>