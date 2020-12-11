<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/include/head.jsp" flush="true" />
</head>
<body>
  <%-- body内的结构说明同Main._extractContentTabHtmlBody --%>
  <%-- 在body内分为同级并列的4个区域，从上到下依次是： --%>
  <%-- 1. 可见的布局，通常使用bootstrap的container/container-fluid或者EasyUI的layout来构建UI --%>
  <%-- 2. 隐藏的div#EasyuiHiddenWrapper.hidden，用来包含弹出框，每个弹出框都包含在独立的div.hidden内 --%>
  <%-- 3. 隐藏的div#ContentScriptWrapper.hidden，用来包含画面所需的JavaScript脚本引用，供独立调试画面时使用。这些脚本在main.jsp已经引入，所以在tab加载画面body时将会被直接抛弃 --%>
  <%-- 4. 使用script标签引入的画面专有的JavaScript逻辑，由includeJspJsPath变量指定 --%>

  <c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />
  <g:addEditFormGroupLayout formFields="${editFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['save']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" record="${record}"
    navFunctions="${functions}" pageTitle="${pageTitle}"
  />

  <div class="hidden" id="EasyuiHiddenWrapper">

    <c:if test="${not empty includeJspHiddenContentPath}">
      <%-- 子类JSP的第2个扩展点(共2个) --%>
      <jsp:include page="${includeJspHiddenContentPath}" flush="true" />
    </c:if>

  </div>
  <!-- /#EasyuiHiddenWrapper -->

  <div class="hidden" id="ContentScriptWrapper">
    <jsp:include page="/WEB-INF/jsp/common/include/javascriptSrc.jsp" flush="true" />
  </div>
  <!-- /#ContentScriptWrapper -->

  <c:if test="${not empty includeJspJsPath}">
    <%-- 子类JSP的第1个扩展点(共2个) --%>
    <jsp:include page="${includeJspJsPath}" flush="true" />
  </c:if>

</body>
</html>