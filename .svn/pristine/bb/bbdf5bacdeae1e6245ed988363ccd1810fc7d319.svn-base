package project.edge.web.controller.budget;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import org.apache.commons.net.util.Base64;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ExcelUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.filter.DataFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.FlowDetailBean;
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
import project.edge.common.constant.ProjectGroupEnum;
import project.edge.domain.converter.BudgetEstimateBeanConverter;
import project.edge.domain.converter.BudgetEstimateVersionBeanConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateChange;
import project.edge.domain.entity.BudgetEstimateMain;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.FlowableSetting;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.filter.PageFilter;
//import project.edge.domain.entity.Site;
import project.edge.domain.view.BudgetEstimateBean;
import project.edge.domain.view.BudgetEstimateVersionBean;
import project.edge.domain.view.ProjectBean;
import project.edge.flowable.FlowableManager;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.budget.BudgetEstimateChangeService;
import project.edge.service.budget.BudgetEstimateMainService;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateSumService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.budget.BudgetFeeService;
import project.edge.service.facility.SiteService;
import project.edge.service.flowable.FlowableSettingService;
import project.edge.service.hr.PersonService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;

/**
 * 预算规划管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetEstimate")
public class BudgetEstimateController extends TreeDoubleGridUploadController<BudgetEstimate, BudgetEstimateBean> {

	private static final Logger logger = LoggerFactory.getLogger(BudgetEstimateController.class);

	private static final String PID = "P10003";
	
	private String viewFilterStr = "";

	private static final String ID_MAP_KEY_BUDGET_DIALOG = "edit-budget-form-dialog";
	
	private String VIEW_GRID_DIALOG_ID = "view-grid-dialog-id";
	
	private String VIEW_GRID_DIALOG_URL = "view-grid-dialog-url";

	private static final String MODEL_KEY_SITE_FIELDS = "budgetFields";
	@Autowired
	HttpServletRequest request;
	@Resource
	CreateWorkFlowManager createWorkFlowManager;
	@Resource
	private BudgetEstimateService budgetEstimateService;

	@Resource
	private BudgetEstimateChangeService budgetEstimateChangeService;

	@Resource
	private BudgetEstimateVersionService budgetEstimateVersionService;

	@Resource
	private VirtualOrgService virtualOrgService;

	@Resource
	private ProjectPersonService projectPersonService;

	@Resource
	private SiteService siteService;

	@Resource
	private DataOptionService dataOptionService;

	@Resource
	private BudgetFeeService budgetFeeService;
	
    @Resource
    private ProjectService projectService;
    
	@Resource
	private BudgetEstimateSumService budgetEstimateSumService;
	
    @Resource
    private FlowableSettingService flowableSettingService;
    
    @Resource
    private PersonService personService;
    
    @Resource
    private FlowableManager flowableManager;
    
    @Resource
    private BudgetEstimateMainService budgetEstimateMainService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.BUDGET_ESTIMATE.value();
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected Service<BudgetEstimate, String> getDataService() {
		return this.budgetEstimateService;
	}

	@Override
	protected ViewConverter<BudgetEstimate, BudgetEstimateBean> getViewConverter() {
		return new BudgetEstimateBeanConverter();
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
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/budget/budget.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/budget/budgetHidden.jsp";
	}
	

	@Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();
        
        idMap.put(VIEW_GRID_DIALOG_ID, "P10003_ViewGridDialog");
        
        idMap.put(VIEW_GRID_DIALOG_URL, "/project-edge/budget/budgetEstimate/list.json");

        return idMap;
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
		if (field.getFieldName().equals("proposalArchive_")) { // 建议
			this.putFiledList(model, MODEL_KEY_SITE_FIELDS, field);

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

		List<ComboboxOptionBean> yearOptions = new ArrayList<>();
		yearOptions.add(new ComboboxOptionBean("2019", "2019"));
		yearOptions.add(new ComboboxOptionBean("2020", "2020"));
		yearOptions.add(new ComboboxOptionBean("2021", "2021"));
		yearOptions.add(new ComboboxOptionBean("2022", "2022"));
		yearOptions.add(new ComboboxOptionBean("2023", "2023"));

		CommonFilter f = null;
		List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f, null);
		if (virtualOrgList != null) {
			for (VirtualOrg v : virtualOrgList) {
				projectGroupOptions.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
			}
		}
		
		// 来源类型
		List<ComboboxOptionBean> budgetTypeOptions = new ArrayList<>();
		List<DataOption> list = this.dataOptionService.list(f, null);
		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.BUDGET_TYPE.value())) {
				budgetTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			} 
		}

		optionMap.put("groupOptions", projectGroupOptions);
		optionMap.put("mainGroupOptions", projectGroupOptions);
		optionMap.put("yearOptions", yearOptions);
		optionMap.put("mainYearOptions", yearOptions);
		optionMap.put("budgetTypeOptions", budgetTypeOptions);
		return optionMap;
	}

	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {
		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

		// 上传文件
		jsMap.put(ControllerJsMapKeys.OPEN_IMPORT,
				String.format("BUDGET_ESTIMATE.openUploadFormDialog('#%1$s');", idMap.get(ID_MAP_KEY_BUDGET_DIALOG)));

		// 导出文件
		// jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
		// String.format("BUDGET_ESTIMATE.exportData();"));

		// 导出总表文件
		jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
				String.format("BUDGET_ESTIMATE.openExportFormDialog('#%1$s');", idMap.get(ID_MAP_KEY_BUDGET_DIALOG)));
		
		
        // 删除成功后刷新两个grid
/*        jsMap.put(ControllerJsMapKeys.OPEN_DELETE,
                String.format("BUDGET_ESTIMATE.batchDeleteSelected('%1$s', '#%2$s');",
                        urlMap.get(ControllerUrlMapKeys.DELETE),
                        idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));*/

		// 修改保存
/*		jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT,
				String.format("BUDGET_ESTIMATE.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
						idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						false));*/

