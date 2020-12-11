<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

 
<!-- 导出总表 -->
<div style="display: none;"> 
  <div id="_BudgetversionExcelExportDialog" class="easyui-dialog" title="导出" data-options="buttons:'#_BudgetversionExcelExportDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 200px; padding: 10px;"
  >
    <form id="_BudgetversionExcelExportDialogForm">
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
          <td width="20%">选择年份：</td>
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
      </table>
    </form>
  </div>
  <div id="_BudgetversionExcelExportDialogButtons" style="text-align:right">
    	<div>
      		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="BUDGETVERSION.exportVersionData();"><s:message code="ui_common_ok" /> </a> 
      		<a href="javascript:void(0)" class="easyui-linkbutton"
        	onclick="EasyDialogUtils.closeFormDialog('#_BudgetversionExcelExportDialog');"> <s:message code="ui_common_quit" /></a>
    	</div>
  </div>
</div>
