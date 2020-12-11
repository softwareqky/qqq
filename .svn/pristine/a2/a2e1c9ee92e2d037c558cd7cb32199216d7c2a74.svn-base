<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!PURCHASE_ORDER_DETAIL) {
    	var g_planChangeOptions = [];
    	
        var PURCHASE_ORDER_DETAIL = (function($) {

            var isClickCell = false;
        	var isUnSelected = false;
            
            /**
             * 新建画面初始化的回调，用来获取SubGrid的选中记录，并设置计划名称和计划版本。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {

                var controlName = ControlUtils.getControlName(control);
                if (controlName == 'purchaseOrder_') { // 设置 申购编号

                    var row = $('#P301002-PURCHASE_ORDER-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }

                    $.ajax({
                        url: BASE_URL + "/purchase-order-detail/sub-grid-list.json",
                        type: "get",
                        dataType: "json",
                        success: function (res) {
                        	debugger;
                        	var dataArr = res.dataMap.rows;
                       		control.empty();
                            control.append('<option value="">&nbsp;</option>');
                            for (var i = 0; i < dataArr.length; i++) {
                                control.append('<option value="' + dataArr[i].id + '">' + dataArr[i].purchaseOrderNo
                                        + '</option>');
                            }

                            ControlUtils.setControlValue(control, row.id);
                        }
                	});
                    
                }
            }
            
            function openViewDialog(dialogId, url, gridId, callback, rowId) {

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
            
            function beforeInitEditFormWidget(control, isFirstLoad, response) {
            	if (ControlUtils.getControlName(control) == 'city_') {
                    var cityOptions = response.dataMap.rows;
                    control.empty();
                    // if (!control.is('[data-mandatory]')) {
                    // control.append('<option value="">&nbsp;</option>');
                    // }
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < cityOptions.length; i++) {
                        control.append('<option value="' + cityOptions[i].id + '">' + cityOptions[i].text
                                + '</option>');
                    }
                } else if (ControlUtils.getControlName(control) == 'purchaseOrder_') {
                	debugger;
                	var dataOptions = response.dataMap.updatePruchaseNo;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < dataOptions.length; i++) {
                        control.append('<option value="' + dataOptions[i].id + '">' + dataOptions[i].text
                                + '</option>');
                    }
                	
                }
            }
            
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

                var row = $('#P301002-PURCHASE_ORDER-SubDatagrid').datagrid('getSelected');
                if (!row) {
                    return;
                }
                
                // datagrid的id形如'pid-datatype-MainDatagrid'，功能按钮的id形如'pidF01'
                var idPre = 'P301001';
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

                var modifyFunc = 'disable', purchaseTypeSetFunc = 'disable';
               	if (row.flowStatus == '0') {
               		modifyFunc = 'enable';
               	}
               	if (row.flowStatus == '2') {
               		purchaseTypeSetFunc = 'enable';
               	}
               	$('#' + idPre + 'F03').linkbutton(modifyFunc);
               	$('#' + idPre + 'F02').linkbutton(modifyFunc);//删除
            	$('#' + idPre + 'F24').linkbutton(purchaseTypeSetFunc);//采购状态
            	
            	idPre = 'P301002';
            	if (row.flowStatus != '0') {
            		modifyFunc = 'disable';
	               	$('#' + idPre + 'F01').linkbutton(modifyFunc);
	               	$('#' + idPre + 'F02').linkbutton(modifyFunc);
	            	$('#' + idPre + 'F03').linkbutton(modifyFunc);
            	}
            }
            
            function openEditFormDialog(gridId, url) {

                var status;

                var $grid = $('#${idMap["sub-datagrid"]}');
                var selectedRows = $grid.datagrid('getSelections');
                var recordId = '';
                if (selectedRows.length == 1) {
                    status = selectedRows[0].reviewStatus_;
                } else {
                    if (selectedRows.length == 0) {
                        AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    } else if (selectedRows.length > 1) {
                        AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                    }
                    return;
                }

                var dialogId = "#${idMap['edit-purchase-status-form-dialog']}";

                // 无需回调，沿用所有CrudUtils默认逻辑
                CrudUtils.openEditFormDialog(dialogId, url, "#${idMap['sub-datagrid']}");
            }
            
            return {
            	openEditFormDialog: openEditFormDialog,
                onInitAddFormWidget: onInitAddFormWidget,
                openViewDialog: openViewDialog,
                handleSelect: handleSelect,
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                handleDblClickRow: handleDblClickRow
            }; // PLAN_CHANGE
        })(jQuery);
    }

    //]]>

    
</script>