package project.edge.web.controller.budget;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
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
import garage.origin.common.constant.FieldValueType;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
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
import project.edge.domain.converter.CapitalPlanBeanConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateSum;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.CapitalPlan;
import project.edge.domain.entity.OaFlowHistory;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetEstimateBean;
import project.edge.domain.view.BudgetEstimateSumBean;
import project.edge.domain.view.CapitalPlanBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateSumService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.budget.CapitalPlanService;
import project.edge.service.flow.OaFlowHistoryService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.TreeGridController;

/**
 * 资金计划总表管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/capitalPlan")
public class CapitalPlanController extends TreeGridController<BudgetEstimate, BudgetEstimateBean> {

    private static final Logger logger = LoggerFactory.getLogger(BudgetEstimateController.class);

    private static final String PID = "P10017";
    
	@Autowired
	HttpServletRequest request;
	
    @Resource
    private CapitalPlanService capitalPlanService;
    
    @Resource
    private BudgetEstimateService budgetEstimateService;
    
    @Resource
    private ProjectService projectService;
    
    @Resource
    private BudgetEstimateSumService budgetEstimateSumService;
    
    @Resource
    private BudgetEstimateVersionService budgetEstimateVersionService;
    
	@Resource
	private VirtualOrgService virtualOrgService;
	
	@Resource
	CreateWorkFlowManager createWorkFlowManager;
	
	@Resource
	private OaFlowHistoryService oaFlowHistoryService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.BUDGET_CAPITALPLAN.value();
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
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/budget/capitalPlanJs.jsp";
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
		
		List<ComboboxOptionBean> levelOptions = new ArrayList<>();
		levelOptions.add(new ComboboxOptionBean("1", "一级分类"));
		levelOptions.add(new ComboboxOptionBean("2", "二级分类"));
		levelOptions.add(new ComboboxOptionBean("3", "三级分类"));
		
		optionMap.put("yearOptions", yearOptions);
		optionMap.put("levelOptions", levelOptions);
		return optionMap;
	}
    
    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 导出文件
        jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
                String.format("CAPITALPLAN.exportExcelFile('#%1$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
        // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT, String.format(
                "CAPITALPLAN.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, CAPITALPLAN);",
                idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                urlMap.get(ControllerUrlMapKeys.FIND),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
		// 修改保存
		jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT,
				String.format("CAPITALPLAN.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
						idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						false));
        
        if (isOaFlow()) {
    		// OA审批
    		jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT, String.format("CAPITALPLAN.handleAuditClick('#%1$s', '%2$s');",
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

    	// 不使用分页
    	model.addAttribute(ControllerModelKeys.IS_PAGE, false);
    	
        // Datagrid行双击事件，增加回调，用来刷新项目集下拉框的内容
        model.addAttribute(ControllerModelKeys.DBL_CLICK_ROW_HANDLER,
                "CAPITALPLAN.handleDblClickRow");
        
        return super.main(paramMap, model, userBean, locale);
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
     */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) throws JsonParseException, JsonMappingException, IOException {

    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
    	String projectId = null; 
    	int level = 0; 
    	if(commonFilterJson.length() != 0) {
        	for(int i=0;i<filter.getFilterFieldList().size();i++) {
        		if(filter.getFilterFieldList().get(i).getFieldName().equals("project.id")) {
        			projectId = filter.getFilterFieldList().get(i).getValue().toString();
        		} else if (filter.getFilterFieldList().get(i).getFieldName().equals("level")) {
        			level = Integer.parseInt(filter.getFilterFieldList().get(i).getValue().toString());
				}
        	}
    	}
    	CommonFilter f = new CommonFilter().addExact("project.id", projectId);
		if (level != 0) {
			//f = new CommonFilter().addExact("level", level);
			f = new CommonFilter().addRange("level", FieldValueType.INT, 0, level, true, false);
		}
		List<OrderByDto> orders = new ArrayList<>();
		OrderByDto order1 = new OrderByDto();
		order1.setColumnName("code");
		orders.add(order1);
/*		PageCtrlDto page1 = new PageCtrlDto();
		page1.setPageSize(30);
		page1.setCurrentPage(1);*/
    	List<BudgetEstimateSum> budgetSumList = budgetEstimateSumService.list(f, orders);
    	
		// 专业组：对应到t_virtual_org的org_order
		CommonFilter f1 = new CommonFilter();
		if (!StringUtils.isEmpty(projectId)) {
			f1 = new CommonFilter().addExact("project.id", projectId);
		}
		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f1, null);
		
		BudgetEstimateSumBeanConverter converter = new BudgetEstimateSumBeanConverter();
    	List<BudgetEstimateBean> budgetList = new ArrayList();
    	
    	//每年版本
		List<OrderByDto> vorders1 = new ArrayList<>();
		OrderByDto vorder1 = new OrderByDto();
		vorder1.setColumnName("cDatetime");
		vorder1.setDesc(true);
		vorders1.add(vorder1);
    	CommonFilter vf1 = new CommonFilter().addExact("year", 2019).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
    	List<BudgetEstimateVersion> versionList1 = budgetEstimateVersionService.list(vf1, vorders1);
    	CommonFilter vf2 = new CommonFilter().addExact("year", 2020).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
    	List<BudgetEstimateVersion> versionList2 = budgetEstimateVersionService.list(vf2, vorders1);
    	CommonFilter vf3 = new CommonFilter().addExact("year", 2021).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
    	List<BudgetEstimateVersion> versionList3 = budgetEstimateVersionService.list(vf3, vorders1);
    	CommonFilter vf4 = new CommonFilter().addExact("year", 2022).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
    	List<BudgetEstimateVersion> versionList4 = budgetEstimateVersionService.list(vf4, vorders1);
    	CommonFilter vf5 = new CommonFilter().addExact("year", 2023).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
    	List<BudgetEstimateVersion> versionList5 = budgetEstimateVersionService.list(vf5, vorders1);
    	
    	
    	for(int i=0;i<budgetSumList.size();i++) {
/*    		if(budgetSumList.get(i).getLevel()==1) {//一级分类
    			for(int j=0;j<budgetSumList.size();j++) {
    				if(budgetSumList.get(j).getLevel()==2 && budgetSumList.get(j).getInnerCode().equals(budgetSumList.get(i).getCode())) {//二级分类
    					for(int m=0;m<budgetSumList.size();m++) {
    	    				if(budgetSumList.get(m).getLevel()==3 && budgetSumList.get(m).getInnerCode().equals(budgetSumList.get(j).getCode())) {//三级分类
*/    	    					
    	    					BudgetEstimateBean bean = new BudgetEstimateBean();
    	    					bean.setName(budgetSumList.get(i).getName());
    	    					
    			    			/*bean.setOneLevelClassification(budgetSumList.get(i).getName());
    			    			bean.setTwoLevelClassification(budgetSumList.get(j).getName());
    			    			bean.setThreeLevelClassification(budgetSumList.get(m).getName());*/
    			    			bean.setCode(budgetSumList.get(i).getCode());
    			    			bean.setProject_(budgetSumList.get(i).getProject().getId());
    			    			
    	    					//2019资金计划
    	    			    	if(versionList1.size()>0) {
    	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList1.get(0).getId()).addExact("code", budgetSumList.get(i).getCode());
    	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
    	    			    		if(budget1.size()>0) {
    	    			    			bean.setCapital2019(budget1.get(0).getPayAmountTotal());
    	    			    			bean.setRemark2019(budget1.get(0).getPlanRemark());
    	    			    		}
    	    			    			
    	    			    	}
    	    					
    	    					//2020资金计划
    	    			    	if(versionList2.size()>0) {
    	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList2.get(0).getId()).addExact("code", budgetSumList.get(i).getCode());
    	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
    	    			    		if(budget1.size()>0) {   
    	    			    			bean.setCapital2020(budget1.get(0).getPayAmountTotal());
    	    			    			bean.setRemark2020(budget1.get(0).getPlanRemark());
    	    			    		}
    	    			    			
    	    			    	}
    	    					
    	    					//2021资金计划
    	    			    	if(versionList3.size()>0) {
    	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList3.get(0).getId()).addExact("code", budgetSumList.get(i).getCode());
    	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
    	    			    		if(budget1.size()>0) {   
    	    			    			bean.setCapital2021(budget1.get(0).getPayAmountTotal());
    	    			    			bean.setRemark2021(budget1.get(0).getPlanRemark());
    	    			    		}
    	    			    			
    	    			    	}
    	    					
    	    					//2022资金计划
    	    			    	if(versionList4.size()>0) {
    	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList4.get(0).getId()).addExact("code", budgetSumList.get(i).getCode());
    	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
    	    			    		if(budget1.size()>0) { 
    	    			    			bean.setCapital2022(budget1.get(0).getPayAmountTotal());
    	    			    			bean.setRemark2022(budget1.get(0).getPlanRemark());
    	    			    		}
    	    			    			
    	    			    	}
    	    					
    	    					//2023资金计划
    	    			    	if(versionList5.size()>0) {
    	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList5.get(0).getId()).addExact("code", budgetSumList.get(i).getCode());
    	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
    	    			    		if(budget1.size()>0) { 
    	    			    			bean.setCapital2023(budget1.get(0).getPayAmountTotal());
    	    			    			bean.setRemark2023(budget1.get(0).getPlanRemark());
    	    			    		}
    	    			    			
    	    			    	}
    	    			    	
    	    			    	for(int n=0;n<virtualOrgList.size();n++) {
    	    			    		List<BudgetEstimateSumBean> budgetEstimateSumBeanList = new ArrayList<BudgetEstimateSumBean>();
    	    			    		budgetEstimateSumBeanList.add(converter.fromEntity(budgetSumList.get(i), this.messageSource, locale));
    	    			    		if(budgetEstimateSumBeanList.get(0).getGroup_()==virtualOrgList.get(n).getId()) {
    	    			    			bean.setCapitalGroup(virtualOrgList.get(n).getVirtualOrgName());;
    	    			    		}
    	    			    	}
    	    					
    	    			    	budgetList.add(bean);
