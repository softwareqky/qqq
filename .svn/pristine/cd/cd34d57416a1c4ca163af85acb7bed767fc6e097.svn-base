<%@tag description="查看画面的内容组件，采用简单布局，适用于所有字段都没有分组的情况" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="viewFields" required="true" type="java.util.ArrayList" description="查看弹出画面的fields列表"%>

<%@attribute name="labelCols" description="label占的列数"%>
<%@attribute name="controlCols" description="输入控件占的列数"%>

<c:if test="${empty labelCols}">
  <c:set var="labelCols" value="5" />
</c:if>
<c:if test="${empty controlCols}">
  <c:set var="controlCols" value="7" />
</c:if>

<div class="container-fluid">
  <c:forEach var="field" items="${viewFields}" varStatus="s">
    <c:choose>
      <c:when test="${field.groupLine}">
        <%-- 字段组分隔线 --%>
        <div class="row row-gapped-top-m">
          <div class="col-xs-12">
            <div style="width: 100%; border-top: 1px solid #CCC; margin-left: auto; margin-right: auto;"></div>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <div class="row row-gapped-top-m">

          <div class="col-xs-${labelCols} col-gapped-left-l col-cell-height-m">${field.captionName}</div>

          <div class="col-xs-${controlCols} col-gapped-s col-cell-height-m">
            <div class="view-value-box" data-view-field="${field.fieldNameView}" data-field-widget="${field.widget}"
              <c:if test="${not empty field.formatter}">data-formatter="${field.formatter}"</c:if> <c:if test="${not empty field.styler}">data-styler="${field.styler}"</c:if>
            ></div>
          </div>
          <!-- /.col-xs-7 -->

        </div>
        <!-- /.row -->
      </c:otherwise>
    </c:choose>
  </c:forEach>
</div>
<!-- /.container-fluid -->