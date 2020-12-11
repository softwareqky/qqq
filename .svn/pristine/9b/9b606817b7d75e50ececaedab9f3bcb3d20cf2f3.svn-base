<%@tag description="检索区域字段控件的组件，用于渲染各种输入控件" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="isSimpleFilter" required="true" type="java.lang.Boolean" description="是否简单检索字段，将影响控件的width"%>
<%@attribute name="field" required="true" type="garage.origin.domain.view.FieldBean" description="字段Bean"%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%-- 高级检索区域控件宽度 --%>
<c:set var="controlWidthPx" value="235" />
<c:set var="rangeControlWidthPx" value="109" />
<c:set var="datetiemControlWidthPx" value="235" />

<%-- 简单检索区域控件宽度 --%>
<c:if test="${isSimpleFilter}">
  <c:set var="controlWidthPx" value="140" />
  <c:set var="rangeControlWidthPx" value="55" />
  <c:set var="datetiemControlWidthPx" value="170" />
</c:if>

<c:set var="rangeDatetimeControlWidthPx" value="170" />
<c:set var="rangeDateControlWidthPx" value="109" />

<c:choose>
  <c:when test="${field.widget eq 'checkbox' }">
    <input type="checkbox" value="1" data-field-widget="${field.widget}" data-field-control />&nbsp;
  </c:when>
  <c:when test="${field.widget eq 'combobox'}">
    <select class="easyui-combobox" data-field-widget="${field.widget}" data-field-control
      data-options="height:32,width:${controlWidthPx},panelMinHeight:32,panelMaxHeight:320,panelHeight:null,editable:false
        <c:if test="${not empty field.valueField}">,valueField:'${field.valueField}'</c:if>
        <c:if test="${not empty field.textField}">,textField:'${field.textField}'</c:if>
        <c:if test="${not empty field.dataKey}">,data:${comboboxDataMap[field.dataKey]}</c:if>
        <c:if test="${not empty field.handler}">,${field.handler}</c:if>
        <c:if test="${not empty field.url}">,loadFilter:MainUtils.comboboxLoadFilter,valueField:'id',textField:'text',url:'<s:url value="${field.url}"/>'</c:if>"
    >
      <c:if test="${not empty field.option}">
        <option value="">&nbsp;</option>
        <c:forEach var="option" items="${optionMap[field.option]}">
          <option value="${option.id}">${option.text}</option>
        </c:forEach>
      </c:if>
    </select>
  </c:when>
  <c:when test="${(field.widget eq 'datetimebox') or (field.widget eq 'datebox')}">
    <c:choose>
      <c:when test="${field.filterByRange}">
        <select class="easyui-${field.widget}" data-field-widget="${field.widget}" data-field-control data-from id="${idPrefix}_advSearch_${field.fieldName}_from"
          data-options="height:32,editable:false,width:<c:choose><c:when test="${field.widget eq 'datetimebox'}">${rangeDatetimeControlWidthPx}</c:when><c:otherwise>${rangeDateControlWidthPx}</c:otherwise></c:choose>
            ,formatter:<c:choose><c:when test="${field.widget eq 'datetimebox'}">MainUtils.datetimeFormatter</c:when><c:otherwise>MainUtils.dateFormatter</c:otherwise></c:choose>
            ,parser:MainUtils.datetimeParser"
        >
        </select>
        ~
        <select class="easyui-${field.widget}" data-field-widget="${field.widget}" data-field-control data-to
          data-options="height:32,editable:false,width:<c:choose><c:when test="${field.widget eq 'datetimebox'}">${rangeDatetimeControlWidthPx}</c:when><c:otherwise>${rangeDateControlWidthPx}</c:otherwise></c:choose>
            ,formatter:<c:choose><c:when test="${field.widget eq 'datetimebox'}">MainUtils.datetimeFormatter</c:when><c:otherwise>MainUtils.dateFormatter</c:otherwise></c:choose>
            ,parser:MainUtils.datetimeParser,validType:{toGeFrom:['#${idPrefix}_advSearch_${field.fieldName}_from']}"
        >
        </select>
      </c:when>
      <c:otherwise>
        <select class="easyui-${field.widget}" data-field-widget="${field.widget}" data-field-control
          data-options="height:32,editable:false,width:<c:choose><c:when test="${field.widget eq 'datetimebox'}">${datetiemControlWidthPx}</c:when><c:otherwise>${controlWidthPx}</c:otherwise></c:choose>
            ,formatter:<c:choose><c:when test="${field.widget eq 'datetimebox'}">MainUtils.datetimeFormatter</c:when><c:otherwise>MainUtils.dateFormatter</c:otherwise></c:choose>
            ,parser:MainUtils.datetimeParser"
        >
        </select>
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:when test="${(field.widget eq 'timespinner')}">
    <select class="easyui-${field.widget}" data-field-widget="${field.widget}" data-field-control data-options="height:32,width:${controlWidthPx},editable:true,showSeconds:true">
    </select>
  </c:when>
  <c:otherwise>
    <c:choose>
      <c:when test="${field.filterByRange}">
        <input type="text" class="easyui-${field.widget}" data-field-widget="${field.widget}" data-field-control data-from id="${idPrefix}_advSearch_${field.fieldName}_from"
          <c:if test="${not empty field.maxLength}">maxlength="${field.maxLength}"</c:if>
          data-options="prompt:'${field.validationPrompt}',width:${rangeControlWidthPx},height:32
              <c:if test="${not empty field.min}">,min:${field.min}</c:if>
              <c:if test="${not empty field.max}">,max:${field.max}</c:if>
              <c:if test="${not empty field.precision}">,precision:${field.precision}</c:if>
              <c:if test="${not empty field.maxLength}">,validType:'length[0,${field.maxLength}]'</c:if>"
        />
        ~
        <input type="text" class="easyui-${field.widget}" data-field-widget="${field.widget}" data-field-control data-to <c:if test="${not empty field.maxLength}">maxlength="${field.maxLength}"</c:if>
          data-options="prompt:'${field.validationPrompt}',width:${rangeControlWidthPx},height:32
              <c:if test="${not empty field.min}">,min:${field.min}</c:if>
              <c:if test="${not empty field.max}">,max:${field.max}</c:if>
              <c:if test="${not empty field.precision}">,precision:${field.precision}</c:if>
              ,validType:{toGeFrom:['#${idPrefix}_advSearch_${field.fieldName}_from']
              <c:if test="${not empty field.maxLength}">,length:[0,${field.maxLength}]</c:if>}"
        />
      </c:when>
      <c:otherwise>
        <input type="text" class="easyui-${field.widget}" data-field-widget="${field.widget}" data-field-control <c:if test="${not empty field.maxLength}">maxlength="${field.maxLength}"</c:if>
          data-options="prompt:'${field.validationPrompt}',width:${controlWidthPx}
            <c:if test="${not empty field.buttonText}">,buttonText:'${field.buttonText}'<c:if test="${field.widget == 'textbox'}">,editable:false</c:if></c:if>
            <c:if test="${not empty field.handler}">,${field.handler}</c:if>
            <c:if test="${field.multiLine}">,multiline:true,height:96</c:if>
            <c:if test="${not field.multiLine}">,height:32</c:if>
            <c:if test="${not empty field.min}">,min:${field.min}</c:if>
            <c:if test="${not empty field.max}">,max:${field.max}</c:if>
            <c:if test="${not empty field.precision}">,precision:${field.precision}</c:if>
            <c:if test="${not empty field.maxLength}">,validType:[<c:if test="${not empty field.customValidType}">'${field.customValidType}',</c:if>'length[0,${field.maxLength}]']</c:if>"
        />
      </c:otherwise>
    </c:choose>
  </c:otherwise>
</c:choose>