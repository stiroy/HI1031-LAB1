<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="subtitle" required="false" type="java.lang.String" %>

<div class="mb-6">
    <h1 class="text-2xl font-extrabold text-slate-900 tracking-tight">${title}</h1>
    <c:if test="${not empty subtitle}">
        <p class="text-sm text-slate-600 mt-1">${subtitle}</p>
    </c:if>
</div>