
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>The ultimate Quiz Game!</title>
        <script>
        function toMainView() {
            window.location.href = 'mainView.jsp';
        }
    </script>
    </head>
    <body>
        <h1>Select a subject</h1>


        <form action="SelectSubjectServlet" method="get">
            <c:forEach items="${list}" var="element">  
                <label>
                    <input type="radio" name="selectedSubject" value="${element.subjectID}">
                    ${element.subjectText}
                    <input type="hidden" name="subjectText_${element.subjectID}" value="${element.subjectText}">
                </label><br>
            </c:forEach>
             <input type="hidden" name="source" value="receiveFirstQuestion">
            <input type="submit" value="Select subject">
            <input type="button" value="Main view" onclick="toMainView()">
        </form>
    </body>
</html>






