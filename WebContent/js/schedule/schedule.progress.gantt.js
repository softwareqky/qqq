var _ganttData = {};
var _selectTaskIdList = [];
var _selectAllFlag = false;
var _ganttCheckBoxClickFlag = false;
var _selectedParticipants = [];
var _briefType = 0;
var _selectedParticipantFilterName = '';
var _ganttInitialized = false;
var _taskNameFilterName = '';
var _tempAttachmentList = [];

var ScheduleProgressGantt = (function($) {
	
	$("input[name=participants]").combobox({
		onSelect: function(v) {
			const selectedIdx = _selectedParticipants.findIndex(p => p.id == v.id);
			if (selectedIdx < 0) {
				_selectedParticipants.push(v);
			}
			
			ScheduleProgressGantt.getWidget('participantsText').textbox('setText', _selectedParticipants ? _selectedParticipants.map(p => p.text).join(', ') : '');
		},
		onUnselect: function(v) {
			const selectedIdx = _selectedParticipants.findIndex(p => p.id == v.id);
			if (selectedIdx >= 0) {
				_selectedParticipants.splice(selectedIdx, 1);
			}
			
			ScheduleProgressGantt.getWidget('participantsText').textbox('setText', _selectedParticipants ? _selectedParticipants.map(p => p.text).join(', ') : '');
		},
		onHidePanel: function() {
			$("#participantInput").css("z-index", 999);
			$(this).combobox('setValues', _selectedParticipants.map(p => p.id));
		},
		onShowPanel: function() {
			$("#participantInput").css("z-index", 1002);
		}
	});
	
	$("#participantMask").click(function() {
		$('#participantsCombo .combo-arrow').click();
	});
	
	$("#participantMask1").click(function() {
		$('#participantsCombo1 .combo-arrow').click();
	});
	
    $(".maximzebuttonposition").css("left", $(window).width() * 0.67 + 1);

    $("#ganttDiv").on("click", "#selectAll", function() {
        _selectTaskIdList = [];
        if ($("#selectAll").is(":checked")) {
            _selectAllFlag = true;
            $(".gantt_row input[type='checkbox']").prop("checked", true);
            for (var i = 0; i < _ganttData.taskBeans.length; i++) {
                _selectTaskIdList.push(_ganttData.taskBeans[i].id);
            }
        } else {
            _selectAllFlag = false;
            $(".gantt_row input[type='checkbox']").prop("checked", false);
        }
        console.dir(_selectTaskIdList)
        
        if (_selectTaskIdList.length > 0) {
        	$("#_LinkButton_upload>a").linkbutton('enable');
        } else {
        	$("#_LinkButton_upload>a").linkbutton('disable');
        }
    })
    
    $("#ganttDiv").on("click", "input[name='mulitselect']", function() {
    	_ganttCheckBoxClickFlag = true;
    })
    
    // 六个月
    // var _long = 5184000000;
    var _long = 1184000000;

    // 预警天数毫秒数
    var overdueDayLong = 7 * 24 * 60 * 60 * 1000;

    var str_to_date = gantt.date.str_to_date("%Y-%m-%d");
    var date_to_str = gantt.date.date_to_str("%Y-%m-%d");

    var $linkdatagrid = $('#_TaskLinkGrid');
    var $taskEditorDialog = $('#_TaskEditorDialog');
    var $calendarEditorDialog = $('#_CalendarEditorDialog');
    var $calendarExceptionGrid = $('#_CalendarExceptionGrid');
    var $workTimeDatagrid = $('#_WorkTimeGrid');

    var $reportProgressDialog = $('#_ReportProgressDialog');
    var $reportProgressForm = $('#_ReportProgressForm');
    var $reportProgressGrid = $('#_ReportProgressGrid');

    var pre_Checkboxx_workday_ = '_Checkboxx_workday_';

    // html ui
    var $taskCountNumberLable = $('#_TaskCountNumber');
    var isMaximize = false;
    var defaultLayout = {
        css: "gantt_container",
        cols: [{
            width: $(window).width() * 0.67,
            // min_width: 800,
            rows: [{
                view: "grid",
                scrollX: "gridScroll",
                scrollable: true,
                scrollY: "scrollVer"
            },
            // horizontal scrollbar for the grid
            {
                view: "scrollbar",
                id: "gridScroll",
                group: "horizontal"
            }]
        }, {
            resizer: true,
            width: 3
        }, {
            rows: [{
                view: "timeline",
                scrollX: "scrollHor",
                scrollY: "scrollVer"
            },
            // horizontal scrollbar for the timeline
            {
                view: "scrollbar",
                id: "scrollHor",
                group: "horizontal"
            }]
        }, {
            view: "scrollbar",
            id: "scrollVer"
        }]
    };

    // deadlinedatebox 清空按钮
    var deadlineButtons = $.extend([], $.fn.datebox.defaults.buttons);
    deadlineButtons.splice(1, 0, {
        text: '清空',
        handler: function(target) {
            ScheduleProgressGantt.getWidget('deadlineDate').datebox('setValue', null);
        }
    });

    // "1FF-1d" 1days ->1d
    var linkFormatter = gantt.ext.formatters.linkFormatter({
        'durationFormatter': gantt.ext.formatters.durationFormatter({
            'short': true,
        })
    });

    var _units = ['year', 'half_year', 'quarter', 'month', 'week', 'day', 'hour'];

    var constraintTypes = [{
        id: 'asap',
        text: '越早越好',
        selected: true
    }, {
        id: 'alap',
        text: '越晚越好',
    }, {
        id: 'mso',
        text: '必须开始于',
    }, {
        id: 'mfo',
        text: '必须完成于',
    }, {
        id: 'snet',
        text: '不得早于…开始',
    }, {
        id: 'snlt',
        text: '不得晚于…开始',
    }, {
        id: 'fnet',
        text: '不得早于…完成',
    }, {
        id: 'fnlt',
        text: '不得晚于…完成',
    }];

    var FlowStatusComboData = [{
        "id": 1,
        "text": "未启动"
    }, {
        "id": 2,
        "text": "延期中"
    }, {
        "id": 3,
        "text": "进行中",
        "selected": true
    }, {
        "id": 4,
        "text": "完成"
    }, {
        "id": 5,
        "text": "搁置"
    }, {
        "id": 6,
        "text": "取消"
    }];

    var IssuesComboData = [{
        "id": "PLAN_TASK_ISSUES_TYPE_0",
        "text": "基础条件不足"
    }, {
        "id": "PLAN_TASK_ISSUES_TYPE_1",
        "text": "需求变化"
    }, {
        "id": "PLAN_TASK_ISSUES_TYPE_2",
        "text": "人员变动"
    }, {
        "id": "PLAN_TASK_ISSUES_TYPE_3",
        "text": "资金紧缺"
    }, {
        "id": "PLAN_TASK_ISSUES_TYPE_4",
        "text": "其他"
    }];

    // 加载行编辑结束后的资源
    function formatterReportProgressComboResource(value, row, index) {

        if (value) {
            for ( var r in FlowStatusComboData) {
                var obj = FlowStatusComboData[r];

                if (value == obj.id) {
                    return obj.text;
                }
            }
        }

        return null;
    }

    // 加载行编辑结束后的资源--问题类型
    function formatterIssuesComboResource(value, row, index) {
        var issuesComboData = ScheduleProgressGantt.optionsMap['issueOption']
        if (value) {
            for ( var r in issuesComboData) {
                var obj = issuesComboData[r];

                if (value == obj.id) {
                    return obj.text;
                }
            }
        }

        return null;
    }

    // 格式化进度 99.99%显示
    function formatterProgressNumber(value, row, index) {
        if (value) {
            return Math.floor(value) + "%";
        }

        return null;
    }

    // 自定义字段
    var columns = [{
        'name': 'checkbox',
        'label': '<input type="checkbox" id="selectAll" style="zoom:130%"/>',
        'align': 'center',
        'width': 32,
        'resize': true,
        'template': function() {
            return '<input type="checkbox" name="mulitselect" style="zoom:130%"/>';
        }
    }, {
        'name': "overdue",
        'label': "",
        'width': 32,
        'template': function(task) {
            if (task.end_date && task.end_date < new Date() && task.progress != 100) {
                return '<div class="overdue-indicator">!</div>';
            }
            return '<div></div>';
        }
    }, {
        'name': 'wbs',
        'label': '标识',
        'align': 'left',
        'width': 70,
        'resize': true,
        'template': gantt.getWBSCode
    }, {
        'name': 'text',
        'label': '任务名称',
        'tree': true,
        'resize': true,
        'width': 300,
    // 'editor': {
    // type: "easyui_textbox",
    // map_to: "text",
    // required: true,
    // validType: "validType:\'length[1,50]\'"
    // }
    }, {
        'name': 'achievement',
        'label': '交付物',
        'resize': true,
        'width': 200,
    }, {
        'name': 'leader',
        'label': '负责人',
        'align': 'center',
        'resize': true,
        'width': 120,
        // 'editor': {
        // type: "easyui_combobox",
        // map_to: "leader",
        // required: false,
        // optionName: 'leaderOption'
        // // url: BASE_URL + '/schedule/plan-task/person-combo-list.json'
        // },
        'template': function(task) {
            return getComboLabel('leaderOption', task.leader);
        }
    }, {
        'name': 'participants',
        'label': '参与人员',
        // 'align': 'center',
        'resize': true,
        'width': 150,
//        'optinoName': 'leaderOption'
    // 'editor': {
    // type: "easyui_number_spinner",
    // map_to: "weight",
    // min: 0,
    // max: 100
    // }
    }, {
        'name': 'taskType',
        'label': '任务类型',
        'resize': true,
        'width': 100,
    }, {
        'name': 'duration',
        'label': '工期',
        'align': 'center',
        'resize': true,
        'width': 70,
    // 'editor': {
    // type: "easyui_number_spinner",
    // map_to: "duration",
    // required: true,
    // min: 0,
    // max: 10000
    // }
    }, {
        'name': 'start_date',
        'label': '开始日期',
        'align': 'center',
        'resize': true,
        'width': 120,
    // 'editor': {
    // type: "easyui_datebox",
    // map_to: "start_date",
    // required: true
    // }
    }, {
        'name': 'end_date',
        'label': '结束日期',
        'align': 'center',
        'resize': true,
        'width': 120,
    // 'editor': {
    // type: "easyui_datebox",
    // map_to: "end_date",
    // required: true
    // }
    }, {
        'name': 'linkText',
        'label': '前置任务',
        'resize': true,
        'width': 120,
        'template': function(task) {
            var links = task.$target;
            var labels = [];
            for (var i = 0; i < links.length; i++) {
                var link = gantt.getLink(links[i]);
                labels.push(linkFormatter.format(link));
            }
            return labels.join(",")
        }
    }
    // , {
    // 'name': 'progress',
    // 'label': '进度',
    // 'align': 'center',
    // 'hide': true,
    // 'width': 80,
    // 'editor': {
    // type: "easyui_number_spinner",
    // map_to: "progress",
    // required: true,
    // min: 0,
    // max: 100,
    // precision: 2
    // },
    // 'template': function(task) {
    // return (task.progress * 100).toFixed(2) + "%";
    // }
    // }
    // , {
    // 'name': 'workload',
    // 'label': '工时',
    // 'align': 'center',
    // 'hide': true,
    // 'width': 70,
    // 'editor': {
    // type: "easyui_number_spinner",
    // map_to: "workload",
    // min: 0,
    // max: 10000
    // }}
    , {
        'name': 'taskLayer',
        'label': '层级',
        'align': 'center',
        'resize': true,
        'width': 70,
        'template': function(task) {
            return task.$level + 1;
        }
    }, {
        'name': 'weight',
        'label': '优先级',
        'align': 'center',
        'resize': true,
        'width': 70,
    // 'editor': {
    // type: "easyui_number_spinner",
    // map_to: "weight",
    // min: 0,
    // max: 100
    // }
    }, {
        'name': 'remark',
        'label': '备注',
        'align': 'center',
        'resize': true,
        'width': 180
    }, {
        'name': 'deadline_date',
        'label': '最后期限日',
        'align': 'center',
        'resize': true,
        'width': 120,
    // 'editor': {
    // type: "easyui_datebox",
    // map_to: "deadline_date",
    // required: false,
    // }
    }, {
        'name': 'taskNum',
        'label': '序号',
        'hide': true,
        'align': 'center',
        'template': function(task) {
            return task.$index + 1;
        }
    }];

    function getComboLabel(type, id) {
        if (ScheduleProgressGantt.optionsMap[type]) {
            for ( var index in ScheduleProgressGantt.optionsMap[type]) {
                var combo = ScheduleProgressGantt.optionsMap[type][index];

                if (combo.id == id) {
                    return combo.text;
                }
            }
        }
        return '';
    }

    var zoomConfig = {
        levels: [{
            name: "day_one_hour",
            scales: [{
                unit: "hour",
                step: 1,
                format: "%H"
            }, {
                unit: "day",
                step: 1,
                format: "%Y-%m-%d"
            }]
        }, {
            name: "day_six_hour",
            scales: [{
                unit: "hour",
                step: 6,
                format: "%H"
            }, {
                unit: "day",
                step: 1,
                format: "%Y-%m-%d"
            }]
        }, {
            name: "week_day",
            scales: [{
                unit: "day",
                step: 1,
                format: "%D",
            // css: daysStyle
            }, {
                unit: "week",
                step: 1,
                format: "%Y-%m-%d"
            }]
        }, {
            name: "month_three_day",
            scales: [{
                unit: "day",
                step: 3,
                format: "%d"
            }, {
                unit: "month",
                step: 1,
                format: "%Y-%m"
            }]
        }, {
            name: "month_week",
            scales: [{
                unit: "week",
                step: 1,
                format: "%d"
            }, {
                unit: "month",
                step: 1,
                format: "%Y-%m"
            }]
        }, {
            name: "querter_year_month",
            scales: [{
                unit: "month",
                step: 1,
                format: "%M"
            }, {
                unit: "quarter",
                step: 1,
                template: formatYearAndQuarterYearLabel
            }]
        }, {
            name: "year_month",
            scales: [{
                unit: "month",
                step: 1,
                format: "%M"
            }, {
                unit: "year",
                step: 1,
                format: "%Y"
            }]
        }, {
            name: "year_quarter_year",
            scales: [{
                unit: "quarter",
                step: 1,
                template: formatQuarterYearLabel
            }, {
                unit: "year",
                step: 1,
                format: "%Y"
            }]
        }, {
            name: "year_half_year",
            scales: [{
                unit: "half_year",
                step: 1,
                template: formatHalfYearLabel
            }, {
                unit: "year",
                step: 1,
                format: "%Y"
            }]
        }],
        minColumnWidth: 30
    };

    function zoomIn() {
        gantt.ext.zoom.zoomIn();
    }

    function zoomOut() {
        gantt.ext.zoom.zoomOut();
    }

    function renderDiv(task, date, className, title) {
        var el = document.createElement('div');
        el.className = className;
        var sizes = gantt.getTaskPosition(task, date);
        el.style.left = sizes.left + 'px';
        el.style.top = sizes.top + 'px';

        el.title = title;
        return el;
    }

    function initWorkTime() {
        var oldCalendar = gantt.getCalendar(_ganttData.workTimeBean.id);

        if (oldCalendar) {
            gantt.deleteCalendar(_ganttData.workTimeBean.id);
        }

        // a number of a week day [0 (Sunday) - 6 (Saturday)]. Note, you can set only 1 day at once
        var calendarId = gantt.addCalendar({
            id: _ganttData.workTimeBean.id, // optional
            worktime: {
                days: _ganttData.workTimeBean.workTimes
            }
        });

        var newCalendar = gantt.getCalendar(calendarId);

        for ( var ce in _ganttData.calendarExceptionBeans) {
            var exceptionBean = _ganttData.calendarExceptionBeans[ce];

            var isHour = true;

            if (0 == exceptionBean.isWorkday) {
                // 休息日
                isHour = false;
            }

            hanlderWorkTime(exceptionBean.startDate, exceptionBean.endDate, isHour, newCalendar);
        }
    }

    function hanlderWorkTime(startDate, endDate, isHour, calendar) {

        var start_date = ScheduleProgressGantt.str_to_date(startDate);
        var end_date = ScheduleProgressGantt.str_to_date(endDate);

        while (start_date.getTime() <= end_date.getTime()) {

            // makes a day-off
            calendar.setWorkTime({
                date: start_date,
                hours: isHour
            });

            start_date = gantt.date.add(start_date, 1, 'day');
        }
    }

    function generateStartAndEndDate(taskBeans) {

        var _start_date_arr = [];
        var _end_date_arr = [];

        if (taskBeans) {
            for ( var t in taskBeans) {
                var task = taskBeans[t];
                _start_date_arr.push(ScheduleProgressGantt.str_to_date(task.start_date).getTime());
                _end_date_arr.push(ScheduleProgressGantt.str_to_date(task.end_date).getTime());
            }
        } else {
            gantt.eachTask(function(task) {
                _start_date_arr.push(task.start_date.getTime());
                _end_date_arr.push(task.end_date.getTime());
            });
        }

        _start_date_arr.sort();
        _end_date_arr.sort();

        if (_start_date_arr.length > 0) {
            gantt.config.start_date = new Date(_start_date_arr[0] - _long);
        }

        if (_end_date_arr.length > 0) {
            gantt.config.end_date = new Date(_end_date_arr[_end_date_arr.length - 1] + _long);
        }

        if (!taskBeans) {

            gantt.render();
        }
    }

    function ganttInit(isModify) {
        _ganttData.isModify = isModify;
        // gantt.config.root_id = null;

        gantt.attachEvent("onAfterLinkAdd", function(id, link) {
            gantt.changeLinkId(id, MainUtils.guid());
        });

        gantt.attachEvent("onAfterTaskAdd", function(id, item) {
            generateStartAndEndDate();
        });

        gantt.attachEvent("onAfterTaskUpdate", function(id, item) {
            generateStartAndEndDate();
        });

        gantt.attachEvent("onAfterTaskDelete", function(id, item) {
            refreshTaskCountNumber();
        });

        gantt.attachEvent("onAfterTaskAdd", function(id, item) {
            refreshTaskCountNumber();
        });

        gantt.attachEvent("onGridResizeEnd", function(old_width, new_width) {// 用户完成拖动网格的边界以调整网格大小后触发;old_width-初始网格的宽度
            // new_width-新网格的宽度
            $(".maximzebuttonposition").css("left", new_width + 1);
            // 用户完成拖动网格后，动态将已选中的行重新选中。
            setTimeout(function() {// 延迟0.2秒执行，等待甘特图渲染完成后，再重新选中。
                if (_selectAllFlag) {
                    $("#selectAll").prop("checked", true);
                }
                for (var i = 0; i < _selectTaskIdList.length; i++) {
                    $(".gantt_row[task_id='" + _selectTaskIdList[i] + "']").find(
                            "input[type='checkbox']").prop("checked", true);
                }
            }, 200);
        });

        gantt.attachEvent("onGanttRender", function(old_width, new_width) {// 甘特图窗口rezise完成后
            if (_selectAllFlag) {
                $("#selectAll").prop("checked", true);
            }
            for (var i = 0; i < _selectTaskIdList.length; i++) {
                $(".gantt_row[task_id='" + _selectTaskIdList[i] + "']").find(
                        "input[type='checkbox']").prop("checked", true);
            }
        });

        gantt.attachEvent("onGanttScroll", function(left, top) {// 鼠标滚动事件
            if (_selectAllFlag) {
                $("#selectAll").prop("checked", true);
            }
            for (var i = 0; i < _selectTaskIdList.length; i++) {
                $(".gantt_row[task_id='" + _selectTaskIdList[i] + "']").find(
                        "input[type='checkbox']").prop("checked", true);
            }
        });
        
        gantt.attachEvent("onTaskRowClick",
                function(id) {
                    if (_ganttCheckBoxClickFlag) {
                    	// checkbox click
                    	_ganttCheckBoxClickFlag = false;
                    	if ($(".gantt_row[task_id='" + id + "']").find("input[type='checkbox']").is(
		                        ":checked")) {
		                    $(".gantt_row[task_id='" + id + "']").find("input[type='checkbox']").prop(
		                            "checked", false);
		                    var index = $.inArray(id, _selectTaskIdList);
		                    if (index > -1) {
		                        _selectTaskIdList.splice(index, 1);
		                    }
		                } else {
		                    $(".gantt_row[task_id='" + id + "']").find("input[type='checkbox']").prop(
		                            "checked", true);
		                    var index = $.inArray(id, _selectTaskIdList);
		                    if (index == -1) {
		                        _selectTaskIdList.push(id);
		                    }
		                }
                    } else {
                    	// row select
                    	if ($(".gantt_row[task_id='" + id + "']").find("input[type='checkbox']").is(
		                        ":checked")) {
		                	// selected -> unselected
                    		$(".gantt_row[task_id='" + id + "']").find("input[type='checkbox']").prop(
		                            "checked", false);
		                    var index = $.inArray(id, _selectTaskIdList);
		                    if (index > -1) {
		                        _selectTaskIdList.splice(index, 1);
		                    }
		                } else {
                    		// unselected -> selected
		                	for (var i = _selectTaskIdList.length-1; i >=0 ; i--) {
                                $(".gantt_row[task_id='" + _selectTaskIdList[i] + "']").find(
                                        "input[type='checkbox']").prop("checked", false);
                                _selectTaskIdList.splice(i, 1);
                            }
                    		$(".gantt_row[task_id='" + id + "']").find("input[type='checkbox']").prop(
		                            "checked", true);
		                    var index = $.inArray(id, _selectTaskIdList);
		                    if (index == -1) {
		                        _selectTaskIdList.push(id);
		                    }
		                }
                    }
                    
                    if (_ganttData.taskBeans.length == _selectTaskIdList.length) {
                    	$("#selectAll").prop("checked", true);
                    } else {
                    	$("#selectAll").prop("checked", false);
                    }
                    
                    console.dir(_selectTaskIdList)
                    
                    if (_selectTaskIdList.length > 0) {
                    	$("#_LinkButton_upload>a").linkbutton('enable');
                    } else {
                    	$("#_LinkButton_upload>a").linkbutton('disable');
                    }
                });

        gantt.config.min_column_width = 30;

        gantt.config.date_format = "%Y-%m-%d %H:%i:%s";

        gantt.config.auto_scheduling = true;
        gantt.config.auto_scheduling_strict = true;
        gantt.config.auto_scheduling_initial = true;

        gantt.config.work_time = true;
        gantt.config.correct_work_time = true;
        gantt.config.start_on_monday = false;

        initWorkTime();

        // gantt.config.schedule_from_end = true;
        // gantt.config.project_end = new Date(2019, 1, 1);
        // gantt.addMarker({
        // start_date: gantt.config.project_end,
        // text: "项目结束"
        // });

        gantt.config.fit_tasks = true;

        this.columns[9].template = function(task) {
            var end_date = task.end_date.getTime();

            var now = new Date().getTime();

            // 计划任务比较时不显示皇底
            if (6 != isModify) {
            	if (task.progress >= 0.5 || ((end_date - now) <= overdueDayLong)) {
//          		return '<div class="overdue-warning"></div>';
            		return "<div style='background: #ffff00;color: #f03434;'>"+gantt.templates.tooltip_date_format(task.start_date)+"</div>";
	        	}
            }

            return gantt.templates.tooltip_date_format(task.start_date);
        };
        
        this.columns[10].template = function(task) {
            var end_date = task.end_date.getTime();

            var now = new Date().getTime();

            // 计划任务比较时不显示皇底
            if (6 != isModify) {
            	if (task.progress >= 0.5 || ((end_date - now) <= overdueDayLong)) {
//                  return '<div class="overdue-warning"></div>';
            		return "<div style='background: #ffff00;color: #f03434;'>"+gantt.templates.tooltip_date_format(task.end_date)+"</div>";
            	}
            }

            return gantt.templates.tooltip_date_format(task.end_date);
        };

        if (2 == isModify) {
            this.columns.push({
                'name': 'flowStatus',
                'label': '完成状态',
                'align': 'center',
                'resize': true,
                'width': 100,
                'template': function(task) {
                    return formatterReportProgressComboResource(task.flowStatus);
                }
            });
            this.columns.push({
                'name': 'lastProgress',
                'label': '上期进度',
                'align': 'center',
                'resize': true,
                'width': 80,
                'template': function(task) {
                    return task.lastProgress==null?0:Math.floor(task.lastProgress) + "%";
                }
            });
            this.columns.push({
                'name': 'progress',
                'label': '本期进度',
                'align': 'center',
                'resize': true,
                'width': 80,
                'template': function(task) {
                    return task.progress==null?0:Math.floor(task.progress) + "%";
                }
            });
            this.columns.push({
                'name': 'actualStartDate',
                'label': '实际开始时间',
                'align': 'center',
                'resize': true,
                'width': 120
            });
            this.columns.push({
                'name': 'actualEndDate',
                'label': '实际完成时间',
                'align': 'center',
                'resize': true,
                'width': 120

            });
            this.columns.push({
                'name': 'cumulativeProgress',
                'label': '累计进展情况',
                'align': 'center',
                'resize': true,
                'width': 130
            });
            this.columns.push({
                'name': 'currentWeekProgress',
                'label': '本期进展情况',
                'align': 'center',
                'resize': true,
                'width': 130
            });
            this.columns.push({
                'name': 'issue_type',
                'label': '问题类型',
                'align': 'center',
                'resize': true,
                'width': 140,
                'template': function(task) {
                    return getComboLabel('issueOption', task.issueType);
                }
            });
            this.columns.push({
                'name': 'description',
                'label': '计划差异/偏离/问题描述',
                'align': 'center',
                'resize': true,
                'width': 150
            });
            this.columns.push({
                'name': 'nextWeekGoals',
                'label': '下期计划目标',
                'align': 'center',
                'resize': true,
                'width': 130
            });
            var new_columns = new Array();
            var old_columns = this.columns;
            new_columns[0] = old_columns[0];
            new_columns[1] = old_columns[1];
            new_columns[2] = old_columns[2];
            new_columns[3] = old_columns[3];
            new_columns[4] = old_columns[17];
            new_columns[5] = old_columns[18];
            new_columns[6] = old_columns[19];
            new_columns[7] = old_columns[20];
            new_columns[8] = old_columns[21];
            new_columns[9] = old_columns[22];
            new_columns[10] = old_columns[23];
            new_columns[11] = old_columns[4];
            new_columns[12] = old_columns[24];
            new_columns[13] = old_columns[25];
            new_columns[14] = old_columns[26];
            new_columns[15] = old_columns[5];
            new_columns[16] = old_columns[6];
            new_columns[17] = old_columns[7];
            new_columns[18] = old_columns[8];
            new_columns[19] = old_columns[9];
            new_columns[20] = old_columns[10];
            new_columns[21] = old_columns[11];
            new_columns[22] = old_columns[12];
            new_columns[23] = old_columns[13];
            new_columns[24] = old_columns[14];
            new_columns[25] = old_columns[15];
            new_columns[26] = old_columns[16];
            this.columns = new_columns;
        }
        
        
        gantt.config.columns = this.columns;

        gantt.config.scale_height = 55;
        gantt.config.row_height = 30;
        gantt.config.drag_links = false;
        gantt.config.drag_progress = false;

        gantt.addTaskLayer(function draw_deadline(task) {
            var constraintType = gantt.getConstraintType(task);
            var types = gantt.config.constraint_types;
            if (constraintType != types.ASAP && constraintType != types.ALAP
                    && task.constraint_date) {
                var dates = gantt.getConstraintLimitations(task);

                var els = document.createElement("div");

                var constraintDate = gantt.locale.labels[constraintType] + ' '
                        + ScheduleProgressGantt.date_to_str(task.constraint_date);

                // if (dates.earliestStart) {
                // els.appendChild(renderDiv(task, dates.earliestStart,
                // 'constraint-marker earliest-start', constraintDate));
                // }

                if (dates.latestEnd) {
                    els.appendChild(renderDiv(task, dates.latestEnd,
                            'constraint-marker latest-end', constraintDate));
                }

                if (els.children.length)
                    return els;
            }
            return false;
        });

        gantt.addTaskLayer({
            renderer: {
                render: function draw_deadline(task) {
                    if (task.deadline_date) {

                        var els = document.createElement("div");

                        els.appendChild(renderDiv(task, task.deadline_date, 'deadline',
                                ScheduleProgressGantt.date_to_str(task.deadline_date)));

                        if (els.children.length) {
                            return els;
                        }
                        return els;
                    }
                    return false;
                },
                // define getRectangle in order to hook layer with the smart rendering
                getRectangle: function(task, view) {
                    if (task.deadline_date) {
                        var sizes = gantt.getTaskPosition(task, task.deadline_date);
                        return gantt.getTaskPosition(task, task.deadline_date);
                    }
                    return null;
                }
            }
        });

        gantt.templates.task_class = function(start, end, task) {
            if (5 == task.flowStatus || 6 == task.flowStatus) {
                return 'overflowShelveCancel';
            } else if (task.deadline_date) {// 最后期限
                if (end.valueOf() > task.deadline_date.valueOf()) {
                    return 'overdue';
                }
            } else if (1 == task.isCritical) { // 关键任务（手动）
                return 'gantt_critical_task';
            } else if ('project' == gantt.getTaskType(task)) {
                return 'custom_project';
            } else if ('task' == gantt.getTaskType(task)) {
                return 'custom_task';
            }
        };

        gantt.templates.grid_header_class = function(columnName, column) {
            return "custom_grid_header";
        };

        gantt.templates.scale_row_class = function(scale) {
            return "custom_row";
        }

        // 右侧过期文本
        gantt.templates.rightside_text = function(start, end, task) {
            if (task.deadline_date) {

                if (end.valueOf() > task.deadline_date.valueOf()) {
                    var overdue = Math.ceil(Math.abs((end.getTime() - task.deadline_date.getTime())
                            / (24 * 60 * 60 * 1000)));
                    var text = "<b>过期: " + overdue + " 天</b>";
                    return text;
                }
            }
        };

        gantt.config.layout = defaultLayout;

        gantt.templates.scale_cell_class = function(date) {
            return formatWeekendStyle(gantt.getScale().unit, date);
        };

        gantt.templates.timeline_cell_class = function(item, date) {
            return formatWeekendStyle(gantt.getScale().unit, date);
        };

        if (isModify != 6) {
        	gantt.templates.tooltip_text = function(start, end, task) {
                return "<b>任务:</b> " + task.text + "<br/><b>开始日期:</b> "
                        + gantt.templates.tooltip_date_format(start) + "<br/><b>结束日期:</b> "
                        + gantt.templates.tooltip_date_format(end);
            };
        }
        

        gantt.ext.zoom.init(ScheduleProgressGantt.zoomConfig);
        gantt.ext.zoom.setLevel("year_month");
    }

    function onChangeTopZoomScale(unit) {
        var secondUnit = gantt.config.scales[0].unit;

        var secondUnitIndex = getUnitIndex(secondUnit);

        var firstUnitIndex = getUnitIndex(unit);

        if (firstUnitIndex < secondUnitIndex) {
            changeZoomScale(1, unit);
        }
    }

    function onChangeButtomZoomScale(unit) {
        var firstUnit = gantt.config.scales[1].unit;

        var firstUnitIndex = getUnitIndex(firstUnit);

        var secondUnitIndex = getUnitIndex(unit);

        if (firstUnitIndex < secondUnitIndex) {
            changeZoomScale(0, unit);
        }
    }
    
    function onSelectParticipantFilter(e) {
    	
    	if (_ganttInitialized) {
        	_selectedParticipantFilterName = e.text;
        	gantt.render();
    	}
    }
    
    function onTaskNameFilter(v) {
    	
    	if (_ganttInitialized) {
        	_taskNameFilterName = v;
        	gantt.render();
    	}
    }
    
    function onUnselectParticipantFilter(e) {
    	console.log(e);
    }

    function getUnitIndex(unit) {

        for (var i = 0; i < _units.length; i++) {
            var u = _units[i];

            if (unit === u) {
                return i;
            }
        }

        return -1;
    }

    function changeZoomScale(index, unit) {

        if ('year' == unit) {
            gantt.config.scales[index] = {
                unit: "year",
                step: 1,
                format: "%Y"
            }
        } else if ('half_year' == unit) {
            gantt.config.scales[index] = {
                unit: "half_year",
                step: 1,
                template: formatHalfYearLabel
            }
        } else if ('quarter' == unit) {
            gantt.config.scales[index] = {
                unit: "quarter",
                step: 1,
                template: formatYearAndQuarterYearLabel
            }
        } else if ('month' == unit) {
            gantt.config.scales[index] = {
                unit: "month",
                step: 1,
                format: "%Y-%m"
            }
        } else if ('week' == unit) {
            gantt.config.scales[index] = {
                unit: "week",
                step: 1,
                format: "%d"
            }
        } else if ('day' == unit) {

            var dayFormat = "%Y-%m-%d";

            if (0 == index) {
                dayFormat = "%d"
            }

            gantt.config.scales[index] = {
                unit: "day",
                step: 1,
                format: dayFormat
            }
        } else if ('hour' == unit) {
            gantt.config.scales[index] = {
                unit: "hour",
                step: 1,
                format: "%H"
            }
        }

        gantt.render();
    }

    function formatWeekendStyle(unit, date) {
        var calendar_id = _ganttData.workTimeBean.id;

        var calendar = gantt.getCalendar(calendar_id);

        if (calendar) {
            if (!calendar.isWorkTime(date, unit)) {
                return "weekend";
            }
        }
    }

    function daysStyle(date) {
        if (date.getDay() == 0 || date.getDay() == 6) {
            return "weekend";
        }
    }
    // 月从0开始，下标是6时表示7月（July）,day从1开始
    var _firstMonth = 0, _halfMonth = 6, _firstDay = 1;
    // 格式化表头-半年
    function formatHalfYearLabel(date) {
        var next = new Date(date);

        var halfStr = 'H';

        if (next.getMonth() < _halfMonth) {
            halfStr = halfStr + '1';
        } else {
            halfStr = halfStr + '2';
        }

        return halfStr;
    }

    gantt.date.half_year_start = function(date) {
        var next = new Date(date);

        if (next.getMonth() < _halfMonth) {
            next.setMonth(_firstMonth);
        } else {
            next.setMonth(_halfMonth);
        }

        next.setDate(_firstDay);

        return next;
    };

    gantt.date.add_half_year = function(date, inc) {
        return gantt.date.add(date, _halfMonth, "month");
    };

    function formatQuarterYearLabel(date) {
        var d = new Date(date);

        var quarterStr = 'Q';

        var month = d.getMonth();

        if (month < 3) {
            quarterStr = quarterStr + '1';
        } else if (month > 2 && month < 6) {
            quarterStr = quarterStr + '2';
        } else if (month > 5 && month < 9) {
            quarterStr = quarterStr + '3';
        } else {
            quarterStr = quarterStr + '4';
        }

        return quarterStr;
    }

    function formatYearAndQuarterYearLabel(date) {

        var qStr = formatQuarterYearLabel(date);

        var d = new Date(date);

        var year = d.getFullYear();

        return year + ',' + qStr;
    }

    function datetimeParser(s) {
        // IE和Firefox下Date.parse出现NaN问题解决 IE 只支持 2001/01/01
        if (s) {
            s = s.replace(/-/g, "/");
        }
        var t = Date.parse(s);
        if (!isNaN(t)) {
            return new Date(t);
        } else {
            return new Date();
        }
    }

    var lightboxWidget = {};

    function getWidget(name, isCheckbox) {

        if (!lightboxWidget[name]) {
            if (isCheckbox) {
                lightboxWidget[name] = $taskEditorDialog.find('input[name="' + name + '"]');
            } else {
                lightboxWidget[name] = $taskEditorDialog.find('[textboxname="' + name + '"]');
            }
        }

        return lightboxWidget[name];
    }

    function saveGanttLightbox() {

        if (!$('#_TaskEditorNormalForm').form('validate')) {
            $('#_TaskEditorTabs').tabs('select', 0);
            return;
        }
        
        var firstTask = gantt.getTaskByIndex(0);
        // 创建修改第一条任务 任务类型和交付物必填
        if (firstTask.id == this.currentEditTaskId) {
        	var taskType = $("input[name=taskType]").val();
        	var achievement = $("input[name=achievement]").val();
        	if (!taskType) {
        		gantt.alert("任务类型必填！");
                return;
        	}
        	if (!achievement) {
        		gantt.alert("计划交付物必填！");
                return;
        	}
        }
        
        if (!gantt.isTaskExists(this.currentEditTaskId)) {
            return;
        }

        var cells = $linkdatagrid.datagrid('getSelectedCells');

        if (cells.length > 0) {
            for ( var c in cells) {
                var cell = cells[c];
                $linkdatagrid.datagrid('endEdit', cell.index);
            }
        }

        var task = gantt.getTask(this.currentEditTaskId);

        task.text = this.getWidget('taskName').textbox('getValue');
        task.taskType = this.getWidget('taskType').textbox('getValue');
        task.achievement = this.getWidget('achievement').textbox('getValue');
        task.delivery = this.getWidget('delivery').textbox('getValue');
        task.progress = this.getWidget('progress').textbox('getValue') / 100;

        task.duration = this.getWidget('durationDays').numberspinner('getValue');
        task.remark = this.getWidget('remark').textbox('getValue');

        var startDate = this.getWidget('startDate').datebox('getValue');
        var endDate = this.getWidget('endDate').datebox('getValue');
        task.start_date = ScheduleProgressGantt.str_to_date(startDate);
        task.end_date = ScheduleProgressGantt.str_to_date(endDate);

        task.workload = this.getWidget('workload').numberspinner('getValue');
        task.weight = this.getWidget('weight').numberspinner('getValue');

        task.leader = this.getWidget('leader').combobox('getValue');
        task.participants = _selectedParticipants.map(p => p.text).join(',');
        task.participantId = _selectedParticipants.map(p => p.id).join(',');
        task.participantName = task.participants;
        console.log('确认后', task.participantName, task.participantId, _selectedParticipants);
        _selectedParticipants = [];
        
        // 前置任务links
        var rows = $linkdatagrid.datagrid('getRows');
        // 先删除再添加
        var linkIds = task.$target;

        if (linkIds.length > 0) {
            for ( var l in linkIds) {
                gantt.deleteLink(linkIds[l]);
            }
        }

        if (rows.length > 0) {

            for ( var r in rows) {
                var row = rows[r];

                if (!row.id) {
                    continue;
                }

                var _task = gantt.getTaskByWBSCode(row.id);

                if (_task) {
                    var _link = {
                        id: MainUtils.guid(),
                        source: _task.id,
                        target: this.currentEditTaskId,
                        type: String(row.linkType),
                        lag: parseInt(row.lagDays)
                    }

                    if (gantt.isLinkAllowed(_link)) {
                        gantt.addLink(_link);
                    }
                }
            }
        }

        // 高级选项
        var constraintTypeValue = this.getWidget('constraintType').combobox('getValue');

        if (constraintTypeValue) {
            task.constraint_type = constraintTypeValue;

            var constraintDateValue = this.getWidget('constraintDate').datebox('getValue');

            if (constraintDateValue && '' != constraintDateValue) {
                var _date = ScheduleProgressGantt.str_to_date(constraintDateValue);

                task.constraint_date = _date;
            } else {
                if ('mso' == constraintTypeValue || 'snet' == constraintTypeValue
                        || 'snlt' == constraintTypeValue) {
                    task.constraint_date = task.start_date;
                } else if ('mfo' == constraintTypeValue || 'fnet' == constraintTypeValue
                        || 'fnlt' == constraintTypeValue) {
                    task.constraint_date = task.end_date;
                }
            }
        }

        var deadlineDateValue = this.getWidget('deadlineDate').datebox('getValue');

        if (deadlineDateValue && '' != deadlineDateValue) {
            task.deadline_date = ScheduleProgressGantt.str_to_date(deadlineDateValue);
        } else {
            task.deadline_date = null;
        }

        var isSummary = this.getWidget('isSummary').prop('checked');
        var isMilestone = this.getWidget('isMilestone').prop('checked');
        var isCritical = this.getWidget('isCritical').prop('checked');
        var isSiteTask = this.getWidget('isSiteTask').prop('checked');

        task.type = 'task';

        if (isSummary) {
            task.type = 'project';
            task.isSummary = 1;
        } else {
            task.isSummary = 0;
        }

        if (isMilestone) {
            task.type = 'milestone';
            task.isMilestone = 1;
        } else {
            task.isMilestone = 0;
        }

        if (isCritical) {
            task.type = 'task';
            task.isCritical = 1;
        } else {
            task.isCritical = 0;
        }

        if (isSiteTask) {
            task.type = 'task';
            task.isSiteTask = 1;
        } else {
            task.isSiteTask = 0;
        }

        if (task.$new) {
            delete task.$new;
        }

        gantt.updateTask(task.id);
        gantt.autoSchedule(task.id);
        gantt.hideLightbox();
    }

    function closeGanttLightbox() {
        gantt.hideLightbox();
    }

    function onChangeForEnddate(newValue, oldValue) {

        if (newValue == oldValue || oldValue == '') {
            return;
        }

        var startDate = ScheduleProgressGantt.getWidget('startDate').datebox('getValue');
        var duration = ScheduleProgressGantt.getWidget('durationDays').numberspinner('getValue');

        if (startDate && duration) {

            var calendar = gantt.getCalendar(_ganttData.workTimeBean.id);

            if (calendar) {

                var end_date = calendar.calculateEndDate(ScheduleProgressGantt
                        .str_to_date(startDate), parseInt(duration));

                ScheduleProgressGantt.getWidget('endDate').datebox('setValue',
                        ScheduleProgressGantt.date_to_str(end_date));
            }
        }
    }

    function onChangeForDuration(newValue, oldValue) {

        if (newValue == oldValue || oldValue == '') {
            return;
        }

        var startDate = ScheduleProgressGantt.getWidget('startDate').datebox('getValue');
        var endDate = ScheduleProgressGantt.getWidget('endDate').datebox('getValue');

        if (startDate && endDate) {

            var calendar = gantt.getCalendar(_ganttData.workTimeBean.id);

            if (calendar) {

                var duration = calendar.calculateDuration({
                    start_date: ScheduleProgressGantt.str_to_date(startDate),
                    end_date: ScheduleProgressGantt.str_to_date(endDate)
                });

                ScheduleProgressGantt.getWidget('durationDays').numberspinner('setValue', duration);
            }
        }
    }

    var newTaskName = '<新增任务>';

    function newTaskDialog() {
    	
    	_selectedParticipants = [];
    	
        var taskId = gantt.getLastSelectedTask();

        var uid = MainUtils.guid();
        var parent = '0';
        var start_date = null;

        var firstTask = gantt.getTaskByIndex(0);
        
        if (firstTask) {
            start_date = firstTask.start_date;
        } else {
            start_date = gantt.config.start_date;
        }

        if (taskId) {

            var task = gantt.getTask(taskId);
            parent = task.parent;
            start_date = task.start_date;
        }

        gantt.addTask({
            id: uid,
            text: newTaskName,
            start_date: start_date,
            duration: 1,
            progress: 0,
            $new: 'new',
            calendar_id: _ganttData.workTimeBean.id
        }, parent, null);

        gantt.unselectTask();
        gantt.selectTask(uid);
        gantt.showLightbox(uid);
        // gantt.showLightbox();
    }

    function editTaskDialog() {
    	
    	_selectedParticipants = [];

        var selectTaskId = _selectTaskIdList;

        if (!selectTaskId) {
            gantt.alert("请先选择任务！");
            return;
        }
        
        if (_selectTaskIdList.length != 1) {
            gantt.alert("请选择一条任务！");
            return;
        }
        
        console.log('editTaskDialog');

        gantt.showLightbox(selectTaskId);
    }

    function deleteTaskDialog() {

        var selectTasks = _selectTaskIdList;

        if (selectTasks.length == 0) {
            gantt.alert("请先选择任务！");
            return;
        }

        gantt.confirm({
            text: "确定要删除任务?",
            ok: "是",
            cancel: "否",
            callback: function(result) {
                if (result) {
                    for ( var i in selectTasks) {
                        if (gantt.isTaskExists(selectTasks[i])) {
                            gantt.deleteTask(selectTasks[i]);
                        }
                    }
                }
            }
        });
    }

    function findMainTaskName(taskId) {

        var parentId = gantt.getParent(taskId);
        if (0 == parentId) {
          var mainTask = gantt.getTask(taskId);
          return mainTask.text;
        } else {
          var mainTask = gantt.getTask(parentId);
          return mainTask.text;
        }
    }
    
    function onRemoveAttachment(index, taskId) {
    	$attachments = $('#attachments').children()[index].remove();
    	_tempAttachmentList.splice(index, 1);
    	generateAttachmentList();
    }
    
    function generateAttachmentList() {
    	
    	$attachments = $('#attachments');
        $attachments.empty();
        
        console.log('generateAttachmetnList', _tempAttachmentList);
        
        for (let i = 0; i < _tempAttachmentList.length; i++) {
        	const attachment = _tempAttachmentList[i];
        	$attachments.append(
        		'<div style="margin-bottom: 12px">' +
        			'<span class="remove-attachment-link" data-index="' + i + '" style="cursor:pointer; text-decoration: underline; font-style: italic; margin-right: 8px;">删除</span>' +
        			'<span style="padding: 4px; background-color: #DDD; border-radius: 4px; margin-right: 12px">' + ('附件' + (i + 1) + ':&nbsp;') + '</span>' +
        			'<span style="cursor:pointer; text-decoration: underline; font-style: italic" onclick="MainUtils.downloadFile(\'/project-edge/archive/project-archive/download.json?idsToDownload=' + attachment.id + '\')">' + attachment.archiveName + '</span>' +
        		'</div>'
        	);
        }
        
        $('.remove-attachment-link').bind('click', function(e) {
    		console.log('删除被点击', e.target.getAttribute("data-index"))
    		onRemoveAttachment(e.target.getAttribute("data-index"));
    	});
    }

    function openEditReportProgressDialog(ganttTaskId) {
        
        if (!ganttTaskId) {
            if (_selectTaskIdList.length != 1) {
                gantt.alert("请选择一条任务！");
                return;
            }
        }

        var $dialog = $('#_EditorReportProgressDialog');
        var $form = $dialog.find('form');

        function process(isFirstLoad) {

            $form.form('reset');
            var task = null;
            if (ganttTaskId) {
                task = gantt.getTask(ganttTaskId);
            } else {
                task = gantt.getTask(_selectTaskIdList[0]);
            }
            
            // 生成附件列表
            console.log('process', _tempAttachmentList, task.attachmentList);
            if (task.attachmentList && task.attachmentList.length > 0) {
            	_tempAttachmentList = [...task.attachmentList];
            	generateAttachmentList();
            } else {
            	_tempAttachmentList = [];
            }
            

            $dialog.find('[textboxname="taskName"]').textbox('setValue', task.text);
            $dialog.find('[textboxname="startDate"]').textbox('setValue',
                    ScheduleProgressGantt.date_to_str(task.start_date));
            $dialog.find('[textboxname="endDate"]').textbox('setValue',
                    ScheduleProgressGantt.date_to_str(task.end_date));
            $dialog.find('[textboxname="leader"]').combobox('setValue', task.leader);
            $dialog.find('[textboxname="weight"]').textbox('setValue', task.weight);
            $dialog.find('[textboxname="taskType"]').textbox('setValue', task.taskType);
            $dialog.find('[textboxname="achievement"]').textbox('setValue', task.achievement);
            $dialog.find('[textboxname="delivery"]').textbox('setValue', task.delivery);

            $dialog.find('input[name="id"]').val(task.id)
            
            if (task.participantName) {
            	$dialog.find('[textboxname="participantsName"]').textbox('setValue', task.participantName);
            }

            if (task.progress) {
                var _value = Math.floor(task.progress);
                $dialog.find('[textboxname="progress"]').numberspinner('setValue', _value);
            }
            
            // 如果已经完成，才可以更新和编辑附件及实际交付物
            if (task.progress && task.progress == 1) {
            	$dialog.find('[textboxname="delivery"]').textbox('readonly', false);
//            	$('#attachment-upload-btn').attr("disabled", false);
            	
            	
            } else {
            	$dialog.find('[textboxname="delivery"]').textbox('readonly', true);
//            	$('#attachment-upload-btn').attr("disabled", true);
            }
            
            $('#attachment-upload-input').bind('change', function(e) {

        		// TODO
        		const files = e.target.files;
        		console.log(files);
        		if (files.length > 0) {
        			$('#attachment-upload-file-name').text(files[0].name);
        		}
        		
        	});
        	$('#attachment-upload-btn').bind('click', function() {
        		console.log('上传Button被点击')
        		$('#attachment-upload-input').click()
        	});
            
            if (task.lastProgress) {
            	var _value = Math.floor(task.lastProgress);
            	$dialog.find('[textboxname="lastProgress"]').numberspinner('setValue', _value);
            }

            if (task.actualStartDate) {
                $dialog.find('[textboxname="actualStartDate"]').datebox('setValue',
                        task.actualStartDate);
            }

            if (task.actualEndDate) {
                $dialog.find('[textboxname="actualEndDate"]').datebox('setValue',
                        task.actualEndDate);
            }

            if (task.flowStatus) {
                $dialog.find('[textboxname="flowStatus"]').combobox('setValue', task.flowStatus);
            }

            if (task.issueType) {
                $dialog.find('[textboxname="issueType"]').combobox('setValue', task.issueType);
            }

            if (task.cumulativeProgress) {
                $dialog.find('[textboxname="cumulativeProgress"]').textbox('setValue',
                        task.cumulativeProgress);
            }

            if (task.currentWeekProgress) {
                $dialog.find('[textboxname="currentWeekProgress"]').textbox('setValue',
                        task.currentWeekProgress);
            }

            if (task.description) {
                $dialog.find('[textboxname="description"]').textbox('setValue', task.description);
            }

            if (task.nextWeekGoals) {
                $dialog.find('[textboxname="nextWeekGoals"]').textbox('setValue',
                        task.nextWeekGoals);
            }

            $dialog.dialog('open').dialog('center').scrollTop(0);

            $form.form('disableValidation');
            
            
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
    
    /**
     * 点击工作量评估
     */
    function openWorkloadDialog() {
    	
    	if (_selectTaskIdList.length == 0) {
            gantt.alert("请先选择任务！");
            return;
        }
    	
    	_briefType = 1;
    	var $dialog = $('#_ReportProgressDialog');
    	$dialog.dialog({ title: '个人简报' });
    	$('#progressRemark').text('补充说明');

		

        function process(isFirstLoad) {

            $reportProgressForm.form('reset');

            $reportProgressGrid.datagrid('loadData', []);

            var tempTasks = [];

            for ( var i in _selectTaskIdList) {
                var task = gantt.getTask(_selectTaskIdList[i]);

                var mainTaskName = findMainTaskName(task.id);

                var subTaskName = task.text;

                tempTasks.push({
                    'id': MainUtils.guid(),
                    'planTask_': task.id,
                    'mainTaskName': mainTaskName,
                    'subTaskName': task.text,
                    'lastProgress': task.lastProgress,
                    'progress': task.progress,
                    'actualStartDate': task.actualStartDate,
                    'actualEndDate': task.actualEndDate,
                    'flowStatus': task.flowStatus,
                    'cumulativeProgress': task.cumulativeProgress,
                    'currentWeekProgress': task.currentWeekProgress,
                    'issueType': task.issueType,
                    'description': task.description,
                    'nextWeekGoals': task.nextWeekGoals
                });
            }

            $reportProgressGrid.datagrid('loadData', tempTasks);

            $dialog.dialog('open').dialog('center').scrollTop(0);

            $reportProgressForm.form('disableValidation');
            $("#_ReportProgressDialog .datagrid-view2 .datagrid-body").css("overflow", "auto");

			$reportProgressForm.find('[textboxname="progressDate"]').datebox('setValue', moment().format('YYYY-MM-DD'));
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

    function openReportProgressDialog() {

        if (_selectTaskIdList.length == 0) {
            gantt.alert("请先选择任务！");
            return;
        }

        _briefType = 0;
        var $dialog = $('#_ReportProgressDialog');
        $dialog.dialog({ title: '工作组简报' });
    	$('#progressRemark').text('专业成效');

        function process(isFirstLoad) {

            $reportProgressForm.form('reset');

            $reportProgressGrid.datagrid('loadData', []);

            var tempTasks = [];

            for ( var i in _selectTaskIdList) {
                var task = gantt.getTask(_selectTaskIdList[i]);

                var mainTaskName = findMainTaskName(task.id);

                var subTaskName = task.text;

                tempTasks.push({
                    'id': MainUtils.guid(),
                    'planTask_': task.id,
                    'mainTaskName': mainTaskName,
                    'subTaskName': task.text,
                    'lastProgress': task.lastProgress,
                    'progress': task.progress,
                    'actualStartDate': task.actualStartDate,
                    'actualEndDate': task.actualEndDate,
                    'flowStatus': task.flowStatus,
                    'cumulativeProgress': task.cumulativeProgress,
                    'currentWeekProgress': task.currentWeekProgress,
                    'issueType': task.issueType,
                    'description': task.description,
                    'nextWeekGoals': task.nextWeekGoals
                });
            }

            $reportProgressGrid.datagrid('loadData', tempTasks);

            $dialog.dialog('open').dialog('center').scrollTop(0);

            $reportProgressForm.form('disableValidation');
            $("#_ReportProgressDialog .datagrid-view2 .datagrid-body").css("overflow", "auto");

			$reportProgressForm.find('[textboxname="progressDate"]').datebox('setValue', moment().format('YYYY-MM-DD'));
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

    function saveEditReportProgressDataToDB(dialogId) {
        if (!dialogId) {
            return;
        }

        var $dialog = $(dialogId);
        var $form = $dialog.find('form');

        $form.form('enableValidation');

        if (!$form.form('validate')) {
            return;
        }

        var actualStartDateValue = $dialog.find('[textboxname="actualStartDate"]').datebox(
                'getValue');

        var actualEndDateValue = $dialog.find('[textboxname="actualEndDate"]').datebox('getValue');

        var _startTime = ScheduleProgressGantt.str_to_date(actualStartDateValue);
        
        // 如果填写了结束时间，比较开始和结束时间
        if (actualEndDateValue) {
        	var _endTime = ScheduleProgressGantt.str_to_date(actualEndDateValue);
            if (_startTime.getTime() > _endTime.getTime()) {
                gantt.alert("实际开始日期不能大于实际结束日期！");
                $dialog.find('[textboxname="actualEndDate"]').datebox('setValue', null);
                return;
            }
        }
        
        // 如果有进度，要比较是否大于等于前期进度
        var progress = $dialog.find('[textboxname="progress"]').numberspinner('getValue');
        var lastProgress = $dialog.find('[textboxname="lastProgress"]').numberspinner('getValue');
        /*if (progress && lastProgress) {
        	if (parseInt(progress) < parseInt(lastProgress)) {
        		gantt.alert('进度要大于等于上期进度');
        		return;
        	}
        }*/

        var issueTypeValue = $dialog.find('[textboxname="issueType"]').combobox('getValue');
        var descriptionValue = $dialog.find('[textboxname="description"]').textbox('getValue');

        if (issueTypeValue) {
            if (!descriptionValue) {
                gantt.alert("请填写计划差异/偏离/问题描述！");
                return;
            }
        }

        if (descriptionValue) {
            if (!issueTypeValue) {
                gantt.alert("请选择问题类型！");
                return;
            }
        }

        var task = {};

        task.id = $dialog.find('input[name="id"]').val();

        task.progress = $dialog.find('[textboxname="progress"]').numberspinner('getValue');
        task.actualStartDate = actualStartDateValue;
        task.actualEndDate = actualEndDateValue;

        task.delivery = $dialog.find('[textboxname="delivery"]').textbox('getValue');
        task.flowStatus = $dialog.find('[textboxname="flowStatus"]').combobox('getValue');

        task.cumulativeProgress = $dialog.find('[textboxname="cumulativeProgress"]').textbox(
                'getValue');
        task.currentWeekProgress = $dialog.find('[textboxname="currentWeekProgress"]').textbox(
                'getValue');

        task.issueType = issueTypeValue;
        task.description = descriptionValue;

        task.nextWeekGoals = $dialog.find('[textboxname="nextWeekGoals"]').textbox('getValue');
        
        if (_tempAttachmentList) {
        	task.reservedAttachmentList = _tempAttachmentList.map(a => a.id);
        }
        
        const formData = new FormData();
        const files = $('#attachment-upload-input')[0].files;
        for (let i = 0; i < files.length; i++) {
        	formData.append('file', files[i]);
        }
        formData.append('task', JSON.stringify(task));
        
        var options = {
            url: BASE_URL + '/schedule/plan-task/save-edit-report-progress-data.json?randomNum='
                    + Math.random(),
            type: 'POST',
            data: formData,
            dataType: 'json',
            contentType: false,
            processData: false,
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                if (MainUtils.processJsonResult(response, true)) {

                    if (response.dataMap.returnObj) {
                        var ganttTaskBean = response.dataMap.returnObj;

                        var ganttTask = gantt.getTask(task.id);
                        ganttTask.lastProgress = ganttTask.lastProgress;

                        ganttTask.progress = ganttTaskBean.progress;
                        ganttTask.flowStatus = ganttTaskBean.flowStatus;

                        ganttTask.actualStartDate = ganttTaskBean.actualStartDate;
                        ganttTask.actualEndDate = ganttTaskBean.actualEndDate;
                        ganttTask.cumulativeProgress = ganttTaskBean.cumulativeProgress;
                        ganttTask.currentWeekProgress = ganttTaskBean.currentWeekProgress;
                        ganttTask.issueType = ganttTaskBean.issueType;
                        ganttTask.description = ganttTaskBean.description;
                        
                        ganttTask.delivery = ganttTaskBean.delivery;

                        ganttTask.nextWeekGoals = ganttTaskBean.nextWeekGoals;
                        ganttTask.attachmentList = ganttTaskBean.attachmentList;

                        gantt.updateTask(task.id);
                        
                        $('#attachment-upload-file-name').text('');
                    }
                    $dialog.dialog('close');
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);// jquery.form plugin
        MainUtils.openLoading();// 弹出loading提示
    }

    function saveReportProgressData() {

        $reportProgressForm.form('enableValidation');

        if (!$reportProgressForm.form('validate')) {
            return;
        }

        // 关闭编辑模式
        var progressData = $reportProgressGrid.datagrid('getData');

        for (var i = 0; i < progressData.rows.length; i++) {
            var isEditing = $reportProgressGrid.datagrid('isEditing', i);

            if (isEditing) {
                $reportProgressGrid.datagrid('endEdit', i);
            }
        }

        for (var i = 0; i < progressData.rows.length; i++) {

            var row = progressData.rows[i];

            var progress = row.progress;
            var actualStartDate = row.actualStartDate;
            var actualEndDate = row.actualEndDate;
            var flowStatus = row.flowStatus;
            
            var progress = row.progress;
            var lastProgress = row.lastProgress;

            var cumulativeProgress = row.cumulativeProgress;
            var currentWeekProgress = row.currentWeekProgress;
            var description = row.description;
            var nextWeekGoals = row.nextWeekGoals;

            // 验证是否填写完整
            if (!progress || !actualStartDate || !flowStatus) {
                $reportProgressGrid.datagrid('beginEdit', i);
                return;
            }
            
            // 验证进度是否大于等于上期进度
            if (progress && lastProgress) {
            	if (parseInt(progress) < parseInt(lastProgress)) {
            		gantt.alert("进度需大于等于上期进度");
                    $reportProgressGrid.datagrid('gotoCell', {
                        index: i,
                        field: 'progress'
                    });
            		return;
            	}
            }

            if (flowStatus == 4 && !actualEndDate) {
                gantt.alert("请填写实际结束时间！");
                $reportProgressGrid.datagrid('gotoCell', {
                    index: i,
                    field: 'actualEndDate'
                });
                return;
            }
            

            if (actualEndDate) {
                var _startTime = ScheduleProgressGantt.str_to_date(actualStartDate);
                var _endTime = ScheduleProgressGantt.str_to_date(actualEndDate);

                if (_startTime.getTime() > _endTime.getTime()) {
                    gantt.alert("实际开始日期不能大于实际结束日期！");
                    $reportProgressGrid.datagrid('gotoCell', {
                        index: i,
                        field: 'actualEndDate'
                    });
                    return;
                }
            }
        }

        var planProgressBean = {
            'plan_': _ganttData.id,
            'progressDate': $reportProgressForm.find('[textboxname="progressDate"]').datebox(
                    'getValue'),
            'progressName': $reportProgressForm.find('[textboxname="progressName"]').textbox(
                    'getValue'),
            'dateType': $reportProgressForm.find('[textboxname="dateType"]').combobox('getValue'),
            'remark': $reportProgressForm.find('[textboxname="remark"]').textbox('getValue'),
            'description': $reportProgressForm.find('[textboxname="description"]').textbox('getValue'),
            'reportType': _briefType == 0 ? 1 : 5
        };

        var planProgressTaskBeans = [];

        for ( var r in progressData.rows) {
            var row = progressData.rows[r];

            var planProgressTaskBean = {
                'planTask_': row.planTask_,
                'actualStartDate': row.actualStartDate,
                'actualEndDate': row.actualEndDate,
                'progress': row.progress,
                'lastProgress': row.lastProgress,
                'cumulativeProgress': row.cumulativeProgress,
                'currentWeekProgress': row.currentWeekProgress,
                'description': row.description,
                'nextWeekGoals': row.nextWeekGoals,
                'flowStatus': row.flowStatus,
                'issueType': row.issueType
            };

            planProgressTaskBeans.push(planProgressTaskBean);
        }

        planProgressBean.planProgressTaskBeans = planProgressTaskBeans;

        var options = {
            url: BASE_URL + '/schedule/plan-task/save-plan-progress-data.json?randomNum='
                    + Math.random(),
            type: 'POST',
            data: JSON.stringify(planProgressBean),
            dataType: 'json',
            contentType: 'application/json',
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                if (MainUtils.processJsonResult(response, true)) {

                    if (response.dataMap.returnObj) {
                    	
                    	isModify = _ganttData.isModify;
                        _ganttData = response.dataMap.returnObj;
                        _ganttData.isModify = isModify;

                        var tasks = [];

                        for ( var d in _ganttData.taskBeans) {
                            var tempTask = _ganttData.taskBeans[d];

                            if (tempTask.deadline_date) {
                                tempTask.deadline_date = ScheduleProgressGantt
                                        .str_to_date(tempTask.deadline_date);
                            }
                        }

                        var _data = {
                            data: _ganttData.taskBeans,
                            links: _ganttData.linkBeans
                        };

                        gantt.parse(_data);
                        
                        console.log('updateGanttSelect', isModify, _ganttData.taskBeans)
                        if (2 == isModify) {
                        	_ganttData.taskBeans.forEach(bean => {
                        		gantt.unselectTask(bean.id);
                        		if (bean.hasProgress == true) {
                        			$(".gantt_row[task_id='" + bean.id + "']").find("input[type='checkbox']").prop("checked", true);
                        			_selectTaskIdList.push(bean.id);
//                        			gantt.selectTask(bean.id);
                        		}
                        	});
                        }

                    }
                    $reportProgressDialog.dialog('close');
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);// jquery.form plugin
        MainUtils.openLoading();// 弹出loading提示

    }

    function handlerNextSameLevelTask(taskId, taskIds) {

        var nextId = gantt.getNextSibling(taskId);

        if (nextId) {
            taskIds.push(nextId);
            handlerNextSameLevelTask(nextId, taskIds);
        }
    }

    function upLevel() {
        var selectTaskId = gantt.getLastSelectedTask();

        if (!selectTaskId) {
            gantt.alert("请先选择任务！");
            return;
        }

        // 顶层不变
        if (0 == gantt.getParent(selectTaskId)) {
            return;
        }

        var taskIds = [];

        handlerNextSameLevelTask(selectTaskId, taskIds);

        if (taskIds.length > 0) {
            for (var i = 0; i < taskIds.length; i++) {
                gantt.moveTask(taskIds[i], i, selectTaskId);
            }
        }

        var parentId = gantt.getParent(selectTaskId);

        gantt.moveTask(selectTaskId, gantt.getTaskIndex(parentId) + 1, gantt.getParent(parentId));
        gantt.open(selectTaskId);

        var task = gantt.getTask(selectTaskId);

        if (gantt.hasChild(selectTaskId)) {
            task.type = 'project';
            task.isSummary = 1;
        } else {
            task.type = 'task';
            task.isSummary = 0;
        }
        gantt.updateTask(selectTaskId);

        var preTaskId = gantt.getPrev(selectTaskId);

        if (preTaskId) {

            var preTask = gantt.getTask(preTaskId);

            if (gantt.hasChild(preTaskId)) {

                preTask.type = 'project';
                preTask.isSummary = 1;
            } else {
                preTask.type = 'task';
                preTask.isSummary = 0;
            }

            gantt.updateTask(preTaskId);
        }
    }

    function downLevel() {
        var selectTaskId = gantt.getLastSelectedTask();

        if (!selectTaskId) {
            gantt.alert("请先选择任务！");
            return;
        }

        var preTaskId = gantt.getPrev(selectTaskId);

        if (preTaskId) {

            var currentTask = gantt.getTask(selectTaskId);

            if (currentTask.parent == preTaskId) {
                return;
            }

            gantt.moveTask(selectTaskId, 0, preTaskId);
            gantt.open(preTaskId);

            var task = gantt.getTask(preTaskId);

            task.type = 'project';
            task.isSummary = 1;
            gantt.updateTask(preTaskId);
        }
    }

    function moveUp() {
        var selectTaskId = gantt.getLastSelectedTask();

        if (!selectTaskId) {
            gantt.alert("请先选择任务！");
            return;
        }

        var preTaskId = gantt.getPrevSibling(selectTaskId);
        if (preTaskId) {

            gantt.moveTask(selectTaskId, gantt.getTaskIndex(preTaskId), gantt
                    .getParent(selectTaskId));
        }
    }

    function moveDown() {
        var selectTaskId = gantt.getLastSelectedTask();

        if (!selectTaskId) {
            gantt.alert("请先选择任务！");
            return;
        }

        var nextTaskId = gantt.getNextSibling(selectTaskId);

        if (nextTaskId) {
            gantt.moveTask(selectTaskId, gantt.getTaskIndex(nextTaskId), gantt
                    .getParent(selectTaskId))
        }
    }

    function addFavorite() {
        var selectTasks = _selectTaskIdList;
        if (selectTasks.length == 0) {
            gantt.alert("请先选择任务！");
            return;
        }
        console.log(selectTasks)
        var url = BASE_URL + '/schedule/plan-task/concern.json';
        MainUtils.openLoading();// 弹出loading提示
        var options = {
            url: url,
            type: 'POST',
            data: {
                idsToConcern: selectTasks.join()
            },
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                if (MainUtils.processJsonResult(response, true)) {
                }
            },
            error: MainUtils.handleAjaxFormError
        };


        gantt.confirm({
            text: "确定要关注任务?",
            ok: "是",
            cancel: "否",
            callback: function(result) {
                if (result) {
                    $.ajax(options);
                }
            }
        });
    }

    function delFavorite() {
        var selectTasks = _selectTaskIdList;
        if (selectTasks.length == 0) {
            gantt.alert("请先选择任务！");
            return;
        }
        console.log(selectTasks)
        var url = BASE_URL + '/schedule/plan-task/no-concern.json';
        MainUtils.openLoading();// 弹出loading提示
        var options = {
            url: url,
            type: 'POST',
            data: {
                idsToNoConcern: selectTasks.join()
            },
            dataType: 'json',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                if (MainUtils.processJsonResult(response, true)) {
                }
            },
            error: MainUtils.handleAjaxFormError
        };


        gantt.confirm({
            text: "确定取消关注任务?",
            ok: "是",
            cancel: "否",
            callback: function(result) {
                if (result) {
                    $.ajax(options);
                }
            }
        });
    }

    function addLink() {
        $linkdatagrid.datagrid('appendRow', {
            'id': null,
            'linkType': '0',
            'lagDays': 0
        });
    }

    function deleteLink() {

        var cells = $linkdatagrid.datagrid('getSelectedCells');

        if (cells.length > 0) {
            $linkdatagrid.datagrid('deleteRow', cells[0].index);
        }
    }

    function addExceptionDate() {
        $calendarExceptionGrid.datagrid('appendRow', {
            'id': MainUtils.guid()
        })
    }

    function deleteExceptionDate() {
        var cells = $calendarExceptionGrid.datagrid('getSelectedCells');

        if (cells.length > 0) {
            $calendarExceptionGrid.datagrid('deleteRow', cells[0].index);
        }
    }

    function onChangeLinkId(newValue, oldValue) {

        if (!newValue) {
            return;
        }
        var cells = $linkdatagrid.datagrid('getSelectedCells');

        if (cells.length > 0) {

            var cell = cells[0];

            var index = cell.index;

            if (!$linkdatagrid.datagrid('isEditing', index)) {
                return;
            }

            var task = gantt.getTaskByWBSCode(newValue);

            if (task) {

                $linkdatagrid.datagrid('updateRow', {
                    index: index,
                    row: {
                        'id': task.$wbs,
                        'taskName': task.text,
                    }
                });
            } else {
                $linkdatagrid.datagrid('updateRow', {
                    index: index,
                    row: {
                        'id': null,
                        'taskName': null,
                    }
                });
            }
        }
    }

    var linkTypes = [{
        id: '0',
        text: '完成-开始 (FS)'
    }, {
        id: '1',
        text: '开始-开始 (SS)'
    }, {
        id: '2',
        text: '完成-完成 (FF)'
    }, {
        id: '3',
        text: '开始-完成 (SF)'
    }];

    function formatterLinkType(value, row, index) {
        if (0 == value || value) {

            var obj = linkTypes[value];

            if (obj) {
                return obj.text;
            }
        }
    }

    var dayUnit = '天';

    function formatterLagDays(value, row, index) {
        if (0 == value || value) {
            return value + dayUnit;
        }
    }

    function onOpenReportProgressDialog() {
        $reportProgressGrid.datagrid('enableCellEditing');
    }

    function onCloseReportProgressDialog() {
        $reportProgressGrid.datagrid('disableCellEditing');
    }

    function onOpenEditTaskDialog() {
        $linkdatagrid.datagrid('enableCellEditing');
    }

    function onCloseEditTaskDialog() {
        $linkdatagrid.datagrid('disableCellEditing');

        var task = gantt.getTask(ScheduleProgressGantt.currentEditTaskId);

        if (task.$new) {
            gantt.deleteTask(task.id);
           
        }
        
        _selectedParticipants = [];

        ScheduleProgressGantt.currentEditTaskId = null;
    }

    function closeCalendarDialog() {
        $calendarEditorDialog.dialog('close');
    }

    function onOpenEditCalendarDialog() {
        $calendarExceptionGrid.datagrid('enableCellEditing');
    }

    function onCloseEditCalendarDialog() {
        $calendarExceptionGrid.datagrid('disableCellEditing');
    }

    function onChangeConstraintTypes(newValue, oldValue) {

        // TODO
        if (!oldValue) {
            return;
        }

        if (newValue) {

            var $constraintDatebox = ScheduleProgressGantt.getWidget('constraintDate');

            if ('asap' == newValue || 'alap' == newValue) {
                $constraintDatebox.datebox('disable');
                $constraintDatebox.datebox('setValue', null);
            } else {
                $constraintDatebox.datebox('enable');
            }
        }
    }
    
    function initParticipants(ganttData) {
    	
    	const participants = [{ id: 0, text: '所有成员' }];
        for (var i = 0; i < ganttData.taskBeans.length; i++) {
            ganttData.taskBeans[i].$open = true;
            const participantName = ganttData.taskBeans[i].participantName;
            const participantId = ganttData.taskBeans[i].participantId;
            if (participantName && participantId) {
            	ganttData.taskBeans[i].participants = participantName;
            	
            	const names = participantName.split(',');
            	const ids = participantId.split(',');
            	
            	for (let j = 0; j < ids.length; j++) {
            		if (!participants.find(p => p.id == ids[j])) {
            			participants.push({ id: ids[j], text: names[j] });
            		}
            	}
            	
            }
            
            const leaderId = ganttData.taskBeans[i].leader;
            if (leaderId && ganttData.optionsMap.leaderOption) {
            	
            	if (!participants.find(p => p.id == leaderId)) {
            		const leader = ganttData.optionsMap.leaderOption.find(l => l.id == leaderId);
            		if (leader) {
            			participants.push({ id: leaderId, text: leader.text });
            		}
            	}
            }
        }
        
        console.log(participants);
        $('#_participantFilter').combobox({
        	data: participants,
        	valueField: 'id',
        	textField: 'text',
        });
    }

    function handlerInit(ganttData, isModify) {
        $('#_LinkButton_20').hide();
        $('#_LinkButton_21').hide();
        console.log(ganttData);
        // 展开所有任务;因为不知道数据来源，所以只能在方法内部将所有节点的$open属性设置为true.
        
        initParticipants(ganttData);
        
        gantt.attachEvent('onBeforeTaskDisplay', function(id, task) {
        	
//        	console.log(task.text, _taskNameFilterName, task.text.indexOf(_taskNameFilterName) < 0);
        	if (_taskNameFilterName != '' && task.text.indexOf(_taskNameFilterName) < 0) {
        		return false;
        	}
        	
        	if (!_selectedParticipantFilterName || _selectedParticipantFilterName == '所有成员') {
        		return true;
        	}
        	
        	// 如果不是参与人员，看是否是负责人
        	let filtered = task.participantName != null && task.participantName.indexOf(_selectedParticipantFilterName) >= 0;
        	if (filtered == false) {
        		const leader = ganttData.optionsMap.leaderOption.find(l => l.text == _selectedParticipantFilterName);
        		console.log(leader, task);
        		if (leader && leader.id == task.leader) {
        			filtered = true;
        		}
        	}
        	
        	return filtered;
        });
        
        // if (!_ganttData.id) {
        // return;
        // }

        ScheduleProgressGantt.optionsMap = ganttData.optionsMap;
        ScheduleProgressGantt.IssuesComboData = ScheduleProgressGantt.optionsMap['issueOption'];

        // gantt.config.multiselect = false;
        
        // 如果是比较任务，只纯展示数据变更，不展示甘特图，也不操作
        if (6 == isModify) {
        	$('#toolbar').hide();
        	
        	gantt.templates.grid_row_class = function(start, end, task) {
        		
        		if (task.compareStatus == 3) {
        			return 'task-compare-deleted';
        		} else if (task.compareStatus == 2) {
        			return 'task-compare-added';
        		} else if (getTaskUpdatedContents(task).length > 0) {
        			return 'task-compare-updated';
        		} else {
        			return '';
        		}
        	};
        	
        	gantt.templates.tooltip_text = function(start, end, task) {
        		
        		if (task.compareStatus == 3) {
        			return '<span>删除的任务</span>';
        		} else if (task.compareStatus == 2) {
        			return '<span>新增的任务</span>';
        		}
        		
        		const diffContents = getTaskUpdatedContents(task);
        		console.log(diffContents);
        		if (diffContents.length > 0) {
        			
        			let html = '<div>';
        			diffContents.forEach(content => {
        				html += '<div>' +
        						  '<span>' + content.name + ': </span>' +
        						  '<span>【' + (content.prev || '空值') + '】</span>' +
        						  '<span>&nbsp;修改为&nbsp;</span>' +
        						  '<span>【' + (content.next || '空值') + '】</span>' +
        					    '</div>';
        			});
        			html += '</div>';
        			
        			return html;
        		} else {
        			return '<span>没有变化</span>';
        		}
        	}
        }
        
        if (4 == isModify || 5 == isModify || 0 == isModify || 1 == isModify) {
        	$('#_LinkButton_upload').hide();
        	$('#_LinkButton_workload').hide();
        }

        if (1 == isModify || 4 == isModify) {
            $('#_LinkButton_12').hide();
            $('#_LinkButton_workload').hide();
            $('#_LinkButton_2_2').hide();

            if (4 == isModify) {
                $('#_LinkButton_8').hide();
                $('#_LinkButton_9').hide();
                $('#_LinkButton_10').hide();
                $('#_LinkButton_12').hide();
                $('#_LinkButton_2_2').hide();
            }
        } else if (2 == isModify || 0 == isModify || 3 == isModify || 5 == isModify) {

            gantt.config.readonly = true;
            gantt.attachEvent("onTaskDblClick", function(task) {
                return false;
            });
            $('#_LinkButton_1').hide();
            $('#_LinkButton_2').hide();
            $('#_LinkButton_3').hide();
            $('#_LinkButton_4').hide();
            $('#_LinkButton_5').hide();
            $('#_LinkButton_6').hide();
            $('#_LinkButton_7').hide();
            $('#_LinkButton_8').hide();
            $('#_LinkButton_9').hide();
            $('#_LinkButton_10').hide();
            $('#_LinkButton_11').hide();
            $('#_LinkButton_12').hide();
            $('#_LinkButton_2_2').hide();

            if (0 == isModify) {
                $('#_LinkButton_10').show();
            } else if (2 == isModify) {
                $('#_LinkButton_12').show();
                $('#_LinkButton_2_2').show();
                $('#_LinkButton_20').show();
                $('#_LinkButton_21').show();

                gantt.attachEvent("onTaskDblClick", function(task) {
                    ScheduleProgressGantt.openEditReportProgressDialog(task);
                    return false;
                });

                gantt.config.multiselect = true;
//                gantt.message({
//                    text: "按住<b> shift </b>或者<b> ctrl </b>选择多个任务！",
//                    expire: -1
//                });
            }

            // $('#_Gantt_layout').layout('panel', 'north').panel('resize', {
            // height: 47
            // });
            // $('#_Gantt_layout').layout('resize', { // 调整north和center区的高度
            // height: '100%'
            // });
        }

        if (ganttData.taskBeans && ganttData.taskBeans.length > 0) {
            for ( var t in ganttData.taskBeans) {
                var task = ganttData.taskBeans[t];

                if (task.deadline_date) {
                    task.deadline_date = ScheduleProgressGantt.str_to_date(task.deadline_date);
                }
            }
        }

        _ganttData = ganttData;

        ScheduleProgressGantt.ganttInit(isModify);

        var _tempLong = new Date().getTime();

        gantt.config.start_date = new Date(_tempLong - _long);
        gantt.config.end_date = new Date(_tempLong + _long);

        if (_ganttData.taskBeans.length > 0) {
            generateStartAndEndDate(_ganttData.taskBeans);
        }

        gantt.init("ganttDiv");

        var _data = {
            data: _ganttData.taskBeans,
            links: _ganttData.linkBeans
        };

        gantt.parse(_data);

        refreshTaskCountNumber();
        
        // 默认不展示甘特图
        ScheduleProgressGantt.maximizeGrid();
        
        // 如果是进度，要选中已提交简报的任务
        if (2 == isModify) {
        	_ganttData.taskBeans.forEach(bean => {
        		if (bean.hasProgress == true) {
        			$(".gantt_row[task_id='" + bean.id + "']").find("input[type='checkbox']").prop("checked", true);
        			_selectTaskIdList.push(bean.id);
        			$("#_LinkButton_upload>a").linkbutton('enable');
//        			gantt.selectTask(bean.id);
        		}
        	});
        }
        
        _ganttInitialized = true;
    }
    
    function getTaskUpdatedContents(task) {
    	
    	const nextTask = task.nextBean;
    	if (!nextTask) return [];
    	
    	const compareFields = [
    		{ name: '任务名称', field: 'text' },
    		{ name: '交付物', field: 'achievement' },
    		{ name: '负责人', field: 'leader' },
    		{ name: '参与人员', field: 'participantName' },
    		{ name: '任务类型', field: 'type' },
    		{ name: '工期', field: 'duration' },
    		{ name: '开始日期', field: 'start_date' },
    		{ name: '结束日期', field: 'end_date' },
    		{ name: '前置任务', field: 'linkText' },
    		{ name: '层级', field: 'taskLayer' },
    		{ name: '优先级', field: 'weight' },
    		{ name: '备注', field: 'remark' },
    		{ name: '最后期限日', field: 'deadline_date' },
    	];
    	
    	const diffContents = [];
    	compareFields.forEach(field => {
    		
    		let prev = task[field.field] || '';
    		let next = nextTask[field.field] || '';
    		console.log('比较字段', prev, next)
    		
    		if (field.field == 'leader') {
    			const prevLeader = _ganttData.optionsMap.leaderOption.find(l => l.id == prev);
    			const nextLeader = _ganttData.optionsMap.leaderOption.find(l => l.id == next);
    			
    			prev = prevLeader ? prevLeader.text : '';
    			next = nextLeader ? nextLeader.text : '';
    		}
    		
    		if (prev != next) {
    			
    			if (field.field == 'start_date' || field.field == 'end_date' || field.field == 'deadline_date') {
    				if (prev && next && moment(prev).valueOf() != moment(next).valueOf()) {
    					const prevDate = moment(prev).format('YYYY-MM-DD');
    					const nextDate = moment(next).format('YYYY-MM-DD')
    					diffContents.push({ name: field.name, prev: prevDate, next: nextDate });
    				}
    			} else {
    				diffContents.push({ name: field.name, prev, next });
    			}
    			
    		}
    	});
    	
    	return diffContents;
    }

    // 更新前台显示任务数
    function refreshTaskCountNumber() {
        $taskCountNumberLable.html(gantt.getTaskCount());
    }

    function saveCalendarDataToDB() {
        var workTimes = [];
        var calendarExceptionBeans = [];

        var workTImeData = $workTimeDatagrid.datagrid('getData');

        for ( var r in workTImeData.rows) {
            var checkboxId = pre_Checkboxx_workday_ + workTImeData.rows[r].id;

            var isChecked = $('#' + checkboxId).prop('checked');

            if (isChecked) {
                workTimes.push(1);
            } else {
                workTimes.push(0);
            }
        }

        var workDayCount = 0;

        for ( var d in workTimes) {
            var value = workTimes[d];
            if (1 == value) {
                workDayCount++;
            }
        }

        if (workDayCount == 0) {
            $('#_CalendarEditorTabs').tabs('select', 1);
            gantt.alert("工作周必须有一天是工作日！");
            return;
        }

        // 关闭编辑模式
        var cells = $calendarExceptionGrid.datagrid('getSelectedCells');

        if (cells.length > 0) {
            for ( var c in cells) {
                var cell = cells[c];
                $calendarExceptionGrid.datagrid('endEdit', cell.index);
            }
        }

        var exceptionData = $calendarExceptionGrid.datagrid('getData');

        for ( var e in exceptionData.rows) {

            var row = exceptionData.rows[e];
            if (row.startDate && row.endDate) {

                var _startDate = ScheduleProgressGantt.str_to_date(row.startDate);
                var _endDate = ScheduleProgressGantt.str_to_date(row.endDate);

                if (_startDate.getTime() > _endDate.getTime()) {
                    continue;
                }

                var checkboxId = pre_Checkboxx_workday_ + row.id;
                var isChecked = $('#' + checkboxId).prop('checked');

                var isWorkday = 0
                if (isChecked) {
                    isWorkday = 1;
                }

                calendarExceptionBeans.push({
                    id: row.id,
                    isWorkday: isWorkday,
                    exceptionName: row.exceptionName,
                    startDate: row.startDate,
                    endDate: row.endDate
                });
            }
        }

        var ganttDataBeanParam = {
            workTimeBean: {
                id: _ganttData.workTimeBean.id,
                workTimes: workTimes
            },
            calendarExceptionBeans: calendarExceptionBeans
        };

        var options = {
            url: BASE_URL + '/schedule/plan-task/save-calendar-data.json?randomNum='
                    + Math.random(),
            type: 'POST',
            data: JSON.stringify(ganttDataBeanParam),
            dataType: 'json',
            contentType: 'application/json',
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                if (MainUtils.processJsonResult(response, true)) {

                    _ganttData.workTimeBean = ganttDataBeanParam.workTimeBean;
                    _ganttData.calendarExceptionBeans = ganttDataBeanParam.calendarExceptionBeans;

                    initWorkTime();

                    // 重新计算任务
                    gantt.batchUpdate(function() {
                        gantt.eachTask(function(task) {

                            var newCalendar = gantt.getCalendar(_ganttData.workTimeBean.id);

                            task.start_date = newCalendar.getClosestWorkTime({
                                dir: "future",
                                date: task.start_date,
                                unit: gantt.config.duration_unit,
                                task: task
                            });
                            task.end_date = newCalendar.calculateEndDate(task);
                            gantt.updateTask(task.id);
                        });
                    });

                    ScheduleProgressGantt.closeCalendarDialog();
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);// jquery.form plugin
        MainUtils.openLoading();// 弹出loading提示
    }

    function saveGanttDataToDB() {

        var ganttDataBean = {};
        ganttDataBean.taskBeans = [];
        ganttDataBean.linkBeans = [];

        ganttDataBean.id = _ganttData.id;

        var tasks = gantt.getTaskByTime();

        if (tasks.length > 0) {

            var taskNum = 0;

            gantt
                    .eachTask(function(task) {
                        var _task = {};
                        _task.id = task.id;
                        _task.text = task.text;
                        _task.taskType = task.taskType;
                        _task.achievement = task.achievement;
                        _task.delivery = task.delivery;
                        _task.start_date = ScheduleProgressGantt.date_to_str(task.start_date);
                        _task.end_date = ScheduleProgressGantt.date_to_str(task.end_date);
                        _task.duration = task.duration;
                        _task.progress = task.progress;
                        _task.type = gantt.getTaskType(task);
                        _task.parent = task.parent;

                        _task.workload = task.workload;
                        _task.weight = task.weight;
                        _task.wbs = task.$wbs;

                        _task.taskNum = ++taskNum;
                        _task.taskLayer = task.$level + 1;

                        _task.leader = task.leader;
                        _task.linkText = task.linkText;
                        
                        _task.participantName = task.participantName;
                        _task.participantId = task.participantId;
                        
                        _task.planTaskId = task.planTaskId;

                        _task.constraint_type = gantt.getConstraintType(task);
                        if (task.constraint_date) {
                            _task.constraint_date = ScheduleProgressGantt
                                    .date_to_str(task.constraint_date);
                        }

                        if (task.deadline_date) {
                            _task.deadline_date = ScheduleProgressGantt
                                    .date_to_str(task.deadline_date);
                        }

                        if (task.isSummary && 1 == task.isSummary) {
                            _task.isSummary = 1;
                        } else {
                            _task.isSummary = 0;
                        }

                        if (task.isMilestone && 1 == task.isMilestone) {
                            _task.isMilestone = 1;
                        } else {
                            _task.isMilestone = 0;
                        }

                        if (task.isCritical && 1 == task.isCritical) {
                            _task.isCritical = 1;
                        } else {
                            _task.isCritical = 0;
                        }

                        if (task.isSiteTask && 1 == task.isSiteTask) {
                            _task.isSiteTask = 1;
                        } else {
                            _task.isSiteTask = 0;
                        }

                        _task.remark = task.remark;
                        _task.issueType = $("#issue_type" + task.id).val();
                        ganttDataBean.taskBeans.push(_task);
                    })
        }

        var links = gantt.getLinks();

        if (links.length > 0) {
            for ( var l in links) {

                ganttDataBean.linkBeans.push(links[l]);
            }
        }

        var url = BASE_URL + '/schedule/plan-task/save-gantt-data.json?randomNum=' + Math.random();

        if (4 == _ganttData.isModify) {
            url = BASE_URL + '/schedule/plan-task/save-gantt-data-plan-change.json?randomNum='
                    + Math.random();
        }

        var options = {
            url: url,
            type: 'POST',
            data: JSON.stringify(ganttDataBean),
            dataType: 'json',
            contentType: 'application/json',
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                if (MainUtils.processJsonResult(response, true)) {
                	
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);// jquery.form plugin
        MainUtils.openLoading();// 弹出loading提示
    }

    function formatterWeekName(value, row, index) {
        var id = row.id;

        if (0 == id) {
            return '星期六';
        } else if (1 == id) {
            return '星期一';
        } else if (2 == id) {
            return '星期二';
        } else if (3 == id) {
            return '星期三';
        } else if (4 == id) {
            return '星期四';
        } else if (5 == id) {
            return '星期五';
        } else if (6 == id) {
            return '星期天';
        }
    }

    function formatterIsWorkdayText(value, row, index) {
        var isChecked = false;

        if (1 == row.isWorkday) {
            isChecked = true;
        }

        var checkStr = isChecked ? ' checked="checked"' : '';

        return '<input id="' + pre_Checkboxx_workday_ + row.id + '" type="checkbox" ' + checkStr
                + '" />';
    }
    
    /**
     * 上传附件
     */
    function uploadAttachment() {
    	
    	// 找到对应的对话框div
		$('#_Gantt_layout').append("<div id='attachment-upload'></div>");
    	var uploadDiv = $('#attachment-upload');
    	uploadDiv.empty();
    	
    	uploadDiv.append();
    	let taskListHtml = '';
    	_selectTaskIdList.forEach(id => {
    		const task = _ganttData.taskBeans.find(bean => bean.id == id);
    		if (task) {
    			taskListHtml += 
    				'<div style="margin:8px; border:solid 1px #CCC; border-radius:4px; padding: 8px; display: flex;">' +
    					'<span style="text-align: left; margin-right: 8px;">' + task.text + ':&nbsp;</span>' +
    					'<span style="display: inline-block" id="task-attachments-' + task.id + '"></span>' +
    				'</div>'
    			;
    		}
    	});
//    	taskListHtml += '</div>';
    	console.log(taskListHtml);
    	uploadDiv.append(taskListHtml);
    	
    	uploadDiv.append(
    		'<form id="uploadForm" enctype="multipart/form-data" style="margin:8px; border:solid 1px #CCC; border-radius:4px; padding: 8px;">' + 
				'<input id="attch-uploader" class="easyui-filebox" name="files" type="text" style="width:300px" data-options="multiple:true">' +
    		'</form>'
    	);
    	
    	uploadDiv.append(
    		'<div class="messager-button">' + 
	    		'<a id="confirmUpload" class="l-btn l-btn-small" style="font-size: 14px; margin-right:6px" href="javascript:;" >确认</a>' +
	    		'<a id="cancelUpload" class="l-btn l-btn-small" style="font-size: 14px" href="javascript:;" >取消</a>' +
    		'</div>'
    	);
    	
    	
    	$('#attch-uploader').filebox({
    		buttonText: '选择文件',
    		buttonAlign: 'left',
    		onChange: function(value) {
    			
    			if (!value || value == '') return;
    			
    			// 上传文件变化时，对应更新任务列表中的上传附件列表
    			const fileNames = value.split(',');
    			const tasks = _ganttData.taskBeans.filter(task => _selectTaskIdList.indexOf(task.id) >= 0);
    			
    			for (let i = 0; i < tasks.length; i++) {
    				const task = tasks[i];
    				const taskFileNames = fileNames.filter(name => name.indexOf(task.$wbs + '-' + task.text) == 0);
    				
    				$('#task-attachments-' + task.id).empty();
    				taskFileNames.forEach(name => {
    					$('#task-attachments-' + task.id).append(
    						'<div>' + name + '</div>'
    					);
    				});
    			}
    		}
    	});
    	
    	uploadDiv.window({
    		title: '上传附件说明',
    		width: 400,
    		minimizable: false,
    		maximizable: false,
    		collapsible: false,
    		closable: true,
    		modal: true,
    		onClose: function() {
    			$('#attachment-upload').remove();
    		},
    	});
    	
    	$('#cancelUpload').bind('click', function() {
    		$('#attachment-upload').window('close');
    	});
    	
    	$('#confirmUpload').bind('click', function() {
    		
    		// 取得上传文件
    		const fileName = $('#attch-uploader').filebox('getValue');
    		const files = $('#attch-uploader').next().find('input[type=file]')[0].files;
    		
    		// 根据已选择的任务去筛选上传的文件，非任务对应的文件不上传
//    		const tasks = _ganttData.taskBeans.filter(task => _selectTaskIdList.indexOf(task.id) >= 0);
//    		const uploadFiles = [];
//    		for (let n = 0; n < tasks.length; n++) {
//    			const task = tasks[n];
//    			for (let i = 0; i < files.length; i++) {
//        			const file = files[i];
//        			if (file.name.indexOf(task.$wbs + '-' + task.text) == 0) {
//        				uploadFiles.push(file);
//        			}
//        		}
//    		}
//    		
    		console.log(files);
    		if (files.length > 0) {
    			
    			const formData = new FormData($("#uploadForm")[0]);
//    			formData.append('files', );
    			
    			MainUtils.openLoading();
    			$.ajax({
    				url: BASE_URL + '/schedule/plan-task/upload-gantt-data-attachment.json?planTaskIds=' + _selectTaskIdList.join(','),
    				type: 'POST',
    				data: formData,
    				contentType:false,
    				processData: false,
    				success: function(resposne, statusText, xhr, jqForm) {
    					
    					// 取得应答，关闭loading提示
    	            	MainUtils.closeLoading();
    	            	
    	            	const ganttTaskBeans = resposne.dataMap.returnObj;
    	            	if (ganttTaskBeans) {
    	            		for (let i = 0; i < ganttTaskBeans.length; i++) {
    	            			const ganttTaskBean = ganttTaskBeans[i];
    	            			const ganttTask = gantt.getTask(ganttTaskBean.id);
    	            			ganttTask.attachmentList = ganttTaskBean.attachmentList;
    	            			gantt.updateTask(ganttTaskBean.id);
    	            		}
    	            	}
    				},
    	            error: MainUtils.handleAjaxFormError
    			})
    		}
    		
    		$('#attachment-upload').window('close');
    	});
    }
    
    function openCalendarDialog() {

        var $dialog = $('#_CalendarEditorDialog');

        function process(isFirstLoad) {

            if (_ganttData.calendarExceptionBeans.length > 0) {

                $calendarExceptionGrid.datagrid('loadData', _ganttData.calendarExceptionBeans);
            }

            var _tempData = [];

            for (var i = 0; i < _ganttData.workTimeBean.workTimes.length; i++) {

                _tempData.push({
                    id: String(i),
                    isWorkday: _ganttData.workTimeBean.workTimes[i]
                });
            }

            $workTimeDatagrid.datagrid('loadData', _tempData);

            $dialog.dialog('open').dialog('center').scrollTop(0);

            $('#_CalendarEditorTabs').tabs('select', 0);
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

    function openImportExcelDialog() {

        var $dialog = $('#_ImportGanttExcelDialog');

        var $form = $('#_ImportGanttExcelDialogForm');

        function process(isFirstLoad) {

            $form.form('reset');

            $dialog.dialog('open').dialog('center').scrollTop(0);

            $form.form('disableValidation');
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

    function saveImportExcelData() {

        var $form = $('#_ImportGanttExcelDialogForm');

        $form.form('enableValidation');
        if (!$form.form('validate')) {
            return;
        }

        var options = {
            url: BASE_URL + '/schedule/plan-task/import-excel-file.json',
            type: 'POST',
            dataType: 'html',
            data: {
                'planId': _ganttData.id,
            },
            success: function(response, statusText, xhr, jqForm) {
                var jsonObj = $.parseJSON($(response).text());

                MainUtils.closeLoading();
                if (MainUtils.processJsonResult(jsonObj, true)) {

                    // 如果解析错误
                    if (jsonObj.message == 'ParseError') {
                        console.log(jsonObj.dataMap.returnObj);
                        $('#_SegmentExcelParseDialog').window('open');
                        $('#_SegmentExcelParseErrors').datagrid({
                            data: jsonObj.dataMap.returnObj,
                            columns: [[{
                                field: 'row',
                                title: '行',
                                width: 50
                            }, {
                                field: 'column',
                                title: '列',
                                width: 50
                            }, {
                                field: 'message',
                                title: '详情',
                                width: 500
                            }, ]]
                        })
                    } else if (jsonObj.dataMap.returnObj) {

                        _ganttData.taskBeans = jsonObj.dataMap.returnObj.taskBeans;

                        gantt.clearAll();
                        handlerInit(_ganttData, 1);

                        gantt.batchUpdate(function() {
                            var tasks = gantt.getTaskByTime();
                            for (var i = 0; i < tasks.length; i++) {
                                var task = tasks[i];
                                gantt.changeTaskId(task.id, MainUtils.guid());
                            }
                        });

                        EasyDialogUtils.closeFormDialog('#_ImportGanttExcelDialog');
                    }
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $form.ajaxSubmit(options);
        MainUtils.openLoading();
    }

    function exportGanttData() {
        var url = BASE_URL
                + '/schedule/plan-task/export-excel-file.json?isExportReport=false&planId='
                + _ganttData.id;

        MainUtils.downloadFile(url);
    }

    function maximizeGrid() {
        if (isMaximize) {
            gantt.config.layout = defaultLayout;
            isMaximize = false;
            $(".maximzebuttonposition").css("left", $(window).width() * 0.67 + 1);
            $(".maximzebuttonposition>a>span>span").text(">");
        } else {
            $(".maximzebuttonposition").css("left", $(window).width() - 23);
            $(".maximzebuttonposition>a>span>span").text("<");
            gantt.config.layout = {
                css: "gantt_container",
                cols: [{
                    width: $(window).width(),
                    min_width: $(window).width(),
                    rows: [{
                        view: "grid",
                        scrollX: "gridScroll",
                        scrollable: true,
                        scrollY: "scrollVer"
                    },
                    // horizontal scrollbar for the grid
                    {
                        view: "scrollbar",
                        id: "gridScroll",
                        group: "horizontal"
                    }]
                }, {
                    resizer: true,
                    width: 3
                }, {
                    rows: [{
                        view: "timeline",
                        scrollX: "scrollHor",
                        scrollY: "scrollVer"
                    },
                    // horizontal scrollbar for the timeline
                    {
                        view: "scrollbar",
                        id: "scrollHor",
                        group: "horizontal"
                    }]
                }, {
                    view: "scrollbar",
                    id: "scrollVer"
                }]
            };
            isMaximize = true;
        }

        gantt.init("ganttDiv");
    }

    function openExport() {
        var _url = BASE_URL + '/schedule/plan-task/export-task-excel-file.json?planId=' + _ganttData.id;
        MainUtils.downloadFile(_url);
    }

    // 打开导入页面
    function openImport() {
    	debugger
        var $dialog = $('#_ProjectExcelDialog');
        var $form = $('#_ProjectExcelDialogForm');
        function process(isFirstLoad) {
            $form.form('reset');
            $dialog.dialog('open').dialog('center').scrollTop(0);
            $form.form('disableValidation');
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

    function saveProcessImportExcelData() {
        var $form = $('#_ProjectExcelDialogForm');

        $form.form('enableValidation');
        if (!$form.form('validate')) {
            return;
        }

        var options = {
            url: BASE_URL + '/schedule/plan-task/import-task-excel-file.json?planId=' + _ganttData.id,
            type: 'POST',
            dataType: 'html',
            data: {

            },
            success: function(response, statusText, xhr, jqForm) {
                var jsonObj = $.parseJSON($(response).text());

                MainUtils.closeLoading();
                if (MainUtils.processJsonResult(jsonObj, true)) {

                    if (jsonObj.dataMap.returnObj) {
                        EasyDialogUtils.closeFormDialog('#_ProjectExcelDialog');
                        location.reload();
                    }
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $form.ajaxSubmit(options);
        MainUtils.openLoading();
    }

    return {
        str_to_date: str_to_date,
        date_to_str: date_to_str,
        constraintTypes: constraintTypes,
        onChangeConstraintTypes: onChangeConstraintTypes,
        ganttInit: ganttInit,
        deadlineButtons: deadlineButtons,
        linkTypes: linkTypes,
        columns: columns,
        zoomConfig: zoomConfig,
        zoomIn: zoomIn,
        zoomOut: zoomOut,
        formatWeekendStyle: formatWeekendStyle,
        onChangeTopZoomScale: onChangeTopZoomScale,
        onChangeButtomZoomScale: onChangeButtomZoomScale,
        datetimeParser: datetimeParser,
        getWidget: getWidget,
        saveGanttLightbox: saveGanttLightbox,
        closeGanttLightbox: closeGanttLightbox,
        onChangeForDuration: onChangeForDuration,
        onChangeForEnddate: onChangeForEnddate,
        newTaskDialog: newTaskDialog,
        editTaskDialog: editTaskDialog,
        deleteTaskDialog: deleteTaskDialog,
        upLevel: upLevel,
        downLevel: downLevel,
        moveUp: moveUp,
        moveDown: moveDown,
        addFavorite: addFavorite,
        delFavorite: delFavorite,
        addLink: addLink,
        deleteLink: deleteLink,
        onChangeLinkId: onChangeLinkId,
        formatterLinkType: formatterLinkType,
        formatterLagDays: formatterLagDays,
        onOpenEditTaskDialog: onOpenEditTaskDialog,
        onCloseEditTaskDialog: onCloseEditTaskDialog,
        openCalendarDialog: openCalendarDialog,
        uploadAttachment: uploadAttachment,
        handlerInit: handlerInit,
        saveGanttDataToDB: saveGanttDataToDB,
        onOpenEditCalendarDialog: onOpenEditCalendarDialog,
        onCloseEditCalendarDialog: onCloseEditCalendarDialog,
        saveCalendarDataToDB: saveCalendarDataToDB,
        closeCalendarDialog: closeCalendarDialog,
        addExceptionDate: addExceptionDate,
        deleteExceptionDate: deleteExceptionDate,
        formatterWeekName: formatterWeekName,
        formatterIsWorkdayText: formatterIsWorkdayText,
        openImportExcelDialog: openImportExcelDialog,
        saveImportExcelData: saveImportExcelData,
        exportGanttData: exportGanttData,
        openEditReportProgressDialog: openEditReportProgressDialog,
        saveEditReportProgressDataToDB: saveEditReportProgressDataToDB,
        openReportProgressDialog: openReportProgressDialog,
        saveReportProgressData: saveReportProgressData,
        onOpenReportProgressDialog: onOpenReportProgressDialog,
        onCloseReportProgressDialog: onCloseReportProgressDialog,
        FlowStatusComboData: FlowStatusComboData,
        IssuesComboData: IssuesComboData,
        formatterReportProgressComboResource: formatterReportProgressComboResource,
        formatterIssuesComboResource: formatterIssuesComboResource,
        formatterProgressNumber: formatterProgressNumber,
        maximizeGrid: maximizeGrid,
        openExport: openExport,
        openImport: openImport,
        openWorkloadDialog: openWorkloadDialog,
        saveProcessImportExcelData: saveProcessImportExcelData,
        onSelectParticipantFilter: onSelectParticipantFilter,
        onUnselectParticipantFilter: onUnselectParticipantFilter,
        onTaskNameFilter: onTaskNameFilter
    }
})(jQuery);