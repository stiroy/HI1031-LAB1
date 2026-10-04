<%@ tag language="java" pageEncoding="UTF-8" %>

<%@ attribute name="label" required="true" type="java.lang.String" %>
<%@ attribute name="value" required="true" type="java.lang.String" %>
<%@ attribute name="valueClass" required="false" type="java.lang.String" %>
<%@ attribute name="hasBorder" required="false" type="java.lang.Boolean" %>

<div class="flex justify-between py-1 ${empty hasBorder or hasBorder ? 'border-b border-slate-50' : ''}">
    <dt class="text-slate-500">${label}:</dt>
    <dd class="${empty valueClass ? 'font-medium text-slate-800' : valueClass}">${value}</dd>
</div>