<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="title" required="true" type="java.lang.String" %>
<%@ attribute name="status" required="true" type="java.lang.Boolean" %>
<%@ attribute name="message" required="false" type="java.lang.String" %>

<div>
    <dt class="text-slate-500 mb-1 font-semibold uppercase tracking-wider text-[10px]">${title}:</dt>
    <dd>
        <t:statusBadge status="${status}" label="${message}" />
    </dd>
</div>