/*		if (isOaFlow()) {
			// OA审批
			jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT, String.format("BUDGET.handleAuditClick('#%1$s', '%2$s');",
					idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), urlMap.get(ControllerUrlMapKeys.AUDIT_SUBMIT)));
		} else {
			// 提交审核，暂时不需要审核，直接生成版本
			jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_SUBMIT_AUDIT,
					String.format("BUDGET.buildVersion('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
		}*/
		
		jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_SUBMIT_AUDIT,
				String.format("BUDGET_ESTIMATE.handleAudit('#%1$s');", idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));

		// 打开查看
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_VIEW,
				String.format("BUDGET_ESTIMATE.openViewDialog('#%1$s', '%2$s', '#%3$s', %4$s)", 
						idMap.get(ControllerIdMapKeys.SUB_GRID_VIEW_GRID_DIALOG),
						urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND), idMap.get(ControllerIdMapKeys.SUB_DATAGRID),
						null));
		
		// 打开修改
		jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
	                String.format("BUDGET_ESTIMATE.openEditFormDialog('#%1$s', '%2$s');",
	                        "P10003-BUDGET_ESTIMATE_MAIN-SubGridViewGridDialog",
	                        urlMap.get(ControllerUrlMapKeys.EDIT)));
		
        // 修改保存
        jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT,
                String.format("BUDGET_ESTIMATE.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));
		
		return jsMap;
	}
	
    @Override
    public String getSubDataType() {
        // TODO Auto-generated method stub
        return DataTypeEnum.BUDGET_ESTIMATE_MAIN.value();
    }

    @Override
    public String getGridTitle(Locale locale) {
        // TODO Auto-generated method stub
        return this.messageSource.getMessage("ui.grid.title.budget_estimate.detail", null, locale);
    }

    @Override
    public String getSubGridTitle(Locale locale) {
        // TODO Auto-generated method stub
        return this.messageSource.getMessage("ui.grid.title.budget_estimate.apply", null, locale);
    }

    @Override
    public OrderByDto getSubGridDefaultOrder() {
        // TODO Auto-generated method stub
        return new OrderByDto("cDatetime", true);
    }

    @Override
    public String getLinkedFilterFieldName() {
        // TODO Auto-generated method stub
        return "budgetEstimateMain_";
    }
    
	@Override
    protected boolean subGridUseFile() {
        return false;
    }
    
	@Override
	public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P10024";
		pageFilter.addExact("pid", pid).addExact("isEnabled", OnOffEnum.ON.value()).addExact("isFunction",
				OnOffEnum.ON.value()); // SubGrid画面的、启用的功能[t_page]

		List<Page> pages = this.pageService.list(pageFilter, null); // 默认按照pageOrder字段排序
		ArrayList<FunctionBean> functions = new ArrayList<>();
		ArrayList<FunctionBean> searchFunctions = new ArrayList<>();

		this.prepareSubGridFunctions(pages, functions, searchFunctions, jsMap, locale);

		return functions;
	}
    
	@Override
	public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P10024";
		pageFilter.addExact("pid", pid).addExact("isEnabled", OnOffEnum.ON.value()).addExact("isFunction",
				OnOffEnum.ON.value()); // SubGrid画面的、启用的功能[t_page]

		List<Page> pages = this.pageService.list(pageFilter, null); // 默认按照pageOrder字段排序
		ArrayList<FunctionBean> functions = new ArrayList<>();
		ArrayList<FunctionBean> searchFunctions = new ArrayList<>();

		this.prepareSubGridFunctions(pages, functions, searchFunctions, jsMap, locale);

		return searchFunctions;
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
		model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "BUDGET_ESTIMATE.handleSelect");
		model.addAttribute(ControllerModelKeys.IS_VIEW_HASGRID, true);
		model.addAttribute(ControllerModelKeys.SUB_GRID_DBL_CLICK_ROW_HANDLER, "BUDGET_ESTIMATE.handleDblClickRow");
		
		// 针对双Grid，添加审批通用界面的数据查询接口自定义（目前2类双grid查询url不一样）
    	Map<String, Object> modelMap = model.asMap();
        Map<String, String> urlMap =
                (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);
        String findUrl = urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND);
        urlMap.put("audit-findurl", findUrl);
        
        return r;
	}
	
    

	/**
	 * 获取一览显示的数据，返回JSON格式。
	 * 
	 * @param commonFilterJson
	 *            JSON字符串形式的过滤条件
	 * @param model
	 *            model
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
	 * @throws IOExcption 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "code") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, 
			@RequestParam(required = false, defaultValue = "") String id, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws JsonParseException, JsonMappingException, IOException{
		
		
		
    	CommonFilter f = null;
    	if (StringUtils.isEmpty(id)) {
	    	// 查看弹出层在未渲染的情况下无法修改queryParam，通过内部变量进行保存过滤条件
	    	if (StringUtils.isEmpty(commonFilterJson)) {
	    		commonFilterJson = viewFilterStr;
	    	} else {
	    		viewFilterStr = commonFilterJson;
	    	}
	    	f = this.generateCommonFilter(null, commonFilterJson);
    	} else {
    		// 内部审批查看页在双Grid场合无法筛选数据，增加查询条件 (tags/viewGroupGridLayout)
    		f = new CommonFilter().addExact("budgetEstimateMain.id", id);
    	}
		
		JsonResultBean jsonResult = new JsonResultBean();
		
		CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
		if (commonFilterJson.length() != 0) {
			for (int i = 0; i < filter.getFilterFieldList().size(); i++) {
				if (!filter.getFilterFieldList().get(i).getFieldName().equals("budgetEstimateMain.id")) {
					//此处不能直接返回jsonResult，否则前端会报错，增加空值更新
					jsonResult.setStatus(JsonStatus.OK);
					jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, 0);
					jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, null);
					return jsonResult;
				}
			}
		}
		
		String name = null;
		List<BudgetEstimate> BudgetEstimates = new ArrayList<>();
		List<BudgetEstimate> BudgetEstimateCount = new ArrayList<>();
		List<BudgetEstimateBean> BudgetEstimateBeans = new ArrayList<>();
		PageCtrlDto pages = new PageCtrlDto();
		pages.setPageSize(30);
		pages.setCurrentPage(page);
		List<OrderByDto> orders = new ArrayList<>();
		OrderByDto timeOrder = new OrderByDto();
		timeOrder.setColumnName("cDatetime");
		timeOrder.setDesc(true);
		OrderByDto codeOrder = new OrderByDto();
		codeOrder.setColumnName("code");
		codeOrder.setDesc(false);
		orders.add(timeOrder);
		orders.add(codeOrder);
		BudgetEstimates = this.budgetEstimateService.list(f, orders, pages);
		BudgetEstimateCount = this.budgetEstimateService.list(f, orders);
		
		
		
		for(BudgetEstimate b: BudgetEstimates) {
			BudgetEstimateBean budgetBean = this.getViewConverter().fromEntity(b, this.messageSource, locale);
			
			
			//硬件类才需要计算上一年下一年
			if(!StringUtils.isEmpty(budgetBean.getBudgetType_()) && budgetBean.getBudgetType_().equals("BUDGET_TYPE_1")) {
	        	//code  year  projectid
				CommonFilter df = new CommonFilter().addExact("code", b.getCode()).addExact("version", "V0.0")
						.addExact("year", b.getYear() - 1).addExact("project.id", budgetBean.getProject_());
				if(name != null) {
					df.addExact("name", name);
		    	}
				List<OrderByDto> orders1 = new ArrayList<>();
		        orders.add(new OrderByDto("cDatetime", true));
				List<BudgetEstimate> estimates = this.budgetEstimateService.list(df, orders1);
				
        		budgetBean.setLastToThisPercent("0.00");
				budgetBean.setLastToThisAmount("0.00");
				budgetBean.setLastToThisCount("0.00");
				
				if (!StringUtils.isEmpty(estimates) && estimates.size() > 0) {
					BudgetEstimate estimate = estimates.get(0);
					
					if (!StringUtils.isEmpty(estimate.getPaymentPercent()) && !StringUtils.isEmpty(estimate.getTaxInclusivePrice()) && !StringUtils.isEmpty(estimate.getPaymentCount())) {
						if (Integer.parseInt(estimate.getPaymentPercent()) != 100) {
							int paymentPercent = 100 - Integer.parseInt(estimate.getPaymentPercent());
			        		budgetBean.setLastToThisPercent(String.valueOf(paymentPercent));
//							budgetBean.setLastToThisAmount(paymentPercent * estimate.getTaxInclusivePrice() * estimate.getPaymentCount() / 100);
							//budgetBean.setLastToThisAmount(estimate.getTaxTotalPrice() - estimate.getPaymentAmount());
							BigDecimal LastYearAmount = new BigDecimal(Double.toString(estimate.getTaxTotalPrice())).subtract(new BigDecimal(Double.toString(estimate.getPaymentAmount())));
							budgetBean.setLastToThisAmount(String.valueOf(LastYearAmount.doubleValue()));
							budgetBean.setLastToThisCount(String.valueOf(estimate.getPaymentCount()));
			        	}
					}
				}
	        }
			else {
				budgetBean.setLastToThisPercent("");
				budgetBean.setLastToThisAmount("");
				budgetBean.setLastToThisCount("");
			}
			
			
			
//	    	CommonFilter bf1 = new CommonFilter().addExact("year", 2019).addExact("project.id", projectId).addExact("version", "V0.0").addExact("code", b.getCode());
//	    	if(name != null) {
//	    		bf1 = bf1.addExact("name", name);
//	    	}
//	    	List<BudgetEstimate> budgetEstimates1 = this.budgetEstimateService.list(bf1, null);
//	    	if(budgetEstimates1.size() > 0) {
//		    	budgetBean.setCount2019(budgetEstimates1.get(0).getCount());
//		    	budgetBean.setPriceTotal2019(budgetEstimates1.get(0).getTaxTotalPrice());
//	    	}
			
			BudgetEstimateBeans.add(budgetBean);
		}
		
		jsonResult.setStatus(JsonStatus.OK);
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, BudgetEstimateCount.size());
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, BudgetEstimateBeans);
		
		return jsonResult;
		
//		sort = "code";
//        jsonResult = super.list(commonFilterJson, f, page, rows, sort, order, locale);
//        return jsonResult;
		
		
		/*JsonResultBean jsonResult = new JsonResultBean();
		List<BudgetEstimate> BudgetEstimates = new ArrayList<>();
		List<BudgetEstimate> BudgetEstimateCount = new ArrayList<>();
		List<BudgetEstimateBean> BudgetEstimateBeans = new ArrayList<>();
		String year = null;
		String projectId = null;
		String groupId = null;
		String name = null;
		int flag = 1;
		
		CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
		if (commonFilterJson.length() != 0) {
			for (int i = 0; i < filter.getFilterFieldList().size(); i++) {
				if (filter.getFilterFieldList().get(i).getFieldName().equals("year")) {
					year = filter.getFilterFieldList().get(i).getValue().toString();
				} else if (filter.getFilterFieldList().get(i).getFieldName().equals("project.id")) {
					projectId = filter.getFilterFieldList().get(i).getValue().toString();
				} else if (filter.getFilterFieldList().get(i).getFieldName().equals("group.id")) {
					groupId = filter.getFilterFieldList().get(i).getValue().toString();
				} else if (filter.getFilterFieldList().get(i).getFieldName().equals("name")) {
					name = filter.getFilterFieldList().get(i).getValue().toString();
				}
			}
		}
		
		//默认选中当年
		if(year==null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			year = sdf.format(date);
		}
		
		if (StringUtils.isEmpty(projectId) || projectId == null) {
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, 0);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, BudgetEstimateBeans);
			return jsonResult;
		}
		
		//当前用户所在组
		CommonFilter rf = new CommonFilter();
		rf = new CommonFilter().addExact("person.id", userBean.getSessionUserId()).addExact("project.id", projectId);
		List<ProjectPerson> projectPersonList = this.projectPersonService.list(rf, null);
		
		if(projectPersonList.size()==0) {
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, 0);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, BudgetEstimateBeans);
			return jsonResult;
		}
		
		
		//该用户有权限
		CommonFilter f = new CommonFilter().addExact("version", "V0.0").addExact("year",Integer.parseInt(year)).addExact("project.id", projectId);
		if(groupId != null) {
			f = f.addExact("group.id", groupId);
		}
		if(name != null) {
			f = f.addExact("name", name);
		}
		if(userBean.getIsSuper()==false) {
			if(projectPersonList.get(0).getProjectRole().getLevel()!=1) {//当前用户不是指定角色，只能查看本专业组数据
				f = f.addExact("group.id", projectPersonList.get(0).getVirtualOrg().getId());
				flag = 0;
			}
		}

		PageCtrlDto pages = new PageCtrlDto();
		pages.setPageSize(30);
		pages.setCurrentPage(page);
		List<OrderByDto> orders = new ArrayList<>();
		OrderByDto timeOrder = new OrderByDto();
		timeOrder.setColumnName("cDatetime");
		timeOrder.setDesc(true);
		OrderByDto codeOrder = new OrderByDto();
		codeOrder.setColumnName("code");
		codeOrder.setDesc(false);
		orders.add(timeOrder);
		orders.add(codeOrder);
		BudgetEstimates = this.budgetEstimateService.list(f, orders, pages);
		BudgetEstimateCount = this.budgetEstimateService.list(f, orders);
		
		for(BudgetEstimate b: BudgetEstimates) {
			
			BudgetEstimateBean budgetBean = this.getViewConverter().fromEntity(b, this.messageSource, locale);
			
	    	CommonFilter bf1 = new CommonFilter().addExact("year", 2019).addExact("project.id", projectId).addExact("version", "V0.0").addExact("code", b.getCode());
	    	if(name != null) {
	    		bf1 = bf1.addExact("name", name);
	    	}
	    	List<BudgetEstimate> budgetEstimates1 = this.budgetEstimateService.list(bf1, null);
	    	if(budgetEstimates1.size() > 0) {
		    	budgetBean.setCount2019(budgetEstimates1.get(0).getCount());
		    	budgetBean.setPriceTotal2019(budgetEstimates1.get(0).getTaxTotalPrice());
	    	}
	    	
	    	CommonFilter bf2 = new CommonFilter().addExact("year", 2020).addExact("project.id", projectId).addExact("version", "V0.0").addExact("code", b.getCode());
	    	if(name != null) {
	    		bf1 = bf1.addExact("name", name);
	    	}
	    	List<BudgetEstimate> budgetEstimates2 = this.budgetEstimateService.list(bf2, null);
	    	if(budgetEstimates2.size() > 0) {
		    	budgetBean.setCount2020(budgetEstimates2.get(0).getCount());
		    	budgetBean.setPriceTotal2020(budgetEstimates2.get(0).getTaxTotalPrice());
	    	}
	    	
	    	CommonFilter bf3 = new CommonFilter().addExact("year", 2021).addExact("project.id", projectId).addExact("version", "V0.0").addExact("code", b.getCode());
	    	if(name != null) {
	    		bf1 = bf1.addExact("name", name);
	    	}
	    	List<BudgetEstimate> budgetEstimates3 = this.budgetEstimateService.list(bf3, null);
	    	if(budgetEstimates3.size() > 0) {
		    	budgetBean.setCount2021(budgetEstimates3.get(0).getCount());
		    	budgetBean.setPriceTotal2021(budgetEstimates3.get(0).getTaxTotalPrice());
	    	}
	    	
	    	CommonFilter bf4 = new CommonFilter().addExact("year", 2022).addExact("project.id", projectId).addExact("version", "V0.0").addExact("code", b.getCode());
	    	if(name != null) {
	    		bf1 = bf1.addExact("name", name);
	    	}
	    	List<BudgetEstimate> budgetEstimates4 = this.budgetEstimateService.list(bf4, null);
	    	if(budgetEstimates4.size() > 0) {
		    	budgetBean.setCount2022(budgetEstimates4.get(0).getCount());
		    	budgetBean.setPriceTotal2022(budgetEstimates4.get(0).getTaxTotalPrice());
	    	}
	    	
	    	CommonFilter bf5 = new CommonFilter().addExact("year", 2023).addExact("project.id", projectId).addExact("version", "V0.0").addExact("code", b.getCode());
	    	if(name != null) {
	    		bf1 = bf1.addExact("name", name);
	    	}
	    	List<BudgetEstimate> budgetEstimates5 = this.budgetEstimateService.list(bf5, null);
	    	if(budgetEstimates5.size() > 0) {
		    	budgetBean.setCount2023(budgetEstimates5.get(0).getCount());
		    	budgetBean.setPriceTotal2023(budgetEstimates5.get(0).getTaxTotalPrice());
	    	}
			
			BudgetEstimateBeans.add(budgetBean);
		}
		
		jsonResult.setStatus(JsonStatus.OK);
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, BudgetEstimateCount.size());
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, BudgetEstimateBeans);
		
		return jsonResult;*/
	}

	/**
	 * 获取变更过的一览显示的数据，返回JSON格式。
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
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	@RequestMapping("/changeList")
	@ResponseBody
	public JsonResultBean changeList(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "code") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale)
			throws JsonParseException, JsonMappingException, IOException {

		CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
		String year = null;
		String projectId = null;

		if (commonFilterJson.length() != 0) {
			for (int i = 0; i < filter.getFilterFieldList().size(); i++) {
				if (filter.getFilterFieldList().get(i).getFieldName().equals("year")) {
					year = filter.getFilterFieldList().get(i).getValue().toString();
				} else if (filter.getFilterFieldList().get(i).getFieldName().equals("project.id")) {
					projectId = filter.getFilterFieldList().get(i).getValue().toString();
				}
			}
		}

		// 如果没搜索年份，默认当年
		if (year == null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			year = sdf.format(date);
		}

		JsonResultBean jsonResult = new JsonResultBean();
		CommonFilter f = new CommonFilter();

		if (commonFilterJson.length() != 0) {
			if (!StringUtils.isEmpty(projectId) && !StringUtils.isEmpty(year)) {
				List<BudgetEstimate> budgetList = this.budgetEstimateService.getBudgetByYear(Integer.parseInt(year),
						projectId);
				if (budgetList.size() > 0) {
					if (budgetList.get(0) != null) {
						f = f.addExact("version", budgetList.get(0).getVersion()).addExact("year", Integer.parseInt(year));
						jsonResult = super.list(commonFilterJson, f, page, rows, "code", "asc", locale);
					}
				} else {
					// 任意设定version，保证查不到数据就行
					f = f.addExact("version", "V1.1");
					jsonResult = super.list(commonFilterJson, f, page, rows, "code", "asc", locale);
				}
			}
		} else {
			f = f.addExact("version", "V1.1");
			jsonResult = super.list(commonFilterJson, f, page, rows, "code", "asc", locale);
		}

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
	@ResponseBody
	@SysLogAnnotation(description = "预算规划管理", action = "新增")
	public JsonResultBean create(@ModelAttribute BudgetEstimateBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
		return super.create(bean, null, userBean, locale);
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
	@SysLogAnnotation(description = "预算规划管理", action = "更新")
	public JsonResultBean update(@ModelAttribute BudgetEstimateBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean budgetJson = super.find(bean.getId(), locale);
		String idBak = bean.getId();
		BudgetEstimateBean budgetEstimateBean = new BudgetEstimateBean();
		budgetEstimateBean = (BudgetEstimateBean) budgetJson.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);
		BudgetEstimateBeanConverter converter = new BudgetEstimateBeanConverter();
		Date now = new Date();
		bean.setProject_(budgetEstimateBean.getProject_());
		BudgetEstimate budgetEstimate = converter.toEntity(bean, null, userBean, now);
		bean.setId(idBak);
		String innerCodeBak = budgetEstimateBean.getInnerCode();
		bean.setInnerCode(innerCodeBak);
		budgetEstimate.setInnerCode(innerCodeBak);
		
		//code  year  projectid
		DataFilter df = new CommonFilter().addExact("code", budgetEstimate.getCode()).addExact("version", "V0.0")
				.addExact("year", budgetEstimate.getYear() - 1).addExact("project.id", bean.getProject_());
		List<OrderByDto> orders = new ArrayList<>();
        orders.add(new OrderByDto("cDatetime", true));
		List<BudgetEstimate> estimates = this.budgetEstimateService.list(df, orders);
		
		
		BigDecimal lastToThisAmount = new BigDecimal(0);
		Double payAmountTotal = 0.0;
		
		if (!StringUtils.isEmpty(estimates)) {
			BudgetEstimate estimate = estimates.get(0);
			
			//硬件类需要计算上一年下一年
			if(!StringUtils.isEmpty(estimate.getId()) && estimate.getId().equals("BUDGET_TYPE_1")) {
				bean.setLastToThisPercent("0.00");
				bean.setLastToThisAmount("0.00");
				bean.setLastToThisCount(String.valueOf(estimate.getCount()));
				
				if (!StringUtils.isEmpty(estimate) && !StringUtils.isEmpty(estimate.getPaymentAmount())) {
					lastToThisAmount = lastToThisAmount.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(estimate.getTaxTotalPrice())?0:estimate.getTaxTotalPrice())))
							.subtract(new BigDecimal(String.valueOf(StringUtils.isEmpty(estimate.getPaymentAmount())?0:estimate.getPaymentAmount())));
				}
			}
			else {
				bean.setLastToThisPercent("");
				bean.setLastToThisAmount("");
				bean.setLastToThisCount("");
			}
		}
		
		//当年支付总金额
		if(bean.getPaymentAmount() != null) {
			payAmountTotal = bean.getPaymentAmount();
		}
		
		//硬件类需要计算上一年下一年
		if(!StringUtils.isEmpty(bean.getId()) && bean.getId().equals("BUDGET_TYPE_1")) {
			bean.setPayAmountTotal(lastToThisAmount.doubleValue() + payAmountTotal);
		}
		else {
			bean.setPayAmountTotal(payAmountTotal);
		}
		
		JsonResultBean jsonResult = new JsonResultBean();
		jsonResult = super.update(bean, null, userBean, locale);
		
		if(jsonResult.getStatus() == 1) {//修改子表记录后，将主表记录审核状态改成未审核
            
			BudgetEstimateMain main = this.budgetEstimateMainService.find(budgetEstimateBean.getBudgetEstimateMain_());
			main.setFlowStatus(0);
			main.setmDatetime(now);
			this.budgetEstimateMainService.update(main);
            
		}
		
		BigDecimal totalDiff = null;
		int totalFlag = 0;
		int payTotalFlag = 0;
		Double totalBefore = new Double(0);
		Double totalAfter = new Double(0); 
		Double payTotalBefore = new Double(0); 
		Double payTotalAfter = new Double(0); 
		
		// 新数据总价和原数据比较，算出差值--------更改之后，直接将上级节点的合计通过查询所有子节点的合计相加得到
		if(budgetEstimateBean.getTaxInclusivePriceTotal() != null) {
			totalBefore = budgetEstimateBean.getTaxInclusivePriceTotal();
		}
		if(bean.getTaxInclusivePriceTotal() != null) {
			totalAfter = bean.getTaxInclusivePriceTotal();
		}
		if(budgetEstimateBean.getPaymentAmount() != null) {
			payTotalBefore = budgetEstimateBean.getPaymentAmount();
		}
		if(bean.getPaymentAmount() != null) {
			payTotalAfter = bean.getPaymentAmount();
		}
		
		if(totalBefore.compareTo(totalAfter) != 0) {
			totalFlag = 1;
		}
		if(payTotalBefore.compareTo(payTotalAfter) != 0) {
			payTotalFlag = 1;
		}

		if(totalFlag != 0) {
			// 预算规划表中除本条以外的上面几级记录的合计
			boolean updateTotal = updateTotal(0, totalFlag, totalDiff, bean.getInnerCode(),
					bean.getYear(), bean.getProject_(), locale, userBean);
		}
		if(payTotalFlag != 0) {
			updatePayAmountTotal(bean.getInnerCode(), bean.getYear(), bean.getProject_(), bean.getCode(),
					locale, userBean);
		}
		
		/*更新主表总价*/
		
		BudgetEstimate budget = this.budgetEstimateService.find(idBak);
		
		List<OrderByDto> orders1 = new ArrayList<>();
		OrderByDto order1 = new OrderByDto();
		order1.setColumnName("code");
		order1.setDesc(false);
		orders1.add(order1);
    	CommonFilter mf = new CommonFilter().addExact("budgetEstimateMain.id", budget.getBudgetEstimateMain().getId());
    	List<BudgetEstimate> budgetList = budgetEstimateService.list(mf, orders1);
    	
    	
    	
    	List<BudgetEstimateBean> BudgetEstimateBeans = new ArrayList<>();
    	for(BudgetEstimate b: budgetList) {
    		BudgetEstimateBean budgetBean = this.getViewConverter().fromEntity(b, this.messageSource, locale);
    		//code  year  projectid
    		DataFilter df1 = new CommonFilter().addExact("code", b.getCode()).addExact("version", "V0.0")
    				.addExact("year", b.getYear() - 1).addExact("project.id", budgetBean.getProject_());
    		List<OrderByDto> orders2 = new ArrayList<>();
            orders2.add(new OrderByDto("cDatetime", true));
    		List<BudgetEstimate> lastEstimates = this.budgetEstimateService.list(df1, orders2);
    		
    		if (!StringUtils.isEmpty(lastEstimates)) {
    			BudgetEstimate estimate = lastEstimates.get(0);
    			
    			//硬件类需要计算上一年下一年
    			if(!StringUtils.isEmpty(estimate.getId()) && estimate.getId().equals("BUDGET_TYPE_1")) {
    				budgetBean.setLastToThisPercent("0.00");
    				budgetBean.setLastToThisAmount("0.00");
        			budgetBean.setLastToThisCount(String.valueOf(estimate.getCount()));
        			if (!StringUtils.isEmpty(estimate.getPaymentPercent())) {
        				int paymentPercent = 100 - Integer.parseInt(estimate.getPaymentPercent());
        				budgetBean.setLastToThisPercent(String.valueOf(paymentPercent));
        				budgetBean.setLastToThisAmount(String.valueOf(estimate.getPaymentCount()* estimate.getTaxInclusivePrice() * paymentPercent / 100));
        			}
    			}
    			else {
    				bean.setLastToThisPercent("");
    				bean.setLastToThisAmount("");
    				bean.setLastToThisCount("");
    			}
    		}
    		BudgetEstimateBeans.add(budgetBean);
    	}
    	changeTotal(BudgetEstimateBeans,budget.getBudgetEstimateMain().getId());
    	

