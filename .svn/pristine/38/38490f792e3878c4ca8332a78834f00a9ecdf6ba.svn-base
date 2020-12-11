<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/include/head.jsp" flush="true" />
</head>
<body>
  <div class="container-fluid">

    <%-- <jsp:include page="/WEB-INF/jsp/component/filters.jsp" flush="true" /> --%>

    <div class="row row-gapped-top-s">
      <div class="col-sm-12">
        <div class="row-single-col-cell">
          <div class="alert alert-info alert-info-origin" role="alert">
            <i class="fa fa-fw fa-calculator"></i> <strong>工程造价合计：</strong> ￥555,315,440.64
          </div>
        </div>
      </div>
    </div>

    <%-- <jsp:include page="/WEB-INF/jsp/component/mainDatagrid.jsp" flush="true" /> --%>

  </div>
  <!-- /.container-fluid -->

  <div class="hidden" id="EasyuiHiddenWrapper">

    <%-- <jsp:include page="/WEB-INF/jsp/component/advSearchDialog.jsp" flush="true" /> --%>
    <jsp:include page="/WEB-INF/jsp/deprecated_/component_/processStatusDialog.jsp" flush="true" />
    <jsp:include page="/WEB-INF/jsp/deprecated_/component_/auditSubmitDialog.jsp" flush="true" />

    <c:if test="${not empty includeJspHiddenContentPath}">
      <%-- 子类JSP的第2个扩展点(共2个) --%>
      <jsp:include page="${includeJspHiddenContentPath}" flush="true" />
    </c:if>

  </div>
  <!-- /#EasyuiHiddenWrapper -->

  <div class="hidden" id="ContentScriptWrapper">
    <jsp:include page="/WEB-INF/jsp/common/include/downloadViaIframe.jsp" flush="true" />
    <jsp:include page="/WEB-INF/jsp/common/include/javascriptSrc.jsp" flush="true" />
  </div>
  <!-- /#ContentScriptWrapper -->

  <c:if test="${not empty includeJspJsPath}">
    <%-- 子类JSP的第1个扩展点(共2个) --%>
    <jsp:include page="${includeJspJsPath}" flush="true" />
  </c:if>

</body>
</html>