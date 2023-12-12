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
        
        function validateForm() {
            var radios = document.getElementsByName('selectedSubject');
            var checked = false;
            for (var i = 0; i < radios.length; i++) {
                if (radios[i].checked) {
                    checked = true;
                    break;
                }
            }
            if (!checked) {
                alert('Please select a subject!');
                return false; // Prevent form submission if no subject is selected
            }
            return true; // Proceed with form submission
        }
    </script>
</head>
<body>
    <h1>Select a subject</h1>

    <form action="questionView" method="get" onsubmit="return validateForm()">
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
