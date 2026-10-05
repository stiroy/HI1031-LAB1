<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Admin - Catalog Management">
    <div class="max-w-6xl mx-auto space-y-8 my-6">

        <!-- Page Header -->
        <div class="border-b border-slate-200 pb-4 flex justify-between items-center">
            <div>
                <h1 class="text-2xl font-bold text-slate-900">Catalog & Product Management</h1>
                <p class="text-xs text-slate-500 mt-1">Add new items to store inventory and update existing product details</p>
            </div>
            <span class="bg-purple-100 border border-purple-200 text-purple-800 text-xs font-bold px-3 py-1 rounded-full">
                ⚙ Administrator
            </span>
        </div>

        <!-- Add New Product Form Card -->
        <div class="bg-white rounded-xl border border-slate-200 p-6 shadow-sm">
            <h2 class="text-sm font-bold text-slate-900 mb-4 flex items-center">
                <span class="bg-purple-100 text-purple-700 w-6 h-6 rounded-full inline-flex items-center justify-center text-xs mr-2">+</span>
                Add New Product
            </h2>
            <form action="${pageContext.request.contextPath}/admin/addProduct" method="POST" class="grid grid-cols-1 md:grid-cols-3 gap-4 text-xs">
                <div>
                    <label class="block font-semibold text-slate-700 mb-1">Product Name</label>
                    <input type="text" name="name" required placeholder="e.g. Mechanical Keyboard" class="w-full border border-slate-300 rounded-lg px-3 py-2 outline-none focus:ring-2 focus:ring-purple-500" />
                </div>
                <div>
                    <label class="block font-semibold text-slate-700 mb-1">Category</label>
                    <input type="text" name="category" required placeholder="e.g. Electronics" class="w-full border border-slate-300 rounded-lg px-3 py-2 outline-none focus:ring-2 focus:ring-purple-500" />
                </div>
                <div>
                    <label class="block font-semibold text-slate-700 mb-1">Price ($)</label>
                    <input type="number" step="0.01" name="price" required placeholder="49.99" class="w-full border border-slate-300 rounded-lg px-3 py-2 outline-none focus:ring-2 focus:ring-purple-500" />
                </div>
                <div>
                    <label class="block font-semibold text-slate-700 mb-1">Stock Quantity</label>
                    <input type="number" name="stock" required placeholder="25" class="w-full border border-slate-300 rounded-lg px-3 py-2 outline-none focus:ring-2 focus:ring-purple-500" />
                </div>
                <div class="md:col-span-2">
                    <label class="block font-semibold text-slate-700 mb-1">Description</label>
                    <input type="text" name="description" required placeholder="Short product summary..." class="w-full border border-slate-300 rounded-lg px-3 py-2 outline-none focus:ring-2 focus:ring-purple-500" />
                </div>
                <div class="md:col-span-3 flex justify-end mt-2">
                    <button type="submit" class="bg-purple-700 hover:bg-purple-600 text-white font-bold px-5 py-2 rounded-lg text-xs shadow-sm transition">
                        Save Product
                    </button>
                </div>
            </form>
        </div>

        <!-- Existing Products Inventory Table -->
        <div class="bg-white rounded-xl border border-slate-200 overflow-hidden shadow-sm">
            <div class="px-5 py-3 border-b border-slate-200 bg-slate-50 flex justify-between items-center">
                <h3 class="text-xs font-bold text-slate-700 uppercase tracking-wider">Current Inventory</h3>
                <span class="text-[11px] text-slate-500 font-mono">${products.size()} Items Total</span>
            </div>
            
            <table class="w-full text-left text-xs">
                <thead class="bg-slate-50 border-b border-slate-200 text-slate-500 uppercase font-mono">
                    <tr>
                        <th class="p-3.5">ID</th>
                        <th class="p-3.5">Name</th>
                        <th class="p-3.5">Category</th>
                        <th class="p-3.5">Price ($)</th>
                        <th class="p-3.5">Stock</th>
                        <th class="p-3.5 text-right">Actions</th>
                    </tr>
                </thead>
                <tbody class="divide-y divide-slate-100">
                    <t:forEach items="${products}" var="p">
                        <tr class="hover:bg-slate-50 transition">
                            <form action="${pageContext.request.contextPath}/admin/updateProduct" method="POST" class="m-0">
                                <input type="hidden" name="id" value="${p.id}" />
                                
                                <td class="p-3.5 font-mono text-slate-400">#${p.id}</td>
                                <td class="p-3.5">
                                    <input type="text" name="name" value="${p.name}" class="w-full border border-slate-200 rounded px-2 py-1 font-semibold text-slate-800" />
                                </td>
                                <td class="p-3.5">
                                    <input type="text" name="category" value="${p.category}" class="w-32 border border-slate-200 rounded px-2 py-1 text-slate-600" />
                                </td>
                                <td class="p-3.5">
                                    <input type="number" step="0.01" name="price" value="${p.price}" class="w-20 border border-slate-200 rounded px-2 py-1 font-mono text-slate-800" />
                                </td>
                                <td class="p-3.5">
                                    <input type="number" name="stock" value="${p.quantity}" class="w-16 border border-slate-200 rounded px-2 py-1 font-mono ${p.quantity < 5 ? 'text-red-600 font-bold' : 'text-slate-800'}" />
                                </td>
                                <td class="p-3.5 text-right">
                                    <!-- Hidden description input to preserve value when submitting inline row edits -->
                                    <input type="hidden" name="description" value="${p.description}" />
                                    <button type="submit" class="bg-slate-900 hover:bg-slate-800 text-white font-semibold px-3 py-1 rounded text-xs transition shadow-sm">
                                        Update
                                    </button>
                                </td>
                            </form>
                        </tr>
                    </t:forEach>
                </tbody>
            </table>
        </div>

    </div>
</t:layout>