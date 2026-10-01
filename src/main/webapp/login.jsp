<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h2>Simple Login</h2>
    <!-- Sends parameters via POST to the LoginServlet mapped at /login -->
    <form action="login" method="POST">
        <label>Username:</label>
        <input type="text" name="username" required><br><br>

        <label>Role:</label>
        <input type="text" name="role" value="Administrator" required><br><br>

        <button type="submit">Log In</button>
    </form>
</body>
</html>