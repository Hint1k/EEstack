<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Update Courier</title>
  <style>
    .error { color: red; }
  </style>
</head>
<body>
<h1>Update Courier</h1>

<form method="post" action="${pageContext.request.contextPath}/api/couriers/${courier.id}">
  <input type="hidden" name="_method" value="PUT"/>  <!-- Hidden field to simulate PUT -->
  <input type="hidden" name="id" value="${courier.id}"/> <!-- Ensure ID is sent -->

  <div>
    <label>First Name:</label>
    <input type="text" name="firstName" value="${courier.firstName}" required/>
    <c:if test="${not empty errors['firstName']}">
      <span class="error">${errors['firstName']}</span>
    </c:if>
  </div>

  <div>
    <label>Last Name:</label>
    <input type="text" name="lastName" value="${courier.lastName}" required/>
    <c:if test="${not empty errors['lastName']}">
      <span class="error">${errors['lastName']}</span>
    </c:if>
  </div>

  <div>
    <label>Phone:</label>
    <input type="text" name="phone" value="${courier.phone}" required/>
    <c:if test="${not empty errors['phone']}">
      <span class="error">${errors['phone']}</span>
    </c:if>
  </div>

  <div>
    <button type="submit">Update Courier</button>
  </div>
</form>

<p>
  <a href="${pageContext.request.contextPath}/api/couriers">
    <button type="button" class="update-button">Return back to Courier list</button>
  </a>
</p>

<br><br>
<a href="${pageContext.request.contextPath}/">
  <button type="button" class="update-button">Return to Index Page</button>
</a>

</body>
</html>