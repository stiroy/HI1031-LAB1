<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout title="Shopping Cart">
    <div class="max-w-4xl mx-auto space-y-6 my-6">

        <div class="flex items-center justify-between border-b border-slate-200 pb-4">
            <div>
                <h1 class="text-2xl font-bold text-slate-900">Your Shopping Cart</h1>
                <p class="text-xs text-slate-500 mt-1">Review your selected items before checkout</p>
            </div>
            <a href="${pageContext.request.contextPath}/app/catalog" class="text-xs text-teal-600 font-semibold hover:underline">
                &larr; Continue Shopping
            </a>
        </div>

        <div class="${empty sessionScope.cart || empty sessionScope.cart.items ? 'block' : 'hidden'} py-12 text-center bg-white rounded-xl border border-slate-200">
            <p class="text-sm font-medium text-slate-600">Your cart is currently empty.</p>
            <a href="${pageContext.request.contextPath}/app/catalog" class="inline-block mt-3 text-xs bg-slate-900 text-white px-4 py-2 rounded-lg font-semibold shadow-sm">
                Browse Products
            </a>
        </div>

        <div class="${not empty sessionScope.cart && not empty sessionScope.cart.items ? 'grid' : 'hidden'} grid-cols-1 lg:grid-cols-3 gap-6">
            
            <!-- Items Table -->
            <div class="lg:col-span-2 bg-white rounded-xl border border-slate-200 overflow-hidden shadow-sm">
                <div class="divide-y divide-slate-100">
                    <t:forEach items="${sessionScope.cart.items}" var="item">
                        <div class="p-4 flex items-center justify-between gap-4">
                            <div class="flex-1">
                                <h4 class="font-bold text-slate-800 text-sm">${item.product.name}</h4>
                                <span class="text-xs text-slate-400">$${item.product.price} each</span>
                            </div>

                            <!-- Quantity Form -->
                            <form action="${pageContext.request.contextPath}/app/updateCart" method="POST" class="flex items-center space-x-2">
                                <input type="hidden" name="productId" value="${item.product.id}" />
                                <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.product.quantity}"
                                       class="w-14 px-2 py-1 text-xs border border-slate-300 rounded font-mono text-center focus:outline-none focus:ring-1 focus:ring-teal-500" />
                                <button type="submit" class="text-[11px] bg-slate-100 hover:bg-slate-200 px-2 py-1 rounded text-slate-700 font-medium">
                                    Update
                                </button>
                            </form>

                            <!-- Subtotal & Delete -->
                            <div class="text-right">
                                <span class="block font-bold text-slate-900 text-sm">$${item.getTotalPriceValue}</span>
                                <form action="${pageContext.request.contextPath}/app/removeFromCart" method="POST" class="inline">
                                    <input type="hidden" name="productId" value="${item.product.id}" />
                                    <button type="submit" class="text-[10px] text-red-500 hover:underline">Remove</button>
                                </form>
                            </div>
                        </div>
                    </t:forEach>
                </div>
            </div>

            <!-- Order Summary Card -->
            <div class="bg-white p-5 rounded-xl border border-slate-200 shadow-sm h-fit space-y-4">
                <h3 class="font-bold text-slate-900 text-sm border-b border-slate-100 pb-2">Order Summary</h3>
                
                <div class="space-y-2 text-xs text-slate-600">
                    <div class="flex justify-between">
                        <span>Total Items:</span>
                        <span class="font-bold font-mono">${sessionScope.cart.totalItemCount}</span>
                    </div>
                    <div class="flex justify-between text-base font-bold text-slate-900 pt-2 border-t border-slate-100">
                        <span>Total Price:</span>
                        <span class="text-teal-600 font-mono">$${sessionScope.cart.totalAmount}</span>
                    </div>
                </div>

                <a href="${pageContext.request.contextPath}/app/userProfile" 
                   class="block text-center w-full py-2.5 bg-teal-500 hover:bg-teal-400 text-slate-950 font-bold text-xs rounded-lg transition shadow-sm">
                    Proceed to Checkout
                </a>
            </div>

        </div>

    </div>
</t:layout>