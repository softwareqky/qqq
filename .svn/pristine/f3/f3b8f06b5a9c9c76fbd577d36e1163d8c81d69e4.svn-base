<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    if (!ARCHIVE) {
        var ARCHIVE = (function($) {

            /**
             * 新建文件夹及上传文件时的回调，用来设定文件夹或文件所在的目录。
             */
            function beforeSubmit(options) {
                var con = $('#P9001-ARCHIVE-MainDatagrid').data('EXTRA_FILTER_OBJ');
                options.data.pid = con && con.value ? con.value : '/'; // 根目录的id是"/"
            }

            /**
             * 新增后的回调，不向datagrid插入数据，而是刷新之。
             */
            function beforeDatagridInsert(datagridId, json) {
                $(datagridId).datagrid('reload');
                return false;
            }

            /**
             * 重写GridUtils.handleSelect，启用/禁用常规的button而非easyui的linkbutton。
             * 
             * @param index 同easyui的datagrid的onSelect事件
             * @param row
             */
            function handleSelect(index, row) {
                var rows = $(this).datagrid('getSelections');

                var isAllFile = true;
                var isAllWritable = true;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].isDir == 1) {
                        isAllFile = false;
                    }
                    if (rows[i].authType != 2) {
                        isAllWritable = false;
                    }
                }

                // datagrid的id形如'pid-datatype-MainDatagrid'，功能按钮的id形如'pidF01'
                var idPre = this.id.substring(0, this.id.indexOf('-'));

                // 删除，选中时启用
                $('#' + idPre + 'F02').prop('disabled', rows.length == 0 || !isAllWritable);

                // 修改，单选启用
                $('#' + idPre + 'F03').prop('disabled', rows.length != 1 || !isAllWritable);

                // 下载，选中且都是文件时启用
                $('#' + idPre + 'F17').prop('disabled', rows.length == 0 || !isAllFile);

                // 复制到/移动到，选中且都是文件时启用
                $('#' + idPre + 'F51').prop('disabled', rows.length == 0 || !isAllFile);
                $('#' + idPre + 'F52').prop('disabled', rows.length == 0 || !isAllFile);

                // 设置grid右上侧状态栏的选中信息
                var gridstatus = $('#P9001-ARCHIVE-MainDatagridToolbar div.gridstatus');
                gridstatus.find('span:eq(1)').remove();
                gridstatus.append('<span><s:message code="message.info.grid.selected.a" />'
                        + rows.length + '<s:message code="message.info.grid.selected.b" /></span>');
            }

            /**
             * 重写FilterUtils.searchSimple，除了检索逻辑，还要显示pname列。
             */
            function searchSimple(datagridId, formId) {

                // 检索前，先验证
                var control = $(formId).find('input');
                var v = ControlUtils.getControlValue(control);
                if ($.trim(v) == '') {
                    return;
                }

                FilterUtils.searchSimple(datagridId, formId);

                // 检索后，显示pname列，切换文件夹后，隐藏pname列
                $(datagridId).datagrid('showColumn', 'pname');
            }

            /**
             * 重写CrudUtils.batchDeleteSelected，做两次确认。防止误删，并且两次确认的"是"和"否"交换位置，防止连点两次。提示改成"确定删除选中目录或文件？"。
             */
            function batchDeleteSelected(url, datagridId) {
                if (!url || !datagridId) {
                    return;
                }

                // 必须选中至少一条记录
                var selectedRows = $(datagridId).datagrid('getSelections');
                if (selectedRows.length > 0) {
                    _dblConfirmDelete(function() {
                        var idArray = [];
                        for (var i = 0; i < selectedRows.length; i++) {
                            idArray.push(selectedRows[i].id);
                        }
                        CrudUtils.doBatchDelete(url, idArray.join(), datagridId);
                    });
                } else {
                    AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    return;
                }
            }

            /**
             * 删除前，进行两次确认，并且两次确认的"是"和"否"交换位置。
             */
            function _dblConfirmDelete(func, isDeleteCurrentRow, filename) {
                var firstMsg = '<s:message code="message.confirm.delete.archive" />';
                var secondMsg = '<s:message code="message.confirm.delete.archive.again" />';
                if (isDeleteCurrentRow) {
                    firstMsg = '<s:message code="message.confirm.delete.cur.archive" /> '
                            + filename + ' ?';
                    secondMsg = '<s:message code="message.confirm.delete.cur.archive.again" /> '
                            + filename + ' ?';
                }
                $.messager.confirm(LABLE_CONFIRM, firstMsg, function(r) {
                    if (r) {
                        $.messager.confirm({
                            ok: '<s:message code="ui.common.cancel" />',
                            cancel: '<s:message code="ui.common.ok" />',
                            title: LABLE_CONFIRM,
                            msg: secondMsg,
                            fn: function(rr) {
                                if (!rr) {
                                    func();
                                }
                            }
                        });
                    }
                });
            }

            /**
             * 批量下载文件。
             */
            function download(url, datagridId) {
                if (!url || !datagridId) {
                    return;
                }

                // 必须选中至少一条记录
                var selectedRows = $(datagridId).datagrid('getSelections');
                if (selectedRows.length == 0) {
                    AlertUtils.msg('warning', MSG_WARN_NOT_SELECT);
                    return;
                }

                var idArray = [];
                for (var i = 0; i < selectedRows.length; i++) {
                    idArray.push(selectedRows[i].id);
                }

                url = url + '?idsToDownload=' + idArray.join();

                MainUtils.downloadFile(url);
            }

            /**
             * 下载单个文件。
             */
            function downloadFile(id) {
                var url = BASE_URL + '/archive/project-archive/download.json?idsToDownload=' + id;
                MainUtils.downloadFile(url);
            }

            /**
             * 预览文件。对于一些无法预览的文件类型，如zip等，则提供下载。
             * 
             * 为前端提供唯一的预览入口，无论是什么类型的文件，只要可以预览，URL都一样。
             */
            function preview(id, fileName) {

                var ext = '';
                var indexDot = fileName.lastIndexOf('.');
                if (indexDot > -1) {
                    ext = fileName.substring(indexDot + 1).toLowerCase();
                }

                var previewArray = ['jpeg', 'jpg', 'bmp', 'png', 'gif', 'txt', 'xml', 'pdf', 'doc',
                        'docx', 'xls', 'xlsx'];

                // 直接下载
                if ($.inArray(ext, previewArray) == -1) {
                    downloadFile(id);
                    return;
                }

                // 查询参数file是为了配合PDF.js使用(当后台文件类型是doc/docx/pdf)
                var url = BASE_URL + '/archive/preview/' + id + '/main.htm?file=' + BASE_URL
                        + '/archive/preview/' + id + '/pdf.htm';

                EasyDialogUtils.openMaxModal(url, new Date().getTime());
            }

            /**
             * 文件名的式样。
             * 
             * @param value 字符值
             * @param row easyui的datagrid中的某一行数据
             * @param index easyui的datagrid中的行index
             */
            function archiveNameFormatter(value, row, index) {

                var icon;
                if (row.isDir == 1) {
                    icon = 'fa-folder-o';
                } else {
                    icon = FileboxUtils.getIcon(value);
                }

                var archiveName = row.archiveName.replace("'", ''); // 防止单引号引起传值问题

                var aNameDiv = '<div class="archive-icon"><i class="fa fa-fw '+icon+'"></i></div><div class="archive-name"><a href="javascript:void(0)" onclick="ARCHIVE.preview('
                        + "'" + row.id + "','" + archiveName + "'" + ')">' + value + '</a></div>';

                if (row.isDir == 1) {
                    aNameDiv = '<div class="archive-icon"><i class="fa fa-fw '+icon+'"></i></div><div class="archive-name"><a href="javascript:void(0)" onclick="ARCHIVE.chdir('
                            + "'" + row.id + "'" + ')">' + value + '</a></div>';
                }

                var aToolDiv = '<div class="archive-name-tool">';
                if (row.isDir == 0) {
                    aToolDiv += '<i class="fa fa-fw fa-download" onclick="ARCHIVE.downloadFile('
                            + "'" + row.id + "'" + ')"></i>';
                }
                aToolDiv += '<i class="fa fa-fw fa-ellipsis-h" onclick="ARCHIVE.showArchiveToolMenu(event,'
                        + "'"
                        + row.id
                        + "','"
                        + archiveName
                        + "',"
                        + index
                        + ','
                        + row.authType
                        + ',' + row.isDir + ')"></i>';
                aToolDiv += '</div><div class="clearfix"></div>';

                return aNameDiv + aToolDiv;
            }

            /**
             * 版本的文件名的式样。
             * 
             * @param value 字符值
             * @param row easyui的datagrid中的某一行数据
             * @param index easyui的datagrid中的行index
             */
            function archiveVersionNameFormatter(value, row, index) {
                if (row.isCurrent == 1) {
                    return value
                            + '&nbsp;<i class="fa fa-fw fa-circle" style="color:#4AAE48;"></i>';
                } else {
                    return value;
                }
            }

            /**
             * 版本的操作的式样。
             * 
             * @param value 字符值
             * @param row easyui的datagrid中的某一行数据
             * @param index easyui的datagrid中的行index
             */
            function archiveVersionOpFormatter(value, row, index) {

                var archiveName = row.archiveName.replace("'", ''); // 防止单引号引起传值问题

                return '<button class="btn btn-info btn-xs" onclick="ARCHIVE.downloadFile('
                        + "'"
                        + row.id
                        + "'"
                        + ')"><i class="fa fa-fw fa-download"></i><s:message code="ui.common.download" /></button>&nbsp;<button class="btn btn-info btn-xs" onclick="ARCHIVE.preview('
                        + "'"
                        + row.id
                        + "','"
                        + archiveName
                        + "'"
                        + ')"><i class="fa fa-fw fa-eye"></i><s:message code="ui.common.preview" /></button>';
            }

            /**
             * 弹出工具菜单，包括删除、修改信息等功能。
             * 
             * @param e
             * @param id 文件id
             * @param name 文件名
             * @param index easyui的datagrid中的行index
             * @param auth 权限
             * @param isDir 是否文件夹
             */
            function showArchiveToolMenu(e, id, name, index, auth, isDir) {

                var $menu = $('#P9001-ARCHIVE-ArchiveNameToolMenu');
                if (!$menu.prop('loaded')) {
                    $menu.prop('loaded', true);
                    $menu.menu();
                }

                // 根据权限，启用或禁用删除和修改
                var itemDelete = $menu.menu('findItem', {
                    name: 'delete'
                });
                var itemEdit = $menu.menu('findItem', {
                    name: 'edit'
                });
                var itemVersion = $menu.menu('findItem', {
                    name: 'version'
                });
                if (auth == 2) {
                    $menu.menu('enableItem', itemDelete.target);
                    $menu.menu('enableItem', itemEdit.target);
                } else {
                    $menu.menu('disableItem', itemDelete.target);
                    $menu.menu('disableItem', itemEdit.target);
                }

                if (isDir == 1) {
                    $menu.menu('disableItem', itemVersion.target);
                } else {
                    $menu.menu('enableItem', itemVersion.target);
                }

                $menu.data('targetId', id).data('targetName', name).data('targetRowIndex', index);

                $menu.menu('show', {
                    left: e.pageX - 35,
                    top: e.pageY
                });
            }

            /**
             * 点击工具栏菜单项的响应处理。
             * 
             * @param item
             */
            function onNameToolMenuClick(item) {

                var $this = $(this);
                var id = $this.data('targetId');
                var name = $this.data('targetName');
                var rowIndex = $this.data('targetRowIndex');
                if (item.name == 'delete') {
                    _dblConfirmDelete(function() {
                        CrudUtils.doBatchDelete(BASE_URL + '/archive/project-archive/delete.json',
                                id, '#P9001-ARCHIVE-MainDatagrid');
                    }, true, name);
                } else if (item.name == 'edit') {
                    // var rowIndex = $('#P9001-ARCHIVE-MainDatagrid').datagrid('getRowIndex', id);
                    CrudUtils.openEditFormDialog('#P9001-ARCHIVE-EditFormDialog', BASE_URL
                            + '/archive/project-archive/find.json', '#P9001-ARCHIVE-MainDatagrid',
                            rowIndex);
                } else if (item.name == 'version') {
                    openVersion(id);
                }
            }

            /**
             * Grid中，上级文件名这一列的式样。
             * 
             * @param value 字符值
             * @param row easyui的datagrid中的某一行数据
             * @param index easyui的datagrid中的行index
             */
            function pnameFormatter(value, row, index) {
                var pid = row.pid || '';
                return '<a href="javascript:void(0)" onclick="ARCHIVE.chdir(' + "'" + pid + "'"
                        + ')">' + value + '</a>';
            }

            /**
             * 重写MainUtils.datagridLoadFilter，设置面包屑和grid状态栏。
             */
            function datagridLoadFilter(data) {

                // 如果服务端发生错误，则弹出错误提示并返回空结果
                if (!MainUtils.processJsonResult(data, false)) {

                    if (data.total && data.rows) {
                        return data;
                    }

                    AlertUtils.msg(data.icon || 'error', data.message || MSG_REMOTE_SERVER_ERROR);
                    return {
                        total: 0,
                        rows: []
                    };
                }

                var gridData;
                if (data.dataMap) {
                    gridData = data.dataMap;
                } else {
                    gridData = {
                        total: 0,
                        rows: []
                    };
                }

                // 设置grid右上侧状态栏的加载记录数信息
                var gridstatus = $('#P9001-ARCHIVE-MainDatagridToolbar div.gridstatus');
                gridstatus.empty().html(
                        '<span><s:message code="message.info.grid.total.a" />' + gridData.total
                                + '<s:message code="message.info.grid.total.b" /></span>');

                // 设置grid左上侧的面包屑
                var breadcrumbList = data.dataMap.returnObj;
                var breadcrumb = $('#P9001-ARCHIVE-MainDatagridToolbar ol.breadcrumb');
                breadcrumb.empty();

                // 最多显示6个路径节点
                var content = '';
                if (breadcrumbList.length <= 6) {
                    for (var i = 0; i < breadcrumbList.length - 1; i++) {
                        content += _getBreadcrumbItem(breadcrumbList[i]);
                    }
                    content += _getBreadcrumbItem(breadcrumbList[breadcrumbList.length - 1], true);
                } else {
                    for (var i = 0; i < 2; i++) {
                        content += _getBreadcrumbItem(breadcrumbList[i]);
                    }
                    content += '<li>...</li>';
                    for (var i = breadcrumbList.length - 3; i < breadcrumbList.length - 1; i++) {
                        content += _getBreadcrumbItem(breadcrumbList[i]);
                    }
                    content += _getBreadcrumbItem(breadcrumbList[breadcrumbList.length - 1], true);
                }
                breadcrumb.html(content);

                // 权限，设置新建文件夹和上传文件是否可用
                var auth = data.dataMap.returnVal;
                $('#P9001F16').prop('disabled', auth != 2);
                $('#P9001F01').prop('disabled', auth != 2);

                return gridData;
            }

            function _getBreadcrumbItem(item, isLast) {
                if (isLast) {
                    return '<li class="active">' + item.text + '</li>';
                }
                return '<li><a href="javascript:void(0)" onclick="ARCHIVE.chdir(' + "'" + item.id
                        + "'" + ')">' + item.text + '</a></li>';
            }

            /**
             * datagrid双击数据行的事件处理，如果是文件夹，则进入该文件夹。
             */
            function handleDblClickRow(index, row) {
                if (row.isDir == 1) {
                    chdir(row.id);
                }
            }

            /**
             * 变更文件夹。
             */
            function chdir(pid) {
                var con = {};
                con.fieldName = 'pid';
                con.valueType = '4';
                con.filterSearchBy = '1';
                con.valueMatchMode = '0';
                con.captionName = 'pid';
                con.value = pid;

                var $grid = $('#P9001-ARCHIVE-MainDatagrid');
                $grid.data('EXTRA_FILTER_OBJ', con);

                // 变更文件夹后，清除其他检索条件
                var SEARCH_ARRAY = $grid.data('SEARCH_ARRAY');
                if (SEARCH_ARRAY && SEARCH_ARRAY.length > 0) {
                    $grid.data('SEARCH_ARRAY', new Array());
                }

                FilterUtils.doSearch('#P9001-ARCHIVE-MainDatagrid');

                // 切换文件夹后，隐藏pname列，检索后，显示pname列
                $grid.datagrid('hideColumn', 'pname');
            }

            /**
             * 打开分配权限的弹出画面。
             */
            function openAuth() {
                EasyDialogUtils.openFormDialog('#${idMap["auth-dialog"]}');
            }

            /**
             * 打开版本的弹出画面。
             */
            function openVersion(id) {

                function beforeOpen(isFirstLoad) {

                    var $dialog = $('#${idMap["version-dialog"]}');
                    var options = $dialog.dialog('options');
                    options.queryParams.id = id;

                    return true;
                }

                EasyDialogUtils.openDialog('#${idMap["version-dialog"]}', beforeOpen);
            }

            return {
                beforeSubmit: beforeSubmit,
                beforeDatagridInsert: beforeDatagridInsert,
                handleSelect: handleSelect,
                searchSimple: searchSimple,
                batchDeleteSelected: batchDeleteSelected,
                download: download,
                downloadFile: downloadFile,
                preview: preview,
                showArchiveToolMenu: showArchiveToolMenu,
                onNameToolMenuClick: onNameToolMenuClick,
                archiveNameFormatter: archiveNameFormatter,
                archiveVersionNameFormatter: archiveVersionNameFormatter,
                archiveVersionOpFormatter: archiveVersionOpFormatter,
                pnameFormatter: pnameFormatter,
                datagridLoadFilter: datagridLoadFilter,
                handleDblClickRow: handleDblClickRow,
                chdir: chdir,
                openAuth: openAuth
            }; // ARCHIVE
        })(jQuery);
    }
    //]]>

    
</script>