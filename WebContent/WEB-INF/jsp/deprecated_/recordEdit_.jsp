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
  <div class="container-fluid min-width-880">

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
            <button type="button" class="btn btn-primary navbar-btn" onclick="${jsMap['edit-submit']}">保存</button>
            <button type="button" class="btn btn-default navbar-btn" onclick="Main.closeThisTab();">关闭</button>
          </div>
        </div>
        <!-- /.collapse -->

      </div>
      <!-- /.container-fix-width-m -->
    </nav>
    <!-- /nav -->

    <div class="container-fix-width-m" style="padding-top: 70px;">
      <form id="${idMap['edit-form']}" action="<s:url value="${urlMap['edit-submit']}"/>" method="post">
        <input type="hidden" name="id" value="${record['id']}" />
        <c:set var="group" value="" />
        <c:set var="colCount" value="0" />
        <c:forEach var="field" items="${addEditFields}" varStatus="s">

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
              <c:if test="${colCount + labelColSpan + field.columnWidth > 12}">
                <c:out value='</div><div class="row row-gapped-top-m">' escapeXml="false" />
                <c:set var="colCount" value="0" />
              </c:if>

            </c:otherwise>
          </c:choose>

          <div class="col-xs-${labelColSpan} col-gapped-s col-cell-height-m">${field.captionName}</div>
          <div class="col-xs-${field.columnWidth} col-gapped-s">
            <c:choose>
              <c:when test="${field.widget eq 'checkbox' }">
                <input name="${field.fieldName}" type="checkbox" value="1" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
                  <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
                />
              </c:when>
              <c:otherwise>
                <c:choose>
                  <c:when test="${field.widget eq 'combobox'}">
                    <select class="easyui-combobox" name="${field.fieldName}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
                      <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
                      data-options="height:32<c:choose><c:when test="${not empty field.width}">,width:${field.width}</c:when><c:otherwise>,width:235</c:otherwise></c:choose>
                                    ,panelMinHeight:32,panelMaxHeight:320,panelHeight:null,editable:false
                                    <c:if test="${not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
                                    <c:if test="${field.mandatory}">,required:true</c:if>
                                    <c:if test="${field.multiLine}">,multiple:true</c:if>
                                    <c:if test="${not empty field.valueField}">,valueField:'${field.valueField}'</c:if>
                                    <c:if test="${not empty field.textField}">,textField:'${field.textField}'</c:if>
                                    <c:if test="${not empty field.dataKey}">,data:${comboboxDataMap[field.dataKey]}</c:if>
                                    <c:if test="${not empty field.handler}">,${field.handler}</c:if>
                                    <c:if test="${not empty field.url}">,loadFilter:MainUtils.comboboxLoadFilter,valueField:'id',textField:'text',url:'<s:url value="${field.url}"/>'</c:if>"
                    >
                      <c:if test="${not empty field.option}">
                        <c:if test="${not field.mandatory}">
                          <option value="">&nbsp;</option>
                        </c:if>
                        <c:forEach var="option" items="${optionMap[field.option]}">
                          <option value="${option.id}">${option.text}</option>
                        </c:forEach>
                      </c:if>
                    </select>
                  </c:when>
                  <c:when test="${field.widget eq 'datetimebox'}">
                    <select class="easyui-datetimebox" name="${field.fieldName}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
                      <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
                      data-options="height:32<c:choose><c:when test="${not empty field.width}">,width:${field.width}</c:when><c:otherwise>,width:235</c:otherwise></c:choose>,editable:false
                                  ,formatter:MainUtils.datetimeFormatter,parser:MainUtils.datetimeParser
                                  <c:if test="${not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
                                  <c:if test="${field.mandatory}">,required:true</c:if>"
                    >
                    </select>
                  </c:when>
                  <c:when test="${field.widget eq 'datebox'}">
                    <select class="easyui-datebox" name="${field.fieldName}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
                      <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
                      data-options="height:32<c:choose><c:when test="${not empty field.width}">,width:${field.width}</c:when><c:otherwise>,width:235</c:otherwise></c:choose>,editable:false,
                                  formatter:MainUtils.dateFormatter,parser:MainUtils.datetimeParser
                                  <c:if test="${not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
                                  <c:if test="${field.mandatory}">,required:true</c:if>
                                  <c:if test="${not empty field.validType}">,validType:'${field.validType}'</c:if> "
                    >
                    </select>
                  </c:when>
                  <c:when test="${field.widget eq 'timespinner'}">
                    <select class="easyui-timespinner" name="${field.fieldName}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
                      <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
                      data-options="height:32<c:choose><c:when test="${not empty field.width}">,width:${field.width}</c:when><c:otherwise>,width:235</c:otherwise></c:choose>,editable:true,showSeconds:true
                                  <c:if test="${not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
                                  <c:if test="${field.mandatory}">,required:true</c:if>
                                  <c:if test="${not empty field.validType}">,validType:'${field.validType}'</c:if> "
                    >
                    </select>
                  </c:when>
                  <c:otherwise>
                    <input name="${field.fieldName}" type="<c:if test="${field.password}">password</c:if><c:if test="${not field.password}">text</c:if>" class="easyui-validatebox form-control"
                      placeholder="${field.validationPrompt}" style="width: 235px; height: 32px;" value="${record[field.fieldName]}" data-field-widget="${field.widget}"
                      <c:if test="${field.copyReserve}">data-copy-reserve</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
                      <c:if test="${not empty field.maxLength}">maxlength="${field.maxLength}"</c:if>
                      data-options="prompt:'${field.validationPrompt}'<c:choose><c:when test="${not empty field.width}">,width:${field.width}</c:when><c:otherwise>,width:235</c:otherwise></c:choose>
                                  <c:if test="${not empty field.buttonText}">,buttonText:'${field.buttonText}'<c:if test="${field.widget == 'textbox'}">,editable:true</c:if></c:if>
                                  <c:if test="${not empty field.handler}">,${field.handler}</c:if>
                                  <c:if test="${field.multiLine}">,multiline:true,height:64</c:if>
                                  <c:if test="${not field.multiLine}">,height:32</c:if>
                                  <c:if test="${not empty field.min}">,min:${field.min}</c:if>
                                  <c:if test="${not empty field.max}">,max:${field.max}</c:if>
                                  <c:if test="${not empty field.precision}">,precision:${field.precision}</c:if>
                                  <c:if test="${not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
                                  <c:if test="${field.mandatory}">,required:true</c:if>
                                  <c:if test="${not empty field.maxLength}">,validType:[<c:if test="${not empty field.validType}">'${field.validType}',</c:if>'length[0,${field.maxLength}]']</c:if>
                                  <c:if test="${field.widget eq 'filebox'}">,buttonText:'浏览'</c:if>"
                    />
                  </c:otherwise>
                </c:choose>
              </c:otherwise>
            </c:choose>
          </div>

          <c:set var="colCount" value="${colCount + labelColSpan + field.columnWidth}" />

          <%-- 最后一个字段，输出row结尾以及panel结尾 --%>
          <c:if test="${s.last}">
            <c:out value="</div></div></div></div>" escapeXml="false" />
          </c:if>

        </c:forEach>

      </form>
    </div>
    <!-- /.container-fix-width-m -->

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