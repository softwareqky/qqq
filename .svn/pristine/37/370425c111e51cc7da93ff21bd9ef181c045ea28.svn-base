<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义驾驶舱图标</title>
</head>
<body>
<script type="text/javascript" src="<s:url value="/js/jQuery-Color-Picker/js/colpick.js"/>"></script>
<link rel="stylesheet" href="<s:url value="/js/jQuery-Color-Picker/css/colpick.css"/>" />
<style>
.iconCell{
	text-align: center!important;
	vertical-align:middle!important;
}
.changeButton{
	margin:0 50px 0 0;
	float:right;
}
#dashboard_sysicon{
	max-height:600px;
	overflow:auto;
}
#dashboard_sysicon .iconbox{
	width:60px;
	height:60px;
	vertical-align:middle;
	text-align:center;
	line-height:60px;
	cursor:pointer;
	border:1px solid #fff;
	margin:5px auto;
}
#dashboard_sysicon .iconbox img{
	vertical-align:middle;
}
#dashboard_sysicon .iconbox.currenticon{
	background-color:#eeeeef;
	border:1px solid #b5b5b5;
}
.cableBox{
	height:42px;
	line-height:42px;
}
.cableLine{
	display:inline-block;
	width:60px;
	/* border-bottom:1px solid red; */
	vertical-align:middle;
	margin-left:10px;
}
.colpick{
	z-index:9999;
}
.linecell{
	line-height:32px;
	margin:10px 0;
}
.lineselect, .linewidth{
	width:200px;
	height:32px;
	line-height:32px;
}
#dashboard_colorpicker{
	width:30px;
	height:30px;
}

.flex-header {
	display: flex;
	flex-diration: column;
}

.flex-header strong {
	flex: 1
}

</style>
	<div class="container-fix-width-m" style="width:860px;margin:10px auto;">
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading flex-header">
				<strong>运行管控中心</strong>
				<span>
					图标尺寸
					<input id="mapMainCitySize" class="easyui-switchbutton" data-options="onText:'大', offText:'小'">
				</span>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s">
							<span>已具备</span><img id="mapMainCity1" src="img/home/markers/marker1-14.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapMainCity1','运行管控中心已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>进行中</span><img id="mapMainCity2" src="img/home/markers/marker1-13.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapMainCity2','运行管控中心进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>计划中</span><img id="mapMainCity3" src="img/home/markers/marker1-20.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapMainCity3','运行管控中心计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading flex-header">
				<strong>核心节点</strong>
				<span>
					图标尺寸
					<input id="mapCoreNodeSize" class="easyui-switchbutton" data-options="onText:'大', offText:'小'">
				</span>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s">
							<span>已具备</span><img id="mapCoreNode1" src="img/home/markers/marker1-14.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapCoreNode1','核心节点已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>进行中</span><img id="mapCoreNode2" src="img/home/markers/marker1-13.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapCoreNode2','核心节点进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>计划中</span><img id="mapCoreNode3" src="img/home/markers/marker1-20.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapCoreNode3','核心节点计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading flex-header">
				<strong>边缘节点</strong>
				<span>
					图标尺寸
					<input id="mapEdgeNodeSize" class="easyui-switchbutton" data-options="onText:'大', offText:'小'">
				</span>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s">
							<span>已具备</span><img id="mapEdgeNode1" src="img/home/markers/marker1-14.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapEdgeNode1','边缘节点已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>进行中</span><img id="mapEdgeNode2" src="img/home/markers/marker1-13.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapEdgeNode2','边缘节点进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>计划中</span><img id="mapEdgeNode3" src="img/home/markers/marker1-20.png" />
							<button type="button" data-toggle="modal" onclick="dashboard_openSiteModal('mapEdgeNode3','边缘节点计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading"><strong>骨干光缆</strong></div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>已具备</span><span class="cableLine" style="border-bottom:1px solid #fff" id="mapHighSpeedCable1"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapHighSpeedCable1','骨干光缆已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>进行中</span><span class="cableLine" style="border-bottom:1px solid yellow" id="mapHighSpeedCable2"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapHighSpeedCable2','骨干光缆进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>计划中</span><span class="cableLine" style="border-bottom:1px solid #8a8a8a" id="mapHighSpeedCable3"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapHighSpeedCable3','骨干光缆计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading"><strong>城域光缆</strong></div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>已具备</span><span class="cableLine" style="border-bottom:1px solid #fff" id="mapMetroCable1"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapMetroCable1','城域光缆')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>进行中</span><span class="cableLine" style="border-bottom:1px solid yellow" id="mapMetroCable2"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapMetroCable2','城域光缆进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>计划中</span><span class="cableLine" style="border-bottom:1px solid #8a8a8a" id="mapMetroCable3"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapMetroCable3','城域光缆计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading"><strong>链路</strong></div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>已具备</span><span class="cableLine" style="border-bottom:1px solid #fff" id="mapCoreLink1"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapCoreLink1','链路已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>计划中</span><span class="cableLine" style="border-bottom:1px solid #8a8a8a" id="mapCoreLink2"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapCoreLink2','链路计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading"><strong>筛选中继段</strong></div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>进行中</span><span class="cableLine" style="border-bottom:1px solid yellow" id="mapSystemSeg"></span>
							<button type="button" data-toggle="modal" onclick="dashboard_openCableModal('mapSystemSeg','筛选中继段')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
    </div>
    
	<div class="modal fade" id="dashboard_myModal" tabindex="-1" role="dialog" aria-labelledby="dashboard_siteModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="dashboard_siteModalLabel">选择图标</h4>
	            </div>
	            <div class="modal-body">
	            	<input type="hidden" id="dashboard_siteTypeText" />
	           		<div class="row" id="dashboard_sysicon">
	           		</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" onclick="dashboard_saveIconInfo();">保存</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	 <div class="modal fade" id="dashboard_cableModal" tabindex="-1" role="dialog" aria-labelledby="dashboard_cableModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="dashboard_cableModalLabel">选择样式</h4>
	            </div>
	            <div class="modal-body">
	            	<input type="hidden" id="dashboard_cableTypeText" />
	            	<div class="row linecell">
	            		<div class="col-xs-2"></div>
	            		<div class="col-xs-2">宽度</div>
	            		<div class="col-xs-2"><input type="text" id="dashboard_linewidth" placeholder="单位px" class="linewidth" /></div>
	            	</div>
	            	<div class="row linecell">
	            		<div class="col-xs-2"></div>
	            		<div class="col-xs-2">样式</div>
	            		<div class="col-xs-2">
		            		<select class="lineselect" id="dashboard_lineselect">
		            			<option value="solid">实线</option>
		            			<option value="dashed">虚线</option>
		            		</select>
	            		</div>
	            	</div>
	            	<div class="row linecell">
	            		<div class="col-xs-2"></div>
	            		<div class="col-xs-2">颜色</div>
	            		<div class="col-xs-4"><div id="dashboard_colorpicker"></div></div>
	            	</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" onclick="dashboard_saveCableInfo();">保存</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
