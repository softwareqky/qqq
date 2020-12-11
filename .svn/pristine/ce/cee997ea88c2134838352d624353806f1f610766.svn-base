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
<title>自定义地图图标</title>
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
#sysicon{
	max-height:600px;
	overflow:auto;
}
#sysicon .iconbox{
	width:60px;
	height:60px;
	vertical-align:middle;
	text-align:center;
	line-height:60px;
	cursor:pointer;
	border:1px solid #fff;
	margin:5px auto;
}
#sysicon .iconbox img{
	vertical-align:middle;
}
#sysicon .iconbox.currenticon{
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
#colorpicker{
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
				<strong>核心节点</strong>
				<span>
					图标尺寸
					<input id="coreNodeSize" class="easyui-switchbutton" data-options="onText:'大', offText:'小'">
				</span>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s">
							<span>已具备</span><img id="coreNode1" src="img/home/markers/marker1-14.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('coreNode1','核心节点已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>进行中</span><img id="coreNode2" src="img/home/markers/marker1-13.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('coreNode2','核心节点进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>计划中</span><img id="coreNode3" src="img/home/markers/marker1-20.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('coreNode3','核心节点计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
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
					<input id="edgeNodeSize" class="easyui-switchbutton" data-options="onText:'大', offText:'小'">
				</span>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s">
							<span>已具备</span><img id="edgeNode1" src="img/home/markers/marker1-14.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('edgeNode1','边缘节点已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>进行中</span><img id="edgeNode2" src="img/home/markers/marker1-13.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('edgeNode2','边缘节点进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>计划中</span><img id="edgeNode3" src="img/home/markers/marker1-20.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('edgeNode3','边缘节点计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading flex-header">
				<strong>传输骨干节点</strong>
				<span>
					图标尺寸
					<input id="highSpeedCoreNodeSize" class="easyui-switchbutton" data-options="onText:'大', offText:'小'">
				</span>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s">
							<span>已具备</span><img id="highSpeedCoreNode1" src="img/home/markers/marker1-14.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('highSpeedCoreNode1','传输骨干节点已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>进行中</span><img id="highSpeedCoreNode2" src="img/home/markers/marker1-13.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('highSpeedCoreNode2','传输骨干节点进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>计划中</span><img id="highSpeedCoreNode3" src="img/home/markers/marker1-20.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('highSpeedCoreNode3','传输骨干节点计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading flex-header">
				<strong>传输中继节点</strong>
				<span>
					图标尺寸
					<input id="highSpeedRelayNodeSize" class="easyui-switchbutton" data-options="onText:'大', offText:'小'">
				</span>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s">
							<span>已具备</span><img id="highSpeedRelayNode1" src="img/home/markers/marker1-14.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('highSpeedRelayNode1','传输中继节点已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>进行中</span><img id="highSpeedRelayNode2" src="img/home/markers/marker1-13.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('highSpeedRelayNode2','传输中继节点进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s">
							<span>计划中</span><img id="highSpeedRelayNode3" src="img/home/markers/marker1-20.png" />
							<button type="button" data-toggle="modal" onclick="openSiteModal('highSpeedRelayNode3','传输中继节点计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading">
				<strong>骨干光缆</strong>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>已具备</span><span class="cableLine" style="border-bottom:1px solid green" id="highSpeedCable1"></span>
							<button type="button" data-toggle="modal" onclick="openCableModal('highSpeedCable1','骨干光缆已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>进行中</span><span class="cableLine" style="border-bottom:1px solid yellow" id="highSpeedCable2"></span>
							<button type="button" data-toggle="modal" onclick="openCableModal('highSpeedCable2','骨干光缆进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>计划中</span><span class="cableLine" style="border-bottom:1px solid red" id="highSpeedCable3"></span>
							<button type="button" data-toggle="modal" onclick="openCableModal('highSpeedCable3','骨干光缆计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading">
				<strong>城域光缆</strong>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>已具备</span><span class="cableLine" style="border-bottom:1px solid green" id="metroCable1"></span>
							<button type="button" data-toggle="modal" onclick="openCableModal('metroCable1','城域光缆')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>进行中</span><span class="cableLine" style="border-bottom:1px solid yellow" id="metroCable2"></span>
							<button type="button" data-toggle="modal" onclick="openCableModal('metroCable2','城域光缆进行中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>计划中</span><span class="cableLine" style="border-bottom:1px solid red" id="metroCable3"></span>
							<button type="button" data-toggle="modal" onclick="openCableModal('metroCable3','城域光缆计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
		
		<div class="panel panel-default panel-default-origin">
			<div class="panel-heading">
				<strong>链路</strong>
			</div>
			<div class="panel-body">
				<div class="container-fluid">
					<div class="row">
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>已具备</span><span class="cableLine" style="border-bottom:1px solid green" id="coreLink1"></span>
							<button type="button" data-toggle="modal" onclick="openCableModal('coreLink1','链路已具备')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
						<div class="col-xs-4 col-gapped-s cableBox">
							<span>计划中</span><span class="cableLine" style="border-bottom:1px solid red" id="coreLink2"></span>
							<button type="button" data-toggle="modal" onclick="openCableModal('coreLink2','链路计划中')" class="btn btn-primary navbar-btn changeButton">更换</button>
						</div>
			        </div>
	        	</div>
	        </div>
		</div>
    </div>
    
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="siteModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="siteModalLabel">选择图标</h4>
	            </div>
	            <div class="modal-body">
	            	<input type="hidden" id="siteTypeText" />
	           		<div class="row" id="sysicon">
	           		</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" onclick="saveIconInfo();">保存</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	 <div class="modal fade" id="cableModal" tabindex="-1" role="dialog" aria-labelledby="cableModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="cableModalLabel">选择样式</h4>
	            </div>
	            <div class="modal-body">
	            	<input type="hidden" id="cableTypeText" />
	            	<div class="row linecell">
	            		<div class="col-xs-2"></div>
	            		<div class="col-xs-2">宽度</div>
	            		<div class="col-xs-2"><input type="text" id="linewidth" placeholder="单位px" class="linewidth" /></div>
	            	</div>
	            	<div class="row linecell">
	            		<div class="col-xs-2"></div>
	            		<div class="col-xs-2">样式</div>
	            		<div class="col-xs-2">
		            		<select class="lineselect" id="lineselect">
		            			<option value="solid">实线</option>
		            			<option value="dashed">虚线</option>
		            		</select>
	            		</div>
	            	</div>
	            	<div class="row linecell">
	            		<div class="col-xs-2"></div>
	            		<div class="col-xs-2">颜色</div>
	            		<div class="col-xs-4"><div id="colorpicker"></div></div>
	            	</div>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" onclick="saveCableInfo();">保存</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
<script>
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
					if(data.siteIconList[i].iconName != null){
						
						const iconEleId = data.siteIconList[i].siteType;
						$("#"+iconEleId).attr("src","img/home/markers/"+data.siteIconList[i].iconName)
						
						const sizeSwitchId = iconEleId.substr(0, iconEleId.length - 1) + 'Size';
						console.log(sizeSwitchId);
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
	
	$("#sysicon").empty();
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
	str += '<div class="col-xs-2"><div class="iconbox"><img x="-21" y="-35" src="img/home/markers/marker1-13.png" /></div></div>'
	str += '<div class="col-xs-2"><div class="iconbox"><img x="-21" y="-35" src="img/home/markers/marker1-14.png" /></div></div>'
	str += '<div class="col-xs-2"><div class="iconbox"><img x="-21" y="-35" src="img/home/markers/marker1-20.png" /></div></div>'
	$("#sysicon").html(str);
	
	$("#colorpicker").colpick({
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
	
	const type = sizeSwitchId.replace('Size', '');
	
	// 开始更新
	MainUtils.openLoading();
	var options = {
        url: BASE_URL + '/system/map-custom/update-icon-size.json?type=' + type + '&size=' + (checked ? 1 : 0),
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

$("#sysicon .iconbox").click(function(){
	$("#sysicon .iconbox").removeClass("currenticon");
	$(this).addClass("currenticon");
})

function openSiteModal(type,title){
	$("#myModal #siteModalLabel").text(title);
	$("#myModal").modal("show");
	$("#siteTypeText").val(type);
	$("#sysicon .iconbox").removeClass("currenticon");
}
function saveIconInfo(){
	if($("#sysicon .currenticon").length != 0){
		var icon = $("#sysicon .currenticon img");
		var name = icon.attr("src").substring(icon.attr("src").lastIndexOf("/")+1,icon.attr("src").length);
		var param = {
				siteType: $("#siteTypeText").val(),
				name: name,
				x: icon.attr("x"),
				y: icon.attr("y")
			}
		
		$.ajax({
			url : BASE_URL + '/system/map-custom/update-defined-icon.json',
			type : 'POST',
			data : param,
			dataType : 'json',
			success : function(response, statusText, xhr, jqForm) {
				if(response.status == 1){
					$("#myModal").modal("hide");
					$("#"+$("#siteTypeText").val()).attr("src","img/home/markers/"+name);
				}else{
					//alert(response.message)
				}
			}
		})
	}else{
		alert("请选择一个图标")
	}
}

function openCableModal(type,title){
	$("#cableModal #cableModalLabel").text(title);
	$("#cableModal").modal("show");
	$("#cableTypeText").val(type);
	$("#linewidth").val($("#"+type).css("borderBottomWidth").substr(0,$("#"+type).css("borderBottomWidth").length-2));
	$("#colorpicker").css("backgroundColor",$("#"+type).css("borderBottomColor"));
	$("#lineselect").val($("#"+type).css("borderBottomStyle"));
}

function saveCableInfo(){
	if($("#linewidth").val() == ""){
		alert("请输入宽度")
		return
	}
	
	var regu = /^(0|[1-9][0-9]*)(\.\d+)?$/;
	if(!regu.test($("#linewidth").val())){
		alert("请输入正数")
		return
	}
	var param = {
			segmentType: $("#cableTypeText").val(),
			width: $("#linewidth").val(),
			lineStyle: $("#lineselect").val(),
			color: $("#colorpicker").css("background-color")
		}
	$.ajax({
		url : BASE_URL + '/system/map-custom/update-defined-icon.json',
		type : 'POST',
		data : param,
		dataType : 'json',
		success : function(response, statusText, xhr, jqForm) {
			if(response.status == 1){
				$("#cableModal").modal("hide");
				$("#"+$("#cableTypeText").val()).css("border-bottom", $("#linewidth").val()+"px "+$("#lineselect").val()+" "+$("#colorpicker").css("background-color"))
			}
		}
	})
}
</script>
</body>
</html>