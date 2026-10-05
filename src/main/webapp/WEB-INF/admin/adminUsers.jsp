<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Admin - User Management">
    <div class="max-w-5xl mx-auto space-y-6 my-6">

        <!-- Header -->
        <div class="border-b border-slate-200 pb-4 flex justify-between items-center">
            <div>
                <h1 class="text-2xl font-bold text-slate-900">User Management</h1>
                <p class="text-xs text-slate-500 mt-1">Manage registered accounts and assign role permissions</p>
            </div>
            <span class="bg-purple-100 border border-purple-200 text-purple-800 text-xs font-bold px-3 py-1 rounded-full">
                ⚙ Administrator
            </span>
        </div>

        <!-- Users Table -->
        <div class="bg-white rounded-xl border border-slate-200 overflow-hidden shadow-sm">
            <table class="w-full text-left text-xs">
                <thead class="bg-slate-50 border-b border-slate-200 text-slate-500 uppercase font-mono">
                    <tr>
                        <th class="p-3.5">ID</th>
                        <th class="p-3.5">Username</th>
                        <th class="p-3.5">Email</th>
                        <th class="p-3.5">Current Role</th>
                        <th class="p-3.5 text-right">Change Role</th>
                    </tr>
                </thead>
                <tbody class="divide-y divide-slate-100">
                    <t:forEach items="${users}" var="u">
                        <tr class="hover:bg-slate-50 transition">
                            <td class="p-3.5 font-mono text-slate-400">#${u.id}</td>
                            <td class="p-3.5 font-bold text-slate-800">${u.username}</td>
                            <td class="p-3.5 text-slate-600">${u.email}</td>
                            <td class="p-3.5">
                                <span class="px-2.5 py-1 text-[10px] font-bold rounded-full font-mono border ${u.role == 'ADMIN' ? 'bg-purple-50 text-purple-700 border-purple-200' : u.role == 'WAREHOUSE' ? 'bg-amber-50 text-amber-700 border-amber-200' : 'bg-slate-100 text-slate-700 border-slate-200'}">
                                    ${u.role}
                                </span>
                            </td>
                            <td class="p-3.5 text-right">
                                <form action="${pageContext.request.contextPath}/admin/updateUserRole" method="POST" class="inline-flex items-center space-x-2 m-0">
                                    <input type="hidden" name="userName value="${u.username}" />
                                    <select name="role" class="text-xs border border-slate-300 rounded-lg px-2.5 py-1 bg-white font-medium focus:ring-2 focus:ring-purple-500 outline-none">
                                        <option value="CUSTOMER" ${u.role == 'CUSTOMER' ? 'selected' : ''}>CUSTOMER</option>
                                        <option value="EMPLOYEE" ${u.role == 'EMPLOYEE' ? 'selected' : ''}>EMPLOYEE</option>
                                        <option value="ADMIN" ${u.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                                    </select>
                                    <button type="submit" class="bg-slate-900 hover:bg-slate-800 text-white font-semibold px-3 py-1 rounded-lg text-xs transition shadow-sm">
                                        Save
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </t:forEach>
                </tbody>
            </table>
        </div>

    </div>
</t:layout>