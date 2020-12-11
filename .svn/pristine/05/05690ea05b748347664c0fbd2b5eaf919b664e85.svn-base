<%@tag description="左树右grid画面布局中，左侧的档案文件一览，采用树形结构展示文件夹-文件层级" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="treeUrl" required="true" description="树请求ajax对应的URL"%>
<%@attribute name="linkedGridId" description="关联的外部datagrid的id，加载或点击项目列表中的行时，将刷新外部的datagrid"%>
<%@attribute name="linkedSubgridId" description="关联的外部datagrid的id，加载或点击项目列表中的行时，将清空外部的datagrid"%>
<%@attribute name="linkedFunc" description="关联的外部datagrid的id，加载或点击项目列表中的行时，将清空外部的datagrid"%>

<div class="easyui-layout" data-options="fit:true">

  <div data-options="region:'center',border:false" style="padding: 6px 5px 5px 5px;">
    <div class="easyui-layout" data-options="fit:true">

      <%-- 刷新按钮 --%>
      <div data-options="region:'north',border:false" style="padding-bottom: 10px">

        <form id="${idPrefix}-SideFilterForm" action="" method="post">
          <div class="container-fluid min-width-200">
            <%-- 业务类型 --%>
            <div class="row row-gapped-top-s">
              <div class="col-xs-12 col-gapped-left-m col-cell-height-m">
                <span class="filter-control-label" data-name="pid" data-value-type="4" data-value-match-mode="2" data-filter-search-by="1"> <s:message
                    code="ui.menu.group.flowable.setting.business.type"
                  />
                </span> <select class="form-control ez-like" data-field-widget="combobox" data-field-control style="width: 161px; vertical-align: middle; display: inline-block;">
                  <option value="">&nbsp;</option>
                  <c:if test="${not empty optionMap['businessCodeOptions']}">
                    <c:forEach var="option" items="${optionMap['businessCodeOptions']}">
                      <option value="${option.id}">${option.text}</option>
                    </c:forEach>
                  </c:if>
                </select>
              </div>
            </div>
            <div class="row row-gapped-top-s">
              <div class="col-xs-6 col-gapped-xs">
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:'100%'" role="button"
                  onclick="FilterUtils.clearSearch('#${idPrefix}-SideTree', '#${idPrefix}-SideFilterForm');"
                > <s:message code="ui.common.clear.search" />
                </a>
              </div>
              <div class="col-xs-6 col-gapped-xs">
                <a href="javascript:void(0)" class="easyui-linkbutton c8" data-options="width:'100%'" role="button"
                  onclick="FilterUtils.searchSimple('#${idPrefix}-SideTree', '#${idPrefix}-SideFilterForm');"
                > <s:message code="ui.common.search" />
                </a>
              </div>
            </div>
          </div>
        </form>
      </div>
      <!-- center.north region -->

      <%-- 业务代码层级树 --%>
      <div data-options="region:'center',border:true">
        <ul id="${idPrefix}-SideTree" class="easyui-tree" <c:if test="${not empty linkedGridId}">data-linked-grid-id="${linkedGridId}"</c:if>
          <c:if test="${not empty linkedSubgridId}">data-linked-subgrid-id="${linkedSubgridId}"</c:if> <c:if test="${not empty linkedFunc}">data-linked-func="${linkedFunc}"</c:if>
          data-options="url:'${treeUrl}',onSelect:SideUtils.handleTreeNodeSelect,
            loadFilter:MainUtils.treeLoadFilter,onLoadSuccess:SideUtils.handleTreeLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError"
        ></ul>
      </div>
      <!-- center.center region -->

    </div>
    <!-- /.easyui-layout in center region -->
  </div>
  <!-- center region -->

  <div data-options="region:'east',border:false,width:2,minWidth:2,maxWidth:2">
    <hr class="vbar" />
  </div>
  <!-- east region -->

</div>
<!-- /.easyui-layout -->