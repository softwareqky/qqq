var planePath = 'path://M.6,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705';
//站点特效图层
var effectPointData = {
    name: '',
    type: 'effectScatter',
    coordinateSystem: 'geo',
    zlevel: 2,
    rippleEffect: {
        brushType: 'stroke'
    },
    label: {
        normal: {
            show: false,
            position: 'right',
            formatter: '{b}'
        }
    },
    symbolSize: 10,
    itemStyle: {
        normal: {
            color: "#fff"
        }
    },
    data: []
}

var _segSystemNames = [];
var _editSystemNames = [];

//中继段特效
var effectSegData = {
	name: '',
    type: 'lines',
    zlevel: 1,
    effect: {
        show: true,
        period: 6,
        trailLength: 0.7,
        color: '#fff',
        symbolSize: 3
    },
    lineStyle: {
        normal: {
            color: "blue",
            width: 0,
            curveness: 0//曲度
        }
    },
    data: []
}

//重点城市已具备
var keyMarkPointData_green = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#fff',
				formatter: '{b}',
				textStyle: {
					color: "#fff"
				}
			},
			emphasis: {
				show: true,
				color: "#fff"
			}
		},
		itemStyle: {
			color:"#fff"
		},
		symbol: 'image://img/home/markers/mainpoint.png',
		symbolSize: 18,
		data: []
	};
//重点城市进行中
var keyMarkPointData_yellow = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#ffff00',
				formatter: '{b}',
				textStyle: {
					color: "#ffff00"
				}
			},
			emphasis: {
				show: true,
				color: "#ffff00"
			}
		},
		itemStyle: {
			color:"#ffff00"
		},
		symbol: 'image://img/home/markers/mainpoint_yellow.png',
		symbolSize: 18,
		data: []
	};
//重点城市未落实
var keyMarkPointData_grey = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#fff',
				formatter: '{b}',
				textStyle: {
					color: "#8a8a8a"
				}
			},
			emphasis: {
				show: true,
				color: "#8a8a8a"
			}
		},
		itemStyle: {
			color:"#8a8a8a"
		},
		symbol: 'image://img/home/markers/mainpoint_grey.png',
		symbolSize: 18,
		data: []
	};

//核心节点已具备
var corePointData_green = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#fff',
				formatter: '{b}',
				textStyle: {
					color: "#fff"
				}
			},
			emphasis: {
				show: true,
				color: "#fff"
			}
		},
		itemStyle: {
			color:"#fff"
		},
		symbol: 'circle',
		symbolSize: 12,
		data: []
	};
//核心节点进行中
var corePointData_yellow = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#ffff00',
				formatter: '{b}',
				textStyle: {
					color: "#ffff00"
				}
			},
			emphasis: {
				show: true,
				color: "#ffff00"
			}
		},
		itemStyle: {
			color:"#ffff00"
		},
		symbol: 'circle',
		symbolSize: 12,
		data: []
	};
//核心节点未落实
var corePointData_grey = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#fff',
				formatter: '{b}',
				textStyle: {
					color: "#8a8a8a"
				}
			},
			emphasis: {
				show: true,
				color: "#8a8a8a"
			}
		},
		itemStyle: {
			color:"#8a8a8a"
		},
		symbol: 'circle',
		symbolSize: 12,
		data: []
	};

//边缘节点已具备
var edgePointData_green = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#fff',
				formatter: '{b}',
				textStyle: {
					color: "#fff"
				}
			},
			emphasis: {
				show: true,
				color: "#fff"
			}
		},
		itemStyle: {
			color:"#fff"
		},
		symbol: 'circle',
		symbolSize: 12,
		data: []
	};
//边缘节点进行中
var edgePointData_yellow = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#ffff00',
				formatter: '{b}',
				textStyle: {
					color: "#ffff00"
				}
			},
			emphasis: {
				show: true,
				color: "#ffff00"
			}
		},
		itemStyle: {
			color:"#ffff00"
		},
		symbol: 'circle',
		symbolSize: 12,
		data: []
	};