<script>
$(function(){
	$.ajax({
		url : BASE_URL + '/system/dashboard-custom/get-dashboard-icon.json',
		type : 'POST',
		data : {},
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			if(response.status == 1){
				var data = response.dataMap.returnObj;
				for(i = 0;i < data.siteIconList.length;i++){
					if(data.siteIconList[i].iconName != null){
						
						const iconEleId = data.siteIconList[i].siteType;
						$("#"+iconEleId).attr("src","img/home/markers/"+data.siteIconList[i].iconName)
						
						const sizeSwitchId = iconEleId.substr(0, iconEleId.length - 1) + 'Size';
						console.log(sizeSwitchId, data.siteIconList[i]);
						$('#' + sizeSwitchId).switchbutton({
							checked: data.siteIconList[i].size == 1,
							onChange: function(checked) {
								onIconSizeChange(sizeSwitchId, checked);
							}
						})
					}
				}
				for(i = 0;i < data.segmentIconList.length;i++){
					if(data.segmentIconList[i].width != null){
						$("#"+data.segmentIconList[i].segmentType).css("borderBottom",data.segmentIconList[i].width+"px "+data.segmentIconList[i].lineStyle+" "+data.segmentIconList[i].color)
					}
				}
			}
		}
	})
	
	$("#dashboard_sysicon").empty();
	var str = "";
	for(var i = 1;i < 67;i++){
		str += '<div class="col-xs-2"><div class="iconbox"><img x="-18" y="-18" src="img/home/markers/marker2-'+i+'.png" /></div></div>'
	}
	for(var i = 1;i < 7;i++){
		str += '<div class="col-xs-2"><div class="iconbox"><img x="-19" y="-31" src="img/home/markers/marker3-'+i+'.png" /></div></div>'
	}
	for(var i = 1;i < 7;i++){
		str += '<div class="col-xs-2"><div class="iconbox"><img x="-12" y="-34" src="img/home/markers/marker4-'+i+'.png" /></div></div>'
	}
	for(var i = 1;i < 7;i++){
		str += '<div class="col-xs-2"><div class="iconbox"><img x="-19" y="-28" src="img/home/markers/marker5-'+i+'.png" /></div></div>'
	}
	/* str += '<div class="col-xs-2"><div class="iconbox"><img x="-21" y="-35" src="img/home/markers/marker1-13.png" /></div></div>'
	str += '<div class="col-xs-2"><div class="iconbox"><img x="-21" y="-35" src="img/home/markers/marker1-14.png" /></div></div>'
	str += '<div class="col-xs-2"><div class="iconbox"><img x="-21" y="-35" src="img/home/markers/marker1-20.png" /></div></div>' */
	$("#dashboard_sysicon").html(str);
	
	$("#dashboard_colorpicker").colpick({
		colorScheme:'dark',
		layout:'rgbhex',
		color:'ffffff',
		onSubmit:function(hsb,hex,rgb,el) {
			$(el).css('background-color', '#'+hex);
			$(el).colpickHide();
		}
	}).css('background-color', '#ff8800');
})

