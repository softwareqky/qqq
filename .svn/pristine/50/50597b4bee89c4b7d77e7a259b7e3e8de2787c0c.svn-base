<%@tag description="基于EasyUI布局的单grid，支持自动适应窗体大小变化" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>


<div class="easyui-layout" data-options="fit:true">

  <div data-options="region:'north',border:false,height:'auto',height:460" style="">
    <table id="_AuditLogOaDatagrid" class="easyui-datagrid"
      data-options="url:'',
                multiSort:false,sortName:'',sortOrder:'asc',
                singleSelect:true,pagination:false,rownumbers:true,pageSize:30,pageList:[30,50],idField:'id',showFooter:true,
                emptyMsg:'<s:message code="message.info.no.record"/>',
                autoRowHeight:true,nowrap:true,striped:true,fit:true,fitColumns:true,
                loadFilter:MainUtils.datagridLoadFilter,onLoadSuccess:MainUtils.handleDatagridLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError"
    >
      <thead>
        <tr>
          <th data-options="field:'creator',width:200,align:'center'"><s:message code="ui.fields.issue.creator" /></th>
          <th data-options="field:'flowDatetime',width:200,align:'center'"><s:message code="ui.fields.flowable.monitor.initiated.process.initiate.datetime" /></th>
          <th data-options="field:'flowActionText',width:200,align:'center'"><s:message code="ui.fields.audit.flow.status" /></th>
        </tr>
      </thead>
    </table>
  </div>
</div>
