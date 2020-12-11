<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">
    //<![CDATA[
    var URL_SESSION_EXPIRE = '<s:url value="/session-expire.htm"/>';
    var BASE_URL = '${pageContext.request.contextPath}';
<%-- without forward slash in the end--%>
    //]]>
</script>
<script type="text/javascript">
    //<![CDATA[
<%-- 共通的提示信息放在此处，如var LABLE_INFO等等 --%>
    var LABLE_INFO = '<s:message code="ui.common.info" />';
    var LABLE_WARNING = '<s:message code="ui.common.warn" />';
    var LABLE_ERROR = '<s:message code="ui.common.error" />';
    var LABLE_CONFIRM = '<s:message code="ui.common.ok" />';
    var LABLE_YES = '<s:message code="ui.common.yes" />';
    var LABLE_NO = '<s:message code="ui.common.no" />';
    var MSG_LOADING = '<s:message code="message.info.loading" />';
    var MSG_SERVER_UPDATING = '<s:message code="message.info.updating" />';
    var MSG_REMOTE_SERVER_ERROR = '<s:message code="message.error.remote.server" />';
    var MSG_ERROR_AJAX = '<s:message code="message.error.ajax" />';
    var MSG_CONFIRM_DELETE = '<s:message code="message.confirm.delete" />';
    var MSG_CONFIRM_CONCERN = '<s:message code="message.confirm.concern" />';
    var MSG_CONFIRM_NO_CONCERN = '<s:message code="message.confirm.no.concern" />';
    var MSG_ERROR_FROM_GT_TO = '<s:message code="message.error.from.gt.to" />';
    var MSG_ERROR_INVALID_EMAIL = '<s:message code="message.error.invalid.email" />';
    var MSG_ERROR_INVALID_FILE_NAME = '<s:message code="message.error.invalid.file.name" />';
    var MSG_ERROR_INVALID_PHONE = '<s:message code="message.error.invalid.phone" />';
    var MSG_ERROR_INVALID_IP = '<s:message code="message.error.invalid.ip" />';
    var DATEBOX_CLEAR_BUTTON = '<s:message code="ui.datebox.clear.button" />';
    var MSG_WARN_NOT_SELECT = '<s:message code="message.warn.not.select" />';
    var MSG_WARN_MORE_THAN_ONE_SELECT = '<s:message code="message.warn.more.than.one.select" />';
    var MSG_CONFIRM_WITHDRAW = '<s:message code="message.confirm.withdraw" />';
    //]]>

    
</script>