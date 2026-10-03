<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<% request.setAttribute("pageTitle", "System Status Dashboard"); %>
<%@ include file="common/header.jsp" %>

<div class="max-w-4xl mx-auto space-y-6">

    <!-- Title & System State Header -->
    <div class="flex items-center justify-between bg-white p-6 rounded-xl shadow-sm border border-slate-200">
        <div>
            <h1 class="text-2xl font-bold text-slate-900">Webshop App Status</h1>
            <p class="text-sm text-slate-500 mt-1">Runtime health & infrastructure diagnostic panel</p>
        </div>
        <span class="inline-flex items-center px-3 py-1 rounded-full text-xs font-semibold bg-emerald-100 text-emerald-800">
            <span class="w-2 h-2 mr-2 bg-emerald-500 rounded-full animate-pulse"></span>
            ${status.deploymentStatus}
        </span>
    </div>

    <!-- Diagnostic Cards Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

        <!-- Environment & Server Info Card -->
        <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
            <h2 class="text-lg font-semibold text-slate-900 border-b border-slate-100 pb-3 mb-4">Server Environment</h2>
            <dl class="space-y-3 text-sm">
                <div class="flex justify-between">
                    <dt class="text-slate-500">Server Time:</dt>
                    <dd class="font-medium text-slate-800">${status.serverTime}</dd>
                </div>
                <div class="flex justify-between">
                    <dt class="text-slate-500">Java Runtime:</dt>
                    <dd class="font-medium text-slate-800">${status.javaVersion}</dd>
                </div>
                <div class="flex justify-between">
                    <dt class="text-slate-500">Servlet Container:</dt>
                    <dd class="font-medium text-slate-800 truncate max-w-[200px]" title="${status.serverInfo}">${status.serverInfo}</dd>
                </div>
                <div class="flex justify-between">
                    <dt class="text-slate-500">Heap Memory (Free / Total):</dt>
                    <dd class="font-mono text-xs text-slate-700">${status.freeMemoryMb} MB / ${status.totalMemoryMb} MB</dd>
                </div>
            </dl>
        </div>

        <!-- Database & Middleware Card -->
        <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200">
            <h2 class="text-lg font-semibold text-slate-900 border-b border-slate-100 pb-3 mb-4">Database Integration</h2>
            <dl class="space-y-4 text-sm">
                
                <!-- Driver Check -->
                <div>
                    <dt class="text-slate-500 mb-1">Postgres JDBC Driver:</dt>
                    <dd>
                        <span class="inline-block px-2.5 py-1 text-xs font-medium rounded-md ${status.driverFound ? 'bg-emerald-50 text-emerald-700 border border-emerald-200' : 'bg-red-50 text-red-700 border border-red-200'}">
                            ${status.driverStatusMessage}
                        </span>
                    </dd>
                </div>

                <!-- DB Connection Check -->
                <div>
                    <dt class="text-slate-500 mb-1">Database Connection Pool:</dt>
                    <dd>
                        <span class="inline-block px-2.5 py-1 text-xs font-medium rounded-md ${status.connectionEstablished ? 'bg-emerald-50 text-emerald-700 border border-emerald-200' : 'bg-red-50 text-red-700 border border-red-200'}">
                            ${status.connectionStatusMessage}
                        </span>
                    </dd>
                </div>

            </dl>
        </div>

    </div>
</div>

<%@ include file="common/footer.jsp" %>