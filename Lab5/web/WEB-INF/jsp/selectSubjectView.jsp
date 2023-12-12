<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>The ultimate Quiz Game!</title>
    <script>
        function toMainView() {
            window.location.href = 'mainView';
        }
    </script>
</head>
<body>
    <h1>Select a subject</h1>

    <form action="questionView" method="get">
        <c:forEach var="element" items="${subjectList}">
            <label>
                <input type="radio" name="selectedSubject" value="${element.subjectID}">
                ${element.subjectText}
                <input type="hidden" name="subjectText_${element.subjectID}" value="${element.subjectText}">
            </label><br>
        </c:forEach>
        <input type="submit" value="Select subject">
        <input type="button" value="Main view" onclick="toMainView()">
    </form>
</body>
</html>
