<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ attribute name="type" required="false" type="java.lang.String" description="error, success, info" %>
<%@ attribute name="title" required="false" type="java.lang.String" %>

<c:set var="borderColor" value="${type eq 'error' ? 'border-t-red-500' : (type eq 'success' ? 'border-t-emerald-500' : 'border-t-teal-500')}" />

<div class="bg-white rounded-2xl shadow-xl border border-slate-200 border-t-8 ${borderColor} p-8 space-y-6">
    <c:if test="${not empty title}">
        <h2 class="text-xl font-bold text-slate-900">${title}</h2>
    </c:if>
    <jsp:doBody />
</div>