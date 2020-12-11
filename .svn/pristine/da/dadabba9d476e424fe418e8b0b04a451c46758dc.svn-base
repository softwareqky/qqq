<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<div style="display: none;">
  <div id="_FlowableSettingProjectRoleDialog" class="easyui-dialog" title="<s:message code="ui.fields.flowable.setting.role.permission" />"
    data-options="buttons:'#_FlowableSettingProjectRoleDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove" style="width: 500px; height: 600px; padding: 10px;"
  >
    <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false" style="padding: 6px 5px 5px 5px;">
        <ul id="_FlowableSettingRolePermissionTree" class="easyui-tree" data-options="checkbox:true,loadFilter:MainUtils.treeLoadFilter,onLoadError:MainUtils.handleDatagridLoadError"></ul>
      </div>
    </div>
  </div>

  <div id="_FlowableSettingProjectRoleDialogButtons">
    <div>
      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="FLOWABLE_SETTING.saveRolePermissionData('${idMap['main-datagrid']}');"><s:message code="ui_common_ok" /> </a> <a
        href="javascript:void(0)" class="easyui-linkbutton" onclick="EasyDialogUtils.closeFormDialog('#_FlowableSettingProjectRoleDialog');"
      > <s:message code="ui_common_quit" />
      </a>
    </div>
  </div>
</div>

<div style="display: none;">
  <div id="_SingleAuditNodeDialog" class="easyui-dialog"
    data-options="title:'<s:message
                      code="ui.fields.flowable.setting.single.audit.node"
                    />',closed:true,buttons:'#_SingleAuditNodeDialogButtons',iconCls:'',height:300,
            maxHeight:500,width:470,modal:true,shadow:false,resizable:false,onMove:EasyDialogUtils.onDialogMove"
  >
    <form>
      <table class="table">
        <tr>
          <td width="20%"><s:message code="ui.fields.flowable.setting.node.name" /><strong class="text-danger">*</strong></td>
          <td width="80%"><input name="flowNodeName" type="text" class="form-control ez-like easyui-validatebox validatebox-text validatebox-readonly" style="width: 300px;"
            data-field-widget="textbox" data-copy-reserve="" data-options="required:true,validType:{length:[1,100]}"
          ></td>
        </tr>
        <tr>
          <td><s:message code="ui.fields.flowable.setting.node.audit.person" /><strong class="text-danger">*</strong></td>
          <td>
            <div class="input-group" style="width: 235px; vertical-align: middle;">
              <input name="personText" type="text" class="form-control ez-like easyui-validatebox validatebox-text validatebox-readonly" style="width: 260px;" data-field-widget="textbox"
                data-copy-reserve="" readonly="" data-options="required:true"
              > <input name="person_" type="hidden" data-field-widget="textbox" data-copy-reserve="" value=""> <span class="input-group-btn" style="width: 40px">
                <button class="btn btn-default" style="height: 32px; width: 40px" type="button" onclick="PopupSelectUtils.openPersonSelect(this)">…</button>
              </span>
            </div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div id="_SingleAuditNodeDialogButtons">
    <div id="_SingleAuditNodeDialogButtons_add">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="FLOWABLE_UI_UTILS.saveSingleAuditPersonNode('#_SingleAuditNodeDialog',true);"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#_SingleAuditNodeDialog');"> <s:message code="ui.common.quit" />
      </a>
    </div>
    <div id="_SingleAuditNodeDialogButtons_edit">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="FLOWABLE_UI_UTILS.saveSingleAuditPersonNode('#_SingleAuditNodeDialog',false);"> <s:message
          code="ui.common.ok"
        />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#_SingleAuditNodeDialog');"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
</div>

<div style="display: none;">
  <div id="_RoleAuditNodeDialog" class="easyui-dialog"
    data-options="title:'<s:message code="ui.datatype.project.role"/>',closed:true,buttons:'#_RoleAuditNodeDialogButtons',iconCls:'',height:300,
            maxHeight:500,width:470,modal:true,shadow:false,resizable:false,onMove:EasyDialogUtils.onDialogMove"
  >
    <form>
      <table class="table">
        <tr>
          <td width="20%"><s:message code="ui.fields.flowable.setting.node.name" /><strong class="text-danger">*</strong></td>
          <td width="80%"><input name="flowNodeName" type="text" class="form-control ez-like easyui-validatebox validatebox-text validatebox-readonly" style="width: 300px;"
            data-field-widget="textbox" data-copy-reserve="" data-options="required:true,validType:{length:[1,100]}"
          ></td>
        </tr>
        <tr>
          <td><s:message code="ui.datatype.project.role" /><strong class="text-danger">*</strong></td>
          <td>
            <div class="input-group" style="width: 235px; vertical-align: middle;">
              <input name="projectRoleText" type="text" class="form-control ez-like easyui-validatebox validatebox-text validatebox-readonly" style="width: 260px;" data-field-widget="textbox"
                data-copy-reserve="" readonly="" data-options="required:true"
              > <input name="projectRole_" type="hidden" data-field-widget="textbox" data-copy-reserve="" value=""> <span class="input-group-btn" style="width: 40px">
                <button class="btn btn-default" style="height: 32px; width: 40px" type="button" onclick="PopupSelectUtils.openProjectRoleSelect(this)">…</button>
              </span>
            </div>
          </td>
        </tr>
      </table>
    </form>
  </div>

  <div id="_RoleAuditNodeDialogButtons">
    <div id="_RoleAuditNodeDialogButtons_add">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="FLOWABLE_UI_UTILS.saveProjectRoleAuditNode('#_RoleAuditNodeDialog',true);"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#_RoleAuditNodeDialog');"> <s:message code="ui.common.quit" />
      </a>
    </div>
    <div id="_RoleAuditNodeDialogButtons_edit">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="FLOWABLE_UI_UTILS.saveProjectRoleAuditNode('#_RoleAuditNodeDialog',false);"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#_RoleAuditNodeDialog');"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
</div>
