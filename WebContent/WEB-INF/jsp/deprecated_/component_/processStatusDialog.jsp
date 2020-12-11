<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 流程状态弹出画面 --%>
<%-- TODO 移至common文件夹 --%>

<div class="hidden">
  <div id="${idMap['page-pre']}-ProcessStatusDialog" class="easyui-dialog"
    data-options="title:'流程状态',closed:true,iconCls:'icon-func-process-status',maxHeight:520,width:748,modal:true,onMove:EasyDialogUtils.onDialogMove"
  >
    <div class="container-fluid">

      <div class="row">
        <div class="col-xs-6 col-xs-offset-3">
          <div class="center-block" style="width: 30%;">
            <h5>
              <strong>流程状态查看</strong>
            </h5>
          </div>
        </div>
      </div>

      <div class="row">
        <div class="col-xs-12" style="min-height: 98px;">
          <table id="_Datagrid" class="easyui-datagrid"
            data-options="multiSort:false,sortName:'name',sortOrder:'asc',
                singleSelect:true,pagination:false,idField:'id',
                autoRowHeight:true,nowrap:true,striped:false,fit:true,fitColumns:false"
          >
            <thead>
              <tr>
                <th data-options="field:'index',width:50,align:'center',sortable:false">序号</th>
                <th data-options="field:'nodeName',width:70,align:'center',sortable:false">节点名称</th>
                <th data-options="field:'auditer',width:70,align:'center',sortable:false">审核人</th>
                <th data-options="field:'auditDatetime',width:150,align:'center',sortable:false">审核时间</th>
                <th data-options="field:'result',width:80,align:'center',sortable:false">审核结果</th>
                <th data-options="field:'opinion',width:120,align:'center',sortable:false">审核，审核意见</th>
                <th data-options="field:'modifiedRecord',width:70,align:'center',sortable:false">修改记录</th>
                <th data-options="field:'status',width:70,align:'center',sortable:false">状态</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>路人乙</td>
                <td>路人乙</td>
                <td>2018-08-01 11:47:53</td>
                <td>通过</td>
                <td>同意</td>
                <td></td>
                <td>已审核</td>
              </tr>
              <tr>
                <td>2</td>
                <td>路人甲</td>
                <td>路人甲</td>
                <td>2018-08-01 11:49:53</td>
                <td>通过</td>
                <td></td>
                <td></td>
                <td>已审核</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>

      <div class="row">
        <div class="col-xs-12" style="min-height: 200px; border-bottom: 2px solid #ccc;"></div>
      </div>

      <div class="row">
        <div class="col-xs-12" style="min-height: 100px;">
          <div class="center-block" style="width: 40%;">
            <img src="<s:url value="/img/process-status.png"/>" class="img-responsive" alt="Responsive image" />
          </div>
        </div>
      </div>

    </div>
    <!-- /.container-fluid -->

  </div>

</div>