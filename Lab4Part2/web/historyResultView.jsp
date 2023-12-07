
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>The ultimate Quiz Game!</title>
    </head>
    <body>
        <h1>Quiz Results</h1>


        <table border="1">
            <thead>
                <tr>
                    <th>User</th>
                    <th>Quiz Subject</th>
                    <th>Score</th>
                </tr>
            </thead>
            <c:forEach items="${list2}" var="element">  
                <tr>
                    <td>${element.userName}</td>
                    <td>${element.quizSubject}</td>
                    <td>${element.totalScore}</td>
                </tr>
            </c:forEach>
        </table>






        <ul>
            <li> <a href="http://localhost:8080/Lab4Part2/SelectSubjectServlet">Quizzes</a></li> 
        </ul>


    </body>
</html>