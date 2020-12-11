<%@tag description="基于bootstrap布局的单grid，目前暂不使用" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>
<%-- 无参数列表，直接采用画面的变量 --%>
<c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />
<div class="container-fluid">

  <c:if test="${not empty searchFields}">
    <div class="row row-gapped-top-s">
      <div class="col-sm-12">
        <g:filter listUrl="${urlMap['list']}" searchFunctions="${searchFunctions}" searchFields="${searchFields}" formId="${idMap['filter-form']}" idPrefix="${idPrefix}"
          comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}"
        />
      </div>
      <!-- /.col -->
    </div>
    <!-- /.row -->
  </c:if>

  <div class="row">
    <div class="col-sm-12">
      <div class="datagrid-wrapper min-width-880 min-height-m">
        <g:grid listFields="${listFields}" gridId="${idMap['main-datagrid']}" listFrozenFields="${listFrozenFields}" defaultOrder="${defaultOrder}" listUrl="${urlMap['list']}" functions="${functions}"
          isHandleDblClickRow="${canEditData}"
        />
      </div>
      <!-- /.datagrid-wrapper -->
    </div>
    <!-- /.col -->
  </div>
  <!-- /.row -->

</div>
<!-- /.container-fluid -->