<%@tag description="查看画面的内容组件，采用分组布局，适用于所有字段都有分组的情况" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="viewFields" required="true" type="java.util.ArrayList" description="查看弹出画面的fields列表"%>

<%@attribute name="viewGridDialogId" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>
<%@attribute name="viewDialogGridUrl" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>
<%@attribute name="listUrl" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>
<%@attribute name="gridId" required="true" description="DataGrid的id"%>
<%@attribute name="correlateDataId" required="false" description="data id"%>
<%@attribute name="defaultOrder" required="true" type="garage.origin.domain.business.OrderByDto" description="一览的默认排序字段名"%>
<%@attribute name="listFrozenFields" required="true" type="java.util.ArrayList" description="一览的固定列fields列表"%>
<%@attribute name="listFields" required="true" type="java.util.ArrayList" description="一览的非固定列fields列表"%>
<%@attribute name="functions" type="java.util.ArrayList" description="功能栏按钮列表"%>

<%@attribute name="isSingleSelect" type="java.lang.Boolean" description="DataGrid是否单选"%>
<%@attribute name="isHandleDblClickRow" required="true" type="java.lang.Boolean" description="是否支持双击行弹出修改画面，配合修改权限控制"%>
<%@attribute name="dblClickRowHandler" description="DataGrid的行双击事件处理器"%>
<%@attribute name="selectHandler" description="DataGrid的行选择和反选事件处理器"%>

<%@attribute name="isPage" type="java.lang.Boolean" description="DataGrid是否使用分页条"%>


<%-- 以下是其他 --%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>


<div class="container-fluid min-width-880">
  <div class="container-fix-width-m" style="padding-top: 20px; height: 450px;">
    <c:if test="${not empty gridTitle || not empty searchFields}">
      <div data-options="region:'north',border:false,height:'auto',maxHeight:95" style="overflow: visible; padding: 0px 15px 0px 13px;">
        <c:if test="${not empty gridTitle}">
          <div class="grid-title">
            <i class="fa fa-fw fa-pencil-square-o"></i>${gridTitle}
          </div>
        </c:if>
        <c:if test="${not empty searchFields}">
          <g:filter listUrl="${listUrl}" searchFunctions="${searchFunctions}" searchFields="${searchFields}" formId="${filterFormId}" idPrefix="${idPrefix}" comboboxDataMap="${comboboxDataMap}"
            optionMap="${optionMap}"
          />
        </c:if>
      </div>
    </c:if>
    <table id="${viewGridDialogId}" class="easyui-datagrid" height="410px" data-options="url:'${viewDialogGridUrl}?id=${correlateDataId}',
        multiSort:false,sortOrder:'<c:choose><c:when test="${defaultOrder.desc}">desc</c:when><c:otherwise>asc</c:otherwise></c:choose>',
        singleSelect:true,pagination:true,pageSize:10,pageList:[10,20],idField:'id',showFooter:true,rownumbers:true,
        emptyMsg:'<s:message code="message.info.no.record"/>', nowrap:true,striped:true,
        loadFilter:MainUtils.datagridLoadFilter,onLoadSuccess:MainUtils.handleDatagridLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError"
	>
	  <c:if test="${not empty listFrozenFields}">
	    <thead data-options="frozen:true">
	      <tr>
	        <c:forEach var="column" items="${listFrozenFields}">
	          <th
	            data-options="field:'${column.fieldNameView}',width:${column.columnWidth},align:'center',sortable:${column.sortableColumn}<c:if test="${not empty column.formatter}">,formatter:${column.formatter}</c:if><c:if test="${not empty column.styler}">,styler:${column.styler}</c:if>
	          <c:if test="${not empty column.precision}">,formatter:function(value,row)
	          {
	            return MainUtils.numberPrecisionFormatter(value, ${column.precision});
	          }</c:if>
	          "
	          >${column.captionName}</th>
	        </c:forEach>
	      </tr>
	    </thead>
	  </c:if>
	  <thead>
	    <tr>
	      <c:forEach var="column" items="${listFields}">
	        <th
	          data-options="field:'${column.fieldNameView}',width:${column.columnWidth},align:'center',sortable:${column.sortableColumn}<c:if test="${not empty column.formatter}">,formatter:${column.formatter}</c:if><c:if test="${not empty column.styler}">,styler:${column.styler}</c:if>
	          <c:if test="${not empty column.precision}">,formatter:function(value,row)
	          {
	            return MainUtils.numberPrecisionFormatter(value, ${column.precision});
	          }</c:if>
	          "
	        >${column.captionName}</th>
	      </c:forEach>
	    </tr>
	  </thead>
	</table>
  </div>
</div>
<!-- /.container-fluid -->