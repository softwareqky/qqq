<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!BUDGETVERSION) {
        var BUDGETVERSION = (function($) {

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
             * 新建画面初始化的回调，用来获取SubGrid的选中记录，并设置计划名称和计划版本。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {
                var controlName = ControlUtils.getControlName(control);
                if (controlName == 'planName') { // 设置计划名称

                    var row = $('#P10010-BUDGET_ESTIMATE_VERSION-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }

                    ControlUtils.setControlValue(control, row.planName);

                } else if (controlName == 'planVersion') { // 设置 计划版本

                    var row = $('P10010-BUDGET_ESTIMATE_VERSION-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }

                    ControlUtils.setControlValue(control, row.planVersion);
                }
            }

            /**
             * 新建/修改时的回调，用来设定计划变更关联的计划。
             */
            function beforeSubmit(options) {
                var con = $('#P10010-BUDGET_ESTIMATE_VERSION-MainDatagrid').data('EXTRA_FILTER_OBJ');
                // 只有在con中检索'计划'时，才修改options.data(con也可以检索'项目')
                if (con && con.fieldName == 'plan_') {
                    options.data.plan_ = con.value;
                }
            }
            
            //导出
            function openExportFormDialog() {
            	var con = $('#P10013-BUDGET_ESTIMATE_VERSION-SubDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                	AlertUtils.msg('warning', "请选择项目！");
                    return;
                }

                var $dialog = $('#_BudgetversionExcelExportDialog');

                var $form = $('#_BudgetversionExcelExportDialogForm');

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
            
            function exportVersionData() {
            	debugger
                var con = $('#P10013-BUDGET_ESTIMATE_PAGE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }
                
                var aaa = $('#_BudgetversionExcelExportDialogForm').serialize();
                var exporturl = BASE_URL + '/budget/budgetEstimateVersion/export-excel-file.json?'+aaa+'&project_='+ con.value;
                MainUtils.downloadFile(exporturl);
                EasyDialogUtils.closeFormDialog('#_BudgetversionExcelExportDialog');
            }

            return {
                changeReasonFormatter: changeReasonFormatter,
                onInitAddFormWidget: onInitAddFormWidget,
                beforeSubmit: beforeSubmit,
                openExportFormDialog:openExportFormDialog,
                exportVersionData:exportVersionData
            }; // BUDGETVERSION
        })(jQuery);
    }

    //]]>

    
</script>