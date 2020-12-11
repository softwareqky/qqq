<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!REVIEW) {
        var REVIEW = (function($) {
            var isClickCell = false;
        	var isUnSelected = false;

        	/**
             * 弹出用于修改记录的带有Form的Dialog，弹出前通过Ajax向服务端发送待查找的数据的id，并将返回自服务端的JSON结果加载到指定的Form中。
             * 根据数据记录的'项目状态'，弹出项目建议文件上传画面、项目可研文件上传画面或者项目初设文件画面其中之一。
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openEditFormDialog(gridId, url) {

                var status;

                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
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

                var dialogId = "#${idMap['edit-form-dialog']}";
                if (status == 'CHECK_STATUS_1') { // 解决
                    dialogId += '-Improve';
                } else if (status == 'CHECK_STATUS_2') { // 验证
                    dialogId += '-Verification';
                } else {
                    return;
                }

                // 无需回调，沿用所有CrudUtils默认逻辑
                CrudUtils.openEditFormDialog(dialogId, url, "#${idMap['main-datagrid']}");
            }
        	
            /**
             * 打开专家选择画面。
             */
            function openSelExpertDialog() {

                function beforeOpen(isFirstLoad) {

                    // 获取选中的单条记录id
                    var $grid = $('#${idMap["main-datagrid"]}');
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

                    var $dialog = $('#${idMap["sel-expert-dialog"]}');
                    var options = $dialog.dialog('options');
                    options.queryParams.reviewId = recordId;
                    
                    // 选择grid数据id保存
                    var con = {};
                    con.value = recordId;
                    $grid.data('EXTRA_FILTER_OBJ', con);

                    return true;
                }

                EasyDialogUtils.openDialog('#${idMap["sel-expert-dialog"]}', beforeOpen);
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

                var auditSubmitFunc = 'disable';
                var expertSelFunc = 'disable';
                var expertReviewFunc = 'disable';
                var improveFunc = 'disable';
                var verificationFunc = 'disable';
                if (rows.length == 1) {
                    for (var i = 0; i < rows.length; i++) {
                    	if (rows[i].flowStatus == '0') {
                    		// 流程状态：未提交
                    		auditSubmitFunc = 'enable';
                    	}
                    	
                    	//if (rows[i].flowStatus == '0')
                    	if (rows[i].reviewStatus_ == 'CHECK_STATUS_0' && rows[i].flowStatus == 2) { // 待审批
                    		expertSelFunc = 'enable';
                    		expertReviewFunc = 'enable';
                            break;
                    	} else if (rows[i].reviewStatus_ == 'CHECK_STATUS_1') { // 待整改
                        	improveFunc = 'enable';
                            break;
                        } else if (rows[i].reviewStatus_ == 'CHECK_STATUS_2') { // 待验证
                        	verificationFunc = 'enable';
                            break;
                        }
                    }
                }

                // 提交审核
                $('#' + idPre + 'F71').linkbutton(auditSubmitFunc);
                // 专家选择
                $('#' + idPre + 'F19').linkbutton(expertSelFunc);
             	// 待审批
             	$('#' + idPre + 'F20').linkbutton(expertReviewFunc);
                // 整改
                $('#' + idPre + 'F31').linkbutton(improveFunc);
                // 验证
                $('#' + idPre + 'F32').linkbutton(verificationFunc);
            }
            
            function openEditExpertReviewFormDialog(gridId, url) {
            	debugger;

                var status;
                var $grid = $(gridId);
                var selectedRows = $grid.datagrid('getSelections');
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

                var dialogId = "#${idMap['edit-form-dialog']}";
                if (status == 'CHECK_STATUS_0') { // 待审批
                    dialogId += '-ExpertReview';
                } else {
                    return;
                }

                // 无需回调，沿用所有CrudUtils默认逻辑
                CrudUtils.openEditFormDialog(dialogId, url, "#${idMap['main-datagrid']}");
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
                openSelExpertDialog: openSelExpertDialog,
                openEditFormDialog: openEditFormDialog,
                handleSelect: handleSelect,
                openEditExpertReviewFormDialog: openEditExpertReviewFormDialog,
            	clickCellHandler: clickCellHandler
            }; // REVIEW
        })(jQuery);
    }

    //]]>

    
</script>