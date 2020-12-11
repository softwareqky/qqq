<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 提交审核弹出画面 --%>
<%-- TODO 与auditSubmitDialog.jsp合并 --%>

<div class="hidden">
  <div id="${idMap['extend-page-pre']}-AuditSubmitDialog" class="easyui-dialog"
    data-options="title:'提交审核',closed:true,buttons:'#${idMap['extend-page-pre']}-AuditSubmitDialogButtons',iconCls:'icon-func-audit-submit',maxHeight:520,width:650,modal:true,onMove:EasyDialogUtils.onDialogMove"
  >
    <div class="container-fluid">

      <div class="row">
        <div class="col-xs-6 col-xs-offset-3">
          <div class="center-block" style="width: 40%;">
            <h5>
              <strong>流程启动选择</strong>
            </h5>
          </div>
        </div>
      </div>

      <div class="row row-gapped-both-s">
        <div class="col-xs-12 col-gapped-s">
          <table style="width: 100%; border: 1px solid #ccc;">
            <tr>
              <td width="25%" style="padding: 8px; border: 1px solid #ccc;">选择流程模板</td>
              <td width="75%" style="padding: 8px; border: 1px solid #ccc;">
                <select class="easyui-combobox" data-options="height:32,width:400,panelMinHeight:32,panelMaxHeight:320,panelHeight:null,editable:false">
                  <option value="1">项目立项审核流程</option>
                </select>
              </td>
            </tr>
            <tr>
              <td style="padding: 8px; border: 1px solid #ccc;">备注</td>
              <td style="padding: 8px; border: 1px solid #ccc;"></td>
            </tr>
            <tr>
              <td style="padding: 8px; border: 1px solid #ccc;">审核内容</td>
              <td style="padding: 8px; border: 1px solid #ccc;">
                <input type="text" class="easyui-textbox" data-options="width:400,multiline:true,height:96,value:'项目立项审核—b939f306-1ff4-40a2-bb63-141a6bb66763'" />
              </td>
            </tr>
            <tr>
              <td style="padding: 8px; border: 1px solid #ccc;">选择接收人</td>
              <td style="padding: 8px; border: 1px solid #ccc;">
                <input type="text" class="easyui-textbox" data-options="width:400,height:32,buttonText:'...'" />
              </td>
            </tr>
          </table>
        </div>
      </div>

      <div class="row">
        <div class="col-xs-6 col-xs-offset-3">
          <div class="center-block" style="width: 45%;">
            <h5>
              <strong>工作流程图显示</strong>
            </h5>
          </div>
        </div>
      </div>

      <div class="row row-gapped-both-s" style="border-top: 1px solid #ccc;">
        <div class="col-xs-12">
          <img src="<s:url value="/img/process-flow.png"/>" class="img-responsive" alt="Responsive image" />
        </div>
      </div>

      <div class="row row-gapped-both-s" style="border-top: 1px solid #ccc;">
        <div class="col-xs-12" style="height: 20px;">&nbsp;</div>
      </div>

    </div>
    <!-- /.container-fluid -->

  </div>
  <%-- 提交审核弹出画面的按钮 --%>
  <div id="${idMap['extend-page-pre']}-AuditSubmitDialogButtons">
    <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${idMap['extend-page-pre']}-AuditSubmitDialog');">
      <s:message code="ui_common_ok" />
    </a>
    <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${idMap['extend-page-pre']}-AuditSubmitDialog');">
      <s:message code="ui_common_quit" />
    </a>
  </div>

</div>