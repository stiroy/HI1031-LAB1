<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="activeTab" required="false" type="java.lang.String" %>

<!DOCTYPE html>
<html lang="en" class="h-full bg-slate-50">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="flex flex-col min-h-full font-sans text-slate-800 antialiased">

    <t:header activeTab="${activeTab}" />

    <main class="flex-grow container mx-auto px-4 py-8">
        <jsp:doBody />
    </main>

    <t:footer />

</body>
</html>