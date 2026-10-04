<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ attribute name="icon" required="true" type="java.lang.String" %>
<%@ attribute name="iconBg" required="false" type="java.lang.String" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="description" required="true" type="java.lang.String" %>
<%@ attribute name="linkHref" required="true" type="java.lang.String" %>
<%@ attribute name="linkLabel" required="true" type="java.lang.String" %>
<%@ attribute name="linkColor" required="false" type="java.lang.String" %>

<div class="bg-white p-6 rounded-xl border border-slate-200 shadow-sm hover:shadow-md transition">
    <div class="w-10 h-10 rounded-lg ${not empty iconBg ? iconBg : 'bg-slate-100 text-slate-700'} flex items-center justify-center font-bold text-lg mb-4">
        ${icon}
    </div>
    <h2 class="text-lg font-bold text-slate-900 mb-2">${title}</h2>
    <p class="text-slate-600 text-sm mb-4">
        ${description}
    </p>
    <a href="${linkHref}" class="text-sm font-semibold ${not empty linkColor ? linkColor : 'text-slate-800 hover:text-slate-600'}">
        ${linkLabel} &rarr;
    </a>
</div>