package project.edge.web.controller.common;

import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FieldValueType;
import garage.origin.common.constant.FilterSearchBy;
import garage.origin.common.constant.JsonIcon;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.SimpleExcelDump;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.business.UpdateFieldInfoDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.entity.DataFields;
import project.edge.domain.entity.Page;
import project.edge.domain.filter.PageFilter;
import garage.origin.domain.view.SessionUserBean;
import project.edge.service.auth.PageService;
import project.edge.service.system.DataFieldsService;
import project.edge.service.system.SystemConfigService;

/**
 * 共通的Controller，只包含单个datagrid及filter。根据[t_data_fields]中获取的字段的信息，生成View需要的数据。用来生成一览画面。
 * 
 * @param <T> 数据层实体
 * @param <V> 表现层实体
 */
public abstract class SingleGridController<T, V extends ViewBean> {

	protected static final String SINGLE_GRID_VIEW_NAME = "common/page/singleGridPage"; // 默认的JSP

	protected static final String AUDIT_VIEW_NAME = "common/page/auditViewPage"; // 默认的审核JSP
	protected static final String SHOW_AUDIT_VIEW_NAME = "common/page/showAuditViewPage"; // 默认的显示审核JSP

	// private static final int SIMPLE_DIALOG_WIDTH = 320;
	private static final int SIMPLE_DIALOG_MAX_HEIGHT = 480;
	protected static final int GROUPED_DIALOG_WIDTH = 950;
	private static final int GROUPED_DIALOG_MAX_HEIGHT = 560;

	@Resource
	protected MessageSource messageSource;

	@Autowired
	protected ServletContext context;

	@Autowired
	protected ResourceLoader resourceLoader;

	@Resource
	protected PageService pageService;

	@Resource
	protected DataFieldsService dataFieldsService;

	@Resource
	protected SystemConfigService systemConfigService;

	/**
	 * 获得子类的logger。
	 * 
	 * @return
	 */
	protected abstract Logger getLogger();

	/**
	 * 获取Controller管理的数据类型，参考[t_data_config].data_type。
	 * 
	 * 画面Open的入口方法将根据数据类型从[t_data_fields]中获取相关的记录。
	 * 
	 * @return 数据类型
	 */
	public abstract String getDataType();

	/**
	 * 获取数据类型对应的Service。
	 * 
	 * @return Service对象
	 */
	protected abstract Service<T, String> getDataService();

	/**
	 * 获取Controller对应的画面id，参考[t_page].id
	 * 
	 * 画面Open的入口方法将根据画面id从[t_page]中获取功能对应的记录。
	 * 
	 * @return 画面id
	 */
	protected abstract String getPageId();

	/**
	 * 取得数据库实体与ViewBean的转换器。
	 * 
	 * @return
	 */
	protected abstract ViewConverter<T, V> getViewConverter();

	/**
	 * 返回用以检查唯一性的CommonFilter，新建时使用。
	 * 
	 * @param bean 表现层实体
	 * @return CommonFilter对象
	 */
	protected List<CommonFilter> getUniqueFilter(V bean) {
		return new ArrayList<>();
	}

	/**
	 * 返回用以检查唯一性的CommonFilter，修改时使用，如果无需检查唯一性，则返回空列表。
	 * 
	 * @param bean 表现层实体
	 * @return CommonFilter对象，如果无需检查唯一性，则返回null
	 */
	protected List<CommonFilter> getUniqueFilter(V bean, T oldEntity) {
		return new ArrayList<>();
	}

	/**
	 * 获取画面Open的View名。提供默认的实现，如果子类不适用共通的JSP，则必须重写此方法。
	 * 
	 * @return
	 */
	protected String getMainView(Map<String, String> paramMap) {
		return SINGLE_GRID_VIEW_NAME;
	}

	/**
	 * 共通JS方法中，callback对象名。如调用CrudUtils.openAddFormDialog时传入的，每个画面必须各不相同，通常是大写的数据类型，例如，立项画面是PROJECT_INIT。
	 * 
	 * @return
	 */
	protected String getJsCallbackObjName() {
		return null;
	}

	/**
	 * 画面使用<jsp:include/>嵌入javascript内容时，指定使用的JSP文件路径。子类JSP的第1个扩展点，必要时，子类应重写此方法。
	 * 
	 * @return
	 */
	protected String getJavascriptJspPath() {
		return null;
	}

	/**
	 * 画面使用<jsp:include/>嵌入隐藏区内容(弹出画面)时，指定使用的JSP文件路径。子类JSP的第2个扩展点，必要时，子类应重写此方法。
	 * 
	 * @return 返回null表示不需要增加额外的<jsp:include/>
	 */
	protected String getHiddenContentJspPath() {
		return null;
	}

