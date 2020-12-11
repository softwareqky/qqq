<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<title>地图首页</title>
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<!--[if IE]><link rel="stylesheet" href="css/ie.css" media="all" /><![endif]-->
<script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/bootstrap/bootstrap-3.4.1.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/bootstrap/bootstrap-switch/bootstrap-switch.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/jscolor/jscolor.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/echarts/echarts.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/moment/moment.js"/>"></script>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=b5b48b067a3b18cdbaa54c9adc2d0a94"></script>
<script type="text/javascript" src="https://webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>
<script type="text/javascript" src="<s:url value="/js/home/laydate.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/home/mapHome.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/home/html2canvas.js"/>"></script>
<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap-switch/bootstrap-switch.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/home/mapHome.css"/>" />
<script>
var BASE_URL = '${pageContext.request.contextPath}';
</script>
</head>
<body>
  <div class="m-container">
    <div id="map" style="width: 100%; height: 100%;"></div>
    <div class="m-menu">
      <div class="m-menu-left">
        <ul>
          <li class="m-currentMenu m-menu-style1">
            <div></div>
            <p>建设图</p>
          </li>
          <li class="m-menu-style2">
            <div></div>
            <p>统计图</p>
          </li>
          <!-- <li class="m-menu-style3">
            <div></div>
            <p>自定义</p>
          </li> -->
          <!-- <li class="m-menu-style4">
            <div></div>
            <p>分类</p>
          </li> -->
          <li class="m-menu-style5">
            <div></div>
            <p>地图样式</p>
          </li>
          <!-- <li class="m-menu-style6">
            <div></div>
            <p>历史纪录</p>
          </li> -->
        </ul>
      </div>
      <div class="m-menu-right">
        <div class="m-menu-hide">
          <span></span>
        </div>
        <div class="m-menu-content">

          <div class="m-menu-box">
            <div class="m-menu-tab" id="networkTab">
              <div class='m-menu-layers'>
                <span class="menu-layer" id="plan">建设计划</span>
                <span class="menu-layer selectedLayer" id="progress">建设进展</span>
                <span class="menu-layer" id="problem">问题预警</span>
			  <!-- <div class="textLeft" id="server" style="display:none">服务提供能力<i class="glyphicon glyphicon-ok"></i></div> -->
              </div>
              
              
            </div>
            <div class="m-menu-detail" id="networkContent">
              <div class="m-menu-list">
              	<div class='m-menu-label'>
              		<div class='m-menu-title'>
              			专业组
              		</div>
              		<div class='virtual-org' id="invalidcondition">
		               <span type="1" class="m-current-tab">光传输网络</span>
		               <span type="2">可编程网络</span>
		               <span type="3">SDN网络</span>
		            </div>
              	</div>
                <div class="m-menu-label">
                	<div class='m-menu-title'>
              			初验时间
              		</div>
					<div class="searchBox">
		              <input type="text" id="startTime" class="selectTime layui-input" placeholder="开始时间" />
		              -
		              <input type="text" id="endTime" class="selectTime layui-input" placeholder="结束时间" />
		            </div>
		            <!-- <div>
		            	<div class="m-radio">
						    <input id="all" name="range" type="radio" checked>
						    <label for="all" class="m-radio-label">全部</label>
						</div>
		            	<div class="m-radio">
						    <input id="year" name="range" type="radio">
						    <label for="year" class="m-radio-label">本年</label>
						</div>
						<div class="m-radio">
						    <input id="quarter" name="range" type="radio">
						    <label for="quarter" class="m-radio-label">本季</label>
						</div>
						<div class="m-radio">
						    <input id="month" name="range" type="radio">
						    <label for="month" class="m-radio-label">本月</label>
						</div>
						<div class="m-radio">
						    <input id="week" name="range" type="radio">
						    <label for="week" class="m-radio-label">本周</label>
						</div>
		            </div> -->
	            </div>
                <div class="m-menu-label">
                	<div class='m-menu-title'>
              			区域分布
              		</div>
                  <div class="selectBox">
                    <select id="area">
                    </select>
                  </div>
                </div>
                
              </div>
            </div>
            <div class="m-menu-detail5" id="">
              <div class="m-menu-label">
              	<div class='m-menu-title'>
       			  节点类型
       		    </div>
              </div>
              <ul class="pointerUl">
                <li class="currnetPointer">
                  <span>核心节点</span>
                  <div class="s-checkbox">
					    <input id="keySite" class="parentCheckbox" name="" type="checkbox" checked>
					    <label for="keySite" class="s-checkbox-label">全部可见</label>
				  </div>
                  <dl>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-20.png" /> -->计划中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="keySite1" name="" type="checkbox" checked>
						    <label for="keySite1" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-13.png" /> -->进行中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="keySite2" name="" type="checkbox" checked>
						    <label for="keySite2" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-14.png" /> -->已具备</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="keySite3" name="" type="checkbox" checked>
						    <label for="keySite3" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                  </dl>
                </li>
                <li class="baseSite">
                  <span>传输骨干节点</span>
                  <div class="s-checkbox">
					    <input id="highspeedSite" class="parentCheckbox" name="" type="checkbox" checked>
					    <label for="highspeedSite" class="s-checkbox-label">全部可见</label>
				  </div>
                  <dl>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-20.png" /> -->计划中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="highspeedSite1" name="" type="checkbox" checked>
						    <label for="highspeedSite1" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-13.png" /> -->进行中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="highspeedSite2" name="" type="checkbox" checked>
						    <label for="highspeedSite2" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-14.png" /> -->已具备</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="highspeedSite3" name="" type="checkbox" checked>
						    <label for="highspeedSite3" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                  </dl>
                </li>
                <li class="baseSite">
                  <span>传输中继节点</span>
                  <div class="s-checkbox">
					    <input id="repeatSite" class="parentCheckbox" name="" type="checkbox" checked>
					    <label for="repeatSite" class="s-checkbox-label">全部可见</label>
				  </div>
                  <dl>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-20.png" /> -->计划中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="repeatSite1" name="" type="checkbox" checked>
						    <label for="repeatSite1" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-13.png" /> -->进行中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="repeatSite2" name="" type="checkbox" checked>
						    <label for="repeatSite2" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-14.png" /> -->已具备</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="repeatSite3" name="" type="checkbox" checked>
						    <label for="repeatSite3" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                  </dl>
                </li>
                <li class="noBaseSite" style="display:none;">
                  <span>边缘节点</span>
                  <div class="s-checkbox">
					    <input id="edgeSite" class="parentCheckbox" name="" type="checkbox" checked>
					    <label for="edgeSite" class="s-checkbox-label">全部可见</label>
				  </div>
                  <dl>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-20.png" /> -->计划中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="edgeSite1" name="" type="checkbox" checked>
						    <label for="edgeSite1" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-13.png" /> -->进行中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="edgeSite2" name="" type="checkbox" checked>
						    <label for="edgeSite2" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <img src="img/home/markers/marker1-14.png" /> -->已具备</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="edgeSite3" name="" type="checkbox" checked>
						    <label for="edgeSite3" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                  </dl>
                </li>
                <li class="baseSite">
                  <span>城域光缆</span>
                  <div class="s-checkbox">
					    <input id="citySegment" class="parentCheckbox" name="" type="checkbox" checked>
					    <label for="citySegment" class="s-checkbox-label">全部可见</label>
				  </div>
                  <dl>
                    <dt>
                      <span><!-- <i style="border-bottom: 3px solid red; width: 25px; margin-right: 10px;"></i> -->计划中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="citySegment1" name="" type="checkbox" checked>
						    <label for="citySegment1" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <i style="border-bottom: 3px solid yellow; width: 25px; margin-right: 10px;"></i> -->进行中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="citySegment2" name="" type="checkbox" checked>
						    <label for="citySegment2" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <i style="border-bottom: 3px solid green; width: 25px; margin-right: 10px;"></i> -->已具备</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="citySegment3" name="" type="checkbox" checked>
						    <label for="citySegment3" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                  </dl>
                </li>
                <li class="baseSite">
                  <span>骨干光缆</span>
                  <div class="s-checkbox">
					    <input id="highspeedSegment" class="parentCheckbox" name="" type="checkbox" checked>
					    <label for="highspeedSegment" class="s-checkbox-label">全部可见</label>
				  </div>
                  <dl>
                    <dt>
                      <span><!-- <i style="border-bottom: 3px solid red; width: 25px; margin-right: 10px;"></i> -->计划中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="highspeedSegment1" name="" type="checkbox" checked>
						    <label for="highspeedSegment1" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <i style="border-bottom: 3px solid yellow; width: 25px; margin-right: 10px;"></i> -->进行中</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="highspeedSegment2" name="" type="checkbox" checked>
						    <label for="highspeedSegment2" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <i style="border-bottom: 3px solid green; width: 25px; margin-right: 10px;"></i> -->已具备</span>
                      <div class="s-checkbox">
						    <input class="childSelect" id="highspeedSegment3" name="" type="checkbox" checked>
						    <label for="highspeedSegment3" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                  </dl>
                </li>
                <li class="noBaseSite" style="display:none;">
                  <span>链路</span>
                  <div class="s-checkbox">
					    <input id="link" class="parentCheckbox" name="" type="checkbox" checked>
					    <label for="link" class="s-checkbox-label">全部可见</label>
				  </div>
                  <dl>
                    <dt>
                      <span><!-- <i style="border-bottom: 3px solid red; width: 25px; margin-right: 10px;"></i> -->未具备</span>
                      <div class="s-checkbox">
						    <input class="linkChildSelect" id="link1" name="" type="checkbox" checked>
						    <label for="link1" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                    <dt>
                      <span><!-- <i style="border-bottom: 3px solid green; width: 25px; margin-right: 10px;"></i> -->已具备</span>
                      <div class="s-checkbox">
						    <input class="linkChildSelect" id="link2" name="" type="checkbox" checked>
						    <label for="link2" class="s-checkbox-label">可见</label>
						</div>
                    </dt>
                  </dl>
                </li>
              </ul>
            </div>
          </div>


          <div class="m-menu-box" style="display: none;">
          	<div class="m-menu-tab" id="">
              <ul id="classify">
                <li class="m-current-tab" href="baseNetwork">光传输网络</li>
                <li href="plcNetwork">可编程网络</li>
                <li href="sdnNetwork">SDN网络</li>
              </ul>
            </div>
            <div class="m-menu-detail" style="padding-top: 0;position:relative;">
           		<div id="baseNetwork" class="chartDiv">
           			<div class="m-chart" id="chart1"></div>
	            	<div class="m-chart" id="chart2"></div>
	            	<div class="m-chart" id="chart3"></div>
           		</div>
           		<div id="plcNetwork" class="chartDiv" style="display:none;">
           			<div class="m-chart" id="chart4"></div>
	            	<div class="m-chart" id="chart5"></div>
           		</div>
           		<div id="sdnNetwork" class="chartDiv" style="display:none;">
           			<div class="m-chart" id="chart6"></div>
	            	<div class="m-chart" id="chart7"></div>
           		</div>
           		
           		<div class="c_loading">
				  	<img src="img/loading.gif" /><br>加载中...
				  </div>
            </div>
          </div>


          <!-- <div class="m-menu-box" style="display: none;">
            
          </div> -->

          <div class="m-menu-box" style="display: none;">
            <div class="m-menu-detail1" id="setMap">
              <div class="m-menu-list">
                <div class="m-menu-label4 mapstyle1">
                  <button type="button" styleFlag="normal">使用</button>
                </div>
                <div class="m-menu-label4 mapstyle2">
                  <button type="button" styleFlag="graffiti">使用</button>
                </div>
                <div class="m-menu-label4 mapstyle3">
                  <button type="button" styleFlag="light">使用</button>
                </div>
                <div class="m-menu-label4 mapstyle4">
                  <button type="button" styleFlag="darkblue">使用</button>
                </div>
                <div class="m-menu-label4 mapstyle5">
                  <button type="button" styleFlag="fresh">使用</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="m_tool">
      <div class="showname" id="showname" onclick="showname();">
        <span class="showflag showflagtrue"></span>
        显示名称
      </div>
      <div id="showlable" onclick="showCityName();">
        <span class="showflag1 showflagtrue"></span>城市名
      </div>
      <div id="showlable" onclick="showStationName()">
        <span class="showflag2"></span>站点名
      </div>
      <div class="screenshot">出图</div>
      <div onclick="initMapData(true);"><i class="glyphicon glyphicon-refresh" style="color:#6378c2;margin-right:5px;"></i>刷新</div>
    </div>

    <div class="m_legend">
      <div class="legend_box">
        <p class="problemLayer progressLayer serverLayer"><img src="img/home/markers/marker1-20.png" id="coreNode3"><span>核心节点计划中</span></p>
        <p class="progressLayer"><img src="img/home/markers/marker1-13.png" id="coreNode2"><span>核心节点进行中</span></p>
        <p class="planLayer progressLayer serverLayer"><img src="img/home/markers/marker1-14.png" id="coreNode1"><span>核心节点已具备</span></p>
        
        <p class="problemLayer progressLayer serverLayer baseLayer"><img src="img/home/markers/marker1-20.png" id="highSpeedCoreNode3"><span>传输骨干节点计划中</span></p>
        <p class="progressLayer baseLayer"><img src="img/home/markers/marker1-13.png" id="highSpeedCoreNode2"><span>传输骨干节点进行中</span></p>
        <p class="planLayer progressLayer serverLayer baseLayer"><img src="img/home/markers/marker1-14.png" id="highSpeedCoreNode1"><span>传输骨干节点已具备</span></p>
        
        <p class="problemLayer progressLayer serverLayer baseLayer"><img src="img/home/markers/marker1-20.png" id="highSpeedRelayNode3"><span>传输中继节点计划中</span></p>
        <p class="progressLayer baseLayer"><img src="img/home/markers/marker1-13.png" id="highSpeedRelayNode2"><span>传输中继节点进行中</span></p>
        <p class="planLayer progressLayer serverLayer baseLayer"><img src="img/home/markers/marker1-14.png" id="highSpeedRelayNode1"><span>传输中继节点已具备</span></p>
        
        <p class="problemLayer progressLayer serverLayer noBaseLayer"><img src="img/home/markers/marker1-20.png" id="edgeNode3"><span>边缘节点计划中</span></p>
        <p class="progressLayer noBaseLayer"><img src="img/home/markers/marker1-13.png" id="edgeNode2"><span>边缘节点进行中</span></p>
        <p class="planLayer progressLayer serverLayer noBaseLayer"><img src="img/home/markers/marker1-14.png" id="edgeNode1"><span>边缘节点已具备</span></p>
        
        <p class="problemLayer progressLayer serverLayer baseLayer"><b style="border-bottom:1px solid red;" id="highSpeedCable3"></b><span>骨干光缆计划中</span></p>
        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid yellow;" id="highSpeedCable2"></b><span>骨干光缆进行中</span></p>
        <p class="planLayer progressLayer serverLayer baseLayer"><b style="border-bottom:1px solid green;" id="highSpeedCable1"></b><span>骨干光缆已具备</span></p>
        
        <p class="problemLayer progressLayer serverLayer baseLayer"><b style="border-bottom:1px solid red;" id="metroCable3"></b><span>城域光缆计划中</span></p>
        <p class="progressLayer baseLayer"><b style="border-bottom:1px solid yellow;" id="metroCable2"></b><span>城域光缆进行中</span></p>
        <p class="planLayer progressLayer serverLayer baseLayer"><b style="border-bottom:1px solid green;" id="metroCable1"></b><span>城域光缆已具备</span></p>
        
        <p class="problemLayer progressLayer serverLayer noBaseLayer"><b style="border-bottom:1px solid red;" id="coreLink2"></b><span>链路计划中</span></p>
        <p class="planLayer progressLayer serverLayer noBaseLayer"><b style="border-bottom:1px solid green;" id="coreLink1"></b><span>链路已具备</span></p>
      </div>
    </div>
  </div>
  <div class="m_loading">
  	<img src="img/loading.gif" /><br>加载中...
  </div>
</body>
</html>