//边缘节点未落实
var edgePointData_grey = {
		type: 'scatter',
		coordinateSystem: 'geo',
		zlevel: 2,
		rippleEffect: {
			period: 4,
			brushType: 'stroke',
			scale: 4
		},
		label: {
			normal: {
				show: true,
				position: 'right',
				//offset:[5, 0],
				color: '#fff',
				formatter: '{b}',
				textStyle: {
					color: "#8a8a8a"
				}
			},
			emphasis: {
				show: true,
				color: "#8a8a8a"
			}
		},
		itemStyle: {
			color:"#8a8a8a"
		},
		symbol: 'circle',
		symbolSize: 12,
		data: []
	};

//高速光缆已具备
var highSpeedCableData_green = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#fff",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};;
//高速光缆进行中
var highSpeedCableData_yellow = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#ffff00",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};
//高速光缆未落实
var highSpeedCableData_grey = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#8a8a8a",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};

//城域光缆已具备
var metroCableData_green = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#fff",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};;
//城域光缆进行中
var metroCableData_yellow = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#ffff00",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};
//城域光缆未落实
var metroCableData_grey = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#8a8a8a",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};

//链路已具备
var coreLinkData_green = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#fff",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};;
//链路未落实
var coreLinkData_grey = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#8a8a8a",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};

//系统中继段
var systemSegData_green = {
		name: '',
	    type: 'lines',
	    zlevel: 2,
	    effect: {
	        show: true,
	        period: 6,
	        trailLength: 0,
	        symbol: planePath,
	        symbolSize: 15
	    },
	    lineStyle: {
	        normal: {
	            color: "#ffff00",
	            width: 1,
	            opacity: 0.4,
	            curveness: 0//曲度
	        }
	    },
	    data: []
	};


