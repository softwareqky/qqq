<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/include/head.jsp" flush="true" />

<style type="text/css">
</style>
</head>
<body>
  <%-- body内的结构说明同Main._extractContentTabHtmlBody --%>
  <%-- 在body内分为同级并列的4个区域，从上到下依次是： --%>
  <%-- 1. 可见的布局，通常使用bootstrap的container/container-fluid或者EasyUI的layout来构建UI --%>
  <%-- 2. 隐藏的div#EasyuiHiddenWrapper.hidden，用来包含弹出框，每个弹出框都包含在独立的div.hidden内 --%>
  <%-- 3. 隐藏的div#ContentScriptWrapper.hidden，用来包含画面所需的JavaScript脚本引用，供独立调试画面时使用。这些脚本在main.jsp已经引入，所以在tab加载画面body时将会被直接抛弃 --%>
  <%-- 4. 使用script标签引入的画面专有的JavaScript逻辑，由includeJspJsPath变量指定 --%>

  <div class="s-container">
    <div class="row drop-able">
	    <div class="s-moda drag-able" id="drag0">
	      <div class="s-head s-purple">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
	      	<span class="drag-able-title">待办事宜</span></div>
	      <div class="s-content">
	      	<div id="btn-left1" class="dasabled"></div>
	      		<div class="slider">
	      			<div class="sliderUl sliderU1" id="sliderU1">
			         </div>
				</div>
	        <div id="btn-right1" class=""></div>
	      </div>
	    </div>
	</div>
    <%-- <div class="row drop-able">
	    <div class="s-moda drag-able" id="drag01">
	      <div class="s-head s-purple">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
	      	<span class="drag-able-title">已办事宜</span></div>
	      <div class="s-content">
	      	<div id="btn-left2" class="dasabled"></div>
	      		<div class="slider">
	      			<div class="sliderUl sliderU2" id="sliderU2">
			         </div>
				</div>
	        <div id="btn-right2" class=""></div>
	      </div>
	    </div>
	</div>
    <div class="row drop-able">
	    <div class="s-moda drag-able" id="drag02">
	      <div class="s-head s-purple">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
	      	<span class="drag-able-title">我的流程</span></div>
	      <div class="s-content">
	      	<div id="btn-left3" class="dasabled"></div>
	      		<div class="slider">
	      			<div class="sliderUl sliderU3" id="sliderU3">
			         </div>
				</div>
	        <div id="btn-right3" class=""></div>
	      </div>
	    </div>
	</div> --%>
    <div class="row" style="margin-top: 20px;">
    	<div class="col-xs-8 drop-able">
        <div class="s-modal drag-able" id="drag1">
          <div class="s-head s-wine">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
          	<span class="drag-able-title">通知公告</span><a onclick="Main.openTab('通知公告', '/project-edge/notice/announcement/main.htm')" href="#">更多&nbsp;》</a></div>
          <div class="s-content">
            <div class="s-table">
              <div class="s-table-head">
                <ul>
                  <li class="s-width-40">标题</li>
                  <li class="s-width-30">专业组</li>
                  <li class="s-width-30">发布时间 </li>
                </ul>
              </div>
              <div class="s-table-body">
                <ul id="notice">
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xs-4 drop-able">
        <div class="s-modal drag-able" id="drag4">
          <div class="s-head s-orange">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
          	<span tar="taskList" class="tasktab curtab">我的任务</span>
          	<span tar="myTaskList" class="tasktab">我的关注</span>
            <a onclick="Main.openTab('计划管理', '/project-edge/schedule/plan/main.htm')" href="#">更多&nbsp;》</a>
          </div>
          <div class="s-content">
            <div class="s-table taskList" id="taskList">
              <div class="s-table-head">
                <ul>
                  <li class="taskwidth1 s-width-35">计划名</li>
                  <li class="taskwidth2 s-width-20">任务名</li>
                  <li class="taskwidth3 s-width-20">计划进度</li>
                  <li class="taskwidth4 s-width-20">任务状态</li>
                </ul>
              </div>
              <div class="s-table-body">
                <ul id="progressList" style="height:190px;overflow-y:scroll">
                </ul>
              </div>
            </div>
            <div class="s-table taskList" style="display: none;" id="myTaskList">
              <div class="s-table-head">
                <ul>
                  <li class="taskwidth1 s-width-35">计划名</li>
                  <li class="taskwidth2 s-width-20">任务名</li>
                  <li class="taskwidth3 s-width-20">计划进度</li>
                  <li class="taskwidth4 s-width-20">任务状态</li>
                </ul>
              </div>
              <div class="s-table-body">
                <ul id="progressListMy" style="height:190px;overflow-y:scroll">
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row" style="margin-top: 20px;">
     <div class="col-xs-8 drop-able">
        <div class="s-modal drag-able" id="drag3">
          <div class="s-head s-wine">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
          	<span tar="proList" class="hometab curtab">项目列表</span>
          	<span tar="myList" class="hometab">我的关注</span>
          <a onclick="Main.openTab('项目列表', '/project-edge/project/project-init/main.htm')" href="#">更多&nbsp;》</a></div>
          <div class="s-content">
            <div class="s-table procontentlist" id="proList">
              <div class="s-table-head">
                <ul>
                  <li class="s-width-5" style="white-space: nowrap;">序号</li>
                  <li class="s-width-50">项目名称</li>
                  <li class="s-width-15">时间</li>
                  <li class="s-width-15">负责人</li>
                  <li class="s-width-15">状态</li>
                </ul>
              </div>
              <div class="s-table-body">
                <ul id="projectList" style="overflow-y: scroll;height: 190px;">
                </ul>
              </div>
            </div>
            <div class="s-table procontentlist" id="myList" style="display:none;">
              <div class="s-table-head">
                <ul>
                  <li class="s-width-5">序号</li>
                  <li class="s-width-50">项目名称</li>
                  <li class="s-width-15">时间</li>
                  <li class="s-width-15">负责人</li>
                  <li class="s-width-15">状态</li>
                </ul>
              </div>
              <div class="s-table-body">
                <ul id="favoriteProject" style="overflow-y: scroll;height: 190px;">
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
   		<div class="col-xs-4 drop-able">
      	<div class="s-modal drag-able" id="drag18">
          <div class="s-head s-grey">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
            <span class="drag-able-title">项目统计</span>
            <a onclick="Main.openTab('项目立项', '/project-edge/project/project-init/main.htm')" href="#">更多&nbsp;》</a>
          </div>
          <div class="s-content">
            <div class="s-chart" id="projectCountChart"></div>
            <div style="clear: both;"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="row" style="margin-top: 20px;">
     <div class="col-xs-4 drop-able">
        <div class="s-modal drag-able" id="drag5">
          <div class="s-head s-blue">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
           	<span class="drag-able-title">资金</span> <select id="feeselect" style="height:24px;line-height:24px;margin-left:20%;" class="yearSelector">
         </select>
            <a onclick="Main.openTab('资金', '/project-edge/budget/budgetEstimate/main.htm')" href="#">更多&nbsp;》</a>
          </div>
          <div class="s-content">
            <div class="s-chart" id="chart1"></div>
            <div style="clear: both;"></div>
          </div>
        </div>
      </div>
      <div class="col-xs-4 drop-able">
        <div class="s-modal drag-able" id="drag6">
          <div class="s-head s-blue">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
            <span class="drag-able-title">站点完成数</span> <select id="vrotaulOrgSelect" style="height:24px;line-height:24px;margin-left:2%;"></select>
            <select id="vrotaulOrgYearSelect" style="height:24px;line-height:24px;" class="yearSelector1"></select>
            <a onclick="Main.openTab('站点完成数', '/project-edge/schedule/plan/main.htm')" href="#">更多&nbsp;》</a>
          </div>
          <!-- <div id="totalText" style="position:absolute;left:0;top:75px;width:100%;height:22px;line-height:22px;padding-left:30px;z-index:99;"></div> -->
          <div class="s-content">
            <div class="s-chart" id="finishChart"></div>
            <div style="clear: both;"></div>
          </div>
        </div>
      </div>
      <div class="col-xs-4 drop-able">
      	<div class="s-modal drag-able" id="drag2">
          <div class="s-head s-blue">
          	<span class="drag-able-title">&nbsp;&nbsp;</span>
            <span class="drag-able-title">人数统计</span><select id="peopleselect" style="height:24px;line-height:24px;margin-left:20%;" class="yearSelector">
         </select>
            <a onclick="Main.openTab('人数统计', '/project-edge/project/project-person/main.htm')" href="#">更多&nbsp;》</a>
          </div>
          <div class="s-content">
            <div class="s-chart" id="people"></div>
            <div style="clear: both;"></div>
          </div>
        </div>
      </div>
      <%-- <div class="col-xs-4 drop-able">
        <div class="s-modal drag-able" id="drag7">
          <div class="s-head s-blue">
           <span class="drag-able-title">问题</span><select id="issueselect" style="height:24px;line-height:24px;margin-left:20%;" class="yearSelector">
         </select>
            <a onclick="Main.openTab('问题', '/project-edge/quality/issue/main.htm')" href="#">更多&nbsp;》</a>
          </div>
          <div class="s-content">
            <div class="s-chart" id="issueChart"></div>
            <div style="clear: both;"></div>
          </div>
        </div>
      </div> --%>
    </div>
  
  	<%-- <div class="row" style="margin-top: 20px;">
  		<div class="col-xs-12 drop-able">
	        <div class="s-modal drag-able" style="height:auto;" id="drag8">
	          <div class="s-head s-grey">
          		<span class="drag-able-title">&nbsp;&nbsp;</span>
	          	<span class="drag-able-title">项目阶段统计</span> 
			            <a onclick="Main.openTab('项目立项', '/project-edge/project/project-init/main.htm')" href="#">更多&nbsp;》</a>
			          </div>
			          <div class="s-content">
			            <div class="s-chart" id="projectGCCountChart" style="height:360px;float:left;width:45%"></div>
			            <div class="s-chart" id="projectKYCountChart" style="height:360px;float:left;width:45%"></div>
			            <div style="clear: both;"></div>
			          </div>
	        </div>
    	</div>
    </div> --%>
  </div>
    
    <!-- 项目详情弹出框 -->
     <div class="easyui-dialog" data-options="closed:true,modal:true,shadow:false,resizable:false,onMove:EasyDialogUtils.onDialogMove" id="projectDialog" title="项目详情查看" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="width:938px;height:500px;">
       <div class="container-fluid min-width-880">
         <div class="container-fix-width-m" style="padding-top: 20px;">
         </div>
       </div>
       <div class="panel panel-default panel-default-origin" title="基本内容">
          <div class="panel-heading">
            <strong>项目信息</strong>
          </div>
          <div class="panel-body">
            <div class="container-fluid">
		      <div class="row">
		        <!-- <div class="col-xs-2 col-gapped-s col-cell-height-m">内部编号</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectNum" data-field-widget="textbox"></div>
		        </div> -->
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">项目名称</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectName" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		      <div class="col-xs-2 col-gapped-s col-cell-height-m">项目状态</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectStatusText" data-field-widget="textbox"></div>
		        </div>
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">归属项目集</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectSetText" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">项目类别</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectKindText" data-field-widget="textbox"></div>
		        </div>
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">项目来源</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectSource" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">专项类别</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="specialCategory" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">备注</div>
		        <div class="col-xs-10 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="graphicProgress" data-field-widget="textbox"></div>
		        </div>
		      </div>
		    </div>
          </div>
		</div>
		<div class="panel panel-default panel-default-origin" title="项目信息">
		  <div class="panel-heading">
            <strong></strong>
          </div>
          <div class="panel-body">
            <div class="container-fluid">
		      <div class="row">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">负责人名称</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="mainLeaderText" data-field-widget="textbox"></div>
		        </div>
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">联系方式</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="mainLeaderMobile" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">开始日期</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectStartDate" data-field-widget="textbox"></div>
		        </div>
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">结束日期</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectEndDate" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		      	<div class="col-xs-2 col-gapped-s col-cell-height-m">立项编号</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="governmentProjectNum" data-field-widget="textbox"></div>
		        </div>
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">工程工期</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectDuration" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">项目造价(万元)</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectCost" data-field-widget="textbox"></div>
		        </div>
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">项目地点</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectAddress" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">主管部门</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="competentDepartment" data-field-widget="textbox"></div>
		        </div>
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">承担性质</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="undertakePropertyText" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">承担单位</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="undertakeUnit" data-field-widget="textbox"></div>
		        </div>
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">参与单位</div>
		        <div class="col-xs-4 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="participateUnit" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">项目背景</div>
		        <div class="col-xs-10 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="remark" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">项目目标</div>
		        <div class="col-xs-10 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="otherReq" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">项目内容</div>
		        <div class="col-xs-10 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="projectDesc" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">考核指标</div>
		        <div class="col-xs-10 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="pjmReq" data-field-widget="textbox"></div>
		        </div>
		      </div>
		      <div class="row row-gapped-top-m">
		        <div class="col-xs-2 col-gapped-s col-cell-height-m">附件</div>
		        <div class="col-xs-10 col-gapped-s col-cell-height-m">
		          <div class="view-value-box" data-view-field="archiveText" data-field-widget="filebox"></div>
		        </div>
		      </div>
	        </div>
          </div>
     </div>
     
  </div>

    <!-- 通知公告弹出框 -->
    <div class="easyui-dialog" data-options="closed:true,modal:true,shadow:false,resizable:false,onMove:EasyDialogUtils.onDialogMove" id="noticeDialog" title="通知公告详情查看" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="width:938px;height:500px;">
      <div class="container-fluid min-width-880">
        <div class="container-fix-width-m" style="padding-top: 20px;">
          <div class="panel panel-default panel-default-origin">
            <div class="panel-heading">
              <strong>通知信息（编号：<span data-view-field="noticeNo" data-field-widget="textbox"></span>）</strong>
            </div>
            <div class="panel-body">
              <div class="container-fluid">
                <div class="row">
                  <div class="col-xs-2 col-gapped-s col-cell-height-m">接收对象</div>
                  <div class="col-xs-4 col-gapped-s col-cell-height-m">
                    <div class="view-value-box" data-view-field="reciversText" data-field-widget="textbox"></div>
                  </div>
                  <div class="col-xs-2 col-gapped-s col-cell-height-m">发布时间</div>
                  <div class="col-xs-4 col-gapped-s col-cell-height-m">
                    <div class="view-value-box" data-view-field="cDatetime" data-field-widget="textbox"></div>
                  </div>
                </div>
                <div class="row row-gapped-top-m">
                  <div class="col-xs-2 col-gapped-s col-cell-height-m">标题</div>
                  <div class="col-xs-10 col-gapped-s col-cell-height-m">
                    <div class="view-value-box" data-view-field="tittle" data-field-widget="textbox"></div>
                  </div>
                </div>
                <div class="row row-gapped-top-m">
                  <div class="col-xs-2 col-gapped-s col-cell-height-m">内容</div>
                  <div class="col-xs-10 col-gapped-s col-cell-height-m">
                    <div class="view-value-box" data-view-field="content" data-field-widget="textbox"></div>
                  </div>
                </div>
                <div class="row row-gapped-top-m">
                  <div class="col-xs-2 col-gapped-s col-cell-height-m">附件</div>
                  <div class="col-xs-10 col-gapped-s col-cell-height-m">
                    <div class="view-value-box" data-view-field="archivesText" data-field-widget="filebox"></div>
                  </div>
                </div>
                <div class="row row-gapped-top-m">
                  <div class="col-xs-2 col-gapped-s col-cell-height-m">编制人</div>
                  <div class="col-xs-4 col-gapped-s col-cell-height-m">
                    <div class="view-value-box" data-view-field="originatorText" data-field-widget="textbox"></div>
                  </div>
                  <div class="col-xs-2 col-gapped-s col-cell-height-m">最后修改时间</div>
                  <div class="col-xs-4 col-gapped-s col-cell-height-m">
                    <div class="view-value-box" data-view-field="mDatetime" data-field-widget="textbox"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  <div class="hidden" id="EasyuiHiddenWrapper"></div>
  <!-- /#EasyuiHiddenWrapper -->

  <div class="hidden" id="ContentScriptWrapper"></div>
  <!-- /#ContentScriptWrapper -->

  <script type="text/javascript">
  			localStorage.clear();
			var BASE_URL = '${pageContext.request.contextPath}';
			var selected_projectId = "";
			
			var localStorageIds = localStorage.getItem("ids");

			if (localStorageIds != undefined && localStorageIds != null) {
				localStorageIds = JSON.parse(localStorageIds);
				var divs = [];
				for(var i = 0; i < localStorageIds.length; i ++) {
					var item = localStorageIds[i]
					var _this = $("#" + item);
					divs.push(_this);
				}

				for(var i = 0; i < localStorageIds.length; i ++) {
					$(".drop-able").eq(i).append(divs[i]);
				}
			}

        	//var issueChart = echarts.init(document.getElementById("issueChart"));
        	var finishChart = echarts.init(document.getElementById("finishChart"));
        	var people = echarts.init(document.getElementById("people"));
        	var chart1 = echarts.init(document.getElementById("chart1"));
        	//var projectKYCountChart = echarts.init(document.getElementById("projectKYCountChart"));
        	//var projectGCCountChart = echarts.init(document.getElementById("projectGCCountChart"));
        	var projectCountChart = echarts.init(document.getElementById("projectCountChart"));
			
            $(function() {

            	
            	var slider_count1 = 0;
            	var slider_count2 = 0;
            	var slider_count3 = 0;
            	var $slider1 = $('.sliderU1');
            	var $slider2 = $('.sliderU2');
            	var $slider3 = $('.sliderU3');
            	var $slider_child_1 = 0;
            	var $slider_child_2 = 0;
            	var $slider_child_3 = 0;
            	var pWidth = $(".slider").width();
            	var pMargin = pWidth*0.01;
            	var $slider_width = pWidth*0.1466//$('.sliderUl>div').width();
            	
            	function sliders() {
                	$slider_child_1 = $('.sliderU1>div').length;
                	$slider_child_2 = $('.sliderU2>div').length;
                	$slider_child_3 = $('.sliderU3>div').length;
                	
                	$('.sliderU1>div').width($slider_width)
                	$('.sliderU1>div').css("margin-left",pMargin).css("margin-right",pMargin)
                	
                	$('.sliderU2>div').width($slider_width)
                	$('.sliderU2>div').css("margin-left",pMargin).css("margin-right",pMargin)
                	
                	$('.sliderU3>div').width($slider_width)
                	$('.sliderU3>div').css("margin-left",pMargin).css("margin-right",pMargin)
                	
                	$slider1.width($slider_child_1 * ($slider_width+pMargin*2));
                	$slider2.width($slider_child_2 * ($slider_width+pMargin*2));
                	$slider3.width($slider_child_3 * ($slider_width+pMargin*2));

                	if ($slider_child_1 < 6) {
                		$('#btn-right1').css({cursor: 'auto'});
                		$('#btn-right1').removeClass("dasabled");
                	}

                	if ($slider_child_2 < 6) {
                		$('#btn-right2').css({cursor: 'auto'});
                		$('#btn-right2').removeClass("dasabled");
                	}

                	if ($slider_child_3 < 6) {
                		$('#btn-right3').css({cursor: 'auto'});
                		$('#btn-right3').removeClass("dasabled");
                	}
            	}
            	
            	$('#btn-left1').click(function() {
            		if (slider_count1 <= 0) {
            			return false;
            		}
            		
            		slider_count1--;
            		$slider1.animate({left: '+=' + ($slider_width+pMargin*2) + 'px'}, 'slow');
            		slider_pic1();
            	});

            	$('#btn-right1').click(function() {
            		if ($slider_child_1 < 6 || slider_count1 >= $slider_child_1 - 6) {
            			return false;
            		}
            		
            		slider_count1++;
            		$slider1.animate({left: '-=' + ($slider_width+pMargin*2) + 'px'}, 'slow');
            		slider_pic1();
            	});
            	
            	$('#btn-left2').click(function() {
            		if (slider_count2 <= 0) {
            			return false;
            		}
            		
            		slider_count2--;
            		$slider2.animate({left: '+=' + ($slider_width+pMargin*2) + 'px'}, 'slow');
            		slider_pic2();
            	});

            	$('#btn-right2').click(function() {
            		if ($slider_child_2 < 6 || slider_count2 >= $slider_child_2 - 6) {
            			return false;
            		}
            		
            		slider_count2++;
            		$slider2.animate({left: '-=' + ($slider_width+pMargin*2) + 'px'}, 'slow');
            		slider_pic2();
            	});
            	
            	$('#btn-left3').click(function() {
            		if (slider_count3 <= 0) {
            			return false;
            		}
            		
            		slider_count3--;
            		$slider3.animate({left: '+=' + ($slider_width+pMargin*2) + 'px'}, 'slow');
            		slider_pic3();
            	});

            	$('#btn-right3').click(function() {
            		if ($slider_child_3 < 6 || slider_count3 >= $slider_child_3 - 6) {
            			return false;
            		}
            		
            		slider_count3++;
            		$slider3.animate({left: '-=' + ($slider_width+pMargin*2) + 'px'}, 'slow');
            		slider_pic3();
            	});

            	function slider_pic1() {
            		if (slider_count1 >= $slider_child_1 - 6) {
            			$('#btn-right1').css({cursor: 'auto'});
            			$('#btn-right1').addClass("dasabled");
            		}
            		else if (slider_count1 > 0 && slider_count1 <= $slider_child_1 - 6) {
            			$('#btn-left1').css({cursor: 'pointer'});
            			$('#btn-left1').removeClass("dasabled");
            			$('#btn-right1').css({cursor: 'pointer'});
            			$('#btn-right1').removeClass("dasabled");
            		}
            		else if (slider_count1 <= 0) {
            			$('#btn-left1').css({cursor: 'auto'});
            			$('#btn-left1').addClass("dasabled");
            		}
            	}

            	function slider_pic2() {
            		if (slider_count2 >= $slider_child_2 - 6) {
            			$('#btn-right2').css({cursor: 'auto'});
            			$('#btn-right2').addClass("dasabled");
            		}
            		else if (slider_count2 > 0 && slider_count2 <= $slider_child_2 - 6) {
            			$('#btn-left2').css({cursor: 'pointer'});
            			$('#btn-left2').removeClass("dasabled");
            			$('#btn-right2').css({cursor: 'pointer'});
            			$('#btn-right2').removeClass("dasabled");
            		}
            		else if (slider_count2 <= 0) {
            			$('#btn-left2').css({cursor: 'auto'});
            			$('#btn-left2').addClass("dasabled");
            		}
            	}

            	function slider_pic3() {
            		if (slider_count3 >= $slider_child_3 - 6) {
            			$('#btn-right3').css({cursor: 'auto'});
            			$('#btn-right3').addClass("dasabled");
            		}
            		else if (slider_count3 > 0 && slider_count3 <= $slider_child_3 - 6) {
            			$('#btn-left3').css({cursor: 'pointer'});
            			$('#btn-left3').removeClass("dasabled");
            			$('#btn-right3').css({cursor: 'pointer'});
            			$('#btn-right3').removeClass("dasabled");
            		}
            		else if (slider_count3 <= 0) {
            			$('#btn-left3').css({cursor: 'auto'});
            			$('#btn-left3').addClass("dasabled");
            		}
            	}

            	$(".drag-able .drag-able-title").draggable({
    				proxy:function(source){
    					var p = $('<div class="proxy">拖到到此</div>');
    					p.appendTo('body');
    					return p;
    				}
    			});
            	

        		$(".drop-able").droppable({
        			onDrop: function(e, source) {
        				var info = $(source).parent().parent();
        				var parent = $(info).parent();
        				var this_first = $(this).children(":first");
        				this_first.appendTo(parent)
        				$(this).append(info);
        				$(source).draggable({
        					proxy:function(source){
        						var p = $('<div class="proxy">拖到到此</div>');
        						p.appendTo('body');
        						return p;
        					}
        				});
        				var ids = [];
        				$(".drag-able").each(function() {
        					ids.push(this.id)
        				})
        				localStorage.setItem("ids", JSON.stringify(ids));

                    	//issueChart.resize();
                    	finishChart.resize();
                    	people.resize();
                    	chart1.resize();
                    	//projectKYCountChart.resize();
                    	//projectGCCountChart.resize();
                    	projectCountChart.resize();
        			}
        		})
            	
            	$("#feeselect").change(function(){
            		var year = $(this).val();
            		feeChartInit(selected_projectId, year);
            	});
            	
            	/* $("#issueselect").change(function(){
            		var year = $(this).val();
            		issueChartInit(selected_projectId, year);
            	}); */
            	
            	$("#peopleselect").change(function(){
            		var year = $(this).val();
            		peopleChartInit(selected_projectId, year);
            	});
            	
            	//projectCountInit();
            	projectCountInit1();
            	
            	$(".hometab").click(function(){
            		$(".hometab").removeClass("curtab");
            		$(this).addClass("curtab");
            		$(".procontentlist").hide();
            		$("#"+$(this).attr("tar")).show();
            	});
            	
            	$(".tasktab").click(function(){
            		$(".tasktab").removeClass("curtab");
            		$(this).addClass("curtab");
            		$(".taskList").hide();
            		$("#"+$(this).attr("tar")).show();
            	});
            	
            	$("#projectList").on("click","li",function(){
            		initThreeCharts($(this).attr("projectId"));
            		$("#projectList li").removeClass("curProject");
            		$("#favoriteProject li").removeClass("curProject");
            		$(this).addClass("curProject");
            	})
            	
            	$("#favoriteProject").on("click","li",function(){
            		initThreeCharts($(this).attr("projectId"));
            		$("#projectList li").removeClass("curProject");
            		$("#favoriteProject li").removeClass("curProject");
            		$(this).addClass("curProject");
            	})
            	
            	$("select#vrotaulOrgSelect").change(function(){
            		var projectId = '';
            		$("#projectList").find('li').each(function(){
            			if ($(this).attr('class') == 'curProject') {
            				projectId = $(this).attr("projectid");
            			}
            		})
            		finishPlanInit(projectId, this.value, $("#vrotaulOrgYearSelect").val());
            	})
            	$("select#vrotaulOrgYearSelect").change(function(){
            		var projectId = '';
            		$("#projectList").find('li').each(function(){
            			if ($(this).attr('class') == 'curProject') {
            				projectId = $(this).attr("projectid");
            			}
            		})
            		finishPlanInit(projectId, $("#vrotaulOrgSelect").val(), $(this).val());
            	})
            	
            	
        		$.ajax({
        			url : BASE_URL + '/main-info.json',
        			type : 'POST',
        			data : "",
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					var years = response.dataMap.returnObj.years;
        					var yearSelector = "";
        					for (var y = 0; y < years.length; y++) {
        						var selected = "";
        						if (y == years.length - 1) {
        							selected = "selected";
        						}
        						yearSelector += "<option "+selected+">"+years[y]+"</option>";
        					}
        					
        					$(".yearSelector").html(yearSelector);
        					
        					var yearSelector1 = "";
        					for (var y = 0; y < years.length; y++) {
        						var selected = "";
        						if (y == years.length - 1) {
        							selected = "selected";
        						}
        						yearSelector1 += "<option "+selected+">"+years[y]+"</option>";
        					}
        					yearSelector1 += "<option>至今</option>";
        					$(".yearSelector1").html(yearSelector1);
        					var cards = ["card-red","card-purple","card-deepblue","card-blue","card-orange","card-pay","card-5"];
        					var data_id = 1;
        					for(i in response.dataMap.returnObj.processSummary){
        						var info = response.dataMap.returnObj.processSummary[i];
        						if (i == 'pendingProcess') {
        							data_id = 1;
            						var processHtml = "";
            						for (j in info) {
            							var className = cards[j % 7];
            							var num = "--";
            							if (info[j].num > 0) {
            								num = info[j].num;
                							processHtml += '<div class="s-card '+className+'" data-target="'+data_id+'" data-id="'+info[j].id+'" data-name="'
                							+info[j].name+'"><p>'+info[j].name+'</p><p><b>'+num+'</b></p></div>';
            							}
            						}
            						processHtml += '<div style="clear: both"></div>';
            						$("#sliderU" + data_id).html(processHtml);
        						}
        						if (i == 'doneProcess') {
        							data_id = 2;
        						}
        						if (i == 'initiatedProcess') {
        							data_id = 3;
        						}
        					}
        	            	$(".s-card").click(function() {
        	            		openProcess($(this).attr("data-target"),$(this).attr("data-id"),$(this).attr("data-name"));
        	            	});
        					sliders();
        					/* for(i in response.dataMap.returnObj.projectSummary){
        						$("#"+i).html(response.dataMap.returnObj.projectSummary[i])
        					}
        					for(i in response.dataMap.returnObj.fundsSummary){
        						$("#"+i).html(response.dataMap.returnObj.fundsSummary[i])
        						$("#"+i+"Div").css("width",(response.dataMap.returnObj.fundsSummary[i]/response.dataMap.returnObj.fundsSummary["total"])*100+"%")
        					} */
        					$("#projectList").empty();
        					for(var i = 0;i < response.dataMap.returnObj.projectInfo.length;i++){
        						var data = response.dataMap.returnObj.projectInfo[i];
        						var className="";
        						if(data.status == "未提交"){
        							className = "red";
        						}else if(data.status == "已提交"){
        							className = "green";
        						}
        						var name = data.name?data.name:"--";
        						var time = data.time?data.time:"--";
        						var leader = data.leader?data.leader:"--";
        						var curProject = "";
        						if(i == 0){
        							curProject = "curProject";
        						}

        						var url = "/project-edge/project/project-init/find.json";
        						var rowId = data.id;
        						
        						var findForm = "<a href='javascript:void(0)' onclick=\"openProjectDialog(\'"+url+"\', \'"+rowId+"\')\">"+name+"</a>";
        						
        						$("#projectList").append('<li class="'+curProject+'" projectId='+data.id+'>'
        			                    +'<div class="s-width-5">'+(i+1)+'</div>'
        			                    +'<div class="s-width-50" title="'+name+'">'+findForm+'</div>'
        			                    +'<div class="s-width-15">'+time+'</div>'
        			                    +'<div class="s-width-15">'+leader+'</div>'
        			                    +'<div class="s-width-15 '+className+'">'+data.status+'</div>'
        			                    +'</li>')
        					}
        					$("#favoriteProject").empty();
        					for(var i = 0;i < response.dataMap.returnObj.favoriteInfo.length;i++){
        						var data = response.dataMap.returnObj.favoriteInfo[i];
        						var className="";
        						if(data.status == "未提交"){
        							className = "red";
        						}else if(data.status == "已提交"){
        							className = "green";
        						}
        						var name = data.name?data.name:"--";
        						var time = data.time?data.time:"--";
        						var leader = data.leader?data.leader:"--";
        						$("#favoriteProject").append('<li projectId='+data.id+'>'
        			                    +'<div class="s-width-5">'+(i+1)+'</div>'
        			                    +'<div class="s-width-50" title="'+name+'">'+name+'</div>'
        			                    +'<div class="s-width-15">'+time+'</div>'
        			                    +'<div class="s-width-15">'+leader+'</div>'
        			                    +'<div class="s-width-15 '+className+'">'+data.status+'</div>'
        			                    +'</li>')
        					}
        					initThreeCharts($("#projectList li").eq(0).attr("projectId"));
        					

        					$("#progressListMy").empty();
        					for(var i = 0;i < response.dataMap.returnObj.taskFavoriteInfo.length;i++){
        						var data = response.dataMap.returnObj.taskFavoriteInfo[i];
           						var planStatus = "#404040";
           						if(data.progress == "已完成"){
           							planStatus = "#00a65a";
           						}else if(data.progress == "未完成"){
           							planStatus = "#dd4b39";
           						}
           						$("#progressListMy").append('<li data-id="'+data.id+'" data-taskId="'+data.taskId+'">'
           			                    +'<div class="s-width-35" style="padding: 0 5px;" title="'+data.planName+'">'+data.planName+'</div>'
           			                    +'<div class="s-width-20" title="'+data.name+'">'+data.name+'</div>'
           			                    +'<div class="s-width-20" style="color:'+planStatus+'">'+data.progress+'</div>'
           			                    +'<div class="s-width-20" title="'+data.status+'">'+data.status+'</div>'
           			                    +'</li>')
           					}
           					$("#progressListMy li").click(function() {
           						var planId = $(this).attr("data-id");
           						var taskId = $(this).attr("data-taskId");
           						Main.openTab('计划管理', '/project-edge/schedule/plan/main.htm?planId=' + planId + '&taskId=' + taskId);
           					})
        					
        					$("#notice").empty();
        					
        					var url = "";
        					
        					for(var i = 0;i < response.dataMap.returnObj.noticeInfo.length;i++){
        						var data = response.dataMap.returnObj.noticeInfo[i];
        						var rowId = data.id;
        						var findForm = "<a href='javascript:void(0)' onclick=\"openNoticeDialog(\'"+url+"\', \'"+rowId+"\')\">"+data.noticeTitle+"</a>";
        						$("#notice").append('<li>'
        			                    +'<div class="s-width-40">'+findForm+'</div>'
        			                    +'<div class="s-width-30">'+data.originator+'</div>'
        			                    +'<div class="s-width-30">'+data.time+'</div>'
        			                    +'</li>')
        					}
        				}else{
        					//alert(response.message)
        				}
        			},
        			error : function(XMLHttpRequest, textStatus, errorThrown) {
        				
        			}
        		})
        		drawChart2();
            })
            
            function initThreeCharts(projectId){
            	if(projectId){
            		selected_projectId = projectId
            		peopleChartInit(projectId, "");
            		//issueChartInit(projectId, "");
            		feeChartInit(projectId, "");
            		finishPlanInit(projectId);
            		$.ajax({
            			url : BASE_URL + '/main-chartInfo.json',
            			type : 'POST',
            			data : {projectId:projectId},
            			dataType : 'json',
            			success : function(response, statusText, xhr, jqForm) {
            				if(response.status == 1){
               				    $("#progressList").empty();
               					for(var i = 0;i < response.dataMap.returnObj.tasks.length;i++){
               						var data = response.dataMap.returnObj.tasks[i];
               						var planStatus = "#404040";
               						if(data.progress == "已完成"){
               							planStatus = "#00a65a";
               						}else if(data.progress == "未完成"){
               							planStatus = "#dd4b39";
               						}
               						$("#progressList").append('<li data-id="'+data.id+'" data-taskId="'+data.taskId+'">'
               			                    +'<div class="s-width-35" style="padding: 0 5px;" title="'+data.planName+'">'+data.planName+'</div>'
               			                    +'<div class="s-width-20" title="'+data.name+'">'+data.name+'</div>'
               			                    +'<div class="s-width-20" style="color:'+planStatus+'">'+data.progress+'</div>'
               			                    +'<div class="s-width-20" title="'+data.status+'">'+data.status+'</div>'
               			                    +'</li>')
               					}
               					$("#progressList li").click(function() {
               						var planId = $(this).attr("data-id");
               						var taskId = $(this).attr("data-taskId");
               						//Main.openTab('计划管理', '/project-edge/schedule/plan/main.htm?planId=' + planId + '&taskId=' + taskId);
               						var _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=3&planId=' + planId;

               			            EasyDialogUtils.openMaxModal(_url, '查看任务');
               					})
            				}else{
            					//alert(response.message)
            				}
            			},
            			error : function(XMLHttpRequest, textStatus, errorThrown) {
            				
            			}
            		})
            	}
            }
            
            function drawFinishChart(box,title,legend,xAxis,unit,seriesData,boundaryGap){
            	
            	console.log('drawFinishChart', box, title);
            	
            	var myChart = finishChart;
            	option = {
					backgroundColor: '#fff',
					color: ['#ffaec9','#22b14c','#fff200','#7092be'],
					title: {
	                    text: title,
	                    left: "center",
	                    top: 'top',
	                    textStyle:{
	                    	fontSize:"14px"
	                    }
					},
					grid:{
						left:"50px",
						top:"30%",
						right:"5%",
						bottom:"10%"
					},
					tooltip: {
                        trigger: 'axis',
                    },
           		    xAxis: {
           		        type: 'category',
           		    	boundaryGap: boundaryGap,
           		        data: xAxis
           		    },
           		    yAxis: {
           		        type: 'value',
						axisLabel:{
							formatter:"{value}"+unit
						}
           		    },
           		    series: seriesData
           		};
            	finishChart.setOption(option, true);
            }

            function feeChartInit(projectId,year) {
            	$.ajax({
        			url : BASE_URL + '/fee-chartInfo.json',
        			type : 'POST',
        			data : {projectId:projectId, year:year},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					var xData = [];
        				    var info = response.dataMap.returnObj.budgetFee;
        			    	var seriesDatas = [];
        				    for (var key in info){
        				    	xData.push(key.slice(5).replace(/\b(0+)/gi,"") + "月");
        				    	var datas = info[key];
        				    	for(var i = 0; i < datas.length; i ++) {
        				    		var d = datas[i];
        				    		if (seriesDatas.length == 0) {
        	    				    	var seriesData = {
        	       				    		name: "",
        	       	           		        data: [],
        	       	           		        type: 'line'
        	       	           		    }
        				    			seriesData.name = d.virtualOrgName;
        				    			seriesData.data.push(d.usedSum);
        				    			seriesDatas.push(seriesData);
        				    		} else {
        				    			var flag = true;
        				    			for(var j = 0; j < seriesDatas.length; j ++) {
        				    				if (seriesDatas[j].name == d.virtualOrgName) {
        				    					seriesDatas[j].data.push(d.usedSum)
        				    					flag = false;
        				    				}
        				    			}
        				    			if (flag) {
        	           				    	var seriesData = {
        	                					name: "",
        	                	           		data: [],
        	                	           		type: 'line'
        	                	           	}
        	   				    			seriesData.name = d.virtualOrgName;
        	   				    			seriesData.data.push(d.usedSum);
        	   				    			seriesDatas.push(seriesData);
        				    			}
        				    		}
        				    	}
        				    	
        				    }
        				    drawFeeChart(xData,seriesDatas);
        				}
        			}
            	});
            }
            
            function issueChartInit(projectId, year) {
            	$.ajax({
        			url : BASE_URL + '/issue-chartInfo.json',
        			type : 'POST',
        			data : {projectId:projectId, year:year},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
			    			var xDataissue = [];
						    var issue = response.dataMap.returnObj.issueMap;
					    	var seriesDatasissue = [];
						    for (var key in issue){
						    	xDataissue.push(key.slice(5).replace(/\b(0+)/gi,"") + "月");
						    	var datas = issue[key];
						    	for(var i = 0; i < datas.length; i ++) {
						    		var d = datas[i];
						    		if (seriesDatasissue.length == 0) {
			    				    	var seriesData = {
			       				    		name: "",
			       	           		        data: [],
			       	           		        type: 'line'
			       	           		    }
						    			seriesData.name = d.virtualOrgName;
						    			seriesData.data.push(d.count);
						    			seriesDatasissue.push(seriesData);
						    		} else {
						    			var flag = true;
						    			for(var j = 0; j < seriesDatasissue.length; j ++) {
						    				if (seriesDatasissue[j].name == d.virtualOrgName) {
						    					seriesDatasissue[j].data.push(d.count)
						    					flag = false;
						    				}
						    			}
						    			if (flag) {
			           				    	var seriesData = {
			                					name: "",
			                	           		data: [],
			                	           		type: 'line'
			                	           	}
			   				    			seriesData.name = d.virtualOrgName;
			   				    			seriesData.data.push(d.count);
			   				    			seriesDatasissue.push(seriesData);
						    			}
						    		}
						    	}
						    	
						    }
			            	drawIssueChart(xDataissue,seriesDatasissue);
        				}
        			}
            	});
            }
            
            function peopleChartInit(projectId,year) {
            	$.ajax({
        			url : BASE_URL + '/people-chartInfo.json',
        			type : 'POST',
        			data : {projectId:projectId, year:year},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					var xData = [];
        				    var info = response.dataMap.returnObj.virtualOrgMap;
        			    	var seriesDatas = [];
        				    for (var key in info){
        				    	xData.push(key.slice(5).replace(/\b(0+)/gi,"") + "月");
        				    	var datas = info[key];
        				    	for(var i = 0; i < datas.length; i ++) {
        				    		var d = datas[i];
        				    		if (seriesDatas.length == 0) {
        	    				    	var seriesData = {
        	       				    		name: "",
        	       	           		        data: [],
        	       	           		        type: 'line'
        	       	           		    }
        				    			seriesData.name = d.virtualOrgName;
        				    			seriesData.data.push(d.count);
        				    			seriesDatas.push(seriesData);
        				    		} else {
        				    			var flag = true;
        				    			for(var j = 0; j < seriesDatas.length; j ++) {
        				    				if (seriesDatas[j].name == d.virtualOrgName) {
        				    					seriesDatas[j].data.push(d.count)
        				    					flag = false;
        				    				}
        				    			}
        				    			if (flag) {
        	           				    	var seriesData = {
        	                					name: "",
        	                	           		data: [],
        	                	           		type: 'line'
        	                	           	}
        	   				    			seriesData.name = d.virtualOrgName;
        	   				    			seriesData.data.push(d.count);
        	   				    			seriesDatas.push(seriesData);
        				    			}
        				    		}
        				    	}
        				    	
        				    }
        					drawPeopleChart(xData,seriesDatas);
        				}
        			}
            	});
            }
            
            function finishPlanInit(projectId, virtualOrg, finishPlanYearselect) {
            	$.ajax({
        			url : BASE_URL + '/main-siteInfo.json',
        			type : 'POST',
        			data : {projectId:projectId, virtualOrg:virtualOrg, nowYear:finishPlanYearselect},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					if (virtualOrg || finishPlanYearselect) {
        						var planStats = response.dataMap.returnObj.planStats;
        						finishPlanData(planStats);
        					} else {
        						var planStats = response.dataMap.returnObj.planStats;
        						finishPlanData(planStats);
            					//$("#vrotaulOrgSelect").html("<option value=''>全部小组</option>");
            				    if(response.dataMap.returnObj.virtualOrgList.length > 0){
                				    for(var i = 0;i < response.dataMap.returnObj.virtualOrgList.length;i++){
                				    	// vrotaulOrgSelect
                				    	if (response.dataMap.returnObj.virtualOrgList[i].virtaulOrgName == "基础网络组") {
                				    		$("#vrotaulOrgSelect").append("<option value='"+response.dataMap.returnObj.virtualOrgList[i].id+"' selected>"+response.dataMap.returnObj.virtualOrgList[i].virtaulOrgName+"</option>");
                				    		continue;
                				    	}
                				    	//vrotaulOrgSelect
                    				    $("#vrotaulOrgSelect").append("<option value='"+response.dataMap.returnObj.virtualOrgList[i].id+"'>"+response.dataMap.returnObj.virtualOrgList[i].virtaulOrgName+"</option>");
                				    }
            				    }
        					}
        				}else{
        					//alert(response.message)
        				}
        			},
        			error : function(XMLHttpRequest, textStatus, errorThrown) {
        				
        			}
        		})
            }
            
            function finishPlanData(planStats){
            	
				if(planStats.length > 0){
					
					var groupArr = [];
					var seriesData = [];
					var coreCounts = [];
					var coreActualCounts = [];
					var edgeCounts = [];
					var edgeActualCounts = [];
					var totalCounts = [];
					
					var coreCount = 0;
					var edgeCount = 0;
					var totalCount = 0;
					
					for(let i = 0; i < planStats.length; i++){
						groupArr.push((i+1)+"月");
						
						coreCounts.push(planStats[i][0]);
						coreCount += planStats[i][0];
						coreActualCounts.push(planStats[i][3]);
						
						edgeCounts.push(planStats[i][1]);
						edgeCount += planStats[i][1];
						edgeActualCounts.push(planStats[i][4]);
						
						totalCounts.push(planStats[i][2]);
						//totalCount = coreActualCounts + edgeActualCounts;
					};
					seriesData = [{
           		    	name:"核心节点目标数",
           		        data: coreCounts,
           		        type: 'line',
           		        lineStyle:{
           		        	normal:{
           		        		color:'#ffaec9'
           		        	}
           		        }
           		    },{
           		    	name:"核心节点完成数",
           		        data: coreActualCounts,
           		        type: 'line',
           		        lineStyle:{
           		        	normal:{
           		        		color:'#22b14c'
           		        	}
           		        }
           		    },{
           		    	name:"边缘节点目标数",
           		        data: edgeCounts,
           		        type: 'line',
           		        lineStyle:{
           		        	normal:{
           		        		color:'#fff200'
           		        	}
           		        }
           		    },{
           		    	name:"边缘节点完成数",
           		        data: edgeActualCounts,
           		        type: 'line',
           		        lineStyle:{
           		        	normal:{
           		        		color:'#7092be'
           		        	}
           		        }
           		    }
           		    /* ,{
           		    	name:"当年累计完成数",
           		        data: totalCounts,
           		        type: 'line'
           		    } */
           		    ];
					drawFinishChart("finishChart","",['核心节点目标数','核心节点完成数','边缘节点目标数','边缘节点完成数'/* ,'累计完成' */],groupArr,'',seriesData,false);
				    //$("#totalText").text("核心节点:"+ coreCount +" 边缘节点:"+ edgeCount +" 总站点计划:"+ totalCount);
				}else{
					drawFinishChart("finishChart","",['核心节点目标数','核心节点完成数','边缘节点目标数','边缘节点完成数'/* ,'累计完成' */],[],'',[],false);
					//$("#totalText").text("核心节点:-- 边缘节点:-- 总站点计划:--");
				}
            }
            
            function drawPeopleChart(xData,seriesData){
            	var myChart = people;
            	option = {
					backgroundColor: '#fff',
					title: {
	                    text: "",
	                    left: "center",
	                    top: 'top',
	                    textStyle:{
	                    	fontSize:"14px"
	                    }
					},
					grid:{
						left:"50px",
						top:"30%",
						right:"5%",
						bottom:"10%"
					},
					tooltip: {
                        trigger: 'axis',
                    },
           		    xAxis: {
           		        type: 'category',
           		     	boundaryGap: false,
           		        data: xData
           		    },
           		    yAxis: {
           		        type: 'value',
						axisLabel:{
							formatter:"{value}人"
						}
           		    },
           		    series: seriesData
           		};
            	people.setOption(option);
            }
            
            function drawChart2(){
            	/* var myChart = echarts.init(document.getElementById("chart1"));
            	option = {
					backgroundColor: '#fff',
					color: ['#556fb5','#f24040'],
					title: {
	                    text: "年度资金统计",
	                    left: "center",
	                    top: 'top',
	                    textStyle:{
	                    	fontSize:"14px"
	                    }
					},
					grid:{
						left:"10%",
						top:"30%",
						right:"5%",
						bottom:"10%"
					},
					tooltip: {
                        trigger: 'axis',
                    },
           		    xAxis: {
           		        type: 'category',
           		        data: ['组一', '组二', '组三', '组四', '组五', '组六', '组七']
           		    },
           		    yAxis: {
           		        type: 'value',
						axisLabel:{
							formatter:"{value}万元"
						}
           		    },
           		    series: [{
           		    	name:"计划资金",
           		        data: [120, 200, 150, 80, 70, 110, 130],
           		        type: 'bar',
           		    	barWidth:"20%"
           		    },{
           		    	name:"已用资金",
           		        data: [20, 100, 50, 30, 20, 100, 90],
           		        type: 'bar',
           		    	barWidth:"20%"
           		    }]
           		};
            	myChart.setOption(option); */
            }
            
            function openProcess(val,flowSettingId,name) {
            	var title = '';
            	var url = '';
				if (val == 1) {
					title = '待办事宜-' + name;
					url = '/project-edge/flowable/monitor/pending-process/main.htm?id=' + flowSettingId;
				}
				if (val == 2) {
					title = '已办事宜-' + name;
					url = '/project-edge/flowable/monitor/done-process/main.htm?id=' + flowSettingId;
				}
				if (val == 3) {
					title = '我的流程-' + name;
					url = '/project-edge/flowable/monitor/initiated-process/main.htm?id=' + flowSettingId;
				}
            	Main.openTab(title, url);
            }
            
            function drawIssueChart(xData,seriesData){
            	var myChart = issueChart;
            	option = {
					backgroundColor: '#fff',
					title: {
	                    text: "问题类型统计",
	                    left: "center",
	                    top: 'top',
	                    textStyle:{
	                    	fontSize:"14px"
	                    }
					},
					grid:{
						left:"10%",
						top:"30%",
						right:"5%",
						bottom:"10%"
					},
					tooltip: {
                        trigger: 'axis',
                    },
           		    xAxis: {
           		        type: 'category',
           		    	boundaryGap: false,
           		        data: xData
           		    },
           		    yAxis: {
           		        type: 'value',
						axisLabel:{
							formatter:"{value}"
						}
           		    },
           		    series: seriesData
           		};
            	issueChart.setOption(option);
            }

            
            function drawFeeChart(xData,seriesData){
            	var myChart = chart1;
            	option = {
					backgroundColor: '#fff',
					title: {
	                    text: "",
	                    left: "center",
	                    top: 'top',
	                    textStyle:{
	                    	fontSize:"14px"
	                    }
					},
					grid:{
						left:"50px",
						top:"30%",
						right:"5%",
						bottom:"10%"
					},
					tooltip: {
                        trigger: 'axis',
                    },
           		    xAxis: {
           		        type: 'category',
           		    	boundaryGap: false,
           		        data: xData
           		    },
           		    yAxis: {
           		        type: 'value',
						axisLabel:{
							formatter:"{value}"
						}
           		    },
           		    series: seriesData
           		};
            	chart1.setOption(option);
            }
            
            function projectCountInit () {
            	$.ajax({
        			url : BASE_URL + '/main-projectCountInfo.json',
        			type : 'POST',
        			data : {},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				console.info(response.dataMap.returnObj)
        				if(response.status == 1){
        					if (response.dataMap.returnObj.gongcheng) {
        						var xData = ['建议','可研','初设','概算','建设','阶段性验收','初验','终验','运维']
        						var datas = [
    								response.dataMap.returnObj.gongcheng.jieduan_1,
    								response.dataMap.returnObj.gongcheng.jieduan_2,
    								response.dataMap.returnObj.gongcheng.jieduan_3,
    								response.dataMap.returnObj.gongcheng.jieduan_4,
    								response.dataMap.returnObj.gongcheng.jieduan_5,
    								response.dataMap.returnObj.gongcheng.jieduan_6,
    								response.dataMap.returnObj.gongcheng.jieduan_7,
    								response.dataMap.returnObj.gongcheng.jieduan_8,
    								response.dataMap.returnObj.gongcheng.jieduan_9
    							];
        						/* var series = [{
        							name: '工程类',
        							barWidth:50,
        							data: [
        								response.dataMap.returnObj.gongcheng.jieduan_1,
        								response.dataMap.returnObj.gongcheng.jieduan_2,
        								response.dataMap.returnObj.gongcheng.jieduan_3,
        								response.dataMap.returnObj.gongcheng.jieduan_4,
        								response.dataMap.returnObj.gongcheng.jieduan_5,
        								response.dataMap.returnObj.gongcheng.jieduan_6,
        								response.dataMap.returnObj.gongcheng.jieduan_7,
        								response.dataMap.returnObj.gongcheng.jieduan_8,
        								response.dataMap.returnObj.gongcheng.jieduan_9
        							],
        							type: 'bar'
        						}] */
								var seriesData = [];
        						for (var d = 0; d < xData.length; d ++) {
                           			seriesData.push({
                               			name: xData[d],
                               			value: datas[d]
                               		});
        						}
        						drawProjectCountChart('工程类',seriesData,"projectGCCountChart");
        					}
        					
        					if (response.dataMap.returnObj.keyan) {
        						var xData = ['建议','可研','方案','概算','实施','阶段性验收','初验','终验','运维']
        						var datas = [
    								response.dataMap.returnObj.keyan.jieduan_1,
    								response.dataMap.returnObj.keyan.jieduan_2,
    								response.dataMap.returnObj.keyan.jieduan_3,
    								response.dataMap.returnObj.keyan.jieduan_4,
    								response.dataMap.returnObj.keyan.jieduan_5,
    								response.dataMap.returnObj.keyan.jieduan_6,
    								response.dataMap.returnObj.keyan.jieduan_7,
    								response.dataMap.returnObj.keyan.jieduan_8,
    								response.dataMap.returnObj.keyan.jieduan_9
    							];
        						/* var series = [{
        							name: '科研类',
        							barWidth:50,
        							data: [
        								response.dataMap.returnObj.keyan.jieduan_1,
        								response.dataMap.returnObj.keyan.jieduan_2,
        								response.dataMap.returnObj.keyan.jieduan_3,
        								response.dataMap.returnObj.keyan.jieduan_4,
        								response.dataMap.returnObj.keyan.jieduan_5,
        								response.dataMap.returnObj.keyan.jieduan_6,
        								response.dataMap.returnObj.keyan.jieduan_7,
        								response.dataMap.returnObj.keyan.jieduan_8,
        								response.dataMap.returnObj.keyan.jieduan_9
        							],
        							type: 'bar'
        						}] */
								var seriesData = [];
        						for (var d = 0; d < xData.length; d ++) {
                           			seriesData.push({
                               			name: xData[d],
                               			value: datas[d]
                               		});
        						}
        						drawProjectCountChart('科研类',seriesData,"projectKYCountChart");
        					}
        				}
        			}
            	})
            }
            function drawProjectCountChart(title, legendData, elementId) {
            	var myChart = elementId == "projectKYCountChart" ? projectKYCountChart : projectGCCountChart;
            	option = {
                   	    title : {
                   	        text: title,
                   	        x:'center'
                   	    },
                   	    tooltip : {
                   	        trigger: 'item',
                   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                   	    },
	                   	grid:{
	 						left:"10%",
	 						top:"30%",
	 						right:"5%",
	 						bottom:"10%"
	 					},
                   	    legend: {
                   	        orient: 'vertical',
                   	        right: 10,
                   	        top: 20,
                   	        bottom: 20,
                   	        data: legendData
                   	    },
                   	    series : [
                   	        {
                   	            name: '数量',
                   	            type: 'pie',
                   	            radius : '55%',
                   	            center: ['40%', '50%'],
                   	            data: legendData,
                   	            itemStyle: {
                   	                emphasis: {
                   	                    shadowBlur: 10,
                   	                    shadowOffsetX: 0,
                   	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                   	                }
                   	            }
                   	        }
                   	    ]
            		};
            	myChart.setOption(option);
            }
            function projectCountInit1 () {
            	$.ajax({
        			url : BASE_URL + '/main-projectCountInfo1.json',
        			type : 'POST',
        			data : {},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				console.info(response.dataMap.returnObj)
        				if(response.status == 1){
        					if (response.dataMap.returnObj.gongcheng) {
        						var xData = ['前期准备','实施','阶段性检查','初验','终验']
        						var datas = [
    								response.dataMap.returnObj.gongcheng.jieduan_1,
    								response.dataMap.returnObj.gongcheng.jieduan_2,
    								response.dataMap.returnObj.gongcheng.jieduan_3,
    								response.dataMap.returnObj.gongcheng.jieduan_4,
    								response.dataMap.returnObj.gongcheng.jieduan_5
    							];
								var seriesData = [];
        						for (var d = 0; d < xData.length; d ++) {
                           			seriesData.push({
                               			name: xData[d],
                               			value: datas[d]
                               		});
        						}
        						drawProjectCountChart1('工程类',xData,seriesData,"projectGCCountChart");
        					}
        					
        					/* if (response.dataMap.returnObj.keyan) {
        						var xData = ['建议','可研','方案','概算','实施','阶段性验收','初验','终验','运维']
        						var datas = [
    								response.dataMap.returnObj.keyan.jieduan_1,
    								response.dataMap.returnObj.keyan.jieduan_2,
    								response.dataMap.returnObj.keyan.jieduan_3,
    								response.dataMap.returnObj.keyan.jieduan_4,
    								response.dataMap.returnObj.keyan.jieduan_5
    							];
								var seriesData = [];
        						for (var d = 0; d < xData.length; d ++) {
                           			seriesData.push({
                               			name: xData[d],
                               			value: datas[d]
                               		});
        						}
        						drawProjectCountChart('科研类',seriesData,"projectKYCountChart");
        					} */
        				}
        			}
            	})
            }
            function drawProjectCountChart1(title, xData, legendData, elementId) {
            	var myChart = projectCountChart;
                myChart.setOption(option = {
               		/* grid:{
               			bottom:"100%"
               		}, */
               		tooltip: {
              		        trigger: 'axis',
              		    },
              		  
               	    xAxis: {
               	        type: 'category',
               	        data: xData,
               	        axisLabel:{
                               interval:0,
                               //rotate:45,//倾斜度 -90 至 90 默认为0
                           }
               	    },
               	    yAxis: {
               	        type: 'value'
               	    },
               	    series: [{
               	        data: legendData,
               	        type: 'bar',
               	     	barWidth:30,
               	    }]
               	});
                //window.onresize = myChart.resize;
            	/* option = {
                   	    title : {
                   	        text: title,
                   	        x:'center'
                   	    },
                   	    tooltip : {
                   	        trigger: 'axis',
                   	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                   	    },
                   	    legend: {
                   	        orient: 'vertical',
                   	        right: 10,
                   	        top: 20,
                   	        bottom: 20,
                   	        data: legendData
                   	    },
                   	    series : [
                   	        {
                   	            name: '数量',
                   	            type: 'axis',
                   	            radius : '55%',
                   	            center: ['40%', '50%'],
                   	            data: legendData,
                   	            itemStyle: {
                   	                emphasis: {
                   	                    shadowBlur: 10,
                   	                    shadowOffsetX: 0,
                   	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                   	                }
                   	            }
                   	        }
                   	    ]
            		}; 
            	myChart.setOption(option);*/
            }
            
            function openProjectDialog(url, projectId) {
            	MainUtils.openLoading();
            	var options = {
                        url: BASE_URL + '/main-viewProject.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {id: projectId},
                        success: function(response, statusText, xhr, jqForm) {
                        	if (MainUtils.processJsonResult(response, false)) {
                                var json = response.dataMap.returnObj;
                                console.info(json);
                                // 将服务器返回的JSON加载到Form(easyui-form的load效率太低)
                                $('#projectDialog').find('[data-view-field]').each(function() {
                                    var $this = $(this);
                                    var name = $this.attr('data-view-field');
                                    var widget = $this.attr('data-field-widget');

                                    // 加载值
                                    if ('filebox' == widget) {
                                        // 按照约定，文件字段的字段名，去掉末尾的'_'，再加上List，成为文件列表的字段名
                                        value = json[name.replace('Text', '') + 'List'];
                                        FileboxUtils.renderView($this, value);
                                    } else {
                                        var text = '';
                                        if (typeof json[name] != 'undefined') {
                                            text = json[name];
                                            if ($this.is('[data-formatter]')) {
                                                var formatFunc = eval($this.attr('data-formatter'));
                                                text = formatFunc(text, json);
                                            }
                                        }
                                        if (text == null) {
                                            text = '';
                                        }
                                        $this.html(text + '&nbsp;');
                                    }
                                });
                                MainUtils.closeLoading();// 关闭loading提示
                                
                                $('#projectDialog .panel:first .panel-heading strong').html("（内部编号 " +json.projectNum + "）");
                                $('#projectDialog').dialog('open').dialog('center').scrollTop(0);
                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
            	}
                $.ajax(options);
            }
            
            function openNoticeDialog (url, noticeId) {
            	MainUtils.openLoading();
            	var options = {
                        url: BASE_URL + '/main-viewNotice.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {id: noticeId},
                        success: function(response, statusText, xhr, jqForm) {
                        	if (MainUtils.processJsonResult(response, false)) {
                                var json = response.dataMap.returnObj;
                                // 将服务器返回的JSON加载到Form(easyui-form的load效率太低)
                                $('#noticeDialog').find('[data-view-field]').each(function() {
                                    var $this = $(this);
                                    var name = $this.attr('data-view-field');
                                    var widget = $this.attr('data-field-widget');

                                    // 加载值
                                    if ('filebox' == widget) {
                                        // 按照约定，文件字段的字段名，去掉末尾的'_'，再加上List，成为文件列表的字段名
                                        value = json[name.replace('Text', '') + 'List'];
                                        FileboxUtils.renderView($this, value);
                                    } else {
                                        var text = '';
                                        if (typeof json[name] != 'undefined') {
                                            text = json[name];
                                            if ($this.is('[data-formatter]')) {
                                                var formatFunc = eval($this.attr('data-formatter'));
                                                text = formatFunc(text, json);
                                            }
                                        }
                                        if (text == null) {
                                            text = '';
                                        }
                                        $this.html(text + '&nbsp;');
                                    }
                                });
                                MainUtils.closeLoading();// 关闭loading提示
                                $('#noticeDialog').dialog('open').dialog('center').scrollTop(0);
                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
            	}
                $.ajax(options);
            }
        </script>
        <style type="text/css">
        .curProject div a{
          color: white;
        }
        @media (max-width: 1280px) {
          #vrotaulOrgSelect {
           margin-left:0px!important;
           width:53px!important;
          }
          .taskwidth1 {
            width: 20%!important;
          }
          .taskwidth3 {
            width: 25%!important;
          }
          .taskwidth4 {
            width: 25%!important;
          }
        }
        </style>
</body>
</html>