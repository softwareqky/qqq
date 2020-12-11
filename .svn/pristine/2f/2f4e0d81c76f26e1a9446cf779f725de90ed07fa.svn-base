var map;

//定义不同节点或光缆不同状态的图层（共15种）
var _keySite1OverlayGroups  //核心节点未落实图层
var _keySite2OverlayGroups  //核心节点进行中图层
var _keySite3OverlayGroups  //核心节点已具备图层

var _edgeSite1OverlayGroups  //边缘节点未落实图层
var _edgeSite2OverlayGroups  //边缘节点进行中图层
var _edgeSite3OverlayGroups  //边缘节点已具备图层

var _combineSite1OverlayGroups  //合设节点未落实图层
var _combineSite2OverlayGroups  //合设节点进行中图层
var _combineSite3OverlayGroups  //合设节点已具备图层

var _highspeedSite1OverlayGroups  //高速传输核心节点未落实图层
var _highspeedSite2OverlayGroups  //高速传输核心节点进行中图层
var _highspeedSite3OverlayGroups  //高速传输核心节点已具备图层

var _repeatSite1OverlayGroups  //高速传输中继节点未落实图层
var _repeatSite2OverlayGroups  //高速传输中继节点进行中图层
var _repeatSite3OverlayGroups  //高速传输中继节点已具备图层

var _citySegment1OverlayGroups  //城域光缆未落实图层
var _citySegment2OverlayGroups  //城域光缆进行中图层
var _citySegment3OverlayGroups  //城域光缆已具备图层

var _highspeedSegment1OverlayGroups  //高速光缆未落实图层
var _highspeedSegment2OverlayGroups  //高速光缆进行中图层
var _highspeedSegment3OverlayGroups  //高速光缆已具备图层

var _linkReadyOverlayGroups  //链路已具备
var _linkNotReadyOverlayGroups  //链路未具备

var markData = {};
var lineData = {};
var markerMap = {};

var _firstLoad = true;

var ICON_BIG_OFFSET = [-21,-35];
var ICON_SMALL_OFFSET = [-9, -10];

var ICON_BIG_SIZE = new AMap.Size(36, 36);
var ICON_SMALL_SIZE = new AMap.Size(21, 21);

var nameFlag = 1;

