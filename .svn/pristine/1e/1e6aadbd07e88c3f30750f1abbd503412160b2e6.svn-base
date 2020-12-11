<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[  
    
    if (!CONTRACTBUDGET) {
        var CONTRACTBUDGET = (function($) {
        	
        	  /**
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openUploadFormDialog(gridId) {
        		debugger
        		
                //合同下拉框赋值
                var paramData = {};
            
           		var options = {
                    url: '/project-edge/budget/contractBudget/getContracts.json',
                    type: 'POST',
                    dataType: 'json',
                    data: paramData,
                    success: function(response, statusText, xhr, jqForm) {
                        if (MainUtils.processJsonResult(response, false)) {
                        	for(var i=0;i<response.dataMap.returnObj.length;i++){
                        		$('#contract').append(new Option(response.dataMap.returnObj[i].text, response.dataMap.returnObj[i].id));
                        	}
                        } else {
                            AlertUtils.msg(response.icon || 'error', "暂无可选择的合同");
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);

                var $dialog = $('#_ContractBudgetExcelDialog');

                var $form = $('#_ContractBudgetExcelDialogForm');

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
            	debugger
                var $form = $('#_ContractBudgetExcelDialogForm');

                $form.form('enableValidation');
                if (!$form.form('validate')) {
                    return;
                }
                
                var con = $('#P10019-CONTRACT_BUDGET-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }

                var options = {
                    url: BASE_URL + '/budget/contractBudget/import-excel-file.json?projectId='+ con.value,
                    type: 'POST',
                    dataType: 'html',
                    data: {

                    },
                    success: function(response, statusText, xhr, jqForm) {
                    	
                        var jsonObj = $.parseJSON($(response).text());

                        MainUtils.closeLoading();
                        if (MainUtils.processJsonResult(jsonObj, true)) {

                            if (jsonObj.dataMap.returnObj) {

                                EasyDialogUtils.closeFormDialog('#_ContractBudgetExcelDialog');
                                $('#P10019-CONTRACT_BUDGET-MainDatagrid').datagrid('reload');
                            	               
                            }
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $form.ajaxSubmit(options);
                MainUtils.openLoading();
            }
            
            return {
            	openUploadFormDialog: openUploadFormDialog,
                saveImportExcelData: saveImportExcelData
            }; 
        })(jQuery);
    }
    //]]>

    
</script>