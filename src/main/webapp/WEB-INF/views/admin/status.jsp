<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="System Status & JNDI Diagnostics">
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
                        <div>
                            <dt class="text-slate-500 mb-1 font-semibold uppercase tracking-wider text-[10px]">JNDI Lookup Path:</dt>
                            <dd class="font-mono bg-slate-100 text-slate-800 px-2.5 py-1.5 rounded border border-slate-200 break-all">
                                ${status.jndiResourceName}
                            </dd>
                        </div>

                        <t:statusRow title="JNDI Naming Resolution" status="${status.jndiResolved}" message="${status.jndiStatusMessage}" />
                        <t:statusRow title="Connection Pool Checkout" status="${status.connectionEstablished}" message="${status.connectionStatusMessage}" />

                        <div>
                            <dt class="text-slate-500 mb-1 font-semibold uppercase tracking-wider text-[10px]">Ping Latency (SELECT 1):</dt>
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
                        <t:metricRow label="Current User Identity" value="${status.loggedInUser}" valueClass="font-semibold text-teal-700" />
                        <t:metricRow label="Active HTTP Sessions" value="${status.activeSessions}" valueClass="font-bold text-slate-800" />
                        <t:metricRow label="Tomcat Uptime" value="${status.serverUptime}" valueClass="font-mono font-medium text-slate-800" />
                        <t:metricRow label="Server Local Time" value="${status.serverTime}" />
                        <t:metricRow label="Java Runtime Version" value="JDK ${status.javaVersion}" />
                        <t:metricRow label="Heap Memory (Free / Total)" value="${status.freeMemoryMb} MB / ${status.totalMemoryMb} MB" valueClass="font-mono text-slate-700" hasBorder="false" />
                    </dl>   
                </div>
            </div>

        </div>
    </div>
</t:layout>
