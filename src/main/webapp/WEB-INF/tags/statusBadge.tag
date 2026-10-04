<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="status" required="false" type="java.lang.Boolean" %>
<%@ attribute name="type" required="false" type="java.lang.String" %> <%-- success | error | warning | info --%>
<%@ attribute name="label" required="false" type="java.lang.String" %>
<%@ attribute name="showDot" required="false" type="java.lang.Boolean" %>

<%-- Determine badge resolved type and defaults using standard EL --%>
<%
    String resolvedType = (String) jspContext.getAttribute("type");
    Boolean status = (Boolean) jspContext.getAttribute("status");
    if (resolvedType == null || resolvedType.trim().isEmpty()) {
        resolvedType = (status != null && status) ? "success" : "error";
    }
    
    String badgeClass = "bg-red-50 text-red-700 border-red-200";
    String dotClass = "bg-red-500";
    String defaultText = "FAILED";

    if ("success".equalsIgnoreCase(resolvedType)) {
        badgeClass = "bg-emerald-50 text-emerald-700 border-emerald-200";
        dotClass = "bg-emerald-500";
        defaultText = "CONNECTED";
    } else if ("warning".equalsIgnoreCase(resolvedType)) {
        badgeClass = "bg-amber-50 text-amber-700 border-amber-200";
        dotClass = "bg-amber-500";
        defaultText = "DEGRADED";
    } else if ("info".equalsIgnoreCase(resolvedType)) {
        badgeClass = "bg-blue-50 text-blue-700 border-blue-200";
        dotClass = "bg-blue-500";
        defaultText = "INFO";
    }

    jspContext.setAttribute("badgeClass", badgeClass);
    jspContext.setAttribute("dotClass", dotClass);
    jspContext.setAttribute("defaultText", defaultText);
    jspContext.setAttribute("renderDot", jspContext.getAttribute("showDot") == null ? Boolean.TRUE : jspContext.getAttribute("showDot"));
%>

<span class="inline-flex items-center px-2.5 py-1 text-xs font-semibold rounded-md border ${badgeClass}">
    <% if (Boolean.TRUE.equals(jspContext.getAttribute("renderDot"))) { %>
        <span class="w-1.5 h-1.5 mr-1.5 rounded-full ${dotClass}"></span>
    <% } %>
    ${empty label ? defaultText : label}
</span>