$(function(){
	$.ajax({
		url : BASE_URL + '/system/map-custom/get-defined-icon.json',
		type : 'POST',
		data : {},
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			if(response.status == 1){
				var data = response.dataMap.returnObj;
				for(i = 0;i < data.siteIconList.length;i++){
					if(data.siteIconList[i].iconName == null){
						markData[data.siteIconList[i].siteType] = {
								iconName: data.siteIconList[i].iconName,
								x: data.siteIconList[i].x,
								y: data.siteIconList[i].y,
								size: data.siteIconList[i].size
							}
					}else{
						markData[data.siteIconList[i].siteType] = {
								iconName: "img/home/markers/"+data.siteIconList[i].iconName,
								x: data.siteIconList[i].x,
								y: data.siteIconList[i].y,
								size: data.siteIconList[i].size
							}
					}
				}
				
				for(i = 0;i < data.segmentIconList.length;i++){
					lineData[data.segmentIconList[i].segmentType] = {
							width: data.segmentIconList[i].width,
							lineStyle: data.segmentIconList[i].lineStyle,
							color: data.segmentIconList[i].color
						}
				}
				
				for(i in markData){
					if(markData[i].iconName != null){
						$("#"+i).attr("src",markData[i].iconName);
					}
				}
				for(i in lineData){
					if(lineData[i].width != null){
						$("#"+i).css("borderBottom",lineData[i].width+"px "+lineData[i].lineStyle+" "+lineData[i].color);
					}
				}
			}
		}
	},false)
	
	laydate.render({
		elem: '#startTime',
		done: function(value, date, endDate) {
			$("#startTime").val(value);
			initMapData();
		}
	});
	laydate.render({
		elem: '#endTime',
		done: function(value, date, endDate) {
			$("#endTime").val(value);
			initMapData();
		}
	});
	initAreaCombobox();
	
	$("#keySite, #highspeedSite, #repeatSite, #citySegment, #highspeedSegment, #link, #edgeSite").click(function(){
		$(this).parents("li").find("dl dt input[type=checkbox]").prop("checked",$(this).is(":checked"));
		siwtchSite();
	});
	$(".childSelect").click(function(){
		if($(this).parents("dl").find("input[type=checkbox]").eq(0).is(":checked") && $(this).parents("dl").find("input[type=checkbox]").eq(1).is(":checked") && $(this).parents("dl").find("input[type=checkbox]").eq(2).is(":checked")){
			$(this).parents("li").find(".parentCheckbox").prop("checked",true);
		}else{
			$(this).parents("li").find(".parentCheckbox").prop("checked",false);
		}
		siwtchSite();
	});
	$(".linkChildSelect").click(function(){
		if($(this).parents("dl").find("input[type=checkbox]").eq(0).is(":checked") && $(this).parents("dl").find("input[type=checkbox]").eq(1).is(":checked")){
			$(this).parents("li").find(".parentCheckbox").prop("checked",true);
		}else{
			$(this).parents("li").find(".parentCheckbox").prop("checked",false);
		}
		siwtchSite();
	});
	
	$("input[name='range']").click(function(){
		if($(this).attr("id") == "all"){
			$("#startTime").val("");
			$("#endTime").val("");
		}else if($(this).attr("id") == "year"){
			$("#startTime").val(moment().year(moment().year()).startOf('year').format('YYYY-MM-DD'));
			$("#endTime").val(moment().year(moment().year()).endOf('year').format('YYYY-MM-DD'));
		}else if($(this).attr("id") == "quarter"){
			$("#startTime").val(moment().quarter(moment().quarter()).startOf('quarter').format('YYYY-MM-DD'));
			$("#endTime").val(moment().quarter(moment().quarter()).endOf('quarter').format('YYYY-MM-DD'));
		}else if($(this).attr("id") == "month"){
			$("#startTime").val(moment().month(moment().month()).startOf('month').format('YYYY-MM-DD'));
			$("#endTime").val(moment().month(moment().month()).endOf('month').format('YYYY-MM-DD'));
		}else if($(this).attr("id") == "week"){
			$("#startTime").val(moment().week(moment().week()).startOf('week').format('YYYY-MM-DD'));
			$("#endTime").val(moment().week(moment().week()).endOf('week').format('YYYY-MM-DD'));
		}
		initMapData();
	});
	$(".selectTime").change(function(){
		$("input[name='range']").attr("checked",false);
		initMapData();
	});
	$("#area").change(function(){
		initMapData();
	});
	
	// 图层被点击时的处理
	$(".menu-layer").click(function(){
		
		$(".menu-layer").removeClass("selectedLayer");
		$(this).addClass("selectedLayer");
		
		if($(this).attr("id") == "server"){
			$("#invalidcondition").addClass("invalidcondition");
		}else{
			$("#invalidcondition").removeClass("invalidcondition");
		}
		if($(this).attr("id") == "plan" || $(this).attr("id") == "problem"){
			$(".pointerUl dl dt").hide();
		}else{
			$(".pointerUl dl dt").show();
		}
		if($(this).attr("id") == "server"){
			$(".pointerUl").hide();
		}else{
			$(".pointerUl").show();
		}
		
		console.log('图层切换');
		initMapData();
	});
	
	$('.screenshot').on("click", function(event) {
		//#proMain:要截图的DOM元素
        //useCORS:true:解决跨域问题
        html2canvas(document.querySelector('#map'),{useCORS:true}).then(function (canvas) {
            //获取年月日作为文件名
            var timers=new Date();
            var fullYear=timers.getFullYear();
            var month=timers.getMonth()+1;
            var date=timers.getDate();
            var randoms=Math.random()+'';
            //年月日加上随机数
            var numberFileName=fullYear+''+month+date+randoms.slice(3,10);
            var imgData=canvas.toDataURL();
            //保存图片
            var saveFile = function(data, filename){
                var save_link = document.createElementNS('http://www.w3.org/1999/xhtml', 'a');
                save_link.href = data;
                save_link.download = filename;

                var event = document.createEvent('MouseEvents');
                event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
                save_link.dispatchEvent(event);
            };
            //最终文件名+文件格式
            var filename = numberFileName + '.png';
            saveFile(imgData,filename);
            //document.body.appendChild(canvas);  把截的图显示在网页上
        })
	}); 
	
	$(".m-menu-left ul li").click(function(){
		$(".m-menu-left ul li").removeClass("m-currentMenu");
		$(this).addClass("m-currentMenu");
		$(".m-menu-right").animate({width:"265px"});
		$(".m-menu").animate({width:"336px"});
	});
	
	$(".pointerUl li").click(function(){
		$(".pointerUl li").removeClass("currnetPointer");
		$(this).addClass("currnetPointer");
	})
	
	$("#invalidcondition span").click(function(){
		$("#invalidcondition span").removeClass("m-current-tab");
		$(this).addClass("m-current-tab");
		initMapData();
		if($(this).attr("type") == "1"){
			$(".baseSite").show();
			$(".noBaseSite").hide();
		}else{
			$(".baseSite").hide();
			$(".noBaseSite").show();
		}
	});
	
	$("#classify li").click(function(){
		$("#classify li").removeClass("m-current-tab");
		$(this).addClass("m-current-tab");
		$(".chartDiv").hide();
		$("#"+$(this).attr("href")).show();
	});
	
	$(".m-menu-hide span").click(function(){
		$(".m-menu-right").animate({width:"0"});
		$(".m-menu").animate({width:"70px"});
	});
	
	$(".m-menu-left ul li").click(function(){
		$(".m-menu-left ul li").removeClass("m-currentMenu");
		$(this).addClass("m-currentMenu");
		$(".m-menu-content .m-menu-box").hide();
		$(".m-menu-content .m-menu-box").eq($(this).index()).show();
	});
	
	$("#setMap .m-menu-list .m-menu-label4 button").click(function(){
		setMapStyle($(this).attr("styleFlag"));
	});
	
	$.ajax({
		url : BASE_URL + '/facility/site/list-piechart-info.json',
		type : 'POST',
		data : "",
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			$(".c_loading").hide();
			
			
			if(response.status == 1){
				drawChart('chart1','核心节点',['计划中','进行中','已具备'],[
					{value:response.dataMap.returnObj.basicCoreNodeSiteList[0].unfinishCount, name:'计划中'},
					{value:response.dataMap.returnObj.basicCoreNodeSiteList[0].ongoingCount, name:'进行中'},
					{value:response.dataMap.returnObj.basicCoreNodeSiteList[0].finishCount, name:'已具备'},
				]);
				drawChart('chart2','传输骨干节点',['计划中','进行中','已具备'],[
					{value:response.dataMap.returnObj.basicHighCoreSiteList[0].unfinishCount, name:'计划中'},
					{value:response.dataMap.returnObj.basicHighCoreSiteList[0].ongoingCount, name:'进行中'},
					{value:response.dataMap.returnObj.basicHighCoreSiteList[0].finishCount, name:'已具备'},
				]);
				drawChart('chart3','传输中继节点',['计划中','进行中','已具备'],[
					{value:response.dataMap.returnObj.basicHighRelaySiteList[0].unfinishCount, name:'计划中'},
					{value:response.dataMap.returnObj.basicHighRelaySiteList[0].ongoingCount, name:'进行中'},
					{value:response.dataMap.returnObj.basicHighRelaySiteList[0].finishCount, name:'已具备'},
				]);
				
				drawChart('chart4','核心节点',['计划中','进行中','已具备'],[
					{value:response.dataMap.returnObj.programmableCoreSiteList[0].unfinishCount, name:'计划中'},
					{value:response.dataMap.returnObj.programmableCoreSiteList[0].ongoingCount, name:'进行中'},
					{value:response.dataMap.returnObj.programmableCoreSiteList[0].finishCount, name:'已具备'},
				]);
				drawChart('chart5','边缘节点',['计划中','进行中','已具备'],[
					{value:response.dataMap.returnObj.programmableEdgeSiteList[0].unfinishCount, name:'计划中'},
					{value:response.dataMap.returnObj.programmableEdgeSiteList[0].ongoingCount, name:'进行中'},
					{value:response.dataMap.returnObj.programmableEdgeSiteList[0].finishCount, name:'已具备'},
				]);
				
				drawChart('chart6','核心节点',['计划中','进行中','已具备'],[
					{value:response.dataMap.returnObj.sdnCoreSiteList[0].unfinishCount, name:'计划中'},
					{value:response.dataMap.returnObj.sdnCoreSiteList[0].ongoingCount, name:'进行中'},
					{value:response.dataMap.returnObj.sdnCoreSiteList[0].finishCount, name:'已具备'},
				]);
				drawChart('chart7','边缘节点',['计划中','进行中','已具备'],[
					{value:response.dataMap.returnObj.sdnEdgeSiteList[0].unfinishCount, name:'计划中'},
					{value:response.dataMap.returnObj.sdnEdgeSiteList[0].ongoingCount, name:'进行中'},
					{value:response.dataMap.returnObj.sdnEdgeSiteList[0].finishCount, name:'已具备'},
				]);
			}else{
				//alert(response.message)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	})
	
	
	
	map = new AMap.Map('map', {
        zoom:7,//级别
        center: [117.56, 31.88],//中心点坐标
        viewMode:'2D'//使用3D视图
    });
    	
	function setMapStyle(value){
		var styleName = "amap://styles/" + value;
		map.setMapStyle(styleName);
	}
})

//初始化区域数据
function initAreaCombobox(){
	$.ajax({
		url : BASE_URL + '/system/city/list-province.json',
		type : 'POST',
		data : "",
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			if(response.status == 1){
				var str = "";
				for(var i = 0;i < response.dataMap.returnObj.length;i++){
					var comboboxData = response.dataMap.returnObj[i];
					str += '<option value="' + comboboxData.id + '">' + comboboxData.text + '</option>'
				}
				$("#area").html(str);
				initMapData();//初始化地图数据
			}else{
				//alert(response.message)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			
		}
	})
}

function showCityName(){
	nameFlag = 1;
	$("#showlable .showflag1").addClass("showflagtrue");
	$("#showlable .showflag2").removeClass("showflagtrue");
	initMapData(true);
}

function showStationName(){
	nameFlag = 2;
	$("#showlable .showflag2").addClass("showflagtrue");
	$("#showlable .showflag1").removeClass("showflagtrue");
	initMapData(true);
}

//$("input[name=showPointName]").click(function(){
//	alert('=======================');
//	nameFlag = $(this).val();
//	alert("nameFlag="+nameFlag);
//	alert('=======================');
//	initMapData(true);
//});

//初始化地图数据
function initMapData(flag){
	$(".m_loading").show();
	$(".legend_box p").hide();
	if($(".selectedLayer").attr("id") == "plan"){
		$(".planLayer").show();
	}else if($(".selectedLayer").attr("id") == "progress"){
		$(".progressLayer").show();
	}else if($(".selectedLayer").attr("id") == "problem"){
		$(".problemLayer").show();
	}else if($(".selectedLayer").attr("id") == "server"){
		$(".serverLayer").show();
	}
	if($("#networkTab ul li.m-current-tab").attr("type") == "1"){
		$(".legend_box .noBaseLayer:visible").hide();
	}else{
		$(".legend_box .baseLayer:visible").hide();
	}
	var param = {
			stationType:$("#invalidcondition span.m-current-tab").attr("type"),
			startTime:$("#startTime").val(),
			endTime:$("#endTime").val(),
			area:$("#area").val(),
			layer:$(".selectedLayer").attr("id")
		};
	map.clearMap();
	
	console.log('开始请求数据');
	$.ajax({
		url : BASE_URL + '/facility/site/list-site-info.json',
		type : 'POST',
		data : param,
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			$(".m_loading").hide();
			
			console.log('取得数据，状态值=' + response.status);
			if(response.status == 1){
				var keySite1 = []  //核心节点未落实
				var keySite2 = []  //核心节点进行中
				var keySite3 = []  //核心节点已具备
				var edgeSite1 = []  //边缘节点未落实
				var edgeSite2 = []  //边缘节点进行中
				var edgeSite3 = []  //边缘节点已具备
				var combineSite1 = []  //合设节点未落实
				var combineSite2 = []  //合设节点进行中
				var combineSite3 = []  //合设节点已具备
				var highspeedSite1 = []  //高速传输核心节点未落实
				var highspeedSite2 = []  //高速传输核心节点进行中
				var highspeedSite3 = []  //高速传输核心节点已具备
				var repeatSite1 = []  //高速传输中继节点未落实
				var repeatSite2 = []  //高速传输中继节点进行中
				var repeatSite3 = []  //高速传输中继节点已具备
				var citySegment1 = []  //城域光缆未落实
				var citySegment2 = []  //城域光缆进行中
				var citySegment3 = []  //城域光缆已具备
				var highspeedSegment1 = []  //高速光缆未落实
				var highspeedSegment2 = []  //高速光缆进行中
				var highspeedSegment3 = []  //高速光缆已具备
				var linkReady = []  //链路已具备
				var linkNotReady = [] //链路未具备
				if(param.layer == "problem"){
					siteList = response.dataMap.returnObj.mapProblemSiteList;
					segmentList = response.dataMap.returnObj.mapProblemSegmentList;
				}else{
					siteList = response.dataMap.returnObj.mapSiteList;
					segmentList = response.dataMap.returnObj.mapSegmentList;
				}
				
				//非边缘节点站点数据
				for(var i = 0;i < siteList.length;i++){
					var siteData = siteList[i];
					if (siteData == null) {
						continue;
					}
					if(siteData.longitude != null && siteData.latitude != null && (siteData.siteType == 1 || siteData.siteType == 2 || siteData.siteType == 3)){
						var siteStatusName = "";
						var icon = "";
						var offset = [];
						if(param.layer == "plan"){//计划图层
							if(siteData.siteType == 1){//核心节点
								if(markData.coreNode1.iconName == null){
									icon = "img/home/markers/marker1-14.png";
									offset = ICON_BIG_OFFSET;
								}else{
									icon = markData.coreNode1.iconName;
									offset = [markData.coreNode1.x,markData.coreNode1.y];
								}
								
								icon = new AMap.Icon({
									image: icon,
									imageSize: markData.coreNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
								});
								offset = markData.coreNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
								
							}else if(siteData.siteType == 2){//高速传输核心节点
								if(markData.highSpeedCoreNode1.iconName == null){
									icon = "img/home/markers/marker1-14.png";
									offset = ICON_BIG_OFFSET;
								}else{
									icon = markData.highSpeedCoreNode1.iconName;
									offset = [markData.highSpeedCoreNode1.x,markData.highSpeedCoreNode1.y];
								}
								
								icon = new AMap.Icon({
									image: icon,
									imageSize: markData.highSpeedCoreNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
								});
								offset = markData.highSpeedCoreNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
								
							}else if(siteData.siteType == 3){//高速传输中继节点
								if(markData.highSpeedRelayNode1.iconName == null){
									icon = "img/home/markers/marker1-14.png";
									offset = ICON_BIG_OFFSET;
								}else{
									icon = markData.highSpeedRelayNode1.iconName;
									offset = [markData.highSpeedRelayNode1.x,markData.highSpeedRelayNode1.y];
								}
								
								icon = new AMap.Icon({
									image: icon,
									imageSize: markData.highSpeedRelayNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
								});
								offset = markData.highSpeedRelayNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
							}
						}else if(param.layer == "progress"){
							if(siteData.siteType == 1){//核心节点
								if(siteData.siteStatus == 0){//核心节点未落实
									if(markData.coreNode3.iconName == null){
										icon = "img/home/markers/marker1-20.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.coreNode3.iconName;
										offset = [markData.coreNode3.x,markData.coreNode3.y];
									}
									
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.coreNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.coreNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
									
								}else if(siteData.siteStatus == 1){//核心节点进行中
									if(markData.coreNode2.iconName == null){
										icon = "img/home/markers/marker1-13.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.coreNode2.iconName;
										offset = [markData.coreNode2.x,markData.coreNode2.y];
									}
									
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.coreNode2.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.coreNode2.size == 1 ? offset : ICON_SMALL_OFFSET;
									
								}else if(siteData.siteStatus == 2){//核心节点已具备
									if(markData.coreNode1.iconName == null){
										icon = "img/home/markers/marker1-14.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.coreNode1.iconName;
										offset = [markData.coreNode1.x,markData.coreNode1.y];
									}
									
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.coreNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.coreNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
								}
							}else if(siteData.siteType == 2){//高速传输核心节点
								if(siteData.siteStatus == 0){//未落实
									if(markData.highSpeedCoreNode3.iconName == null){
										icon = "img/home/markers/marker1-20.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedCoreNode3.iconName;
										offset = [markData.highSpeedCoreNode3.x,markData.highSpeedCoreNode3.y];
									}
									
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedCoreNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedCoreNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
									
								}else if(siteData.siteStatus == 1){//进行中
									if(markData.highSpeedCoreNode2.iconName == null){
										icon = "img/home/markers/marker1-13.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedCoreNode2.iconName;
										offset = [markData.highSpeedCoreNode2.x,markData.highSpeedCoreNode2.y];
									}
									
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedCoreNode2.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedCoreNode2.size == 1 ? offset : ICON_SMALL_OFFSET;
									
								}else if(siteData.siteStatus == 2){//已具备
									if(markData.highSpeedCoreNode1.iconName == null){
										icon = "img/home/markers/marker1-14.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedCoreNode1.iconName;
										offset = [markData.highSpeedCoreNode1.x,markData.highSpeedCoreNode1.y];
									}
									
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedCoreNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedCoreNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
									
								}
							}else if(siteData.siteType == 3){//高速传输中继节点
								if(siteData.siteStatus == 0){//未落实
									if(markData.highSpeedRelayNode3.iconName == null){
										icon = "img/home/markers/marker1-20.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedRelayNode3.iconName;
										offset = [markData.highSpeedRelayNode3.x,markData.highSpeedRelayNode3.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedRelayNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedRelayNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
									
								}else if(siteData.siteStatus == 1){//进行中
									if(markData.highSpeedRelayNode2.iconName == null){
										icon = "img/home/markers/marker1-13.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedRelayNode2.iconName;
										offset = [markData.highSpeedRelayNode2.x,markData.highSpeedRelayNode2.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedRelayNode2.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedRelayNode2.size == 1 ? offset : ICON_SMALL_OFFSET;
									
								}else if(siteData.siteStatus == 2){//已具备
									if(markData.highSpeedRelayNode1.iconName == null){
										icon = "img/home/markers/marker1-14.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedRelayNode1.iconName;
										offset = [markData.highSpeedRelayNode1.x,markData.highSpeedRelayNode1.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedRelayNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedRelayNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
									
								}
							}
						}else if(param.layer == "problem"){
							if(siteData.siteType == 1){//核心节点
								if(markData.coreNode3.iconName == null){
									icon = "img/home/markers/marker1-20.png";
									offset = ICON_BIG_OFFSET;
								}else{
									icon = markData.coreNode3.iconName;
									offset = [markData.coreNode3.x,markData.coreNode3.y];
								}
								icon = new AMap.Icon({
									image: icon,
									imageSize: markData.coreNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
								});
								offset = markData.coreNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
							}else if(siteData.siteType == 2){//高速传输核心节点
								if(markData.highSpeedCoreNode3.iconName == null){
									icon = "img/home/markers/marker1-20.png";
									offset = ICON_BIG_OFFSET;
								}else{
									icon = markData.highSpeedCoreNode3.iconName;
									offset = [markData.highSpeedCoreNode3.x,markData.highSpeedCoreNode3.y];
								}
								icon = new AMap.Icon({
									image: icon,
									imageSize: markData.highSpeedCoreNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
								});
								offset = markData.highSpeedCoreNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
							}else if(siteData.siteType == 3){//高速传输中继节点
								if(markData.highSpeedRelayNode3.iconName == null){
									icon = "img/home/markers/marker1-20.png";
									offset = ICON_BIG_OFFSET;
								}else{
									icon = markData.highSpeedRelayNode3.iconName;
									offset = [markData.highSpeedRelayNode3.x,markData.highSpeedRelayNode3.y];
								}
								icon = new AMap.Icon({
									image: icon,
									imageSize: markData.highSpeedRelayNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
								});
								offset = markData.highSpeedRelayNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
							}
						}else if(param.layer == "server"){
							if(siteData.siteType == 1){//核心节点
								if(siteData.isServer == 0){//核心节点未落实
									if(markData.coreNode3.iconName == null){
										icon = "img/home/markers/marker1-20.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.coreNode3.iconName;
										offset = [markData.coreNode3.x,markData.coreNode3.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.coreNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.coreNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
								}else if(siteData.isServer == 1){//核心节点已具备
									if(markData.coreNode1.iconName == null){
										icon = "img/home/markers/marker1-14.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.coreNode1.iconName;
										offset = [markData.coreNode1.x,markData.coreNode1.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.coreNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.coreNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
								}
							}else if(siteData.siteType == 2){//高速传输核心节点
								if(siteData.isServer == 0){//高速传输核心节点未落实
									if(markData.highSpeedCoreNode3.iconName == null){
										icon = "img/home/markers/marker1-20.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedCoreNode3.iconName;
										offset = [markData.highSpeedCoreNode3.x,markData.highSpeedCoreNode3.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedCoreNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedCoreNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
								}else if(siteData.isServer == 1){//高速传输核心节点已具备
									if(markData.highSpeedCoreNode1.iconName == null){
										icon = "img/home/markers/marker1-14.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedCoreNode1.iconName;
										offset = [markData.highSpeedCoreNode1.x,markData.highSpeedCoreNode1.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedCoreNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedCoreNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
								}
							}else if(siteData.siteType == 3){//高速传输中继节点
								if(siteData.isServer == 0){//高速传输中继节点未落实
									if(markData.highSpeedRelayNode3.iconName == null){
										icon = "img/home/markers/marker1-20.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedRelayNode3.iconName;
										offset = [markData.highSpeedRelayNode3.x,markData.highSpeedRelayNode3.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedRelayNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedRelayNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
								}else if(siteData.isServer == 1){//高速传输中继节点已具备
									if(markData.highSpeedRelayNode1.iconName == null){
										icon = "img/home/markers/marker1-14.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.highSpeedRelayNode1.iconName;
										offset = [markData.highSpeedRelayNode1.x,markData.highSpeedRelayNode1.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.highSpeedRelayNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.highSpeedRelayNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
								}
							}
						}

				        // 创建点实例
				        var marker = new AMap.Marker({
				            position: new AMap.LngLat(siteData.longitude, siteData.latitude),
							offset:new AMap.Pixel(offset[0], offset[1]),
							icon: icon,
				            extData:{
								name:siteData.stationName,
								type:siteData.siteTypeText,
								initNum:siteData.initNum,
								networkRoomAddress:siteData.networkRoomAddress,
								sdnNetworkProgress: siteData.sdnNetworkProgress,
								programmableNetworkProgress: siteData.programmableNetworkProgress,
								basicNetworkProgress: siteData.basicNetworkProgress
							}
				        });
				        
				        markerMap[siteData.longitude + '-' + siteData.latitude] = marker;
				        if (nameFlag == 1) {
				        	marker.setLabel({
								offset: new AMap.Pixel(0, 0),  //设置文本标注偏移量
								content: "<div class='markerinfo'>"+siteData.city+"</div>", //设置文本标注内容
								direction: 'right' //设置文本标注方位
							});
				        } else {
				        	marker.setLabel({
								offset: new AMap.Pixel(0, 0),  //设置文本标注偏移量
								content: "<div class='markerinfo'>"+siteData.stationName+"</div>", //设置文本标注内容
								direction: 'right' //设置文本标注方位
							});
				        }
				        

				      //如果希望的是点击标记才 出现这个信息窗口，那把 下面的注释去掉即可
						AMap.event.addListener(marker,'click',function(e){ //监听点标记的点击事件
							
							extData = this.getExtData();
							const stationType = $("#invalidcondition span.m-current-tab").attr("type");
							
							var str = "<div style=\"padding:5px;font-size:14px;\"><b style='line-height:34px;font-size:16px;'>"+extData.name+"</b>"
							+"<p>类型 : "+extData.type+"</p>"
							+"<p>初设编号 :"+extData.initNum+"</p>" +
							"<p>机房位置 :"+ ( (extData.networkRoomAddress == null || extData.networkRoomAddress == 0) ? '' : extData.networkRoomAddress) +"</p>";
							
							if (extData.sdnNetworkProgress && stationType == 3) {
								str += "<p>建设进展 :"+extData.sdnNetworkProgress+"</p>"
							}
							if (extData.programmableNetworkProgress && stationType == 2) {
								str += "<p>建设进展 :"+extData.programmableNetworkProgress+"</p>"
							}
							if (extData.basicNetworkProgress && stationType == 1) {
								str += "<p>建设进展 :"+extData.basicNetworkProgress+"</p>"
							}
							
							str += "</div>";
							"</div>";
							
							console.log(extData);
					        infoWindow = new AMap.InfoWindow({
					            content: str,  //使用默认信息窗体框样式，显示信息内容
					            offset: new AMap.Pixel(0,-30)
					        });
							infoWindow.open(map,this.getPosition()); //信息窗体打开
						});
						/*AMap.event.addListener(marker,'dblclick',function(e){
							if(this.getDraggable() == true){
								saveMarker(this)
								console.dir(this.getPosition())
								alert("修改成功")
							}else{
								dragMarker(this)
							}
						});*/
						
						if(param.layer == "plan"){//计划图层
							if(siteData.siteType == 1){//核心节点
								if(siteData.isCombineSite == 1){//合设节点
									combineSite3.push(marker);
								}else{
									keySite3.push(marker);
								}
							}else if(siteData.siteType == 2){//高速传输核心节点
								highspeedSite3.push(marker);
							}else if(siteData.siteType == 3){//高速传输中继节点
								repeatSite3.push(marker);
							}
						}else if(param.layer == "progress"){
							if(siteData.siteType == 1){//核心节点
								if(siteData.isCombineSite == 1){//合设节点
									if(siteData.siteStatus == 0){//合设节点未落实
										combineSite1.push(marker);
									}else if(siteData.siteStatus == 1){//合设节点进行中
										combineSite2.push(marker);
									}else if(siteData.siteStatus == 2){//合设节点已具备
										combineSite3.push(marker);
									}
								}else{
									if(siteData.siteStatus == 0){//核心节点未落实
										keySite1.push(marker);
									}else if(siteData.siteStatus == 1){//核心节点进行中
										keySite2.push(marker);
									}else if(siteData.siteStatus == 2){//核心节点已具备
										keySite3.push(marker);
									}
								}
							}else if(siteData.siteType == 2){//高速传输核心节点
								if(siteData.siteStatus == 0){//高速传输核心节点未落实
									highspeedSite1.push(marker);
								}else if(siteData.siteStatus == 1){//高速传输核心节点进行中
									highspeedSite2.push(marker);
								}else if(siteData.siteStatus == 2){//高速传输核心节点已具备
									highspeedSite3.push(marker);
								}
							}else if(siteData.siteType == 3){//高速传输中继节点
								if(siteData.siteStatus == 0){//高速传输中继节点未落实
									repeatSite1.push(marker);
								}else if(siteData.siteStatus == 1){//高速传输中继节点进行中
									repeatSite2.push(marker);
								}else if(siteData.siteStatus == 2){//高速传输中继节点已具备
									repeatSite3.push(marker);
								}
							}
						}else if(param.layer == "problem"){
							if(siteData.siteType == 1){//核心节点
								if(siteData.isCombineSite == 1){//合设节点
									combineSite1.push(marker);
								}else{
									keySite1.push(marker);
								}
							}else if(siteData.siteType == 2){//高速传输核心节点
								highspeedSite1.push(marker);
							}else if(siteData.siteType == 3){//高速传输中继节点
								repeatSite1.push(marker);
							}
						}else if(param.layer == "server"){
							if(siteData.siteType == 1){//核心节点
								if(siteData.isCombineSite == 1){//合设节点
									if(siteData.isServer == 0){//核心节点未落实
										combineSite1.push(marker);
									}else if(siteData.isServer == 1){//核心节点已具备
										combineSite3.push(marker);
									}
								}else{
									if(siteData.isServer == 0){//核心节点未落实
										keySite1.push(marker);
									}else if(siteData.isServer == 1){//核心节点已具备
										keySite3.push(marker);
									}
								}
							}else if(siteData.siteType == 2){//高速传输核心节点
								if(siteData.isServer == 0){//高速传输核心节点未落实
									highspeedSite1.push(marker);
								}else if(siteData.isServer == 1){//高速传输核心节点已具备
									highspeedSite3.push(marker);
								}
							}else if(siteData.siteType == 3){//高速传输中继节点
								if(siteData.isServer == 0){//高速传输中继节点未落实
									repeatSite1.push(marker);
								}else if(siteData.isServer == 1){//高速传输中继节点已具备
									repeatSite3.push(marker);
								}
							}
						}
					}
				}
				
				console.log('遍历结束所有站点数据');
				
				_keySite1OverlayGroups = new AMap.OverlayGroup(keySite1);
				 map.add(_keySite1OverlayGroups);
				 _keySite2OverlayGroups = new AMap.OverlayGroup(keySite2);
				 map.add(_keySite2OverlayGroups);
				 _keySite3OverlayGroups = new AMap.OverlayGroup(keySite3);
				 map.add(_keySite3OverlayGroups);
				 
				 _highspeedSite1OverlayGroups = new AMap.OverlayGroup(highspeedSite1);
				 map.add(_highspeedSite1OverlayGroups);
				 _highspeedSite2OverlayGroups = new AMap.OverlayGroup(highspeedSite2);
				 map.add(_highspeedSite2OverlayGroups);
				 _highspeedSite3OverlayGroups = new AMap.OverlayGroup(highspeedSite3);
				 map.add(_highspeedSite3OverlayGroups);
				 
				 _repeatSite1OverlayGroups = new AMap.OverlayGroup(repeatSite1);
				 map.add(_repeatSite1OverlayGroups);
				 _repeatSite2OverlayGroups = new AMap.OverlayGroup(repeatSite2);
				 map.add(_repeatSite2OverlayGroups);
				 _repeatSite3OverlayGroups = new AMap.OverlayGroup(repeatSite3);
				 map.add(_repeatSite3OverlayGroups);
				 
				 _combineSite1OverlayGroups = new AMap.OverlayGroup(combineSite1);
				 map.add(_combineSite1OverlayGroups);
				 _combineSite2OverlayGroups = new AMap.OverlayGroup(combineSite2);
				 map.add(_combineSite2OverlayGroups);
				 _combineSite3OverlayGroups = new AMap.OverlayGroup(combineSite3);
				 map.add(_combineSite3OverlayGroups);
				 
				 //中继段数据
				for(var i = 0;i < segmentList.length;i++){
					var segmentData = segmentList[i];
					if(segmentData.endALng != null && segmentData.endALat != null && segmentData.endBLng != null && segmentData.endBLat != null){
						var color = "green";
						var strokeWeight = 2;
						var strokeStyle = "solid";
						
						if(param.layer == "plan"){//计划图层
							if(segmentData.segmentType == 1){//城域光缆
								if(lineData.metroCable1.width == null){
									color = "green";
								}else{
									color = lineData.metroCable1.color;
									strokeWeight = lineData.metroCable1.width;
									strokeStyle = lineData.metroCable1.lineStyle;
								}
							}else if(segmentData.segmentType == 2){//高速光缆
								if(lineData.highSpeedCable1.width == null){
									color = "green";
								}else{
									color = lineData.highSpeedCable1.color;
									strokeWeight = lineData.highSpeedCable1.width;
									strokeStyle = lineData.highSpeedCable1.lineStyle;
								}
							}
						}else if(param.layer == "progress"){
							if(segmentData.segmentType == 1){//城域光缆
								if(segmentData.segmentStatus == 0){//城域光缆未落实
									if(lineData.metroCable3.width == null){
										color = "red";
									}else{
										color = lineData.metroCable3.color;
										strokeWeight = lineData.metroCable3.width;
										strokeStyle = lineData.metroCable3.lineStyle;
									}
								}else if(segmentData.segmentStatus == 1){//城域光缆进行中
									if(lineData.metroCable2.width == null){
										color = "yellow";
									}else{
										color = lineData.metroCable2.color;
										strokeWeight = lineData.metroCable2.width;
										strokeStyle = lineData.metroCable2.lineStyle;
									}
								}else if(segmentData.segmentStatus == 2){//城域光缆已具备
									if(lineData.metroCable1.width == null){
										color = "green";
									}else{
										color = lineData.metroCable1.color;
										strokeWeight = lineData.metroCable1.width;
										strokeStyle = lineData.metroCable1.lineStyle;
									}
								}
							}else if(segmentData.segmentType == 2){//高速光缆
								if(segmentData.segmentStatus == 0){//未落实
									if(lineData.highSpeedCable3.width == null){
										color = "red";
									}else{
										color = lineData.highSpeedCable3.color;
										strokeWeight = lineData.highSpeedCable3.width;
										strokeStyle = lineData.highSpeedCable3.lineStyle;
									}
								}else if(segmentData.segmentStatus == 1){//进行中
									if(lineData.highSpeedCable2.width == null){
										color = "yellow";
									}else{
										color = lineData.highSpeedCable2.color;
										strokeWeight = lineData.highSpeedCable2.width;
										strokeStyle = lineData.highSpeedCable2.lineStyle;
									}
								}else if(segmentData.segmentStatus == 2){//已具备
									if(lineData.highSpeedCable1.width == null){
										color = "green";
									}else{
										color = lineData.highSpeedCable1.color;
										strokeWeight = lineData.highSpeedCable1.width;
										strokeStyle = lineData.highSpeedCable1.lineStyle;
									}
								}
							}
						}else if(param.layer == "problem"){
							if(segmentData.segmentType == 1){//城域光缆
								if(lineData.metroCable3.width == null){
									color = "red";
								}else{
									color = lineData.metroCable3.color;
									strokeWeight = lineData.metroCable3.width;
									strokeStyle = lineData.metroCable3.lineStyle;
								}
							}else if(segmentData.segmentType == 2){//高速光缆
								if(lineData.highSpeedCable3.width == null){
									color = "red";
								}else{
									color = lineData.highSpeedCable3.color;
									strokeWeight = lineData.highSpeedCable3.width;
									strokeStyle = lineData.highSpeedCable3.lineStyle;
								}
							}
						}else if(param.layer == "server"){
							
						}
						 // 创建线实例
				        var polyline = new AMap.Polyline({
				            path: [new AMap.LngLat(segmentData.endALng, segmentData.endALat), new AMap.LngLat(segmentData.endBLng, segmentData.endBLat)],
				            strokeWeight: strokeWeight,
				            strokeColor: color,
				            isOutline: false,//是否描边
				            strokeStyle:strokeStyle,
				           // borderWeight: 1,
				           // outlineColor: '#ffeeff'
				            extData:{
				            	endAText:segmentData.endAText,
				            	endBText:segmentData.endBText
							}
				        });
				        AMap.event.addListener(polyline,'mouseover',function(e){ //监听点标记的点击事件
							var info = [];
					        infoWindow = new AMap.InfoWindow({
					            content: "<div class='' style=''>"+this.getExtData().endAText+"-"+this.getExtData().endBText+"</div>"  //使用默认信息窗体框样式，显示信息内容
					        });
							infoWindow.open(map,[e.lnglat.lng,e.lnglat.lat]); //信息窗体打开
						});
				        AMap.event.addListener(polyline,'mouseout',function(e){ //监听点标记的点击事件
							infoWindow.close(); //信息窗体打开
						});
				        if(segmentData.segmentType == 1){//城域光缆
							if(segmentData.segmentStatus == 0){//城域光缆未落实
								citySegment1.push(polyline);
							}else if(segmentData.segmentStatus == 1){//城域光缆进行中
								citySegment2.push(polyline);
							}else if(segmentData.segmentStatus == 2){//城域光缆已具备
								citySegment3.push(polyline);
							}
						}else if(segmentData.segmentType == 2){//高速光缆
							if(segmentData.segmentStatus == 0){//高速光缆未落实
								highspeedSegment1.push(polyline);
							}else if(segmentData.segmentStatus == 1){//高速光缆进行中
								highspeedSegment2.push(polyline);
							}else if(segmentData.segmentStatus == 2){//高速光缆已具备
								highspeedSegment3.push(polyline);
							}
						}
					}
				}
				
				console.log('遍历结束所有中继段数据');
				
				 _citySegment1OverlayGroups = new AMap.OverlayGroup(citySegment1);
				 map.add(_citySegment1OverlayGroups);
				 _citySegment2OverlayGroups = new AMap.OverlayGroup(citySegment2);
				 map.add(_citySegment2OverlayGroups);
				 _citySegment3OverlayGroups = new AMap.OverlayGroup(citySegment3);
				 map.add(_citySegment3OverlayGroups);
				 _highspeedSegment1OverlayGroups = new AMap.OverlayGroup(highspeedSegment1);
				 map.add(_highspeedSegment1OverlayGroups);
				 _highspeedSegment2OverlayGroups = new AMap.OverlayGroup(highspeedSegment2);
				 map.add(_highspeedSegment2OverlayGroups);
				 _highspeedSegment3OverlayGroups = new AMap.OverlayGroup(highspeedSegment3);
				 map.add(_highspeedSegment3OverlayGroups);
				 
				 
				 //链路数据
				 for(var i = 0;i < response.dataMap.returnObj.mapLinkList.length;i++){
					var linkData = response.dataMap.returnObj.mapLinkList[i];
					if(linkData.endALng != null && linkData.endALat != null && linkData.endBLng != null && linkData.endBLat != null){
						var color = "green";
						var strokeWeight = 2;
						var strokeStyle = "solid";
						if(param.layer == "plan"){//计划图层
							if(lineData.coreLink1.width == null){
								color = "green";
							}else{
								color = lineData.coreLink1.color;
								strokeWeight = lineData.coreLink1.width;
								strokeStyle = lineData.coreLink1.lineStyle;
							}
						}else if(param.layer == "progress"){
							if(linkData.dataLinkStatus == 2){
								if(lineData.coreLink1.width == null){
									color = "green";
								}else{
									color = lineData.coreLink1.color;
									strokeWeight = lineData.coreLink1.width;
									strokeStyle = lineData.coreLink1.lineStyle;
								}
							}else if(linkData.dataLinkStatus == 1){
								if(lineData.coreLink2.width == null){
									color = "red";
								}else{
									color = lineData.coreLink2.color;
									strokeWeight = lineData.coreLink2.width;
									strokeStyle = lineData.coreLink2.lineStyle;
								}
							}
						}else if(param.layer == "problem"){
							if(lineData.coreLink2.width == null){
								color = "red";
							}else{
								color = lineData.coreLink2.color;
								strokeWeight = lineData.coreLink2.width;
								strokeStyle = lineData.coreLink2.lineStyle;
							}
						}else if(param.layer == "server"){
							
						}
						 // 创建线实例
				        var polyline = new AMap.Polyline({
				            path: [new AMap.LngLat(linkData.endALng, linkData.endALat), new AMap.LngLat(linkData.endBLng, linkData.endBLat)],
				            strokeWeight: strokeWeight,
				            strokeColor: color,
				            isOutline: false,//是否描边
				            strokeStyle: strokeStyle,
				            extData:{
				            	endAText:linkData.endA,
				            	endBText:linkData.endB
							}
				        });
				        AMap.event.addListener(polyline,'mouseover',function(e){ //监听点标记的点击事件
							var info = [];
					        infoWindow = new AMap.InfoWindow({
					            content: "<div class='' style=''>"+this.getExtData().endAText+"-"+this.getExtData().endBText+"</div>"  //使用默认信息窗体框样式，显示信息内容
					        });
							infoWindow.open(map,[e.lnglat.lng,e.lnglat.lat]); //信息窗体打开
						});
				        AMap.event.addListener(polyline,'mouseout',function(e){ //监听点标记的点击事件
							infoWindow.close(); //信息窗体打开
						});
						if(linkData.dataLinkStatus == 1){//链路未具备
							linkNotReady.push(polyline);
						}else if(linkData.dataLinkStatus == 2){//链路已具备
							linkReady.push(polyline);
						}
					}
				}
				 
				 console.log('遍历结束所有链路数据');
				 
				 _linkReadyOverlayGroups = new AMap.OverlayGroup(linkReady);
				 map.add(_linkReadyOverlayGroups);
				 _linkNotReadyOverlayGroups = new AMap.OverlayGroup(linkNotReady);
				 map.add(_linkNotReadyOverlayGroups);
				 
				 
				 //边缘节点数据(非合设节点)
				 for(var i = 0;i < response.dataMap.returnObj.mapEdgeSiteList.length;i++){
						var siteData = response.dataMap.returnObj.mapEdgeSiteList[i];
						if(siteData.longitude != null && siteData.latitude != null){
							var siteStatusName = "";
							var icon = "";
							var offset = [];
							if(param.layer == "plan"){//计划图层
								if(markData.edgeNode1.iconName == null){
									icon = "img/home/markers/marker1-14.png";
									offset = ICON_BIG_OFFSET;
								}else{
									icon = markData.edgeNode1.iconName;
									offset = [markData.edgeNode1.x,markData.edgeNode1.y];
								}
								icon = new AMap.Icon({
									image: icon,
									imageSize: markData.edgeNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
								});
								offset = markData.edgeNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
							}else if(param.layer == "progress"){
								if(siteData.siteStatus == 0){
									if(markData.edgeNode3.iconName == null){
										icon = "img/home/markers/marker1-20.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.edgeNode3.iconName;
										offset = [markData.edgeNode3.x,markData.edgeNode3.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.edgeNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.edgeNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
								}else if(siteData.siteStatus == 1){
									if(markData.edgeNode2.iconName == null){
										icon = "img/home/markers/marker1-13.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.edgeNode2.iconName;
										offset = [markData.edgeNode2.x,markData.edgeNode2.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.edgeNode2.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.edgeNode2.size == 1 ? offset : ICON_SMALL_OFFSET;
								}else if(siteData.siteStatus == 2){
									if(markData.edgeNode1.iconName == null){
										icon = "img/home/markers/marker1-14.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.edgeNode1.iconName;
										offset = [markData.edgeNode1.x,markData.edgeNode1.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.edgeNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.edgeNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
								}
							}else if(param.layer == "problem"){
								if(markData.edgeNode3.iconName == null){
									icon = "img/home/markers/marker1-20.png";
									offset = ICON_BIG_OFFSET;
								}else{
									icon = markData.edgeNode3.iconName;
									offset = [markData.edgeNode3.x,markData.edgeNode3.y];
								}
								icon = new AMap.Icon({
									image: icon,
									imageSize: markData.edgeNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
								});
								offset = markData.edgeNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
							}else if(param.layer == "server"){
								if(siteData.isServer == 0){
									if(markData.edgeNode3.iconName == null){
										icon = "img/home/markers/marker1-20.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.edgeNode3.iconName;
										offset = [markData.edgeNode3.x,markData.edgeNode3.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.edgeNode3.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.edgeNode3.size == 1 ? offset : ICON_SMALL_OFFSET;
								}else if(siteData.isServer == 1){
									if(markData.edgeNode1.iconName == null){
										icon = "img/home/markers/marker1-14.png";
										offset = ICON_BIG_OFFSET;
									}else{
										icon = markData.edgeNode1.iconName;
										offset = [markData.edgeNode1.x,markData.edgeNode1.y];
									}
									icon = new AMap.Icon({
										image: icon,
										imageSize: markData.edgeNode1.size == 1 ? ICON_BIG_SIZE : ICON_SMALL_SIZE
									});
									offset = markData.edgeNode1.size == 1 ? offset : ICON_SMALL_OFFSET;
								}
							}

					        // 创建点实例
					        var marker = new AMap.Marker({
					            position: new AMap.LngLat(siteData.longitude, siteData.latitude),
								offset:new AMap.Pixel(offset[0], offset[1]),
					            icon: icon,
					            extData:{
									name:siteData.stationName,
									type:siteData.siteTypeText,
									initNum:siteData.initNum,
									networkRoomAddress:siteData.networkRoomAddress,
									sdnNetworkProgress: siteData.sdnNetworkProgress,
									programmableNetworkProgress: siteData.programmableNetworkProgress,
									basicNetworkProgress: siteData.basicNetworkProgress
								}
					        });
					        markerMap[siteData.longitude + '-' + siteData.latitude] = marker;
					        marker.setLabel({
								offset: new AMap.Pixel(0, 0),  //设置文本标注偏移量
								content: "<div class='markerinfo'>"+siteData.city+"</div>", //设置文本标注内容
								direction: 'right' //设置文本标注方位
							});

					      //如果希望的是点击标记才 出现这个信息窗口，那把 下面的注释去掉即可
							AMap.event.addListener(marker,'click',function(e){ //监听点标记的点击事件
								
								extData = this.getExtData();
								const stationType = $("#invalidcondition span.m-current-tab").attr("type");
								
								var str = "<div style=\"padding:5px;font-size:14px;\"><b style='line-height:34px;font-size:16px;'>"+extData.name+"</b>"
								+"<p>类型 : 边缘节点</p>"
								+"<p>初设编号 :"+extData.initNum+"</p>" +
								"<p>机房位置 :"+ ((extData.networkRoomAddress == null || extData.networkRoomAddress == 0) ? '' : extData.networkRoomAddress)+"</p>";
								
								if (extData.sdnNetworkProgress && stationType == 3) {
									str += "<p>建设进展 :"+extData.sdnNetworkProgress+"</p>"
								}
								if (extData.programmableNetworkProgress && stationType == 2) {
									str += "<p>建设进展 :"+extData.programmableNetworkProgress+"</p>"
								}
								if (extData.basicNetworkProgress && stationType == 1) {
									str += "<p>建设进展 :"+extData.basicNetworkProgress+"</p>"
								}
								
								str += "</div>";
						        infoWindow = new AMap.InfoWindow({
						            content: str,  //使用默认信息窗体框样式，显示信息内容
						            offset: new AMap.Pixel(0,-30)
						        });
								infoWindow.open(map,this.getPosition()); //信息窗体打开
							});
							/*AMap.event.addListener(marker,'dblclick',function(e){
								if(this.getDraggable() == true){
									saveMarker(this)
									console.dir(this.getPosition())
									alert("修改成功")
								}else{
									dragMarker(this)
								}
							});*/
							
							if(param.layer == "plan"){//计划图层
								edgeSite3.push(marker);
							}else if(param.layer == "progress"){
								if(siteData.siteStatus == 0){//边缘节点未落实
									edgeSite1.push(marker);
								}else if(siteData.siteStatus == 1){//边缘节点进行中
									edgeSite2.push(marker);
								}else if(siteData.siteStatus == 2){//边缘节点已具备
									edgeSite3.push(marker);
								}
							}else if(param.layer == "problem"){
								edgeSite1.push(marker);
							}else if(param.layer == "server"){
								if(siteData.isServer == 0){//边缘节点未落实
									edgeSite1.push(marker);
								}else if(siteData.isServer == 1){//边缘节点已具备
									edgeSite3.push(marker);
								}
							}
						}
					}
				 
				 console.log('遍历结束所有边缘节点数据');
				 
				 _edgeSite1OverlayGroups = new AMap.OverlayGroup(edgeSite1);
				 map.add(_edgeSite1OverlayGroups);
				 _edgeSite2OverlayGroups = new AMap.OverlayGroup(edgeSite2);
				 map.add(_edgeSite2OverlayGroups);
				 _edgeSite3OverlayGroups = new AMap.OverlayGroup(edgeSite3);
				 map.add(_edgeSite3OverlayGroups);
				 
				//问题图层链路数据
				 for(var i = 0;i < response.dataMap.returnObj.mapProblemLinkList.length;i++){
					var linkData = response.dataMap.returnObj.mapProblemLinkList[i];
					if(linkData.endALng != null && linkData.endALat != null && linkData.endBLng != null && linkData.endBLat != null){
						var color = "red";
						var strokeWeight = 2;
						var strokeStyle = "solid";
						if(param.layer == "problem"){
							if(lineData.coreLink2.width == null){
								color = "red";
							}else{
								color = lineData.coreLink2.color;
								strokeWeight = lineData.coreLink2.width;
								strokeStyle = lineData.coreLink2.lineStyle;
							}
						}
						 // 创建线实例
				        var polyline = new AMap.Polyline({
				            path: [new AMap.LngLat(linkData.endALng, linkData.endALat), new AMap.LngLat(linkData.endBLng, linkData.endBLat)],
				            strokeWeight: strokeWeight,
				            strokeColor: color,
				            isOutline: false,//是否描边
				            strokeStyle: strokeStyle,
				            extData:{
				            	endAText:linkData.endA,
				            	endBText:linkData.endB
							}
				        });
				        AMap.event.addListener(polyline,'mouseover',function(e){ //监听点标记的点击事件
							var info = [];
					        infoWindow = new AMap.InfoWindow({
					            content: "<div class='' style=''>"+this.getExtData().endAText+"-"+this.getExtData().endBText+"</div>"  //使用默认信息窗体框样式，显示信息内容
					        });
							infoWindow.open(map,[e.lnglat.lng,e.lnglat.lat]); //信息窗体打开
						});
				        AMap.event.addListener(polyline,'mouseout',function(e){ //监听点标记的点击事件
							infoWindow.close(); //信息窗体打开
						});
						linkNotReady.push(polyline);
					}
				}
				 
				 console.log('遍历结束所有问题图层数据');
				 
				 _linkNotReadyOverlayGroups = new AMap.OverlayGroup(linkNotReady);
				 map.add(_linkNotReadyOverlayGroups);
				 
				 //合设节点数据
				 console.log('SwitchSite');
				 if(flag){
					 siwtchSite();
				 }else{
					 $(".pointerUl input[type=checkbox]").prop("checked",true);
					 siwtchSite();
				 }
				 
				 // 如果是第一次载入
				 if (_firstLoad) {
					 const latitude  = getUrlParam('latitude');
					 const longitude = getUrlParam('longitude');
					 if (latitude && longitude) {
						 map.setCenter([longitude, latitude]);
						 
						 console.log(markerMap, longitude, latitude);
						 const marker = markerMap[longitude + '-' + latitude];
						 marker.emit('click');
					 }
					 
					 $(".pointerUl input[id=highspeedSite]").prop("checked", false);
					 $(".pointerUl input[id=highspeedSite1]").prop("checked", false);
					 $(".pointerUl input[id=highspeedSite2]").prop("checked", false);
					 $(".pointerUl input[id=highspeedSite3]").prop("checked", false);
					 
					 $(".pointerUl input[id=repeatSite]").prop("checked", false);
					 $(".pointerUl input[id=repeatSite1]").prop("checked", false);
					 $(".pointerUl input[id=repeatSite2]").prop("checked", false);
					 $(".pointerUl input[id=repeatSite3]").prop("checked", false);
					 
					 //$(".pointerUl input[id=citySegment]").prop("checked", false);
					 //$(".pointerUl input[id=citySegment1]").prop("checked", false);
					 //$(".pointerUl input[id=citySegment2]").prop("checked", false);
					 //$(".pointerUl input[id=citySegment3]").prop("checked", false);
					 
					 _firstLoad = false;
					 
					 siwtchSite();
				 }
				 
			}else{
				console.log('跑到else了');
				//alert(response.message)
				siwtchSite();
			}
			
			siwtchSite();
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			console.log('报错了');
			//alert(response.message)
			siwtchSite();
		}
	})
}

function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

//网络组开关初始化
/*function initChcekbox(box,yText,nText,flag){
	$("#"+box).bootstrapSwitch({
		onText : yText,      // 设置ON文本  
		offText : nText,    // 设置OFF文本  
		onColor : "success",// 设置ON文本颜色     (info/success/warning/danger/primary)  
		offColor : "danger",  // 设置OFF文本颜色        (info/success/warning/danger/primary)  
		size : "large",    // 设置控件大小,从小到大  (mini/small/normal/large)  
		handleWidth:"100",//设置控件宽度
		labelWidth:"100",
        // 当开关状态改变时触发  
		onSwitchChange : function(event, state) {
			$(".forbbidden").css("filter","grayscale(100%)").attr("status","forbbidden");
			$(this).parents(".forbbidden").css("filter","grayscale(0)").removeAttr("status");
			initMapData();
		}
	}).bootstrapSwitch('state',flag);
}*/

function siwtchSite(){
	try{
		if($("#keySite1").is(":checked")){
			map.add(_keySite1OverlayGroups); 
		}else{
			map.remove(_keySite1OverlayGroups);
		}
		
		console.log('KeySite1 is checked: ' + $("#keySite1").is(":checked"));
	}
	catch(err){
	}
	try{
		if($("#keySite2").is(":checked")){
			map.add(_keySite2OverlayGroups); 
		}else{
			map.remove(_keySite2OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#keySite3").is(":checked")){
			map.add(_keySite3OverlayGroups); 
		}else{
			map.remove(_keySite3OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#highspeedSite1").is(":checked")){
			map.add(_highspeedSite1OverlayGroups); 
		}else{
			map.remove(_highspeedSite1OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#highspeedSite2").is(":checked")){
			map.add(_highspeedSite2OverlayGroups); 
		}else{
			map.remove(_highspeedSite2OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#highspeedSite3").is(":checked")){
			map.add(_highspeedSite3OverlayGroups); 
		}else{
			map.remove(_highspeedSite3OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#repeatSite1").is(":checked")){
			map.add(_repeatSite1OverlayGroups); 
		}else{
			map.remove(_repeatSite1OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#repeatSite2").is(":checked")){
			map.add(_repeatSite2OverlayGroups); 
		}else{
			map.remove(_repeatSite2OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#repeatSite3").is(":checked")){
			map.add(_repeatSite3OverlayGroups); 
		}else{
			map.remove(_repeatSite3OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#citySegment1").is(":checked")){
			map.add(_citySegment1OverlayGroups); 
		}else{
			map.remove(_citySegment1OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#citySegment2").is(":checked")){
			map.add(_citySegment2OverlayGroups); 
		}else{
			map.remove(_citySegment2OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#citySegment3").is(":checked")){
			map.add(_citySegment3OverlayGroups); 
		}else{
			map.remove(_citySegment3OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#highspeedSegment1").is(":checked")){
			map.add(_highspeedSegment1OverlayGroups); 
		}else{
			map.remove(_highspeedSegment1OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#highspeedSegment2").is(":checked")){
			map.add(_highspeedSegment2OverlayGroups); 
		}else{
			map.remove(_highspeedSegment2OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#highspeedSegment3").is(":checked")){
			map.add(_highspeedSegment3OverlayGroups); 
		}else{
			map.remove(_highspeedSegment3OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#link1").is(":checked")){
			map.add(_linkNotReadyOverlayGroups); 
		}else{
			map.remove(_linkNotReadyOverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#link2").is(":checked")){
			map.add(_linkReadyOverlayGroups); 
		}else{
			map.remove(_linkReadyOverlayGroups);
		}
	}
	catch(err){
	}
	
	try{
		if($("#edgeSite1").is(":checked")){
			map.add(_edgeSite1OverlayGroups); 
		}else{
			map.remove(_edgeSite1OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#edgeSite2").is(":checked")){
			map.add(_edgeSite2OverlayGroups); 
		}else{
			map.remove(_edgeSite2OverlayGroups);
		}
	}
	catch(err){
	}
	try{
		if($("#edgeSite3").is(":checked")){
			map.add(_edgeSite3OverlayGroups); 
		}else{
			map.remove(_edgeSite3OverlayGroups);
		}
	}
	catch(err){
	}
	
	try{
		if($("#keySite1").is(":checked") || $("#edgeSite1").is(":checked")){
			map.add(_combineSite1OverlayGroups); 
		}else{
			map.remove(_combineSite1OverlayGroups);
		}
	}
	catch(err){
	}
	
	try{
		if($("#keySite2").is(":checked") || $("#edgeSite2").is(":checked")){
			map.add(_combineSite2OverlayGroups); 
		}else{
			map.remove(_combineSite2OverlayGroups);
		}
	}
	catch(err){
	}
	
	try{
		if($("#keySite3").is(":checked") || $("#edgeSite3").is(":checked")){
			map.add(_combineSite3OverlayGroups); 
		}else{
			map.remove(_combineSite3OverlayGroups);
		}
	}
	catch(err){
	}
	
	
}

/*function dragMarker(marker){
	marker.setDraggable(true);
}
function saveMarker(marker){
	marker.setDraggable(false);
}*/

function drawChart(box,title,xData,yData){
	var myChart = echarts.init(document.getElementById(box));
	option = {
		//backgroundColor: '#f3f5f7',
		title: {
			text: title,
			left:"left",
			top:'5px'
		},
		grid:{
			left:"10%"
		},
		tooltip: {
			trigger: 'item',
			formatter: "{b}: {c} ({d}%)"
		},
		color:['red','yellow','green'],
		legend: {
			orient: 'vertical',
			x: 'right',
			data:xData
		},
		series: [
			{
				name:'',
				type:'pie',
				center: ['40%', '60%'],
				radius: ['45%', '70%'],
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

	myChart.setOption(option);
	window.onresize = myChart.resize;
}

function showname(){
	if($("#showname .showflag").hasClass("showflagtrue")){
		$("#showname .showflag").removeClass("showflagtrue");
		$("#map").addClass("labelhide");
	}else{
		$("#showname .showflag").addClass("showflagtrue");
		$("#map").removeClass("labelhide");
	}
}
