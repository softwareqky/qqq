package project.edge.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerUrlMapKeys;

/**
 * Controller中UrlMap、IdMap和JsMap的工具类。
 *
 */
public class ControllerMapUtils {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ControllerMapUtils.class);

	public static final String FILTER_FORM_ID = "%1$s-%2$s-FilterForm";
	public static final String MAIN_DATAGRID_ID = "%1$s-%2$s-MainDatagrid";
	public static final String MAIN_CHART_ID = "%1$s-%2$s-MainChart";
	public static final String ADV_SEARCH_FORM_DIALOG_ID = "%1$s-%2$s-AdvSearchFormDialog";
	public static final String ADD_FORM_DIALOG_ID = "%1$s-%2$s-AddFormDialog";
	public static final String EDIT_FORM_DIALOG_ID = "%1$s-%2$s-EditFormDialog";
	public static final String VIEW_DIALOG_ID = "%1$s-%2$s-ViewDialog";

	// 流程相关
	public static final String SUBMIT_AUDIT_FORM_DIALOG_ID = "%1$s-%2$s-SubmitAuditFormDialog";
	public static final String SUBMIT_AUDIT_DIALOG_FLOWABLE_DIV_ID = "%1$s-%2$s-FlowDetailViewDiv";
	public static final String SUBMIT_AUDIT_DIALOG_FLOWABLE_COMBOBOX_ID = "%1$s-%2$s-FlowableSettingCombobox";
	public static final String SUBMIT_AUDIT_RECORD_DIALOG_ID = "AuditLogRecordDialog";
	public static final String WITHDRAW_DIALOG_ID = "WithdrawDialog";
	
	public static final String OA_AUDIT_RECORD_DIALOG_ID = "OaAuditLogRecordDialog";

	/**
	 * 根据画面ID和数据类型来构建id Map。
	 * 
	 * @param pageId
	 * @param dataType
	 * @return
	 */
	public static Map<String, String> buildIdMap(String pageId, String dataType) {

		Map<String, String> idMap = new HashMap<>();

		idMap.put(ControllerIdMapKeys.PAGE_PRE, pageId);
		idMap.put(ControllerIdMapKeys.DATA_TYPE_PRE, dataType);

		// 检索相关
		idMap.put(ControllerIdMapKeys.FILTER_FORM, String.format(FILTER_FORM_ID, pageId, dataType));

		idMap.put(ControllerIdMapKeys.MAIN_DATAGRID, String.format(MAIN_DATAGRID_ID, pageId, dataType));

		idMap.put(ControllerIdMapKeys.MAIN_CHART, String.format(MAIN_CHART_ID, pageId, dataType));

		idMap.put(ControllerIdMapKeys.ADV_SEARCH_FORM_DIALOG,
				String.format(ADV_SEARCH_FORM_DIALOG_ID, pageId, dataType));

		// CRUD相关
		idMap.put(ControllerIdMapKeys.ADD_FORM_DIALOG, String.format(ADD_FORM_DIALOG_ID, pageId, dataType));

		idMap.put(ControllerIdMapKeys.EDIT_FORM_DIALOG, String.format(EDIT_FORM_DIALOG_ID, pageId, dataType));

		idMap.put(ControllerIdMapKeys.VIEW_DIALOG, String.format(VIEW_DIALOG_ID, pageId, dataType));

		// 审核相关
		idMap.put(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG,
				String.format(SUBMIT_AUDIT_FORM_DIALOG_ID, pageId, dataType));

		idMap.put(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG_FLOWABLE_DIV,
				String.format(SUBMIT_AUDIT_DIALOG_FLOWABLE_DIV_ID, pageId, dataType));

		idMap.put(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG_FLOWABLE_COMBOBOX,
				String.format(SUBMIT_AUDIT_DIALOG_FLOWABLE_COMBOBOX_ID, pageId, dataType));

		idMap.put(ControllerIdMapKeys.AUDIT_LOG_DIALOG, SUBMIT_AUDIT_RECORD_DIALOG_ID); // 所有画面共通一个dialog
		idMap.put(ControllerIdMapKeys.WITHDRAW_DIALOG, WITHDRAW_DIALOG_ID); // 所有画面共通一个dialog
		
		idMap.put(ControllerIdMapKeys.OA_AUDIT_LOG_DIALOG, OA_AUDIT_RECORD_DIALOG_ID);

		return idMap;
	}

	public static final String SUB_GRID_FILTER_FORM_ID = "%1$s-%2$s-SubGridFilterForm";
	public static final String SUB_DATAGRID_ID = "%1$s-%2$s-SubDatagrid";
	public static final String SUB_GRID_ADV_SEARCH_FORM_DIALOG_ID = "%1$s-%2$s-SubGridAdvSearchFormDialog";
	public static final String SUB_GRID_ADD_FORM_DIALOG_ID = "%1$s-%2$s-SubGridAddFormDialog";
	public static final String SUB_GRID_EDIT_FORM_DIALOG_ID = "%1$s-%2$s-SubGridEditFormDialog";
	public static final String SUB_GRID_VIEW_DIALOG_ID = "%1$s-%2$s-SubGridViewDialog";
	public static final String SUB_GRID_VIEW_GRID_DIALOG_ID = "%1$s-%2$s-SubGridViewGridDialog"; // 查看页面(带grid)

	/**
	 * 画面是双Grid的场合，根据画面ID和数据类型来构建SubGrid的id Map。
	 * 
	 * @param pageId
	 * @param subDataType
	 * @return
	 */
	public static Map<String, String> buildSubGridIdMap(String pageId, String subDataType) {

		Map<String, String> idMap = new HashMap<>();

		idMap.put(ControllerIdMapKeys.SUB_DATA_TYPE_PRE, subDataType);

		// 检索相关
		idMap.put(ControllerIdMapKeys.SUB_GRID_FILTER_FORM,
				String.format(SUB_GRID_FILTER_FORM_ID, pageId, subDataType));

		idMap.put(ControllerIdMapKeys.SUB_DATAGRID, String.format(SUB_DATAGRID_ID, pageId, subDataType));

		idMap.put(ControllerIdMapKeys.SUB_GRID_ADV_SEARCH_FORM_DIALOG,
				String.format(SUB_GRID_ADV_SEARCH_FORM_DIALOG_ID, pageId, subDataType));

		// CRUD相关
		idMap.put(ControllerIdMapKeys.SUB_GRID_ADD_FORM_DIALOG,
				String.format(SUB_GRID_ADD_FORM_DIALOG_ID, pageId, subDataType));

		idMap.put(ControllerIdMapKeys.SUB_GRID_EDIT_FORM_DIALOG,
				String.format(SUB_GRID_EDIT_FORM_DIALOG_ID, pageId, subDataType));

		idMap.put(ControllerIdMapKeys.SUB_GRID_VIEW_DIALOG,
				String.format(SUB_GRID_VIEW_DIALOG_ID, pageId, subDataType));
		
		idMap.put(ControllerIdMapKeys.SUB_GRID_VIEW_GRID_DIALOG,
				String.format(SUB_GRID_VIEW_GRID_DIALOG_ID, pageId, subDataType));

		// 审核相关
		idMap.put(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG,
				String.format(SUBMIT_AUDIT_FORM_DIALOG_ID, pageId, subDataType));

		idMap.put(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG_FLOWABLE_DIV,
				String.format(SUBMIT_AUDIT_DIALOG_FLOWABLE_DIV_ID, pageId, subDataType));

		idMap.put(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG_FLOWABLE_COMBOBOX,
				String.format(SUBMIT_AUDIT_DIALOG_FLOWABLE_COMBOBOX_ID, pageId, subDataType));
		
		return idMap;
	}

	/**
	 * 构建URL Map。
	 * 
	 * @param contextPath 站点路径，形如"/project-edge"
	 * @param urlPrefix   URL的前缀，形如"/auth/user/"
	 * @return
	 */
	public static Map<String, String> buildUrlMap(String contextPath, String urlPrefix, String pageId) {

		Map<String, String> urlMap = new HashMap<>();

		// CRUD和检索相关
		urlMap.put(ControllerUrlMapKeys.LIST, urlPrefix + "list.json");
		urlMap.put(ControllerUrlMapKeys.ADD, urlPrefix + "add.json");
		urlMap.put(ControllerUrlMapKeys.EDIT, urlPrefix + "edit.json");
		urlMap.put(ControllerUrlMapKeys.FIND, urlPrefix + "find.json");
		urlMap.put(ControllerUrlMapKeys.DELETE, urlPrefix + "delete.json");

		urlMap.put(ControllerUrlMapKeys.SAVE, urlPrefix + "save.json");

		urlMap.put(ControllerUrlMapKeys.CHART, urlPrefix + "chart.json");

		// 同步
		urlMap.put(ControllerUrlMapKeys.SYNC, contextPath + "/oa/hrm/sync.json");

		// 左侧项目列表一览
		// urlMap.put(ControllerUrlMapKeys.LIST_SIDE_PROJECT,
		// contextPath + "/project/project-init/list-side.json");
		urlMap.put(ControllerUrlMapKeys.TREE_SIDE_PROJECT, contextPath + "/project/project-init/tree-side.json");

		urlMap.put(ControllerUrlMapKeys.TREE_SIDE_BUSNIESS_CODE, contextPath + "/flowable/setting/tree-side.json");

		// 左侧公司部门树
		urlMap.put(ControllerUrlMapKeys.TREE_SIDE_DEPT, contextPath + "/org/dept/tree-side.json");

		// 左侧文件夹-文件层级树
		urlMap.put(ControllerUrlMapKeys.TREE_SIDE_ARCHIVE, contextPath + "/archive/project-archive/tree-side.json");

		// 计划的任务编辑画面的URL
		urlMap.put(ControllerUrlMapKeys.PLAN_TASK_MAIN, contextPath + "/schedule/plan-task/main.htm");

		urlMap.put(ControllerUrlMapKeys.CONCERN, urlPrefix + "concern.json");
		urlMap.put(ControllerUrlMapKeys.NO_CONCERN, urlPrefix + "no-concern.json");

		// OA审批URL
		urlMap.put(ControllerUrlMapKeys.AUDIT_SUBMIT, urlPrefix + "auditSubmit.json");

		// 审核流程列表
		if (!StringUtils.isEmpty(pageId)) {
			urlMap.put(ControllerUrlMapKeys.ACCESS_PROCESS_LIST,
					contextPath + "/flowable/setting/access-process.json?businessCode=" + pageId);
		}

		// 提交审核流程
		urlMap.put(ControllerUrlMapKeys.SUBMIT_AUDIT, contextPath + "/flowable/setting/submit-audit-process.json");
		return urlMap;
	}

	/**
	 * 画面是双Grid的场合，增加SubGrid相关的URL。
	 * 
	 */
	public static void buildSubGridUrlMap(Map<String, String> urlMap) {

		urlMap.put(ControllerUrlMapKeys.SUB_GRID_LIST,
				urlMap.get(ControllerUrlMapKeys.LIST).replace("list.json", "sub-grid-list.json"));
		urlMap.put(ControllerUrlMapKeys.SUB_GRID_ADD,
				urlMap.get(ControllerUrlMapKeys.ADD).replace("add.json", "sub-grid-add.json"));
		urlMap.put(ControllerUrlMapKeys.SUB_GRID_EDIT,
				urlMap.get(ControllerUrlMapKeys.EDIT).replace("edit.json", "sub-grid-edit.json"));
		urlMap.put(ControllerUrlMapKeys.SUB_GRID_FIND,
				urlMap.get(ControllerUrlMapKeys.FIND).replace("find.json", "sub-grid-find.json"));
		urlMap.put(ControllerUrlMapKeys.SUB_GRID_DELETE,
				urlMap.get(ControllerUrlMapKeys.DELETE).replace("delete.json", "sub-grid-delete.json"));
	}

	/**
	 * 根据画面ID、数据类型和是否包含文件上传来构建JavaScript Map。
	 * 
	 * 包含用于功能按钮的事件JavaScript，以及较复杂的JavaScript调用。
	 * 
	 * @param idMap
	 * @param urlMap
	 * @param isFile            新建修改使用弹出对话框时，是否包含文件上传
	 * @param jsCallbackObjName 回调JS对象名
	 * @return
	 */
	public static Map<String, String> buildJsMap(Map<String, String> idMap, Map<String, String> urlMap, boolean isFile,
			String jsCallbackObjName) {

		Map<String, String> jsMap = new HashMap<>();

		// 检索
		jsMap.put(ControllerJsMapKeys.SEARCH, String.format(SEARCH_JS, idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
				idMap.get(ControllerIdMapKeys.FILTER_FORM)));

		// 打开高级检索
		jsMap.put(ControllerJsMapKeys.OPEN_ADV_SEARCH,
				String.format(OPEN_ADV_SEARCH_JS, idMap.get(ControllerIdMapKeys.ADV_SEARCH_FORM_DIALOG),
						idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), jsCallbackObjName));

		// 清空检索
		jsMap.put(ControllerJsMapKeys.CLEAR_SEARCH,
				String.format(CLEAR_SEARCH_JS, idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						idMap.get(ControllerIdMapKeys.FILTER_FORM), jsCallbackObjName));

		// 打开新建
		jsMap.put(ControllerJsMapKeys.OPEN_ADD,
				String.format(OPEN_ADD_JS, idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG), jsCallbackObjName));

		// 连续新建
		jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT,
				String.format(CONTINUOUS_ADD_SUBMIT_JS, idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
						idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), isFile, jsCallbackObjName));

		// 新建保存
		jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
				String.format(ADD_SUBMIT_JS, idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
						idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), isFile, jsCallbackObjName));

		// 删除
		jsMap.put(ControllerJsMapKeys.OPEN_DELETE, String.format(OPEN_DELETE_JS,
				urlMap.get(ControllerUrlMapKeys.DELETE), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 打开修改
		jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
				String.format(OPEN_EDIT_JS, idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
						urlMap.get(ControllerUrlMapKeys.FIND), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						jsCallbackObjName));

		// 修改保存
		jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT,
				String.format(EDIT_SUBMIT_JS, idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
						idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), isFile, jsCallbackObjName));

		// 打开查看
		jsMap.put(ControllerJsMapKeys.OPEN_VIEW,
				String.format(OPEN_VIEW_JS, idMap.get(ControllerIdMapKeys.VIEW_DIALOG),
						urlMap.get(ControllerUrlMapKeys.FIND), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						jsCallbackObjName));

		// 保存
		jsMap.put(ControllerJsMapKeys.SAVE, String.format(SAVE_JS, isFile, jsCallbackObjName));

		// 同步
		jsMap.put(ControllerJsMapKeys.SYNC, String.format(SYNC_JS, urlMap.get(ControllerUrlMapKeys.SYNC)));

		// 编制任务
		jsMap.put(ControllerJsMapKeys.OPEN_MODIFY_TASK,
				String.format(OPEN_MODIFY_TASK_JS, idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 查看任务
		jsMap.put(ControllerJsMapKeys.OPEN_VIEW_TASK,
				String.format(OPEN_VIEW_TASK_JS, idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 合并计划
		jsMap.put(ControllerJsMapKeys.OPEN_MERGE_SCHEDULE,
				String.format(OPEN_MERGE_SCHEDULE_JS, idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 查看总体任务
		jsMap.put(ControllerJsMapKeys.OPEN_VIEW_OVERALL_TASK,
				String.format(OPEN_VIEW_OVERALL_TASK_JS, idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 查看进度预警
		jsMap.put(ControllerJsMapKeys.OPEN_PROGRESS_WARNING,
				String.format(OPEN_PROGRESS_WARNING_JS, idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 历史记录
		jsMap.put(ControllerJsMapKeys.OPEN_HISTORY_LOG, String.format(OPEN_HISTORY_LOG_JS,
				idMap.get(ControllerIdMapKeys.HISTORY_LOG_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
		
		// OA审批
		jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT, String.format(OPEN_SUBMIT_AUDIT_JS,
				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), urlMap.get(ControllerUrlMapKeys.AUDIT_SUBMIT)));
		
		// OA审核日志
		jsMap.put(ControllerJsMapKeys.OPEN_AUDIT_LOG, String.format(OPEN_AUDIT_LOG_JS,
				idMap.get(ControllerIdMapKeys.OA_AUDIT_LOG_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

		// 打开提交审核对话框
		jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_SUBMIT_AUDIT,
				String.format(OPEN_LOCAL_SUBMIT_AUDIT_JS, idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG),
						idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG_FLOWABLE_DIV),
						idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG_FLOWABLE_COMBOBOX)));

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
		
		// 打开重新生成对话框
		jsMap.put(ControllerJsMapKeys.REGENERATE_PLAN, String.format(REGENERATE_PLAN_JS, 
				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
 		
		return jsMap;
	}

	public static final String SEARCH_JS = "FilterUtils.searchSimple('#%1$s', '#%2$s')";
	public static final String OPEN_ADV_SEARCH_JS = "FilterUtils.openAdvSearchFormDialog('#%1$s', '#%2$s', %3$s)";
	public static final String CLEAR_SEARCH_JS = "FilterUtils.clearSearch('#%1$s', '#%2$s', %3$s)";
	public static final String OPEN_ADD_JS = "CrudUtils.openAddFormDialog('#%1$s', %2$s)";
	public static final String CONTINUOUS_ADD_SUBMIT_JS = "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, %4$s)";
	public static final String ADD_SUBMIT_JS = "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, %4$s)";
	public static final String OPEN_DELETE_JS = "CrudUtils.batchDeleteSelected('%1$s', '#%2$s')";
	public static final String OPEN_EDIT_JS = "CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, %4$s)";
	public static final String EDIT_SUBMIT_JS = "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, false, false, %4$s)";
	public static final String OPEN_VIEW_JS = "CrudUtils.openViewDialog('#%1$s', '%2$s', '#%3$s', %4$s)";
	
	private static final String REGENERATE_PLAN_JS = "PlanTaskUtils.regeneratePlan('#%1$s')";

	private static final String OPEN_LOCAL_SUBMIT_AUDIT_JS = "FlowableUtils.openSubmitAuditFormDialog('#%1$s','#%2$s','#%3$s')";
	private static final String LOCAL_AUDIT_SUBMIT_JS = "FlowableUtils.saveProcessInstanceFormData('#%1$s','%2$s','#%3$s');";
	private static final String OPEN_LOCAL_AUDIT_LOG_JS = "FlowableUtils.openAuditLogDialog('#%1$s','#%2$s')";
	private static final String OPEN_LOCAL_WITHDRAW_JS = "FlowableUtils.openWithdrawDialog('#%1$s','#%2$s')";
	
	public static final String SAVE_JS = "CrudUtils.saveFormData(this, %1$s, %2$s)";
	public static final String SYNC_JS = "OaIntegration.syncHrmData('%1$s')";
	public static final String OPEN_MODIFY_TASK_JS = "PlanTaskUtils.openModifyTask('#%1$s')";
	public static final String OPEN_VIEW_TASK_JS = "PlanTaskUtils.openViewTask('#%1$s')";
	public static final String OPEN_MERGE_SCHEDULE_JS = "PlanUtils.openMergeScheduleDialog('#%1$s')";
	public static final String OPEN_VIEW_OVERALL_TASK_JS = "PlanTaskUtils.openProgressTask('#%1$s')";
	public static final String OPEN_PROGRESS_WARNING_JS = "PlanTaskUtils.openProgressWarning('#%1$s')";
	public static final String OPEN_VERSION_EXPORT_JS = "BUDGETVERSION.openExportFormDialog()";
	
	public static final String OPEN_SUBMIT_AUDIT_JS = "OaIntegration.submitAudit('#%1$s', '%2$s');";
	public static final String OPEN_SUBMIT_AUDIT_BATCH_JS = "OaIntegration.submitAuditBatch('#%1$s', '%2$s');";
	public static final String OPEN_AUDIT_LOG_JS = "OaIntegration.openAuditLogDialog('#%1$s','#%2$s')";
	public static final String OPEN_HISTORY_LOG_JS = "CrudUtils.openHistoryLogDialog('#%1$s','#%2$s')";

	/**
	 * 画面是双Grid的场合，根据画面ID、数据类型和是否包含文件上传来构建SubGrid的JavaScript Map。
	 * 
	 * 包含用于功能按钮的事件JavaScript，以及较复杂的JavaScript调用。
	 * 
	 * @param idMap
	 * @param urlMap
	 * @param isFile            新建修改使用弹出对话框时，是否包含文件上传
	 * @param jsCallbackObjName 回调JS对象名
	 * @return
	 */
	public static Map<String, String> buildSubGridJsMap(Map<String, String> idMap, Map<String, String> urlMap,
			boolean isFile, String jsCallbackObjName) {

		Map<String, String> jsMap = new HashMap<>();

		// 检索
		jsMap.put(ControllerJsMapKeys.SUB_GRID_SEARCH, String.format(SEARCH_JS,
				idMap.get(ControllerIdMapKeys.SUB_DATAGRID), idMap.get(ControllerIdMapKeys.SUB_GRID_FILTER_FORM)));

		// 打开高级检索
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_ADV_SEARCH,
				String.format(OPEN_ADV_SEARCH_JS, idMap.get(ControllerIdMapKeys.SUB_GRID_ADV_SEARCH_FORM_DIALOG),
						idMap.get(ControllerIdMapKeys.SUB_DATAGRID), jsCallbackObjName));

		// 清空检索
		jsMap.put(ControllerJsMapKeys.SUB_GRID_CLEAR_SEARCH,
				String.format(CLEAR_SEARCH_JS, idMap.get(ControllerIdMapKeys.SUB_DATAGRID),
						idMap.get(ControllerIdMapKeys.SUB_GRID_FILTER_FORM), jsCallbackObjName));

		// 打开新建
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_ADD,
				String.format(OPEN_ADD_JS, idMap.get(ControllerIdMapKeys.SUB_GRID_ADD_FORM_DIALOG), jsCallbackObjName));

		// 连续新建
		jsMap.put(ControllerJsMapKeys.SUB_GRID_CONTINUOUS_ADD_SUBMIT,
				String.format(CONTINUOUS_ADD_SUBMIT_JS, idMap.get(ControllerIdMapKeys.SUB_GRID_ADD_FORM_DIALOG),
						idMap.get(ControllerIdMapKeys.SUB_DATAGRID), isFile, jsCallbackObjName));

		// 新建保存
		jsMap.put(ControllerJsMapKeys.SUB_GRID_ADD_SUBMIT,
				String.format(ADD_SUBMIT_JS, idMap.get(ControllerIdMapKeys.SUB_GRID_ADD_FORM_DIALOG),
						idMap.get(ControllerIdMapKeys.SUB_DATAGRID), isFile, jsCallbackObjName));

		// 删除
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_DELETE, String.format(OPEN_DELETE_JS,
				urlMap.get(ControllerUrlMapKeys.SUB_GRID_DELETE), idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));

		// 打开修改
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_EDIT,
				String.format(OPEN_EDIT_JS, idMap.get(ControllerIdMapKeys.SUB_GRID_EDIT_FORM_DIALOG),
						urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND), idMap.get(ControllerIdMapKeys.SUB_DATAGRID),
						jsCallbackObjName));

		// 修改保存
		jsMap.put(ControllerJsMapKeys.SUB_GRID_EDIT_SUBMIT,
				String.format(EDIT_SUBMIT_JS, idMap.get(ControllerIdMapKeys.SUB_GRID_EDIT_FORM_DIALOG),
						idMap.get(ControllerIdMapKeys.SUB_DATAGRID), isFile, jsCallbackObjName));

		// 打开查看
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_VIEW,
				String.format(OPEN_VIEW_JS, idMap.get(ControllerIdMapKeys.SUB_GRID_VIEW_DIALOG),
						urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND), idMap.get(ControllerIdMapKeys.SUB_DATAGRID),
						jsCallbackObjName));

		//处理双grid时，处理sub-datagrid选中值
		// 提交审核数据按钮
        jsMap.put(ControllerJsMapKeys.LOCAL_AUDIT_SUBMIT,
                String.format(LOCAL_AUDIT_SUBMIT_JS, idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.SUBMIT_AUDIT), idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));

        // 打开审核日志对话框
        jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_AUDIT_LOG, String.format(OPEN_LOCAL_AUDIT_LOG_JS,
                idMap.get(ControllerIdMapKeys.AUDIT_LOG_DIALOG), idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));

        // 打开撤回对话框
        jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_WITHDRAW, String.format(OPEN_LOCAL_WITHDRAW_JS,
                idMap.get(ControllerIdMapKeys.WITHDRAW_DIALOG), idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));
		
        
        // 打开提交审核对话框
     	jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_SUBMIT_AUDIT, String.format(OPEN_LOCAL_SUBMIT_AUDIT_JS, 
     			idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG),
     			idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG_FLOWABLE_DIV),
     			idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG_FLOWABLE_COMBOBOX)));
        
		// OA审批
		jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT, String.format(OPEN_SUBMIT_AUDIT_JS,
				idMap.get(ControllerIdMapKeys.SUB_DATAGRID), urlMap.get(ControllerUrlMapKeys.AUDIT_SUBMIT)));
		
		// OA审核日志
		jsMap.put(ControllerJsMapKeys.OPEN_AUDIT_LOG, String.format(OPEN_AUDIT_LOG_JS,
				idMap.get(ControllerIdMapKeys.OA_AUDIT_LOG_DIALOG), idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));
		
		return jsMap;
	}
}
