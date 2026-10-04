<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ attribute name="label" required="true" type="java.lang.String" %>
<%@ attribute name="type" required="false" type="java.lang.String" %>
<%@ attribute name="required" required="false" type="java.lang.Boolean" %>

<div class="mb-4">
    <label for="${name}" class="block text-xs font-semibold uppercase tracking-wider text-slate-600 mb-1">
        ${label}
    </label>
    <input type="${empty type ? 'text' : type}" 
           id="${name}" 
           name="${name}" 
           ${required ? 'required' : ''} 
           class="w-full px-3 py-2 border border-slate-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-teal-500 focus:border-transparent" />
</div>