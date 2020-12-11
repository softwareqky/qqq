<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />


<!-- 导出总表 -->
<div style="display: none;"> 
  <div id="_BudgetExcelExportDialog" class="easyui-dialog" title="导出" data-options="buttons:'#_BudgetExcelExportDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 260px; padding: 10px;"
  >
    <form id="_BudgetExcelExportDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
        <tr>
          <td width="30%">选择年份：</td>
		  <td width="75%">
		  <div class="input-group" style="width:235px;vertical-align:middle;">
                <select class="form-control ez-like" name="year" data-field-widget="combobox" data-field-control="" style="width: 161px; vertical-align: middle; display: inline-block;">
                    <option value="">&nbsp;</option>
                    <option value="2019">2019</option>
                    <option value="2020">2020</option>
                    <option value="2021">2021</option>
                    <option value="2022">2022</option>
                    <option value="2023">2023</option>
                </select>
		  </div>
		  </td>
        </tr>
        <tr>
          <td width="30%">选择时间段：</td>
		  <td width="75%">
		  	<input class="easyui-datebox" id="startDate" name="startDate" style="width: 120px;" ></input> -
		  	<input class="easyui-datebox" id="endDate" name="endDate" style="width: 120px;" ></input> 
		  </td>
        </tr>
<%--         <tr>
          <td width="30%">选择项目：</td>
		  <td width="75%">
		  <div class="input-group" style="width:235px;vertical-align:middle;">
		      <input name="projectText" type="text" class="form-control ez-like easyui-validatebox validatebox-text validatebox-readonly" style="width:195px;" data-field-widget="textbox" data-copy-reserve="" readonly="" data-options="required:true">
		      <input name="project_" type="hidden" data-field-widget="textbox" data-copy-reserve="" value="">
		      <span class="input-group-btn" style="width: 40px">
		        <button class="btn btn-default" style="height: 32px; width: 40px" type="button" onclick="PopupSelectUtils.openProjectSelect(this)">…</button>
		      </span>
		  </div>
		  </td>
        </tr> --%>
      </table>
    </form>
  </div>
  <div id="_BudgetExcelExportDialogButtons" style="text-align:right">
    	<div>
      		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="BUDGETFINAL.exportData();"><s:message code="ui_common_ok" /> </a> 
      		<a href="javascript:void(0)" class="easyui-linkbutton"
        	onclick="EasyDialogUtils.closeFormDialog('#_BudgetExcelExportDialog');"> <s:message code="ui_common_quit" /></a>
    	</div>
  </div>
</div>

<div class="hidden">
    <div id="PurchaseOrderListDialog" class="easyui-dialog" data-id-field="id" data-text-field="archiveName"
      data-options="title:'<s:message code="ui.common.audit.log"/>',closed:true,buttons:'#OrderListDialogButtons',iconCls:'icon-func-audit-log',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <div class="easyui-layout" data-options="fit:true">

		  <div data-options="region:'north',border:false,height:'auto',height:460" style="">
		    <table id="_OrderListDatagrid" class="easyui-datagrid"
		      data-options="url:'',
		                multiSort:false,sortName:'',sortOrder:'asc',
		                singleSelect:true,pagination:false,rownumbers:true,pageSize:30,pageList:[30,50],idField:'id',showFooter:true,
		                emptyMsg:'<s:message code="message.info.no.record"/>',
		                autoRowHeight:true,nowrap:true,striped:true,fit:true,fitColumns:true,
		                loadFilter:MainUtils.datagridLoadFilter,onLoadSuccess:MainUtils.handleDatagridLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError"
		    >
		      <thead>
		        <tr>
		          <th data-options="field:'mainid',width:150,align:'center'"><s:message code="ui.fields.budget_fee.flowid" /></th>
		          <th data-options="field:'payAmount',width:150,align:'center'"><s:message code="ui.fields.budget_fee.payAmount" /></th>
		          <th data-options="field:'payRatio',width:150,align:'center'"><s:message code="ui.fields.budget_fee.payRatio" /></th>
		          <th data-options="field:'purchaseNo',width:150,align:'center'"><s:message code="ui.fields.budget_fee.purchaseId" /></th>
		          <th data-options="field:'requestPersonText',width:100,align:'center'"><s:message code="ui.fields.budget_fee.lkr" /></th>
		          <th data-options="field:'requestTime',width:150,align:'center'"><s:message code="ui.fields.budget_fee.creator.datetime" /></th>
		          <th data-options="field:'totalPrice',width:150,align:'center'"><s:message code="ui.fields.budget_fee.totalPrice" /></th>
		          <th data-options="field:'virtualOrgText',width:150,align:'center'"><s:message code="ui.fields.budget_fee.gzz" /></th>
		          <th data-options="field:'workflowName',width:150,align:'center'"><s:message code="ui.fields.flowable.setting.name" /></th>
		        </tr>
		      </thead>
		    </table>
		  </div>
		</div>
    </div>
  </div>
