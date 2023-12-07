<html>
<head>
    <title>Guess Number Game!</title>
</head>
<body>
    <h1>YOU WON!</h1>

    <c:if test="${not empty param.guess}">
    <p>Your guess: ${param.guess} is correct! </p>
</c:if>

<c:if test="${not empty requestScope.numberOfGuessesMade}">
    <p>Total number of guesses made: <%= request.getAttribute("numberOfGuessesMade") %></p>
</c:if>

    
    <form method="get" action="Controller">
        Do you wanna restart the game?
        <br>
        <input type="submit" value="Restart">
    </form>

</body>
</html>
