<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Admin Dashboard">
    <t:pageHeader 
        title="Admin Control Center" 
        subtitle="Manage product catalog, user permissions, and system metrics." />

    <!-- Metric Summary Row -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div class="bg-slate-900 border border-slate-800 rounded-xl p-5">
            <p class="text-xs font-medium text-slate-400 uppercase tracking-wider">Catalog Management</p>
            <h3 class="text-2xl font-bold text-teal-400 mt-2">${totalProducts != null ? totalProducts : 0} Products</h3>
            <p class="text-xs text-slate-500 mt-1">Active inventory & pricing control</p>
        </div>
        <div class="bg-slate-900 border border-slate-800 rounded-xl p-5">
            <p class="text-xs font-medium text-slate-400 uppercase tracking-wider">User Administration</p>
            <h3 class="text-2xl font-bold text-sky-400 mt-2">${totalUsers != null ? totalUsers : 0} Accounts</h3>
            <p class="text-xs text-slate-500 mt-1">RBAC user & role assignments</p>
        </div>
        <div class="bg-slate-900 border border-slate-800 rounded-xl p-5">
            <p class="text-xs font-medium text-slate-400 uppercase tracking-wider">System Health</p>
            <h3 class="text-2xl font-bold text-emerald-400 mt-2">Operational</h3>
            <p class="text-xs text-slate-500 mt-1">PostgreSQL connection active</p>
        </div>
    </div>

    <!-- Quick Navigation Modules -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
        <!-- Catalog Control -->
        <a href="${pageContext.request.contextPath}/app/adminCatalog" 
           class="group bg-slate-900 hover:bg-slate-850 border border-slate-800 hover:border-teal-500/50 p-6 rounded-xl transition shadow-lg flex flex-col justify-between">
            <div>
                <div class="w-12 h-12 bg-teal-500/10 text-teal-400 rounded-lg flex items-center justify-center text-2xl mb-4 group-hover:scale-110 transition-transform">
                    📦
                </div>
                <h3 class="text-lg font-bold text-slate-100 group-hover:text-teal-400 transition">Catalog Management</h3>
                <p class="text-sm text-slate-400 mt-2">
                    Add new items, update stock levels, edit pricing, and adjust categories in `T_products`.
                </p>
            </div>
            <div class="mt-6 flex items-center text-xs font-semibold text-teal-400">
                <span>Manage Catalog</span>
                <span class="ml-1 group-hover:translate-x-1 transition-transform">→</span>
            </div>
        </a>

        <!-- User & RBAC Management -->
        <a href="${pageContext.request.contextPath}/app/adminUsers" 
           class="group bg-slate-900 hover:bg-slate-850 border border-slate-800 hover:border-sky-500/50 p-6 rounded-xl transition shadow-lg flex flex-col justify-between">
            <div>
                <div class="w-12 h-12 bg-sky-500/10 text-sky-400 rounded-lg flex items-center justify-center text-2xl mb-4 group-hover:scale-110 transition-transform">
                    👥
                </div>
                <h3 class="text-lg font-bold text-slate-100 group-hover:text-sky-400 transition">User Administration</h3>
                <p class="text-sm text-slate-400 mt-2">
                    View active system users, inspect assigned roles (`CUSTOMER`, `EMPLOYEE`, `ADMIN`), and edit permissions.
                </p>
            </div>
            <div class="mt-6 flex items-center text-xs font-semibold text-sky-400">
                <span>Manage Users</span>
                <span class="ml-1 group-hover:translate-x-1 transition-transform">→</span>
            </div>
        </a>

        <!-- System & Database Status -->
        <a href="${pageContext.request.contextPath}/app/adminStatus" 
           class="group bg-slate-900 hover:bg-slate-850 border border-slate-800 hover:border-purple-500/50 p-6 rounded-xl transition shadow-lg flex flex-col justify-between">
            <div>
                <div class="w-12 h-12 bg-purple-500/10 text-purple-400 rounded-lg flex items-center justify-center text-2xl mb-4 group-hover:scale-110 transition-transform">
                    ⚡
                </div>
                <h3 class="text-lg font-bold text-slate-100 group-hover:text-purple-400 transition">System Status</h3>
                <p class="text-sm text-slate-400 mt-2">
                    Check DB connectivity via `DBManager`, inspect runtime stats, and view `status.jsp`.
                </p>
            </div>
            <div class="mt-6 flex items-center text-xs font-semibold text-purple-400">
                <span>View Status</span>
                <span class="ml-1 group-hover:translate-x-1 transition-transform">→</span>
            </div>
        </a>
    </div>
</t:layout>