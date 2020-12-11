<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/jsp/common/include/head.jsp" flush="true" />
</head>
<body>


  <c:set var="idPrefix" value="${idMap['page-pre']}-${idMap['data-type-pre']}" />

  <div class="easyui-layout" data-options="fit:true" style="overflow: hidden;">
    <div data-options="region:'center',border:false" style="padding: 0px 15px 0px 13px;">
      <div id="${idPrefix }-view-audit-panel" class="easyui-panel" data-options="fit:true,border:false">
        <c:choose>
          <c:when test="${not empty subGridViewFields}">
            <c:choose>
              <c:when test="${isSubGridFieldGrouped}">
                <g:viewGroupLayout viewFields="${subGridViewFields}" />
              </c:when>
              <c:otherwise>
                <g:viewSimpleLayout viewFields="${subGridViewFields}" controlCols="${subGridViewDialogControlCols}" labelCols="${12 - subGridViewDialogControlCols}" />
              </c:otherwise>
            </c:choose>
            <g:viewGroupGridLayout viewFields="${subGridViewFields}" listFields="${listFields}" gridId="${gridId}" listFrozenFields="${listFrozenFields}" defaultOrder="${defaultOrder}"
              listUrl="${listUrl}" functions="${functions}" isHandleDblClickRow="${isHandleDblClickRow}" isSingleSelect="${isSingleSelect}" dblClickRowHandler="${dblClickRowHandler}"
              selectHandler="${selectHandler}" isPage="${isPage}" viewDialogGridUrl="${idMap['view-grid-dialog-url']}" viewGridDialogId="${idMap['view-grid-dialog-id']}" correlateDataId="${flowableCorrelateDataId}"
            />
          </c:when>
          <c:otherwise>
            <c:choose>
              <c:when test="${isFieldGrouped}">
                <g:viewGroupLayout viewFields="${viewFields}" />
              </c:when>
              <c:otherwise>
                <g:viewSimpleLayout viewFields="${viewFields}" controlCols="${viewDialogControlCols}" labelCols="${12 - viewDialogControlCols}" />
              </c:otherwise>
            </c:choose>
          </c:otherwise>
        </c:choose>
        <c:choose>
          <c:when test="${not empty subGridViewFields}">
            <input type="hidden" name="penalId_ex" value="${idPrefix }-view-audit-panel" />
            <input type="hidden" name="findUrl_ex" value="${urlMap['audit-findurl']}" />
          </c:when>
          <c:otherwise>
            <input type="hidden" name="penalId_ex" value="${idPrefix }-view-audit-panel" />
            <input type="hidden" name="findUrl_ex" value="${urlMap['find']}" />
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    <div data-options="region:'south',border:true,height:'auto',height:300" style="padding: 5px 10px 5px 10px;">
      <form id="${idPrefix }-view-audit-form" method="post">
        <input type="hidden" id="idPrefix" value="${idPrefix}-view-audit-form" /> <input type="hidden" name="auditTaskId" value="${flowableAuditTaskId}" /> <input type="hidden"
          name="correlateDataId" value="${flowableCorrelateDataId}" id="correlateDataId"
        /> <input type="hidden" name="businessEntity" value="${flowableBusinessEntity}" />
        <table class="table-condensed" style="width: 100%">
          <tr>
            <td width="80"><s:message code="ui.fields.flowable.monitor.pending.audit.opinion" /></td>
            <td><table class="table-condensed">
                <tr>
                  <td><input type="radio" name="auditResult" checked="checked" value="1" /></td>
                  <td><s:message code="ui.fields.flowable.monitor.pending.audit.agree" /></td>
                  <td><input type="radio" name="auditResult" value="2" /></td>
                  <td><s:message code="ui.fields.flowable.monitor.pending.audit.reject" /></td>
                  <td><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-func-submit-audit'" onclick="FlowableUtils.saveAuditFormData('${idPrefix }-view-audit-form');"><s:message
                        code="ui.common.save"
                      /></a></td>
                </tr>
              </table>
          </tr>
          <tr>
            <td></td>
            <td><textarea name="auditRemark" class="form-control ez-like easyui-validatebox" style="width: 100%; height: 90px;" data-field-widget="easyui-textbox"></textarea></td>
          </tr>
        </table>
      </form>
      <table class="table table-condensed" id="process-table">
        <caption>流程意见</caption>
      </table>
    </div>
  </div>
  <div class="hidden" id="EasyuiHiddenWrapper"></div>
  <!-- /#EasyuiHiddenWrapper -->

  <div class="hidden" id="ContentScriptWrapper">
    <jsp:include page="/WEB-INF/jsp/common/include/javascriptSrc.jsp" flush="true" />
  </div>
  <!-- /#ContentScriptWrapper -->

  <script type="text/javascript">
            var formId = $("#idPrefix").val();
            var $form = $('#' + formId);
            var param = {};
            param['correlateDataId'] = $("#correlateDataId").val();
            var options = {
                url: BASE_URL + '/flowable/setting/audit-log-info.json',
                dataType: 'json',
                data: param,
                success: function(response, statusText, xhr, jqForm) {
                    console.log(response)
                    var data = response.dataMap.rows;
                    var html = '';

                    var $processTable = $('#process-table');

                    for (var i = 0; i < data.length; i++) {
                        var info = data[i];
                        //console.log(info)

                        if (info.auditPerson != null) {

                            var $tr = $('<tr></tr>');

                            var $tdPerson = $('<td width="10%" align="center" style="vertical-align:middle;">'
                                    + info.auditPersonText + '</td>');

                            var $tdResult = $('<td><strong>' + info.auditResultText
                                    + '</strong><br /><strong>' + info.auditRemark
                                    + '</strong><br />' + info.auditDatetimeText
                                    + '&nbsp;&nbsp;&nbsp;&nbsp;[' + info.nodeName + ']<br /></td>');
                            $tr.append($tdPerson);
                            $tr.append($tdResult);
                            /*  html += '<tr style="border-bottom:1px solid #dcdcdc;"><td width="100"><div style="margin: 12px 0">'
                                     + info.auditPersonText
                                     + '</div>'
                                     + '<div style="color: #999">'
                                     + info.nodeName
                                     + '</div>'
                                     + '</td><td><div style="margin: 12px 0">'
                                     + info.auditResultText
                                     + '。'
                                     + info.auditRemark
                                     + '</div>'
                                     + '<div style="color: #999">'
                                     + info.auditDatetimeText
                                     + '</div></td></tr>'; */

                            $processTable.append($tr);
                        }
                    }
                    //$("#process-table").html(html);
                },
                error: MainUtils.handleAjaxFormError
            };
            $.ajax(options);
        </script>

</body>
</html>