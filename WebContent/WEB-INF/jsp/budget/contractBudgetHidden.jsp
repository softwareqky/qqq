<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-预算规划总表文件上传 --%>
<c:if test="${not empty budgetFields}">
  <s:message var="budgetsumTitle" code="ui.label.upload.budget_estimate_sum.file" />
  <g:editDialog dialogId="${idMap['edit-site-form-dialog']}" width="520" editSubmitJs="${jsMap['edit-site-submit']}" maxHeight="200" dialogTitle="${budgetTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${budgetFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=site" />
  </g:editDialog>
</c:if>

<div style="display: none;">
  <div id="_ContractBudgetExcelDialog" class="easyui-dialog" title="导入" data-options="buttons:'#_ContractBudgetExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 250px; padding: 10px;"
  >
    <form id="_ContractBudgetExcelDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
        <tr>
          <td width="20%">选择合同：</td>
		  <td width="75%">
			<select name="contract" id="contract" class="form-control ez-like" data-field-widget="combobox" data-field-control="" style="width:140px;vertical-align:middle;display:inline-block;">
		          <option value="">&nbsp;</option>

		    </select>
		  </td>
        </tr>
        <tr>
          <td width="20%">导入文件：</td>
          <td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="_ContractBudgetExcelDialogButtons">
    <div>
      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="CONTRACTBUDGET.saveImportExcelData();"><s:message code="ui_common_ok" /> </a> <a href="javascript:void(0)" class="easyui-linkbutton"
        onclick="EasyDialogUtils.closeFormDialog('#_ContractBudgetExcelDialog');"
      > <s:message code="ui_common_quit" />
      </a>
    </div>
  </div>
</div>

