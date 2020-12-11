gantt.config.editor_types.easyui_textbox = {
    // 生成Id方法 example textbox_duration_xxxx
    getId: function(column, id) {

        if (this.id) {
            return this.id;
        }

        this.id = 'textbox_' + column.name + '_' + id;

        return this.id;
    },
    show: function(id, column, config, placeholder) {

        gantt.selectTask(id);

        var _id = this.getId(column, id);

        var required = column.editor.required;
        var validType = column.editor.validType;

        placeholder.innerHTML = '<input id="' + _id
                + '" type="text" class="easyui-textbox" data-options="required:'
                + (required ? 'true' : 'false') + (validType ? ',' + validType : '') + '"/>';

        $.parser.parse($('#' + _id).parent());
    },
    hide: function() {
    },
    set_value: function(value, id, column, node) {
        $('#' + this.id).textbox('setValue', value);
    },
    get_value: function(id, column, node) {
        // $('#' + this.id).textbox('getValue');
    },
    is_changed: function(value, id, column, node) {
        var textValue = $('#' + this.id).textbox('getValue');

        if (textValue != value) {
            return true;
        }

        return false;
    },
    is_valid: function(value, id, column, node) {

        var $textbox = $('#' + this.id);

        if ($textbox.textbox('isValid')) {

            gantt.getTask(id)[column] = $textbox.textbox('getValue');

            gantt.updateTask(id);
        }

        return false;
    },
    save: function(id, column, node) {
        // only for inputs with map_to:auto. complex save behavior goes here
    },
    focus: function(node) {
        if (node) {
            $('#' + this.id).textbox('textbox').focus();
        }
    }
}

gantt.config.editor_types.easyui_number_spinner = {

    getId: function(column, id) {

        if (this.id) {
            return this.id;
        }

        this.id = 'number_spinner_' + column.name + '_' + id;

        return this.id;
    },
    show: function(id, column, config, placeholder) {
        gantt.selectTask(id);
        var _id = this.getId(column, id);

        var required = column.editor.required;
        var min = column.editor.min;
        var max = column.editor.max;
        var precision = column.editor.precision;

        placeholder.innerHTML = '<input id="' + _id
                + '" type="text" class="easyui-numberspinner" data-options="required:'
                + (required ? 'true' : 'false') + (min ? ',min:' + min : '')
                + (max ? ',max:' + max : '') + (precision ? ',precision:' + precision : '') + '"/>';

        $.parser.parse($('#' + _id).parent());
    },
    hide: function() {
    },
    set_value: function(value, id, column, node) {

        var _value = value;

        if ('progress' == column.name) {
            _value = (value * 100).toFixed(2);
        }

        $('#' + this.id).numberspinner('setValue', _value);
    },

    get_value: function(id, column, node) {
    },
    is_changed: function(value, id, column, node) {
        var textValue = $('#' + this.id).numberspinner('getValue');

        if (textValue != value) {
            return true;
        }

        return false;
    },
    is_valid: function(value, id, column, node) {
        var $textbox = $('#' + this.id);

        if ($textbox.numberspinner('isValid')) {

            var durationValue = parseInt($('#' + this.id).numberspinner('getValue'));

            if ('progress' == column) {
                durationValue = ($('#' + this.id).numberspinner('getValue'));
            }

            if (isNaN(durationValue) || durationValue < 0) {
                return false;
            }

            if ('duration' == column) {
                var calendar = gantt.getCalendar(_ganttData.workTimeBean.id);

                if (calendar) {

                    var end_date = calendar.calculateEndDate(gantt.getTask(id).start_date,
                            parseInt(durationValue));
                }
                gantt.getTask(id).end_date = end_date;
            } else if ('progress' == column) {
                // 进度包含小数点 0.65
                gantt.getTask(id)[column] = durationValue / 100;
            } else {
                gantt.getTask(id)[column] = durationValue;
            }

            gantt.updateTask(id);
        }

        return false;
    },
    save: function(id, column, node) {
    },
    focus: function(node) {
        $('#' + this.id).numberspinner('textbox').focus();
    }
}

