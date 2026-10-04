<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Welcome - Webshop Platform" activeTab="catalog">

    <!-- Hero Banner Section -->
    <section class="bg-gradient-to-r from-slate-900 via-slate-800 to-teal-900 rounded-2xl p-10 text-white shadow-xl mb-10">
        <div class="max-w-2xl space-y-4">
            <span class="inline-block px-3 py-1 bg-teal-500/20 text-teal-300 text-xs font-semibold rounded-full uppercase tracking-wider">
                Webshop App
            </span>
            <h1 class="text-4xl font-extrabold tracking-tight">Explore Our Product Catalog</h1>
            <p class="text-slate-300 text-base leading-relaxed">
                Welcome, <strong class="text-teal-400 font-semibold">${sessionScope.user.username}</strong>. Browse our collection, manage items, or check system metrics.
            </p>
            <div class="pt-2 flex flex-wrap gap-4">
                <a href="${pageContext.request.contextPath}/app/itemDetail?id=101" 
                   class="bg-teal-500 hover:bg-teal-400 text-slate-950 font-semibold text-sm px-6 py-3 rounded-lg shadow-md transition duration-150">
                    Browse Products
                </a>
                <a href="${pageContext.request.contextPath}/app/status" 
                   class="bg-white/10 hover:bg-white/20 text-white font-medium text-sm px-6 py-3 rounded-lg border border-white/10 transition duration-150">
                    System Diagnostics
                </a>
            </div>
        </div>
    </section>

    <!-- Main Quick Access / Feature Cards -->
    <section class="grid grid-cols-1 md:grid-cols-3 gap-6">
        
        <t:featureCard 
            icon="🛍️" 
            iconBg="bg-teal-50 text-teal-600"
            title="Product Catalog"
            description="Search through stored inventory, view detailed product information, and manage items."
            linkHref="${pageContext.request.contextPath}/app/itemDetail?id=101"
            linkLabel="View products"
            linkColor="text-teal-600 hover:text-teal-700" />

        <t:featureCard 
            icon="👤" 
            iconBg="bg-slate-100 text-slate-700"
            title="User Session"
            description="Log in or manage your current active user session state within the web application."
            linkHref="${pageContext.request.contextPath}/app/login"
            linkLabel="Session settings"
            linkColor="text-slate-800 hover:text-slate-600" />

        <t:featureCard 
            icon="⚡" 
            iconBg="bg-emerald-50 text-emerald-600"
            title="Runtime Status"
            description="Inspect live runtime stats, database connection states, and driver availability."
            linkHref="${pageContext.request.contextPath}/app/status"
            linkLabel="Check status"
            linkColor="text-emerald-600 hover:text-emerald-700" />

    </section>

</t:layout>