<%@tag description="高级检索弹出画面组件" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="dialogId" required="true" description="弹出画面Dialog的id"%>
<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="listUrl" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>

<%@attribute name="filterFormId" required="true" description="关联的此组件外部的检索栏的Form的id"%>
<%@attribute name="gridId" required="true" description="关联的此组件外部的DataGrid的id"%>

<%@attribute name="advSearchFields" required="true" type="java.util.ArrayList" description="高级检索的fields列表"%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%-- 高级检索弹出画面 --%>
<c:if test="${not empty advSearchFields}">
  <div class="hidden">
    <div id="${dialogId}" class="easyui-dialog"
      data-options="title:'<s:message code="ui.common.search.more"/>',closed:true,buttons:'#${dialogId}Buttons',iconCls:'icon-func-search-more',maxHeight:${advSearchDialogMaxHeight},width:${advSearchDialogWidth},modal:true,onMove:EasyDialogUtils.onDialogMove"
    >

      <form action="${listUrl}" method="post">
        <div class="container-fluid">

          <c:forEach var="field" items="${advSearchFields}">
            <c:choose>
              <c:when test="${field.groupLine}">
                <%-- 字段组分隔线 --%>
                <%-- <div class="row row-gapped-top-m">
                  <div class="col-xs-12">
                    <div style="width: 100%; border-top: 1px solid #CCC; margin-left: auto; margin-right: auto;"></div>
                  </div>
                </div> --%>
              </c:when>
              <c:otherwise>
                <div class="row row-gapped-top-m">
                  <div class="col-xs-1 col-gapped-s col-cell-height-m">
                    <input type="checkbox" name="${field.fieldName}" class="col-cell-checkbox" data-value-type="${field.valueType}" data-value-match-mode="${field.valueMatchMode}"
                      data-filter-search-by="${field.filterByRange?2:1}" onclick="FilterUtils.toggleCheckFormField(this, '#${dialogId}');"
                    />
                    &nbsp;
                  </div>
                  <div class="col-xs-${11 - advSearchDialogControlCols} col-gapped-s col-cell-height-m">${field.captionName}</div>
                  <div class="col-xs-${advSearchDialogControlCols} col-gapped-s">

                    <g:plainFilterFieldControl isSimpleFilter="${false}" idPrefix="${idPrefix}" field="${field}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" />
                  </div>
                  <!-- /.col-xs-7 -->

                </div>
                <!-- /.row -->
              </c:otherwise>
            </c:choose>
          </c:forEach>

        </div>
        <!-- /.container-fluid -->
      </form>

    </div>

    <%-- 高级检索弹出画面的按钮 --%>
    <div id="${dialogId}Buttons">
      <div class="dlg-checkbox">
        <input type="checkbox" value="1" data-check-all onclick="FilterUtils.toggleCheckAllFormFields('#${dialogId}')" />
        <span>
          <s:message code="ui.common.select.all" />
        </span>
      </div>
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="FilterUtils.searchAdv('#${dialogId}', '#${gridId}', '#${filterFormId}')">
        <s:message code="ui.common.ok" />
      </a>
      <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${dialogId}');">
        <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
</c:if>