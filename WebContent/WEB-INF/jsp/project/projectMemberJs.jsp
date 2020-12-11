<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!PROJECT_MEMBER) {
        var PROJECT_MEMBER = (function($) {

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

                var mainGridId = '#P2063-PROJECT_MEMBER-MainDatagrid';
                var $mainGrid = $(mainGridId);

                var $mainGridAdd = $('#P2063F01');

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

                var mainGridId = '#P2063-PROJECT_MEMBER-MainDatagrid';
                var $mainGrid = $(mainGridId);

                var $mainGridAdd = $('#P2063F01');

                // 判断是否重复点击
                var oldCon = $subGrid.data('EXTRA_FILTER_OBJ');
                if (oldCon == null) {
                    // 画面首次加载时，也会直接返回
                    return;
                }

                // 2. 处理MainGrid
                $mainGrid.data('EXTRA_FILTER_OBJ', null); // 这里使用相同的检索条件，后台应灵活处理，由后台决定是否进行过滤
                FilterUtils.doSearch(mainGridId);

                // 3. 更新项目组Combobox
                var control = $filterForm.find('select[name="virtualOrg_"]');
                control.empty();
                control.append('<option value="">&nbsp;</option>');
            }

            /**
             * easyui datagrid双击数据行的事件处理，弹出修改画面
             */
            function handleDblClickRow(index, row) {
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

                var dialogId = '#P2063-PROJECT_MEMBER-AddFormDialog';
                var $dialog = $(dialogId);

                var param = {};
                param.personList = [];

                $.each(rows, function(i, n) {
                    param.personList.push(n.id);
                });

                $dialog.data('param', param);

                CrudUtils.openAddFormDialog(dialogId, PROJECT_MEMBER);
            }

            return {
                projectTreeNodeCallback: projectTreeNodeCallback,
                _selectTreeNode: _selectTreeNode,
                _treeLoadSuccess: _treeLoadSuccess,
                handleDblClickRow: handleDblClickRow,
                onChooseRows: onChooseRows,
            }; // PROJECT_PERSON_CHANGE
        })(jQuery);

    }
    //]]>

    
</script>