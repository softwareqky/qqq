<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-评审结果更新 --%>
<c:if test="${not empty expertReviewFields}">
  <s:message var="expertReviewTitle" code="ui.common.select.expert.review" />
  <g:editDialog dialogId="${idMap['edit-expertreview-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-expertreview-submit']}" maxHeight="600" dialogTitle="${expertReviewTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${expertReviewFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=review" />
  </g:editDialog>
</c:if>

<%-- 修改弹出画面-改善 --%>
<c:if test="${not empty improveFields}">
  <s:message var="improveTitle" code="ui.label.title.project.inspect.improve" />
  <g:editDialog dialogId="${idMap['edit-improve-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-improve-submit']}" maxHeight="600" dialogTitle="${improveTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${improveFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=improve" />
  </g:editDialog>
</c:if>

<%-- 修改弹出画面-验证 --%>
<c:if test="${not empty verificationFields}">
  <s:message var="verificationTitle" code="ui.label.title.project.inspect.verification" />
  <g:editDialog dialogId="${idMap['edit-verification-form-dialog']}" width="620" editSubmitJs="${jsMap['edit-verification-submit']}" maxHeight="500" dialogTitle="${verificationTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${verificationFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit-file']}?uploadType=verification" />
  </g:editDialog>
</c:if>

<%-- 专家选择画面，内容加载的方式与Main.openTab()完全相同，都是基于easyui的panel --%>
<div class="hidden">
  <div id="${idMap['sel-expert-dialog']}" class="easyui-dialog"
    data-options="title:'<s:message code="ui.common.select.expert" />',closed:true,iconCls:'dialog-icon-fa fa fa-fw fa-edit',height:'calc(100vh - 20px)',minHeight:480,width:'480',modal:true,shadow:true,resizable:false,onMove:EasyDialogUtils.onDialogMove,
        href:'${urlMap['sel-expert']}',cache:false,extractor:Main.extractContentTabHtmlBody,onLoadError:MainUtils.handleDatagridLoadError,
        onBeforeClose:Main.onBeforeCotentPanelClose"
  ></div>
</div>