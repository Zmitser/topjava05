<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Meal</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<section>
    <h2><a href="">Home</a></h2>
    <h3>Edit meal</h3>
    <hr>



    <form:form commandName="userMeal"  method="post" action="/save">
        <form:hidden path="id"></form:hidden>
        <dl>
            <dt>DateTime:</dt>
            <dd><form:input  type="datetime-local"  path="dateTime"></form:input></dd>
        </dl>
        <dl>
            <dt>Description:</dt>
            <dd><form:input path="description" size="40"></form:input></dd>
        </dl>
        <dl>
            <dt>Calories:</dt>
            <dd><form:input type="number" path="calories"></form:input></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form:form>
</section>
</body>
</html>
