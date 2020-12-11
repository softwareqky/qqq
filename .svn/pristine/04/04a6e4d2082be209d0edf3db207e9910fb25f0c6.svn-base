<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-预算规划总表文件上传 --%>
<c:if test="${not empty budgetsumFields}">
  <s:message var="budgetsumTitle" code="ui.label.upload.budget_estimate_sum.file" />
  <g:editDialog dialogId="${idMap['edit-site-form-dialog']}" width="520" editSubmitJs="${jsMap['edit-site-submit']}" maxHeight="200" dialogTitle="${budgetsumTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${budgetsumFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=site" />
  </g:editDialog>
</c:if>

<!-- 导入 -->
<div style="display: none;">
  <div id="_BudgetsumExcelDialog" class="easyui-dialog" title="导入" data-options="buttons:'#_BudgetsumExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 200px; padding: 10px;"
  >
    <form id="_BudgetsumExcelDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
<%--         <tr>
          <td width="20%">选择项目：</td>
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
        <tr>
          <td width="20%">导入文件：</td>
          <td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="_BudgetsumExcelDialogButtons">
    <div>
      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="BUDGETSUM.saveImportExcelData();"><s:message code="ui_common_ok" /> </a> <a href="javascript:void(0)" class="easyui-linkbutton"
        onclick="EasyDialogUtils.closeFormDialog('#_BudgetsumExcelDialog');"
      > <s:message code="ui_common_quit" />
      </a>
    </div>
  </div>
</div>
  
  
<!-- 导出总表 -->
<div style="display: none;"> 
  <div id="_BudgetsumExcelExportDialog" class="easyui-dialog" title="导出" data-options="buttons:'#_BudgetsumExcelExportDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 200px; padding: 10px;"
  >
    <form id="_BudgetsumExcelExportDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
        <tr>
        	<td width="20%">导出为：</td>
        	<td>
        		<select id="_BudgetsumExcelExportCombobox" class="easyui-combobox" editable="false" panelHeight="auto" name="dept" style="width:200px;">
    				<option value="1">分专业概算总表</option>
    				<option value="2">分专业概算明细表</option>
				</select>
        	</td>
        </tr>
<%--         <tr>
          <td width="20%">选择项目：</td>
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
  <div id="_BudgetsumExcelExportDialogButtons" style="text-align:right">
    	<div>
      		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="BUDGETSUM.exportSumData();"><s:message code="ui_common_ok" /> </a> 
      		<a href="javascript:void(0)" class="easyui-linkbutton"
        	onclick="EasyDialogUtils.closeFormDialog('#_BudgetsumExcelExportDialog');"> <s:message code="ui_common_quit" /></a>
    	</div>
  </div>
</div>



