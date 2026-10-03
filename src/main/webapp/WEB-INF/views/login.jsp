<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<% request.setAttribute("pageTitle", "Sign In - Webshop"); %>
<%@ include file="common/header.jsp" %>

<div class="max-w-md mx-auto my-12 bg-white rounded-xl shadow-md border border-slate-200 p-8">
    <div class="mb-6 text-center">
        <h1 class="text-2xl font-bold text-slate-900">Sign in to your account</h1>
        <p class="text-xs text-slate-500 mt-1">Enter your credentials to access your session</p>
    </div>

    <!-- Error Alert Box -->
    <% if (request.getAttribute("errorMessage") != null) { %>
        <div class="mb-4 p-3 bg-red-50 border border-red-200 rounded-lg text-xs font-semibold text-red-700">
            ${errorMessage}
        </div>
    <% } %>

    <form action="j_security_check" method="POST" class="space-y-4">
        <div>
            <label for="username" class="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1">Username</label>
            <input type="text" name="j_username" required required 
                   class="w-full px-3 py-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent">
        </div>

        <div>
            <label for="password" class="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1">Password</label>
            <input type="password" name="j_password" required 
                   class="w-full px-3 py-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent">
        </div>

        <button type="submit" 
                class="w-full bg-teal-600 hover:bg-teal-700 text-white font-semibold text-sm py-2.5 rounded-lg transition duration-150 shadow-sm mt-2">
            Sign In
        </button>
    </form>
</div>

<%@ include file="common/footer.jsp" %>