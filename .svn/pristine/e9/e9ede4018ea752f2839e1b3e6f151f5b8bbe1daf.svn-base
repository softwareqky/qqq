<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    if (!BUDGET_FEE) {
        var BUDGET_FEE = (function($) {
            /**
             * SubGrid的选中和不选中的事件处理，不联动MainGrid，仅控制按钮。
             * 
             * @param index 同easyui的datagrid的onSelect事件
             * @param row
             */
            function handleSubGridSelect(index, row) {
                var $this = $(this);
                var rows = $this.datagrid('getSelections');
                debugger;
                if (rows.length == 1) {
                	var dataSource = rows[0].dataSource;
                	if (dataSource == 0) {
                		var idPre = 'P10025';
                		$('#' + idPre + 'F02').linkbutton('disable');
                		$('#' + idPre + 'F03').linkbutton('disable');
                		idPre = 'P10011';
                		$('#' + idPre + 'F01').linkbutton('disable');
                		$('#' + idPre + 'F02').linkbutton('disable');
                		$('#' + idPre + 'F03').linkbutton('disable');
                	} else if (dataSource == 1) {
                		var idPre = 'P10025';
                		$('#' + idPre + 'F02').linkbutton('enable');
                		$('#' + idPre + 'F03').linkbutton('enable');
                		idPre = 'P10011';
                		$('#' + idPre + 'F01').linkbutton('enable');
                		$('#' + idPre + 'F02').linkbutton('enable');
                		$('#' + idPre + 'F03').linkbutton('enable');
                	}
                }

                // 查看，检索，清除检索，提交审核
                //var singleFunc = rows.length == 1 ? 'enable' : 'disable';
                //$('#P10011F04').linkbutton(singleFunc);
                
                _refreshLinkedGrid($this, rows);
            }
            
            function _refreshLinkedGrid($target, rows) {
                if (rows.length != 1) {
                    return;
                }

                var linkedGridId = '#' + $target.attr('data-linked-grid-id');
                var linkedFilterField = $target.attr('data-linked-filter-field-name');

                var selectedRow = rows[0];

                // 判断是否重复点击
                var oldCon = $(linkedGridId).data('EXTRA_FILTER_OBJ');
                if (!oldCon && selectedRow == null) {
                    return;
                }
                if (oldCon && selectedRow && oldCon.value == selectedRow.id) {
                    return;
                }

                var con = null;
                if (selectedRow != null) { // 选择全部
                    con = {};
                    con.fieldName = linkedFilterField;
                    con.valueType = '4';
                    con.filterSearchBy = '1';
                    con.valueMatchMode = '0';
                    con.captionName = '';
                    con.value = selectedRow.id;
                }

                $(linkedGridId).data('EXTRA_FILTER_OBJ', con);
                FilterUtils.doSearch(linkedGridId);
            }
            
            /**
             * 新建画面初始化的回调，用来获取SubGrid的选中记录，并设置计划名称和计划版本。也用于连续新建保存后的回调。
             */
            function onInitAddFormWidget(control) {

                var controlName = ControlUtils.getControlName(control);
                if (controlName == 'mainfee_') { // 设置 申购编号

                    var row = $('#P10011-BUDGET_MAINFEE-SubDatagrid').datagrid('getSelected');
                    if (!row) {
                        return;
                    }

                    $.ajax({
                        url: BASE_URL + "/budget/budgetfee/sub-grid-list.json",
                        type: "get",
                        dataType: "json",
                        success: function (res) {
                        	var dataArr = res.dataMap.rows;
                       		control.empty();
                            control.append('<option value="">&nbsp;</option>');
                            for (var i = 0; i < dataArr.length; i++) {
                                control.append('<option value="' + dataArr[i].id + '">' + dataArr[i].mainid
                                        + '</option>');
                            }

                            ControlUtils.setControlValue(control, row.id);
                        }
                	});
                    
                }
            }
        	
            return {
            	handleSubGridSelect: handleSubGridSelect,
            	onInitAddFormWidget: onInitAddFormWidget
            }; // BUDGET_FEE
        })(jQuery);
    }
    //]]>    
</script>