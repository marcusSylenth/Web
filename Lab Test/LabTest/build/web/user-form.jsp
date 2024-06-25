<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Form</title>
</head>
<body>
<h2>User Form</h2>
<form action="user" method="post">
    <input type="hidden" name="action" value="${user != null ? 'update' : 'insert'}">
    <table>
        <tr>
            <td>User ID:</td>
            <td><input type="text" name="userId" value="${user != null ? user.userId : ''}" required></td>
        </tr>
        <tr>
            <td>User Name:</td>
            <td><input type="text" name="userName" value="${user != null ? user.userName : ''}" required></td>
        </tr>
        <tr>
            <td>Gender:</td>
            <td>
                <select name="gender" required>
                    <option value="Male" ${user != null && user.gender == 'Male' ? 'selected' : ''}>Male</option>
                    <option value="Female" ${user != null && user.gender == 'Female' ? 'selected' : ''}>Female</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Contact No:</td>
            <td><input type="text" name="contactNo" value="${user != null ? user.contactNo : ''}" required></td>
        </tr>
        <tr>
            <td>ID:</td>
            <td><input type="text" name="id" value="${user != null ? user.id : ''}" required></td>
        </tr>
        <tr>
            <td>Age:</td>
            <td><input type="number" name="age" value="${user != null ? user.age : ''}" required></td>
        </tr>
        <tr>
            <td>Address:</td>
            <td><input type="text" name="address" value="${user != null ? user.address : ''}" required></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="${user != null ? 'Update' : 'Save'}"></td>
        </tr>
    </table>
</form>
<a href="user?action=list">Back to List</a>
</body>
</html>
