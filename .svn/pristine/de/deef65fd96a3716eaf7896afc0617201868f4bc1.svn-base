<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
	var selectId = '${idMap["main-chart"]}Div';
    $('#' + selectId).hide();
    if (!VIRTUAL_ORG_CHART) {
        var VIRTUAL_ORG_CHART = (function($) {
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

                _drawChart(node.id);
            }

            /**
             * 绘制虚拟组织图。
             */
            function _drawChart(id) {

                var chartId = '${idMap["main-chart"]}';
                var $chart = $('#' + chartId);

                var options = {
                    url: $chart.attr('data-chart-url'),
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        projectId: id
                    },
                    success: function(response, statusText, xhr, jqForm) {
                        if (MainUtils.processJsonResult(response, false)) {
                            var myChart = echarts.init(document.getElementById(chartId));
                            myChart.setOption(option = {
                                tooltip: {
                                    trigger: 'item',
                                    triggerOn: 'mousemove'
                                },
                                series: [{
                                    type: 'tree',
                                    data: response.dataMap.rows,
                                    left: '2%',
                                    right: '2%',
                                    top: '10%',
                                    bottom: '10%',
                                    lineStyle: {
                                        curveness: 0.8,
                                    },
                                    symbol: 'rect',
                                    initialTreeDepth: -1,
                                    symbolSize: function(value, params) {
                                        if (params.data.attributes == null) {
                                            return [params.name.length * 15, 30]
                                        } else {
                                            if (params.data.attributes.isDept == true) {
                                                return [30, 170]
                                            } else {
                                                return [params.name.length * 15, 30]
                                            }
                                        }
                                    },
                                    itemStyle: {
                                        color: "#3c8dbc",
                                        borderColor: "#bababa",
                                        borderWidth: 1
                                    },
                                    orient: 'TB',
                                    expandAndCollapse: true,
                                    label: {
                                        position: 'inside',
                                        rotate: 0,
                                        verticalAlign: 'middle',
                                        align: 'center',
                                        color: "#fff",
                                        formatter: function(params) {
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
                                }]
                            });
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
            }; // VIRTUAL_ORG_CHART
        })(jQuery);
    }
    //]]>

    
</script>