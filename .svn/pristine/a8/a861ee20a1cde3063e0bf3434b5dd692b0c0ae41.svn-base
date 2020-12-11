<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    	
    var virtualOrg = '';
    var nowYear = 2020;
    var projectId = '';
    var chartElement = null;
	
    var selectLabel = '${idMap["main-chart"]}SelectLabel';
    $('#' + selectLabel).text('小组');
    var selectLabel1 = '${idMap["main-chart"]}SelectLabel1';
    $('#' + selectLabel1).text('年度');
    
    if (!PLAN_SUMMARY) {
        var PLAN_SUMMARY = (function($) {
        	
        	var selectEleId = '${idMap["main-chart"]}Select';
            var selectEle = $('#' + selectEleId);
            selectEle.change(function(e) {
            	virtualOrg = e.target.value;
            	_drawChart();
            });
            
            var yearEleId = '${idMap["main-chart"]}Select1';
            var selectEle1 = $('#' + yearEleId);
            selectEle1.change(function(e) {
            	nowYear = e.target.value;
            	_drawChart();
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

                projectId = node.id;
                _drawChart(node.id);
            }

            /**
             * 绘制虚拟组织图,阮工修改这个函数，柱状图。
             */
            function _drawChart() {
            	var chartId = '${idMap["main-chart"]}';
                var $chart = $('#' + chartId);
                if(!projectId){
					return
				}
                var options = {
                    url: BASE_URL + '/main-finishPlanInfo.json',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        projectId: projectId,
                        virtualOrg: virtualOrg,
                        nowYear: nowYear
                    },
                    success: function(response, statusText, xhr, jqForm) {
                    	
						if (selectEle.find("option").length == 0) {
							selectEle.append("<option value=''>全部小组</option>");
	                    	if (response.dataMap.returnObj.virtualOrgList.length > 0) {
	                    		for(var i = 0;i < response.dataMap.returnObj.virtualOrgList.length;i++){
	                    			selectEle.append("<option value='"+response.dataMap.returnObj.virtualOrgList[i].id+"'>"+response.dataMap.returnObj.virtualOrgList[i].virtaulOrgName+"</option>")
	        				    }
	                    	}
						}
						
						if (selectEle1.find("option").length == 0) {
							selectEle1.append("<option value='2019'>2019</option>");
							selectEle1.append("<option value='2020'>2020</option>");
						}
                    	
                    	var planFinish = response.dataMap.returnObj.planFinish;
						finishPlanData(planFinish);
                        /* if (MainUtils.processJsonResult(response, false)) {
                        	var legendData = [];
                        	var seriesData = [];
                        	for(var i = 0;i < response.dataMap.returnObj.length;i++){
                        		legendData.push(response.dataMap.returnObj[i].planName);
                        		seriesData.push(response.dataMap.returnObj[i].progress)
                        	}
                        	try {
                        		myChart.dispose();
							} catch (e) {
								
							}
                        	myChart = echarts.init(document.getElementById(chartId));
                            myChart.setOption(option = {
                           		grid:{
                           			bottom:"25%"
                           		},
                           		tooltip: {
                          		        trigger: 'axis',
                          		    },
                           	    xAxis: {
                           	        type: 'category',
                           	        data: legendData,
                           	        axisLabel:{
                                           interval:0,
                                           rotate:45,//倾斜度 -90 至 90 默认为0
                                       }
                           	    },
                           	    yAxis: {
                           	        type: 'value'
                           	    },
                           	    series: [{
                           	        data: seriesData,
                           	        type: 'bar'
                           	    }]
                           	});
                            window.onresize = myChart.resize
                        } else {
                            AlertUtils.msg(response.icon || 'error', response.message
                                    || MSG_REMOTE_SERVER_ERROR);
                        } */
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);
            }

            return {
                onSelectProjectTreeNode: onSelectProjectTreeNode
            }; // PLAN_SUMMARY
        })(jQuery);
    }
    
    
    
    
    function finishPlanData(planFinish){
    	
		if(planFinish.length > 0){
			var groupArr = [];
			var seriesData = [];
			var taskCountData = [];
			var unfinishTaskCountData = [];
			var finishTaskCountData = [];
			for(i in planFinish){
				groupArr.push((parseInt(i)+1)+"月");
				taskCountData.push(planFinish[i].taskCount);
				unfinishTaskCountData.push(planFinish[i].unfinishTaskCount);
				finishTaskCountData.push(planFinish[i].finishTaskCount);
			};
			seriesData = [{
   		    	name:"任务总数",
   		        data: taskCountData,
   		        type: 'line'
   		    },{
   		    	name:"已完成",
   		        data: finishTaskCountData,
   		        type: 'line'
   		    },{
   		    	name:"出现问题数",
   		        data: unfinishTaskCountData,
   		        type: 'line'
   		    }];
			drawFinishChart("finishChart","",['任务总数','已完成','出现问题数'],groupArr,'',seriesData,false);
		    $("#totalText").text("总数:"+taskCountData[11]+" 已完成:"+finishTaskCountData[11]+" 出现问题数:"+unfinishTaskCountData[11]);
		}else{
			drawFinishChart("finishChart","",['任务总数','已完成','出现问题数'],[],'',[],false);
			$("#totalText").text("总数:-- 已完成:-- 未完成:--");
		}
    }
    
    function drawFinishChart(box,title,legend,xAxis,unit,seriesData,boundaryGap){
    	
    	var chartId = '${idMap["main-chart"]}';
    	if (!chartElement) {
    		chartElement = echarts.init(document.getElementById(chartId));
    	}

        option = {
			backgroundColor: '#fff',
			color: ['#556fb5','#f24040','#e0a232'],
			title: {
                text: title,
                left: "center",
                top: 'top',
                textStyle:{
                	fontSize:"14px"
                }
			},
			grid:{
				left:"50px",
				top:"10%",
				right:"5%",
				bottom:"10%"
			},
			tooltip: {
                trigger: 'axis',
            },
   		    xAxis: {
   		        type: 'category',
   		    	boundaryGap: boundaryGap,
   		        data: xAxis
   		    },
   		    yAxis: {
   		        type: 'value',
				axisLabel:{
					formatter:"{value}"+unit
				}
   		    },
   		    series: seriesData
   		};
        chartElement.setOption(option, true);
    }
    //]]>

    
</script>