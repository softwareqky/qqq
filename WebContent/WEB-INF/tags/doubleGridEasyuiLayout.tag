<%@tag description="基于EasyUI布局的左树+右双grid，支持自动适应窗体大小变化" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%-- 以下是MainGrid相关(右下) --%>
<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="filterFormId" required="true" description="检索栏的Form的id"%>
<%@attribute name="listUrl" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>

<%@attribute name="searchFunctions" required="true" type="java.util.ArrayList" description="检索按钮列表"%>
<%@attribute name="searchFields" required="true" type="java.util.ArrayList" description="普通检索fields列表"%>

<%@attribute name="gridId" required="true" description="DataGrid的id"%>
<%@attribute name="gridTitle" required="true" description="DataGrid的标题"%>
<%@attribute name="defaultOrder" required="true" type="garage.origin.domain.business.OrderByDto" description="一览的默认排序字段名"%>
<%@attribute name="isPage" type="java.lang.Boolean" description="DataGrid是否使用分页条"%>
<%@attribute name="listFrozenFields" required="true" type="java.util.ArrayList" description="一览的固定列fields列表"%>
<%@attribute name="listFields" required="true" type="java.util.ArrayList" description="一览的非固定列fields列表"%>
<%@attribute name="functions" type="java.util.ArrayList" description="功能栏按钮列表"%>

<%@attribute name="isSingleSelect" type="java.lang.Boolean" description="DataGrid是否单选"%>
<%@attribute name="isHandleDblClickRow" required="true" type="java.lang.Boolean" description="是否支持双击行弹出修改画面，配合修改权限控制"%>
<%@attribute name="dblClickRowHandler" description="DataGrid的行双击事件处理器"%>
<%@attribute name="selectHandler" description="DataGrid的行选择和反选事件处理器"%>

<%-- 以下是SubGrid相关(右上) --%>
<%@attribute name="subGridIdPrefix" required="true" description="画面为单位的SubGrid相关的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="subGridFilterFormId" required="true" description="SubGrid检索栏的Form的id"%>
<%@attribute name="subGridListUrl" required="true" description="SubGrid一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>

<%@attribute name="subGridSearchFunctions" required="true" type="java.util.ArrayList" description="SubGrid检索按钮列表"%>
<%@attribute name="subGridSearchFields" required="true" type="java.util.ArrayList" description="SubGrid普通检索fields列表"%>

<%@attribute name="subGridId" required="true" description="SubGrid的id"%>
<%@attribute name="subGridTitle" required="true" description="SubGrid的标题"%>
<%@attribute name="subGridDefaultOrder" required="true" type="garage.origin.domain.business.OrderByDto" description="SubGrid的一览的默认排序字段名"%>
<%@attribute name="isSubGridPage" type="java.lang.Boolean" description="SubGrid是否使用分页条"%>
<%@attribute name="subGridListFrozenFields" required="true" type="java.util.ArrayList" description="SubGrid一览的固定列fields列表"%>
<%@attribute name="subGridListFields" required="true" type="java.util.ArrayList" description="SubGrid一览的非固定列fields列表"%>
<%@attribute name="subGridFunctions" type="java.util.ArrayList" description="SubGrid功能栏按钮列表"%>

<%@attribute name="isSubGridSingleSelect" type="java.lang.Boolean" description="SubGrid是否单选"%>
<%@attribute name="isSubGridHandleDblClickRow" required="true" type="java.lang.Boolean" description="SubGrid是否支持双击行弹出修改画面，配合修改权限控制"%>
<%@attribute name="subGridDblClickRowHandler" description="SubGrid的行双击事件处理器"%>
<%@attribute name="subGridSelectHandler" description="SubGrid的行选择和反选事件处理器"%>

<%@attribute name="linkedFilterFieldName" required="true" description="双Grid画面的场合，点击行时，刷新SubGrid时使用的检索字段名"%>

<%-- 以下是其他 --%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<div class="easyui-layout" data-options="fit:true">

  <%-- Double DataGrids with filter --%>
  <div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">

      <div data-options="region:'north',border:false,height:400">
        <div class="easyui-layout" data-options="fit:true">

          <c:if test="${not empty subGridTitle || not empty subGridSearchFields}">
            <div data-options="region:'north',border:false,height:'auto',maxHeight:95" style="overflow: visible; padding: 5px 15px 0px 13px;">
              <c:if test="${not empty subGridTitle}">
                <div class="grid-title">
                  <i class="fa fa-fw fa-tag"></i>${subGridTitle}
                </div>
              </c:if>
              <c:if test="${not empty subGridSearchFields}">
                <g:filter listUrl="${subGridListUrl}" searchFunctions="${subGridSearchFunctions}" searchFields="${subGridSearchFields}" formId="${subGridFilterFormId}" idPrefix="${subGridIdPrefix}"
                  comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}"
                />
              </c:if>
            </div>
          </c:if>
          <!-- /.north of SubGrid -->

          <div data-options="region:'center',border:false" style="padding: 0px 10px 5px 8px;">
            <g:grid listFields="${subGridListFields}" gridId="${subGridId}" listFrozenFields="${subGridListFrozenFields}" defaultOrder="${subGridDefaultOrder}" listUrl="${subGridListUrl}"
              functions="${subGridFunctions}" isHandleDblClickRow="${isSubGridHandleDblClickRow}" isSingleSelect="${isSubGridSingleSelect}" dblClickRowHandler="${subGridDblClickRowHandler}"
              selectHandler="${subGridSelectHandler}" linkedGridId="${gridId}" linkedFilterFieldName="${linkedFilterFieldName}" isPage="${isSubGridPage}"
            />
          </div>
          <!-- /.center of SubGrid -->

        </div>
        <!-- /.easyui-layout of SubGrid -->
      </div>
      <!-- /.north for SubGrid -->

      <div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit:true">

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
          <!-- /.north of MainGrid -->

          <div data-options="region:'center',border:false" style="padding: 0px 10px 5px 8px;">
            <g:grid listFields="${listFields}" gridId="${gridId}" listFrozenFields="${listFrozenFields}" defaultOrder="${defaultOrder}" listUrl="${listUrl}" functions="${functions}"
              isHandleDblClickRow="${isHandleDblClickRow}" isSingleSelect="${isSingleSelect}" dblClickRowHandler="${dblClickRowHandler}" selectHandler="${selectHandler}" isPage="${isPage}"
            />
          </div>
          <!-- /.center of MainGrid -->

        </div>
        <!-- /.easyui-layout of MainGrid -->
      </div>
      <!-- /.center for MainGrid -->

    </div>
    <!-- /.easyui-layout in center region -->
  </div>
  <!-- center region -->

</div>
<!-- /.easyui-layout outmost -->