gantt.config.editor_types.easyui_datebox = {

    getId: function(column, id) {

        if (this.id) {
            return this.id;
        }

        this.id = 'datebox_' + column.name + '_' + id;

        return this.id;
    },
    show: function(id, column, config, placeholder) {
        gantt.selectTask(id);
        var _id = this.getId(column, id);

        var required = column.editor.required;

        placeholder.innerHTML = '<input id="' + _id
                + '" type="text" class="easyui-datebox" data-options="required:'
                + (required ? 'true' : 'false') + ',parser:ScheduleProgressGantt.datetimeParser"/>';

        $.parser.parse($('#' + _id).parent());

        if (!required) {
            var buttons = $.extend([], $.fn.datebox.defaults.buttons);
            buttons.splice(1, 0, {
                text: '清空',
                handler: function(target) {
                    $('#' + _id).datebox('setValue', null);
                }
            });
            $('#' + _id).datebox({
                buttons: buttons
            });
        }
    },
    hide: function() {
    },
    set_value: function(value, id, column, node) {
        if (value) {
            if (typeof value == 'string') {
                $('#' + this.id).datebox('setValue', value);
            } else {
                var date = ScheduleProgressGantt.date_to_str(value); // -> "29/06/2019"
                $('#' + this.id).datebox('setValue', date);
            }
        }
    },

    get_value: function(id, column, node) {
        // $('#' + this.id).datebox('getValue');
    },
    is_changed: function(value, id, column, node) {

        var textValue = $('#' + this.id).datebox('getValue');
        var date = null;

        if (value) {
            if (typeof value == 'string') {
                return textValue != value;
            }

            date = ScheduleProgressGantt.date_to_str(value);
        }

        return textValue != date;
    },
    is_valid: function(value, id, column, node) {
        var $textbox = $('#' + this.id);

        if ($textbox.datebox('isValid')) {

            var dateValue = $textbox.datebox('getValue');

            if (dateValue == null || '' == dateValue) {
                gantt.getTask(id)[column] = null;
                gantt.updateTask(id);
                return true;
            }

            var t = Date.parse(dateValue);
            if (isNaN(t)) {
                return false;
            } else {

                var date = ScheduleProgressGantt.str_to_date(dateValue); // -> 29 June, 2019
                // 00:00:00

                gantt.getTask(id)[column] = date;

                gantt.updateTask(id);
            }
        }

        return false;
    },
    save: function(id, column, node) {
    },
    focus: function(node) {
        $('#' + this.id).datebox('textbox').focus();
    }
}

gantt.config.editor_types.easyui_combobox = {

    getId: function(column, id) {

    	console.log('Get ID id=' + id, column);
        if (this.id) {
            return this.id;
        }

        this.id = 'combobox_' + column.name + '_' + id;

        return this.id;
    },
    show: function(id, column, config, placeholder) {
        gantt.selectTask(id);
        var _id = this.getId(column, id);

        var required = column.editor.required;
        var url = column.editor.url;

        var optionName = column.editor.optionName;

        var _data = '';

        if (ScheduleProgressGantt.optionsMap[optionName]) {
            _data = ',data:ScheduleProgressGantt.optionsMap.' + optionName;
        }

        placeholder.innerHTML = '<input id="' + _id
                + '" type="text" class="easyui-combobox" data-options="required:'
                + (required ? 'true' : 'false') + _data
                + ',valueField:\'id\',textField:\'text\'"/>';

        $.parser.parse($('#' + _id).parent());
    },
    hide: function() {
    },
    set_value: function(value, id, column, node) {
    	console.log(value, id, column, node);
        if (value) {
            $('#' + this.id).combobox('setValue', value);
        }
    },
    get_value: function(id, column, node) {
        // $('#' + this.id).combobox('getValue');
    },
    is_changed: function(value, id, column, node) {

    	console.log(value, id, column, node);
        var textValue = $('#' + this.id).combobox('getValue');

        return textValue != value;
    },
    is_valid: function(value, id, column, node) {
        var $textbox = $('#' + this.id);

        if ($textbox.combobox('isValid')) {

            var dateValue = $textbox.combobox('getValue');

            if (dateValue == null || '' == dateValue) {
                gantt.getTask(id)[column] = null;
                gantt.updateTask(id);
                return true;
            }

            gantt.getTask(id)[column] = dateValue;
            gantt.updateTask(id);
        }

        return false;
    },
    save: function(id, column, node) {
    },
    focus: function(node) {
        $('#' + this.id).combobox('textbox').focus();
    }
}

