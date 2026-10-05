<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" %>

<%@ attribute name="id" required="true" type="java.lang.Integer" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ attribute name="description" required="false" type="java.lang.String" %>
<%@ attribute name="category" required="true" type="java.lang.String" %>
<%@ attribute name="price" required="true" type="java.lang.Double" %>
<%@ attribute name="quantity" required="true" type="java.lang.Integer" %>

<div class="bg-white rounded-xl border border-slate-200 shadow-sm flex flex-col justify-between p-5 hover:shadow-md transition">
    <div>
        <!-- Category & Stock Status -->
        <div class="flex items-center justify-between mb-2">
            <!-- DEBUG -->
            <span>${id}</span> 
            <span class="text-[10px] font-bold uppercase tracking-wider text-teal-600 bg-teal-50 px-2 py-0.5 rounded border border-teal-100">
                ${category}
            </span>
            
            <span class="text-xs font-semibold ${quantity > 0 ? 'text-emerald-600' : 'text-red-500'}">
                ${quantity > 0 ? 'In Stock ('.concat(quantity).concat(')') : 'Out of Stock'}
            </span>
        </div>

        <!-- Name & Description -->
        <h3 class="font-bold text-slate-900 text-base mb-1">${name}</h3>
        <p class="text-xs text-slate-500 line-clamp-2 mb-4">${description}</p>
    </div>

    <!-- Price & Actions -->
    <div class="pt-4 border-t border-slate-100 flex items-center justify-between">
        <div>
            <span class="text-xs text-slate-400 block font-mono">Price</span>
            <span class="text-lg font-extrabold text-slate-900">$${price}</span>
        </div>

        <form action="${pageContext.request.contextPath}/app/addToCart" method="POST" class="m-0">
            <input type="hidden" name="productId" value="${id}" />
            <input type="hidden" name="quantity" value="1" />
            <button type="submit" ${quantity == 0 ? 'disabled' : ''} 
                    class="px-3.5 py-2 rounded-lg text-xs font-semibold transition ${quantity > 0 ? 'bg-slate-900 hover:bg-slate-800 text-white shadow-sm' : 'bg-slate-100 text-slate-400 cursor-not-allowed'}">
                ${quantity > 0 ? 'Add to Cart' : 'Unavailable'}
            </button>
        </form>
    </div>
</div>