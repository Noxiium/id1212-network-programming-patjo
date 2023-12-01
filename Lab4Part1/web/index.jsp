<%-- 
    Document   : index
    Created on : Dec 1, 2023, 10:39:55 AM
    Author     : PC
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Guess Number Game!</title>
</head>
<body>
    <h1>Guess the Number</h1>

    <form method="post" action="Controller">
        Guess a number between 1-100:
        <br>
        <input type="text" name="guess">
        <br>
        <input type="submit" value="Submit Guess">
    </form>

    <%-- Check if a guess exists in the request, and display it --%>
    <c:if test="${not empty param.guess}">
        <p>Your guess: ${param.guess}</p>
    </c:if>

</body>
</html>