/*		// 是否生成过版本
		int versionFlag = 0;
		
		List<OrderByDto> orders1 = new ArrayList<>();
		OrderByDto order1 = new OrderByDto();
		order1.setColumnName("cDatetime");
		order1.setDesc(true);
		orders1.add(order1);
    	CommonFilter vf = new CommonFilter().addExact("year", budgetEstimateBean.getYear()).addExact("project.id", budgetEstimateBean.getProject_());
    	List<BudgetEstimateVersion> versionList = budgetEstimateVersionService.list(vf, orders1);
    	
		if (versionList.size() > 0) {
			versionFlag = 1;
			
			// 如果已生成过版本，就取出最新版本中的计划备注,只需在资金计划录入一遍就行，无需重复录入
			CommonFilter rf = new CommonFilter();
			rf.addExact("code", budgetEstimateBean.getCode()).addExact("version", versionList.get(0).getId());
			List<BudgetEstimate> budgetList = budgetEstimateService.list(rf, null);
			
			budgetEstimate.setPlanRemark(budgetList.get(0).getPlanRemark());
			
		}

		// 更新预算规划表和变更表中除本条以外的上面几级记录的合计
		boolean updateTotal = updateTotal(versionFlag, totalFlag, totalDiff, budgetEstimateBean.getInnerCode(),
				budgetEstimateBean.getYear(), budgetEstimateBean.getProject_(), locale, userBean);

		// 如果已生成过版本，就将修改的该条记录插到变更表中
		if (versionFlag == 1) {
			// 根据code和version在预算规划变更表中查找是否存在相同的记录，有的话就删除
			DataFilter df = new CommonFilter().addExact("innerCode", innerCodeBak).addExact("version", "V0.0")
					.addNull("auditStatus").addExact("year", budgetEstimateBean.getYear());
			List<OrderByDto> orders = new ArrayList<>();
			OrderByDto order = new OrderByDto();
			order.setColumnName("id");
			orders.add(order);
			PageCtrlDto page = new PageCtrlDto();
			page.setPageSize(10);
			page.setCurrentPage(1);
			List<BudgetEstimateChange> changeList = budgetEstimateChangeService.list(df, orders, page);
			if (changeList.size() > 0) {
				budgetEstimateChangeService.delete(changeList.get(0));
			}

			// 将修改的数据插到变更表中
			insertToChange(budgetEstimate, userBean, new Date());
		}*/
		

		return jsonResult;
		
	}

	public boolean updateTotal(int versionFlag, int totalFlag, BigDecimal totalDiff, String innerCode, int year, String projectId,
			Locale locale, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
		
		CommonFilter f = null;
		f = new CommonFilter().addExact("code", innerCode).addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetEstimateList = this.budgetEstimateService.list(f, null);
		
		CommonFilter f1 = new CommonFilter().addExact("innerCode", innerCode).addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetDetailList = this.budgetEstimateService.list(f1, null);
		
		if(budgetEstimateList.size() > 0) {
/*			BigDecimal total = new BigDecimal(0);
			if(budgetEstimateList.get(0).getTaxTotalPrice() != null) {
				System.out.println(budgetEstimateList.get(0).getCode());
				System.out.println(budgetEstimateList.get(0).getTaxTotalPrice()); 
				total = new BigDecimal(budgetEstimateList.get(0).getTaxTotalPrice());
			}
			if (totalFlag == 2) {
				budgetEstimateList.get(0).setTaxTotalPrice(total.subtract(totalDiff).doubleValue());
			} else if (totalFlag == 1) {
				budgetEstimateList.get(0).setTaxTotalPrice(total.add(totalDiff).doubleValue());
			}*/
			
			if(budgetDetailList.size()>0) {
				BigDecimal maxNode = new BigDecimal(0);
				BigDecimal maxPaymentAmount = new BigDecimal(0);
				BigDecimal maxLastToThis = new BigDecimal(0);
				for(BudgetEstimate detail : budgetDetailList) {
					//总价
					maxNode = maxNode.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(detail.getTaxTotalPrice())?0:detail.getTaxTotalPrice())));
					
					//当年支付金额
					maxPaymentAmount = maxPaymentAmount.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(detail.getPaymentAmount())?0:detail.getPaymentAmount())));
					
