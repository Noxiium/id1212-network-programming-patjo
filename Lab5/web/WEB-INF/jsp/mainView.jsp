<%-- 
    Document   : mainView
    Created on : 9 Dec 2023, 18:13:03
    Author     : patricialagerhult
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
       <title>The ultimate Quiz Game!</title>
    </head>
    
    <body>
        <h1>Hello ${username}!</h1>
        <h1>WELCOME TO THE ULTIMATE QUIZ GAME !</h1>
        
         <!-- <form action="${pageContext.request.contextPath}/main" method="get">
        <input type="submit" value="Main"> -->
        
    <ul>
        <li> <a href="http://localhost:8080/Lab5/selectSubject">Quizzes</a></li>
        <li> <a href="http://localhost:8080/Lab5/result">Past results</a></li>
    </ul>
        
        
        
        
    </form>
    </body>
</html>
