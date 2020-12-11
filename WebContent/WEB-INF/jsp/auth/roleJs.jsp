<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    //<![CDATA[

    if (!ROLE) {
        var ROLE = (function($) {

            /**
             * 双击行时，传入回调对象ROLE。
             */
            function handleDblClickRow(index, row) {
                //debugger;
                /*
                var editFormId = this.id.replace('MainDatagrid', 'EditFormDialog');
                CrudUtils.openEditFormDialog('#' + editFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, index, ROLE);
                */
                //alert("ddd");
                var viewFormId = this.id.replace('MainDatagrid', 'ViewDialog');
                CrudUtils.openViewDialog('#' + viewFormId, $(this).datagrid('options').url
                        .replace('list', 'find'), '#' + this.id, ROLE, row.id);
                
                
            }

            /**
             * 弹出新建画面之后，刷新page树。
             */
            function onOpenAddFormDialog() {
                // 重新加载page树
                $("#${idMap['add-form-dialog']}-PageTree").tree({
                    queryParams: {
                        roleId: ''
                    }
                });
            }

            /**
             * 连续新增后的回调，刷新page树。
             */
            function onContinuousAdd() {
                onOpenAddFormDialog();
            }

            /**
             * 在修改dialog弹出前的处理，刷新page树。
             */
            function beforeOpenEditFormDialog(json) {
                // 重新加载page树
                $("#${idMap['edit-form-dialog']}-PageTree").tree({
                    queryParams: {
                        roleId: json.id
                    }
                });
            }

            /**
             * 在查看dialog弹出前的处理，刷新page树。
             */
            function beforeOpenViewDialog(json) {
                // 重新加载page树
                $("#${idMap['view-dialog']}-PageTree").tree({
                    queryParams: {
                        roleId: json.id
                    }
                });
            }

            /**
             * 在新建/修改画面，提交前，获取角色对应的page列表。
             */
            function beforeSubmit(options, isAdd) {

                var treeId = isAdd ? "#${idMap['add-form-dialog']}-PageTree"
                        : "#${idMap['edit-form-dialog']}-PageTree";

                var pageIdArray = new Array();
                var nodes = $(treeId).tree('getChecked', ['checked', 'indeterminate']);
                $.each(nodes, function(i, o) {
                    if (o.id != 'ALL') {
                        pageIdArray.push(o.id);
                    }
                });
                options.data.pageIdList = pageIdArray;
                options.traditional = true;
            }

            return {
                handleDblClickRow: handleDblClickRow,
                onOpenAddFormDialog: onOpenAddFormDialog,
                onContinuousAdd: onContinuousAdd,
                beforeOpenEditFormDialog: beforeOpenEditFormDialog,
                beforeOpenViewDialog: beforeOpenViewDialog,
                beforeSubmit: beforeSubmit
            }; // ROLE
        })(jQuery);
    }

    //]]>

    
</script>