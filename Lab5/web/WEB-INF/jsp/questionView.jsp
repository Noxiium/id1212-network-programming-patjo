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


    <h1>${currentQuestion.getText()}</h1>


    <form action="" method="post">
        <p>
            <input type="checkbox" name="selectedOptions0" value="true" id="option0" />
            <label for="option0">${currentQuestion.options[0]}</label>
        </p>

        <p>
            <input type="checkbox" name="selectedOptions1" value="true" id="option1" />
            <label for="option1">${currentQuestion.options[1]}</label>
        </p>

        <p>
            <input type="checkbox" name="selectedOptions2" value="true" id="option2" />
            <label for="option2">${currentQuestion.options[2]}</label>
        </p>

        <input type="submit" value="Next Question">

    </form>
</body>
</body>