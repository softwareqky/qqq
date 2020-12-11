<%@tag description="基于EasyUI布局的左树+右双grid，支持自动适应窗体大小变化" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%-- 以下是MainGrid相关(右下) --%>
<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="filterFormId" required="true" description="检索栏的Form的id"%>
<%@attribute name="listUrl" required="true" description="一览的datagrid请求ajax对应的URL，即Controller中list方法的URL"%>

<%@attribute name="searchFunctions" required="true" type="java.util.ArrayList" description="检索按钮列表"%>
<%@attribute name="searchFields" required="true" type="java.util.ArrayList" description="普通检索fields列表"%>

<%@attribute name="gridId" required="true" description="DataGrid的id"%>
<%@attribute name="defaultOrder" required="true" type="garage.origin.domain.business.OrderByDto" description="一览的默认排序字段名"%>
<%@attribute name="isPage" type="java.lang.Boolean" description="DataGrid是否使用分页条"%>
<%@attribute name="listFrozenFields" required="true" type="java.util.ArrayList" description="一览的固定列fields列表"%>
<%@attribute name="listFields" required="true" type="java.util.ArrayList" description="一览的非固定列fields列表"%>
<%@attribute name="functions" type="java.util.ArrayList" description="功能栏按钮列表"%>

<%@attribute name="isSingleSelect" type="java.lang.Boolean" description="DataGrid是否单选"%>
<%@attribute name="isHandleDblClickRow" required="true" type="java.lang.Boolean" description="是否支持双击行弹出修改画面，配合修改权限控制"%>
<%@attribute name="dblClickRowHandler" description="DataGrid的行双击事件处理器"%>
<%@attribute name="selectHandler" description="DataGrid的行选择和反选事件处理器"%>


<%-- 以下是其他 --%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<div class="easyui-layout" data-options="fit:true">

  <%-- Left side tree --%>
  <div data-options="region:'west',border:false,width:'auto',width:250,maxWidth:250">
    <jsp:doBody />
  </div>
  <!-- west region -->

  <%-- Double DataGrids with filter --%>
  <div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">

      <div data-options="region:'north',border:false,height:350">
        <div class="easyui-layout" data-options="fit:true">

          <div data-options="region:'north',border:false,height:'auto',maxHeight:95" style="overflow: visible; padding: 0px 15px 0px 13px;">
            <div class="grid-title">
              <i class="fa fa-fw fa-pencil-square-o"></i>
              <s:message code="ui.menu.group.flowable.setting.view.list" />
            </div>
            <c:if test="${not empty searchFields}">
              <g:filter listUrl="${listUrl}" searchFunctions="${searchFunctions}" searchFields="${searchFields}" formId="${filterFormId}" idPrefix="${idPrefix}" comboboxDataMap="${comboboxDataMap}"
                optionMap="${optionMap}"
              />
            </c:if>
          </div>
          <!-- /.north of MainGrid -->

          <div data-options="region:'center',border:false" style="padding: 0px 10px 5px 8px;">
            <g:grid listFields="${listFields}" gridId="${gridId}" listFrozenFields="${listFrozenFields}" defaultOrder="${defaultOrder}" listUrl="${listUrl}" functions="${functions}"
              isHandleDblClickRow="${isHandleDblClickRow}" isSingleSelect="true" dblClickRowHandler="${dblClickRowHandler}" selectHandler="FLOWABLE_UI_UTILS.reloadFlowDetail" isPage="${isPage}"
            />
          </div>
          <!-- /.center of MainGrid -->

        </div>
        <!-- /.easyui-layout of SubGrid -->
      </div>
      <!-- /.north for SubGrid -->

      <div data-options="region:'center',border:false">
        <div class="easyui-layout" data-options="fit:true">

          <div data-options="region:'north',border:false,height:'auto',maxHeight:95" style="overflow: visible; padding: 5px 15px 0px 13px;">
            <div class="grid-title">
              <i class="fa fa-fw fa-tag"></i>
              <s:message code="ui.menu.group.flowable.setting.detail" />
            </div>
          </div>
          <!-- /.north of SubGrid -->
          <div data-options="region:'center',border:false" style="padding: 0px 10px 5px 8px;">
            <div class="easyui-layout" data-options="fit:true">
              <div data-options="region:'north',border:false,height:'auto',maxHeight:95" style="overflow: visible; padding: 5px 15px 5px 13px;">
                <div>
                  <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-func-flowable-single-audit-node'" onclick="FLOWABLE_UI_UTILS.openSingleAuditDialog('#_SingleAuditNodeDialog',true);"><s:message
                      code="ui.fields.flowable.setting.single.audit.node"
                    /></a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-func-flowable-role-audit-node'" onclick="FLOWABLE_UI_UTILS.openRoleAuditDialog('#_RoleAuditNodeDialog',true);"><s:message
                      code="ui.datatype.project.role"
                    /></a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-func-flowable-end-node'" onclick="FLOWABLE_UI_UTILS.endFlowableNode();"><s:message
                      code="ui.fields.flowable.setting.end.node"
                    /></a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-func-edit'" onclick="FLOWABLE_UI_UTILS.editFlowableNode();"><s:message code="ui.common.modify" /></a> <a
                    href="#" class="easyui-linkbutton" data-options="iconCls:'icon-func-delete'" onclick="FLOWABLE_UI_UTILS.deleteFlowableNode();"
                  ><s:message code="ui.common.delete" /></a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-func-save'" onclick="FLOWABLE_UI_UTILS.saveFlowDetailData('${gridId}');"><s:message
                      code="ui.common.save"
                    /></a>
                </div>
              </div>
              <!-- /.north of SubGrid -->
              <div data-options="region:'center',border:true" style="padding: 0px 10px 5px 8px;">
                <div id="_FlowDetailViewDiv"></div>
              </div>
              <!-- /.center of SubGrid -->

            </div>
          </div>
          <!-- /.center of SubGrid -->

        </div>

        <!-- /.easyui-layout of MainGrid -->
      </div>
      <!-- /.center for MainGrid -->

    </div>
    <!-- /.easyui-layout in center region -->
  </div>
  <!-- center region -->

</div>
<!-- /.easyui-layout outmost -->

