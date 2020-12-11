<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<div style="display: none;">
  <div id="_SegmentExcelParseDialog" closed="true" class="easyui-window" title="excel校验错误详情" style="width:600px;height:400px"
    data-options="iconCls:'icon-save',modal:true,collapsible:false,minimizable:false,maximizable:false,zIndex:9010,resizable:false">
    <table id="_SegmentExcelParseErrors"></table>
  </div>
  <div id="_SegmentExcelDialog" class="easyui-dialog" title="导入" data-options="buttons:'#_SegmentExcelDialogButtons',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"
    style="width: 500px; height: 200px; padding: 10px;"
  >
    <form id="_SegmentExcelDialogForm">
      <table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">
        <tr>
          <td width="20%">导入文件：</td>
          <td width="75%"><input name="importFile" type="text" class="easyui-filebox" data-options="accept:'.xls,.xlsx',width:300,height:25,buttonText:'选择',required:true" /></td>
        </tr>
      </table>
    </form>
  </div>
  <div id="_SegmentExcelDialogButtons">
    <div>
      <a href="javascript:void(0)" class="easyui-linkbutton" onclick="SEGMENT.saveImportExcelData();"><s:message code="ui_common_ok" /> </a> <a href="javascript:void(0)" class="easyui-linkbutton"
        onclick="EasyDialogUtils.closeFormDialog('#_SegmentExcelDialog');"
      > <s:message code="ui_common_quit" />
      </a>
    </div>
  </div>
</div>


