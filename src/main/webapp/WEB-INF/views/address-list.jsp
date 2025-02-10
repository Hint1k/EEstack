<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>List of Addresses</title>
    <style>
        .btn {
            margin-right: 5px;
        }
    </style>
</head>
<body>
<h1>List of Addresses:</h1>
<hr>
<div id="container">
    <div id="content">
        <a href="${pageContext.request.contextPath}/api/addresses?action=new">
            <button type="button">Add Address</button>
        </a>
        <br><br>
        <table border="1">
            <tr>
                <th>Country Name</th>
                <th>City Name</th>
                <th>Street Name</th>
                <th>House Number</th>
                <th>Courier ID</th>
                <th>Action</th>
            </tr>
            <c:forEach var="address" items="${addresses}">
                <tr>
                    <td>${address.countryName}</td>
                    <td>${address.cityName}</td>
                    <td>${address.streetName}</td>
                    <td>${address.houseNumber}</td>
                    <td>${address.courier.id}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/api/addresses/${address.id}">
                            <button type="button" class="btn">Update Address</button>
                        </a>
                        <form action="${pageContext.request.contextPath}/api/addresses/${address.id}" method="post"
                              onsubmit="return confirm('Are you sure you want to delete this address?');">
                            <input type="hidden" name="_method" value="delete"/>
                            <button type="submit" class="btn">Delete Address</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<br>
<button type="button" onclick="history.go(-1)" class="go-button">Go to Previous Page</button>
<br><br>
<a href="${pageContext.request.contextPath}/">
    <button type="button" class="update-button">Return to Index Page</button>
</a>
</body>
</html>