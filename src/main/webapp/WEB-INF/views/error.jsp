<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
</head>
<body>
<h1>An error occurred</h1>
<p style="color: red;">
    <c:out value="${errorMessage}"/>
</p>
<p>We are unable to process your request at this time.<br>
    Please try again later.
</p>
<br><br>
<a href="${pageContext.request.contextPath}/">
    <button type="button" class="update-button">Return to Index Page</button>
</a>
</body>
</html>