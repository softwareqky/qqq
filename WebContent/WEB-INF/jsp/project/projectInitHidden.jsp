<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-建议文件上传 --%>
<c:if test="${not empty proposalFields}">
  <s:message var="proposalTitle" code="ui.label.upload.proposal.file" />
  <g:editDialog dialogId="${idMap['edit-proposal-form-dialog']}" width="520" editSubmitJs="${jsMap['edit-proposal-submit']}" maxHeight="200" dialogTitle="${proposalTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${proposalFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=proposal" />
  </g:editDialog>
</c:if>

<%-- 修改弹出画面-可研文件上传 --%>
<c:if test="${not empty feasibilityFields}">
  <s:message var="feasibilityTitle" code="ui.label.upload.feasibility.file" />
  <g:editDialog dialogId="${idMap['edit-feasibility-form-dialog']}" width="520" editSubmitJs="${jsMap['edit-feasibility-submit']}" maxHeight="480" dialogTitle="${feasibilityTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${feasibilityFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=feasibility" />
  </g:editDialog>
</c:if>

<%-- 修改弹出画面-初设文件上传 --%>
<c:if test="${not empty preliminaryDesignFields}">
  <s:message var="preliminaryDesignTitle" code="ui.label.upload.preliminary.design.file" />
  <g:editDialog dialogId="${idMap['edit-preliminary-design-form-dialog']}" width="520" editSubmitJs="${jsMap['edit-preliminary-design-submit']}" maxHeight="200" dialogTitle="${preliminaryDesignTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${preliminaryDesignFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=preliminaryDesign" />
  </g:editDialog>
</c:if>

<div style="display: none;">
  <div id="_ProjectExcelDialog" class="easyui-dialog" title="导入" data-options="buttons:'#_ProjectExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 200px; padding: 10px;"
  >
    <form id="_ProjectExcelDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
        <tr>
          <td width="20%">导入文件：</td>
          <td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="_ProjectExcelDialogButtons">
    <div>
      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="PROJECT_INIT.saveImportExcelData();"><s:message code="ui_common_ok" /> </a> <a href="javascript:void(0)" class="easyui-linkbutton"
        onclick="EasyDialogUtils.closeFormDialog('#_ProjectExcelDialog');"
      > <s:message code="ui_common_quit" />
      </a>
    </div>
  </div>
</div>