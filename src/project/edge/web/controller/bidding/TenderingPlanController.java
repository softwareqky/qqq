package project.edge.web.controller.bidding;

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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.PriceTypeEnum;
import garage.origin.common.constant.TenderingStatusEnum;
import garage.origin.common.util.DateUtils;
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
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.TenderingPlanBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.TenderingPlan;
import project.edge.domain.entity.TenderingPlanAttachment;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.TenderingPlanBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.bidding.TenderingPlanService;
import project.edge.service.bidding.TenderingPurchaseService;
import project.edge.service.org.DeptService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 招标计划明细画面
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
//@RequestMapping("/tendering_plan")
@RequestMapping("/tendering_plan_relatedunit")
public class TenderingPlanController extends TreeGridUploadController<TenderingPlan, TenderingPlanBean> {

	private static final Logger logger = LoggerFactory.getLogger(TenderingPlanController.class);

	private static final String PID = "P301005";

	private static final String URL_MAP_KEY_EDIT_FILE = "edit-file"; // 上传文件的URL

	private static final String ID_MAP_KEY_VERIFICATION_DIALOG = "edit-verification-form-dialog";

	private static final String JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT = "edit-verification-submit";

	private static final String MODEL_KEY_SOLVE_FIELDS = "verificationFields";
	
	@Resource
	CreateWorkFlowManager createWorkFlowManager;
	
	@Autowired
	HttpServletRequest request;
	
	@Resource
	private TenderingPlanService tenderingPlanService;

	@Resource
	private DataOptionService dataOptionService;

	@Resource
	private PurchaseOrderService purchaseOrderService;

	@Resource
	private DeptService deptService;
	
	@Resource
    private MessageSource messageSource;
	
	@Resource
	private TenderingPurchaseService tenderingPurchaseService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.TENDERING_PLAN.value();
	}

	@Override
	protected Service<TenderingPlan, String> getDataService() {
		return this.tenderingPlanService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<TenderingPlan, TenderingPlanBean> getViewConverter() {
		return new TenderingPlanBeanConverter();
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/bidding/tenderingPlanJs.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/bidding/tenderingPlanHidden.jsp";
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

		CommonFilter f = null;

		List<ComboboxOptionBean> purchaseOrderOptions = new ArrayList<>();
		List<PurchaseOrder> purchaseOrdersList = this.purchaseOrderService.list(f, null);
		if (purchaseOrdersList != null) {
			for (PurchaseOrder purchaseOrders : purchaseOrdersList) {
				purchaseOrderOptions
						.add(new ComboboxOptionBean(purchaseOrders.getId(), purchaseOrders.getPurchaseOrderNo()));
			}
		}

		optionMap.put("purchaseOrderOptions", purchaseOrderOptions);

		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.PROJECT_BIDDING_METHOD.value());

		CommonFilter f1 = new CommonFilter().addWithin("dataType", dataTypeList);

		List<ComboboxOptionBean> relatedUnitPropertyOptions = new ArrayList<>();

		List<DataOption> list = this.dataOptionService.list(f1, null);
		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.PROJECT_BIDDING_METHOD.value())) {
				relatedUnitPropertyOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			}
		}
		optionMap.put("TenderingTypeOptions", relatedUnitPropertyOptions);

		// 招标部门
		List<ComboboxOptionBean> deptOptions = new ArrayList<>();
		List<Dept> deptOptionsList = this.deptService.list(f, null);
		if (deptOptionsList != null) {
			for (Dept dept : deptOptionsList) {
				deptOptions.add(new ComboboxOptionBean(dept.getId(), dept.getDeptName()));
			}
		}

		optionMap.put("DeptOptions", deptOptions);

		// 状态
		ArrayList<ComboboxOptionBean> tenderingStatusMode = new ArrayList<ComboboxOptionBean>();
		for (TenderingStatusEnum onoffEnum : TenderingStatusEnum.values()) {
			tenderingStatusMode.add(new ComboboxOptionBean(onoffEnum.value().toString(),
					messageSource.getMessage(onoffEnum.resourceName(), null, locale)));
		}
		// 类型
		optionMap.put("TenderingStatus", tenderingStatusMode);

		ArrayList<ComboboxOptionBean> priceTypeEnumMode = new ArrayList<ComboboxOptionBean>();
		for (PriceTypeEnum priceTypeEnum : PriceTypeEnum.values()) {
			priceTypeEnumMode.add(new ComboboxOptionBean(priceTypeEnum.value().toString(),
					messageSource.getMessage(priceTypeEnum.resourceName(), null, locale)));
		}
		optionMap.put("PriceType", priceTypeEnumMode);

		return optionMap;
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
		// if (field.getFieldName().equals("proposalArchive_")) { // 建议
		this.putFiledList(model, MODEL_KEY_SOLVE_FIELDS, field);

		// }
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

	@Override
	protected Map<String, String> prepareUrlMap() {

		Map<String, String> urlMap = super.prepareUrlMap();

		String contextPath = this.context.getContextPath();
		urlMap.put(URL_MAP_KEY_EDIT_FILE, contextPath + "/bidding/teneree/edit-file.json");

		return urlMap;
	}

	@Override
	protected Map<String, String> prepareIdMap() {

		Map<String, String> idMap = super.prepareIdMap();

		idMap.put(ID_MAP_KEY_VERIFICATION_DIALOG,
				String.format("%1$s-%2$s-EditFormDialog-Verification", this.getPageId(), this.getDataType()));

		return idMap;
	}

	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {
		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

		// 打开新建和修改画面，以及连续新建保存时，增加回调，刷新项目集下拉框的内容

		// 打开新建
		jsMap.put(ControllerJsMapKeys.OPEN_ADD, String.format("CrudUtils.openAddFormDialog('#%1$s', TENDERINGPLAN);",
				idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

		// 新建保存，isFile强制设为false
		jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true);",
						idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						this.useFile()));

		// 打开修改
		jsMap.put(ControllerJsMapKeys.OPEN_EDIT, String.format("TENDERINGPLAN.openEditFormDialog('#%1$s', '%2$s');",
				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), urlMap.get(ControllerUrlMapKeys.FIND)));

		// 修改保存
		jsMap.put(JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
						idMap.get(ID_MAP_KEY_VERIFICATION_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						this.useFile()));
 		
		return jsMap;
	}
	
