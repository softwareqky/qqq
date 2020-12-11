<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 共通的<head/>内容 --%>

<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />
<title><s:message code="ui.system.title" /></title>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<link rel="Shortcut Icon" href="${baseUrl}<s:theme code="shortcut.ico"/>" />

<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/font-awesome/css/font-awesome.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/Ionicons/css/ionicons.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/adminlte/AdminLTE.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/adminlte/skins/skin-blue.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/material/easyui.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/icon.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/color.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="custom.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="home.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/flowable/flowable.node.css"/>" />


<%-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 --%>
<!--[if lt IE 9]>
      <script src="<s:url value="/js/util/bootstrap/html5shiv-3.7.3.min.js"/>"></script>
      <script src="<s:url value="/js/util/bootstrap/respond-1.4.2.min.js"/>"></script>
    <![endif]-->
