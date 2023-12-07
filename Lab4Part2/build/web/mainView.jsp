
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>The ultimate Quiz Game!</title>
</head>
<body>
    <h1>Welcome  <%= request.getAttribute("usermail") %> !</h1>

    <ul>
        <li> <a href="http://localhost:8080/Lab4Part2/SelectSubjectServlet">Quizzes</a></li>
        <li><a href="https://localhost:8080/Lab4Part2/ResultServlet">Past results</a></li>
    </ul>


</body>
</html>