<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    if (!PaperLibraryLendHistory) {
        var PaperLibraryLendHistory = (function($) {
            var isClickCell = false;
        	var isUnSelected = false;

            /**
             * 借阅领取
             */
            function openEditFormDialog(gridId, url) {
            	debugger;
            	var ul = "${urlMap['edit-lend']}";
            	var status;
                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
                if (selectedRows.length == 1) {
                    status = selectedRows[0].status;
                } else {
                    if (selectedRows.length == 0) {
                        AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    } else if (selectedRows.length > 1) {
                        AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                    }
                    return;
                }

                var dialogId = "#${idMap['edit-form-dialog']}";
                if (status == '2') { // 待领取
                    dialogId += '-Take';
                } else if (status == '3') { // 借出中
                    dialogId += '-Return';
                } else {
                    return;
                }

                // 无需回调，沿用所有CrudUtils默认逻辑
                CrudUtils.openEditFormDialog(dialogId, url, "#${idMap['main-datagrid']}", null, PaperLibraryLendHistory);
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

                var rows = $(this).datagrid('getSelections');

                // datagrid的id形如'pid-datatype-MainDatagrid'，功能按钮的id形如'pidF01'
                var idPre = this.id.substring(0, this.id.indexOf('-'));

                var takeFunc = 'disable';
                var returnFunc = 'disable';
                if (rows.length == 1) {
                    for (var i = 0; i < rows.length; i++) {
                    	if (rows[i].status == '2') {
                    		// 0-在库，1-借出申请中，2-待领取，3-借出中
                    		takeFunc = 'enable';
                    		break;
                    	} else if (rows[i].status == '3') {
                    		// 0-在库，1-借出申请中，2-待领取，3-借出中
                    		returnFunc = 'enable';
                    		break;
                    	}
                    }
                }

                $('#' + idPre + 'F33').linkbutton(takeFunc); //借阅领取
                $('#' + idPre + 'F34').linkbutton(returnFunc); //借阅返还
            }
            
            /**
             * 修改成功后回调。刷新grid，如果修改继承的权限，则实际是新建，此时id与修改提交的id是不同的，因此必须刷新整个grid。
             */
            function onDatagridUpdated(datagridId, json) {
                $(datagridId).datagrid('reload');
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
            
            return {
            	openEditFormDialog: openEditFormDialog,
            	onDatagridUpdated: onDatagridUpdated,
            	handleSelect: handleSelect,
            	clickCellHandler: clickCellHandler
            }; // PaperLibraryLendHistory
        })(jQuery);
    }
    //]]>

    
</script>