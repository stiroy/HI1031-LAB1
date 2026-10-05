<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Order Confirmed">
    <div class="max-w-2xl mx-auto space-y-6 my-10">

        <!-- Confirmation Card -->
        <div class="bg-white p-8 rounded-2xl border border-slate-200 shadow-sm text-center space-y-4">
            <div class="w-16 h-16 bg-emerald-100 text-emerald-600 rounded-full flex items-center justify-center mx-auto text-2xl font-bold">
                ✓
            </div>
            
            <h1 class="text-2xl font-extrabold text-slate-900">Thank You for Your Order!</h1>
            <p class="text-xs text-slate-500">Your order has been placed successfully. A receipt has been generated below.</p>

            <!-- Order Details Box -->
            <div class="bg-slate-50 border border-slate-200 rounded-xl p-5 text-left text-xs space-y-3 font-mono mt-6">
                <div class="flex justify-between border-b border-slate-200 pb-2">
                    <span class="text-slate-500 uppercase">Order ID</span>
                    <span class="font-bold text-slate-900">${completedOrder.orderId}</span>
                </div>
                <div class="flex justify-between border-b border-slate-200 pb-2">
                    <span class="text-slate-500 uppercase">Customer</span>
                    <span class="font-bold text-slate-900">${completedOrder.customerName}</span>
                </div>
                <div class="flex justify-between">
                    <span class="text-slate-500 uppercase">Date & Time</span>
                    <span class="font-bold text-slate-900">${completedOrder.orderDate}</span>
                </div>
            </div>

            <!-- Items Purchased Summary -->
            <div class="text-left space-y-2 mt-4">
                <h3 class="text-xs font-bold text-slate-700 uppercase tracking-wider">Items Purchased</h3>
                <div class="divide-y divide-slate-100 border-t border-b border-slate-100">
                    <t:forEach items="${completedOrder.items}" var="item">
                        <div class="py-2.5 flex justify-between items-center text-xs">
                            <div>
                                <span class="font-semibold text-slate-800">${item.product.name}</span>
                                <span class="text-slate-400 block text-[11px]">Qty: ${item.quantity} &times; $${item.product.price}</span>
                            </div>
                            <span class="font-bold text-slate-900 font-mono">$${item.getTotalPriceValue}</span>
                        </div>
                    </t:forEach>
                </div>
            </div>

            <!-- Total Price -->
            <div class="flex justify-between items-center pt-2 text-sm font-bold text-slate-900">
                <span>Total Amount Paid:</span>
                <span class="text-teal-600 font-mono text-lg">$${completedOrder.getTotalAmount}</span>
            </div>

            <!-- Action Buttons -->
            <div class="pt-6 flex justify-center space-x-4">
                <a href="${pageContext.request.contextPath}/app/catalog" 
                   class="px-5 py-2.5 bg-slate-900 hover:bg-slate-800 text-white font-semibold text-xs rounded-lg transition shadow-sm">
                    Back to Catalog
                </a>
            </div>
        </div>

    </div>
</t:layout>