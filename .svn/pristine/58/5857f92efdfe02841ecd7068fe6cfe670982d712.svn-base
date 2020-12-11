<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[  
    
    if (!CAPITALPLAN) {
        var CAPITALPLAN = (function($) {
        	
          	//点修改，传递code和projectId
            function openEditFormDialog(dialogId, url, gridId, rowIndex, callback) {
          		
                if (!dialogId || !url || (!gridId && !rowIndex)) {
                    return;
                }

                // 获取选中的单条记录id
                var $grid = null;
                var recordId = null;
                if (gridId) {
                    $grid = $(gridId);
                    if ($.isNumeric(rowIndex)) {
                        recordId = $grid.datagrid('getRows')[rowIndex].code + "," + selectedRows[0].project_;
                        
                    } else {

                        var selectedRows = $grid.datagrid('getSelections');
                        if (selectedRows.length == 1) {
                            recordId = selectedRows[0].code + "," + selectedRows[0].project_;
                        } else {
                            if (selectedRows.length == 0) {
                                // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
                                AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                            } else if (selectedRows.length > 1) {
                                // $.messager.alert(LABLE_WARNING, MSG_WARN_MORE_THAN_ONE_SELECT,
                                // 'warning');
                                AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                            }
                            return;
                        }
                    }
                } else {
                    // gridId为空时，rowIndex作为recordId使用
                    recordId = rowIndex;
                }

                var $dialog = $(dialogId);

                function process(isFirstLoad, callback) {
                	debugger

                    var paramData = {};
                    paramData['id'] = recordId;
                    var options = {
                        url: url,
                        type: 'POST',
                        dataType: 'json',
                        data: paramData,
                        success: function(response, statusText, xhr, jqForm) {

                            if (MainUtils.processJsonResult(response, false)) {
                                var json = response.dataMap.returnObj;

                                $dialog.find('input[name="id"]').val(recordId);

                                // 将服务器返回的JSON加载到Form(easyui-form的load效率太低)
                                $dialog
                                        .find('[data-field-widget]')
                                        .each(
                                                function() {
                                                    var $this = $(this);

                                                    // 修改弹出画面的每个widget初始化前回调，通常用来设置字段联动
                                                    if (callback && callback.beforeInitEditFormWidget) {
                                                        callback.beforeInitEditFormWidget($this,
                                                                isFirstLoad, response);
                                                    }

                                                    var widget = $this.attr('data-field-widget');

                                                    var name = ControlUtils.getControlName($this, widget);

                                                    // 加载值
                                                    var value = json[name];
                                                    if ('filebox' == widget) {
                                                        // 按照约定，文件字段的字段名，去掉末尾的'_'，再加上List，成为文件列表的字段名
                                                        value = json[name.replace('_', '') + 'List'];
                                                    }
                                                    if (typeof value != 'undefined') {
                                                        ControlUtils.setControlValue($this, value, widget);
                                                    } else {
                                                        ControlUtils.resetControl($this, widget);
                                                    }

                                                    // 修改画面设置只读
                                                    if ($this.is('[data-edit-readonly]')) {
                                                        ControlUtils.setReadonly($this, widget);
                                                    }

                                                    if (!isFirstLoad) {
                                                        // 非首次弹出，重新加载带有url的combobox
                                                        if ($this
                                                                .is('[data-field-widget="combobox"].easyui-combobox')
                                                                && $this.combobox('options').url) {
                                                            $this.combobox('reload');
                                                        }
                                                    }

                                                    // 修改弹出画面的每个widget初始化后回调，通常用来设置字段联动
                                                    if (callback && callback.onInitEditFormWidget) {
                                                        callback.onInitEditFormWidget($this, isFirstLoad,
                                                                response);
                                                    }
                                                });

                                // 将服务端返回JSON更新到datagrid
                                if ($grid) {
                                    if (!$.isNumeric(rowIndex)) {
                                        rowIndex = $grid.datagrid('getRowIndex', recordId);
                                    }

                                    // 更新前回调
                                    var needUpdate = true;
                                    if (callback && callback.beforeOpenEditUpdateGridRow) {
                                        needUpdate = callback.beforeOpenEditUpdateGridRow($grid, rowIndex,
                                                json);
                                    }

                                    if (needUpdate) {
                                        $grid.datagrid('updateRow', {
                                            index: rowIndex,
                                            row: json
                                        });
                                    }
                                }

                                $dialog.find('form').form('disableValidation');

                                // if (dialogTitle) {
                                // var $panelHeader = $dialog.dialog('header');
                                // $panelHeader.find('div.panel-title').html(dialogTitle);
                                // }

                                if (callback && callback.beforeOpenEditFormDialog) { // 弹出前回调
                                    callback.beforeOpenEditFormDialog(json);
                                }
                                $dialog.dialog('open').dialog('center').scrollTop(0);

                                if (callback && callback.onOpenEditFormDialog) { // 弹出后回调
                                    callback.onOpenEditFormDialog(json);
                                }
                            } else {
                                // if (response.message) {
                                // $.messager.alert(LABLE_ERROR, response.message, 'error');
                                // } else {
                                // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
                                // }
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                            MainUtils.closeLoading();// 关闭loading提示
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);
                }
                MainUtils.openLoading();

                var $dialogWrapper = $dialog.parent();
                if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
                    setTimeout(function() {
                        $.parser.parse($dialogWrapper);

                        // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                        $dialogWrapper.remove();
                        $dialog.parent().prop('rendered', true);

                        if (callback && callback.onInitEditFormDialog) {
                            callback.onInitEditFormDialog();
                        }
                        process(true, callback);
                    }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
                } else {
                    process(false, callback);
                }
            }
          	
            /**
             * easyui datagrid双击数据行的事件处理，弹出修改画面。主要是为了传入callback对象。
             * 
             * 可以参考SiteController，Modle中通过key(dblClickRowHandler)来指定grid.tag中使用的双击事件处理函数。
             */
            function handleDblClickRow(index, row) {
            }
        	
          	
/*         	$("select").bind("change", function() {
        		debugger
        		
                // 获取选中的单条记录code和project
                var $grid = null;
                var code = null; 
                var projectId = null; 
                var gridId = '#P10017-BUDGET_CAPITALPLAN-MainDatagrid';
                if (gridId) {
                    $grid = $(gridId);
                        var selectedRows = $grid.datagrid('getSelections');
                        if (selectedRows.length == 1) {
                        	code = selectedRows[0].code;
                        	projectId = selectedRows[0].project_;
                        }
                }
                
                var paramData = {};
                paramData['year'] = this.value;
                paramData['code'] = code;
                paramData['projectId'] = projectId;
                
                var options = {
                        url: '/project-edge/budget/capitalPlan/getInfo.json',
                        type: 'POST',
                        dataType: 'json',
                        data: paramData,
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
	                        	$("input[name='planRemark']").val(response.dataMap.planRemark);
                            } else {
                                AlertUtils.msg(response.icon || 'error', "该年份未查到相关预算");
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);
          	}) */
            
        	
        	/**
             * 新建和修改时的回调，用来设定项目变更关联的项目。修改时，后台不应处理前台传入的project_
             */
            function beforeSubmit(options) {

                var con = $('#P10017-CAPITAL_PLAN-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (con) {
                    options.data.project_ = con.value;
                }

    			try{
    			    
    	            var isClosed = $('#_P10017-CAPITAL_PLAN-MergeScheduleFormDialog').dialog('options').closed;

    	            //弹出合并对话框，特殊处理project_字段
    	            if (!isClosed) {
    	                var rows = $('#P10017-CAPITAL_PLAN-MainDatagrid').datagrid('getSelections');
    	                if (rows.length > 0) {
    	                    options.data.project_ = rows[0].project_;

    	                    var mergeIds = [];

    	                    for ( var r in rows) {
    	                        mergeIds.push(rows[r].id);
    	                    }

    	                    options.data.mergeIds = mergeIds.join(',');
    	                }
    	            }
    			}catch(e){
    			    //do nothing
    			}

                
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

                var $dialog = $('#_PlanExcelDialog');

                var $form = $('#_PlanExcelDialogForm');

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
                var $form = $('#_PlanExcelDialogForm');

                $form.form('enableValidation');
                if (!$form.form('validate')) {
                    return;
                }
                
                var con = $('#P10001-CAPITAL_PLAN-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                    return;
                }

                var options = {
                    url: BASE_URL + '/budget/capitalPlan/import-excel-file.json?projectId='+ con.value,
                    type: 'POST',
                    dataType: 'html',
                    data: {

                    },
                    success: function(response, statusText, xhr, jqForm) {
                    	
                        var jsonObj = $.parseJSON($(response).text());

                        MainUtils.closeLoading();
                        if (MainUtils.processJsonResult(jsonObj, true)) {

                            if (jsonObj.dataMap.returnObj) {

                                EasyDialogUtils.closeFormDialog('#_PlanExcelDialog');
                                $('#P10001-CAPITAL_PLAN-MainDatagrid').datagrid('reload');
                            	               
                            }
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $form.ajaxSubmit(options);
                MainUtils.openLoading();
            }
            
            function exportExcelFile(datagridId) {
            	var con = $('#P10017-BUDGET_CAPITALPLAN-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                	AlertUtils.msg('warning', "请选择项目！");
                    return;
                }
                
                var url = BASE_URL
                        + '/budget/capitalPlan/export-excel-file.json?projectId='
                        + con.value;

                MainUtils.downloadFile(url);
            }
            
            function handleAuditClick(gridId, url) {
                // 获取选中的单条记录id
                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
                var recordId = null;
                
                var con = $('#P10017-BUDGET_CAPITALPLAN-MainDatagrid').data('EXTRA_FILTER_OBJ');
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
                paramData['projectId'] = con.value;
                MainUtils.openLoading();
                var options = {
                    url: BASE_URL + '/budget/capitalPlan/auditSubmit.json',
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
            
            return {
            	openEditFormDialog: openEditFormDialog,
            	beforeSubmit: beforeSubmit,
            	submitAddEditFormData: submitAddEditFormData,
            	openUploadFormDialog: openUploadFormDialog,
                saveImportExcelData: saveImportExcelData,
            	exportExcelFile: exportExcelFile,
            	handleAuditClick: handleAuditClick,
            	handleDblClickRow: handleDblClickRow
            }; 
        })(jQuery);
    }
    //]]>

    
</script>