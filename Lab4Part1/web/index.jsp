<%-- 
     Main View page for the Guess Number Game.
    - Includes a form enabling users to submit guesses, with restrictions to numbers between 1 and 100.
    - Conditional rendering based on the presence of the 'guess' and 'numberOfGuessesMade' parameters.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>Guess Number Game!</title>
</head>
<body>
    <h1>Guess the Number</h1>

    <form method="post" action="Controller">
        Guess a number between 1-100:
        <br>
        <input type="text" name="guess" pattern="[1-9]\d?|100" title="Enter a number between 1 and 100" required>
        <br>
        <input type="submit" value="Submit Guess">
    </form>

<%-- Check if a guess exists in the request, and display it --%>
<c:if test="${not empty param.guess}">
    <p>Your guess: ${param.guess}</p>
</c:if>

<c:if test="${not empty requestScope.numberOfGuessesMade}">
    <p>Number of guesses made: <%= request.getAttribute("numberOfGuessesMade") %></p>
</c:if>



</body>
</html>
