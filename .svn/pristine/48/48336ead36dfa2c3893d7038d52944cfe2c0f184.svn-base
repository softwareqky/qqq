<%@tag description="检索区域字段控件的组件，用于渲染各种输入控件" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="isSimpleFilter" required="true" type="java.lang.Boolean" description="是否简单检索字段，将影响控件的width和display"%>
<%@attribute name="field" required="true" type="garage.origin.domain.view.FieldBean" description="字段Bean"%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%-- 高级检索区域控件宽度 --%>
<c:set var="controlWidthPx" value="235" />
<c:set var="rangeControlWidthPx" value="109" />
<c:set var="datetimeControlWidthPx" value="235" />

<%-- 简单检索区域控件宽度 --%>
<c:if test="${isSimpleFilter}">
  <c:set var="controlWidthPx" value="140" />
  <c:set var="rangeControlWidthPx" value="55" />
  <c:set var="datetimeControlWidthPx" value="170" />
</c:if>

<c:set var="rangeDatetimeControlWidthPx" value="170" />
<c:set var="rangeDateControlWidthPx" value="109" />

<c:choose>
  <c:when test="${field.widget eq 'checkbox' }">
    <%-- <input type="checkbox" value="1" data-field-widget="checkbox" data-field-control <c:if test="${not empty field.handler}">${field.handler}</c:if> />&nbsp; --%>
    <input class="easyui-switchbutton" data-field-widget="checkbox" data-field-control
      data-options="height:32,onText:'<s:message code="ui.common.yes" />',offText:'<s:message code="ui.common.no" />',value:'1'"
    />
  </c:when>
  <c:when test="${field.widget eq 'combobox'}">
    <select name="${field.fieldName}" class="form-control ez-like" data-field-widget="combobox" data-field-control
      style="width:${controlWidthPx}px;vertical-align:middle;<c:if test="${isSimpleFilter}">display:inline-block;</c:if>" <c:if test="${not empty field.handler}">${field.handler}</c:if>
    >
      <c:if test="${not empty field.option}">
        <option value="">&nbsp;</option>
        <c:forEach var="option" items="${optionMap[field.option]}">
          <option value="${option.id}">${option.text}</option>
        </c:forEach>
      </c:if>
    </select>
  </c:when>
  <c:when test="${field.widget eq 'numberbox'}">
    <c:choose>
      <c:when test="${field.filterByRange}">
        <input type="text" class="form-control ez-like <c:if test="${not empty field.customValidType}">easyui-validatebox</c:if>" id="${idPrefix}_advSearch_${field.fieldName}_from"
          style="width:${rangeControlWidthPx}px;vertical-align:middle;ime-mode:disabled;<c:if test="${isSimpleFilter}">display:inline-block;</c:if>" data-field-widget="numberbox" data-field-control
          data-from maxlength="15" <c:if test="${not empty field.handler}">${field.handler}</c:if> data-min="${field.min}" data-max="${field.max}" data-precision="${field.precision}"
          onkeypress="MainUtils.filterNumberInputKey(this,event)" onblur="MainUtils.fixNumberboxValue(this,event)" onkeydown="MainUtils.enterNumberboxValue(this,event)"
          <c:if test="${not empty field.customValidType}">data-options="validType:['${field.customValidType}']"</c:if>
        />
        ~
        <input type="text" class="form-control ez-like easyui-validatebox"
          style="width:${rangeControlWidthPx}px;vertical-align:middle;ime-mode:disabled;<c:if test="${isSimpleFilter}">display:inline-block;</c:if>" data-field-widget="numberbox" data-field-control
          data-to maxlength="15" <c:if test="${not empty field.handler}">${field.handler}</c:if> data-min="${field.min}" data-max="${field.max}" data-precision="${field.precision}"
          onkeypress="MainUtils.filterNumberInputKey(this,event)" onblur="MainUtils.fixNumberboxValue(this,event)" onkeydown="MainUtils.enterNumberboxValue(this,event)"
          data-options="validType:{<c:if test="${not empty field.customValidType}">${field.customValidType}:[],</c:if>toGeFrom:['#${idPrefix}_advSearch_${field.fieldName}_from']}"
        />
      </c:when>
      <c:otherwise>
        <input type="text" class="form-control ez-like <c:if test="${not empty field.customValidType}">easyui-validatebox</c:if>"
          style="width:${controlWidthPx}px;vertical-align:middle;ime-mode:disabled;<c:if test="${isSimpleFilter}">display:inline-block;</c:if>" data-field-widget="numberbox" data-field-control
          maxlength="15" <c:if test="${not empty field.handler}">${field.handler}</c:if> data-min="${field.min}" data-max="${field.max}" data-precision="${field.precision}"
          onkeypress="MainUtils.filterNumberInputKey(this,event)" onblur="MainUtils.fixNumberboxValue(this,event)" onkeydown="MainUtils.enterNumberboxValue(this,event)"
          <c:if test="${not empty field.customValidType}">data-options="validType:['${field.customValidType}']"</c:if>
        />
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:when test="${field.widget eq 'textbox' and field.multiLine}">
    <textarea class="form-control ez-like easyui-validatebox" style="width:${controlWidthPx}px;height:64px;resize:none;" data-field-widget="textbox" data-field-control maxlength="${field.maxLength}"
      <c:if test="${not empty field.handler}">${field.handler}</c:if>
      data-options="validType:[<c:if test="${not empty field.customValidType}">'${field.customValidType}',</c:if>'length[0,${field.maxLength}]']"
    ></textarea>
  </c:when>
  <c:when test="${(field.widget eq 'textbox') and (not empty field.buttonText)}">
    <div class="input-group" style="width:${controlWidthPx}px;vertical-align:middle;<c:if test="${isSimpleFilter}">display:inline-block;</c:if>">
      <input type="text" class="form-control ez-like" style="width:${controlWidthPx - 40}px;" data-field-widget="textbox" readonly="readonly" />
      <input type="hidden" data-field-widget="textbox" data-field-control />
      <span class="input-group-btn" style="width: 40px">
        <button class="btn btn-default" style="height: 32px; width: 40px" type="button" ${field.handler}>${field.buttonText}</button>
      </span>
    </div>
  </c:when>
  <c:when test="${field.widget eq 'textbox'}">
    <input type="text" class="form-control ez-like <c:if test="${not empty field.customValidType}">easyui-validatebox</c:if>"
      style="width:${controlWidthPx}px;vertical-align:middle;<c:if test="${isSimpleFilter}">display:inline-block;</c:if>" data-field-widget="textbox" data-field-control maxlength="${field.maxLength}"
      <c:if test="${not empty field.handler}">${field.handler}</c:if> <c:if test="${not empty field.customValidType}">data-options="validType:['${field.customValidType}']"</c:if>
    />
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
          data-options="height:32,editable:false,width:<c:choose><c:when test="${field.widget eq 'datetimebox'}">${datetimeControlWidthPx}</c:when><c:otherwise>${controlWidthPx}</c:otherwise></c:choose>
            ,formatter:<c:choose><c:when test="${field.widget eq 'datetimebox'}">MainUtils.datetimeFormatter</c:when><c:otherwise>MainUtils.dateFormatter</c:otherwise></c:choose>
            ,parser:MainUtils.datetimeParser"
        >
        </select>
      </c:otherwise>
    </c:choose>
  </c:when>
  <c:when test="${field.widget eq 'timespinner'}">
    <select class="easyui-${field.widget}" data-field-widget="${field.widget}" data-field-control data-options="height:32,width:${controlWidthPx},editable:true,showSeconds:true">
    </select>
  </c:when>
  <c:otherwise>
    <div style="width:${controlWidthPx}px;vertical-align:middle;<c:if test="${isSimpleFilter}">display:inline-block;</c:if>">NA</div>
  </c:otherwise>
</c:choose>