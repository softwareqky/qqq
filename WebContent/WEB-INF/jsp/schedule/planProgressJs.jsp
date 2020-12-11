<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">
    //<![CDATA[
    /**
     * 实际进度相关的逻辑。
     */
     $("[name='planType'] option:contains(年度建设工作计划)").prop("selected", true);
    var PlanProgressUtils = (function($) {

        function openAddFormDialog(datagridId) {
            return;
        }

        function handleDblClickRow(index, row) {

            var EditOpenedCallback = {};

            EditOpenedCallback.onOpenEditFormDialog = function(json) {
                var reportType = json.reportType;

                var dialogId = '';
                if (1 == reportType) {
                	dialogId = '#reportProgressViewDialog';
                }else if (2 == reportType) {
                    dialogId = '#_BuildEditDialog';
                } else if (3 == reportType) {
                    dialogId = '#_FundEditDialog';
                } else if (4 == reportType) {
                    dialogId = '#_BuildProgressEditDialog';
                }

                var $dialog = $(dialogId);
                $dialog.find('[name="reportType"]').val(reportType);
                $dialog.find('[name="reportType"]').parent().parent().hide();
            }

            var reportType = row.reportType;

            if (1 == reportType || 5 == reportType) {
            	FlowableUtils.openReportProgressDialog('#P5015-PLAN_PROGRESS-SubmitAuditFormDialog', row.id);
            } else if (2 == reportType) {
                var editFormId = this.id.replace('MainDatagrid', '#_BuildEditDialog');
                CrudUtils.openEditFormDialog('#_BuildEditDialog',
                        '/project-edge/schedule/progress/find.json',
                        '#P5015-PLAN_PROGRESS-MainDatagrid', index, EditOpenedCallback);
            } else if (3 == reportType) {
                var editFormId = this.id.replace('MainDatagrid', '#_FundEditDialog');
                CrudUtils.openEditFormDialog('#_FundEditDialog',
                        '/project-edge/schedule/progress/find.json',
                        '#P5015-PLAN_PROGRESS-MainDatagrid', index, EditOpenedCallback);
            } else if (4 == reportType) {
                var editFormId = this.id.replace('MainDatagrid', '#_BuildProgressEditDialog');
                CrudUtils.openEditFormDialog('#_BuildProgressEditDialog',
                        '/project-edge/schedule/progress/find.json',
                        '#P5015-PLAN_PROGRESS-MainDatagrid', index, EditOpenedCallback);
            }
        }

        function openReportTypeDialog(dialogId, reportType) {

            var node = $('#P5015-PLAN_PROGRESS-SideTree').tree('getSelected');

            //CrudUtils.openAddFormDialog('#P5015-PLAN_PROGRESS-AddFormDialog');
            var $dialog = $(dialogId)

            function process(isFirstLoad, reportType) {

                var $form = $dialog.find('form');
                $form.form('reset');
                $form.form('disableValidation');
                $dialog.dialog('open').dialog('center').scrollTop(0);
                $dialog.find('[name="reportType"]').val(reportType);
                $dialog.find('[name="reportType"]').parent().parent().hide();
            }

            var $dialogWrapper = $dialog.parent();
            if (!$dialogWrapper.prop('rendered')) { // 只渲染一次
                MainUtils.openLoading();
                setTimeout(function() {
                    $.parser.parse($dialogWrapper);

                    // 渲染后，$dialog.parent()的DOM对象已改变，原来的包裹div已经没用了
                    $dialog.parent().prop('rendered', true);
                    process(true, reportType);
                    MainUtils.closeLoading();
                }, 0);
            } else {
                process(false, reportType);
            }
        }

        function openRportType_2_Dialog() {
            openReportTypeDialog('#_BuildAddDialog', 2);
        }

        function openRportType_3_Dialog() {
            openReportTypeDialog('#_FundAddDialog', 3);
        }

        function openRportType_4_Dialog() {
            openReportTypeDialog('#_BuildProgressAddDialog', 4);
        }

        function saveFormData(formId, dialogId) {

            // 上传文件
            $(formId).form('enableValidation');
            if (!$(formId).form('validate')) {
                return;
            }

            var options = {
                dataType: 'json',
                success: function(response, statusText, xhr, jqForm) {
                    MainUtils.closeLoading();// 关闭loading提示

                    var jsonObj = $.parseJSON($(response).text());

                    if (MainUtils.processJsonResult(jsonObj, true)) {

                        EasyDialogUtils.closeFormDialog(dialogId);
                    }
                },
                error: MainUtils.handleAjaxFormError
            };
            $(formId).ajaxSubmit(options);// jquery.form plugin
            MainUtils.openLoading();// 弹出loading提示
        }

        function exportExcelFile(datagridId) {
            var $datagrid = $(datagridId);
            var $form = $('#P5015-PLANPROGRESS-FilterForm');
            var selectedRows = $datagrid.datagrid('getSelections');
            var idArray = [];
            if (selectedRows.length > 0) {
                for (var i = 0; i < selectedRows.length; i++) {
                    idArray.push(selectedRows[i].id);
                }
            }
            var planid = idArray.toString();
            var url = BASE_URL
                    + '/schedule/progress/export-excel-file.json?isExportReport=true&planId='
                    + planid;

            MainUtils.downloadFile(url);
            /*    }
            } */
        }

         function previewInfo(datagridId) {
             var $datagrid = $(datagridId);
             var $form = $('#P5015-PLANPROGRESS-FilterForm');
             var selectedRows = $datagrid.datagrid('getSelections');
             var idArray = [];
             if (selectedRows.length > 0) {
                 for (var i = 0; i < selectedRows.length; i++) {
                     idArray.push(selectedRows[i].id);
                     console.log(selectedRows[i])
                     if (selectedRows[i].reportType != 1 && selectedRows[i].reportType != 5 ) {
                         AlertUtils.msg('warning', "只能预览内部简报");
                         return;
                     }
                 }
             }
             if (idArray.length != 1) {
                 AlertUtils.msg('warning', "选择一条记录预览");
             } else {
                 var planid = idArray.toString();
                 console.log(planid);
                 var url = BASE_URL + '/schedule/progress/'+planid+'/preview.json';

                 EasyDialogUtils.openMaxModal(url, new Date().getTime());
             }
         }
            
          /**
           * 新建/修改时的回调，用来设定计划变更关联的计划。
           */
          function beforeSubmit(options) {
              var con = $('#P5015-PLAN_PROGRESS-MainDatagrid').data('EXTRA_FILTER_OBJ');
              // 只有在con中检索'计划'时，才修改options.data(con也可以检索'项目')
              if (con && con.fieldName == 'project_') {
                  options.data.project_ = con.value;
              }
          }

        return {
            openAddFormDialog: openAddFormDialog,
            saveFormData: saveFormData,
            openRportType_2_Dialog: openRportType_2_Dialog,
            openRportType_3_Dialog: openRportType_3_Dialog,
            openRportType_4_Dialog: openRportType_4_Dialog,
            handleDblClickRow: handleDblClickRow,
            exportExcelFile: exportExcelFile,
            previewInfo: previewInfo,
            beforeSubmit: beforeSubmit
        };
    })(jQuery);

    $(document).ready(
            function() {

            	$('#P5015-PLAN_PROGRESS-MainDatagrid').datagrid({
            		onClickRow: function(rowIndex, rowData){
            			console.log(rowData);
            			if (1 == rowData.reportType || 5 == rowData.reportType) {
            				$('#P5015F03').linkbutton({text:'查看'});
            			} else {
            				$('#P5015F03').linkbutton({text:'修改'});
            			}
            		}
            	});
            	
                $('#P5015F01').menubutton({
                    menu: '#_ProgressReportAddButton'
                });

                $('#P5015F04').prop('onclick', null);
                $('#P5015F04').bind(
                        'click',
                        function(e) {

                            var selectRow = $('#P5015-PLAN_PROGRESS-MainDatagrid').datagrid(
                                    'getSelected');

                            var reportType = selectRow.reportType;

                            if (2 == reportType) {

                                CrudUtils.openViewDialog('#_BuildViewDialog',
                                        '/project-edge/schedule/progress/find.json',
                                        '#P5015-PLAN_PROGRESS-MainDatagrid');
                            } else if (3 == reportType) {
                                CrudUtils.openViewDialog('#_FundViewDialog',
                                        '/project-edge/schedule/progress/find.json',
                                        '#P5015-PLAN_PROGRESS-MainDatagrid');
                            } else if (4 == reportType) {
                                CrudUtils.openViewDialog('#_BuildProgressViewDialog',
                                        '/project-edge/schedule/progress/find.json',
                                        '#P5015-PLAN_PROGRESS-MainDatagrid');
                            }
                        });


                $('#P5015F03').prop('onclick', null);
                $('#P5015F03').bind(
                        'click',
                        function(e) {

                            var selectRow = $('#P5015-PLAN_PROGRESS-MainDatagrid').datagrid(
                                    'getSelected');

                            var reportType = selectRow.reportType;

                            var EditOpenedCallback = {};

                            EditOpenedCallback.onOpenEditFormDialog = function(json) {
                                var reportType = json.reportType;

                                var dialogId = '';
                                if (1 == reportType) {
                                	dialogId = '#reportProgressViewDialog';
                                }else if (2 == reportType) {
                                    dialogId = '#_BuildEditDialog';
                                } else if (3 == reportType) {
                                    dialogId = '#_FundEditDialog';
                                } else if (4 == reportType) {
                                    dialogId = '#_BuildProgressEditDialog';
                                }

                                var $dialog = $(dialogId);
                                $dialog.find('[name="reportType"]').val(reportType);
                                $dialog.find('[name="reportType"]').parent().parent().hide();
                            }

                            if (1 == reportType || 5 == reportType) {
                            	FlowableUtils.openReportProgressDialog('#P5015-PLAN_PROGRESS-SubmitAuditFormDialog', selectRow.id);
                            }else if (2 == reportType) {
                                CrudUtils.openEditFormDialog('#_BuildEditDialog',
                                        '/project-edge/schedule/progress/find.json',
                                        '#P5015-PLAN_PROGRESS-MainDatagrid', null,
                                        EditOpenedCallback);
                            } else if (3 == reportType) {
                                CrudUtils.openEditFormDialog('#_FundEditDialog',
                                        '/project-edge/schedule/progress/find.json',
                                        '#P5015-PLAN_PROGRESS-MainDatagrid', null,
                                        EditOpenedCallback);
                            } else if (4 == reportType) {
                                CrudUtils.openEditFormDialog('#_BuildProgressEditDialog',
                                        '/project-edge/schedule/progress/find.json',
                                        '#P5015-PLAN_PROGRESS-MainDatagrid', null,
                                        EditOpenedCallback);
                            }
                        });
            });
    //]]>

    
</script>