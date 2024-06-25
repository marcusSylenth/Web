<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation List</title>
</head>
<body>
<h2>Reservation List</h2>
<table border="1" width="80%">
    <tr>
        <th>Reservation Code</th>
        <th>User ID</th>
        <th>Check In Date</th>
        <th>Check Out Date</th>
        <th>Room Type</th>
        <th>Room ID</th>
        <th>No. of Customers</th>
        <th>Total Price</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="reservation" items="${listReservations}">
        <tr>
            <td>${reservation.reservationCode}</td>
            <td>${reservation.userId}</td>
            <td>${reservation.checkInDate}</td>
            <td>${reservation.checkOutDate}</td>
            <td>${reservation.roomType}</td>
            <td>${reservation.roomId}</td>
            <td>${reservation.noOfCustomers}</td>
            <td>${reservation.totalPrice}</td>
            <td>
                <a href="reservation?action=edit&code=${reservation.reservationCode}">Edit</a>
                <a href="reservation?action=delete&code=${reservation.reservationCode}" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="reservation?action=new">Add New Reservation</a>
</body>
</html>
