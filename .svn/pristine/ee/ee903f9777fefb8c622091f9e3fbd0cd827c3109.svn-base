<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    	
    document.ready = function() {
    	$('#P110505F06').linkbutton('enable');
    }
    
    $(function() {
    	setTimeout(function() {
    		
    		// 查看历史记录
    		$('#P110505F74').click(function() {
        		const selectedRow= $('#P110505-SEGMENT-MainDatagrid').datagrid('getSelected');
        		console.log(selectedRow);
        		if (selectedRow) {
        			viewSegmentHistoryLog(selectedRow.id);
        		}
        	})
    		
    		$('#P110505-SEGMENT-MainDatagrid').datagrid({
        		onLoadSuccess: function(data) {
        			initializeGanttAndMapField();
        		}
        	});
    		
    		observeDatagridReload();
    		
    		/* $('#P110505-SEGMENT-MainDatagridToolbar>table>tbody>tr').append(
       			'<td>' +
       				'<a class="easyui-linkbutton l-btn l-btn-small l-btn-plain" data-options="iconCls: \'icon-func-authority\', plain: \'true\'" role="button" onclick="handleGenerateAll()" group>' +
       					'<span class="l-btn-left l-btn-icon-left">' +
       						'<span class="l-btn-text">建设计划一键生成</span>' +
       						'<span class="l-btn-icon icon-func-authority">&nbsp;</span>' +
       					'</span>' +
       				'</a>' +
       			'</td>'
       		); */
    		
    	}, 500);
    })
    
    /**
     * 查看历史记录
     */
    function viewSegmentHistoryLog(recordId) {
    	
    	$('#_SegmentHistoryLogDialog').window('open');
    	$('#_SegmentHistoryLogTable').datagrid({
    		url: "/project-edge/facility/segment/history.json?targetId=" + recordId,
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
    
    function observeDatagridReload() {
    	
    	// 处理说明
		// 在重新显示Tab页后，重新载入datagrid数据
		const panel = $('#P110505-SEGMENT-MainDatagridToolbar').parent().parent().parent().parent().parent().parent().parent();
		
		const MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
		const observer = new MutationObserver(function(mutations) {
			
			for (var i = 0; i < mutations.length; i++) {
				const mutation = mutations[i];
				if (mutation.type == 'attributes' && mutation.attributeName == 'style') {
					const style = panel.attr('style');
					if (style.indexOf('block') > 0 && lastDisplay == 'none') {
						$('#P110505-SEGMENT-MainDatagrid').datagrid('reload');
						lastDisplay = 'block';
					} else {
						lastDisplay = 'none';
					}
					return;
				}
			}
		});
		
		observer.observe(panel[0], { attributes: true });
    }
    
    function initializeGanttAndMapField() {
    	
    	$(".datagrid-body div[class*='location']").html("<span style='text-align: center; color: blue; cursor: pointer'>快速定位</span>");
		$(".datagrid-body div[class*='location']").click(function() {
			setTimeout(function() {
				const selectedRow= $('#P110505-SEGMENT-MainDatagrid').datagrid('getSelected');
				viewInMap(selectedRow.latitude, selectedRow.longitude);
				$('#P110505-SEGMENT-MainDatagrid').datagrid('unselectAll');
			});
		});
		
		$(".datagrid-body div[class*='progress']").attr({ style: 'text-align: center; color: blue; cursor: pointer' });
		$(".datagrid-body div[class*='progress']").click(function() {
			setTimeout(function() {
				const selectedRow= $('#P110505-SEGMENT-MainDatagrid').datagrid('getSelected');
				viewGantt(selectedRow.id);
				$('#P110505-SEGMENT-MainDatagrid').datagrid('unselectAll');
			});
		});
    }
    
    function handleGenerateAll() {
    	
    	MainUtils.openLoading();
    	const options = {
                url: BASE_URL + '/facility/segment/regenerateAll.json',
                type: 'POST',
                contentType: 'application/json',
                success: function(response, statusText, xhr, jqForm) {
                	 MainUtils.closeLoading();
                },
                error: MainUtils.handleAjaxFormError
    	};
    	
    	$.ajax(options);
    }
    
    function viewInMap(latitude, longitude) {
    	EasyDialogUtils.openMaxModal(BASE_URL + '/map-home.htm?latitude=' + latitude + '&longitude=' + longitude, '地图');
    }
    
 	function viewGantt(segmentId) {
    	
    	const options = {
                url: BASE_URL + '/facility/segment/get-plan.json?segmentId=' + segmentId,
                type: 'GET',
                contentType: 'application/json',
                success: function(response, statusText, xhr, jqForm) {
                    const _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=2&planId=' + response.dataMap.planId;
            		EasyDialogUtils.openMaxModal(_url, '查看任务');
                }
    	};
    	
    	$.ajax(options);
    }

    var SEGMENT = (function($) {
    	
    	function exportExcel(gridId) {
    		const url = BASE_URL + '/facility/segment/export-excel.json';
    		MainUtils.downloadFile(url);
    	}

        /**
         * 
         * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
         * @param url
         */
        function openUploadFormDialog(gridId) {

            var $dialog = $('#_SegmentExcelDialog');

            var $form = $('#_SegmentExcelDialogForm');

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
        
        /**
         * 弹出编辑框的回调
         */
        function onInitEditFormWidget(control, isFirstLoad, response) {
        	
        	const fieldName = $(control).attr('name');
        	
        	if (fieldName == 'progress') {
        		$(control).attr('disabled', 'disabled');
        		return;
        	}
        }

        function saveImportExcelData() {
            var $form = $('#_SegmentExcelDialogForm');

            $form.form('enableValidation');
            if (!$form.form('validate')) {
                return;
            }

            var options = {
                url: BASE_URL + '/facility/segment/import-excel-file.json',
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
                    		$('#_SegmentExcelParseDialog').window('open');
                    		$('#_SegmentExcelParseErrors').datagrid({
                    			data: jsonObj.dataMap.returnObj,
                    			columns: [[
                    				{ field: 'row', title: '行', width: 50 },
                    				{ field: 'column', title: '列', width: 50 },
                    				{ field: 'message', title: '详情', width: 500 },
                    			]]
                    		})
                    	}
                    	else if (jsonObj.dataMap.returnObj) {
                    		
                    		EasyDialogUtils.closeFormDialog('#_SegmentExcelDialog');
                            $('#P110505-SEGMENT-MainDatagrid').datagrid('reload');
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
            onInitEditFormWidget: onInitEditFormWidget,
            exportExcel: exportExcel
        }; // Segment
    })(jQuery);
    //]]>

    
</script>