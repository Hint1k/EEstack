<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Courier Form</title>
    <style>
        .error { color: red; }
    </style>
</head>
<body>
<h1>Create New Courier</h1>

<form method="post" action="${pageContext.request.contextPath}/api/couriers">
    <div>
        <label>First Name:</label>
        <input type="text" name="firstName" value="${courier.firstName}" required/>
        <c:if test="${not empty errors['firstName']}">
            <span class="error">${errors['firstName']}</span>
        </c:if>
    </div>
    <br>
    <div>
        <label>Last Name:</label>
        <input type="text" name="lastName" value="${courier.lastName}" required/>
        <c:if test="${not empty errors['lastName']}">
            <span class="error">${errors['lastName']}</span>
        </c:if>
    </div>
    <br>
    <div>
        <label>Phone:</label>
        <input type="text" name="phone" value="${courier.phone}" required/>
        <c:if test="${not empty errors['phone']}">
            <span class="error">${errors['phone']}</span>
        </c:if>
    </div>
    <br>
    <div>
        <button type="submit">Create Courier</button>
    </div>
</form>
<br><br>
<p>
    <a href="${pageContext.request.contextPath}/api/couriers">
        <button type="button">Return to Courier List</button>
    </a>
</p>
<br><br>
<p>
    <a href="${pageContext.request.contextPath}/">
        <button type="button">Return to Index Page</button>
    </a>
</p>
</body>
</html>