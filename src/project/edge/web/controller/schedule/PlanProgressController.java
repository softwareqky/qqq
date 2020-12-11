package project.edge.web.controller.schedule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FileEncodingTypeEnum;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ExcelConverterUtils;
import garage.origin.common.util.ZipUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.FolderPath;
import project.edge.common.constant.InvestorEnum;
import project.edge.common.constant.ProgressFlowStatusEnum;
import project.edge.common.constant.ReportTypeEnum;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.PlanProgressBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanProgress;
import project.edge.domain.entity.PlanProgressAttachment;
import project.edge.domain.entity.PlanProgressTask;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PlanProgressBean;
import project.edge.domain.view.PlanProgressTaskBean;
import project.edge.service.facility.SegmentService;
import project.edge.service.facility.SiteService;
import project.edge.service.hr.PersonService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanProgressService;
import project.edge.service.schedule.PlanProgressTaskService;
import project.edge.service.schedule.PlanService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 进度上报画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/progress")
public class PlanProgressController extends TreeGridUploadController<PlanProgress, PlanProgressBean> {

	private static final Logger logger = LoggerFactory.getLogger(PlanProgressController.class);

	private static final String MAP_KEY_ADD_BUILD_FIELDS = "addBuildFields";
	private static final String MAP_KEY_ADD_FUND_FIELDS = "addFundFields";
	private static final String MAP_KEY_ADD_BUILD_PROGRESS_FIELDS = "addBuildProgressFields";

	private static final String MAP_KEY_EDIT_BUILD_FIELDS = "editBuildFields";
	private static final String MAP_KEY_EDIT_FUND_FIELDS = "editFundFields";
	private static final String MAP_KEY_EDIT_BUILD_PROGRESS_FIELDS = "editBuildProgressFields";

	private static final String MAP_KEY_VIEW_BUILD_FIELDS = "viewBuildFields";
	private static final String MAP_KEY_VIEW_FUND_FIELDS = "viewFundFields";
	private static final String MAP_KEY_VIEW_BUILD_PROGRESS_FIELDS = "viewBuildProgressFields";

	private static final String MODEL_KEY_DBL_CLICK_ROW_HANDLER = "dblClickRowHandler";

	private static final String JS_MAP_KEY_EXPORT_PLANPROGRESS_SUBMIT = "P5015-PLANPROGRESS-FilterForm";
	private static final String PID = "P5015";

	private static final String MODLE_KEY_HTML_URL = "htmlUrl";

	@Resource
	private PlanProgressService planProgressService;

	@Resource
	private PlanProgressTaskService planProgressTaskService;

	@Resource
	private PlanService planService;

	@Resource
	private ProjectService projectService;

	@Resource
	private PersonService personService;

	@Resource
	private ProjectPersonService projectPersonService;
	
	@Resource
	private VirtualOrgService virtualOrgService;
	
	@Resource
	private SiteService siteService;
	
