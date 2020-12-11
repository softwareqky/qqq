<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    $(document).ready(function() {
    	
    	// 修改Toolbar中按钮文字
    	$('#P2001F03').html("编制");
    	
    	// 修改Dialog的标题
    	var options = $('#P2001-PROJECT-EditFormDialog').attr("data-options");
    	console.log(options);
    	options = options.replace("修改", "编制");
    	options = options.replace("maxHeight:560", "maxHeight:800");
    	$('#P2001-PROJECT-EditFormDialog').attr("data-options", options);
    	
    	$('#P2001F04').prop('onclick', null);
    	$('#P2001F04').bind(
    		'click',
    		function(e) {
    			console.log('click');
    			var callback = {
   					onOpenViewDialog: function(editProject) {
    					console.log(editProject.projectNum, editProject);
    					$('#P2001-PROJECT-ViewDialog .panel:first .panel-heading strong').html("项目信息（内部编号 " + editProject.projectNum + "）");
    				}
    			}
    			CrudUtils.openViewDialog('#P2001-PROJECT-ViewDialog', '/project-edge/project/project-init/find.json', '#P2001-PROJECT-MainDatagrid', callback);
    		}
    	);
    	
    });
    	
    if (!PROJECT_INIT) {
        var PROJECT_INIT = (function($) {
            var isClickCell = false;
        	var isUnSelected = false;

            /**
             * 新建画面初始化的回调，用来设置项目编号的内容，并刷新项目集下拉框的内容。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {

                var controlName = ControlUtils.getControlName(control);

                if (controlName == 'projectNum') { // 设置项目编号的内容
                    var options = {
                        url: BASE_URL + '/project/project-init/project-num.json',
                        type: 'POST',
                        dataType: 'json',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var pjNum = response.dataMap.returnObj;

                                ControlUtils.setControlValue(control, pjNum);

                            } else {
                                AlertUtils.msg(response.icon || 'error', response.message
                                        || MSG_REMOTE_SERVER_ERROR);
                            }
                        },
                        error: MainUtils.handleAjaxFormError
                    };
                    $.ajax(options);

                } else if (controlName == 'projectSet_') { // 刷新项目集下拉框的内容
                    var options = {
                        url: BASE_URL + '/project/project-set/list-options.json',
                        type: 'POST',
                        dataType: 'json',
                        success: function(response, statusText, xhr, jqForm) {
                            if (MainUtils.processJsonResult(response, false)) {
                                var projectSetOptions = response.dataMap.rows;
                                control.empty();
                                control.append('<option value="">&nbsp;</option>');
                                for (var i = 0; i < projectSetOptions.length; i++) {
                                    control
                                            .append('<option value="' + projectSetOptions[i].id + '">'
                                                    + projectSetOptions[i].text + '</option>');
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
             * 修改画面初始化的回调，用来刷新项目集下拉框的内容。
             * 
             * 可以参考ProjectInitController，在Controller的find方法中，需要把项目集列表返回给前端。
             */
            function beforeInitEditFormWidget(control, isFirstLoad, response) {
            	
            	if (response && response.dataMap && response.dataMap.returnObj) {
            		var editProject = response.dataMap.returnObj;
            		console.log(editProject);
                	if (editProject) {
                		$('#P2001-PROJECT-EditFormDialog form .panel:first .panel-heading strong').html("项目信息（内部编号 " + editProject.projectNum + "）");
                	}
            	}
                if (ControlUtils.getControlName(control) == 'projectSet_') {
                    var projectSetOptions = response.dataMap.rows;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < projectSetOptions.length; i++) {
                        control.append('<option value="' + projectSetOptions[i].id + '">'
                                + projectSetOptions[i].text + '</option>');
                    }
                }
            }

            /**
             * easyui datagrid双击数据行的事件处理，弹出修改画面。主要是为了传入callback对象。
             * 
             * 可以参考ProjectInitController，Modle中通过key(dblClickRowHandler)来指定grid.tag中使用的双击事件处理函数。
             */
            function handleDblClickRow(index, row) {
                
                /*
                var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
                CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, index, PROJECT_INIT);
                */
                
                var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
                CrudUtils.openViewDialog('#' + viewFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, PROJECT_INIT, row.id);
            }

            /**
             * DataGrid行数据选择事件处理
             */
            function clickCellHandler(index, field, value) {
            	var thisObj = $(this);
            	var selectedRows = $(this).datagrid('getChecked');
            	$.each(selectedRows, function(i, n) {
            		var cIndex = thisObj.datagrid('getRowIndex', n);
        			if (cIndex == index) {
            			isUnSelected = true;
            			//break;
            		}
        		})
            	isClickCell = true;
            }
            
            /**
             * 调用默认的GridUtils.handleSelect，并增加逻辑，特别处理文件上传按钮。
             */
            function handleSelect(index, row) {
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

                var rows = $(this).datagrid('getSelections');

                // datagrid的id形如'pid-datatype-MainDatagrid'，功能按钮的id形如'pidF01'
                var idPre = this.id.substring(0, this.id.indexOf('-'));

                //  文件上传
                var singleFunc = rows.length == 1
                        && (rows[0].projectStatus_ == 'PROJECT_STATUS_0'
                                || rows[0].projectStatus_ == 'PROJECT_STATUS_1' || rows[0].projectStatus_ == 'PROJECT_STATUS_2_0') ? 'enable'
                        : 'disable';
                $('#' + idPre + 'F16').linkbutton(singleFunc);

                if (rows && rows[0]) {
                    singleFunc = rows.length == 1 ? 'enable' : 'disable';
                    var flowStatusProject = rows[0].flowStatusProject;

                    if (flowStatusProject) {
                        if ('enable' == singleFunc) {
                            if (flowStatusProject == 0 || flowStatusProject == 3) {
                                $('#' + idPre + 'F71').linkbutton('enable');
                            } else {
                                $('#' + idPre + 'F71').linkbutton('disable');
                            }

                            if (flowStatusProject == 1) {
                                $('#' + idPre + 'F72').linkbutton('enable');
                            } else {
                                $('#' + idPre + 'F72').linkbutton('disable');
                            }

                            if (flowStatusProject == 1 || flowStatusProject == 2
                                    || flowStatusProject == 3) {
                                $('#' + idPre + 'F73').linkbutton('enable');
                            } else {
                                $('#' + idPre + 'F73').linkbutton('disable');
                            }
                        }
                    }else{
                        $('#' + idPre + 'F72').linkbutton('disable');
                        $('#' + idPre + 'F73').linkbutton('disable');
                    }
                }else{
                    $('#' + idPre + 'F71').linkbutton('disable');
                }
            }

            /**
             * 弹出用于修改记录的带有Form的Dialog，弹出前通过Ajax向服务端发送待查找的数据的id，并将返回自服务端的JSON结果加载到指定的Form中。
             * 根据数据记录的'项目状态'，弹出项目建议文件上传画面、项目可研文件上传画面或者项目初设文件画面其中之一。
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openUploadFormDialog(gridId, url) {

                var status;

                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
                if (selectedRows.length == 1) {
                    status = selectedRows[0].projectStatus_;
                } else {
                    if (selectedRows.length == 0) {
                        AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    } else if (selectedRows.length > 1) {
                        AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                    }
                    return;
                }

                var dialogId = "#${idMap['edit-form-dialog']}";
                if (status == 'PROJECT_STATUS_0') { // 建议
                    dialogId += '-Proposal';
                } else if (status == 'PROJECT_STATUS_1') { // 可研
                    dialogId += '-Feasibility';
                } else if (status == 'PROJECT_STATUS_2_0') { // 初设
                    dialogId += '-PreliminaryDesign';
                } else {
                    return;
                }

                // 无需回调，沿用所有CrudUtils默认逻辑
                CrudUtils.openEditFormDialog(dialogId, url, "#${idMap['main-datagrid']}");
            }

            /**
             * 将datagrid中选中的记录批量关注，进行Ajax提交。
             * 
             * @param url
             * @param datagridId 待刷新并清除选中的datagrid的ID。如果非空，则在ajax成功后，刷新并清除选中datagrid。CSS
             *            selector，形如"#myDialogId"
             */
            function batchConcernSelected(url, datagridId) {

                if (!url || !datagridId) {
                    return;
                }

                var $grid = $(datagridId);
                var selectedRows = $grid.datagrid('getSelections');

                // 必须选中至少一条记录
                selectedRows = $(datagridId).datagrid('getSelections');
                if (selectedRows.length > 0) {
                    $.messager.confirm(LABLE_CONFIRM, MSG_CONFIRM_CONCERN, function(r) {
                        if (r) {
                            var idArray = [];
                            for (var i = 0; i < selectedRows.length; i++) {
                                idArray.push(selectedRows[i].id);
                            }
                            _doBatchConcern(url, idArray.join(), datagridId);
                        }
                    });
                } else {
                    // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
                    AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    return;
                }
            }

            /** ********************** */
            /* private functions below */
            /** ********************** */

            /**
             * 批量删除指定的记录，进行Ajax提交。服务端必须对名为"idsToDelete"的query string进行处理。
             * 
             * @param url
             * @param idsToConcern 逗号分隔的待删除记录的id字符串
             * @param datagridId 待刷新并清除选中的datagrid的ID。如果非空，则在ajax成功后，刷新并清除选中datagrid。CSS
             *            selector，形如"#myDialogId"
             */
            function _doBatchConcern(url, idsToConcern, datagridId) {
                if (!url || !idsToConcern) {
                    return;
                }

                var options = {
                    url: url,
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        // 服务端必须对名为"idsToDelete"的query string进行处理
                        idsToConcern: idsToConcern
                    },
                    success: function(response, statusText, xhr, jqForm) {
                        MainUtils.closeLoading();// 关闭loading提示
                        if (MainUtils.processJsonResult(response, true)) {
                            if (datagridId) {
                                $(datagridId).datagrid('reload');// 刷新easyui datagrid
                                // TODO 删除后，datagrid加载成功后会自动清除选中，此处是否还要再手动调用一次？
                                $(datagridId).datagrid('clearSelections');// 必须清除easyui datagrid选中
                            }
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);// jquery.form plugin
                MainUtils.openLoading();// 弹出loading提示
            }

            /**
             * 将datagrid中选中的记录批量关注，进行Ajax提交。
             * 
             * @param url
             * @param datagridId 待刷新并清除选中的datagrid的ID。如果非空，则在ajax成功后，刷新并清除选中datagrid。CSS
             *            selector，形如"#myDialogId"
             */
            function batchNoConcernSelected(url, datagridId) {
                if (!url || !datagridId) {
                    return;
                }

                // 必须选中至少一条记录
                var selectedRows = $(datagridId).datagrid('getSelections');
                if (selectedRows.length > 0) {
                    $.messager.confirm(LABLE_CONFIRM, MSG_CONFIRM_NO_CONCERN, function(r) {
                        if (r) {
                            var idArray = [];
                            for (var i = 0; i < selectedRows.length; i++) {
                                idArray.push(selectedRows[i].id);
                            }
                            _doBatchNoConcern(url, idArray.join(), datagridId);
                        }
                    });
                } else {
                    // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
                    AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    return;
                }
            }

            /** ********************** */
            /* private functions below */
            /** ********************** */

            /**
             * 批量删除指定的记录，进行Ajax提交。服务端必须对名为"idsToDelete"的query string进行处理。
             * 
             * @param url
             * @param idsToConcern 逗号分隔的待删除记录的id字符串
             * @param datagridId 待刷新并清除选中的datagrid的ID。如果非空，则在ajax成功后，刷新并清除选中datagrid。CSS
             *            selector，形如"#myDialogId"
             */
            function _doBatchNoConcern(url, idsToNoConcern, datagridId) {
                if (!url || !idsToNoConcern) {
                    return;
                }

                var options = {
                    url: url,
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        // 服务端必须对名为"idsToDelete"的query string进行处理
                        idsToNoConcern: idsToNoConcern
                    },
                    success: function(response, statusText, xhr, jqForm) {
                        MainUtils.closeLoading();// 关闭loading提示
                        if (MainUtils.processJsonResult(response, true)) {
                            if (datagridId) {
                                $(datagridId).datagrid('reload');// 刷新easyui datagrid
                                // TODO 删除后，datagrid加载成功后会自动清除选中，此处是否还要再手动调用一次？
                                $(datagridId).datagrid('clearSelections');// 必须清除easyui datagrid选中
                            }
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);// jquery.form plugin
                MainUtils.openLoading();// 弹出loading提示
            }

            /**
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openImportFormDialog(gridId) {

                var $dialog = $('#_ProjectExcelDialog');

                var $form = $('#_ProjectExcelDialogForm');

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
                var $form = $('#_ProjectExcelDialogForm');

                $form.form('enableValidation');
                if (!$form.form('validate')) {
                    return;
                }

                var options = {
                    url: BASE_URL + '/project/project-init/import-excel-file.json',
                    type: 'POST',
                    dataType: 'html',
                    data: {

                    },
                    success: function(response, statusText, xhr, jqForm) {
                        var jsonObj = $.parseJSON($(response).text());

                        MainUtils.closeLoading();
                        if (MainUtils.processJsonResult(jsonObj, true)) {

                            if (jsonObj.dataMap.returnObj) {

                                EasyDialogUtils.closeFormDialog('#_ProjectExcelDialog');
                                $('#P2001-PROJECT-MainDatagrid').datagrid('reload');
                            }
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $form.ajaxSubmit(options);
                MainUtils.openLoading();
            }

            function emportData(datagridId) {

                var $form = $('#P2001-PROJECT-FilterForm');
                // 用来保存普通检索内容的数组，其中每个成员的结构同FilterFieldInfoDto.java
                var SEARCH_ARRAY = new Array();

                $form.find('span[data-value-type]').each(function() {
                    var cb = $(this);
                    var con = {};
                    con.fieldName = cb.attr('data-name');
                    con.valueType = cb.attr('data-value-type');
                    con.filterSearchBy = cb.attr('data-filter-search-by');
                    con.valueMatchMode = cb.attr('data-value-match-mode');

                    if (con.filterSearchBy == '1') { // 按值过滤，只有一个控件

                        // 兼容带按钮的textbox，弹出选择画面控件
                        var control = cb.parent().find('[data-field-control]');
                        con.value = ControlUtils.getControlValue(control);
                        if (con.value) { // 各种input和checkbox取到的值都是字符串，所以不会出现false和数值0
                            SEARCH_ARRAY.push(con);
                        }

                    } else { // 按范围过滤，有from和to两个控件
                        var controls = cb.nextAll('[data-field-control]');
                        con.from = ControlUtils.getControlValue(controls.first());
                        con.to = ControlUtils.getControlValue(controls.last());

                        if (con.from || con.to) {
                            SEARCH_ARRAY.push(con);
                        }
                    }
                });

                var projectName = "";
                var projectNum = "";
                var governmentProjectNum = "";

                if (SEARCH_ARRAY.length > 0) {
                    $.each(SEARCH_ARRAY, function(i, item) {
                        if (item.fieldName == "projectName")
                            projectName = item.value;
                        if (item.fieldName == "projectNum")
                            projectNum = item.value;
                        if (item.fieldName == "governmentProjectNum")
                            governmentProjectNum = item.value;
                    });
                }

                var url = BASE_URL + '/project/project-init/export-excel-file.json?projectNum='
                        + projectNum + '&projectName=' + projectName + '&governmentProjectNum='
                        + governmentProjectNum;

                MainUtils.downloadFile(url);

            }

            return {
                onInitAddFormWidget: onInitAddFormWidget,
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                handleDblClickRow: handleDblClickRow,
                handleSelect: handleSelect,
                clickCellHandler: clickCellHandler,
                openUploadFormDialog: openUploadFormDialog,
                batchConcernSelected: batchConcernSelected,
                batchNoConcernSelected: batchNoConcernSelected,
                openImportFormDialog: openImportFormDialog,
                saveImportExcelData: saveImportExcelData,
                emportData: emportData
            }; // PROJECT_INIT
        })(jQuery);
    }

    //]]>

    
</script>