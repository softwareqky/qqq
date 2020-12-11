<%@tag description="查看弹出画面的骨架组件，画面的内容可以用viewGroupLayout.tag或者viewSimpleLayout.tag来构建" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="dialogId" required="true" description="弹出画面Dialog的id"%>
<%@attribute name="maxHeight" required="true" description="弹出画面Dialog的最大高度，内容是否采用分组布局时最大高度不同"%>
<%@attribute name="height" description="弹出画面Dialog的高度，如果不提供，则根据内容自适应"%>
<%@attribute name="width" required="true" description="弹出画面Dialog的宽度，内容是否采用分组布局时宽度不同"%>

<%-- 查看弹出画面 --%>
<div class="hidden">
  <div id="${dialogId}" class="easyui-dialog"
    data-options="title:'<s:message code="ui.common.view"/>',closed:true,buttons:'#${dialogId}Buttons',iconCls:'icon-func-view',<c:if test="${not empty height}">height:${height},</c:if>
            maxHeight:${maxHeight},width:${width},modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
  >
    <jsp:doBody />
  </div>
  
  <%-- 查看弹出画面的按钮 --%>
  <div id="${dialogId}Buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${dialogId}');">
      <s:message code="ui.common.quit" />
    </a>
  </div>
</div>