/*			        //上一年转至今年
			        Double lastToThisAmount = 0.0;
					if (!StringUtils.isEmpty(detail.getPaymentPercent()) && !StringUtils.isEmpty(detail.getTaxInclusivePrice()) && !StringUtils.isEmpty(detail.getPaymentCount())) {
						if (Integer.parseInt(detail.getPaymentPercent()) != 100) {
							int paymentPercent = 100 - Integer.parseInt(detail.getPaymentPercent());
							lastToThisAmount = paymentPercent * detail.getTaxInclusivePrice() * detail.getPaymentCount() / 100;
						}
					}
					maxLastToThis = maxLastToThis.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(lastToThisAmount)?0:lastToThisAmount)));*/
				}
				budgetEstimateList.get(0).setTaxTotalPrice(maxNode.doubleValue());
				//budgetEstimateList.get(0).setPaymentAmount(maxPaymentAmount.doubleValue());
				
				// 更新预算规划
				budgetEstimateService.update(budgetEstimateList.get(0));
			}

			// 如果已生成过版本，就插到变更表
			if (versionFlag == 1) {
				insertToChange(budgetEstimateList.get(0), userBean, new Date());
			}
			
			if (!StringUtils.isEmpty(budgetEstimateList.get(0).getInnerCode())) {
				return updateTotal(versionFlag, totalFlag, totalDiff, budgetEstimateList.get(0).getInnerCode(), year, projectId, 
						locale, userBean);
			}
		}
		
		return false;
	}
	
	public boolean updatePayAmountTotal(String innerCode, int year, String projectId, String code,
			Locale locale, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
		
		// 获取上级节点
		CommonFilter f = null;
		f = new CommonFilter().addExact("code", innerCode).addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetEstimateList = this.budgetEstimateService.list(f, null);
		
		// 获取上级节点为innerCode的所有下级节点
		CommonFilter f1 = new CommonFilter().addExact("innerCode", innerCode).addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetDetailList = this.budgetEstimateService.list(f1, null);
		
		if(budgetEstimateList.size() > 0) {
			
			if(budgetDetailList.size()>0) {
				BigDecimal maxNode = new BigDecimal(0);
//				BigDecimal maxPaymentAmount = new BigDecimal(0);
//				BigDecimal maxLastToThis = new BigDecimal(0);
				for(BudgetEstimate detail : budgetDetailList) {
					//总价
					if (!StringUtils.isEmpty(detail.getPayAmountTotal())) {
						maxNode = maxNode.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(detail.getPayAmountTotal())?0:detail.getPayAmountTotal())));
					}
					
//					// 获取code的上一年数据
//					CommonFilter f2 = new CommonFilter().addExact("code", detail.getCode()).addExact("version", "V0.0").addExact("year", year-1).addExact("project.id", projectId);
//					List<BudgetEstimate> budgetDetailList2 = this.budgetEstimateService.list(f2, null);
//					
//					if (!StringUtils.isEmpty(budgetDetailList2) && budgetDetailList2.size() > 0) {
//						BudgetEstimate lastBudgetEstimate = budgetDetailList2.get(0);
//						if (!StringUtils.isEmpty(lastBudgetEstimate) && !StringUtils.isEmpty(lastBudgetEstimate.getPaymentAmount())) {
//							maxNode = maxNode.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(lastBudgetEstimate.getTaxTotalPrice())?0:lastBudgetEstimate.getTaxTotalPrice())))
//									.subtract(new BigDecimal(String.valueOf(StringUtils.isEmpty(lastBudgetEstimate.getPaymentAmount())?0:lastBudgetEstimate.getPaymentAmount())));
//						}
//					}
					
				}
				
				
				
				budgetEstimateList.get(0).setPayAmountTotal(maxNode.doubleValue());
				
				// 更新预算规划
				budgetEstimateService.update(budgetEstimateList.get(0));
			}

			// 如果已生成过版本，就插到变更表
//			if (versionFlag == 1) {
//				insertToChange(budgetEstimateList.get(0), userBean, new Date());
//			}
			
			if (!StringUtils.isEmpty(budgetEstimateList.get(0).getInnerCode())) {
				return updatePayAmountTotal(budgetEstimateList.get(0).getInnerCode(), year, projectId, code,
						locale, userBean);
			}
		}
		
		return false;
	}

	public void insertToChange(@ModelAttribute BudgetEstimate budgetEstimate,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Date now) {
		
		BudgetEstimateChange change = new BudgetEstimateChange();
		change.setProject(budgetEstimate.getProject());
		change.setCode(budgetEstimate.getCode());
		change.setInnerCode(budgetEstimate.getInnerCode());
		change.setOrderNumber(budgetEstimate.getOrderNumber());
		change.setName(budgetEstimate.getName());
		change.setKeyPerformance(budgetEstimate.getKeyPerformance());
		change.setBrand(budgetEstimate.getBrand());
		change.setVersion(budgetEstimate.getVersion());
		change.setGroup(budgetEstimate.getGroup());
		change.setMeasurementUnit(budgetEstimate.getMeasurementUnit());
		change.setCount(budgetEstimate.getCount());
		change.setTaxInclusivePrice(budgetEstimate.getTaxInclusivePrice());
		change.setTaxExcludingPrice(budgetEstimate.getTaxExcludingPrice());
		change.setTaxTotalPrice(budgetEstimate.getTaxTotalPrice());
		change.setRemarks(budgetEstimate.getRemarks());
		change.setYear(budgetEstimate.getYear());
		change.setTaxPoint(budgetEstimate.getTaxPoint());
		change.setDeviceClassify(budgetEstimate.getDeviceClassify());
		change.setPersonMonth(budgetEstimate.getPersonMonth());
		change.setPersonPrice(budgetEstimate.getPersonPrice());
		change.setCreator(userBean.getSessionUserId());
		change.setcDatetime(now);
		change.setmDatetime(now);
		
		
		//如果变更表里存在记录，则更新，否则新增
		CommonFilter f = null;
		f = new CommonFilter().addExact("code", budgetEstimate.getCode());
		List<BudgetEstimateChange> budgetEstimateChangeList = this.budgetEstimateChangeService.list(f, null);
		
		if(budgetEstimateChangeList.size() == 0) {
			budgetEstimateChangeService.create(change);
		}
		else {
			change.setId(budgetEstimateChangeList.get(0).getId());
			budgetEstimateChangeService.update(change);
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
	@SysLogAnnotation(description = "预算规划管理", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}

	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(String commonFilterJson, CommonFilter addedFilter, File serverFile,
			HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub
		super.exportExcel(commonFilterJson, addedFilter, serverFile, response, locale);
	}

	@RequestMapping("/import-excel-file")
	public void validateSourceModelType(@RequestParam String projectId,@RequestParam String mainId,HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		
		BudgetEstimateMain budgetEstimateMain = this.budgetEstimateMainService.find(mainId);
		String year = budgetEstimateMain.getYear().toString();

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
					List<BudgetEstimateBean> beans = this.parseExcelFileToBudgetEstimateBeans(projectId, mainId, year, tempFile,
							userBean, locale);

					if (!beans.isEmpty()) {
						BudgetEstimateBeanConverter converter = new BudgetEstimateBeanConverter();

						Date now = new Date();

						String planName = "预算规划_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);

						CommonFilter filter = new CommonFilter();
						filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());

						for (BudgetEstimateBean bean : beans) {
							
							BudgetEstimate budgetEstimate = converter.toEntity(bean, null, userBean, now);

							// 根据code和version查找是否存在相同的记录
							CommonFilter f = new CommonFilter().addExact("code", bean.getCode())
									.addExact("version", "V0.0").addExact("year", Integer.parseInt(year)).addExact("project.id", bean.getProject_());
							JsonResultBean budgetJson = super.list(null, f, 1, 9999, "code", "asc", locale);
							List<BudgetEstimateBean> resultList = new ArrayList<>();
							resultList = (List<BudgetEstimateBean>) budgetJson.getDataMap()
									.get(JsonResultBean.KEY_EASYUI_ROWS);
							
							BigDecimal totalDiff = null;
							int totalFlag = 0;
							Double totalBefore = new Double(0);
							Double totalAfter = new Double(0); 
							
							if (resultList.size() > 0) {
								// 新数据总价和原数据比较，算出差值--------更改之后，直接将上级节点的合计通过查询所有子节点的合计相加得到
								if(resultList.get(0).getTaxInclusivePriceTotal() != null) {
									totalBefore = resultList.get(0).getTaxInclusivePriceTotal();
								}
								if(bean.getTaxInclusivePriceTotal() != null) {
									totalAfter = bean.getTaxInclusivePriceTotal();
								}
								if(totalBefore.compareTo(totalAfter) != 0) {
									totalFlag = 1;
								}
								
/*								if(bean.getTaxInclusivePriceTotal() != null) {
									totalAfter = new BigDecimal(Double.toString(bean.getTaxInclusivePriceTotal()));
								}
								if (totalBefore.compareTo(totalAfter) > 0) {// 修改的值比原数据小
									totalDiff = totalBefore.subtract(totalAfter);
									totalFlag = 2;
								} else if (totalBefore.compareTo(totalAfter) < 0) {// 修改的值比原数据大
									totalDiff = totalAfter.subtract(totalBefore);
									totalFlag = 1;
								}*/
								
								
								// resultList = (List<BudgetEstimateBean>) ;
								this.delete(resultList.get(0).getId(), userBean, locale);
							}

							this.budgetEstimateService.create(budgetEstimate);
							
							if(totalFlag != 0) {
								// 预算规划表中除本条以外的上面几级记录的合计
								boolean updateTotal = updateTotal(0, totalFlag, totalDiff, budgetEstimate.getInnerCode(),
										budgetEstimate.getYear(), budgetEstimate.getProject().getId(), locale, userBean);
							}
							
							//code  year  projectid
				    		DataFilter df1 = new CommonFilter().addExact("code", bean.getCode()).addExact("version", "V0.0")
				    				.addExact("year", bean.getYear() - 1).addExact("project.id", bean.getProject_());
				    		List<OrderByDto> orders2 = new ArrayList<>();
				            orders2.add(new OrderByDto("cDatetime", true));
				    		List<BudgetEstimate> lastEstimates = this.budgetEstimateService.list(df1, orders2);
				    		
				    		if (!StringUtils.isEmpty(lastEstimates) && lastEstimates.size() > 0) {
				    			BudgetEstimate estimate = lastEstimates.get(0);
				    			
				    			//硬件类需要计算上一年下一年
				    			if(!StringUtils.isEmpty(estimate.getId()) && estimate.getId().equals("BUDGET_TYPE_1")) {
				    				bean.setLastToThisPercent("0.00");
				    				bean.setLastToThisAmount("0.00");
					    			bean.setLastToThisCount(String.valueOf(estimate.getCount()));
					    			if (!StringUtils.isEmpty(estimate.getPaymentPercent()) && !StringUtils.isEmpty(estimate.getPaymentCount()) && !StringUtils.isEmpty(estimate.getTaxInclusivePrice())) {
					    				int paymentPercent = 100 - Integer.parseInt(estimate.getPaymentPercent());
					    				bean.setLastToThisPercent(String.valueOf(paymentPercent));
					    				bean.setLastToThisAmount(String.valueOf(estimate.getPaymentCount()* estimate.getTaxInclusivePrice() * paymentPercent / 100));
					    			}
				    			}
				    			else {
				    				bean.setLastToThisPercent("");
				    				bean.setLastToThisAmount("");
				    				bean.setLastToThisCount("");
				    			}
				    		}
							
							changeTotal(beans,mainId);
							
							updatePayAmountTotal(bean.getInnerCode(), bean.getYear(), bean.getProject_(), bean.getCode(),
									locale, userBean);
							
						}
					}
					// 准备JSON结果
					jsonResult.setStatus(JsonStatus.OK);
					jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beans);
				}
			}
		} catch (Exception e) {
			logger.error("Exception while import excel file.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		try {
			if (jsonResult.getStatus() == 1) {
				jsonResult.setMessage("数据导入成功");
				
				//修改子表记录后，将主表记录审核状态改成未审核
				BudgetEstimateMain main = this.budgetEstimateMainService.find(mainId);
				main.setFlowStatus(0);
				Date now = new Date();
				main.setmDatetime(now);
				this.budgetEstimateMainService.update(main);
		            
			} else {
				jsonResult.setMessage("数据导入失败");
			}
			String result = new ObjectMapper().writeValueAsString(jsonResult);
			response.getWriter().write("<textarea>" + result + "</textarea>");
		} catch (Exception e) {
			logger.error("Exception while converting object to json string.", e);
		}
	}

	private List<BudgetEstimateBean> parseExcelFileToBudgetEstimateBeans(String projectId, String mainId, String year, File tempFile,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale)
			throws FileNotFoundException, IOException {

		// 当前用户所属项目组
		CommonFilter f2 = new CommonFilter();
		f2 = new CommonFilter().addExact("person.id", userBean.getSessionUserId());
		List<ProjectPerson> projectPersonList = this.projectPersonService.list(f2, null);
		String groupId = "";
		if (projectPersonList.size() > 0) {
			groupId = projectPersonList.get(0).getVirtualOrg().getId();
		}

		List<BudgetEstimateBean> beans = new ArrayList<>();

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

				// 专业组：对应到t_virtual_org的org_order
				CommonFilter f1 = new CommonFilter();
				if (!StringUtils.isEmpty(projectId)) {
					f1 = new CommonFilter().addExact("project.id", projectId);
				}
				List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f1, null);
				
				// 获取版本，以便将最新版本的计划备注和下达金额保留
				List<OrderByDto> orders = new ArrayList<>();
				OrderByDto order = new OrderByDto();
				order.setColumnName("cDatetime");
				order.setDesc(true);
				orders.add(order);
		    	CommonFilter vf = new CommonFilter().addExact("year", Integer.parseInt(year)).addExact("project.id", projectId);
		    	List<BudgetEstimateVersion> versionList = budgetEstimateVersionService.list(vf, orders);

				for (int i = 0; i <= sheet.getLastRowNum(); i++) {
					// 跳过前4行
					if (i < 4) {
						continue;
					}

					Row row = sheet.getRow(i);

					String orgName = "";
					String orgId = "";
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(12)))) {
						orgName = ExcelUtils.getCellValue(row.getCell(12));
						for (int k = 0; k < virtualOrgList.size(); k++) {
							if (orgName.equals(virtualOrgList.get(k).getVirtualOrgName())) {
								orgId = virtualOrgList.get(k).getId();
							}
						}
					}

					if (groupId.equals(orgId) || userBean.getIsSuper()==true) {
						BudgetEstimateBean bean = new BudgetEstimateBean();

						// TODO
						bean.setId(ExcelUtils.getCellValue(row.getCell(0)));
						bean.setProject_(projectId);
						bean.setBudgetEstimateMain_(mainId);
						bean.setCode(ExcelUtils.getCellValue(row.getCell(0)));
						bean.setInnerCode(ExcelUtils.getCellValue(row.getCell(1)));
						bean.setYear(Integer.parseInt(year));

						Cell orderNumberCell = row.getCell(2);
						String orderNumberVal = null;
						switch (orderNumberCell.getCellTypeEnum()) {
						case STRING:
							orderNumberVal = orderNumberCell.getRichStringCellValue().getString();
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(orderNumberCell)) {
								orderNumberVal = DateUtils.date2String(orderNumberCell.getDateCellValue());
							} else {
								NumberFormat nf = NumberFormat.getInstance();
								String s = nf.format(row.getCell(2).getNumericCellValue());
								//这种方法对于自动加".0"的数字可直接解决
								//但如果是科学计数法的数字就转换成了带逗号的，例如：12345678912345的科学计数法是1.23457E+13，经过这个格式化后就变成了字符串“12,345,678,912,345”，这也并不是想要的结果，所以要将逗号去掉
								if (s.indexOf(",") >= 0) {
								    s = s.replace(",", "");
								}
								orderNumberVal = s;
							}
							break;
						case BOOLEAN:
							orderNumberVal = "" + orderNumberCell.getBooleanCellValue();
							break;
						case FORMULA:
							orderNumberVal = orderNumberCell.getCellFormula();
							break;
						case BLANK:
							orderNumberVal = "";
							break;
						default:
							orderNumberVal = orderNumberCell.getStringCellValue();
						}
						bean.setOrderNumber(String.valueOf(orderNumberVal));

						bean.setName(ExcelUtils.getCellValue(row.getCell(3)));
						
						if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(4)))) {
                            CommonFilter f = new CommonFilter();
                            f.addExact("optionName", ExcelUtils.getCellValue(row.getCell(4)));
                            List<DataOption> budgetTypeList =
                                    this.dataOptionService.list(f, null);
                            if ((budgetTypeList != null)
                                    && (budgetTypeList.size() > 0)) {
                                bean.setBudgetTypeText(
                                		budgetTypeList.get(0).getOptionName());
                                bean.setBudgetType_(
                                		budgetTypeList.get(0).getId());
                            }
                        }
						
						
						bean.setKeyPerformance(ExcelUtils.getCellValue(row.getCell(5)));
						bean.setBrand(ExcelUtils.getCellValue(row.getCell(6)));
						bean.setUnit(ExcelUtils.getCellValue(row.getCell(7)));
						bean.setVersion("V0.0");

						// excel中数量列有可能是公式，获取计算值
						Cell countCell = row.getCell(8);
						Double count;
						if (countCell != null) {
							switch (countCell.getCellTypeEnum()) {
							case STRING:
								count = Double.valueOf(countCell.getRichStringCellValue().toString());
								break;
							case FORMULA:
								if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(8)))) {
									count = Double.valueOf(String.valueOf(countCell.getNumericCellValue()));
									bean.setCount(count);
								}
								break;
							case NUMERIC:
								count = Double.valueOf(ExcelUtils.getCellValue(row.getCell(8)));
								bean.setCount(count);
							}
						}

						// excel中数量列有可能是公式，获取计算值
						Cell taxInclusivePriceCell = row.getCell(9);
						if (taxInclusivePriceCell != null) {
							switch (taxInclusivePriceCell.getCellTypeEnum()) {
							case FORMULA:
								if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(9)))) {
									Double taxInclusivePrice = Double
											.parseDouble(String.valueOf(taxInclusivePriceCell.getNumericCellValue()));
									bean.setTaxInclusivePrice(taxInclusivePrice);
								}
								break;
							case NUMERIC:
								Double taxInclusivePrice = Double.parseDouble(ExcelUtils.getCellValue(row.getCell(9)));
								bean.setTaxInclusivePrice(taxInclusivePrice);
							}
						}

						// excel中合计列是公式，获取计算值
						Cell totalPriceCell = row.getCell(10);
						if (totalPriceCell != null) {
							switch (totalPriceCell.getCellTypeEnum()) {
							case FORMULA:
								if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(10)))) {
									BigDecimal taxInclusivePriceTotal = new BigDecimal(String.valueOf(totalPriceCell.getNumericCellValue()));
									Double total = taxInclusivePriceTotal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
									bean.setTaxInclusivePriceTotal(total);
								}
								break;
							case NUMERIC:
								Double taxInclusivePriceTotal = Double
										.parseDouble(String.valueOf(totalPriceCell.getNumericCellValue()));
								bean.setTaxInclusivePriceTotal(taxInclusivePriceTotal);
							}
						}

						bean.setRemarks(ExcelUtils.getCellValue(row.getCell(11)));

						for (int k = 0; k < virtualOrgList.size(); k++) {
							if (orgName.equals(virtualOrgList.get(k).getVirtualOrgName())) {
								bean.setGroup_(virtualOrgList.get(k).getId());
							}
						}
						
						if(ExcelUtils.getCellValue(row.getCell(13)) != ""){
								bean.setLevel(Integer.parseInt(ExcelUtils.getCellValue(row.getCell(13))));
						}
						
						if(ExcelUtils.getCellValue(row.getCell(14)) != ""){
							bean.setPaymentCount(Double.parseDouble(ExcelUtils.getCellValue(row.getCell(14))));
						}
						
						if(ExcelUtils.getCellValue(row.getCell(15)) != ""){
							bean.setPaymentPercent(ExcelUtils.getCellValue(row.getCell(15)));
						}
						else {
			    			bean.setPaymentPercent("0");
						}
						
						// excel中合计列是公式，获取计算值
						Cell paymentAmountCell = row.getCell(16);
						if (paymentAmountCell != null) {
							switch (paymentAmountCell.getCellTypeEnum()) {
							case FORMULA:
								if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(16)))) {
									BigDecimal paymentAmountTotal = new BigDecimal(String.valueOf(paymentAmountCell.getNumericCellValue()));
									Double total = paymentAmountTotal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
									bean.setPaymentAmount(total);
								}
								break;
							case NUMERIC:
								Double paymentAmountTotal = Double
										.parseDouble(String.valueOf(paymentAmountCell.getNumericCellValue()));
								bean.setPaymentAmount(paymentAmountTotal);
							}
						}
						
