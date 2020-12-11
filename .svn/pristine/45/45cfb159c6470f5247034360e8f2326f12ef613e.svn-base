<%@tag description="字段控件的组件，用于渲染各种输入控件" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<%@attribute name="idPrefix" required="true" description="画面为单位的id前缀，通常格式为'pageId-dataType'，如'P18001-ROLE'"%>
<%@attribute name="isFixedWidth" required="true" type="java.lang.Boolean" description="字段控件是否固定宽度，当不固定宽度时，宽度由controlWidth字段指定"%>
<%@attribute name="field" required="true" type="garage.origin.domain.view.FieldBean" description="字段Bean"%>
<%@attribute name="comboboxDataMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>
<%@attribute name="optionMap" type="java.util.HashMap" description="输入控件下拉框的内容来源"%>

<%@attribute name="record" type="java.util.Map" description="数据记录，如果找到字段对应的值，则填充到输入控件中"%>
<%@attribute name="isAdd" type="java.lang.Boolean" description="是否是新建的字段，否表示修改的字段"%>

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
<c:if test="${empty isAdd}">
  <c:set var="isAdd" value="false" />
</c:if>
<c:set var="validInfo" value="" />
<c:choose>
  <c:when test="${not empty field.customValidType}">

    <c:choose>
      <c:when test="${field.mandatory}">
        <c:set var="validInfo" value="required:true,validType:['${field.customValidType}']" />
      </c:when>
      <c:otherwise>
        <c:set var="validInfo" value="validType:['${field.customValidType}']" />
      </c:otherwise>
    </c:choose>

  </c:when>
  <c:otherwise>
    <c:if test="${field.mandatory}">
      <c:set var="validInfo" value="required:true" />
    </c:if>
  </c:otherwise>
</c:choose>

