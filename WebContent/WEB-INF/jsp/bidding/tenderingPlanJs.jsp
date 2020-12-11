<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!TENDERINGPLAN) {
        var TENDERINGPLAN = (function($) {
        	

            /**
             * 弹出用于修改记录的带有Form的Dialog，弹出前通过Ajax向服务端发送待查找的数据的id，并将返回自服务端的JSON结果加载到指定的Form中。
             * 根据数据记录的'项目状态'，弹出项目建议文件上传画面、项目可研文件上传画面或者项目初设文件画面其中之一。
             * 
             * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新
             * @param url
             */
            function openEditFormDialog(gridId, url) {
                var dialogId = "#${idMap['edit-form-dialog']}";

                // 无需回调，沿用所有CrudUtils默认逻辑
                CrudUtils.openEditFormDialog(dialogId, url, "#${idMap['main-datagrid']}");
            }

            return {
                
                openEditFormDialog: openEditFormDialog
            }; // BUDGETTEMPLATE
        })(jQuery);
    }

    //]]>

    
</script>