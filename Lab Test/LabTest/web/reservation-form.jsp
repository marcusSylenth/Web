<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservation Form</title>
</head>
<body>
<h2>Reservation Form</h2>
<form action="reservation" method="post">
    <input type="hidden" name="action" value="${reservation != null ? 'update' : 'insert'}">
    <input type="hidden" name="reservationCode" value="${reservation != null ? reservation.reservationCode : ''}">
    <table>
        <tr>
            <td>User ID:</td>
            <td><input type="text" name="userId" value="${reservation != null ? reservation.userId : ''}" required></td>
        </tr>
        <tr>
            <td>Check In Date:</td>
            <td><input type="date" name="checkInDate" value="${reservation != null ? reservation.checkInDate : ''}" required></td>
        </tr>
        <tr>
            <td>Check Out Date:</td>
            <td><input type="date" name="checkOutDate" value="${reservation != null ? reservation.checkOutDate : ''}" required></td>
        </tr>
        <tr>
            <td>Room Type:</td>
            <td>
                <select name="roomType" required>
                    <c:forEach var="room" items="${roomDetails}">
                        <option value="${room.roomType}" ${reservation != null && reservation.roomType == room.roomType ? 'selected' : ''}>${room.roomType}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Room ID:</td>
            <td><input type="text" name="roomId" value="${reservation != null ? reservation.roomId : ''}" required></td>
        </tr>
        <tr>
            <td>No. of Customers:</td>
            <td><input type="number" name="noOfCustomers" value="${reservation != null ? reservation.noOfCustomers : ''}" required></td>
        </tr>
        <tr>
            <td>Total Price:</td>
            <td><input type="number" step="0.01" name="totalPrice" value="${reservation != null ? reservation.totalPrice : ''}" required></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="${reservation != null ? 'Update' : 'Save'}"></td>
        </tr>
    </table>
</form>
<a href="reservation?action=list">Back to List</a>
</body>
</html>
