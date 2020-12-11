<%@tag description="DataGrid及其功能按钮栏组件，在同一个画面上，支持出现多个此组件" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@attribute name="gridId" required="true" description="DataGrid的id"%>
<%@attribute name="listUrl" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>
<%@attribute name="defaultOrder" required="true" type="garage.origin.domain.business.OrderByDto" description="一览的默认排序字段名"%>
<%@attribute name="listFrozenFields" required="true" type="java.util.ArrayList" description="一览的固定列fields列表"%>
<%@attribute name="listFields" required="true" type="java.util.ArrayList" description="一览的非固定列fields列表"%>
<%@attribute name="functions" type="java.util.ArrayList" description="功能栏按钮列表"%>

<%@attribute name="autoRowHeight" type="java.lang.Boolean" description="DataGrid是否自动行高"%>
<%@attribute name="isFitColumns" type="java.lang.Boolean" description="DataGrid是否自动列宽"%>
<%@attribute name="isPage" type="java.lang.Boolean" description="DataGrid是否使用分页条"%>
<%@attribute name="isRemoteSort" type="java.lang.Boolean" description="DataGrid是否使用远程排序"%>
<%@attribute name="view" description="DataGrid使用的视图"%>
<%@attribute name="isSingleSelect" type="java.lang.Boolean" description="DataGrid是否单选"%>
<%@attribute name="isHandleDblClickRow" required="true" type="java.lang.Boolean" description="是否支持双击行弹出修改画面，配合修改权限控制"%>
<%@attribute name="dblClickRowHandler" description="DataGrid的行双击事件处理器"%>
<%@attribute name="selectHandler" description="DataGrid的行选择和反选事件处理器"%>
<%@attribute name="clickCellHandler" description="DataGrid的单元格选择事件处理器"%>

<%@attribute name="linkedGridId" description="双Grid画面的场合，此SubGrid关联的MainGrid的id，点击行时，将刷新外部的datagrid"%>
<%@attribute name="linkedFilterFieldName" description="双Grid画面的场合，点击行时，将刷新MainGrid时使用的检索字段名"%>

<%-- easyui-datagrid --%>
<c:if test="${isHandleDblClickRow && empty dblClickRowHandler}">
  <c:set var="dblClickRowHandler" value="GridUtils.handleDblClickRow" />
</c:if>
<c:if test="${empty selectHandler}">
  <c:set var="selectHandler" value="GridUtils.handleSelect" />
</c:if>
<c:if test="${empty clickCellHandler}">
  <c:set var="clickCellHandler" value="GridUtils.handleCell" />
</c:if>
<c:if test="${empty isSingleSelect}">
  <c:set var="isSingleSelect" value="false" />
</c:if>
<c:if test="${empty autoRowHeight}">
  <c:set var="autoRowHeight" value="false" />
</c:if>
<c:if test="${empty isFitColumns}">
  <c:set var="isFitColumns" value="false" />
</c:if>
<c:if test="${empty isPage}">
  <c:set var="isPage" value="true" />
</c:if>
<c:if test="${empty isRemoteSort}">
  <c:set var="isRemoteSort" value="true" />
</c:if>
<c:if test="${not empty view}">
  <c:set var="view" value="view:${view}," />
</c:if>
<table id="${gridId}" class="easyui-datagrid" <c:if test="${not empty linkedGridId}">data-linked-grid-id="${linkedGridId}"</c:if>
  <c:if test="${not empty linkedFilterFieldName}">data-linked-filter-field-name="${linkedFilterFieldName}"</c:if>
  data-options="url:'${listUrl}',<c:if test="${not empty functions}">toolbar:'#${gridId}Toolbar',</c:if>remoteSort:${isRemoteSort},
                multiSort:false,sortName:'${defaultOrder.columnName}',sortOrder:'<c:choose><c:when test="${defaultOrder.desc}">desc</c:when><c:otherwise>asc</c:otherwise></c:choose>',
                singleSelect:${isSingleSelect},pagination:${isPage},${view}rownumbers:true,pageSize:30,pageList:[30,50],idField:'id',showFooter:true,
                emptyMsg:'<s:message code="message.info.no.record"/>',
                autoRowHeight:${autoRowHeight},nowrap:true,striped:true,fit:true,fitColumns:${isFitColumns},
                <c:if test="${isHandleDblClickRow}">onDblClickRow:${dblClickRowHandler},</c:if>
                loadFilter:MainUtils.datagridLoadFilter,onLoadSuccess:MainUtils.handleDatagridLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError,
                onSelect:${selectHandler},onUnselect:${selectHandler},onSelectAll:${selectHandler},onUnselectAll:${selectHandler},onClickCell:${clickCellHandler}"
>
  <c:if test="${not empty listFrozenFields}">
    <thead data-options="frozen:true">
      <tr>
        <c:if test="${not isSingleSelect}">
          <th data-options="field:'ck',checkbox:true"></th>
        </c:if>
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
      <c:if test="${empty listFrozenFields && not isSingleSelect}">
        <th data-options="field:'ck',checkbox:true"></th>
      </c:if>
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

<c:if test="${not empty functions}">
  <div id="${gridId}Toolbar">
    <table>
      <tbody>
        <tr>
          <c:forEach var="func" items="${functions}">
            <td>
              <c:choose>
                <c:when test="${func.seperator}">
                  <div class="datagrid-btn-separator" style="margin-left: 5px;"></div>
                </c:when>
                <c:otherwise>
                  <a id="${func.id}" href="<c:choose><c:when test="${not empty func.href}"><s:url value="${func.href}"/></c:when><c:otherwise>javascript:void(0)</c:otherwise></c:choose>"
                    class="easyui-linkbutton" data-options="iconCls:'${func.icon}',plain:'true'" role="button" onclick="${func.handler}"
                  >${func.name}</a>
                </c:otherwise>
              </c:choose>
            </td>
          </c:forEach>
        </tr>
      </tbody>
    </table>
  </div>
</c:if>
