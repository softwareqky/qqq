<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<c:set var="baseUrl" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/material/easyui.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/icon.css"/>" />
<link rel="stylesheet" href="<s:url value="/css/easyui/color.css"/>" />
<link rel="stylesheet" href="${baseUrl}<s:theme code="custom.css"/>" />

<link rel="stylesheet" href="<s:url value="/css/dhtmlxgantt/dhtmlxgantt.css"/>" />
<%-- <link rel="stylesheet" href="${baseUrl}<s:theme code="dhtmlxgantt.skin.css"/>" /> --%>
<link rel="stylesheet" href="<s:url value="/css/dhtmlxgantt/custom.css"/>" />

<script type="text/javascript" src="<s:url value="/js/util/moment/moment.js"/>"></script>

<style>
	input {
		color: infotext;
		margin: 0;
	}
	
	buttonTr {
		background:
	}
	
	.task-compare-updated {
		background-color: #E6E6FA !important;
	}

	.task-compare-added {
		background-color: #00FA9A !important;
	}
	
	.task-compare-deleted {
		background-color: #FFB6C1 !important;
	}

</style>
</head>
<body>
  <%-- 加载easyui时的遮盖，防止画面跳动 --%>
  <div id="EasyuiLoading" class="jeasyui-loading"></div>
  <div class="maximzebuttonposition" style="top: 50%; z-index: 999;">
    <a href="#" class="easyui-linkbutton" data-options="height:55" onclick="ScheduleProgressGantt.maximizeGrid();">></a>
  </div>
  <div id="_Gantt_layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',height:90">
      <div class="container-fluid">
        <div class="row">
          <div class="col-md-11">
            <table class="table-condensed" id='toolbar'>
              <tr id="toolbar_row1">
                <td id="_LinkButton_zoomIn"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.zoomIn();">放大</a></td>
                <td id="_LinkButton_zoomOut"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.zoomOut();">缩小</a></td>
                <td id="_LinkButton_1"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.newTaskDialog();">新增</a></td>
                <td id="_LinkButton_2"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.editTaskDialog();">编辑</a></td>
                <td id="_LinkButton_3"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.deleteTaskDialog();">删除</a></td>
                <td id="_LinkButton_4"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.upLevel();">升级</a></td>
                <td id="_LinkButton_5"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.downLevel();">降级</a></td>
                <td id="_LinkButton_6"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.moveUp();">上移</a></td>
                <td id="_LinkButton_7"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.moveDown();">下移</a></td>
                <td id="_LinkButton_upload"><a href="#" class="easyui-linkbutton" data-options="disabled:true" onclick="ScheduleProgressGantt.uploadAttachment();">上传附件</a></td>
                <td id="_LinkButton_8"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.openCalendarDialog();">日历</a></td>
                <td id="_LinkButton_9"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.openImportExcelDialog();">导入</a></td>
                <td id="_LinkButton_10"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.exportGanttData();">导出</a></td>
                <td id="_LinkButton_20"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.openImport();">导入进度</a></td>
                <td id="_LinkButton_21"><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.openExport();">导出进度</a></td>
                <td id="_LinkButton_11"><a href="#" class="easyui-linkbutton" data-options="plain:'true',width:50" style="background: #556fb5; color: white;"
                  onclick="ScheduleProgressGantt.saveGanttDataToDB();"
                >保存</a></td>
                <td id="_LinkButton_2_2"><a href="#" class="easyui-linkbutton" data-options="" style="background: #556fb5; color: white;"
                  onclick="ScheduleProgressGantt.openEditReportProgressDialog();"
                >编辑</a></td>
                <td id="_LinkButton_12"><a href="#" class="easyui-linkbutton" data-options="plain:'true',width:80" style="background: #556fb5; color: white;"
                  onclick="ScheduleProgressGantt.openReportProgressDialog();"
                >工作组简报</a></td>
                <td id="_LinkButton_workload"><a href="#" class="easyui-linkbutton" data-options="plain:'true',width:80" style="background: #556fb5; color: white;"
                  onclick="ScheduleProgressGantt.openWorkloadDialog();"
                >个人简报</a></td>
                <td style="padding-left: 0px;"></td>
              </tr>
              <tr id="toolbar_row2">
                <td colspan=4>顶层时间按 <select class="easyui-combobox" data-options="editable:false,panelMaxHeight:250,panelHeight:null,onChange:ScheduleProgressGantt.onChangeTopZoomScale,width:80">
                    <option value="year" selected="selected">年</option>
                    <option value="half_year">半年</option>
                    <option value="quarter">季度</option>
                    <option value="month">月</option>
                    <option value="week">周</option>
                    <option value="day">日</option>
                    <option value="hour">时</option>
                </select> 显示</td>
                <td colspan=4>底层时间按 <select class="easyui-combobox"
                  data-options="editable:false,panelMinHeight:25,panelMaxHeight:250,panelHeight:null,onChange:ScheduleProgressGantt.onChangeButtomZoomScale,width:80"
                >
                    <option value="year">年</option>
                    <option value="half_year">半年</option>
                    <option value="quarter">季度</option>
                    <option value="month" selected="selected">月</option>
                    <option value="week">周</option>
                    <option value="day">日</option>
                    <option value="hour">时</option>
                </select> 显示</td>
                <td id="_LinkButton_13" colspan=2><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.addFavorite();">添加关注</a></td>
                <td id="_LinkButton_14" colspan=2><a href="#" class="easyui-linkbutton" data-options="" onclick="ScheduleProgressGantt.delFavorite();">删除关注</a></td>
                <td colspan=4>
                	<span>参与人员</span> 
                	<select id="_participantFilter" class="easyui-combobox" 
                		data-options="editable:false,panelMaxHeight:250,panelHeight:null,onSelect:ScheduleProgressGantt.onSelectParticipantFilter,onUnselect:ScheduleProgressGantt.onUnselectParticipantFilter,width:160" 
                	>
                	</select>
                </td>
                <td colspan=4>
                	<span>任务名称</span> 
                	<select id="_taskNameFilter" class="easyui-textbox" 
                		data-options="panelMaxHeight:250,panelHeight:null,onChange:ScheduleProgressGantt.onTaskNameFilter,width:160" 
                	>
                	</select>
                </td>
              </tr>
            </table>
          </div>
          <div class="col-md-1">
            <div style="margin-top: 10px;">
              任务数： <label id="_TaskCountNumber">0</label>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div data-options="region:'center'">
      <div id="ganttDiv" style="width: 100%; height: 100%;"></div>
    </div>
  </div>

  <div style="display: none;">
    <div id="_CalendarEditorDialog" class="easyui-dialog" title="项目日历"
      data-options="onOpen:ScheduleProgressGantt.onOpenEditCalendarDialog,onClose:ScheduleProgressGantt.onCloseEditCalendarDialog,buttons:'#_CalendarEditorDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
      style="width: 500px; height: 450px; padding: 0px;"
    >
      <div id="_CalendarEditorTabs" class="easyui-tabs" data-options="fit:true">
        <div title="例外日期" style="padding: 10px">
          <table id="_CalendarExceptionGrid" class="easyui-datagrid" data-options="fit:true,singleSelect:true,toolbar:'#_CalendarExceptionToolbar'">
            <thead>
              <tr>
                <th data-options="field:'id',width:100,hidden:true"></th>
                <th data-options="field:'isWorkdayText',width:60,formatter:ScheduleProgressGantt.formatterIsWorkdayText">工作日</th>
                <th data-options="field:'exceptionName',width:160,editor:{type:'textbox'}">例外名称</th>
                <th data-options="field:'startDate',width:120,editor:{type:'datebox'}">开始日期</th>
                <th data-options="field:'endDate',width:120,editor:{type:'datebox'}">结束日期</th>
              </tr>
            </thead>
          </table>
          <div id="_CalendarExceptionToolbar">
            <table>
              <tbody>
                <tr>
                  <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:'true'" onclick="ScheduleProgressGantt.addExceptionDate();">增加</a></td>
                  <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:'true'" onclick="ScheduleProgressGantt.deleteExceptionDate();">删除</a></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div title="工作周" style="padding: 10px">
          <table id="_WorkTimeGrid" class="easyui-datagrid" data-options="fit:true,singleSelect:true">
            <thead>
              <tr>
                <th data-options="field:'id',width:100,hidden:true"></th>
                <th data-options="field:'isWorkdayText',width:60,formatter:ScheduleProgressGantt.formatterIsWorkdayText">工作日</th>
                <th data-options="field:'week',width:160,formatter:ScheduleProgressGantt.formatterWeekName">星期</th>
              </tr>
            </thead>
          </table>
        </div>
      </div>
      <div id="_CalendarEditorDialogButtons">
        <div>
          <a href="javascript:void(0)" class="easyui-linkbutton" onclick="ScheduleProgressGantt.saveCalendarDataToDB();"> <s:message code="ui_common_ok" />
          </a> <a href="javascript:void(0)" class="easyui-linkbutton" onclick="ScheduleProgressGantt.closeCalendarDialog();"> <s:message code="ui.common.cancel" />
          </a>
        </div>
      </div>
    </div>
  </div>

  <div style="display: none;">
    <div id="_TaskEditorDialog" class="easyui-dialog" title="编辑任务"
      data-options="onOpen:ScheduleProgressGantt.onOpenEditTaskDialog,onClose:ScheduleProgressGantt.onCloseEditTaskDialog,buttons:'#_TaskEditorDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
      style="width: 580px; height: 520px; padding: 0px;"
    >
      <div id="_TaskEditorTabs" class="easyui-tabs" data-options="fit:true">
        <div title="常规" style="padding: 10px">
          <form id="_TaskEditorNormalForm">
            <table class="table">
              <tr>
                <td width="15%">任务名称</td>
                <td colspan="3"><input name="taskName" type="text" style="height: 84px;" class="easyui-textbox" data-options="multiline:true,required:true,width:422,validType:{length:[1,250]}" /></td>
              </tr>
              <tr>
                <td>负责人</td>
                <td><input name="leader" type="text" class="easyui-combobox"
                  data-options="width:160,valueField:'id',textField:'text',loadFilter:MainUtils.comboboxLoadFilter,url:'<s:url value="/schedule/plan-task/person-combo-list.json"/>'"
                /></td>
              </tr>
              <tr>
              	<td>参与人</td>
                <td colspan="3">
                	<div id="participantsCombo" style="position:relative; height: 84px">
	                	<div style="position:absolute; z-index: 1000" >
		                	<input name="participantsText" type="text" style="height: 84px;" class="easyui-textbox" data-options="multiline:true,width:422" />
	                	</div>
	                	<div id="participantMask" style="width: 100%;height: 100%; z-index:1001; position: absolute" ></div>
	                	<div id='participantInput' style="position:absolute; bottom:0px;" >
		                	<input name="participants" type='text' class="easyui-combobox"
		                  		data-options="valueField:'id',multiple: true,textField:'text',width:422,loadFilter:MainUtils.comboboxLoadFilter,url:'<s:url value="/schedule/plan-task/person-combo-list.json"/>'"
		                	/>
	                	</div>
                	</div>
                </td>
              </tr>
              <tr>
                <td>优先级</td>
                <td><input name="weight" type="text" class="easyui-numberspinner" data-options="width:160,min:0,max:100" /></td>
                <td>任务类型</td>
                <td><input name="taskType" type="text" class="easyui-textbox" data-options="required:false,width:160,validType:{length:[1,50]}" /></td>
              </tr>
              <tr>
                <td>WBS</td>
                <td><input name="wbs" type="text" class="easyui-textbox" data-options="width:160,disabled:true" /></td>
                <td></td>
                <td></td>
              </tr>
               <tr>
              	<td>计划交付物</td>
                <td><input name="achievement" type="text" class="easyui-textbox" data-options="width:160,readonly:false" /></td>
                <td></td>
                <td></td>
                <td style="display:none">实际交付物</td>
                <td style="display:none"><input name="delivery" type="text" class="easyui-textbox" data-options="width:160" /></td>
              </tr>
            </table>
            <div style="display: none;">
              <table>
                <tr>
                  <td>工时</td>
                  <td><input name="workload" type="text" class="easyui-numberspinner" data-options="width:160,min:0,max:10000" /></td>
                  <td>进度</td>
                  <td><input name="progress" type="text" class="easyui-numberspinner" data-options="required:true,width:160,min:0,max:100" /></td>
                </tr>
              </table>
            </div>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">日期范围</h3>
              </div>
              <div class="panel-body">
                <table class="table">
                  <tr>
                    <td width="15%">开始</td>
                    <td><input name="startDate" type="text" class="easyui-datebox" data-options="required:true,width:160,editable:false,onChange:ScheduleProgressGantt.onChangeForDuration" /></td>
                    <td width="15%">结束</td>
                    <td><input name="endDate" type="text" class="easyui-datebox" data-options="required:true,width:160,editable:false,onChange:ScheduleProgressGantt.onChangeForDuration" /></td>
                  </tr>
                  <tr>
                    <td width="15%">工期</td>
                    <td colspan="3"><input name="durationDays" type="text" class="easyui-numberspinner"
                      data-options="required:true,width:160,min:0,max:10000,onChange:ScheduleProgressGantt.onChangeForEnddate"
                    /></td>
                  </tr>
                </table>
              </div>
            </div>
          </form>
        </div>
        <div title="前置任务" style="padding: 10px">
          <table id="_TaskLinkGrid" class="easyui-datagrid" data-options="fit:true,singleSelect:true,toolbar:'#_TaskLinkToolbar'">
            <thead>
              <tr>
                <th data-options="field:'id',width:100,editor:{type:'textbox',options:{onChange:ScheduleProgressGantt.onChangeLinkId}}">标识</th>
                <th data-options="field:'taskName',width:160">任务名称</th>
                <th
                  data-options="field:'linkType',width:150,formatter:ScheduleProgressGantt.formatterLinkType,editor:{type:'combobox',options:{onEndEdit:ScheduleProgressGantt.onChangeLinkType,editable:false,valueField:'id',textField:'text',data:ScheduleProgressGantt.linkTypes}}"
                >类型</th>
                <th data-options="field:'lagDays',width:100,formatter:ScheduleProgressGantt.formatterLagDays,editor:{type:'numberbox',options:{min:0,max:1000}}">延迟天数</th>
              </tr>
            </thead>
          </table>
          <div id="_TaskLinkToolbar">
            <table>
              <tbody>
                <tr>
                  <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:'true'" onclick="ScheduleProgressGantt.addLink();">增加</a></td>
                  <td><a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:'true'" onclick="ScheduleProgressGantt.deleteLink();">删除</a></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div title="高级" data-options="" style="padding: 10px">
          <form id="_TaskEditorAdvForm">
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">任务限制</h3>
              </div>
              <div class="panel-body">
                <table class="table">
                  <tr>
                    <td width="20%">限制类型</td>
                    <td><input name="constraintType" type="text" class="easyui-combobox"
                      data-options="width:160,editable:false,valueField:'id',textField:'text',data:ScheduleProgressGantt.constraintTypes,onChange:ScheduleProgressGantt.onChangeConstraintTypes"
                    /></td>
                    <td width="15%">限制日期</td>
                    <td><input name="constraintDate" type="text" class="easyui-datebox" data-options="width:160,editable:false,disabled:true" /></td>
                  </tr>
                  <tr>
                    <td>最后期限日</td>
                    <td><input name="deadlineDate" type="text" class="easyui-datebox" data-options="width:160,editable:false,buttons:ScheduleProgressGantt.deadlineButtons" /></td>
                  </tr>
                </table>
              </div>
            </div>
            <div class="panel panel-default">
              <div class="panel-heading">
                <h3 class="panel-title">任务类型</h3>
              </div>
              <div class="panel-body">
                <table class="table">
                  <tr>
                    <td width="5%"><input name="isSummary" type="checkbox" value="0" /></td>
                    <td>标记为摘要任务</td>
                  </tr>
                  <tr>
                    <td><input name="isMilestone" type="checkbox" value="0" /></td>
                    <td>标记为里程碑</td>
                  </tr>
                  <tr>
                    <td><input name="isCritical" type="checkbox" value="0" /></td>
                    <td>标记为关键任务(手动)</td>
                  </tr>
                  <tr>
                    <td><input name="isSiteTask" type="checkbox" value="0" /></td>
                    <td>标记为站点任务</td>
                  </tr>
                </table>
              </div>
            </div>
          </form>
        </div>
        <div title="备注" style="padding: 10px">
          <form id="_TaskEditorRemarkForm">
            <input name="remark" type="text" class="easyui-textbox" data-options="multiline:true" style="width: 100%; height: 380px;" />
          </form>
        </div>
      </div>
    </div>
    <div id="_TaskEditorDialogButtons">
      <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="ScheduleProgressGantt.saveGanttLightbox();"> <s:message code="ui_common_ok" />
        </a> <a href="javascript:void(0)" class="easyui-linkbutton" onclick="ScheduleProgressGantt.closeGanttLightbox();"> <s:message code="ui.common.cancel" />
        </a>
      </div>
    </div>
  </div>

  <div style="display: none;">
    <div id="_EditorReportProgressDialog" class="easyui-dialog" title="编辑" data-options="buttons:'#_EditorReportProgressDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
      style="width: 680px; height: 730px; padding: 0px;"
    >
      <form id="_EditorReportProgressForm" enctype="multipart/form-data">
        <input type="hidden" name="id"/>
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">任务信息</h3>
          </div>
          <div class="panel-body">
            <table class="table">
              <tr>
                <td width="20%">任务名称</td>
                <td colspan="3"><input name="taskName" type="text" class="easyui-textbox" data-options="width:495,readonly:true" /></td>
              </tr>
              <tr>
                <td width="20%">开始</td>
                <td><input name="startDate" type="text" class="easyui-textbox" data-options="width:160,readonly:true" /></td>
                <td width="20%">结束</td>
                <td><input name="endDate" type="text" class="easyui-textbox" data-options="width:160,readonly:true" /></td>
              </tr>
              <tr>
                <td>负责人</td>
                <td><input name="leader" type="text" class="easyui-combobox" data-options="width:160,readonly:true,valueField:'id',textField:'text',loadFilter:MainUtils.comboboxLoadFilter,url:'<s:url value="/schedule/plan-task/person-combo-list.json"/>'" /></td>
                <td>优先级</td>
                <td><input name="weight" type="text" class="easyui-textbox" data-options="width:160,readonly:true" /></td>
              </tr>
              <tr>
              	<td>参与人</td>
                <td colspan="3">
                	<input name="participantsName" type="text" style="height: 84px;" class="easyui-textbox" data-options="multiline:true,width:495,readonly:true" />
                </td>
              </tr>
              <tr>
                <td>任务类型</td>
                <td colspan='3'><input name="taskType" type="text" class="easyui-textbox" data-options="width:160,readonly:true" /></td>
              </tr>
              <tr>
              	<td>计划交付物</td>
                <td><input name="achievement" type="text" class="easyui-textbox" data-options="width:160,readonly:true" /></td>
                <td>实际交付物</td>
                <td><input name="delivery" type="text" class="easyui-textbox" data-options="width:160" /></td>
              </tr>
              <tr>
              	<td>附件列表</td>
              	<td colspan='3'>
              		<div id='attachments'></div>
              		<a class='easyui-linkbutton' href='#' id='attachment-upload-btn'>上传附件</a>
              		<input style="display: none" id="attachment-upload-input" type="file" name="attachmentUpload" multiple="multiple"></input>
              		<div style="margin-top: 8px" id='attachment-upload-file-name' ></div>
              	</td>
              </tr>
            </table>
          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">
            <h3 class="panel-title">任务进度</h3>
          </div>
          <div class="panel-body">
            <table class="table">
              <tr>
                <td width="20%">进度</td>
                <td><input name="progress" type="text" class="easyui-numberspinner" data-options="required:true,width:160,min:0,max:100" /></td>
                <td width="20%">上期进度</td>
                <td><input name="lastProgress" type="text" class="easyui-numberspinner" data-options="width:160,min:0,max:100,readonly:true" /></td>
              </tr>
              <tr>
              	<td>完成状态</td>
                <td colspan='3'><select name="flowStatus" class="easyui-combobox" data-options="required:true,width:160,valueField:'id',textField:'text',data:ScheduleProgressGantt.FlowStatusComboData,editable:false,panelMaxHeight:250,panelHeight:null"></select></td>
              </tr>
              <tr>
                <td width="20%">实际开始日期</td>
                <td><input name="actualStartDate" class="easyui-datebox" data-options="required:true,width:160" /></td>
                <td width="20%">实际完成日期</td>
                <td><input name="actualEndDate" class="easyui-datebox" data-options="required:false,width:160" /></td>
              </tr>
              <tr>
                <td>累计进展情况</td>
                <td colspan="3"><textarea name="cumulativeProgress" class="form-control easyui-textbox" style="height: 60px;" data-options="multiline:true,width:485,validType:{length:[1,255]}" ></textarea></td>
              </tr>
              <tr>
                <td>本期进展情况</td>
                <td colspan="3"><textarea name="currentWeekProgress" class="form-control easyui-textbox" style="height: 60px;" data-options="multiline:true,width:485,validType:{length:[1,255]}" ></textarea></td>
              </tr>
              <tr>
                <td>问题类型</td>
                <td colspan="3"><select name="issueType" class="easyui-combobox" data-options="width:485,valueField:'id',textField:'text',data:ScheduleProgressGantt.IssuesComboData,editable:false,panelMaxHeight:250,panelHeight:null"></select></td>
              </tr>
              <tr>
                <td>计划差异/偏离/问题描述</td>
                <td colspan="3"><textarea name="description" class="form-control easyui-textbox" style="height: 60px;" data-options="multiline:true,width:485,validType:{length:[1,255]}" ></textarea></td>
              </tr>
              <tr>
                <td>下期计划目标</td>
                <td colspan="3"><textarea name="nextWeekGoals" class="form-control easyui-textbox" style="height: 60px;" data-options="multiline:true,width:485,validType:{length:[1,255]}" ></textarea></td>
              </tr>
            </table>
          </div>
        </div>
      </form>

    </div>
    <div id="_EditorReportProgressDialogButtons">
      <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="ScheduleProgressGantt.saveEditReportProgressDataToDB('#_EditorReportProgressDialog');"> <s:message code="ui_common_ok" />
        </a> <a href="javascript:void(0)" class="easyui-linkbutton" onclick="EasyDialogUtils.closeFormDialog('#_EditorReportProgressDialog');"> <s:message code="ui.common.cancel" />
        </a>
      </div>
    </div>
  </div>

  <div style="display: none;">
    <div id="_ReportProgressDialog" class="easyui-dialog" title="生成简报"
      data-options="onOpen:ScheduleProgressGantt.onOpenReportProgressDialog,onClose:ScheduleProgressGantt.onCloseReportProgressDialog,buttons:'#_ReportProgressDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
      style="width: 880px; height: 650px; padding: 10px;"
    >
      <form id="_ReportProgressForm">
        <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px" data-options="onClickCell: onClickCell">
          <tr>
            <td width="12%">报告类型</td>
            <td width="38%"><select name="dateType" class="easyui-combobox" data-options="panelMinHeight:25,panelMaxHeight:250,panelHeight:null,width:300,editable:false">
                <option value="1" selected="selected">周报</option>
                <option value="2">月报</option>
                <option value="3">季报</option>
                <option value="4">半年报</option>
                <option value="5">年报</option>
            </select></td>
            <td width="12%">报告日期<strong class="text-danger">*</strong></td>
            <td width="38%"><input name="progressDate" type="text" class="easyui-datebox" data-options="required:true,width:300,editable:false" /></td>
          </tr>
          <tr>
            <td>标题<strong class="text-danger">*</strong></td>
            <td colspan="3"><input name="progressName" type="text" class="easyui-textbox" data-options="required:true,width:730,validType:{length:[1,36]}" /></td>
          </tr>
          <tr>
            <td colspan="4" style="height: 290px"><table id="_ReportProgressGrid" class="easyui-datagrid" title="任务进度" data-options="fit:true,singleSelect:true,buttons:null">
                <thead>
                  <tr>
                    <th data-options="field:'planTask_',hidden:true"></th>
                    <th data-options="field:'id',hidden:true"></th>
                    <th data-options="field:'mainTaskName',width:100">主任务</th>
                    <th data-options="field:'subTaskName',width:100">子任务</th>
                    <th
                      data-options="field:'lastProgress',width:80,align:'center',formatter:ScheduleProgressGantt.formatterProgressNumber"
                    >上期进度</th>
                    <th
                      data-options="field:'progress',width:80,align:'center',formatter:ScheduleProgressGantt.formatterProgressNumber,editor:{type:'numberbox',options:{required:true,min:1,max:100,suffix:'%'}}"
                    >进度</th>
                    <th data-options="field:'actualStartDate',width:120,align:'center',editor:{type:'datebox',options:{required:true,editable:false}}">实际开始日期</th>
                    <th data-options="field:'actualEndDate',width:120,align:'center',editor:{type:'datebox',options:{required:false,editable:false}}">实际完成日期</th>
                    <th
                      data-options="
                      			field:'flowStatus',
                      			width:100,
                      			align:'center',
                      			formatter:ScheduleProgressGantt.formatterReportProgressComboResource,
                      			editor:{
                      				type:'combobox',
                      				options:{
                      					required:true,
                      					editable:false,
                      					valueField:'id',
                      					textField: 'text',
                      					data:ScheduleProgressGantt.FlowStatusComboData,
                      					panelMaxHeight:250,
                      					panelHeight:null
                      				}
                      			}"
                    >完成状态</th>
                    <th data-options="field:'cumulativeProgress',width:110,align:'left',editor:{type:'textbox'}">累计进展情况</th>
                    <th data-options="field:'currentWeekProgress',width:110,align:'left',editor:{type:'textbox'}">本期进展情况</th>
                    <th
                      data-options="
                    			field:'issueType',
                    			width:150,
                    			align:'center',
                    			formatter:ScheduleProgressGantt.formatterIssuesComboResource,
                    			editor:{
                    				type:'combobox',
                    				options:{
                    					required:false,
                    					editable:false,
                    					valueField:'id',
                    					textField: 'text',
                    					data:ScheduleProgressGantt.IssuesComboData,
                    					panelMaxHeight:250,
                    					panelHeight:null
                    				}
                    			}"
                    >问题类型</th>
                    <th data-options="field:'description',width:180,align:'left',editor:{type:'textbox'}">计划差异/偏离/问题描述</th>
                    <th data-options="field:'nextWeekGoals',width:110,align:'left',editor:{type:'textbox'}">下期计划目标</th>
                  </tr>
                </thead>
              </table></td>
          </tr>
          <tr>
            <td>进展概述</td>
            <td colspan="3"><input name="description" type="text" class="easyui-textbox" data-options="multiline:true" style="width: 730px; height: 80px;" /></td>
          </tr>
          <tr>
            <td id='progressRemark'>专业成效</td>
            <td colspan="3"><input name="remark" type="text" class="easyui-textbox" data-options="multiline:true" style="width: 730px; height: 80px;" /></td>
          </tr>
        </table>


      </form>

    </div>
    <div id="_ReportProgressDialogButtons">
      <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="ScheduleProgressGantt.saveReportProgressData();">创建 </a> <a href="javascript:void(0)" class="easyui-linkbutton"
          onclick="EasyDialogUtils.closeFormDialog('#_ReportProgressDialog');"
        > <s:message code="ui.common.cancel" />
        </a>
      </div>
    </div>
  </div>
  
  <div style="display: none;">
    <div id="_GanttExcelParseDialog" closed="true" class="easyui-window" title="excel校验错误详情" style="width: 600px; height: 400px"
      data-options="iconCls:'icon-save',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9010,resizable:false"
    >
      <table id="_GanntExcelParseErrors"></table>
    </div>
    <div id="_ImportGanttExcelDialog" class="easyui-dialog" title="导入" data-options="buttons:'#_ImportGanttExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
      style="width: 500px; height: 200px; padding: 10px;"
    >
      <form id="_ImportGanttExcelDialogForm">
        <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
          <tr>
            <td width="20%">导入文件：</td>
            <td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
          </tr>
        </table>
      </form>
    </div>
    <div id="_ImportGanttExcelDialogButtons">
      <div>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="ScheduleProgressGantt.saveImportExcelData();"><s:message code="ui_common_ok" /> </a> <a href="javascript:void(0)"
          class="easyui-linkbutton" onclick="EasyDialogUtils.closeFormDialog('#_ImportGanttExcelDialog');"
        > <s:message code="ui.common.cancel" />
        </a>
      </div>
    </div>
  </div>
  <div style="display: none;"><div id="_ProjectExcelDialog" class="easyui-dialog" title="导入"' 
  data-options="buttons:'#_ProjectExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove" 
  style="width: 500px; height: 200px; padding: 10px;"><form id="_ProjectExcelDialogForm">
