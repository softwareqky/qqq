<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[  
    
    if (!CAPITALPLANSUM) {
        var CAPITALPLANSUM = (function($) {
            
            function exportExcelFile(datagridId) {
            	var con = $('#P10016-BUDGET_CAPITALPLANSUM-MainDatagrid').data('EXTRA_FILTER_OBJ');
                if (!con) {
                	AlertUtils.msg('warning', "请选择项目！");
                    return;
                }
                
                var url = BASE_URL
                        + '/budget/capitalPlanSum/export-excel-file.json?projectId='
                        + con.value;

                MainUtils.downloadFile(url);
                /*    }
                } */
            }
            
            return {
            	exportExcelFile: exportExcelFile
            }; 
        })(jQuery);
    }
    //]]>

    
</script>