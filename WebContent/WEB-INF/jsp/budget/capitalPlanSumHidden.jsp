<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-资金计划总表文件上传 --%>
<c:if test="${not empty siteFields}">
  <s:message var="capitalPlanSumTitle" code="ui.label.upload.capital_plan_sum.file" />
  <g:editDialog dialogId="${idMap['edit-capitalPlanSum-form-dialog']}" width="520" editSubmitJs="${jsMap['edit-site-submit']}" maxHeight="200" dialogTitle="${capitalPlanSumTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${capitalPlanSumFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=capitalPlanSum" />
  </g:editDialog>
</c:if>

<div style="display: none;">
  <div id="_PlanSumExcelDialog" class="easyui-dialog" title="导入" data-options="buttons:'#_PlanSumExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 250px; padding: 10px;">
    <form id="_PlanSumExcelDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
        <tr>
          <td width="20%">导入文件：</td>
          <td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="_PlanSumExcelDialogButtons">
    <div>
      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="CAPITALPLANSUM.saveImportExcelData();"><s:message code="ui_common_ok" /> </a> <a href="javascript:void(0)" class="easyui-linkbutton"
        onclick="EasyDialogUtils.closeFormDialog('#_PlanSumExcelDialog');"
      > <s:message code="ui_common_quit" />
      </a>
    </div>
  </div>
</div>
