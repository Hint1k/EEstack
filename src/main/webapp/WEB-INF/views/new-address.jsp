<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Address Form</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>Please fill in the Address form</h1>
<hr>
<h2>New Address:</h2>

<form method="post" action="${pageContext.request.contextPath}/api/addresses">
    <table>
        <tbody>
        <tr>
            <td><label for="countryName">Country name:</label></td>
            <td><input type="text" id="countryName" name="countryName" value="${address.countryName}" required/></td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${not empty errors['countryName']}">
                    <span class="error">${errors['countryName']}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td><label for="cityName">City name:</label></td>
            <td><input type="text" id="cityName" name="cityName" value="${address.cityName}" required/></td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${not empty errors['cityName']}">
                    <span class="error">${errors['cityName']}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td><label for="streetName">Street name:</label></td>
            <td><input type="text" id="streetName" name="streetName" value="${address.streetName}" required/></td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${not empty errors['streetName']}">
                    <span class="error">${errors['streetName']}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td><label for="houseNumber">House number:</label></td>
            <td><input type="text" id="houseNumber" name="houseNumber" value="${address.houseNumber}" required/></td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${not empty errors['houseNumber']}">
                    <span class="error">${errors['houseNumber']}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td><label for="courierId">Courier:</label></td>
            <td>
                <select id="courierId" name="courierId" required>
                    <option value="">Choose...</option>
                    <c:forEach var="courier" items="${couriers}">
                        <option value="${courier.id}"
                            ${address.courier != null && address.courier.id == courier.id ? 'selected' : ''}>
                                ${courier.firstName} ${courier.lastName}
                        </option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <c:if test="${not empty errors['courierId']}">
                    <span class="error">${errors['courierId']}</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">Create Address</button>
            </td>
        </tr>
        </tbody>
    </table>
</form>
<br><br>
<p>
    <a href="${pageContext.request.contextPath}/api/addresses">
        <button type="button" class="update-button">Return back to Address list</button>
    </a>
</p>
<br><br>
<a href="${pageContext.request.contextPath}/">
    <button type="button" class="update-button">Return to Index Page</button>
</a>
</body>
</html>