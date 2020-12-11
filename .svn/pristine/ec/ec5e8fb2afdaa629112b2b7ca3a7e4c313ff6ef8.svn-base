<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!VIRTUAL_ORG) {
        var VIRTUAL_ORG = (function($) {

            /**
             * 新建画面初始化的回调，用来刷新上级虚拟组织下拉框的内容。
             */
            function onInitAddFormWidget(control) {

                if (ControlUtils.getControlName(control) == 'pid_') {

                    project = "";
                    var options = {
                        url: BASE_URL + '/project/virtual-org/list-pid-options.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
                            project_: project
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
             * 修改画面初始化的回调，用来设置上级虚拟组织下拉框的内容。
             */
            function beforeInitEditFormWidget(control, isFirstLoad, response) {
                if (ControlUtils.getControlName(control) == 'pid_') {

                    var virtualOrgOptions = response.dataMap.rows;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < virtualOrgOptions.length; i++) {
                        control.append('<option value="' + virtualOrgOptions[i].id + '">'
                                + virtualOrgOptions[i].text + '</option>');
                    }
                }
            }
            
            /**
             * easyui datagrid双击数据行的事件处理，弹出修改画面
             */
            function handleDblClickRow(index, row) {
                
                /*
                var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
                CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url.replace(
                        'list', 'find'), '#' + this.id, index, VIRTUAL_ORG);
                
                */
                
                var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
                CrudUtils.openViewDialog('#' + viewFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, VIRTUAL_ORG, row.id);
            }
            
            /**
             * 新建和修改时的回调，用来设定项目变更关联的项目。修改时，后台不应处理前台传入的project_
             */
            function beforeSubmit(options) {
                var con = $('#P2030-PROJECT_GROUP-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (con) {
                    options.data.project_ = con.value;
                }
            }

            return {
                onInitAddFormWidget: onInitAddFormWidget,
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                handleDblClickRow: handleDblClickRow,
                beforeSubmit: beforeSubmit
            }; // VIRTUAL_ORG
        })(jQuery);
    }
    //]]>

    
</script>