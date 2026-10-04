<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Product Catalog">
    <div class="max-w-6xl mx-auto space-y-6 my-6">

        <div class="flex items-center justify-between border-b border-slate-200 pb-4">
            <div>
                <h1 class="text-2xl font-bold text-slate-900">Product Catalog</h1>
                <p class="text-xs text-slate-500 mt-1">Browse our available items and inventory</p>
            </div>
            <span class="text-xs font-mono bg-slate-100 text-slate-700 px-3 py-1 rounded-full border border-slate-200">
                ${not empty products ? products.size() : 0} Products
            </span>
        </div>

        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            <t:forEach items="${products}" var="item">
                <t:productCard 
                    name="${item.name}" 
                    description="${item.description}" 
                    category="${item.category}" 
                    price="${item.price}" 
                    quantity="${item.quantity}" 
                />
            </t:forEach>

            <div class="${empty products ? 'col-span-full' : 'hidden'} py-12 text-center bg-white rounded-xl border border-slate-200">
                <p class="text-sm text-slate-500">No products available in the catalog right now.</p>
            </div>
        </div>

    </div>
</t:layout>