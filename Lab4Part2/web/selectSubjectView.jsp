<%-- 
    Document   : selectSubjectView.jsp
    Created on : Dec 5, 2023, 2:43:52 PM
    Author     : Indiana Johan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello <%= request.getAttribute("usermail") %></h1>
        <div class = "results">
            <h2> Check Box Example: </h2>
            <!-- Declare input box with type as checkbox, we have also assigned name to this element-->
            <%= request.getAttribute("usermail") %> <input type = "checkbox" name = "checkbox1" >
            </br>
            <%= request.getAttribute("usermail") %> <input type = "checkbox" name = "checkbox2" >
            <p id = "result"> </p>
            </div>
    </body>
</html>
