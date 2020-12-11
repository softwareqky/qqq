<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!PROJECT_CHANGE) {
        var PROJECT_CHANGE = (function($) {

            /**
             * 变更原因的式样，增加tooltip。同MainUtils.remarkFormatter。
             * 
             * @param value 字符值
             * @param row easyui的datagrid中的某一行数据
             * @param index easyui的datagrid中的行index
             */
            function changeReasonFormatter(value, row, index) {
                return '<span title="' + row.changeReasonHtmlEscaped + '">' + value + '</span>';
            }

            /**
             * 变更内容的式样，增加tooltip。同MainUtils.remarkFormatter。
             * 
             * @param value 字符值
             * @param row easyui的datagrid中的某一行数据
             * @param index easyui的datagrid中的行index
             */
            function changeContentFormatter(value, row, index) {
                return '<span title="' + row.changeContentHtmlEscaped + '">' + value + '</span>';
            }

            /**
             * 变更标题(remark)的式样，增加tooltip。同MainUtils.remarkFormatter。
             * 
             * @param value 字符值
             * @param row easyui的datagrid中的某一行数据
             * @param index easyui的datagrid中的行index
             */
            function changeRemarkFormatter(value, row, index) {
                return '<span title="' + row.changeRemarkHtmlEscaped + '">' + value + '</span>';
            }

            /**
             * 使用TreeGrid中选中项目的信息，调用立项Controller的find方法，加载到项目变更的新建画面，然后弹出。
             */
            function openAddFormDialog() {
                var con = $('#P6510-PROJECT_CHANGE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }
                CrudUtils.openEditFormDialog('#P6510-PROJECT_CHANGE-AddFormDialog', BASE_URL
                        + '/project/project-init/find.json', null, con.value, PROJECT_CHANGE);
            }

            /**
             * easyui datagrid双击数据行的事件处理，弹出修改画面。主要是为了传入callback对象。
             * 
             * 可以参考ProjectChangeController，Modle中通过key(dblClickRowHandler)来指定grid.tag中使用的双击事件处理函数。
             */
            function handleDblClickRow(index, row) {
                
                /*
                var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
                CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, index, PROJECT_CHANGE);
                */
                
                var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
                CrudUtils.openViewDialog('#' + viewFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, PROJECT_CHANGE, row.id);
            }

            /**
             * 修改画面初始化的回调，用来刷新项目集下拉框的内容。
             * 
             * 可以参考ProjectChangeController，在Controller的find方法中，需要把项目集列表返回给前端。
             */
            function beforeInitEditFormWidget(control, isFirstLoad, response) {
                var name = ControlUtils.getControlName(control);
                if (name == 'projectSet_') {
                    var projectSetOptions = response.dataMap.rows;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < projectSetOptions.length; i++) {
                        control.append('<option value="' + projectSetOptions[i].id + '">'
                                + projectSetOptions[i].text + '</option>');
                    }
                } else if (name == 'archive_') {

                    // 新建时，丢弃来自Project的附件
                    if (control.closest('form').attr('action').indexOf('add.json') != -1) {
                        var json = response.dataMap.returnObj;
                        json.archiveList = undefined; // 详见CrudUtils.openEditFormDialog中针对文件附件的处理
                    }
                }
            }

            /**
             * 新建和修改时的回调，用来设定项目变更关联的项目。修改时，后台不应处理前台传入的project_
             */
            function beforeSubmit(options) {
                var con = $('#P6510-PROJECT_CHANGE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (con) {
                    options.data.project_ = con.value;
                }
            }

            return {
                changeReasonFormatter: changeReasonFormatter,
                changeContentFormatter: changeContentFormatter,
                changeRemarkFormatter: changeRemarkFormatter,
                openAddFormDialog: openAddFormDialog,
                handleDblClickRow: handleDblClickRow,
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                beforeSubmit: beforeSubmit
            }; // PROJECT_CHANGE
        })(jQuery);
    }

    //]]>

    
</script>