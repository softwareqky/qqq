<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    	
    var date = new Date();
	this.year = date.getFullYear();
    $("[name='year'] option:contains("+this.year+")").prop("selected", true);
         
    if (!BUDGETCHANGE) {
        var BUDGETCHANGE = (function($) {
            /**
             * SubGrid的选中和不选中的事件处理，不联动MainGrid，仅控制按钮。
             * 
             * @param index 同easyui的datagrid的onSelect事件
             * @param row
             */
            function handleSubGridSelect(index, row) {
                var $this = $(this);
                var rows = $this.datagrid('getSelections');

                // 查看，检索，清除检索，提交审核
                var singleFunc = rows.length == 1 ? 'enable' : 'disable';
                $('#P10012F04').linkbutton(singleFunc);
                $('#P10012F08').linkbutton(singleFunc);
                $('#P10012F09').linkbutton(singleFunc);
                $('#P10012F71').linkbutton(singleFunc);
                $('#P10012F72').linkbutton(singleFunc);
            }
        	
        	
        	
        	 function handleAuditClick(gridId, url) {
                 // 获取选中的单条记录id
                 var $grid = $(gridId);
                 var selectedRows = $grid.datagrid('getSelections');
                 var recordId = null;
                 
                 var $form = $('#P10012-BUDGET_ESTIMATE_CHANGE-FilterForm');
                 if (!$form.form('validate')) {
                     return;
                 }
                 var year = $('#P10012-BUDGET_ESTIMATE_CHANGE-FilterForm select[name=year] option:checked').val();
                 
                 var con = $('#P10012-BUDGET_ESTIMATE_CHANGE-MainDatagrid').data('EXTRA_FILTER_OBJ');
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
                 MainUtils.openLoading()
                 var options = {
                         url: BASE_URL + '/budget/budgetEstimateChange/auditSubmit.json',
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
                 var con = $('#P10012-BUDGET_ESTIMATE_CHANGE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                 if (!con) {
                     return;
                 }
                 
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
                         url: BASE_URL + '/budget/budgetEstimateChange/buildVersion.json',
                         type: 'POST',
                         dataType: 'json',
                         data: {
 							projectId:con.value,recordId:recordId
                         },
                         success: function(response, statusText, xhr, jqForm) {
                         	MainUtils.closeLoading();// 关闭loading提示
                            AlertUtils.msg("OK", response.message);
                         },
                         error: MainUtils.handleAjaxFormError
                     };
                 $.ajax(options);
                 MainUtils.openLoading();
             }
        	 
        	 
            return {
            	handleAuditClick: handleAuditClick,
            	buildVersion: buildVersion
            }; // SITE
        })(jQuery);
    }
    //]]>

    
</script>