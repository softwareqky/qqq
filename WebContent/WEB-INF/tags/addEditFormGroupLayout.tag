<%@tag description="新建和修改画面的共用的内容组件，采用分组布局，适用于所有字段都有分组的情况" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="submitUrl" required="true" description="新建/修改的form的action对应的URL"%>
<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>

<%@attribute name="formFields" required="true" type="java.util.ArrayList" description="新建/修改的fields列表"%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%@attribute name="isAdd" type="java.lang.Boolean" description="是否是新建的字段，否表示修改的字段"%>

<%-- 以下属性仅供表单画面专用，弹出画面无需关心 --%>
<%@attribute name="navFunctions" type="java.util.ArrayList" description="功能按钮列表，如果提供，则渲染NAV工具栏"%>
<%@attribute name="record" type="java.util.Map" description="独立Form画面的数据记录"%>
<%@attribute name="pageTitle" description="独立Form画面的标题"%>

<%-- 新建/修改的Form，字段使用分组样式，所有字段必须都有分组 --%>
<form action="${submitUrl}" method="post">
  <input type="hidden" name="id" />

  <div class="container-fluid min-width-880">

    <c:if test="${not empty navFunctions}">
      <%-- 表单画面专用工具栏 --%>
      <nav class="navbar navbar-default navbar-fixed-top navbar-absolute min-width-880">

        <div class="container-fix-width-m">

          <div class="navbar-header">
            <a class="navbar-brand" href="javascript:void(0)">
              <i class="fa fa-fw fa-pencil"></i><strong>${pageTitle}</strong>
            </a>
          </div>
          <!-- /.navbar-header -->

          <div class="collapse navbar-collapse">
            <div class="navbar-right navbar-right-button-group">
              <c:forEach var="func" items="${navFunctions}" varStatus="s">

                <c:choose>
                  <c:when test="${s.first}">
                    <c:set var="btnClass" value="btn-primary" />
                  </c:when>
                  <c:otherwise>
                    <c:set var="btnClass" value="btn-default" />
                  </c:otherwise>
                </c:choose>

                <button id="${func.id}" type="button" class="btn ${btnClass} navbar-btn" onclick="${func.handler}">${func.name}</button>

              </c:forEach>
              <button type="button" class="btn btn-default navbar-btn" onclick="Main.closeThisTab();">
                <s:message code="ui.common.close" />
              </button>
            </div>
          </div>
          <!-- /.collapse -->

        </div>
        <!-- /.container-fix-width-m -->
      </nav>
      <!-- /nav -->
    </c:if>

    <c:choose>
      <c:when test="${not empty navFunctions}">
        <c:set var="paddingTop" value="70" />
      </c:when>
      <c:otherwise>
        <c:set var="paddingTop" value="20" />
      </c:otherwise>
    </c:choose>

    <div class="container-fix-width-m" style="padding-top: ${paddingTop}px;">

      <c:set var="group" value="" />
      <c:set var="colCount" value="0" />
      <c:forEach var="field" items="${formFields}" varStatus="s">
        <%-- 跳过字段组分隔线 --%>
        <c:if test="${not field.groupLine}">

          <%-- 字段label的列数，如果字段的宽度小于4列，则label占用1列，否则label占2列 --%>
          <c:set var="labelColSpan" value="2" />
          <c:if test="${field.controlWidth < 4}">
            <c:set var="labelColSpan" value="1" />
          </c:if>

          <c:choose>
            <c:when test="${group != field.fieldGroup}">
              <%-- 新的group开始 --%>

              <c:if test="${not empty group}">
                <%-- 如果存在上一个group，则输出row结尾以及上一个panel的结尾 --%>
                <c:out value="</div></div></div></div>" escapeXml="false" />
              </c:if>

              <%-- 直接输出panel开头及row开头 --%>
              <c:out
                value='<div class="panel panel-default panel-default-origin"><div class="panel-heading"><strong>${field.fieldGroup}</strong></div><div class="panel-body"><div class="container-fluid"><div class="row">'
                escapeXml="false"
              />

              <c:set var="group" value="${field.fieldGroup}" />
              <c:set var="colCount" value="0" />

            </c:when>
            <c:otherwise>
              <%-- 同一个group内 --%>

              <%-- 如果列数超过bootstrap的12列，则换行 --%>
              <c:if test="${colCount + labelColSpan + field.controlWidth > 12}">
                <c:out value='</div><div class="row row-gapped-top-m">' escapeXml="false" />
                <c:set var="colCount" value="0" />
              </c:if>

            </c:otherwise>
          </c:choose>

          <div class="col-xs-${labelColSpan} col-gapped-s col-cell-height-m">${field.captionName}<c:if test="${field.mandatory}">
              <strong class="text-danger">*</strong>
            </c:if>
          </div>
          <div class="col-xs-${field.controlWidth} col-gapped-s">
            <g:plainFieldControl idPrefix="${idPrefix}" field="${field}" isFixedWidth="${false}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" record="${record}" isAdd="${isAdd}" />
          </div>

          <c:set var="colCount" value="${colCount + labelColSpan + field.controlWidth}" />

          <%-- 最后一个字段，输出row结尾以及panel结尾 --%>
          <c:if test="${s.last}">
            <c:out value="</div></div></div></div>" escapeXml="false" />
          </c:if>

        </c:if>
      </c:forEach>
    </div>
    <!-- /.container-fix-width-m -->

  </div>
  <!-- /.container-fluid -->
</form>