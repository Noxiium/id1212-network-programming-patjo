
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The ultimate Quiz Game!</title>
</head>
<body>
    <h1>Login</h1>

    <h2>Wrong password, please try again</h2>
      <form method="post" action="ServletController">
        <br/>
        <p>Username: <input type="text" name="usermail" required></p>
        <p>Password: <input type="password" name="password" required></p>
        <p><input type="submit" value="Login"></p>
    </form>

</body>
</html>