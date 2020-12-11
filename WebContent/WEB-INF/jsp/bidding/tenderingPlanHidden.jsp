<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面 --%>
<c:if test="${not empty verificationFields}">
  <s:message var="verificationTitle" code="ui.label.title.project.check.verification" />
  <g:editDialog dialogId="${idMap['edit-verification-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-verification-submit']}" maxHeight="600" dialogTitle="${verificationTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${verificationFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit-file']}" />
  </g:editDialog>
</c:if>
