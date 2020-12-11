<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!PROJECT_PERSON_CHANGE) {
        var PROJECT_PERSON_CHANGE = (function($) {

            /**
             * 点击项目集-项目树的节点时，更新右侧的两个Grid，并且更新检索区域的项目组combobox。
             * 
             * @param node
             * @param data
             * @param isLoadSuccessHandler true表示加载成功的回调，false表示选择节点的回调
             */
            function projectTreeNodeCallback(node, data, isLoadSuccessHandler) {

                if (isLoadSuccessHandler) {
                    _treeLoadSuccess(node);
                } else {
                    _selectTreeNode(node);
                }
            }

            /**
             * 选中树节点事件处理。
             */
            function _selectTreeNode(node) {

                var subGridId = '#P2061-PROJECT_PERSON-SubDatagrid';
                var mainGridId = '#P2061-PROJECT_PERSON_CHANGE-MainDatagrid';
                var $subGrid = $(subGridId);
                var $mainGrid = $(mainGridId);

                var $subGridAdd = $('#P2060F01');
                var $mainGridAdd = $('#P2061F01');

                // 0. 联动启用禁用两个Grid的新建按钮
                var gridFunc = node.id == 'ALL' || !node.attributes.fieldName ? 'disable'
                        : 'enable';
                $subGridAdd.linkbutton(gridFunc);
                $mainGridAdd.linkbutton(gridFunc);

                // 判断是否重复点击
                var oldCon = $subGrid.data('EXTRA_FILTER_OBJ');
                if (oldCon && oldCon.value == node.id) {
                    return;
                }
                if (node.id == 'ALL' && oldCon == null) {
                    return;
                }
                if (node.id != 'ALL' && !node.attributes.fieldName) {
                    // 后台如果对node.attributes.fieldName不设置，则不处理此点击
                    return;
                }

                // 1. 处理SubGrid
                var con = null;
                if (node.id != 'ALL') { // 不是选择全部时，准备过滤条件对象
                    con = {};
                    con.fieldName = node.attributes.fieldName;
                    con.valueType = '4';
                    con.filterSearchBy = '1';
                    con.valueMatchMode = '0';
                    con.captionName = node.text;
                    con.value = node.id;
                }

                var $filterForm = $('#P2061-PROJECT_PERSON-SubGridFilterForm');
                $filterForm.form('reset');

                var SEARCH_ARRAY = $subGrid.data('SEARCH_ARRAY');
                if (SEARCH_ARRAY && SEARCH_ARRAY.length > 0) {
                    $subGrid.data('SEARCH_ARRAY', new Array());
                }

                $subGrid.data('EXTRA_FILTER_OBJ', con);
                FilterUtils.doSearch(subGridId);

                // 2. 处理MainGrid
                $mainGrid.data('EXTRA_FILTER_OBJ', con); // 这里使用相同的检索条件，后台应灵活处理，由后台决定是否进行过滤
                FilterUtils.doSearch(mainGridId);

                // 3. 更新项目组Combobox
                var control = $filterForm.find('select[name="virtualOrg_"]');

                if (node.id != 'ALL') { // 不是选择全部时
                    var options = {
                        url: BASE_URL + '/project/virtual-org/list-pid-options.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            project_: node.id
                        },
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var vorgOptions = response.dataMap.rows;
                                control.empty();
                                control.append('<option value="">&nbsp;</option>');
                                for (var i = 0; i < vorgOptions.length; i++) {
                                    control.append('<option value="' + vorgOptions[i].id + '">'
                                            + vorgOptions[i].text + '</option>');
                                }

                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);
                } else {
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                }
            }

            /**
             * 树加载成功事件处理。
             */
            function _treeLoadSuccess(node) {

                var subGridId = '#P2061-PROJECT_PERSON-SubDatagrid';
                var mainGridId = '#P2061-PROJECT_PERSON_CHANGE-MainDatagrid';
                var $subGrid = $(subGridId);
                var $mainGrid = $(mainGridId);

                var $subGridAdd = $('#P2060F01');
                var $mainGridAdd = $('#P2061F01');

                // 0. 联动启用禁用两个Grid的新建按钮
                $subGridAdd.linkbutton('disable');
                $mainGridAdd.linkbutton('disable');

                // 判断是否重复点击
                var oldCon = $subGrid.data('EXTRA_FILTER_OBJ');
                if (oldCon == null) {
                    // 画面首次加载时，也会直接返回
                    return;
                }

                // 1. 处理SubGrid
                var $filterForm = $('#P2061-PROJECT_PERSON-SubGridFilterForm');
                $filterForm.form('reset');

                var SEARCH_ARRAY = $subGrid.data('SEARCH_ARRAY');
                if (SEARCH_ARRAY && SEARCH_ARRAY.length > 0) {
                    $subGrid.data('SEARCH_ARRAY', new Array());
                }

                $subGrid.data('EXTRA_FILTER_OBJ', null);
                FilterUtils.doSearch(subGridId);

                // 2. 处理MainGrid
                $mainGrid.data('EXTRA_FILTER_OBJ', null); // 这里使用相同的检索条件，后台应灵活处理，由后台决定是否进行过滤
                FilterUtils.doSearch(mainGridId);

                // 3. 更新项目组Combobox
                var control = $filterForm.find('select[name="virtualOrg_"]');
                control.empty();
                control.append('<option value="">&nbsp;</option>');
            }

            /**
             * SubGrid的选中和不选中的事件处理，不联动MainGrid，仅控制按钮。
             * 
             * @param index 同easyui的datagrid的onSelect事件
             * @param row
             */
            function handleSubGridSelect(index, row) {
                var $this = $(this);
                var rows = $this.datagrid('getSelections');

                // 删除
                $('#P2060F02').linkbutton(rows.length > 0 ? 'enable' : 'disable');

                // 修改/查看
                var singleFunc = rows.length == 1 ? 'enable' : 'disable';
                $('#P2060F03').linkbutton(singleFunc);
                $('#P2060F04').linkbutton(singleFunc);
            }

            /**
             * 新建画面初始化的回调，用来刷新上级虚拟组织下拉框的内容。
             */
            function onInitAddFormWidget(control) {

                var name = ControlUtils.getControlName(control);
                if (name == 'projectRole_') {

                    var options = {
                        url: BASE_URL + '/project/role/list.json',
                        type: 'POST',
                        dataType: 'json',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var roleOptions = response.dataMap.rows;

                                control.empty();
                                control.append('<option value="">&nbsp;</option>');
                                for (var i = 0; i < roleOptions.length; i++) {
                                    control.append('<option value="' + roleOptions[i].id + '">'
                                            + roleOptions[i].projectRoleName + '</option>');
                                }

                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);
                } else if (name == 'virtualOrg_') {

                    var con = $('#P2061-PROJECT_PERSON-SubDatagrid').data('EXTRA_FILTER_OBJ');

                    var options = {
                        url: BASE_URL + '/project/virtual-org/list-pid-options.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            project_: con.value
                        },
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var virtualOrgOptions = response.dataMap.rows;

                                control.empty();
                                control.append('<option value="">&nbsp;</option>');
                                for (var i = 0; i < virtualOrgOptions.length; i++) {
                                    control
                                            .append('<option value="' + virtualOrgOptions[i].id + '">'
                                                    + virtualOrgOptions[i].text + '</option>');
                                }

                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);
                }
            }

            /**
             * 修改画面初始化的回调，用来设置项目组和项目角色下拉框的内容。
             */
            function beforeInitEditFormWidget(control, isFirstLoad, response) {

                var name = ControlUtils.getControlName(control);
                if (name == 'projectRole_') {

                    var roleOptions = response.dataMap.roles;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < roleOptions.length; i++) {
                        control.append('<option value="' + roleOptions[i].id + '">'
                                + roleOptions[i].text + '</option>');
                    }
                } else if (name == 'virtualOrg_') {

                    var vorgOptions = response.dataMap.vorgs;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < vorgOptions.length; i++) {
                        control.append('<option value="' + vorgOptions[i].id + '">'
                                + vorgOptions[i].text + '</option>');
                    }
                }
            }

            /**
             * easyui datagrid双击数据行的事件处理，弹出修改画面
             */
            function handleDblClickRow(index, row) {

                /*
                *
                
                var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');

                var $this = $(this);

                // 双Grid画面的场合，兼容SubGrid
                if ($this.is('[data-linked-grid-id]')) {
                    editFormId = this.id.replace('SubDatagrid', 'SubGridEditFormDialog');
                }

                CrudUtils.openEditFormDialog('#' + editFormId, $this.datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, index, PROJECT_PERSON_CHANGE);
                */
                
                var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
                var $this = $(this);
                
                if($this.is('[data-linked-grid-id]')){
                    viewFormId = this.id.replace('SubDatagrid', 'SubGridViewGridDialog'); 
                }
                
                CrudUtils.openViewDialog('#' + viewFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, PROJECT_PERSON_CHANGE, row.id);
                
            }

            /**
             * 在选择人员后的回调，根据所选人员弹出MainGrid的新建成员变更画面。
             * 
             * @param rows 选择的人员记录
             */
            function onChooseRows(rows) {

                var dialogId = '#P2061-PROJECT_PERSON_CHANGE-AddFormDialog';
                var $dialog = $(dialogId);

                var param = {};
                param.personList = [];

                $.each(rows, function(i, n) {
                    param.personList.push(n.id);
                });

                $dialog.data('param', param);

                CrudUtils.openAddFormDialog(dialogId, PROJECT_PERSON_CHANGE);
            }

            /**
             * 新建和修改时的回调，用来设定关联的项目。修改时，后台不应处理前台传入的project_
             */
            function beforeSubmit(options, isAdd, isContinuousAdd, $dialog) {

                if (isAdd) {
                    options.traditional = true;
                    options.data = $dialog.data('param');
                }

                var con = $('#P2061-PROJECT_PERSON-SubDatagrid').data('EXTRA_FILTER_OBJ');
                if (con) {
                    options.data.project_ = con.value;
                }
            }

            /**
             * 新建成功后的回调，刷新grid，不向grid插入新建的结果。
             */
            function beforeDatagridInsert(datagridId, json) {
                $(datagridId).datagrid('reload');
                return false;
            }

            return {
                projectTreeNodeCallback: projectTreeNodeCallback,
                handleSubGridSelect: handleSubGridSelect,
                onInitAddFormWidget: onInitAddFormWidget,
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                handleDblClickRow: handleDblClickRow,
                beforeSubmit: beforeSubmit,
                onChooseRows: onChooseRows,
                beforeDatagridInsert: beforeDatagridInsert
            }; // PROJECT_PERSON_CHANGE
        })(jQuery);

        var PROJECT_PERSON_CHANGE_SUBGRID = (function($) {

            /**
             * 在选择人员后的回调，根据所选人员弹出SubGrid的新建成员画面。
             * 
             * @param rows 选择的人员记录
             */
            function onChooseRows(rows) {

                var dialogId = '#P2061-PROJECT_PERSON-SubGridAddFormDialog';
                var $dialog = $(dialogId);

                var param = {};
                param.personList = [];

                $.each(rows, function(i, n) {
                    param.personList.push(n.id);
                });

                $dialog.data('param', param);

                CrudUtils.openAddFormDialog(dialogId, PROJECT_PERSON_CHANGE);
            }

            return {
                onChooseRows: onChooseRows
            }; // PROJECT_PERSON_CHANGE_SUBGRID
        })(jQuery);
    }
    //]]>

    
</script>