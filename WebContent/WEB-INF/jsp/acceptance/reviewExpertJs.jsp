<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    if (!REVIEW_EXPERT) {
        var REVIEW_EXPERT = (function($) {

            /**
             * 在选择人员后的回调，根据所选人员弹出MainGrid的新建成员变更画面。
             * 
             * @param rows 选择的人员记录
             */
             function onChooseRows(rows) {

                 var $parentGrid = $('#P7005-REVIEW-MainDatagrid');
                 var $grid = $('#P7006-REVIEW_EXPERT-MainDatagrid');
                 var con = $parentGrid.data('EXTRA_FILTER_OBJ');

                 var param = {};
                 param.review_ = con.value;
                 param.expertList = [];

                 $.each(rows, function(i, n) {
                     param.expertList.push(n.id);
                 });

                 var options = {
                     url: "${urlMap['add']}",
                     type: 'POST',
                     dataType: 'json',
                     data: param,
                     traditional: true,
                     success: function(response, statusText, xhr, jqForm) {
                         MainUtils.closeLoading(); // 关闭loading提示
                         if (MainUtils.processJsonResult(response, true)) {

                             // 刷新grid
                             $grid.datagrid('reload');
                         }
                     },
                     error: MainUtils.handleAjaxFormError
                 };

                 $.ajax(options);
                 MainUtils.openLoading();// 弹出loading提示
             }

            return {
                onChooseRows: onChooseRows
            };
        })(jQuery);
    }
    //]]>

    
</script>