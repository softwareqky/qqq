<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />
<%-- 上传文件弹出画面 --%>
<s:message var="addFileDialogTitle" code="ui.common.upload.file" />
<g:addDialog dialogId="${idMap['add-file-dialog']}" continuousAddSubmitJs="${jsMap['con-add-file-submit']}" addSubmitJs="${jsMap['add-file-submit']}" width="420" maxHeight="480" hasCopyReserve="true"
  dialogTitle="${addFileDialogTitle}" dialogIconClass="dialog-icon-fa fa fa-fw fa-upload"
>
  <g:addEditFormSimpleLayout formFields="${addFileFields}" submitUrl="${urlMap['add-file']}" idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
</g:addDialog>

<%-- 新建文件夹弹出画面 --%>
<s:message var="addFolderDialogTitle" code="ui.common.add.folder" />
<g:addDialog dialogId="${idMap['add-folder-dialog']}" continuousAddSubmitJs="${jsMap['continuous-add-submit']}" addSubmitJs="${jsMap['add-submit']}" width="420" maxHeight="480" hasCopyReserve="true"
  dialogTitle="${addFolderDialogTitle}" dialogIconClass="dialog-icon-fa fa fa-fw fa-folder-o"
>
  <g:addEditFormSimpleLayout formFields="${addFolderFields}" submitUrl="${urlMap['add-folder']}" idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
</g:addDialog>

<%-- 修改信息弹出画面 --%>
<g:editDialog dialogId="${idMap['edit-form-dialog']}" editSubmitJs="${jsMap['edit-submit']}" width="420" maxHeight="480" dialogIconClass="dialog-icon-fa fa fa-fw fa-pencil-square-o">
  <g:addEditFormSimpleLayout formFields="${editInfoFields}" submitUrl="${urlMap['edit']}" idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
</g:editDialog>


<%-- 复制到弹出画面 --%>

<%-- 移动到弹出画面 --%>

<%-- 工具菜单 --%>
<div id="${idPrefix}-KnowledgeBaseNameToolMenu" class="easyui-menu" data-options="onClick:KNOWLEDGE_BASE.onNameToolMenuClick">
  <div data-options="name:'delete',iconCls:'fa fa-fw fa-trash-o'">
    <s:message code="ui.common.delete" />
  </div>
  <div data-options="name:'edit'">
    <s:message code="ui.common.modify.info" />
  </div>
</div>

<%-- 分配权限画面 --%>
<s:message var="authDialogTitle" code="ui.common.authority" />
<div class="hidden">
  <div id="${idMap['auth-dialog']}" class="easyui-dialog"
    data-options="title:'${authDialogTitle}',closed:true,iconCls:'dialog-icon-fa fa fa-fw fa-lock',height:560,width:980,modal:true,shadow:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
  ></div>
</div>