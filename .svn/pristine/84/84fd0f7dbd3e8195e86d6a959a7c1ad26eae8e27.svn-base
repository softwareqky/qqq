<%@tag description="字段控件的组件，用于渲染各种输入控件" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="isFixedWidth" required="true" type="java.lang.Boolean" description="字段控件是否固定宽度，当不固定宽度时，宽度由controlWidth字段指定"%>
<%@attribute name="field" required="true" type="garage.origin.domain.view.FieldBean" description="字段Bean"%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%@attribute name="record" type="java.util.Map" description="数据记录，如果找到字段对应的值，则填充到输入控件中"%>

<%-- 字段输入 --%>

<c:set var="controlWidthPx" value="235" />
<c:if test="${not isFixedWidth}">
  <c:choose>
    <c:when test="${field.controlWidth == 3}">
      <c:set var="controlWidthPx" value="170" />
    </c:when>
    <c:when test="${field.controlWidth == 4}">
      <c:set var="controlWidthPx" value="235" />
    </c:when>
    <c:when test="${field.controlWidth == 10}">
      <c:set var="controlWidthPx" value="635" />
    </c:when>
    <c:otherwise>
      <c:set var="controlWidthPx" value="235" />
    </c:otherwise>
  </c:choose>
</c:if>

<c:choose>
  <c:when test="${field.widget eq 'checkbox' }">
    <input name="${field.fieldName}" type="checkbox" value="1" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
      <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
    />
  </c:when>
  <c:when test="${field.widget eq 'combobox'}">
    <select class="easyui-combobox" name="${field.fieldName}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
      <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
      data-options="height:32,width:${controlWidthPx},panelMinHeight:32,panelMaxHeight:320,panelHeight:null,editable:false
               <c:if test="${not empty record && not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
               <c:if test="${field.mandatory}">,required:true</c:if>
               <c:if test="${field.multiLine}">,multiple:true</c:if>
               <c:if test="${not empty field.valueField}">,valueField:'${field.valueField}'</c:if>
               <c:if test="${not empty field.textField}">,textField:'${field.textField}'</c:if>
               <c:if test="${not empty field.dataKey}">,data:${comboboxDataMap[field.dataKey]}</c:if>
               <c:if test="${not empty field.handler}">,${field.handler}</c:if>
               <c:if test="${not empty field.url}">,loadFilter:MainUtils.comboboxLoadFilter,valueField:'id',textField:'text',url:'<s:url value="${field.url}"/>'</c:if>"
    >
      <c:if test="${not empty field.option}">
        <c:if test="${not field.mandatory}">
          <option value="">&nbsp;</option>
        </c:if>
        <c:forEach var="option" items="${optionMap[field.option]}">
          <option value="${option.id}">${option.text}</option>
        </c:forEach>
      </c:if>
    </select>
  </c:when>
  <c:when test="${field.widget eq 'datetimebox'}">
    <select class="easyui-datetimebox" name="${field.fieldName}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
      <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
      data-options="height:32,width:${controlWidthPx},editable:false,
               formatter:MainUtils.datetimeFormatter,parser:MainUtils.datetimeParser
               <c:if test="${not empty record && not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
               <c:if test="${field.mandatory}">,required:true</c:if>"
    >
    </select>
  </c:when>
  <c:when test="${field.widget eq 'datebox'}">
    <select class="easyui-datebox" name="${field.fieldName}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
      <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
      data-options="height:32,width:${controlWidthPx},editable:false,
               formatter:MainUtils.dateFormatter,parser:MainUtils.datetimeParser
               <c:if test="${not empty record && not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
               <c:if test="${field.mandatory}">,required:true</c:if>
               <c:if test="${not empty field.customValidType}">,validType:'${field.customValidType}'</c:if> "
    >
    </select>
  </c:when>
  <c:when test="${field.widget eq 'timespinner'}">
    <select class="easyui-timespinner" name="${field.fieldName}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
      <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
      data-options="height:32,width:${controlWidthPx},editable:true,showSeconds:true
               <c:if test="${not empty record && not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
               <c:if test="${field.mandatory}">,required:true</c:if>
               <c:if test="${not empty field.customValidType}">,validType:'${field.customValidType}'</c:if> "
    >
    </select>
  </c:when>
  <c:otherwise>
    <c:choose>
      <c:when test="${field.widget eq 'passwordbox'}">
        <c:set var="textType" value="password" />
      </c:when>
      <c:otherwise>
        <c:set var="textType" value="text" />
      </c:otherwise>
    </c:choose>
    <input name="${field.fieldName}" type="${textType}" class="easyui-${field.widget}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
      <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
      <c:if test="${not empty field.maxLength}">maxlength="${field.maxLength}"</c:if>
      data-options="prompt:'${field.validationPrompt}',width:${controlWidthPx}
               <c:if test="${not empty field.buttonText}">,buttonText:'${field.buttonText}'<c:if test="${field.widget == 'textbox'}">,editable:false</c:if></c:if>
               <c:if test="${not empty field.handler}">,${field.handler}</c:if>
               <c:if test="${field.multiLine}">,multiline:true,height:64</c:if>
               <c:if test="${not field.multiLine}">,height:32</c:if>
               <c:if test="${not empty field.min}">,min:${field.min}</c:if>
               <c:if test="${not empty field.max}">,max:${field.max}</c:if>
               <c:if test="${not empty field.precision}">,precision:${field.precision}</c:if>
               <c:if test="${not empty record && not empty record[field.fieldName]}">,value:'${record[field.fieldName]}'</c:if>
               <c:if test="${field.mandatory}">,required:true</c:if>
               <c:if test="${not empty field.maxLength}">,validType:[<c:if test="${not empty field.customValidType}">'${field.customValidType}',</c:if>'length[0,${field.maxLength}]']</c:if>
               <c:if test="${field.widget eq 'filebox'}">,buttonText:'<s:message code="ui.common.choose.file"/>'</c:if>"
    />
  </c:otherwise>
</c:choose>