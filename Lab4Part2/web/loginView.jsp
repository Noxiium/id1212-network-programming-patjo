
<%-- This JSP view displays the login page for the Quiz Game.
     Users are required to enter an email and password to log in. --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>The ultimate Quiz Game!</title>
</head>
<body>
    <h1>Login</h1>

    <form method="post" action="LoginHandlerServlet">
        <br/>
        <p>Username: <input type="email" name="usermail" required></p>
        <p>Password: <input type="password" name="password" required></p>
        <p><input type="submit" value="Login"></p>
    </form>

</body>
</html>