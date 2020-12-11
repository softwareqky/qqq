<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    	
    $(function() {
    	setTimeout(function() {
    		
    		// 查看历史记录
    		$('#P110510F74').click(function() {
        		const selectedRow= $('#P110510-LINK-MainDatagrid').datagrid('getSelected');
        		console.log(selectedRow);
        		if (selectedRow) {
        			viewLinkHistoryLog(selectedRow.id);
        		}
        	})
    		
    	}, 500);
    })
    
    /**
     * 查看历史记录
     */
    function viewLinkHistoryLog(recordId) {
    	
    	$('#_LinkHistoryLogDialog').window('open');
    	$('#_LinkHistoryLogTable').datagrid({
    		url: "/project-edge/facility/link/history.json?targetId=" + recordId,
  			method: "GET",
  			singleSelect: true,
  			pageSize: 8,
  			pageNumber: 1,
  			pagination: true,
  			fitColumns: true,
  			columns: [[
  				{ 
  					field: 'createType', title: '操作类型', width: 100, 
  					formatter: function(value) {
  						if (value == 0) return '新增';
  						if (value == 1) return '修改';
  						if (value == 2) return '删除';
  						else return '其它';
  					}	
  				},
  				{ field: 'content', title: '操作内容', width: 600 },
  				{ field: 'personInChargeText', title: '操作人', width: 100 },
  				{ field: 'createAt', title: '操作日期', width: 100 }
  			]],
  			onClickRow: function(index, data) {
  				const contents = data.content.split('\n');
  				$('#_historyWindow').html(contents.map(c => '<br>' + c + '</br>'));
  				$('#_historyWindow').window('open');
  			},
  			loadFilter: function(data) {
  				if (data.status == 1) {
  					return data.dataMap;
  				} else {
  					return []
  				}
  			},
    	})
    }

    var LINK = (function($) {

    	
    	function exportExcel(gridId) {
    		const url = BASE_URL + '/facility/link/export-excel.json';
    		MainUtils.downloadFile(url);
    	}
    	
        /**
         * 
         * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
         * @param url
         */
        function openUploadFormDialog(gridId) {

            var $dialog = $('#_LinkExcelDialog');

            var $form = $('#_LinkExcelDialogForm');

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
            var $form = $('#_LinkExcelDialogForm');

            $form.form('enableValidation');
            if (!$form.form('validate')) {
                return;
            }

            var options = {
                url: BASE_URL + '/facility/link/import-excel-file.json',
                type: 'POST',
                dataType: 'html',
                data: {

                },
                success: function(response, statusText, xhr, jqForm) {
                    var jsonObj = $.parseJSON($(response).text());

                    MainUtils.closeLoading();
                    if (MainUtils.processJsonResult(jsonObj, true)) {

                    	// 如果解析错误
                    	if (jsonObj.message == 'ParseError') {
                    		console.log(jsonObj.dataMap.returnObj);
                    		$('#_LinkExcelParseDialog').window('open');
                    		$('#_LinkExcelParseErrors').datagrid({
                    			data: jsonObj.dataMap.returnObj,
                    			columns: [[
                    				{ field: 'row', title: '行', width: 50 },
                    				{ field: 'column', title: '列', width: 50 },
                    				{ field: 'message', title: '详情', width: 500 },
                    			]]
                    		})
                    	}
                        if (jsonObj.dataMap.returnObj) {

                            EasyDialogUtils.closeFormDialog('#_LinkExcelDialog');
                            $('#P110510-LINK-MainDatagrid').datagrid('reload');
                        }
                    }
                },
                error: MainUtils.handleAjaxFormError
            };
            $form.ajaxSubmit(options);
            MainUtils.openLoading();
        }

        function onDatagridInserted(gridId, json){
            var $grid = $(gridId);
            $grid.datagrid('reload');
        }
        
        function onDatagridUpdated(gridId, json){
            var $grid = $(gridId);
            $grid.datagrid('reload');
        }
        
        return {
            openUploadFormDialog: openUploadFormDialog,
            saveImportExcelData: saveImportExcelData,
            onDatagridInserted: onDatagridInserted,
            onDatagridUpdated: onDatagridUpdated,
            exportExcel: exportExcel
        }; // Link
    })(jQuery);
    //]]>

    
</script>