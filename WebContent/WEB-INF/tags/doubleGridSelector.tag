<%@tag description="基于EasyUI布局的左树+中grid+右grid，支持自动适应窗体大小变化。基于treeDoubleGridEasyuiLayout.tag进行适当调整" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%-- 以下是MainGrid相关(右侧) --%>
<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>

<%@attribute name="gridId" required="true" description="DataGrid的id"%>
<%@attribute name="gridTitle" required="true" description="DataGrid的标题"%>
<%@attribute name="defaultOrder" required="true" type="garage.origin.domain.business.OrderByDto" description="一览的默认排序字段名"%>
<%@attribute name="listFrozenFields" required="true" type="java.util.ArrayList" description="一览的固定列fields列表"%>
<%@attribute name="listFields" required="true" type="java.util.ArrayList" description="一览的非固定列fields列表"%>

<%@attribute name="isHandleDblClickRow" required="true" type="java.lang.Boolean" description="是否支持双击行弹出修改画面，配合修改权限控制"%>
<%@attribute name="dblClickRowHandler" description="DataGrid的行双击事件处理器"%>
<%@attribute name="selectHandler" description="DataGrid的行选择和反选事件处理器"%>

<%-- 以下是SubGrid相关(中间) --%>
<%@attribute name="subGridIdPrefix" required="true" description="画面为单位的SubGrid相关的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="subGridFilterFormId" required="true" description="SubGrid检索栏的Form的id"%>
<%@attribute name="subGridListUrl" required="true" description="SubGrid一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>

<%@attribute name="subGridSearchFunctions" required="true" type="java.util.ArrayList" description="SubGrid检索按钮列表"%>
<%@attribute name="subGridSearchFields" required="true" type="java.util.ArrayList" description="SubGrid普通检索fields列表"%>

<%@attribute name="subGridId" required="true" description="SubGrid的id"%>
<%@attribute name="subGridDefaultOrder" required="true" type="garage.origin.domain.business.OrderByDto" description="SubGrid的一览的默认排序字段名"%>
<%@attribute name="subGridListFrozenFields" required="true" type="java.util.ArrayList" description="SubGrid一览的固定列fields列表"%>
<%@attribute name="subGridListFields" required="true" type="java.util.ArrayList" description="SubGrid一览的非固定列fields列表"%>

<%@attribute name="isSubGridHandleDblClickRow" required="true" type="java.lang.Boolean" description="SubGrid是否支持双击行弹出修改画面，配合修改权限控制"%>
<%@attribute name="subGridDblClickRowHandler" description="SubGrid的行双击事件处理器"%>
<%@attribute name="subGridSelectHandler" description="SubGrid的行选择和反选事件处理器"%>

<%-- 以下是其他 --%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<div class="easyui-layout" data-options="fit:true">

  <%-- Center SubGrid with filter --%>
  <div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">

      <div data-options="region:'north',border:false,height:'auto',maxHeight:95" style="overflow: visible; padding: 5px 10px 0px 13px;">
        <c:if test="${not empty subGridSearchFields}">
          <g:filter listUrl="${subGridListUrl}" searchFunctions="${subGridSearchFunctions}" searchFields="${subGridSearchFields}" formId="${subGridFilterFormId}" idPrefix="${subGridIdPrefix}"
            comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}"
          />
        </c:if>
      </div>
      <!-- /.north of SubGrid -->

      <div data-options="region:'center',border:false" style="padding: 0px 5px 5px 8px;">
        <g:grid listFields="${subGridListFields}" gridId="${subGridId}" listFrozenFields="${subGridListFrozenFields}" defaultOrder="${subGridDefaultOrder}" listUrl="${subGridListUrl}"
          isHandleDblClickRow="${isSubGridHandleDblClickRow}" isSingleSelect="false" dblClickRowHandler="${subGridDblClickRowHandler}" selectHandler="${subGridSelectHandler}"
        />
      </div>
      <!-- /.center of SubGrid -->

    </div>
    <!-- /.easyui-layout of SubGrid -->

  </div>
  <!-- center region -->

  <%-- East MainGrid --%>
  <div data-options="region:'east',border:false,width:340,maxWidth:340">
    <div class="easyui-layout" data-options="fit:true">

      <%-- 穿梭按钮 --%>
      <div data-options="region:'west',border:false,width:40,maxWidth:40">
        <div style="position: absolute; top: 150px; left: 0px; width: 40px;">
          <a href="javascript:void(0)" class="easyui-linkbutton c8" data-options="width:'100%'" role="button" onclick="PopupSelectUtils.select('#${subGridId}','#${gridId}')">
            <i class="fa fa-fw fa-arrow-right"></i>
          </a>
        </div>
        <div style="position: absolute; top: 190px; left: 0px; width: 40px;">
          <a href="javascript:void(0)" class="easyui-linkbutton c8" data-options="width:'100%'" role="button" onclick="PopupSelectUtils.unselect('#${gridId}')">
            <i class="fa fa-fw fa-arrow-left"></i>
          </a>
        </div>
      </div>

      <%-- MainGrid --%>
      <div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit:true">
          <div data-options="region:'north',border:false,height:'auto',maxHeight:95" style="overflow: visible; padding: 0px 10px 0px 10px;">
            <div class="grid-title">
              <i class="fa fa-fw fa-pencil-square-o"></i>${gridTitle}
            </div>
          </div>
          <!-- /.north of MainGrid -->

          <div data-options="region:'center',border:false" style="padding: 0px 5px 5px 5px;">
            <g:grid listFields="${listFields}" gridId="${gridId}" listFrozenFields="${listFrozenFields}" defaultOrder="${defaultOrder}" listUrl="" isHandleDblClickRow="${isHandleDblClickRow}"
              isSingleSelect="false" dblClickRowHandler="${dblClickRowHandler}" selectHandler="${selectHandler}" isPage="false" isRemoteSort="false"
            />
          </div>
          <!-- /.center of MainGrid -->
        </div>
      </div>

    </div>
  </div>
  <!-- east region -->

</div>
<!-- /.easyui-layout outmost -->
