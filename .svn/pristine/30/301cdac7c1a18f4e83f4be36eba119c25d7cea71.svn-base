<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!ISSUE) {
        var ISSUE = (function($) {

            /**
             * 弹出用于修改记录的带有Form的Dialog，弹出前通过Ajax向服务端发送待查找的数据的id，并将返回自服务端的JSON结果加载到指定的Form中。
             * 根据数据记录的'项目状态'，弹出项目建议文件上传画面、项目可研文件上传画面或者项目初设文件画面其中之一。
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openEditFormDialog(gridId, url) {

                //debugger;
                console.log('a');
                
                var status;

                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
                if (selectedRows.length == 1) {
                    status = selectedRows[0].solveStatus_;
                } else {
                    if (selectedRows.length == 0) {
                        AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    } else if (selectedRows.length > 1) {
                        AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                    }
                    return;
                }
                
                var dialogId = "#${idMap['edit-form-dialog']}";
                console.log(dialogId, status, url, "#${idMap['main-datagrid']}");
                if (status == 'ISSUE_STATUS_2') { // 解决
                    dialogId += '-Solve';
                } else if (status == 'ISSUE_STATUS_5') { // 验证
                    dialogId += '-SpotCheck';
                } else {
                   /*  return; */
                }
                
                // 无需回调，沿用所有CrudUtils默认逻辑
                CrudUtils.openEditFormDialog(dialogId, url, "#${idMap['main-datagrid']}");
            }
            
            /**
             * 新建画面初始化的回调，用来刷新项目组下拉框的内容。
             */
            function onInitAddFormWidget(control) {
            	var con = $('#P8050-ISSUE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                	AlertUtils.msg('warning', "请选择项目！");
                    return;
                }

                if (ControlUtils.getControlName(control) == 'virtualOrg_') {

                    var options = {
                        url: BASE_URL + '/project/virtual-org/list-pid-options.json?project_='+ con.value,
                        type: 'POST',
                        dataType: 'json',
                        data: '',
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
             * 新建和修改时的回调，用来设定项目变更关联的项目。修改时，后台不应处理前台传入的project_
             */
            function beforeSubmit(options) {
            	var con = $('#P8050-ISSUE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (con) {
                    options.data.project_ = con.value;
                }
            }

            return {
                openEditFormDialog: openEditFormDialog,
                onInitAddFormWidget: onInitAddFormWidget,
                beforeSubmit: beforeSubmit
            }; // ISSUE
        })(jQuery);
    }

    //]]>

    
</script>