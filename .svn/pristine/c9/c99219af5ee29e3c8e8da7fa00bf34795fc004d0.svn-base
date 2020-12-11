

/**
 * 给时间框控件扩展一个清除的按钮
 */
$.fn.datebox.defaults.cleanText = '清空';

(function ($) {
    var buttons = $.extend([], $.fn.datebox.defaults.buttons);
    buttons.splice(1, 0, {
        text: function (target) {
            return $(target).datebox("options").cleanText
        },
        handler: function (target) {
            $(target).datebox("setValue", "");
            $(target).datebox("hidePanel");
        }
    });
    $.extend($.fn.datebox.defaults, {
        buttons: buttons
    });

})(jQuery)

/**
 * 弹出消息，包装的easyui的message。
 */
var AlertUtils = (function($) {

    /**
     * 弹出消息框提示。
     * 
     * @param level 包括'info'，'error'，'warning'。默认'info'
     * @param content 消息内容
     */
    function msg(level, content) {
        var icon = level || 'info';

        var title;
        if (icon == 'error') {
            title = LABLE_ERROR;
        } else if (icon == 'warning') {
            title = LABLE_WARNING;
        } else {
            title = LABLE_INFO;
        }

        $.messager.alert(title, content, icon);
    }

    return {
        msg: msg
    }; // AlertUtils
})(jQuery);

/**
 * 共通Utils。 依赖的javascript公共常量在commonJsVar.jsp中定义。 大致分为三类函数：处理数据，处理UI和处理交互。
 */
var MainUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 用来遍历指定对象所有的属性名称和值
     * 
     * @param obj 需要遍历的对象
     * @author Jet Mah
     * @see http://www.javatang.com/archives/2006/09/13/442864.html
     */
    function printProperties(obj) {
        // 用来保存所有的属性名称和值
        var props = "";
        // 开始遍历
        for ( var p in obj) {
            // 方法
            if (typeof (obj[p]) == "function") {
                // obj[p]();
            } else {
                // p 为属性名称，obj[p]为对应属性的值
                props += "'" + p + "'" + "=" + obj[p] + ";\n";
            }
        }
        // 最后显示所有的属性
        alert(props);
    }

    /**
     * 解决字符串中的转义符号，及快速实现换行。
     * 
     * @param fn
     * @returns
     * @deprecated 应在Controller中转换，见Spring的HtmlUtils
     */
    function heredoc(fn) {
        return fn.toString().split('\n').slice(1, -1).join('\n');
    }

    /**
     * 将字符串中的HTML专用字符转义。
     * 
     * @param str 待转义字符串
     * @returns 转义后的字符串
     * @deprecated 应在Controller中转换，见Spring的HtmlUtils
     */
    function encodeHtml(str) {
        str = str.replace(/&/g, '&amp;');
        str = str.replace(/</g, '&lt;');
        str = str.replace(/>/g, '&gt;');
        // str = str.replace(/(?:t| |v|r)*n/g, '&lt;br /&gt;');
        str = str.replace(/ /g, '&nbsp; ');
        // str = str.replace(/t/g, '&nbsp; &nbsp; ');
        str = str.replace(/x22/g, '&quot;');
        str = str.replace(/x27/g, '&#39;');
        return str;
    }

    function guid() {
        return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
            return v.toString(16);
        });
    }

    /**
     * 限制IP输入框的按键，只允许输入数字、Backspace、'.'、ctrl + c、ctrl + v
     * 
     * @param e
     * @returns {Boolean}
     */
    function filterIpInputKey(e) {
        var key = e.which || e.keyCode;
        if (key == 13) {
            return true;
        }
        if ((key >= 48 && key <= 57 && e.ctrlKey == false && e.shiftKey == false) || key == 0
                || key == 8 || key == 46) { // 数字 or Backspace or '.'
            return true;
        } else {
            if (e.ctrlKey == true && (key == 99 || key == 118)) { // ctrl + c or ctrl + v
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 限制数值输入框的按键。参考EasyUI。
     * 
     * @param target
     * @param e
     * @returns {Boolean}
     */
    function filterNumberInputKey(target, e) {
        var s = $(target).val();
        if (e.metaKey || e.ctrlKey) {
            return true;
        }
        if ($.inArray(String(e.which), ['46', '8', '13', '0']) >= 0) { // DELETE BACKSPACE ENTER
            return true;
        }
        var tmp = $('<span></span>');
        tmp.html(String.fromCharCode(e.which));
        var c = tmp.text();
        tmp.remove();
        if (!c) {
            return true;
        }
        if (c == '-' || c == '.') {
            return (s.indexOf(c) == -1) ? true : false;
        } else if ('0123456789'.indexOf(c) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解析并修正Numberbox的输入值。参考EasyUI。
     * 
     * @param target
     * @param e
     */
    function fixNumberboxValue(target, e) {

        function _parseNumberboxValue(s, min, max, precision) {
            s = s + '';
            // if (parseFloat(s) != s){
            // if (opts.prefix) s = $.trim(s.replace(new RegExp('\\'+$.trim(opts.prefix),'g'), ''));
            // if (opts.suffix) s = $.trim(s.replace(new RegExp('\\'+$.trim(opts.suffix),'g'), ''));
            // if (opts.groupSeparator) s = $.trim(s.replace(new
            // RegExp('\\'+opts.groupSeparator,'g'), ''));
            // if (opts.decimalSeparator) s = $.trim(s.replace(new
            // RegExp('\\'+opts.decimalSeparator,'g'), '.'));
            // s = s.replace(/\s/g,'');
            // }
            if (parseFloat(s) != s) {
                s = s.replace(/\s/g, '');
            }
            var val = parseFloat(s).toFixed(precision);
            if (isNaN(val)) {
                val = '';
            } else if (typeof (min) == 'number' && val < min) {
                val = min.toFixed(precision);
            } else if (typeof (max) == 'number' && val > max) {
                val = max.toFixed(precision);
            }
            return val;
        }

        var $this = $(target);
        var min = parseFloat($this.attr('data-min'));
        var max = parseFloat($this.attr('data-max'));
        var precision = parseInt($this.attr('data-precision'));
        var s = $this.val();

        var r = _parseNumberboxValue(s, min, max, precision);

        $this.val(r);
    }

    /**
     * 键入Enter后，解析并修正Numberbox的输入值。参考EasyUI。
     * 
     * @param target
     * @param e
     */
    function enterNumberboxValue(target, e) {
        if (e.keyCode == 13) {
            fixNumberboxValue(target, e);
        }
    }

    /**
     * 禁用F5、Ctrl+F5，并禁用Backspace。如果传入了datagridId，则F5触发datagrid刷新数据。
     * 
     * @param e
     * @param datagridId CSS selector，形如"#myDatagridId"，如果传入了此参数，则刷新相应的datagrid
     * @param f5Handler F5事件处理
     */
    function disableF5Backspace(e, datagridId, f5Handler) {
        e = window.event || e; // window.event兼容IE
        if ((e.which || e.keyCode) == 116) {

            if (datagridId) {
                $(datagridId).datagrid('reload');
            }

            if ($.isFunction(f5Handler)) {
                f5Handler();
            }

            // e.preventDefault(); // IE不适用
            e.keyCode = 0;
            return false;
        }
        if ((e.which || e.keyCode) == 8) { // 禁用Backspace
            var targ = {};
            if (e.target) { // Firefox, Chrome ...
                targ = e.target;
            } else if (e.srcElement) { // IE only
                targ = e.srcElement;
            }
            if (targ.nodeType == 3) {// defeat Safari bug
                targ = targ.parentNode;
            }

            if ((targ.type != "text" && targ.type != "textarea" && targ.type != "password")
                    || targ.readOnly == true) {
                e.keyCode = 0;
                return false;
            }
        }
        return true;
    }

    /**
     * 弹出'loading...'等待提示， 提示信息div使用id=loading。
     * 
     * @param id
     * @param msg 提示信息
     * @param w 宽度
     * @param h 高度
     */
    function openLoading(id, msg, w, h) {

        if (!id) {
            id = 'EasyDialogLoadingPrompt';
        }

        if (!msg) {
            msg = MSG_LOADING;
        }

        if (!w) {
            w = 190;
        }

        if (!h) {
            h = 40;
        }

        // 如果没有找到弹出提示框，则追加一个到body
        if ($('div#' + id + '.loading-prompt').size() == 0) {
            $('body').append(
                    '<div style="display:hidden"><div id="' + id
                            + '" class="loading-prompt" style="padding-top:12px;">' + msg
                            + '</div></div>');
            var option = {
                border: false,
                noheader: true,
                closed: true,
                width: w,
                height: h,
                modal: true,
                zIndex: 111000
            };

            $('div#' + id + '.loading-prompt').window(option);
        }
        $('#' + id).window('open');
    }

    /** javascript公共常量在commonJsVar.jsp中定义 */

    /**
     * 关闭弹出的'loading...'等待提示， 提示信息div使用id=loading。
     */
    function closeLoading(id) {
        if (!id) {
            id = 'EasyDialogLoadingPrompt';
        }
        $('div#' + id + '.loading-prompt').window('close');
    }

    /**
     * 使用异步内容获取时，用来抽取<body/>内的内容(不含<body/>)。拷贝自easyui的$.fn.panel.defaults.extractor。
     * 
     * @param data 服务器回传的数据
     */
    function extractHtmlBody(data) {
        var pattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
        var matches = pattern.exec(data);
        if (matches) {
            return matches[1]; // only extract body content
        } else {
            return data;
        }
    }

    /**
     * 使用异步内容获取时，用来抽取目标<body/>内可见的内容(不含<body/>)，并在首次加载时，将不可见的内容('#EasyuiHiddenWrapper')直接添加到当前body。
     * 
     * 见Main._extractContentTabHtmlBody。
     * 
     * @param data 服务器回传的数据
     */
    function extractVisibleHtmlBody(data) {
        var content = extractHtmlBody.call(this, data);
        var $content = $('<div>' + content + '</div>');

        // this指向.panel-body
        // 当在easyui-tabs内使用时，每次关闭tab将从文档流中删除整个.panel-body，再打开tab会创建全新的.panel-body，loaded属性始终是false，
        // 所以此处loaded的判断没有必要 TODO
        if (!$(this).prop('loaded')) {
            // console.log('not loaded');
            if ($content.find('#EasyuiHiddenWrapper').length > 0) {
                $('#EasyuiHiddenWrapper', $content).removeAttr('id').appendTo('body');
            }
        }

        $(this).prop('loaded', true);

        content = $content.find('#EasyuiHiddenWrapper, #ContentScriptWrapper').remove().end()
                .html();
        return content;
    }

    /**
     * 对服务端返回的JSON结果的通用处理，待处理的一定是JSON对象。
     * 
     * 如果服务端返回的不是JSON格式的数据，如Session超时的时候返回的是HTML文档(sessionExpire.jsp)，
     * 这种情况ajax会发生错误，ajax的error回调函数会被调用，本函数只处理符合JSON格式的数据。
     * 
     * @param result JSON对象，参考JsonResultBean.java
     * @param isMsgAlert 是否弹出提示
     * @param isFailWithPop 是否在失败时使用气泡提示
     * @returns 服务端处理结果
     */
    function processJsonResult(result, isMsgAlert, isFailWithPop) {
        if (result && result.status) {
            if (result.status == 1) {// 成功
                if (isMsgAlert) {
                    $.messager.show({
                        msg: result.message,
                        showType: 'slide',
                        showSpeed: 300,
                        timeout: 2500,
                        width: 170,
                        height: 30,
                        style: {
                            right: '',
                            top: document.body.scrollTop + document.documentElement.scrollTop,
                            bottom: '',
                            padding: 5
                        }
                    });
                }
                return true;
            } else {// 失败
                if (isMsgAlert) {
                    var errorMsg = MSG_REMOTE_SERVER_ERROR;
                    if (result.message) {
                        errorMsg = result.message;
                    }

                    if (isFailWithPop) {
                        $.messager.show({
                            msg: errorMsg,
                            showType: 'slide',
                            showSpeed: 300,
                            timeout: 3000,
                            width: 300,
                            height: 30,
                            style: {
                                right: '',
                                top: document.body.scrollTop + document.documentElement.scrollTop,
                                bottom: '',
                                padding: 5
                            }
                        });
                    } else {
                        // $.messager.alert(LABLE_ERROR, errorMsg, 'error');
                        AlertUtils.msg(result.icon, errorMsg);
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * easyui combobox通用loadFilter。
     * 
     * @param data JSON对象，参考JsonResultBean.java
     * @returns
     */
    function comboboxLoadFilter(data) {

        // 如果服务端发生错误，则弹出错误提示并返回空结果
        if (!processJsonResult(data, false)) {
            // if (data.message) {
            // $.messager.alert(LABLE_ERROR, data.message, 'error');
            // } else {
            // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
            // }
            AlertUtils.msg(data.icon || 'error', data.message || MSG_REMOTE_SERVER_ERROR);
            return [{
                'id': '',
                'text': ''
            }];
        }

        if (data.dataMap && data.dataMap.rows) {
            return data.dataMap.rows;
        } else {
            return [{
                'id': '',
                'text': ''
            }];
        }
    }

    /**
     * easyui datagrid通用loadFilter。
     * 
     * @param data JSON对象，参考JsonResultBean.java
     */
    function datagridLoadFilter(data) {

        // 如果服务端发生错误，则弹出错误提示并返回空结果
        if (!processJsonResult(data, false)) {

            if ((data.total && data.rows) || (data.total === 0 && Array.isArray(data.rows))) {
                return data;
            }

            // if (data.message) {
            // $.messager.alert(LABLE_ERROR, data.message, 'error');
            // } else {
            // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
            // }
            AlertUtils.msg(data.icon || 'error', data.message || MSG_REMOTE_SERVER_ERROR);
            return {
                total: 0,
                rows: []
            };
        }

        if (data.dataMap) {
            return data.dataMap;
        } else {
            return {
                total: 0,
                rows: []
            };
        }
    }

    /**
     * easyui tree通用loadFilter。
     * 
     * @param data JSON对象，参考JsonResultBean.java
     */
    function treeLoadFilter(data) {

        // 兼容tree的拖拽操作，拖拽时同样会触发loadFilter
        if (typeof data.status == 'undefined') {
            return data;
        }

        // 如果服务端发生错误，则弹出错误提示并返回空结果
        if (!processJsonResult(data, false)) {
            // if (data.message) {
            // $.messager.alert(LABLE_ERROR, data.message, 'error');
            // } else {
            // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
            // }
            AlertUtils.msg(data.icon || 'error', data.message || MSG_REMOTE_SERVER_ERROR);
            return [];
        }

        if (data.dataMap && data.dataMap.rows) {
            return data.dataMap.rows;
        } else {
            return [];
        }
    }

    /**
     * easyui datagrid通用的onLoadSuccess事件处理。
     * 
     * 触发datagrid加载的事件有：画面OPEN，检索，取消检索，翻页，刷新，删除。easyui的datagrid默认在加载时不清除选中，
     * 在某些场合这个特性会给用户带来困惑，尤其是选中某些行后翻页，在翻页后这些行虽然看不见，但仍是选中状态，点击修改仍有效。
     * 
     * 约定datagrid凡是加载，必须清除选择。新增和修改操作不加载datagrid，因此也不清除选择。
     * 
     * @param data 数据
     */
    function handleDatagridLoadSuccess(data) {
        $(this).datagrid('clearSelections');
    }

    /**
     * easyui datagrid通用的onLoadError事件处理。
     * 
     * 任何返回jsp内容的情况，都会触发datagrid的onLoadError事件，因为该内容无法被解析成JSON对象，且解析时会出错。
     * 
     * @param XMLHttpRequest
     * @param textStatus
     * @param errorThrown
     */
    function handleDatagridLoadError(XMLHttpRequest, textStatus, errorThrown) {

        if (XMLHttpRequest.status === 401) {

            // 在Session超时的情况下，服务器返回的是sessionExpire.jsp的内容以及状态401，该内容无法被解析成JSON对象，且解析时会出错。
            // 故，如果出错了，但是status是401(表示Unauthorized)，则判断为Session超时
            // 参考SecurityInterceptor.java的处理逻辑
            top.location = URL_SESSION_EXPIRE;

        } else if (XMLHttpRequest.status === 500) {

            // Controller未捕获异常导致服务端程序出错时，返回的是error.jsp的内容以及状态500
            // 参考GeneralExceptionHandler.java的处理逻辑
            // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
            AlertUtils.msg('error', MSG_REMOTE_SERVER_ERROR);

        } else {

            // 其他错误
            // $.messager.alert(LABLE_ERROR, MSG_ERROR_AJAX + ' ' + XMLHttpRequest.status + ' '
            // + textStatus, 'error');
            AlertUtils
            // .msg('error', MSG_ERROR_AJAX + ' ' + XMLHttpRequest.status + ' ' + textStatus);
            .msg('error', MSG_ERROR_AJAX + ' readyState:' + XMLHttpRequest.readyState + ', status:'
                    + XMLHttpRequest.status + ', textStatus:' + textStatus);
        }
    }

    /**
     * jQuery form通用的ajaxSubmit调用后的error事件处理。
     * 
     * @param XMLHttpRequest
     * @param textStatus
     * @param errorThrown
     */
    function handleAjaxFormError(XMLHttpRequest, textStatus, errorThrown) {

        // 关闭loading提示
        closeLoading();

        handleDatagridLoadError(XMLHttpRequest, textStatus, errorThrown);
    }

    /**
     * 格式化datetimebox控件的时间格式，采用'2015-06-10 08:12:34'
     * 
     * @param date
     */
    function datetimeFormatter(date) {
        return date.Format('yyyy-MM-dd hh:mm:ss');
    }

    /**
     * 解析datetime
     * 
     * @param s
     * @returns {Date}
     */
    function datetimeParser(s) {
        // IE和Firefox下Date.parse出现NaN问题解决 ，IE只支持 2001/01/01
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

    /**
     * 格式化datebox控件的时间格式，采用'2015-06-10'
     * 
     * @param date
     */
    function dateFormatter(date) {
        return date.Format('yyyy-MM-dd');
    }

    /**
     * 格式化数字小数点显示
     * 
     * @param value
     * @param precision
     * @returns
     */
    function numberPrecisionFormatter(value, precision) {

        if (value == null) {
            value = 0;
        }

        var idx = String(value).indexOf('.');
        if (idx < 0) {
            value += '.';
            for (var i = 0; i < precision; ++i) {
                value += '0';
            }
        } else {
            var size = String(value).length - idx - 1;
            for (var i = size; i < precision; ++i) { // 位数不足补零
                value += '0';
            }
        }
        return value;
    }

    /**
     * 备注的式样，增加tooltip。ViewBean必须有remarkHtmlEscaped字段，并调用Spring的HtmlUtils.htmlEscape进行转义。
     * 
     * @param value 字符值
     * @param row easyui的datagrid中的某一行数据
     * @param index easyui的datagrid中的行index
     */
    function remarkFormatter(value, row, index) {
        // return '<span class="easyui-tooltip" title="' + row.remarkHtmlEscaped + '">'
        // + value + '</span>';
        return '<span title="' + row.remarkHtmlEscaped + '">' + value + '</span>';
    }
    
    function changeReasonFormatter(value, row, index) {
        return '<span title="' + row.changeReasonHtmlEscaped + '">' + value + '</span>';
    }

    function changeContentFormatter(value, row, index) {
        return '<span title="' + row.changeContentHtmlEscaped + '">' + value + '</span>';
    }

    function changeRemarkFormatter(value, row, index) {
        return '<span title="' + row.changeRemarkHtmlEscaped + '">' + value + '</span>';
    }

    function isYearFormatter(value, row, index) {
        return '<span>' + (value == 0 ? '否' : '是') + '</span>';
    }

    /**
     * 借助组件downloadViaIframe.jsp中隐藏的iframe，动态嵌入<form/>进行提交，再由服务端输出下载。
     * 这么做是为了防止Firefox提交时，Websocket断开。
     * 
     * @param url 形如 BASE_URL + '/license/download.json'
     */
    function downloadFile(url) {
        if (!url) {
            return;
        }

        // 使用隐藏的iframe，防止Firefox提交时，Websocket断开
        var frameBody = $('#DownloadTempFrame').contents().find('body');

        var form = $("<form>");// 定义一个form表单
        form.attr("style", "display:none");
        form.attr("method", "post");
        form.attr("action", url);

        frameBody.append(form);// 将表单放置在iframe的body中
        form.submit();// 表单提交
        form.remove();
    }

    /**
     * 将Datagrid中筛选过的记录，导出到Excel提供下载。
     * 
     * @param datagridId
     * @param url 提交的url
     */
    function exportDatagridToExcel(datagridId, url) {

        if (!datagridId) {
            return;
        }

        var tempArray = new Array();

        // 用来保存普通检索内容的数组，其中每个成员的结构同FilterFieldInfoDto.java
        var SEARCH_ARRAY = $(datagridId).data('SEARCH_ARRAY');

        // 为了提升性能，使用的object来保存高级检索内容，其中每个成员的结构同FilterFieldInfoDto.java
        var CONDITION_MAP_OBJ = $(datagridId).data('CONDITION_MAP_OBJ');

        if (SEARCH_ARRAY) {
            $.merge(tempArray, SEARCH_ARRAY);
        }
        if (CONDITION_MAP_OBJ) {
            for ( var i in CONDITION_MAP_OBJ) {
                tempArray.push(CONDITION_MAP_OBJ[i]);
            }
        }

        var commonFilter = {
            filterFieldList: tempArray
        };

        var param = {
            commonFilterJson: JSON.stringify(commonFilter)
        // Java方法必须接受一个名为commonFilterJson的参数
        };

        var hasParam = '&';
        if (url.indexOf('?') == -1) {
            hasParam = '?';
        }

        url = url + hasParam + $.param(param);

        downloadFile(url);
    }

    /** ********************** */
    /* private functions below */
    /** ********************** */


    /** ************************** */
    /* initialization logics below */
    /** ************************** */

    /**
     * 对Date的扩展，将 Date 转化为指定格式的String<br>
     * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，<br>
     * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)<br>
     * 例子：<br>
     * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423<br>
     * (new Date()).Format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18<br>
     */
    Date.prototype.Format = function(fmt) { // author: meizz
        var o = {
            "M+": this.getMonth() + 1, // 月份
            "d+": this.getDate(), // 日
            "h+": this.getHours(), // 小时
            "m+": this.getMinutes(), // 分
            "s+": this.getSeconds(), // 秒
            // "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
            "S": this.getMilliseconds()
        // 毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for ( var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
                        .substr(("" + o[k]).length)));
            }
        }
        return fmt;
    };

    /**
     * IP正则表达式
     */
    var IP_PATTERN = /^([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])(\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])){2}(\.([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))$/;
    var IP_SEGS_PATTERN = /^([1-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])(\.([0-9]|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])){2}$/;

    /**
     * 固定电话正则表达式
     */
    var PHONE_PATTERN = /^(([0\+]\d{2,3})?(0\d{2,3}))(\d{7,8})(-(\d{3,}))?$/;

    /**
     * 手机号码正则表达式
     */
    var MOBILE_PATTERN = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/;

    /**
     * 邮箱地址正则表达式
     */
    var EMAIL_PATTERN = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;

    /**
     * 文件名正则表达式
     */
    var FILE_NAME_PATTERN = /^[^\\/:\*\?"<>|]+$/;

    $.extend($.fn.validatebox.defaults.rules, {
        toGeFrom: { // 'to' greater than or equal to 'from'
            validator: function(value, param) {

                var $from = $(param[0]);
                var from;
                if ($from.is('.easyui-numberbox')) {
                    from = $from.numberbox('getValue');
                } else if ($from.is('.easyui-datebox')) {
                    from = $from.datebox('getValue');
                } else {
                    from = $from.val();
                }
                var to = value;
                if ($.isNumeric(from)) {
                    from = parseFloat(from);
                    to = parseFloat(to);
                }
                return to >= from;
            },
            message: MSG_ERROR_FROM_GT_TO
        },
        email: { // 邮箱地址验证
            validator: function(value, param) {
                return EMAIL_PATTERN.test(value);
            },
            message: MSG_ERROR_INVALID_EMAIL
        },
        filename: { // 邮箱地址验证
            validator: function(value, param) {
                return FILE_NAME_PATTERN.test(value);
            },
            message: MSG_ERROR_INVALID_FILE_NAME
        },
        phone: { // 电话号码验证
            validator: function(value, param) {
                return PHONE_PATTERN.test(value) || MOBILE_PATTERN.test(value);
            },
            message: MSG_ERROR_INVALID_PHONE
        },
        ip: { // IP验证
            validator: function(value, param) {
                return IP_PATTERN.test(value);
            },
            message: MSG_ERROR_INVALID_IP
        },
        ipSegs: { // IP前3段验证
            validator: function(value, param) {
                return IP_SEGS_PATTERN.test(value);
            },
            message: MSG_ERROR_INVALID_IP
        }
    });

    /** *********************************** */
    /* easyui & jQuery form functions below */
    /** *********************************** */

    /**
     * easyui在加载控件时会出现短暂的布局混乱，因此对于所有控件采用先用层隐藏，加载完成后显现的策略。
     * 
     * 在使用easyui的画面上都要在<body/>内第一行加上<div id="EasyuiLoading" class="jeasyui-loading"></div>
     */
    $.parser.onComplete = function() {
        $("#EasyuiLoading").remove();
    };

    /**
     * easyui一次渲染所有控件耗时非常长，为了提升用户体验，采用分步渲染的方法。
     * 在画面加载完成后，只渲染看得到的部分，即检索区、功能列表及一览。其他弹出画面都放在隐藏层中，在初次渲染之前从body中删除，渲染完成后再写回body，并在需要弹出前进行单独渲染。
     */
    $.parser.auto = false;
    $(function() {
        // 也可以将body的可见部分放在以下层内，配合$.parser.parse('#containerDiv');渲染出同样的效果，但是当浏览器窗口改变大小时，感觉速度较慢
        // <div id="containerDiv"
        // style="width:100%;height:100%;position:absolute;top:0px;left:0px;">
        // <div class="easyui-layout" data-options="fit:true">
        var hiddenDiv = $('#EasyuiHiddenWrapper').remove();
        $.parser.parse();
        hiddenDiv.appendTo('body');
    });

    // 定制datagrid的pager，去掉pageList
    $.fn.pagination.defaults.showPageList = false;

    /**
     * implement jQuery to support set css visibility: hidden
     */
    jQuery.fn.visible = function() {
        return this.css('visibility', 'visible');
    };

    jQuery.fn.invisible = function() {
        return this.css('visibility', 'hidden');
    };

    jQuery.fn.visibilityToggle = function() {
        return this.css('visibility', function(i, visibility) {
            return (visibility == 'visible') ? 'hidden' : 'visible';
        });
    };

    $(function() {
        // 禁用F5、Ctrl+F5，并禁用Backspace
        $(document).on("keydown", function(e) {
            return disableF5Backspace(e, null, Main.refreshThisTab);
        });
    });

    return {
        printProperties: printProperties,
        heredoc: heredoc,
        encodeHtml: encodeHtml,
        guid: guid,
        filterIpInputKey: filterIpInputKey,
        filterNumberInputKey: filterNumberInputKey,
        fixNumberboxValue: fixNumberboxValue,
        enterNumberboxValue: enterNumberboxValue,
        disableF5Backspace: disableF5Backspace,
        openLoading: openLoading,
        closeLoading: closeLoading,
        extractHtmlBody: extractHtmlBody,
        extractVisibleHtmlBody: extractVisibleHtmlBody,
        processJsonResult: processJsonResult,
        comboboxLoadFilter: comboboxLoadFilter,
        datagridLoadFilter: datagridLoadFilter,
        treeLoadFilter: treeLoadFilter,
        handleDatagridLoadSuccess: handleDatagridLoadSuccess,
        handleDatagridLoadError: handleDatagridLoadError,
        handleAjaxFormError: handleAjaxFormError,
        datetimeFormatter: datetimeFormatter,
        datetimeParser: datetimeParser,
        dateFormatter: dateFormatter,
        numberPrecisionFormatter: numberPrecisionFormatter,
        remarkFormatter: remarkFormatter,
        changeReasonFormatter: changeReasonFormatter,
        changeContentFormatter: changeContentFormatter,
        changeRemarkFormatter: changeRemarkFormatter,
        isYearFormatter: isYearFormatter,
        downloadFile: downloadFile,
        exportDatagridToExcel: exportDatagridToExcel
    }; // MainUtils
})(jQuery);

/**
 * 文件上传输入控件相关的Utils。
 */
var FileboxUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 删除一个文件所表示的文件框。
     * 
     * @param target 删除按钮
     */
    function removeFile(target) {
        $(target).closest('div.row').remove();
    }

    /**
     * 生成文件框，每个文件对应一个文件框。用于修改画面。
     * 
     * @param control 输入控件filebox
     * @param fileList 文件对象列表，结构见ArchiveBean.java
     */
    function render(control, fileList) {

        var width = control.filebox('options').width;

        var controlName = control.attr('textboxName');
        // 按照约定，文件字段的字段名，去掉末尾的'_'，再加上ReservedList，成为保留的文件列表回传的字段名
        var hiddenName = controlName.replace('_', '') + 'ReservedList';

        var $container = control.closest('div.container-fluid');
        $container.find('div.row:not(:first)').remove();

        for (var i = 0; i < fileList.length; i++) {
            var file = fileList[i];
            var id = file.id;
            var name = file.archiveName;
            var size = file.fileSize;

            var sizeText = _getSizeText(size);
            var icon = _getIcon(name);

            var row = _getBoxRow(id, hiddenName, name, icon, sizeText, width, true);
            $container.append(row);
        }
    }

    /**
     * 生成文件框，每个文件对应一个文件框。用于查看画面。
     * 
     * @param viewContainer 查看字段的容器div
     * @param fileList 文件对象列表，结构见ArchiveBean.java
     */
    function renderView(viewContainer, fileList) {

        viewContainer.empty();

        var $container = $('<div class="container-fluid"></div>');

        if (!fileList || fileList.length == 0) {
            $container.html('&nbsp;'); // 占位，使高度一致
        } else {
            for (var i = 0; i < fileList.length; i++) {
                var file = fileList[i];
                var id = file.id;
                var name = file.archiveName;
                var size = file.fileSize;

                var sizeText = _getSizeText(size);
                var icon = _getIcon(name);

                var row = _getBoxRow(id, null, name, icon, sizeText, false);
                $container.append(row);
            }
        }
        viewContainer.append($container);
    }

    /** ********************** */
    /* private functions below */
    /** ********************** */

    /**
     * 根据文件名，生成icon。
     * 
     * @param name 文件名
     */
    function _getIcon(name) {
        var ext = '';
        var indexDot = name.lastIndexOf('.');
        if (indexDot > -1) {
            ext = name.substring(indexDot + 1).toLowerCase();
        }

        var icon = 'fa-file-o';
        if (ext == 'mp3' || ext == 'wma') {
            icon = 'fa-file-audio-o';
        } else if (ext == 'java' || ext == 'cs' || ext == 'py' || ext == 'js' || ext == 'jsp'
                || ext == 'html') {
            icon = 'fa-file-code-o';
        } else if (ext == 'xls' || ext == 'xlsx') {
            icon = 'fa-file-excel-o';
        } else if (ext == 'pdf') {
            icon = 'fa-file-pdf-o';
        } else if (ext == 'doc' || ext == 'docx') {
            icon = 'fa-file-word-o';
        } else if (ext == 'ppt' || ext == 'pptx') {
            icon = 'fa-file-powerpoint-o';
        } else if (ext == 'jpg' || ext == 'jpeg' || ext == 'png' || ext == 'bmp' || ext == 'gif') {
            icon = 'fa-file-image-o';
        } else if (ext == 'avi' || ext == 'mp4' || ext == 'mkv' || ext == 'rmvb') {
            icon = 'fa-file-video-o';
        } else if (ext == 'zip' || ext == 'rar' || ext == '7z') {
            icon = 'fa-file-archive-o';
        } else if (ext == 'txt') {
            icon = 'fa-file-text-o';
        }

        return icon;
    }

    /**
     * 根据字节大小，生成文本表示。
     */
    function _getSizeText(size) {
        var sizeText = '';
        if (size <= 1024) {
            sizeText = size + ' Byte';
        } else if (size <= 9437184) {
            sizeText = (size / 1024).toFixed(2) + ' KB';
        } else {
            sizeText = (size / 1048576).toFixed(2) + ' MB';
        }
        return sizeText;
    }

    function _getBoxRow(id, hiddenName, name, icon, sizeText, width, isEdit) {

        var hidden = '';
        if (hiddenName) {
            hidden = '<input name="' + hiddenName + '" type="hidden" value="' + id + '"/>';
        }

        var widthText = '';
        if (width) {
            widthText = 'style="width:' + width + 'px;"';
        }

        var downloadUrl = BASE_URL + '/archive/project-archive/download.json?idsToDownload=' + id;
        var downloadJs = 'onclick="MainUtils.downloadFile(' + "'" + downloadUrl + "'" + ')"';

        var ret = '<div class="row row-gapped-top-s">'
                + hidden
                + '<div class="col-xs-12 col-gapped-none">'
                + '<div class="alert alert-info alert-info-origin file-box alert-dismissible"'
                + widthText
                + '>';
        if (isEdit) {
        	ret += '<button type="button" class="close" onclick="FileboxUtils.removeFile(this)"><span aria-hidden="true">&times;</span></button>';
        }
        ret += '<i class="fa fa-fw ' + icon + '" ' + downloadJs + '></i>'
            + '<div class="file-name" ' + downloadJs + '>' + name + '</div>'
            + '<div class="file-size">' + sizeText + '</div>' + '</div></div></div>';
        return ret;
    }

    return {
        removeFile: removeFile,
        render: render,
        renderView: renderView,
        getIcon: _getIcon
    }; // FileboxUtils
})(jQuery);

/**
 * EasyUI的输入控件相关的Utils。
 */
var EasyControlUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * reload指定容器内所有带有url属性的combobox控件
     * 
     * @param containerId 容器的id，用于定位
     */
    function reloadRemoteCombobox(containerId) {
        $(containerId + ' [data-field-widget="combobox"].easyui-combobox').each(function() {
            if ($(this).combobox('options').url) {
                $(this).combobox('reload');
            }
        });
    }

    /**
     * 获取easyui控件的值，控件都带有data-field-widget属性。
     * 
     * @param w 控件
     * @param wName
     */
    function getControlValue(w, wName) {

        var widget = wName || w.attr('data-field-widget');

        if ('checkbox' == widget) {
            return w.prop('checked') ? '1' : '0';
        }

        // TODO combobox需要区分是否支持多选
        if ('combobox' == widget) {
            var values = w[widget]('getValues');
            var str = "";
            for (i in values) {
                str = str + values[i] + ',';
            }
            str = str.substring(0, str.length - 1);
            return str;
        }
        return w[widget]('getValue');
    }

    /**
     * 获取easyui控件的文本，控件都带有data-field-widget属性。用来在tag显示。
     * 
     * @param w 控件
     * @param wName
     */
    function getControlText(w, wName) {

        var widget = wName || w.attr('data-field-widget');

        if ('checkbox' == widget) {
            return w.prop('checked') ? LABLE_YES : LABLE_NO;
        } else if ('combobox' == widget || 'datetimebox' == widget || 'datebox' == widget) {
            return w.combo('getText');
        }
        return w[widget]('getValue');
    }

    /**
     * 获取控件的name属性。
     * 
     * @param w 控件
     * @param wName
     */
    function getControlName(w, wName) {
        var widget = wName || w.attr('data-field-widget');
        var name;
        if ('checkbox' == widget) {
            name = w.attr('name');
        } else {
            name = w.attr('textboxName');
        }
        return name;
    }

    /**
     * 设置控件的值，控件都带有data-field-widget属性。
     * 
     * @param control
     * @param v value
     * @param wName widget name
     */
    function setControlValue(control, v, wName) {
        var widget = wName || control.attr('data-field-widget');
        if ('checkbox' == widget) {
            if ('1' === v || 1 === v) {
                control.prop('checked', true);
            } else {
                control.prop('checked', false);
            }
        } else if ('textbox' == widget) {
            control.textbox('setValue', v);
        } else if ('numberbox' == widget) {
            control.numberbox('setValue', v);
        } else if ('combobox' == widget) {
            var state = control.data('combobox');
            if (state.options.multiple) {
                control.combobox('setValues', v);
            } else {
                if (v != null) {// 解决下拉框值 类型为integer并且值为零时 ，在修改对话框中，下拉框不能选中问题
                    control.combobox('setValue', v + '');
                }
            }
        } else if ('datetimebox' == widget) {
            control.datetimebox('setValue', v);
        } else if ('datebox' == widget) {
            control.datebox('setValue', v);
        } else if ('timespinner' == widget) {
            control.timespinner('setValue', v);
        }
    }

    /**
     * 重置控件的值，控件都带有data-field-widget属性。
     * 
     * @param control
     * @param widget name
     */
    function resetControl(control, wName) {
        var widget = wName || control.attr('data-field-widget');
        if ('checkbox' == widget) {
            control.prop('checked', false);
        } else {
            if (control.is('[resettable]')) {
                control[widget]('reset');
            } else {
                control[widget]('clear');
            }
        }
    }

    /**
     * 启用或禁用easyui的控件，控件都带有data-field-widget属性。
     * 
     * @param w Widget
     * @param isEnabled
     */
    function toggleWidget(w, isEnabled) {

        var widget = w.attr('data-field-widget');
        var funcName = isEnabled ? 'enable' : 'disable';

        if (w.is('a')) {
            w.linkbutton(funcName);
        } else if ('checkbox' == widget) {
            w.prop('disabled', !isEnabled);
        } else {
            w[widget](funcName);
            if (!isEnabled) {
                // 防止这样的情况：在提交时，某些控件的输入可能验证失败，然后反选该控件后提交，此时会因为该控件仍处于验证失败状态而无法提交
                w[widget]('disableValidation');
            }
        }
    }

    /**
     * 将控件设置为只读。
     * 
     * @param control
     * @param widget name
     */
    function setReadonly(control, wName) {
        var widget = wName || control.attr('data-field-widget');

        if ('checkbox' == widget) {
            control.on('click', false);
        } else if ('textbox' == widget || 'numberbox' == widget) {
            control.textbox('readonly', true);
        } else if ('combobox' == widget || 'datetimebox' == widget || 'datebox' == widget) {
            control.combo('readonly', true);
        }
    }

    /** ********************** */
    /* private functions below */
    /** ********************** */


    return {
        reloadRemoteCombobox: reloadRemoteCombobox,
        getControlValue: getControlValue,
        getControlText: getControlText,
        getControlName: getControlName,
        setControlValue: setControlValue,
        resetControl: resetControl,
        toggleWidget: toggleWidget,
        setReadonly: setReadonly
    }; // EasyControlUtils
})(jQuery);

/**
 * 普通的HTML输入控件相关的Utils。
 */
var PlainControlUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 获取控件的值，控件都带有data-field-widget属性。
     * 
     * @param w 控件
     * @param wName
     */
    function getControlValue(w, wName) {

        var widget = wName || w.attr('data-field-widget');

        if ('checkbox' == widget) {
            // return w.prop('checked') ? '1' : '0';
            return w.switchbutton('options').checked ? '1' : '0';
        } else if ('datetimebox' == widget || 'datebox' == widget) {
            // EasyUI
            return w[widget]('getValue');
        } else if ('textbox' == widget || 'numberbox' == widget || 'passwordbox' == widget) {
            return w.val();
        } else if ('combobox' == widget) {
            // 支持多选
            var val = w.val();
            if ($.isArray(val)) {
                val = val.join();
            }
            return val;
        }
        return null;
    }

    /**
     * 获取easyui控件的文本，控件都带有data-field-widget属性。配合高级检索，用来在tag显示。
     * 
     * @param w 控件
     * @param wName
     */
    function getControlText(w, wName) {

        var widget = wName || w.attr('data-field-widget');

        if ('checkbox' == widget) {
            // return w.prop('checked') ? LABLE_YES : LABLE_NO;
            return w.switchbutton('options').checked ? LABLE_YES : LABLE_NO;
        } else if ('datetimebox' == widget || 'datebox' == widget) {
            return w.combo('getText');
        } else if ('combobox' == widget) {
            return w.find('option:selected').text();
        } else if (w.parent().is('div.input-group')) {
            return w.parent().find('input.form-control').val();
        }
        return w.val();
    }

    /**
     * 获取控件的name属性。
     * 
     * @param w 控件
     * @param wName
     */
    function getControlName(w, wName) {
        var widget = wName || w.attr('data-field-widget');
        var name;
        if ('checkbox' == widget) {
            // name = w.attr('name');
            name = w.attr('switchbuttonname');
        } else if ('textbox' == widget || 'numberbox' == widget || 'passwordbox' == widget
                || 'combobox' == widget) {
            name = w.attr('name');
        } else if ('datetimebox' == widget || 'datebox' == widget || 'timespinner' == widget
                || 'filebox' == widget) {
            name = w.attr('textboxName');
        } else {
            name = w.attr('name');
        }
        return name;
    }

    /**
     * 设置控件的值，控件都带有data-field-widget属性。
     * 
     * @param control
     * @param v value
     * @param wName widget name
     */
    function setControlValue(control, v, wName) {
        var widget = wName || control.attr('data-field-widget');
        if ('checkbox' == widget) {
            if ('1' === v || 1 === v) {
                // control.prop('checked', true);
                control.switchbutton('check');
            } else {
                // control.prop('checked', false);
                control.switchbutton('uncheck');
            }
        } else if ('textbox' == widget || 'numberbox' == widget || 'passwordbox' == widget) {
            control.val(v);
        } else if ('combobox' == widget) {
            // TODO 多选的情况也要支持
            control.val(v);
        } else if ('datetimebox' == widget) {
            // EasyUI
            control.datetimebox('setValue', v);
        } else if ('datebox' == widget) {
            // EasyUI
            control.datebox('setValue', v);
        } else if ('timespinner' == widget) {
            // EasyUI
            control.timespinner('setValue', v);
        } else if ('filebox' == widget) {
            control.filebox('clear');
            FileboxUtils.render(control, v);
        }
    }

    /**
     * 重置控件的值，控件都带有data-field-widget属性。
     * 
     * @param control
     * @param widget name
     */
    function resetControl(control, wName) {
        var widget = wName || control.attr('data-field-widget');
        if ('checkbox' == widget) {
            // control.prop('checked', false);
            control.switchbutton('reset');
        } else if ('datetimebox' == widget || 'datebox' == widget || 'filebox' == widget) {
            // EasyUI
            if (control.is('[resettable]')) {
                control[widget]('reset');
            } else {
                control[widget]('clear');
            }
        } else if ('textbox' == widget || 'numberbox' == widget || 'passwordbox' == widget) {
            if (control.parent().is('div.input-group')) {
                control.parent().find('input').val('');
            } else {
                control.val('');
            }
        } else if ('combobox' == widget) {
            control.val('');
            // control.removeAttr('selected');
        } else {
            control.val('');
        }
    }

    /**
     * 启用或禁用easyui的控件，控件都带有data-field-widget属性。
     * 
     * @param w Widget
     * @param isEnabled
     */
    function toggleWidget(w, isEnabled) {

        var widget = w.attr('data-field-widget');
        var funcName = isEnabled ? 'enable' : 'disable';

        if (w.is('a')) {
            w.linkbutton(funcName);
        } else if ('checkbox' == widget) {
            // w.prop('disabled', !isEnabled);
            w.switchbutton(funcName);
        } else if ('datetimebox' == widget || 'datebox' == widget) {
            w[widget](funcName);
            if (!isEnabled) {
                // 防止这样的情况：在提交时，某些控件的输入可能验证失败，然后反选该控件后提交，此时会因为该控件仍处于验证失败状态而无法提交
                w[widget]('disableValidation');
            }
        } else if ('textbox' == widget || 'numberbox' == widget || 'passwordbox' == widget) {

            if (w.parent().is('div.input-group')) {
                // 带按钮的textbox，弹出选择画面控件
                w.parent().find('button,input').prop('disabled', !isEnabled);
            } else {
                // 判断是否validatebox
                if (w.is('.easyui-validatebox')) {
                    w.validatebox(funcName);
                    if (!isEnabled) {
                        w.validatebox('disableValidation');
                    }
                } else {
                    w.prop('disabled', !isEnabled);
                }
            }
        } else if ('combobox' == widget) {
            w.prop('disabled', !isEnabled);
        } else {
            w.prop('disabled', !isEnabled);
        }
    }

    /**
     * 将控件设置为只读。
     * 
     * @param control
     * @param widget name
     */
    function setReadonly(control, wName) {
        var widget = wName || control.attr('data-field-widget');

        if ('checkbox' == widget) {
            // $this.on('click', false);
            control.switchbutton('readonly', true);
        } else if ('datetimebox' == widget || 'datebox' == widget) {
            control.combo('readonly', true);
        } else if ('textbox' == widget || 'numberbox' == widget || 'passwordbox' == widget) {

            if (control.parent().is('div.input-group')) {
                // 带按钮的textbox，弹出选择画面控件
                var controlParent = control.parent();
                if (!controlParent.is('readonly')) {
                    controlParent.addClass('readonly');
                }
                controlParent.find('button').prop('disabled', true);
            } else {
                // 判断是否validatebox
                if (control.is('.easyui-validatebox')) {
                    control.validatebox('readonly', true);
                } else {
                    control.prop('readonly', true);
                }
            }
        } else if ('combobox' == widget) {
            // 对select用prop('readonly', true)不生效，必须用attr
            control.attr('readonly', 'readonly');
            control.find('option:selected').prop('disabled', false);
            control.find('option:not(:selected)').prop('disabled', true);
        } else {
            control.prop('readonly', true);
        }
    }

    /** ********************** */
    /* private functions below */
    /** ********************** */


    return {
        getControlValue: getControlValue,
        getControlText: getControlText,
        getControlName: getControlName,
        setControlValue: setControlValue,
        resetControl: resetControl,
        toggleWidget: toggleWidget,
        setReadonly: setReadonly
    }; // PlainControlUtils
})(jQuery);

/**
 * 配合plainFieldControl.tag和plainFilterFieldControl.tag用，处理HTML的表单输入控件。
 * 当启用此PlainControlUtils时，要将所有g:fieldControl换成g:plainFieldControl，将g:filterFieldControl换成g:plainFilterFieldControl。
 */
var ControlUtils = PlainControlUtils;
/**
 * 配合fieldControl.tag和filterFieldControl.tag用，处理EasyUI的表单输入控件。
 * 当启用此EasyControlUtils时，要将所有g:plainFieldControl换成g:fieldControl，将g:plainFilterFieldControl换成g:filterFieldControl。
 */
