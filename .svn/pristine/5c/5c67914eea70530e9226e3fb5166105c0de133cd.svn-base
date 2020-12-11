package project.edge.web.controller.project;

import java.util.ArrayList;
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
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.ProjectPersonBeanConverter;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ProjectPersonBean;
import project.edge.domain.view.ProjectPersonChangeDetailBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectRoleService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 项目成员弹出框单Grid画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/project/project-member")
public class ProjectMemberController extends TreeGridUploadController<ProjectPerson, ProjectPersonBean> {

	private static final Logger logger = LoggerFactory.getLogger(ProjectMemberController.class);

	private static final String PID = "P2063";

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
		return DataTypeEnum.PROJECT_MEMBER.value();
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected boolean useFile() {
		return false;
	}

	protected OrderByDto getGridDefaultOrder() {
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

		// 获取数据
		List<VirtualOrg> virtualOrg = this.virtualOrgService.list(f, null);
		List<ComboboxOptionBean> virtualOrgList = new ArrayList<>();

		for (VirtualOrg entity : virtualOrg) {
			virtualOrgList.add(new ComboboxOptionBean(entity.getId(), entity.getVirtualOrgName()));
		}
		optionMap.put("virtualOrgOptions", virtualOrgList);
		return optionMap;
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/project/projectMemberJs.jsp";
	}

	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {

		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

		// 左侧树的加载和点击节点的事件处理
		jsMap.put(ControllerJsMapKeys.LINKED_FUNC, "PROJECT_MEMBER.projectTreeNodeCallback");
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
		// model.addAttribute(ControllerModelKeys.SUB_GRID_SELECT_HANDLER,
		// "PROJECT_PERSON_CHANGE.handleSubGridSelect");

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

	@Override
	protected Service<ProjectPerson, String> getDataService() {
		return this.projectPersonService;
	}

	@Override
	protected ViewConverter<ProjectPerson, ProjectPersonBean> getViewConverter() {
		return new ProjectPersonBeanConverter();
	}

}