function onIconSizeChange(sizeSwitchId, checked) {
	
	console.log(sizeSwitchId, checked);
	const type = sizeSwitchId.replace('Size', '');
	
	// 开始更新
	MainUtils.openLoading();
	var options = {
        url: BASE_URL + '/system/dashboard-custom/update-icon-size.json?type=' + type + '&size=' + (checked ? 1 : 0),
        data: {},
        datatype: 'json',
        type: 'POS',
        contentType: 'application/json',
        success: function(response, statusText, xhr, jqForm) {
        	
        	// 取得应答，关闭loading提示
        	MainUtils.closeLoading();
        },
        error: MainUtils.handleAjaxFormError
    };
    $.ajax(options); // 检查计划是否已审核
}

$("#dashboard_sysicon .iconbox").click(function(){
	$("#dashboard_sysicon .iconbox").removeClass("currenticon");
	$(this).addClass("currenticon");
})

function dashboard_openSiteModal(type,title){
	$("#dashboard_myModal #dashboard_siteModalLabel").text(title);
	$("#dashboard_myModal").modal("show");
	$("#dashboard_siteTypeText").val(type);
	$("#dashboard_sysicon .iconbox").removeClass("currenticon");
}
function dashboard_saveIconInfo(){
	if($("#dashboard_sysicon .currenticon").length != 0){
		var icon = $("#dashboard_sysicon .currenticon img");
		var name = icon.attr("src").substring(icon.attr("src").lastIndexOf("/")+1,icon.attr("src").length);
		var param = {
				siteType: $("#dashboard_siteTypeText").val(),
				name: name,
				x: icon.attr("x"),
				y: icon.attr("y")
			}
		
		$.ajax({
			url : BASE_URL + '/system/dashboard-custom/update-dashboard-icon.json',
			type : 'POST',
			data : param,
			dataType : 'json',
			success : function(response, statusText, xhr, jqForm) {
				if(response.status == 1){
					$("#dashboard_myModal").modal("hide");
					$("#"+$("#dashboard_siteTypeText").val()).attr("src","img/home/markers/"+name);
				}else{
					//alert(response.message)
				}
			}
		})
	}else{
		alert("请选择一个图标")
	}
}

function dashboard_openCableModal(type,title){
	$("#dashboard_cableModal #dashboard_cableModalLabel").text(title);
	$("#dashboard_cableModal").modal("show");
	$("#dashboard_cableTypeText").val(type);
	$("#dashboard_linewidth").val($("#"+type).css("borderBottomWidth").substr(0,$("#"+type).css("borderBottomWidth").length-2));
	$("#dashboard_colorpicker").css("backgroundColor",$("#"+type).css("borderBottomColor"));
	$("#dashboard_lineselect").val($("#"+type).css("borderBottomStyle"));
}

function dashboard_saveCableInfo(){
	if($("#dashboard_linewidth").val() == ""){
		alert("请输入宽度")
		return
	}
	
	var regu = /^(0|[1-9][0-9]*)(\.\d+)?$/;
	if(!regu.test($("#dashboard_linewidth").val())){
		alert("请输入正数")
		return
	}
	var param = {
			segmentType: $("#dashboard_cableTypeText").val(),
			width: $("#dashboard_linewidth").val(),
			lineStyle: $("#dashboard_lineselect").val(),
			color: $("#dashboard_colorpicker").css("background-color")
		}
	$.ajax({
		url : BASE_URL + '/system/dashboard-custom/update-dashboard-icon.json',
		type : 'POST',
		data : param,
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			if(response.status == 1){
				$("#dashboard_cableModal").modal("hide");
				$("#"+$("#dashboard_cableTypeText").val()).css("border-bottom", $("#dashboard_linewidth").val()+"px "+$("#dashboard_lineselect").val()+" "+$("#dashboard_colorpicker").css("background-color"))
			}
		}
	})
}
</script>
</body>
</html>