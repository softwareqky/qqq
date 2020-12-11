<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    //<![CDATA[

    var ProjectInitiation = (function($) {

        /**
         * 格式化查看记录的式样。
         * 
         * @param value 字符值
         * @param row easyui的datagrid中的某一行数据
         * @param index easyui的datagrid中的行index
         */
        function viewRecordFormatter(value, row, index) {

            if (!value) {
                return value;
            }

            return '<a href="javascript:void(0)" onclick="Main.openTab(' + "'查看项目信息-"
                    + row.projectNo + "', '"
                    + "<s:url value="/project-management/initiation/view.htm"/>?id=" + row.id + "'"
                    + ')"><ins>' + value + '</ins></a>';
        }

        return {
            viewRecordFormatter: viewRecordFormatter
        }; // ProjectInitiation
    })(jQuery);

    //]]>

    
</script>