<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="${not empty requestScope.pageTitle ? requestScope.pageTitle : 'User Profile'}">
    <div class="max-w-2xl mx-auto my-8 space-y-6">
        
        <!-- Header -->
        <div class="border-b border-slate-200 pb-4">
            <h1 class="text-2xl font-bold text-slate-900">User Profile</h1>
            <p class="text-sm text-slate-500">Manage your account information and preferences.</p>
        </div>

        <!-- Profile Details Card -->
        <div class="bg-white p-6 rounded-xl border border-slate-200 shadow-sm space-y-4">
            <div class="flex items-center space-x-4">
                <div class="w-12 h-12 rounded-full bg-slate-900 text-teal-400 flex items-center justify-center font-bold text-lg">
                    ${sessionScope.user.substring(0, 1).toUpperCase()}
                </div>
                <div>
                    <h2 class="text-base font-semibold text-slate-800">${sessionScope.user}</h2>
                    <span class="inline-block px-2 py-0.5 text-xs rounded bg-emerald-50 text-emerald-700 border border-emerald-200">
                        Active User
                    </span>
                </div>
            </div>

            <hr class="border-slate-100" />

            <div class="grid grid-cols-2 gap-4 text-xs font-mono">
                <div>
                    <span class="text-slate-400 block uppercase">Username</span>
                    <span class="text-slate-800 font-bold">${sessionScope.user}</span>
                </div>
                <div>
                    <span class="text-slate-400 block uppercase">Session ID</span>
                    <span class="text-slate-800 truncate block">${pageContext.session.id}</span>
                </div>
            </div>
        </div>

        <!-- Navigation Actions -->
        <div class="flex space-x-4">
            <a href="${pageContext.request.contextPath}/app/index" 
               class="px-4 py-2 bg-slate-100 hover:bg-slate-200 text-slate-700 text-xs font-semibold rounded-lg transition">
                &larr; Back to Catalog
            </a>
            <a href="${pageContext.request.contextPath}/app/logout" 
               class="px-4 py-2 bg-red-50 hover:bg-red-100 text-red-600 border border-red-200 text-xs font-semibold rounded-lg transition">
                Sign Out
            </a>
        </div>

    </div>
</t:layout>