// var ControlUtils = EasyControlUtils;
/**
 * easyui-dialog相关的Utils。
 */
var EasyDialogUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 用指定url最大化打开模态窗口，窗口保留标题栏、无地址栏、 无菜单栏、无工具栏、有滚动条、无状态栏、可调整画面大小。
     * 
     * @param url 窗口url
     * @param name 窗口name
     */
    function openMaxModal(url, name) {
        var iWidth = screen.availWidth;
        var iHeight = screen.availHeight;

        var wd = _openModal(url, name, iWidth, iHeight);

        return wd;
        // wd.resizeTo(screen.width, screen.height); // 设置新页面的大小
    }

    /**
     * easyui的dialog移动时的回调，防止dialog移动到视窗之外。
     * 
     * @param l left值
     * @param t top值
     */
    function onDialogMove(l, t) {
        // console.log('origin from (' + l + ', ' + t + ')');
        var ll = l;
        var tt = t;
        var needMove = false;

        if (l < 0) {
            ll = 0;
            needMove = true;
        }
        if (t < 0) {
            tt = 0;
            needMove = true;
        }

        var panel = $(this).dialog('panel');

        // var maxWidth = $('body').width();
        var maxWidth = $('html').width();
        var oWidth = panel.outerWidth();
        if (oWidth > maxWidth) {
            if (l == 0) { // dialog的宽度超过body，且当前left=0时，必须停止后续的move动作，否则将进入死循环
                return;
            }
            ll = 0;
            needMove = true;
        } else if (l + oWidth > maxWidth) {
            ll = maxWidth - oWidth;
            needMove = true;
        }

        // console.log('Need move after width handling? ' + needMove);

        // var maxHeight = $('body').height();
        var maxHeight = $('html').height();
        var oHeight = panel.outerHeight();
        if (oHeight > maxHeight) {
            if (t == 0) { // dialog的高度超过body，且当前top=0时，必须停止后续的move动作，否则将进入死循环
                return;
            }
            tt = 0;
            needMove = true;
        } else if (t + oHeight > maxHeight) {
            tt = maxHeight - oHeight;
            needMove = true;
        }

        // console.log('Need move after height handling? ' + needMove);
        // console.log('body(' + maxWidth + ', ' + maxHeight + ')');
        // console.log('dialog(' + oWidth + ', ' + oHeight + ')');

        if (needMove) {
            // console.log('move to (' + ll + ', ' + tt + ')');
            $(this).dialog('move', {
                left: ll,
                top: tt
            });
        }
    }

    /**
     * 与extractVisibleHtmlBody配合使用，在关闭extractVisibleHtmlBody的宿主时，销毁异步获取的dialog。
     * 
     * @param dialogId
     */
    function destroyDialog(dialogId) {
        var $dialog = $(dialogId);
        if ($dialog.length > 0) {
            if ($dialog.parent().prop('rendered')) {
                $dialog.dialog('destroy');
            } else {
                $dialog.parent().remove();
            }
        }
    }

    /**
     * 打开带有form的dialog。
     * 
     * @param formDialogId CSS selector，形如"#myFormDialogId"
     */
    function openFormDialog(formDialogId) {

        if (!formDialogId) {
            return;
        }
        var $dialog = $(formDialogId);

        function process(isFirstLoad) {
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

    /**
     * 初始化并弹出dialog，如果传入了回调函数，则在弹出前先调用回调。
     * 
     * @param dialogId CSS selector，形如"#myDialogId"
     * @param beforeOpenCallback 弹出前回调，接受一个参数，表示是否首次弹出。如果该函数返回false，则不弹出dialog
     */
    function openDialog(dialogId, beforeOpenCallback) {
        if (!dialogId) {
            return;
        }
        var $dialog = $(dialogId);

        function open() {
            $dialog.dialog('open').dialog('center').scrollTop(0);
        }

        var needOpen = true;

        var $dialogWrapper = $dialog.parent();
        if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
            MainUtils.openLoading();
            setTimeout(function() {
                $.parser.parse($dialogWrapper);

                // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                $dialogWrapper.remove();
                $dialog.parent().prop('rendered', true);

                if (beforeOpenCallback && $.isFunction(beforeOpenCallback)) {
                    needOpen = beforeOpenCallback(true);
                }

                MainUtils.closeLoading();

                if (needOpen) {
                    open();
                }
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            if (beforeOpenCallback && $.isFunction(beforeOpenCallback)) {
                needOpen = beforeOpenCallback(false);
            }
            if (needOpen) {
                open();
            }
        }
    }

    /**
     * 关闭带有form的dialog，如"新建/修改"弹出画面等。
     * 
     * @param formDialogId CSS selector，形如"#myFormDialogId"
     */
    function closeFormDialog(formDialogId) {
        if (formDialogId) {
            $(formDialogId).dialog('close');
            // closeValidateTooltip();
        } else {
            $('#_FormDialog').dialog('close'); // 默认ID
        }
    }

    /**
     * 打印页面
     */
    function pringPage(formDialogId) {
        var height = $(formDialogId).css('max-height');
        $(formDialogId).css('max-height', '100%');
        $(formDialogId).jqprint({
            debug: false,
            importCSS: true,
            printContainer: true,
            operaSupport: false
        });
        $(formDialogId).css('max-height', height);
    }

    /** ********************** */
    /* private functions below */
    /** ********************** */

    /**
     * 用指定url打开模态窗口，窗口保留标题栏、无地址栏、 无菜单栏、无工具栏、有滚动条、无状态栏、可调整画面大小。
     * 
     * @param url 窗口url
     * @param name 窗口name
     * @param iWidth 窗口宽度，单位px
     * @param iHeight 窗口高度，单位px
     */
    function _openModal(url, name, iWidth, iHeight) {
        var spcs = 'height='
                + iHeight
                + ',width='
                + iWidth
                + ',top=0,left=0,toolbar=no,titlebar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no,channelmode=yes';
        var win = window.open(url, name, spcs);

        if (win != null) {
            win.moveTo(0, 0); // 将新页面打开位置定位在屏幕左上角
            win.focus();// 新页面获得焦点
        }
        return win;
    }

    return {
        openMaxModal: openMaxModal,
        onDialogMove: onDialogMove,
        destroyDialog: destroyDialog,
        openFormDialog: openFormDialog,
        openDialog: openDialog,
        closeFormDialog: closeFormDialog,
        pringPage: pringPage
    }; // EasyDialogUtils
})(jQuery);

/**
 * 检索相关的逻辑。
 */
var FilterUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 弹出用于高级检索的带有Form的Dialog，弹出前对Form进行reset，然后将CONDITION_MAP_OBJ填充到检索内各字段控件。
     * 
     * @param dialogId CSS selector，形如"#myDialogId"
     * @param datagridId
     * @param callback 包含回调函数的对象
     */
    function openAdvSearchFormDialog(dialogId, datagridId, callback) {

        if (!dialogId) {
            return;
        }

        var $dialog = $(dialogId);

        // 为了提升性能，使用的object来保存高级检索内容，其中每个成员的结构同FilterFieldInfoDto.java
        var CONDITION_MAP_OBJ = $(datagridId).data('CONDITION_MAP_OBJ');
        if (!CONDITION_MAP_OBJ) {
            CONDITION_MAP_OBJ = {};
        }

        function process(isFirstLoad) {

            var $form = $dialog.find('form');

            var $leadingCheckbox = $form.find('input[data-value-type]');

            // 遍历弹出画面的所有字段，如果进行过检索，则启用赋值，否则禁用
            $leadingCheckbox.each(function() {
                var cb = $(this);
                var isCheckedNow = cb.prop('checked');
                var con = CONDITION_MAP_OBJ[cb.attr('name')];

                // 设置checkbox
                if (con && !isCheckedNow) {
                    cb.prop('checked', true);
                } else if (!con && isCheckedNow) {
                    cb.prop('checked', false);
                }

                var controls = cb.parent().nextAll().last().find('[data-field-control]');
                controls.each(function() {
                    var $this = $(this);

                    // 控件赋值
                    // 不管弹出画面内目前是什么状态，都以CONDITION_MAP_OBJ为准
                    if (con) {

                        // 即使checkbox选中的情况下，也要重新赋值，因为有可能打开弹出画面，修改了筛选条件，然后没有执行筛选就关闭了弹出画面。

                        if (con.filterSearchBy == '1') { // 按值过滤，只有一个控件
                            ControlUtils.setControlValue($this, con.value);

                            // 兼容带按钮的textbox，弹出选择画面控件
                            if ($this.parent().is('div.input-group')) {
                                ControlUtils.setControlValue($this.parent().find(
                                        'input.form-control'), con.valueText);
                            }

                        } else { // 按范围过滤，有from和to两个控件
                            if ($this.is('[data-from]')) {
                                ControlUtils.setControlValue($this, con.from);
                            } else {
                                ControlUtils.setControlValue($this, con.to);
                            }
                        }
                        ControlUtils.toggleWidget($this, true);
                    } else {
                        ControlUtils.resetControl($this);
                        ControlUtils.toggleWidget($this, false);
                    }

                    // 非首次弹出，重新加载带有url的combobox
                    if (!isFirstLoad) {
                        if ($this.is('[data-field-widget="combobox"].easyui-combobox')
                                && $this.combobox('options').url) {
                            $this.combobox('reload');
                        }
                    }
                });

                if (callback && callback.onInitAdvSearchFormWidget) { // 高级检索弹出画面的每个widget初始化后回调，通常用来设置字段联动
                    callback.onInitAdvSearchFormWidget(cb, controls, isFirstLoad);
                }
            });

            $form.form('disableValidation');

            var totalCount = $leadingCheckbox.size();
            var checkedCount = 0;
            var i = null;
            for (i in CONDITION_MAP_OBJ) {
                checkedCount++;
            }

            var $selectAll = $dialog.next().find('input[data-check-all]');
            $selectAll.prop('checked', totalCount == checkedCount); // 设置全选checkbox状态

            $selectAll.parent().next().each(function() { // 确定按钮
                ControlUtils.toggleWidget($(this), checkedCount > 0);
            });

            $dialog.dialog('open').dialog('center').scrollTop(0);

            if (callback && callback.onOpenAdvSearchFormDialog) { // 弹出后回调
                callback.onOpenAdvSearchFormDialog();
            }
        }

        var $dialogWrapper = $dialog.parent();
        if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
            MainUtils.openLoading();
            setTimeout(function() {
                $.parser.parse($dialogWrapper);

                // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                $dialogWrapper.remove();
                $dialog.parent().prop('rendered', true);

                if (callback && callback.onInitAdvSearchFormDialog) {
                    callback.onInitAdvSearchFormDialog();
                }
                MainUtils.closeLoading();
                process(true);
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(false);
        }
    }

    /**
     * 检索按钮事件处理，整理普通检索区的检索项并准备好SEARCH_ARRAY后调用_doSearch(...)重新加载datagrid。
     * 
     * @param datagridId
     * @param formId 普通检索的form的id
     */
    function searchSimple(datagridId, formId) {

        if (!formId || !datagridId) {
            return;
        }

        var $form = $(formId);
        if (!$form.form('validate')) {
            return;
        }

        // 用来保存普通检索内容的数组，其中每个成员的结构同FilterFieldInfoDto.java
        var SEARCH_ARRAY = new Array();
        $(datagridId).data('SEARCH_ARRAY', SEARCH_ARRAY);

        $form.find('span[data-value-type]').each(function() {
            var cb = $(this);
            var con = {};
            con.fieldName = cb.attr('data-name');
            con.valueType = cb.attr('data-value-type');
            con.filterSearchBy = cb.attr('data-filter-search-by');
            con.valueMatchMode = cb.attr('data-value-match-mode');

            if (con.filterSearchBy == '1') { // 按值过滤，只有一个控件

                // 兼容带按钮的textbox，弹出选择画面控件
                var control = cb.parent().find('[data-field-control]');
                con.value = ControlUtils.getControlValue(control);
                if (con.value) { // 各种input和checkbox取到的值都是字符串，所以不会出现false和数值0
                    SEARCH_ARRAY.push(con);
                }

            } else { // 按范围过滤，有from和to两个控件
                var controls = cb.nextAll('[data-field-control]');
                con.from = ControlUtils.getControlValue(controls.first());
                con.to = ControlUtils.getControlValue(controls.last());

                if (con.from || con.to) {
                    SEARCH_ARRAY.push(con);
                }
            }
        });

        _doSearch(datagridId);
    }

    // 为了提升性能，使用的object来保存高级检索内容，其中每个成员的结构同FilterFieldInfoDto.java
    // var CONDITION_MAP_OBJ = {};

    // 用来保存普通检索内容的数组，其中每个成员的结构同FilterFieldInfoDto.java
    // var SEARCH_ARRAY = new Array();

    /**
     * 高级检索弹出画面的确定事件处理。验证通过后，重新设置CONDITION_MAP_OBJ，调用_initFilterTags初始化tag区，
     * 准备好CONDITION_MAP_OBJ后调用_doSearch(...)重新加载datagrid。
     * 
     * @param formDialogId 包含高级检索字段form的dialog的id
     * @param datagridId
     * @param simpleSearchFormId 普通检索的form的id
     * @param callback 包含回调函数的对象，在清空检索的过程中，用以调用回调
     */
    function searchAdv(formDialogId, datagridId, simpleSearchFormId, callback) {
        if (!formDialogId || !datagridId) {
            return;
        }

        var $dialog = $(formDialogId);
        var $form = $dialog.find('form');

        $form.form('enableValidation');
        if (!$form.form('validate')) {
            return;
        }

        var CONDITION_MAP_OBJ = {};
        $(datagridId).data('CONDITION_MAP_OBJ', CONDITION_MAP_OBJ);

        $form.find('input[data-value-type]:checked').each(
                function() {
                    var cb = $(this);
                    var con = {};
                    con.fieldName = cb.attr('name');
                    con.valueType = cb.attr('data-value-type');
                    con.filterSearchBy = cb.attr('data-filter-search-by');
                    con.valueMatchMode = cb.attr('data-value-match-mode');
                    con.captionName = cb.parent().next().text();

                    if (con.filterSearchBy == '1') { // 按值过滤，只有一个控件

                        var control = cb.parent().nextAll().last().find('[data-field-control]');
                        con.value = ControlUtils.getControlValue(control);

                        var widget = control.attr('data-field-widget');
                        if ('checkbox' == widget || 'combobox' == widget || 'datetimebox' == widget
                                || 'datebox' == widget || control.parent().is('div.input-group')) {
                            con.valueText = ControlUtils.getControlText(control); // 用来在tag显示
                        }
                    } else { // 按范围过滤，有from和to两个控件
                        var controls = cb.parent().nextAll().last().find('[data-field-control]');
                        con.from = ControlUtils.getControlValue(controls.first());
                        con.to = ControlUtils.getControlValue(controls.last());
                    }

                    CONDITION_MAP_OBJ[con.fieldName] = con;
                });

        _initFilterTags(datagridId, simpleSearchFormId, callback);

        EasyDialogUtils.closeFormDialog(formDialogId);

        _doSearch(datagridId);
    }

    /**
     * 清空检索。清除CONDITION_MAP_OBJ和SEARCH_ARRAY，删除tag区，清空普通检索区，调用_doSearch(...)重新加载datagrid。
     * 
     * @param datagridId
     * @param formId 普通检索的form的id
     * @param callback 包含回调函数的对象，在清空检索的过程中，用以调用回调
     */
    function clearSearch(datagridId, formId, callback) {

        if (!datagridId) {
            return;
        }
        if (!formId) {
            return;
        }

        // 对普通检索区总是执行清空
        $(formId).form('reset');

        // 针对秤列表界面清空检索时清空子型号下拉框数据处理
        if (callback && callback.beforeClearSearch) {
            callback.beforeClearSearch();
        }

        // 用来保存普通检索内容的数组，其中每个成员的结构同FilterFieldInfoDto.java
        var SEARCH_ARRAY = $(datagridId).data('SEARCH_ARRAY');

        // 为了提升性能，使用的object来保存高级检索内容，其中每个成员的结构同FilterFieldInfoDto.java
        var CONDITION_MAP_OBJ = $(datagridId).data('CONDITION_MAP_OBJ');

        if ((!CONDITION_MAP_OBJ || $.isEmptyObject(CONDITION_MAP_OBJ))
                && (!SEARCH_ARRAY || SEARCH_ARRAY.length == 0)) {
            return;
        }

        if (CONDITION_MAP_OBJ && !$.isEmptyObject(CONDITION_MAP_OBJ)) {
            $(datagridId).data('CONDITION_MAP_OBJ', {})
            _initFilterTags(datagridId, formId, callback);
        }

        if (SEARCH_ARRAY && SEARCH_ARRAY.length > 0) {
            $(datagridId).data('SEARCH_ARRAY', new Array());
        }

        _doSearch(datagridId);
    }


    /**
     * 高级检索画面上，点击某个字段的checkbox时，联动全选checkbox。
     * 
     * @param dialogId
     * @param isOkButtonLinked 是否处理确定按钮
     */
    function checkLinkedCheckAll(dialogId, isOkButtonLinked) {

        if (!dialogId) {
            return;
        }

        var $dialog = $(dialogId);
        var $allCh = $dialog.find('input[name]:checkbox');
        var totalCount = $allCh.size();
        var checkCount = $allCh.filter(':checked').size();

        var $selectAllCh = $dialog.next().find('input[data-check-all]');
        $selectAllCh.prop('checked', checkCount == totalCount); // '全选'checkbox

        if (isOkButtonLinked) {
            $selectAllCh.parent().next().each(function() { // 确定按钮
                ControlUtils.toggleWidget($(this), checkCount > 0);
            });
        }
    }

    /**
     * 高级检索弹出画面上，点击某个字段的checkbox的click事件处理。当勾选时，启用对应的字段控件。否则禁用字段控件。
     * 如果只有一个勾选的checkbox，则反选'全选'checkbox并启用'确定'按钮。如果所有checkbox都选中，则勾选'全选'checkbox并启用'确定'按钮。
     * 如果没有勾选的checkbox，则反选'全选'checkbox并禁用'确定'按钮。
     * 
     * @param currentCheckbox 当前点击的checkbox
     * @param dialogId 弹出画面ID
     */
    function toggleCheckFormField(currentCheckbox, dialogId) {
        var $curCh = $(currentCheckbox);
        var isChecked = $curCh.prop('checked');
        $curCh.parent().nextAll().last().find('[data-field-control]').each(function() { // 处理该checkbox对应所有字段控件
            ControlUtils.toggleWidget($(this), isChecked);
        });

        checkLinkedCheckAll(dialogId, true);
    }


    /**
     * 高级检索弹出画面上，全选checkbox的click事件处理。 当勾选时，选中所有字段的checkbox，并启用所有字段控件，启用'确定'按钮。
     * 否则反选所有字段的checkbox，并禁用所有字段控件，禁用'确定'按钮。
     * 
     * @param dialogId 弹出画面ID
     */
    function toggleCheckAllFormFields(dialogId) {

        var $dialog = $(dialogId);
        var $selectAllCh = $dialog.next().find('input[data-check-all]');
        var isEnabled = $selectAllCh.prop('checked');
        $dialog.find('[data-field-control]').each(function() { // 处理字段控件
            ControlUtils.toggleWidget($(this), isEnabled);
        });

        $selectAllCh.parent().next().each(function() { // 确定按钮
            ControlUtils.toggleWidget($(this), isEnabled);
        });

        $dialog.find('input[data-value-type]').each(function() { // 处理所有字段对应的checkbox
            $(this).prop('checked', isEnabled);
        });
    }

    /** ********************** */
    /* private functions below */
    /** ********************** */

    /**
     * 将普通检索和高级检索的内容合并，提交并刷新datagrid。
     * 
     * @param datagridId
     */
    function _doSearch(datagridId) {

        if (!datagridId) {
            return;
        }

        var tempArray = new Array();

        // 用来保存普通检索内容的数组，其中每个成员的结构同FilterFieldInfoDto.java
        var SEARCH_ARRAY = $(datagridId).data('SEARCH_ARRAY');

        // 为了提升性能，使用的object来保存高级检索内容，其中每个成员的结构同FilterFieldInfoDto.java
        var CONDITION_MAP_OBJ = $(datagridId).data('CONDITION_MAP_OBJ');

        // 左侧树或一览中，选中项产生的检索信息
        var EXTRA_FILTER_OBJ = $(datagridId).data('EXTRA_FILTER_OBJ');

        if (SEARCH_ARRAY) {
            $.merge(tempArray, SEARCH_ARRAY);
        }
        if (CONDITION_MAP_OBJ) {
            for ( var i in CONDITION_MAP_OBJ) {
                tempArray.push(CONDITION_MAP_OBJ[i]);
            }
        }
        if (EXTRA_FILTER_OBJ) {
            tempArray.push(EXTRA_FILTER_OBJ);
        }

        var commonFilter = {
            filterFieldList: tempArray
        };

        var param = {
            commonFilterJson: JSON.stringify(commonFilter)
        // list方法必须接受一个名为commonFilterJson的参数
        };

        // 兼容tree和datagrid
        var $target = $(datagridId);
        if ($target.is('.easyui-tree')) {
            $target.tree('options').queryParams = param;
            $target.tree('reload');
        } else {
            $target.datagrid('load', param);
        }
    }

    /**
     * 初始化高级检索的tag区域，根据高级检索的条件，绘制tag区。
     * 
     * @param datagridId
     * @param formId 普通检索的form的id
     * @param callback 包含回调函数的对象，在清空检索的过程中，用以调用回调
     */
    function _initFilterTags(datagridId, formId, callback) {

        var wrapper = $(formId).closest('div.filter-wrapper');
        var row = wrapper.find('div.filter-tag-row');

        var c = wrapper.parent().parent().parent('.easyui-layout'); // 自适应easyui和bootstrap两种layout
        var isEasyuiLayout = c.length > 0;

        // 为了提升性能，使用的object来保存高级检索内容，其中每个成员的结构同FilterFieldInfoDto.java
        var CONDITION_MAP_OBJ = $(datagridId).data('CONDITION_MAP_OBJ');

        if (!CONDITION_MAP_OBJ || $.isEmptyObject(CONDITION_MAP_OBJ)) { // 清空检索功能
            row.remove();
            c.layout('resize', { // 调整north和center区的高度
                height: '100%'
            });
        } else { // 高级检索点击确定
            if (row.size() == 0) {
                var p = isEasyuiLayout ? c.layout('panel', 'north') : null;

                row = $('<div class="filter-tag-row"><div class="filter-tag-box"><div class="filter-tag-inner-box"><div class="filter-tags"></div><div class="filter-tag-pin-wrapper"><a class="filter-tag-pin"><span class="filter-tag-pin-icon"></span></a></div></div></div></div>');
                row.appendTo(wrapper);

                if (isEasyuiLayout) {
                    c.layout('resize', { // 调整north和center区的高度
                        height: '100%'
                    });
                    p.parent().css('overflow', 'visible'); // 必须设置overflow:visible才能让层下浮
                }

                var box = row.children('div.filter-tag-box');
                box.mouseenter(function() {
                    box.addClass('filter-tag-box-hover');
                });
                box.mouseleave(function() {
                    box.removeClass('filter-tag-box-hover');
                });

                var pin = box.find('a.filter-tag-pin');
                pin.on('click', function() { // 图钉事件
                    if (pin.hasClass('filter-tag-pin-selected')) {
                        pin.removeClass('filter-tag-pin-selected');
                        box.mouseleave(function() {
                            box.removeClass('filter-tag-box-hover');
                        });
                    } else {
                        pin.addClass('filter-tag-pin-selected');
                        box.off('mouseleave');
                    }
                });
            }

            // 清空并重新加载tag
            row.find('a.filter-tag').remove();
            for ( var cIndex in CONDITION_MAP_OBJ) {

                if (callback && callback.beforeAppendFilterTag) { // 添加一个检索条件tag前的回调
                    if (!callback.beforeAppendFilterTag(cIndex)) {
                        continue;
                    }
                }

                var closeBtn = $('<span class="filter-tag-close"></span>');
                closeBtn.bind("click", function() { // 删除一个tag

                    // 每次删除一个tag，都要调整CONDITION_MAP_OBJ，并重新加载datagrid
                    var t = $(this).parent();
                    var tName = t.attr('data-name');
                    delete CONDITION_MAP_OBJ[tName];

                    t.remove();

                    if (callback && callback.onDeleteFilterTag) { // 删除一个检索条件后的回调
                        callback.onDeleteFilterTag(tName);
                    }

                    // 删除最后一个tag时，tag区域同时删除
                    if (row.find('a.filter-tag').size() == 0) {
                        row.remove();
                        if (isEasyuiLayout) {
                            c.layout('resize', {
                                height: '100%'
                            });
                        }
                    }

                    _doSearch(datagridId);
                });

                var con = CONDITION_MAP_OBJ[cIndex];

                // 生成tag上的显示文本
                var valueText = null;
                if (con.filterSearchBy == '1') { // 按值过滤

                    valueText = con.valueText; // combo类型的字面值
                    if (!valueText) { // 如果非combo类型
                        if (con.valueType == '0') { // short
                            valueText = con.value == 1 ? LABLE_YES : LABLE_NO;
                        } else if (con.valueType == '4') { // string
                            var t = $.trim(con.value);
                            valueText = t.length <= 4 ? t : t.substr(0, 4) + '...';
                        } else { // 数值类型
                            valueText = con.value;
                        }
                    }
                } else { // 按范围过滤
                    if (con.from && con.to) {
                        valueText = con.from + '~' + con.to;
                    } else if (con.from) {
                        valueText = '>=' + con.from;
                    } else if (con.to) {
                        valueText = '<=' + con.to;
                    }
                }

                var tagText = con.captionName;
                if ($.trim(valueText)) {
                    tagText = tagText + ' : ' + valueText;
                }

                var filterTag = $('<a class="filter-tag" data-name="' + con.fieldName
                        + '"><span class="filter-tag-text">' + tagText + '</span></a>');
                filterTag.append(closeBtn);

                row.find('div.filter-tags').append(filterTag);
            }
        }
    }

    return {
        openAdvSearchFormDialog: openAdvSearchFormDialog,
        searchSimple: searchSimple,
        searchAdv: searchAdv,
        clearSearch: clearSearch,
        doSearch: _doSearch,
        checkLinkedCheckAll: checkLinkedCheckAll,
        toggleCheckFormField: toggleCheckFormField,
        toggleCheckAllFormFields: toggleCheckAllFormFields
    }; // FilterUtils
})(jQuery);

