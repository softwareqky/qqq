<%@tag description="基于EasyUI布局的单grid，支持自动适应窗体大小变化" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="filterFormId" required="true" description="检索栏的Form的id"%>
<%@attribute name="listUrl" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>

<%@attribute name="searchFunctions" required="true" type="java.util.ArrayList" description="检索按钮列表"%>

<%@attribute name="searchFields" required="true" type="java.util.ArrayList" description="普通检索fields列表"%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%@attribute name="gridId" required="true" description="DataGrid的id"%>
<%@attribute name="defaultOrder" required="true" type="garage.origin.domain.business.OrderByDto" description="一览的默认排序字段名"%>
<%@attribute name="listFrozenFields" required="true" type="java.util.ArrayList" description="一览的固定列fields列表"%>
<%@attribute name="listFields" required="true" type="java.util.ArrayList" description="一览的非固定列fields列表"%>
<%@attribute name="functions" type="java.util.ArrayList" description="功能栏按钮列表"%>

<%@attribute name="isPage" type="java.lang.Boolean" description="DataGrid是否使用分页条"%>
<%@attribute name="isSingleSelect" type="java.lang.Boolean" description="DataGrid是否单选"%>
<%@attribute name="isHandleDblClickRow" required="true" type="java.lang.Boolean" description="是否支持双击行弹出修改画面，配合修改权限控制"%>
<%@attribute name="dblClickRowHandler" description="DataGrid的行双击事件处理器"%>
<%@attribute name="selectHandler" description="DataGrid的行选择和反选事件处理器"%>
<%@attribute name="clickCellHandler" description="DataGrid的单元格选择事件处理器"%>

<div class="easyui-layout" data-options="fit:true">

  <c:if test="${not empty searchFields}">
    <div data-options="region:'north',border:false,height:'auto',maxHeight:95" style="overflow: visible; padding: 5px 15px 0px;">
      <g:filter listUrl="${listUrl}" searchFunctions="${searchFunctions}" searchFields="${searchFields}" formId="${filterFormId}" idPrefix="${idPrefix}" comboboxDataMap="${comboboxDataMap}"
        optionMap="${optionMap}"
      />
    </div>
  </c:if>

  <div data-options="region:'center',border:false" style="padding: 5px 10px;">
    <g:grid listFields="${listFields}" gridId="${gridId}" listFrozenFields="${listFrozenFields}" defaultOrder="${defaultOrder}" listUrl="${listUrl}" functions="${functions}"
      isHandleDblClickRow="${isHandleDblClickRow}" isSingleSelect="${isSingleSelect}" dblClickRowHandler="${dblClickRowHandler}" selectHandler="${selectHandler}" isPage="${isPage}" clickCellHandler="${clickCellHandler}"
    />
  </div>

</div>
<!-- /.easyui-layout -->
