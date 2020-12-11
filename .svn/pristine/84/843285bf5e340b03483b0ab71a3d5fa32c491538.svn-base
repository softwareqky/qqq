package project.edge.web.controller.process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.NotifyTargetTypeEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.ProjectInspectBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Notify;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectInspect;
import project.edge.domain.entity.ProjectInspectAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.ProjectInspectBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.facility.SiteService;
import project.edge.service.hr.PersonService;
import project.edge.service.notice.NoticeNotifyService;
import project.edge.service.notice.NoticeNotifySubscriptionService;
import project.edge.service.process.ProjectInspectService;
import project.edge.service.project.ProjectService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 项目巡查画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/process/project-inspect")
public class ProjectInspectController extends TreeGridUploadController<ProjectInspect, ProjectInspectBean> {

	private static final Logger logger = LoggerFactory.getLogger(ProjectInspectController.class);

	private static final String PID = "P6005";

	private static final String URL_MAP_KEY_EDIT_FILE = "edit-file"; // 上传文件的URL
	private static final String URL_MAP_KEY_SEL_EXPERT = "sel-expert";

    private static final String MODEL_KEY_IMPROVE_FIELDS = "improveFields";
    private static final String MODEL_KEY_VERIFICATION_FIELDS = "verificationFields";
    private static final String MODEL_KEY_EXPERTREVIEW_FIELDS = "expertReviewFields";

    private static final String ID_MAP_KEY_IMPROVE_DIALOG = "edit-improve-form-dialog";
    private static final String ID_MAP_KEY_VERIFICATION_DIALOG = "edit-verification-form-dialog";
    private static final String ID_MAP_KEY_SEL_EXPERT_DIALOG = "sel-expert-dialog";
    private static final String ID_MAP_KEY_EXPERTREVIEW_DIALOG = "edit-expertreview-form-dialog";

    private static final String JS_MAP_KEY_EDIT_IMPROVE_SUBMIT = "edit-improve-submit";
    private static final String JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT = "edit-verification-submit";
    private static final String JS_MAP_KEY_SEL_EXPERT = "sel-expert";
    private static final String JS_MAP_KEY_EDIT_EXPERTREVIEW_SUBMIT = "edit-expertreview-submit";

	private static final String SEL_EXPERT_DIALOG_ID = "%1$s-%2$s-SelExpertDialog";
	@Autowired
	HttpServletRequest request;
	@Resource
	CreateWorkFlowManager createWorkFlowManager;
	@Resource
	private ProjectInspectService projectInspectService;

	@Resource
	private ProjectService projectService;
	@Resource
	private PersonService personService;
	@Resource
	private NoticeNotifyService notifyService;
	@Resource
	private NoticeNotifySubscriptionService notifySubscriptionService;

	@Resource
	private SiteService siteService;