<c:choose>
  <c:when test="${field.widget eq 'checkbox' }">
    <%-- <input name="${field.fieldName}" type="checkbox" value="1" data-field-widget="checkbox" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
      <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
      <c:if test="${not empty field.handler}">${field.handler}</c:if>
    /> --%>
    <input name="${field.fieldName}" class="easyui-switchbutton" data-field-widget="checkbox" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
      <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
      data-options="height:32,onText:'<s:message code="ui.common.yes" />',offText:'<s:message code="ui.common.no" />',value:'1'"
    />
  </c:when>
  <c:when test="${field.widget eq 'combobox'}">
    <select name="${field.fieldName}" class="form-control ez-like <c:if test="${not empty validInfo}">easyui-validatebox</c:if>" style="width:${controlWidthPx}px;" data-field-widget="combobox"
      <c:if test="${field.copyReserve}">data-copy-reserve</c:if> <c:if test="${field.readonly}">data-edit-readonly</c:if>
      <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if> <c:if test="${not empty field.handler}">${field.handler}</c:if>
      <c:if test="${not empty validInfo}">data-options="${validInfo}"</c:if>
    >
      <c:if test="${not empty field.option}">
        <option value="">&nbsp;</option>
        <c:forEach var="option" items="${optionMap[field.option]}">
          <option value="${option.id}" <c:if test="${not empty record && not empty record[field.fieldName] && record[field.fieldName] == option.id}">selected</c:if>>${option.text}</option>
        </c:forEach>
      </c:if>
    </select>
  </c:when>
  <c:when test="${field.widget eq 'numberbox'}">
    <input name="${field.fieldName}" type="text" class="form-control ez-like <c:if test="${not empty validInfo}">easyui-validatebox</c:if>" style="width:${controlWidthPx}px;ime-mode:disabled;"
      data-field-widget="numberbox" <c:if test="${field.copyReserve}">data-copy-reserve</c:if> <c:if test="${field.readonly}">data-edit-readonly</c:if>
      <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if> maxlength="15" <c:if test="${not empty field.handler}">${field.handler}</c:if>
      <c:if test="${not empty field.validationPrompt}">placeholder="${field.validationPrompt}"</c:if> data-min="${field.min}" data-max="${field.max}" data-precision="${field.precision}"
      onkeypress="MainUtils.filterNumberInputKey(this,event)" onblur="MainUtils.fixNumberboxValue(this,event)" onkeydown="MainUtils.enterNumberboxValue(this,event)"
      <c:if test="${not empty validInfo}">data-options="${validInfo}"</c:if> <c:if test="${not empty record && not empty record[field.fieldName]}">value="${record[field.fieldName]}"</c:if>
    />
  </c:when>
  <c:when test="${field.widget eq 'textbox' and field.multiLine}">
    <textarea name="${field.fieldName}" class="form-control ez-like easyui-validatebox" style="width:${controlWidthPx}px;height:128px;resize:none;" data-field-widget="textbox"
      <c:if test="${field.copyReserve}">data-copy-reserve</c:if> <c:if test="${field.readonly}">data-edit-readonly</c:if>
      <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if> maxlength="${field.maxLength}" <c:if test="${not empty field.handler}">${field.handler}</c:if>
      <c:if test="${not empty field.validationPrompt}">placeholder="${field.validationPrompt}"</c:if>
      data-options="<c:if test="${field.mandatory}">required:true,</c:if>validType:[<c:if test="${not empty field.customValidType}">'${field.customValidType}',</c:if>'length[0,${field.maxLength}]']"
    ><c:if test="${not empty record && not empty record[field.fieldName]}">${record[field.fieldName]}</c:if></textarea>
  </c:when>
  <c:when test="${(field.widget eq 'textbox') and (not empty field.buttonText)}">
    <div class="input-group" style="width:${controlWidthPx}px;vertical-align:middle;">
      <input name="${field.fieldNameView}" type="text" class="form-control ez-like easyui-validatebox" style="width:${controlWidthPx - 40}px;" data-field-widget="textbox"
        <c:if test="${field.copyReserve}">data-copy-reserve</c:if> readonly="readonly" <c:if test="${not empty validInfo}">data-options="${validInfo}"</c:if>
        <c:if test="${not empty record && not empty record[field.fieldNameView]}">value="${record[field.fieldNameView]}"</c:if>
      />
      <input name="${field.fieldName}" type="hidden" data-field-widget="textbox" <c:if test="${field.copyReserve}">data-copy-reserve</c:if> <c:if test="${field.readonly}">data-edit-readonly</c:if>
        <c:if test="${not empty record && not empty record[field.fieldName]}">value="${record[field.fieldName]}"</c:if>
      />
      <span class="input-group-btn" style="width: 40px">
        <button class="btn btn-default" style="height: 32px; width: 40px" type="button" ${field.handler}>${field.buttonText}</button>
      </span>
    </div>
  </c:when>
  <c:when test="${field.widget eq 'textbox' || field.widget eq 'passwordbox'}">
    <c:choose>
      <c:when test="${field.widget eq 'passwordbox'}">
        <c:set var="textType" value="password" />
      </c:when>
      <c:otherwise>
        <c:set var="textType" value="text" />
      </c:otherwise>
    </c:choose>
    <input name="${field.fieldName}" type="${textType}" class="form-control ez-like <c:if test="${not empty validInfo}">easyui-validatebox</c:if>" style="width:${controlWidthPx}px;"
      data-field-widget="textbox" <c:if test="${field.copyReserve}">data-copy-reserve</c:if> <c:if test="${field.readonly}">data-edit-readonly</c:if>
      <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if> maxlength="${field.maxLength}" <c:if test="${not empty field.handler}">${field.handler}</c:if>
      <c:if test="${not empty field.validationPrompt}">placeholder="${field.validationPrompt}"</c:if> <c:if test="${not empty validInfo}">data-options="${validInfo}"</c:if>
      <c:if test="${not empty record && not empty record[field.fieldName]}">value="${record[field.fieldName]}"</c:if>
    />
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
  <c:when test="${field.widget eq 'filebox'}">
    <div class="container-fluid">
      <div class="row">
        <div class="col-xs-12 col-gapped-none">
          <input name="${field.fieldName}" type="text" class="easyui-${field.widget}" data-field-widget="${field.widget}" <c:if test="${field.copyReserve}">data-copy-reserve</c:if>
            <c:if test="${field.readonly}">data-edit-readonly</c:if> <c:if test="${field.hasDefaultValue}">data-default-value="${field.defaultValue}"</c:if>
            data-options="prompt:'${field.validationPrompt}',width:${controlWidthPx},height:32
               <c:if test="${field.mandatory}">,required:true</c:if><c:if test="${field.multiLine}">,multiple:true</c:if>
               ,buttonText:'<s:message code="ui.common.choose.file"/>'"
          />
        </div>
      </div>
    </div>
  </c:when>
  <c:when test="${field.widget eq 'component'}">
    <jsp:include page="${field.componentPath}" flush="true">
      <jsp:param value="${isAdd}" name="isAdd" />
    </jsp:include>
  </c:when>
  <c:otherwise>
    <div style="width:${controlWidthPx}px;">NA</div>
  </c:otherwise>
</c:choose>