//	/**
//	 * 画面Open的入口方法，用于生成JSP。
//	 * 
//	 * @param paramMap
//	 *            画面请求中的任何参数，都会成为检索的字段
//	 * @param model
//	 *            model
//	 * @param userBean
//	 *            session中的当前登录的用户信息
//	 * @param locale
//	 *            locale
//	 * @return
//	 */
//	@RequestMapping("/main")
//	public String main(@RequestParam Map<String, String> paramMap, Model model,
//			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
//
//		return super.main(paramMap, model, userBean, locale);
//	}

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
	//@RequestMapping("/list")
	@RequestMapping("/sub-grid-list")
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
	@RequestMapping("/sub-grid-find")
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
	@RequestMapping("/sub-grid-add")
	@SysLogAnnotation(description = "招标计划明细", action = "新增")
	public void create(HttpServletRequest request, HttpServletResponse response, @ModelAttribute TenderingPlanBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archiveDraftFirst_", bean.getArchiveDraftFirstList());
		fieldNameArchiveListMap.put("archiveDraftFinal_", bean.getArchiveDraftFinalList());
		fieldNameArchiveListMap.put("archiveTechnicalDoc_", bean.getArchiveTechnicalDocList());
		fieldNameArchiveListMap.put("archiveAnnouncement_", bean.getArchiveAnnouncementList());
		fieldNameArchiveListMap.put("archiveTenderWin_", bean.getArchiveTenderWinList());

		// 项目ID需由申购号后台查询设置
		if (StringUtils.isNotEmpty(bean.getPurchaseOrder_())) {
            PurchaseOrder purchaseOrder = purchaseOrderService.find(bean.getPurchaseOrder_());
            Project project = purchaseOrder.getProject();
            bean.setProject_(project.getId());
        }
		
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
	@RequestMapping("/sub-grid-edit")
	@SysLogAnnotation(description = "招标计划明细", action = "更新")
	public void update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute TenderingPlanBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archiveDraftFirst_", bean.getArchiveDraftFirstList());
		fieldNameArchiveListMap.put("archiveDraftFinal_", bean.getArchiveDraftFinalList());
		fieldNameArchiveListMap.put("archiveTechnicalDoc_", bean.getArchiveTechnicalDocList());
		fieldNameArchiveListMap.put("archiveAnnouncement_", bean.getArchiveAnnouncementList());
		
		// 项目ID需由申购号后台查询设置
		if (StringUtils.isNotEmpty(bean.getPurchaseOrder_())) {
            PurchaseOrder purchaseOrder = purchaseOrderService.find(bean.getPurchaseOrder_());
            Project project = purchaseOrder.getProject();
            bean.setProject_(project.getId());
        }

		// 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
		super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
	}

	@Override
	protected void postUpdate(TenderingPlan entity, TenderingPlan oldEntity, TenderingPlanBean bean,
			java.util.Map<String, Object> paramMap) throws IOException {
		super.postUpdate(entity, oldEntity, bean, paramMap);
		List<Archive> list = new ArrayList<>();
		for (TenderingPlanAttachment attachment : entity.getArchivesToDelete()) {
			list.add(attachment.getArchive());
		}
		this.deleteArchiveFiles(list);
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
	@RequestMapping("/sub-grid-delete")
	@ResponseBody
	@SysLogAnnotation(description = "招标计划明细", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}

	/**
	 * 管理平台提交审核
	 * 
	 * @param id
	 *            提交审核记录ID
	 * @param locale
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "招标计划明细", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		TenderingPlan entity = this.getDataService().find(id);

		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_TENDINGPLAN.value();

		// 向OA提交审核请求
		HashMap<String, Object> reqMap = new HashMap<>();

		if (entity.getTenderingType() != null) {
			reqMap.put("tenderingType", entity.getTenderingType().getOptionName());
		} else {
			reqMap.put("tenderingType", "");
		}
		reqMap.put("tenderingNo", entity.getTenderingNo());
		reqMap.put("tenderingName", entity.getTenderingName());
		reqMap.put("tenderingStatus", messageSource.getMessage(TenderingStatusEnum.getResouceName(entity.getTenderingStatus()), null, locale));
		if (entity.getTenderingLeader() != null) {
			reqMap.put("tenderingLeader", entity.getTenderingLeader().getPersonName());
		} else {
			reqMap.put("tenderingLeader", "");
		}
		reqMap.put("tenderingMethod", entity.getTenderingMethod());
		reqMap.put("evaluatingMethods", entity.getEvaluatingMethods());
		reqMap.put("priceUnit", entity.getPriceUnit());
		reqMap.put("estimatedPrice", String.valueOf(entity.getEstimatedPrice()));
		reqMap.put("tenderScope", entity.getTenderScope());
		reqMap.put("planStartTime", DateUtils.date2String(entity.getPlanStartTime(), Constants.DATE_FORMAT));
		reqMap.put("planEndTime", DateUtils.date2String(entity.getPlanEndTime(), Constants.DATE_FORMAT));
		reqMap.put("tenderOpenTime", DateUtils.date2String(entity.getTenderOpenTime(), Constants.DATE_FORMAT));
		reqMap.put("tenderAssessmentStartTime", DateUtils.date2String(entity.getTenderAssessmentStartTime(), Constants.DATE_FORMAT));
		reqMap.put("remark", entity.getRemark());
		reqMap.put("applicant", entity.getApplicant().getUser().getId());
		reqMap.put("recordTime", DateUtils.date2String(entity.getRecordTime(), Constants.DATE_FORMAT));
		
//		CommonFilter filter = new CommonFilter().addExact("tenderingPlan.id", id);
//		List<TenderingPurchase> tenderingPurchaseList = tenderingPurchaseService.list(filter, null);
//		if (tenderingPurchaseList.size() > 0) {
//			reqMap.put("purchaseOrderNo", tenderingPurchaseList.get(0).getPurchaseOrder());
//		} else {
//			reqMap.put("purchaseOrderNo", "");
//		}

		Set<TenderingPlanAttachment> attchments = entity.getArchives();
		String fileName = "";
		String filePath = "";
		String attchmentType = "";
		List<HashMap<String, String>> archives = new ArrayList<>();
		if (attchments.size() > 0) {
			for (TenderingPlanAttachment attchment : attchments) {
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
				//attMap.put("attchmentType", String.valueOf(attchment.getAttachmentType()));
				String type = String.valueOf(attchment.getAttachmentType());
				if (attchmentType.indexOf(type) == -1) {
					if (StringUtils.isEmpty(attchmentType)) {
						attchmentType = type;
					} else {
						attchmentType += "," + type;
					}
				}
				archives.add(attMap);
			}

		}
		reqMap.put("archives", archives);
		reqMap.put("attchmentType", attchmentType);
		reqMap.put("tenderStatus", String.valueOf(entity.getTenderingStatus()));

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();
		
		logger.info("[OA Audit] No.6 TenderingPlan");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.6 TenderingPlan Audit request success, history data id was " + dataId);
			TenderingPlan tend = tenderingPlanService.find(id);
			tend.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			tend.setmDatetime(new Date());
			tend.setModifier(userName);
			tenderingPlanService.setData(tend);

		}

		return jsonResult;
	}
}
