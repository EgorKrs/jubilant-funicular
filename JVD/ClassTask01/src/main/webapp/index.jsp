<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Plugin tester</title>
</head>

<body>
    <h1>Just task</h1>
    <form action = "servlet-parameters" method = "GET">
    <table border = "0">


    <tr>
        <td><b>Parameter1</b></td>
        <td><input type = "text" name = "text"
            value = "http://localhost/some-test-url" size = "70" maxlength="255"/></td>
    </tr>



    <tr>
        <td colspan = "2"><input type = "submit" value = "Start test"/></td>
    </tr>
    <tr>
    <td>${status}</td>
    </tr>
    </table>
    </form>
</body>
</html>
