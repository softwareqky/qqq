package project.edge.web.controller.budget;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
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
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.domain.converter.MainInfoBudgetFeeListBeanConvert;
import project.edge.domain.converter.MainInfoProjectInfoListBeanConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateMain;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.BudgetFee;
import project.edge.domain.entity.BudgetMainfee;
import project.edge.domain.entity.CapitalApply;
import project.edge.domain.entity.CapitalReceive;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.MainChartInfoBean;
import project.edge.domain.view.MainInfoBean;
import project.edge.domain.view.MainInfoProjectInfoListBean;
import project.edge.domain.view.MainInfoProjectSummarySubBean;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.budget.BudgetEstimateMainService;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateSumService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.budget.BudgetFeeService;
import project.edge.service.budget.BudgetMainfeeService;
import project.edge.service.budget.CapitalApplyService;
import project.edge.service.budget.CapitalPlanService;
import project.edge.service.budget.CapitalPlanSumService;
import project.edge.service.budget.CapitalPlanVersionService;
import project.edge.service.budget.CapitalReceiveService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;

/**
 * 滞后分析画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budget-statistics")
public class BudgetStatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(BudgetStatisticsController.class);

	private static final int LIST_PAGE = 1; // 获取首页
	private static final int LIST_ROW = 5; // 首页模块数据条数
	private String projectId;
    
    @Resource
    private BudgetEstimateService budgetEstimateService;
    
	@Resource
	private BudgetEstimateSumService budgetEstimateSumService;
	
    @Resource
    private CapitalReceiveService capitalReceiveService;
	
    @Resource
    private CapitalPlanService capitalPlanService;
	
	@Resource
	private CapitalPlanVersionService capitalPlanVersionService;
    
	@Resource
	private ProjectService projectService;
	
	@Resource
	private MessageSource messageSource;
	
	@Resource
	private BudgetFeeService budgetFeeService;
	
	@Resource
	private VirtualOrgService virtualOrgService;
	
    @Resource
    private CapitalPlanSumService capitalPlanSumService;
    
    @Resource
    private CapitalApplyService capitalApplyService;
    
	@Resource
	private PurchaseOrderService purchaseOrderService;
	
	@Resource
	private BudgetMainfeeService budgetMainfeeService;
	
    @Resource
    private BudgetEstimateVersionService budgetEstimateVersionService;
    
    @Resource
    private BudgetEstimateMainService budgetEstimateMainService;

	/**
	 * 打开主画面。
	 */
	@RequestMapping("/home")
	public String main(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Model model,
			HttpServletResponse response, Locale locale) {

		return "budget/budgetStatistics";
	}
	
	/**
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/main-info")
	@ResponseBody
	public JsonResultBean getMainInfo(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			MainInfoBean mainInfoBean = new MainInfoBean();

			// 设置分页信息
			PageCtrlDto pageCtrl = new PageCtrlDto();
			pageCtrl.setCurrentPage(LIST_PAGE);
			pageCtrl.setPageSize(LIST_ROW);

			String userid = userBean.getSessionUserId();

			MainInfoProjectSummarySubBean projectSummary = new MainInfoProjectSummarySubBean();

			// 项目列表
			List<Project> projectList = this.projectService.list(null, null, pageCtrl);
			List<MainInfoProjectInfoListBean> mainInfoProjectInfoList = new ArrayList<MainInfoProjectInfoListBean>();

			if ((projectList != null) && (projectList.size() > 0)) {

				projectId = projectList.get(0).getId();
				MainInfoProjectInfoListBeanConverter converter = new MainInfoProjectInfoListBeanConverter();

				for (Project p : projectList) {

					mainInfoProjectInfoList.add(converter.fromEntity(p, this.messageSource, locale));
				}

				mainInfoBean.setProjectInfo(mainInfoProjectInfoList);
			}

			mainInfoBean.setYears(DateUtils.yearToNow(2019));

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainInfoBean);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);

		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;

	}
    
	
	@RequestMapping("/main-chartInfo")
	@ResponseBody
	public JsonResultBean getMainChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 参数检查
			if (StringUtils.isEmpty(projectId)) {
				jsonResult.setStatus(JsonStatus.ERROR);
				return jsonResult;
			}

			MainChartInfoBean mainChartInfoBean = new MainChartInfoBean();

			// 设置分页信息
			PageCtrlDto pageCtrl = new PageCtrlDto();
			pageCtrl.setCurrentPage(LIST_PAGE);
			pageCtrl.setPageSize(LIST_ROW);

			// 过滤
			// CommonFilter budgetFeeFilter = new CommonFilter().addExact("p.id",
			// projectId);

			// 资金计划
			List<BudgetFee> budgetFeeList = this.budgetFeeService.list(null, null, pageCtrl);
			if ((budgetFeeList != null) && (budgetFeeList.size() > 0)) {
				MainInfoBudgetFeeListBeanConvert converter = new MainInfoBudgetFeeListBeanConvert();

				for (BudgetFee b : budgetFeeList) {
					mainChartInfoBean.getBudget().add(converter.fromEntity(b, this.messageSource, locale));
				}
			}

			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mainChartInfoBean);

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);

		} catch (Exception e) {
			logger.error("Exception main.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;

	}
    
	
	@RequestMapping("/fee-chartInfo")
	@ResponseBody
	public JsonResultBean getFeeChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@RequestParam(required = true, defaultValue = "") String year,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws ParseException {
		JsonResultBean jsonResult = new JsonResultBean();
		List<String> monthList = DateUtils.monthList(year);
		
        Map<String, List> map = new HashMap<String, List>();
        //月份
        ArrayList<String> xAxisList = new ArrayList<>();
        //使用金额
        ArrayList<BigDecimal> useList = new ArrayList<>();
        // 计划资金
        ArrayList<BigDecimal> planList = new ArrayList<>();
        
		// 加入按创建时间排序 start
		List<OrderByDto> feeOrderList = new ArrayList<>();
		OrderByDto feeOrder = new OrderByDto();
		feeOrder.setColumnName("cDatetime");
		feeOrder.setDesc(false);
		feeOrderList.add(feeOrder);
		CommonFilter ff = new CommonFilter().addExact("project.id", projectId);
		//采购
		List<PurchaseOrder> orderList = this.purchaseOrderService.list(ff, feeOrderList);
		
/*		// 资金
		List<BudgetFee> feeList = this.budgetFeeService.list(ff, feeOrderList);
		
		// 计划
 		CommonFilter vf = new CommonFilter().addExact("project.id", projectId);
 		List<CapitalPlanVersion> versionList = capitalPlanVersionService.list(vf, feeOrderList);
 		BigDecimal firstVersionCapital = new BigDecimal(0);
		
 		if(versionList.size()>0) {
 			//第一版资金合计
 			CommonFilter cf = new CommonFilter().addExact("versionId", versionList.get(0).getId()).addExact("year", Integer.parseInt(year)).addExact("project.id", projectId);;
 	    	List<CapitalPlan> capitalList = capitalPlanService.list(cf, null);
 	    	for (CapitalPlan capital : capitalList) {
 	    		firstVersionCapital.add(new BigDecimal(capital.getCapitalPlan()));
 	    	}
 		}*/
 		
 		
        for (String month : monthList) {
            xAxisList.add(month);
            
            BigDecimal feeSum = new BigDecimal(0);
			if ((orderList != null) && (orderList.size() > 0)) {
				for (PurchaseOrder order : orderList) {
					Calendar c = Calendar.getInstance();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
					Date d = dateFormat.parse(month);
					c.setTime(d);
					c.add(Calendar.MONTH, 1);
					
					if (order.getcDatetime().before(c.getTime()) && order.getcDatetime().after(d)) {
						CommonFilter bf = new CommonFilter().addExact("purchaseNo", order.getPurchaseOrderNo());
						//费用主表
						List<BudgetMainfee> mainList = this.budgetMainfeeService.list(bf, null);
						for (BudgetMainfee mainfee : mainList) {
							feeSum = feeSum.add(mainfee.getPayAmount()).divide(new BigDecimal(10000), 6, RoundingMode.HALF_UP);
						}
					}
				}
			}
			useList.add(feeSum);
            
/*            String versionId = "";
            BigDecimal planSum = new BigDecimal(0);
            if ((versionList != null) && (versionList.size() > 0)) {
            	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            	for (CapitalPlanVersion version : versionList) {
    		    	//赋初值，默认是第一个
            		planSum = firstVersionCapital;

            		String createTime = df.format(version.getcDatetime());
            		int res = createTime.compareTo(month);
            		if (res == 0 || res > 0) {
            			planSum = new BigDecimal(0);
            			CommonFilter f = new CommonFilter().addExact("versionId", version.getId()).addExact("year", year).addExact("project.id", projectId);
            	    	List<CapitalPlan> capitals = capitalPlanService.list(f, null);
            	    	for (CapitalPlan capital : capitals) {
            	    		planSum.add(new BigDecimal(capital.getCapitalPlan()));
            	    	}
            			continue;
            		}
            	}
            }
    		planList.add(planSum);*/
            
        }
        map.put("xAxisList", xAxisList);
        map.put("useList",useList);
        map.put("planList",planList);
        
		jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, map);
		jsonResult.setStatus(JsonStatus.OK);
        
        return jsonResult;
	}
	
	

	@RequestMapping("/group-chartInfo")
	@ResponseBody
	public JsonResultBean getGroupChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@RequestParam(required = true, defaultValue = "") String year,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		
        Map<String, List> map = new HashMap<String, List>();
        
        //专业组
        ArrayList<String> xAxisList = new ArrayList<>();
        //使用金额
        ArrayList<BigDecimal> useList = new ArrayList<>();
        // 计划资金
        ArrayList<BigDecimal> planList = new ArrayList<>();
        
		CommonFilter virtualOrgFilter = new CommonFilter().addExact("project.id", projectId);
		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(virtualOrgFilter, null);
        
		// 加入按创建时间排序 start
		List<OrderByDto> feeOrderList = new ArrayList<>();
		OrderByDto feeOrder = new OrderByDto();
		feeOrder.setColumnName("cDatetime");
		feeOrder.setDesc(true);
		feeOrderList.add(feeOrder);
		CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addExact("year", Integer.parseInt(year)).addExact("budgetMain.flowStatus", 2);
		//版本
		List<BudgetEstimateVersion> versionList = this.budgetEstimateVersionService.list(vf, feeOrderList);
			
		for (VirtualOrg org : virtualOrgList) {
            xAxisList.add(org.getVirtualOrgName());
            
            if(versionList.size() > 0) {
                BigDecimal useFee = new BigDecimal(0);
                BigDecimal planFee = new BigDecimal(0);
                
    			//预算
    			CommonFilter bf = new CommonFilter().addExact("version", versionList.get(0).getId()).addExact("group.id", org.getId());
    			List<BudgetEstimate> budgetList = this.budgetEstimateService.list(bf, null);
                
        		//将innercode全部取出用于后面的比较
        		List<String> innerCodeList = new ArrayList<String>();
        		for(BudgetEstimate budget: budgetList) {
        			innerCodeList.add(budget.getInnerCode());
        		}
        		
        		for(BudgetEstimate budget: budgetList) {
        			if(innerCodeList.contains(budget.getCode()) == false) {
        				if(budget.getPayAmountTotal() != null) {
        					useFee = useFee.add(new BigDecimal(budget.getPayAmountTotal())); 
        				}
        				if(budget.getTaxTotalPrice() != null) {
        					planFee = planFee.add(new BigDecimal(budget.getTaxTotalPrice())); 
        				}
        			}
        		}
    			
    			useList.add(useFee);
    			planList.add(planFee);
            }
            
        }
		
        map.put("xAxisList", xAxisList);
        map.put("useList",useList);
        map.put("planList",planList);
        
		jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, map);
		jsonResult.setStatus(JsonStatus.OK);
        
        return jsonResult;
	}
	
	
	@RequestMapping("/apply-chartInfo")
	@ResponseBody
	public JsonResultBean getApplyChartInfo(@RequestParam(required = true, defaultValue = "") String projectId,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		
        Map<String, List> map = new HashMap<String, List>();
        
        //时间
        ArrayList<String> xAxisList = new ArrayList<>();
        //申请金额
        ArrayList<BigDecimal> applyList = new ArrayList<>();
        //下达金额
        ArrayList<BigDecimal> provideList = new ArrayList<>();

        CommonFilter filter = new CommonFilter().addExact("project.id", projectId);
        
		// 申请
		List<OrderByDto> OrderList = new ArrayList<>();
		OrderByDto Order = new OrderByDto();
		Order.setColumnName("cDatetime");
		Order.setDesc(false);
		OrderList.add(Order);
		
 		List<CapitalApply> aList = capitalApplyService.list(filter, OrderList);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for(CapitalApply apply: aList) {
			CommonFilter f = new CommonFilter().addExact("capitalApply.id", apply.getId());
			List<CapitalReceive> rList = capitalReceiveService.list(f, null);
			BigDecimal applyFee = new BigDecimal(0);
 	    	for (CapitalReceive receive : rList) {
 	    		applyFee = applyFee.add(new BigDecimal(receive.getReceiveMoney()));
 	    	}
 	    	provideList.add(applyFee);
 	    	applyList.add(new BigDecimal(apply.getApplyAmount()));
 	    	xAxisList.add(sdf.format(apply.getcDatetime()));
		}
		
        map.put("xAxisList", xAxisList);
        map.put("applyList", applyList);
        map.put("provideList", provideList);
        
		jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, map);
		jsonResult.setStatus(JsonStatus.OK);
        
        return jsonResult;
	}
}
