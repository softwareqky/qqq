<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<div class="hidden" id="EasyuiHiddenWrapper">

  <%-- 人员选择弹出画面 --%>
  <div class="hidden">
    <div id="${personPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="personName"
      data-options="title:'<s:message code="ui.datatype.person"/>',closed:true,buttons:'#${personPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:treeGridEasyuiLayout idPrefix="${personPopupDialogId}" filterFormId="${personPopupDialogId}-FilterForm" listUrl="${personControlListUrl}" searchFunctions="${personControlSearchFunctions}"
        searchFields="${personControlSearchFields}" comboboxDataMap="${personControlComboboxDataMap}" optionMap="${personControlOptionMap}" gridId="${personPopupDialogId}-MainDatagrid"
        isHandleDblClickRow="true" defaultOrder="${personControlDefaultOrder}" listFrozenFields="${personControlListFrozenFields}" listFields="${personControlListFields}" isSingleSelect="true"
        selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      >
        <g:sideDeptTree treeUrl="${urlMap['tree-side-dept']}" idPrefix="${personPopupDialogId}" linkedGridId="${personPopupDialogId}-MainDatagrid" />
      </g:treeGridEasyuiLayout>

    </div>

    <%-- 人员选择弹出画面的按钮 --%>
    <div id="${personPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${personPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${personPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
  
  <%-- link选择弹出画面 --%>
  <div class="hidden">
    <div id="${linkPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="linkName"
      data-options="title:'<s:message code="ui.datatype.link"/>',closed:true,buttons:'#${linkPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:singleGridEasyuiLayout idPrefix="${linkPopupDialogId}" filterFormId="${linkPopupDialogId}-FilterForm" listUrl="${linkControlListUrl}" searchFunctions="${linkControlSearchFunctions}"
        searchFields="${linkControlSearchFields}" comboboxDataMap="${linkControlComboboxDataMap}" optionMap="${linkControlOptionMap}" gridId="${linkPopupDialogId}-MainDatagrid"
        isHandleDblClickRow="true" defaultOrder="${linkControlDefaultOrder}" listFrozenFields="${linkControlListFrozenFields}" listFields="${linkControlListFields}" isSingleSelect="true"
        selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      >
      </g:singleGridEasyuiLayout>

    </div>

    <%-- 人员选择弹出画面的按钮 --%>
    <div id="${linkPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${linkPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${linkPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
  
  <%-- 概算册选择弹出画面 --%>
  <div class="hidden">
    <div id="${budget_estimate_sumPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="name"
      data-options="title:'<s:message code="ui.datatype.budget_estimate_sum"/>',closed:true,buttons:'#${budget_estimate_sumPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:treeGridEasyuiLayout idPrefix="${budget_estimate_sumPopupDialogId}" filterFormId="${budget_estimate_sumPopupDialogId}-FilterForm" listUrl="${budget_estimate_sumControlListUrl}" searchFunctions="${budget_estimate_sumControlSearchFunctions}"
        searchFields="${budget_estimate_sumControlSearchFields}" comboboxDataMap="${budget_estimate_sumControlComboboxDataMap}" optionMap="${budget_estimate_sumControlOptionMap}" gridId="${budget_estimate_sumPopupDialogId}-MainDatagrid"
        isHandleDblClickRow="true" defaultOrder="${budget_estimate_sumControlDefaultOrder}" listFrozenFields="${budget_estimate_sumControlListFrozenFields}" listFields="${budget_estimate_sumControlListFields}" isSingleSelect="true"
        selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      >
      	<g:sideProjectFilterTree treeUrl="${urlMap['tree-side-project']}" idPrefix="${budget_estimate_sumPopupDialogId}" linkedGridId="${budget_estimate_sumPopupDialogId}-MainDatagrid" />
      </g:treeGridEasyuiLayout>

    </div>

    <%-- 人员选择弹出画面的按钮 --%>
    <div id="${budget_estimate_sumPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${budget_estimate_sumPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${budget_estimate_sumPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
  
  <%-- 项目成员选择弹出画面 --%>
  <div class="hidden">
    <div id="${project_memberPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="personText"
      data-options="title:'<s:message code="ui.datatype.project.person"/>',closed:true,buttons:'#${project_memberPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:treeGridEasyuiLayout idPrefix="${project_memberPopupDialogId}" filterFormId="${project_memberPopupDialogId}-FilterForm" listUrl="${project_memberControlListUrl}" searchFunctions="${project_memberControlSearchFunctions}"
        searchFields="${project_memberControlSearchFields}" comboboxDataMap="${project_memberControlComboboxDataMap}" optionMap="${project_memberControlOptionMap}" gridId="${project_memberPopupDialogId}-MainDatagrid"
        isHandleDblClickRow="true" defaultOrder="${project_memberControlDefaultOrder}" listFrozenFields="${project_memberControlListFrozenFields}" listFields="${project_memberControlListFields}" isSingleSelect="true"
        selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      >
      	<g:sideProjectFilterTree treeUrl="${urlMap['tree-side-project']}" idPrefix="${project_memberPopupDialogId}" linkedGridId="${project_memberPopupDialogId}-MainDatagrid" />
      </g:treeGridEasyuiLayout>

    </div>

    <%-- 人员选择弹出画面的按钮 --%>
    <div id="${project_memberPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${project_memberPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${project_memberPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 人员多行通用选择弹出画面 --%>
  <div class="hidden">
    <div id="General_${personPopupDialogId}" class="easyui-dialog"
      data-options="title:'<s:message code="ui.datatype.person"/>',closed:true,buttons:'#General_${personPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:treeGridEasyuiLayout idPrefix="General_${personPopupDialogId}" filterFormId="General_${personPopupDialogId}-FilterForm" listUrl="${personControlListUrl}"
        searchFunctions="${personControlSearchFunctions}" searchFields="${personControlSearchFields}" comboboxDataMap="${personControlComboboxDataMap}" optionMap="${personControlOptionMap}"
        gridId="General_${personPopupDialogId}-MainDatagrid" isHandleDblClickRow="false" defaultOrder="${personControlDefaultOrder}" listFrozenFields="${personControlListFrozenFields}"
        listFields="${personControlListFields}" isSingleSelect="false" selectHandler="PopupSelectUtils.handleRowSelect" clickCellHandler="PopupSelectUtils.handleCellSelect"
      >
        <g:sideDeptTree treeUrl="${urlMap['tree-side-dept']}" idPrefix="General_${personPopupDialogId}" linkedGridId="General_${personPopupDialogId}-MainDatagrid" />
      </g:treeGridEasyuiLayout>

    </div>

    <%-- 人员选择弹出画面的按钮 --%>
    <div id="General_${personPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleGeneralClickOk('#General_${personPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#General_${personPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 双Grid人员多行通用选择弹出画面 --%>
  <div class="hidden">
    <div id="General-PERSON-PopupSelectorDialog" class="easyui-dialog"
      data-options="title:'<s:message code="ui.datatype.person"/>',closed:true,buttons:'#General-PERSON-PopupSelectorDialogButtons',iconCls:'icon-func-edit',
            height:'calc(100vh)',minHeight:600,width:'100%',minWidth:1000,modal:true,shadow:true,resizable:false,onMove:EasyDialogUtils.onDialogMove,
            href:'<s:url value="/hr/person-selector/main.htm"/>',cache:false,queryParams:{timestamp: new Date().getTime()},extractor:Main.extractContentTabHtmlBody,onLoadError:MainUtils.handleDatagridLoadError,
            onBeforeClose:Main.onBeforeCotentPanelClose"
    ></div>

    <%-- 人员选择弹出画面的按钮 --%>
    <div id="General-PERSON-PopupSelectorDialogButtons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleDblGridClickOk('#General-PERSON-PopupSelectorDialog', '#P17006-PERSON-MainDatagrid')">
        <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#General-PERSON-PopupSelectorDialog')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 双Grid专家选择弹出画面 --%>
  <div class="hidden">
    <div id="General-EXPERT-PopupSelectorDialog" class="easyui-dialog"
      data-options="title:'<s:message code="ui.datatype.project.person"/>',closed:true,buttons:'#General-EXPERT-PopupSelectorDialogButtons',iconCls:'icon-func-edit',
            height:'calc(100vh)',minHeight:600,width:'950',minWidth:1000,modal:true,shadow:true,resizable:false,onMove:EasyDialogUtils.onDialogMove,
            href:'<s:url value="/hr/expert-selector/main.htm"/>',cache:false,queryParams:{timestamp: new Date().getTime()},extractor:Main.extractContentTabHtmlBody,onLoadError:MainUtils.handleDatagridLoadError,
            onBeforeClose:Main.onBeforeCotentPanelClose"
    ></div>

    <%-- 专家选择弹出画面的按钮 --%>
    <div id="General-EXPERT-PopupSelectorDialogButtons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleDblGridClickOk('#General-EXPERT-PopupSelectorDialog', '#P17011-EXPERT-MainDatagrid')">
        <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#General-EXPERT-PopupSelectorDialog')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
  
  <%-- 岗位选择弹出画面 --%>
  <div class="hidden">
    <div id="${postPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="postName"
      data-options="title:'<s:message code="ui.datatype.post"/>',closed:true,buttons:'#${postPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:treeGridEasyuiLayout idPrefix="${postPopupDialogId}" filterFormId="${postPopupDialogId}-FilterForm" listUrl="${postControlListUrl}" searchFunctions="${postControlSearchFunctions}"
        searchFields="${postControlSearchFields}" comboboxDataMap="${postControlComboboxDataMap}" optionMap="${postControlOptionMap}" gridId="${postPopupDialogId}-MainDatagrid"
        isHandleDblClickRow="true" defaultOrder="${postControlDefaultOrder}" listFrozenFields="${postControlListFrozenFields}" listFields="${postControlListFields}" isSingleSelect="true"
        selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      >
        <g:sideDeptTree treeUrl="${urlMap['tree-side-dept']}" idPrefix="${postPopupDialogId}" linkedGridId="${postPopupDialogId}-MainDatagrid" />
      </g:treeGridEasyuiLayout>

    </div>

    <%-- 岗位选择弹出画面的按钮 --%>
    <div id="${postPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${postPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${postPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 站点选择弹出画面 --%>
  <div class="hidden">
    <div id="${sitePopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="stationName"
      data-options="title:'<s:message code="ui.datatype.site"/>',closed:true,buttons:'#${sitePopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:singleGridEasyuiLayout idPrefix="${sitePopupDialogId}" filterFormId="${sitePopupDialogId}-FilterForm" listUrl="${siteControlListUrl}" searchFunctions="${siteControlSearchFunctions}"
        searchFields="${siteControlSearchFields}" comboboxDataMap="${siteControlComboboxDataMap}" optionMap="${siteControlOptionMap}" gridId="${sitePopupDialogId}-MainDatagrid"
        isHandleDblClickRow="true" defaultOrder="${siteControlDefaultOrder}" listFields="${siteControlListFields}" listFrozenFields="${siteControlListFrozenFields}" isSingleSelect="true"
        selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      />

    </div>

    <%-- 站点选择弹出画面的按钮 --%>
    <div id="${sitePopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${sitePopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${sitePopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 中继段选择弹出画面 --%>
  <div class="hidden">
    <div id="${segmentPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="popupSelectInfo"
      data-options="title:'<s:message code="ui.datatype.segment"/>',closed:true,buttons:'#${segmentPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:singleGridEasyuiLayout idPrefix="${segmentPopupDialogId}" filterFormId="${segmentPopupDialogId}-FilterForm" listUrl="${segmentControlListUrl}"
        searchFunctions="${segmentControlSearchFunctions}" searchFields="${segmentControlSearchFields}" comboboxDataMap="${segmentControlComboboxDataMap}" optionMap="${segmentControlOptionMap}"
        gridId="${segmentPopupDialogId}-MainDatagrid" isHandleDblClickRow="true" defaultOrder="${segmentControlDefaultOrder}" listFields="${segmentControlListFields}"
        listFrozenFields="${segmentControlListFrozenFields}" isSingleSelect="true" selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow"
        clickCellHandler="PopupSelectUtils.handleCellSelect"
      />

    </div>

    <%-- 中继段选择弹出画面的按钮 --%>
    <div id="${segmentPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${segmentPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${segmentPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 往来单位选择弹出画面 --%>
  <div class="hidden">
    <div id="${related_unitPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="unitName"
      data-options="title:'<s:message code="ui.datatype.related.unit"/>',closed:true,buttons:'#${related_unitPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:singleGridEasyuiLayout idPrefix="${related_unitPopupDialogId}" filterFormId="${related_unitPopupDialogId}-FilterForm" listUrl="${related_unitControlListUrl}"
        searchFunctions="${related_unitControlSearchFunctions}" searchFields="${related_unitControlSearchFields}" comboboxDataMap="${related_unitControlComboboxDataMap}"
        optionMap="${related_unitControlOptionMap}" gridId="${related_unitPopupDialogId}-MainDatagrid" isHandleDblClickRow="true" defaultOrder="${related_unitControlDefaultOrder}"
        listFields="${related_unitControlListFields}" listFrozenFields="${related_unitControlListFrozenFields}" isSingleSelect="true" selectHandler="PopupSelectUtils.handleRowSelect"
        dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      />

    </div>

    <%-- 往来单位选择弹出画面的按钮 --%>
    <div id="${related_unitPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${related_unitPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${related_unitPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 项目选择弹出画面 --%>
  <div class="hidden">
    <div id="${projectPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="projectName"
      data-options="title:'<s:message code="ui.datatype.project"/>',closed:true,buttons:'#${projectPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:singleGridEasyuiLayout idPrefix="${projectPopupDialogId}" filterFormId="${projectPopupDialogId}-FilterForm" listUrl="${projectControlListUrl}"
        searchFunctions="${projectControlSearchFunctions}" searchFields="${projectControlSearchFields}" comboboxDataMap="${projectControlComboboxDataMap}" optionMap="${projectControlOptionMap}"
        gridId="${projectPopupDialogId}-MainDatagrid" isHandleDblClickRow="true" defaultOrder="${projectControlDefaultOrder}" listFields="${projectControlListFields}"
        listFrozenFields="${projectControlListFrozenFields}" isSingleSelect="true" selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow"
        clickCellHandler="PopupSelectUtils.handleCellSelect"
      />

    </div>

    <%-- 项目选择弹出画面的按钮 --%>
    <div id="${projectPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${projectPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${projectPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 计划选择弹出画面 --%>
  <div class="hidden">
    <div id="${planPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="planName"
      data-options="title:'<s:message code="ui.datatype.plan"/>',closed:true,buttons:'#${planPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:singleGridEasyuiLayout idPrefix="${planPopupDialogId}" filterFormId="${planPopupDialogId}-FilterForm" listUrl="${planControlListUrl}" searchFunctions="${planControlSearchFunctions}"
        searchFields="${planControlSearchFields}" comboboxDataMap="${planControlComboboxDataMap}" optionMap="${planControlOptionMap}" gridId="${planPopupDialogId}-MainDatagrid"
        isHandleDblClickRow="true" defaultOrder="${planControlDefaultOrder}" listFields="${planControlListFields}" listFrozenFields="${planControlListFrozenFields}" isSingleSelect="true"
        selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      />

    </div>

    <%-- 计划选择弹出画面的按钮 --%>
    <div id="${planPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${planPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${planPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- 纸质文档选择弹出画面 --%>
  <div class="hidden">
    <div id="${paper_libraryPopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="archiveName"
      data-options="title:'<s:message code="ui.menu.item.paper.library.list"/>',closed:true,buttons:'#${paper_libraryPopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:treeGridEasyuiLayout idPrefix="${paper_libraryPopupDialogId}" filterFormId="${paper_libraryPopupDialogId}-FilterForm" listUrl="${paper_libraryControlListUrl}"
        searchFunctions="${paper_libraryControlSearchFunctions}" searchFields="${paper_libraryControlSearchFields}" comboboxDataMap="${paper_libraryControlComboboxDataMap}"
        optionMap="${paper_libraryControlOptionMap}" gridId="${paper_libraryPopupDialogId}-MainDatagrid" isHandleDblClickRow="true" defaultOrder="${paper_libraryControlDefaultOrder}"
        listFrozenFields="${paper_libraryControlListFrozenFields}" listFields="${paper_libraryControlListFields}" isSingleSelect="true" selectHandler="PopupSelectUtils.handleRowSelect"
        dblClickRowHandler="PopupSelectUtils.handleDblClickRow" clickCellHandler="PopupSelectUtils.handleCellSelect"
      >
        <g:sideDeptTree treeUrl="${urlMap['tree-side-project']}" idPrefix="${paper_libraryPopupDialogId}" linkedGridId="${paper_libraryPopupDialogId}-MainDatagrid" />
      </g:treeGridEasyuiLayout>

    </div>

    <%-- 选择弹出画面的按钮 --%>
    <div id="${paper_libraryPopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${paper_libraryPopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${paper_libraryPopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <div class="hidden">
    <div id="AuditLogRecordDialog" class="easyui-dialog" data-id-field="id" data-text-field="archiveName"
      data-options="title:'<s:message code="ui.common.audit.log"/>',closed:true,buttons:'#AuditLogRecordDialogButtons',iconCls:'icon-func-audit-log',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:auditLogLayout></g:auditLogLayout>
    </div>

    <%-- 审核日志弹出画面的按钮 --%>
    <div id="AuditLogRecordDialogButtons">
      <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#AuditLogRecordDialog')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>

  <%-- OA审批日志dialog --%>
  <div class="hidden">
    <div id="OaAuditLogRecordDialog" class="easyui-dialog" data-id-field="id" data-text-field="archiveName"
      data-options="title:'<s:message code="ui.common.audit.log"/>',closed:true,buttons:'#OaAuditLogRecordDialogButtons',iconCls:'icon-func-audit-log',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:auditLogLayoutOa></g:auditLogLayoutOa>
    </div>

    <%-- 审核日志弹出画面的按钮 --%>
    <div id="OaAuditLogRecordDialogButtons">
      <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#OaAuditLogRecordDialog')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
  
  <%-- 项目角色选择弹出画面 --%>
  <div class="hidden">
    <div id="${project_rolePopupDialogId}" class="easyui-dialog" data-id-field="id" data-text-field="projectRoleName"
      data-options="title:'<s:message code="ui.datatype.project.role"/>',closed:true,buttons:'#${project_rolePopupDialogId}Buttons',iconCls:'icon-func-edit',
            height:560,minHeight:560,width:950,modal:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
    >
      <g:singleGridEasyuiLayout idPrefix="${project_rolePopupDialogId}" filterFormId="${project_rolePopupDialogId}-FilterForm" listUrl="${project_roleControlListUrl}"
        searchFunctions="${project_roleControlSearchFunctions}" searchFields="${project_roleControlSearchFields}" comboboxDataMap="${project_roleControlComboboxDataMap}" optionMap="${project_roleControlOptionMap}"
        gridId="${project_rolePopupDialogId}-MainDatagrid" isHandleDblClickRow="true" defaultOrder="${project_roleControlDefaultOrder}" listFields="${project_roleControlListFields}"
        listFrozenFields="${project_roleControlListFrozenFields}" isSingleSelect="true" selectHandler="PopupSelectUtils.handleRowSelect" dblClickRowHandler="PopupSelectUtils.handleDblClickRow"
        clickCellHandler="PopupSelectUtils.handleCellSelect"
      />

    </div>

    <%-- 人员选择弹出画面的按钮 --%>
    <div id="${project_rolePopupDialogId}Buttons">
      <a href="javascript:void(0)" class="easyui-linkbutton c8" role="button" onclick="PopupSelectUtils.handleClickOk('#${project_rolePopupDialogId}')"> <s:message code="ui.common.ok" />
      </a> <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#${project_rolePopupDialogId}')"> <s:message code="ui.common.quit" />
      </a>
    </div>
  </div>
</div>
<%-- 计划任务进度编制/查看弹出画面 --%>
<%-- <div class="hidden">
  <div id="_TaskPlanGanttPanel" class="easyui-dialog"
    data-options="closed:true,
            height:560,minHeight:560,width:950,modal:true,maximized:true,resizable:false,onMove:EasyDialogUtils.onDialogMove"
  ></div>

  计划任务进度编制/查看弹出画面的按钮
  <div id="_TaskPlanGanttPanelButtons">
    <a href="javascript:void(0)" class="easyui-linkbutton" role="button" onclick="EasyDialogUtils.closeFormDialog('#_TaskPlanGanttPanel')"> <s:message code="ui.common.quit" />
    </a>
  </div>
</div> --%>
<!-- /#EasyuiHiddenWrapper -->