	@Resource
	private DataOptionService dataOptionService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.PROJECT_INSPECT.value();
	}

	@Override
	protected Service<ProjectInspect, String> getDataService() {
		return this.projectInspectService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<ProjectInspect, ProjectInspectBean> getViewConverter() {
		return new ProjectInspectBeanConverter();
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/progress/projectInspectJs.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/progress/projectInspectHidden.jsp";
	}

	/**
	 * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
	 */
	@Override
	protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

        //if (field.isCommonVisible()) {
        //    return;
        //}

    	// 专家评审结果更新项
    	if ((field.getFieldName().equals("inspectResult_"))
                || (field.getFieldName().equals("isImprove"))
                || (field.getFieldName().equals("inspectDate"))
                || (field.getFieldName().equals("improveReqDate"))
                || (field.getFieldName().equals("inspectStatus_"))
                || (field.getFieldName().equals("flowStatus"))
                || (field.getFieldName().equals("remark"))) {
            this.putFiledList(model, MODEL_KEY_EXPERTREVIEW_FIELDS, field);
		}

        // 在[t_data_fields]中有且仅有这些附件字段是设为全局不可见
        if ((field.getFieldName().equals("project_"))
                || (field.getFieldName().equals("inspectContent"))
                || (field.getFieldName().equals("inspectType_"))
                || (field.getFieldName().equals("inspectResult_"))
                || (field.getFieldName().equals("site_"))
                || (field.getFieldName().equals("inspectPlan"))
                || (field.getFieldName().equals("isImprove"))
                || (field.getFieldName().equals("improvePlanDate"))
                || (field.getFieldName().equals("improveActualDate"))
                || (field.getFieldName().equals("improveReq"))
                || (field.getFieldName().equals("inspectStatus_"))
                || (field.getFieldName().equals("flowStatus"))
                || (field.getFieldName().equals("improvePlan"))) { // 建议
            this.putFiledList(model, MODEL_KEY_IMPROVE_FIELDS, field);

        }

        if ((field.getFieldName().equals("project_"))
                || (field.getFieldName().equals("inspectContent"))
                || (field.getFieldName().equals("inspectType_"))
                || (field.getFieldName().equals("inspectResult_"))
                || (field.getFieldName().equals("site_"))
                || (field.getFieldName().equals("inspectPlan"))
                || (field.getFieldName().equals("isImprove"))
                || (field.getFieldName().equals("verifyDate"))
                || (field.getFieldName().equals("improveResultVerify"))
                || (field.getFieldName().equals("verifyResult_"))
                || (field.getFieldName().equals("inspectStatus_"))
                || (field.getFieldName().equals("flowStatus"))
                || (field.getFieldName().equals("improvearchive_"))) { // 整改
			this.putFiledList(model, MODEL_KEY_VERIFICATION_FIELDS, field);
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
		Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<String, List<ComboboxOptionBean>>();

		ArrayList<ComboboxOptionBean> projectOptions = new ArrayList<ComboboxOptionBean>();
		CommonFilter f = null;
		List<Project> projectList = this.projectService.list(f, null);
		if (projectList != null) {
			for (Project p : projectList) {
				projectOptions.add(new ComboboxOptionBean(p.getId(), p.getProjectName()));
			}
		}
		optionMap.put("ProjectOptions", projectOptions);

		// ArrayList<ComboboxOptionBean> siteOptions = new
		// ArrayList<ComboboxOptionBean>();
		//
		// List<Site> siteList = this.siteService.list(f, null);
		// if (siteList != null) {
		// for (Site p : siteList) {
		// siteOptions.add(new ComboboxOptionBean(p.getId(),
		// p.getStationName()));
		// }
		// }
		// optionMap.put("SiteOptions", siteOptions);

		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.PROJECT_INSPECT_TYPE.value());
		dataTypeList.add(DataTypeEnum.INSPECT_RESULT.value());
		dataTypeList.add(DataTypeEnum.VERIFY_RESULT.value());
		dataTypeList.add(DataTypeEnum.INSPECT_STATUS.value());

		f = new CommonFilter().addWithin("dataType", dataTypeList);

		// 巡查类型
		List<ComboboxOptionBean> projectCheckMode = new ArrayList<>();
		// 巡查结果
		List<ComboboxOptionBean> checkResultMode = new ArrayList<>();
		// 验证结果
		List<ComboboxOptionBean> verifyResultMode = new ArrayList<>();

		List<ComboboxOptionBean> inspectStatusOptions = new ArrayList<>();

		List<DataOption> list = this.dataOptionService.list(f, null);
		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.PROJECT_INSPECT_TYPE.value())) {
				projectCheckMode.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			} else if (o.getDataType().equals(DataTypeEnum.INSPECT_RESULT.value())) {
				checkResultMode.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			} else if (o.getDataType().equals(DataTypeEnum.VERIFY_RESULT.value())) {
				verifyResultMode.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			} else if (o.getDataType().equals(DataTypeEnum.INSPECT_STATUS.value())) {
				inspectStatusOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			}
		}

		optionMap.put("PROJECT_CHECK_TYPE", projectCheckMode);
		optionMap.put("CHECK_RESULT", checkResultMode);
		optionMap.put("VERIFY_RESULT", verifyResultMode);
		optionMap.put("InspectStatusOptions", inspectStatusOptions);

		ArrayList<ComboboxOptionBean> flowStatus = new ArrayList<ComboboxOptionBean>();

		for (FlowStatusEnum s : FlowStatusEnum.values()) {
			flowStatus.add(new ComboboxOptionBean(s.value().toString(),
					messageSource.getMessage(s.resourceName(), null, locale)));
		}

		optionMap.put("FlowStatus", flowStatus);

		return optionMap;
	}

	@Override
	protected Map<String, String> prepareUrlMap() {

		Map<String, String> urlMap = super.prepareUrlMap();

		String contextPath = this.context.getContextPath();
		urlMap.put(URL_MAP_KEY_EDIT_FILE, contextPath + "/process/project-inspect/edit-file.json");
		urlMap.put(URL_MAP_KEY_SEL_EXPERT, contextPath + "/process/projectinspect-expert/main.htm");

		return urlMap;
	}

	@Override
	protected Map<String, String> prepareIdMap() {

		Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_IMPROVE_DIALOG, String.format("%1$s-%2$s-EditFormDialog-Improve",
                this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_VERIFICATION_DIALOG, String.format(
                "%1$s-%2$s-EditFormDialog-Verification", this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_SEL_EXPERT_DIALOG,
                String.format(SEL_EXPERT_DIALOG_ID, this.getPageId(), this.getDataType()));
        idMap.put(ID_MAP_KEY_EXPERTREVIEW_DIALOG, String.format(
                "%1$s-%2$s-EditFormDialog-ExpertReview", this.getPageId(), this.getDataType()));

		return idMap;
	}

	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {
		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

		// 打开新建和修改画面，以及连续新建保存时，增加回调，刷新项目集下拉框的内容

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', PROJECTINSPECT);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 新建保存，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 连续新建，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, PROJECTINSPECT);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 打开修改
        //jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
        //        String.format("PROJECTINSPECT.openEditFormDialog('#%1$s', '%2$s');",
        //                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
        //                urlMap.get(ControllerUrlMapKeys.FIND)));

        // 专家评审结果更新
        jsMap.put(ControllerJsMapKeys.OPEN_EXPERTREVIEW,
                String.format("PROJECTINSPECT.openEditExpertReviewFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 抽查
        jsMap.put(ControllerJsMapKeys.OPEN_IMPROVE,
                String.format("PROJECTINSPECT.openEditFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 验证
        jsMap.put(ControllerJsMapKeys.OPEN_VERIFY,
                String.format("PROJECTINSPECT.openEditFormDialog('#%1$s', '%2$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
                        urlMap.get(ControllerUrlMapKeys.FIND)));
        
        // 修改保存-抽查
        jsMap.put(JS_MAP_KEY_EDIT_IMPROVE_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_IMPROVE_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));

        // 修改保存-验证
        jsMap.put(JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_VERIFICATION_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 修改保存-专家评审结果更新
        jsMap.put(JS_MAP_KEY_EDIT_EXPERTREVIEW_SUBMIT, 
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_EXPERTREVIEW_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));
        
        // 专家选择
        jsMap.put(JS_MAP_KEY_SEL_EXPERT, "PROJECTINSPECT.openSelExpertDialog()");

        return jsMap;
    }

    @Override
    protected void createFunctionBean(Page p, List<FunctionBean> functions,
            List<FunctionBean> searchFunctions, Map<String, String> funcGroupMap,
            Map<String, String> jsMap, Locale locale) {

		String pageId = p.getId();
		if (pageId.endsWith(ControllerFunctionIds.SELECT_EXPERT)) { // 专家选择

			this.parseFunction(p, functions, funcGroupMap, "", jsMap.get(JS_MAP_KEY_SEL_EXPERT), locale);

		}
	}

	/**
	 * 画面Open的入口方法，用于生成JSP。
	 * 
	 * @param paramMap
	 *            画面请求中的任何参数，都会成为检索的字段
	 * @param model
	 *            model
	 * @param userBean
	 *            session中的当前登录的用户信息
	 * @param locale
	 *            locale
	 * @return
	 */
	@RequestMapping("/main")
	public String main(@RequestParam Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        //return super.main(paramMap, model, userBean, locale);
    	String r = super.main(paramMap, model, userBean, locale);
        
        // Datagrid行选择的事件，除了默认的逻辑，还需要特别处理删除按钮，当至少选中一条记录，且没有选中继承权限的记录时，才启用该按钮
        model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "PROJECTINSPECT.handleSelect");
        model.addAttribute(ControllerModelKeys.CLICK_CELL_HANDLER, "PROJECTINSPECT.clickCellHandler");
        
        return r;
    }

	/**
	 * 获取一览显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson
	 *            JSON字符串形式的过滤条件
	 * @param page
	 *            页数
	 * @param rows
	 *            每页的记录数
	 * @param sort
	 *            排序的字段，CSV
	 * @param order
	 *            顺序，CSV
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

		JsonResultBean jsonResult = super.list(commonFilterJson, null, page, rows, sort, order, locale);

		return jsonResult;
	}

	/**
	 * 查找单条记录，返回Json格式。
	 * 
	 * @param id
	 *            ID
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
	 * @param bean
	 *            表现层对象
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/add")
	@SysLogAnnotation(description = "项目巡查", action = "新增")
	public void create(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ProjectInspectBean bean, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archive_", bean.getArchiveList());

		// 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
		super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean, locale);
	}
	
	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean
	 *            表现层对象
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/edit-file")
	@SysLogAnnotation(description = "项目巡查", action = "更新")
	public void update(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ProjectInspectBean bean,
			@RequestParam(required = false, defaultValue = "") String uploadType,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		bean.setUploadFileType(uploadType);
		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		// fieldNameArchiveListMap.put("archives_", bean.getArchivesList());
		fieldNameArchiveListMap.put("improvearchive_", bean.getImprovearchiveList());

		// 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
		super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
	}

	@Override
	protected void postUpdate(ProjectInspect entity, ProjectInspect oldEntity, ProjectInspectBean bean,
			java.util.Map<String, Object> paramMap) throws IOException {
		super.postUpdate(entity, oldEntity, bean, paramMap);
		// this.deleteArchiveFiles(entity.getArchivesToDelete());

		List<Archive> list = new ArrayList<>();
		for (ProjectInspectAttachment attachment : entity.getAttachmentsToDelete()) {
			list.add(attachment.getArchive());
		}
		this.deleteArchiveFiles(list);
	}

	// /**
	// * 修改，返回Json格式。
	// *
	// * @param bean 表现层对象
	// * @param userBean Session中的用户信息
	// * @param locale
	// * @return
	// */
	// @RequestMapping("/edit")
	// public void update(HttpServletRequest request, HttpServletResponse
	// response,
	// @ModelAttribute ProjectInspectBean bean,
	// @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale
	// locale) {
	//
	// Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
	// fieldNameArchiveListMap.put("archives_", bean.getArchivesList());
	//
	// // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
	// super.updateWithUpload(request, response, bean, fieldNameArchiveListMap,
	// userBean, locale);
	// }

	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean
	 *            表现层对象
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	@SysLogAnnotation(description = "项目巡查", action = "更新")
	public JsonResultBean update(@ModelAttribute ProjectInspectBean bean,
			@RequestParam(required = false, defaultValue = "") String uploadType,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		ProjectInspect prevEntity = projectInspectService.find(bean.getId());
		
		bean.setUploadFileType(uploadType);
		JsonResultBean result = super.update(bean, null, userBean, locale);
		
		// 添加或修改消息提醒
		if (result.getStatus() == JsonStatus.OK) {
			ProjectInspect entity = projectInspectService.find(bean.getId());
			this.createNotify(entity, prevEntity, userBean);
		}
		
		// 返回结果
		return result;
	}
	
	
	/**
	 * 添加或修改相关消息提醒
	 * @param inspect
	 * @param prevInspect
	 * @param user
	 * @return
	 */
	private void createNotify(ProjectInspect inspect, ProjectInspect prevInspect, SessionUserBean user) {
		
		try {
			
			String prevStatus = prevInspect.getInspectStatus().getId();
			String nextStatus = inspect.getInspectStatus().getId();
			
			Project project = inspect.getProject();
			
			String leaderUserId = personService.find(project.getMainLeader().getId()).getUser().getId();
			String inspectorId = personService.find(inspect.getInspector().getId()).getId();
			
			
			Notify notify = new Notify();
			notify.setType(1);
			notify.setAction("EDIT");
			notify.setCreateAt(new Date());
			notify.setCreateBy(user.getSessionUserId());
			notify.setTargetId(inspect.getId());
			notify.setTargetType(NotifyTargetTypeEnum.PROJECT_INSPECT.value());
			
			// 如果发起->进展中，需要添加[新巡查]提醒
			if (prevStatus.equals("INSPECT_STATUS_0") && nextStatus.equals("INSPECT_STATUS_2") && inspect.getInspectDate() != null) {
				notify.setContent("项目" + project.getProjectName() + "的巡检将在" + (DateUtils.date2String(inspect.getInspectDate(), Constants.DATE_FORMAT)) + "进行");
				notifyService.create(notify);
				notifySubscriptionService.subscribe(notify, leaderUserId, DateUtils.getDateByDaysDiff(inspect.getInspectDate(), -1));
			}
			// 如果进展中->批复，需要添加[待验证]提醒
			else if (prevStatus.equals("INSPECT_STATUS_2") && nextStatus.equals("INSPECT_STATUS_1") && inspect.getInspectDate() != null) {
				notify.setContent("项目" + project.getProjectName() + "的巡检结果为不合格，将在" + (DateUtils.date2String(inspect.getInspectDate(), Constants.DATE_FORMAT)) + "进行");
				notifyService.create(notify);
				notifySubscriptionService.subscribe(notify, inspectorId, DateUtils.getDateByDaysDiff(inspect.getInspectDate(), -1));
			}
			// 如果批复->进展中，需要添加[新巡查]提醒
			else if (prevStatus.equals("INSPECT_STATUS_1") && nextStatus.equals("INSPECT_STATUS_2") && inspect.getInspectDate() != null) {
				notify.setContent("项目" + project.getProjectName() + "的再次巡检将在" + (DateUtils.date2String(inspect.getInspectDate(), Constants.DATE_FORMAT)) + "进行");
				notifyService.create(notify);
				notifySubscriptionService.subscribe(notify, leaderUserId, DateUtils.getDateByDaysDiff(inspect.getInspectDate(), -1));
			}
			// 其它情况，如巡查日期变更、巡查人变更等，需要更新提醒
			else {
				
				// 如果巡查日期发生了变化，修改提醒的日期 // TODO
				if (prevInspect.getInspectDate().compareTo(inspect.getInspectDate()) != 0) {
					
				}
				// 如果巡查负责人发生了变化 // TODO
				else if (!prevInspect.getInspector().getUser().getId().equals(inspectorId)) {
					
				}
			}
			
		} catch (Exception e) {
			logger.error("项目巡检添加提醒消息失败", e);
		}
	}
	

	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete
	 *            待删除的ID，CSV
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@SysLogAnnotation(description = "项目巡查", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// ★ Project的文件夹对应的Archive的id，就是Project的id
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(KEY_ARCHIVE_IDS_TO_DELETE, idsToDelete);
		paramMap.put(KEY_NEED_DELETE_SELF_RECORD, true);

		return super.delete(idsToDelete, null, userBean, locale);
	}

	/**
	 * 管理平台提交审核
	 * @param bean
	 * @param uploadType
	 * @param userBean
	 * @param locale
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "项目巡查", action = "审核")
	public JsonResultBean auditSubmit(
			@ModelAttribute ProjectInspectBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		ProjectInspect entity = this.getDataService().find(bean.getId());
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PROJECT_INSPECT.value();

		// 向OA提交审核请求
		
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("inspectContent", entity.getInspectContent());
		reqMap.put("inspectType", entity.getInspectType().getOptionName());
		reqMap.put("inspectResult", entity.getInspectResult());
		reqMap.put("projectName", entity.getProject().getProjectName());
		reqMap.put("stationName", entity.getSite().getStationName());
		reqMap.put("inspectPlan", entity.getInspectPlan());
		reqMap.put("inspectArea", entity.getInspectArea());
		reqMap.put("inspectBasis", entity.getInspectBasis());

		reqMap.put("inspector", entity.getInspector().getId());
		reqMap.put("inspectResultContent", entity.getInspectResultContent());
		if (entity.getIsImprove() != null) {
			reqMap.put("isImprove", entity.getIsImprove() == 1
                ? messageSource.getMessage(OnOffEnum.ON.resourceName(), null, locale)
                : messageSource.getMessage(OnOffEnum.OFF.resourceName(), null, locale));
		} else {
			reqMap.put("isImprove", "");
		}
		reqMap.put("inspectDate", DateUtils.date2String(entity.getInspectDate(), Constants.DATE_FORMAT));
		reqMap.put("improveReqDate", DateUtils.date2String(entity.getImproveReqDate(), Constants.DATE_FORMAT));
		reqMap.put("inspectStatus", entity.getInspectStatus().getOptionName());
		reqMap.put("remark", entity.getRemark());
		reqMap.put("flowStartDate", DateUtils.date2String(entity.getFlowStartDate(), Constants.DATE_FORMAT));
		reqMap.put("flowEndDate", DateUtils.date2String(entity.getFlowEndDate(), Constants.DATE_FORMAT));
		reqMap.put("flowStatus", messageSource.getMessage(FlowStatusEnum.getResouceName(entity.getFlowStatus()), null, locale));
		Set<ProjectInspectAttachment> attchments = entity.getProjectInspectAttachments();
		String fileName = "";
		String filePath = "";
		List<HashMap<String, String>> archives = new ArrayList<>();
		if (attchments.size() > 0) {
			for (ProjectInspectAttachment attchment : attchments) {
				fileName = attchment.getArchive().getArchiveName();
				String rootFolderPath = this.systemConfigService
						.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

				filePath = rootFolderPath + attchment.getArchive().getRelativePath();
				File refereeFile = new File(filePath);
				byte[] refereeFileOriginalBytes = FileUtils.readFileToByteArray(refereeFile);
				byte[] refereeFileBase64Bytes = Base64.encodeBase64(refereeFileOriginalBytes);
				String UpFile = new String(refereeFileBase64Bytes, "UTF-8");
				HashMap<String, String> attMap = new HashMap<String, String>();
				attMap.put("attachmentFileName", fileName);
				attMap.put("attachmentFile", UpFile);

				archives.add(attMap);
			}

		}

		reqMap.put("archives", archives);

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();

		logger.info("[OA Audit] No.13 ProjectInspect");
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		// 如果请求审批成功更新表t_project 审批中
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.13 ProjectInspect Audit request success, history data id was {}", dataId);
			entity.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			entity.setFlowStartDate(new Date());
            projectInspectService.setData(entity);
		}

		// 提交成功需要将flowhistory action 修改为REVIEWING 审核中
		

		return jsonResult;
	}
}
