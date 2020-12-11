<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>工程项目管理系统</title>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<link rel="Shortcut Icon" href="${baseUrl}<s:theme code="shortcut.ico"/>" />

<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/font-awesome/css/font-awesome.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/Ionicons/css/ionicons.min.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="adminlte.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="adminlte.skin.css"/>" />
<link rel="stylesheet" type="text/css" href="${baseUrl}<s:theme code="easyui.css"/>" />
<link rel="stylesheet" type="text/css" href="${baseUrl}<s:theme code="easyui.icon.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="custom.css"/>" />

<!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
<!--[if lt IE 9]>
      <script src="<s:url value="/js/util/bootstrap/html5shiv-3.7.3.min.js"/>"></script>
      <script src="<s:url value="/js/util/bootstrap/respond-1.4.2.min.js"/>"></script>
    <![endif]-->
</head>
<body>
  <div class="container-relative min-width-880">

    <nav class="navbar navbar-default navbar-fixed-top navbar-absolute min-width-880">

      <div class="container-fix-width-m">

        <div class="navbar-header">
          <a class="navbar-brand" href="javascript:void(0)">
            <i class="fa fa-fw fa-pencil"></i><strong>新建项目信息</strong>
          </a>
        </div>
        <!-- /.navbar-header -->

        <div class="collapse navbar-collapse">
          <div class="navbar-right navbar-right-button-group">
            <button type="button" class="btn btn-primary navbar-btn">保存</button>
            <button type="button" class="btn btn-default navbar-btn">关闭</button>
          </div>
        </div>
        <!-- /.collapse -->

      </div>
      <!-- /.container-fix-width-m -->
    </nav>
    <!-- /nav -->

    <form id="${idMap['add-form']}" action="<s:url value="${urlMap['add-submit']}"/>" method="post">
      <div class="container-fix-width-m" style="padding-top: 70px;">

        <div class="panel panel-default panel-default-origin">
          <div class="panel-heading">
            <strong>立项人</strong>
          </div>
          <div class="panel-body">
            <div class="container-fluid">
              <div class="row">
                <div class="col-xs-2 col-gapped-s col-cell-height-m">.col-xs-2</div>
                <div class="col-xs-4 col-gapped-s">
                  <input type="text" class="easyui-textbox" maxlength="50" data-options="prompt:'最大长度:50',width:235,height:32,validType:['length[0,50']" />
                </div>
                <div class="col-xs-2 col-gapped-s col-cell-height-m">.col-xs-2</div>
                <div class="col-xs-4 col-gapped-s col-cell-height-m">
                  <input type="text" class="easyui-textbox" maxlength="50" data-options="prompt:'最大长度:50',width:235,height:32,validType:['length[0,50']" />
                </div>
              </div>
              <div class="row row-gapped-top-m">
                <div class="col-xs-2 col-gapped-s col-cell-height-m">.col-xs-2</div>
                <div class="col-xs-10 col-gapped-s">
                  <input type="text" class="easyui-textbox" maxlength="50" data-options="prompt:'最大长度:50',width:635,multiline:true,height:96,validType:['length[0,50']" />
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="panel panel-default panel-default-origin">
          <div class="panel-heading">
            <strong>建设单位</strong>
          </div>
          <div class="panel-body">
            <div class="container-fluid">
              <div class="row">
                <div class="col-xs-2 col-gapped-s col-cell-height-m">.col-xs-2</div>
                <div class="col-xs-4 col-gapped-s col-cell-height-m">.col-xs-4</div>
                <div class="col-xs-2 col-gapped-s col-cell-height-m">.col-xs-2</div>
                <div class="col-xs-4 col-gapped-s col-cell-height-m">.col-xs-4</div>
              </div>
              <div class="row row-gapped-top-m">
                <div class="col-xs-2 col-gapped-s col-cell-height-m">.col-xs-2</div>
                <div class="col-xs-4 col-gapped-s col-cell-height-m">.col-xs-4</div>
                <div class="col-xs-2 col-gapped-s col-cell-height-m">.col-xs-2</div>
                <div class="col-xs-4 col-gapped-s col-cell-height-m">.col-xs-4</div>
              </div>
            </div>
          </div>
        </div>

      </div>
      <!-- /.container-fix-width-m -->
    </form>

  </div>

  <div class="hidden" id="EasyuiHiddenWrapper">

    <c:if test="${not empty includeJspHiddenContentPath}">
      <%-- 子类JSP的第2个扩展点(共2个) --%>
      <jsp:include page="${includeJspHiddenContentPath}" flush="true" />
    </c:if>

  </div>
  <!-- /#EasyuiHiddenWrapper -->

  <div class="hidden" id="ContentScriptWrapper">

    <jsp:include page="/WEB-INF/jsp/common/include/downloadViaIframe.jsp" flush="true" />

    <jsp:include page="/WEB-INF/jsp/common/include/commonJsVar.jsp" flush="true" />
    <script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/bootstrap/bootstrap-3.4.1.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/adminlte/adminlte-2.4.15.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/jquery-slimscroll/jquery.slimscroll.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/charts/Chart.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/easyui/jquery.easyui-1.8.2.min.js"/>"></script>
    <s:eval var="langEasyui" expression="T(project.edge.domain.business.SystemConfigSettings).instance.langEasyui" />
    <script type="text/javascript" src="<s:url value="/js/util/easyui/locale/easyui-lang-${langEasyui}.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/form/jquery.form.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/json2.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/main.js"/>"></script>

  </div>
  <!-- /#ContentScriptWrapper -->

  <c:if test="${not empty includeJspJsPath}">
    <%-- 子类JSP的第1个扩展点(共2个) --%>
    <jsp:include page="${includeJspJsPath}" flush="true" />
  </c:if>

</body>
</html>