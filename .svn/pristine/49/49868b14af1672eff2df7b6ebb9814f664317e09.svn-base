<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />
<%
    String isAdd = request.getParameter("isAdd");
    String idSuffix = "Edit";
    if ("true".equals(isAdd)) {
        idSuffix = "Add";
    }
%>
<div id="${idPrefix}-location-field-<%=idSuffix %>" style="height: 450px; width: 100%;"></div>

