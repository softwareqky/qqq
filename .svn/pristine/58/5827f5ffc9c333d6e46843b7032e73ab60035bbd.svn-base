<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!INCOME_INVOICE) {
    	var g_planChangeOptions = [];
    	
        var INCOME_INVOICE = (function($) {

            var isClickCell = false;
        	var isUnSelected = false;
            
            /**
             * 新建画面初始化的回调，用来获取SubGrid的选中记录，并设置计划名称和计划版本。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {
            	debugger
                var controlName = ControlUtils.getControlName(control);
                if (controlName == 'incomeContract_') {

                    var row = $('#P4025-INCOME_CONTRACT-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }

                    $.ajax({
                        url: BASE_URL + "/income-invoice/sub-grid-list.json",
                        type: "get",
                        dataType: "json",
                        success: function (res) {
                        	var dataArr = res.dataMap.rows;
                       		control.empty();
                            control.append('<option value="">&nbsp;</option>');
                            for (var i = 0; i < dataArr.length; i++) {
                                control.append('<option value="' + dataArr[i].id + '">' + dataArr[i].contractName
                                        + '</option>');
                            }

                            ControlUtils.setControlValue(control, row.id);
                        }
                	});
                    
                }
            }
            
            return {
                onInitAddFormWidget: onInitAddFormWidget
            }; // INCOME_INVOICE
        })(jQuery);
    }

    //]]>

    
</script>