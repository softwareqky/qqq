<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">

	var lastDisplay = 'block';

	$(document).ready(function() {
		
		// 处理说明
		// 在重新显示Tab页后，重新载入datagrid数据
		const panel = $('#P5001-PLAN-SideFilterForm').parent().parent().parent().parent().parent().parent().parent().parent();
		
		const MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
		const observer = new MutationObserver(function(mutations) {
			
			for (var i = 0; i < mutations.length; i++) {
				const mutation = mutations[i];
				if (mutation.type == 'attributes' && mutation.attributeName == 'style') {
					const style = panel.attr('style');
					if (style.indexOf('block') > 0 && lastDisplay == 'none') {
						$('#P5001-PLAN-MainDatagrid').datagrid('reload');
						lastDisplay = 'block';
					} else {
						lastDisplay = 'none';
					}
					return;
				}
			}
		});
		
		observer.observe(panel[0], { attributes: true });
	});
	
    //<![CDATA[
    /**
     * 计划相关的逻辑。
     */
    $("[name='planType'] option:contains(年度建设工作计划)").prop("selected", true);
    var PlanUtils = (function($) {

        // 打开合并计划
        function openMergeScheduleDialog(dialogId) {

            var rows = $('#P5001-PLAN-MainDatagrid').datagrid('getSelections');

            if (rows.length > 0) {

                var firstProject_ = rows[0].project_;

                var isSameProject = true;

                for ( var r in rows) {

                    var row = rows[r];

                    if (firstProject_ == row.project_) {
                        isSameProject = true;
                    } else {
                        isSameProject = false;
                        break;
                    }
                }

                if (!isSameProject) {
                    AlertUtils.msg('warning', '计划所属项目不同，不能进行合并！');
                    return;
                }

                CrudUtils.openAddFormDialog('#_P5001-PLAN-MergeScheduleFormDialog');
            }
        }

        /**
         * 新建和修改时的回调，用来设定项目变更关联的项目。修改时，后台不应处理前台传入的project_
         */
        function beforeSubmit(options) {

            var con = $('#P5001-PLAN-MainDatagrid').data('EXTRA_FILTER_OBJ');
            if (con) {
                options.data.project_ = con.value;
            }

			try{
			    
	            var isClosed = $('#_P5001-PLAN-MergeScheduleFormDialog').dialog('options').closed;

	            //弹出合并对话框，特殊处理project_字段
	            if (!isClosed) {
	                var rows = $('#P5001-PLAN-MainDatagrid').datagrid('getSelections');
	                if (rows.length > 0) {
	                    options.data.project_ = rows[0].project_;

	                    var mergeIds = [];

	                    for ( var r in rows) {
	                        mergeIds.push(rows[r].id);
	                    }

	                    options.data.mergeIds = mergeIds.join(',');
	                }
	            }
			}catch(e){
			    //do nothing
			}

            
        }

        return {
            openMergeScheduleDialog: openMergeScheduleDialog,
            beforeSubmit: beforeSubmit
        };
    })(jQuery);
    //]]>

    
</script>