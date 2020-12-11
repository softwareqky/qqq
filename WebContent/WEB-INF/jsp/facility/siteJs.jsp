<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    //<![CDATA[
    	
    var lastDisplay = '';
    	
    document.ready = function() {
    	$('#P110501F06').linkbutton('enable');
    }
    
    $(function() {
    	
    	setTimeout(function() {
    		
    		console.log('reload');
    		
    		// 查看历史记录
    		$('#P110501F74').click(function() {
        		const selectedRow= $('#P110501-SITE-MainDatagrid').datagrid('getSelected');
        		console.log(selectedRow);
        		if (selectedRow) {
        			viewSiteHistoryLog(selectedRow.id);
        		}
        	})
        	
        	
    		$('#P110501-SITE-MainDatagrid').datagrid({
        		onLoadSuccess: function(data) {
        			initializeGanttAndMapField();
        		}
        	});
    		
    		observeDatagridReload();
    		
    		/* $('#P110501-SITE-MainDatagridToolbar>table>tbody>tr').append(
    			'<td>' +
    				'<a class="easyui-linkbutton l-btn l-btn-small l-btn-plain" data-options="iconCls: \'icon-func-authority\', plain: \'true\'" role="button" onclick="handleGenerateAll()" group>' +
    					'<span class="l-btn-left l-btn-icon-left">' +
    						'<span class="l-btn-text">建设计划一键生成</span>' +
    						'<span class="l-btn-icon icon-func-authority">&nbsp;</span>' +
    					'</span>' +
    				'</a>' +
    			'</td>'
    		); */
    		
    	}, 400);
    	
    	/* setTimeout(function() {
    		$('#P110501-SITE-EditFormDialog').dialog({
    			onBeforeOpen: function() {
        			console.log('Dialog loaded');
        			return true;
        		}
        	})
    	}, 500); */
    	
    	
    });
    
    /**
     * 查看历史记录
     */
    function viewSiteHistoryLog(recordId) {
    	
    	$('#_SiteHistoryLogDialog').window('open');
    	$('#_SiteHistoryLogTable').datagrid({
    		url: "/project-edge/facility/site/history.json?targetId=" + recordId,
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
		const panel = $('#P110501-SITE-MainDatagridToolbar').parent().parent().parent().parent().parent().parent().parent();
		
		const MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
		const observer = new MutationObserver(function(mutations) {
			
			for (var i = 0; i < mutations.length; i++) {
				const mutation = mutations[i];
				if (mutation.type == 'attributes' && mutation.attributeName == 'style') {
					const style = panel.attr('style');
					if (style.indexOf('block') > 0 && lastDisplay == 'none') {
						$('#P110501-SITE-MainDatagrid').datagrid('reload');
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
    
    function handleGenerateAll() {
    	
    	MainUtils.openLoading();
    	const options = {
                url: BASE_URL + '/facility/site/regenerateAll.json',
                type: 'POST',
                contentType: 'application/json',
                success: function(response, statusText, xhr, jqForm) {
                	 MainUtils.closeLoading();
                },
                error: MainUtils.handleAjaxFormError
    	};
    	
    	$.ajax(options);
    }
    
    function initializeGanttAndMapField() {
    	
    	$(".datagrid-body div[class*='location']").html("<span style='text-align: center; color: blue; cursor: pointer'>地图定位</span>");
		$(".datagrid-body div[class*='location']").click(function() {
			setTimeout(function() {
				const selectedRow= $('#P110501-SITE-MainDatagrid').datagrid('getSelected');
				viewInMap(selectedRow.latitude, selectedRow.longitude);
				$('#P110501-SITE-MainDatagrid').datagrid('unselectAll');
			}, 200);
		});
		
		$(".datagrid-body div[class*='sdnNetworkProgress']").attr({ style: 'text-align: center; color: blue; cursor: pointer' });
		$(".datagrid-body div[class*='sdnNetworkProgress']").click(function() {
			setTimeout(function() {
				const selectedRow= $('#P110501-SITE-MainDatagrid').datagrid('getSelected');
				viewGantt(selectedRow.id, 'sdn');
				$('#P110501-SITE-MainDatagrid').datagrid('unselectAll');
			});
		});
		
		$(".datagrid-body div[class*='programmableNetworkProgress']").attr({ style: 'text-align: center; color: blue; cursor: pointer' });
		$(".datagrid-body div[class*='programmableNetworkProgress']").click(function() {
			console.log($(this).text());
			setTimeout(function() {
				const selectedRow= $('#P110501-SITE-MainDatagrid').datagrid('getSelected');
				viewGantt(selectedRow.id, 'programmable');
				$('#P110501-SITE-MainDatagrid').datagrid('unselectAll');
			});
		});
		
		$(".datagrid-body div[class*='basicNetworkProgress']").attr({ style: 'text-align: center; color: blue; cursor: pointer' });
		$(".datagrid-body div[class*='basicNetworkProgress']").click(function() {
			console.log($(this).text());
			setTimeout(function() {
				const selectedRow= $('#P110501-SITE-MainDatagrid').datagrid('getSelected');
				viewGantt(selectedRow.id, 'basic');
				$('#P110501-SITE-MainDatagrid').datagrid('unselectAll');
			});
		});
    }
    
    function viewInMap(latitude, longitude) {
    	EasyDialogUtils.openMaxModal(BASE_URL + '/map-home.htm?latitude=' + latitude + '&longitude=' + longitude, '地图');
    }
    
    function viewGantt(siteId, type) {
    	
    	const options = {
                url: BASE_URL + '/facility/site/get-plan.json?siteId=' + siteId + '&type=' + type,
                type: 'GET',
                contentType: 'application/json',
                success: function(response, statusText, xhr, jqForm) {
                	if (response.dataMap.planId) {
                		const _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=2&planId=' + response.dataMap.planId;
                		EasyDialogUtils.openMaxModal(_url, '查看任务');
                	}
                }
    	};
    	
    	$.ajax(options);
    }

    if (!SITE) {
        var SITE = (function($) {
        	
        	var isClickCell = false;
        	var isUnSelected = false;
        	
        	function exportExcel(gridId) {
        		const url = BASE_URL + '/facility/site/export-excel.json';
        		MainUtils.downloadFile(url);
        	}

            /**
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openUploadFormDialog(gridId) {

                var $dialog = $('#_SiteExcelDialog');

                var $form = $('#_SiteExcelDialogForm');

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
                var $form = $('#_SiteExcelDialogForm');

                $form.form('enableValidation');
                if (!$form.form('validate')) {
                    return;
                }

                var options = {
                    url: BASE_URL + '/facility/site/import-excel-file.json',
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
                        		$('#_SiteExcelParseDialog').window('open');
                        		$('#_SiteExcelParseErrors').datagrid({
                        			data: jsonObj.dataMap.returnObj,
                        			columns: [[
                        				{ field: 'row', title: '行', width: 50 },
                        				{ field: 'column', title: '列', width: 50 },
                        				{ field: 'message', title: '详情', width: 500 },
                        			]]
                        		})
                        	}
                        	else if (jsonObj.dataMap.returnObj) {

                                EasyDialogUtils.closeFormDialog('#_SiteExcelDialog');
                                $('#P110501-SITE-MainDatagrid').datagrid('reload');
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
            
            /**
             * 省份下拉框的选择事件处理，联动获取城市列表。站点画面以及站点弹出选择画面都要使用。
             * 
             * @param target 输入控件
             */
            function handleSelectProvince(target) {

                var $p = $(target);
                var province = $p.val();

                var options = {
                    url: BASE_URL + '/system/city/list-options.json',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        provinceId: province
                    },
                    success: function(response, statusText, xhr, jqForm) {
                        if (MainUtils.processJsonResult(response, false)) {
                            var cityOptions = response.dataMap.rows;

                            var $c = $p.closest('form').find('select[name="city_"]');
                            $c.empty();
                            // if (!$c.is('[data-mandatory]')) {
                            // $c.append('<option value="">&nbsp;</option>');
                            // }
                            $c.append('<option value="">&nbsp;</option>');
                            for (var i = 0; i < cityOptions.length; i++) {
                                $c.append('<option value="' + cityOptions[i].id + '">'
                                        + cityOptions[i].text + '</option>');
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
                        // MainUtils.closeLoading();// 关闭loading提示
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);
                // MainUtils.openLoading();// 弹出loading提示
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

                var row = $('#P110501-SITE-MainDatagrid').datagrid('getSelected');
                if (!row) {
                    return;
                }
                
                var paramData = {};
                paramData['id'] = row.id;
                
                // datagrid的id形如'pid-datatype-MainDatagrid'，功能按钮的id形如'pidF01'
                var idPre = 'P110501';
                var modifyFunc = 'disable';
               	if (row.flowStatus == '0') {
               		modifyFunc = 'enable';
               	}
               	$('#' + idPre + 'F03').linkbutton(modifyFunc);
               	$('#' + idPre + 'F02').linkbutton(modifyFunc);//删除
               	$('#' + idPre + 'F71').linkbutton(modifyFunc);//审核
            	
            	if (row.flowStatus != '0') {
            		modifyFunc = 'disable';
	               	//$('#' + idPre + 'F01').linkbutton(modifyFunc);
	               	$('#' + idPre + 'F02').linkbutton(modifyFunc);
	            	$('#' + idPre + 'F03').linkbutton(modifyFunc);
	            	$('#' + idPre + 'F71').linkbutton(modifyFunc);
            	}
            	if (row.flowStatus == '1') {
            		$('#' + idPre + 'F72').linkbutton('enable');
            	}
            	if (row.flowStatus == '2') {
            		$('#' + idPre + 'F72').linkbutton(modifyFunc);
            		$('#' + idPre + 'F03').linkbutton('enable');
            	}
            	
            	
            }

            /**
             * 新建画面初始化的回调，用来清空城市下拉框的内容。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {
                if (ControlUtils.getControlName(control) == 'city_') {
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                }
                
                initAddMap();
            }
            
            /**
             * 弹出编辑框的回调
             */
            function onInitEditFormWidget(control, isFirstLoad, response) {
            	
            	const dialogId = '#P110501-SITE-EditFormDialog';
            	const $dialog = $(dialogId);
                
            	const fieldName = $(control).attr('name');
            	
            	// 判断边缘节点类型
            	const programmableEdgeNodeFields = [
            		'programmableMainTrunkNodeDistance', 'programmableMainTrunkNodeModel',
            		'programmableDirectConnNodeDataDevicePower', 'programmableEdgeNodeRackPower'
            	];
            	const sdnEdgeNodeFields = [
            		'sdnMainTrunkNodeDistance', 'sdnMainTrunkNodeModel',
            		'sdnDirectConnNodeDataDevicePower', 'sdnEdgeNodeRackPower'
            	];
            	
            	const edgeNodeType = response.dataMap.returnObj.edgeNodeType_;
            	
            	if (programmableEdgeNodeFields.indexOf(fieldName) >= 0) {
            		console.log(edgeNodeType);
            		if (edgeNodeType != 'EDGE_NODE_TYPE_1') {
            			$(control).attr('disabled', 'disabled');
            			return;
            		}
            	}
            	
            	if (sdnEdgeNodeFields.indexOf(fieldName) >= 0) {
            		console.log(edgeNodeType);
            		if (edgeNodeType != 'EDGE_NODE_TYPE_0') {
            			$(control).attr('disabled', 'disabled');
            			return;
            		}
            	}
            	
            	
            	if (fieldName == 'basicNetworkProgress' || fieldName == 'sdnNetworkProgress' || fieldName == 'programmableNetworkProgress') {
            		$(control).attr('disabled', 'disabled');
            		return;
            	}
            	
            	if (fieldName == 'basicNetworkTransmitStationType') {
            		$(control).change(function() {
            			const result = $(this).children('option:selected').val();
            			
            			if (result == 1) {
            				console.log('enabled');
            				$dialog.find('[name="dataNetworkNodeType"]').removeAttr('disabled');
            			} else {
            				console.log('disabled');
            				$dialog.find('[name="dataNetworkNodeType"]').val('');
            				$dialog.find('[name="dataNetworkNodeType"]').attr('disabled', 'disabled');
            			}
            		});
            	}
				
				// 如果站点类型不是核心节点，数通网节点类型不可编辑
				if (fieldName == 'dataNetworkNodeType') {
					if (response.dataMap.returnObj.basicNetworkTransmitStationType != 1) {
						$(control).attr('disabled', 'disabled');
					} else {
						$(control).removeAttr('disabled');
					}
				}
				
				if (fieldName == 'networkRoomOwnerUnitType_') {
					$(control).change(function() {
						
						const result = $(this).children('option:selected').val();
                        
                        console.log(result);
                        if (result == 'NETWORK_ROOM_OWNER_UNIT_TYPE_3') {
                        	$dialog.find('[name="siteDivideDate"]').parent().prev().datebox('enable');
                        	$dialog.find('[name="isDivideSite"]').parent().parent().prev().switchbutton('enable');
                        	$dialog.find('[name="siteDividePersonInCharge_"]').next().find('.btn').removeAttr('disabled');
                        	$dialog.find('[name="siteDivideLocation"]').removeAttr('disabled');
                        } else {
                        	$dialog.find('[name="siteDivideDate"]').parent().prev().datebox('disable');
            				$dialog.find('[name="isDivideSite"]').parent().parent().prev().switchbutton('disable');
            				$dialog.find('[name="siteDividePersonInCharge_"]').next().find('.btn').attr('disabled', 'disabled');
            				$dialog.find('[name="siteDivideLocation"]').val('');
            				$dialog.find('[name="siteDivideLocation"]').attr('disabled', 'disabled');
                        }
					});
				}
            	
            	// 如果机房业主单位类型不是【中交节点】，裂站相关字段不可编辑
            	if (fieldName == 'networkRoomOwnerUnitType_' && response.dataMap.returnObj.networkRoomOwnerUnitType_ != 'NETWORK_ROOM_OWNER_UNIT_TYPE_3') {
            		$dialog.find('[name="siteDivideDate"]').parent().prev().datebox('disable');
            		$dialog.find('[name="isDivideSite"]').parent().parent().prev().switchbutton('disable');
         			$dialog.find('[name="siteDividePersonInCharge_"]').next().find('.btn').attr('disabled', 'disabled');
         			$dialog.find('[name="siteDividePersonInChargeText"]').val('');
         			$dialog.find('[name="siteDivideLocation"]').attr('disabled', 'disabled');
				}
            }

            /**
             * 修改画面初始化的回调，用来设置城市下拉框的内容。
             * 
             * 可以参考SiteController，在Controller的find方法中，需要把该数据所属的省份对应的城市列表返回给前端。
             */
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
                }
                
                var dialogId = '#P110501-SITE-EditFormDialog';
                var $dialog = $(dialogId);
                var longitude = $dialog.find('[name="longitude"]').val();
                var latitude = $dialog.find('[name="latitude"]').val();
                
                setOriginalMarker(longitude,latitude);
            }

            /**
             * easyui datagrid双击数据行的事件处理，弹出修改画面。主要是为了传入callback对象。
             * 
             * 可以参考SiteController，Modle中通过key(dblClickRowHandler)来指定grid.tag中使用的双击事件处理函数。
             */
            function handleDblClickRow(index, row) {
            	
                var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
                CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url.replace(
                        'list', 'find'), '#' + this.id, index, SITE);
                /* 
                var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
                CrudUtils.openViewDialog('#' + viewFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, ProvinceCityUtils, row.id); */
            }

            return {
                openUploadFormDialog: openUploadFormDialog,
                saveImportExcelData: saveImportExcelData,
                onDatagridInserted: onDatagridInserted,
                onDatagridUpdated: onDatagridUpdated,
                handleSelectProvince: handleSelectProvince,
                handleSelect: handleSelect,
                onInitAddFormWidget: onInitAddFormWidget,
                beforeInitEditFormWidget: beforeInitEditFormWidget,
                onInitEditFormWidget: onInitEditFormWidget,
                handleDblClickRow: handleDblClickRow,
                exportExcel: exportExcel
            }; // SITE
        })(jQuery);
    }
    
    var mapAdd;
    var mapEdit;
    var markerOriginal;
    $(function() {
    	
        mapAdd = new AMap.Map('P110501-SITE-location-field-Add', {
            zoom: 7,//级别
            center: [116.398, 39.907],//中心点坐标
            viewMode: '2D'//使用3D视图
        });
        mapAdd.setDefaultCursor("crosshair");

        var markerAdd;
        mapAdd.on('click', function(e) {
            if (markerAdd) {
                markerAdd.setMap(null);
                markerAdd = null;
            }
           
            markerAdd = new AMap.Marker({
                icon: "https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
                position: [e.lnglat.getLng(), e.lnglat.getLat()]
            });
            markerAdd.setMap(mapAdd);
            //alert("经度：" + e.lnglat.getLng() + "纬度：" + e.lnglat.getLat())
            var dialogId = '#P110501-SITE-AddFormDialog';
            var $dialog = $(dialogId);
            $dialog.find('[name="longitude"]').val(e.lnglat.getLng());
            $dialog.find('[name="latitude"]').val(e.lnglat.getLat());
        });

        mapEdit = new AMap.Map('P110501-SITE-location-field-Edit', {
            zoom: 7,//级别
            center: [116.398, 39.907],//中心点坐标
            viewMode: '2D'//使用3D视图
        });
        mapEdit.setDefaultCursor("crosshair");
        
        var markerEdit;
        mapEdit.on('click', function(e) {
            if (markerEdit) {
                markerEdit.setMap(null);
                markerEdit = null;
            }
            if (markerOriginal) {
            	markerOriginal.setMap(null);
            	markerOriginal = null;
            }
            markerEdit = new AMap.Marker({
                icon: "https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
                position: [e.lnglat.getLng(), e.lnglat.getLat()]
            });
            markerEdit.setMap(mapEdit);
            var dialogId = '#P110501-SITE-EditFormDialog';
            var $dialog = $(dialogId);
            $dialog.find('[name="longitude"]').val(e.lnglat.getLng());
            $dialog.find('[name="latitude"]').val(e.lnglat.getLat());
        });

        
    });
    function initAddMap(){
    	mapAdd.clearMap();
    }
    
    function setOriginalMarker(longitude,latitude){
    	mapEdit.clearMap();
    	if(longitude != "" && latitude != ""){
    		markerOriginal = new AMap.Marker({
                icon: "https://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
                position: [longitude, latitude]
            });
        	markerOriginal.setMap(mapEdit);
    	}
    }
    //]]>

    
</script>