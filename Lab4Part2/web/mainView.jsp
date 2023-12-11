
<%-- This JSP view provides the user with options to navigate to different sections:
      - Quizzes: Will show available quizzes to take
      - Past results: Will show past quiz results for all users  --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The ultimate Quiz Game!</title>
</head>
<body>
    <h1>WELCOME TO THE ULTIMATE QUIZ GAME !</h1>

    <ul>
        <li> <a href="http://localhost:8080/Lab4Part2/SelectSubjectServlet">Quizzes</a></li>
        <li> <a href="http://localhost:8080/Lab4Part2/ResultServlet">Past results</a></li>
    </ul>


</body>
</html>