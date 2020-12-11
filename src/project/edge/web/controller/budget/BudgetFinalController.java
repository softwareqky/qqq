package project.edge.web.controller.budget;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.commons.net.util.Base64;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
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
import project.edge.domain.converter.BudgetEstimateBeanConverter;
import project.edge.domain.converter.BudgetEstimateSumBeanConverter;
import project.edge.domain.converter.BudgetFinalBeanConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateSum;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.BudgetFinal;
import project.edge.domain.entity.BudgetMainfee;
import project.edge.domain.entity.OaFlowHistory;
import project.edge.domain.entity.PredefinedRule;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrderDetail;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetEstimateBean;
import project.edge.domain.view.BudgetEstimateSumBean;
import project.edge.domain.view.BudgetFinalBean;
import project.edge.domain.view.BudgetMainfeeBean;
import project.edge.domain.view.ProjectBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.bidding.PurchaseOrderDetailService;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateSumService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.budget.BudgetFeeService;
import project.edge.service.budget.BudgetFinalService;
import project.edge.service.budget.BudgetMainfeeService;
import project.edge.service.budget.PredefinedRuleService;
import project.edge.service.flow.OaFlowHistoryService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.TreeGridController;


/**
 * 经费决算记录
 *
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetFinal")
public class BudgetFinalController extends TreeGridController<BudgetEstimate, BudgetEstimateBean>{
 
    private static final Logger logger = LoggerFactory.getLogger(BudgetEstimateController.class);
    
    private static final String PID ="P10014";
    
    private static final String ID_MAP_KEY_BUDGET_DIALOG = "edit-budget-form-dialog";
    
	private static final String OPEN_ORDER_LIST_JS = "BUDGETFINAL.openOrderListDialog('#%1$s','#%2$s')";
	
	public static final String PURCHASE_ORDER_LIST_DIALOG_ID = "PurchaseOrderListDialog"; 
    
    @Resource
    private BudgetEstimateService budgetEstimateService;
    
    @Resource
    private VirtualOrgService virtualOrgService;
    
    @Resource
    private BudgetFeeService budgetFeeService;
    
	@Resource
	private BudgetEstimateSumService budgetEstimateSumService;
	
	@Resource
	private BudgetEstimateVersionService budgetEstimateVersionService;
	
	@Resource
	private OaFlowHistoryService oaFlowHistoryService;
    
	@Autowired
   	HttpServletRequest request;
	
    @Resource
   	CreateWorkFlowManager createWorkFlowManager;
    
    @Resource
    private ProjectService projectService;
    
	@Resource
	private BudgetFinalService budgetFinalService;
	
    @Resource
    private PurchaseOrderDetailService purchaseOrderDetailService;
    
	@Resource
	private BudgetMainfeeService budgetMainfeeService;
	
    @Resource
    private PredefinedRuleService predefinedRuleService;

       
    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.BUDGET_FINAL.value();
    }

    @Override
    protected Service<BudgetEstimate, String> getDataService() {
        return this.budgetEstimateService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<BudgetEstimate, BudgetEstimateBean> getViewConverter() {
        return new BudgetEstimateBeanConverter();
    }
    
    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/budget/budgetFinalHidden.jsp";
    }
     
    
    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/budget/budgetFinal.jsp";
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
		
		List<ComboboxOptionBean> levelOptions = new ArrayList<>();
		levelOptions.add(new ComboboxOptionBean("1", "一级分类"));
		levelOptions.add(new ComboboxOptionBean("2", "二级分类"));
		levelOptions.add(new ComboboxOptionBean("3", "三级分类"));
	
		optionMap.put("levelOptions", levelOptions);
		return optionMap;
	}
    
	@Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ControllerIdMapKeys.ORDER_LIST_DIALOG, PURCHASE_ORDER_LIST_DIALOG_ID);

        return idMap;
    }
    
    
    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
        
        // 导出总表文件
        jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
                String.format("BUDGETFINAL.openExportFormDialog('#%1$s');",
                        idMap.get(ID_MAP_KEY_BUDGET_DIALOG)));
        
     	// 查看采购记录
        jsMap.put(ControllerJsMapKeys.ORDER_LIST, String.format(OPEN_ORDER_LIST_JS,
                idMap.get(ControllerIdMapKeys.ORDER_LIST_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
		if (isOaFlow()) {
			// OA审批
			jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT, String.format("BUDGETFINAL.handleAuditClick('#%1$s', '%2$s');",
					idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), urlMap.get(ControllerUrlMapKeys.AUDIT_SUBMIT)));
		}
		
        return jsMap;
    }
    
    /**
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
     */
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		String r = super.main(paramMap, model, userBean, locale);
		model.addAttribute(ControllerModelKeys.IS_VIEW_HASGRID, true);
		model.addAttribute(ControllerModelKeys.DBL_CLICK_ROW_HANDLER, "BUDGETFINAL.handleDblClickRow");
        
        return r;
    }

    /**
     * 获取一览显示的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param page 页数
     * @param rows 每页的记录数
     * @param sort 排序的字段，CSV
     * @param order 顺序，CSV
     * @param locale
     * @return
     * @throws IOException 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     * @throws ParseException 
     */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) throws JsonParseException, JsonMappingException, IOException, ParseException {
    	 
    	 sort = "code";
    	 JsonResultBean jsonResult = new JsonResultBean();

         SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
         Date date = new Date();
         String year = sdf.format(date);
         String projectId = "";
         String code = "";
         String group = "";
         String name = "";
        	 
         CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
         CommonFilter f = new CommonFilter();

         if(filter!=null) {
             for (int i = 0; i < filter.getFilterFieldList().size(); i++) {
                if (filter.getFilterFieldList().get(i).getFieldName().equals("year")) {
                    year = filter.getFilterFieldList().get(i).getValue().toString();
                }
         		else if(filter.getFilterFieldList().get(i).getFieldName().equals("project.id")) {
        			projectId = filter.getFilterFieldList().get(i).getValue().toString();
        		}
         		else if(filter.getFilterFieldList().get(i).getFieldName().equals("code")) {
         			code = filter.getFilterFieldList().get(i).getValue().toString();
        		}
         		else if(filter.getFilterFieldList().get(i).getFieldName().equals("group.id")) {
         			group = filter.getFilterFieldList().get(i).getValue().toString();
        		}
         		else if(filter.getFilterFieldList().get(i).getFieldName().equals("name")) {
         			name = filter.getFilterFieldList().get(i).getValue().toString();
        		}
             }
         }
         
         // 设置分页信息
         PageCtrlDto pageCtrl = new PageCtrlDto();
         if (page > 0 && rows > 0) {
             pageCtrl.setCurrentPage(page);
             pageCtrl.setPageSize(rows);
         }
         
         if (year.equals("9999")) {//总表
        	 year = "2019,2020,2021,2022,2023";
        	 
             //排序
             List<OrderByDto> orders = new ArrayList<>();
             orders.add(new OrderByDto("code", false));
             
             f = f.addExact("project.id", projectId);
             
             if (!StringUtils.isEmpty(code)) {
            	 f = f.addAnywhere("code", code);
             }
             if (!StringUtils.isEmpty(group)) {
            	 f = f.addExact("group.id", group);
             }
             if (!StringUtils.isEmpty(name)) {
            	 f = f.addAnywhere("name", name);
             }
             
             List<BudgetEstimateSum> budgetsumList = budgetEstimateSumService.list(f, orders, pageCtrl);
             List<BudgetEstimateSum> budgetEstimateCount = budgetEstimateSumService.list(f, orders);
             
             long total = budgetEstimateSumService.list(f, orders).size();//总数
             List<BudgetEstimateSumBean> budgetEstimateSumBeanList = new ArrayList<BudgetEstimateSumBean>();

             if ((budgetsumList != null) && (budgetsumList.size() > 0)) {
            	 BudgetEstimateSumBeanConverter converter = new BudgetEstimateSumBeanConverter();
                 for (BudgetEstimateSum b : budgetsumList) {
                	 budgetEstimateSumBeanList.add(converter.fromEntity(b, this.messageSource, locale));
                 }
             }
             
             jsonResult.setStatus(JsonStatus.OK);
             jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, budgetEstimateSumBeanList);
             jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, budgetEstimateCount.size());
         }
         else {
        	 //获取选中年份的所有版本，按创建时间倒序
             List<OrderByDto> orders = new ArrayList<>();
             orders.add(new OrderByDto("cDatetime", true));
             f = f.addExact("project.id", projectId).addExact("year",Integer.parseInt(year)).addExact("budgetMain.flowStatus", 2);
             List<BudgetEstimateVersion> budgetVersionList = budgetEstimateVersionService.list(f, orders);
             
             //根据budgetVersionList第一条数据的version查找预算
             if(budgetVersionList.size()>0) {
            	 f = new CommonFilter().addExact("version", budgetVersionList.get(0).getId());
             }
             else {//随便赋值，只要搜不出来
            	 f = new CommonFilter().addExact("version", "V111");
             }
             
             List<OrderByDto> orders1 = new ArrayList<>();
             orders1.add(new OrderByDto("code", false));
             if (!StringUtils.isEmpty(code)) {
            	 f = f.addAnywhere("code", code);
             }
             if (!StringUtils.isEmpty(group)) {
            	 f = f.addExact("group.id", group);
             }
             if (!StringUtils.isEmpty(name)) {
            	 f = f.addAnywhere("name", name);
             }
        	 List<BudgetEstimate> budgetList = budgetEstimateService.list(f, orders1, pageCtrl);
        	 List<BudgetEstimate> BudgetEstimateCount = budgetEstimateService.list(f, orders);
        	 
             long total = budgetEstimateService.getBudgetByYear(Integer.parseInt(year), projectId).size();//总数
             List<BudgetEstimateBean> budgetEstimateBeanList = new ArrayList<BudgetEstimateBean>();

             if ((budgetList != null) && (budgetList.size() > 0)) {
            	 BudgetEstimateBeanConverter converter = new BudgetEstimateBeanConverter();
                 for (BudgetEstimate b : budgetList) {
                	 budgetEstimateBeanList.add(converter.fromEntity(b, this.messageSource, locale));
                 }
             }
             
             jsonResult.setStatus(JsonStatus.OK);
             jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, budgetEstimateBeanList);
             jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, BudgetEstimateCount.size());

         }
         
         //加上报销金额、报销数量、余额、预警
         List<Object> budgetFeeList = this.budgetFeeService.getFeeByYearAndCode(year);
         
         //预警规则
         List<PredefinedRule> configList = this.predefinedRuleService.list(null, null);
         Map<String, Object> record = new HashMap<>();
         for (PredefinedRule c : configList) {
             record.put(c.getId(), c.getConfigValue());
         }
         //{_IS_FEE_USE_MORE=1, _IS_NOT_USE=1, _NOT_USE_PERIOD=1, _IS_COUNT=1, _NOT_USE_PERCENT=80%, _COUNT_EQUAL=1, _FEE_USE_MORE_PERCENT=20%, _FEE_USE_MORE_PERIOD=2}
         
         if(year.equals("2019,2020,2021,2022,2023")) {
        	 List<BudgetEstimateSumBean> budgetsumList = new ArrayList<>();
        	 budgetsumList = (List<BudgetEstimateSumBean>) jsonResult.getDataMap().get(JsonResultBean.KEY_EASYUI_ROWS);
             for (int i=0; i < budgetsumList.size(); i++) { 
                 for (int j=0; j < budgetFeeList.size(); j++) {
                	 Object[] fee = (Object[]) budgetFeeList.get(j);
                	 if(budgetsumList.get(i).getCode().equals(fee[0].toString())) {
                		 BudgetEstimateSumBean bean = budgetsumList.get(i);

                		 BigDecimal budgetFee = new BigDecimal(0);
                		 if(fee[1] != null) {
                			 budgetFee = new BigDecimal(fee[1].toString());
                		 }
                		 BigDecimal total = new BigDecimal(0);
                		 if(budgetsumList.get(i).getTaxInclusivePriceTotal()!=null) {
                			 total = new BigDecimal(new BigDecimal(Double.toString(budgetsumList.get(i).getTaxInclusivePriceTotal())).multiply(new BigDecimal(Double.toString(10000))).doubleValue());
/*                    		 if(budgetFee.divide(total, 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(0.9)) == 1) {
                    			 bean.setWarning("<div style='margin-left:75px;width:15px;height:15px;background:red;border-radius:7.5px'></div>");
                    		 }*/
                		 }
                		 BigDecimal balance = new BigDecimal(total.subtract(budgetFee).doubleValue());
                		 //jsonResult.getDataMap().get(JsonResultBean.KEY_EASYUI_ROWS)
//                		 jsonResult.getDataMap()
                		 
                		 bean.setBudgetFee(budgetFee);
                		 bean.setBalance(balance);
                		 bean.setFeeCount(Integer.parseInt(fee[2].toString()));
                		 jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, bean);
                	 }
                  }
             }
         }
         else {
        	 List<BudgetEstimateBean> budgetList = new ArrayList<>();
        	 budgetList = (List<BudgetEstimateBean>) jsonResult.getDataMap().get(JsonResultBean.KEY_EASYUI_ROWS);
             for (int i=0; i < budgetList.size(); i++) { 
                 for (int j=0; j < budgetFeeList.size(); j++) {
                	 Object[] fee = (Object[]) budgetFeeList.get(j);
                	 if(budgetList.get(i).getCode().equals(fee[0].toString())) {
                		 if(fee[1] != null) {
                			 BudgetEstimateBean bean = budgetList.get(i);
                			 
                			 BigDecimal total = new BigDecimal(0);
                			 BigDecimal budgetFee = new BigDecimal(fee[1].toString());
                			 if(budgetList.get(i).getTaxInclusivePriceTotal()!=null) {
                				 if(!StringUtils.isEmpty(budgetList.get(i).getPayAmountTotal())) {
                					 total = new BigDecimal(new BigDecimal(Double.toString(budgetList.get(i).getPayAmountTotal())).multiply(new BigDecimal(Double.toString(10000))).doubleValue());
                				 }
                				 
                				 /*根据预警规则判断是否报警 当年的才需要*/
                				 int flag = 0;
                				 String info = "";
                				 Calendar cal = Calendar.getInstance();
	        			         int nowMonth = cal.get(Calendar.MONTH) + 1;
	        			         int nowYear = cal.get(Calendar.YEAR);
	        			         
	        			         if(nowYear == bean.getYear()) {
	        			        	 NumberFormat nf=NumberFormat.getPercentInstance();//将百分比转化成小数
		        			         Number hasUsePercent = nf.parse(record.get("_FEE_USE_MORE_PERCENT").toString());
		        			         Number noUsePercent = nf.parse(record.get("_NOT_USE_PERCENT").toString());
		        			         BigDecimal noUseTotal = new BigDecimal(0);
		        			         if(!StringUtils.isEmpty(budgetList.get(i).getPayAmountTotal())) {
		        			        	 noUseTotal = new BigDecimal(bean.getPaymentAmount()).subtract(total);//未使用预算
		        			         }
	                				 
	                				 if(record.get("_IS_COUNT").toString().equals("1")) {
	                					 if(!StringUtils.isEmpty(bean.getCount())) {
	                						 if(Integer.parseInt(fee[2].toString()) != bean.getCount()) {
		                						 flag = 1;
		                						 info += "采购数量和目标数量不一致；";
		                					 }
	                					 }
	                				 }
	                				 if(record.get("_IS_FEE_USE_MORE").toString().equals("1")) {
	                					 if(record.get("_FEE_USE_MORE_PERIOD").toString().equals("0")) {
	                						 if(nowMonth < 3){
	                            				 if(budgetFee.divide(total, 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(hasUsePercent.toString())) == 1) {
	                            					 flag = 1;
	                        						 info += "经费使用过多预警；";
	                                    		 }
	                						 }
	                					 }
	                					 else if(record.get("_FEE_USE_MORE_PERIOD").toString().equals("1")) {
	                						 if(nowMonth > 5 && nowMonth < 11){
	                            				 if(budgetFee.divide(total, 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(hasUsePercent.toString())) == 1) {
	                            					 flag = 1;
	                        						 info += "经费使用过多预警；";
	                                    		 }
	                						 }
	                					 }
	                					 else if(record.get("_FEE_USE_MORE_PERIOD").toString().equals("2")) {
	                						 if(nowMonth > 10){
	                            				 if(budgetFee.divide(total, 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(hasUsePercent.toString())) == 1) {
	                            					 flag = 1;
	                        						 info += "经费使用过多预警；";
	                                    		 }
	                						 }
	                					 }
	                				 }
	                				 if(record.get("_IS_NOT_USE").toString().equals("1")) {
	                					 if(record.get("_NOT_USE_PERIOD").toString().equals("0")) {
	                						 if(nowMonth < 3){
	                            				 if(budgetFee.divide(noUseTotal, 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(noUsePercent.toString())) == 1) {
	                            					 flag = 1;
	                        						 info += "经费未使用预警；";
	                                    		 }
	                						 }
	                					 }
	                					 else if(record.get("_NOT_USE_PERIOD").toString().equals("1")) {
	                						 if(nowMonth > 5 && nowMonth < 11){
	                            				 if(budgetFee.divide(noUseTotal, 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(noUsePercent.toString())) == 1) {
	                            					 flag = 1;
	                        						 info += "经费未使用预警；";
	                                    		 }
	                						 }
	                					 }
	                					 else if(record.get("_NOT_USE_PERIOD").toString().equals("2")) {
	                						 if(nowMonth > 10){
	                            				 if(budgetFee.divide(noUseTotal, 2, BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(noUsePercent.toString())) == -1) {
	                            					 flag = 1;
	                        						 info += "经费未使用预警；";
	                                    		 }
	                						 }
	                					 }
	                				 }
	                				 
	                				 if(flag == 1) {
	                        			 bean.setWarning("<div title=\'"+info+"\' style='margin-left:75px;width:15px;height:15px;background:red;border-radius:7.5px'></div>");
	                        		 }
	        			         }
                			 }
                    		 BigDecimal balance = new BigDecimal(total.subtract(budgetFee).doubleValue());
                    		 
                    		 bean.setBudgetFee(budgetFee);
                    		 bean.setBalance(balance);
                    		 bean.setFeeCount(Integer.parseInt(fee[2].toString()));
                    		 
                    		 jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, bean);
                		 }
                	 }
                  }
             }
         }

         return jsonResult;
    }

    /**
     * 查找单条记录，返回Json格式。
     * 
     * @param id ID
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
     * 导出决算表
     * 
     * @param response
     * @param locale
     */
    @RequestMapping("/export-final-excel-file")
    public void exportFinalBudgetExcel(@RequestParam String project_, @RequestParam String year, @RequestParam String startDate, @RequestParam String endDate, @ModelAttribute ProjectBean bean,HttpServletResponse response,Locale locale) {
        
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
    

    private File generateBudgetFinalExcelFile(String project_, int year, String startDate, String endDate) throws IOException {
    	FileOutputStream output = null;
        File file = null;
        try {
            List<BudgetEstimate> BudgetEstimates = this.budgetEstimateService.getBudgetByYear(year, project_);
//        	CommonFilter filter = new CommonFilter().addExact("year", year).addExact("version", "V0.0");
//
//            List<OrderByDto> orders = new ArrayList<>();
//            orders.add(new OrderByDto("code", false));
//
//            List<BudgetEstimate> BudgetEstimates = this.budgetEstimateService.list(filter,orders);
            List<Object> totalFeeList = this.budgetFeeService.getBudgetFeeList(startDate, endDate);
            
            List<Object> siteFeeList = this.budgetFeeService.getFeeBySite(startDate, endDate);
            
            List<Object> siteNameList = this.budgetFeeService.getSiteNameList(startDate, endDate);

            @SuppressWarnings("resource")
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("决算表");
            Row header = sheet.createRow(0);
            header.setHeight((short) 800);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));//合并列(起始行，结束行，起始列，结束列)

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
            for (int i=0; i < siteNameList.size(); i++) {
            	//Object[] fee = (Object[]) siteNameList.get(i);
            	title.createCell(13+3*i).setCellValue(siteNameList.get(i).toString());
            	title.createCell(14+3*i);
            	title.createCell(15+3*i);
            	//title.createCell(14+3*i).setCellValue(fee[0].toString());
            	//合并列(起始行，结束行，起始列，结束列)
    	    	sheet.addMergedRegion(new CellRangeAddress(2,2,13+3*i,15+3*i));
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
            for (int i=0; i < siteNameList.size(); i++) {
            	title.getCell(13+3*i).setCellStyle(border);
            	title.getCell(14+3*i).setCellStyle(border);
            	title.getCell(15+3*i).setCellStyle(border);
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
            for (int i=0; i < siteNameList.size(); i++) {
            	num.createCell(13+i*3).setCellValue("支出数量");
            	num.createCell(14+i*3).setCellValue("支出单价(万元)");
            	num.createCell(15+i*3).setCellValue("支出金额(万元)");
            	num.getCell(13+3*i).setCellStyle(border);
            	//num.getCell(14+3*i).setCellStyle(border);
            	num.getCell(15+3*i).setCellStyle(border);
            }

            for (int i = 0; i < BudgetEstimates.size(); i++) {

                BudgetEstimate BudgetEstimate = BudgetEstimates.get(i);
                Row row = sheet.createRow(i+4);
                row.createCell(0).setCellValue(BudgetEstimate.getCode());
                row.createCell(1).setCellValue(BudgetEstimate.getInnerCode());
                row.createCell(2).setCellValue(BudgetEstimate.getOrderNumber());
                row.createCell(3).setCellValue(BudgetEstimate.getName());
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
                for (int m=0; m < siteNameList.size(); m++) {
                	row.createCell(13+3*m).setCellStyle(border);
                	row.createCell(14+3*m).setCellStyle(border);
                	row.createCell(15+3*m).setCellStyle(border);
                }
                
                for (int j = 0; j < totalFeeList.size(); j++) {
                	Object[] fee = (Object[]) totalFeeList.get(j);
                	if (fee[0].equals(BudgetEstimate.getCode())) {
                		Double doubleVal = (Double.parseDouble(fee[1].toString())) / 10000;
                		int sl = Integer.parseInt(fee[2].toString());
                		//Double djy = Double.parseDouble(fee[3].toString());
                        if (!StringUtils.isEmpty(BudgetEstimate.getCount())) {
                        	row.getCell(9).setCellValue(sl);
                        }
                        
                        /*if (!StringUtils.isEmpty(BudgetEstimate.getTaxInclusivePrice())) {
                        	row.getCell(10).setCellValue(djy);
                        }*/
                        if (!StringUtils.isEmpty(BudgetEstimate.getTaxTotalPrice())) {
                        	row.getCell(11).setCellValue(doubleVal);
                        }
                        if (!StringUtils.isEmpty(BudgetEstimate.getTaxTotalPrice())) {
                        	row.getCell(12).setCellValue(BudgetEstimate.getTaxTotalPrice() - doubleVal);
                        }
                	}
                }
                
                for (int k=0; k < siteNameList.size(); k++) {
                	//Object[] fee1 = (Object[]) siteNameList.get(k);
                	for (int j=0; j < siteFeeList.size(); j++) {
                		Object[] fee = (Object[]) siteFeeList.get(j);
                		if (siteNameList.get(k).toString().equals(fee[0])) {
                        	if (fee[1].equals(BudgetEstimate.getCode())) {
                        		row.getCell(13+k*3).setCellValue(Integer.parseInt(fee[2].toString()));
                        		//row.getCell(14+k*3).setCellValue((Double.parseDouble(fee[3].toString())) / 10000);
                        		row.getCell(15+k*3).setCellValue((Double.parseDouble(fee[3].toString())) / 10000);
                        	}
                		}
                	}
                }
            }

            Date now = new Date();

            String fileName = "未来网络试验设施项目分专业概算明细表"
                    + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
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
    

    @RequestMapping("/auditSubmit")
  	@ResponseBody
  	@SysLogAnnotation(description = "经费决算", action = "审核")
  	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean
  			, @RequestParam String year, @RequestParam String projectId, @ModelAttribute BudgetEstimateBean bean, Locale locale) {
  		JsonResultBean jsonResult = new JsonResultBean();
  		if (bean == null) {
  			jsonResult.setStatus(JsonStatus.ERROR);
  			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
  			return jsonResult;
  		}
  		try {
  			// 插入到t_oa_flow_history
  	  		String dataId = projectId + "," + year;
  			String userName=userBean.getSessionLoginName();
 			String creatorId = userBean.getSessionUserId();
  			int oaType = OAAuditApiType.OA_TYPE_BUDGETFINAL.value();
  			
  			//判断是否有未走完流程的审批
  	    	CommonFilter of = new CommonFilter().addExact("category", oaType).addExact("flowAction", 1);
  			List<OaFlowHistory> oaFlowHistoryList = oaFlowHistoryService.list(of, null);
  			if(oaFlowHistoryList.size() > 0) {
  				jsonResult.setStatus(JsonStatus.ERROR);
  				jsonResult.setMessage("当前还有审批未结束");
  				return jsonResult;
  			}
  			
  			CommonFilter f = new CommonFilter();
  			
  			HashMap<String, Object> reqMap = new HashMap<>();
  			List<HashMap<String, Object>> budgets = new ArrayList<>();
  			
 	        //加上报销金额、报销数量、余额
 	        List<Object> budgetFeeList = this.budgetFeeService.getFeeByYearAndCode(year);
 	         
	        BudgetFinalBeanConverter converter = new BudgetFinalBeanConverter();
	        Date now = new Date();
	        List<BudgetFinalBean> budgetFinalBeanList = new ArrayList<BudgetFinalBean>();
  			
  	        if (year.equals("9999")) {//总表
  	        	 year = "2019,2020,2021,2022,2023";
  	        	 
  	             //排序
  	             List<OrderByDto> orders = new ArrayList<>();
  	             orders.add(new OrderByDto("code", false));
  	             
  	           	 f = new CommonFilter().addExact("project.id", projectId);
  	        	 
  	             List<BudgetEstimateSum> budgetsumList = budgetEstimateSumService.list(f, orders);
  	             long total = budgetEstimateSumService.list(f, orders).size();//总数
  	             List<BudgetEstimateSumBean> budgetEstimateSumBeanList = new ArrayList<BudgetEstimateSumBean>();
  	           
  	             if ((budgetsumList != null) && (budgetsumList.size() > 0)) {
  	                 for (BudgetEstimateSum b : budgetsumList) {
  	                	HashMap<String, Object> budgetMap = new HashMap<String, Object>();
  	                	budgetMap.put("budgetId", b.getId());
  	                	budgetMap.put("code", b.getCode());
  	                	budgetMap.put("year", b.getYear());
  	                	budgetMap.put("orderNum", b.getOrderNumber());
  	                	budgetMap.put("name", b.getName());
  	                	budgetMap.put("unit", b.getMeasurementUnit());  			
  	                	budgetMap.put("count", b.getCount());
  	                	budgetMap.put("taxInclusivePrice", b.getTaxInclusivePrice());
  	                	budgetMap.put("taxTotalPrice", b.getTaxTotalPrice());
	  	  				if (b.getGroup() != null) {
	  						budgetMap.put("groupName", b.getGroup().getVirtualOrgName());
	  					} else {
	  						budgetMap.put("groupName", "");
	  					}
	  	  				
	  	                 // 将数据新增到决算表中
	  	                 BudgetFinalBean finalBean = new BudgetFinalBean();
	  	                 finalBean.setProject_(b.getProject().getId());
	  	                 finalBean.setCode(b.getCode());
	  	                 finalBean.setOrderNumber(b.getOrderNumber());
	  	                 finalBean.setName(b.getName());
	  	                 finalBean.setUnit(b.getMeasurementUnit());
	  	                 finalBean.setCount(Double.valueOf(b.getCount()));
	  	                 finalBean.setTaxInclusivePrice(b.getTaxInclusivePrice());
	  	                 finalBean.setTaxInclusivePriceTotal(b.getTaxTotalPrice());
	  	                 finalBean.setYear(b.getYear());
	  	                 finalBean.setGroup_(b.getGroup().getId());
	  	  				
	  	                 // 使用金额、报销数量、结余
	  	                 for (int j=0; j < budgetFeeList.size(); j++) {
	  	                	 Object[] fee = (Object[]) budgetFeeList.get(j);
	  	                	 if(b.getCode().equals(fee[0].toString())) {
	  	                		 BigDecimal budgetFee = new BigDecimal(fee[1].toString());
	  	                		 BigDecimal totalFee = new BigDecimal(b.getTaxTotalPrice()).multiply(new BigDecimal(10000));
	  	                		 BigDecimal balance = totalFee.subtract(budgetFee);
	  	  	                	 budgetMap.put("reimburseAmount", budgetFee);
	  	  	                	 budgetMap.put("reimburseCount", budgetFeeList.size());
	  	  	                	 budgetMap.put("balance", balance);
	  	  	                	 
	  	  	                	 finalBean.setReimburseAmount(budgetFee.doubleValue());
	  	  	                	 finalBean.setReimburseCount(budgetFeeList.size());
	  	  	                	 finalBean.setBalance(balance.doubleValue());
	  	                	 }
	  	                 }
	  	                 
	  					 budgets.add(budgetMap);
	  					 budgetFinalBeanList.add(finalBean);
  	                 }
  	             }
  	         }
  	         else {
  	        	 //获取选中年份的所有版本，按创建时间倒序
  	             List<OrderByDto> orders = new ArrayList<>();
  	             orders.add(new OrderByDto("cDatetime", true));
  	             f = new CommonFilter().addExact("project.id", projectId).addExact("year",Integer.parseInt(year)).addExact("budgetMain.flowStatus", 2);
  	             List<BudgetEstimateVersion> budgetVersionList = budgetEstimateVersionService.list(f, orders);
  	             
  	             //根据budgetVersionList第一条数据的version查找预算
  	             if(budgetVersionList.size()>0) {
  	            	 f = new CommonFilter().addExact("version", budgetVersionList.get(0).getId());
  	             }
  	             else {//随便赋值，只要搜不出来
  	            	 f = new CommonFilter().addExact("version", "V111");
  	             }
  	             
  	             List<OrderByDto> orders1 = new ArrayList<>();
  	             orders1.add(new OrderByDto("code", false));
  	        	 List<BudgetEstimate> budgetList = budgetEstimateService.list(f, orders1);
  	        	 
  	             if ((budgetList != null) && (budgetList.size() > 0)) {
  	                 for (BudgetEstimate b : budgetList) {
   	                	HashMap<String, Object> budgetMap = new HashMap<String, Object>();
   	                	budgetMap.put("budgetId", b.getId());
   	                	budgetMap.put("code", b.getCode());
   	                	budgetMap.put("year", b.getYear());
   	                	budgetMap.put("orderNum", b.getOrderNumber());
   	                	budgetMap.put("name", b.getName());
   	                	budgetMap.put("unit", b.getMeasurementUnit());  			
   	                	budgetMap.put("count", b.getCount());
   	                	budgetMap.put("taxInclusivePrice", b.getTaxInclusivePrice());
   	                	budgetMap.put("taxTotalPrice", b.getTaxTotalPrice());
 	  	  				if (b.getGroup() != null) {
 	  						budgetMap.put("groupName", b.getGroup().getVirtualOrgName());
 	  					} else {
 	  						budgetMap.put("groupName", "");
 	  					}
 	  	  				
	  	                // 将数据新增到决算表中
	  	                BudgetFinalBean finalBean = new BudgetFinalBean();
	  	                finalBean.setProject_(b.getProject().getId());
	  	                finalBean.setCode(b.getCode());
	  	                finalBean.setOrderNumber(b.getOrderNumber());
	  	                finalBean.setName(b.getName());
	  	                finalBean.setUnit(b.getMeasurementUnit());
	  	                finalBean.setCount(b.getCount());
	  	                finalBean.setTaxInclusivePrice(b.getTaxInclusivePrice());
	  	                finalBean.setTaxInclusivePriceTotal(b.getTaxTotalPrice());
	  	                finalBean.setYear(b.getYear());
	  	                if (b.getGroup() != null) {
	  	                	finalBean.setGroup_(b.getGroup().getId());
	  	                }
 	  	  				
	  	                // 使用金额、报销数量、结余
 	  	                for (int j=0; j < budgetFeeList.size(); j++) {
 	  	                	 Object[] fee = (Object[]) budgetFeeList.get(j);
 	  	                	 if(b.getCode().equals(fee[0].toString())) {
 	  	                		 BigDecimal budgetFee = new BigDecimal(fee[1].toString());
 	  	                		 BigDecimal total = new BigDecimal(b.getTaxTotalPrice()).multiply(new BigDecimal(10000));
 	  	                		 BigDecimal balance = total.subtract(budgetFee);
 	  	  	                	 budgetMap.put("reimburseAmount", budgetFee);
 	  	  	                	 budgetMap.put("reimburseCount", budgetFeeList.size());
 	  	  	                	 budgetMap.put("balance", balance);
 	  	  	                	 
	  	  	                	 finalBean.setReimburseAmount(budgetFee.doubleValue());
	  	  	                	 finalBean.setReimburseCount(budgetFeeList.size());
	  	  	                	 finalBean.setBalance(balance.doubleValue());
 	  	                	 }
 	  	                }
 	  	                
 	  					budgets.add(budgetMap);
 	  					budgetFinalBeanList.add(finalBean);
  	                 }
  	             }
  	         }
  	         
  	         
  			//将数据存到excel中
			File downloadFile = null;
			downloadFile = this.generateExcelFile(budgetFinalBeanList);
  			
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
  			logger.info("[OA Audit] No.23 BudgetEstimateFinal");
  			// 调用oa接口
  			jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
			if(JsonStatus.OK == 1){
				logger.info("[OA Audit] No.23 BudgetEstimateFinal Audit request success, history year was {}", year);
				
				// 获取的数据存到决算表中
				for (BudgetFinalBean b : budgetFinalBeanList) {
					BudgetFinal budgetFinal = converter.toEntity(b, null, userBean, now);
					budgetFinal.setAuditStatus(String.valueOf(FlowStatusEnum.REVIEWING.value()));
					budgetFinal.setModifier(userName);
					budgetFinal.setmDatetime(new Date());
  					BudgetFinalBeanConverter converter1 = new BudgetFinalBeanConverter();
					this.budgetFinalService.create(budgetFinal);
				}
				
			}
  		} catch (Exception e) {
  			getLogger().error("Project audio error.", e);

  			// 在JSON对象内设定服务器处理结果状态
  			jsonResult.setStatus(JsonStatus.ERROR);
  		}

  		return jsonResult;
  	}
    
    
	//生成excel
	private File generateExcelFile(List<BudgetFinalBean> budgetFinalBeanList) throws IOException {
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

			Sheet sheet = wb.createSheet("经费决算");

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
			row1.createCell(3).setCellValue("单位");
			row1.getCell(3).setCellStyle(textCellStyle2);
			row1.createCell(4).setCellValue("数量");
			row1.getCell(4).setCellStyle(textCellStyle2);
			row1.createCell(5).setCellValue("含税单价（万元）");
			row1.getCell(5).setCellStyle(textCellStyle2);
			row1.createCell(6).setCellValue("含税总价（万元）");
			row1.getCell(6).setCellStyle(textCellStyle2);
			row1.createCell(7).setCellValue("使用金额（元）");
			row1.getCell(7).setCellStyle(textCellStyle2);
			row1.createCell(8).setCellValue("报销数量");
			row1.getCell(8).setCellStyle(textCellStyle2);
			row1.createCell(9).setCellValue("预算结余（元）");
			row1.getCell(9).setCellStyle(textCellStyle2);
			row1.createCell(10).setCellValue("年份");
			row1.getCell(10).setCellStyle(textCellStyle2);
			row1.createCell(11).setCellValue("专业组");
			row1.getCell(11).setCellStyle(textCellStyle2);

			for (int i = 0; i < budgetFinalBeanList.size(); i++) {
	   				Row info = sheet.createRow(1 + i);
	   				String code = budgetFinalBeanList.get(i).getCode();
	   				String orderNumber = budgetFinalBeanList.get(i).getOrderNumber();
	   				String name = budgetFinalBeanList.get(i).getName();
	   				String measurementUnit = budgetFinalBeanList.get(i).getUnit();
	   				Double count = budgetFinalBeanList.get(i).getCount();
	   				Double taxInclusivePrice = budgetFinalBeanList.get(i).getTaxInclusivePrice();
	   				Double taxExcludingPrice = budgetFinalBeanList.get(i).getTaxInclusivePriceTotal();
	   				String groupName = budgetFinalBeanList.get(i).getGroupText();
	   				Double ReimburseAmount = budgetFinalBeanList.get(i).getReimburseAmount();
	   				int reimburseCount = 0;
	   				if(budgetFinalBeanList.get(i).getReimburseCount() != null) {
	   					reimburseCount = budgetFinalBeanList.get(i).getReimburseCount();
	   				}
	   				Double balance = budgetFinalBeanList.get(i).getBalance();
	   				int budgetYear = 0;
	   				if(budgetFinalBeanList.get(i).getYear() != null) {
	   					budgetYear = budgetFinalBeanList.get(i).getYear();
	   				}
	   				
	   				if(code != null) {
	   					info.createCell(0).setCellValue(code);
	   				}
	   				if(orderNumber != null) {
	   					info.createCell(1).setCellValue(orderNumber);
	   				}
	   				if(name != null) {
	   					info.createCell(2).setCellValue(name);
	   				}
	   				if(measurementUnit != null) {
	   					info.createCell(3).setCellValue(measurementUnit);
	   				}
	   				if(count != null) {
	   					info.createCell(4).setCellValue(count);
	   				}
	   				if(taxInclusivePrice != null) {
	   					info.createCell(5).setCellValue(taxInclusivePrice);
	   				}
	   				if(taxExcludingPrice != null) {
	   					info.createCell(6).setCellValue(taxExcludingPrice);
	   				}
	   				if(ReimburseAmount != null) {
	   					info.createCell(7).setCellValue(ReimburseAmount);
	   				}
	   				if(!"".equals(reimburseCount +"")) {
	   					info.createCell(8).setCellValue(reimburseCount);
	   				}
	   				if(balance != null) {
	   					info.createCell(9).setCellValue(balance);
	   				}
	   				if(!"".equals(budgetYear +"")) {
	   					info.createCell(10).setCellValue(budgetYear);
	   				}
	   				if(groupName != null) {
	   					info.createCell(11).setCellValue(groupName);
	   				}
			}

			Date now = new Date();

			String fileName = "经费决算_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + "budgetFinal" + File.separator + randomID);

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
	
    @RequestMapping("/get_order_list")
    @ResponseBody
    public JsonResultBean getAuditLogInfo(String correlateDataId,String code,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();

        try {
        	List<BudgetMainfeeBean> feeBeans = new ArrayList<>();
        	
        	//根据所选记录的code查找总表id
			CommonFilter filter = new CommonFilter().addExact("code", code);
			List<BudgetEstimateSum> budgetEstimateSums = this.budgetEstimateSumService.list(filter,null);
			
			if(budgetEstimateSums.size() > 0) {
				//根据总表id查找采购明细
				CommonFilter filter1 = new CommonFilter().addExact("budgetEstimateSum.id", budgetEstimateSums.get(0).getId());
				List<PurchaseOrderDetail> purchaseOrderDetails = this.purchaseOrderDetailService.list(filter1,null);

				int i = 0;
	            for (PurchaseOrderDetail order : purchaseOrderDetails) {
	            	
	            	//判断采购编号是否有重复，有就跳过
	            	int flag = 0;
	            	for(int j=0 ; j<i ; j++) {
	            		if(purchaseOrderDetails.get(j).getPurchaseOrder().getPurchaseOrderNo().equals(purchaseOrderDetails.get(i).getPurchaseOrder().getPurchaseOrderNo())) {
	            			flag = 1;
	            		}
	            	}
	            	
	            	if(flag == 0) {
	            		//根据采购编号查找费用报销记录
		    			CommonFilter filter2 = new CommonFilter().addExact("purchaseNo", order.getPurchaseOrder().getPurchaseOrderNo());
		    			List<BudgetMainfee> budgetMainfees = this.budgetMainfeeService.list(filter2,null);
		    			
		    			for(BudgetMainfee fee : budgetMainfees) {
		    				BudgetMainfeeBean bean = new BudgetMainfeeBean();
		    				
		    				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		    				if(fee.getApprovalTime() != null) {
		    					bean.setApprovalTime(sdf.format(fee.getApprovalTime()));
		    				}
		    				if(fee.getApprover() != null) {
		    					bean.setApproverText(fee.getApprover().getPersonName());
		    				}
		    				if(fee.getcDatetime() != null) {
		    					bean.setcDatetime(DateUtils.date2String(fee.getcDatetime(), Constants.DATE_FORMAT));
		    				}
		    				bean.setMainid(fee.getMainid());
		    				bean.setPayAmount(fee.getPayAmount());
		    				bean.setPayRatio(fee.getPayRatio());
		    				bean.setPurchaseNo(fee.getPurchaseNo());
		    				if(fee.getRequestPerson() != null) {
		    					bean.setRequestPersonText(fee.getRequestPerson().getPersonName());
		    				}
		    				if(fee.getRequestTime() != null) {
		    					bean.setRequestTime(sdf.format(fee.getRequestTime()));
		    				}
		    				bean.setTotalPrice(fee.getPayAmount());
		    				if(fee.getVirtualOrg() != null) {
		    					bean.setVirtualOrgText(fee.getVirtualOrg().getVirtualOrgName());
		    				}
		    				bean.setWorkflowName(fee.getWorkflowName());
		    				
		    				feeBeans.add(bean);
		    			}
		    			i++;
	            	}
	            }
			}
			
            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, feeBeans.size());// easyui-datagrid分页用
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, feeBeans);// easyui-datagrid分页用
        } catch (Exception e) {
            getLogger().error("Exception while audit-log-info" + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

}
