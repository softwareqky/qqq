<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    if (!ARCHIVE_AUTHORITY) {
        var ARCHIVE_AUTHORITY = (function($) {
            var isClickCell = false;
        	var isUnSelected = false;

            /**
             * 双击行时，传入回调对象。
             */
            function handleDblClickRow(index, row) {
                
                /*
                var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
                CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, index, ARCHIVE_AUTHORITY);
                */
                
                var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
                CrudUtils.openViewDialog('#' + viewFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, ARCHIVE_AUTHORITY, row.id);
            }

            /**
             * 在选择人员后的回调，根据所选人员新建档案权限。
             * 
             * @param rows 选择的人员记录
             */
            function onChooseRows(rows) {

                var $grid = $('#P9002-ARCHIVE_AUTHORITY-MainDatagrid');
                var con = $grid.data('EXTRA_FILTER_OBJ');

                var param = {};
                param.archive_ = con.value;
                param.personList = [];

                $.each(rows, function(i, n) {
                    param.personList.push(n.id);
                });

                var options = {
                    url: "${urlMap['add']}",
                    type: 'POST',
                    dataType: 'json',
                    data: param,
                    traditional: true,
                    success: function(response, statusText, xhr, jqForm) {
                        MainUtils.closeLoading(); // 关闭loading提示
                        if (MainUtils.processJsonResult(response, true)) {

                            // 刷新grid
                            $grid.datagrid('reload');
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };

                $.ajax(options);
                MainUtils.openLoading();// 弹出loading提示
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

                var func = 'enable';
                if (rows.length == 0) {
                    func = 'disable';
                } else {
                    for (var i = 0; i < rows.length; i++) {
                        if (rows[i].isInherit == 1) {
                            func = 'disable';
                            break;
                        }
                    }
                }

                // 删除
                $('#' + idPre + 'F02').linkbutton(func);
            }

            /**
             * 打开修改弹出画面时，不更新grid中的记录。因为后台的find方法，不处理"继承自"字段，如果更新，会清空grid中的"继承自"的值。
             */
            function beforeOpenEditUpdateGridRow($grid, rowIndex, json) {
                return false;
            }

            /**
             * 修改时的回调，用来设定档案权限关联的档案。修改时，后台不应处理前台传入的archive_
             */
            function beforeSubmit(options) {
                var con = $('#P9002-ARCHIVE_AUTHORITY-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (con) {
                    options.data.archive_ = con.value;
                }
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
                handleDblClickRow: handleDblClickRow,
                onChooseRows: onChooseRows,
                handleSelect: handleSelect,
                beforeOpenEditUpdateGridRow: beforeOpenEditUpdateGridRow,
                beforeSubmit: beforeSubmit,
                onDatagridUpdated: onDatagridUpdated,
            	clickCellHandler: clickCellHandler
            }; // ARCHIVE_AUTHORITY
        })(jQuery);
    }
    //]]>

    
</script>