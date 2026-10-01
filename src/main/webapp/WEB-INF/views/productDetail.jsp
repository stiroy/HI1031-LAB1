<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Item Verification</title>
</head>
<body>
    <h2>Dummy Item Fetch Test</h2>
    <hr>
    <% if (request.getAttribute("item") != null) { %>
        <p><strong>Item ID:</strong> ${item.id}</p>
        <p><strong>Item Name:</strong> ${item.name}</p>
        <p><strong>Item Price:</strong> $${item.price}</p>
    <% } else { %>
        <p style="color:red;">No item data found in request scope!</p>
    <% } %>
</body>
</html>