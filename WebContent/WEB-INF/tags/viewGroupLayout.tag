<%@tag description="查看画面的内容组件，采用分组布局，适用于所有字段都有分组的情况" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="viewFields" required="true" type="java.util.ArrayList" description="查看弹出画面的fields列表"%>

<div class="container-fluid min-width-880">
  <div class="container-fix-width-m" style="padding-top: 20px;">

    <c:set var="group" value="" />
    <c:set var="colCount" value="0" />
    <c:forEach var="field" items="${viewFields}" varStatus="s">
      <%-- 跳过字段组分隔线 --%>
      <c:if test="${not field.groupLine}">

        <%-- 字段label的列数，如果字段的宽度小于4列，则label占用1列，否则label占2列 --%>
        <c:set var="labelColSpan" value="2" />
        <c:if test="${field.columnWidth < 4}">
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

        <div class="col-xs-${labelColSpan} col-gapped-s col-cell-height-m">${field.captionName}</div>
        <div class="col-xs-${field.controlWidth} col-gapped-s col-cell-height-m">
          <div class="view-value-box" data-view-field="${field.fieldNameView}" data-field-widget="${field.widget}" <c:if test="${not empty field.formatter}">data-formatter="${field.formatter}"</c:if>
            <c:if test="${not empty field.styler}">data-styler="${field.styler}"</c:if>
          ></div>
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