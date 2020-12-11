<%@tag description="新建和修改画面的共用的内容组件，采用简单布局，适用于所有字段都没有分组的情况" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="submitAuditUrl" required="true" description="提交审核的form的action对应的URL"%>
<%@attribute name="accessProcessUrl" required="true" description="流程设置下拉框对应的URL"%>
<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>

<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%@attribute name="isAdd" type="java.lang.Boolean" description="是否是新建的字段，否表示修改的字段"%>

<%@attribute name="labelCols" description="label占的列数"%>
<%@attribute name="controlCols" description="输入控件占的列数"%>

<c:set var="controlWidthPx" value="235" />
<c:if test="${empty labelCols}">
  <c:set var="labelCols" value="3" />
</c:if>
<c:if test="${empty controlCols}">
  <c:set var="controlCols" value="9" />
</c:if>

<%-- 新建修改Form，字段无分组，使用简单样式 --%>
<form action="${submitAuditUrl}" method="post">
  <input type="hidden" name="id" />

  <div class="container-fluid">

    <div class="row row-gapped-top-m">
      <div class="col-xs-${labelCols} col-gapped-left-l col-cell-height-m">
        <s:message code="ui.menu.group.flowable.setting" />
        <strong class="text-danger">*</strong>
      </div>
      <div class="col-xs-${controlCols} col-gapped-s">
        <input id="${idMap['submit-audit-dialog-flowable-combobox']}" name="flowableSetting_" class="easyui-combobox"
          data-options="required:true,panelMinHeight:32,panelMaxHeight:320,panelHeight:null,loadFilter:MainUtils.comboboxLoadFilter,editable:false,valueField:'id',textField:'text',url:'${accessProcessUrl}'"
          style="width: 360px;" data-field-widget="combobox"
        />
      </div>
      <!-- /.col-xs-7 -->
    </div>

    <div class="row row-gapped-top-m">
      <div class="col-xs-12">
        <div style="width: 100%; border-top: 1px solid #CCC; margin-left: auto; margin-right: auto;"></div>
      </div>
    </div>

    <div class="row row-gapped-top-m">
      <div class="col-xs-12">
        <div id="${idMap['submit-audit-dialog-flowable-div']}"></div>
      </div>
    </div>
    <%-- <c:forEach var="field" items="${formFields}" varStatus="s">
      <c:choose>
        <c:when test="${field.groupLine}">
          字段组分隔线
          <div class="row row-gapped-top-m">
            <div class="col-xs-12">
              <div style="width: 100%; border-top: 1px solid #CCC; margin-left: auto; margin-right: auto;"></div>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <div class="row row-gapped-top-m">

            <div class="col-xs-${labelCols} col-gapped-left-l col-cell-height-m">${field.captionName}<c:if test="${field.mandatory}">
                <strong class="text-danger">*</strong>
              </c:if>
            </div>

            <div class="col-xs-${controlCols} col-gapped-s">
              <g:plainFieldControl idPrefix="${idPrefix}" field="${field}" isFixedWidth="${true}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" isAdd="${isAdd}" />
            </div>
            <!-- /.col-xs-7 -->

          </div>
          <!-- /.row -->
        </c:otherwise>
      </c:choose>
    </c:forEach> --%>
  </div>
  <!-- /.container-fluid -->
</form>