<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
$("[name='planType'] option:contains(年度建设工作计划)").prop("selected", true);
    if (!PLAN_CHANGE) {
    	var g_planChangeOptions = [];
    	
        var PLAN_CHANGE = (function($) {
        	
        	function compare(gridId) {
        		const selectedRow = $(gridId).datagrid('getSelected');
				PlanTaskUtils.openCompareTask(selectedRow);
        	}

            /**
             * 变更原因的式样，增加tooltip。同MainUtils.remarkFormatter。
             * 
             * @param value 字符值
             * @param row easyui的datagrid中的某一行数据
             * @param index easyui的datagrid中的行index
             */
            function changeReasonFormatter(value, row, index) {
                return '<span title="' + row.changeReasonHtmlEscaped + '">' + value + '</span>';
            }

            /**
             * 新建画面初始化的回调，用来获取SubGrid的选中记录，并设置计划名称和计划版本。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {

                var controlName = ControlUtils.getControlName(control);
                console.log(controlName)
                if (controlName == 'planName') { // 设置计划名称

                    var row = $('#P5005-PLAN-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }

                    ControlUtils.setControlValue(control, row.planName);

                } else if (controlName == 'planVersion') { // 设置 计划版本

                    var row = $('#P5005-PLAN-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }

                    ControlUtils.setControlValue(control, row.planVersion);
                } else if (controlName == 'virtualOrg_') {
                	var row = $('#P5005-PLAN-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }
                    if (g_planChangeOptions.length == 0) {
                    	var options = control.context.options;
                    	for (var i=0; i<options.length; i++) {
                    		g_planChangeOptions.push({value: options[i].value, text: options[i].text});
                        }
                    }
                    var options = g_planChangeOptions;
                    control.empty();
                    for (var i=0; i<options.length; i++) {
                    	var text = options[i].text;
                    	if (text.indexOf("|") > 0) {
                    		var textArr = text.split("|");
                    		if (textArr[0] == row.project_) {
                    			if (options[i].value == row.virtualOrg_) {
                    				control.append('<option value="' + options[i].value + '" selected>'
                                        	+ textArr[1] + '</option>');
                    			} else {
                    				control.append('<option value="' + options[i].value + '">'
                                        	+ textArr[1] + '</option>');
                    			}
                    		}
                    	} else {
                    		control.append('<option value="' + options[i].value + '">'
                                    + text + '</option>');
                    	}
                    }
                }
            }

            /**
             * 修改画面初始化的回调，用来刷新项目组下拉框的内容。
             *
             */
            function beforeInitEditFormWidget(control, isFirstLoad, response) {
            	
                var name = ControlUtils.getControlName(control);
                if (name == 'virtualOrg_') {
                    var projectSetOptions = response.dataMap.rows;
                    control.empty();
                    control.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < projectSetOptions.length; i++) {
                        control.append('<option value="' + projectSetOptions[i].id + '">'
                                + projectSetOptions[i].text + '</option>');
                    }
                }
            }
            
            /**
             * 新建/修改时的回调，用来设定计划变更关联的计划。
             */
            function beforeSubmit(options) {
                var con = $('#P5005-PLAN_CHANGE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                // 只有在con中检索'计划'时，才修改options.data(con也可以检索'项目')
                if (con && con.fieldName == 'plan_') {
                    options.data.plan_ = con.value;
                }
            }

            return {
            	beforeInitEditFormWidget: beforeInitEditFormWidget,
                changeReasonFormatter: changeReasonFormatter,
                onInitAddFormWidget: onInitAddFormWidget,
                beforeSubmit: beforeSubmit,
                compare: compare
            }; // PLAN_CHANGE
        })(jQuery);
    }

    //]]>

    
</script>