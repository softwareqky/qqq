<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-改善 --%>
<c:if test="${not empty improveFields}">
  <s:message var="improveTitle" code="ui.label.title.acceptance.check.improve" />
  <g:editDialog dialogId="${idMap['edit-improve-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-improve-submit']}" maxHeight="600" dialogTitle="${improveTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${improveFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=improve" />
  </g:editDialog>
</c:if>

<%-- 修改弹出画面-验证 --%>
<c:if test="${not empty verificationFields}">
  <s:message var="verificationTitle" code="ui.label.title.acceptance.check.verification" />
  <g:editDialog dialogId="${idMap['edit-verification-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-verification-submit']}" maxHeight="500" dialogTitle="${verificationTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${verificationFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit-file']}?uploadType=verification" />
  </g:editDialog>
</c:if>
