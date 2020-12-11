<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/include/head.jsp" flush="true" />
</head>
<body>
  <%-- body内的结构说明同Main._extractContentTabHtmlBody --%>
  <%-- 在body内分为同级并列的4个区域，从上到下依次是： --%>
  <%-- 1. 可见的布局，通常使用bootstrap的container/container-fluid或者EasyUI的layout来构建UI --%>
  <%-- 2. 隐藏的div#EasyuiHiddenWrapper.hidden，用来包含弹出框，每个弹出框都包含在独立的div.hidden内 --%>
  <%-- 3. 隐藏的div#ContentScriptWrapper.hidden，用来包含画面所需的JavaScript脚本引用，供独立调试画面时使用。这些脚本在main.jsp已经引入，所以在tab加载画面body时将会被直接抛弃 --%>
  <%-- 4. 使用script标签引入的画面专有的JavaScript逻辑，由includeJspJsPath变量指定 --%>

  <c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />
  <g:singleGridEasyuiLayout idPrefix="${idPrefix}" filterFormId="${idMap['filter-form']}" listUrl="${urlMap['list']}" searchFunctions="${searchFunctions}" searchFields="${searchFields}"
    comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" gridId="${idMap['main-datagrid']}" listFields="${listFields}" listFrozenFields="${listFrozenFields}" defaultOrder="${defaultOrder}"
    functions="${functions}" isHandleDblClickRow="${canEditData}" dblClickRowHandler="${dblClickRowHandler}" selectHandler="${selectHandler}" isSingleSelect="${isSingleSelect}"
  />

  <div class="hidden" id="EasyuiHiddenWrapper">

    <%-- 高级检索弹出画面 --%>
    <g:advSearchDialog advSearchFields="${advSearchFields}" dialogId="${idMap['adv-search-form-dialog']}" listUrl="${urlMap['list']}" filterFormId="${idMap['filter-form']}"
      gridId="${idMap['main-datagrid']}" idPrefix="${idPrefix}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}"
    />

    <%-- 新建弹出画面 --%>
    <c:if test="${not empty addFields}">
      <g:addDialog continuousAddSubmitJs="${jsMap['continuous-add-submit']}" dialogId="${idMap['add-form-dialog']}" width="${addDialogWidth}" addSubmitJs="${jsMap['add-submit']}"
        maxHeight="${addDialogMaxHeight}" hasCopyReserve="${hasCopyReserve}" isShadow="false"
      >
        <c:choose>
          <c:when test="${isFieldGrouped}">
            <g:addEditFormGroupLayout formFields="${addFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['add']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" isAdd="true" />
          </c:when>
          <c:otherwise>
            <g:addEditFormSimpleLayout formFields="${addFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['add']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" isAdd="true"
              controlCols="${addDialogControlCols}" labelCols="${12 - addDialogControlCols}"
            />
          </c:otherwise>
        </c:choose>
      </g:addDialog>
    </c:if>

    <%-- 修改弹出画面 --%>
    <c:if test="${not empty editFields}">
      <g:editDialog dialogId="${idMap['edit-form-dialog']}" width="${editDialogWidth}" editSubmitJs="${jsMap['edit-submit']}" maxHeight="${editDialogMaxHeight}" isShadow="false">
        <c:choose>
          <c:when test="${isFieldGrouped}">
            <g:addEditFormGroupLayout formFields="${editFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" />
          </c:when>
          <c:otherwise>
            <g:addEditFormSimpleLayout formFields="${editFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}"
              controlCols="${editDialogControlCols}" labelCols="${12 - editDialogControlCols}"
            />
          </c:otherwise>
        </c:choose>
      </g:editDialog>
    </c:if>

    <%-- 查看弹出画面 --%>
    <c:if test="${not empty viewFields}">
      <g:viewDialog dialogId="${idMap['view-dialog']}" width="${viewDialogWidth}" maxHeight="${viewDialogMaxHeight}">
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

    <%-- 提交审核画面 --%>
    <c:if test="${isHasFlowable}">
      <g:submitAuditDialog dialogId="${idMap['submit-audit-dialog']}" auditSubmitJs="${jsMap['local-audit-submit']}" width="500" height="400" maxHeight="600" isShadow="false">
        <g:submitAuditFormLayout idPrefix="${idPrefix}" submitAuditUrl="${urlMap['submit-audit']}" accessProcessUrl="${urlMap['access-process-list']}" />
      </g:submitAuditDialog>
    </c:if>


    <%-- 
    <jsp:include page="/WEB-INF/jsp/component/processStatusDialog.jsp" flush="true" />
    <jsp:include page="/WEB-INF/jsp/component/auditSubmitDialog.jsp" flush="true" /> --%>

    <c:if test="${not empty includeJspHiddenContentPath}">
      <%-- 子类JSP的第2个扩展点(共2个) --%>
      <jsp:include page="${includeJspHiddenContentPath}" flush="true" />
    </c:if>

  </div>
  <!-- /#EasyuiHiddenWrapper -->

  <div class="hidden" id="ContentScriptWrapper">
    <jsp:include page="/WEB-INF/jsp/common/include/javascriptSrc.jsp" flush="true" />
  </div>
  <!-- /#ContentScriptWrapper -->

  <c:if test="${not empty includeJspJsPath}">
    <%-- 子类JSP的第1个扩展点(共2个) --%>
    <jsp:include page="${includeJspJsPath}" flush="true" />
  </c:if>
  
	<script type="text/javascript">
		var flowableSettingId = '${flowableSettingId}';
		console.log("flowableSettingId:::::" + flowableSettingId);
		if (flowableSettingId) {
			$("select[name='flowName']").parent().css("display","none");
		}
	</script>
</body>
</html>