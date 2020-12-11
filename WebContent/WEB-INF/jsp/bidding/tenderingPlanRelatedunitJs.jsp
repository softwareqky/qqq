<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!TENDERING_PLAN_RELATEDUNIT) {
    	var g_planChangeOptions = [];
    	
        var TENDERING_PLAN_RELATEDUNIT = (function($) {

            /**
             * 新建画面初始化的回调，用来获取SubGrid的选中记录，并设置计划名称和计划版本。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {

                var controlName = ControlUtils.getControlName(control);
                if (controlName == 'tenderingPlan_') { // 设置 申购编号

                    var row = $('#P301011-TENDERING_PLAN-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }
                    
                    $.ajax({
                        url: BASE_URL + "/tendering_plan_relatedunit/sub-grid-list.json",
                        type: "get",
                        dataType: "json",
                        success: function (res) {
                        	debugger;
                        	var dataArr = res.dataMap.rows;
                        	for (var i = 0; i<dataArr.length; i++) {
                        		control.empty();
                                control.append('<option value="">&nbsp;</option>');
                                for (var i = 0; i < dataArr.length; i++) {
                                    control.append('<option value="' + dataArr[i].id + '">' + dataArr[i].tenderingName
                                            + '</option>');
                                }

                                ControlUtils.setControlValue(control, row.id);
                        	}
                        }
                	});

                } else if (controlName == 'tenderingNo') { // 设置招标编号的内容
                    var options = {
                        url: BASE_URL + '/tendering_plan_relatedunit/generate-tendering-no.json',
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

                } else if (controlName == 'applicant_') { // 设置招标编号的内容
                    var options = {
                        url: BASE_URL + '/tendering_plan_relatedunit/get-person-info.json',
                        type: 'POST',
                        dataType: 'json',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var data = response.dataMap.returnObj;

                                ControlUtils.setControlValue(control, data.applicant_);

                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);

                } else if (controlName == 'applicantText') { // 设置招标编号的内容
                    var options = {
                        url: BASE_URL + '/tendering_plan_relatedunit/get-person-info.json',
                        type: 'POST',
                        dataType: 'json',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var data = response.dataMap.returnObj;

                                ControlUtils.setControlValue(control, data.applicantText);

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
            
            function beforeInitEditFormWidget(control, isFirstLoad, response) {
            	if (ControlUtils.getControlName(control) == 'tenderingPlan_') {
                	var dataOptions = response.dataMap.rows;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < dataOptions.length; i++) {
                        control.append('<option value="' + dataOptions[i].id + '">' + dataOptions[i].text
                                + '</option>');
                    }
                }
            }
            
            function openSelPurchaseDialog() {

                function beforeOpen(isFirstLoad) {

                    // 获取选中的单条记录id
                    var $grid = $('#P301011-TENDERING_PLAN-SubDatagrid');
                    var selectedRows = $grid.datagrid('getSelections');
                    var recordId = '';
                    if (selectedRows.length == 1) {
                        recordId = selectedRows[0].id;
                    } else {
                        if (selectedRows.length == 0) {
                            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                        } else if (selectedRows.length > 1) {
                            AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                        }
                        return false;
                    }

                    var $dialog = $('#P301011-TENDERING_PLAN_RELATEDUNIT-AddPurchaseDialog');
                    var options = $dialog.dialog('options');
                    options.queryParams.dataId = recordId;
                    
                    // 选择grid数据id保存
                    var con = {};
                    con.value = recordId;
                    $grid.data('EXTRA_FILTER_OBJ', con);

                    return true;
                }

                EasyDialogUtils.openDialog('#P301011-TENDERING_PLAN_RELATEDUNIT-AddPurchaseDialog', beforeOpen);
            }
            
            /**
             * 调用默认的GridUtils.handleSelect，并增加逻辑，特别处理删除按钮，当至少选中一条记录，且没有选中继承权限的记录时，才启用该按钮。
             */
            function handleSelect(index, row) {
            	debugger;

                var row = $('#P301011-TENDERING_PLAN-SubDatagrid').datagrid('getSelected');
                if (!row) {
                    return;
                }
                
                // datagrid的id形如'pid-datatype-MainDatagrid'，功能按钮的id形如'pidF01'
                var idPre = 'P301005';
                var modifyFunc = 'disable';
               	if (row.flowStatus == '0') {
               		modifyFunc = 'enable';
               	}
               	$('#' + idPre + 'F03').linkbutton(modifyFunc);
               	$('#' + idPre + 'F02').linkbutton(modifyFunc);//删除
               	$('#' + idPre + 'F71').linkbutton(modifyFunc);
               	$('#' + idPre + 'F23').linkbutton(modifyFunc);
            	
            	idPre = 'P301011';
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
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                openSelPurchaseDialog: openSelPurchaseDialog,
                handleSelect: handleSelect
            }; // TENDERING_PLAN_RELATEDUNIT
        })(jQuery);
    }

    //]]>

    
</script>