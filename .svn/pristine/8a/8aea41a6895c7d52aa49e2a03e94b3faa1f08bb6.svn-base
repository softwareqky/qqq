<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[  
/*     var date = new Date();
	this.year = date.getFullYear();
    $("[name='year'] option:contains("+this.year+")").prop("selected", true);  */
    
    if (!BUDGET_ESTIMATE) {
        var BUDGET_ESTIMATE = (function($) {
        	
            var isClickCell = false;
        	var isUnSelected = false;
        	
            /**
             * 新建画面初始化的回调，用来设置项目编号的内容，并刷新项目集下拉框的内容。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {
            	debugger

                var controlName = ControlUtils.getControlName(control);
                if (controlName == 'budgetEstimateMain_') {
                    var row = $('#P10003-BUDGET_ESTIMATE_MAIN-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }
                    
                    $.ajax({
                        url: BASE_URL + "/budget/budgetEstimate/sub-grid-list.json",
                        type: "get",
                        dataType: "json",
                        success: function (res) {
                        	debugger;
                        	var dataArr = res.dataMap.rows;
                        	for (var i = 0; i<dataArr.length; i++) {
                        		control.empty();
                                control.append('<option value="">&nbsp;</option>');
                                for (var i = 0; i < dataArr.length; i++) {
                                    control.append('<option value="' + dataArr[i].id + '">' + dataArr[i].id
                                            + '</option>');
                                }

                                ControlUtils.setControlValue(control, row.id);
                        	}
                        }
                	});
                }
            }
            
            /**
             * 调用默认的GridUtils.handleSelect，并增加逻辑，特别处理删除按钮，当至少选中一条记录，且没有选中继承权限的记录时，才启用该按钮。
             */
            function handleSelect(index, row) {
            	debugger
            	// Grid行选择动作调整
            	if (isClickCell) {
                	isClickCell = false;
                	if (!isUnSelected) {
                		// onSelect
                		$(this).datagrid('unselectAll');
                		$(this).datagrid('selectRow', index);
                	} else {
                		// onUnSelect
                		isUnSelected = false;
                		$(this).datagrid('unselectRow', index);
                	}
                }
            	
                GridUtils.handleSelect.apply(this, arguments);

                var row = $('#P10003-BUDGET_ESTIMATE_MAIN-SubDatagrid').datagrid('getSelected');
                if (!row) {
                    return;
                }
                
                var paramData = {};
                paramData['id'] = row.id;
                
/*                 //重新去获取主表记录的相关数据
                $.ajax({
                    url: BASE_URL + "/budget/budgetEstimate/sub-grid-find.json",
                    type: "get",
                    data: paramData,
                    dataType: "json",
                    async: false,
                    success: function (res) {
                    	debugger;
                    	row.flowStatus = res.dataMap.returnObj.flowStatus;
                    	console.log(row.flowStatus)
                    }
            	}); */
                
                // datagrid的id形如'pid-datatype-MainDatagrid'，功能按钮的id形如'pidF01'
                var idPre = 'P10024';
//                var auditSubmitFunc = 'disable';
//               	if ((row.flowStatus == '0')||(row.flowStatus == '3')) {
//               		// 流程状态：未提交
//               		auditSubmitFunc = 'enable';
//               	}else{
//               	    auditSubmitFunc = 'disable';
//               	}
//
//                // 提交审核
//                $('#' + idPre + 'F71').linkbutton(auditSubmitFunc);

                var modifyFunc = 'disable';
               	if (row.flowStatus == '0') {
               		modifyFunc = 'enable';
               	}
               	$('#' + idPre + 'F03').linkbutton(modifyFunc);
               	$('#' + idPre + 'F02').linkbutton(modifyFunc);//删除
               	$('#' + idPre + 'F71').linkbutton(modifyFunc);//审核
            	
            	idPre = 'P10024';
            	if (row.flowStatus != '0') {
            		modifyFunc = 'disable';
	               	//$('#' + idPre + 'F01').linkbutton(modifyFunc);
	               	$('#' + idPre + 'F02').linkbutton(modifyFunc);
	            	$('#' + idPre + 'F03').linkbutton(modifyFunc);
	            	$('#' + idPre + 'F71').linkbutton(modifyFunc);
            	}
            	if (row.flowStatus == '1') {
            		$('#' + idPre + 'F72').linkbutton('enable');
            	}
            	if (row.flowStatus == '2') {
            		$('#' + idPre + 'F72').linkbutton(modifyFunc);
            	}
            	
            	
            	idPre = 'P10003';
            	if (row.flowStatus == '0' || row.flowStatus == '2' || row.flowStatus == '3') {
	            	$('#' + idPre + 'F05').linkbutton("enable");
            	}
            	else{
            		$('#' + idPre + 'F05').linkbutton("disable");
	            	$('#' + idPre + 'F03').linkbutton("disable");
            	}
            	
            }
            
            function openViewDialog(dialogId, url, gridId, callback, rowId) {
            	debugger

                // 获取选中的单条记录id
                var $grid = $('#${idMap["sub-datagrid"]}');
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

                try {
                  var $subgrid = $('#${idMap["view-grid-dialog-id"]}');
                  $subgrid.datagrid('reload');
                } catch (e) {
                	// 初次加载由于grid未渲染，无法进行reload
                }
                
            	CrudUtils.openViewDialog(dialogId, url, gridId, callback, rowId);
            }
            
            function handleDblClickRow(index, row) {
				try {
                  var $subgrid = $('#${idMap["view-grid-dialog-id"]}');
                  $subgrid.datagrid('reload');
                } catch (e) {
                	// 初次加载由于grid未渲染，无法进行reload
                }
                
                var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
                CrudUtils.openViewDialog('#${idMap["sub-grid-view-grid-dialog"]}',
                		"${urlMap['sub-grid-find']}", '#${idMap["sub-datagrid"]}', null, null);
            }
        	
            /**
             * 重写FilterUtils.submitAddEditFormData。
             */
            
        	function submitAddEditFormData(dialogId, datagridId, isFile, isAdd, isContinuousAdd, callback) {

                if (!dialogId) {
                    return;
                }

                // easyui form仅仅用到了validate、reset和load等方法(以$('...').form('...')的形式调用)，
                // 其他form相关的都使用jQuery.form
                var $dialog = $(dialogId);
                var $form = $dialog.find('form');

                var url = $form.attr('action');
                if (!url) {
                    return;
                }

                $form.form('enableValidation');
                if (!$form.form('validate')) {

                    // 新建/修改验证失败后回调
                    if (callback && $.isFunction(callback.onInvalidData)) {
                        callback.onInvalidData();
                    }
                    return;
                }

                // 如果checkbox未选中，则直接提交form时该值不会传到后台。此处额外处理，将未选中的checkbox也提交至后台。
                var param = {};
                // TODO
                if (callback && $.isFunction(callback.initSaveDataParam)) {
                    callback.initSaveDataParam(param);
                }

                // $form.find('input[data-field-widget="checkbox"]').not(':checked').each(function() {
                // var n = this.name;
                // if (!n || this.disabled) {
                // return;
                // }
                //
                // param[n] = ControlUtils.getControlValue($(this), 'checkbox');
                // });
                $form.find('input[data-field-widget="checkbox"]').each(function() {
                    var $this = $(this);
                    var n = ControlUtils.getControlName($this);
                    var v = ControlUtils.getControlValue($this, 'checkbox');
                    if (!n || $this.switchbutton('options').disabled || v === '1') {
                        return;
                    }

                    param[n] = v;
                });

                var options = {
                    url: url,
                    dataType: isFile ? 'html' : 'json',
                    data: param,
                    success: function(response, statusText, xhr, jqForm) {
                        MainUtils.closeLoading();// 关闭loading提示

                        var jsonObj = response;
                        if (isFile) {
                            jsonObj = $.parseJSON($(response).text());
                        }

                        if (MainUtils.processJsonResult(jsonObj, true)) {
                        	debugger

                            var json = jsonObj.dataMap.returnObj;

                            // 新增后将数据插入datagrid首行，修改后更新datagrid中相应行
                            if (isAdd) {
                                var needInsertRow = true;
                                if (callback && $.isFunction(callback.beforeDatagridInsert)) {
                                    needInsertRow = callback.beforeDatagridInsert(datagridId, json) === true;
                                }

                                if (needInsertRow && datagridId) {
                                    $(datagridId).datagrid('insertRow', {
                                        index: 0, // index start with 0
                                        row: json
                                    }).datagrid('clearSelections');
                                }

                                // 新增记录保存成功后回调
                                if (callback && $.isFunction(callback.onDatagridInserted)) {
                                    callback.onDatagridInserted(datagridId, json);
                                }
                            } else {
                                if (datagridId) {
                                    $(datagridId).datagrid('reload');// 刷新easyui datagrid
                                    $('#P10003-BUDGET_ESTIMATE_MAIN-SubDatagrid').datagrid('reload');//上面一个grid也刷新
                                    // TODO 删除后，datagrid加载成功后会自动清除选中，此处是否还要再手动调用一次？
                                    $(datagridId).datagrid('clearSelections');// 必须清除easyui datagrid选中}
                                }

                                // 修改记录保存成功后回调
                                if (callback && $.isFunction(callback.onDatagridUpdated)) {
                                    callback.onDatagridUpdated(datagridId, json);
                                }
                            }

                            if (isAdd && isContinuousAdd) { // 只有在连续新增时，才不关闭弹出画面

                                // 禁用验证
                                $form.form('disableValidation');

                                // 处理默认值和复制，并重置
                                // 是否选中复制
                                var isCopy = $dialog.next().find('input[data-copy-reserve-checkbox]').prop(
                                        'checked');

                                $form.find('[data-field-widget]').each(function() {
                                    var $this = $(this);
                                    var widget = $this.attr('data-field-widget');

                                    var val = null;
                                    var useCurrentValue = false;
                                    if (isCopy && $this.is('[data-copy-reserve]')) {
                                        useCurrentValue = true;
                                    } else if ($this.is('[data-default-value]')) {
                                        val = $this.attr('data-default-value');
                                    }

                                    if (!useCurrentValue) {
                                        if (val) {
                                            ControlUtils.setControlValue($this, val, widget);
                                        } else {
                                            ControlUtils.resetControl($this, widget);
                                        }
                                    }

                                    // 新建弹出画面的每个widget初始化后回调
                                    if (callback && $.isFunction(callback.onInitAddFormWidget)) {
                                        callback.onInitAddFormWidget($this, false);
                                    }
                                });

                                // 连续新增点击后回调
                                if (callback && $.isFunction(callback.onContinuousAdd)) {
                                    callback.onContinuousAdd();
                                }

                            } else {
                                EasyDialogUtils.closeFormDialog(dialogId);
                            }
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };

                // 提交新建/修改前回调
                if (callback && $.isFunction(callback.beforeSubmit)) {
                    callback.beforeSubmit(options, isAdd, isContinuousAdd);
                }
                $form.ajaxSubmit(options);// jquery.form plugin
                MainUtils.openLoading();// 弹出loading提示
            } 
            
            /**
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openUploadFormDialog(gridId) {
            	debugger
            	
                var row = $('#P10003-BUDGET_ESTIMATE_MAIN-SubDatagrid').datagrid('getSelected');
                if (!row) {
                	AlertUtils.msg('warning', "请选择年度预算！");
                    return;
                }
            	
                var $dialog = $('#_BudgetExcelDialog');

                var $form = $('#_BudgetExcelDialogForm');

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
                var $form = $('#_BudgetExcelDialogForm');

                $form.form('enableValidation');
                if (!$form.form('validate')) {
                    return;
                }
                
                var row = $('#P10003-BUDGET_ESTIMATE_MAIN-SubDatagrid').datagrid('getSelected');
                if (!row) {
                    return;
                }

                var options = {
                    url: BASE_URL + '/budget/budgetEstimate/import-excel-file.json?projectId=' + row.project_ + "&mainId=" + row.id,
                    type: 'POST',
                    dataType: 'html',
                    data: {

                    },
                    success: function(response, statusText, xhr, jqForm) {
                    	
                        var jsonObj = $.parseJSON($(response).text());

                        MainUtils.closeLoading();
                        if (MainUtils.processJsonResult(jsonObj, true)) {

                            if (jsonObj.dataMap.returnObj) {

                                EasyDialogUtils.closeFormDialog('#_BudgetExcelDialog');
                                $('#P10003-BUDGET_ESTIMATE-MainDatagrid').datagrid('reload');
                                $('#P10003-BUDGET_ESTIMATE_MAIN-SubDatagrid').datagrid('reload');
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
        	  
                var con = $('#P10003-BUDGET_ESTIMATE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                	AlertUtils.msg('warning', "请选择项目！");
                    return;
                }

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
                var con = $('#P10003-BUDGET_ESTIMATE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }
                var data = $('#_BudgetExcelExportDialogForm').serialize();
                var url = BASE_URL + '/budget/budgetEstimate/export-excel-file.json?'+data+'&project_='+ con.value;
                MainUtils.downloadFile(url);
                EasyDialogUtils.closeFormDialog('#_BudgetExcelExportDialog');
            }
            
            /* function exportData() {
                var combobox=$('#_BudgetExcelExportCombobox').combobox('getValue'); 
                var exporturl = BASE_URL + '/budget/budgetEstimate/export-excel-file.json';
                var exportFinalurl = BASE_URL + '/budget/budgetEstimate/export-final-excel-file.json';
                if(combobox==1){
                    MainUtils.downloadFile(exporturl);
                }
                if(combobox==2){
                    MainUtils.downloadFile(exportFinalurl);
                }
                EasyDialogUtils.closeFormDialog('#_BudgetExcelExportDialog');
            } */

            /* function exportData() {
                var url = BASE_URL + '/budget/budgetEstimate/export-excel-file.json';

                MainUtils.downloadFile(url);
            } */
            function handleAuditClick(gridId, url) {
                // 获取选中的单条记录id
                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
                var recordId = null;
                
                var $form = $('#P10003-BUDGET_ESTIMATE-FilterForm');
                if (!$form.form('validate')) {
                    return;
                }
                var year = $('#P10003-BUDGET_ESTIMATE-FilterForm select[name=year] option:checked').val();
                
                var con = $('#P10003-BUDGET_ESTIMATE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }
                
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
                paramData['year'] = year;
                paramData['projectId'] = con.value;
                MainUtils.openLoading();
                var options = {
                    url: BASE_URL + '/budget/budgetEstimate/auditSubmit.json',
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
            
            function buildVersion(gridId) {
            	debugger
                var con = $('#P10003-BUDGET_ESTIMATE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }
                
                var $form = $('#P10003-BUDGET_ESTIMATE-FilterForm');
                if (!$form.form('validate')) {
                    return;
                }
                var year = $('#P10003-BUDGET_ESTIMATE-FilterForm select[name=year] option:checked').val();
                
                // 获取选中的单条记录id
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
                
                var options = {
                        url: BASE_URL + '/budget/budgetEstimate/buildVersion.json',
                        type: 'POST',
                        dataType: 'json',
                        data: {
							projectId:con.value,recordId:recordId,year:year
                        },
                        success: function(response, statusText, xhr, jqForm) {
                        	MainUtils.closeLoading();// 关闭loading提示
                            AlertUtils.msg("OK", response.message);
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                $.ajax(options);
                MainUtils.openLoading();// 弹出loading提示
            }
            
            
            function handleAudit(gridId, url) {
                var row = $('#P10003-BUDGET_ESTIMATE_MAIN-SubDatagrid').datagrid('getSelected');
                if (!row) {
                    return;
                }
                
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
                
                //审核中的记录不可再提交
                if(row.flowStatus == 1){
                	AlertUtils.msg('info', "该记录正在审核中，请勿重复提交！");
                	return;
                }
                
                var paramData = {};
                paramData['id'] = row.id;
                paramData['year'] = row.year;
                paramData['projectId'] = row.project_;
                MainUtils.openLoading();
                var options = {
                    url: BASE_URL + '/budget/budgetEstimate/submitReview.json',
                    type: 'POST',
                    dataType: 'json',
                    data: paramData,
                    success: function(response, statusText, xhr, jqForm) {
                    	debugger
                    	MainUtils.closeLoading();
                    	
                    	if(response.status == 0){//专业组的走内部审批
                    		FlowableUtils.openSubmitAuditFormDialog('#P10003-BUDGET_ESTIMATE_MAIN-SubmitAuditFormDialog','#P10003-BUDGET_ESTIMATE_MAIN-FlowDetailViewDiv','#P10003-BUDGET_ESTIMATE_MAIN-FlowableSettingCombobox')
                    	}
                    	else if(response.status == 1){//项目办走oa审批
                    		MainUtils.openLoading();
                            var options = {
                                    url: BASE_URL + '/budget/budgetEstimate/auditSubmit.json',
                                    type: 'POST',
                                    dataType: 'json',
                                    data: paramData,
                                    success: function(response, statusText, xhr, jqForm) {
                                    	MainUtils.closeLoading();
                                        if (MainUtils.processJsonResult(response, false)) {
                                            $('#P10003-BUDGET_ESTIMATE_MAIN-SubDatagrid').datagrid('reload');
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
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);
            }
            
            function openEditFormDialog(gridId, url) {
            	
                AlertUtils.msg('info', "修改预算需重新提交审核数据！");
                
                setTimeout(function() {
                    // 无需回调，沿用所有CrudUtils默认逻辑
                    CrudUtils.openEditFormDialog("#P10003-BUDGET_ESTIMATE-EditFormDialog", "/project-edge/budget/budgetEstimate/find.json", "#P10003-BUDGET_ESTIMATE-MainDatagrid");
                }, 3000);
                
            }
            
            /**
             * 将datagrid中选中的记录批量删除，进行Ajax提交。成功刷新两个grid
             * 
             * @param url
             * @param datagridId 待刷新并清除选中的datagrid的ID。如果非空，则在ajax成功后，刷新并清除选中datagrid。CSS
             *            selector，形如"#myDialogId"
             */
            /* function batchDeleteSelected(url, datagridId) {
                if (!url || !datagridId) {
                    return;
                }

                // 必须选中至少一条记录
                var selectedRows = $(datagridId).datagrid('getSelections');
                if (selectedRows.length > 0) {
                    $.messager.confirm(LABLE_CONFIRM, MSG_CONFIRM_DELETE, function(r) {
                        if (r) {
                            var idArray = [];
                            for (var i = 0; i < selectedRows.length; i++) {
                                idArray.push(selectedRows[i].id);
                            }
                            CrudUtils.doBatchDelete(url, idArray.join(), datagridId);
                            $('#P10003-BUDGET_ESTIMATE-MainDatagrid').datagrid('reload');
                        }
                    });
                } else {
                    // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
                    AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    return;
                }
            } */
                
            return {
            	//batchDeleteSelected: batchDeleteSelected,
            	submitAddEditFormData: submitAddEditFormData,
            	openEditFormDialog: openEditFormDialog,
            	handleAudit: handleAudit,
                openUploadFormDialog: openUploadFormDialog,
                onInitAddFormWidget: onInitAddFormWidget,
                saveImportExcelData: saveImportExcelData,
                openExportFormDialog: openExportFormDialog,
                exportData:exportData,
                handleAuditClick: handleAuditClick,
                buildVersion: buildVersion,
                openViewDialog: openViewDialog,
                handleDblClickRow: handleDblClickRow,
                handleSelect: handleSelect
            }; // SITE
        })(jQuery);
    }
    //]]>

    
</script>