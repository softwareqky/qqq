<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-采购状态更新 --%>
<c:if test="${not empty purchaseStatusFields}">
  <s:message var="expertReviewTitle" code="ui.common.select.expert.review" />
  <g:editDialog dialogId="${idMap['edit-purchase-status-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-purchase-status-submit']}" maxHeight="600" dialogTitle="${expertReviewTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${purchaseStatusFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit-purchase-status-submit-url']}" />
  </g:editDialog>
</c:if>
