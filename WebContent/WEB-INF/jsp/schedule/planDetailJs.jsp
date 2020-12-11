<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    
    var planId = '';
   	var layer = 0;
   	var chartData = [];
	
    var selectLabel1 = '${idMap["main-chart"]}SelectLabel1';
    $('#' + selectLabel1).text('层级');
    
    if (!PLAN_DETAIL) {
        var PLAN_DETAIL = (function($) {
        	
        	var selectEleId1 = '${idMap["main-chart"]}Select1';
            var selectEle1 = $('#' + selectEleId1);
            selectEle1.change(function(e) {
            	layer = e.target.value;
            	
            	var legendData = [];
            	var seriesData = [];
           		for(var i = 0;i < chartData.length;i++){
               		const data = chartData[i];
               		if (data.layer == layer || layer == '') {
               			legendData.push(data.taskName);
                   		seriesData.push(data.progress)
               		}
               	}
               	
               	drawChartMap(legendData, seriesData);
            });
            
        	var myChart;
            /**
             * 点击项目集-项目树的节点时，重新绘制虚拟组织图。
             * 
             * @param node
             */
            function onSelectProjectTreeNode(node) {
            	
                // 跳过'全部'根节点和项目集节点
                if (node.id == 'ALL' || node.attributes.isProjectSet) {
                    return;
                }
                $.ajax({
        			url : BASE_URL + '/schedule/delay-analysis/list-plan.json',
        			type : 'POST',
        			data : {projectId:node.id},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					
							    					
        					var str = ""
        					for(var i = 0;i < response.dataMap.rows.length;i++){
        						
        						str += "<option value='"+response.dataMap.rows[i].id+"'>"+response.dataMap.rows[i].text+"</option>"
        					}
        					var selectId = '${idMap["main-chart"]}Select';
        		            var $select = $('#' + selectId);
        		            $select.html(str);
        		            
							planId = $select.val();
        		            _drawChart();
        		            $select.change(function(){
        		            	planId = $(this).val();
        		            	_drawChart();
        		            });
        				}else{
        					alert(response.message)
        				}
        			},
        			error : function(XMLHttpRequest, textStatus, errorThrown) {
        				//MainUtils.handleDatagridLoadError
        			}
        		})

               // _drawChart(node.id);
            }

            /**
             * 绘制虚拟组织图,阮工修改这个函数，柱状图。
             */
            function _drawChart() {
            	var chartId = '${idMap["main-chart"]}';
	            var $chart = $('#' + chartId);
                if(!planId){
					return
				}
                var options = {
                    url: $chart.attr('data-chart-url'),
                    type: 'POST',
                    dataType: 'json',
                    data: {
                    	planId: planId
                    },
                    success: function(response, statusText, xhr, jqForm) {
                        if (MainUtils.processJsonResult(response, false)) {
                        	
                        	
                        	const layers = [];    
                        	
                        	var legendData = [];
                        	var seriesData = [];
                        	chartData = response.dataMap.returnObj;
                        	for(var i = 0;i < response.dataMap.returnObj.length;i++){
                        		legendData.push(response.dataMap.returnObj[i].taskName);
                        		seriesData.push(response.dataMap.returnObj[i].progress)
                        		if (layers.indexOf(response.dataMap.returnObj[i].layer) < 0) {
        							layers.push(response.dataMap.returnObj[i].layer);
        						}
                        	}
                        	
                        	selectEle1.empty();
                        	selectEle1.append("<option value=''></option>");
       						for (let i = 0; i < layers.length; i++) {
       							selectEle1.append("<option value='" + layers[i] + "'>" + layers[i] + "</option>");
       						}
       						
       						drawChartMap(legendData, seriesData);
				            
                        } else {
                            AlertUtils.msg(response.icon || 'error', response.message
                                    || MSG_REMOTE_SERVER_ERROR);
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);
            }
            
            function drawChartMap(legendData, seriesData) {
            	
            	try {
            		myChart.dispose();
				} catch (e) {
					
				}
            	
				var chartId = '${idMap["main-chart"]}';
            	myChart = echarts.init(document.getElementById(chartId));
                myChart.setOption(option = {
               		grid:{
               			bottom:"10%"
               		},
              		   tooltip: {
              		        trigger: 'axis',
              		    },
               	    xAxis: {
               	    	type: 'value'
               	    },
               	    yAxis: {
           	        	type: 'category',
               	        data: legendData,
               	        axisLabel:{
                           interval: 2,
                           /* rotate:45,//倾斜度 -90 至 90 默认为0 */
                        }
               	    },
               	    series: [{
               	        data: seriesData,
               	        type: 'bar'
               	    }]
               	});
                window.onresize = myChart.resize
            }

            return {
                onSelectProjectTreeNode: onSelectProjectTreeNode
            }; // PLAN_DETAIL
        })(jQuery);
    }
    //]]>

    
</script>