	@Resource
	private SegmentService segmentService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.PLAN_PROGRESS.value();
	}

	@Override
	protected Service<PlanProgress, String> getDataService() {
		return this.planProgressService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<PlanProgress, PlanProgressBean> getViewConverter() {
		return new PlanProgressBeanConverter();
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/schedule/planProgressJs.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/schedule/planProgressHidden.jsp";
	}

	/**
	 * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
	 * 
	 * @param locale
	 * @return key:[v_data_option].option_source，value:[v_data_option]
	 */
	@Override
	protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
		// TODO Auto-generated method stub

		Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<String, List<ComboboxOptionBean>>();

		// 简报类型
		List<ComboboxOptionBean> reportType = new ArrayList<>();
		for (ReportTypeEnum s : ReportTypeEnum.values()) {
			reportType.add(new ComboboxOptionBean(s.value().toString(),
					this.messageSource.getMessage(s.resourceName(), null, locale)));
		}
		optionMap.put("ReportType", reportType);

		// 流程状态
		List<ComboboxOptionBean> flowStatus = new ArrayList<>();
		for (FlowStatusEnum s : FlowStatusEnum.values()) {
			flowStatus.add(new ComboboxOptionBean(s.value().toString(),
					this.messageSource.getMessage(s.resourceName(), null, locale)));
		}
		optionMap.put("FlowStatus", flowStatus);

		// 投资方
		List<ComboboxOptionBean> investors = new ArrayList<>();
		for (InvestorEnum s : InvestorEnum.values()) {
			investors.add(new ComboboxOptionBean(s.value().toString(),
					this.messageSource.getMessage(s.resourceName(), null, locale)));
		}
		optionMap.put("Investor", investors);
		
		// 计划年份
		String[] planYearArr = new String[] { "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027",
				"2028", "2029", "2030" };
		List<ComboboxOptionBean> planYearOptions = new ArrayList<>();
		for (String s : planYearArr) {
			planYearOptions.add(new ComboboxOptionBean(s, s));
		}
		optionMap.put("planYearOptions", planYearOptions);

		// 计划类型
		String[] planTypeArr = new String[] { "年度建设工作计划", "站点计划", "中继段计划" };
		List<ComboboxOptionBean> planTypeOptions = new ArrayList<>();
		for (int i = 0; i < planTypeArr.length; i++) {
			planTypeOptions.add(new ComboboxOptionBean(i + "", planTypeArr[i]));
		}
		optionMap.put("planTypeOptions", planTypeOptions);
		
		// 项目组
		CommonFilter filter = new CommonFilter();
		filter.addExact("isDeleted", OnOffEnum.OFF.value());
		filter.addExact("isEchartsShow", OnOffEnum.ON.value().intValue());
		List<VirtualOrg> virtualOrgs = this.virtualOrgService.list(filter, null);
		List<ComboboxOptionBean> groupOptions = new ArrayList<>();
		for (int i = 0; i < virtualOrgs.size(); i++) {
			groupOptions
					.add(new ComboboxOptionBean(virtualOrgs.get(i).getId(), virtualOrgs.get(i).getVirtualOrgName()));
		}
		optionMap.put("groupOptions", groupOptions);
		
		// 负责人
		List<Site> sites = this.siteService.list(null, null);
		List<Segment> segments = this.segmentService.list(null, null);
		CommonFilter pFilter = new CommonFilter();
		filter.addExact("isDeleted", OnOffEnum.OFF.value());
		pFilter.addNull("site.id");
		pFilter.addNull("segment.id");
		List<Plan> plans = this.planService.list(pFilter, null);
		
		List<ComboboxOptionBean> personOptions = new ArrayList<>();
		List<String> personIds = new ArrayList<>();
		for (int i = 0; i < sites.size(); i++) {
			if (sites.get(i).getPersonInCharge() != null
					&& !personIds.contains(sites.get(i).getPersonInCharge().getId())) {
				personIds.add(sites.get(i).getPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getPersonInCharge().getId(),
						sites.get(i).getPersonInCharge().getPersonName()));
			}
			if (sites.get(i).getProgrammablePersonInCharge() != null
					&& !personIds.contains(sites.get(i).getProgrammablePersonInCharge().getId())) {
				personIds.add(sites.get(i).getProgrammablePersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getProgrammablePersonInCharge().getId(),
						sites.get(i).getProgrammablePersonInCharge().getPersonName()));
			}
			if (sites.get(i).getSdnPersonInCharge() != null
					&& !personIds.contains(sites.get(i).getSdnPersonInCharge().getId())) {
				personIds.add(sites.get(i).getSdnPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(sites.get(i).getSdnPersonInCharge().getId(),
						sites.get(i).getSdnPersonInCharge().getPersonName()));
			}
		}
		for (int i = 0; i < segments.size(); i++) {
			if (segments.get(i).getPersonInCharge() != null
					&& !personIds.contains(segments.get(i).getPersonInCharge().getId())) {
				personIds.add(segments.get(i).getPersonInCharge().getId());
				personOptions.add(new ComboboxOptionBean(segments.get(i).getPersonInCharge().getId(),
						segments.get(i).getPersonInCharge().getPersonName()));
			}
		}
		for (int i = 0; i < plans.size(); i++) {
			if (plans.get(i).getCreator() != null && !personIds.contains(plans.get(i).getCreator().getId())) {
				personIds.add(plans.get(i).getCreator().getId());
				personOptions.add(new ComboboxOptionBean(plans.get(i).getCreator().getId(),
						plans.get(i).getCreator().getPersonName()));
			}
		}
		optionMap.put("personOptions", personOptions);
		return optionMap;
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

		String viewName = super.main(paramMap, model, userBean, locale);

		// 1. 将默认的新增删除用的字段移除，重新添加秤字段Map，这样就不会生成默认的新建/修改弹出框
		Map<String, Object> modelMap = model.asMap();

		ArrayList<FieldBean> addEditFields = (ArrayList<FieldBean>) modelMap.get(ControllerModelKeys.ADD_FIELDS);

		List<FieldBean> addBuildFields = new ArrayList<>();
		List<FieldBean> addFundFields = new ArrayList<>();
		List<FieldBean> addBuildProgressFields = new ArrayList<>();

		for (FieldBean bean : addEditFields) {
			if (bean.getFieldGroup() != null) {
				if (bean.getFieldGroup().contains("简报基本信息")) {
					addBuildFields.add(bean);
					addFundFields.add(bean);
					addBuildProgressFields.add(bean);
				}
				if (bean.getFieldGroup().contains("建设简报")) {
					addBuildFields.add(bean);
				}
				if (bean.getFieldGroup().contains("资金使用简报")) {
					addFundFields.add(bean);
				}
				if (bean.getFieldGroup().contains("建设进展简报")) {
					addBuildProgressFields.add(bean);
				}
			}
		}

		ArrayList<FieldBean> editFields = (ArrayList<FieldBean>) modelMap.get(ControllerModelKeys.EDIT_FIELDS);

		List<FieldBean> editBuildFields = new ArrayList<>();
		List<FieldBean> editFundFields = new ArrayList<>();
		List<FieldBean> editBuildProgressFields = new ArrayList<>();

		for (FieldBean bean : editFields) {
			if (bean.getFieldGroup() != null) {
				if (bean.getFieldGroup().contains("简报基本信息")) {
					editBuildFields.add(bean);
					editFundFields.add(bean);
					editBuildProgressFields.add(bean);
				}
				if (bean.getFieldGroup().contains("建设简报")) {
					editBuildFields.add(bean);
				}
				if (bean.getFieldGroup().contains("资金使用简报")) {
					editFundFields.add(bean);
				}
				if (bean.getFieldGroup().contains("建设进展简报")) {
					editBuildProgressFields.add(bean);
				}
			}
		}

		ArrayList<FieldBean> viewFields = (ArrayList<FieldBean>) modelMap.get(ControllerModelKeys.VIEW_FIELDS);

		List<FieldBean> viewBuildFields = new ArrayList<>();
		List<FieldBean> viewFundFields = new ArrayList<>();
		List<FieldBean> viewBuildProgressFields = new ArrayList<>();

		for (FieldBean bean : viewFields) {
			if (bean.getFieldGroup() != null) {
				if (bean.getFieldGroup().contains("简报基本信息")) {
					viewBuildFields.add(bean);
					viewFundFields.add(bean);
					viewBuildProgressFields.add(bean);
				}
				if (bean.getFieldGroup().contains("建设简报")) {
					viewBuildFields.add(bean);
				}
				if (bean.getFieldGroup().contains("资金使用简报")) {
					viewFundFields.add(bean);
				}
				if (bean.getFieldGroup().contains("建设进展简报")) {
					viewBuildProgressFields.add(bean);
				}
			}
		}

		List<FieldBean> proViewBuildFields = new ArrayList<>();
		List<FieldBean> proViewFundFields = new ArrayList<>();
		List<FieldBean> proViewBuildProgressFields = new ArrayList<>();

		for (FieldBean bean : viewBuildFields) {
			if (bean.getFieldGroup() != null) {
				if (bean.getFieldGroup().contains("建设简报")) {
					bean.setFieldGroup("建设简报");
				}

				if (bean.getFieldGroup().contains("简报基本信息") || bean.getFieldGroup().contains("建设简报")) {
					proViewBuildFields.add(bean);
				}
			}
		}

		for (FieldBean bean : viewFundFields) {
			if (bean.getFieldGroup() != null) {
				if (bean.getFieldGroup().contains("资金使用简报")) {
					bean.setFieldGroup("资金使用简报");
				}

				if (bean.getFieldGroup().contains("简报基本信息") || bean.getFieldGroup().contains("资金使用简报")) {
					proViewFundFields.add(bean);
				}
			}
		}

		for (FieldBean bean : viewBuildProgressFields) {
			if (bean.getFieldGroup() != null) {
				if (bean.getFieldGroup().contains("建设进展简报")) {
					bean.setFieldGroup("建设进展简报");
				}

				if (bean.getFieldGroup().contains("简报基本信息") || bean.getFieldGroup().contains("建设进展简报")) {
					proViewBuildProgressFields.add(bean);
				}
			}
		}

		model.addAttribute(MAP_KEY_ADD_BUILD_FIELDS, addBuildFields);
		model.addAttribute(MAP_KEY_ADD_FUND_FIELDS, addFundFields);
		model.addAttribute(MAP_KEY_ADD_BUILD_PROGRESS_FIELDS, addBuildProgressFields);

		model.addAttribute(MAP_KEY_EDIT_BUILD_FIELDS, editBuildFields);
		model.addAttribute(MAP_KEY_EDIT_FUND_FIELDS, editFundFields);
		model.addAttribute(MAP_KEY_EDIT_BUILD_PROGRESS_FIELDS, editBuildProgressFields);

		model.addAttribute(MAP_KEY_VIEW_BUILD_FIELDS, proViewBuildFields);
		model.addAttribute(MAP_KEY_VIEW_FUND_FIELDS, proViewFundFields);
		model.addAttribute(MAP_KEY_VIEW_BUILD_PROGRESS_FIELDS, proViewBuildProgressFields);

		// Datagrid行双击事件，增加回调，处理省市下拉框联动
		model.addAttribute(MODEL_KEY_DBL_CLICK_ROW_HANDLER, "PlanProgressUtils.handleDblClickRow");

		return viewName;
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

		//JsonResultBean jsonResult = super.list(commonFilterJson, null, page, rows, sort, order, locale);
		JsonResultBean jsonResult = planProgressService.list(commonFilterJson, page, rows, sort, order, locale);

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
		return super.find(id, locale);
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
	@SysLogAnnotation(description = "进度上报", action = "新增")
	public void create(HttpServletRequest request, HttpServletResponse response, @ModelAttribute PlanProgressBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
		// 参数在projectInitHidden.jsp中用GET的方式设定
		String projectId = bean.getProject_();
		bean.setProject_(projectId);
		String userid = userBean.getSessionUserId();
		bean.setApplicant_(userid);
		CommonFilter filter = new CommonFilter();
		filter.addExact("project.id", projectId);
		filter.addExact("person.id", userid);
		ProjectPerson projectPerson = projectPersonService.list(filter, null).get(0);// (projectId, userid);
		if (projectPerson != null && projectPerson.getVirtualOrg() != null) {
			bean.setVirtualOrg_(projectPerson.getVirtualOrg().getId());
		}
		Date now = new Date();
		bean.setProgressDate(DateUtils.date2String(now, Constants.DATE_FORMAT));
		bean.setFlowStatus(FlowStatusEnum.UNSUBMITTED.value());

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

		// 实际进度的文件，位于/plan-progress/id文件夹内，id是项目组的主键值
		super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean, locale);

	}

	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/edit")
	@SysLogAnnotation(description = "进度上报", action = "更新")
	public void update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute PlanProgressBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
		// 参数在projectInitHidden.jsp中用GET的方式设定

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

		// 项目的文件，位于/project/id文件夹内，id是项目的主键值
		super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
	}

	@Override
	protected void postUpdate(PlanProgress entity, PlanProgress oldEntity, PlanProgressBean bean,
			java.util.Map<String, Object> paramMap) throws IOException {
		super.postUpdate(entity, oldEntity, bean, paramMap);

		List<Archive> list = new ArrayList<>();
		for (PlanProgressAttachment attachment : entity.getAttachmentsToDelete()) {
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
	@SysLogAnnotation(description = "进度上报", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}

	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {

		Map<String, String> jsMap = ControllerMapUtils.buildJsMap(idMap, urlMap, this.useFile(),
				this.getJsCallbackObjName());

		// 修改新增页面
		jsMap.put(ControllerJsMapKeys.OPEN_ADD, String.format("PlanProgressUtils.openAddFormDialog('#%1$s');",
				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 导出文件
		jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT, String.format("PlanProgressUtils.exportExcelFile('#%1$s');",
				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		jsMap.put(ControllerJsMapKeys.OPEN_PRINT,
				String.format("PlanProgressUtils.previewInfo('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		return jsMap;
	}

	@RequestMapping("/export-excel-file")
	public void ExportExcelFile(@RequestParam String planId, HttpServletResponse response, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();

		try {

			String[] idArray = StringUtils.commaDelimitedListToStringArray(planId);
			List<String> idList = Arrays.asList(idArray);

			CommonFilter filter = new CommonFilter();
			filter.addWithin("id", idList);

			List<PlanProgress> planProgresses = this.planProgressService.list(filter, null);

			List<PlanProgress> handleProgresses = new ArrayList<>();

			for (PlanProgress pp : planProgresses) {
				// 内部简报/建设进展简报才能导出
				if (ReportTypeEnum.INTERNAL_REPORT.value().equals(pp.getReportType())
						|| (ReportTypeEnum.CONSTRUCTION_PROGRESS_REPORT.value().equals(pp.getReportType())
								&& !pp.getPlanProgressAttachments().isEmpty())) {
					handleProgresses.add(pp);
				}
			}

			File downloadFile = null;

			if (handleProgresses.size() == 1) {
				downloadFile = this.handleDownloadReportFile(handleProgresses.get(0), locale);
			} else if (handleProgresses.size() > 1) {
				Date now = new Date();

				String fileName = "简报_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT) + ".zip";

				String path = this.context.getRealPath("/");

				// mtstar\webapp\webapps\app
				File root = new File(path);

				File uploadPath = new File(root.getParentFile().getParentFile().getParent(), "temp");

				FileUtils.forceMkdir(uploadPath);

				downloadFile = new File(uploadPath.getAbsoluteFile(), fileName);

				List<SimpleEntry<String, String>> files = new ArrayList<>();
				Map<String, String> entryNameMap = new HashMap<>();

				for (PlanProgress pp : handleProgresses) {

					File handleFile = this.handleDownloadReportFile(pp, locale);

					files.add(new SimpleEntry<>(handleFile.getAbsolutePath(), ""));
					entryNameMap.put(handleFile.getAbsolutePath(), handleFile.getName());
				}

				ZipUtils.zip(files, entryNameMap, downloadFile.getAbsolutePath(), null);
			} else {
				downloadFile = new File("无导出内容.txt");
			}

			ControllerDownloadUtils.downloadFile(downloadFile, response);
			FileUtils.deleteQuietly(downloadFile);
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while export project init xls file", e);
			jsonResult.setStatus(JsonStatus.ERROR);
		}
	}

	private File handleDownloadReportFile(PlanProgress pp, Locale locale) throws IOException {

		File downloadFile = null;
		// 内部简报/建设进展简报才能导出
		if (ReportTypeEnum.INTERNAL_REPORT.value().equals(pp.getReportType())) {
			downloadFile = this.generatePlanTaskReportExcelFile(pp.getId(), locale);
		} else if (ReportTypeEnum.CONSTRUCTION_PROGRESS_REPORT.value().equals(pp.getReportType())) {

			String rootFolderPath = this.systemConfigService.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

			Set<PlanProgressAttachment> planProgressAttachmentset = pp.getPlanProgressAttachments();

			List<PlanProgressAttachment> list = new ArrayList<>(planProgressAttachmentset);

			String path = this.context.getRealPath("/");
			File root = new File(path);
			File copyPath = new File(root.getParentFile().getParentFile().getParent(), "temp");

			FileUtils.forceMkdir(copyPath);

			if (list.size() == 1) {
				Archive archive = list.get(0).getArchive();

				File realPath = new File(rootFolderPath + archive.getRelativePath());

				Date now = new Date();

				String formatName = "建设进展简报_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT);

				downloadFile = new File(copyPath, formatName + Constants.UNDERSCORE + archive.getArchiveName());
				FileUtils.copyFile(realPath, downloadFile);
			} else if (list.size() > 1) {
				// TODO
			}
		}

		return downloadFile;
	}

	@RequestMapping("/{id}/preview")
	public String preview(@PathVariable String id, HttpServletResponse response, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws IOException {
		String planProgressId = id;
		FileOutputStream output = null;
		File file = null;
		try {
			PlanProgress planProgress = this.planProgressService.find(planProgressId);

			CommonFilter filter = new CommonFilter();
			filter.addExact("planProgress.id", planProgressId);

			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("taskNum", false));

			List<PlanProgressTask> planTasks = this.planProgressTaskService.list(filter, orders);

			Plan plan = this.planService.find(planProgress.getPlan().getId());

			Workbook wb = new XSSFWorkbook();
			setUp(wb, plan, planProgress, planTasks, locale);
			Date now = new Date();

			String fileName = "内部简报_" + plan.getPlanName() + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
					+ ".xlsx";
			String randomID = UUID.randomUUID().toString();

			// mtstar\webapp\webapps\app

			String rootPath = this.context.getRealPath(FolderPath.PREVIEW_CACHE);
			String relativePath = "temp" + File.separator + planProgressId + File.separator + randomID;

			File uploadPath = new File(rootPath, relativePath);

			FileUtils.forceMkdir(uploadPath);

			file = new File(uploadPath.getAbsoluteFile(), fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			output = new FileOutputStream(file);
			wb.write(output);
			output.flush();

			try {

				this.excelToHtml(rootPath, relativePath, "简报预览", fileName);

			} catch (Exception e) {
				logger.error("Exception preview Excel archive file.", e);
				return "common/page/error";
			}

			String contextPath = this.context.getContextPath();
			model.addAttribute(MODLE_KEY_HTML_URL, contextPath + FolderPath.PREVIEW_CACHE + File.separator
					+ relativePath.replace(Constants.BACK_SLASH, Constants.SLASH) + ".html"); // HTML的URL
		} finally {

			if (output != null) {
				output.close();
			}
		}

		return "archive/preview/htmlViewer";
	}

	private String excelToHtml(String rootPath, String relativePath, String name, String filename) throws Exception {

		String path = rootPath + File.separator + relativePath + File.separator + filename;
		String htmlPath = rootPath + File.separator + relativePath + ".html";
		String attachedDir = rootPath + File.separator + relativePath;

		String parent = FilenameUtils.getFullPathNoEndSeparator(relativePath);
		if (File.separator.equals(parent)) {
			parent = ""; // 确保路径末尾不加斜杠
		}
		String baseDir = rootPath + parent;

		File html = new File(htmlPath);

		if (!html.exists()) {

			File attachedDirFile = new File(attachedDir);
			if (!attachedDirFile.exists()) {
				attachedDirFile.mkdirs();
			}

			ExcelConverterUtils.toHtml(path, htmlPath, attachedDir, name);

			// 修改入口html中的链接的前缀
			// String encoding =
			// garage.origin.common.util.FileUtils.getFilecharset(htmlPath);
			String content = FileUtils.readFileToString(html, FileEncodingTypeEnum.UTF8);
			content = content.replace(baseDir + File.separator, "");
			FileUtils.writeStringToFile(html, content, FileEncodingTypeEnum.UTF8);
		}

		return htmlPath;
	}

	// 上报简报导出
	private File generatePlanTaskReportExcelFile(String planProgressId, Locale locale) throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {
			PlanProgress planProgress = this.planProgressService.find(planProgressId);

			CommonFilter filter = new CommonFilter();
			filter.addExact("planProgress.id", planProgressId);

			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("taskNum", false));

			List<PlanProgressTask> planTasks = this.planProgressTaskService.list(filter, orders);

			Plan plan = this.planService.find(planProgress.getPlan().getId());

			Workbook wb = new XSSFWorkbook();
			setUp(wb, plan, planProgress, planTasks, locale);
			Date now = new Date();

			String fileName = "内部简报_" + plan.getPlanName() + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
					+ ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			System.out.println("root.getParentFile().getParentFile().getParent():::::::"
					+ root.getParentFile().getParentFile().getParent());

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + planProgressId + File.separator + randomID);

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

	private void setUp(Workbook wb, Plan plan, PlanProgress planProgress, List<PlanProgressTask> planTasks,
			Locale locale) {

		Sheet sheet = wb.createSheet(plan.getPlanName());
		Row header = sheet.createRow(0);
		header.setHeight((short) 700);
		CellRangeAddress cheader = new CellRangeAddress(0, 0, 0, 11); // 起始行, 终止行, 起始列, 终止列
		// sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
		sheet.addMergedRegion(cheader);

		sheet.setColumnWidth(0, 20 * 100);
		sheet.setColumnWidth(1, 20 * 200);
		sheet.setColumnWidth(2, 20 * 80);
		sheet.setColumnWidth(3, 20 * 100);
		sheet.setColumnWidth(4, 20 * 80);
		sheet.setColumnWidth(5, 20 * 150);
		sheet.setColumnWidth(6, 20 * 150);
		sheet.setColumnWidth(7, 20 * 150);
		sheet.setColumnWidth(8, 20 * 150);
		sheet.setColumnWidth(9, 20 * 150);
		sheet.setColumnWidth(10, 20 * 100);
		sheet.setColumnWidth(11, 20 * 300);

		Cell headCell = header.createCell(0);

		Project p = this.projectService.find(plan.getProject().getId());

//		String resourceStr = this.messageSource.getMessage(DateTypeEnum.getResouceName(planProgress.getDateType()),
//				null, locale);
		headCell.setCellValue(p.getProjectName() + "项目\r\n " + planProgress.getProgressName());

		XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();

		cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

		Font font = wb.createFont();
		font.setBold(true); // 加粗
		font.setFontHeightInPoints((short) 15); // 设置标题字体大小
		cellStyle.setFont(font);

		XSSFCellStyle cellStyle2 = (XSSFCellStyle) wb.createCellStyle();
		headCell.setCellStyle(cellStyle);
		cellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle2.setAlignment(HorizontalAlignment.LEFT);
		cellStyle2.setFont(font);
		RegionUtil.setBorderBottom(1, cheader, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cheader, sheet); // 左边框
		RegionUtil.setBorderRight(1, cheader, sheet); // 有边框
		RegionUtil.setBorderTop(1, cheader, sheet); // 上边框

		Row properties = sheet.createRow(1);
		properties.setHeight((short) 400);

		CellRangeAddress cell_0 = new CellRangeAddress(1, 1, 0, 6);
		sheet.addMergedRegion(cell_0);

		if (plan.getVirtualOrg() != null) {
			properties.createCell(0).setCellValue(plan.getVirtualOrg().getVirtualOrgName());
		} else {
			properties.createCell(0).setCellValue("");
		}
//		properties.getCell(0).setCellStyle(cellStyle2);

		RegionUtil.setBorderBottom(1, cell_0, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_0, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_0, sheet); // 有边框
		RegionUtil.setBorderTop(1, cell_0, sheet); // 上边框

		CellRangeAddress cell_2 = new CellRangeAddress(1, 1, 7, 11);
		sheet.addMergedRegion(cell_2);
		properties.createCell(7)
				.setCellValue(DateUtils.date2String(planProgress.getProgressDate(), Constants.DATE_FORMAT));

		RegionUtil.setBorderBottom(1, cell_2, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_2, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_2, sheet); // 有边框
		RegionUtil.setBorderTop(1, cell_2, sheet); // 上边框

		XSSFCellStyle WrapTextCellStyle = (XSSFCellStyle) wb.createCellStyle();
		WrapTextCellStyle.setWrapText(true);
		
		String progressDescription = planProgress.getDescription();
		if (progressDescription == null) {
			progressDescription = "";
		}
		Row summary = sheet.createRow(2);
		CellRangeAddress cell_summary = new CellRangeAddress(2, 2, 0, 11);
		sheet.addMergedRegion(cell_summary);
		summary.createCell(0).setCellValue("【进展概述】");
		summary.getCell(0).setCellStyle(cellStyle2);
		RegionUtil.setBorderBottom(1, cell_summary, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_summary, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_summary, sheet); // 有边框
		RegionUtil.setBorderTop(1, cell_summary, sheet); // 上边框

		Row summary2 = sheet.createRow(3);
		summary2.createCell(0).setCellValue(progressDescription);
		summary2.getCell(0).setCellStyle(WrapTextCellStyle);
		summary2.setHeight((short) 800);
		CellRangeAddress cell_summary2 = new CellRangeAddress(3, 3, 0, 11);
		sheet.addMergedRegion(cell_summary2);
//		summary2.createCell(0).setCellValue(planProgress.getRemark());
		RegionUtil.setBorderBottom(1, cell_summary2, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_summary2, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_summary2, sheet); // 有边框
		RegionUtil.setBorderTop(1, cell_summary2, sheet); // 上边框

		Row title = sheet.createRow(4);
		CellRangeAddress cell_title = new CellRangeAddress(4, 4, 0, 11);
		sheet.addMergedRegion(cell_title);
		title.createCell(0).setCellValue("【任务状态一览】");
		title.getCell(0).setCellStyle(cellStyle2);
		RegionUtil.setBorderBottom(1, cell_title, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_title, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_title, sheet); // 有边框
		RegionUtil.setBorderTop(1, cell_title, sheet); // 上边框

//        properties.createCell(3).setCellValue("项目名称");
//
//        properties.createCell(6).setCellValue("项目类型");
//
//        properties.createCell(6).setCellValue("上报时间");

		XSSFCellStyle WrapTextCellStyle2 = (XSSFCellStyle) wb.createCellStyle();
		WrapTextCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		WrapTextCellStyle2.setBorderBottom(BorderStyle.THIN);
		WrapTextCellStyle2.setBorderLeft(BorderStyle.THIN);
		WrapTextCellStyle2.setBorderRight(BorderStyle.THIN);
		WrapTextCellStyle2.setBorderTop(BorderStyle.THIN);
		WrapTextCellStyle2.setWrapText(true);

		XSSFCellStyle titleTextCellStyle = (XSSFCellStyle) wb.createCellStyle();
		titleTextCellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
		titleTextCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
		titleTextCellStyle.setBorderBottom(BorderStyle.THIN);
		titleTextCellStyle.setBorderLeft(BorderStyle.THIN);
		titleTextCellStyle.setBorderRight(BorderStyle.THIN);
		titleTextCellStyle.setBorderTop(BorderStyle.THIN);
		titleTextCellStyle.setWrapText(true);

		Row titleCol = sheet.createRow(5);
		titleCol.setHeight((short) 800);

		titleCol.createCell(0).setCellStyle(titleTextCellStyle);
		titleCol.getCell(0).setCellValue("WBS标识");
		titleCol.createCell(1).setCellStyle(titleTextCellStyle);
		titleCol.getCell(1).setCellValue("任务名称");
		titleCol.createCell(2).setCellStyle(titleTextCellStyle);
		titleCol.getCell(2).setCellValue("状态");
		titleCol.createCell(3).setCellStyle(titleTextCellStyle);
		// titleCol.getCell(3).setCellValue("计划开始日期\r\n" + "（年月日）");
		titleCol.getCell(3).setCellValue("上期进度");
		titleCol.createCell(4).setCellStyle(titleTextCellStyle);
		// titleCol.getCell(4).setCellValue("计划结束日期\r\n" + "（年月日）");
		titleCol.getCell(4).setCellValue("本期进度");
		titleCol.createCell(5).setCellStyle(titleTextCellStyle);
		titleCol.getCell(5).setCellValue("计划开始时间");
		titleCol.createCell(6).setCellStyle(titleTextCellStyle);
		titleCol.getCell(6).setCellValue("计划结束时间");
		titleCol.createCell(7).setCellStyle(titleTextCellStyle);
		titleCol.getCell(7).setCellValue("实际开始时间");
		titleCol.createCell(8).setCellStyle(titleTextCellStyle);
		titleCol.getCell(8).setCellValue("实际结束时间");
		titleCol.createCell(9).setCellStyle(titleTextCellStyle);
		titleCol.getCell(9).setCellValue("结束偏离天数");
		titleCol.createCell(10).setCellStyle(titleTextCellStyle);
		titleCol.getCell(10).setCellValue("负责人");
		titleCol.createCell(11).setCellStyle(titleTextCellStyle);
		titleCol.getCell(11).setCellValue("参与人员");

		int currentStatusIndex = 6 + planTasks.size();
		Row currentStatusRow = sheet.createRow(currentStatusIndex);
		CellRangeAddress cell_currentStatus = new CellRangeAddress(currentStatusIndex, currentStatusIndex, 0, 11);
		sheet.addMergedRegion(cell_currentStatus);
		currentStatusRow.createCell(0).setCellValue("【本期完成情况】");
		currentStatusRow.getCell(0).setCellStyle(cellStyle2);
		RegionUtil.setBorderBottom(1, cell_currentStatus, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_currentStatus, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_currentStatus, sheet); // 有边框
		RegionUtil.setBorderTop(1, cell_currentStatus, sheet); // 上边框

		// int currentStatusTextIndex = 6 + planTasks.size();
		// Row currentStatusTextRow = sheet.createRow(currentStatusTextIndex);
		// sheet.addMergedRegion(
		// new CellRangeAddress(currentStatusTextIndex, currentStatusTextIndex, 0, 9));

		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String linefeed = "\r\n";
		String noneText = "无";

		int nextStatusIndex = 6 + planTasks.size() * 2 + 1;
		Row nextStatusRow = sheet.createRow(nextStatusIndex);
		CellRangeAddress cell_nextStatus = new CellRangeAddress(nextStatusIndex, nextStatusIndex, 0, 11);
		sheet.addMergedRegion(cell_nextStatus);

		nextStatusRow.createCell(0).setCellValue("【下期计划】");
		nextStatusRow.getCell(0).setCellStyle(cellStyle2);
		RegionUtil.setBorderBottom(1, cell_nextStatus, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_nextStatus, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_nextStatus, sheet); // 有边框
		RegionUtil.setBorderTop(1, cell_nextStatus, sheet); // 上边框

		int remarkIndex = 6 + planTasks.size() * 3 + 2;
		Row remarkRow = sheet.createRow(remarkIndex);
		CellRangeAddress cell_remark = new CellRangeAddress(remarkIndex, remarkIndex, 0, 11);
		sheet.addMergedRegion(cell_remark);

		remarkRow.createCell(0).setCellValue("【专业成效】");
		remarkRow.getCell(0).setCellStyle(cellStyle2);
		RegionUtil.setBorderBottom(1, cell_remark, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_remark, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_remark, sheet); // 有边框

		int remarkInfoIndex = 6 + planTasks.size() * 3 + 3;
		Row remarkInfoRow = sheet.createRow(remarkInfoIndex);
		CellRangeAddress cell_remarkInfo = new CellRangeAddress(remarkInfoIndex, remarkInfoIndex, 0, 11);
		sheet.addMergedRegion(cell_remarkInfo);

		remarkInfoRow.createCell(0).setCellValue(planProgress.getRemark());
		remarkInfoRow.getCell(0).setCellStyle(WrapTextCellStyle);
		RegionUtil.setBorderBottom(1, cell_remarkInfo, sheet); // 下边框
		RegionUtil.setBorderLeft(1, cell_remarkInfo, sheet); // 左边框
		RegionUtil.setBorderRight(1, cell_remarkInfo, sheet); // 有边框
		RegionUtil.setBorderTop(1, cell_remarkInfo, sheet); // 上边框
		RegionUtil.setBorderTop(1, cell_remarkInfo, sheet); // 上边框

		// int nextStatusTextIndex = 6 + planTasks.size() * 2 + 2;
		// Row nextStatusTextRow = sheet.createRow(nextStatusTextIndex);
		// sheet.addMergedRegion(
		// new CellRangeAddress(nextStatusTextIndex, nextStatusTextIndex, 0, 9));

		for (int i = 0; i < planTasks.size(); i++) {

			PlanProgressTask task = planTasks.get(i);

			{
				sb = new StringBuilder();

				sb.append(task.getWbs()).append(" - ").append(task.getTaskName()).append(linefeed);

//				DecimalFormat percent = new DecimalFormat("0.00%");
				String progressText = String.valueOf(new Double(task.getProgress()).intValue()) + "%";

				sb.append("进度：").append(progressText).append(linefeed);

				String cumulativeProgressText = StringUtils.isEmpty(task.getCumulativeProgress()) ? noneText
						: task.getCumulativeProgress();

				sb.append("累计完成：").append(cumulativeProgressText).append(linefeed);

				String currentWeekProgress = StringUtils.isEmpty(task.getCurrentWeekProgress()) ? noneText
						: task.getCurrentWeekProgress();

				sb.append("本期完成：").append(currentWeekProgress).append(linefeed);

				String description = StringUtils.isEmpty(task.getDescription()) ? noneText : task.getDescription();

				sb.append("计划偏离/问题：").append(description).append(linefeed);

				int currentStatusTextIndex = 7 + i + planTasks.size();
				Row currentStatusTextRow = sheet.createRow(currentStatusTextIndex);
				currentStatusTextRow.setHeight((short) 1800);
				CellRangeAddress cell_current = new CellRangeAddress(currentStatusTextIndex, currentStatusTextIndex, 0,
						11);
				sheet.addMergedRegion(cell_current);

				if (sb.length() > 0) {
					Cell cell = currentStatusTextRow.createCell(0);
					cell.setCellValue(sb.toString());
					cell.setCellStyle(WrapTextCellStyle);
				}
				RegionUtil.setBorderBottom(1, cell_current, sheet); // 下边框
				RegionUtil.setBorderLeft(1, cell_current, sheet); // 左边框
				RegionUtil.setBorderRight(1, cell_current, sheet); // 有边框
				RegionUtil.setBorderTop(1, cell_current, sheet); // 上边框
			}

			{
				sb2 = new StringBuilder();

				sb2.append(task.getWbs()).append(" - ").append(task.getTaskName()).append(linefeed);
				String nextWeekGoals = StringUtils.isEmpty(task.getNextWeekGoals()) ? noneText
						: task.getNextWeekGoals();
				sb2.append("下期计划：").append(nextWeekGoals).append(linefeed);

				int nextStatusTextIndex = 7 + i + planTasks.size() * 2 + 1;
				Row nextStatusTextRow = sheet.createRow(nextStatusTextIndex);
				nextStatusTextRow.setHeight((short) 1800);
				CellRangeAddress cell_next = new CellRangeAddress(nextStatusTextIndex, nextStatusTextIndex, 0, 11);
				sheet.addMergedRegion(cell_next);

				if (sb2.length() > 0) {
					Cell cell = nextStatusTextRow.createCell(0);
					cell.setCellValue(sb2.toString());
					cell.setCellStyle(WrapTextCellStyle);

				}
				RegionUtil.setBorderBottom(1, cell_next, sheet); // 下边框
				RegionUtil.setBorderLeft(1, cell_next, sheet); // 左边框
				RegionUtil.setBorderRight(1, cell_next, sheet); // 有边框
				RegionUtil.setBorderTop(1, cell_next, sheet); // 上边框
			}

			Row row = sheet.createRow(i + 6);
			row.setHeight((short) 500);
			row.createCell(0).setCellStyle(WrapTextCellStyle2);
			row.getCell(0).setCellValue(task.getWbs());

			row.createCell(1).setCellStyle(WrapTextCellStyle2);
			row.getCell(1).setCellValue(task.getTaskName());

			row.createCell(2).setCellStyle(WrapTextCellStyle2);
			row.getCell(2).setCellValue(this.messageSource
					.getMessage(ProgressFlowStatusEnum.getResouceName(task.getFlowStatus()), null, locale));

			row.createCell(3).setCellStyle(WrapTextCellStyle2);
			row.createCell(4).setCellStyle(WrapTextCellStyle2);
			row.createCell(5).setCellStyle(WrapTextCellStyle2);

//			DecimalFormat percent = new DecimalFormat("0.00%");
			String lastProgressText = String.valueOf(new Double(task.getLastProgress()).intValue()) + "%";
			row.getCell(3).setCellValue(lastProgressText);
			String progressText = String.valueOf(new Double(task.getProgress()).intValue()) + "%";
			row.getCell(4).setCellValue(progressText);

			row.createCell(5).setCellStyle(WrapTextCellStyle2);
			if (task.getPlanStartDate() != null) {

				row.getCell(5).setCellValue(DateUtils.date2String(task.getPlanStartDate(), Constants.DATE_FORMAT));

			}

			row.createCell(6).setCellStyle(WrapTextCellStyle2);
			if (task.getPlanEndDate() != null) {

				row.getCell(6).setCellValue(DateUtils.date2String(task.getPlanEndDate(), Constants.DATE_FORMAT));
			}

			row.createCell(7).setCellStyle(WrapTextCellStyle2);
			if (task.getActualStartDate() != null) {

				row.getCell(7).setCellValue(DateUtils.date2String(task.getActualStartDate(), Constants.DATE_FORMAT));
			}

			row.createCell(8).setCellStyle(WrapTextCellStyle2);
			if (task.getActualEndDate() != null) {

				row.getCell(8).setCellValue(DateUtils.date2String(task.getActualEndDate(), Constants.DATE_FORMAT));

			}

			row.createCell(9).setCellStyle(WrapTextCellStyle2);
			if ((task.getActualEndDate() != null) && (task.getPlanEndDate() != null)) {

				long day = (task.getActualEndDate().getTime() - task.getPlanEndDate().getTime())
						/ (24 * 60 * 60 * 1000);
				row.getCell(9).setCellValue(day + "");
			} else {
				row.getCell(9).setCellValue("0");
			}

			row.createCell(10).setCellStyle(WrapTextCellStyle2);
			if (task.getLeader() != null) {
				Person person = this.personService.find(task.getLeader().getId());

				row.getCell(10).setCellValue(person.getPersonName());
			}

			row.createCell(11).setCellStyle(WrapTextCellStyle2);
			row.getCell(11).setCellValue(task.getParticipantNameList());
		}

//		summary.getCell(0).setCellStyle(WrapTextCellStyle2);
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
		printSetup.setLandscape(true);// 打印方向，true：横向，false：纵向(默认)

	}

	@RequestMapping("/find-reportProgress")
	@ResponseBody
	public JsonResultBean findReportProgress(@RequestParam String id, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 参数检查
			if (StringUtils.isEmpty(id)) {
				jsonResult.setStatus(JsonStatus.ERROR);
			}

			PlanProgress progress = this.planProgressService.find(id);
			PlanProgressBean progressBean = new PlanProgressBeanConverter().fromEntity(progress, this.messageSource,
					locale);

			// 过滤
			CommonFilter taskFilter = new CommonFilter().addExact("planProgress.id", id);
			OrderByDto orderDto = new OrderByDto();
			orderDto.setColumnName("wbs");
			orderDto.setDesc(false);
			List<OrderByDto> orderByDtos = new ArrayList<OrderByDto>();
			orderByDtos.add(orderDto);
			List<PlanProgressTask> tasks = this.planProgressTaskService.list(taskFilter, orderByDtos);
			String[] statusArr = { "", "未启动", "延期中", "进行中", "完成", "搁置", "取消" };
			for (PlanProgressTask task : tasks) {
				PlanProgressTaskBean taskBean = new PlanProgressTaskBean();
				taskBean.setPlan_(tasks.get(0).getTaskName());
				taskBean.setPlanTaskText(task.getTaskName());
				taskBean.setLastProgress(task.getLastProgress());
				taskBean.setProgress(task.getProgress());
				taskBean.setActualStartDate(DateUtils.date2String(task.getActualStartDate(), "yyyy-MM-dd"));
				taskBean.setActualEndDate(DateUtils.date2String(task.getActualEndDate(), "yyyy-MM-dd"));
				taskBean.setFlowStatusText(statusArr[task.getFlowStatus()]);
//				taskBean.setFlowStatusText(this.messageSource
//						.getMessage(FlowStatusEnum.getResouceName(task.getFlowStatus()), null, locale));
				taskBean.setCumulativeProgress(task.getCumulativeProgress());
				taskBean.setCurrentWeekProgress(task.getCurrentWeekProgress());
				taskBean.setDescription(task.getDescription());
				taskBean.setNextWeekGoals(task.getNextWeekGoals());
				taskBean.setProgressRemark(task.getProgressRemark());
				progressBean.getPlanProgressTaskBeans().add(taskBean);
			}

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, progressBean);
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception planProgress.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
	}

}
