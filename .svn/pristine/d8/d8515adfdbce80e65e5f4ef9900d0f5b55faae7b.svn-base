package project.edge.web.controller.project;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.util.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FieldValueType;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ExcelUtils;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.TreeNodeBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.common.constant.UndertakePropertyEnum;
import project.edge.domain.converter.ProjectBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectAttachment;
import project.edge.domain.entity.ProjectFavorite;
import project.edge.domain.entity.ProjectSet;
import project.edge.domain.entity.User;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.archive.ArchiveService;
import project.edge.service.auth.UserService;
import project.edge.service.hr.PersonService;
import project.edge.service.project.ProjectAttachmentService;
import project.edge.service.project.ProjectFavoriteService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.ProjectSetService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.SingleGridUploadController;

/**
 * 项目立项画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/project/project-init")
public class ProjectInitController extends SingleGridUploadController<Project, ProjectBean> {

	private static final Logger logger = LoggerFactory.getLogger(ProjectInitController.class);

	private static final String PID = "P2001";

	private static final String MODEL_KEY_PROPOSAL_FIELDS = "proposalFields";
	private static final String MODEL_KEY_FEASIBILITY_FIELDS = "feasibilityFields";
	private static final String MODEL_KEY_PRELIMINARY_DESIGN_FIELDS = "preliminaryDesignFields";

	private static final String ID_MAP_KEY_PROPOSAL_DIALOG = "edit-proposal-form-dialog";
	private static final String ID_MAP_KEY_FEASIBILITY_DIALOG = "edit-feasibility-form-dialog";
	private static final String ID_MAP_KEY_PRELIMINARY_DESIGN_DIALOG = "edit-preliminary-design-form-dialog";

	private static final String JS_MAP_KEY_EDIT_PROPOSAL_SUBMIT = "edit-proposal-submit";
	private static final String JS_MAP_KEY_EDIT_FEASIBILITY_SUBMIT = "edit-feasibility-submit";
	private static final String JS_MAP_KEY_EDIT_PRELIMINARY_DESIGN_SUBMIT = "edit-preliminary-design-submit";

	private static final String ID_MAP_KEY_IMPORT_PROJECT_DIALOG = "import-project-form-dialog";
	private static final String JS_MAP_KEY_IMPORT_PROJECT_SUBMIT = "import-project-submit";

	private static final String JS_MAP_KEY_EXPORT_PROJECT_SUBMIT = "P2001-PROJECT-FilterForm";
	@Resource
	private ProjectAttachmentService projectAttachmentService;
	@Resource
	private ArchiveService archiveService;

	@Resource
	private ProjectService projectService;

	@Resource
	private DataOptionService dataOptionService;

	@Resource
	private ProjectSetService projectSetService;

	@Resource
	private ProjectFavoriteService projectFavoriteService;

	@Resource
	private PersonService personService;
	@Resource
	private UserService userService;
	@Autowired
	HttpServletRequest request;
	@Resource
	CreateWorkFlowManager createWorkFlowManager;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.PROJECT.value();
	}

	@Override
	protected Service<Project, String> getDataService() {
		return this.projectService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<Project, ProjectBean> getViewConverter() {
		return new ProjectBeanConverter();
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/project/projectInitJs.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/project/projectInitHidden.jsp";
	}

	/**
	 * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
	 */
	@Override
	protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

		if (field.isCommonVisible()) {
			return;
		}

		// 在[t_data_fields]中有且仅有这些附件字段是设为全局不可见
		if (field.getFieldName().equals("proposalArchive_") || 
				field.getFieldName().equals("proposalArchiveRemark")) { // 建议
			this.putFiledList(model, MODEL_KEY_PROPOSAL_FIELDS, field);

		} else if (field.getFieldName().equals("preliminaryDesignArchive_") ||
				field.getFieldName().equals("preliminaryDesignArchiveRemark")) { // 初设
			this.putFiledList(model, MODEL_KEY_PRELIMINARY_DESIGN_FIELDS, field);

		} else { // 可研
			this.putFiledList(model, MODEL_KEY_FEASIBILITY_FIELDS, field);
		}
	}

	@SuppressWarnings("unchecked")
	private void putFiledList(Model model, String key, FieldBean f) {
		List<FieldBean> list = null;
		if (model.containsAttribute(key)) {
			list = (List<FieldBean>) model.asMap().get(key);
		} else {
			list = new ArrayList<>();
			model.addAttribute(key, list);
		}
		list.add(f);
	}

	/**
	 * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
	 * 
	 * @param locale
	 * @return key:[v_data_option].option_source，value:[v_data_option]
	 */
	@Override
	protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

		Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

		// 项目来源
		/*
		 * List<ComboboxOptionBean> projectSourceOptions = new ArrayList<>(); for
		 * (ProjectSourceEnum projectSourceEnum : ProjectSourceEnum.values()) {
		 * projectSourceOptions.add(new
		 * ComboboxOptionBean(projectSourceEnum.value().toString(),
		 * this.messageSource.getMessage(projectSourceEnum.resourceName(), null,
		 * locale))); } optionMap.put("ProjectSource", projectSourceOptions);
		 */

		List<ComboboxOptionBean> undertakePropertyOptions = new ArrayList<>();
		for (UndertakePropertyEnum undertakePropertyEnum : UndertakePropertyEnum.values()) {
			undertakePropertyOptions.add(new ComboboxOptionBean(undertakePropertyEnum.value().toString(),
					this.messageSource.getMessage(undertakePropertyEnum.resourceName(), null, locale)));
		}
		optionMap.put("UndertakePropertyOptions", undertakePropertyOptions);

		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.PROJECT_KIND.value()); // 项目类别
		dataTypeList.add(DataTypeEnum.PROJECT_STATUS.value()); // 目前状态
		dataTypeList.add(DataTypeEnum.PROJECT_STAGE.value()); // 目前阶段
		// dataTypeList.add(DataTypeEnum.CURRENCY.value()); // 币种
		// dataTypeList.add(DataTypeEnum.PROJECT_TYPE.value()); // 项目类型
		// dataTypeList.add(DataTypeEnum.PROJECT_QUALITY_GRADE.value()); // 质量等级
		// dataTypeList.add(DataTypeEnum.PROJECT_CATEGORY.value()); // 项目属性
		// dataTypeList.add(DataTypeEnum.PROJECT_FINAL_CLIENT.value()); // 最终客户
		// dataTypeList.add(DataTypeEnum.PROJECT_IMPLEMENT_DEPT.value()); // 实施项目部
		// dataTypeList.add(DataTypeEnum.PROJECT_SIGN_SUBJECT.value()); // 签约主体
		// dataTypeList.add(DataTypeEnum.REGION.value()); // 区域
		// dataTypeList.add(DataTypeEnum.PROJECT_PROFESSION_TYPE.value()); // 专业类型
		// dataTypeList.add(DataTypeEnum.PROJECT_QUALIFICATION_REQ.value()); // 资质要求
		// dataTypeList.add(DataTypeEnum.PROJECT_CONTRACT_METHOD.value()); // 承包方式
		// dataTypeList.add(DataTypeEnum.PROJECT_PAYMENT_METHOD.value()); // 付款方式
		// dataTypeList.add(DataTypeEnum.PROJECT_BIDDING_METHOD.value()); // 招标形式
		// dataTypeList.add(DataTypeEnum.PROJECT_SETTLEMENT_METHOD.value()); // 结算方式
		// dataTypeList.add(DataTypeEnum.PROJECT_BUDGET_METHOD.value()); // 预算方式
		// dataTypeList.add(DataTypeEnum.PROJECT_IMPORTANCE.value()); // 重要程度
		// dataTypeList.add(DataTypeEnum.PROJECT_CLASS.value()); // 工程类型

		CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);

		// 项目类别
		List<ComboboxOptionBean> projectKindOptions = new ArrayList<>();
		// 目前状态
		List<ComboboxOptionBean> projectStatusOptions = new ArrayList<>();
		// 目前阶段
		List<ComboboxOptionBean> projectStageOptions = new ArrayList<>();
		// // 币种
		// List<ComboboxOptionBean> currencyOptions = new ArrayList<>();
		// // 项目类型
		// List<ComboboxOptionBean> projectTypeOptions = new ArrayList<>();
		// // 质量等级
		// List<ComboboxOptionBean> projectQualityGradeOptions = new ArrayList<>();
		// // 项目属性
		// List<ComboboxOptionBean> projectCategoryOptions = new ArrayList<>();
		// // 最终客户
		// List<ComboboxOptionBean> projectFinalClientOptions = new ArrayList<>();
		// // 实施项目部
		// List<ComboboxOptionBean> projectImplementDeptOptions = new ArrayList<>();
		// // 签约主体
		// List<ComboboxOptionBean> projectSignSubjectOptions = new ArrayList<>();
		// // 区域
		// List<ComboboxOptionBean> regionOptions = new ArrayList<>();
		// // 专业类型
		// List<ComboboxOptionBean> projectProfessionTypeOptions = new ArrayList<>();
		// // 资质要求
		// List<ComboboxOptionBean> projectQualificationReqOptions = new ArrayList<>();
		// // 承包方式
		// List<ComboboxOptionBean> projectContractMethodOptions = new ArrayList<>();
		// // 付款方式
		// List<ComboboxOptionBean> projectPaymentMethodOptions = new ArrayList<>();
		// // 招标形式
		// List<ComboboxOptionBean> projectBiddingMethodOptions = new ArrayList<>();
		// // 结算方式
		// List<ComboboxOptionBean> projectSettlementMethodOptions = new ArrayList<>();
		// // 预算方式
		// List<ComboboxOptionBean> projectBudgetMethodOptions = new ArrayList<>();
		// // 重要程度
		// List<ComboboxOptionBean> projectImportanceOptions = new ArrayList<>();
		// // 工程类型
		// List<ComboboxOptionBean> projectClassOptions = new ArrayList<>();

		List<DataOption> list = this.dataOptionService.list(f, null);

		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.PROJECT_KIND.value())) {
				// 屏蔽复合类项目
				if (!o.getOptionName().equals("复合类项目")) {
					projectKindOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
				}

			} else if (o.getDataType().equals(DataTypeEnum.PROJECT_STATUS.value())) {
				projectStatusOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			} else if (o.getDataType().equals(DataTypeEnum.PROJECT_STAGE.value())) {
				projectStageOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			}
			// } else if (o.getDataType().equals(DataTypeEnum.CURRENCY.value())) {
			// currencyOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if (o.getDataType().equals(DataTypeEnum.PROJECT_TYPE.value())) {
			// projectTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_QUALITY_GRADE.value())) {
			// projectQualityGradeOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if (o.getDataType().equals(DataTypeEnum.PROJECT_CATEGORY.value())) {
			// projectCategoryOptions.add(new ComboboxOptionBean(o.getId(),
			// o.getOptionName()));
			// } else if (o.getDataType().equals(DataTypeEnum.PROJECT_FINAL_CLIENT.value()))
			// {
			// projectFinalClientOptions.add(new ComboboxOptionBean(o.getId(),
			// o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_IMPLEMENT_DEPT.value())) {
			// projectImplementDeptOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if (o.getDataType().equals(DataTypeEnum.PROJECT_SIGN_SUBJECT.value()))
			// {
			// projectSignSubjectOptions.add(new ComboboxOptionBean(o.getId(),
			// o.getOptionName()));
			// } else if (o.getDataType().equals(DataTypeEnum.REGION.value())) {
			// regionOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_PROFESSION_TYPE.value())) {
			// projectProfessionTypeOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_QUALIFICATION_REQ.value())) {
			// projectQualificationReqOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_CONTRACT_METHOD.value())) {
			// projectContractMethodOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_PAYMENT_METHOD.value())) {
			// projectPaymentMethodOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_BIDDING_METHOD.value())) {
			// projectBiddingMethodOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_SETTLEMENT_METHOD.value())) {
			// projectSettlementMethodOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if
			// (o.getDataType().equals(DataTypeEnum.PROJECT_BUDGET_METHOD.value())) {
			// projectBudgetMethodOptions
			// .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			// } else if (o.getDataType().equals(DataTypeEnum.PROJECT_IMPORTANCE.value())) {
			// projectImportanceOptions.add(new ComboboxOptionBean(o.getId(),
			// o.getOptionName()));
			// } else if (o.getDataType().equals(DataTypeEnum.PROJECT_CLASS.value())) {
			// projectClassOptions.add(new ComboboxOptionBean(o.getId(),
			// o.getOptionName()));
			// }
		}

		optionMap.put("ProjectKindOptions", projectKindOptions);
		optionMap.put("ProjectStatusOptions", projectStatusOptions);
		optionMap.put("ProjectStageOptions", projectStageOptions);
		// optionMap.put("CurrencyOptions", currencyOptions);
		// optionMap.put("ProjectTypeOptions", projectTypeOptions);
		// optionMap.put("ProjectQualityGradeOptions", projectQualityGradeOptions);
		// optionMap.put("ProjectCategoryOptions", projectCategoryOptions);
		// optionMap.put("ProjectFinalClientOptions", projectFinalClientOptions);
		// optionMap.put("ProjectImplementDeptOptions", projectImplementDeptOptions);
		// optionMap.put("ProjectSignSubjectOptions", projectSignSubjectOptions);
		// optionMap.put("RegionOptions", regionOptions);
		// optionMap.put("ProjectProfessionTypeOptions", projectProfessionTypeOptions);
		// optionMap.put("ProjectQualificationReqOptions",
		// projectQualificationReqOptions);
		// optionMap.put("ProjectContractMethodOptions", projectContractMethodOptions);
		// optionMap.put("ProjectPaymentMethodOptions", projectPaymentMethodOptions);
		// optionMap.put("ProjectBiddingMethodOptions", projectBiddingMethodOptions);
		// optionMap.put("ProjectSettlementMethodOptions",
		// projectSettlementMethodOptions);
		// optionMap.put("ProjectBudgetMethodOptions", projectBudgetMethodOptions);
		// optionMap.put("ProjectImportanceOptions", projectImportanceOptions);
		// optionMap.put("ProjectClassOptions", projectClassOptions);

		return optionMap;
	}

	@Override
	protected Map<String, String> prepareIdMap() {

		Map<String, String> idMap = super.prepareIdMap();

		idMap.put(ID_MAP_KEY_PROPOSAL_DIALOG,
				String.format("%1$s-%2$s-EditFormDialog-Proposal", this.getPageId(), this.getDataType()));
		idMap.put(ID_MAP_KEY_FEASIBILITY_DIALOG,
				String.format("%1$s-%2$s-EditFormDialog-Feasibility", this.getPageId(), this.getDataType()));
		idMap.put(ID_MAP_KEY_PRELIMINARY_DESIGN_DIALOG,
				String.format("%1$s-%2$s-EditFormDialog-PreliminaryDesign", this.getPageId(), this.getDataType()));
		return idMap;
	}

	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {
		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

		// 打开新建和修改画面，以及连续新建保存时，增加回调，刷新项目集下拉框的内容

		// 打开新建
		jsMap.put(ControllerJsMapKeys.OPEN_ADD, String.format("CrudUtils.openAddFormDialog('#%1$s', PROJECT_INIT);",
				idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

		// 新建保存，isFile强制设为false
		jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true);",
						idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						false));

		// 连续新建，isFile强制设为false
		jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, PROJECT_INIT);",
						idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						false));

		// 打开修改
		jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
				String.format("CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, PROJECT_INIT);",
						idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG), urlMap.get(ControllerUrlMapKeys.FIND),
						idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 修改保存-建议
		jsMap.put(JS_MAP_KEY_EDIT_PROPOSAL_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
						idMap.get(ID_MAP_KEY_PROPOSAL_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						this.useFile()));

		// 修改保存-可研
		jsMap.put(JS_MAP_KEY_EDIT_FEASIBILITY_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
						idMap.get(ID_MAP_KEY_FEASIBILITY_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						this.useFile()));

		// 修改保存-初设
		jsMap.put(JS_MAP_KEY_EDIT_PRELIMINARY_DESIGN_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
						idMap.get(ID_MAP_KEY_PRELIMINARY_DESIGN_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						this.useFile()));

		// 上传文件
		jsMap.put(ControllerJsMapKeys.OPEN_UPLOAD, String.format("PROJECT_INIT.openUploadFormDialog('#%1$s', '%2$s');",
				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), urlMap.get(ControllerUrlMapKeys.FIND)));

		// 我的关注
		jsMap.put(ControllerJsMapKeys.CONCERN, String.format("PROJECT_INIT.batchConcernSelected('%1$s', '#%2$s');",
				urlMap.get(ControllerUrlMapKeys.CONCERN), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
		// 取消关注
		jsMap.put(ControllerJsMapKeys.NO_CONCERN, String.format("PROJECT_INIT.batchNoConcernSelected('%1$s', '#%2$s');",
				urlMap.get(ControllerUrlMapKeys.NO_CONCERN), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 导入文件
		jsMap.put(ControllerJsMapKeys.OPEN_IMPORT, String.format("PROJECT_INIT.openImportFormDialog('#%1$s');",
				idMap.get(JS_MAP_KEY_IMPORT_PROJECT_SUBMIT)));

		// 导出文件
		jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
				String.format("PROJECT_INIT.emportData('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		return jsMap;
	}

	/**
	 * 画面Open的入口方法，用于生成JSP。
	 * 
	 * @param paramMap 画面请求中的任何参数，都会成为检索的字段
	 * @param model    model
	 * @param userBean session中的当前登录的用户信息
	 * @param locale   locale
	 * @return
	 */
	@RequestMapping("/main")
	public String main(@RequestParam Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// Datagrid行双击事件，增加回调，用来刷新项目集下拉框的内容
		model.addAttribute(ControllerModelKeys.DBL_CLICK_ROW_HANDLER, "PROJECT_INIT.handleDblClickRow");

		// Datagrid行选择的事件，除了默认的逻辑，还需要控制文件上传按钮，当选中一条记录，且记录的项目状态符合要求，才启用该按钮
		model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "PROJECT_INIT.handleSelect");
        model.addAttribute(ControllerModelKeys.CLICK_CELL_HANDLER, "PROJECT_INIT.clickCellHandler");

		return super.main(paramMap, model, userBean, locale);
	}

	/**
	 * 获取一览显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson JSON字符串形式的过滤条件
	 * @param page             页数
	 * @param rows             每页的记录数
	 * @param sort             排序的字段，CSV
	 * @param order            顺序，CSV
	 * @param locale
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		return super.list(commonFilterJson, null, page, rows, sort, order, locale);
	}

	/**
	 * 获取画面左侧项目列表的一览显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson JSON字符串形式的过滤条件
	 * @param page             页数
	 * @param rows             每页的记录数
	 * @param sort             排序的字段，CSV
	 * @param order            顺序，CSV
	 * @param locale
	 * @return
	 * @deprecated
	 */
	@RequestMapping("/list-side")
	@ResponseBody
	public JsonResultBean listSide(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 暂时不处理流程相关的状态
			// CommonFilter filter = new CommonFilter().addExact("isDeleted",
			// OnOffEnum.OFF.value())
			// .addExact("flowStatusProject", FlowStatusEnum.REVIEW_PASSED.value());
			CommonFilter filter = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());

			// 转换查询中的立项年份
			CommonFilter reqFilter = this.generateCommonFilter(null, commonFilterJson);
			if (reqFilter != null) {
				for (FilterFieldInfoDto field : reqFilter.getFilterFieldList()) {
					if (field.getFieldName().equals("projectYear")) {

						Object v = field.getValue();
						if (StringUtils.isEmpty(v)) {
							continue;
						}
						String fromText = String.format("%1$s-01-01 00:00:00", v.toString());
						String toText = String.format("%1$s-12-31 23:59:59", v.toString());
						filter.addRange("projectStartDate", FieldValueType.DATE,
								DateUtils.string2Date(fromText, Constants.SIMPLE_DATE_TIME_FORMAT),
								DateUtils.string2Date(toText, Constants.SIMPLE_DATE_TIME_FORMAT));
					} else {
						filter.getFilterFieldList().add(field);
					}
				}
			}
			jsonResult = super.list(null, filter, page, rows, sort, order, locale);

		} catch (Exception e) {
			this.getLogger().error("Exception listing the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 获取画面左侧项目树显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson JSON字符串形式的过滤条件
	 * @param locale
	 * @return
	 */
	@RequestMapping("/tree-side")
	@ResponseBody
	public JsonResultBean getTreeSide(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 暂时不处理流程相关的状态
			// CommonFilter filter = new CommonFilter().addExact("isDeleted",
			// OnOffEnum.OFF.value())
			// .addExact("flowStatusProject", FlowStatusEnum.REVIEW_PASSED.value());
			CommonFilter filter = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());

			// 转换查询中的立项年份
			CommonFilter reqFilter = this.generateCommonFilter(null, commonFilterJson);
			if (reqFilter != null) {
				for (FilterFieldInfoDto field : reqFilter.getFilterFieldList()) {
					if (field.getFieldName().equals("projectYear")) {

						Object v = field.getValue();
						if (StringUtils.isEmpty(v)) {
							continue;
						}
						String fromText = String.format("%1$s-01-01 00:00:00", v.toString());
						String toText = String.format("%1$s-12-31 23:59:59", v.toString());
						filter.addRange("projectStartDate", FieldValueType.DATE,
								DateUtils.string2Date(fromText, Constants.SIMPLE_DATE_TIME_FORMAT),
								DateUtils.string2Date(toText, Constants.SIMPLE_DATE_TIME_FORMAT));
					} else {
						filter.getFilterFieldList().add(field);
					}
				}
			}

			List<OrderByDto> orderList = Collections.singletonList(new OrderByDto("projectName"));
			List<Project> list = this.projectService.list(filter, orderList);
			List<ProjectSet> setList = this.projectSetService.list(null, orderList);

			// 借助map来转换并构建树形结构
			Map<String, TreeNodeBean> map = new HashMap<>(); // key: id
			List<TreeNodeBean> resultList = new ArrayList<>();

			TreeNodeBean root = new TreeNodeBean();
			root.setId(Constants.ALL);
			root.setText(this.messageSource.getMessage("ui.common.all", null, locale));
			resultList.add(root);

			for (ProjectSet s : setList) {
				TreeNodeBean n = new TreeNodeBean();
				n.setId(s.getId());
				n.setText(s.getProjectName());

				n.setIconCls("tree-icon-fa fa fa-fw fa-sliders");

				Map<String, Object> attr = new HashMap<>();
				attr.put("isProjectSet", true);
				n.setAttributes(attr);

				map.put(n.getId(), n);
				root.getChildren().add(n);
			}

			for (Project p : list) {
				TreeNodeBean n = new TreeNodeBean();
				n.setId(p.getId());
				n.setText(p.getProjectName());

				n.setIconCls("tree-icon-fa fa fa-fw fa-calendar-check-o");

				Map<String, Object> attr = new HashMap<>();
				attr.put("isProjectSet", false);
				attr.put("fieldName", "project_"); // 检索信息中的字段名
				n.setAttributes(attr);

				if (p.getProjectSet() != null) {
					n.setPid(p.getProjectSet().getId());
					map.get(n.getPid()).getChildren().add(n);
				} else {
					root.getChildren().add(n);
				}
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);

		} catch (Exception e) {
			this.getLogger().error("Exception side-listing the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 查找单条记录，返回Json格式。
	 * 
	 * @param id     ID
	 * @param locale
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	@Override
	public JsonResultBean find(@RequestParam String id, Locale locale) {

		JsonResultBean jsonResult = super.find(id, locale);
		try {

			// 把项目集准备好，以便前端更新项目集下拉框的内容

			List<ProjectSet> list = this.projectSetService.list(null, null);
			List<ComboboxOptionBean> optionList = new ArrayList<>();
			String format = "(%1$s) %2$s";

			for (ProjectSet ps : list) {
				optionList.add(new ComboboxOptionBean(ps.getId(),
						String.format(format, ps.getProjectNum(), ps.getProjectName())));
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, optionList);

		} catch (Exception e) {
			this.getLogger().error("Exception finding the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/project-num")
	@ResponseBody
	public JsonResultBean makeProjectNum(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			String template = this.systemConfigService.getStringConfig(SystemConfigKeys.PROJECT_NUM_TEMPLATE);
			int elStart = template.indexOf("${");
			int elEnd = template.lastIndexOf("}");
			String el = template.substring(elStart + 2, elEnd);
			String v = DateUtils.date2String(new Date(), el);

			// String projectNum = template.substring(0, elStart) + v +
			// template.substring(elEnd +
			// 1);
			CommonFilter f = new CommonFilter().addAnywhere("projectNum", v);
			List<Project> projects = this.projectService.list(f, null);
			String retstr = "";
			if (projects != null) {
				retstr = String.format("%06d", (projects.size() + 1));
			} else {
				retstr = "000001";
			}

			String projectNum = template.substring(0, elStart) + v + retstr;

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, projectNum);

		} catch (Exception e) {
			getLogger().error("Exception making project number.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 新建，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	@SysLogAnnotation(description = "项目立项", action = "新增")
	public JsonResultBean create(@ModelAttribute ProjectBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		if (!StringUtils.isEmpty(bean.getProjectSet_())) {
			CommonFilter f = new CommonFilter().addExact("projectSet.id", bean.getProjectSet_());
			List<Project> projects = this.projectService.list(f, null);

			String retstr = "";
			if (projects != null) {
				retstr = String.format("%03d", (projects.size() + 1));
			} else {
				retstr = "001";
			}

			ProjectSet p = this.projectSetService.find(bean.getProjectSet_());

			String projectNum = p.getProjectNum() + retstr;

			bean.setProjectNum(projectNum);
		}
		
		if (StringUtils.isEmpty(bean.getProjectNum())) {
			JsonResultBean jsonBean = makeProjectNum(userBean, locale);
			bean.setProjectNum(jsonBean.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT).toString());
		}

		String useid = userBean.getSessionUserId();
		bean.setApplicant_(useid);

		Person p = this.personService.find(useid);
		if (p != null) {
			bean.setApplicantName(p.getPersonName());
			bean.setApplicantText(p.getPersonName());
			bean.setApplicantMobile(p.getMobile());
		}
		return super.create(bean, null, userBean, locale);
	}

	/**
	 * 修改，返回Json格式。普通修改画面、项目建议文件上传画面、项目可研文件上传画面以及项目初设文件画面共用此方法。
	 * 
	 * @param request
	 * @param response
	 * @param bean       表现层对象
	 * @param uploadType 用来区分是普通修改画面、项目建议文件上传画面、项目可研文件上传画面还是项目初设文件画面提交的。普通修改画面的提交URL中不带此参数，其他都在URL后加上了此参数。
	 * @param userBean   Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/edit")
	@SysLogAnnotation(description = "项目立项", action = "更新")
	public void update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ProjectBean bean,
			@RequestParam(required = false, defaultValue = "") String uploadType,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
		// 参数在projectInitHidden.jsp中用GET的方式设定
		bean.setUploadFileType(uploadType);

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archive_", bean.getArchiveList());
		fieldNameArchiveListMap.put("proposalArchive_", bean.getProposalArchiveList());
		fieldNameArchiveListMap.put("feasibilityArchive_", bean.getFeasibilityArchiveList());
		fieldNameArchiveListMap.put("eiaArchive_", bean.getEiaArchiveList());
		fieldNameArchiveListMap.put("energyAssessmentArchive_", bean.getEnergyAssessmentArchiveList());
		fieldNameArchiveListMap.put("landArchive_", bean.getLandArchiveList());
		fieldNameArchiveListMap.put("preliminaryDesignArchive_", bean.getPreliminaryDesignArchiveList());
		
		Project entity = projectService.find(bean.getId());
		bean.setProjectNum(entity.getProjectNum());

		// 项目的文件，位于/project/id文件夹内，id是项目的主键值
		super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
	}

	@Override
	protected void postUpdate(Project entity, Project oldEntity, ProjectBean bean,
			java.util.Map<String, Object> paramMap) throws IOException {
		super.postUpdate(entity, oldEntity, bean, paramMap);

		List<Archive> list = new ArrayList<>();
		for (ProjectAttachment attachment : entity.getAttachmentsToDelete()) {
			list.add(attachment.getArchive());
		}
		this.deleteArchiveFiles(list);
	}

	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SysLogAnnotation(description = "项目立项", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// ★ Project的文件夹对应的Archive的id，就是Project的id
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(KEY_ARCHIVE_IDS_TO_DELETE, idsToDelete);
		paramMap.put(KEY_NEED_DELETE_SELF_RECORD, true);

		return super.delete(idsToDelete, paramMap, userBean, locale);
	}

	/**
	 * 关注，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/concern")
	@ResponseBody
	public JsonResultBean Concern(@RequestParam String idsToConcern,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToConcern);
		List<String> idList = Arrays.asList(idArray);

		try {

			if ((idList != null) && (idList.size() > 0)) {
				CommonFilter filter = new CommonFilter().addExact("user.id", userBean.getSessionUserId())
						.addWithin("project.id", idList);
				List<ProjectFavorite> projectFavorites = projectFavoriteService.list(filter, null);
				if ((projectFavorites != null) && (projectFavorites.size() >= 0)) {
					List<String> delIds = new ArrayList<>();
					for (ProjectFavorite p : projectFavorites) {
						delIds.add(p.getId());
					}

					if (delIds.size() > 0) {
						projectFavoriteService.delete(delIds);
					}
				}

				List<ProjectFavorite> entities = new ArrayList<>();
				for (String i : idList) {

					ProjectFavorite p = new ProjectFavorite();
					User u = new User();
					u.setId(userBean.getSessionUserId());
					p.setUser(u);

					Project project = new Project();
					project.setId(i);
					p.setProject(project);

					entities.add(p);
				}

				if (entities.size() > 0) {
					projectFavoriteService.create(entities);
				}
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.concern.ok", null, locale));

		} catch (Exception e) {
			this.getLogger().error("Exception Concern the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 关注，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/no-concern")
	@ResponseBody
	public JsonResultBean NoConcern(@RequestParam String idsToNoConcern,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToNoConcern);
		List<String> idList = Arrays.asList(idArray);

		try {

			if ((idList != null) && (idList.size() > 0)) {
				CommonFilter filter = new CommonFilter().addExact("user.id", userBean.getSessionUserId())
						.addWithin("project.id", idList);
				List<ProjectFavorite> projectFavorites = projectFavoriteService.list(filter, null);
				if ((projectFavorites != null) && (projectFavorites.size() >= 0)) {
					List<String> delIds = new ArrayList<>();
					for (ProjectFavorite p : projectFavorites) {
						delIds.add(p.getId());
					}

					if (delIds.size() > 0) {
						projectFavoriteService.delete(delIds);
					}

				}
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.no.concern.ok", null, locale));

		} catch (Exception e) {
			this.getLogger().error("Exception Concern the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	@RequestMapping("/import-excel-file")
	public void ImportExcelFile(HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		// 当浏览器是IE时，如果返回application/json，IE会提示用户保存upload.json文件
		response.setContentType("text/html");

		try {

			if (request instanceof MultipartHttpServletRequest) {

				MultipartFile importFile = ((MultipartHttpServletRequest) request).getFile("importFile");

				String path = this.context.getRealPath("/");

				// mtstar\webapp\webapps\app
				File root = new File(path);
				String randomID = UUID.randomUUID().toString();

				File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
						"temp" + File.separator + randomID);
				FileUtils.forceMkdir(uploadPath);
				String uploadFilePath = uploadPath.getAbsolutePath() + File.separator + randomID;
				String templateExtension = FilenameUtils.getExtension(importFile.getOriginalFilename());
				String templateFilePath = uploadFilePath + Constants.DOT + templateExtension;
				if (templateFilePath != null && !importFile.isEmpty()) {
					File tempFile = new File(templateFilePath);

					FileUtils.writeByteArrayToFile(new File(templateFilePath), importFile.getBytes());

					// 解析excel文件，xls/xlsx需要分开处理
					List<ProjectBean> beans = this.parseExcelFileToProjectBeans(tempFile);

					if (!beans.isEmpty()) {
						Date now = new Date();
						ProjectBeanConverter converter = new ProjectBeanConverter();
						List<Project> projects = new ArrayList<>();
						for (ProjectBean bean : beans) {
							Project p = converter.toImportEntity(bean, null, userBean, now);
							projects.add(p);

						}

						this.projectService.create(projects);
					}
					// 准备JSON结果
					jsonResult.setStatus(JsonStatus.OK);
					jsonResult.setMessage(this.messageSource.getMessage("message.info.import.ok", null, locale));
					jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beans);
				}
			}
		} catch (Exception e) {
			logger.error("Exception while import excel file.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		try {
			String result = new ObjectMapper().writeValueAsString(jsonResult);
			response.getWriter().write("<textarea>" + result + "</textarea>");
		} catch (Exception e) {
			logger.error("Exception while converting object to json string.", e);
		}
	}

	private List<ProjectBean> parseExcelFileToProjectBeans(File tempFile) throws FileNotFoundException, IOException {

		List<ProjectBean> beans = new ArrayList<>();

		String extension = FilenameUtils.getExtension(tempFile.getName());

		Workbook wb = null;
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(tempFile);

			if ("xlsx".equals(extension)) {
				wb = new XSSFWorkbook(fileInputStream);
			} else if ("xls".equals(extension)) {
				wb = new HSSFWorkbook(fileInputStream);
			}

			if (wb != null) {

				Sheet sheet = wb.getSheetAt(0);

				for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
					// 跳过第一二三行

					if (i < 2) {
						continue;
					}

					Row row = sheet.getRow(i);
					ProjectBean bean = new ProjectBean();

					if (row == null || StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(0)))
							|| StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(1)))
							|| StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(2)))) {
						continue;
					}

					// TODO

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(0)))) {

						CommonFilter projectFilter = new CommonFilter();
						projectFilter.addExact("projectNum", ExcelUtils.getCellValue(row.getCell(0)).trim());

						List<Project> projects = this.projectService.list(projectFilter, null);

						if ((projects != null) && (projects.size() > 0))
							continue;

						bean.setProjectNum(ExcelUtils.getCellValue(row.getCell(0)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(1)))) {
						bean.setProjectName(ExcelUtils.getCellValue(row.getCell(1)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(2)))) {
						bean.setProjectKind_(ExcelUtils.getCellValue(row.getCell(2)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(3)))) {
						bean.setGovernmentProjectNum(ExcelUtils.getCellValue(row.getCell(3)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(4)))) {

						Person person = this.personService.find(ExcelUtils.getCellValue(row.getCell(4)));

						if (person != null) {
							bean.setApplicant_(person.getId());
							bean.setApplicantName(person.getPersonName());
						}

					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(5)))) {
						bean.setApplicantMobile(ExcelUtils.getCellValue(row.getCell(5)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(6)))) {
						CommonFilter projectSetFilter = new CommonFilter();
						projectSetFilter.addExact("projectNum", ExcelUtils.getCellValue(row.getCell(6)).trim());

						List<ProjectSet> projectSets = this.projectSetService.list(projectSetFilter, null);

						if (!projectSets.isEmpty()) {
							bean.setProjectSet_(projectSets.get(0).getId());
						}
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(7)))) {
						bean.setProjectStatus_(ExcelUtils.getCellValue(row.getCell(7)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(8)))) {
						bean.setMainLeader_(ExcelUtils.getCellValue(row.getCell(8)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(9)))) {
						bean.setMainLeaderMobile(ExcelUtils.getCellValue(row.getCell(9)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(10)))) {
						bean.setProjectStartDate(ExcelUtils.getCellValue(row.getCell(10)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(11)))) {
						bean.setProjectEndDate(ExcelUtils.getCellValue(row.getCell(11)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(12)))) {
						bean.setProjectSource(ExcelUtils.getCellValue(row.getCell(12)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(13)))) {
						bean.setProjectDuration(ExcelUtils.getCellValue(row.getCell(13)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(14)))) {
						bean.setProjectCost(Double.valueOf(ExcelUtils.getCellValue(row.getCell(14))));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(15)))) {
						bean.setProjectAddress(ExcelUtils.getCellValue(row.getCell(15)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(16)))) {
						bean.setProjectDesc(ExcelUtils.getCellValue(row.getCell(16)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(17)))) {
						bean.setRemark(ExcelUtils.getCellValue(row.getCell(17)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(18)))) {
						bean.setOtherReq(ExcelUtils.getCellValue(row.getCell(18)));
					}

					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(19)))) {
						bean.setPjmReq(ExcelUtils.getCellValue(row.getCell(19)));
					}

					beans.add(bean);
				}
			}
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}

			if (wb != null) {
				wb.close();
			}
		}

		return beans;
	}

	@RequestMapping("/export-excel-file")
	public void ExportExcelFile(@RequestParam String projectNum, @RequestParam String projectName,
			@RequestParam String governmentProjectNum, HttpServletResponse response, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {
			File downloadFile = null;

			downloadFile = this.CreateExcelFile(projectNum, projectName, governmentProjectNum, locale);

			String fileName = downloadFile.getName();

			String mimeType = URLConnection.guessContentTypeFromName(fileName);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);

			// 解决中文文件名显示问题
			fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

			// response.setContentLength(downloadFile..getLabelFileContent().length);

			InputStream inputStream = new BufferedInputStream(new FileInputStream(downloadFile));

			// Copy bytes from source to destination, closes both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());

			inputStream.close();
			response.getOutputStream().close();

			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while export project init xls file", e);
			jsonResult.setStatus(JsonStatus.ERROR);
		}

	}

	private File CreateExcelFile(String projectNum, String projectName, String governmentProjectNum, Locale locale)
			throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {

			CommonFilter filter = new CommonFilter();
			if (!StringUtils.isEmpty(projectNum))
				filter.addExact("projectNum", projectNum);
			if (!StringUtils.isEmpty(projectName))
				filter.addExact("projectName", projectName);
			if (!StringUtils.isEmpty(governmentProjectNum))
				filter.addExact("governmentProjectNum", governmentProjectNum);

			List<Project> projects = this.projectService.list(filter, null);

			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet(this.messageSource.getMessage("ui.menu.item.project.init", null, locale));
			Row header = sheet.createRow(0);
			header.setHeight((short) 1000);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 19));

			sheet.setColumnWidth(0, 15 * 256);
			sheet.setColumnWidth(1, 20 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 20 * 256);
			sheet.setColumnWidth(4, 20 * 256);
			sheet.setColumnWidth(5, 20 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 20 * 256);
			sheet.setColumnWidth(8, 10 * 256);
			sheet.setColumnWidth(9, 30 * 256);
			sheet.setColumnWidth(10, 15 * 256);
			sheet.setColumnWidth(11, 15 * 256);
			sheet.setColumnWidth(12, 15 * 256);
			sheet.setColumnWidth(13, 15 * 256);
			sheet.setColumnWidth(14, 15 * 256);
			sheet.setColumnWidth(15, 15 * 256);
			sheet.setColumnWidth(16, 15 * 256);
			sheet.setColumnWidth(17, 15 * 256);
			sheet.setColumnWidth(18, 15 * 256);
			sheet.setColumnWidth(19, 15 * 256);

			Cell headCell = header.createCell(0);
			headCell.setCellValue(this.messageSource.getMessage("ui.menu.item.project.init", null, locale));

			CellStyle cellStyle = wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			Font font = wb.createFont();
			font.setBold(true); // 加粗
			font.setFontHeightInPoints((short) 15); // 设置标题字体大小
			cellStyle.setFont(font);

			headCell.setCellStyle(cellStyle);

			Row properties = sheet.createRow(1);
			properties.setHeight((short) 800);

			properties.createCell(0).setCellValue("内部编号");
			properties.createCell(1).setCellValue("项目名称");
			properties.createCell(2).setCellValue("项目类别");
			properties.createCell(3).setCellValue("政府项目编号");
			properties.createCell(4).setCellValue("立项申请人");
			properties.createCell(5).setCellValue("立项人联系方式");
			properties.createCell(6).setCellValue("归属项目集");
			properties.createCell(7).setCellValue("目前状态");
			properties.createCell(8).setCellValue("主要负责人");
			properties.createCell(9).setCellValue("主要负责人联系方式");
			properties.createCell(10).setCellValue("计划开始日期");
			properties.createCell(11).setCellValue("计划结束日期");
			properties.createCell(12).setCellValue("项目来源");
			properties.createCell(13).setCellValue("工程工期");
			properties.createCell(14).setCellValue("工程造价(项目造价)");
			properties.createCell(15).setCellValue("项目地点");
			properties.createCell(16).setCellValue("项目描述(项目内容)");
			properties.createCell(17).setCellValue("备注(项目背景)");
			properties.createCell(18).setCellValue("项目要求（项目目标）");
			properties.createCell(19).setCellValue("项目经理要求（考核指标）");

			for (int i = 0; i < projects.size(); i++) {

				Project p = projects.get(i);
				Row row = sheet.createRow(i + 2);
				row.setHeight((short) 500);

				row.createCell(0).setCellValue(p.getProjectNum());
				row.createCell(1).setCellValue(p.getProjectName());
				if (p.getProjectKind() != null) {
					row.createCell(2).setCellValue(p.getProjectKind().getOptionName());
				}

				row.createCell(3).setCellValue(p.getGovernmentProjectNum());

				if (p.getApplicant() != null) {
					row.createCell(4).setCellValue(p.getApplicant().getPersonName());

				}

				row.createCell(5).setCellValue(p.getApplicantMobile());

				if (p.getProjectSet() != null) {
					row.createCell(6).setCellValue(p.getProjectSet().getProjectName());
				}

				if (p.getProjectStatus() != null) {
					row.createCell(7).setCellValue(p.getProjectStatus().getOptionName());
				}

				if (p.getMainLeader() != null) {
					row.createCell(8).setCellValue(p.getMainLeader().getPersonName());
				}

				row.createCell(9).setCellValue(p.getMainLeaderMobile());

				if (p.getProjectStartDate() != null) {
					row.createCell(10)
							.setCellValue(DateUtils.date2String(p.getProjectStartDate(), Constants.DATE_FORMAT));
				}

				if (p.getProjectEndDate() != null) {
					row.createCell(11)
							.setCellValue(DateUtils.date2String(p.getProjectEndDate(), Constants.DATE_FORMAT));
				}

				row.createCell(12).setCellValue(p.getProjectSource());
				row.createCell(13).setCellValue(p.getProjectDuration());
				if (p.getProjectCost() != null) {
					row.createCell(14).setCellValue(p.getProjectCost());
				}

				row.createCell(15).setCellValue(p.getProjectAddress());
				row.createCell(16).setCellValue(p.getProjectDesc());
				row.createCell(17).setCellValue(p.getRemark());
				row.createCell(18).setCellValue(p.getOtherReq());
				row.createCell(19).setCellValue(p.getPjmReq());
			}

			Date now = new Date();

			String fileName = this.messageSource.getMessage("ui.menu.item.project.init", null, locale)
					+ DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + this.messageSource.getMessage("ui.menu.item.project.init", null, locale)
							+ File.separator + randomID);

			FileUtils.forceMkdir(uploadPath);

			file = new File(uploadPath.getAbsoluteFile(), fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			output = new FileOutputStream(file);
			wb.write(output);
			output.flush();
		} finally {

			if (output != null) {
				output.close();
			}
		}

		return file;
	}

	/**
	 * 管理平台提交审核
	 * 
	 * @param id     提交审核记录ID
	 * @param locale
	 * @return
	 * @throws IOException
	 *
	 */
	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "项目立项", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		Project entity = this.getDataService().find(id);
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		String dataId = entity.getId();
		String creatorId = userBean.getSessionUserId();
		String userName = userBean.getSessionLoginName();
		int oaType = OAAuditApiType.OA_TYPE_PROJECTINIT.value();

		// 向OA提交审核请求
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("projectNum", entity.getProjectNum());
		reqMap.put("projectName", entity.getProjectName());
		reqMap.put("projectKind", entity.getProjectKind().getOptionName());
		reqMap.put("governmentProjectNum", entity.getGovernmentProjectNum());
		if (entity.getApplicant() != null) {
			reqMap.put("applicant", entity.getApplicant().getUser().getId());
		} else {
			reqMap.put("applicant", "");
		}
		reqMap.put("applicantMobile", entity.getApplicantMobile());
		reqMap.put("projectStatus", entity.getProjectStatus().getOptionName());
		CommonFilter filter = new CommonFilter();
		filter.addExact("project.id", entity.getId());
		String projectStatusId = entity.getProjectStatus().getId();
		// 项目目前状态 0:建议, 1:可研, 2_0:初设, 2_1:方案, 3:概算, 4_0:建设, 4_1:实施, 5:阶段性验收, 6:初验, 7:终验,
		// 8:运维
		// 附件类型： 0-项目附件，1-项目建议书，2-可研文档，3-环评材料，4-能评材料，5-土地材料，6-初设文件
		if ("PROJECT_STATUS_0".equals(projectStatusId)) {
			// 建议
			filter.addExact("attachmentType", 1);
		} else if ("PROJECT_STATUS_1".equals(projectStatusId)) {
			// 可研
			//filter.addExact("attachmentType", 2);
			filter.addRange("attachmentType", FieldValueType.INT, 2, 5);
		} else if ("PROJECT_STATUS_2_0".equals(projectStatusId) || "PROJECT_STATUS_2_1".equals(projectStatusId)) {
			// 初设
			filter.addExact("attachmentType", 6);
			oaType = OAAuditApiType.OA_TYPE_PROJECTAUDIT.value();
			
			// 增加新修改参数
			reqMap.put("projectSource", entity.getProjectSource());
			reqMap.put("specialCategory", entity.getSpecialCategory());
			reqMap.put("mainLeader", entity.getMainLeader().getPersonName());
			reqMap.put("mainLeaderMobile", entity.getMainLeaderMobile());
			reqMap.put("projectStartDate", DateUtils.date2String(entity.getProjectStartDate(), Constants.DATE_FORMAT));
			reqMap.put("projectEndDate", DateUtils.date2String(entity.getProjectEndDate(), Constants.DATE_FORMAT));
			reqMap.put("governmentProjectNum", entity.getGovernmentProjectNum());
			reqMap.put("projectDuration", entity.getProjectDuration());
			reqMap.put("projectCost", String.valueOf(entity.getProjectCost()));
			reqMap.put("projectAddress", entity.getProjectAddress());
			reqMap.put("competentDepartment", entity.getCompetentDepartment());
			reqMap.put("undertakeProperty", messageSource.getMessage(
	                UndertakePropertyEnum.getResouceName(entity.getUndertakeProperty()), null, locale));
			reqMap.put("undertakeUnit", entity.getUndertakeUnit());
			reqMap.put("participateUnit", entity.getParticipateUnit());
			reqMap.put("projectRemark", entity.getRemark());
			reqMap.put("otherReq", entity.getOtherReq());
			reqMap.put("projectDesc", entity.getProjectDesc());
			reqMap.put("pjmReq", entity.getPjmReq());
		}
		List<ProjectAttachment> attchments = projectAttachmentService.list(filter, null);
		String fileName = "";
		String filePath = "";
		String fileType = "";
		String archiveRemark = "";
		List<HashMap<String, String>> archives = new ArrayList<>();
		if (attchments.size() > 0) {
			for (ProjectAttachment attchment : attchments) {
				fileName = attchment.getArchive().getArchiveName();
				String rootFolderPath = this.systemConfigService
						.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

				filePath = rootFolderPath + attchment.getArchive().getRelativePath();
				fileType = attchment.getAttachmentType() + "";
				File refereeFile = new File(filePath);
				byte[] refereeFileOriginalBytes = FileUtils.readFileToByteArray(refereeFile);
				byte[] refereeFileBase64Bytes = Base64.encodeBase64(refereeFileOriginalBytes);
				String UpFile = new String(refereeFileBase64Bytes, "UTF-8");
				HashMap<String, String> attMap = new HashMap<String, String>();
				attMap.put("attachmentFileName", fileName);
				attMap.put("attachmentFile", UpFile);
				// 0-项目附件，1-项目建议书，2-可研文档，3-环评材料，4-能评材料，5-土地材料，6-初设文件
				attMap.put("attchmentType", fileType);
				if (!StringUtils.isEmpty(attchment.getAttachmentRemark())) {
					archiveRemark += attchment.getAttachmentRemark();
				}

				archives.add(attMap);
			}
		}

		reqMap.put("archiveRemark", archiveRemark);
		reqMap.put("archives", archives);

		logger.info("[OA Audit] No.1 ProjectInit");
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, entity.getId(), reqMap, request);
		// 如果请求审批成功更新表t_project 审批中
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.1 ProjectInit Audit request success, data id was {}", dataId);
			Project proEntity = projectService.find(dataId);
			proEntity.setFlowStatusProject(FlowStatusEnum.REVIEWING.value());
			proEntity.setmDatetime(new Date());
			proEntity.setModifier(personService.find(creatorId));
			projectService.setData(proEntity);
		}

		return jsonResult;
	}
}
