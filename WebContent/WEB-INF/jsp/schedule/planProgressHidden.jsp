<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="g" tagdir="/WEB-INF/tags"%>

<div class="hidden">

	<div id="_ProgressReportAddButton">
		<div data-options="" onclick="PlanProgressUtils.openRportType_2_Dialog();">建设简报</div>
		<div data-options="" onclick="PlanProgressUtils.openRportType_3_Dialog();">资金使用简报</div>
		<div data-options="" onclick="PlanProgressUtils.openRportType_4_Dialog();">建设进展简报</div>
	</div>

	<div class="hidden">

		<c:choose>
			<c:when test="${isFieldGrouped}">
				<c:set var="dialogMaxHeight" value="560" />
				<c:set var="dialogWidth" value="950" />
			</c:when>
			<c:otherwise>
				<c:set var="dialogMaxHeight" value="480" />
				<c:set var="dialogWidth" value="520" />
			</c:otherwise>
		</c:choose>

		<g:addDialog dialogId="_BuildAddDialog"
			continuousAddSubmitJs="CrudUtils.submitAddEditFormData('#_BuildAddDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true, true, true,PlanProgressUtils);"
			addSubmitJs="CrudUtils.submitAddEditFormData('#_BuildAddDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true, true,null,PlanProgressUtils);"
			width="420" maxHeight="480" hasCopyReserve="true" dialogTitle="建设简报"
			dialogIconClass="dialog-icon-fa fa fa-fw fa-upload">
			<g:addEditFormSimpleLayout formFields="${addBuildFields}" submitUrl="${urlMap['add']}"
				idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
		</g:addDialog>

		<g:addDialog dialogId="_FundAddDialog"
			continuousAddSubmitJs="CrudUtils.submitAddEditFormData('#_FundAddDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true, true, true,PlanProgressUtils);"
			addSubmitJs="CrudUtils.submitAddEditFormData('#_FundAddDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true, true,null,PlanProgressUtils);"
			width="420" maxHeight="480" hasCopyReserve="true" dialogTitle="资金使用简报"
			dialogIconClass="dialog-icon-fa fa fa-fw fa-upload">
			<g:addEditFormSimpleLayout formFields="${addFundFields}" submitUrl="${urlMap['add']}"
				idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
		</g:addDialog>

		<g:addDialog dialogId="_BuildProgressAddDialog"
			continuousAddSubmitJs="CrudUtils.submitAddEditFormData('#_BuildProgressAddDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true, true, true,PlanProgressUtils);"
			addSubmitJs="CrudUtils.submitAddEditFormData('#_BuildProgressAddDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true, true,null,PlanProgressUtils);"
			width="420" maxHeight="480" hasCopyReserve="true" dialogTitle="建设进展简报"
			dialogIconClass="dialog-icon-fa fa fa-fw fa-upload">
			<g:addEditFormSimpleLayout formFields="${addBuildProgressFields}" submitUrl="${urlMap['add']}"
				idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
		</g:addDialog>

	</div>

	<div class="hidden">
		<g:editDialog dialogId="_BuildEditDialog"
			editSubmitJs="CrudUtils.submitAddEditFormData('#_BuildEditDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true);"
			width="420" maxHeight="480" dialogIconClass="dialog-icon-fa fa fa-fw fa-pencil-square-o">
			<g:addEditFormSimpleLayout formFields="${editBuildFields}" submitUrl="${urlMap['edit']}"
				idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
		</g:editDialog>

		<g:editDialog dialogId="_FundEditDialog"
			editSubmitJs="CrudUtils.submitAddEditFormData('#_FundEditDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true);"
			width="420" maxHeight="480" dialogIconClass="dialog-icon-fa fa fa-fw fa-pencil-square-o">
			<g:addEditFormSimpleLayout formFields="${editFundFields}" submitUrl="${urlMap['edit']}"
				idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
		</g:editDialog>

		<g:editDialog dialogId="_BuildProgressEditDialog"
			editSubmitJs="CrudUtils.submitAddEditFormData('#_BuildProgressEditDialog', '#P5015-PLAN_PROGRESS-MainDatagrid', true);"
			width="420" maxHeight="480" dialogIconClass="dialog-icon-fa fa fa-fw fa-pencil-square-o">
			<g:addEditFormSimpleLayout formFields="${editBuildProgressFields}" submitUrl="${urlMap['edit']}"
				idPrefix="${idPrefix}" labelCols="3" controlCols="9" />
		</g:editDialog>
	</div>

	<div class="hidden">


		<g:viewDialog dialogId="_BuildViewDialog" width="${dialogWidth}" maxHeight="${dialogMaxHeight}">
			<c:choose>
				<c:when test="${isFieldGrouped}">
					<g:viewGroupLayout viewFields="${viewBuildFields}" />
				</c:when>
				<c:otherwise>
					<g:viewSimpleLayout viewFields="${viewBuildFields}" />
				</c:otherwise>
			</c:choose>
		</g:viewDialog>

		<g:viewDialog dialogId="_FundViewDialog" width="${dialogWidth}" maxHeight="${dialogMaxHeight}">
			<c:choose>
				<c:when test="${isFieldGrouped}">
					<g:viewGroupLayout viewFields="${viewFundFields}" />
				</c:when>
				<c:otherwise>
					<g:viewSimpleLayout viewFields="${viewFundFields}" />
				</c:otherwise>
			</c:choose>
		</g:viewDialog>

		<g:viewDialog dialogId="_BuildProgressViewDialog" width="${dialogWidth}"
			maxHeight="${dialogMaxHeight}">
			<c:choose>
				<c:when test="${isFieldGrouped}">
					<g:viewGroupLayout viewFields="${viewBuildProgressFields}" />
				</c:when>
				<c:otherwise>
					<g:viewSimpleLayout viewFields="${viewBuildProgressFields}" />
				</c:otherwise>
			</c:choose>
		</g:viewDialog>

	</div>

</div>