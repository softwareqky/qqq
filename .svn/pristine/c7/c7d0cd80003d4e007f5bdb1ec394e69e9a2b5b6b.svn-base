<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 查看弹出画面 --%>
<c:if test="${not empty viewFields}">
  <g:viewDialog dialogId="${idMap['view-dialog']}" width="800" maxHeight="${viewDialogMaxHeight}">
    <c:choose>
      <c:when test="${isFieldGrouped}">
        <g:viewGroupLayout viewFields="${viewFields}" />
      </c:when>
      <c:otherwise>
        <g:viewSimpleLayout viewFields="${viewFields}" controlCols="${viewDialogControlCols}" labelCols="${12 - viewDialogControlCols}" />
      </c:otherwise>
    </c:choose>
  </g:viewDialog>
</c:if>