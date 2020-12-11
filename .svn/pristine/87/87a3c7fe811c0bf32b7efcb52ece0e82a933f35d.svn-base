package project.edge.web.controller.bidding;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.constant.PriceTypeEnum;
import garage.origin.common.constant.TenderingStatusEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
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
import project.edge.common.constant.TenderResultTypeEnum;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.TenderingPlanRelatedunitBeanConverter;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Dept;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.RelatedUnit;
import project.edge.domain.entity.TenderingPlan;
import project.edge.domain.entity.TenderingPlanRelatedunit;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.view.TenderingPlanRelatedunitBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.bidding.TenderingPlanRelatedunitService;
import project.edge.service.bidding.TenderingPlanService;
import project.edge.service.hr.PersonService;
import project.edge.service.org.DeptService;
import project.edge.service.org.RelatedUnitService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;

/**
 * 招标计划明细画面
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/tendering_plan_relatedunit")
public class TenderingPlanRelatedunitController extends TreeDoubleGridUploadController<TenderingPlanRelatedunit, TenderingPlanRelatedunitBean> {

	private static final Logger logger = LoggerFactory.getLogger(TenderingPlanRelatedunitController.class);

	private static final String PID = "P301011";

	private static final String URL_MAP_KEY_EDIT_FILE = "edit-file"; // 上传文件的URL

	private static final String ID_MAP_KEY_VERIFICATION_DIALOG = "edit-verification-form-dialog";

	private static final String JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT = "edit-verification-submit";

	private static final String MODEL_KEY_SOLVE_FIELDS = "verificationFields";
	
	private static final String OPEN_ADD_PURCHASE_JS = "TENDERING_PLAN_RELATEDUNIT.openSelPurchaseDialog()";
	
	@Resource
	CreateWorkFlowManager createWorkFlowManager;
	
	@Autowired
	HttpServletRequest request;
	
	@Resource
	private TenderingPlanRelatedunitService tenderingPlanRelatedunitService;

	@Resource
	private DataOptionService dataOptionService;

	@Resource
	private PurchaseOrderService purchaseOrderService;

	@Resource
	private DeptService deptService;

    @Resource
    private VirtualOrgService virtualOrgService;
	
	@Resource
    private MessageSource messageSource;

	@Resource
	private TenderingPlanService tenderingPlanService;

	@Resource
	private RelatedUnitService relatedUnitService;
	
	@Resource
	private PersonService personService;
	
	@Resource
	private ProjectPersonService projectPersonService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.TENDERING_PLAN_RELATEDUNIT.value();
	}

	@Override
	protected Service<TenderingPlanRelatedunit, String> getDataService() {
		return this.tenderingPlanRelatedunitService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<TenderingPlanRelatedunit, TenderingPlanRelatedunitBean> getViewConverter() {
		return new TenderingPlanRelatedunitBeanConverter();
	}

	

	@Override
	public String getSubDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.TENDERING_PLAN.value();
	}
	
	@Override
	public String getGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.menu.item.bidder", null, locale);
	}

	@Override
	public String getSubGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.menu.item.tender.documents.upload", null, locale);
	}

	@Override
	public OrderByDto getSubGridDefaultOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLinkedFilterFieldName() {
		// TODO Auto-generated method stub
		return "tenderingPlan_";
	}
	
	@Override
    protected boolean subGridUseFile() {
        return true;
    }

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/bidding/tenderingPlanRelatedunitJs.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/bidding/tenderingPlanRelatedunitHidden.jsp";
	}
	
	@Override
	protected String getJsCallbackObjName() {
		return this.getDataType();
	}

	@Override
	protected String getSubGridJsCallbackObjName() {
		return this.getDataType();
	}
	
	@Override
	public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P301005";
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
    protected void createFunctionBean(Page p, List<FunctionBean> functions,
            List<FunctionBean> searchFunctions, Map<String, String> funcGroupMap,
            Map<String, String> jsMap, Locale locale) {

		String pageId = p.getId();
		if (pageId.endsWith(ControllerFunctionIds.ADD_PURCHASE)) {

			this.parseFunction(p, functions, funcGroupMap, "", jsMap.get(ControllerJsMapKeys.ADD_PURCHASE), locale);

		}
	}

	@Override
	public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P301005";
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
		List<String> purchaseTypeList = new ArrayList<>();
		purchaseTypeList.add("PURCHASE_TYPE_2"); // 公开招标
		//purchaseTypeList.add("PURCHASE_TYPE_3"); // 合同签订
		f = new CommonFilter()//.addExact("flowStatus", FlowStatusEnum.REVIEW_PASSED.value()) // 审批通过
				.addWithin("purchaseType.id", purchaseTypeList);
		List<PurchaseOrder> purchaseOrdersList = this.purchaseOrderService.list(f, null);
		if (purchaseOrdersList != null) {
			for (PurchaseOrder purchaseOrders : purchaseOrdersList) {
				purchaseOrderOptions
						.add(new ComboboxOptionBean(purchaseOrders.getId(), purchaseOrders.getPurchaseName()));
			}
		}

		optionMap.put("purchaseOrderOptions", purchaseOrderOptions);

		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.TENDERING_TYPE.value());

		CommonFilter f1 = new CommonFilter().addWithin("dataType", dataTypeList);

		List<ComboboxOptionBean> relatedUnitPropertyOptions = new ArrayList<>();

		List<DataOption> list = this.dataOptionService.list(f1, null);
		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.TENDERING_TYPE.value())) {
				relatedUnitPropertyOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			}
		}
		optionMap.put("TenderingTypeOptions", relatedUnitPropertyOptions);

		// 招标部门
		f = null;
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
		for (TenderingStatusEnum tenderStatus : TenderingStatusEnum.values()) {
			tenderingStatusMode.add(new ComboboxOptionBean(tenderStatus.value().toString(),
					messageSource.getMessage(tenderStatus.resourceName(), null, locale)));
		}
		// 类型
		optionMap.put("TenderingStatus", tenderingStatusMode);

		ArrayList<ComboboxOptionBean> priceTypeEnumMode = new ArrayList<ComboboxOptionBean>();
		for (PriceTypeEnum priceTypeEnum : PriceTypeEnum.values()) {
			priceTypeEnumMode.add(new ComboboxOptionBean(priceTypeEnum.value().toString(),
					messageSource.getMessage(priceTypeEnum.resourceName(), null, locale)));
		}
		optionMap.put("PriceType", priceTypeEnumMode);

		
		// 招标名称
		List<ComboboxOptionBean> tenderingPlanOptions = new ArrayList<>();
        f = null;
        // f.addExact("projectRoleName", DataTypeEnum.PROJECT_ROLE.value());
        List<TenderingPlan> tenderingPlanList = this.tenderingPlanService.list(f, null);
        if (purchaseOrdersList != null) {
            for (TenderingPlan tenderingPlan : tenderingPlanList) {
            	tenderingPlanOptions.add(new ComboboxOptionBean(tenderingPlan.getId(),
                		tenderingPlan.getTenderingName()));
            }
        }
		optionMap.put("tenderingPlanOptions", tenderingPlanOptions);
		
		// 往来单位名称
		List<ComboboxOptionBean> relatedUnitOptions = new ArrayList<>();
        f = null;
        // f.addExact("projectRoleName", DataTypeEnum.PROJECT_ROLE.value());
        List<RelatedUnit> relatedUnitList = this.relatedUnitService.list(f, null);
        if (relatedUnitList != null) {
            for (RelatedUnit relatedUnit : relatedUnitList) {
            	relatedUnitOptions.add(new ComboboxOptionBean(relatedUnit.getId(),
            			relatedUnit.getUnitName()));
            }
        }
		optionMap.put("relatedUnitOptions", relatedUnitOptions);
		
		// 流程状态
		ArrayList<ComboboxOptionBean> flowStatusTypeOptions = new ArrayList<ComboboxOptionBean>();

        for (FlowStatusEnum s : FlowStatusEnum.values()) {
            flowStatusTypeOptions.add(new ComboboxOptionBean(s.value().toString(),
                    messageSource.getMessage(s.resourceName(), null, locale)));
        }

        optionMap.put("FlowStatusProject", flowStatusTypeOptions);
		
        
        // 中标结果
        ArrayList<ComboboxOptionBean> tenderResultOptions = new ArrayList<ComboboxOptionBean>();
        for (TenderResultTypeEnum e : TenderResultTypeEnum.values()) {
        	tenderResultOptions.add(new ComboboxOptionBean(e.value().toString(),
                    messageSource.getMessage(e.resourceName(), null, locale)));
        }
        optionMap.put("tenderResultOptions", tenderResultOptions);
		
		// 项目组
		CommonFilter virtualFilter = null;
        List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
        List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(virtualFilter, null);
        if (virtualOrgList != null) {
            for (VirtualOrg v : virtualOrgList) {
                projectGroupOptions.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
            }
        }
        optionMap.put("projectGroupOptions", projectGroupOptions);
        
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

//		// 打开新建
//		jsMap.put(ControllerJsMapKeys.OPEN_ADD, String.format("CrudUtils.openAddFormDialog('#%1$s', TENDERING_PLAN_RELATEDUNIT);",
//				idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

//		// 新建保存，isFile强制设为false
//		jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
//				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true);",
//						idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
//						this.useFile()));

//		// 打开修改
//		jsMap.put(ControllerJsMapKeys.OPEN_EDIT, String.format("TENDERING_PLAN_RELATEDUNIT.openEditFormDialog('#%1$s', '%2$s');",
//				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), urlMap.get(ControllerUrlMapKeys.FIND)));

//		// 修改保存
//		jsMap.put(JS_MAP_KEY_EDIT_VERIFICATION_SUBMIT,
//				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
//						idMap.get(ID_MAP_KEY_VERIFICATION_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
//						this.useFile()));
		
		// OA审批
        jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT,
                String.format(ControllerMapUtils.OPEN_SUBMIT_AUDIT_JS, "P301011-TENDERING_PLAN-SubDatagrid",
                        urlMap.get(ControllerUrlMapKeys.AUDIT_SUBMIT)));
        
        // OA审核日志
 		jsMap.put(ControllerJsMapKeys.OPEN_AUDIT_LOG, String.format(ControllerMapUtils.OPEN_AUDIT_LOG_JS,
 				idMap.get(ControllerIdMapKeys.OA_AUDIT_LOG_DIALOG), idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));

     	// 采购订单
 		jsMap.put(ControllerJsMapKeys.ADD_PURCHASE, String.format(OPEN_ADD_PURCHASE_JS,
 				idMap.get(ControllerIdMapKeys.ADD_PURCHASE_DIALOG), idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));
 		
		return jsMap;
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

		String view = super.main(paramMap, model, userBean, locale);
		
		// SubGrid多选
		model.addAttribute(ControllerModelKeys.IS_SUB_GRID_SINGLE_SELECT, false);
		model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "TENDERING_PLAN_RELATEDUNIT.handleSelect");
		
		return view;
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
		//return super.find(id, locale);
		JsonResultBean jsonResult = super.find(id, locale);
        try {
            // 获取数据
            List<TenderingPlan> list = this.tenderingPlanService.list(null, null);
            List<ComboboxOptionBean> resultList = new ArrayList<>();
            for (TenderingPlan entity : list) {
                resultList.add(new ComboboxOptionBean(entity.getId(),
                        entity.getTenderingName()));
            }
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);
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
	@SysLogAnnotation(description = "招标计划明细", action = "新增")
	public void create(HttpServletRequest request, HttpServletResponse response, @ModelAttribute TenderingPlanRelatedunitBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
		super.createWithUpload(request, response, bean, null, null, userBean, locale);
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
	@SysLogAnnotation(description = "招标计划明细", action = "更新")
	public void update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute TenderingPlanRelatedunitBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
		super.updateWithUpload(request, response, bean, null, userBean, locale);
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
	@SysLogAnnotation(description = "招标计划明细", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}
	

	/**
	 * 生成招标编号
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/generate-tendering-no")
	@ResponseBody
	public JsonResultBean generateTenderingNo(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			String template = "ZB${yyyyMMdd}xxx";
			int elStart = template.indexOf("${");
			int elEnd = template.lastIndexOf("}");
			String el = template.substring(elStart + 2, elEnd);
			String v = DateUtils.date2String(new Date(), el);

			CommonFilter f = new CommonFilter().addAnywhere("tenderingNo", v);
			List<TenderingPlan> tenderingPlan = this.tenderingPlanService.list(f, null);
			String retstr = "";
			if (tenderingPlan != null) {
				retstr = String.format("%06d", (tenderingPlan.size() + 1));
			} else {
				retstr = "000001";
			}

			String projectNum = template.substring(0, elStart) + v + retstr;

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, projectNum);

		} catch (Exception e) {
			getLogger().error("Exception generate TenderingNo.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}
	
	/**
	 * 新建时自动显示当前用户
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/get-person-info")
	@ResponseBody
	public JsonResultBean getPersonInfo(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			Map<String, String> personInfo = new HashMap<String, String>();
			Person person = personService.find(userBean.getSessionUserId());
			if (person != null) {
				personInfo.put("applicantText", person.getPersonName());
				personInfo.put("applicant_", person.getId());
			} else {
				personInfo.put("applicantText", "");
				personInfo.put("applicant_", "");
			}
			
			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, personInfo);

		} catch (Exception e) {
			getLogger().error("Exception get PersonInfo.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}

}
