<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-抽查 --%>
<c:if test="${not empty spotcheckFields}">
  <s:message var="spotcheckTitle" code="ui.label.title.issue.spot.check" />
  <g:editDialog dialogId="${idMap['edit-spotcheck-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-spotcheck-submit']}" maxHeight="600" dialogTitle="${spotcheckTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${spotcheckFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=spotcheck" />
  </g:editDialog>
</c:if>

<%-- 修改弹出画面-验证 --%>
<c:if test="${not empty solveFields}">
  <s:message var="solveTitle" code="ui.label.title.issue.solve" />
  <g:editDialog dialogId="${idMap['edit-solve-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-solve-submit']}" maxHeight="500" dialogTitle="${solveTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${solveFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit-file']}?uploadType=solve" />
  </g:editDialog>
</c:if>