/**
 * 增删改查相关的逻辑。
 */
var CrudUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 弹出用于新增记录的带有Form的Dialog，弹出前对Form进行reset。
     * 
     * @param dialogId CSS selector，形如"#myDialogId"
     * @param callback 包含回调函数的对象
     * @deprecated
     */
    function openAddFormDialog(dialogId, callback) {

        if (!dialogId) {
            return;
        }
        var $dialog = $(dialogId);

        function process(isFirstLoad, callback) {

            $dialog.find('[data-field-widget]').each(
                    function() {
                        var $this = $(this);
                        var widget = $this.attr('data-field-widget');

                        // 新建画面设置默认值 160ms
                        if ($this.is('[data-default-value]')) {
                            var defaultValue = $this.attr('data-default-value');
                            ControlUtils.setControlValue($this, defaultValue, widget);
                        } else {
                            ControlUtils.resetControl($this, widget);
                        }

                        // 去掉修改时的只读设置
                        // if ($this.is('[data-edit-readonly]')) {
                        // if ('checkbox' == widget) {
                        // $this.off('click');
                        // } else if ('textbox' == widget || 'numberbox' == widget) {
                        // $this.textbox('readonly', false);
                        // } else if ('combobox' == widget || 'datetimebox' == widget
                        // || 'datebox' == widget) {
                        // $this.combo('readonly', false);
                        // }
                        // }

                        if (!isFirstLoad) {
                            // 非首次弹出，重新加载带有url的combobox
                            if ($this.is('[data-field-widget="combobox"].easyui-combobox')
                                    && $this.combobox('options').url) {
                                $this.combobox('reload');
                            }
                        }

                        if (callback && callback.onInitAddFormWidget) { // 新建弹出画面的每个widget初始化后回调，通常用来设置字段联动
                            callback.onInitAddFormWidget($this, isFirstLoad);
                        }
                    });

            $dialog.find('form').form('disableValidation');

            // if (dialogTitle) {
            // var $panelHeader = $dialog.dialog('header');
            // $panelHeader.find('div.panel-title').html(dialogTitle);
            // }

            $dialog.next().find('input[data-copy-reserve-checkbox]').prop('checked', false);

            $dialog.dialog('open').dialog('center').scrollTop(0);

            if (callback && callback.onOpenAddFormDialog) { // 弹出后回调
                callback.onOpenAddFormDialog();
            }
        }

        var $dialogWrapper = $dialog.parent();
        if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
            MainUtils.openLoading();
            setTimeout(function() {
                $.parser.parse($dialogWrapper);

                // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                $dialogWrapper.remove();
                $dialog.parent().prop('rendered', true);

                if (callback && callback.onInitAddFormDialog) {
                    callback.onInitAddFormDialog();
                }
                process(true, callback);
                MainUtils.closeLoading();
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(false, callback);
        }
    }

    /**
     * 弹出用于修改记录的带有Form的Dialog，弹出前通过Ajax向服务端发送待查找的数据的id，并将返回自服务端的JSON结果加载到指定的Form中。
     * 
     * 双击datagrid的行或者点击修改按钮时调用。
     * 
     * @param dialogId CSS selector，形如"#myDialogId"
     * @param url
     * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新。为空时，rowIndex作为recordId使用。
     * @param rowIndex 双击datagrid的行时使用。datagrid中数据的index，如果提供了datagridId，但未提供rowIndex，则根据数据的id查找
     * @param callback 包含回调函数的对象
     * @deprecated
     */
    function openEditFormDialog(dialogId, url, gridId, rowIndex, callback) {
        if (!dialogId || !url || (!gridId && !rowIndex)) {
            return;
        }

        // 获取选中的单条记录id
        var $grid = null;
        var recordId = null;
        if (gridId) {
            $grid = $(gridId);
            if ($.isNumeric(rowIndex)) {
                recordId = $grid.datagrid('getRows')[rowIndex].id;
            } else {

                var selectedRows = $grid.datagrid('getSelections');
                if (selectedRows.length == 1) {
                    recordId = selectedRows[0].id;
                } else {
                    if (selectedRows.length == 0) {
                        // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
                        AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    } else if (selectedRows.length > 1) {
                        // $.messager.alert(LABLE_WARNING, MSG_WARN_MORE_THAN_ONE_SELECT,
                        // 'warning');
                        AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                    }
                    return;
                }
            }
        } else {
            // gridId为空时，rowIndex作为recordId使用
            recordId = rowIndex;
        }

        var $dialog = $(dialogId);

        function process(isFirstLoad, callback) {

            var paramData = {};
            paramData['id'] = recordId;
            var options = {
                url: url,
                type: 'POST',
                dataType: 'json',
                data: paramData,
                success: function(response, statusText, xhr, jqForm) {

                    if (MainUtils.processJsonResult(response, false)) {
                        var json = response.dataMap.returnObj;

                        $dialog.find('input[name="id"]').val(recordId);

                        // 将服务器返回的JSON加载到Form(easyui-form的load效率太低)
                        $dialog
                                .find('[data-field-widget]')
                                .each(
                                        function() {
                                            var $this = $(this);

                                            // 修改弹出画面的每个widget初始化前回调，通常用来设置字段联动
                                            if (callback && callback.beforeInitEditFormWidget) {
                                                callback.beforeInitEditFormWidget($this,
                                                        isFirstLoad, response);
                                            }

                                            var widget = $this.attr('data-field-widget');

                                            var name = ControlUtils.getControlName($this, widget);

                                            // 加载值
                                            var value = json[name];
                                            if ('filebox' == widget) {
                                                // 按照约定，文件字段的字段名，去掉末尾的'_'，再加上List，成为文件列表的字段名
                                                value = json[name.replace('_', '') + 'List'];
                                            }
                                            if (typeof value != 'undefined') {
                                                ControlUtils.setControlValue($this, value, widget);
                                            } else {
                                                ControlUtils.resetControl($this, widget);
                                            }

                                            // 修改画面设置只读
                                            if ($this.is('[data-edit-readonly]')) {
                                                ControlUtils.setReadonly($this, widget);
                                            }

                                            if (!isFirstLoad) {
                                                // 非首次弹出，重新加载带有url的combobox
                                                if ($this
                                                        .is('[data-field-widget="combobox"].easyui-combobox')
                                                        && $this.combobox('options').url) {
                                                    $this.combobox('reload');
                                                }
                                            }

                                            // 修改弹出画面的每个widget初始化后回调，通常用来设置字段联动
                                            if (callback && callback.onInitEditFormWidget) {
                                                callback.onInitEditFormWidget($this, isFirstLoad,
                                                        response);
                                            }
                                        });

                        // 将服务端返回JSON更新到datagrid
                        if ($grid) {
                            if (!$.isNumeric(rowIndex)) {
                                rowIndex = $grid.datagrid('getRowIndex', recordId);
                            }

                            // 更新前回调
                            var needUpdate = true;
                            if (callback && callback.beforeOpenEditUpdateGridRow) {
                                needUpdate = callback.beforeOpenEditUpdateGridRow($grid, rowIndex,
                                        json);
                            }

                            if (needUpdate) {
                                $grid.datagrid('updateRow', {
                                    index: rowIndex,
                                    row: json
                                });
                            }
                        }

                        $dialog.find('form').form('disableValidation');

                        // if (dialogTitle) {
                        // var $panelHeader = $dialog.dialog('header');
                        // $panelHeader.find('div.panel-title').html(dialogTitle);
                        // }

                        if (callback && callback.beforeOpenEditFormDialog) { // 弹出前回调
                            callback.beforeOpenEditFormDialog(json);
                        }
                        $dialog.dialog('open').dialog('center').scrollTop(0);

                        if (callback && callback.onOpenEditFormDialog) { // 弹出后回调
                            callback.onOpenEditFormDialog(json);
                        }
                    } else {
                        // if (response.message) {
                        // $.messager.alert(LABLE_ERROR, response.message, 'error');
                        // } else {
                        // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
                        // }
                        AlertUtils.msg(response.icon || 'error', response.message
                                || MSG_REMOTE_SERVER_ERROR);
                    }
                    MainUtils.closeLoading();// 关闭loading提示
                },
                error: MainUtils.handleAjaxFormError
            };
            $.ajax(options);
        }
        MainUtils.openLoading();

        var $dialogWrapper = $dialog.parent();
        if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
            setTimeout(function() {
                $.parser.parse($dialogWrapper);

                // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                $dialogWrapper.remove();
                $dialog.parent().prop('rendered', true);

                if (callback && callback.onInitEditFormDialog) {
                    callback.onInitEditFormDialog();
                }
                process(true, callback);
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(false, callback);
        }
    }

    /**
     * 弹出用于查看记录的Dialog，弹出前通过Ajax向服务端发送待查找的数据的id，并加载返回自服务端的JSON结果。
     * 
     * @param dialogId CSS selector，形如"#myDialogId"
     * @param url
     * @param gridId 当从服务端查找成功后，将此datagrid的相应记录更新。如果未提供，则忽略
     * @param callback 包含回调函数的对象
     * @deprecated
     */
    function openViewDialog(dialogId, url, gridId, callback, rowId) {
        if (!dialogId || !url || !gridId) {
            return;
        }

        // 获取选中的单条记录id
        var $grid = $(gridId);
        var recordId = '';
        if (rowId) {
            recordId = rowId;
        } else {
            var selectedRows = $grid.datagrid('getSelections');
            if (selectedRows.length == 1) {
                recordId = selectedRows[0].id;
            } else {
                if (selectedRows.length == 0) {
                    // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
                    AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                } else if (selectedRows.length > 1) {
                    // $.messager.alert(LABLE_WARNING, MSG_WARN_MORE_THAN_ONE_SELECT, 'warning');
                    AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                }
                return;
            }
        }

        var $dialog = $(dialogId);

        function process(callback) {

            var paramData = {};
            paramData['id'] = recordId;
            var options = {
                url: url,
                type: 'POST',
                dataType: 'json',
                data: paramData,
                success: function(response, statusText, xhr, jqForm) {

                    if (MainUtils.processJsonResult(response, false)) {
                        var json = response.dataMap.returnObj;

                        // 将服务器返回的JSON加载到Form(easyui-form的load效率太低)
                        $dialog.find('[data-view-field]').each(function() {
                            var $this = $(this);
                            var name = $this.attr('data-view-field');
                            var widget = $this.attr('data-field-widget');

                            // 加载值
                            if ('filebox' == widget) {
                                // 按照约定，文件字段的字段名，去掉末尾的'_'，再加上List，成为文件列表的字段名
                                value = json[name.replace('Text', '') + 'List'];
                                FileboxUtils.renderView($this, value);
                            } else {
                                var text = '';
                                if (typeof json[name] != 'undefined') {
                                    text = json[name];
                                    if ($this.is('[data-formatter]')) {
                                        var formatFunc = eval($this.attr('data-formatter'));
                                        text = formatFunc(text, json);
                                    }
                                }
                                if (text == null) {
                                    text = '';
                                }
                                $this.html(text + '&nbsp;');
                            }
                        });

                        // 将服务端返回JSON更新到datagrid
                        var rowIndex = $grid.datagrid('getRowIndex', recordId);
                        $grid.datagrid('updateRow', {
                            index: rowIndex,
                            row: json
                        });

                        if (callback && callback.beforeOpenViewDialog) { // 弹出前回调
                            callback.beforeOpenViewDialog(json);
                        }
                        $dialog.dialog('open').dialog('center').scrollTop(0);

                        if (callback && callback.onOpenViewDialog) { // 弹出后回调
                            callback.onOpenViewDialog(json);
                        }
                    } else {
                        // if (response.message) {
                        // $.messager.alert(LABLE_ERROR, response.message, 'error');
                        // } else {
                        // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
                        // }
                        AlertUtils.msg(response.icon || 'error', response.message
                                || MSG_REMOTE_SERVER_ERROR);
                    }
                    MainUtils.closeLoading();// 关闭loading提示
                },
                error: MainUtils.handleAjaxFormError
            };
            $.ajax(options);
        }
        MainUtils.openLoading();

        var $dialogWrapper = $dialog.parent();
        if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
            setTimeout(function() {
                $.parser.parse($dialogWrapper);

                // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                $dialogWrapper.remove();
                $dialog.parent().prop('rendered', true);

                process(callback);
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(callback);
        }
    }

    function openAuditViewPanel(panelId, url, dataId, callback) {
        if (!panelId || !url || !dataId) {
            return;
        }
        var $panel = $(panelId);
        // 特殊处理计划管理审核弹出画面
        var isPlanView = ('/project-edge/schedule/plan/find.json' == url);

        function process(callback) {

            var paramData = {};
            paramData['id'] = dataId;
            var options = {
                url: url,
                type: 'POST',
                dataType: 'json',
                data: paramData,
                success: function(response, statusText, xhr, jqForm) {

                    if (MainUtils.processJsonResult(response, false)) {
                        var json = response.dataMap.returnObj;
                        if (json) {
	                        // 将服务器返回的JSON加载到Form(easyui-form的load效率太低)
	                        $panel
                                .find('[data-view-field]')
                                .each(
                                        function() {
                                            var $this = $(this);
                                            var name = $this.attr('data-view-field');
                                            var widget = $this.attr('data-field-widget');

                                            if (isPlanView) {
                                                if ('planName' == name) {
                                                    var text = '';
                                                    if (typeof json[name] != 'undefined') {
                                                        text = json[name];
                                                        if ($this.is('[data-formatter]')) {
                                                            var formatFunc = eval($this
                                                                    .attr('data-formatter'));
                                                            text = formatFunc(text, json);
                                                        }
                                                    }
                                                    if (text == null) {
                                                        text = '';
                                                    }
                                                    $this
                                                            .html('<a onclick="PlanTaskUtils.openViewTask(null,\''
                                                                    + dataId
                                                                    + '\')">'
                                                                    + text
                                                                    + '</a>');
                                                    return true;
                                                }
                                            }

                                            // 加载值
                                            if ('filebox' == widget) {
                                                // 按照约定，文件字段的字段名，去掉末尾的'_'，再加上List，成为文件列表的字段名
                                                value = json[name.replace('Text', '') + 'List'];
                                                FileboxUtils.renderView($this, value);
                                            } else {
                                                var text = '';
                                                if (typeof json[name] != 'undefined') {
                                                    text = json[name];
                                                    if ($this.is('[data-formatter]')) {
                                                        var formatFunc = eval($this
                                                                .attr('data-formatter'));
                                                        text = formatFunc(text, json);
                                                    }
                                                }
                                                if (text == null) {
                                                    text = '';
                                                }
                                                $this.html(text + '&nbsp;');
                                            }
                                        });
                        }
                        if (callback && callback.beforeOpenViewDialog) { // 弹出前回调
                            callback.beforeOpenViewDialog(json);
                        }

                        if (callback && callback.onOpenViewDialog) { // 弹出后回调
                            callback.onOpenViewDialog(json);
                        }
                    } else {
                        // if (response.message) {
                        // $.messager.alert(LABLE_ERROR, response.message, 'error');
                        // } else {
                        // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
                        // }
                        AlertUtils.msg(response.icon || 'error', response.message
                                || MSG_REMOTE_SERVER_ERROR);
                    }
                    MainUtils.closeLoading();// 关闭loading提示
                },
                error: MainUtils.handleAjaxFormError
            };
            $.ajax(options);
        }
        MainUtils.openLoading();

        // var $panelWrapper = $panel.parent();
        // $.parser.parse($panelWrapper);
        process(callback);
    }

    /**
     * 用于新建/修改的保存处理，使用jQuery form通过Ajax来提交form。新增后将数据插入datagrid首行。修改后更新datagrid中相应行。
     * 
     * 如果提供了回调对象，则使用回调对象中的相关函数，否则使用默认的实现。涉及的回调函数有：
     * onInvalidData、beforeSubmit、beforeDatagridInsert、onDatagridInserted、onDatagridUpdated
     * 
     * @param dialogId CSS selector，形如"#myDialogId"
     * @param datagridId 待刷新的datagrid的ID。如果非空，则在ajax成功后，刷新datagrid
     * @param isFile 是否包含文件上传
     * @param isAdd 是否新增
     * @param isContinuousAdd 是否连续新增，如果true，则不关闭dialog
     * @param callback 保存回调函数的对象
     */
    function submitAddEditFormData(dialogId, datagridId, isFile, isAdd, isContinuousAdd, callback) {

        if (!dialogId) {
            return;
        }

        // easyui form仅仅用到了validate、reset和load等方法(以$('...').form('...')的形式调用)，
        // 其他form相关的都使用jQuery.form
        var $dialog = $(dialogId);
        var $form = $dialog.find('form');

        var url = $form.attr('action');
        if (!url) {
            return;
        }

        $form.form('enableValidation');
        if (!$form.form('validate')) {

            // 新建/修改验证失败后回调
            if (callback && $.isFunction(callback.onInvalidData)) {
                callback.onInvalidData();
            }
            return;
        }

        // 如果checkbox未选中，则直接提交form时该值不会传到后台。此处额外处理，将未选中的checkbox也提交至后台。
        var param = {};

        // $form.find('input[data-field-widget="checkbox"]').not(':checked').each(function() {
        // var n = this.name;
        // if (!n || this.disabled) {
        // return;
        // }
        //
        // param[n] = ControlUtils.getControlValue($(this), 'checkbox');
        // });
        $form.find('input[data-field-widget="checkbox"]').each(function() {
            var $this = $(this);
            var n = ControlUtils.getControlName($this);
            var v = ControlUtils.getControlValue($this, 'checkbox');
            if (!n || $this.switchbutton('options').disabled || v === '1') {
                return;
            }

            param[n] = v;
        });

        var options = {
            url: url,
            dataType: isFile ? 'html' : 'json',
            data: param,
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示

                var jsonObj = response;
                if (isFile) {
                    jsonObj = $.parseJSON($(response).text());
                }

                if (MainUtils.processJsonResult(jsonObj, true)) {

                    var json = jsonObj.dataMap.returnObj;

                    // 新增后将数据插入datagrid首行，修改后更新datagrid中相应行
                    if (isAdd) {
                        var needInsertRow = true;
                        if (callback && $.isFunction(callback.beforeDatagridInsert)) {
                            needInsertRow = callback.beforeDatagridInsert(datagridId, json) === true;
                        }

                        if (needInsertRow && datagridId) {
                            $(datagridId).datagrid('insertRow', {
                                index: 0, // index start with 0
                                row: json
                            }).datagrid('clearSelections');
                        }

                        // 新增记录保存成功后回调
                        if (callback && $.isFunction(callback.onDatagridInserted)) {
                            callback.onDatagridInserted(datagridId, json);
                        }

                    } else {
                        if (datagridId) {
                            var editIndex = $(datagridId).datagrid('getRowIndex', json.id);
                            if (editIndex != -1) { // 若在当前页选中行，翻页后点击修改并保存，此时getRowIndex方法返回-1
                                $(datagridId).datagrid('updateRow', {
                                    index: editIndex,
                                    row: json
                                });
                            }
                        }

                        // 修改记录保存成功后回调
                        if (callback && $.isFunction(callback.onDatagridUpdated)) {
                            callback.onDatagridUpdated(datagridId, json);
                        }
                    }

                    if (isAdd && isContinuousAdd) { // 只有在连续新增时，才不关闭弹出画面

                        // 禁用验证
                        $form.form('disableValidation');

                        // 处理默认值和复制，并重置
                        // 是否选中复制
                        var isCopy = $dialog.next().find('input[data-copy-reserve-checkbox]').prop(
                                'checked');

                        $form.find('[data-field-widget]').each(function() {
                            var $this = $(this);
                            var widget = $this.attr('data-field-widget');

                            var val = null;
                            var useCurrentValue = false;
                            if (isCopy && $this.is('[data-copy-reserve]')) {
                                useCurrentValue = true;
                            } else if ($this.is('[data-default-value]')) {
                                val = $this.attr('data-default-value');
                            }

                            if (!useCurrentValue) {
                                if (val) {
                                    ControlUtils.setControlValue($this, val, widget);
                                } else {
                                    ControlUtils.resetControl($this, widget);
                                }
                            }

                            // 新建弹出画面的每个widget初始化后回调
                            if (callback && $.isFunction(callback.onInitAddFormWidget)) {
                                callback.onInitAddFormWidget($this, false);
                            }
                        });

                        // 连续新增点击后回调
                        if (callback && $.isFunction(callback.onContinuousAdd)) {
                            callback.onContinuousAdd();
                        }

                    } else {
                        EasyDialogUtils.closeFormDialog(dialogId);
                    }
                }
            },
            error: MainUtils.handleAjaxFormError
        };

        // 提交新建/修改前回调
        if (callback && $.isFunction(callback.beforeSubmit)) {
            callback.beforeSubmit(options, isAdd, isContinuousAdd, $dialog);
        }
        $form.ajaxSubmit(options);// jquery.form plugin
        MainUtils.openLoading();// 弹出loading提示

    }

    /**
     * 用于保存处理，使用jQuery form通过Ajax来提交form。
     * 
     * 如果提供了回调对象，则使用回调对象中的相关函数，否则使用默认的实现。涉及的回调函数有： onInvalidData、beforeSubmit
     * 
     * @param isFile 是否包含文件上传
     */
    function saveFormData(submitButton, isFile, callback) {

        // easyui form仅仅用到了validate、reset和load等方法(以$('...').form('...')的形式调用)，
        // 其他form相关的都使用jQuery.form
        var $form = $(submitButton).closest('form');

        var url = $form.attr('action');
        if (!url) {
            return;
        }

        $form.form('enableValidation');
        if (!$form.form('validate')) {

            // 验证失败后回调
            if (callback && $.isFunction(callback.onInvalidData)) {
                callback.onInvalidData();
            }
            return;
        }

        // 如果checkbox未选中，则直接提交form时该值不会传到后台。此处额外处理，将未选中的checkbox也提交至后台。
        var param = {};
        $form.find('input[data-field-widget="checkbox"]').not(':checked').each(function() {
            var n = this.name;
            if (!n || this.disabled) {
                return;
            }

            param[n] = ControlUtils.getControlValue($(this), 'checkbox');
        });

        var options = {
            url: url,
            dataType: isFile ? 'html' : 'json',
            data: param,
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示

                var jsonObj = response;
                if (isFile) {
                    jsonObj = $.parseJSON($(response).text());
                }

                if (MainUtils.processJsonResult(jsonObj, true)) {

                    var json = jsonObj.dataMap.returnObj;
                }
            },
            error: MainUtils.handleAjaxFormError
        };

        // 提交前回调
        if (callback && $.isFunction(callback.beforeSubmit)) {
            callback.beforeSubmit(options);
        }
        $form.ajaxSubmit(options);// jquery.form plugin
        MainUtils.openLoading();// 弹出loading提示

    }

    /**
     * 将datagrid中选中的记录批量删除，进行Ajax提交。
     * 
     * @param url
     * @param datagridId 待刷新并清除选中的datagrid的ID。如果非空，则在ajax成功后，刷新并清除选中datagrid。CSS
     *            selector，形如"#myDialogId"
     */
    function batchDeleteSelected(url, datagridId) {
        if (!url || !datagridId) {
            return;
        }

        // 必须选中至少一条记录
        var selectedRows = $(datagridId).datagrid('getSelections');
        if (selectedRows.length > 0) {
            $.messager.confirm(LABLE_CONFIRM, MSG_CONFIRM_DELETE, function(r) {
                if (r) {
                    var idArray = [];
                    for (var i = 0; i < selectedRows.length; i++) {
                        idArray.push(selectedRows[i].id);
                    }
                    _doBatchDelete(url, idArray.join(), datagridId);
                }
            });
        } else {
            // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
            return;
        }
    }

    /** ********************** */
    /* private functions below */
    /** ********************** */

    /**
     * 批量删除指定的记录，进行Ajax提交。服务端必须对名为"idsToDelete"的query string进行处理。
     * 
     * @param url
     * @param idsToDelete 逗号分隔的待删除记录的id字符串
     * @param datagridId 待刷新并清除选中的datagrid的ID。如果非空，则在ajax成功后，刷新并清除选中datagrid。CSS
     *            selector，形如"#myDialogId"
     */
    function _doBatchDelete(url, idsToDelete, datagridId) {
        if (!url || !idsToDelete) {
            return;
        }

        var options = {
            url: url,
            type: 'POST',
            dataType: 'json',
            data: {
                // 服务端必须对名为"idsToDelete"的query string进行处理
                idsToDelete: idsToDelete
            },
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                if (MainUtils.processJsonResult(response, true)) {
                    if (datagridId) {
                        $(datagridId).datagrid('reload');// 刷新easyui datagrid
                        // TODO 删除后，datagrid加载成功后会自动清除选中，此处是否还要再手动调用一次？
                        $(datagridId).datagrid('clearSelections');// 必须清除easyui datagrid选中
                    }
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);
        MainUtils.openLoading();// 弹出loading提示
    }

    return {
        openAddFormDialog: openAddFormDialog,
        openEditFormDialog: openEditFormDialog,
        openViewDialog: openViewDialog,
        openAuditViewPanel: openAuditViewPanel,
        submitAddEditFormData: submitAddEditFormData,
        saveFormData: saveFormData,
        batchDeleteSelected: batchDeleteSelected,
        doBatchDelete: _doBatchDelete
    }; // CrudUtils
})(jQuery);

