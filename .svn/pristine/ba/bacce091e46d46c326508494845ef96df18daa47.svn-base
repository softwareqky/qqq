<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!PROJECT_ROLE) {
        var PROJECT_ROLE = (function($) {

            /**
             * 新建画面初始化的回调，用来刷新上级虚拟组织下拉框的内容。
             */
            function onInitAddFormWidget(control) {

                if (ControlUtils.getControlName(control) == 'pid_') {

                    project = "";
                    var options = {
                        url: BASE_URL + '/project/role/list-pid-options.json',
                        type: 'POST',
                        dataType: 'json',
                        data: '',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var projectRoleOptions = response.dataMap.rows;

                                control.empty();
                                control.append('<option value="">&nbsp;</option>');
                                for (var i = 0; i < projectRoleOptions.length; i++) {
                                    control
                                            .append('<option value="' + projectRoleOptions[i].id + '">'
                                                    + projectRoleOptions[i].text + '</option>');
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

                    var projectRoleOptions = response.dataMap.rows;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < projectRoleOptions.length; i++) {
                        control.append('<option value="' + projectRoleOptions[i].id + '">'
                                + projectRoleOptions[i].text + '</option>');
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
            
            return {
                onInitAddFormWidget: onInitAddFormWidget,
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                handleDblClickRow: handleDblClickRow
            }; // PROJECT_ROLE
        })(jQuery);
    }
    //]]>

    
</script>