<table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
<tr><td width="20%">导入文件：</td><td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
</tr></table></form></div><div id="_ProjectExcelDialogButtons"><div>
<a href="javascript:void(0)" class="easyui-linkbutton" onclick="ScheduleProgressGantt.saveProcessImportExcelData();">确认</a>
<a href="javascript:void(0)" class="easyui-linkbutton" onclick="EasyDialogUtils.closeFormDialog('#_ProjectExcelDialog');">退出</a></div></div></div>

  <div class="hidden" id="ContentScriptWrapper">
    <jsp:include page="/WEB-INF/jsp/common/include/commonJsVar.jsp" flush="true" />
    <jsp:include page="/WEB-INF/jsp/common/include/downloadViaIframe.jsp" flush="true" />
    <script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/easyui/jquery.easyui-1.9.2.min.js"/>"></script>
    <s:eval var="langEasyui" expression="T(project.edge.domain.business.SystemConfigSettings).instance.langEasyui" />
    <script type="text/javascript" src="<s:url value="/js/util/easyui/locale/easyui-lang-${langEasyui}.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/form/jquery.form.min.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/main.js"/>"></script>

    <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/dhtmlxgantt.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/ext/dhtmlxgantt_multiselect.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/ext/dhtmlxgantt_tooltip.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/ext/dhtmlxgantt_auto_scheduling.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/ext/dhtmlxgantt_fullscreen.js"/>"></script>
    <%-- <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/ext/dhtmlxgantt_keyboard_navigation.js"/>"></script> --%>
    <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/ext/dhtmlxgantt_critical_path.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/ext/dhtmlxgantt_marker.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/dhtmlxgantt/locale/locale_cn.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/util/easyui/datagrid-cellediting.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/schedule/progress.custom.editor.js"/>"></script>
    <script type="text/javascript" src="<s:url value="/js/schedule/schedule.progress.gantt.js"/>"></script>

    <script type="text/javascript">
        $(document).ready(function() {
        	var ganttData = ${ganttData};
        	var ganttIsModify = ${ganttIsModify};
            ScheduleProgressGantt.handlerInit(ganttData,ganttIsModify);
            console.log(ganttData);

            var selectedId = '${selectedTask}';
            if (selectedId) {
            	console.log(selectedId);
            	setTimeout(function(){
	                $(".gantt_row[task_id='" + selectedId + "']").find("input[type='checkbox']").prop("checked",true);
	                $(".gantt_row[task_id='" + selectedId + "']").css({"background":"#fff3a1"});
            	},200);
            }
            var isModify = ${ganttIsModify};
            var title = "";
            if( isModify == 1 || isModify == 4){
                //$('title').text('编制任务');
                title = '编制任务${plantaskTitleExtend}';
            }else if(isModify == 2){
                //$('title').text('进度跟踪');
            	title = '进度跟踪${plantaskTitleExtend}';
            }else if(isModify == 3){
                //$('title').text('查看进度预警');
                title = '查看进度预警${plantaskTitleExtend}';
            }else if(isModify == 6){
                //$('title').text('查看进度预警');
                title = '比较任务${plantaskTitleExtend}';
            }else {
                //$('title').text('查看任务');
                title = '查看任务${plantaskTitleExtend}';
            }
            $('title').text(title);
        });
        
        function onClickCell(index, field){
        	console.log(field)
        }
	</script>
  </div>
</body>
</html>
<div style="display: none;">
  <div id="_SegmentExcelParseDialog" closed="true" class="easyui-window" title="excel校验错误详情" style="width:600px;height:400px"
    data-options="iconCls:'icon-save',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9010,resizable:false">
    <table id="_SegmentExcelParseErrors"></table>
  </div>
  <div id="_SegmentExcelDialog" class="easyui-dialog" title="导入" data-options="buttons:'#_SegmentExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 200px; padding: 10px;"
  >
    <form id="_SegmentExcelDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
        <tr>
          <td width="20%">导入文件：</td>
          <td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="_SegmentExcelDialogButtons">
    <div>
      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SEGMENT.saveImportExcelData();"><s:message code="ui_common_ok" /> </a> <a href="javascript:void(0)" class="easyui-linkbutton"
        onclick="EasyDialogUtils.closeFormDialog('#_SegmentExcelDialog');"
      > <s:message code="ui_common_quit" />
      </a>
    </div>
  </div>
</div>