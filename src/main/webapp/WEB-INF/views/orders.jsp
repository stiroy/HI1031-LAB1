<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="My Orders">
    <div class="max-w-4xl mx-auto space-y-6 my-6">

        <!-- Page Header -->
        <div class="flex items-center justify-between border-b border-slate-200 pb-4">
            <div>
                <h1 class="text-2xl font-bold text-slate-900">Order History</h1>
                <p class="text-xs text-slate-500 mt-1">Review your past purchases and receipts</p>
            </div>
            <a href="${pageContext.request.contextPath}/app/catalog" class="text-xs text-teal-600 font-semibold hover:underline">
                &larr; Back to Catalog
            </a>
        </div>

        <!-- Empty State -->
        <div class="${empty orders ? 'block' : 'hidden'} py-12 text-center bg-white rounded-xl border border-slate-200">
            <p class="text-sm font-medium text-slate-600">You haven't placed any orders yet.</p>
            <a href="${pageContext.request.contextPath}/app/catalog" class="inline-block mt-3 text-xs bg-slate-900 text-white px-4 py-2 rounded-lg font-semibold shadow-sm">
                Start Shopping
            </a>
        </div>

        <!-- Orders List -->
        <div class="${not empty orders ? 'space-y-4' : 'hidden'}">
            <t:forEach items="${orders}" var="order">
                <div class="bg-white rounded-xl border border-slate-200 overflow-hidden shadow-sm">
                    
                    <!-- Order Card Header -->
                    <div class="bg-slate-50 px-5 py-3 border-b border-slate-200 flex flex-wrap justify-between items-center text-xs font-mono">
                        <div class="flex space-x-4">
                            <div>
                                <span class="text-slate-400 block text-[10px] uppercase">Order ID</span>
                                <span class="font-bold text-slate-900">${order.orderId}</span>
                            </div>
                            <div>
                                <span class="text-slate-400 block text-[10px] uppercase">Date</span>
                                <span class="text-slate-700">${order.orderDate}</span>
                            </div>
                        </div>
                        <div>
                            <span class="text-slate-400 block text-[10px] uppercase font-mono">Status</span>
                            <span class="inline-flex items-center px-2 py-0.5 rounded-full text-[10px] font-bold font-mono border ${order.status == 'PACKED' ? 'bg-emerald-50 text-emerald-700 border-emerald-200' : 'bg-amber-50 text-amber-700 border-amber-200'}">
                                <span class="w-1.5 h-1.5 rounded-full mr-1.5 ${order.status == 'PACKED' ? 'bg-emerald-500' : 'bg-amber-500'}"></span>
                                ${order.status == 'PACKED' ? 'Packed' : 'Processing'}
                            </span>
                        </div>

                        <div class="text-right">
                            <span class="text-slate-400 block text-[10px] uppercase">Total</span>
                            <span class="font-extrabold text-teal-600 text-sm">$${order.getTotalAmount}</span>
                        </div>
                    </div>

                    <!-- Order Purchased Items -->
                    <div class="divide-y divide-slate-100 px-5">
                        <t:forEach items="${order.items}" var="item">
                            <div class="py-3 flex justify-between items-center text-xs">
                                <div>
                                    <h5 class="font-semibold text-slate-800">${item.product.name}</h5>
                                    <span class="text-slate-400 text-[11px]">Quantity: ${item.quantity} &times; $${item.product.price}</span>
                                </div>
                                <span class="font-bold text-slate-900 font-mono">$${item.getTotalPrice}</span>
                            </div>
                        </t:forEach>
                    </div>
                </div>
            </t:forEach>
        </div>

    </div>
</t:layout>