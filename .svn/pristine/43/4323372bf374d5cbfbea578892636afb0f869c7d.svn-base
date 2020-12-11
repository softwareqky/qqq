<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    if (!BUDGETSUM) {
        var BUDGETSUM = (function($) {

            /**
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
             
             //导入 
            function openUploadFormDialog() {

                var $dialog = $('#_BudgetsumExcelDialog');

                var $form = $('#_BudgetsumExcelDialogForm');

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

            function saveImportExcelData() {
                var $form = $('#_BudgetsumExcelDialogForm');

                $form.form('enableValidation');
                if (!$form.form('validate')) {
                    return;
                }
                
                var con = $('#P10004-BUDGET_ESTIMATE_SUM-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                	AlertUtils.msg('warning', "请选择项目！");
                    return;
                }

                var options = {
                    url: BASE_URL + '/budget/budgetEstimateSum/import-excel-file.json?projectId='+ con.value,
                    type: 'POST',
                    dataType: 'html',
                    data: {

                    },
                    success: function(response, statusText, xhr, jqForm) {
                        var jsonObj = $.parseJSON($(response).text());

                        MainUtils.closeLoading();
                        if (MainUtils.processJsonResult(jsonObj, true)) {

                            if (jsonObj.dataMap.returnObj) {

                                EasyDialogUtils.closeFormDialog('#_BudgetsumExcelDialog');
                                $('#P10004-BUDGET_ESTIMATE_SUM-MainDatagrid').datagrid('reload');
                            }
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $form.ajaxSubmit(options);
                MainUtils.openLoading();
            }
            
            
            //导出
            function openExportFormDialog() {
            	
            	var con = $('#P10004-BUDGET_ESTIMATE_SUM-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                	AlertUtils.msg('warning', "请选择项目！");
                    return;
                }

                var $dialog = $('#_BudgetsumExcelExportDialog');

                var $form = $('#_BudgetsumExcelExportDialogForm');

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
            
            function exportSumData() {
                var con = $('#P10004-BUDGET_ESTIMATE_SUM-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }
                
                var combobox=$('#_BudgetsumExcelExportCombobox').combobox('getValue'); 
                var exporturl = BASE_URL + '/budget/budgetEstimateSum/export-excel-file.json?projectId='+ con.value;
                var exportdetailurl = BASE_URL + '/budget/budgetEstimateSum/export-detail-excel-file.json?projectId='+ con.value;
                if(combobox==1){
                    MainUtils.downloadFile(exporturl);
                }
                if(combobox==2){
                    MainUtils.downloadFile(exportdetailurl);
                }
                EasyDialogUtils.closeFormDialog('#_BudgetsumExcelExportDialog');
            }
            
            

            return {
                openUploadFormDialog: openUploadFormDialog,
                saveImportExcelData: saveImportExcelData,
                openExportFormDialog:openExportFormDialog,
                exportSumData:exportSumData
            }; // SITE
        })(jQuery);
    }
    //]]>

    
</script>