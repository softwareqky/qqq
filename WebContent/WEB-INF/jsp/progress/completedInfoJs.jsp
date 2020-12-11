<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!COMPLETED_INFO) {
        var COMPLETED_INFO = (function($) {

            /**
             * 弹出用于修改记录的带有Form的Dialog，弹出前通过Ajax向服务端发送待查找的数据的id，并将返回自服务端的JSON结果加载到指定的Form中。
             * 根据数据记录的'项目状态'，弹出项目建议文件上传画面、项目可研文件上传画面或者项目初设文件画面其中之一。
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openEditFormDialog(gridId, url) {

                var status;

                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
                if (selectedRows.length == 1) {
                    status = selectedRows[0].checkStatus_;
                } else {
                    if (selectedRows.length == 0) {
                        AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    } else if (selectedRows.length > 1) {
                        AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                    }
                    return;
                }

                var dialogId = "#${idMap['edit-form-dialog']}";
                if (status == 'CHECK_STATUS_1') { // 解决
                    dialogId += '-Improve';
                } else if (status == 'CHECK_STATUS_2') { // 验证
                    dialogId += '-Verification';
                } else {
                    return;
                }

                // 无需回调，沿用所有CrudUtils默认逻辑
                CrudUtils.openEditFormDialog(dialogId, url, "#${idMap['main-datagrid']}");
            }
            
            function openViewPrint(dialogId, url, gridId, callback) {
            	debugger;
            	CrudUtils.openViewDialog(dialogId, url, gridId, callback);
            }
            
            function generateCert(gridId, url) {
            	// 获取选中的单条记录id
            	debugger;
                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
                var recordId = null;
                
                if (gridId) {
                    $grid = $(gridId);
                    var selectedRows = $grid.datagrid('getSelections');
                    if (selectedRows.length == 1) {
                        recordId = selectedRows[0].id;
                    } else {
                        if (selectedRows.length == 0) {
                            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                        } else if (selectedRows.length > 1) {
                            AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                        }
                        return;
                    }
                } else {
                    // gridId为空时，rowIndex作为recordId使用
                    recordId = null;
                }

                var paramData = {};
                paramData['id'] = recordId;
                paramData['project_'] = selectedRows[0].project_;
                var options = {
                        url: BASE_URL + '/process/project-completed/generate-cert.json',
                        type: 'POST',
                        dataType: 'json',
                        data: paramData,
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                            	AlertUtils.msg('info', response.message);
                            	$grid.datagrid('reload');
                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);
            }

            return {
            	generateCert: generateCert,
                openEditFormDialog: openEditFormDialog,
                openViewPrint: openViewPrint
            }; // ISSUE
        })(jQuery);
    }

    //]]>

    
</script>