	/**
	 * 在main(...)方法中，当字段从[t_data_fields]加载并转换为FieldBean对象之后调用。必要时，子类应重写此方法，如PLU的价格要
	 * 根据系统设置来确定精度，而不是来自[t_data_fields]的设定。
	 * 
	 * @param field
	 * @param paramMap
	 * @param model
	 * @param userBean
	 */
	protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
		return;
	}

	/**
	 * 新建修改使用弹出对话框时，是否包含文件上传。如果新建修改是弹出另外的画面，则不使用此方法。
	 *
	 * @return
	 */
	protected boolean useFile() {
		return false;
	}

	/**
	 * 根据[t_page]实体，创建功能按钮Bean，并加入到相应列表。如果子类需要支持额外的功能按钮，可重写此方法，重写时需要先调用父类此方法。
	 * 
	 * @param p               [t_page]实体
	 * @param functions       存放在功能栏的按钮的列表
	 * @param searchFunctions 存放在检索栏的按钮的列表
	 * @param funcGroupMap    用来辅助判断是否要加按钮的分隔
	 * @param jsMap           JavaScript Map
	 * @param locale
	 */
	protected void createFunctionBean(Page p, List<FunctionBean> functions, List<FunctionBean> searchFunctions,
			Map<String, String> funcGroupMap, Map<String, String> jsMap, Locale locale) {
		return;
	}

	/**
	 * 新建修改时，检查是否有相应的权限。必要时需要重写此方法。
	 * 
	 * @return
	 */
	protected boolean isUserHasAuthority(SessionUserBean userBean, V bean) {
		return true;
	}

	/**
	 * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
	 * 
	 * @param locale
	 * @return key:[v_data_option].option_source，value:[v_data_option]
	 */
	protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
		return null;
	}

	/**
	 * CRUD对应的URL是按照规则生成的，通过模板构建相关操作的URL，用来在画面上使用。使用本方法获取CRUD对应的URL Map。子类应重写此方法。
	 * 
	 * @return URL Map key由ControllerUrlMapKeys定义
	 */
	protected Map<String, String> prepareUrlMap() {

		Page p = this.pageService.find(this.getPageId());
		if (p == null || StringUtils.isEmpty(p.getUrl())) {
			return new HashMap<>();
		}

		String url = p.getUrl();
		String contextPath = this.context.getContextPath();
		return ControllerMapUtils.buildUrlMap(contextPath,
				contextPath + url.substring(0, url.lastIndexOf(Constants.SLASH) + 1), this.getPageId());
	}

	/**
	 * 创建画面使用的HTML元素的id的Map。子类应重写此方法。
	 * 
	 * @return id Map key由ControllerIdMapKeys定义
	 */
	protected Map<String, String> prepareIdMap() {
		return ControllerMapUtils.buildIdMap(this.getPageId(), this.getDataType());
	}

	/**
	 * 创建画面使用的JavaScript函数的Map。子类应重写此方法。
	 * 
	 * @param idMap
	 * @param urlMap
	 * @return id Map key由ControllerJsMapKeys定义
	 */
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {
		return ControllerMapUtils.buildJsMap(idMap, urlMap, this.useFile(), this.getJsCallbackObjName());
	}

	/**
	 * 返回一览的DataGrid的默认排序。
	 * 
	 * @return
	 */
	protected OrderByDto getGridDefaultOrder() {
		return this.getDataService().getDefaultOrder();
	}

	/**
	 * 获取当前系统配置审批流程（内部审批/外部审批）
	 * 
	 * @return
	 */
	protected boolean isOaFlow() {
		String isOaFlow = this.systemConfigService.getStringConfig(SystemConfigKeys.IS_OA_FLOW);
		if (String.valueOf(OnOffEnum.ON.value()).equals(isOaFlow)) {
			return true;
		}
		return false;
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
	public String main(@RequestParam Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 1. 一览的默认排序
		OrderByDto defaultOrder = this.getGridDefaultOrder();
		model.addAttribute(ControllerModelKeys.DEFAULT_ORDER, defaultOrder);

		// 2. CRUD URL Map/id Map/javascript Map
		Map<String, String> idMap = this.prepareIdMap();
		model.addAttribute(ControllerModelKeys.ID_MAP, idMap);

		Map<String, String> urlMap = this.prepareUrlMap();

		model.addAttribute(ControllerModelKeys.URL_MAP, urlMap);

		Map<String, String> jsMap = this.prepareJsMap(idMap, urlMap);
		model.addAttribute(ControllerModelKeys.JS_MAP, jsMap);

		// 3. 下拉列表项
		Map<String, List<ComboboxOptionBean>> optionMap = this.prepareOptionMap(locale);
		if (optionMap != null) {
			model.addAttribute(ControllerModelKeys.OPTION_MAP, optionMap);
		}

		// 4. 功能栏的各个按钮及其对应的js事件处理函数在Controller内统一指定
		PageFilter pageFilter = new PageFilter();

		String pid = this.getPageId();
		pageFilter.addExact("pid", pid).addExact("isEnabled", OnOffEnum.ON.value()).addExact("isFunction",
				OnOffEnum.ON.value()); // 当前画面的、启用的功能[t_page]

		if (!StringUtils.isEmpty(userBean.getRoleId()) && !userBean.isAccessAllPages()) {
			pageFilter.setRoleId(userBean.getRoleId()); // 用户角色不为空，且该角色只能访问部分画面时，用role id过滤

		}

		List<Page> pages = this.pageService.list(pageFilter, null); // 默认按照pageOrder字段排序
		ArrayList<FunctionBean> functions = new ArrayList<>();
		ArrayList<FunctionBean> searchFunctions = new ArrayList<>();

		boolean canEditData = this.prepareFunctions(pages, functions, searchFunctions, jsMap, locale);

		model.addAttribute(ControllerModelKeys.FUNCTIONS, functions);
		model.addAttribute(ControllerModelKeys.SEARCH_FUNCTIONS, searchFunctions);
		model.addAttribute(ControllerModelKeys.PAGES, pages);

		model.addAttribute(ControllerModelKeys.CAN_EDIT_DATA, canEditData);
		model.addAttribute(ControllerModelKeys.IS_VIEW_HASGRID, false);

		boolean isHasFlowable = this.prepareIsHasFlowable(pid);

		model.addAttribute(ControllerModelKeys.IS_HAS_FLOWABLE, isHasFlowable);

		// 5.
		// 获取画面所需的各个数据字段；并计算各弹出画面的高度和宽度，高度只用maxHeight，让弹出画面根据实际情况自适应，宽度则根据字段Label的最大值做调整
		CommonFilter dataFieldsFilter = new CommonFilter().addExact("dataType", this.getDataType())
				.addExact("pageId", this.getPageId()).addExact("isEnabled", OnOffEnum.ON.value()); // 根据数据类型获取全局可见的字段

		boolean isAdvSearchFieldsEmpty = this.processFields(dataFieldsFilter, false, paramMap, model, userBean, locale);

		// 修正高级检索按钮，在没有高级检索字段时，不显示该按钮
		if (isAdvSearchFieldsEmpty && searchFunctions.size() == 3) {
			searchFunctions.remove(2);
		}

		// 6. 使用<jsp:include/>嵌入javascript内容时，使用的JSP文件路径，子类JSP扩展点1
		String jsJspPath = this.getJavascriptJspPath();
		if (!StringUtils.isEmpty(jsJspPath)) {
			model.addAttribute(ControllerModelKeys.INCLUDE_JSP_JS_PATH, jsJspPath);
		}

		// 7. 使用<jsp:include/>嵌入的隐藏区JSP路径，用来实现子类特有的弹出画面功能，子类JSP扩展点2
		String hiddenContentJspPath = this.getHiddenContentJspPath();
		if (!StringUtils.isEmpty(hiddenContentJspPath)) {
			model.addAttribute(ControllerModelKeys.INCLUDE_JSP_HIDDEN_CONTENT_PATH, hiddenContentJspPath);
		}

		// 8.扩展审核页面与查看审核
		if (paramMap != null) {

			model.addAttribute(ControllerModelKeys.FLOWABLE_AUDIT_TASK_ID, paramMap.get("auditTaskId"));
			model.addAttribute(ControllerModelKeys.FLOWABLE_CORRELATE_DATA_ID, paramMap.get("correlateDataId"));
			model.addAttribute(ControllerModelKeys.FLOWABLE_BUSINESS_ENTITY, paramMap.get("businessEntity"));

			if (!StringUtils.isEmpty(paramMap.get("isFromFlowAudit"))) {

				if (!StringUtils.isEmpty(paramMap.get("isViewAudit"))
						&& OnOffEnum.ON.value().equals(Short.valueOf(paramMap.get("isViewAudit")))) {
					return this.getMainShowAuditView(paramMap);
				} else {
					return this.getMainAuditView(paramMap);
				}
			}
		}

		// 9. 返回View Name
		return this.getMainView(paramMap);
	}

	protected String getMainAuditView(Map<String, String> paramMap) {
		return AUDIT_VIEW_NAME;
	}

	protected String getMainShowAuditView(Map<String, String> paramMap) {
		return SHOW_AUDIT_VIEW_NAME;
	}

	private boolean prepareIsHasFlowable(String pid) {

		if (StringUtils.isEmpty(pid)) {
			return false;
		}

		Page page = this.pageService.find(pid);

		if (page != null) {
			if (OnOffEnum.ON.value().equals(page.getIsHasFlowable())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 准备功能按钮。
	 * 
	 * @param pages
	 * @param functions
	 * @param searchFunctions
	 * @param jsMap
	 * @param locale
	 * @return 是否有权限双击datagrid弹出修改画面
	 */
	protected boolean prepareFunctions(List<Page> pages, ArrayList<FunctionBean> functions,
			ArrayList<FunctionBean> searchFunctions, Map<String, String> jsMap, Locale locale) {

		Map<String, String> funcGroupMap = new HashMap<String, String>(); // 用来控制按钮的分隔的添加
		String crudFuncGroup = "crudFuncGroup";
		String importExportFuncGroup = "importExportFuncGroup";
		String editFieldsFuncGroup = "editFieldsFuncGroup";
		String printFuncGroup = "printFuncGroup";
		String backupRestoreFuncGroup = "backupRestoreFuncGroup";
		String syncSaveFuncGroup = "syncSaveFuncGroup";
		String uploadDownloadFuncGroup = "uploadDownloadFuncGroup";
		String compareFuncGroup = "compareFuncGroup";
		String authFuncGroup = "authFuncGroup";
		String improveVerifyFuncGroup = "improveVerifyFuncGroup";
		String taskFuncGroup = "taskFuncGroup";
		String auditFuncGroup = "auditFuncGroup";
		String concernFuncGroup = "concernFuncGroup";
		String orderFuncGroup = "orderFuncGroup";

		boolean canEditData = false;
		for (Page p : pages) {

			String pageId = p.getId();

			if (pageId.endsWith(ControllerFunctionIds.ADD)) { // 新建

				this.parseFunction(p, functions, funcGroupMap, crudFuncGroup, jsMap.get(ControllerJsMapKeys.OPEN_ADD),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.DELETE)) { // 删除

				this.parseFunction(p, functions, funcGroupMap, crudFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_DELETE), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.MODIFY)) { // 修改

				this.parseFunction(p, functions, funcGroupMap, crudFuncGroup, jsMap.get(ControllerJsMapKeys.OPEN_EDIT),
						locale);
				canEditData = true;

			} else if (pageId.endsWith(ControllerFunctionIds.VIEW)) { // 查看

				this.parseFunction(p, functions, funcGroupMap, crudFuncGroup, jsMap.get(ControllerJsMapKeys.OPEN_VIEW),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.IMPORT)) { // 导入

				this.parseFunction(p, functions, funcGroupMap, importExportFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_IMPORT), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.EXPORT)) { // 导出

				this.parseFunction(p, functions, funcGroupMap, importExportFuncGroup,
						jsMap.get(ControllerJsMapKeys.EXPORT_SUBMIT), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.EDIT_FIELDS)) { // 字段编辑

				this.parseFunction(p, functions, funcGroupMap, editFieldsFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_EDIT_FIELDS), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.SEARCH)) { // 检索

				this.parseFunction(p, searchFunctions, null, null, jsMap.get(ControllerJsMapKeys.SEARCH), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.CLEAR_SEARCH)) { // 清空检索

				this.parseFunction(p, searchFunctions, null, null, jsMap.get(ControllerJsMapKeys.CLEAR_SEARCH), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.SEARCH_MORE)) { // 高级检索

				this.parseFunction(p, searchFunctions, null, null, jsMap.get(ControllerJsMapKeys.OPEN_ADV_SEARCH),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.PRINT)) { // 打印

				this.parseFunction(p, functions, funcGroupMap, printFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_PRINT), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.BACKUP)) { // 备份

				this.parseFunction(p, functions, funcGroupMap, backupRestoreFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_BACKUP), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.RESTORE)) { // 恢复

				this.parseFunction(p, functions, funcGroupMap, backupRestoreFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_RESTORE), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.SYNC)) { // 同步

				this.parseFunction(p, functions, funcGroupMap, syncSaveFuncGroup, jsMap.get(ControllerJsMapKeys.SYNC),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.SAVE)) { // 保存

				this.parseFunction(p, functions, funcGroupMap, syncSaveFuncGroup, jsMap.get(ControllerJsMapKeys.SAVE),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.UPLOAD)) { // 上传

				this.parseFunction(p, functions, funcGroupMap, uploadDownloadFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_UPLOAD), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.DOWNLOAD)) { // 下载

				this.parseFunction(p, functions, funcGroupMap, uploadDownloadFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_DOWNLOAD), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.COMPARE)) { // 比较

				this.parseFunction(p, functions, funcGroupMap, compareFuncGroup, jsMap.get(ControllerJsMapKeys.COMPARE),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.ROLE_SETTING)) { // 角色设置

				this.parseFunction(p, functions, funcGroupMap, authFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_ROLE_SETTING), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.AUTHORITY)) { // 分配权限

				this.parseFunction(p, functions, funcGroupMap, authFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_AUTHORITY), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.EDIT_EXPERTREVIEW)) { // 专家评审结果

				this.parseFunction(p, functions, funcGroupMap, "", jsMap.get(ControllerJsMapKeys.OPEN_EXPERTREVIEW),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.IMPROVE)) { // 整改

				this.parseFunction(p, functions, funcGroupMap, improveVerifyFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_IMPROVE), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.VERIFY)) { // 验证

				this.parseFunction(p, functions, funcGroupMap, improveVerifyFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_VERIFY), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.MODIFY_TASK)) { // 编制任务

				this.parseFunction(p, functions, funcGroupMap, taskFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_MODIFY_TASK), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.REGENERATE)) { // 重新生成
				
				this.parseFunction(p, functions, funcGroupMap, taskFuncGroup, 
						jsMap.get(ControllerJsMapKeys.REGENERATE_PLAN), locale);
				
			} else if (pageId.endsWith(ControllerFunctionIds.VIEW_TASK)) { // 查看任务

				this.parseFunction(p, functions, funcGroupMap, taskFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_VIEW_TASK), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.MERGE_SCHEDULE)) { // 合并计划

				this.parseFunction(p, functions, funcGroupMap, taskFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_MERGE_SCHEDULE), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.VIEW_OVERALL_TASK)) { // 查看总体任务

				this.parseFunction(p, functions, funcGroupMap, taskFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_VIEW_OVERALL_TASK), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.VIEW_PROGRESS_WARNING)) { // 查看进度预警

				this.parseFunction(p, functions, funcGroupMap, taskFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_PROGRESS_WARNING), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.SUBMIT_AUDIT)) { // 提交审核

				if (isOaFlow()) {
					this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
							jsMap.get(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT), locale);
				} else {
					this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
							jsMap.get(ControllerJsMapKeys.OPEN_LOCAL_SUBMIT_AUDIT), locale);
				}

			} else if (pageId.endsWith(ControllerFunctionIds.WITHDRAW)) { // 撤回

				if (isOaFlow()) {
					// OA审批不支持撤销
					//this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
					//        jsMap.get(ControllerJsMapKeys.OPEN_WITHDRAW), locale);
				} else {
					this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_LOCAL_WITHDRAW), locale);
				}
				
			} else if (pageId.endsWith(ControllerFunctionIds.AUDIT_LOG)) { // 审核记录
				
				if (isOaFlow()) {
					this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
							jsMap.get(ControllerJsMapKeys.OPEN_AUDIT_LOG), locale);
				} else {
					this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
							jsMap.get(ControllerJsMapKeys.OPEN_LOCAL_AUDIT_LOG), locale);
				}
				
			} else if (pageId.endsWith(ControllerFunctionIds.FLOW_PERMISSION)) { // 流程权限

				this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_FLOW_PERMISSION), locale);
			} else if (pageId.endsWith(ControllerFunctionIds.FLOW_ROLE_PERMISSION)) { // 流程角色权限

				this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_FLOW_ROLE_PERMISSION), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.FLOW_AUDIT)) { // 流程审核

				this.parseFunction(p, functions, funcGroupMap, auditFuncGroup,
						jsMap.get(ControllerJsMapKeys.OPEN_FLOW_AUDIT), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.CONCERN)) {

				this.parseFunction(p, functions, funcGroupMap, concernFuncGroup, jsMap.get(ControllerJsMapKeys.CONCERN),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.NO_CONCERN)) {

				this.parseFunction(p, functions, funcGroupMap, concernFuncGroup,
						jsMap.get(ControllerJsMapKeys.NO_CONCERN), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.LEND_TAKE)) { // 借阅领取

				this.parseFunction(p, functions, funcGroupMap, crudFuncGroup, jsMap.get(ControllerJsMapKeys.LEND_TAKE),
						locale);

			} else if (pageId.endsWith(ControllerFunctionIds.LEND_RETURN)) {// 借阅归还

				this.parseFunction(p, functions, funcGroupMap, crudFuncGroup,
						jsMap.get(ControllerJsMapKeys.LEND_RETURN), locale);

			} else if (pageId.endsWith(ControllerFunctionIds.ADD_PURCHASE)) {

				this.parseFunction(p, functions, funcGroupMap, crudFuncGroup,
						jsMap.get(ControllerJsMapKeys.ADD_PURCHASE), locale);
			} else if (pageId.endsWith(ControllerFunctionIds.ORDER_LIST)) {//查看采购记录

				this.parseFunction(p, functions, funcGroupMap, orderFuncGroup, jsMap.get(ControllerJsMapKeys.ORDER_LIST),
						locale);

			} else {
				this.createFunctionBean(p, functions, searchFunctions, funcGroupMap, jsMap, locale); // 供子类扩展
			}
		}
		if (!functions.isEmpty()) {
			functions.add(new FunctionBean(true));
		}
		return canEditData;
	}

	protected void parseFunction(Page p, List<FunctionBean> targetList, Map<String, String> funcGroupMap,
			String funcGroup, String handler, Locale locale) {

		if (funcGroupMap != null && !funcGroupMap.containsKey(funcGroup)) {
			targetList.add(new FunctionBean(true)); // 分隔
			funcGroupMap.put(funcGroup, funcGroup);
		}

		FunctionBean f = new FunctionBean(p, this.messageSource, locale);
		f.setHandler(handler);
		targetList.add(f);
	}

	/**
	 * 从数据库中读取[t_data_fields]，将其划分到画面各个地方所需，并计算自适应弹出画面的大小。
	 * 
	 * @param dataFieldsFilter
	 * @param isSubGrid
	 * @param paramMap
	 * @param model
	 * @param userBean
	 * @param locale
	 * @return 高级检索字段列表是否为空
	 */
	protected boolean processFields(CommonFilter dataFieldsFilter, boolean isSubGrid, Map<String, String> paramMap,
			Model model, SessionUserBean userBean, Locale locale) {

		// 获取画面所需的各个数据字段
		List<DataFields> fields = this.dataFieldsService.list(dataFieldsFilter, null); // 默认按fieldOrder字段排序

		ArrayList<FieldBean> searchFields = new ArrayList<>(); // 检索的字段
		ArrayList<FieldBean> advSearchFields = new ArrayList<>(); // 高级检索的字段
		ArrayList<FieldBean> listFrozenFields = new ArrayList<>(); // 一览中固定列的字段
		ArrayList<FieldBean> listFields = new ArrayList<>(); // 一览的字段
		ArrayList<FieldBean> addFields = new ArrayList<>(); // 新建弹出画面的字段
		ArrayList<FieldBean> editFields = new ArrayList<>(); // 修改弹出画面的字段
		ArrayList<FieldBean> viewFields = new ArrayList<>(); // 查看弹出画面的字段
		ArrayList<FieldBean> batchEditFields = new ArrayList<>(); // 批量修改弹出画面的字段
		ArrayList<FieldBean> fieldEditFields = new ArrayList<>(); // 字段编辑画面

		boolean hasCopyReserve = false;
		boolean isFieldGrouped = false;

		int addLabelMaxLength = 0;
		int addGroupCount = 0;
		int editLabelMaxLength = 0;
		int editGroupCount = 0;
		int viewLabelMaxLength = 0;
		int viewGroupCount = 0;
		int advSearchLabelMaxLength = 0;
		int advSearchGroupCount = 0;

		String lastFieldGroupAdd = null;
		String lastFieldGroupEdit = null;
		String lastFieldGroupView = null;
		String lastFieldGroupBatchEdit = null;
		String lastFieldGroupAdvSearch = null;
		String lastFieldGroupFieldEdit = null;
		for (DataFields f : fields) {

			// 转换
			FieldBean fieldBean = new FieldBean(f, this.messageSource, locale);
			this.postFieldBeanInit(fieldBean, paramMap, model, userBean);

			String fieldGroup = f.getFieldGroup();
			if (!StringUtils.isEmpty(fieldGroup)) {
				isFieldGrouped = true;
			}

			if (fieldBean.isCustomizable()) { // 字段编辑

				// 判断是否字段组
				if (StringUtils.isEmpty(fieldGroup)) {
					if (!StringUtils.isEmpty(lastFieldGroupFieldEdit)) {
						fieldEditFields.add(new FieldBean(true));
					}
				} else {
					if (!fieldGroup.equals(lastFieldGroupFieldEdit)) {
						fieldEditFields.add(new FieldBean(true));
					}
				}
				lastFieldGroupFieldEdit = fieldGroup;
				fieldEditFields.add(fieldBean);
			}

			if (OnOffEnum.OFF.value().equals(f.getIsCommonVisible())) {
				continue;
			}

			if (fieldBean.isCopyReserve()) {
				hasCopyReserve = true;
			}

			// 归类
			if (fieldBean.isSimpleSearch()) { // 检索字段
				searchFields.add(fieldBean);
			}

			if (fieldBean.isListVisible()) { // 一览字段
				if (fieldBean.isFrozonColumn()) {
					listFrozenFields.add(fieldBean);
				} else {
					listFields.add(fieldBean);
				}
			}

			if (fieldBean.isAddVisible()) { // 新增字段

				// 判断是否字段组
				if (StringUtils.isEmpty(fieldGroup)) {
					if (!StringUtils.isEmpty(lastFieldGroupAdd)) {
						addFields.add(new FieldBean(true));
						addGroupCount++;
					}
				} else {
					if (!fieldGroup.equals(lastFieldGroupAdd)) {
						addFields.add(new FieldBean(true));
						addGroupCount++;
					}
				}
				lastFieldGroupAdd = fieldGroup;

				if (addLabelMaxLength < fieldBean.getCaptionName().length()) {
					addLabelMaxLength = fieldBean.getCaptionName().length();
				}
				addFields.add(fieldBean);
			}

			if (fieldBean.isEditVisible()) { // 修改字段

				// 判断是否字段组
				if (StringUtils.isEmpty(fieldGroup)) {
					if (!StringUtils.isEmpty(lastFieldGroupEdit)) {
						editFields.add(new FieldBean(true));
						editGroupCount++;
					}
				} else {
					if (!fieldGroup.equals(lastFieldGroupEdit)) {
						editFields.add(new FieldBean(true));
						editGroupCount++;
					}
				}
				lastFieldGroupEdit = fieldGroup;

				if (editLabelMaxLength < fieldBean.getCaptionName().length()) {
					editLabelMaxLength = fieldBean.getCaptionName().length();
				}
				editFields.add(fieldBean);
			}

			if (fieldBean.isAddVisible() || fieldBean.isEditVisible()) { // 查看字段

				// 判断是否字段组
				if (StringUtils.isEmpty(fieldGroup)) {
					if (!StringUtils.isEmpty(lastFieldGroupView)) {
						viewFields.add(new FieldBean(true));
						viewGroupCount++;
					}
				} else {
					if (!fieldGroup.equals(lastFieldGroupView)) {
						viewFields.add(new FieldBean(true));
						viewGroupCount++;
					}
				}
				lastFieldGroupView = fieldGroup;

				if (viewLabelMaxLength < fieldBean.getCaptionName().length()) {
					viewLabelMaxLength = fieldBean.getCaptionName().length();
				}
				viewFields.add(fieldBean);
			}

			if (fieldBean.isBatchEditable()) { // 批量修改字段

				// 判断是否字段组
				if (StringUtils.isEmpty(fieldGroup)) {
					if (!StringUtils.isEmpty(lastFieldGroupBatchEdit)) {
						batchEditFields.add(new FieldBean(true));
					}
				} else {
					if (!fieldGroup.equals(lastFieldGroupBatchEdit)) {
						batchEditFields.add(new FieldBean(true));
					}
				}
				lastFieldGroupBatchEdit = fieldGroup;

				batchEditFields.add(fieldBean);
			}

			if (fieldBean.isAdvSearch()) { // 高级检索字段

				// 判断是否字段组
				if (StringUtils.isEmpty(fieldGroup)) {
					if (!StringUtils.isEmpty(lastFieldGroupAdvSearch)) {
						advSearchFields.add(new FieldBean(true));
						advSearchGroupCount++;
					}
				} else {
					if (!fieldGroup.equals(lastFieldGroupAdvSearch)) {
						advSearchFields.add(new FieldBean(true));
						advSearchGroupCount++;
					}
				}
				lastFieldGroupAdvSearch = fieldGroup;

				if (advSearchLabelMaxLength < fieldBean.getCaptionName().length()) {
					advSearchLabelMaxLength = fieldBean.getCaptionName().length();
				}
				advSearchFields.add(fieldBean);
			}
		}

		String keyFieldEdit = ControllerModelKeys.EDIT_DATA_FIELDS;
		String keySearch = ControllerModelKeys.SEARCH_FIELDS;
		String keyAdvSearch = ControllerModelKeys.ADV_SEARCH_FIELDS;
		String keyList = ControllerModelKeys.LIST_FIELDS;
		String keyListFrozen = ControllerModelKeys.LIST_FROZEN_FIELDS;
		String keyAdd = ControllerModelKeys.ADD_FIELDS;
		String keyEdit = ControllerModelKeys.EDIT_FIELDS;
		String keyView = ControllerModelKeys.VIEW_FIELDS;
		String keyBatchEdit = ControllerModelKeys.BATCH_EDIT_FIELDS;
		String keyCopyReserve = ControllerModelKeys.HAS_COPY_RESERVE;
		String keyFieldGroup = ControllerModelKeys.IS_FIELD_GROUPED;

		// SubGrid时，Model中使用不同的Key
		if (isSubGrid) {
			keyFieldEdit = ControllerModelKeys.SUB_GRID_EDIT_DATA_FIELDS;
			keySearch = ControllerModelKeys.SUB_GRID_SEARCH_FIELDS;
			keyAdvSearch = ControllerModelKeys.SUB_GRID_ADV_SEARCH_FIELDS;
			keyList = ControllerModelKeys.SUB_GRID_LIST_FIELDS;
			keyListFrozen = ControllerModelKeys.SUB_GRID_LIST_FROZEN_FIELDS;
			keyAdd = ControllerModelKeys.SUB_GRID_ADD_FIELDS;
			keyEdit = ControllerModelKeys.SUB_GRID_EDIT_FIELDS;
			keyView = ControllerModelKeys.SUB_GRID_VIEW_FIELDS;
			keyBatchEdit = ControllerModelKeys.SUB_GRID_BATCH_EDIT_FIELDS;
			keyCopyReserve = ControllerModelKeys.SUB_GRID_HAS_COPY_RESERVE;
			keyFieldGroup = ControllerModelKeys.IS_SUB_GRID_FIELD_GROUPED;
		}

		model.addAttribute(keyFieldEdit, fieldEditFields);
		model.addAttribute(keySearch, searchFields);
		model.addAttribute(keyAdvSearch, advSearchFields);
		model.addAttribute(keyList, listFields);
		model.addAttribute(keyListFrozen, listFrozenFields);
		model.addAttribute(keyAdd, addFields);
		model.addAttribute(keyEdit, editFields);
		model.addAttribute(keyView, viewFields);
		model.addAttribute(keyBatchEdit, batchEditFields);

		model.addAttribute(keyCopyReserve, hasCopyReserve);
		model.addAttribute(keyFieldGroup, isFieldGrouped);

		// 计算各弹出画面的高度和宽度，高度只用maxHeight，让弹出画面根据实际情况自适应，宽度则根据字段Label的最大值做调整
		this.calcDialogWidthHeight(model, isSubGrid, isFieldGrouped, addLabelMaxLength, editLabelMaxLength,
				viewLabelMaxLength, advSearchLabelMaxLength, addFields.size(), editFields.size(), viewFields.size(),
				advSearchFields.size(), addGroupCount, editGroupCount, viewGroupCount, advSearchGroupCount);

		return advSearchFields.isEmpty();
	}

	/**
	 * 计算各弹出画面的高度和宽度，高度只用maxHeight，让弹出画面根据实际情况自适应，宽度则根据字段Label的最大值做调整。
	 * 
	 * @param model
	 * @param isSubGrid
	 * @param isFieldGrouped
	 * @param addLabelMaxLength
	 * @param editLabelMaxLength
	 * @param viewLabelMaxLength
	 * @param advSearchLabelMaxLength
	 * @param addFieldsCount
	 * @param editFieldsCount
	 * @param viewFieldsCount
	 * @param advSearchFieldsCount
	 * @param addGroupCount
	 * @param editGroupCount
	 * @param viewGroupCount
	 * @param advSearchGroupCount
	 */
	protected void calcDialogWidthHeight(Model model, boolean isSubGrid, boolean isFieldGrouped, int addLabelMaxLength,
			int editLabelMaxLength, int viewLabelMaxLength, int advSearchLabelMaxLength, int addFieldsCount,
			int editFieldsCount, int viewFieldsCount, int advSearchFieldsCount, int addGroupCount, int editGroupCount,
			int viewGroupCount, int advSearchGroupCount) {

		String keyAddMaxH = ControllerModelKeys.ADD_DIALOG_MAX_HEIGHT;
		String keyEditMaxH = ControllerModelKeys.EDIT_DIALOG_MAX_HEIGHT;
		String keyViewMaxH = ControllerModelKeys.VIEW_DIALOG_MAX_HEIGHT;
		String keyAdvSearchMaxH = ControllerModelKeys.ADV_SEARCH_DIALOG_MAX_HEIGHT;
		String keyAddCols = ControllerModelKeys.ADD_DIALOG_CONTROL_COLS;
		String keyEditCols = ControllerModelKeys.EDIT_DIALOG_CONTROL_COLS;
		String keyViewCols = ControllerModelKeys.VIEW_DIALOG_CONTROL_COLS;
		String keyAdvSearchCols = ControllerModelKeys.ADV_SEARCH_DIALOG_CONTROL_COLS;
		String keyAddW = ControllerModelKeys.ADD_DIALOG_WIDTH;
		String keyEditW = ControllerModelKeys.EDIT_DIALOG_WIDTH;
		String keyViewW = ControllerModelKeys.VIEW_DIALOG_WIDTH;
		String keyAdvSearchW = ControllerModelKeys.ADV_SEARCH_DIALOG_WIDTH;

		// SubGrid时，Model中使用不同的Key
		if (isSubGrid) {
			keyAddMaxH = ControllerModelKeys.SUB_GRID_ADD_DIALOG_MAX_HEIGHT;
			keyEditMaxH = ControllerModelKeys.SUB_GRID_EDIT_DIALOG_MAX_HEIGHT;
			keyViewMaxH = ControllerModelKeys.SUB_GRID_VIEW_DIALOG_MAX_HEIGHT;
			keyAdvSearchMaxH = ControllerModelKeys.SUB_GRID_ADV_SEARCH_DIALOG_MAX_HEIGHT;
			keyAddCols = ControllerModelKeys.SUB_GRID_ADD_DIALOG_CONTROL_COLS;
			keyEditCols = ControllerModelKeys.SUB_GRID_EDIT_DIALOG_CONTROL_COLS;
			keyViewCols = ControllerModelKeys.SUB_GRID_VIEW_DIALOG_CONTROL_COLS;
			keyAdvSearchCols = ControllerModelKeys.SUB_GRID_ADV_SEARCH_DIALOG_CONTROL_COLS;
			keyAddW = ControllerModelKeys.SUB_GRID_ADD_DIALOG_WIDTH;
			keyEditW = ControllerModelKeys.SUB_GRID_EDIT_DIALOG_WIDTH;
			keyViewW = ControllerModelKeys.SUB_GRID_VIEW_DIALOG_WIDTH;
			keyAdvSearchW = ControllerModelKeys.SUB_GRID_ADV_SEARCH_DIALOG_WIDTH;
		}

		int addDialogMaxHeight = this.calcDialogHeight(isFieldGrouped, addFieldsCount, addGroupCount);
		int editDialogMaxHeight = this.calcDialogHeight(isFieldGrouped, editFieldsCount, editGroupCount);
		int viewDialogMaxHeight = this.calcDialogHeight(isFieldGrouped, viewFieldsCount, viewGroupCount);
		int advSearchDialogMaxHeight = this.calcDialogHeight(false, advSearchFieldsCount, advSearchGroupCount);

		model.addAttribute(keyAddMaxH, addDialogMaxHeight);
		model.addAttribute(keyEditMaxH, editDialogMaxHeight);
		model.addAttribute(keyViewMaxH, viewDialogMaxHeight);
		model.addAttribute(keyAdvSearchMaxH, advSearchDialogMaxHeight);

		int addDialogWidth = 0;
		int editDialogWidth = 0;
		int viewDialogWidth = 0;
		int advSearchDialogWidth = 0;

		if (isFieldGrouped) {
			addDialogWidth = GROUPED_DIALOG_WIDTH;
			editDialogWidth = addDialogWidth;
			viewDialogWidth = addDialogWidth;

		} else {

			// 因为用了Bootstrap的格栅系统，此处布局计算较复杂，后期应考虑用更简单的布局方法，如<table/>

			int addControlCols = this.calcControlCols(addLabelMaxLength, 12);
			int editControlCols = this.calcControlCols(editLabelMaxLength, 12);
			int viewControlCols = this.calcControlCols(viewLabelMaxLength, 12);

			model.addAttribute(keyAddCols, addControlCols);
			model.addAttribute(keyEditCols, editControlCols);
			model.addAttribute(keyViewCols, viewControlCols);

			addDialogWidth = this.calcDialogWidth(addControlCols);
			editDialogWidth = this.calcDialogWidth(editControlCols);
			viewDialogWidth = this.calcDialogWidth(viewControlCols);
		}

		int advSearchControlCols = this.calcControlCols(advSearchLabelMaxLength, 11); // checkbox要占1格，故余11格
		model.addAttribute(keyAdvSearchCols, advSearchControlCols);

		advSearchDialogWidth = this.calcDialogWidth(advSearchControlCols);

		model.addAttribute(keyAddW, addDialogWidth);
		model.addAttribute(keyEditW, editDialogWidth);
		model.addAttribute(keyViewW, viewDialogWidth);
		model.addAttribute(keyAdvSearchW, advSearchDialogWidth);
	}

	/**
	 * 根据字段数量，计算弹出画面高度。
	 * 
	 * 如果easyui-dialog使用了shadow，同时存在附件字段，则在修改弹出画面中，删掉之前上传的附件时，
	 * 会导致弹出画面自适应高度减小，但shadow不会同步减小高度，这就造成了UI不美观。此方法原目的是根据字段数量来计算弹出画面高度，
	 * 并在JSP中同时设定dialog的height和maxHeight从而禁用自适应高度，后简化故，仍保留之前仅用maxHeight的自适应做法，同时对于修改弹出画面，直接不使用shadow。
	 * 
	 * @param isFieldGrouped
	 * @param fieldsCount
	 * @param groupCount
	 * @return
	 */
	protected int calcDialogHeight(boolean isFieldGrouped, int fieldsCount, int groupCount) {

		int h = 0;
		if (isFieldGrouped) {
			h = GROUPED_DIALOG_MAX_HEIGHT;
		} else {
			// // 每个字段行高42，字段区域外的高度108
			// h = 42 * fieldsCount + 108;
			// if (h > SIMPLE_DIALOG_MAX_HEIGHT) {
			// h = SIMPLE_DIALOG_MAX_HEIGHT;
			// }

			h = SIMPLE_DIALOG_MAX_HEIGHT;
		}

		return h;
	}

	/**
	 * 根据字段Label的最大字符数，计算字段控件占的格栅数(Bootstrap的12格栅)。
	 * 
	 * @param labelMaxLength
	 * @param totalCols
	 * @return
	 */
	protected int calcControlCols(int labelMaxLength, int totalCols) {

		if (labelMaxLength > 12) { // 限制最大长度
			labelMaxLength = 12;
		}
		int labelWidth = 20 + (labelMaxLength + 1) * 14; // 每个字符按14px计算，加一个*(表示必填)，内边距20px

		int controlWidth = 245; // control统一宽度245px
		double controlPct = controlWidth * 100.0 / (labelWidth + controlWidth);

		// 取floor保证弹出画面的width足够容纳label+control
		int cols = (int) Math.floor(controlPct * totalCols / 100);
		return cols;
	}

	/**
	 * 根据字段控件占的格栅数(Bootstrap的12格栅)，计算弹出画面的宽度。
	 * 
	 * @param controlCols
	 * @return
	 */
	protected int calcDialogWidth(int controlCols) {

		int controlWidth = 245; // Simple布局中control统一宽度245px

		int width = (int) ((40 + Math.ceil(controlWidth * 12.0 / controlCols)) * 1.05); // 边框和留白的宽度共计40px，整体宽度再增加5%，防止太紧凑
		return width;
	}

	/**
	 * 生成过滤
	 * 
	 * @param addedFilter
	 * @param commonFilterJson
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	protected CommonFilter generateCommonFilter(CommonFilter addedFilter, String commonFilterJson)
			throws JsonParseException, JsonMappingException, IOException {

		CommonFilter filter = addedFilter;
		if (!StringUtils.isEmpty(commonFilterJson)) {

			ObjectMapper jsonMapper = new ObjectMapper();
			jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			CommonFilter commonFilter = jsonMapper.readValue(commonFilterJson, CommonFilter.class);

			for (FilterFieldInfoDto f : commonFilter.getFilterFieldList()) {

				// 转换字段值
				if (FilterSearchBy.BY_VALUE == f.getFilterSearchBy()) {
					f.setValue(FilterFieldInfoDto.parseValue(f.getValue(), f.getValueType(), false, this.getLogger()));
				} else if (FilterSearchBy.BY_RANGE == f.getFilterSearchBy()) {
					f.setFrom(FilterFieldInfoDto.parseValue(f.getFrom(), f.getValueType(), false, this.getLogger()));

					// 修正日期的To，补齐时间信息(如果只有日期但不带时间信息，会被当作是00:00:00.000)
					if (f.getValueType() == FieldValueType.DATE && !StringUtils.isEmpty(f.getTo())) {
						if (!f.getTo().toString().contains(Constants.COLON)) {
							f.setTo(f.getTo() + " 23:59:59.999");
						}
					}
					f.setTo(FilterFieldInfoDto.parseValue(f.getTo(), f.getValueType(), false, this.getLogger()));
				}

				// 转换外键字段名
				f.setFieldName(f.getFieldName().replaceAll(Constants.UNDERSCORE, ".id"));
			}

			if (filter != null) {
				filter.getFilterFieldList().addAll(commonFilter.getFilterFieldList());
			} else {
				filter = commonFilter;
			}
		}

		return filter;
	}

	/**
	 * 获取一览显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson 画面上的检索信息
	 * @param addedFilter      包含过滤信息，便于子类传入特定的过滤条件
	 * @param page             页数
	 * @param rows             每页的记录数
	 * @param sort             排序的字段，CSV
	 * @param order            顺序，CSV
	 * @param locale
	 * @return
	 */
	protected JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			CommonFilter addedFilter, @RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 设置过滤信息
			CommonFilter filter = this.generateCommonFilter(addedFilter, commonFilterJson);

			// 设置分页信息
			PageCtrlDto pageCtrl = new PageCtrlDto();
			if (page > 0 && rows > 0) {
				pageCtrl.setCurrentPage(page);
				pageCtrl.setPageSize(rows);
			}

			// 设置排序信息
			List<OrderByDto> orders = new ArrayList<OrderByDto>();
			if (!StringUtils.isEmpty(sort)) {
				String[] orderArray = StringUtils.commaDelimitedListToStringArray(order);
				String[] sortArray = StringUtils.commaDelimitedListToStringArray(sort);
				for (int i = 0; i < orderArray.length; i++) {

					// 将bean的Text后缀的名字改为对应的entity的名字，即去掉Text后缀，规则见[t_data_fields].field_name_view
					String beanSort = sortArray[i];
					if (beanSort.endsWith("Text")) {
						beanSort = beanSort.substring(0, beanSort.length() - 4);
					}
					orders.add(new OrderByDto(beanSort, orderArray[i].equalsIgnoreCase(OrderByDto.DESC)));
				}
			}

			// 获取分页后的数据
			List<T> list = this.getDataService().list(filter, orders, pageCtrl);
			List<ViewBean> resultList = new ArrayList<>();
			for (T entity : list) {
				resultList.add(this.getViewConverter().fromEntity(entity, this.messageSource, locale));
			}
			long total = pageCtrl.getRecordAmount();

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, total);// easyui-datagrid分页用
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用

		} catch (Exception e) {
			this.getLogger().error("Exception listing the " + this.getDataType(), e);

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
	public JsonResultBean find(@RequestParam String id, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {
			// 检查是否存在记录
			T entity = this.getDataService().find(id);
			if (entity == null) {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
				return jsonResult;
			}

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT,
					this.getViewConverter().fromEntity(entity, this.messageSource, locale));

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
	 * @param bean     表现层对象
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	public JsonResultBean create(@ModelAttribute V bean, Map<String, Object> paramMap,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 检查是否有相应的权限
			if (!this.isUserHasAuthority(userBean, bean)) {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage(this.messageSource.getMessage("message.error.unauthorized", null, locale));
				return jsonResult;
			}

			// 检查是否存在相同的记录
			List<CommonFilter> filterList = this.getUniqueFilter(bean);
			for (CommonFilter filter : filterList) {
				if (this.getDataService().exist(filter)) {
					jsonResult.setStatus(JsonStatus.ERROR);
					jsonResult.setMessage(this.messageSource.getMessage("message.error.same.record", null, locale));
					return jsonResult;
				}
			}

			// 创建数据
			T entity = this.getViewConverter().toEntity(bean, null, userBean, new Date());

			this.beforeCreate(entity, bean, paramMap);
			this.getDataService().create(entity);
			this.postCreate(entity, bean, paramMap, locale);

			// 重新获取实体，及其关联，用于在一览画面显示
			T newEntity = this.getDataService().find(bean.getId());
			ViewBean beanToRender = this.getViewConverter().fromEntity(newEntity, this.messageSource, locale);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.add.ok", null, locale));
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beanToRender);

		} catch (Exception e) {
			getLogger().error("Exception creating the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 创建前处理。默认什么都不做。
	 * 
	 * @param entity   创建的数据库实体
	 * @param bean     表现层对象
	 * @param paramMap 参数Map
	 * @throws Exception
	 */
	protected void beforeCreate(T entity, V bean, Map<String, Object> paramMap) throws Exception {
		return;
	}

	/**
	 * 创建后处理，如创建秤后，通过JMS发送立即在线监测的任务摘要。默认什么都不做。
	 * 
	 * @param entity   创建的数据库实体
	 * @param bean     表现层对象
	 * @param paramMap 参数Map
	 * @throws Exception
	 */
	protected void postCreate(T entity, V bean, Map<String, Object> paramMap, Locale locale) throws Exception {
		return;
	}

	/**
	 * 逻辑新建，创建前先判断数据库有没有逻辑删除的记录，如果有，则修改，否则新建。返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param paramMap 参数Map，用以传递给postXxxxxx进行修改后续处理，在本方法内不作任何处理
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	public JsonResultBean createLogic(@ModelAttribute V bean, Map<String, Object> paramMap,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 检查是否存在相同的记录
			List<CommonFilter> filterList = this.getUniqueFilter(bean);
			T oldEntity = null;
			for (CommonFilter filter : filterList) {
				List<T> dbEntityList = this.getDataService().list(filter, null);
				if (!dbEntityList.isEmpty()) {

					oldEntity = dbEntityList.get(0);
					if (OnOffEnum.OFF.value().toString().equals(BeanUtils.getSimpleProperty(oldEntity, "isDeleted"))) {
						jsonResult.setStatus(JsonStatus.ERROR);
						jsonResult.setMessage(this.messageSource.getMessage("message.error.same.record", null, locale));
						return jsonResult;
					} else {
						break;
					}
				}
			}

			T entity = null;

			// 创建数据
			if (oldEntity == null) {

				entity = this.getViewConverter().toEntity(bean, null, userBean, new Date());
				this.getDataService().create(entity);

				this.postCreate(entity, bean, paramMap, locale);

			} else { // 修改，存在已经逻辑删除的记录

				entity = this.getViewConverter().toEntity(bean, oldEntity, userBean, new Date());
				this.getDataService().update(entity);

				this.postUpdate(entity, oldEntity, bean, paramMap);
			}

			// 重新获取实体，及其关联，用于在一览画面显示
			T newEntity = this.getDataService().find(bean.getId());
			ViewBean beanToRender = this.getViewConverter().fromEntity(newEntity, this.messageSource, locale);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.add.ok", null, locale));
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beanToRender);

		} catch (Exception e) {
			getLogger().error("Exception creating the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean     表现层对象
	 * @param paramMap 参数Map，用以传递给postUpdate进行修改后续处理，在本方法内不作任何处理
	 * @param userBean Session中的用户信息
	 * @param locale
	 * @return
	 */
	public JsonResultBean update(@ModelAttribute V bean, Map<String, Object> paramMap,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 检查是否有相应的权限
			if (!this.isUserHasAuthority(userBean, bean)) {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage(this.messageSource.getMessage("message.error.unauthorized", null, locale));
				return jsonResult;
			}

			// 检查是否存在记录
			T oldEntity = this.getDataService().find(bean.getId());
			if (oldEntity == null) {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
				return jsonResult;
			}

			// 检查是否存在相同的记录
			List<CommonFilter> filterList = this.getUniqueFilter(bean, oldEntity);
			for (CommonFilter filter : filterList) {
				if (this.getDataService().exist(filter)) {
					jsonResult.setStatus(JsonStatus.ERROR);
					jsonResult.setMessage(this.messageSource.getMessage("message.error.same.record", null, locale));
					return jsonResult;
				}
			}

			// 修改
			T entity = this.getViewConverter().toEntity(bean, oldEntity, userBean, new Date());
			this.getDataService().update(entity);
			this.postUpdate(entity, oldEntity, bean, paramMap);

			// 重新获取实体，及其关联，用于在一览画面显示
			T newEntity = this.getDataService().find(bean.getId());
			ViewBean beanToRender = this.getViewConverter().fromEntity(newEntity, this.messageSource, locale);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beanToRender);

		} catch (Exception e) {
			this.getLogger().error("Exception updating the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 更新后处理，如更新某记录后，通过JMS发送消息。默认什么都不做。
	 * 
	 * @param entity    更新的数据库实体
	 * @param oldEntity 更新前的数据库实体
	 * @param bean      表现层对象
	 * @param paramMap  参数Map
	 * @throws Exception
	 */
	protected void postUpdate(T entity, T oldEntity, V bean, Map<String, Object> paramMap) throws Exception {
		return;
	}

	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param paramMap    参数Map，用以传递给postDelete进行删除后续处理，在本方法内不作任何变更
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	public JsonResultBean delete(@RequestParam String idsToDelete, Map<String, Object> paramMap,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToDelete);
		List<String> idList = Arrays.asList(idArray);

		return this.delete(idList, paramMap, userBean, locale);
	}

	/**
	 * 删除，返回Json格式。
	 * 
	 * @param idsToDelete 待删除的ID，CSV
	 * @param paramMap    参数Map，用以传递给postDelete进行删除后续处理，在本方法内不作任何变更
	 * @param userBean    Session中的用户信息
	 * @param locale
	 * @return
	 */
	public JsonResultBean deleteLogic(@RequestParam String idsToDelete, Map<String, Object> paramMap,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToDelete);
			List<String> idList = Arrays.asList(idArray);

			UpdateFieldInfoDto field = new UpdateFieldInfoDto("isDeleted", OnOffEnum.ON.value(), FieldValueType.SHORT);

			UpdateFieldInfoDto field_mDatetime = new UpdateFieldInfoDto("mDatetime", new Date(), FieldValueType.DATE);

			List<UpdateFieldInfoDto> flist = new ArrayList<>();
			flist.add(field);
			flist.add(field_mDatetime);

			this.getDataService().update(flist, idList);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.delete.ok", null, locale));

		} catch (Exception e) {
			getLogger().error("Exception deleting the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.delete.failed", null, locale));
		}

		return jsonResult;
	}

	/**
	 * 按照id列表删除
	 */
	private JsonResultBean delete(List<String> idList, Map<String, Object> paramMap,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			this.beforeDelete(paramMap, userBean.getSessionLoginName(), new Date());
			this.getDataService().delete(idList);
			this.postDelete(paramMap, userBean.getSessionLoginName(), new Date());

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.delete.ok", null, locale));

		} catch (DataIntegrityViolationException violationException) {
			this.getLogger().error("Exception deleting the " + this.getDataType(), violationException);

			jsonResult.setStatus(JsonStatus.ERROR, JsonIcon.INFO);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.delete.failed.link.data", null, locale));

		} catch (Exception e) {
			this.getLogger().error("Exception deleting the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 删除全部
	 */
	private JsonResultBean deleteAll(Map<String, Object> paramMap,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {
			// TODO
			// this.getDataService().deleteAll();

			this.postDelete(paramMap, userBean.getSessionLoginName(), new Date());

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage(this.messageSource.getMessage("message.info.record.delete.ok", null, locale));

		} catch (Exception e) {
			this.getLogger().error("Exception deleting the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 删除前处理。默认什么都不做。
	 * 
	 * @param paramMap  参数Map
	 * @param modifier  修改者
	 * @param mDatetime 修改时间
	 */
	protected void beforeDelete(Map<String, Object> paramMap, String modifier, Date mDatetime) {
		return;
	}

	/**
	 * 删除后处理。默认什么都不做。
	 * 
	 * @param paramMap  参数Map
	 * @param modifier  修改者
	 * @param mDatetime 修改时间
	 */
	protected void postDelete(Map<String, Object> paramMap, String modifier, Date mDatetime) {
		return;
	}

	/**
	 * 统计符合过滤条件的待删除记录数，返回提示信息。
	 * 
	 * @param commonFilterJson JSON字符串形式的过滤条件
	 * @param locale
	 * @return
	 */
	public JsonResultBean countDeleting(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			Locale locale, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

		JsonResultBean jsonResult = new JsonResultBean();

		try {

			CommonFilter filter = new CommonFilter();
			filter = this.generateCommonFilter(filter, commonFilterJson);

			long count = this.getDataService().count(filter);

			String alertMsg = String
					.format(this.messageSource.getMessage("message.warn.delete.all.records", null, locale), count);

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_VALUE, alertMsg);
			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			this.getLogger().error("Exception counting the deleting " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

	/**
	 * 删除符合过滤条件的全部记录数，返回JSON。
	 * 
	 * @param commonFilterJson JSON字符串形式的过滤条件
	 * @param locale
	 * @return
	 */
	public JsonResultBean deleteFiltered(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		Map<String, Object> paramMap = new HashMap<String, Object>();

		CommonFilter filter = new CommonFilter();

		try {
			filter = this.generateCommonFilter(filter, commonFilterJson);
			if (filter.getFilterFieldList().size() == 0) {
				return this.deleteAll(paramMap, userBean, locale);
			}
		} catch (IOException e) {
			this.getLogger().error("Exception generating the common filter of " + this.getDataType(), e);
		}

		List<String> fieldList = new ArrayList<>();
		fieldList.add("id");

		List<Map<String, Object>> resultMapList = this.getDataService().listProjection(fieldList, filter, null, null);

		List<String> idList = new ArrayList<>();

		if (!resultMapList.isEmpty()) {

			for (Map<String, Object> resultMap : resultMapList) {
				idList.add(resultMap.get("id").toString());
			}
		}

		return this.delete(idList, paramMap, userBean, locale);
	}

	/**
	 * 导出Excel文件。
	 * 
	 * @param commonFilterJson
	 * @param addedFilter
	 * @param serverFile
	 * @param response
	 * @param locale
	 */
	public void exportExcel(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			CommonFilter addedFilter, File serverFile, HttpServletResponse response, Locale locale) {

		try {

			// 根据数据类型获取全局可见的字段
			CommonFilter dataFieldsFilter = new CommonFilter().addExact("dataType", this.getDataType())
					.addExact("pageId", this.getPageId()).addExact("isEnabled", OnOffEnum.ON.value())
					.addExact("isCommonVisible", OnOffEnum.ON.value()).addExact("isListVisible", OnOffEnum.ON.value());

			List<DataFields> listFields = this.dataFieldsService.list(dataFieldsFilter, null); // 默认按fieldOrder字段排序

			// 设置过滤信息
			CommonFilter filter = this.generateCommonFilter(addedFilter, commonFilterJson);

			// 设置分页信息
			PageCtrlDto pageCtrl = new PageCtrlDto();
			pageCtrl.setCurrentPage(1);
			pageCtrl.setPageSize(999999999);

			// 设置排序信息
			List<OrderByDto> orders = new ArrayList<OrderByDto>();
			orders.add(this.getDataService().getDefaultOrder());

			List<String> targetFields = listFields.stream().map(f -> f.getFieldName()).collect(Collectors.toList());

			List<Map<String, Object>> resultList = this.getDataService().listProjection(targetFields, filter, orders,
					pageCtrl);

			List<Object> titleRow = listFields.stream()
					.map(f -> this.messageSource.getMessage(f.getCaptionName(), null, locale))
					.collect(Collectors.toList());
			SimpleExcelDump dump = new SimpleExcelDump(titleRow);

			for (Map<String, Object> row : resultList) {
				List<Object> r = new ArrayList<>();
				for (DataFields df : listFields) {
					r.add(row.get(df.getFieldName()));
				}
				dump.appendRow(r);
			}

			dump.export(serverFile);

			ControllerDownloadUtils.downloadFile(serverFile, response);

		} catch (Exception e) {
			this.getLogger().error("Exception exporting excel " + this.getDataType(), e);
		}
	}

}
