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
            <div class="space-x-6 text-sm font-medium">
                <a href="${pageContext.request.contextPath}/app/itemDetail?id=101" class="hover:text-teal-300 transition">Products</a>
                <a href="${pageContext.request.contextPath}/app/about" class="hover:text-teal-300 transition">About</a>
            </div>
        </div>
    </nav>

    <!-- Main Content Container Opens Here -->
    <main class="max-w-6xl mx-auto px-4 py-8 flex-grow w-full">