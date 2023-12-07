<%-- 
    Document   : resultView
    Created on : Dec 6, 2023, 4:47:57 PM
    Author     : Indiana Johan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>The ultimate Quiz Game!</title>
    </head>
    <body>
        <h1>Good Job!</h1>
        <h3>Your score is:  <%= request.getAttribute("totalScore") %></h3>
        
          <form action="SelectSubjectServlet" method="get">
              <input type="submit" value="Play again">
        </form>
        
    </body>
</html>