/**
 * EasyUI的datagrid相关的逻辑。
 */
var GridUtils = (function($) {
    var isClickCell = false;
    var isUnSelected = false;

    /** ********************* */
    /* public functions below */
    /** ********************* */
    function handleCell(index, field, value) {
        var thisObj = $(this);
        var selectedRows = $(this).datagrid('getChecked');
        $.each(selectedRows, function(i, n) {
            var cIndex = thisObj.datagrid('getRowIndex', n);
            if (cIndex == index) {
                isUnSelected = true;
                // break;
            }
        })
        isClickCell = true;
    }

    /**
     * easyui datagrid双击数据行的事件处理，弹出修改画面
     */
    function handleDblClickRow(index, row) {

        /*
        *
        var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');

        var $this = $(this);

        // 双Grid画面的场合，兼容SubGrid
        if ($this.is('[data-linked-grid-id]')) {
            editFormId = this.id.replace('SubDatagrid', 'SubGridEditFormDialog');
        }

        CrudUtils.openEditFormDialog('#' + editFormId, $this.datagrid('options').url.replace(
                'list', 'find'), '#' + this.id, index);
                
         */
        
        var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
        var $this = $(this);
        
        if($this.is('[data-linked-grid-id]')){
            viewFormId = this.id.replace('SubDatagrid', 'SubGridViewGridDialog'); 
        }
        
        CrudUtils.openViewDialog('#' + viewFormId, $(this).datagrid('options').url
                .replace('list', 'find'), '#' + this.id, null, row.id);
        
    }

    /**
     * easyui-datagrid的选中和不选中的事件处理，这些事件在datagrid初始化时就会自动调用一次，之后在用户做选择操作时会相应触发调用。
     * 
     * 例如，对于删除、修改、查看按钮，在选中单条记录时启用，不选中或选中多条时禁用。
     * 
     * @param index 同easyui的datagrid的onSelect事件
     * @param row
     */
    function handleSelect(index, row) {
        // debugger;
        var $this = $(this);
        var rows = $this.datagrid('getSelections');

        var isSingleSelect = false;
        if ($(this).datagrid('options') != null) {
            isSingleSelect = $(this).datagrid('options').singleSelect;
        }
        if (isClickCell && !isSingleSelect) {
            isClickCell = false;
            if (!isUnSelected) {
                // onSelect
                $(this).datagrid('unselectAll');
                $(this).datagrid('selectRow', index);
            } else {
                // onUnSelect
                isUnSelected = false;
                $(this).datagrid('unselectRow', index);
            }
        }

        // datagrid的id形如'pageid-datatype-MainDatagrid'，功能按钮的id形如'pageidF01'
        var idPre = this.id.substring(0, this.id.indexOf('-'));

        // 双Grid画面的场合，兼容SubGrid
        if ($this.is('[data-linked-grid-id]')) {
            idPre += '-SubGrid-'; // Controller中SubGrid的功能按钮id必须与之一致，形如'pageid-SubGrid-F01'
            if (idPre == "P2061-SubGrid-") {
                // 项目成员页面上下grid不做联动
            } else {
                _refreshLinkedGrid($this, rows);
            }

            // MainGrid的新建按钮，要根据SubGrid是否选中来进行启用禁用
            var mainGridFunc = rows.length == 1 ? 'enable' : 'disable';
            var mainGridId = $this.attr('data-linked-grid-id');
            var mainIdPre = mainGridId.substring(0, mainGridId.indexOf('-'));
            $('#' + mainIdPre + 'F01').linkbutton(mainGridFunc);

        }

        // 删除
        $('#' + idPre + 'F02').linkbutton(rows.length > 0 ? 'enable' : 'disable');

        // 修改/查看/导出/比较/查看任务/编辑任务/查看总体进度
        var singleFunc = rows.length == 1 ? 'enable' : 'disable';
        $('#' + idPre + 'F03').linkbutton(singleFunc);
        $('#' + idPre + 'F04').linkbutton(singleFunc);
        // $('#' + idPre + 'F06').linkbutton(singleFunc);
        $('#' + idPre + 'F18').linkbutton(singleFunc);
        $('#' + idPre + 'F19').linkbutton(singleFunc); // 专家选择
        $('#' + idPre + 'F20').linkbutton(singleFunc); // 专家评审结果
        $('#' + idPre + 'F50').linkbutton(singleFunc); // 生成电子证书
        $('#' + idPre + 'F51').linkbutton(singleFunc); // 生成电子证书
        $('#' + idPre + 'F52').linkbutton(singleFunc); // 借阅领取
        $('#' + idPre + 'F53').linkbutton(singleFunc); // 借阅归还
        $('#' + idPre + 'F41').linkbutton(singleFunc);
        $('#' + idPre + 'F42').linkbutton(singleFunc);
        // 合并至少选中两条记录
        $('#' + idPre + 'F43').linkbutton(rows.length > 1 ? 'enable' : 'disable');

        if (idPre == 'P110501' || idPre == 'P110505' || idPre == 'P110510') {
        	$('#' + idPre + 'F06').linkbutton('enable');
        } else {
        	$('#' + idPre + 'F06').linkbutton(rows.length > 0 ? 'enable' : 'disable');
        }

        $('#' + idPre + 'F45').linkbutton(singleFunc);
        // 提交审核/撤回/审核记录/流程权限/流程角色权限
        // $('#' + idPre + 'F71').linkbutton(singleFunc);
        if (idPre == 'P301002-SubGrid-') {
            $('#P301001F71').linkbutton(rows.length > 1 ? 'disable' : 'enable');
        } else {
            $('#' + idPre + 'F71').linkbutton(rows.length > 1 ? 'disable' : 'enable');
        }

        $('#' + idPre + 'F72').linkbutton(singleFunc);
        $('#' + idPre + 'F73').linkbutton(singleFunc);
        $('#' + idPre + 'F74').linkbutton(singleFunc);
        $('#' + idPre + 'F75').linkbutton(singleFunc);

        if (rows != null && (rows[0])) {
            singleFunc = rows.length == 1 ? 'enable' : 'disable';
            var flowStatus = rows[0].flowStatus;
            if (typeof flowStatus == 'undefined') {
                flowStatus = rows[0].flowStatusProject;
            }
            if (flowStatus != null) {
                if ('enable' == singleFunc) {

                	// flowstatus 0-未提交，1-审核中，2-审核通过，3-审核不通过，4-撤回中，5-已撤回
                    if (idPre == 'P301002-SubGrid-') {
                        if (flowStatus == 0 || flowStatus == 3) {
                            $('#P301001F71').linkbutton('enable');
                        } else {
                            $('#P301001F71').linkbutton('disable');
                        }
                    } else {
                        if (flowStatus == 0 || flowStatus == 3) {
                            $('#' + idPre + 'F71').linkbutton('enable');
                        } else {
                            $('#' + idPre + 'F71').linkbutton('disable');
                        }
                    }

                    if (flowStatus == 1) {
                        $('#' + idPre + 'F72').linkbutton('enable');
                    } else {
                        $('#' + idPre + 'F72').linkbutton('disable');
                    }

                    if (flowStatus == 1 || flowStatus == 2 || flowStatus == 3) {
                        $('#' + idPre + 'F73').linkbutton('enable');
                    } else {
                        $('#' + idPre + 'F73').linkbutton('disable');
                    }
                    
                    if (flowStatus != 0) {
                    	// 审批状态一旦提交后，修改功能即关闭
                    	$('#' + idPre + 'F03').linkbutton('disable');
                    	$('#' + idPre + 'F02').linkbutton('disable');//删除
                    	$('#' + idPre + 'F41').linkbutton('disable');//编制计划
                    }
                }
            } else {
                $('#' + idPre + 'F72').linkbutton('disable');
                $('#' + idPre + 'F73').linkbutton('disable');
            }
        } else {
            $('#' + idPre + 'F71').linkbutton('disable');
            if (idPre == 'P301002-SubGrid-') {
                $('#P301001F71').linkbutton('disable');
            }
        }

    }

    /** ********************** */
    /* private functions below */
    /** ********************** */

    /**
     * SubGrid选中行，刷新关联的MainGrid。
     * 
     * @param target SubGrid
     * @param rows 选中的行
     */
    function _refreshLinkedGrid($target, rows) {
        if (rows.length != 1) {
            return;
        }

        var linkedGridId = '#' + $target.attr('data-linked-grid-id');
        var linkedFilterField = $target.attr('data-linked-filter-field-name');

        var selectedRow = rows[0];

        // 判断是否重复点击
        var oldCon = $(linkedGridId).data('EXTRA_FILTER_OBJ');
        if (!oldCon && selectedRow == null) {
            return;
        }
        if (oldCon && selectedRow && oldCon.value == selectedRow.id) {
            return;
        }

        var con = null;
        if (selectedRow != null) { // 选择全部
            con = {};
            con.fieldName = linkedFilterField;
            con.valueType = '4';
            con.filterSearchBy = '1';
            con.valueMatchMode = '0';
            con.captionName = '';
            con.value = selectedRow.id;
        }

        $(linkedGridId).data('EXTRA_FILTER_OBJ', con);
        FilterUtils.doSearch(linkedGridId);
    }

    return {
        handleDblClickRow: handleDblClickRow,
        handleSelect: handleSelect,
        handleCell: handleCell
    }; // GridUtils
})(jQuery);

/**
 * main画面逻辑。
 */
var Main = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 在主窗口区域弹出Tab形式的画面，不使用iframe。
     * 
     * 打开标签页时，通过Main._extractContentTabHtmlBody将内容画面的div#EasyuiHiddenWrapper.hidden内的所有子元素剪切到主画面的body，
     * 并记录所有dialog的id到标签页的data-hiddenComponentIdArray中，以便在关闭该标签页时通过easyui-tabs的onBeforeClose，
     * 回调Main.onBeforeCotentTabClose来销毁剪切到主画面的dialog。
     * 
     * @param title
     * @param url
     * @param isUpdateContent 如果Tab已打开，是否更新Tab的内容
     * @param isClosable 是否可关闭
     */
    function openTab(title, url, isUpdateContent, isClosable, params, onLoadPanel) {

        var $contentTab = $('#MainContentTab');

        if ($contentTab.tabs('exists', title)) {
            $contentTab.tabs('select', title);
            if (isUpdateContent) {
                var tab = $contentTab.tabs('getSelected');
                tab.panel('refresh');
            }
        } else {

            if (isClosable !== false) { // 不提供此参数时，默认为true
                isClosable = true;
            }
            setTimeout(function() {

                var queryParams = {
                    timestamp: new Date().getTime()
                };

                if (params) {
                    queryParams = params;
                }

                $contentTab.tabs('add', {
                    title: title,
                    queryParams: queryParams,
                    href: url,
                    extractor: _extractContentTabHtmlBody,
                    onLoadError: MainUtils.handleDatagridLoadError,
                    closable: isClosable,
                    onLoad: onLoadPanel
                });
            }, 0);
        }

        // easyui-panel的loader和extractor的使用，
        // 参考jquery.panel.js源码中的loadData以及loader和extractor的默认实现
    }

    /**
     * 在主窗口区域弹出Tab形式的画面，使用iframe。
     * 
     * @param title
     * @param url
     * @param isUpdateContent 如果Tab已打开，是否更新Tab的内容
     * @param isClosable 是否可关闭
     */
    function openTabIframe(title, url, isUpdateContent, isClosable) {

        var $contentTab = $('#MainContentTab');

        if ($contentTab.tabs('exists', title)) {
            $contentTab.tabs('select', title);
            if (isUpdateContent) {

                var tab = $contentTab.tabs('getSelected');
                $contentTab.tabs('update', {
                    tab: tab,
                    options: {
                        content: _createTabContent(url),
                    }
                });
            }
        } else {
            if (isClosable !== false) { // 不提供此参数时，默认为true
                isClosable = true;
            }
            $contentTab.tabs('add', {
                title: title,
                content: _createTabContent(url),
                closable: isClosable
            });
        }
    }

    /**
     * 打开查看或编辑数据记录的标签页。
     * 
     * @param datagridId easyui的datagrid id，用来找到查看的记录
     * @param subtitleFieldName 标签页头上，除了显示title，还会加上记录的唯一键字段值，此参数用来指定在datagrid中的用作subtitle的字段名
     * @param title
     * @param url
     */
    function openViewEditTab(datagridId, subtitleFieldName, title, url) {

        if (!datagridId || !subtitleFieldName || !title || !url) {
            return;
        }

        var selectedRows = $(datagridId).datagrid('getSelections');
        if (selectedRows.length == 0) {
            // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
            return;
        } else if (selectedRows.length > 1) {
            // $.messager.alert(LABLE_WARNING, MSG_WARN_MORE_THAN_ONE_SELECT, 'warning');
            AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
            return;
        }

        title = title + '-' + selectedRows[0][subtitleFieldName];

        // 如果不希望显示subtitle，可以用<i class="hidden">...</i>隐藏
        // title = title + '&lt;i class=&quot;hidden&quot;&gt;' + selectedRows[0][subtitleFieldName]
        // + '&lt;/i&gt;'

        openTab(title, url + '?id=' + selectedRows[0].id);
    }

    /**
     * 根据标签页的title来关闭标签页。
     * 
     * @param title
     */
    function closeTab(title) {
        setTimeout(function() {
            $('#MainContentTab').tabs('close', title); // 比较耗时
        }, 0);
    }

    /**
     * 刷新当前的标签页内容。
     */
    function refreshThisTab() {

        var $contentTab = $('#MainContentTab');
        var tab = $contentTab.tabs('getSelected');

        var ids = tab.data('hiddenComponentIdArray');
        $.each(ids, function(k, v) {
            EasyDialogUtils.destroyDialog(v);
        });

        tab.panel('refresh');
    }

    /**
     * 关闭当前元素所在的标签页。
     * 
     */
    function closeThisTab() {
        var $contentTab = $('#MainContentTab');
        var tab = $contentTab.tabs('getSelected');
        var index = $contentTab.tabs('getTabIndex', tab);

        setTimeout(function() {
            $contentTab.tabs('close', index); // 比较耗时
        }, 0);
    }

    /**
     * 在tab关闭前回调，用来销毁加载Tab时，添加到body的那些easyui组件('#EasyuiHiddenWrapper')。
     * 
     * 配合Main.openTab使用，用来关闭非iframe的标签页。
     */
    function onBeforeCotentTabClose(title, index) {

        var ids = $(this).tabs('getTab', index).data('hiddenComponentIdArray');
        // console.log(ids);
        if (ids) {
            setTimeout(function() {
                $.each(ids, function(k, v) {
                    EasyDialogUtils.destroyDialog(v);
                });
            }, 0);
        }
        return true;
    }

    /**
     * 在panel关闭前回调，用来销毁加载panel时，添加到body的那些easyui组件('#EasyuiHiddenWrapper')。
     * 
     * 机制与tab相同，当easyui-dialog设置了href属性，并将extractor属性设为Main.extractContentTabHtmlBody时使用。
     * easyui的dialog、window、tab，本质上都是基于panel的内容加载机制。
     * 
     * 注意，panel的cache属性必须设为false，这样每次都会重新加载href，从而保证easyui组件('#EasyuiHiddenWrapper')的正确加载和销毁。
     */
    function onBeforeCotentPanelClose() {

        var ids = $(this).data('hiddenComponentIdArray');
        // console.log(ids);
        if (ids) {
            setTimeout(function() {
                $.each(ids, function(k, v) {
                    EasyDialogUtils.destroyDialog(v);
                });
            }, 0);
        }
        return true;
    }

    /**
     * 显示tabs的工具栏菜单时，如果当前标签页不可被关闭，则禁用"关闭当前标签页"菜单项，否则启用之。
     */
    function onMainContentTabToolsMenuShow() {

        var $contentTab = $('#MainContentTab');
        var tab = $contentTab.tabs('getSelected');
        var item = $(this).menu('findItem', {
            name: 'colse-current'
        });

        if (tab.panel('options').closable) {
            $(this).menu('enableItem', item.target);
        } else {
            $(this).menu('disableItem', item.target);
        }
    }

    /**
     * 点击tabs的工具栏菜单项的响应处理。
     * 
     * @param item
     */
    function onMainContentTabToolsMenuClick(item) {

        var $contentTab = $('#MainContentTab');

        if (item.name == 'colse-current') {
            // 关闭当前标签页
            var tab = $contentTab.tabs('getSelected');
            if (tab.panel('options').closable) {
                var index = $contentTab.tabs('getTabIndex', tab);
                setTimeout(function() {
                    $contentTab.tabs('close', index);
                }, 0);
            }

        } else if (item.name == 'colse-other') {
            // 关闭其他标签页
            var tab = $contentTab.tabs('getSelected');
            var currentTitle = tab.panel('options').title;

            var tabs = $contentTab.tabs('tabs');
            setTimeout(function() {
                // 关闭标签页时，tabs的内容(length)也会同时变更，所以这里不能用i++
                for (var i = tabs.length - 1; i >= 0; i--) {
                    var options = tabs[i].panel('options');
                    if (options.closable && options.title != currentTitle) {
                        $contentTab.tabs('close', options.title);
                    }
                }
            }, 0);

        } else if (item.name == 'colse-right') {
            // 关闭右侧标签页
            var tab = $contentTab.tabs('getSelected');
            var currentIndex = $contentTab.tabs('getTabIndex', tab);

            var tabs = $contentTab.tabs('tabs');
            setTimeout(function() {
                // 关闭标签页时，tabs的内容(length)也会同时变更，所以这里不能用i++
                for (var i = tabs.length - 1; i > currentIndex; i--) {
                    var options = tabs[i].panel('options');
                    if (options.closable) {
                        $contentTab.tabs('close', options.title);
                    }
                }
            }, 0);

        } else {
            // 关闭所有标签页
            var tabs = $contentTab.tabs('tabs');
            setTimeout(function() {
                // 关闭标签页时，tabs的内容(length)也会同时变更，所以这里不能用i++
                for (var i = tabs.length - 1; i >= 0; i--) {
                    var options = tabs[i].panel('options');
                    if (options.closable) {
                        $contentTab.tabs('close', options.title);
                    }
                }
            }, 0);
        }
    }

    /**
     * 在tab中点击右键，弹出标签页管理的菜单。
     * 
     * @deprecated
     */
    function onCotentTabContextMenu(e, title, index) {
        // e.preventDefault();
        // $('#MainContentTabToolsClickMenu').menu('show', {
        // left: e.pageX,
        // top: e.pageY
        // });
    }

    /**
     * 退出登录
     */
    function logout() {
        top.location.href = BASE_URL + '/logout.htm';
    }

    /** ********************** */
    /* private functions below */
    /** ********************** */

    /**
     * 针对easyui-tabs使用异步内容获取时，用来抽取内容画面<body/>内可见的内容(不含<body/>)，并在首次加载时，
     * 将不可见的内容('#EasyuiHiddenWrapper'的所有子元素)剪切到主画面的body，同时记录所有dialog的id到标签页的data-hiddenComponentIdArray中，
     * 以便在关闭该标签页时通过easyui-tabs的onBeforeClose，回调Main.onBeforeCotentTabClose来销毁剪切到主画面的dialog。
     * 
     * 参考MainUtils.extractVisibleHtmlBody。
     * 
     * <pre>
     * body的结构参考singleGridPage.jsp，在body内分为同级并列的4个区域，从上到下依次是： 
     * 1. 可见的布局，通常使用bootstrap的container/container-fluid或者EasyUI的layout来构建UI
     * 2. 隐藏的div#EasyuiHiddenWrapper.hidden，用来包含弹出框，每个弹出框都包含在独立的div.hidden内
     * 3. 隐藏的div#ContentScriptWrapper.hidden，用来包含画面所需的JavaScript脚本引用，供独立调试画面时使用。这些脚本在main.jsp已经引入，所以在tab加载画面body时将会被直接抛弃
     * 4. 使用script标签引入的画面专有的JavaScript逻辑，由includeJspJsPath变量指定
     * </pre>
     * 
     * @param data 服务器回传的数据
     */
    function _extractContentTabHtmlBody(data) {

        // console.log(this);

        // this指向tab/window/dialog/panel的.panel-body
        var content = MainUtils.extractHtmlBody.call(this, data);
        var $content = $('<div>' + content + '</div>');

        // 每次关闭tab将从文档流中删除整个.panel-body，再打开tab会创建全新的.panel-body
        if ($content.find('#EasyuiHiddenWrapper').length > 0) {

            var $hiddenWrapper = $('#EasyuiHiddenWrapper', $content);

            // 将#EasyuiHiddenWrapper内所有easyui组件的id缓存起来，以便在关闭tab时进行销毁
            // 目前只支持easyui-dialog
            var ids = [];
            $hiddenWrapper.find('div.easyui-dialog[id]').each(function(i) {
                ids.push('#' + $(this).attr('id'));
            });
            $(this).data('hiddenComponentIdArray', ids);

            if ($('#EasyuiHiddenWrapper').size() == 0) {
                $('body').append('<div id="EasyuiHiddenWrapper" class="hidden"></div>');
            }

            $hiddenWrapper.children().appendTo('#EasyuiHiddenWrapper');
        }

        content = $content.find('#EasyuiHiddenWrapper, #ContentScriptWrapper').remove().end()
                .html();
        return content;
    }

    /**
     * 根据url生成iframe。配合Main.openTabIframe使用。
     */
    function _createTabContent(url) {
        var hasParam = '&';

        if (url.indexOf('?') == -1) {
            hasParam = '?';
        }
        var content = '<iframe scrolling="no" frameborder="0"  src="' + url + hasParam
                + 'timestamp=' + new Date().getTime()
                + '" style="width:100%;height:100%;" seamless ></iframe>';
        return content;
    }

    /**
     * 调整easyui-tabs的宽度和高度使其最大化铺满所属的div.content-wrapper
     */
    function _resizeContentTab(e) {
        // 当视窗大小改变时，以及AdminLET的PushMenu在展开和收缩时，由于使用了setTimeout，使得.content-wrapper的margin是异步调整的，
        // 这将导致easyui容器如tabs的fit:true不能正常工作(参考adminlte-2.4.15.js中PushMenu.prototype.expand和PushMenu.prototype.collapse)。
        // 这里通过手动调用容器的resize来调整容器大小，并使用setTimeout，将时间设为400毫秒，从而确保在.content-wrapper的大小变更完成后，再执行tabs容器的resize
        setTimeout(function() {
            $('#MainContentTab').tabs('resize');
        }, 400);
    }

    /** ************************** */
    /* initialization logics below */
    /** ************************** */

    $(function() {

        // 点击侧边栏的展开收缩，或者视窗改变大小时，调整easyui-tabs的宽度和高度使其最大化铺满所属的div.content-wrapper
        $(document).on('expanded.pushMenu', _resizeContentTab).on('collapsed.pushMenu',
                _resizeContentTab);
        $(window).resize(_resizeContentTab);

        // 菜单项的点击样式，增加选中的菜单组左侧边框颜色，不能用官方的.active类，会导致菜单栏行为异常
        var $menuItems = $('ul.sidebar-menu > li:not(.header)');
        $menuItems.on('click', function(e) {
            var $currentItem = $(this);
            if (!$currentItem.hasClass('active-menu-group')) {
                $menuItems.filter('.active-menu-group').removeClass('active-menu-group');
                $currentItem.addClass('active-menu-group');
            }
        });
    });

    return {
        openTab: openTab,
        openTabIframe: openTabIframe,
        openViewEditTab: openViewEditTab,
        closeTab: closeTab,
        closeThisTab: closeThisTab,
        refreshThisTab: refreshThisTab,
        onBeforeCotentTabClose: onBeforeCotentTabClose,
        onBeforeCotentPanelClose: onBeforeCotentPanelClose,
        onMainContentTabToolsMenuShow: onMainContentTabToolsMenuShow,
        onMainContentTabToolsMenuClick: onMainContentTabToolsMenuClick,
        extractContentTabHtmlBody: _extractContentTabHtmlBody,
        logout: logout
    }; // Main
})(jQuery);

