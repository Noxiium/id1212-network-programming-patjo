<%-- 
    Document   : selectSubjectView.jsp
    Created on : Dec 5, 2023, 2:43:52 PM
    Author     : Indiana Johan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>The ultimate Quiz Game!</title>
    </head>
    <body>
        <h1><%= request.getAttribute("text") %></h1>


        <form action="QuestionServlet" method="get">
                    <p> <%= request.getAttribute("option1") %> <input type="checkbox" name="option1" value="true"> </p>
                    <p> <%= request.getAttribute("option2") %> <input type="checkbox" name="option2" value="true"> </p>
                    <p> <%= request.getAttribute("option3") %> <input type="checkbox" name="option3" value="true"> </p>
                    
            <input type="submit" value="Submit answer">
        </form>
    </body>