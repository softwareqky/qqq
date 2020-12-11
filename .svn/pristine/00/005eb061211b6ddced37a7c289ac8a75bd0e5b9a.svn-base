<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">
    //<![CDATA[

    if (!FLOWABLE_SETTING) {
        var FLOWABLE_SETTING = (function($) {

            /**
             * 新建和修改时的回调，用来设定关联的项目。修改时，后台不应处理前台传入的project_
             */
            function beforeSubmit(options, isAdd, isContinuousAdd, $dialog) {

                var con = $('#P9501-FLOWABLE_SETTING-MainDatagrid').data('EXTRA_FILTER_OBJ');

                if (con) {
                    options.data.page_ = con.value;
                }
            }

            function openFlowPermissionDialog(datagridId) {
                //TODO
                //console.log('openFlowPermissionDialog');
            }

            function openFlowRolePermissionDialog(datagridId) {
            	
            	if ($(datagridId).datagrid('getSelected') == null) {
            		AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
            		return;
            	}

                var $dialog = $('#_FlowableSettingProjectRoleDialog');

                function process(isFirstLoad) {

                    var selected = $(datagridId).datagrid('getSelected');
                    $('#_FlowableSettingRolePermissionTree')
                            .tree(
                                    {
                                        'url': BASE_URL
                                                + '/flowable/setting/access-process-tree.json?flowableSettingId='
                                                + selected.id
                                    });

                    $dialog.dialog('open').dialog('center').scrollTop(0);
                }

                var $dialogWrapper = $dialog.parent();
                if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
                    MainUtils.openLoading();
                    setTimeout(function() {
                        $.parser.parse($dialogWrapper);

                        // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                        $dialog.parent().prop('rendered', true);
                        process(true);
                        MainUtils.closeLoading();
                    }, 0);
                } else {
                    process(false);
                }
            }

            function saveRolePermissionData(datagridId) {

                var selected = $('#' + datagridId).datagrid('getSelected');

                var nodes = $('#_FlowableSettingRolePermissionTree').tree('getChecked');

                var roles = [];

                for ( var n in nodes) {
                    roles.push(nodes[n].id);
                }

                var options = {
                    url: BASE_URL + '/flowable/setting/save-access-process-data.json',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        'flowableSettingId': selected.id,
                        'roles': roles.join(',')
                    },
                    success: function(response, statusText, xhr, jqForm) {
                        MainUtils.closeLoading();
                        var jsonObj = response;
                        if (MainUtils.processJsonResult(jsonObj, true)) {


                            EasyDialogUtils.closeFormDialog('#_FlowableSettingProjectRoleDialog');
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);
                MainUtils.openLoading();
            }

            return {
                beforeSubmit: beforeSubmit,
                openFlowPermissionDialog: openFlowPermissionDialog,
                openFlowRolePermissionDialog: openFlowRolePermissionDialog,
                saveRolePermissionData: saveRolePermissionData
            }; // FLOWABLE_SETTING
        })(jQuery);
    }
    //]]>

    
</script>