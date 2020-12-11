<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><s:message code="ui.system.title" /></title>
<style>
table thead {
	background: #7EB4E4;
	color: #FFFFFF;
	font-weight: bolder;
}

table td {
	word-break: break-all;
	font-size: 14px;
	text-align: center;
	padding: 2px;
}
</style>
</head>
<body>
  <table cellpadding="0" cellspacing="0" border="1" width="85%">
    <thead>
      <tr>
        <td width="5%">NO.</td>
        <td width="35%">Property Name</td>
        <td width="60%">Value</td>
      </tr>
    </thead>
    <c:if test="${not empty systemProperties}">
      <tbody>
        <c:forEach var="p" items="${systemProperties}" varStatus="s">
          <tr>
            <td>${s.count}</td>
            <td>${p.key}</td>
            <td>${p.value}</td>
          </tr>
        </c:forEach>
      </tbody>
    </c:if>
  </table>
  <h2>Context Path:${pageContext.request.contextPath}</h2>
</body>
</html>