<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<div class="hidden">


  <div class="hidden">


    <c:if test="${not empty mergeFields}">
      <g:addDialog continuousAddSubmitJs="${jsMap['continuous-add-submit']}" dialogId="_P5001-PLAN-MergeScheduleFormDialog" dialogTitle="合并计划" width="${addDialogWidth}"
        addSubmitJs="CrudUtils.submitAddEditFormData('#_P5001-PLAN-MergeScheduleFormDialog', '#P5001-PLAN-MainDatagrid', false, true, false, PlanUtils)" maxHeight="${addDialogMaxHeight}"
        hasCopyReserve="false" isShadow="false"
      >
        <c:choose>
          <c:when test="${isFieldGrouped}">
            <g:addEditFormGroupLayout formFields="${mergeFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['add']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}" />
          </c:when>
          <c:otherwise>
            <g:addEditFormSimpleLayout formFields="${mergeFields}" idPrefix="${idPrefix}" submitUrl="${urlMap['add']}" comboboxDataMap="${comboboxDataMap}" optionMap="${optionMap}"
              controlCols="${addDialogControlCols}" labelCols="${12 - addDialogControlCols}"
            />
          </c:otherwise>
        </c:choose>
      </g:addDialog>
    </c:if>

  </div>
</div>