<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty pageTitle ? pageTitle : "Webshop"}</title>
    <!-- Tailwind CSS CDN -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-slate-50 text-slate-800 min-h-screen flex flex-col justify-between">

    <!-- Global Navigation Header -->
    <nav class="bg-slate-900 text-white shadow-md">
    <div class="max-w-6xl mx-auto px-4 py-3 flex justify-between items-center">
        <a href="${pageContext.request.contextPath}/app/index" class="text-xl font-bold tracking-wide text-teal-400">
            Webshop
        </a>
        
        <div class="flex items-center space-x-6 text-sm font-medium">
            <a href="${pageContext.request.contextPath}/app/index" class="hover:text-teal-300 transition">Home</a>
            <a href="${pageContext.request.contextPath}/app/status" class="hover:text-teal-300 transition">Status</a>

            <!-- Session Navigation Check -->
            <% if (session != null && session.getAttribute("user") != null) { %>
                <span class="text-xs text-slate-400 bg-slate-800 px-2.5 py-1 rounded-full border border-slate-700">
                    👤 <%= session.getAttribute("user") %>
                </span>
                <a href="${pageContext.request.contextPath}/app/logout" 
                   class="text-xs bg-red-500/20 hover:bg-red-500/30 text-red-300 border border-red-500/30 px-3 py-1.5 rounded-md transition">
                    Logout
                </a>
            <% } else { %>
                <a href="${pageContext.request.contextPath}/app/login" 
                   class="bg-teal-500 hover:bg-teal-400 text-slate-950 font-semibold px-3 py-1.5 rounded-md text-xs transition">
                    Sign In
                </a>
            <% } %>
        </div>
    </div>
</nav>

    <!-- Main Content Container Opens Here -->
    <main class="max-w-6xl mx-auto px-4 py-8 flex-grow w-full">