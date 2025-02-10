<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>Index Page</title>
</head>
<body>
<h1>Index Page</h1>
<hr>
<a href="${pageContext.request.contextPath}/api/couriers/*">
    <button type="button" class="update-button">
        List of Couriers
    </button>
</a>
<br><br>
<a href="${pageContext.request.contextPath}/api/addresses">
    <button type="button" class="update-button">
        List of Addresses
    </button>
</a>
</body>
</html>