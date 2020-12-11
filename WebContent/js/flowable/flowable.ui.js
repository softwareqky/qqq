var FLOWABLE_UI_UTILS = (function($) {

    function reloadFlowDetail(index, row) {

        var $this = $(this);
        var selected = $this.datagrid('getSelected');
        // 选中载入详细节点
        if (selected) {
            generateFlowableView(selected.id, '#_FlowDetailViewDiv');
        } else {
            $('#_FlowDetailViewDiv').empty();
        }
    }

    function generateFlowableView(settingId, divId) {

        if (!settingId || !divId) {
            return;
        }

        var options = {
            url: BASE_URL + '/flowable/setting/get-flowable-setting-nodes.json',
            type: 'POST',
            data: {
                'flowableSettingId': settingId
            },
            dataType: 'json',
            success: function(response, statusText, xhr, jqForm) {
                var jsonObj = response;
                if (MainUtils.processJsonResult(jsonObj, false)) {

                    generateFlowableNodesHtml(divId, jsonObj.dataMap.rows);
                }
            },
            error: MainUtils.handleAjaxFormError
        };

        $.ajax(options);
        $(divId).empty();
    }

    function saveFlowDetailData(datagridId) {
        var selected = $('#' + datagridId).datagrid('getSelected');

        if (!selected) {
            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
            return;
        }

        var flowableSettingBean = {};

        flowableSettingBean.id = selected.id;
        flowableSettingBean.page_ = selected.page_;
        flowableSettingBean.flowName = selected.flowName;

        var nodeTds = $('#_FlowDetailViewDiv').find('td');

        if (nodeTds.length == 0) {
            AlertUtils.msg('warning', '请提交完整流程！');
            return;
        }

        var dbDatas = [];

        nodeTds.each(function(i) {
            var $this = $(this);

            var dbData = $this.data('dbData');
            if (dbData) {
                dbDatas.push(dbData);
            }
        });

        if (dbDatas.length < 3) {
            AlertUtils.msg('warning', '请提交完整流程！');
            return;
        }

        // 判断最后节点是否结束节点
        var lastNodeData = dbDatas[dbDatas.length - 1];
        if (lastNodeData && lastNodeData.nodeType != 1) {
            AlertUtils.msg('warning', '请提交完整流程！');
            return;
        }

        flowableSettingBean.flowableSettingNodeBeans = dbDatas;

        var options = {
            url: BASE_URL + '/flowable/setting/save-flowable-setting-node.json',
            type: 'POST',
            data: JSON.stringify(flowableSettingBean),
            dataType: 'json',
            contentType: 'application/json',
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();
                var jsonObj = response;
                if (MainUtils.processJsonResult(jsonObj, true)) {

                    // TODO
                    // console.log(datagridId);
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);
        MainUtils.openLoading();
    }

    function openSingleAuditDialog(dialogId, isAdd, callback) {

        var nodeSelected = $('.flowableNodeSelected');

        if (isAdd) {
            if (nodeSelected.length == 0) {
                AlertUtils.msg('warning', '请选择前置节点！');
                return;
            }

            if (nodeSelected.hasClass('flowableNodeEndEvent')) {
                AlertUtils.msg('warning', '前置节点不能选择结束节点！');
                return;
            }
        }
        if (!dialogId) {
            return;
        }

        var $dialog = $(dialogId);

        var $form = $dialog.find('form');

        function process(isFirstLoad) {
            $form.form('reset');
            $form.form('disableValidation');

            if (isAdd) {
                $('#_SingleAuditNodeDialogButtons_add').show();
                $('#_SingleAuditNodeDialogButtons_edit').hide();
            } else {
                $('#_SingleAuditNodeDialogButtons_add').hide();
                $('#_SingleAuditNodeDialogButtons_edit').show();
            }

            if (callback && $.isFunction(callback.onBeforeOpenDialog)) {
                callback.onBeforeOpenDialog();
            }

            $dialog.dialog('open').dialog('center').scrollTop(0);
        }

        var $dialogWrapper = $dialog.parent();
        if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
            MainUtils.openLoading();
            setTimeout(function() {
                $.parser.parse($dialogWrapper);

                // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                $dialogWrapper.remove();
                $dialog.parent().prop('rendered', true);

                MainUtils.closeLoading();
                process(true);
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(false);
        }
    }

    function openRoleAuditDialog(dialogId, isAdd, callback) {

        var nodeSelected = $('.flowableNodeSelected');

        if (isAdd) {
            if (nodeSelected.length == 0) {
                AlertUtils.msg('warning', '请选择前置节点！');
                return;
            }

            if (nodeSelected.hasClass('flowableNodeEndEvent')) {
                AlertUtils.msg('warning', '前置节点不能选择结束节点！');
                return;
            }
        }
        if (!dialogId) {
            return;
        }

        var $dialog = $(dialogId);

        var $form = $dialog.find('form');

        function process(isFirstLoad) {
            $form.form('reset');
            $form.form('disableValidation');

            if (isAdd) {
                $('#_RoleAuditNodeDialogButtons_add').show();
                $('#_RoleAuditNodeDialogButtons_edit').hide();
            } else {
                $('#_RoleAuditNodeDialogButtons_add').hide();
                $('#_RoleAuditNodeDialogButtons_edit').show();
            }

            if (callback && $.isFunction(callback.onBeforeOpenDialog)) {
                callback.onBeforeOpenDialog();
            }

            $dialog.dialog('open').dialog('center').scrollTop(0);
        }

        var $dialogWrapper = $dialog.parent();
        if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
            MainUtils.openLoading();
            setTimeout(function() {
                $.parser.parse($dialogWrapper);

                // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                $dialogWrapper.remove();
                $dialog.parent().prop('rendered', true);

                MainUtils.closeLoading();
                process(true);
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(false);
        }
    }

    function generateFlowableNodesHtml(divId, dbNodes) {

        var $nodeDiv = $(divId);
        $nodeDiv.empty();

        $nodeDiv.addClass('flowableViewDiv');

        if (dbNodes && dbNodes.length > 0) {

            var $table = $('<table class="flowableNodeTable"></table>');
            var $tr = $('<tr></tr>');

            for ( var n in dbNodes) {
                var node = dbNodes[n];

                var nodeType = node.nodeType;
                var nodeName = node.nodeName;

                if (nodeType > 0) {
                    var flowHtml = generateFlowNodeHtml(2);
                    $tr.append($(flowHtml));
                }

                var nodeHtml = generateFlowNodeHtml(nodeType, nodeName);
                if (2 == nodeType) {
                    nodeHtml = generateFlowNodeHtml(3, nodeName);
                } else if (3 == nodeType) {
                    nodeHtml = generateFlowNodeHtml(4, nodeName);
                }

                var $nodeTd = $(nodeHtml);

                $nodeTd.bind('click', clickNodeEvent);
                $nodeTd.data('dbData', node);

                $tr.append($nodeTd);
            }

            $table.append($tr);
            $nodeDiv.append($table);
        }
    }

    function generateFlowNodeHtml(nodeType, nodeName) {

        var nodeHtml = '';

        if (0 == nodeType) {

            nodeHtml = '<td class="flowableNode flowableNodeStartEvent"><table><tr><td class="flowableNodeStartEventStartEventBackground"></td></tr><tr><td>'
                    + nodeName + '</td></tr></table></td>';
        } else if (1 == nodeType) {

            nodeHtml = '<td class="flowableNode flowableNodeEndEvent"><table><tr><td class="flowableNodeEndEventBackground"></td></tr><tr><td>'
                    + nodeName + '</td></tr></table></td>';
        } else if (2 == nodeType) {

            nodeHtml = '<td class="flowableNode flowableNodeLineArrowBackground"></td>';
        } else if (3 == nodeType) {

            nodeHtml = '<td class="flowableNode flowableNodeUserTaskSingle"><table><tr><td class="flowableNodeUserTaskSingleBackground"></td></tr><tr><td>'
                    + nodeName + '</td></tr></table></td>';
        } else if (4 == nodeType) {

            nodeHtml = '<td class="flowableNode flowableNodeUserTaskRole"><table><tr><td class="flowableNodeUserTaskRoleBackground"></td></tr><tr><td>'
                    + nodeName + '</td></tr></table></td>';
        }

        return nodeHtml;
    }

    // 节点选中事件
    function clickNodeEvent(e) {
        var $this = $(this);

        $('.flowableNodeSelected').removeClass('flowableNodeSelected');
        $this.addClass('flowableNodeSelected');
    }

    function saveSingleAuditPersonNode(dialogId, isAdd) {

        var $dialog = $(dialogId);
        var $form = $dialog.find('form');

        $form.form('enableValidation');
        if (!$form.form('validate')) {
            return;
        }
        var $nodeName = $dialog.find('[name="flowNodeName"]');
        var $personText = $dialog.find('[name="personText"]');
        var $person_ = $dialog.find('[name="person_"]');


        var nodeName = $nodeName.val();
        var personId = $person_.val();
        var personName = $personText.val();

        if (isAdd) {
            addAuditNodeHtml(nodeName, personId, personName, 3, 2);
        } else {
            editAuditNodeHtml(nodeName, personId, personName, 2);
        }

        EasyDialogUtils.closeFormDialog(dialogId);
    }

    function saveProjectRoleAuditNode(dialogId, isAdd) {

        var $dialog = $(dialogId);
        var $form = $dialog.find('form');

        $form.form('enableValidation');
        if (!$form.form('validate')) {
            return;
        }
        var $nodeName = $dialog.find('[name="flowNodeName"]');
        var $personText = $dialog.find('[name="projectRoleText"]');
        var $person_ = $dialog.find('[name="projectRole_"]');

        var nodeName = $nodeName.val();
        var personId = $person_.val();
        var personName = $personText.val();

        if (isAdd) {
            addAuditNodeHtml(nodeName, personId, personName, 4, 3);
        } else {
            editAuditNodeHtml(nodeName, personId, personName, 3);
        }

        EasyDialogUtils.closeFormDialog(dialogId);
    }


    // 添加审核节点
    function addAuditNodeHtml(nodeName, personId, personName, nodeHtmlType, dbNodeType) {

        var $nodeSelected = $('.flowableNodeSelected');

        var $nodeFlow = $(generateFlowNodeHtml(2));
        var $nodeUser = $(generateFlowNodeHtml(nodeHtmlType, nodeName));
        $nodeUser.bind('click', clickNodeEvent);

        $nodeSelected.after($nodeFlow);
        $nodeFlow.after($nodeUser);

        $nodeUser.data('dbData', {
            'nodeName': nodeName,
            'nodeType': dbNodeType,
            'participantAuditList': personId,
            'participantAuditNameList': personName,
        });

        $('.flowableNodeSelected').removeClass('flowableNodeSelected');
        $nodeUser.addClass('flowableNodeSelected');
    }

    function editAuditNodeHtml(nodeName, personId, personName, dbNodeType) {

        var $nodeSelected = $('.flowableNodeSelected');

        $nodeSelected.find('td:eq(1)').html(nodeName);

        $nodeSelected.data('dbData', {
            'id': MainUtils.guid(),
            'nodeName': nodeName,
            'nodeType': dbNodeType,
            'participantAuditList': personId,
            'participantAuditNameList': personName,
        });
    }



    // 添加结束节点
    function endFlowableNode() {

        var nodeSelected = $('.flowableNodeSelected');

        if (nodeSelected.length == 0) {
            AlertUtils.msg('warning', '请选择前置节点！');
            return;
        }

        if ($('.flowableNodeEndEvent').length > 0) {
            AlertUtils.msg('warning', '结束节点已存在！');
            return;
        }

        if (nodeSelected.next().length != 0) {
            AlertUtils.msg('warning', '请选择最后一个节点！');
            return;
        }

        var $nodeSelected = $('.flowableNodeSelected');

        var $nodeFlow = $(generateFlowNodeHtml(2));
        var $nodeEnd = $(generateFlowNodeHtml(1, '结束'));

        $nodeSelected.after($nodeFlow);
        $nodeFlow.after($nodeEnd);

        $nodeEnd.bind('click', clickNodeEvent);

        $nodeEnd.data('dbData', {
            'id': MainUtils.guid(),
            'nodeType': 1,
            'nodeName': '结束'
        });

        generateFlowNodeData(1, $nodeFlow);
        generateFlowNodeData(3, $nodeEnd)
    }

    function generateFlowNodeData(nodeType, $nodeSelected) {

        if (nodeType && $nodeSelected) {

            var tempData = {};
        }
    }

    function editFlowableNode() {
        var nodeSelected = $('.flowableNodeSelected');

        if (nodeSelected.length == 0) {
            AlertUtils.msg('warning', '请先选择节点！');
            return;
        }

        if (nodeSelected.hasClass('flowableNodeStartEvent')
                || nodeSelected.hasClass('flowableNodeEndEvent')) {
            AlertUtils.msg('warning', '该节点不支持修改，请重新选择！');
            return;
        }

        var dbData = nodeSelected.data('dbData');
        var callback = {};
        if (2 == dbData.nodeType) {
            callback.onBeforeOpenDialog = function() {
                // 编辑单人节点
                var $dialog = $('#_SingleAuditNodeDialog');

                var $nodeName = $dialog.find('[name="flowNodeName"]');
                var $personText = $dialog.find('[name="personText"]');
                var $person_ = $dialog.find('[name="person_"]');


                $nodeName.val(dbData.nodeName);
                $person_.val(dbData.participantAuditList);
                $personText.val(dbData.participantAuditNameList);
            };

            FLOWABLE_UI_UTILS.openSingleAuditDialog('#_SingleAuditNodeDialog', false, callback);
        } else if (3 == dbData.nodeType) {
            callback.onBeforeOpenDialog = function() {
                // 编辑单人节点
                var $dialog = $('#_RoleAuditNodeDialog');

                var $nodeName = $dialog.find('[name="flowNodeName"]');
                var $personText = $dialog.find('[name="projectRoleText"]');
                var $person_ = $dialog.find('[name="projectRole_"]');

                $nodeName.val(dbData.nodeName);
                $person_.val(dbData.participantAuditList);
                $personText.val(dbData.participantAuditNameList);
            };

            FLOWABLE_UI_UTILS.openRoleAuditDialog('#_RoleAuditNodeDialog', false, callback);
        }
    }

    function deleteFlowableNode() {
        var nodeSelected = $('.flowableNodeSelected');

        if (nodeSelected.length == 0) {
            AlertUtils.msg('warning', '请先选择节点！');
            return;
        }

        if (nodeSelected.hasClass('flowableNodeStartEvent')) {
            AlertUtils.msg('warning', '该节点不支持删除，请重新选择！');
            return;
        }

        nodeSelected.prev().remove();
        nodeSelected.remove();
    }

    return {
        reloadFlowDetail: reloadFlowDetail,
        saveFlowDetailData: saveFlowDetailData,
        openSingleAuditDialog: openSingleAuditDialog,
        openRoleAuditDialog: openRoleAuditDialog,
        saveSingleAuditPersonNode: saveSingleAuditPersonNode,
        saveProjectRoleAuditNode: saveProjectRoleAuditNode,
        endFlowableNode: endFlowableNode,
        editFlowableNode: editFlowableNode,
        deleteFlowableNode: deleteFlowableNode,
        generateFlowableView: generateFlowableView
    }
})(jQuery);