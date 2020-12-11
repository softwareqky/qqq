<%@tag description="基于EasyUI布局的左树+右Chart，支持自动适应窗体大小变化" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="chartUrl" required="true" description="Chart的数据请求ajax对应的URL"%>
<%@attribute name="chartId" required="true" description="Chart的id"%>

<div class="easyui-layout" data-options="fit:true">

  <%-- Left side tree --%>
  <div data-options="region:'west',border:false,width:'auto',width:250,maxWidth:250">
    <jsp:doBody />
  </div>
  <!-- west region -->

  <%-- Single DataGrid with filter --%>
  <div data-options="region:'center',border:false">
    <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false" style="padding: 5px 10px;">
      	<div id="${chartId}Div" style="height:24px;line-height:24px;">
      		<div id="${chartId}SelectArea" style="display: inline-block">
      			<span id="${chartId}SelectLabel">选择计划：</span>
				<select id="${chartId}Select" style="width:250px;height:24px;line-height:24px;"></select>
      		</div>
      		<div id="${chartId}SelectArea1" style="display: inline-block; margin-left: 12px;">
      			<span id="${chartId}SelectLabel1">选择计划1：</span>
				<select id="${chartId}Select1" style="width:250px;height:24px;line-height:24px;"></select>
      		</div>
		</div>
        <div id="${chartId}" data-chart-url="${chartUrl}" style="width: 100%; height: 96%"></div>
      </div>
    </div>
    <!-- /.easyui-layout in center region -->
  </div>
  <!-- center region -->

</div>
<!-- /.easyui-layout outmost -->