//						if(ExcelUtils.getCellValue(row.getCell(15)) != ""){
//							bean.setPaymentAmount(Double.parseDouble(ExcelUtils.getCellValue(row.getCell(15))));
//						}
						
						// 如果已生成过版本，就取出最新版本中的计划备注,只需在资金计划录入一遍就行，无需重复录入
				        if(versionList.size()>0) {
					          if(!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(0)))) {
					           CommonFilter rf = new CommonFilter();
					           rf.addExact("code", ExcelUtils.getCellValue(row.getCell(0))).addExact("version", versionList.get(0).getId());
					           List<BudgetEstimate> budgetList = budgetEstimateService.list(rf, null);
					           if(budgetList.size()>0) {
					        	   bean.setPlanRemark(budgetList.get(0).getPlanRemark());
					           }
					          }
				        }
				        
				        //上一年转至今年
//				        Double lastToThisAmount = 0.0;
//				        Double payAmountTotal = 0.0;
//						if (!StringUtils.isEmpty(bean.getPaymentPercent()) && !StringUtils.isEmpty(bean.getTaxInclusivePrice()) && !StringUtils.isEmpty(bean.getPaymentCount())) {
//							if (Integer.parseInt(bean.getPaymentPercent()) != 100) {
//								int paymentPercent = 100 - Integer.parseInt(bean.getPaymentPercent());
//								lastToThisAmount = paymentPercent * bean.getTaxInclusivePrice() * bean.getPaymentCount() / 100;
//							}
//						}
//
//						//当年支付总金额
//						if(bean.getPaymentAmount() != null) {
//							payAmountTotal = bean.getPaymentAmount();
//						}
//						bean.setPayAmountTotal(lastToThisAmount + payAmountTotal);
				        
				        
				        // 获取code的上一年数据
				        BigDecimal maxNode = new BigDecimal(0);
				        if (!StringUtils.isEmpty(bean.getPaymentAmount())) {
				        	maxNode = maxNode.add(new BigDecimal(bean.getPaymentAmount()));
				        }
				        
				        //硬件类需要计算上一年下一年
		    			if(!StringUtils.isEmpty(bean.getBudgetType_()) && bean.getBudgetType_().equals("BUDGET_TYPE_1")) {
		    				
		    				//上一年转至今年
		    				BigDecimal lastToThisAmount = new BigDecimal(0);
		    				
							// 获取code的上一年数据
							CommonFilter lf = new CommonFilter().addExact("code", bean.getCode()).addExact("version", "V0.0").addExact("year", bean.getYear()-1).addExact("project.id", projectId);
							List<BudgetEstimate> budgetDetailList2 = this.budgetEstimateService.list(lf, null);
							
							if (!StringUtils.isEmpty(budgetDetailList2) && budgetDetailList2.size() > 0) {
								BudgetEstimate lastBudgetEstimate = budgetDetailList2.get(0);
								if (!StringUtils.isEmpty(lastBudgetEstimate) && !StringUtils.isEmpty(lastBudgetEstimate.getPaymentAmount())) {
									lastToThisAmount = lastToThisAmount.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(lastBudgetEstimate.getTaxTotalPrice())?0:lastBudgetEstimate.getTaxTotalPrice())))
											.subtract(new BigDecimal(String.valueOf(StringUtils.isEmpty(lastBudgetEstimate.getPaymentAmount())?0:lastBudgetEstimate.getPaymentAmount())));
								}
							}
							bean.setPayAmountTotal(maxNode.doubleValue() + lastToThisAmount.doubleValue());
		    			}
		    			else {
		    				bean.setPayAmountTotal(maxNode.doubleValue());
		    			}
				        
						beans.add(bean);
					}
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

	/**
	 * 导出预算规划表
	 * 
	 * @param response
	 * @param locale
	 */
	@RequestMapping("/export-excel-file")
	public void exportBudgetExcel(@RequestParam String project_, @RequestParam String year,
			@ModelAttribute ProjectBean bean, HttpServletResponse response, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			File downloadFile = this.generateBudgetExcelFile(project_, Integer.parseInt(year), userBean.getSessionUserId());

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
			logger.error("Exception while export plan task xls file", e);
			jsonResult.setStatus(JsonStatus.ERROR);
		}
	}

	private File generateBudgetExcelFile(String project_, int year, String userId) throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {
			//当前用户所在组
			CommonFilter rf = new CommonFilter();
			rf = new CommonFilter().addExact("person.id", userId).addExact("project.id", project_);
			List<ProjectPerson> projectPersonList = this.projectPersonService.list(rf, null);
			
			List<BudgetEstimate> BudgetEstimates = new ArrayList<>();
			
			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("code", false));
			
			//该用户有权限
			if(projectPersonList.size()>0) {
				CommonFilter f = new CommonFilter().addExact("version", "V0.0").addExact("year", year).addExact("project.id", project_);
				if(projectPersonList.get(0).getProjectRole().getLevel()!=1) {//当前用户不是指定角色，只能查看本专业组数据
					f = f.addExact("group.id", projectPersonList.get(0).getVirtualOrg().getId());
				}
				BudgetEstimates = this.budgetEstimateService.list(f, orders);
			}

			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("概算表");
			Row header = sheet.createRow(0);
			header.setHeight((short) 800);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));// 合并列(起始行，结束行，起始列，结束列)

			sheet.setColumnWidth(0, 10 * 256);
			sheet.setColumnWidth(1, 10 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 15 * 256);
			sheet.setColumnWidth(4, 10 * 256);
			sheet.setColumnWidth(5, 15 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256);

			Cell headCell = header.createCell(0);
			headCell.setCellValue("未来网络试验设施项目分专业概算明细表");

			CellStyle cellStyle = wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			Font font = wb.createFont();
			font.setBold(true); // 加粗
			font.setFontHeightInPoints((short) 15); // 设置标题字体大小
			cellStyle.setFont(font);

			headCell.setCellStyle(cellStyle);

			Row properties = sheet.createRow(1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));

			properties.createCell(0).setCellValue(" 建设单位名称:江苏省未来网络创新研究院");

			Row title = sheet.createRow(2);
			title.createCell(0).setCellValue("编号");
			title.createCell(1).setCellValue("序号");
			title.createCell(2).setCellValue("设备名称");
			title.createCell(3).setCellValue("单位");
			title.createCell(4).setCellValue("数量");
			title.createCell(5).setCellValue("含税单价（万元）");
			title.createCell(6).setCellValue("含税总价（万元）");
			title.createCell(7).setCellValue("备注");
			// title.createCell(9).setCellValue("专业组");

			CellStyle border = wb.createCellStyle();
			// 边框
			border.setBorderBottom(CellStyle.BORDER_THIN);
			border.setBorderLeft(CellStyle.BORDER_THIN);
			border.setBorderRight(CellStyle.BORDER_THIN);
			border.setBorderTop(CellStyle.BORDER_THIN);
			// 边框颜色
			border.setBottomBorderColor(HSSFColor.BLACK.index);
			border.setRightBorderColor(HSSFColor.BLACK.index);
			border.setLeftBorderColor(HSSFColor.BLACK.index);
			border.setTopBorderColor(HSSFColor.BLACK.index);

			title.getCell(0).setCellStyle(border);
			title.getCell(1).setCellStyle(border);
			title.getCell(2).setCellStyle(border);
			title.getCell(3).setCellStyle(border);
			title.getCell(4).setCellStyle(border);
			title.getCell(5).setCellStyle(border);
			title.getCell(6).setCellStyle(border);
			title.getCell(7).setCellStyle(border);
			// title.getCell(9).setCellStyle(border);

			Row num = sheet.createRow(3);
			num.createCell(0).setCellValue("0001");
			num.createCell(1).setCellValue("Ⅰ");
			num.createCell(2).setCellValue("Ⅱ");
			num.createCell(3).setCellValue("Ⅲ");
			num.createCell(4).setCellValue("Ⅳ");
			num.createCell(5).setCellValue("Ⅴ");
			num.createCell(6).setCellValue("Ⅵ");
			num.createCell(7).setCellValue("VII");
			num.getCell(0).setCellStyle(border);
			num.getCell(1).setCellStyle(border);
			num.getCell(2).setCellStyle(border);
			num.getCell(3).setCellStyle(border);
			num.getCell(4).setCellStyle(border);
			num.getCell(5).setCellStyle(border);
			num.getCell(6).setCellStyle(border);
			num.getCell(7).setCellStyle(border);

			for (int i = 0; i < BudgetEstimates.size(); i++) {

				BudgetEstimate BudgetEstimate = BudgetEstimates.get(i);

				Row row = sheet.createRow(i + 4);
				row.createCell(0).setCellValue(BudgetEstimate.getCode());
				row.createCell(1).setCellValue(BudgetEstimate.getOrderNumber());
				row.createCell(2).setCellValue(BudgetEstimate.getName());
				row.createCell(3).setCellValue(BudgetEstimate.getMeasurementUnit());
				row.getCell(0).setCellStyle(border);
				row.getCell(1).setCellStyle(border);
				row.getCell(2).setCellStyle(border);
				row.getCell(3).setCellStyle(border);
				row.createCell(4).setCellStyle(border);
				if (!StringUtils.isEmpty(BudgetEstimate.getCount())) {
					row.getCell(4).setCellValue(BudgetEstimate.getCount());
				}
				row.createCell(5).setCellStyle(border);
				if (!StringUtils.isEmpty(BudgetEstimate.getTaxInclusivePrice())) {
					row.getCell(5).setCellValue(BudgetEstimate.getTaxInclusivePrice());
				}
				row.createCell(6).setCellStyle(border);
				if (!StringUtils.isEmpty(BudgetEstimate.getTaxTotalPrice())) {
					row.getCell(6).setCellValue(BudgetEstimate.getTaxTotalPrice());
				}
				row.createCell(7).setCellValue(BudgetEstimate.getRemarks());
				row.getCell(7).setCellStyle(border);
				// row.createCell(9).setCellValue(BudgetEstimateSum.getGroup());
			}

			Date now = new Date();

			String fileName = "未来网络试验设施项目分专业概算明细表" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
					+ ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + "sum" + File.separator + randomID);

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
	 * 导出决算表
	 * 
	 * @param response
	 * @param locale
	 */
	@RequestMapping("/export-final-excel-file")
	public void exportFinalBudgetExcel(@RequestParam String project_, @RequestParam String year,
			@RequestParam String startDate, @RequestParam String endDate, @ModelAttribute ProjectBean bean,
			HttpServletResponse response, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			File downloadFile = this.generateBudgetFinalExcelFile(project_, Integer.parseInt(year), startDate, endDate);

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
			logger.error("Exception while export plan task xls file", e);
			jsonResult.setStatus(JsonStatus.ERROR);
		}
	}

	private File generateBudgetFinalExcelFile(String project_, int year, String startDate, String endDate)
			throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {
			List<BudgetEstimate> BudgetEstimates = this.budgetEstimateService.getBudgetByYear(year, project_);
			// CommonFilter filter = new CommonFilter().addExact("year",
			// year).addExact("version", "V0.0");
			//
			// List<OrderByDto> orders = new ArrayList<>();
			// orders.add(new OrderByDto("code", false));
			//
			// List<BudgetEstimate> BudgetEstimates =
			// this.budgetEstimateService.list(filter,orders);
			List<Object> totalFeeList = this.budgetFeeService.getBudgetFeeList(startDate, endDate);

			List<Object> siteFeeList = this.budgetFeeService.getFeeBySite(startDate, endDate);

			List<Object> siteNameList = this.budgetFeeService.getSiteNameList(startDate, endDate);

			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("决算表");
			Row header = sheet.createRow(0);
			header.setHeight((short) 800);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));// 合并列(起始行，结束行，起始列，结束列)

			sheet.setColumnWidth(0, 10 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 10 * 256);
			sheet.setColumnWidth(3, 15 * 256);
			sheet.setColumnWidth(4, 15 * 256);
			sheet.setColumnWidth(5, 10 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256);
			sheet.setColumnWidth(8, 15 * 256);
			sheet.setColumnWidth(9, 15 * 256);
			sheet.setColumnWidth(10, 15 * 256);

			Cell headCell = header.createCell(0);
			headCell.setCellValue("未来网络试验设施项目分专业概算明细表");

			CellStyle cellStyle = wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			Font font = wb.createFont();
			font.setBold(true); // 加粗
			font.setFontHeightInPoints((short) 15); // 设置标题字体大小
			cellStyle.setFont(font);

			headCell.setCellStyle(cellStyle);

			Row properties = sheet.createRow(1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));

			properties.createCell(0).setCellValue(" 建设单位名称:江苏省未来网络创新研究院");

			Row title = sheet.createRow(2);
			title.createCell(0).setCellValue("编号");
			title.createCell(1).setCellValue("内部编号");
			title.createCell(2).setCellValue("序号");
			title.createCell(3).setCellValue("设备名称");
			title.createCell(4).setCellValue("单位");
			title.createCell(5).setCellValue("数量");
			title.createCell(6).setCellValue("含税单价（万元）");
			title.createCell(7).setCellValue("含税总价（万元）");
			title.createCell(8).setCellValue("备注");
			title.createCell(9).setCellValue("支出数量");
			title.createCell(10).setCellValue("支出单价");
			title.createCell(11).setCellValue("支出金额（万元）");
			title.createCell(12).setCellValue("决算金额（万元）");
			for (int i = 0; i < siteNameList.size(); i++) {
				Object[] fee = (Object[]) siteNameList.get(i);
				title.createCell(13 + 3 * i).setCellValue(fee[0].toString());
				title.createCell(14 + 3 * i).setCellValue(fee[0].toString());
				title.createCell(15 + 3 * i).setCellValue(fee[0].toString());
				// 合并列(起始行，结束行，起始列，结束列)
				sheet.addMergedRegion(new CellRangeAddress(2, 2, 13 + 3 * i, 15 + 3 * i));
			}

			CellStyle border = wb.createCellStyle();
			// 边框
			border.setBorderBottom(CellStyle.BORDER_THIN);
			border.setBorderLeft(CellStyle.BORDER_THIN);
			border.setBorderRight(CellStyle.BORDER_THIN);
			border.setBorderTop(CellStyle.BORDER_THIN);
			// 边框颜色
			border.setBottomBorderColor(HSSFColor.BLACK.index);
			border.setRightBorderColor(HSSFColor.BLACK.index);
			border.setLeftBorderColor(HSSFColor.BLACK.index);
			border.setTopBorderColor(HSSFColor.BLACK.index);

			title.getCell(0).setCellStyle(border);
			title.getCell(1).setCellStyle(border);
			title.getCell(2).setCellStyle(border);
			title.getCell(3).setCellStyle(border);
			title.getCell(4).setCellStyle(border);
			title.getCell(5).setCellStyle(border);
			title.getCell(6).setCellStyle(border);
			title.getCell(7).setCellStyle(border);
			title.getCell(8).setCellStyle(border);
			title.getCell(9).setCellStyle(border);
			title.getCell(10).setCellStyle(border);
			title.getCell(11).setCellStyle(border);
			title.getCell(12).setCellStyle(border);
			for (int i = 0; i < siteNameList.size(); i++) {
				title.getCell(13 + 3 * i).setCellStyle(border);
				title.getCell(14 + 3 * i).setCellStyle(border);
				title.getCell(15 + 3 * i).setCellStyle(border);
			}

			Row num = sheet.createRow(3);
			num.createCell(0).setCellValue("0001");
			num.createCell(2).setCellValue("Ⅰ");
			num.createCell(3).setCellValue("Ⅱ");
			num.createCell(4).setCellValue("Ⅲ");
			num.createCell(5).setCellValue("Ⅳ");
			num.createCell(6).setCellValue("Ⅴ");
			num.createCell(7).setCellValue("Ⅵ");
			num.createCell(8).setCellValue("VII");
			num.getCell(0).setCellStyle(border);
			num.getCell(2).setCellStyle(border);
			num.getCell(3).setCellStyle(border);
			num.getCell(4).setCellStyle(border);
			num.getCell(5).setCellStyle(border);
			num.getCell(6).setCellStyle(border);
			num.getCell(7).setCellStyle(border);
			num.getCell(8).setCellStyle(border);
			num.createCell(9).setCellStyle(border);
			num.createCell(10).setCellStyle(border);
			num.createCell(11).setCellStyle(border);
			num.createCell(12).setCellStyle(border);
			for (int i = 0; i < siteNameList.size(); i++) {
				num.createCell(13 + i * 3).setCellValue("支出数量");
				num.createCell(14 + i * 3).setCellValue("支出单价(万元)");
				num.createCell(15 + i * 3).setCellValue("支出金额(万元)");
				num.getCell(13 + 3 * i).setCellStyle(border);
				num.getCell(14 + 3 * i).setCellStyle(border);
				num.getCell(15 + 3 * i).setCellStyle(border);
			}

			for (int i = 0; i < BudgetEstimates.size(); i++) {

				BudgetEstimate BudgetEstimate = BudgetEstimates.get(i);
				Row row = sheet.createRow(i + 4);
				row.createCell(0).setCellValue(BudgetEstimate.getCode());
				row.createCell(1).setCellValue(BudgetEstimate.getInnerCode());
				row.createCell(2).setCellValue(BudgetEstimate.getOrderNumber());
				//row.createCell(3).setCellValue(BudgetEstimate.getName());
				row.createCell(4).setCellValue(BudgetEstimate.getMeasurementUnit());
				row.getCell(0).setCellStyle(border);
				row.getCell(1).setCellStyle(border);
				row.getCell(2).setCellStyle(border);
				row.getCell(3).setCellStyle(border);
				row.getCell(4).setCellStyle(border);
				row.createCell(5).setCellStyle(border);
				if (!StringUtils.isEmpty(BudgetEstimate.getCount())) {
					row.getCell(5).setCellValue(BudgetEstimate.getCount());
				}
				row.createCell(6).setCellStyle(border);
				if (!StringUtils.isEmpty(BudgetEstimate.getTaxInclusivePrice())) {
					row.getCell(6).setCellValue(BudgetEstimate.getTaxInclusivePrice());
				}
				row.createCell(7).setCellStyle(border);
				if (!StringUtils.isEmpty(BudgetEstimate.getTaxTotalPrice())) {
					row.getCell(7).setCellValue(BudgetEstimate.getTaxTotalPrice());
				}
				row.createCell(8).setCellValue(BudgetEstimate.getRemarks());
				row.getCell(8).setCellStyle(border);
				row.createCell(9).setCellStyle(border);
				row.createCell(10).setCellStyle(border);
				row.createCell(11).setCellStyle(border);
				row.createCell(12).setCellStyle(border);
				for (int m = 0; m < siteNameList.size(); m++) {
					row.createCell(13 + 3 * m).setCellStyle(border);
					row.createCell(14 + 3 * m).setCellStyle(border);
					row.createCell(15 + 3 * m).setCellStyle(border);
				}

				for (int j = 0; j < totalFeeList.size(); j++) {
					Object[] fee = (Object[]) totalFeeList.get(j);
					if (fee[0].equals(BudgetEstimate.getCode())) {
						Double doubleVal = (Double.parseDouble(fee[1].toString())) / 10000;
						int sl = Integer.parseInt(fee[2].toString());
						Double djy = Double.parseDouble(fee[3].toString());
						if (!StringUtils.isEmpty(BudgetEstimate.getCount())) {
							row.getCell(9).setCellValue(sl);
						}

						if (!StringUtils.isEmpty(BudgetEstimate.getTaxInclusivePrice())) {
							row.getCell(10).setCellValue(djy);
						}
						if (!StringUtils.isEmpty(BudgetEstimate.getTaxTotalPrice())) {
							row.getCell(11).setCellValue(doubleVal);
						}
						if (!StringUtils.isEmpty(BudgetEstimate.getTaxTotalPrice())) {
							row.getCell(12).setCellValue(BudgetEstimate.getTaxTotalPrice() - doubleVal);
						}
					}
				}

				for (int k = 0; k < siteNameList.size(); k++) {
					Object[] fee1 = (Object[]) siteNameList.get(k);
					for (int j = 0; j < siteFeeList.size(); j++) {
						Object[] fee = (Object[]) siteFeeList.get(j);
						if (fee1[0].equals(fee[0])) {
							if (fee[1].equals(BudgetEstimate.getCode())) {
								row.getCell(13 + k * 3).setCellValue(Integer.parseInt(fee[2].toString()));
								row.getCell(14 + k * 3).setCellValue((Double.parseDouble(fee[3].toString())) / 10000);
								row.getCell(15 + k * 3).setCellValue((Double.parseDouble(fee[4].toString())) / 10000);
							}
						}
					}
				}
			}

			Date now = new Date();

			String fileName = "未来网络试验设施项目分专业概算明细表" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
					+ ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + "sum" + File.separator + randomID);

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
	
	@RequestMapping("/submitReview")
	@ResponseBody
	public JsonResultBean submitReview(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, @RequestParam String year, @RequestParam String projectId, Locale locale) throws IOException {
		
		JsonResultBean jsonResult = new JsonResultBean();
		short isOa = 0; 
		
/*		//内部审批用
		FlowDetailBean flowDetailBean = new FlowDetailBean();
		flowDetailBean.setFlowableSettingId("2056ce97-eb16-4dd3-8906-53a0f97bed4b");
		flowDetailBean.setCorrelateDataId(id);
		flowDetailBean.setCorrelateProjectId(projectId);
		Project project = this.projectService.find(projectId);
		flowDetailBean.setCorrelateProjectName(project.getProjectName());
		flowDetailBean.setFlowableSettingName("分年度预算编制审批");*/
		
		//cini项目中，项目办的人走oa，其他人走内部；其他项目，角色是SU_role的人走oa，其他人走内部
		if(projectId.equals("02c53818-2d6a-4e6e-b802-bdd799ba6618")) {//cini项目
			//当前用户所在组
			CommonFilter rf = new CommonFilter();
			rf = new CommonFilter().addExact("person.id", userBean.getSessionUserId()).addExact("project.id", projectId);
			List<ProjectPerson> projectPersonList = this.projectPersonService.list(rf, null);
			if(projectPersonList.get(0)!=null && projectPersonList.get(0).getVirtualOrg().getId().equals("d27ce191-87ec-4c47-95a5-622bc5feb41e")) {//项目办
				// OA审批
				isOa = 1;
				//jsonResult = this.auditSubmit(userBean, id, year, projectId, locale);
			} 
/*			else {
				// 内部审核
				jsonResult = this.submitAudit(flowDetailBean,userBean,locale);  
			}*/
		}
		else {//其他项目
			if(userBean.getIsSuper()==true) {
				// OA审批
				isOa = 1;
				//jsonResult = this.auditSubmit(userBean, id, year, projectId, locale);
			}
			/*else {
				// 内部审核
				jsonResult = this.submitAudit(flowDetailBean,userBean,locale);  
			}*/
		}
		jsonResult.setStatus(isOa);
		return jsonResult;
	}
	
	
	 /**
     * 提交审核流程
     * 
     * @return
     */
    @RequestMapping("/submit-audit")
    @ResponseBody
    public JsonResultBean submitAudit(@RequestBody FlowDetailBean flowDetailBean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            FlowableSetting setting =
                    this.flowableSettingService.find(flowDetailBean.getFlowableSettingId());

            flowDetailBean
                    .setId(setting.getPage().getId() + Constants.UNDERSCORE + setting.getId());

            flowDetailBean.setBusinessCode(setting.getPage().getId());
            flowDetailBean.setBusinessCodeUrl(setting.getPage().getUrl());
            flowDetailBean.setBusinessCodeName(
                    this.messageSource.getMessage(setting.getPage().getPageName(), null, locale));

            if (StringUtils.isEmpty(setting.getPage().getFlowableEntity())) {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource
                        .getMessage("message.info.no.flowable.business.entity", null, locale));
                return jsonResult;
            }

            flowDetailBean.setBusinessEntity(setting.getPage().getFlowableEntity());

            flowDetailBean.setStartUserId(userBean.getSessionUserId());

            Person person = this.personService.find(userBean.getSessionUserId());

            if (person != null) {
                flowDetailBean.setStartUserId(person.getId());
                flowDetailBean.setStartUserName(person.getPersonName());
            }

            Set<String> filterIds = new HashSet<>();

            String assigneeIds = this.flowableManager.getAssigneeIdList(flowDetailBean.getId());

            if (!StringUtils.isEmpty(assigneeIds)) {
                String[] tempIds = StringUtils.commaDelimitedListToStringArray(assigneeIds);

                for (String id : tempIds) {
                    filterIds.add(id);
                }
            }

            String candidateGroupsIds =
                    this.flowableManager.getCandidateGroupsIdList(flowDetailBean.getId());

            if (!StringUtils.isEmpty(candidateGroupsIds)) {
                String[] tempIds = StringUtils.commaDelimitedListToStringArray(candidateGroupsIds);

                CommonFilter pFilter = new CommonFilter();
                pFilter.addWithin("projectRole.id", tempIds);

                List<ProjectPerson> persons = this.projectPersonService.list(pFilter, null);

                CommonFilter groupFilter = new CommonFilter();
                groupFilter.addExact("person.id", userBean.getSessionUserId());

                // 发起人过滤
                List<ProjectPerson> groupPersons =
                        this.projectPersonService.list(groupFilter, null);

                for (ProjectPerson p : persons) {

                    for (ProjectPerson g : groupPersons) {

                        if (p.getVirtualOrg().getId().equals(g.getVirtualOrg().getId())) {
                            filterIds.add(p.getPerson().getId());
                        } else {

                            // 发起人的组信息
                            VirtualOrg org = g.getVirtualOrg();

                            // 该组的副经理与角色权限匹配时，加入审批人列表
                            if (org != null && org.getLeader() != null) {
                                if (org.getLeader().getId().equals(p.getPerson().getId())) {
                                    filterIds.add(org.getLeader().getId());
                                }
                            }
                        }
                    }
                }
            }

            if (!filterIds.isEmpty()) {

                CommonFilter filter = new CommonFilter();
                filter.addWithin("id", filterIds);

                List<Person> persons = this.personService.list(filter, null);
                StringBuffer sbId = new StringBuffer();
                StringBuffer sbName = new StringBuffer();

                for (Person p : persons) {
                    sbId.append(p.getId()).append(Constants.COMMA);
                    sbName.append(p.getPersonName()).append(Constants.COMMA);
                }

                if (sbName.length() > 0) {
                    flowDetailBean.setParticipantAuditNameList(
                            sbName.substring(0, sbName.length() - 1).toString());
                }

                if (sbId.length() > 0) {
                    flowDetailBean.setParticipantAuditIdList(
                            sbId.substring(0, sbId.length() - 1).toString());
                }
            }

            this.flowableManager.runProcessInstance(flowDetailBean);

            // 设置实体的流程状态 审核中
            this.flowableSettingService.updateFlowableStatus(flowDetailBean.getBusinessEntity(),
                    flowDetailBean.getCorrelateDataId(), FlowStatusEnum.REVIEWING.value());

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
        } catch (Exception e) {
            getLogger().error("Exception submit-audit-process " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
	
	

	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "预算规划管理", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, @RequestParam String year, @RequestParam String projectId, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();
		
		// 插入到t_oa_flow_history
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_BUDGET.value();
		
/*		//当前用户所在组
		CommonFilter pf = new CommonFilter();
		pf = new CommonFilter().addExact("person.id", creatorId).addExact("project.id", projectId);
		List<ProjectPerson> projectPersonList = this.projectPersonService.list(pf, null);*/
		
		List<BudgetEstimate> budgetList = new ArrayList<>();
		int flag = 1;
		String groupId = "";
		
/*		//该用户有权限
		if(projectPersonList.size()>0) {
			CommonFilter f = new CommonFilter().addExact("version", "V0.0").addExact("year", Integer.parseInt(year)).addExact("project.id", projectId);
			if(projectPersonList.get(0).getProjectRole().getLevel()!=1) {//当前用户不是指定角色，只能查看本专业组数据
				f = f.addExact("group.id", projectPersonList.get(0).getVirtualOrg().getId());
				flag = 0;
				groupId = projectPersonList.get(0).getVirtualOrg().getId();
			}
			根据年份、项目、当前用户所在组获取初版预算
			budgetList = budgetEstimateService.list(f, null);
		}*/
		
		List<OrderByDto> orders = new ArrayList<>();
		OrderByDto timeOrder = new OrderByDto();
		timeOrder.setColumnName("code");
		timeOrder.setDesc(false);
		orders.add(timeOrder);
		CommonFilter f = new CommonFilter().addExact("budgetEstimateMain.id", id);
		budgetList = budgetEstimateService.list(f, orders);
		
		String dataId = id;
		
		if (budgetList.size() == 0) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		
		//将数据存到excel中
		File downloadFile = null;
		downloadFile = this.generateExcelFile(budgetList);
		
		HashMap<String, Object> reqMap = new HashMap<>();
		
		List<HashMap<String, String>> archives = new ArrayList<>();
		HashMap<String, String> attMap = new HashMap<String, String>();
		attMap.put("attachmentFileName", downloadFile.getName());
		byte[] refereeFileOriginalBytes = FileUtils.readFileToByteArray(downloadFile);
		byte[] refereeFileBase64Bytes = Base64.encodeBase64(refereeFileOriginalBytes);
		String UpFile = new String(refereeFileBase64Bytes, "UTF-8");
		attMap.put("attachmentFile", UpFile);
		archives.add(attMap);
		
		Project entity = this.projectService.find(projectId);
		reqMap.put("projectName", entity.getProjectName());
		reqMap.put("projectNum", entity.getProjectNum());
		reqMap.put("archives", archives);
		reqMap.put("year", year);
		reqMap.put("dataId", dataId);
		
		// 调用oa接口
		logger.info("[OA Audit] No.21 BudgetEstimate");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		
		if(jsonResult.getStatus() == JsonStatus.OK){
			//logger.info("[OA Audit] No.21 BudgetEstimate Audit request success, history data id was {}", dataId);
			logger.info("[OA Audit] No.21 BudgetEstimate Audit request success, history data groupId was {}", groupId);
			
			for (BudgetEstimate budget : budgetList) {
				budget.setAuditStatus(String.valueOf(FlowStatusEnum.REVIEWING.value()));
				budget.setModifier(userName);
				budget.setmDatetime(new Date());
				this.budgetEstimateService.setData(budget);
			}
		}

		return jsonResult;
	}
	
	

	//生成excel
	private File generateExcelFile(List<BudgetEstimate> budgetList) throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {
			
			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();

			XSSFCellStyle textCellStyle2 = (XSSFCellStyle) wb.createCellStyle();
			textCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			textCellStyle2.setBorderBottom(BorderStyle.THIN);
			textCellStyle2.setBorderLeft(BorderStyle.THIN);
			textCellStyle2.setBorderRight(BorderStyle.THIN);
			textCellStyle2.setBorderTop(BorderStyle.THIN);

			Sheet sheet = wb.createSheet("预算规划");

			XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			Row row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue("编号");
			row1.getCell(0).setCellStyle(textCellStyle2);
			row1.createCell(1).setCellValue("序号");
			row1.getCell(1).setCellStyle(textCellStyle2);
			row1.createCell(2).setCellValue("设备名称");
			row1.getCell(2).setCellStyle(textCellStyle2);
			row1.createCell(3).setCellValue("主要性能指标");
			row1.getCell(3).setCellStyle(textCellStyle2);
			row1.createCell(4).setCellValue("参考品牌及型号");
			row1.getCell(4).setCellStyle(textCellStyle2);
			row1.createCell(5).setCellValue("单位");
			row1.getCell(5).setCellStyle(textCellStyle2);
			row1.createCell(6).setCellValue("数量");
			row1.getCell(6).setCellStyle(textCellStyle2);
			row1.createCell(7).setCellValue("含税单价（万元）");
			row1.getCell(7).setCellStyle(textCellStyle2);
			row1.createCell(8).setCellValue("含税总价（万元）");
			row1.getCell(8).setCellStyle(textCellStyle2);
			row1.createCell(9).setCellValue("备注");
			row1.getCell(9).setCellStyle(textCellStyle2);
			row1.createCell(10).setCellValue("专业组");
			row1.getCell(10).setCellStyle(textCellStyle2);
			row1.createCell(11).setCellValue("年份");
			row1.getCell(11).setCellStyle(textCellStyle2);

			for (int i = 0; i < budgetList.size(); i++) {
				Row info = sheet.createRow(1 + i);
				String code = budgetList.get(i).getCode();
				String orderNumber = budgetList.get(i).getOrderNumber();
				String name = budgetList.get(i).getName();
				String keyPerformance = budgetList.get(i).getKeyPerformance();
				String brand = budgetList.get(i).getBrand();
				String measurementUnit = budgetList.get(i).getMeasurementUnit();
				Double count = budgetList.get(i).getCount();
				Double taxInclusivePrice = budgetList.get(i).getTaxInclusivePrice();
				Double taxTotalPrice = budgetList.get(i).getTaxTotalPrice();
				String remarks = budgetList.get(i).getRemarks();
				int budgetYear = budgetList.get(i).getYear();
				if(code != null) {
					info.createCell(0).setCellValue(code);
				}
				if(orderNumber != null) {
					info.createCell(1).setCellValue(orderNumber);
				}
				if(name != null) {
					info.createCell(2).setCellValue(name);
				}
				if(keyPerformance != null) {
					info.createCell(3).setCellValue(keyPerformance);
				}
				if(brand != null) {
					info.createCell(4).setCellValue(brand);
				}
				if(measurementUnit != null) {
					info.createCell(5).setCellValue(measurementUnit);
				}
				if(count != null) {
					info.createCell(6).setCellValue(count);
				}
				if(taxInclusivePrice != null) {
					info.createCell(7).setCellValue(taxInclusivePrice);
				}
				if(taxTotalPrice != null) {
					info.createCell(8).setCellValue(taxTotalPrice);
				}
				if(remarks != null) {
					info.createCell(9).setCellValue(remarks);
				}
				if (budgetList.get(i).getGroup() != null) {
					info.createCell(10).setCellValue(budgetList.get(i).getGroup().getVirtualOrgName());
				} 
				if(!"".equals(budgetYear +"")) {
					info.createCell(11).setCellValue(budgetYear);
				}
			}

			Date now = new Date();

			String fileName = "预算规划_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + "budgetEstimate" + File.separator + randomID);

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
	
	
	@RequestMapping("/buildVersion")
	@ResponseBody
	public JsonResultBean buildVersion(@RequestParam String projectId,@RequestParam String year,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
		JsonResultBean jsonResult = new JsonResultBean();
		
		try {
			
			int year1 = Integer.parseInt(year);
			
			//当前用户所在组
			CommonFilter rf = new CommonFilter();
			rf = new CommonFilter().addExact("person.id", userBean.getSessionUserId()).addExact("project.id", projectId);
			List<ProjectPerson> projectPersonList = this.projectPersonService.list(rf, null);
			
			List<BudgetEstimate> budgetList = new ArrayList<>();
			int flag = 1;
			
			//该用户有权限
			if(projectPersonList.size()>0) {
				CommonFilter f = new CommonFilter().addExact("version", "V0.0").addExact("year", year1).addExact("project.id", projectId);
				if(projectPersonList.get(0).getProjectRole().getLevel()!=1) {//当前用户不是指定角色，只能查看本专业组数据
					f = f.addExact("group.id", projectPersonList.get(0).getVirtualOrg().getId());
					flag = 0;
				}
				/*根据年份、项目、当前用户所在组获取初版预算*/
				budgetList = budgetEstimateService.list(f, null);
			}
			
			
			if(budgetList.size() == 0) {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage("版本生成失败");
				return jsonResult;
			}
			
			/*新增版本*/
			
			//查所有版本，为了生成版本号
	        CommonFilter f1 = new CommonFilter().addExact("year", year1).addExact("project.id", projectId);
			List<BudgetEstimateVersion> versionList = budgetEstimateVersionService.list(f1, null);
			
	        //版本号——年月日+0000+_年份（例：202004130001）
	        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
	        java.util.Date currTime = new java.util.Date();
	        String curTime = formatter.format(currTime);
	        String code = curTime+String.format("%04d", versionList.size()+1)+"_"+year1;  
	        
			BudgetEstimateVersionBean budgetEstimateVersionBean = new BudgetEstimateVersionBean();
			budgetEstimateVersionBean.setYear(year1);
			budgetEstimateVersionBean.setVersion(code);
			if(flag == 1) {
				budgetEstimateVersionBean.setName("1");
			}
			else {
				budgetEstimateVersionBean.setName("0");
			}
			budgetEstimateVersionBean.setProject_(projectId);
			BudgetEstimateVersionBeanConverter converter = new BudgetEstimateVersionBeanConverter();
			BudgetEstimateVersion budgetEstimateVersion = converter.toEntity(budgetEstimateVersionBean, null, userBean, new Date());
			budgetEstimateVersionService.create(budgetEstimateVersion);
			
			/*根据版本号查找刚新增的版本id*/
			
	        CommonFilter f2 = new CommonFilter().addExact("version", code);
			List<BudgetEstimateVersion> versionList1 = budgetEstimateVersionService.list(f2, null);
			String versionId = versionList1.get(0).getId();
			
			/*将初版赋值版本号后新增到预算表中*/
			
			for(int i=0;i<budgetList.size();i++) {
				BudgetEstimate budget1 = new BudgetEstimate();
				budget1.setProject(budgetList.get(i).getProject());
				budget1.setCode(budgetList.get(i).getCode());
				budget1.setInnerCode(budgetList.get(i).getInnerCode());
				budget1.setOrderNumber(budgetList.get(i).getOrderNumber());
				budget1.setName(budgetList.get(i).getName());
				budget1.setVersion(versionId);
				budget1.setGroup(budgetList.get(i).getGroup());
				budget1.setMeasurementUnit(budgetList.get(i).getMeasurementUnit());
				budget1.setCount(budgetList.get(i).getCount());
				budget1.setTaxInclusivePrice(budgetList.get(i).getTaxInclusivePrice());
				budget1.setTaxExcludingPrice(budgetList.get(i).getTaxExcludingPrice());
				budget1.setTaxTotalPrice(budgetList.get(i).getTaxTotalPrice());
				budget1.setRemarks(budgetList.get(i).getRemarks());
				budget1.setYear(budgetList.get(i).getYear());
				budget1.setTaxPoint(budgetList.get(i).getTaxPoint());
				budget1.setDeviceClassify(budgetList.get(i).getDeviceClassify());
				budget1.setPersonMonth(budgetList.get(i).getPersonMonth());
				budget1.setPersonPrice(budgetList.get(i).getPersonPrice());
				budget1.setCreator(userBean.getSessionUserId());
				budget1.setcDatetime(new Date());
				budget1.setmDatetime(new Date());

				this.budgetEstimateService.create(budget1);
			}
			
			
			
			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage("版本已生成");
//			String result = new ObjectMapper().writeValueAsString(jsonResult);
//			response.getWriter().write("<textarea>" + result + "</textarea>");
			
		} catch (Exception e) {
			logger.error("Exception while converting object to json string.", e);
		}
		return jsonResult;
	}
	
	public void changeTotal(List<BudgetEstimateBean> budgetList,String mainId) {
		
		BigDecimal total = new BigDecimal(0);
		
		//将code全部取出用于后面的比较
		List<String> innerCodeList = new ArrayList<String>();
		for(BudgetEstimateBean budget: budgetList) {
			innerCodeList.add(budget.getInnerCode());
		}
		
		for(BudgetEstimateBean budget: budgetList) {
			//如果codeList中不包含当前innerCode，就说明当前数据是导入数据中的最大层级，直接取他的总价
			if (StringUtils.isEmpty(budget.getTaxInclusivePriceTotal())) {
				continue;
			}
			if(innerCodeList.contains(budget.getCode()) == false) {
				total = total.add(new BigDecimal(StringUtils.isEmpty(budget.getPayAmountTotal())?0:budget.getPayAmountTotal()));
			}
		}
		
		//更新预算主表总计
		BudgetEstimateMain budgetEstimateMain = this.budgetEstimateMainService.find(mainId);
		budgetEstimateMain.setBudgetTotal(total.doubleValue());
		this.budgetEstimateMainService.update(budgetEstimateMain);
	}
	
}
