<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Product Catalog">
    <div class="max-w-6xl mx-auto space-y-6 my-6">

        <!-- Page Header & Search Bar -->
        <div class="flex flex-col md:flex-row md:items-center justify-between gap-4 border-b border-slate-200 pb-4">
            <div>
                <h1 class="text-2xl font-bold text-slate-900">Product Catalog</h1>
                <p class="text-xs text-slate-500 mt-1">Browse our available items and inventory</p>
            </div>

            <!-- Search Form -->
            <form action="${pageContext.request.contextPath}/app/search" method="GET" class="flex items-center space-x-2">
                <div class="relative">
                    <input type="text" 
                           name="query" 
                           value="${searchQuery}" 
                           placeholder="Search products..." 
                           class="w-64 px-3.5 py-2 pl-9 bg-slate-50 border border-slate-300 rounded-lg text-xs font-medium text-slate-800 focus:outline-none focus:ring-2 focus:ring-teal-500 focus:bg-white transition" />
                    <span class="absolute left-3 top-2.5 text-slate-400 text-xs">🔍</span>
                </div>
                
                <button type="submit" 
                        class="px-4 py-2 bg-slate-900 hover:bg-slate-800 text-white font-semibold text-xs rounded-lg transition shadow-sm">
                    Search
                </button>

                <!-- Clear Search Button -->
                <div class="${not empty searchQuery ? 'block' : 'hidden'}">
                    <a href="${pageContext.request.contextPath}/app/catalog" 
                       class="px-3 py-2 bg-slate-100 hover:bg-slate-200 text-slate-600 font-semibold text-xs rounded-lg transition">
                        Clear
                    </a>
                </div>
            </form>
        </div>

        <!-- Search Active Banner -->
        <div class="${not empty searchQuery ? 'block' : 'hidden'} bg-teal-50 border border-teal-200 text-teal-800 px-4 py-2.5 rounded-lg text-xs flex justify-between items-center">
            <span>Showing results for: <strong class="font-bold">"${searchQuery}"</strong></span>
            <span class="font-mono text-[11px]">${products.size()} matches found</span>
        </div>

        <!-- Product Grid -->
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            <t:forEach items="${products}" var="item">
                <t:productCard 
                    id="${item.id}"
                    name="${item.name}" 
                    description="${item.description}" 
                    category="${item.category}" 
                    price="${item.price}" 
                    quantity="${item.quantity}" 
                />
            </t:forEach>

            <!-- Empty State -->
            <div class="${empty products ? 'col-span-full' : 'hidden'} py-12 text-center bg-white rounded-xl border border-slate-200">
                <p class="text-sm font-medium text-slate-600">No products match your search criteria.</p>
                <a href="${pageContext.request.contextPath}/app/index" class="inline-block mt-3 text-xs text-teal-600 hover:underline font-semibold">
                    &larr; View all products
                </a>
            </div>
        </div>

    </div>
</t:layout>