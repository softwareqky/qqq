<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

<%-- 修改弹出画面-站点文件上传 --%>
<c:if test="${not empty siteFields}">
  <s:message var="siteTitle" code="ui.label.upload.site.file" />
  <g:editDialog dialogId="${idMap['edit-site-form-dialog']}" width="520" editSubmitJs="${jsMap['edit-site-submit']}" maxHeight="200" dialogTitle="${siteTitle}"
    dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
  >
    <g:addEditFormSimpleLayout formFields="${siteFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}?uploadType=site" />
  </g:editDialog>
</c:if>

<div style="display: none;">
  <div id="_SiteExcelParseDialog" closed="true" class="easyui-window" title="excel校验错误详情" style="width:600px;height:400px"
    data-options="iconCls:'icon-save',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9010,resizable:false">
    <table id="_SiteExcelParseErrors"></table>
  </div>
  <div id="_SiteExcelDialog" class="easyui-dialog" title="导入" data-options="buttons:'#_SiteExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 200px; padding: 10px;"
  >
    <form id="_SiteExcelDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
        <tr>
          <td width="20%">导入文件：</td>
          <td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="_SiteExcelDialogButtons">
    <div>
      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SITE.saveImportExcelData();"><s:message code="ui_common_ok" /> </a> <a href="javascript:void(0)" class="easyui-linkbutton"
        onclick="EasyDialogUtils.closeFormDialog('#_SiteExcelDialog');"
      > <s:message code="ui_common_quit" />
      </a>
    </div>
  </div>
</div>




