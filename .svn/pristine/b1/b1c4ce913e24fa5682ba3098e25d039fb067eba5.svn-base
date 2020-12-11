<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>

  <div class="s-container">
     	<div class="row">
	      	<div class="col-xs-6" style="margin-bottom: 20px;">
		        <div class="s-modal">
		          <div class="s-head s-wine"><span tar="proList" class="hometab curtab">项目列表</span>
			          <div class="s-content">
			            <div class="s-table procontentlist" id="proList">
			              <div class="s-table-head">
			                <ul>
			                  <li class="s-width-20">序号</li>
			                  <li class="s-width-100">项目名称</li>
			                </ul>
			              </div>
			              <div class="s-table-body">
			                <ul id="projectList1">
			                </ul>
			              </div>
			            </div>
		          	</div>
		        </div>
		     </div>
	     </div>
	     <div class="col-xs-6" style="margin-bottom: 20px;">
	        <div class="s-modal">
	          <div class="s-head s-blue">
	            资金（按月统计） <select id="feeselect1" style="height:24px;line-height:24px;margin-left:20%;" class="yearSelector">
	         </select>
	          </div>
	          <div class="s-content">
	            <div class="s-chart" id="chart11"></div>
	            <div style="clear: both;"></div>
	          </div>
	        </div>
	      </div>
	      <div class="col-xs-6" style="margin-bottom: 20px;">
	        <div class="s-modal">
	          <div class="s-head s-blue">
	            资金（按专业组统计）<select id="feeselect2" style="height:24px;line-height:24px;margin-left:20%;" class="yearSelector">
	         </select>
	          </div>
	          <div class="s-content">
	            <div class="s-chart" id="chart12"></div>
	            <div style="clear: both;"></div>
	          </div>
	        </div>
	      </div>
	      <div class="col-xs-6">
	        <div class="s-modal">
	          <div class="s-head s-blue">
	            资金申请与下达统计
	          </div>
	          <div class="s-content">
	            <div class="s-chart" id="chart13"></div>
	            <div style="clear: both;"></div>
	          </div>
	        </div>
	      </div>
	    </div>
	</div>
  
  <script type="text/javascript">
  
			var BASE_URL = '${pageContext.request.contextPath}';
			var selected_projectId = "";
            $(function() {
            	$("#feeselect1").change(function(){
            		var year = $(this).val();
            		feeChartInit(selected_projectId, year);
            	});
            	
            	$("#feeselect2").change(function(){
            		var year = $(this).val();
            		groupChartInit(selected_projectId, year);
            	});
            	
            	$("#projectList1").on("click","li",function(){
            		initThreeCharts($(this).attr("projectId"));
            		$("#projectList1 li").removeClass("curProject");
            		$("#favoriteProject li").removeClass("curProject");
            		$(this).addClass("curProject");
            	})
            	
        		$.ajax({
        			url : BASE_URL + '/main-info.json',
        			type : 'POST',
        			data : "",
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					console.log(response)
        					var years = response.dataMap.returnObj.years;
        					var yearSelector = "";
        					for (var y = 0; y < years.length; y++) {
        						var selected = "";
        						if (y == years.length - 1) {
        							selected = "selected";
        						}
        						yearSelector += "<option "+selected+">"+years[y]+"</option>";
        					}
        					console.log(yearSelector);
        					
        					$(".yearSelector").html(yearSelector);
        					
        					$("#projectList1").empty();
        					for(var i = 0;i < response.dataMap.returnObj.projectInfo.length;i++){
        						var data = response.dataMap.returnObj.projectInfo[i];
        						var name = data.name?data.name:"--";
        						var curProject = "";
        						if(i == 0){
        							curProject = "curProject";
        						}
        						$("#projectList1").append('<li class="'+curProject+'" projectId='+data.id+'>'
        			                    +'<div class="s-width-20">'+(i+1)+'</div>'
        			                    +'<div class="s-width-100" title="'+name+'">'+name+'</div>'
        			                    +'</li>')
        					}

        					initThreeCharts($("#projectList1 li").eq(0).attr("projectId"));
        					
        				}else{
        					alert(response.message)
        				}
        			},
        			error : function(XMLHttpRequest, textStatus, errorThrown) {
        				
        			}
        		})
            })
            
            function initThreeCharts(projectId){
                var date = new Date();
            	this.year = date.getFullYear();
            	if(projectId){
            		selected_projectId = projectId
            		feeChartInit(projectId, this.year);
            		groupChartInit(projectId, this.year);
            		applyChartInit(projectId);
            		$.ajax({
            			url : BASE_URL + '/budget/budget-statistics/main-chartInfo.json',
            			type : 'POST',
            			data : {projectId:projectId},
            			dataType : 'json',
            			success : function(response, statusText, xhr, jqForm) {
            				if(response.status == 1){
            					console.log(response.dataMap.returnObj);
            				    
            				}else{
            					alert(response.message)
            				}
            			},
            			error : function(XMLHttpRequest, textStatus, errorThrown) {
            				
            			}
            		})
            	}
            }
            
            function feeChartInit(projectId,year) {
            	$.ajax({
        			url : BASE_URL + '/budget/budget-statistics/fee-chartInfo.json',
        			type : 'POST',
        			data : {projectId:projectId, year:year},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					var xData = response.dataMap.returnObj.xAxisList;
        			    	var useDatas = response.dataMap.returnObj.useList;
        			    	var planDatas = response.dataMap.returnObj.planList;
        				    drawFeeChart(xData,useDatas,planDatas);
        				}
        			}
            	});
            }
            
            function drawFeeChart(xData,useDatas,planDatas){
            	var myChart = echarts.init(document.getElementById("chart11"));
            	option = {
					backgroundColor: '#fff',
					title: {
	                    text: "年度资金统计",
	                    left: "center",
	                    top: 'top',
	                    textStyle:{
	                    	fontSize:"14px"
	                    }
					},
					grid:{
						left:"10%",
						top:"30%",
						right:"5%",
						bottom:"10%"
					},
					tooltip: {
                        trigger: 'axis',
                    },
           		    xAxis: {
           		        type: 'category',
           		    	boundaryGap: true,
           		        data: xData
           		    },
           		    yAxis: {
           		        type: 'value',
						axisLabel:{
							formatter:"{value}元"
						}
           		    },
           		    series: [{
        		    	name: "使用金额",
 	           			data: useDatas,
 	           			type: 'line'
        		    	}
/*         		 		,{
        		    	name: "计划金额",
 	           			data: planDatas,
 	           			type: 'line'
        		    	} */
        		    	]
           		};
            	myChart.setOption(option);
            }
            
            function groupChartInit(projectId,year) {
            	$.ajax({
        			url : BASE_URL + '/budget/budget-statistics/group-chartInfo.json',
        			type : 'POST',
        			data : {projectId:projectId,year:year},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					var xData = response.dataMap.returnObj.xAxisList;
        			    	var useDatas = response.dataMap.returnObj.useList;
        			    	var planDatas = response.dataMap.returnObj.planList;
        				    drawGroupChart(xData,useDatas,planDatas);
        				}
        			}
            	});
            }
            
            function drawGroupChart(xData,useDatas,planDatas){
            	var myChart = echarts.init(document.getElementById("chart12"));
            	option = {
					backgroundColor: '#fff',
					title: {
	                    text: "",
	                    left: "center",
	                    top: 'top',
	                    textStyle:{
	                    	fontSize:"14px"
	                    }
					},
					grid:{
						left:"10%",
						top:"30%",
						right:"5%",
						bottom:"35%"
					},
					tooltip: {
                        trigger: 'axis',
                    },
           		    xAxis: {
           		        type: 'category',
           		    	boundaryGap: true,
           		        data: xData,
	           		    axisLabel:{
	                         interval:0,
	                         rotate:45,//倾斜度 -90 至 90 默认为0
	                    },
           		    },
           		    yAxis: {
           		        type: 'value',
						axisLabel:{
							formatter:"{value}元"
						}
           		    },
           		    series: [{
           		    	name: "使用金额",
    	           		data: useDatas,
    	           		type: 'bar'
           		    },
           		 	{
           		    	name: "计划金额",
    	           		data: planDatas,
    	           		type: 'bar'
           		    }]
           		};
            	myChart.setOption(option);
            }
            
            
            function applyChartInit(projectId) {
            	$.ajax({
        			url : BASE_URL + '/budget/budget-statistics/apply-chartInfo.json',
        			type : 'POST',
        			data : {projectId:projectId},
        			dataType : 'json',
        			success : function(response, statusText, xhr, jqForm) {
        				if(response.status == 1){
        					var xData = response.dataMap.returnObj.xAxisList;
        			    	var applyDatas = response.dataMap.returnObj.applyList;
        			    	var provideDatas = response.dataMap.returnObj.provideList;
        			    	drawApplyChart(xData,applyDatas,provideDatas);
        				}
        			}
            	});
            }
            
            function drawApplyChart(xData,applyDatas,provideDatas){
            	var myChart = echarts.init(document.getElementById("chart13"));
            	option = {
            		color: ['#D53A35', '#334B5C'],
					title: {
	                    text: "",
	                    left: "center",
	                    top: 'top',
	                    textStyle:{
	                    	fontSize:"14px"
	                    }
					},
					grid:{
						bottom:"25%"
					},
					tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                    	right : 40,
                        top: 0,
                        data: ['申请金额', '下达金额']
                    },
           		    xAxis: {
           		        type: 'category',
           		    	boundaryGap: true,
           		        data: xData
           		    },
           		    yAxis: {
           		        type: 'value',
	           		    axisLabel:{
	                        formatter: '{value}元'
	                    },
           		    },
           		    series: [{
           		    	name: "申请金额",
    	           		data: applyDatas,
    	           		type: 'line'
           		    },
           		    {
           		    	name: "下达金额",
    	           		data: provideDatas,
    	           		type: 'line'
           		    } 
           		    ]
           		};
            	myChart.setOption(option);
            }
            
            
        </script>
</body>
</html>
