<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-借阅领取 --%>
<c:if test="${not empty takeFields}">
  <s:message var="takeTitle" code="ui.common.submit.lend.take" />
  <g:editDialog dialogId="${idMap['edit-take-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-take-submit']}" maxHeight="600" dialogTitle="${takeTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${takeFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit-lend']}?actionType=take" />
  </g:editDialog>
</c:if>

<%-- 修改弹出画面-借阅归还 --%>
<c:if test="${not empty returnFields}">
  <s:message var="returnTitle" code="ui.common.submit.lend.return" />
  <g:editDialog dialogId="${idMap['edit-return-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-return-submit']}" maxHeight="600" dialogTitle="${returnTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${returnFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit-lend']}?actionType=return" />
  </g:editDialog>
</c:if>