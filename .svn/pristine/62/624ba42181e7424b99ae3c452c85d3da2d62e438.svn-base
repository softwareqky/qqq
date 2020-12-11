<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 下载文件时，在隐藏的iframe内动态加入<from />然后提交该form，防止Firefox提交时，Websocket断开 --%>
<%-- 配合MainUtils.downloadFile一起使用 --%>
<div class="hidden">
  <iframe id="DownloadTempFrame" src="<s:url value="/in-progress.json"/>"></iframe>
</div>