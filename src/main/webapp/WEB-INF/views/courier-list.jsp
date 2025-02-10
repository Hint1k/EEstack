<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>List of Couriers</title>
</head>
<body>
<h1>List of Couriers</h1>
<a href="${pageContext.request.contextPath}/api/couriers?action=new">
    <button type="button">Add Courier</button>
</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Phone</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="courier" items="${couriers}">
        <tr>
            <td>${courier.id}</td>
            <td>${courier.firstName}</td>
            <td>${courier.lastName}</td>
            <td>${courier.phone}</td>
            <td>
                <a href="${pageContext.request.contextPath}/api/couriers/${courier.id}">
                    <button type="button" class="btn">Update Courier</button>
                </a>
                <form action="${pageContext.request.contextPath}/api/couriers/${courier.id}" method="post"
                      onsubmit="return confirm('Are you sure you want to delete this courier?');">
                    <input type="hidden" name="_method" value="delete"/>
                    <button type="submit" class="btn">Delete Courier</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<button type="button" onclick="history.go(-1)" class="go-button">Go to Previous Page</button>
<br><br>
<a href="${pageContext.request.contextPath}/">
    <button type="button" class="update-button">Return to Index Page</button>
</a>
</body>
</html>