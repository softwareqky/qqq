<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%-- 共通的JavaScript脚本引用 --%>

<jsp:include page="/WEB-INF/jsp/common/include/commonJsVar.jsp" flush="true" />
<script type="text/javascript" src="<s:url value="/js/util/jquery-1.12.4.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/jquery.jqprint-0.3.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/jquery-migrate-1.2.1.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/bootstrap/bootstrap-3.4.1.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/adminlte/adminlte-2.4.15.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/jquery-sparkline/jquery.sparkline.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/jquery-slimscroll/jquery.slimscroll.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/easyui/jquery.easyui-1.9.2.min.js"/>"></script>
<s:eval var="langEasyui" expression="T(project.edge.domain.business.SystemConfigSettings).instance.langEasyui" />
<script type="text/javascript" src="<s:url value="/js/util/easyui/locale/easyui-lang-${langEasyui}.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/easyui/datagrid-bufferview.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/easyui/datagrid-scrollview.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/form/jquery.form.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/json2.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/util/echarts/echarts.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/main.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/flowable/flowable.ui.js"/>"></script>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=b5b48b067a3b18cdbaa54c9adc2d0a94"></script>
