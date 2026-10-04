<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ attribute name="activeTab" required="false" type="java.lang.String" %>

<header class="bg-slate-900 text-white border-b border-slate-800">
    <nav class="bg-slate-900 text-white shadow-md">
    <div class="max-w-6xl mx-auto px-4 py-3 flex justify-between items-center">
        <a href="${pageContext.request.contextPath}/app/index" class="text-xl font-bold tracking-wide text-teal-400">
            Webshop
        </a>
        
        <div class="flex items-center space-x-6 text-sm font-medium">
            <a href="${pageContext.request.contextPath}/app/index" class="hover:text-teal-300 transition">Home</a>
            <a href="${pageContext.request.contextPath}/app/status" class="hover:text-teal-300 transition">Status</a>

            <!-- Session Navigation Check -->
            <% if (request.getRemoteUser() != null) { %>
                    <a href="${pageContext.request.contextPath}/app/userProfile" 
                       class="text-xs text-slate-300 hover:text-teal-300 bg-slate-800 hover:bg-slate-700 px-3 py-1.5 rounded-full border border-slate-700 transition flex items-center space-x-1.5">
                        <span>👤</span>
                        <span class="font-semibold"><%= request.getRemoteUser() %></span>
                    </a>
                    <a href="${pageContext.request.contextPath}/app/logout" 
                       class="text-xs bg-red-500/20 hover:bg-red-500/30 text-red-300 border border-red-500/30 px-3 py-1.5 rounded-md transition">
                        Logout
                    </a>
                <% } else { %>
                    <a href="${pageContext.request.contextPath}/login.jsp" 
                       class="bg-teal-500 hover:bg-teal-400 text-slate-950 font-semibold px-3.5 py-1.5 rounded-md text-xs transition shadow-sm">
                        Sign In
                    </a>
                <% } %>
        </div>
    </div>
</nav>
</header>