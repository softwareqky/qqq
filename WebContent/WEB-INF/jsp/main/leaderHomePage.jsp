<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>首页</title>
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<!--[if IE]><link rel="stylesheet" href="css/ie.css" media="all" /><![endif]-->
<jsp:include page="/WEB-INF/jsp/common/include/commonJsVar.jsp" flush="true" />
<script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/easyui/jquery.easyui-1.9.2.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/echarts/echarts.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/echarts/china.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/main.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/home/leaderHome.js"/>"></script>
<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/home/mapHome.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/home/leaderHome.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/material/easyui.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/icon.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/color.css"/>" />

<script>
var BASE_URL = '${pageContext.request.contextPath}';
</script>
</head>
<body>
  <div class="l-container">
    <div class="l-title">
      <h1><img src="img/home/dashboardlogo.png" style="height:34px;" /></h1>
      <p id="l-time"></p>
    </div>
    <div class="row" style="margin: 0; position: relative;height:calc(100% - 68px)">
      <div class="pageleft">
        <div class="l-box" id="systemBox">
          <div class="header">
            <span style="flex: 1">筛选</span>
            <span style="cursor: pointer" onclick="handleEditArea()">编辑</span>
          </div>
          <div class="l-box-content">
            <div class="m-menu-box">
            	<div class="row area" id="area">
            	</div>
            </div>
          </div>
          <div class="l-box-border b1"></div>
          <div class="l-box-border b2"></div>
          <div class="l-box-border b3"></div>
          <div class="l-box-border b4"></div>
        </div>
        <div id="areaEditModal" title="编辑筛选区域" class="easyui-dialog" style="width: 600px; height: 600px"
        	data-options="modal: true, closed: true, collapsible: false, minimizable: false, maximizable: false, resizable: false, closable: false,
        		buttons: [{
        			text: '保存',
        			handler: confirmSubmitArea
        		}, {
        			text: '取消',
        			handler: cancelSubmitArea
        		}]">
        	<div id='areaEditList' style="padding: 16px;"></div>
        </div>
      </div>
      <div class="col-xs-12" style="position: relative;height:100%">
      	<div class="opbox">
      		<div class="opbutton layerbutton" layer="0">
	      		<img src="img/home/opbutton.png">
	      		<span layer="0">设施分布</span>
      			<ul>
      				<li layer="0">设施分布</li>
      				<li layer="1">设施进展</li>
      			</ul>
      		</div>
      		<div class="opbutton siteTypebutton" siteType="1">
	      		<img src="img/home/opbutton.png">
	      		<span siteType="1">光传输网络</span>
      			<ul>
      				<li siteType="1">光传输网络</li>
      				<li siteType="2">可编程网络</li>
      				<li siteType="3">SDN网络</li>
      			</ul>
      		</div>
      		<div class="refreshBtn" onclick="drawMap();"><span class="glyphicon glyphicon-refresh"></span>刷新</div>
      	</div>
        <div id="chart5" style="width: 100%;height:100%;"></div>
        <div class="l-box l-legend">
        	<div class="legend_box">
        		<p class="progressLayer"><img src="img/home/markers/mainpoint_grey_legend.png" id="mapMainCity3"><span>运行管控中心计划中</span></p>
		        <p class="progressLayer"><img src="img/home/markers/mainpoint_yellow_legend.png" id="mapMainCity2"><span>运行管控中心进行中</span></p>
		        <p class="progressLayer"><img src="img/home/markers/mainpoint_legend.png" id="mapMainCity1"><span>运行管控中心已具备</span></p>
		        <p class="planLayer"><img src="img/home/markers/mainpoint_legend.png" id="mapMainCity1"><span>运行管控中心</span></p>
		        
		        <p class="progressLayer"><img src="img/home/markers/dashboard_grey.png" id="mapCoreNode3"><span>核心节点计划中</span></p>
		        <p class="progressLayer"><img src="img/home/markers/dashboard_yellow.png" id="mapCoreNode2"><span>核心节点进行中</span></p>
		        <p class="progressLayer"><img src="img/home/markers/dashboard_green.png" id="mapCoreNode1"><span>核心节点已具备</span></p>
		        <p class="planLayer"><img src="img/home/markers/dashboard_green.png" id="mapCoreNode1"><span>核心节点</span></p>
		        
		        <p class="progressLayer noBaseLayer"><img src="img/home/markers/dashboard_grey.png" id="mapEdgeNode3"><span>边缘节点计划中</span></p>
		        <p class="progressLayer noBaseLayer"><img src="img/home/markers/dashboard_yellow.png" id="mapEdgeNode2"><span>边缘节点进行中</span></p>
		        <p class="progressLayer noBaseLayer"><img src="img/home/markers/dashboard_green.png" id="mapEdgeNode1"><span>边缘节点已具备</span></p>
		        <p class="planLayer noBaseLayer"><img src="img/home/markers/dashboard_green.png" id="mapEdgeNode1"><span>边缘节点</span></p>
		        
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid #8a8a8a;" id="mapHighSpeedCable3"></b><span>骨干光缆计划中</span></p>
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid yellow;" id="mapHighSpeedCable2"></b><span>骨干光缆进行中</span></p>
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid #fff;" id="mapHighSpeedCable1"></b><span>骨干光缆已具备</span></p>
		        <p class="planLayer baseLayer"><b style="border-bottom:1px solid #fff;" id="mapHighSpeedCable1"></b><span>骨干光缆</span></p>
		        
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid #8a8a8a;" id="mapMetroCable3"></b><span>城域光缆计划中</span></p>
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid yellow;" id="mapMetroCable2"></b><span>城域光缆进行中</span></p>
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid #fff;" id="mapMetroCable1"></b><span>城域光缆已具备</span></p>
		        <p class="planLayer baseLayer"><b style="border-bottom:1px solid #fff;" id="mapMetroCable1"></b><span>城域光缆</span></p>
		        
		        <p class="progressLayer noBaseLayer"><b style="border-bottom:1px solid #8a8a8a;" id="mapCoreLink2"></b><span>链路计划中</span></p>
		        <p class="progressLayer noBaseLayer"><b style="border-bottom:1px solid #fff;" id="mapCoreLink1"></b><span>链路已具备</span></p>
		        <p class="planLayer noBaseLayer"><b style="border-bottom:1px solid #fff;" id="mapCoreLink1"></b><span>链路</span></p>
		        
		        <%-- <p class="progressLayer"><img src="img/home/markers/mainpoint_grey_legend.png" id="mapMainCity3"><span>运行管控中心计划中</span></p>
		        <p class="progressLayer"><img src="img/home/markers/mainpoint_yellow_legend.png" id="mapMainCity2"><span>运行管控中心进行中</span></p>
		        <p class="planLayer progressLayer"><img src="img/home/markers/mainpoint_legend.png" id="mapMainCity1"><span>运行管控中心已具备</span></p>
		        
		        <p class="progressLayer"><img src="img/home/markers/dashboard_grey.png" id="mapCoreNode3"><span>核心节点计划中</span></p>
		        <p class="progressLayer"><img src="img/home/markers/dashboard_yellow.png" id="mapCoreNode2"><span>核心节点进行中</span></p>
		        <p class="planLayer progressLayer"><img src="img/home/markers/dashboard_green.png" id="mapCoreNode1"><span>核心节点已具备</span></p>
		        
		        <p class="progressLayer noBaseLayer"><img src="img/home/markers/dashboard_grey.png" id="mapEdgeNode3"><span>边缘节点计划中</span></p>
		        <p class="progressLayer noBaseLayer"><img src="img/home/markers/dashboard_yellow.png" id="mapEdgeNode2"><span>边缘节点进行中</span></p>
		        <p class="planLayer progressLayer noBaseLayer"><img src="img/home/markers/dashboard_green.png" id="mapEdgeNode1"><span>边缘节点已具备</span></p>
		        
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid #8a8a8a;" id="mapHighSpeedCable3"></b><span>骨干光缆计划中</span></p>
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid yellow;" id="mapHighSpeedCable2"></b><span>骨干光缆进行中</span></p>
		        <p class="planLayer progressLayer baseLayer"><b style="border-bottom:1px solid #fff;" id="mapHighSpeedCable1"></b><span>骨干光缆已具备</span></p>
		        
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid #8a8a8a;" id="mapMetroCable3"></b><span>城域光缆计划中</span></p>
		        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid yellow;" id="mapMetroCable2"></b><span>城域光缆进行中</span></p>
		        <p class="planLayer progressLayer baseLayer"><b style="border-bottom:1px solid #fff;" id="mapMetroCable1"></b><span>城域光缆已具备</span></p>
		        
		        <p class="progressLayer noBaseLayer"><b style="border-bottom:1px solid #8a8a8a;" id="mapCoreLink2"></b><span>链路计划中</span></p>
		        <p class="planLayer progressLayer noBaseLayer"><b style="border-bottom:1px solid #fff;" id="mapCoreLink1"></b><span>链路已具备</span></p> --%>
		        
		        <p class="sysLayer"><b style="border-bottom:1px solid yellow;" id="mapSystemSeg"></b><span>筛选中继段</span></p>
		      </div>
          <div class="l-box-border b1"></div>
          <div class="l-box-border b2"></div>
          <div class="l-box-border b3"></div>
          <div class="l-box-border b4"></div>
        </div>
      </div>
      <div class="pageright">
        <div class="l-box pointbox">
          <h1 id="pointType" style="background-position: 100px 15px; color: #EEE">节点类型</h1>
          <div class="l-box-content">
            <div class="l-box-text pointdata">
              <p>节点名称</p>
              <h1>机房位置：</h1>
              <h1>初设编号：</h1>
              <h1 id="constructionProgress">建设进展：</h1>
            </div>
          </div>
          <div class="l-box-border b1"></div>
          <div class="l-box-border b2"></div>
          <div class="l-box-border b3"></div>
          <div class="l-box-border b4"></div>
        </div>
        <div class="l-box" id="progressBox" style="display:none;">
          <h1 style="color: #EEE">进度统计</h1>
          <div class="l-box-content" style="margin:0 -10px;">
            <div class="s-modal">
              <div class="s-content">
              	<div class="" id="chart1" style="width:100%;"></div>
              </div>
            </div>
          </div>
          <div class="l-box-border b1"></div>
          <div class="l-box-border b2"></div>
          <div class="l-box-border b3"></div>
          <div class="l-box-border b4"></div>
        </div>
      </div>
    </div>
  </div>
   <div class="m_loading">
  	<img src="img/loading.gif" /><br>加载中...
  </div>
</body>
</html>
