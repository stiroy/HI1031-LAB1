<!-- Headers & config -->
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<% request.setAttribute("pageTitle", "Product Details"); %>
<%@ include file="common/header.jsp" %>
<!-- end -->

<div class="bg-white rounded-xl shadow-md p-6 max-w-2xl mx-auto">
    <h1 class="text-2xl font-bold text-slate-900 mb-2">${product.name}</h1>
    <p class="text-slate-600 mb-4">${product.description}</p>
    
    <div class="flex items-center justify-between border-t border-slate-100 pt-4">
        <span class="text-xl font-bold text-teal-600">$${product.price}</span>
        <button class="bg-slate-900 hover:bg-slate-700 text-white text-sm px-4 py-2 rounded-lg transition">
            Add to Cart
        </button>
    </div>
</div>

<!-- Footer -->
<%@ include file="common/footer.jsp" %>