/**
 * OA集成相关的逻辑。
 */
var OaIntegration = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 同步OA系统的人力资源数据，包括分部、部门、岗位和人员。
     * 
     * @param url
     */
    function syncHrmData(url) {
        if (!url) {
            return;
        }

        var options = {
            url: url,
            type: 'POST',
            dataType: 'json',
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                MainUtils.processJsonResult(response, true);
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);// jquery.form plugin
        MainUtils.openLoading();// 弹出loading提示
    }

    /**
     * OA审批共通处理
     */
    function submitAudit(gridId, url) {
        // debugger;
        if (!url) {
            return;
        }

        // 获取选中的单条记录id
        var $grid = $(gridId);
        var selectedRows = $grid.datagrid('getSelections');
        var recordId = null;

        if (gridId) {
            $grid = $(gridId);
            var selectedRows = $grid.datagrid('getSelections');
            if (selectedRows.length == 1) {
                recordId = selectedRows[0].id;
            } else {
                if (selectedRows.length == 0) {
                    AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                } else if (selectedRows.length > 1) {
                    AlertUtils.msg('warning', MSG_WARN_MORE_THAN_ONE_SELECT);
                }
                return;
            }
        } else {
            // gridId为空时，rowIndex作为recordId使用
            recordId = null;
        }

        MainUtils.openLoading();
        var paramData = {};
        paramData['id'] = recordId;
        var options = {
            url: url,
            type: 'POST',
            dataType: 'json',
            data: paramData,
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();
                if (MainUtils.processJsonResult(response, false)) {
                    AlertUtils.msg('info', response.message);
                    $grid.datagrid('reload');
                } else {
                    AlertUtils.msg(response.icon || 'error', response.message
                            || MSG_REMOTE_SERVER_ERROR);
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);
    }

    function submitAuditBatch(gridId, url) {
        // debugger;
        if (!url) {
            return;
        }

        // 获取选中的单条记录id
        var $grid = $(gridId);
        var selectedRows = $grid.datagrid('getSelections');
        var recordId = null;

        if (gridId) {
            $grid = $(gridId);
            var selectedRows = $grid.datagrid('getSelections');
            if (selectedRows.length == 1) {
                recordId = selectedRows[0].id;
            } else {
                if (selectedRows.length == 0) {
                    AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                } else if (selectedRows.length > 1) {
                    var ids = [];
                    for (var i = 0; i < selectedRows.length; i++) {
                        ids.push(selectedRows[i].id);
                    }
                    recordId = ids.join();
                }
                return;
            }
        } else {
            // gridId为空时，rowIndex作为recordId使用
            recordId = null;
        }

        MainUtils.openLoading();
        var paramData = {};
        paramData['id'] = recordId;
        var options = {
            url: url,
            type: 'POST',
            dataType: 'json',
            data: paramData,
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();
                if (MainUtils.processJsonResult(response, false)) {
                    AlertUtils.msg('info', response.message);
                } else {
                    AlertUtils.msg(response.icon || 'error', response.message
                            || MSG_REMOTE_SERVER_ERROR);
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);
    }

    function openAuditLogDialog(dialogId, datagridId) {
        // debugger;
        if (!dialogId || !datagridId) {
            return;
        }
        var $dialog = $(dialogId);
        var $datagrid = $(datagridId);

        var $logDatagrid = $('#_AuditLogOaDatagrid');
        function process(isFirstLoad) {

            var selected = $datagrid.datagrid('getSelected');

            var options = $logDatagrid.datagrid('options');
            options.queryParams.dataId = selected.id;

            $logDatagrid.datagrid('reload', BASE_URL + '/oa/flowhistory/list.json');
            // queryOAAuditLog(selected.id);

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
                process(true);
                MainUtils.closeLoading();
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(false);
        }
    }

    /** ************************** */
    /* initialization logics below */
    /** ************************** */


    return {
        syncHrmData: syncHrmData,
        submitAudit: submitAudit,
        openAuditLogDialog: openAuditLogDialog
    }; // OaIntegration
})(jQuery);

/**
 * 计划进度。
 */
var PlanTaskUtils = (function($) {


    var changeFlagStr = 'PLAN_CHANGE';
    
    // 打开任务比较界面
    function openCompareTask(selected) {

    	console.log('openCompareTask', selected);
    	if (selected) {
    		var _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=6&planChangeId=' + selected.id;
        	EasyDialogUtils.openMaxModal(_url, '比较任务');
    	}
    }

    // 打开修改任务界面
    function openModifyTask(maingrid) {
        var selected = $(maingrid).datagrid('getSelected');

        if (selected) {

            var _changeFlag = false;

            var isModify = 1;

            // 编制计划变更
            if (maingrid.indexOf(changeFlagStr) >= 0) {
                _changeFlag = true;
                isModify = 4;
            }

            var _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=' + isModify + '&planId='
                    + selected.id;

            EasyDialogUtils.openMaxModal(_url, '编制任务');
        }
    }

    // 打开查看任务界面
    function openViewTask(maingrid, dataId) {

        var _id = null;

        if (maingrid) {
            var selected = $(maingrid).datagrid('getSelected');
            _id = selected.id;
        } else {
            _id = dataId;
        }

        if (_id) {

            var _changeFlag = false;

            var isModify = 0;

            // 查看计划变更
            if (maingrid) {
                if (maingrid.indexOf(changeFlagStr) >= 0) {
                    _changeFlag = true;
                    isModify = 5;
                }
            }

            var _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=' + isModify + '&planId='
                    + _id;

            EasyDialogUtils.openMaxModal(_url, '查看任务');
        }
    }

    // 打开查看任务界面
    function openViewTaskById(planId, taskId) {
        var _changeFlag = false;
        _changeFlag = true;
        var _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=' + 0 + '&planId=' + planId
                + '&taskId=' + taskId;
        EasyDialogUtils.openMaxModal(_url, '查看任务');
    }

    // 打开查看总体进度界面
    function openProgressTask(maingrid) {
        var selected = $(maingrid).datagrid('getSelected');

        if (selected) {

            var _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=2&planId=' + selected.id;

            EasyDialogUtils.openMaxModal(_url, '进度跟踪');
        }
    }

    function openExport(maingrid) {
        var selected = $(maingrid).datagrid('getSelected');
        if (selected) {
            var _url = BASE_URL + '/schedule/plan-task/export-task-excel-file.json?planId='
                    + selected.id;
            MainUtils.downloadFile(_url);
        } else {
            AlertUtils.msg('warning', '请选择需要导出的计划！');
        }
    }

    function regeneratePlan(mainGrid) {
    	var selected = $(mainGrid).datagrid('getSelected');
        if (selected) {
        	console.log(selected);
        	MainUtils.openLoading();
        	var options = {
                url: BASE_URL + '/facility/site/flow-status.json?id=' + selected.id,
                data: {},
                datatype: 'json',
                type: 'GET',
                contentType: 'application/json',
                success: function(response, statusText, xhr, jqForm) {
                	
                	// 取得应答，关闭loading提示
                	MainUtils.closeLoading();
                	var jsonObj = response.dataMap.returnObj;
                	
                	// 如果已经有审核通过的计划，提示用户先删除已审核通过的计划
                	if (jsonObj.is_authorized) {
                		AlertUtils.msg('warning', '已有通过审核的计划，请联系管理员删除后重试');
                		return;
                	}
                	// 无审核通过的建设计划，提示用户需删除原计划
                	else {
                		$.messager.confirm('重建计划', '重新生成计划将删除原建设计划，确认继续吗？', function(confirmed) {
                			if (confirmed) {
                				
                				// 如果确认了，弹出重新生成的对话框
                				
                				// 如果是站点管理，选择站点类型
                				if (selected.hasOwnProperty('isBasicNetworkTransmitStationType')) {
//                					showRegenerationModal(selected);
                					submitRegenerate(['1', '2', '3'], selected)
                				}
                				// 如果是中继段，直接提交
                				else {
                					submitRegenerate([], selected)
                				}
                				
                			}
                		});
                	}
                },
                error: MainUtils.handleAjaxFormError
            };
            $.ajax(options); // 检查计划是否已审核
        }
    }
    
    // 显示重新生成的对话框
    function showRegenerationModal(selected) {
    	
    	// 找到对应的对话框div
		$('.content-wrapper').append("<div id='regenerate-task'></div>");
    	var regenerateDiv = $('#regenerate-task');
    	regenerateDiv.empty();
    	
    	regenerateDiv.append(
    		'<div style="margin:8px; border:solid 1px #CCC; border-radius:4px; padding: 8px;">' +
				'<div style="margin-bottom:12px">' +
	                '<input style="margin-right: 6px" type="checkbox" class="regenerate-checks" ' + (selected.isBasicNetworkTransmitStationType ? '' : 'disabled') + ' value="basic">基础网络组' +
	            '</div>' +
	            '<div style="margin-bottom:12px">' +
	                '<input style="margin-right: 6px" type="checkbox" class="regenerate-checks" ' + (selected.isProgrammableNetworkTransmitStationType ? '' : 'disabled') + ' value="programmable">可编程组' +
	            '</div>' +
	            '<div style="margin-bottom:12px">' +
	                '<input style="margin-right: 6px" type="checkbox" class="regenerate-checks" ' + (selected.isSdnNetworkTransmitStationType ? '' : 'disabled') + ' value="sdn">SDN网络组' +
	            '</div>' +
            '</div>'
    	);
    	
    	regenerateDiv.append(
    		'<div class="messager-button">' + 
    			'<a id="confirmRegenerate" class="l-btn l-btn-small" style="font-size: 14px; margin-right:6px" href="javascript:;" >确认</a>' +
	    		'<a id="cancelRegenerate" class="l-btn l-btn-small" style="font-size: 14px" href="javascript:;" >取消</a>' +
    		'</div>'
    	);
    	
    	regenerateDiv.window({
    		title: '重新生成建设计划',
    		width: 400,
    		height: 220,
    		minimizable: false,
    		maximizable: false,
    		collapsible: false,
    		closable: true,
    		modal: true,
    		onClose: function() {
    			$('#regenerate-task').remove();
    		},
    	});
    	
    	var checkMap = {};
    	
    	$('.regenerate-checks').bind('change', function(e) {
    		checkMap[e.target.value] = e.target.checked;
    	});
    	
    	$('#cancelRegenerate').bind('click', function() {
    		$('#regenerate-task').window('close');
    	});
    	
    	$('#confirmRegenerate').bind('click', function() {
    		$('#regenerate-task').window('close');
    		
    		var groupIds = [];
    		if (checkMap['basic']) {
    			groupIds.push('1');
    		}
    		if (checkMap['programmable']) {
    			groupIds.push('2');
    		}
    		if (checkMap['sdn']) {
    			groupIds.push('3');
    		}
    		
    		if (groupIds.length == 0) {
    			return;
    		}
    		
    		submitRegenerate(groupIds, selected)
    	});
    }
    
    /**
     * 提交重新生成
     */
    function submitRegenerate(groupIds, selected) {
    	
    	const type = selected.hasOwnProperty('isBasicNetworkTransmitStationType') ? 'site' : 'segment';
    	
    	MainUtils.openLoading();
    	var options = {
            url: BASE_URL + '/facility/' + type + '/regenerate.json?id=' + selected.id + '&groupIds=' + groupIds.join(','),
            data: {},
            datatype: 'json',
            type: 'GET',
            contentType: 'application/json',
            success: function(response, statusText, xhr, jqForm) {
            	
            	// 取得应答，关闭loading提示
            	MainUtils.closeLoading();
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options); // 检查计划是否已审核
    }
    

    function saveImportExcelData() {
        var $form = $('#_ProjectExcelDialogForm');

        $form.form('enableValidation');
        if (!$form.form('validate')) {
            console.log(111)
            return;
        }
        console.log(222)

        var options = {
            url: BASE_URL + '/schedule/plan-task/import-task-excel-file.json',
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
                        $('#P5003-PROJECT-MainDatagrid').datagrid('reload');
                    }
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $form.ajaxSubmit(options);
        MainUtils.openLoading();
    }

    // 打开导入页面
    function openImport(maingrid) {
        var html = '<div style="display: none;"><div id="_ProjectExcelDialog" class="easyui-dialog" title="导入"'
                + ' data-options="buttons:\'#_ProjectExcelDialogButtons\',closed:true,modal:true,onMove:EasyDialogUtils.onDialogMove"'
                + 'style="width: 500px; height: 200px; padding: 10px;"><form id="_ProjectExcelDialogForm">'
                + '<table class="table" style="width: 100%; height: 100%; border-collapse: separate; border-spacing: 0px 0px">'
                + '<tr><td width="20%">导入文件：</td><td width="75%"><input name="importFile" type="text" '
                + 'class="easyui-filebox" data-options="accept:\'.xls,.xlsx\',width:300,height:25,buttonText:\'选择\',required:true" /></td>'
                + '</tr></table></form></div><div id="_ProjectExcelDialogButtons"><div>'
                + '<a href="javascript:void(0)" class="easyui-linkbutton" onclick="PlanTaskUtils.saveImportExcelData();">确认</a>'
                + ' <a href="javascript:void(0)" class="easyui-linkbutton" '
                + 'onclick="EasyDialogUtils.closeFormDialog(\'#_ProjectExcelDialog\');">退出'
                + '</a></div></div></div>';
        $("body").append(html);


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

    // 打开查看进度预警界面
    function openProgressWarning(maingrid) {
        var selected = $(maingrid).datagrid('getSelected');

        if (selected) {

            var _url = BASE_URL + '/schedule/plan-task/main.htm?isModify=3&planId=' + selected.id;

            EasyDialogUtils.openMaxModal(_url, '查看进度预警');
        }
    }

    return {
        openModifyTask: openModifyTask,
        openViewTask: openViewTask,
        openCompareTask: openCompareTask,
        openProgressTask: openProgressTask,
        openImport: openImport,
        openExport: openExport,
        saveImportExcelData: saveImportExcelData,
        openProgressWarning: openProgressWarning,
        openViewTaskById: openViewTaskById,
        regeneratePlan: regeneratePlan
    };
})(jQuery);

/**
 * 画面侧边，包括项目一览、公司部门和档案文件树，相关的逻辑。
 */
var SideUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 左侧项目一览的式样。
     * 
     * @param value 字符值
     * @param row easyui的datagrid中的某一行数据
     * @param index easyui的datagrid中的行index
     * @deprecated
     */
    function projectFormatter(value, row, index) {
        var pjNum = '<div class="side-project-title">' + row.projectNum + '</div>';
        var pjName = '<div class="side-project-content">' + row.projectName + '</div>';

        var pjKind = '';
        if (row.projectKindText) {
            var lb = 'label-success';
            if (row.projectKind_ == 'PROJECT_KIND_0') {
                lb = 'label-primary';
            } else if (row.projectKind_ == 'PROJECT_KIND_1') {
                lb = 'label-warning';
            }

            pjKind = '<span class="label ' + lb + '">' + row.projectKindText.substring(0, 3)
                    + '</span>';
        }

        var pj = '<div>';
        if (pjKind) {
            pj = '<div class="side-project-row">';
        }
        pj = pj + pjNum + pjName + pjKind;
        pj += '</div>';
        return pj;
    }

    /**
     * easyui-datagrid的选中和不选中的事件处理，设置关联的grid的检索信息，并刷新关联grid。
     * 
     * @param index 同easyui的datagrid的onSelect事件
     * @param row
     * @deprecated
     */
    function handleGridRowSelect(index, row) {

        var linkedGridId = '#' + $(this).attr('data-linked-grid-id');

        var selectedRow = $(this).datagrid('getSelected');

        // 判断是否重复点击
        var oldCon = $(linkedGridId).data('EXTRA_FILTER_OBJ');
        if (!oldCon && selectedRow == null) {
            return;
        }
        if (oldCon && selectedRow && oldCon.value == selectedRow.id) {
            return;
        }

        var con = null;
        if (selectedRow != null) { // 选择全部
            con = {};
            con.fieldName = 'project_';
            con.valueType = '4';
            con.filterSearchBy = '1';
            con.valueMatchMode = '0';
            con.captionName = selectedRow.projectName;
            con.value = selectedRow.id;
        }

        $(linkedGridId).data('EXTRA_FILTER_OBJ', con);
        FilterUtils.doSearch(linkedGridId);
    }

    /**
     * easyui-datagrid的加载成功事件处理，设置关联的grid的检索信息，并刷新关联grid。
     * 
     * @param data
     * @deprecated
     */
    function handleGridLoadSuccess(data) {

        $(this).datagrid('clearSelections');

        var linkedGridId = '#' + $(this).attr('data-linked-grid-id');

        // 判断是否重复点击
        var oldCon = $(linkedGridId).data('EXTRA_FILTER_OBJ');
        if (oldCon == null) {
            return;
        }

        $(linkedGridId).data('EXTRA_FILTER_OBJ', null);
        FilterUtils.doSearch(linkedGridId);
    }

    /**
     * EasyUI树的节点选择事件处理，设置关联的grid的检索信息，并刷新关联grid。根节点代表"所有数据"，它不是一个普通的数据节点，不对应Tree的数据源中的数据。
     * 这种情况下根节点的id等于ALL。
     * 
     * @param node
     */
    function handleTreeNodeSelect(node) {

        var $this = $(this);

        // 如果有关联的函数，则处理关联的函数，之后直接返回
        if ($this.is('[data-linked-func]')) {
            var linkedFunc = eval($this.attr('data-linked-func'));
            linkedFunc(node);
            return;
        }

        // 如果既没有关联的函数，也没有关联的grid id，则直接返回
        if (!$this.is('[data-linked-grid-id]')) {
            return;
        }

        var linkedGridId = '#' + $this.attr('data-linked-grid-id');
        var linkedSubGridId = null;
        if ($this.is('[data-linked-subgrid-id]')) {
            linkedSubGridId = '#' + $this.attr('data-linked-subgrid-id');
        }

        // 单Grid时，只要刷新关联的Grid。双Grid时，刷新第一层关联(SubGrid)，同时重置第二层关联(MainGrid)。
        // SubGrid通常位于MainGrid上面。
        var idFirstLevelLink = linkedGridId;
        var idSecondLevelLink = null; // 双Grid时非null
        if (linkedSubGridId) {
            idFirstLevelLink = linkedSubGridId;
            idSecondLevelLink = linkedGridId;
        } else {

            // 0. 单Grid的场合，联动启用禁用关联Grid的新建按钮

            var gridFunc = node.id == 'ALL' || !node.attributes.fieldName ? 'disable' : 'enable';

            // datagrid的id形如'pageid-datatype-MainDatagrid'，功能按钮的id形如'pageidF01'
            var idPre = linkedGridId.substring(0, linkedGridId.indexOf('-'));
            $(idPre + 'F01').linkbutton(gridFunc);
        }

        // 判断是否重复点击
        var oldCon = $(idFirstLevelLink).data('EXTRA_FILTER_OBJ');
        if (oldCon && oldCon.value == node.id) {
            return;
        }
        if (node.id == 'ALL' && oldCon == null) {
            return;
        }
        if (node.id != 'ALL' && !node.attributes.fieldName) {
            // 后台如果对node.attributes.fieldName不设置，则不处理此点击
            return;
        }

        // 1. 处理第一层关联的Grid
        var con = null;
        if (node.id != 'ALL') { // 不是选择全部时，准备过滤条件对象
            con = {};
            con.fieldName = node.attributes.fieldName;
            con.valueType = '4';
            con.filterSearchBy = '1';
            con.valueMatchMode = '0';
            con.captionName = node.text;
            con.value = node.id;
        }

        $(idFirstLevelLink).data('EXTRA_FILTER_OBJ', con);
        FilterUtils.doSearch(idFirstLevelLink);

        // 2. 双Grid时，处理第二层关联的Grid
        if (idSecondLevelLink) {
            // $(idSecondLevelLink).data('EXTRA_FILTER_OBJ', null).datagrid('loadData', {
            // status: 1
            // });
            $(idSecondLevelLink).data('EXTRA_FILTER_OBJ', con); // 这里使用相同的检索条件，后台应灵活处理，由后台决定是否进行过滤
            FilterUtils.doSearch(idSecondLevelLink);
        }
    }

    /**
     * EasyUI树的加载成功事件处理，设置关联的grid的检索信息，并刷新关联grid。根节点代表"所有数据"，它不是一个普通的数据节点，不对应Tree的数据源中的数据。
     * 这种情况下根节点的id等于ALL。
     * 
     * @param node
     * @param data
     */
    function handleTreeLoadSuccess(node, data) {
    	debugger

        var $this = $(this);

        // 如果有关联的函数，则处理关联的函数，之后直接返回
        if ($this.is('[data-linked-func]')) {
            var linkedFunc = eval($this.attr('data-linked-func'));
            linkedFunc(node, data, true);
            return;
        }

        // 如果没有关联的grid id，则直接返回
        if (!$this.is('[data-linked-grid-id]')) {
            return;
        }

        var linkedGridId = '#' + $this.attr('data-linked-grid-id');
        var linkedSubGridId = null; // 双Grid时非null
        if ($this.is('[data-linked-subgrid-id]')) {
            linkedSubGridId = '#' + $this.attr('data-linked-subgrid-id');
        }

        // 单Grid时，只要刷新关联的Grid。双Grid时，刷新SubGrid，同时清空MainGrid。
        var idFirstLevelLink = linkedGridId;
        var idSecondLevelLink = null;
        if (linkedSubGridId) {
            idFirstLevelLink = linkedSubGridId;
            idSecondLevelLink = linkedGridId;
        } else {

            // 0. 单Grid的场合，联动启用禁用关联Grid的新建按钮

            // datagrid的id形如'pageid-datatype-MainDatagrid'，功能按钮的id形如'pageidF01'
            var idPre = linkedGridId.substring(0, linkedGridId.indexOf('-'));
            $(idPre + 'F01').linkbutton('disable');
        }

        // 判断是否重复点击
        var oldCon = $(idFirstLevelLink).data('EXTRA_FILTER_OBJ');
        if (oldCon == null) {
            // 画面首次加载时，也会直接返回
            return;
        }

        // 1. 处理待刷新的Grid
        $(idFirstLevelLink).data('EXTRA_FILTER_OBJ', null);
        FilterUtils.doSearch(idFirstLevelLink);

        // 2. 处理待清空的Grid
        if (idSecondLevelLink) {
            // $(idSecondLevelLink).data('EXTRA_FILTER_OBJ', null).datagrid('loadData', {
            // status: 1
            // });
            $(idSecondLevelLink).data('EXTRA_FILTER_OBJ', null);
            FilterUtils.doSearch(idSecondLevelLink);
        }
    }

    /**
     * EasyUI树的节点选择事件处理，设置关联的grid的检索信息，并刷新关联grid。根节点是一个普通的数据节点，对应Tree的数据源中的数据。
     * 
     * @param node
     */
    function handleTreeNodeOfDataRootSelect(node) {
    	debugger

        var $this = $(this);

        // 如果有关联的函数，则处理关联的函数，之后直接返回
        if ($this.is('[data-linked-func]')) {
            var linkedFunc = eval($this.attr('data-linked-func'));
            linkedFunc(node);
            return;
        }

        // 如果既没有关联的函数，也没有关联的grid id，则直接返回
        if (!$this.is('[data-linked-grid-id]')) {
            return;
        }

        var linkedGridId = '#' + $this.attr('data-linked-grid-id');
        var linkedSubGridId = null;
        if ($this.is('[data-linked-subgrid-id]')) {
            linkedSubGridId = '#' + $this.attr('data-linked-subgrid-id');
        }

        // 单Grid时，只要刷新关联的Grid。双Grid时，刷新第一层关联(SubGrid)，同时重置第二层关联(MainGrid)。
        // SubGrid通常位于MainGrid上面。
        var idFirstLevelLink = linkedGridId;
        var idSecondLevelLink = null; // 双Grid时非null
        if (linkedSubGridId) {
            idFirstLevelLink = linkedSubGridId;
            idSecondLevelLink = linkedGridId;
        } else {

            // 0. 单Grid的场合，联动启用禁用关联Grid的新建按钮

            var gridFunc = !node.attributes.fieldName ? 'disable' : 'enable';

            // datagrid的id形如'pageid-datatype-MainDatagrid'，功能按钮的id形如'pageidF01'
            var idPre = linkedGridId.substring(0, linkedGridId.indexOf('-'));
            $(idPre + 'F01').linkbutton(gridFunc);
        }

        // 判断是否重复点击
        var oldCon = $(idFirstLevelLink).data('EXTRA_FILTER_OBJ');
        if (oldCon && oldCon.value == node.id) {
            return;
        }
        if (!node.attributes || !node.attributes.fieldName) {
            // 后台如果对node.attributes.fieldName不设置，则不处理此点击
            return;
        }

        // 1. 处理第一层关联的Grid
        var con = {};
        con.fieldName = node.attributes.fieldName;
        con.valueType = '4';
        con.filterSearchBy = '1';
        con.valueMatchMode = '0';
        con.captionName = node.text;
        con.value = node.id;

        $(idFirstLevelLink).data('EXTRA_FILTER_OBJ', con);
        FilterUtils.doSearch(idFirstLevelLink);

        // 2. 双Grid时，处理第二层关联的Grid
        if (idSecondLevelLink) {
            // $(idSecondLevelLink).data('EXTRA_FILTER_OBJ', null).datagrid('loadData', {
            // status: 1
            // });
            $(idSecondLevelLink).data('EXTRA_FILTER_OBJ', con); // 这里使用相同的检索条件，后台应灵活处理，由后台决定是否进行过滤
            FilterUtils.doSearch(idSecondLevelLink);
        }
    }

    /**
     * EasyUI树的加载成功事件处理，设置关联的grid的检索信息，并刷新关联grid。根节点也是一个普通的数据节点，对应Tree的数据源中的数据。
     * 
     * 可以是整个Tree的加载成功，或是某个节点的加载成功。如果是加载整个Tree，则要刷新关联的grid，否则，某个节点的加载成功时不影响Tree的选中状态，直接返回。
     * 
     * @param node
     * @param data
     */
    function handleTreeOfDataRootLoadSuccess(node, data) {
    	debugger

        // 只有在加载整个Tree时，node为null
        if (node) {
            return;
        }

        var $this = $(this);

        // 如果有关联的函数，则处理关联的函数，之后直接返回
        if ($this.is('[data-linked-func]')) {
            var linkedFunc = eval($this.attr('data-linked-func'));
            linkedFunc(node, data, true);
            return;
        }

        // 如果没有关联的grid id，则直接返回
        if (!$this.is('[data-linked-grid-id]')) {
            return;
        }

        var linkedGridId = '#' + $this.attr('data-linked-grid-id');
        var linkedSubGridId = null; // 双Grid时非null
        if ($this.is('[data-linked-subgrid-id]')) {
            linkedSubGridId = '#' + $this.attr('data-linked-subgrid-id');
        }

        // 单Grid时，只要刷新关联的Grid。双Grid时，刷新SubGrid，同时清空MainGrid。
        var idFirstLevelLink = linkedGridId;
        var idSecondLevelLink = null;
        if (linkedSubGridId) {
            idFirstLevelLink = linkedSubGridId;
            idSecondLevelLink = linkedGridId;
        } else {

            // 0. 单Grid的场合，联动启用禁用关联Grid的新建按钮

            // datagrid的id形如'pageid-datatype-MainDatagrid'，功能按钮的id形如'pageidF01'
            var idPre = linkedGridId.substring(0, linkedGridId.indexOf('-'));
            $(idPre + 'F01').linkbutton('disable');
        }

        // 判断是否重复点击
        var oldCon = $(idFirstLevelLink).data('EXTRA_FILTER_OBJ');
        if (oldCon == null) {
            // 画面首次加载时，也会直接返回
            return;
        }

        // 1. 处理第一层关联的Grid
        $(idFirstLevelLink).data('EXTRA_FILTER_OBJ', null);
        FilterUtils.doSearch(idFirstLevelLink);

        // 2. 处理第二层关联的Grid
        if (idSecondLevelLink) {
            // $(idSecondLevelLink).data('EXTRA_FILTER_OBJ', null).datagrid('loadData', {
            // status: 1
            // });
            $(idSecondLevelLink).data('EXTRA_FILTER_OBJ', null);
            FilterUtils.doSearch(idSecondLevelLink);
        }
    }

    /** ************************** */
    /* initialization logics below */
    /** ************************** */


    return {
        projectFormatter: projectFormatter,
        handleGridRowSelect: handleGridRowSelect,
        handleGridLoadSuccess: handleGridLoadSuccess,
        handleTreeNodeSelect: handleTreeNodeSelect,
        handleTreeLoadSuccess: handleTreeLoadSuccess,
        handleTreeNodeOfDataRootSelect: handleTreeNodeOfDataRootSelect,
        handleTreeOfDataRootLoadSuccess: handleTreeOfDataRootLoadSuccess
    }; // SideUtils
})(jQuery);

