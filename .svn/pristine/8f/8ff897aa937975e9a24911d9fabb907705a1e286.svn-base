<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    	
    var selectArea1 = '${idMap["main-chart"]}SelectArea1';
    $('#' + selectArea1).hide();

    if (!DELAY_ANALYSIS) {
        var DELAY_ANALYSIS = (function($) {
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
        		            _drawChart($select.val());
        		            $select.change(function(){
        		            	_drawChart($(this).val());
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
             * 绘制虚拟组织图,阮工修改这个函数，饼图。
             */
            function _drawChart(id) {
            	var chartId = '${idMap["main-chart"]}';
                var $chart = $('#' + chartId);
                if(!id){
					return
				}
                console.log($chart.attr('data-chart-url'));
                var options = {
                    url: $chart.attr('data-chart-url'),
                    type: 'POST',
                    dataType: 'json',
                    data: {
                    	planId: id
                    },
                    success: function(response, statusText, xhr, jqForm) {
                        if (MainUtils.processJsonResult(response, false)) {
                        	var legendData = [];
                        	var seriesData = [];
                        	console.log(response.dataMap.returnObj);
                        	for (var i = 0; i < response.dataMap.returnObj.length; i ++) {
                        		var info = response.dataMap.returnObj[i];
                       			seriesData.push({
                           			name: info.issueTypeText,
                           			value: info.num
                           		});
                           		legendData.push(info.issueTypeText);
                        	}
                        	try {
                        		myChart.dispose();
							} catch (e) {
								
							}
                            myChart = echarts.init(document.getElementById(chartId));
                            myChart.setOption({
                           	    title : {
                           	        text: '滞后分析',
                           	        x:'center'
                           	    },
                           	    tooltip : {
                           	        trigger: 'item',
                           	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                           	    },
                           	    legend: {
                           	        orient: 'vertical',
                           	        right: 10,
                           	        top: 20,
                           	        bottom: 20,
                           	        data: legendData
                           	    },
                           	    series : [
                           	        {
                           	            name: '数量',
                           	            type: 'pie',
                           	            radius : '55%',
                           	            center: ['40%', '50%'],
                           	            data: seriesData,
                           	            itemStyle: {
                           	                emphasis: {
                           	                    shadowBlur: 10,
                           	                    shadowOffsetX: 0,
                           	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                           	                }
                           	            }
                           	        }
                           	    ]
                           	});
                            window.onresize = myChart.resize
                        } else {
                            AlertUtils.msg(response.icon || 'error', response.message
                                    || MSG_REMOTE_SERVER_ERROR);
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);
            }

            return {
                onSelectProjectTreeNode: onSelectProjectTreeNode
            }; // DELAY_ANALYSIS
        })(jQuery);
    }
    //]]>

    
</script>