/*    	    				}
    					}
    				}
    			}
    		}*/
    	}
    	
        // 准备JSON结果
        jsonResult.setStatus(JsonStatus.OK);
        jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, budgetList);
        jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, budgetList.size());

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
    	
        String[] s = id.split(",");
        String code = s[0];
        String projectId = s[1];
        
        JsonResultBean jsonResult = new JsonResultBean();
        try {
        	
        	// 默认选中当前年份
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Date date = new Date();
			String year = sdf.format(date);
			
			// 获取最新版本的版本号
			List<OrderByDto> orders = new ArrayList<>();
			OrderByDto order = new OrderByDto();
			order.setColumnName("cDatetime");
			order.setDesc(true);
			orders.add(order);
	    	CommonFilter vf = new CommonFilter().addExact("year", Integer.parseInt(year)).addExact("project.id", projectId);
	    	List<BudgetEstimateVersion> versionList = budgetEstimateVersionService.list(vf, orders);
        	
	    	if(versionList.size()>0) {
	            // 检查是否存在记录
				CommonFilter rf = new CommonFilter();
				rf.addExact("code", code).addExact("version", versionList.get(0).getId());
				List<BudgetEstimate> budgetList = budgetEstimateService.list(rf, null);
				
	            if (budgetList.size()==0) {
	                jsonResult.setStatus(JsonStatus.ERROR);
	                jsonResult.setMessage(this.messageSource
	                        .getMessage("最新版本的预算数据中未找到相关记录", null, locale));
	                return jsonResult;
	            }
	            
	            // 准备JSON结果
	            jsonResult.setStatus(JsonStatus.OK);
	            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT,
	                    this.getViewConverter().fromEntity(budgetList.get(0), this.messageSource, locale));
	    	}
	    	else {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource
                        .getMessage("没有预算版本生成，未查到相关记录", null, locale));
                return jsonResult;
	    	}

        } catch (Exception e) {
            this.getLogger().error("Exception finding the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
    
    
    /**
     * 选中年份，查找计划备注和下达金额，返回Json格式。
     * 
     * @param id ID
     * @param locale
     * @return
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public JsonResultBean getInfo(@RequestParam String year,@RequestParam String projectId,@RequestParam String code, Locale locale) {
    	
        
        JsonResultBean jsonResult = new JsonResultBean();
        try {
        	
			
			// 获取最新版本的版本号
			List<OrderByDto> orders = new ArrayList<>();
			OrderByDto order = new OrderByDto();
			order.setColumnName("cDatetime");
			order.setDesc(true);
			orders.add(order);
	    	CommonFilter vf = new CommonFilter().addExact("year", Integer.parseInt(year)).addExact("project.id", projectId);
	    	List<BudgetEstimateVersion> versionList = budgetEstimateVersionService.list(vf, orders);
        	
	    	if(versionList.size()>0) {
	            // 检查是否存在记录
				CommonFilter rf = new CommonFilter();
				rf.addExact("code", code).addExact("version", versionList.get(0).getId()).addExact("project.id", projectId);
				List<BudgetEstimate> budgetList = budgetEstimateService.list(rf, null);
				
	            if (budgetList.size()==0) {
	                jsonResult.setStatus(JsonStatus.ERROR);
	                jsonResult.setMessage(this.messageSource
	                        .getMessage("message.error.record.not.exist", null, locale));
	                return jsonResult;
	            }
	            
	            // 准备JSON结果
	            jsonResult.setStatus(JsonStatus.OK);
	            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT,
	                    this.getViewConverter().fromEntity(budgetList.get(0), this.messageSource, locale));
	    	}
	    	else {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource
                        .getMessage("message.error.record.not.exist", null, locale));
                return jsonResult;
	    	}

        } catch (Exception e) {
            this.getLogger().error("Exception finding the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
    
    /**
     * 修改，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public JsonResultBean update(@ModelAttribute BudgetEstimateBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
        String[] s = bean.getId().split(",");
        String code = s[0];
        String projectId = s[1];

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            // 检查是否有相应的权限
            if (!this.isUserHasAuthority(userBean, bean)) {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(
                        this.messageSource.getMessage("message.error.unauthorized", null, locale));
                return jsonResult;
            }

			// 获取最新版本的版本号
            
            List<OrderByDto> orders = new ArrayList<>();
			OrderByDto order = new OrderByDto();
			order.setColumnName("cDatetime");
			order.setDesc(true);
			orders.add(order);
			
	    	CommonFilter vf = new CommonFilter().addExact("year", bean.getYear()).addExact("project.id", projectId);
	    	List<BudgetEstimateVersion> versionList = budgetEstimateVersionService.list(vf, orders);
        	
	    	if(versionList.size()>0) {
	            // 更新到预算表
				budgetEstimateService.updateCapitalPlan(projectId, code, versionList.get(0).getId(), bean.getPlanRemark(), bean.getYear());
				
				CommonFilter rf = new CommonFilter();
				rf.addExact("code", code).addExact("version", versionList.get(0).getId()).addExact("project.id", projectId);
				List<BudgetEstimate> budgetList = budgetEstimateService.list(rf, null);
				
				BudgetEstimateBean budget = this.getViewConverter().fromEntity(budgetList.get(0), this.messageSource, locale);
				if(bean.getYear() == 2019) {
					budget.setRemark2019(budgetList.get(0).getPlanRemark());
				}
				else if(bean.getYear() == 2020) {
					budget.setRemark2020(budgetList.get(0).getPlanRemark());
				}
				else if(bean.getYear() == 2021) {
					budget.setRemark2021(budgetList.get(0).getPlanRemark());
				}
				else if(bean.getYear() == 2022) {
					budget.setRemark2022(budgetList.get(0).getPlanRemark());
				}
				else if(bean.getYear() == 2023) {
					budget.setRemark2023(budgetList.get(0).getPlanRemark());
				}
				
				budget.setId(bean.getId());
				
	            // 准备JSON结果
	            jsonResult.setStatus(JsonStatus.OK);
	            jsonResult.setMessage(
	                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
	            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT,budget);
	    	}
	    	else {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource
                        .getMessage("message.error.record.not.exist", null, locale));
                return jsonResult;
	    	}

        } catch (Exception e) {
            this.getLogger().error("Exception updating the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
    
    
	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "资金计划构成表", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, @RequestParam String projectId, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();

		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_BUDGET.value();
		String dataId = projectId;
		
		//判断是否有未走完流程的审批
    	CommonFilter of = new CommonFilter().addExact("category", oaType).addExact("flowAction", 1);
		List<OaFlowHistory> oaFlowHistoryList = oaFlowHistoryService.list(of, null);
		if(oaFlowHistoryList.size() > 0) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage("当前还有审批未结束");
			return jsonResult;
		}
		
    	CommonFilter f = new CommonFilter();
    	f.addExact("project.id", projectId);
		List<OrderByDto> orders = new ArrayList<>();
		OrderByDto order1 = new OrderByDto();
		order1.setColumnName("innerCode");
		orders.add(order1);
    	List<BudgetEstimateSum> budgetSumList = budgetEstimateSumService.list(f, orders);
    	
		// 专业组：对应到t_virtual_org的org_order
		CommonFilter f1 = new CommonFilter();
		f1 = new CommonFilter().addExact("project.id", projectId);
		
		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f1, null);
		
		BudgetEstimateSumBeanConverter converter = new BudgetEstimateSumBeanConverter();
    	
    	List<BudgetEstimate> budgetList = new ArrayList();
    	List<CapitalPlanBean> capitalPlanBeanList = new ArrayList<CapitalPlanBean>();
    	CapitalPlanBeanConverter converter1 = new CapitalPlanBeanConverter();
    	
		HashMap<String, Object> reqMap = new HashMap<>();
		List<HashMap<String, Object>> budgets = new ArrayList<>();
		
		List<OrderByDto> vorders1 = new ArrayList<>();
		OrderByDto vorder1 = new OrderByDto();
		vorder1.setColumnName("cDatetime");
		vorder1.setDesc(true);
		vorders1.add(vorder1);
		
		for(int i=0;i<budgetSumList.size();i++) {
			if(budgetSumList.get(i).getLevel()==1) {//一级分类
				for(int j=0;j<budgetSumList.size();j++) {
					if(budgetSumList.get(j).getLevel()==2 && budgetSumList.get(j).getInnerCode().equals(budgetSumList.get(i).getCode())) {//二级分类
						for(int m=0;m<budgetSumList.size();m++) {
		    				if(budgetSumList.get(m).getLevel()==3 && budgetSumList.get(m).getInnerCode().equals(budgetSumList.get(j).getCode())) {//三级分类
    	    					HashMap<String, Object> budgetMap = new HashMap<String, Object>();
    	    					BudgetEstimate budget = new BudgetEstimate();
    	    					CapitalPlanBean capital = new CapitalPlanBean();
    	    					
    	    					budgetMap.put("budgetId", budgetSumList.get(m).getId());
    	    					budgetMap.put("oneLevelClassification", budgetSumList.get(i).getName());
    	    					budgetMap.put("twoLevelClassification", budgetSumList.get(j).getName());
    	    					budgetMap.put("threeLevelClassification", budgetSumList.get(m).getName());
    	    					capital.setFirstClassify(budgetSumList.get(i).getName());
    	    					capital.setSecondClassify(budgetSumList.get(j).getName());
    	    					capital.setThirdClassify(budgetSumList.get(m).getName());
    	    					if(budgetSumList.get(m).getGroup()!=null) {
    	    						capital.setGroup_(budgetSumList.get(m).getGroup().getId());
    	    					}
    	    					capital.setProject_(projectId);
    	    					
    	    					for(int y=2019;y<2024;y++) {
    	    						capital.setYear(y);
        	    			    	CommonFilter vf1 = new CommonFilter().addExact("year", y);
        	    			    	List<BudgetEstimateVersion> versionList1 = budgetEstimateVersionService.list(vf1, vorders1);
        	    			    	if(versionList1.size()>0) {
        	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList1.get(0).getId()).addExact("innerCode", budgetSumList.get(m).getInnerCode());
        	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
        	    			    		if(budget1.size()>0) {
        	    			    			budget.setId(budget1.get(0).getId());
        	    			    			if(y == 2019) {
        	    			    				budgetMap.put("capital2019", budget1.get(0).getPayAmountTotal());
        	    			    				budgetMap.put("remark2019", budget1.get(0).getPlanRemark());
        	    			    			}
        	    			    			else if(y == 2020) {
        	    			    				budgetMap.put("capital2020", budget1.get(0).getPayAmountTotal());
            	    			    			budgetMap.put("remark2020", budget1.get(0).getPlanRemark());
        	    			    			}
        	    			    			else if(y == 2021) {
        	    			    				budgetMap.put("capital2021", budget1.get(0).getPayAmountTotal());
            	    			    			budgetMap.put("remark2021", budget1.get(0).getPlanRemark());
        	    			    			}
        	    			    			else if(y == 2022) {
        	    			    				budgetMap.put("budget2022", budget1.get(0).getPayAmountTotal());
            	    			    			budgetMap.put("capital2022", budget1.get(0).getPlanRemark());
        	    			    			}
        	    			    			else if(y == 2023) {
        	    			    				budgetMap.put("budget2023", budget1.get(0).getPayAmountTotal());
            	    			    			budgetMap.put("capital2023", budget1.get(0).getPlanRemark());
        	    			    			}
        	    			    			capital.setCapitalPlan(budget1.get(0).getPayAmountTotal());
        	    			    			capital.setRemarks(budget1.get(0).getPlanRemark());

        	    			    			capitalPlanBeanList.add(capital);
        	    			    		}
        	    			    			
        	    			    	}
    	    					}

    	    			    	
    	    			    	for(int n=0;n<virtualOrgList.size();n++) {
    	    			    		List<BudgetEstimateSumBean> budgetEstimateSumBeanList = new ArrayList<BudgetEstimateSumBean>();
    	    			    		budgetEstimateSumBeanList.add(converter.fromEntity(budgetSumList.get(i), this.messageSource, locale));
    	    			    		if(budgetEstimateSumBeanList.get(0).getGroup_()==virtualOrgList.get(n).getId()) {
    	    			    			budgetMap.put("groupName", virtualOrgList.get(n).getVirtualOrgName());
    	    			    		}
    	    			    	}
    	    					
    	    			    	budgets.add(budgetMap);
    	    			    	budgetList.add(budget);
    	    				}
    					}
    				}
    			}
    		}
    	}
    	
		reqMap.put("budgets", budgetList);
		Project entity = this.projectService.find(projectId);
		reqMap.put("projectName", entity.getProjectName());
		
		// 调用oa接口
		logger.info("[OA Audit] No.20 CapitalPlan");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		
		if(jsonResult.getStatus() == JsonStatus.OK){
			logger.info("[OA Audit] No.20 CapitalPlan Audit request success, history data id was {}", dataId);
			
			//将数据插到资金计划表中
			
			for (CapitalPlanBean capital : capitalPlanBeanList) {
				CapitalPlan capitalPlan = converter1.toEntity(capital, null, userBean, new Date());
				capitalPlan.setAuditStatus(String.valueOf(FlowStatusEnum.REVIEWING.value()));
				capitalPlan.setModifier(userName);
				capitalPlan.setmDatetime(new Date());
    			capitalPlanService.create(capitalPlan);
			}
		}

		return jsonResult;
	}

    /**
     * 导出资金计划
     * 
     * @param response
     * @param locale
     */
    @RequestMapping("/export-excel-file")
    public void exportBudgetVersionExcel(@RequestParam String projectId,HttpServletResponse response,
            HttpServletRequest request, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            File downloadFile = this.generateBudgetExcelFile(projectId,locale);

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
    
    
    private File generateBudgetExcelFile(String projectId,Locale locale) throws IOException {
        FileOutputStream output = null;
        File file = null;
        try {

            @SuppressWarnings("resource")
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("2019-2023年CENI项目资金计划构成表");

            sheet.setColumnWidth(0, 20 * 256);
            sheet.setColumnWidth(1, 20 * 256);
            sheet.setColumnWidth(2, 20 * 256);
            sheet.setColumnWidth(3, 35 * 256);
            sheet.setColumnWidth(4, 35 * 256);
            sheet.setColumnWidth(5, 35 * 256);
            sheet.setColumnWidth(6, 35 * 256);
            sheet.setColumnWidth(7, 35 * 256);
            sheet.setColumnWidth(8, 35 * 256);
            sheet.setColumnWidth(9, 35 * 256);
            sheet.setColumnWidth(10, 35 * 256);
            sheet.setColumnWidth(11, 35 * 256);
            sheet.setColumnWidth(12, 35 * 256);
            sheet.setColumnWidth(13, 20 * 256);

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
            //内容换行
            border.setWrapText(true);
            
            Row header = sheet.createRow(0);
            header.setHeight((short) 800);
            Cell headCell = header.createCell(0);
            headCell.setCellValue("2019-2023年CENI项目资金计划构成表");
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
            Font font = wb.createFont();
            font.setBold(true); // 加粗
            font.setFontHeightInPoints((short) 15); // 设置标题字体大小
            cellStyle.setFont(font);
            headCell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 11));

            Row title = sheet.createRow(1);
            title.createCell(0).setCellValue("一级分类");
            title.createCell(1).setCellValue("二级分类");
            title.createCell(2).setCellValue("三级分类");
            title.createCell(3).setCellValue("分年度资金计划（万元）");
            title.createCell(4);
            title.createCell(5);
            title.createCell(6);
            title.createCell(7);
            title.createCell(8).setCellValue("19年计划备注");
            title.createCell(9).setCellValue("20年计划备注");
            title.createCell(10).setCellValue("21年计划备注");
            title.createCell(11).setCellValue("22年计划备注");
            title.createCell(12).setCellValue("23年计划备注");
            title.createCell(13).setCellValue("专业组");
            
            Row yearHead = sheet.createRow(2);
            yearHead.createCell(0);
            yearHead.createCell(1);
            yearHead.createCell(2);
            yearHead.createCell(3).setCellValue("2019年");
            yearHead.createCell(4).setCellValue("2020年");
            yearHead.createCell(5).setCellValue("2021年");
            yearHead.createCell(6).setCellValue("2022年");
            yearHead.createCell(7).setCellValue("2023年");
            yearHead.createCell(8);
            yearHead.createCell(9);
            yearHead.createCell(10);
            yearHead.createCell(11);
            yearHead.createCell(12);
            yearHead.createCell(13);
            
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 2, 2));
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 7));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 8, 8));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 9, 9));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 10, 10));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 11, 11));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 12, 12));
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 13, 13));
            
            
            CellStyle border1 = wb.createCellStyle();
            // 边框
            border1.setBorderBottom(CellStyle.BORDER_THIN);
            border1.setBorderLeft(CellStyle.BORDER_THIN);
            border1.setBorderRight(CellStyle.BORDER_THIN);
            border1.setBorderTop(CellStyle.BORDER_THIN);
            // 边框颜色
            border1.setBottomBorderColor(HSSFColor.BLACK.index);
            border1.setRightBorderColor(HSSFColor.BLACK.index);
            border1.setLeftBorderColor(HSSFColor.BLACK.index);
            border1.setTopBorderColor(HSSFColor.BLACK.index);
            //内容换行
            border1.setWrapText(true);
            border1.setAlignment(HorizontalAlignment.CENTER);// 水平居中
            border1.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

            title.getCell(0).setCellStyle(border1);
            title.getCell(1).setCellStyle(border1);
            title.getCell(2).setCellStyle(border1);
            title.getCell(3).setCellStyle(border1);
            title.getCell(4).setCellStyle(border1);
            title.getCell(5).setCellStyle(border1);
            title.getCell(6).setCellStyle(border1);
            title.getCell(7).setCellStyle(border1);
            title.getCell(8).setCellStyle(border1);
            title.getCell(9).setCellStyle(border1);
            title.getCell(10).setCellStyle(border1);
            title.getCell(11).setCellStyle(border1);
            title.getCell(12).setCellStyle(border1);
            title.getCell(13).setCellStyle(border1);
            yearHead.getCell(0).setCellStyle(border1);
            yearHead.getCell(1).setCellStyle(border1);
            yearHead.getCell(2).setCellStyle(border1);
            yearHead.getCell(3).setCellStyle(border1);
            yearHead.getCell(4).setCellStyle(border1);
            yearHead.getCell(5).setCellStyle(border1);
            yearHead.getCell(6).setCellStyle(border1);
            yearHead.getCell(7).setCellStyle(border1);
            yearHead.getCell(8).setCellStyle(border1);
            yearHead.getCell(9).setCellStyle(border1);
            yearHead.getCell(10).setCellStyle(border1);
            yearHead.getCell(11).setCellStyle(border1);
            yearHead.getCell(12).setCellStyle(border1);
            yearHead.getCell(13).setCellStyle(border1);

            int k = 2;
            DataFormat format = wb.createDataFormat();
            CellStyle numCellStyle = wb.createCellStyle();
            numCellStyle.setDataFormat(format.getFormat("0.00"));// 设置单元类型
            numCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            numCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            numCellStyle.setBorderRight(CellStyle.BORDER_THIN);
            numCellStyle.setBorderTop(CellStyle.BORDER_THIN);
            numCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setTopBorderColor(HSSFColor.BLACK.index);

            CommonFilter f = new CommonFilter().addExact("project.id", projectId);
    		List<OrderByDto> orders = new ArrayList<>();
    		OrderByDto order1 = new OrderByDto();
    		order1.setColumnName("innerCode");
    		orders.add(order1);
    /*		PageCtrlDto page1 = new PageCtrlDto();
    		page1.setPageSize(30);
    		page1.setCurrentPage(1);*/
        	List<BudgetEstimateSum> budgetSumList = budgetEstimateSumService.list(f, orders);
        	
    		// 专业组：对应到t_virtual_org的org_order
    		CommonFilter f1 = new CommonFilter();
    		if (!StringUtils.isEmpty(projectId)) {
    			f1 = new CommonFilter().addExact("project.id", projectId);
    		}
    		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f1, null);
    		
    		BudgetEstimateSumBeanConverter converter = new BudgetEstimateSumBeanConverter();
        	
        	List<BudgetEstimateBean> budgetList = new ArrayList();
        	
			List<OrderByDto> vorders1 = new ArrayList<>();
			OrderByDto vorder1 = new OrderByDto();
			vorder1.setColumnName("cDatetime");
			vorder1.setDesc(true);
			vorders1.add(vorder1);
        	
	    	CommonFilter vf1 = new CommonFilter().addExact("year", 2019).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
	    	List<BudgetEstimateVersion> versionList1 = budgetEstimateVersionService.list(vf1, vorders1);
	    	CommonFilter vf2 = new CommonFilter().addExact("year", 2020).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
	    	List<BudgetEstimateVersion> versionList2 = budgetEstimateVersionService.list(vf2, vorders1);
	    	CommonFilter vf3 = new CommonFilter().addExact("year", 2021).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
	    	List<BudgetEstimateVersion> versionList3 = budgetEstimateVersionService.list(vf3, vorders1);
	    	CommonFilter vf4 = new CommonFilter().addExact("year", 2022).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
	    	List<BudgetEstimateVersion> versionList4 = budgetEstimateVersionService.list(vf4, vorders1);
	    	CommonFilter vf5 = new CommonFilter().addExact("year", 2023).addExact("name", "1").addExact("budgetMain.flowStatus", 2);
	    	List<BudgetEstimateVersion> versionList5 = budgetEstimateVersionService.list(vf5, vorders1);
        	
        	for(int i=0;i<budgetSumList.size();i++) {
        		if(budgetSumList.get(i).getLevel()==1) {//一级分类
        			for(int j=0;j<budgetSumList.size();j++) {
        				if(budgetSumList.get(j).getLevel()==2 && budgetSumList.get(j).getInnerCode().equals(budgetSumList.get(i).getCode())) {//二级分类
        					for(int m=0;m<budgetSumList.size();m++) {
        	    				if(budgetSumList.get(m).getLevel()==3 && budgetSumList.get(m).getInnerCode().equals(budgetSumList.get(j).getCode())) {//三级分类
        	    					
        	    					BudgetEstimateBean bean = new BudgetEstimateBean();
        	    					
        			    			bean.setOneLevelClassification(budgetSumList.get(i).getName());
        			    			bean.setTwoLevelClassification(budgetSumList.get(j).getName());
        			    			bean.setThreeLevelClassification(budgetSumList.get(m).getName()); 
        			    			
        	    					//2019资金计划
        	    			    	if(versionList1.size()>0) {
        	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList1.get(0).getId()).addExact("code", budgetSumList.get(m).getCode());
        	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
        	    			    		if(budget1.size()>0) {
        	    			    			bean.setCapital2019(budget1.get(0).getPayAmountTotal());
        	    			    			bean.setRemark2019(budget1.get(0).getPlanRemark());
        	    			    		}
        	    			    			
        	    			    	}
        	    					
        	    					//2020资金计划
        	    			    	if(versionList2.size()>0) {
        	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList2.get(0).getId()).addExact("code", budgetSumList.get(m).getCode());
        	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
        	    			    		if(budget1.size()>0) {   
        	    			    			bean.setCapital2020(budget1.get(0).getPayAmountTotal());
        	    			    			bean.setRemark2020(budget1.get(0).getPlanRemark());
        	    			    		}
        	    			    			
        	    			    	}
        	    					
        	    					//2021资金计划
        	    			    	if(versionList3.size()>0) {
        	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList3.get(0).getId()).addExact("code", budgetSumList.get(m).getCode());
        	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
        	    			    		if(budget1.size()>0) {   
        	    			    			bean.setCapital2021(budget1.get(0).getPayAmountTotal());
        	    			    			bean.setRemark2021(budget1.get(0).getPlanRemark());
        	    			    		}
        	    			    			
        	    			    	}
        	    					
        	    					//2022资金计划
        	    			    	if(versionList4.size()>0) {
        	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList4.get(0).getId()).addExact("code", budgetSumList.get(m).getCode());
        	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
        	    			    		if(budget1.size()>0) { 
        	    			    			bean.setCapital2022(budget1.get(0).getPayAmountTotal());
        	    			    			bean.setRemark2022(budget1.get(0).getPlanRemark());
        	    			    		}
        	    			    			
        	    			    	}
        	    					
        	    					//2023资金计划
        	    			    	if(versionList5.size()>0) {
        	    			    		CommonFilter bf1 = new CommonFilter().addExact("version", versionList5.get(0).getId()).addExact("code", budgetSumList.get(m).getCode());
        	    			    		List<BudgetEstimate> budget1 = budgetEstimateService.list(bf1, null);
        	    			    		if(budget1.size()>0) { 
        	    			    			bean.setCapital2023(budget1.get(0).getPayAmountTotal());
        	    			    			bean.setRemark2023(budget1.get(0).getPlanRemark());
        	    			    		}
        	    			    			
        	    			    	}
        	    			    	
        	    			    	for(int n=0;n<virtualOrgList.size();n++) {
        	    			    		List<BudgetEstimateSumBean> budgetEstimateSumBeanList = new ArrayList<BudgetEstimateSumBean>();
        	    			    		budgetEstimateSumBeanList.add(converter.fromEntity(budgetSumList.get(i), this.messageSource, locale));
        	    			    		if(budgetEstimateSumBeanList.get(0).getGroup_()==virtualOrgList.get(n).getId()) {
        	    			    			bean.setCapitalGroup(virtualOrgList.get(n).getVirtualOrgName());;
        	    			    		}
        	    			    	}
        	    					
        	    			    	budgetList.add(bean);
        	    				}
        					}
        				}
        			}
        		}
        	}
            
            
            
            for(int j=0;j<budgetList.size();j++) {
            	
                Row dataRow = sheet.createRow(j+3);
                dataRow.createCell(0).setCellStyle(border);
                dataRow.createCell(1).setCellStyle(border);
                dataRow.createCell(2).setCellStyle(border);
                dataRow.createCell(3).setCellStyle(border);
                dataRow.createCell(4).setCellStyle(border);
                dataRow.createCell(5).setCellStyle(border);
                dataRow.createCell(6).setCellStyle(border);
                dataRow.createCell(7).setCellStyle(border);
                dataRow.createCell(8).setCellStyle(border);
                dataRow.createCell(9).setCellStyle(border);
                dataRow.createCell(10).setCellStyle(border);
                dataRow.createCell(11).setCellStyle(border);
                dataRow.createCell(12).setCellStyle(border);
                dataRow.createCell(13).setCellStyle(border);
            	
            	dataRow.getCell(0).setCellValue(budgetList.get(j).getOneLevelClassification());
            	dataRow.getCell(1).setCellValue(budgetList.get(j).getTwoLevelClassification());
            	dataRow.getCell(2).setCellValue(budgetList.get(j).getThreeLevelClassification());
            	if(budgetList.get(j).getCapital2019()!=null) {
            		dataRow.getCell(3).setCellValue(budgetList.get(j).getCapital2019());
            	}
            	if(budgetList.get(j).getCapital2020()!=null) {
            		dataRow.getCell(4).setCellValue(budgetList.get(j).getCapital2020());
            	}
            	if(budgetList.get(j).getCapital2021()!=null) {
            		dataRow.getCell(5).setCellValue(budgetList.get(j).getCapital2021());
            	}
            	if(budgetList.get(j).getCapital2022()!=null) {
            		dataRow.getCell(6).setCellValue(budgetList.get(j).getCapital2022());
            	}
            	if(budgetList.get(j).getCapital2023()!=null) {
            		dataRow.getCell(7).setCellValue(budgetList.get(j).getCapital2023());
            	}
            	if(budgetList.get(j).getRemark2019()!=null) {
            		dataRow.getCell(8).setCellValue(budgetList.get(j).getRemark2019());
            	}
            	if(budgetList.get(j).getRemark2020()!=null) {
            		dataRow.getCell(9).setCellValue(budgetList.get(j).getRemark2020());
            	}
            	if(budgetList.get(j).getRemark2021()!=null) {
            		dataRow.getCell(10).setCellValue(budgetList.get(j).getRemark2021());
            	}
            	if(budgetList.get(j).getRemark2022()!=null) {
            		dataRow.getCell(11).setCellValue(budgetList.get(j).getRemark2022());
            	}
            	if(budgetList.get(j).getRemark2023()!=null) {
            		dataRow.getCell(12).setCellValue(budgetList.get(j).getRemark2023());
            	}
            	
            	dataRow.getCell(13).setCellValue(budgetList.get(j).getCapitalGroup());
            }
            
            
			Date now = new Date();

            String fileName = "2019-2023年CENI项目资金计划构成表"
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (output != null) {
                output.close();
            }
        }

        return file;
    }

}
