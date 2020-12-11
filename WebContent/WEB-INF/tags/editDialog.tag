<%@tag description="修改弹出画面的骨架组件，画面的内容可以用addEditFormGroupLayout.tag或者addEditFormSimpleLayout.tag来构建" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="dialogId" required="true" description="弹出画面Dialog的id"%>
<%@attribute name="maxHeight" required="true" description="弹出画面Dialog的最大高度，内容是否采用分组布局时最大高度不同"%>
<%@attribute name="height" description="弹出画面Dialog的高度，如果不提供，则根据内容自适应"%>
<%@attribute name="width" required="true" description="弹出画面Dialog的宽度，内容是否采用分组布局时宽度不同"%>
<%@attribute name="editSubmitJs" required="true" description="修改按钮的JavaScript处理函数"%>

<%@attribute name="isShadow" type="java.lang.Boolean" description="如果存在附件字段，则关闭shadow，防止删除附件时，Dialog自适应减小高度，但shadow的高度不变"%>
<%@attribute name="dialogTitle" description="弹出画面Dialog的title"%>
<%@attribute name="dialogIconClass" description="弹出画面Dialog的icon"%>
<c:if test="${empty isShadow}">
  <c:set var="isShadow" value="false" />
</c:if>
<c:if test="${empty dialogTitle}">
  <s:message var="dialogTitle" code="ui.common.modify" />
</c:if>
<c:if test="${empty dialogIconClass}">
  <c:set var="dialogIconClass" value="icon-func-edit" />
</c:if>

<%-- 修改弹出画面 --%>
<div class="hidden">
  <div id="${dialogId}" class="easyui-dialog"
    data-options="title:'${dialogTitle}',closed:true,buttons:'#${dialogId}Buttons',iconCls:'${dialogIconClass}',<c:if test="${not empty height}">height:${height},</c:if>
            maxHeight:${maxHeight},width:${width},modal:true,shadow:${isShadow},resizable:false,onMove:EasyDialogUtils.onDialogMove"
  >
    <jsp:doBody />
  </div>

  <%-- 修改弹出画面的按钮 --%>
  <div id="${dialogId}Buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="${editSubmitJs}">
      <s:message code="ui.common.ok" />
    </a>
    <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${dialogId}');">
      <s:message code="ui.common.quit" />
    </a>
  </div>
</div>