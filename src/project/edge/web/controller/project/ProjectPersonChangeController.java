package project.edge.web.controller.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
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
import project.edge.common.constant.ProjectPersonOnDutyEnum;
import project.edge.domain.converter.ProjectPersonChangeDetailBeanConverter;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.ProjectPersonChange;
import project.edge.domain.entity.ProjectPersonChangeDetail;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.view.ProjectPersonChangeDetailBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.project.ProjectPersonChangeDetailService;
import project.edge.service.project.ProjectPersonChangeService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectRoleService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;

/**
 * 项目成员变更画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/project/project-person")
public class ProjectPersonChangeController
		extends TreeDoubleGridUploadController<ProjectPersonChangeDetail, ProjectPersonChangeDetailBean> {

	private static final Logger logger = LoggerFactory.getLogger(ProjectPersonChangeController.class);

	private static final String PID = "P2061";
	
    private static final String LOCAL_AUDIT_SUBMIT_JS = "FlowableUtils.saveProcessInstanceFormData('#%1$s','%2$s','#%3$s');";
    private static final String OPEN_LOCAL_AUDIT_LOG_JS = "FlowableUtils.openAuditLogDialog('#%1$s','#%2$s')";
    private static final String OPEN_LOCAL_WITHDRAW_JS = "FlowableUtils.openWithdrawDialog('#%1$s','#%2$s')";
    
	@Resource
	private ProjectPersonChangeDetailService projectPersonChangeDetailService;

	@Resource
	private ProjectPersonChangeService projectPersonChangeService;

	@Resource
	private ProjectRoleService projectRoleService;

	@Resource
	private VirtualOrgService virtualOrgService;
	@Resource
	CreateWorkFlowManager createWorkFlowManager;
	@Resource
	private ProjectPersonService projectPersonService;
	@Autowired
	HttpServletRequest request;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.PROJECT_PERSON_CHANGE.value();
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected Service<ProjectPersonChangeDetail, String> getDataService() {
		return this.projectPersonChangeDetailService;
	}

	@Override
	protected ViewConverter<ProjectPersonChangeDetail, ProjectPersonChangeDetailBean> getViewConverter() {
		return new ProjectPersonChangeDetailBeanConverter();
	}

	@Override
	protected boolean useFile() {
		return false;
	}

	@Override
	public String getSubDataType() {
		return DataTypeEnum.PROJECT_PERSON.value();
	}

	@Override
	public String getGridTitle(Locale locale) {
		return this.messageSource.getMessage("ui.menu.item.project.person.change", null, locale);
		// return null;
	}

	@Override
	public String getSubGridTitle(Locale locale) {
		// return this.messageSource.getMessage("ui.menu.item.project.person",
		// null, locale);
		return null;
	}

	protected OrderByDto getGridDefaultOrder() {
		return new OrderByDto("person.personName");
	}

	@Override
	public OrderByDto getSubGridDefaultOrder() {
		return new OrderByDto("person.personName");
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

		// 设置过滤信息
		CommonFilter f = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());

		// 获取数据
		List<ProjectRole> rolelist = this.projectRoleService.list(f, null);
		List<ComboboxOptionBean> roleResultList = new ArrayList<>();

		for (ProjectRole entity : rolelist) {
			roleResultList.add(new ComboboxOptionBean(entity.getId(), entity.getProjectRoleName()));
		}
		optionMap.put("roleOptions", roleResultList);
		
		List<ComboboxOptionBean> onDutyOptions = new ArrayList<>();
        for (ProjectPersonOnDutyEnum s : ProjectPersonOnDutyEnum.values()) {
        	onDutyOptions.add(new ComboboxOptionBean(s.value().toString(),
                    this.messageSource.getMessage(s.resourceName(), null, locale)));
        }
        optionMap.put("onDutyOptions", onDutyOptions);
		return optionMap;
	}

	@Override
	public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {

		PageFilter pageFilter = new PageFilter();

		String pid = "P2060";
		pageFilter.addExact("pid", pid).addExact("isEnabled", OnOffEnum.ON.value()).addExact("isFunction",
				OnOffEnum.ON.value()); // SubGrid画面的、启用的功能[t_page]

		if (!StringUtils.isEmpty(userBean.getRoleId()) && !userBean.isAccessAllPages()) {
			pageFilter.setRoleId(userBean.getRoleId()); // 用户角色不为空，且该角色只能访问部分画面时，用role
														// id过滤

		}

		List<Page> pages = this.pageService.list(pageFilter, null); // 默认按照pageOrder字段排序
		ArrayList<FunctionBean> functions = new ArrayList<>();
		ArrayList<FunctionBean> searchFunctions = new ArrayList<>();

		this.prepareSubGridFunctions(pages, functions, searchFunctions, jsMap, locale);

		return functions;
	}

	@Override
	public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {

		PageFilter pageFilter = new PageFilter();

		String pid = "P2060";
		pageFilter.addExact("pid", pid).addExact("isEnabled", OnOffEnum.ON.value()).addExact("isFunction",
				OnOffEnum.ON.value()); // SubGrid画面的、启用的功能[t_page]

		if (!StringUtils.isEmpty(userBean.getRoleId()) && !userBean.isAccessAllPages()) {
			pageFilter.setRoleId(userBean.getRoleId()); // 用户角色不为空，且该角色只能访问部分画面时，用role
														// id过滤

		}

		List<Page> pages = this.pageService.list(pageFilter, null); // 默认按照pageOrder字段排序
		ArrayList<FunctionBean> functions = new ArrayList<>();
		ArrayList<FunctionBean> searchFunctions = new ArrayList<>();

		this.prepareSubGridFunctions(pages, functions, searchFunctions, jsMap, locale);

		return searchFunctions;
	}

	@Override
	public String getLinkedFilterFieldName() {
		// 使用了linked-func，不再需要此值
		return null;
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/project/projectPersonChangeJs.jsp";
	}

	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {

		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

		// 左侧树的加载和点击节点的事件处理
		jsMap.put(ControllerJsMapKeys.LINKED_FUNC, "PROJECT_PERSON_CHANGE.projectTreeNodeCallback");

		// MainGrid打开新建，弹出人员通用选择画面
		jsMap.put(ControllerJsMapKeys.OPEN_ADD,
				"PopupSelectUtils.openPersonDblGridSelect(" + this.getJsCallbackObjName() + ")");

		// SubGrid打开新建，弹出人员通用选择画面
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_ADD,
				"PopupSelectUtils.openPersonDblGridSelect(" + this.getJsCallbackObjName() + "_SUBGRID)");
		
        // 提交审核数据按钮
        jsMap.put(ControllerJsMapKeys.LOCAL_AUDIT_SUBMIT,
                String.format(LOCAL_AUDIT_SUBMIT_JS, idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.SUBMIT_AUDIT), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 打开审核日志对话框
        jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_AUDIT_LOG, String.format(OPEN_LOCAL_AUDIT_LOG_JS,
                idMap.get(ControllerIdMapKeys.AUDIT_LOG_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 打开撤回对话框
        jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_WITHDRAW, String.format(OPEN_LOCAL_WITHDRAW_JS,
                idMap.get(ControllerIdMapKeys.WITHDRAW_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
		return jsMap;
	}

	protected String getJsCallbackObjName() {
		return this.getDataType();
	}

	protected String getSubGridJsCallbackObjName() {
		return this.getJsCallbackObjName();
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

		String r = super.main(paramMap, model, userBean, locale);

		// 不使用分页
		model.addAttribute(ControllerModelKeys.IS_PAGE, false);
		model.addAttribute(ControllerModelKeys.IS_SUB_GRID_PAGE, false);

		// 覆盖默认的SubGrid选中事件处理，不再联动MainGrid
		//model.addAttribute(ControllerModelKeys.SUB_GRID_SELECT_HANDLER, "PROJECT_PERSON_CHANGE.handleSubGridSelect");

		// 两个Grid的双击行事件处理，增加回调对象
		model.addAttribute(ControllerModelKeys.DBL_CLICK_ROW_HANDLER, "PROJECT_PERSON_CHANGE.handleDblClickRow");
		model.addAttribute(ControllerModelKeys.SUB_GRID_DBL_CLICK_ROW_HANDLER,
				"PROJECT_PERSON_CHANGE.handleDblClickRow");

		// SubGrid多选
		model.addAttribute(ControllerModelKeys.IS_SUB_GRID_SINGLE_SELECT, false);

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

		return super.list(commonFilterJson, null, page, rows, sort, order, locale);
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

		JsonResultBean jsonResult = super.find(id, locale);
		try {
			ProjectPersonChangeDetailBean bean = (ProjectPersonChangeDetailBean) jsonResult.getDataMap()
					.get(JsonResultBean.KEY_RETURN_OBJECT);

			bean.setPerson_(bean.getPersonText());

			// 获取下拉框的option，以便修改弹出画面上设置

			// 设置过滤信息
			CommonFilter filter = new CommonFilter().addExact("project.id", bean.getProject_());

			// 获取数据
			List<VirtualOrg> list = this.virtualOrgService.list(filter, null);
			List<ComboboxOptionBean> resultList = new ArrayList<>();

			for (VirtualOrg entity : list) {
				resultList.add(new ComboboxOptionBean(entity.getId(), entity.getVirtualOrgName()));
			}
			jsonResult.getDataMap().put("vorgs", resultList);

			// 设置过滤信息
			CommonFilter f = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());

			// 获取数据
			List<ProjectRole> rolelist = this.projectRoleService.list(f, null);
			List<ComboboxOptionBean> roleResultList = new ArrayList<>();

			for (ProjectRole entity : rolelist) {
				roleResultList.add(new ComboboxOptionBean(entity.getId(), entity.getProjectRoleName()));
			}
			jsonResult.getDataMap().put("roles", roleResultList);

		} catch (Exception e) {
			this.getLogger().error("Exception finding the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
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
	@ResponseBody
	@SysLogAnnotation(description = "项目成员变更", action = "新增")
	public JsonResultBean create(@ModelAttribute ProjectPersonChangeDetailBean bean,
			@RequestParam(required = true) List<String> personList,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			CommonFilter filter = new CommonFilter().addWithin("person.id", personList).addExact("project.id",
					bean.getProject_());
			List<ProjectPersonChangeDetail> dbList = this.projectPersonChangeDetailService.list(filter, null);
			Map<String, ProjectPersonChangeDetail> map = new HashMap<>(); // key:
																			// person_id
			for (ProjectPersonChangeDetail dbItem : dbList) {
				map.put(dbItem.getPerson().getId(), dbItem);
			}

			// 数据库是否已存在记录
			boolean hasEntity = false;

			Date now = new Date();
			List<ProjectPersonChangeDetail> listToCreate = new ArrayList<>();
			List<ProjectPersonChange> plistToCreate = new ArrayList<>();
			for (String person : personList) {
				if (map.containsKey(person)) {
					hasEntity = true;
					continue;
				}

				bean.setPerson_(person);
				ProjectPersonChangeDetail entity = this.getViewConverter().toEntity(bean, null, userBean, now);

				listToCreate.add(entity);
				plistToCreate.add(entity.getProjectPersonChange());
			}

			this.projectPersonChangeService.batchCreate(plistToCreate);
			this.projectPersonChangeDetailService.batchCreate(listToCreate);

			String msg = this.messageSource.getMessage("message.info.record.add.ok", null, locale);
			if (hasEntity) {
				msg += this.messageSource.getMessage("message.info.skip.same.record", null, locale);
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(msg);

		} catch (Exception e) {
			this.getLogger().error("Exception creating the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}
		return jsonResult;
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
	@RequestMapping("/edit")
	@ResponseBody
	@SysLogAnnotation(description = "项目成员变更", action = "更新")
	public JsonResultBean update(@ModelAttribute ProjectPersonChangeDetailBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.update(bean, null, userBean, locale);
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
	@SysLogAnnotation(description = "项目成员变更", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}

	/**
	 * OA审批 [No.4 项目成员变更]
	 * @param userBean
	 * @param id
	 * @param locale
	 * @return
	 */
	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "项目成员变更", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		ProjectPersonChangeDetail entity = projectPersonChangeDetailService.find(id);
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}

		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PROJECT_PERSON_CHANGE.value();

		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("projectName", entity.getProject().getProjectName());
		if (entity.getPerson() != null) {
			reqMap.put("personName", entity.getPerson().getUser().getId());
		} else {
			reqMap.put("personName", "");
		}
		if (entity.getVirtualOrg() != null) {
			reqMap.put("shortName", entity.getVirtualOrg().getVirtualOrgName());
		} else {
			reqMap.put("shortName", "");
		}
		if (entity.getProjectRole() != null) {
			reqMap.put("projectRoleName", entity.getProjectRole().getProjectRoleName());
		} else {
			reqMap.put("projectRoleName", "");
		}
		reqMap.put("remark", entity.getRemark());

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();

		logger.info("[OA Audit] No.4 ProjectPersonChange");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.4 ProjectPersonChange Audit request success, data id was {}", dataId);
			ProjectPersonChange personChangeEntity = projectPersonChangeService.find(entity.getProjectPersonChange().getId());
			personChangeEntity.setFlowStartDate(new Date());
			personChangeEntity.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			personChangeEntity.setmDatetime(new Date());
			personChangeEntity.setModifier(userName);
			this.projectPersonChangeService.setData(personChangeEntity);
			
			entity.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			projectPersonChangeDetailService.update(entity);
		}

		return jsonResult;
	}

}
