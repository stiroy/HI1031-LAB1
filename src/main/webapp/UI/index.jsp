<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Enterprise App - Status</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f4f6f9; }
        .card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .status { color: #2e7d32; font-weight: bold; }
    </style>
</head>
<body>
    <div class="card">
        <h1>Enterprise App Status</h1>
        <p>Deployment Status: <span class="status">ONLINE</span></p>
        <hr>
        <p><strong>Server Time:</strong> <%= new java.util.Date() %></p>
        <p><strong>Java Runtime:</strong> <%= System.getProperty("java.version") %></p>
        <p><strong>Server Info:</strong> <%= application.getServerInfo() %></p>
    </div>
</body>
</html>
