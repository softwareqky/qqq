<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!NOTICE_DETAIL) {
        var NOTICE_DETAIL = (function($) {
            var isClickCell = false;
        	var isUnSelected = false;

            /**
             * 弹出用于修改记录的带有Form的Dialog，弹出前通过Ajax向服务端发送待查找的数据的id，并将返回自服务端的JSON结果加载到指定的Form中。
             * 根据数据记录的'项目状态'，弹出项目建议文件上传画面、项目可研文件上传画面或者项目初设文件画面其中之一。
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            /* function onInitEditFormWidget(control, isFirstLoad, response) {
alert(22222);
            	const fieldName = $(control).attr('project_');$(control).attr('disabled', 'disabled');
            	
            } */
            
            /**
             * 调用默认的GridUtils.handleSelect，并增加逻辑，特别处理删除按钮，当至少选中一条记录，且没有选中继承权限的记录时，才启用该按钮。
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
                var modifyFunc = 'disable';
                var rows = $(this).datagrid('getSelections');
               	if (rows.length == 1) {
               		for (var i = 0; i < rows.length; i++) {
                    	if (rows[i].publishText == '未发布') {
                    		// 流程状态：未提交
                    		modifyFunc = 'enable';
                    	}
                    }
               		
               	}
               	var idPre = this.id.substring(0, this.id.indexOf('-'));
               	$('#' + idPre + 'F03').linkbutton(modifyFunc);
               	$('#' + idPre + 'F02').linkbutton(modifyFunc);//删除
               	$('#' + idPre + 'F06').linkbutton(modifyFunc);
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
            
            function beforeInitEditFormWidget(control, isFirstLoad, response) {
                if (ControlUtils.getControlName(control) == 'recivers_') {
                    var obj = response.dataMap.returnObj;
                    var project = obj.project_;
                    var virtualOrg = obj.recivers_;
                    if (project != null && project != "") {
                        var options = {
                            url: BASE_URL + '/project/virtual-org/list-pid-options.json',
                            type: 'POST',
                            dataType: 'json',
                            data: {
                                project_: project
                            },
                            success: function(res, statusText, xhr, jqForm) {
                                if (MainUtils.processJsonResult(res, false)) {
                                    var dataOptions = res.dataMap.rows;

                                    control.append('<option value="">&nbsp;</option>');
                                    for (var i = 0; i < dataOptions.length; i++) {
                                    	if (virtualOrg != null && virtualOrg != "") {
                                    		if (virtualOrg == dataOptions[i].id) {
                                    			control.append('<option selected value="' + dataOptions[i].id + '">' + dataOptions[i].text
                                                        + '</option>');
                                    		} else {
                                    			control.append('<option value="' + dataOptions[i].id + '">' + dataOptions[i].text
                                                        + '</option>');
                                    		}
                                    	} else {
                                        	control.append('<option value="' + dataOptions[i].id + '">' + dataOptions[i].text
                                                + '</option>');
                                    	}
                                    }
                                }
                            },
                            error: MainUtils.handleAjaxFormError
                        };
                        $.ajax(options);
                    }
                    control.empty();

                    
                }
            }

            function onDatagridInserted(gridId, json){
            	debugger;
                var $grid = $(gridId);
                $grid.datagrid('reload');
            }
            
            function onDatagridUpdated(gridId, json){
            	debugger;
                var $grid = $(gridId);
                $grid.datagrid('reload');
            }
            
            return {
                handleSelect: handleSelect,
                clickCellHandler: clickCellHandler,
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                onDatagridInserted: onDatagridInserted,
                onDatagridUpdated: onDatagridUpdated
            }; // ISSUE
        })(jQuery);
    }

    //]]>

    
</script>