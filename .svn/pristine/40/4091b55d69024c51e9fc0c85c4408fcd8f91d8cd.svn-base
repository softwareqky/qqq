<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    	
    var date = new Date();
	this.year = date.getFullYear();
    $("[name='year'] option:contains("+this.year+")").prop("selected", true);
         
    if (!BUDGETFINAL) {
        var BUDGETFINAL = (function($) {
        	
        	//导出
            function openExportFormDialog() {

                var $dialog = $('#_BudgetExcelExportDialog');

                var $form = $('#_BudgetExcelExportDialogForm');

                function process(isFirstLoad) {
                    $form.form('reset');
                    $dialog.dialog('open').dialog('center').scrollTop(0);
                    $form.form('disableValidation');
                }

                var $dialogWrapper = $dialog.parent();
                if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
                    MainUtils.openLoading();
                    setTimeout(function() {
                        $.parser.parse($dialogWrapper);

                        // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                        $dialog.parent().prop('rendered', true);
                        process(true);
                        MainUtils.closeLoading();
                    }, 0);
                } else {
                    process(false);
                }
            }
          
            function exportData() {
                var con = $('#P10014-BUDGET_ESTIMATE_FINAL-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }
                
                var data = $('#_BudgetExcelExportDialogForm').serialize();
                var url = BASE_URL + '/budget/budgetFinal/export-final-excel-file.json?'+data+'&project_='+ con.value;
                MainUtils.downloadFile(url);
                EasyDialogUtils.closeFormDialog('#_BudgetExcelExportDialog');
            }
            
            /**
                         *  经费决算对应oa审批 
              * (budgetFee,balance,warning字段为特殊查询获取，审批不再调用共通处理，页面js直接获取选中行数据提交)
             */
            function handleAuditClick(gridId, url) {
                // 获取选中的单条记录id
                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
                var selectedObj = null;
                
                var $form = $('#P10014-BUDGET_ESTIMATE_FINAL-FilterForm');
                if (!$form.form('validate')) {
                    return;
                }
                var year = $('#P10014-BUDGET_ESTIMATE_FINAL-FilterForm select[name=year] option:checked').val();
                
                var con = $('#P10014-BUDGET_ESTIMATE_FINAL-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }
                
                if (gridId) {
                    $grid = $(gridId);
                    selectedRows = $grid.datagrid('getSelections');
                    if (selectedRows.length == 1) {
                    	selectedObj = selectedRows[0];
                    } else {
                        if (selectedRows.length == 0) {
                            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                        } else if (selectedRows.length > 1) {
                            AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                        }
                        return;
                    }
                } else {
                    return;
                }
                
                var paramData = {};
                paramData['id'] = selectedObj.id;
                paramData['year'] = year;
                paramData['projectId'] = con.value;
                MainUtils.openLoading();
                var options = {
                        url: BASE_URL + '/budget/budgetFinal/auditSubmit.json',
                        type: 'POST',
                        dataType: 'json',
                        data: paramData,
                        success: function(response, statusText, xhr, jqForm) {
                        	MainUtils.closeLoading();
                            if (MainUtils.processJsonResult(response, false)) {
                            	AlertUtils.msg('info', response.message);
                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);
            }
            
            // 打开查看采购记录对话框
            function openOrderListDialog(dialogId, datagridId) {
                var row = $('#P10014-BUDGET_ESTIMATE_FINAL-MainDatagrid').datagrid('getSelected');
                if (!row) {
                	AlertUtils.msg('warning', "请选择记录！");
                    return;
                }
                
                if (!dialogId || !datagridId) {
                    return;
                }
                var $dialog = $(dialogId);
                var $datagrid = $(datagridId);

                var $logDatagrid = $('#_OrderListDatagrid');
                function process(isFirstLoad) {

                    var selected = $datagrid.datagrid('getSelected');

                    var options = $logDatagrid.datagrid('options');
                    options.queryParams.correlateDataId = selected.id;
                    options.queryParams.code = selected.code;

                    $logDatagrid.datagrid('reload', BASE_URL + '/budget/budgetFinal/get_order_list.json');

                    $dialog.dialog('open').dialog('center').scrollTop(0);
                }

                var $dialogWrapper = $dialog.parent();
                if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
                    MainUtils.openLoading();
                    setTimeout(function() {
                        $.parser.parse($dialogWrapper);

                        // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                        $dialogWrapper.remove();
                        $dialog.parent().prop('rendered', true);
                        process(true);
                        MainUtils.closeLoading();
                    }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
                } else {
                    process(false);
                }
            }
            
            
            return {
            	openExportFormDialog: openExportFormDialog,
                exportData:exportData,
                handleAuditClick: handleAuditClick,
                openOrderListDialog: openOrderListDialog
            }; // BUDGETFINAL
        })(jQuery);
    }
    //]]>

    
</script>