<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User List</title>
</head>
<body>
<h2>User List</h2>
<table border="1" width="50%">
    <tr>
        <th>User ID</th>
        <th>User Name</th>
        <th>Gender</th>
        <th>Contact No</th>
        <th>ID</th>
        <th>Age</th>
        <th>Address</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="user" items="${listUser}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.userName}</td>
            <td>${user.gender}</td>
            <td>${user.contactNo}</td>
            <td>${user.id}</td>
            <td>${user.age}</td>
            <td>${user.address}</td>
            <td>
                <a href="user?action=edit&id=${user.userId}">Edit</a>
                <a href="user?action=delete&id=${user.userId}" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="user?action=new">Add New User</a>
</body>
</html>