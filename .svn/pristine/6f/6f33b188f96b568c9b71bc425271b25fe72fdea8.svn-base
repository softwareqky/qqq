<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />
<c:set var="dialogWidth" value="785" />
<c:set var="dialogHeight" value="380" />

<%-- 新建弹出画面 --%>
<c:if test="${not empty addRoleFields}">
  <g:addDialog continuousAddSubmitJs="${jsMap['continuous-add-submit']}" dialogId="${idMap['add-form-dialog']}" width="${dialogWidth}" addSubmitJs="${jsMap['add-submit']}" maxHeight="${dialogHeight}"
    height="${dialogHeight}" hasCopyReserve="${hasCopyReserve}"
  >
    <div class="easyui-layout" data-options="fit:true">
      <%-- Form --%>
      <div data-options="region:'west',border:false,width:'auto',width:400,maxWidth:400">
        <div class="easyui-layout" data-options="fit:true">

          <div data-options="region:'center',border:false" style="padding: 6px 5px 5px 5px;">
            <g:addEditFormSimpleLayout formFields="${addRoleFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['add']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}"
              controlCols="${addDialogControlCols}" labelCols="${12 - addDialogControlCols}"
            />
          </div>
          <!-- center region in west -->

          <div data-options="region:'east',border:false,width:2,minWidth:2,maxWidth:2">
            <hr class="vbar" />
          </div>
          <!-- east region in west -->

        </div>
        <!-- /.easyui-layout in west -->
      </div>
      <!-- /.west region form -->

      <%-- Tree --%>
      <div data-options="region:'center',border:false" style="padding: 8px 0px 8px 20px">
        <ul class="easyui-tree" id="${idMap['add-form-dialog']}-PageTree"
          data-options="url:'<s:url value="/auth/role/page-tree.json"/>',
                            checkbox:true,loadFilter:MainUtils.treeLoadFilter,onLoadError:MainUtils.handleDatagridLoadError"
        >
        </ul>
      </div>
      <!-- /.center region tree -->

    </div>
    <!-- /.easyui-layout -->
  </g:addDialog>
</c:if>

<%-- 修改弹出画面 --%>
<c:if test="${not empty editRoleFields}">
  <g:editDialog dialogId="${idMap['edit-form-dialog']}" width="${dialogWidth}" editSubmitJs="${jsMap['edit-submit']}" maxHeight="${dialogHeight}" height="${dialogHeight}">
    <div class="easyui-layout" data-options="fit:true">
      <%-- Form --%>
      <div data-options="region:'west',border:false,width:'auto',width:400,maxWidth:400">
        <div class="easyui-layout" data-options="fit:true">

          <div data-options="region:'center',border:false" style="padding: 6px 5px 5px 5px;">
            <g:addEditFormSimpleLayout formFields="${editRoleFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['edit']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}"
              controlCols="${editDialogControlCols}" labelCols="${12 - editDialogControlCols}"
            />
          </div>
          <!-- center region in west -->

          <div data-options="region:'east',border:false,width:2,minWidth:2,maxWidth:2">
            <hr class="vbar" />
          </div>
          <!-- east region in west -->

        </div>
        <!-- /.easyui-layout in west -->
      </div>
      <!-- /.west region form -->

      <%-- Tree --%>
      <div data-options="region:'center',border:false" style="padding: 8px 0px 8px 20px">
        <ul class="easyui-tree" id="${idMap['edit-form-dialog']}-PageTree"
          data-options="url:'<s:url value="/auth/role/page-tree.json"/>',
                            checkbox:true,loadFilter:MainUtils.treeLoadFilter,onLoadError:MainUtils.handleDatagridLoadError"
        >
        </ul>
      </div>
      <!-- /.center region tree -->

    </div>
    <!-- /.easyui-layout -->
  </g:editDialog>
</c:if>

<%-- 查看弹出画面 --%>
<c:if test="${not empty viewRoleFields}">
  <g:viewDialog dialogId="${idMap['view-dialog']}" width="${dialogWidth}" maxHeight="${dialogHeight}" height="${dialogHeight}">
    <div class="easyui-layout" data-options="fit:true">
      <%-- Form --%>
      <div data-options="region:'west',border:false,width:'auto',width:400,maxWidth:400">
        <div class="easyui-layout" data-options="fit:true">

          <div data-options="region:'center',border:false" style="padding: 6px 5px 5px 5px;">
            <g:viewSimpleLayout viewFields="${viewRoleFields}" controlCols="${viewDialogControlCols}" labelCols="${12 - viewDialogControlCols}" />
          </div>
          <!-- center region in west -->

          <div data-options="region:'east',border:false,width:2,minWidth:2,maxWidth:2">
            <hr class="vbar" />
          </div>
          <!-- east region in west -->

        </div>
        <!-- /.easyui-layout in west -->
      </div>
      <!-- /.west region form -->

      <%-- Tree --%>
      <div data-options="region:'center',border:false" style="padding: 8px 0px 8px 20px">
        <ul class="easyui-tree" id="${idMap['view-dialog']}-PageTree"
          data-options="url:'<s:url value="/auth/role/page-tree.json"/>',
                            checkbox:true,loadFilter:MainUtils.treeLoadFilter,onLoadError:MainUtils.handleDatagridLoadError"
        >
        </ul>
      </div>
      <!-- /.center region tree -->

    </div>
    <!-- /.easyui-layout -->
  </g:viewDialog>
</c:if>

