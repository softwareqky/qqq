<%@tag description="基于EasyUI布局的单grid，支持自动适应窗体大小变化" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>


<div class="easyui-layout" data-options="fit:true">

  <div data-options="region:'north',border:false,height:'auto',height:240" style="">
    <table id="_AuditLogDatagrid" class="easyui-datagrid"
      data-options="url:'',
                multiSort:false,sortName:'',sortOrder:'asc',
                singleSelect:true,pagination:false,rownumbers:true,pageSize:30,pageList:[30,50],idField:'id',showFooter:true,
                emptyMsg:'<s:message code="message.info.no.record"/>',
                autoRowHeight:true,nowrap:true,striped:true,fit:true,fitColumns:true,
                loadFilter:MainUtils.datagridLoadFilter,onLoadSuccess:MainUtils.handleDatagridLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError"
    >
      <thead>
        <tr>
          <th data-options="field:'nodeName',width:100,align:'center'"><s:message code="ui.fields.flowable.setting.node.name" /></th>
          <th data-options="field:'auditRemark',width:200,align:'center'"><s:message code="ui.fields.flowable.monitor.pending.audit.opinion" /></th>
          <th data-options="field:'auditPersonText',width:100,align:'center'"><s:message code="ui.fields.flowable.setting.node.audit.person" /></th>
          <th data-options="field:'auditResultText',width:100,align:'center'"><s:message code="ui.fields.flowable.monitor.pending.audit.result" /></th>
          <th data-options="field:'auditDatetimeText',width:100,align:'center'"><s:message code="ui.fields.flowable.monitor.pending.audit.datetime" /></th>
        </tr>
      </thead>
    </table>
  </div>

  <div data-options="region:'center',border:true">
    <div id="_AuditLogFlowableView"></div>
  </div>
</div>
