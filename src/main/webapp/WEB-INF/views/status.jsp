<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<% request.setAttribute("pageTitle", "System Status & JNDI Diagnostics"); %>
<%@ include file="common/header.jsp" %>

<div class="max-w-5xl mx-auto space-y-6">

    <!-- Title & Status Header -->
    <div class="flex items-center justify-between bg-white p-6 rounded-xl shadow-sm border border-slate-200">
        <div>
            <h1 class="text-2xl font-bold text-slate-900">System Status & Diagnostics</h1>
            <p class="text-xs text-slate-500 mt-1">Runtime health, JNDI lookup checks, and database pool state</p>
        </div>
        <span class="inline-flex items-center px-3 py-1 rounded-full text-xs font-semibold bg-emerald-100 text-emerald-800">
            <span class="w-2 h-2 mr-2 bg-emerald-500 rounded-full animate-pulse"></span>
            ${status.deploymentStatus}
        </span>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">

        <!-- JNDI & Database Infrastructure Card -->
        <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200 flex flex-col justify-between space-y-4">
            <div>
                <h2 class="text-base font-bold text-slate-900 border-b border-slate-100 pb-3 mb-4 flex items-center justify-between">
                    <span>Database & JNDI Infrastructure</span>
                    <span class="text-xs font-mono font-normal text-slate-400">PostgreSQL</span>
                </h2>

                <dl class="space-y-4 text-xs">
                    <!-- JNDI Resource Path -->
                    <div>
                        <dt class="text-slate-500 mb-1 font-semibold uppercase tracking-wider">JNDI Lookup Path:</dt>
                        <dd class="font-mono bg-slate-100 text-slate-800 px-2.5 py-1.5 rounded border border-slate-200 break-all">
                            ${status.jndiResourceName}
                        </dd>
                    </div>

                    <!-- JNDI Resolution Check -->
                    <div>
                        <dt class="text-slate-500 mb-1 font-semibold uppercase tracking-wider">JNDI Naming Resolution:</dt>
                        <dd>
                            <span class="inline-block px-2.5 py-1 text-xs font-medium rounded-md ${status.jndiResolved ? 'bg-emerald-50 text-emerald-700 border border-emerald-200' : 'bg-red-50 text-red-700 border border-red-200'}">
                                ${status.jndiStatusMessage}
                            </span>
                        </dd>
                    </div>

                    <!-- DB Connection Check -->
                    <div>
                        <dt class="text-slate-500 mb-1 font-semibold uppercase tracking-wider">Connection Pool Checkout:</dt>
                        <dd>
                            <span class="inline-block px-2.5 py-1 text-xs font-medium rounded-md ${status.connectionEstablished ? 'bg-emerald-50 text-emerald-700 border border-emerald-200' : 'bg-red-50 text-red-700 border border-red-200'}">
                                ${status.connectionStatusMessage}
                            </span>
                        </dd>
                    </div>

                    <!-- Query Latency -->
                    <div>
                        <dt class="text-slate-500 mb-1 font-semibold uppercase tracking-wider">Ping Latency (SELECT 1):</dt>
                        <dd class="font-mono text-sm font-bold text-slate-800">
                            ${status.dbQueryLatencyMs >= 0 ? status.dbQueryLatencyMs : 'N/A'} <span class="text-xs font-normal text-slate-500">ms</span>
                        </dd>
                    </div>
                </dl>
            </div>
        </div>

        <!-- Server Environment & Session Card -->
        <div class="bg-white p-6 rounded-xl shadow-sm border border-slate-200 flex flex-col justify-between space-y-4">
            <div>
                <h2 class="text-base font-bold text-slate-900 border-b border-slate-100 pb-3 mb-4 flex items-center justify-between">
                    <span>Container & Runtime State</span>
                    <span class="text-xs font-normal text-slate-400">Tomcat</span>
                </h2>

                <dl class="space-y-3 text-xs">
                    <div class="flex justify-between py-1 border-b border-slate-50">
                        <dt class="text-slate-500">Current User Identity:</dt>
                        <dd class="font-semibold text-teal-700">${status.loggedInUser}</dd>
                    </div>
                    <div class="flex justify-between py-1 border-b border-slate-50">
                        <dt class="text-slate-500">Active HTTP Sessions:</dt>
                        <dd class="font-bold text-slate-800">${status.activeSessions}</dd>
                    </div>
                    <div class="flex justify-between py-1 border-b border-slate-50">
                        <dt class="text-slate-500">Tomcat Uptime:</dt>
                        <dd class="font-mono font-medium text-slate-800">${status.serverUptime}</dd>
                    </div>
                    <div class="flex justify-between py-1 border-b border-slate-50">
                        <dt class="text-slate-500">Server Local Time:</dt>
                        <dd class="font-medium text-slate-800">${status.serverTime}</dd>
                    </div>
                    <div class="flex justify-between py-1 border-b border-slate-50">
                        <dt class="text-slate-500">Java Runtime Version:</dt>
                        <dd class="font-medium text-slate-800">JDK ${status.javaVersion}</dd>
                    </div>
                    <div class="flex justify-between py-1">
                        <dt class="text-slate-500">Heap Memory (Free / Total):</dt>
                        <dd class="font-mono text-slate-700">${status.freeMemoryMb} MB / ${status.totalMemoryMb} MB</dd>
                    </div>
                </dl>
            </div>
        </div>

    </div>
</div>

<%@ include file="common/footer.jsp" %>