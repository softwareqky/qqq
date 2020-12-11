<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!PAYMENT_CONTRACT) {
    	
        var PAYMENT_CONTRACT = (function($) {

            /**
             * 新建画面初始化的回调，用来获取SubGrid的选中记录，并设置计划名称和计划版本。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {
            	debugger;

                var controlName = ControlUtils.getControlName(control);
                if (controlName == 'entryPerson_') {
                    var options = {
                        url: BASE_URL + '/payment-contract-statement/get-person-info.json',
                        type: 'POST',
                        dataType: 'json',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var data = response.dataMap.returnObj;

                                ControlUtils.setControlValue(control, data.entryPerson_);

                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);

                } else if (controlName == 'entryPersonText') { // 设置招标编号的内容
                    var options = {
                        url: BASE_URL + '/payment-contract-statement/get-person-info.json',
                        type: 'POST',
                        dataType: 'json',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var data = response.dataMap.returnObj;

                                ControlUtils.setControlValue(control, data.entryPersonText);

                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);

                } else if (controlName == 'serialNumber') { // 设置合同流水
                    var options = {
                        url: BASE_URL + '/payment-contract-statement/generate-serial-no.json',
                        type: 'POST',
                        dataType: 'json',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var dataNum = response.dataMap.returnObj;

                                ControlUtils.setControlValue(control, dataNum);

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
             * 调用默认的GridUtils.handleSelect，并增加逻辑，特别处理删除按钮，当至少选中一条记录，且没有选中继承权限的记录时，才启用该按钮。
             */
            function handleSelect(index, row) {
            	debugger;

                var row = $('#P4010-PAYMENT_CONTRACT-SubDatagrid').datagrid('getSelected');
                if (!row) {
                    return;
                }
                
                // datagrid的id形如'pid-datatype-MainDatagrid'，功能按钮的id形如'pidF01'
                var idPre = 'P4001';
                var modifyFunc = 'disable';
               	if (row.flowStatus == '0') {
               		modifyFunc = 'enable';
               	}
               	$('#' + idPre + 'F03').linkbutton(modifyFunc);
               	$('#' + idPre + 'F02').linkbutton(modifyFunc);//删除
            	$('#' + idPre + 'F71').linkbutton(modifyFunc);//审批
            	
            	idPre = 'P4010';
            	modifyFunc = 'disable';
               	if (row.flowStatus == '0') {
               		modifyFunc = 'enable';
               	}
               	$('#' + idPre + 'F01').linkbutton(modifyFunc);
               	$('#' + idPre + 'F02').linkbutton(modifyFunc);
            	$('#' + idPre + 'F03').linkbutton(modifyFunc);
            }
            
            return {
                onInitAddFormWidget: onInitAddFormWidget,
                handleSelect: handleSelect
            }; // TENDERING_PLAN_RELATEDUNIT
        })(jQuery);
    }

    //]]>

    
</script>