/**
 * 弹出画面相关的逻辑。同时支持带按钮的textbox输入控件触发的弹出，或者更为通用的弹出，两者在回调上的处理不同。
 * 
 * 【约定】弹出画面中，所有元素的id的前缀等于弹出画面dialog的id。
 */
var PopupSelectUtils = (function($) {

    /** ********************* */
    /* public functions below */
    /** ********************* */

    /**
     * 打开人员选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openPersonSelect(target, callback) {
        _openPopupSelect('#Global_P00000_PERSON-ControlDialog', true, target, callback);
    }
    
    /**
     * 打开项目成员选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openProjectPersonSelect(target, callback) {
        _openPopupSelect('#Global_P00000_PROJECT_MEMBER-ControlDialog', false, target, callback);
    }
    
    /**
     * 打开概算册选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openBudgetEstimateSumSelect(target, callback) {
        _openPopupSelect('#Global_P00000_BUDGET_ESTIMATE_SUM-ControlDialog', false, target, callback);
    }
    
    /**
     * 打开链路选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openLinkSelect(target, callback) {
        _openPopupSelect('#Global_P00000_LINK-ControlDialog', false, target, callback);
    }

    /**
     * 打开人员通用选择画面。
     * 
     * @param callback 必须提供onChooseRows方法，来处理选择的多行记录
     */
    function openPersonGeneralSelect(callback) {
        _openPopupSelect('#General_Global_P00000_PERSON-ControlDialog', true, null, callback);
    }

    /**
     * 打开人员双Grid通用选择画面。
     * 
     * @param callback 必须提供onChooseRows方法，来处理选择的多行记录
     */
    function openPersonDblGridSelect(callback) {
        _openDblGridPopupSelect('#General-PERSON-PopupSelectorDialog', callback);
    }

    /**
     * 打开专家双Grid通用选择画面
     */
    function openExpertDblGridSelect(callback) {
        _openDblGridPopupSelect('#General-EXPERT-PopupSelectorDialog', callback);
    }

    /**
     * 打开岗位选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openPostSelect(target, callback) {
        _openPopupSelect('#Global_P00000_POST-ControlDialog', true, target, callback);
    }

    /**
     * 打开站点选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openSiteSelect(target, callback) {
        _openPopupSelect('#Global_P00000_SITE-ControlDialog', false, target, callback);
    }

    /**
     * 打开中继段选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openSegmentSelect(target, callback) {
        _openPopupSelect('#Global_P00000_SEGMENT-ControlDialog', false, target, callback);
    }

    /**
     * 打开往来单位选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openRelatedUnitSelect(target, callback) {
        _openPopupSelect('#Global_P00000_RELATED_UNIT-ControlDialog', false, target, callback);
    }

    /**
     * 打开项目选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openProjectSelect(target, callback) {
        _openPopupSelect('#Global_P00000_PROJECT-ControlDialog', false, target, callback);
    }

    /**
     * 打开计划选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openPlanSelect(target, callback) {
        _openPopupSelect('#Global_P00000_PLAN-ControlDialog', false, target, callback);
    }


    /**
     * 打开纸质库文件选择
     */
    function openPaperLibrarySelect(target, callback) {
        _openPopupSelect('#Global_P00000_PAPER_LIBRARY-ControlDialog', false, target, callback);
    }

    /**
     * 打开项目角色选择画面。
     * 
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function openProjectRoleSelect(target, callback) {
        _openPopupSelect('#Global_P00000_PROJECT_ROLE-ControlDialog', false, target, callback);
    }

    /**
     * 选择弹出画面中，记录行选择与反选的事件处理，启用/禁用确定按钮。这些事件在datagrid初始化时就会自动调用一次，之后在用户做选择操作时会相应触发调用。
     * 适用于单选和单Grid多选画面。
     * 
     * @param index 同easyui的datagrid的onSelect事件
     * @param row
     */
    function handleRowSelect(index, row) {

        // datagrid的id形如'dialogId-MainDatagrid'
        var dialogId = this.id.substring(0, this.id.lastIndexOf('-'));
        var $dialog = $('#' + dialogId);

        var rows = $(this).datagrid('getSelections');

        // 确定按钮
        var okBtn = $dialog.next().find('a:first');
        okBtn.linkbutton(rows.length > 0 ? 'enable' : 'disable');
    }

    /**
     * 选择弹出画面中，双击记录行的事件处理。将选择的值填入输入控件，并关闭选择画面。适用于单选画面。
     * 
     * @param index 同easyui的datagrid的onDblClickRow事件
     * @param row
     */
    function handleDblClickRow(index, row) {

        // datagrid的id形如'dialogId-MainDatagrid'
        var dialogId = this.id.substring(0, this.id.lastIndexOf('-'));
        var $dialog = $('#' + dialogId);

        var idField = $dialog.attr('data-id-field');
        var textField = $dialog.attr('data-text-field');
        var id = row[idField];
        var text = row[textField];

        var target = $dialog.data('target');
        var $inputGroup = $(target).closest('div.input-group');

        $inputGroup.find('input[readonly]').val(text);
        $inputGroup.find('input:hidden').val(id);

        var callback = $dialog.data('callback');
        if (callback && $.isFunction(callback.onChooseRow)) {
            callback.onChooseRow(target, row);
        }
        $dialog.dialog('close');
    }

    /**
     * 选择弹出画面中，选中记录行后，点击确定按钮的事件处理。将选择的值填入输入控件，并关闭选择画面。适用于单选画面。
     * 
     * @param dialogId
     */
    function handleClickOk(dialogId) {

        // datagrid的id形如'dialogId-MainDatagrid'
        var gridId = dialogId + '-MainDatagrid';
        var $dialog = $(dialogId);

        var idField = $dialog.attr('data-id-field');
        var textField = $dialog.attr('data-text-field');

        var $grid = $(gridId);
        var row = $grid.datagrid('getSelected');
        if (!row) {
            return;
        }

        var id = row[idField];
        var text = row[textField];

        var target = $dialog.data('target');
        var $inputGroup = $(target).closest('div.input-group');

        $inputGroup.find('input[readonly]').val(text);
        $inputGroup.find('input:hidden').val(id);

        var callback = $dialog.data('callback');
        if (callback && $.isFunction(callback.onChooseRow)) {
            callback.onChooseRow(target, row);
        }

        $dialog.dialog('close');
    }

    /**
     * 选择弹出画面中，选中记录行后，点击确定按钮的事件处理。调用回调函数，将选择的值输入，并关闭选择画面。适用于单Grid多选画面。
     * 
     * 此方法更通用，不再假定弹出画面是由带按钮的textbox输入控件触发，因此不再将选择的值回填到输入控件。支持多选，并且必须提供callback回调，选择的值全部交给callback处理。
     * 
     * @param dialogId
     */
    function handleGeneralClickOk(dialogId) {

        // datagrid的id形如'dialogId-MainDatagrid'
        var gridId = dialogId + '-MainDatagrid';
        var $dialog = $(dialogId);

        var $grid = $(gridId);
        var rows = $grid.datagrid('getSelections');
        if (rows.length == 0) {
            return;
        }

        var callback = $dialog.data('callback');
        if (callback && $.isFunction(callback.onChooseRows)) {
            callback.onChooseRows(rows);
        }

        $dialog.dialog('close');
    }

    /**
     * 选择弹出画面中，选中记录行后，点击确定按钮的事件处理。调用回调函数，将选择的值输入，并关闭选择画面。适用于双Grid多选画面。
     * 
     * 此方法更通用，不再假定弹出画面是由带按钮的textbox输入控件触发，因此不再将选择的值回填到输入控件。支持多选，并且必须提供callback回调，选择的值全部交给callback处理。
     * 
     * @param dialogId
     * @param gridId 选择结果Grid(MainGrid)的id
     */
    function handleDblGridClickOk(dialogId, gridId) {

        var $dialog = $(dialogId);
        var $grid = $(gridId);

        var rows = $grid.datagrid('getRows');
        if (rows.length == 0) {
            return;
        }

        var callback = $dialog.data('callback');
        if (callback && $.isFunction(callback.onChooseRows)) {
            callback.onChooseRows(rows);
        }

        $dialog.dialog('close');
    }

    /**
     * 从候选grid中，将勾选的记录复制到结果grid。
     */
    function select(fromGridId, toGridId) {
        var $fromGrid = $(fromGridId);
        var $toGrid = $(toGridId);

        var rows = $fromGrid.datagrid('getSelections');
        if (rows.length == 0) {
            return;
        }

        var hasSelected = false; // 结果grid是否有记录

        // 已经存在的记录，不再添加
        // console.time('Add rows');
        var resultRows = $toGrid.datagrid('getRows');
        var resultIds = '';
        if (resultRows.length > 0) {
            hasSelected = true;
            var idArray = [];
            $.each(resultRows, function(i, n) {
                idArray.push(n.id);
            });
            resultIds = idArray.join();
        }

        $.each(rows, function(i, n) {
            if (resultIds.indexOf(n.id) == -1) {
                $toGrid.datagrid('appendRow', n);
                hasSelected = true;
            }
        });
        // console.timeEnd('Add rows');

        // $.each(rows, function(i, n) {
        // var rowIndex = $toGrid.datagrid('getRowIndex', n.id);
        // if (rowIndex == -1) {
        // $toGrid.datagrid('appendRow', n);
        // hasSelected = true;
        // }
        // });

        // 确定按钮
        var okBtn = $fromGrid.closest('.easyui-dialog').next().find('a:first');
        okBtn.linkbutton(hasSelected ? 'enable' : 'disable');
    }

    /**
     * 
     * 从结果grid中移除选中的记录。
     */
    function unselect(gridId) {
        var $grid = $(gridId);
        var rows = $grid.datagrid('getSelections');
        if (rows.length > 0) {

            var idArray = [];
            $.each(rows, function(i, n) {
                idArray.push(n.id);
            });

            $.each(idArray, function(i, n) {
                var rowIndex = $grid.datagrid('getRowIndex', n);
                $grid.datagrid('deleteRow', rowIndex);
            });

            $grid.datagrid('unselectAll');
        }

        var resultRows = $grid.datagrid('getRows');

        // 确定按钮
        var okBtn = $grid.closest('.easyui-dialog').next().find('a:first');
        okBtn.linkbutton(resultRows.length > 0 ? 'enable' : 'disable');
    }

    /** ************************** */
    /* initialization logics below */
    /** ************************** */

    /**
     * 打开选择画面。支持左侧树、右侧检索grid式样，以及单grid样式。适用于单选和单Grid多选画面。
     * 
     * @param dialogId 形如'#Global_P00000_PERSON-ControlDialog'
     * @param isTree 是否左侧树
     * @param target 输入控件
     * @param callback 例如项目组的负责人选择，选好后，要把负责人的电话等信息回填到表单里，这时可通过回调实现
     */
    function _openPopupSelect(dialogId, isTree, target, callback) {
        var $dialog = $(dialogId);
        $dialog.data('target', target);
        if (callback) {
            $dialog.data('callback', callback);
        }

        function beforeOpen(isFirstLoad) {

            // 非首次打开时，初始化画面
            if (!isFirstLoad) {

                // 清空检索，参考FilterUtils.clearSearch
                // 因为去掉了高级检索，所以只要处理简单检索
                var $filterForm = $(dialogId + '-FilterForm');
                $filterForm.form('reset');

                // 用来保存普通检索内容的数组，其中每个成员的结构同FilterFieldInfoDto.java
                var $gird = $(dialogId + '-MainDatagrid');
                var SEARCH_ARRAY = $gird.data('SEARCH_ARRAY');

                if (SEARCH_ARRAY && SEARCH_ARRAY.length > 0) {
                    $gird.data('SEARCH_ARRAY', new Array());
                }

                if (isTree) {
                    // 刷新树，刷新成功后自动触发刷新grid
                    var $tree = $(dialogId + '-SideTree');
                    $tree.tree('reload');
                }

                // grid清空筛选字段并回到第一页
                $gird.datagrid('load', {});
            }

            // 禁用确定按钮
            ControlUtils.toggleWidget($dialog.next().find('a:first'), false);

            return true;
        }

        EasyDialogUtils.openDialog(dialogId, beforeOpen);
    }

    /**
     * 打开选择画面，布局：左侧树、中间检索grid和右侧选择结果grid式样。适用于双Grid多选画面。
     * 不再假定弹出画面是由带按钮的textbox输入控件触发，因此不再将选择的值回填到输入控件。双grid都是多选，并且必须提供callback回调，选择的值全部交给callback处理。
     * 
     * @param dialogId 形如'#Global_P00000_PERSON-ControlDialog'
     * @param callback 回调对象，必须包含onChooseRows的函数，用来处理选择结果
     */
    function _openDblGridPopupSelect(dialogId, callback) {
        var $dialog = $(dialogId);
        if (callback && $.isFunction(callback.onChooseRows)) {
            $dialog.data('callback', callback);
        } else {
            return;
        }

        function beforeOpen(isFirstLoad) {

            // 因为dialog每次打开都会自动加载href(cache设为false)，因此不用手动初始化

            // 禁用确定按钮
            ControlUtils.toggleWidget($dialog.next().find('a:first'), false);

            return true;
        }

        EasyDialogUtils.openDialog(dialogId, beforeOpen);
    }

    function handleCellSelect() {
        return;
    }


    return {
        openPersonSelect: openPersonSelect,
        openLinkSelect: openLinkSelect,
        openProjectPersonSelect: openProjectPersonSelect,
        openBudgetEstimateSumSelect: openBudgetEstimateSumSelect,
        openPersonGeneralSelect: openPersonGeneralSelect,
        openPersonDblGridSelect: openPersonDblGridSelect,
        openExpertDblGridSelect: openExpertDblGridSelect,
        openPostSelect: openPostSelect,
        openSiteSelect: openSiteSelect,
        openSegmentSelect: openSegmentSelect,
        openRelatedUnitSelect: openRelatedUnitSelect,
        openProjectSelect: openProjectSelect,
        openPlanSelect: openPlanSelect,
        openPaperLibrarySelect: openPaperLibrarySelect,
        openProjectRoleSelect: openProjectRoleSelect,
        handleRowSelect: handleRowSelect,
        handleDblClickRow: handleDblClickRow,
        handleClickOk: handleClickOk,
        handleGeneralClickOk: handleGeneralClickOk,
        select: select,
        unselect: unselect,
        handleDblGridClickOk: handleDblGridClickOk,
        handleCellSelect: handleCellSelect
    }; // PopupSelectUtils
})(jQuery);