gantt.showLightbox = function(id) {
	
    var $dialog = $('#_TaskEditorDialog');

    var $normalForm = $('#_TaskEditorNormalForm');
    var $advForm = $('#_TaskEditorAdvForm');
    var $remarkForm = $('#_TaskEditorRemarkForm');

    $normalForm.form('reset');
    $advForm.form('reset');
    $remarkForm.form('reset');

    $normalForm.form('disableValidation');
    $advForm.form('disableValidation');
    $remarkForm.form('disableValidation');

    var task = gantt.getTask(id);
    
    console.log('打开编辑前', task.participantName, task.participantId);
    if (task.participantName && task.participantId) {
    	const names = task.participantName.split(',');
    	const ids = task.participantId.split(',');
    	
    	for (let i = 0; i < names.length; i++) {
    		_selectedParticipants.push({ id: ids[i], text: names[i] })
    	}
    }
    console.log('打开编辑后', _selectedParticipants);

    ScheduleProgressGantt.currentEditTaskId = task.id;

    // 常规
    var $taskName = ScheduleProgressGantt.getWidget('taskName');
    var $achievement = ScheduleProgressGantt.getWidget('achievement');
    var $delivery = ScheduleProgressGantt.getWidget('delivery');
    var $taskType = ScheduleProgressGantt.getWidget('taskType');
    var $wbs = ScheduleProgressGantt.getWidget('wbs');

    var $durationDays = ScheduleProgressGantt.getWidget('durationDays');
    var $workload = ScheduleProgressGantt.getWidget('workload');
    var $progress = ScheduleProgressGantt.getWidget('progress');
    var $lastProgress = ScheduleProgressGantt.getWidget('lastProgress');
    var $weight = ScheduleProgressGantt.getWidget('weight');
    var $leader = ScheduleProgressGantt.getWidget('leader');
    var $participants = ScheduleProgressGantt.getWidget('participants');
    var $participantsText = ScheduleProgressGantt.getWidget('participantsText');

    var $startDate = ScheduleProgressGantt.getWidget('startDate');
    var $endDate = ScheduleProgressGantt.getWidget('endDate');

    // 高级选项
    var $constraintTypeCombo = ScheduleProgressGantt.getWidget('constraintType');
    var $constraintDatebox = ScheduleProgressGantt.getWidget('constraintDate');
    var $deadlineDate = ScheduleProgressGantt.getWidget('deadlineDate');

    var $isSummary = ScheduleProgressGantt.getWidget('isSummary', true);
    var $isMilestone = ScheduleProgressGantt.getWidget('isMilestone', true);
    var $isCritical = ScheduleProgressGantt.getWidget('isCritical', true);
    var $isSiteTask = ScheduleProgressGantt.getWidget('isSiteTask', true);

    // 备注
    var $remark = ScheduleProgressGantt.getWidget('remark');

    function process(isFirstLoad) {

        // 常规设置
        $taskName.textbox('setValue', task.text);
        $taskType.textbox('setValue', task.taskType);
        $achievement.textbox('setValue', task.achievement);
        $delivery.textbox('setValue', task.delivery);
        $durationDays.textbox('setValue', task.duration);
        $progress.textbox('setValue', (task.progress * 100).toFixed(2));
//        $lastProgress.textbox('setValue', (task.lastProgress * 100).toFixed(2));

        $startDate.datebox('setValue', ScheduleProgressGantt.date_to_str(task.start_date));
        $endDate.datebox('setValue', ScheduleProgressGantt.date_to_str(task.end_date));

        $leader.combobox('setValue', task.leader);

        const participants = [];
        if (task.participantName && task.participantId) {
        	const names = task.participantName.split(',');
        	const ids = task.participantId.split(',');
        	for (let i = 0; i < ids.length; i++) {
        		participants.push({
            		id: ids[i],
            		text: names[i]
            	});
        	}
        }
        $participants.combobox('setValues', participants);
        $participantsText.textbox('setText', task.participantName);

        $wbs.textbox('setValue', task.$wbs);

        $workload.numberspinner('setValue', task.workload);
        $weight.numberspinner('setValue', task.weight);
        $remark.textbox('setValue', task.remark);

        var data = [];
        // 前置任务
        var linkIds = task.$target;

        if (linkIds.length > 0) {

            for ( var l in linkIds) {

                var linkId = linkIds[l];

                var link = gantt.getLink(linkId);

                var sourceTask = gantt.getTask(link.source);

                data.push({
                    id: sourceTask.$wbs,
                    taskName: sourceTask.text,
                    linkType: link.type,
                    lagDays: link.lag ? link.lag : 0
                });
            }
        }

        $('#_TaskLinkGrid').datagrid('loadData', data);
        // 高级选项
        // 限制类型与日期
        var constraintType = gantt.getConstraintType(task);

        if (constraintType) {
            $constraintTypeCombo.combobox('setValue', constraintType);

            var _date = task.constraint_date;

            if (_date) {
                var strDate = ScheduleProgressGantt.date_to_str(_date);
                $constraintDatebox.datebox('setValue', strDate);
            }
        }

        // 最后期限
        if (task.deadline_date) {
            $deadlineDate
                    .datebox('setValue', ScheduleProgressGantt.date_to_str(task.deadline_date));
        }

        if (1 == task.isSummary) {
            $isSummary.prop('checked', true);
        } else {
            $isSummary.prop('checked', false);
        }

        if (1 == task.isMilestone) {
            $isMilestone.prop('checked', true);
        } else {
            $isMilestone.prop('checked', false);
        }

        if (1 == task.isCritical) {
            $isCritical.prop('checked', true);
        } else {
            $isCritical.prop('checked', false);
        }

        if (1 == task.isSiteTask) {
            $isSiteTask.prop('checked', true);
        } else {
            $isSiteTask.prop('checked', false);
        }

        $dialog.dialog('open').dialog('center').scrollTop(0);

        $('#_TaskEditorTabs').tabs('select', 0);
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
};

gantt.hideLightbox = function() {
    $('#_TaskEditorDialog').dialog('close');
}
