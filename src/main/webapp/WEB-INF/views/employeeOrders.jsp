<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Warehouse - Pack Orders">
    <div class="max-w-5xl mx-auto space-y-6 my-6">

        <!-- Page Header -->
        <div class="border-b border-slate-200 pb-4 flex justify-between items-center">
            <div>
                <h1 class="text-2xl font-bold text-slate-900">Warehouse & Order Fulfillment</h1>
                <p class="text-xs text-slate-500 mt-1">Review incoming customer orders and mark them as packed</p>
            </div>
            <span class="bg-amber-100 border border-amber-200 text-amber-800 text-xs font-bold px-3 py-1 rounded-full">
                📦 Warehouse Staff
            </span>
        </div>

        <!-- Empty State -->
        <div class="${empty orders ? 'block' : 'hidden'} py-12 text-center bg-white rounded-xl border border-slate-200 shadow-sm">
            <p class="text-sm font-medium text-slate-600">No incoming orders in the queue right now.</p>
            <p class="text-xs text-slate-400 mt-1">When customers place an order, it will appear here automatically.</p>
        </div>

        <!-- Orders Queue -->
        <div class="${not empty orders ? 'space-y-4' : 'hidden'}">
            <t:forEach items="${orders}" var="order">
                <div class="bg-white rounded-xl border border-slate-200 overflow-hidden shadow-sm">
                    
                    <!-- Order Top Info Bar -->
                    <div class="bg-slate-50 px-5 py-3 border-b border-slate-200 flex flex-wrap justify-between items-center text-xs">
                        <div class="flex items-center space-x-4">
                            <div>
                                <span class="text-slate-400 block text-[10px] uppercase font-mono">Order ID</span>
                                <span class="font-bold text-slate-900 font-mono">${order.orderId}</span>
                            </div>
                            <div>
                                <span class="text-slate-400 block text-[10px] uppercase font-mono">Customer</span>
                                <span class="font-bold text-slate-700">${order.customerName}</span>
                            </div>
                            <div>
                                <span class="text-slate-400 block text-[10px] uppercase font-mono">Date</span>
                                <span class="text-slate-600">${order.orderDate}</span>
                            </div>
                        </div>

                        <div class="flex items-center space-x-3">
                            <!-- Status Badge -->
                            <span class="px-2.5 py-1 text-[11px] font-bold rounded-full font-mono ${order.status == 'PACKED' ? 'bg-emerald-100 text-emerald-700 border border-emerald-200' : 'bg-amber-100 text-amber-700 border border-amber-200'}">
                                ${order.status == 'PACKED' ? '✓ PACKED' : '⏳ PENDING'}
                            </span>

                            <!-- Pack Order Action Form -->
                            <form action="${pageContext.request.contextPath}/app/packOrder" method="POST" class="m-0">
                                <input type="hidden" name="orderId" value="${order.orderId}" />
                                <button type="submit" ${order.status == 'PACKED' ? 'disabled' : ''} 
                                        class="px-3.5 py-1.5 rounded-lg text-xs font-bold transition ${order.status != 'PACKED' ? 'bg-emerald-600 hover:bg-emerald-500 text-white shadow-sm' : 'bg-slate-100 text-slate-400 cursor-not-allowed'}">
                                    ${order.status == 'PACKED' ? 'Packed' : 'Pack Order'}
                                </button>
                            </form>
                        </div>
                    </div>

                    <!-- Items Included in Order -->
                    <div class="px-5 py-3 bg-white divide-y divide-slate-100">
                        <span class="text-[10px] uppercase font-bold text-slate-400 block mb-1">Items to Pick:</span>
                        <t:forEach items="${order.items}" var="item">
                            <div class="py-2 flex justify-between items-center text-xs">
                                <div>
                                    <span class="font-semibold text-slate-800">${item.product.name}</span>
                                    <span class="text-slate-400 block text-[11px]">Category: ${item.product.category}</span>
                                </div>
                                <span class="font-bold text-slate-900 bg-slate-100 px-2.5 py-1 rounded border border-slate-200 font-mono">
                                    Qty: ${item.quantity} pcs
                                </span>
                            </div>
                        </t:forEach>
                    </div>

                </div>
            </t:forEach>
        </div>

    </div>
</t:layout>