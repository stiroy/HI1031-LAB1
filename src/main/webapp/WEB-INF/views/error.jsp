<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" isErrorPage="true" %>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Webshop</title>
    <style>
        body {
            font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            margin: 0;
            padding: 40px 20px;
            display: flex;
            justify-content: center;
        }
        .error-card {
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            max-width: 600px;
            width: 100%;
            padding: 32px;
            border-top: 6px solid #e63946;
        }
        h1 {
            margin-top: 0;
            color: #1d3557;
            font-size: 1.8rem;
        }
        .status-code {
            display: inline-block;
            background-color: #f1faee;
            color: #e63946;
            font-weight: bold;
            padding: 4px 12px;
            border-radius: 4px;
            font-size: 0.9rem;
            margin-bottom: 16px;
        }
        .message-box {
            background-color: #f8f9fa;
            border-left: 4px solid #457b9d;
            padding: 12px 16px;
            margin: 20px 0;
            font-size: 1rem;
            color: #1d3557;
        }
        .actions {
            margin-top: 24px;
        }
        .btn {
            display: inline-block;
            background-color: #1d3557;
            color: #ffffff;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 4px;
            font-weight: 500;
            transition: background-color 0.2s;
        }
        .btn:hover {
            background-color: #457b9d;
        }
        details {
            margin-top: 24px;
            background: #2b2d42;
            color: #edf2f4;
            padding: 12px;
            border-radius: 6px;
            font-family: monospace;
            font-size: 0.85rem;
            overflow-x: auto;
        }
        summary {
            cursor: pointer;
            color: #a8dadc;
            font-weight: bold;
        }
        pre {
            margin-top: 10px;
            white-space: pre-wrap;
            word-wrap: break-word;
        }
    </style>
</head>
<body>

<div class="error-card">
    <!-- HTTP Status Code -->
    <div class="status-code">
        HTTP Status: ${pageContext.response.status}
    </div>

    <h1>Oops! Something went wrong.</h1>

    <!-- Display custom error message set in UIHandler or Controller -->
    <div class="message-box">
        <strong>Details:</strong>
        <p style="margin: 8px 0 0 0;">
            ${not empty requestScope.errorMessage ? requestScope.errorMessage : "An unexpected error occurred while processing your request."}
        </p>
    </div>

    <div class="actions">
        <a href="${pageContext.request.contextPath}/app/itemDetail?id=101" class="btn">Return to Catalog</a>
    </div>

    <!-- Dev/Debug Stack Trace Block (Injected by Servlet Container if uncaught) -->
    <% if (exception != null) { %>
        <details>
            <summary>View Stack Trace (Developer Debug Info)</summary>
            <pre><%
                exception.printStackTrace(new PrintWriter(out));
            %></pre>
        </details>
    <% } %>
</div>

</body>
</html>