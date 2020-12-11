<%@tag description="左树右grid画面布局中，左侧的项目一览，采用grid展示项目(后续本tag不再使用，改用sideProjectFilterTree.tag)" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="listUrl" required="true" description="项目列表的一览的datagrid请求ajax对应的URL"%>
<%@attribute name="linkedGridId" required="true" description="关联的外部datagrid的id，加载或点击项目列表中的行时，将刷新外部的datagrid"%>
<%@attribute name="linkedSubgridId" description="关联的外部datagrid的id，加载或点击项目列表中的行时，将清空外部的datagrid"%>

<div class="easyui-layout" data-options="fit:true">

  <div data-options="region:'center',border:false" style="padding: 6px 5px 5px 5px;">
    <div class="easyui-layout" data-options="fit:true">

      <div data-options="region:'north',border:false">

        <form id="${idPrefix}-SideFilterForm" action="" method="post">
          <div class="container-fluid min-width-200">
            <%-- 项目名称 --%>
            <div class="row row-gapped-top-s">
              <div class="col-xs-12 col-gapped-left-m col-cell-height-m">
                <span class="filter-control-label" data-name="projectName" data-value-type="4" data-value-match-mode="2" data-filter-search-by="1">
                  <s:message code="ui.fields.project.project.name" />
                </span>
                <input type="text" class="form-control ez-like" style="width: 161px; vertical-align: middle; display: inline-block;" data-field-widget="textbox" data-field-control maxlength="50"
                  placeholder="<s:message code="ui.common.maxlength" /> 50"
                />
              </div>
            </div>
            <%-- 立项年份 --%>
            <div class="row row-gapped-top-s">
              <div class="col-xs-12 col-gapped-left-m col-cell-height-m">
                <span class="filter-control-label" data-name="projectYear" data-value-type="4" data-value-match-mode="2" data-filter-search-by="1">
                  <s:message code="ui.fields.project.project.year" />
                </span>
                <select class="form-control ez-like" data-field-widget="combobox" data-field-control style="width: 161px; vertical-align: middle; display: inline-block;">
                  <option value="">&nbsp;</option>
                  <c:forEach var="i" begin="2015" end="2030" step="1">
                    <option value="${i}">${i}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="row row-gapped-top-s">
              <div class="col-xs-6 col-gapped-xs">
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="width:'100%'" role="button"
                  onclick="FilterUtils.clearSearch('#${idPrefix}-SideDatagrid', '#${idPrefix}-SideFilterForm');"
                >
                  <s:message code="ui.common.clear.search" />
                </a>
              </div>
              <div class="col-xs-6 col-gapped-xs">
                <a href="javascript:void(0)" class="easyui-linkbutton c8" data-options="width:'100%'" role="button"
                  onclick="FilterUtils.searchSimple('#${idPrefix}-SideDatagrid', '#${idPrefix}-SideFilterForm');"
                >
                  <s:message code="ui.common.search" />
                </a>
              </div>
            </div>
          </div>
        </form>

      </div>
      <!-- center.north region -->

      <%-- 项目列表 --%>
      <div data-options="region:'center',border:false" style="padding-top: 10px">
        <table id="${idPrefix}-SideDatagrid" class="easyui-datagrid" data-linked-grid-id="${linkedGridId}" <c:if test="${not empty linkedSubgridId}">data-linked-subgrid-id="${linkedSubgridId}"</c:if>
          data-options="url:'${listUrl}',multiSort:false,sortName:'projectName',sortOrder:'asc',
                singleSelect:true,pagination:false,view:bufferview,rownumbers:false,pageSize:50,pageList:[30,50],idField:'id',showFooter:false,
                autoRowHeight:true,nowrap:true,striped:false,fit:true,fitColumns:false,
                loadFilter:MainUtils.datagridLoadFilter,onLoadSuccess:SideUtils.handleGridLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError,
                onSelect:SideUtils.handleGridRowSelect,onUnselect:SideUtils.handleGridRowSelect" clickCellHandler=""
        >
          <thead>
            <tr>
              <th data-options="field:'projectName',width:236,align:'center',sortable:true,formatter:SideUtils.projectFormatter">
                <s:message code="ui.fields.project.side.name" />
              </th>
            </tr>
          </thead>
        </table>
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