$(function(){
	$.ajax({
		url : BASE_URL + '/system/dashboard-custom/get-dashboard-icon.json',
		type : 'POST',
		data : {},
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			if(response.status == 1){
				var data = response.dataMap.returnObj;
				for(var i = 0;i < data.siteIconList.length;i++){
					
					const size = data.siteIconList[i].size;
					
					if(data.siteIconList[i].siteType == "mapMainCity1"){//已具备
						if(data.siteIconList[i].iconName != null){
							keyMarkPointData_green.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							keyMarkPointData_green.symbolSize = (size == 1 ? 36 : 24)
							keyMarkPointData_green.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}else if(data.siteIconList[i].siteType == "mapMainCity2"){//进行中
						if(data.siteIconList[i].iconName != null){
							keyMarkPointData_yellow.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							keyMarkPointData_yellow.symbolSize = (size == 1 ? 36 : 24)
							keyMarkPointData_yellow.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}else if(data.siteIconList[i].siteType == "mapMainCity3"){//未落实
						if(data.siteIconList[i].iconName != null){
							keyMarkPointData_grey.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							keyMarkPointData_grey.symbolSize = (size == 1 ? 36 : 24)
							keyMarkPointData_grey.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}else if(data.siteIconList[i].siteType == "mapCoreNode1"){//已具备
						if(data.siteIconList[i].iconName != null){
							corePointData_green.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							corePointData_green.symbolSize = (size == 1 ? 36 : 24)
							corePointData_green.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}else if(data.siteIconList[i].siteType == "mapCoreNode2"){//进行中
						if(data.siteIconList[i].iconName != null){
							corePointData_yellow.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							corePointData_yellow.symbolSize = (size == 1 ? 36 : 24)
							corePointData_yellow.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}else if(data.siteIconList[i].siteType == "mapCoreNode3"){//未落实
						if(data.siteIconList[i].iconName != null){
							corePointData_grey.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							corePointData_grey.symbolSize = (size == 1 ? 36 : 24)
							corePointData_grey.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}else if(data.siteIconList[i].siteType == "mapEdgeNode1"){//已具备
						if(data.siteIconList[i].iconName != null){
							edgePointData_green.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							edgePointData_green.symbolSize = (size == 1 ? 36 : 24)
							edgePointData_green.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}else if(data.siteIconList[i].siteType == "mapEdgeNode2"){//进行中
						if(data.siteIconList[i].iconName != null){
							edgePointData_yellow.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							edgePointData_yellow.symbolSize = (size == 1 ? 36 : 24)
							edgePointData_yellow.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}else if(data.siteIconList[i].siteType == "mapEdgeNode3"){//未落实
						if(data.siteIconList[i].iconName != null){
							edgePointData_grey.symbol = "image://img/home/markers/"+data.siteIconList[i].iconName;
							edgePointData_grey.symbolSize = (size == 1 ? 36 : 24)
							edgePointData_grey.symbolOffset = (size == 1 ? [18+parseInt(data.siteIconList[i].x),18+parseInt(data.siteIconList[i].y)] : [18+parseInt(data.siteIconList[i].x) ,18+parseInt(data.siteIconList[i].y) ]);
						}
					}
					
					if(data.siteIconList[i].iconName != null){
						$("#"+data.siteIconList[i].siteType).attr("src","img/home/markers/"+data.siteIconList[i].iconName);
					}
				}
				
				for(var j = 0;j < data.segmentIconList.length;j++){
					if(data.segmentIconList[j].segmentType == "mapMetroCable1"){//已具备
						if(data.segmentIconList[j].color != null){
							metroCableData_green.lineStyle.normal.color = data.segmentIconList[j].color;
							metroCableData_green.lineStyle.normal.width = data.segmentIconList[j].width;
							metroCableData_green.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}else if(data.segmentIconList[j].segmentType == "mapMetroCable2"){//进行中
						if(data.segmentIconList[j].color != null){
							metroCableData_yellow.lineStyle.normal.color = data.segmentIconList[j].color;
							metroCableData_yellow.lineStyle.normal.width = data.segmentIconList[j].width;
							metroCableData_yellow.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}else if(data.segmentIconList[j].segmentType == "mapMetroCable3"){//未落实
						if(data.segmentIconList[j].color != null){
							metroCableData_grey.lineStyle.normal.color = data.segmentIconList[j].color;
							metroCableData_grey.lineStyle.normal.width = data.segmentIconList[j].width;
							metroCableData_grey.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}else if(data.segmentIconList[j].segmentType == "mapHighSpeedCable1"){//已具备
						if(data.segmentIconList[j].color != null){
							highSpeedCableData_green.lineStyle.normal.color = data.segmentIconList[j].color;
							highSpeedCableData_green.lineStyle.normal.width = data.segmentIconList[j].width;
							highSpeedCableData_green.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}else if(data.segmentIconList[j].segmentType == "mapHighSpeedCable2"){//进行中
						if(data.segmentIconList[j].color != null){
							highSpeedCableData_yellow.lineStyle.normal.color = data.segmentIconList[j].color;
							highSpeedCableData_yellow.lineStyle.normal.width = data.segmentIconList[j].width;
							highSpeedCableData_yellow.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}else if(data.segmentIconList[j].segmentType == "mapHighSpeedCable3"){//未落实
						if(data.segmentIconList[j].color != null){
							highSpeedCableData_grey.lineStyle.normal.color = data.segmentIconList[j].color;
							highSpeedCableData_grey.lineStyle.normal.width = data.segmentIconList[j].width;
							highSpeedCableData_grey.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}else if(data.segmentIconList[j].segmentType == "mapCoreLink1"){//已具备
						if(data.segmentIconList[j].color != null){
							coreLinkData_green.lineStyle.normal.color = data.segmentIconList[j].color;
							coreLinkData_green.lineStyle.normal.width = data.segmentIconList[j].width;
							coreLinkData_green.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}else if(data.segmentIconList[j].segmentType == "mapCoreLink2"){//未落实
						if(data.segmentIconList[j].color != null){
							coreLinkData_grey.lineStyle.normal.color = data.segmentIconList[j].color;
							coreLinkData_grey.lineStyle.normal.width = data.segmentIconList[j].width;
							coreLinkData_grey.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}else if(data.segmentIconList[j].segmentType == "mapSystemSeg"){//系统
						if(data.segmentIconList[j].color != null){
							systemSegData_green.lineStyle.normal.color = data.segmentIconList[j].color;
							systemSegData_green.lineStyle.normal.width = data.segmentIconList[j].width;
							systemSegData_green.lineStyle.normal.type = data.segmentIconList[j].lineStyle;
						}
					}
					
					if(data.segmentIconList[j].color != null){
						$("#"+data.segmentIconList[j].segmentType).css("borderBottom",data.segmentIconList[j].width+"px "+data.segmentIconList[j].lineStyle+" "+data.segmentIconList[j].color);
					}
				}
			}
		}
	},false)
	
	setInterval(function(){
		var now = new Date();
		$("#l-time").html(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+"&nbsp;"+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds())
	},1000)
	
	$(".area").on("click","span",function(){
		
		if($(this).hasClass("curspan")){
			$(this).removeClass("curspan");
		}else{
			//$(".area span").removeClass("curspan");
			$(this).addClass("curspan");
		}
		drawMap();
	});
	
	$(".siteTypebutton ul li").click(function(){
		$(this).parents(".siteTypebutton").attr("siteType",$(this).attr("siteType"));
		$(this).parents(".siteTypebutton").find("span").text($(this).text());
		drawMap();
	});
	
	$(".layerbutton ul li").click(function(){
		$(this).parents(".layerbutton").attr("layer",$(this).attr("layer"));
		$(this).parents(".layerbutton").find("span").text($(this).text());
		drawMap();
	});
	
	$.ajax({
		url : BASE_URL + '/site-info.json',
		type : 'POST',
		data : "",
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			if(response.status == 1){
				var str = "";
				_segSystemNames = [];
				for(var i = 0; i < response.dataMap.returnObj.segSystemName.length;i++){
					if(response.dataMap.returnObj.segSystemName[i]){
						const systemName = response.dataMap.returnObj.segSystemName[i];
						_segSystemNames.push(systemName);
						
						if (!systemName.hidden) {
							const text = systemName.name;
							str += '<div class="col-xs-4">'+
								'<span id="'+ text +'" title="'+ text +'">'+ text +'</span></div>'
						}
					}
				}
				
				$("#area").html(str);
				
			}else{
				//alert(response.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	})
	
	drawMap();
})

/**
 * 点击开始编辑筛选区域
 */
function handleEditArea() {
	
	$('#areaEditList').empty();
			
	_editSystemNames = [..._segSystemNames];
	
	// 显示列表
	_editSystemNames.forEach((systemName, index) => {
		
		const name = systemName.name;
		
		$('#areaEditList').append(
			'<div id="areaEditWrapper' + index + '" style="margin-bottom: 12px; display: inline-block; width: 50%;">' + 
				'<div style="margin-right: 12px; display: inline-block;">' +
					'<input id="areaEditInput' + index + '" type="text" style="width: 180px;">' +
				'</div>' +
				'<input id="areaEditSwitch' + index + '" class="easyui-switchbutton">' +
			'</div>'
		);
		
		$('#areaEditSwitch' + index).switchbutton({
			checked: systemName.hidden == 0,
			offText: '隐藏',
			onText: '显示',
			onChange: function(checked) {
				const index = _editSystemNames.findIndex(name => name.id == systemName.id)
				_editSystemNames[index].hidden = checked ? 0 : 1;;
			}
		});
		
		$('#areaEditInput' + index).textbox({
			value: name,
			buttonText: '删除',
			buttonAlign: 'left',
			onClickButton: function() {
				if (systemName.segmentCount == 0) {
					$('#areaEditWrapper' + index).remove();
					_editSystemNames.splice(index, 1);
				} else {
					$.messager.alert('关联数据提醒','存在关联的中继段信息，请删除后再进行此操作！');					
				}
			},
			onChange: function(value) {
				const index = _editSystemNames.findIndex(name => name.id == systemName.id);
				_editSystemNames[index].name = value;
			}
		});
	});
	
	$('#areaEditList').append(
		'<div id="areaEditWrapper999" style="margin-bottom: 12px; display: inline-block; width: 50%;">' + 
			'<input id="areaEditInput999" type="text" style="width: 180px">' +
		'</div>'
	);
	$('#areaEditInput999').textbox({
		buttonText: '添加',
		buttonAlign: 'right',
		buttonIcon: 'icon-add',
		onClickButton: function() {
			
			const value = $('#areaEditInput999').textbox('getValue');
			if (!value) return;
			
			const maxNameId = _editSystemNames.length + 100;
			_editSystemNames.push({ id: maxNameId, name: value, hidden: 0 });
			
			$('#areaEditWrapper999').before(
				'<div id="areaEditWrapper' + maxNameId + '" style="margin-bottom: 12px; display: inline-block; width: 50%;">' + 
					'<div style="margin-right: 12px; display: inline-block;">' +
						'<input id="areaEditInput' + maxNameId + '" type="text" style="width: 180px">' +
					'</div>' +
					'<input id="areaEditSwitch' + maxNameId + '" class="easyui-switchbutton">' +
				'</div>'
			);
			
			$('#areaEditSwitch' + maxNameId).switchbutton({
				checked: true,
				offText: '隐藏',
				onText: '显示',
				onChange: function(checked) {
					const id = $(this).attr('id').replace('areaEditSwitch', '');
					console.log(id, _editSystemNames);
					const index = _editSystemNames.findIndex(systemName => systemName.id == id);
					_editSystemNames[index].hidden = checked ? 0 : 1;
				}
			});
			
			$('#areaEditInput' + maxNameId).textbox({
				value: value,
				buttonText: '删除',
				buttonAlign: 'left',
				onClickButton: function() {
					$(this).parent().parent().remove();
				},
				onChange: function(value) {
					const id = $(this).attr('id').replace('areaEditInput', '');
					const index = _editSystemNames.findIndex(systemName => systemName.id == id);
					_editSystemNames[index].name = value;
				}
			});
			
			$('#areaEditInput999').textbox('setValue', '');
		}
	});
	
	// 打开编辑框
	$('#areaEditModal').dialog('open');
}

function confirmSubmitArea() {
	
	console.log('confirmSubmitArea', _editSystemNames);
	
	// 提交更新筛选地区列表
	$.ajax({
		url : BASE_URL + '/segment-system-name.json',
		type : 'POST',
		data : JSON.stringify(_editSystemNames),
		dataType : 'json',
		contentType: "application/json;charset=UTF-8",
		success : function(response, statusText, xhr, jqForm) {
			if(response.status == 1){
				var str = "";
				_segSystemNames = [];
				for(var i = 0; i < response.dataMap.returnObj.length;i++){
					if(response.dataMap.returnObj[i]){
						const systemName = response.dataMap.returnObj[i];
						_segSystemNames.push(systemName);
						
						if (!systemName.hidden) {
							const text = systemName.name;
							str += '<div class="col-xs-4">'+
								'<span id="'+ text +'" title="'+ text +'">'+ text +'</span></div>'
						}
					}
				}
				
				$("#area").html(str);
				
			}else{
				//alert(response.message);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	})
	
	cancelSubmitArea();
	
	drawMap();
}

function cancelSubmitArea() {
	
	console.log('cancelSubmitArea', _segSystemNames, _editSystemNames);
	
	_editSystemNames = [];
	$('#areaEditModal').dialog('close');
}


function drawChart(box,title,xData,yData,yPosition,center){
	var myChart = echarts.init(document.getElementById(box));
	option = {
		title: {
			text: title,
			left:"left",
			top:'0px',
			textStyle:{
				color:'#fff',
				fontWeight:'normal',
				fontSize:14
			}
		},
		tooltip: {
			trigger: 'item',
			formatter: "{b}: {c} ({d}%)"
		},
		color:['green','yellow','red'],
		legend: {
			orient: 'vertical',
			x: 'right',
			y:yPosition,
			data:xData,
			textStyle: {
				fontSize: 12,
				color: '#fff'
			},
			icon: "circle",
			itemWidth: 10,  // 设置宽度
			itemHeight: 10, // 设置高度
			itemGap: 5 // 设置间距
		},
		series: [
			{
				name:'',
				type:'pie',
				center: center,
				radius: ['40%', '70%'],
				avoidLabelOverlap: false,
				label: {
					normal: {
						show: false,
						position: 'right'
					}
				},
				labelLine: {
					normal: {
						show: false
					}
				},
				data:yData
			}
		]
	};

	myChart.setOption(option,true);
	window.onresize = myChart.resize;
}

function drawMap(){
	$(".m_loading").show();
	var layer = $(".layerbutton").attr("layer");
	var siteType = $(".siteTypebutton").attr("siteType");
	var siteId = "";
	if($("#area .curspan").length > 0){
		$("#area .curspan").each(function(i){
			if ($(this).attr("id")) {
				if(i == $("#area .curspan").length-1){
					siteId += $(this).attr("id");
				}else{
					siteId += $(this).attr("id") + ",";
				}
			}
		});
	}
	
	$(".legend_box p").hide();
	if(layer == "0"){
		$(".planLayer").show();
		$("#progressBox").hide();
//		$("#constructionProgress").hide();
		if(siteId != ""){
			$(".sysLayer").show();
		}else{
			$(".sysLayer").hide();
		}
		var str = '<p>节点名称</p>'
			+'<h1>机房位置：</h1>'
			+'<h1>初设编号：</h1>';
			$(".pointdata").html(str);
	}else if(layer == "1"){
		$(".progressLayer").show();
		$(".sysLayer").hide();
		$("#progressBox").show();
		//$("#constructionProgress").show();
		var str = '<p>节点名称</p>'
		+'<h1>机房位置：</h1>'
		+'<h1>初设编号：</h1>'
		+'<h1 id=\'constructionProgress\'>进展状态：</h1>';
		
		$(".pointdata").html(str);
	}
	
	if(siteType == "1"){
		$(".legend_box .noBaseLayer:visible").hide();
	}else{
		$(".legend_box .baseLayer:visible").hide();
	}
	
	
	var param = {};
	if(layer == 0){//计划图层
		$("#systemBox").show();
		param = {
			layer: layer,
			siteType: siteType,
			segSystemName: siteId//
		}
		$(".l-legend").css({
			"left":"auto",
			"right":"20px"
		});
	}else{//进度图层
		$("#systemBox").hide();
		param = {
			layer: layer,
			siteType: siteType,
		}
		$(".l-legend").css({
			"right":"auto",
			"left":"20px"
		});
	}
	
	var myChart = echarts.init(document.getElementById("chart5"));

	$.ajax({
		url : BASE_URL + '/site-layer-info.json',
		type : 'POST',
		data : param,
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			$(".m_loading").hide();
			if(response.status == 1){
				effectPointData.data = [];
				effectSegData.data = [];
				keyMarkPointData_green.data = [];
			    keyMarkPointData_yellow.data = [];
			    keyMarkPointData_grey.data = [];
			    corePointData_green.data = [];
			    corePointData_yellow.data = [];
			    corePointData_grey.data = [];
			    edgePointData_green.data = [];
			    edgePointData_yellow.data = [];
			    edgePointData_grey.data = [];
			    highSpeedCableData_green.data = [];
			    highSpeedCableData_yellow.data = [];
			    highSpeedCableData_grey.data = [];
			    metroCableData_green.data = [];
			    metroCableData_yellow.data = [];
			    metroCableData_grey.data = [];
			    coreLinkData_green.data = [];
			    coreLinkData_grey.data = [];
			    systemSegData_green.data = [];
				for(var i = 0;i < response.dataMap.returnObj.layerSiteList.length;i++){
					var siteData = response.dataMap.returnObj.layerSiteList[i];
					var obj = {name:siteData.city,value:[siteData.longitude, siteData.latitude,0],extraData:{
						type:siteData.siteTypeText,
						name:siteData.city,
						status:siteData.manualSiteStatus,
						isMajorCity:siteData.isMajorCity,
						initNum:siteData.initNum,
						networkRoomAddress:siteData.stationName,//机房位置
						basicNetworkProgress:siteData.basicProgress,
						programmableNetworkProgress:siteData.programProgress,
						sdnNetworkProgress:siteData.sdnProgress,
						id:siteData.id
					},type:"site"}
					if(siteData.siteType == 0){//边缘节点
						if(layer == 0){//目标图层
							edgePointData_green.data.push(obj);
							effectPointData.data.push(obj);
						}else{
							if(siteData.manualSiteStatus == 2){//已开通
								edgePointData_green.data.push(obj);
								effectPointData.data.push(obj);
							}else if(siteData.manualSiteStatus == 1){//进行中
								edgePointData_yellow.data.push(obj);
							}else if(siteData.manualSiteStatus == 0){//未落实
								edgePointData_grey.data.push(obj);
							}
						}
					}else if(siteData.siteType == 1){//核心节点
						if(siteData.isMajorCity == 1){//重点城市
							if(layer == 0){//目标图层
								keyMarkPointData_green.data.push(obj);
								effectPointData.data.push(obj);
							}else{
								if(siteData.manualSiteStatus == 2){//已开通
									keyMarkPointData_green.data.push(obj);
									effectPointData.data.push(obj);
								}else if(siteData.manualSiteStatus == 1){//进行中
									keyMarkPointData_yellow.data.push(obj);
								}else if(siteData.manualSiteStatus == 0){//未落实
									keyMarkPointData_grey.data.push(obj);
								}
							}
						}else{
							if(layer == 0){//目标图层
								corePointData_green.data.push(obj);
								effectPointData.data.push(obj);
							}else{
								if(siteData.manualSiteStatus == 2){//已开通
									corePointData_green.data.push(obj);
									effectPointData.data.push(obj);
								}else if(siteData.manualSiteStatus == 1){//进行中
									corePointData_yellow.data.push(obj);
								}else if(siteData.manualSiteStatus == 0){//未落实
									corePointData_grey.data.push(obj);
								}
							}
						}
					}
				}
				for(var i = 0;i < response.dataMap.returnObj.layerSegmentList.length;i++){
					var segmentData = response.dataMap.returnObj.layerSegmentList[i];
					var list = [
				        {coord: [segmentData.endALng,segmentData.endALat]},
				        {coord: [segmentData.endBLng,segmentData.endBLat]}
				    ];
					if(segmentData.segmentType == 2){//高速光缆
						if(layer == 0){//目标图层
							highSpeedCableData_green.data.push(list);
							effectSegData.data.push(list);
						}else{
							if(segmentData.manualSegmentStatus == 2){//已开通
								highSpeedCableData_green.data.push(list);
								effectSegData.data.push(list);
							}else if(segmentData.manualSegmentStatus == 1){//进行中
								highSpeedCableData_yellow.data.push(list);
							}else if(segmentData.manualSegmentStatus == 0){//未落实
								highSpeedCableData_grey.data.push(list);
							}
						}
					}else if(segmentData.segmentType == 1){//城域光缆
						if(layer == 0){//目标图层
							metroCableData_green.data.push(list);
							effectSegData.data.push(list);
						}else{
							if(segmentData.manualSegmentStatus == 2){//已开通
								metroCableData_green.data.push(list);
								effectSegData.data.push(list);
							}else if(segmentData.manualSegmentStatus == 1){//进行中
								metroCableData_yellow.data.push(list);
							}else if(segmentData.manualSegmentStatus == 0){//未落实
								metroCableData_grey.data.push(list);
							}
						}
					} 
					
				}
				
				for(var i = 0;i < response.dataMap.returnObj.layerLinkList.length;i++){
					var linkData = response.dataMap.returnObj.layerLinkList[i];
					var list = [
				        {coord: [linkData.endALng,linkData.endALat]},
				        {coord: [linkData.endBLng,linkData.endBLat]}
				    ];
					if(layer == 0){//目标图层
						coreLinkData_green.data.push(list);
						effectSegData.data.push(list);
					}else{
						if(linkData.dataLinkStatus == 1){//未具备
							coreLinkData_grey.data.push(list);
						}else{//已具备
							coreLinkData_green.data.push(list);
							effectSegData.data.push(list);
						} 
					}
				}
				
			/*	for(var i = 0;i < response.dataMap.returnObj.filtrateSiteList.length;i++){
					var siteData = response.dataMap.returnObj.filtrateSiteList[i];
					filterPointData.push({name:siteData.stationName,value:[siteData.longitude, siteData.latitude,0],extraData:{
						type:siteData.siteTypeText,
						name:siteData.city,
						status:siteData.manualSiteStatus,
						isMajorCity:siteData.isMajorCity,
						initNum:siteData.initNum,
						networkRoomAddress:siteData.stationName,//机房位置
						id:siteData.id
					},type:"site"});
				}*/
				for(var i = 0;i < response.dataMap.returnObj.filtrateSegmentList.length;i++){
					var segmentData = response.dataMap.returnObj.filtrateSegmentList[i];
					systemSegData_green.data.push([
				        {coord: [segmentData.endALng, segmentData.endALat]},
				        {coord: [segmentData.endBLng,segmentData.endBLat]}
				    ])
				}
				
				var myChart = echarts.init(document.getElementById("chart5"));
				var option = {
					    tooltip: {
					        trigger: 'item',
					        formatter:function(param){
					        	//console.dir(param)
					        }
					    },
					    geo: {
					        map: 'china',
					        zoom:1.5,
					        label: {
					            emphasis: {
					                show: false
					            }
					        },
					        roam: true,
					        itemStyle: {
					            normal: {
					                areaColor: '#152448',
					                borderColor: '#6681b6'
					            },
					            emphasis: {
					                areaColor: '#152448'
					            }
					        }
					    },
					    series: [
					    	effectPointData,
						    keyMarkPointData_green,
						    keyMarkPointData_yellow,
						    keyMarkPointData_grey,
						    corePointData_green,
						    corePointData_yellow,
						    corePointData_grey,
						    edgePointData_green,
						    edgePointData_yellow,
						    edgePointData_grey,
						    highSpeedCableData_green,
						    highSpeedCableData_yellow,
						    highSpeedCableData_grey,
						    metroCableData_green,
						    metroCableData_yellow,
						    metroCableData_grey,
						    coreLinkData_green,
						    coreLinkData_grey,
						    effectSegData,
						    systemSegData_green
					    ]
				}
				myChart.clear();
				myChart.setOption(option);
				if(layer == 1){
					var title = "";
					if(siteType == "0"){
						title = '站点进度统计';
					}else if(siteType == "1"){
						title = '光传输网络进度统计';
					}else if(siteType == "2"){
						title = '可编程网络进度统计';
					}else if(siteType == "3"){
						title = 'SDN网络进度统计';
					}
					drawChart('chart1',title,['已完成','建设中','未完成'],[
						{value:response.dataMap.returnObj.statusCountSiteList[0].finishCount, name:'已完成'},
						{value:response.dataMap.returnObj.statusCountSiteList[0].ongoingCount, name:'建设中'},
						{value:response.dataMap.returnObj.statusCountSiteList[0].unfinishCount, name:'未完成'},
					],"0",['45%', '60%']);
				}
			}else{
				//alert(response.message)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	})
	
	myChart.on("mouseover", function (params){
		if(params.data){
			if(params.data.extraData){
				if(params.data.extraData.isMajorCity == 1){
					$("#pointType").text("运行管控中心");
				}else{
					$("#pointType").text(params.data.extraData.type);
				}
				var info = params.data.extraData;
				var statusText = "";
				if(info.status == 2){
					statusText = "已开通";
				}else if(info.status == 1){
					statusText = "进行中";
				}else if(info.status == 0){
					statusText = "计划中";
				}
				var siteType = $(".siteTypebutton").attr("siteType");
				var progressStatus,viewGanttStatus;
				if (siteType == 1) {
					progressStatus = info.basicNetworkProgress;
					viewGanttStatus = 'basic';
				} else if (siteType == 2) {
					progressStatus = info.programmableNetworkProgress;
					viewGanttStatus = 'programmable';
				} else if (siteType == 3) {
					progressStatus = info.sdnNetworkProgress;
					viewGanttStatus = 'sdn';
				}
				var layer = $(".layerbutton").attr("layer");
				var str = '<p>'+info.name+'</p>'
				+'<h1>机房位置：'+info.networkRoomAddress+'</h1>'
				+'<h1>初设编号：'+info.initNum+'</h1>';
				console.log(JSON.stringify(info));
				if (layer == 1) {
					str +='<h1 id="constructionProgress" onclick="viewGantt(\''+info.id+'\',\''+viewGanttStatus+'\')">进展状态：'+progressStatus+'</h1>';
					//str +="<h1 id='constructionProgress' onclick='viewGantt("+"'"+info.id+"\',\'"+viewGanttStatus+"\')'>进展状态："+progressStatus+"</h1>";
				} else {
					$("#constructionProgress").hide();
				}
				
				$(".pointdata").html(str);
			}
		}
	});
	

	myChart.on("click", function (params){
		if(params.data){
			if(params.data.extraData){
				var sysWindow = window.parent.opener;
				if(!sysWindow.closed){
					sysWindow.Main.openTab('局站管理', '/project-edge/facility/site/main.htm',null,null,{id:params.data.extraData.id});
					window.open("javascript:;", 'ceni_main');
				}
			}
		}
	});
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

