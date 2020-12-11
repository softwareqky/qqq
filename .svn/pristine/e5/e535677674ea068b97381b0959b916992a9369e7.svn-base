<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[

    var PROJECT_SET = (function($) {

        /**
         * 新建画面初始化的回调，用来设置项目编号的内容。
         */
        function onInitAddFormWidget(control) {

            if (ControlUtils.getControlName(control) == 'projectNum') {
                var options = {
                    url: BASE_URL + '/project/project-set/project-num.json',
                    type: 'POST',
                    dataType: 'json',
                    success: function(response, statusText, xhr, jqForm) {
                        if (MainUtils.processJsonResult(response, false)) {
                            var pjNum = response.dataMap.returnObj;

                            ControlUtils.setControlValue(control, pjNum);

                        } else {
                            AlertUtils.msg(response.icon || 'error', response.message
                                    || MSG_REMOTE_SERVER_ERROR);
                        }
                        // MainUtils.closeLoading();// 关闭loading提示
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);
                // MainUtils.openLoading();// 弹出loading提示
            }
        }

        return {
            onInitAddFormWidget: onInitAddFormWidget
        }; // PROJECT_INIT
    })(jQuery);
    //]]>

    
</script>