/**
 * 画面以及选择弹出画面中，省份城市联动相关的逻辑。
 * 
 * 需要重写前端的"新建打开/修改打开/连续新建保存/双击grid"的事件处理函数，增加callback回调。
 * 并且还需要重写Controller的find，以便在弹出修改画面返回后台数据时，将相应的下拉框内容一起提供。
 */
var NoticeUtils = (function($) {


    function publish(maingrid) {
        var selected = $(maingrid).datagrid('getSelected');
        console.log(selected);
        if (selected) {
            var noticeAnnouncementBean = {};
            noticeAnnouncementBean.id = selected.id;
            if (selected.publish_ != null && selected.publish_ != 0) {
                AlertUtils.msg('warning', '该公告已经发布，请勿重复操作！');
            } else {
                noticeAnnouncementBean.publish_ = 1;
                var options = {
                    url: BASE_URL + '/notice/announcement/publish.json?randomNum=' + Math.random(),
                    type: 'POST',
                    data: JSON.stringify(noticeAnnouncementBean),
                    dataType: 'json',
                    contentType: 'application/json',
                    success: function(response, statusText, xhr, jqForm) {
                        MainUtils.closeLoading();// 关闭loading提示
                        $(maingrid).datagrid('reload');
                    },
                    error: MainUtils.handleAjaxFormError
                };
                $.ajax(options);// jquery.form plugin
                MainUtils.openLoading();// 弹出loading提示
            }
        }
    }

    /**
     * 省份下拉框的选择事件处理，联动获取城市列表。站点画面以及站点弹出选择画面都要使用。
     * 
     * @param target 输入控件
     */
    function handleSelectProject(target) {
    	debugger

        var $p = $(target);
        var project = $p.val();

        var options = {
            url: BASE_URL + '/project/virtual-org/list-pid-options.json',
            type: 'POST',
            dataType: 'json',
            data: {
                project_: project
            },
            success: function(response, statusText, xhr, jqForm) {
                if (MainUtils.processJsonResult(response, false)) {
                    var cityOptions = response.dataMap.rows;

                    var $c = $p.closest('form').find('select[name="recivers_"]');
                    $c.empty();
                    // if (!$c.is('[data-mandatory]')) {
                    // $c.append('<option value="">&nbsp;</option>');
                    // }
                    $c.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < cityOptions.length; i++) {
                        $c.append('<option value="' + cityOptions[i].id + '">'
                                + cityOptions[i].text + '</option>');
                    }

                } else {
                    // if (response.message) {
                    // $.messager.alert(LABLE_ERROR, response.message, 'error');
                    // } else {
                    // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
                    // }
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

    /**
     * 新建画面初始化的回调，用来清空城市下拉框的内容。也用于连续新建保存后的回调。
     */
    function onInitAddFormWidget(control) {
        if (ControlUtils.getControlName(control) == 'city_') {
            control.empty();
            control.append('<option value="">&nbsp;</option>');
        }
    }

    /**
     * 修改画面初始化的回调，用来设置城市下拉框的内容。
     * 
     * 可以参考SiteController，在Controller的find方法中，需要把该数据所属的省份对应的城市列表返回给前端。
     */
    function beforeInitEditFormWidget(control, isFirstLoad, response) {
        if (ControlUtils.getControlName(control) == 'city_') {
            var cityOptions = response.dataMap.rows;
            control.empty();
            // if (!control.is('[data-mandatory]')) {
            // control.append('<option value="">&nbsp;</option>');
            // }
            control.append('<option value="">&nbsp;</option>');
            for (var i = 0; i < cityOptions.length; i++) {
                control.append('<option value="' + cityOptions[i].id + '">' + cityOptions[i].text
                        + '</option>');
            }
        }
    }

    /**
     * easyui datagrid双击数据行的事件处理，弹出修改画面。主要是为了传入callback对象。
     * 
     * 可以参考SiteController，Modle中通过key(dblClickRowHandler)来指定grid.tag中使用的双击事件处理函数。
     */
    function handleDblClickRow(index, row) {
        var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
        CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url.replace(
                'list', 'find'), '#' + this.id, index, ProvinceCityUtils);
    }

    return {
        handleSelectProject: handleSelectProject,
        onInitAddFormWidget: onInitAddFormWidget,
        beforeInitEditFormWidget: beforeInitEditFormWidget,
        handleDblClickRow: handleDblClickRow,
        publish: publish
    }; // ProvinceCityUtils
})(jQuery);

/**
 * 画面以及选择弹出画面中，省份城市联动相关的逻辑。
 * 
 * 需要重写前端的"新建打开/修改打开/连续新建保存/双击grid"的事件处理函数，增加callback回调。
 * 并且还需要重写Controller的find，以便在弹出修改画面返回后台数据时，将相应的下拉框内容一起提供。
 */
var ProvinceCityUtils = (function($) {

    /**
     * 省份下拉框的选择事件处理，联动获取城市列表。站点画面以及站点弹出选择画面都要使用。
     * 
     * @param target 输入控件
     */
    function handleSelectProvince(target) {

        var $p = $(target);
        var province = $p.val();

        var options = {
            url: BASE_URL + '/system/city/list-options.json',
            type: 'POST',
            dataType: 'json',
            data: {
                provinceId: province
            },
            success: function(response, statusText, xhr, jqForm) {
                if (MainUtils.processJsonResult(response, false)) {
                    var cityOptions = response.dataMap.rows;

                    var $c = $p.closest('form').find('select[name="city_"]');
                    $c.empty();
                    // if (!$c.is('[data-mandatory]')) {
                    // $c.append('<option value="">&nbsp;</option>');
                    // }
                    $c.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < cityOptions.length; i++) {
                        $c.append('<option value="' + cityOptions[i].id + '">'
                                + cityOptions[i].text + '</option>');
                    }

                } else {
                    // if (response.message) {
                    // $.messager.alert(LABLE_ERROR, response.message, 'error');
                    // } else {
                    // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
                    // }
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

    /**
     * 新建画面初始化的回调，用来清空城市下拉框的内容。也用于连续新建保存后的回调。
     */
    function onInitAddFormWidget(control) {
        if (ControlUtils.getControlName(control) == 'city_') {
            control.empty();
            control.append('<option value="">&nbsp;</option>');
        }
    }

    /**
     * 修改画面初始化的回调，用来设置城市下拉框的内容。
     * 
     * 可以参考SiteController，在Controller的find方法中，需要把该数据所属的省份对应的城市列表返回给前端。
     */
    function beforeInitEditFormWidget(control, isFirstLoad, response) {
        if (ControlUtils.getControlName(control) == 'city_') {
            var cityOptions = response.dataMap.rows;
            control.empty();
            // if (!control.is('[data-mandatory]')) {
            // control.append('<option value="">&nbsp;</option>');
            // }
            control.append('<option value="">&nbsp;</option>');
            for (var i = 0; i < cityOptions.length; i++) {
                control.append('<option value="' + cityOptions[i].id + '">' + cityOptions[i].text
                        + '</option>');
            }
        }
    }

    /**
     * easyui datagrid双击数据行的事件处理，弹出修改画面。主要是为了传入callback对象。
     * 
     * 可以参考SiteController，Modle中通过key(dblClickRowHandler)来指定grid.tag中使用的双击事件处理函数。
     */
    function handleDblClickRow(index, row) {
        var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
        CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url.replace(
                'list', 'find'), '#' + this.id, index, ProvinceCityUtils);
    }
    
    /**
     * 分年度预算中数量或单价变化，自动计算合计。
     * 
     * @param target 输入控件
     */
    function handleChangeTotal(target) {

        var $p = $(target);
        
        var $t = $p.closest('form').find('input[name="taxInclusivePrice"]');
        var taxInclusivePrice = $t.val();
        
        var $c = $p.closest('form').find('input[name="count"]');
        var count = $c.val();
        
        var $tt = $p.closest('form').find('input[name="taxInclusivePriceTotal"]');
        var total = taxInclusivePrice * count;  
        $tt.val(total);
        
    }
    
    /**
     * 分年度预算中当年支付数量或当年支付比例变化，自动计算当年支付金额。
     * 
     * @param target 输入控件
     */
    function handleChangePayment(target) {

        var $p = $(target);
        
        var $t = $p.closest('form').find('input[name="taxInclusivePrice"]');
        var taxInclusivePrice = $t.val();
        
        var $c = $p.closest('form').find('input[name="paymentCount"]');
        var paymentCount = $c.val();
        
        var $p = $p.closest('form').find('input[name="paymentPercent"]');
        var paymentPercent = $p.val();
        
        var $tt = $p.closest('form').find('input[name="paymentAmount"]');
        var total = taxInclusivePrice * paymentCount * paymentPercent / 100;  
        $tt.val(total);
        
    }

    return {
    	handleChangePayment: handleChangePayment,
    	handleChangeTotal: handleChangeTotal,
        handleSelectProvince: handleSelectProvince,
        onInitAddFormWidget: onInitAddFormWidget,
        beforeInitEditFormWidget: beforeInitEditFormWidget,
        handleDblClickRow: handleDblClickRow
    }; // ProvinceCityUtils
})(jQuery);

/**
 * 画面以及选择弹出画面中，组织部门联动相关的逻辑。
 * 
 * 需要重写前端的"新建打开/修改打开/连续新建保存/双击grid"的事件处理函数，增加callback回调。
 * 并且还需要重写Controller的find，以便在弹出修改画面返回后台数据时，将相应的下拉框内容一起提供。
 */
var OrgDeptUtils = (function($) {

    /**
     * 组织下拉框的选择事件处理，联动获取部门列表。岗位画面以及人员弹出选择画面都要使用。
     * 
     * @param target 输入控件
     */
    function handleSelectOrg(target) {

        var $p = $(target);
        var org = $p.val();
        debugger;
        var options = {
            url: BASE_URL + '/hr/post/list-dept-type.json',
            type: 'POST',
            dataType: 'json',
            data: {
                orgId: org
            },
            success: function(response, statusText, xhr, jqForm) {
                if (MainUtils.processJsonResult(response, false)) {
                    var deptOptions = response.dataMap.rows;

                    var $c = $p.closest('form').find('select[name="dept_"]');
                    $c.empty();
                    // if (!$c.is('[data-mandatory]')) {
                    // $c.append('<option value="">&nbsp;</option>');
                    // }
                    $c.append('<option value="">&nbsp;</option>');
                    for (var i = 0; i < deptOptions.length; i++) {
                        $c.append('<option value="' + deptOptions[i].id + '">'
                                + deptOptions[i].text + '</option>');
                    }

                } else {
                    // if (response.message) {
                    // $.messager.alert(LABLE_ERROR, response.message, 'error');
                    // } else {
                    // $.messager.alert(LABLE_ERROR, MSG_REMOTE_SERVER_ERROR, 'error');
                    // }
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

    /**
     * 新建画面初始化的回调，用来清空部门下拉框的内容。也用于连续新建保存后的回调。
     */
    function onInitAddFormWidget(control) {
        if (ControlUtils.getControlName(control) == 'dept_') {
            control.empty();
            control.append('<option value="">&nbsp;</option>');
        }
    }

    /**
     * 修改画面初始化的回调，用来设置部门下拉框的内容。
     * 
     * 可以参考PostController，在Controller的find方法中，需要把该数据所属的org对应的dept列表返回给前端。
     */
    function beforeInitEditFormWidget(control, isFirstLoad, response) {
        if (ControlUtils.getControlName(control) == 'dept_') {
            var deptOptions = response.dataMap.rows;
            control.empty();
            // if (!control.is('[data-mandatory]')) {
            // control.append('<option value="">&nbsp;</option>');
            // }

            if (deptOptions != null) {
                control.append('<option value="">&nbsp;</option>');
                for (var i = 0; i < deptOptions.length; i++) {
                    control.append('<option value="' + deptOptions[i].id + '">'
                            + deptOptions[i].text + '</option>');
                }
            }

        }
    }

    /**
     * easyui datagrid双击数据行的事件处理，弹出修改画面。主要是为了传入callback对象。
     * 
     * 可以参考PostController，Modle中通过key(dblClickRowHandler)来指定grid.tag中使用的双击事件处理函数。
     */
    function handleDblClickRow(index, row) {
        var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
        CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url.replace(
                'list', 'find'), '#' + this.id, index, OrgDeptUtils);
    }

    return {
        handleSelectOrg: handleSelectOrg,
        onInitAddFormWidget: onInitAddFormWidget,
        beforeInitEditFormWidget: beforeInitEditFormWidget,
        handleDblClickRow: handleDblClickRow
    }; // OrgDeptUtils
})(jQuery);

/**
 * 审核相关通用逻辑
 */
var FlowableUtils = (function($) {

    // 打开提交审核对话框
    function openSubmitAuditFormDialog(dialogId, flowableDivId, flowableSettingComboboxId) {
        if (!dialogId) {
            return;
        }
        var $dialog = $(dialogId);
        var $form = $dialog.find('form');

        var $settingCombobox = $(flowableSettingComboboxId);

        function process(isFirstLoad) {
            $form.form('reset');
            $(flowableDivId).empty();
            $settingCombobox.combobox('reload');

            if (isFirstLoad) {
                $settingCombobox.combobox({
                    'onChange': function(newValue, oldValue) {
                        if (newValue) {
                            FLOWABLE_UI_UTILS.generateFlowableView(newValue, flowableDivId);
                        }
                    }
                })
            }

            $form.form('disableValidation');
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
                process(true);
                MainUtils.closeLoading();
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(false);
        }
    }

    // 提交审核-启动流程-设置记录的流程状态为审核中 Project的状态为 flowStatusProject,其他实体为 flowStatus
    function saveProcessInstanceFormData(dialogId, url, datagridId) {

        if (!dialogId || !datagridId) {
            return;
        }

        var $dialog = $(dialogId);
        var $datagrid = $(datagridId);

        var selected = $datagrid.datagrid('getSelected');

        if (!selected) {
            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
            return;
        }

        var $form = $dialog.find('form');
        $form.form('enableValidation');
        if (!$form.form('validate')) {
            return;
        }

        var projectId = '';
        var projectName = '';

        if (selected.project_) {
            projectId = selected.project_;
        } else {
            projectId = selected.id;
        }

        if (selected.projectName) {
            projectName = selected.projectName;
        }

        if (selected.projectText) {
            projectName = selected.projectText;
        }

        var $combobox = $dialog.find('[textboxname="flowableSetting_"]');
        var flowDetailBean = {};

        flowDetailBean.correlateDataId = selected.id;
        flowDetailBean.correlateProjectId = projectId;
        flowDetailBean.correlateProjectName = projectName;
        flowDetailBean.flowableSettingId = $combobox.combobox('getValue');
        flowDetailBean.flowableSettingName = $combobox.combobox('getText');

        var options = {
            url: url,
            dataType: 'json',
            type: 'POST',
            data: JSON.stringify(flowDetailBean),
            contentType: 'application/json',
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示
                var jsonObj = response;

                if (MainUtils.processJsonResult(jsonObj, true)) {
                    $datagrid.datagrid('reload');
                    EasyDialogUtils.closeFormDialog(dialogId);
                }
            },
            error: MainUtils.handleAjaxFormError
        };

        $.ajax(options);
        MainUtils.openLoading();
    }

    // 打开简报预览窗口
    function openReportProgressDialog(dialogId, progressId) {
        MainUtils.openLoading();
        // 打开简报
        if ("#P5015-PLAN_PROGRESS-SubmitAuditFormDialog" == dialogId) {
            // 简报信息
            var options = {
                url: BASE_URL + '/schedule/progress/find-reportProgress.json',
                type: 'POST',
                dataType: 'json',
                data: {
                    id: progressId
                },
                success: function(response, statusText, xhr, jqForm) {
                    MainUtils.closeLoading();// 关闭loading提示
                    var json = response.dataMap.returnObj;
                    $("#reportProgress-reportTypeText").text(json.reportTypeText);
                    $("#reportProgress-progressDate").text(json.progressDate);
                    $("#reportProgress-progressName").text(json.progressName);
                    $("#reportProgress-progressRemark").text(json.remark);
                    $("#reportProgress-progressDescription").text(json.description || '');

                    $('#reportProgressViewDialog').dialog('open').dialog('center').scrollTop(0);

                    // 简报任务信息
                    $('#reportProgressTaskViewDg').datagrid({
                        data: json.planProgressTaskBeans
                    });
                },
                error: MainUtils.handleAjaxFormError
            }
            $.ajax(options);
        }
    }

    // 通用审核界面
    function openFlowAuditDialog(datagridId) {

        var selected = $(datagridId).datagrid('getSelected');

        if (!selected) {
            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
            return;
        }

        if (selected.businessCodeUrl) {
            var params = {
                'timestamp': new Date().getTime(),
                'isFromFlowAudit': 1,
                'isViewAudit': 0,
                'correlateDataId': selected.correlateDataId,
                'businessEntity': selected.businessEntity,
                'auditTaskId': selected.auditTaskId
            }
            Main.openTab(selected.pendingMatters, BASE_URL + selected.businessCodeUrl, null, null,
                    params, function(panel) {

                        var $panel = $(panel);
                        var panelId = $panel.find('input[name="penalId_ex"]').val();
                        var findUrl = $panel.find('input[name="findUrl_ex"]').val();
                        CrudUtils.openAuditViewPanel('#' + panelId, findUrl,
                                selected.correlateDataId, null)
                    });
        } else {
            AlertUtils.msg('warning', '没有审批接口！');
            return;
        }
    }

    // 通用审核结果提交
    function saveAuditFormData(formId) {

        var $form = $('#' + formId);
        if (!$form.form('validate')) {
            return;
        }

        var param = {};

        var options = {
            url: BASE_URL + '/flowable/setting/submit-audit.json',
            dataType: 'json',
            data: param,
            success: function(response, statusText, xhr, jqForm) {
                MainUtils.closeLoading();// 关闭loading提示

                var jsonObj = response;

                if (MainUtils.processJsonResult(jsonObj, true)) {

                    var json = jsonObj.dataMap.returnObj;

                    $('#P950202-FLOWABLE_PENDING_PROCESS-MainDatagrid').datagrid('reload');
                    var $tab = $('#MainContentTab');
                    var tab = $tab.tabs('getSelected');
                    var index = $tab.tabs('getTabIndex', tab);

                    $tab.tabs('close', index);
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $form.ajaxSubmit(options);// jquery.form plugin
        MainUtils.openLoading();// 弹出loading提示
    }

    function queryFlowableSettingIdForAuditLog(correlateDataId) {

        if (!correlateDataId) {
            return;
        }

        var options = {
            url: BASE_URL + '/flowable/setting/get-flowable-setting-with-correlateDataId.json',
            type: 'POST',
            data: {
                'correlateDataId': correlateDataId
            },
            dataType: 'json',
            success: function(response, statusText, xhr, jqForm) {
                var jsonObj = response;
                if (MainUtils.processJsonResult(jsonObj, false)) {

                    FLOWABLE_UI_UTILS.generateFlowableView(jsonObj.dataMap.returnVal,
                            '#_AuditLogFlowableView');
                }
            },
            error: MainUtils.handleAjaxFormError
        };
        $.ajax(options);
        $('#_AuditLogFlowableView').empty();
    }

    // 打开审核日志对话框
    function openAuditLogDialog(dialogId, datagridId) {
        if (!dialogId || !datagridId) {
            return;
        }
        var $dialog = $(dialogId);
        var $datagrid = $(datagridId);

        var $logDatagrid = $('#_AuditLogDatagrid');
        function process(isFirstLoad) {

            var selected = $datagrid.datagrid('getSelected');

            var options = $logDatagrid.datagrid('options');
            options.queryParams.correlateDataId = selected.id;

            $logDatagrid.datagrid('reload', BASE_URL + '/flowable/setting/audit-log-info.json');
            queryFlowableSettingIdForAuditLog(selected.id);

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
                process(true);
                MainUtils.closeLoading();
            }, 0); // setTimeout 0，使得openLoading能正常展现遮挡
        } else {
            process(false);
        }
    }

    function openWithdrawDialog(dialogId, datagridId) {

        if (!dialogId || !datagridId) {
            return;
        }
        var $dialog = $(dialogId);
        var $datagrid = $(datagridId);

        // 必须选中至少一条记录
        var selected = $(datagridId).datagrid('getSelected');

        if (!selected) {
            // $.messager.alert(LABLE_WARNING, MSG_WARN_NOT_SELECT, 'warning');
            AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
            return;
        }


        $.messager.confirm(LABLE_CONFIRM, MSG_CONFIRM_WITHDRAW, function(r) {
            if (r) {

                var options = {
                    url: BASE_URL + '/flowable/setting/withdraw-with-correlateDataId.json',
                    type: 'POST',
                    data: {
                        'correlateDataId': selected.id
                    },
                    dataType: 'json',
                    success: function(response, statusText, xhr, jqForm) {
                        MainUtils.closeLoading();// 关闭loading提示

                        var jsonObj = response;
                        if (MainUtils.processJsonResult(jsonObj, true)) {
                            $datagrid.datagrid('clearSelections');
                            $datagrid.datagrid('reload');
                        }
                    },
                    error: MainUtils.handleAjaxFormError
                };

                $.ajax(options);
                MainUtils.openLoading();// 弹出loading提示
            }
        });
    }

    return {
        openSubmitAuditFormDialog: openSubmitAuditFormDialog,
        openFlowAuditDialog: openFlowAuditDialog,
        saveProcessInstanceFormData: saveProcessInstanceFormData,
        saveAuditFormData: saveAuditFormData,
        openAuditLogDialog: openAuditLogDialog,
        openWithdrawDialog: openWithdrawDialog,
        openReportProgressDialog: openReportProgressDialog
    };
})(jQuery);
