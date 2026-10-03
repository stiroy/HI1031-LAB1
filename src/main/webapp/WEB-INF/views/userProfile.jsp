<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
</head>
<body>
    <h2>Welcome to your Profile!</h2>
    <hr>
    <p><strong>Logged in User:</strong> ${sessionScope.currentUser.username}</p>
    <p><strong>Assigned Role:</strong> ${sessionScope.currentUser.role}</p>
</body>
</html>