<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ attribute name="activeTab" required="false" type="java.lang.String" %>

<header class="bg-slate-900 text-white border-b border-slate-800">
    <div class="container mx-auto px-4 py-4 flex items-center justify-between">
        <a href="${pageContext.request.contextPath}/app/index" class="text-xl font-bold tracking-tight text-teal-400">
            Webshop
        </a>
        <nav class="space-x-4 text-sm font-medium">
            <a href="${pageContext.request.contextPath}/app/index" 
               class="${activeTab eq 'catalog' ? 'text-teal-400 font-semibold' : 'text-slate-300 hover:text-white'}">
                Catalog
            </a>
            <a href="${pageContext.request.contextPath}/app/status" 
               class="${activeTab eq 'status' ? 'text-teal-400 font-semibold' : 'text-slate-300 hover:text-white'}">
                Status
            </a>
        </nav>
    </div>
</header>