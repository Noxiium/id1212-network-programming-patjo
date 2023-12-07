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
        <h1>Hello <%= request.getAttribute("usermail") %></h1>


        <form action="SelectSubjectServlet" method="get">
            <c:forEach items="${list}" var="element">  
                <label>
                    <input type="radio" name="selectedSubject" value="${element.subjectID}">
                    ${element.subjectText}
                    <input type="hidden" name="subjectText_${element.subjectID}" value="${element.subjectText}">
                </label><br>
            </c:forEach>
            <input type="submit" value="Select subject">
        </form>
    </body>
</html>






