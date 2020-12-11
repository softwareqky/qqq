<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="">
<head>
<meta charset="utf-8">
<title>组织架构图</title>
<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale=1.0">
<!--[if IE]><link rel="stylesheet" href="css/ie.css" media="all" /><![endif]-->
<script type="text/javascript" src="<s:url value="/js/main.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/echarts/echarts.js"/>"></script>
<link rel="stylesheet" href="<s:url value="/css/bootstrap/bootstrap.min.css"/>" />
</head>
<body>
	<div id="organization" style="width: 100%; height: 800px;"></div>
	<script>
		var BASE_URL = '${pageContext.request.contextPath}';
		$.ajax({
			url : BASE_URL + '/org/dept/tree-side.json',
			type : 'POST',
			data : "",
			dataType : 'json',
			success : function(response, statusText, xhr, jqForm) {
				if(response.status == 1){
					var myChart = echarts.init(document
							.getElementById("organization"));
					myChart.setOption(option = {
						tooltip : {
							trigger : 'item',
							triggerOn : 'mousemove'
						},
						series : [ {
							type : 'tree',
							data : response.dataMap.rows,
							left : '2%',
							right : '2%',
							top : '10%',
							bottom : '10%',
							lineStyle : {
								curveness : 0.8,
							},
							symbol : 'rect',
							initialTreeDepth : -1,
							symbolSize : function(value, params) {
								if (params.data.attributes == null) {
									return [ params.name.length * 15, 30 ]
								} else {
									if (params.data.attributes.isDept == true) {
										return [ 30, 170 ]
									} else {
										return [ params.name.length * 15, 30 ]
									}
								}
							},
							itemStyle : {
								color : "#3c8dbc",
								borderColor : "#bababa",
								borderWidth : 1
							},
							orient : 'TB',
							expandAndCollapse : true,
							label : {
								position : 'inside',
								rotate : 0,
								verticalAlign : 'middle',
								align : 'center',
								color : "#fff",
								formatter : function(params) {
									if (params.data.attributes == null) {
										return params.name
									} else {
										if (params.data.attributes.isDept == true) {
											var str = "";
											for (i in params.name) {
												str += params.name[i] + "\n"
											}
											return str;
										} else {
											return params.name
										}
									}

								}
							}
						} ]
					});
				}else{
					alert(response.message)
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				//MainUtils.handleDatagridLoadError
			}
		})
	</script>
</body>
</html>
