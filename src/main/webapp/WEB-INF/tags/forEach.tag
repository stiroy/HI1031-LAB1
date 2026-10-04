<%@ tag language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ attribute name="items" required="true" type="java.lang.Iterable" %>
<%@ attribute name="var" required="true" type="java.lang.String" rtexprvalue="false" %>

<%
    java.lang.Iterable<?> collection = (java.lang.Iterable<?>) jspContext.getAttribute("items");
    String varName = (String) jspContext.getAttribute("var");

    if (collection != null) {
        for (Object item : collection) {
            // Expose variable to REQUEST_SCOPE so the caller JSP can read it
            jspContext.setAttribute(varName, item, jakarta.servlet.jsp.PageContext.REQUEST_SCOPE);
            getJspBody().invoke(null);
        }
        // Cleanup after loop completes
        jspContext.removeAttribute(varName, jakarta.servlet.jsp.PageContext.REQUEST_SCOPE);
    }
%>