<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/include/head.jsp" flush="true" />
</head>
<body>
  <%-- body内的结构说明同Main._extractContentTabHtmlBody --%>
  <%-- 在body内分为同级并列的4个区域，从上到下依次是： --%>
  <%-- 1. 可见的布局，通常使用bootstrap的container/container-fluid或者EasyUI的layout来构建UI --%>
  <%-- 2. 隐藏的div#EasyuiHiddenWrapper.hidden，用来包含弹出框，每个弹出框都包含在独立的div.hidden内 --%>
  <%-- 3. 隐藏的div#ContentScriptWrapper.hidden，用来包含画面所需的JavaScript脚本引用，供独立调试画面时使用。这些脚本在main.jsp已经引入，所以在tab加载画面body时将会被直接抛弃 --%>
  <%-- 4. 使用script标签引入的画面专有的JavaScript逻辑，由includeJspJsPath变量指定 --%>

  <c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />
  <div class="easyui-layout" data-options="fit:true">

    <div data-options="region:'north',border:false,minWidth:830,height:'auto',maxHeight:95" style="padding: 10px 15px 0px;">
      <div class="container-fluid">
        <div class="row">
          <div class="col-xs-9 col-gapped-none">
            <%-- 文件功能按钮 --%>
            <button id="P9001F16" type="button" class="btn btn-primary" onclick="${jsMap['open-add-file']}">
              <i class="fa fa-fw fa-upload"></i>
              <s:message code="ui.common.upload.file" />
            </button>
            <button id="P9001F01" type="button" class="btn btn-default" onclick="${jsMap['open-add-folder']}">
              <i class="fa fa-fw fa-folder-o"></i>
              <s:message code="ui.common.add.folder" />
            </button>
            <div class="btn-group" role="group">
              <button id="P9001F17" type="button" class="btn btn-default" onclick="${jsMap['open-download']}">
                <i class="fa fa-fw fa-download"></i>
                <s:message code="ui.common.download" />
              </button>
              <button id="P9001F02" type="button" class="btn btn-default" onclick="${jsMap['open-delete']}">
                <i class="fa fa-fw fa-trash-o"></i>
                <s:message code="ui.common.delete" />
              </button>
              <button id="P9001F03" type="button" class="btn btn-default" onclick="${jsMap['open-edit']}">
                <s:message code="ui.common.modify.info" />
              </button>
              <button id="P9001F22" type="button" class="btn btn-default" onclick="${jsMap['open-authority']}">
                <s:message code="ui.common.authority" />
              </button>
              <%-- <button id="P9001F51" type="button" class="btn btn-default">
                <s:message code="ui.common.copy.to" />
              </button>
              <button id="P9001F52" type="button" class="btn btn-default">
                <s:message code="ui.common.move.to" />
              </button> --%>
            </div>
          </div>
          <div class="col-xs-3 col-gapped-none">
            <%-- 文件检索框 --%>
            <form id="${idMap['filter-form']}" action="${urlMap['list']}" method="post">
              <span class="filter-control-label hidden" data-name="archiveName" data-value-type="4" data-value-match-mode="2" data-filter-search-by="1"></span>
              <div class="input-group">
                <input type="text" class="form-control ez-like" style="height: 34px;" data-field-widget="textbox" data-field-control maxlength="50"
                  placeholder="<s:message code="message.info.search.file" />"
                >
                <span class="input-group-btn">
                  <button class="btn btn-default" type="button" onclick="${jsMap['search']}">
                    <i class="fa fa-fw fa-search"></i>
                  </button>
                </span>
              </div>
              <!-- /input-group -->
            </form>
          </div>
        </div>
      </div>
    </div>

    <div data-options="region:'center',border:false,minWidth:830,bodyCls:'archive-content'" style="padding: 10px 0px;">

      <table id="${idMap['main-datagrid']}" class="easyui-datagrid"
        data-options="url:'${urlMap['list']}',toolbar:'#${idMap['main-datagrid']}Toolbar',
                multiSort:false,sortName:'${defaultOrder.columnName}',sortOrder:'<c:choose><c:when test="${defaultOrder.desc}">desc</c:when><c:otherwise>asc</c:otherwise></c:choose>',
                singleSelect:false,pagination:false,view:bufferview,rownumbers:false,pageSize:999999999,idField:'id',showFooter:false,
                autoRowHeight:true,nowrap:true,striped:false,fit:true,fitColumns:true,emptyMsg:'<s:message code="message.info.no.record" />',
                onDblClickRow:ARCHIVE.handleDblClickRow,
                loadFilter:ARCHIVE.datagridLoadFilter,onLoadSuccess:MainUtils.handleDatagridLoadSuccess,onLoadError:MainUtils.handleDatagridLoadError,
                onSelect:ARCHIVE.handleSelect,onUnselect:ARCHIVE.handleSelect,onSelectAll:ARCHIVE.handleSelect,onUnselectAll:ARCHIVE.handleSelect"
      >
        <thead>
          <tr>
            <th data-options="field:'ck',checkbox:true"></th>
            <th data-options="field:'archiveName',width:450,align:'left',sortable:true,formatter:ARCHIVE.archiveNameFormatter">
              <s:message code="ui.fields.archive.name" />
            </th>
            <th data-options="field:'remark',width:200,align:'left',sortable:false,formatter:MainUtils.remarkFormatter">
              <s:message code="ui.fields.archive.remark" />
            </th>
            <th data-options="field:'fileSizeText',width:70,align:'left',sortable:true,fixed:true">
              <s:message code="ui.fields.archive.file.size" />
            </th>
            <th data-options="field:'mDatetime',width:145,align:'left',sortable:true,fixed:true">
              <s:message code="ui.fields.archive.mdatetime" />
            </th>
            <th data-options="field:'pname',width:130,align:'left',sortable:false,fixed:true,formatter:ARCHIVE.pnameFormatter,hidden:true">
              <s:message code="ui.fields.archive.pname" />
            </th>
          </tr>
        </thead>
      </table>

      <div id="${idMap['main-datagrid']}Toolbar">
        <ol class="breadcrumb">
        </ol>
        <div class="gridstatus"></div>
        <div class="clearfix"></div>
      </div>

    </div>
    <!-- /region center -->
  </div>

  <div class="hidden" id="EasyuiHiddenWrapper">

    <c:if test="${not empty includeJspHiddenContentPath}">
      <%-- 子类JSP的第2个扩展点(共2个) --%>
      <jsp:include page="${includeJspHiddenContentPath}" flush="true" />
    </c:if>

  </div>
  <!-- /#EasyuiHiddenWrapper -->

  <div class="hidden" id="ContentScriptWrapper">
    <jsp:include page="/WEB-INF/jsp/common/include/javascriptSrc.jsp" flush="true" />
  </div>
  <!-- /#ContentScriptWrapper -->

  <c:if test="${not empty includeJspJsPath}">
    <%-- 子类JSP的第1个扩展点(共2个) --%>
    <jsp:include page="${includeJspJsPath}" flush="true" />
  </c:if>

</body>
</html>