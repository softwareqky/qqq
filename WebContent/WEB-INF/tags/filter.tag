<%@tag description="画面的普通检索组件，在同一个画面上，支持出现多个此组" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="formId" required="true" description="检索栏的Form的id"%>
<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="listUrl" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>

<%@attribute name="searchFunctions" required="true" type="java.util.ArrayList" description="检索按钮列表"%>

<%@attribute name="searchFields" required="true" type="java.util.ArrayList" description="普通检索fields列表"%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%-- easyui-datagrid上部的检索区域 --%>

<div class="filter-wrapper min-width-980">

  <form id="${formId}" action="${listUrl}" method="post" onsubmit="return false">

    <div class="filter-form-control-row">
      <c:forEach var="field" items="${searchFields}">
        <div class="filter-form-group">
          <span class="filter-control-label" data-name="${field.fieldName}" data-value-type="${field.valueType}" data-value-match-mode="${field.valueMatchMode}"
            data-filter-search-by="${field.filterByRange?2:1}"
          >${field.captionName}</span>

          <g:plainFilterFieldControl isSimpleFilter="${true}" idPrefix="${idPrefix}" field="${field}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" />
        </div>
      </c:forEach>
      <c:forEach var="searchFunc" items="${searchFunctions}">
        <div class="filter-form-group">
          <a href="javascript:void(0)" class="easyui-linkbutton" role="button" data-options="iconCls:'${searchFunc.icon}'" onclick="${searchFunc.handler}">${searchFunc.name}</a>
        </div>
      </c:forEach>
    </div>
    <!-- /.filter-form-control-row -->

  </form>

</div>
<!-- /.filter-wrapper -->
