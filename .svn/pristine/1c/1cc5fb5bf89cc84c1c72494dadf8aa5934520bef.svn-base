package project.edge.web.controller.budget;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
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
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.BudgetEstimateBeanConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.VirtualOrg;
//import project.edge.domain.entity.Site;
import project.edge.domain.view.BudgetEstimateBean;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.budget.BudgetFeeService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridController;

/**
 * 预算规划管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/capitalPlanSum")
public class CapitalPlanSumController
        extends TreeGridController<BudgetEstimate, BudgetEstimateBean> {

    private static final Logger logger = LoggerFactory.getLogger(BudgetEstimateController.class);

    private static final String PID = "P10016";

    //private static final String ID_MAP_KEY_BUDGET_DIALOG = "edit-budget-form-dialog";
    
    @Resource
    private BudgetEstimateService budgetEstimateService;
    
    @Resource
    private BudgetEstimateVersionService budgetEstimateVersionService;

    @Resource
    private PlanService planService;
    
    @Resource
    private PlanTaskService planTaskService;
    
    @Resource
    private DataOptionService dataOptionService;
    
    @Resource
    private BudgetFeeService budgetFeeService;
    
    @Resource
    private VirtualOrgService virtualOrgService;

    @Override
    protected Logger getLogger() {
        return logger;
    }
    

    @Override
    public String getDataType() {
        return DataTypeEnum.BUDGET_CAPITALPLANSUM.value();
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
        return "/WEB-INF/jsp/budget/capitalPlanSumJs.jsp";
    }
    
    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 导出文件
        jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
                String.format("CAPITALPLANSUM.exportExcelFile('#%1$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
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

        return super.main(paramMap, model, userBean, locale);
    }

    /**
     * 获取一览显示的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param model model
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
            Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "code") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) throws JsonParseException, JsonMappingException, IOException {
    	
    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
    	String projectId = null; 
    	if(commonFilterJson.length() != 0) {
        	for(int i=0;i<filter.getFilterFieldList().size();i++) {
        		if(filter.getFilterFieldList().get(i).getFieldName().equals("project.id")) {
        			projectId = filter.getFilterFieldList().get(i).getValue().toString();
        		}
        	}
    	}
        
        List<BudgetEstimateBean> beans = new ArrayList<>();
        String[][] str = new String[4][11];
        
        List<OrderByDto> orders = new ArrayList<>();
        OrderByDto order1 = new OrderByDto();
        order1.setColumnName("cDatetime");
        order1.setDesc(true);
        orders.add(order1);
        
        /*2019*/
        
        //获取2019年最新的总计划
        CommonFilter pf1 = new CommonFilter().addExact("planYear", "2019").addExact("project.id", projectId);
        List<Plan> planList1 = this.planService.list(pf1, orders);
        //获取2019年最新总计划里面的任务
        if(planList1.size()>0) {
        	CommonFilter tf1 = new CommonFilter().addExact("plan.id", planList1.get(0).getId()).addExact("taskLayer", 1);
            List<PlanTask> taskList1 = this.planTaskService.list(tf1, orders);
            //获取每个任务下的预算金额
            for(int i=0;i<taskList1.size();i++) {
            	String groupKey = taskList1.get(i).getTaskName();
            	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                        List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2019",projectId,virtualList.get(0).getId());
                        str[0][1] = groupKey;
                        if(budgetEstimate1.size()>0) {
                        	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate1) {
                        		total = total + budget.getPayAmountTotal();
                        	}
                        	str[0][6] = total.toString();
                        }
                    }
                }
                else if(groupKey.contains("可编程")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                        List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2019",projectId,virtualList.get(0).getId());
                        str[1][1] = groupKey;
                        if(budgetEstimate3.size()>0) {
                        	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate3) {
                        		total = total + budget.getPayAmountTotal();
                        	}
                        	str[1][6] = total.toString();
                        }
                    }
                }
                else if(groupKey.contains("SDN")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2019",projectId,virtualList.get(0).getId());
                    	str[2][1] = groupKey;
	                    if(budgetEstimate4.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate4) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[2][6] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("大数据")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2019",projectId,virtualList.get(0).getId());
	                    str[3][1] = groupKey;
	                    if(budgetEstimate5.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate5) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[3][6] = total.toString();
	                    }
                    }
                }
            }
        }
        
        
        
        /*2020*/
        
        //获取2020年最新的总计划
        CommonFilter pf2 = new CommonFilter().addExact("planYear", "2020").addExact("project.id", projectId);
        List<Plan> planList2 = this.planService.list(pf2, orders);
        //获取2020年最新总计划里面的任务
        if(planList2.size()>0) {
        	CommonFilter tf2 = new CommonFilter().addExact("plan.id", planList2.get(0).getId()).addExact("taskLayer", 1);
            List<PlanTask> taskList2 = this.planTaskService.list(tf2, orders);
            //获取每个任务下的预算金额
            for(int i=0;i<taskList2.size();i++) {
            	String groupKey = taskList2.get(i).getTaskName();
            	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2020",projectId,virtualList.get(0).getId());
	                    str[0][2] = groupKey;
	                    if(budgetEstimate1.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate1) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[0][7] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("可编程")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2020",projectId,virtualList.get(0).getId());
	                    str[1][2] = groupKey;
	                    if(budgetEstimate3.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate3) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[1][7] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("SDN")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2020",projectId,virtualList.get(0).getId());
	                    str[2][2] = groupKey;
	                    if(budgetEstimate4.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate4) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[2][7] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("大数据")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2020",projectId,virtualList.get(0).getId());
	                    str[3][2] = groupKey;
	                    if(budgetEstimate5.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate5) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[3][7] = total.toString();
	                    }
                    }
                }
            }
        }
        
        
        
        /*2021*/
        
        //获取2021年最新的总计划
        CommonFilter pf3 = new CommonFilter().addExact("planYear", "2021").addExact("project.id", projectId);
        List<Plan> planList3 = this.planService.list(pf3, orders);
        //获取2021年最新总计划里面的任务
        if(planList3.size()>0) {
        	CommonFilter tf3 = new CommonFilter().addExact("plan.id", planList3.get(0).getId()).addExact("taskLayer", 1);
            List<PlanTask> taskList3 = this.planTaskService.list(tf3, orders);
            //获取每个任务下的预算金额
            for(int i=0;i<taskList3.size();i++) {
            	String groupKey = taskList3.get(i).getTaskName();
            	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2021",projectId,virtualList.get(0).getId());
	                    str[0][3] = groupKey;
	                    if(budgetEstimate1.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate1) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[0][8] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("可编程")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2021",projectId,virtualList.get(0).getId());
	                    str[1][3] = groupKey;
	                    if(budgetEstimate3.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate3) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[1][8] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("SDN")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2021",projectId,virtualList.get(0).getId());
	                    str[2][3] = groupKey;
	                    if(budgetEstimate4.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate4) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[2][8] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("大数据")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2021",projectId,virtualList.get(0).getId());
	                    str[3][3] = groupKey;
	                    if(budgetEstimate5.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate5) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[3][8] = total.toString();
	                    }
                    }
                }
            }
        }
        
        
        
        /*2022*/
        
        //获取2022年最新的总计划
        CommonFilter pf4 = new CommonFilter().addExact("planYear", "2022").addExact("project.id", projectId);
        List<Plan> planList4 = this.planService.list(pf4, orders);
        //获取2022年最新总计划里面的任务
        if(planList4.size()>0) {
        	CommonFilter tf4 = new CommonFilter().addExact("plan.id", planList4.get(0).getId()).addExact("taskLayer", 1);
            List<PlanTask> taskList4 = this.planTaskService.list(tf4, orders);
            //获取每个任务下的预算金额
            for(int i=0;i<taskList4.size();i++) {
            	String groupKey = taskList4.get(i).getTaskName();
            	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2022",projectId,virtualList.get(0).getId());
	                    str[0][4] = groupKey;
	                    if(budgetEstimate1.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate1) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[0][9] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("可编程")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2022",projectId,virtualList.get(0).getId());
	                    str[1][4] = groupKey;
	                    if(budgetEstimate3.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate3) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[1][9] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("SDN")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2022",projectId,virtualList.get(0).getId());
	                    str[2][4] = groupKey;
	                    if(budgetEstimate4.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate4) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[2][9] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("大数据")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2022",projectId,virtualList.get(0).getId());
	                    str[3][4] = groupKey;
	                    if(budgetEstimate5.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate5) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[3][9] = total.toString();
	                    }
                    }
                }
            }
        }
        
        
        
        
        /*2023*/
        
        //获取2023年最新的总计划
        CommonFilter pf5 = new CommonFilter().addExact("planYear", "2023").addExact("project.id", projectId);
        List<Plan> planList5 = this.planService.list(pf5, orders);
        //获取2023年最新总计划里面的任务
        if(planList5.size()>0) {
        	CommonFilter tf5 = new CommonFilter().addExact("plan.id", planList5.get(0).getId()).addExact("taskLayer", 1);
            List<PlanTask> taskList5 = this.planTaskService.list(tf5, orders);
            //获取每个任务下的预算金额
            for(int i=0;i<taskList5.size();i++) {
            	String groupKey = taskList5.get(i).getTaskName();
            	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2023",projectId,virtualList.get(0).getId());
	                    str[0][5] = groupKey;
	                    if(budgetEstimate1.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate1) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[0][10] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("可编程")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2023",projectId,virtualList.get(0).getId());
	                    str[1][5] = groupKey;
	                    if(budgetEstimate3.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate3) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[1][10] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("SDN")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2023",projectId,virtualList.get(0).getId());
	                    str[2][5] = groupKey;
	                    if(budgetEstimate4.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate4) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[2][10] = total.toString();
	                    }
                    }
                }
                else if(groupKey.contains("大数据")) {
                	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                    List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                    if(virtualList.size()>0) {
                    	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2023",projectId,virtualList.get(0).getId());
	                    str[3][5] = groupKey;
	                    if(budgetEstimate5.size()>0) {
	                    	Double total = new Double(0);
                        	for(BudgetEstimate budget: budgetEstimate5) {
                        		total = total + budget.getPayAmountTotal();
                        	}
	                    	str[3][10] = total.toString();
	                    }
                    }
                }
            }
        }
        
        for(int j=0;j<4;j++) {
        	BudgetEstimateBean bean = new BudgetEstimateBean();
        	if(j==0) {
        		bean.setCapitalGroup("基础网络");
        	}
        	else if(j==1) {
        		bean.setCapitalGroup("可编程网络");
        	}
        	else if(j==2) {
        		bean.setCapitalGroup("SDN网络");
        	}
        	else if(j==3) {
        		bean.setCapitalGroup("大数据");
        	}
        	if(str[j][6]!=null) {
        		bean.setBudget2019(new BigDecimal(str[j][6]));
        	}
        	if(str[j][7]!=null) {
        		bean.setBudget2020(new BigDecimal(str[j][7]));
        	}
        	if(str[j][8]!=null) {
        		bean.setBudget2021(new BigDecimal(str[j][8]));
        	}
        	if(str[j][9]!=null) {
        		bean.setBudget2022(new BigDecimal(str[j][9]));
        	}
        	if(str[j][10]!=null) {
        		bean.setBudget2023(new BigDecimal(str[j][10]));
        	}
        	if(str[j][1]!=null) {
        		bean.setTask2019(str[j][1]);
        	}
        	if(str[j][2]!=null) {
        		bean.setTask2020(str[j][2]);
        	}
        	if(str[j][3]!=null) {
        		bean.setTask2021(str[j][3]);
        	}
        	if(str[j][4]!=null) {
        		bean.setTask2022(str[j][4]);
        	}
        	if(str[j][5]!=null) {
        		bean.setTask2023(str[j][5]);
        	}
        	beans.add(bean);
        }
        
        // 准备JSON结果
        jsonResult.setStatus(JsonStatus.OK);
        jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, beans);
        jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, 4);
    	
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

            File downloadFile =
                    this.generateBudgetExcelFile(projectId);

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

    private File generateBudgetExcelFile(String projectId) throws IOException {
        FileOutputStream output = null;
        File file = null;
        try {

            @SuppressWarnings("resource")
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("2019-2023年资金计划总表");

            sheet.setColumnWidth(0, 7 * 256);
            sheet.setColumnWidth(1, 20 * 256);
            sheet.setColumnWidth(2, 35 * 256);
            sheet.setColumnWidth(3, 35 * 256);
            sheet.setColumnWidth(4, 35 * 256);
            sheet.setColumnWidth(5, 35 * 256);
            sheet.setColumnWidth(6, 35 * 256);
            sheet.setColumnWidth(7, 20 * 256);
            sheet.setColumnWidth(8, 20 * 256);
            sheet.setColumnWidth(9, 20 * 256);
            sheet.setColumnWidth(10, 20 * 256);
            sheet.setColumnWidth(11, 20 * 256);

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
            headCell.setCellValue("2019-2023年资金计划总表");
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
            title.createCell(0).setCellValue("序号");
            title.createCell(1).setCellValue("专业组");
            title.createCell(2).setCellValue("2019年建设目标");
            title.createCell(3).setCellValue("2020年建设目标");
            title.createCell(4).setCellValue("2021年建设目标");
            title.createCell(5).setCellValue("2022年建设目标");
            title.createCell(6).setCellValue("2023年建设目标");
            title.createCell(7).setCellValue("2019年资金计划(万元)");
            title.createCell(8).setCellValue("2020年资金计划(万元)");
            title.createCell(9).setCellValue("2021年资金计划(万元)");
            title.createCell(10).setCellValue("2022年资金计划(万元)");
            title.createCell(11).setCellValue("2023年资金计划(万元)");

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

            String[][] str = new String[4][11];
            
            List<OrderByDto> orders = new ArrayList<>();
            OrderByDto order1 = new OrderByDto();
            order1.setColumnName("cDatetime");
            order1.setDesc(true);
            orders.add(order1);
            
            /*2019*/
            
            //获取2019年最新的总计划
            CommonFilter pf1 = new CommonFilter().addExact("planYear", "2019").addExact("project.id", projectId);
            List<Plan> planList1 = this.planService.list(pf1, orders);
            //获取2019年最新总计划里面的任务
            if(planList1.size()>0) {
            	CommonFilter tf1 = new CommonFilter().addExact("plan.id", planList1.get(0).getId()).addExact("taskLayer", 1);
                List<PlanTask> taskList1 = this.planTaskService.list(tf1, orders);
                //获取每个任务下的预算金额
                for(int i=0;i<taskList1.size();i++) {
                	String groupKey = taskList1.get(i).getTaskName();
                	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                            List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2019",projectId,virtualList.get(0).getId());
                            str[0][1] = groupKey;
                            if(budgetEstimate1.size()>0) {
                            	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate1) {
                            		total = total + budget.getPayAmountTotal();
                            	}
                            	str[0][6] = total.toString();
                            }
                        }
                    }
                    else if(groupKey.contains("可编程")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                            List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2019",projectId,virtualList.get(0).getId());
                            str[1][1] = groupKey;
                            if(budgetEstimate3.size()>0) {
                            	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate3) {
                            		total = total + budget.getPayAmountTotal();
                            	}
                            	str[1][6] = total.toString();
                            }
                        }
                    }
                    else if(groupKey.contains("SDN")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2019",projectId,virtualList.get(0).getId());
                        	str[2][1] = groupKey;
    	                    if(budgetEstimate4.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate4) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[2][6] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("大数据")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2019",projectId,virtualList.get(0).getId());
    	                    str[3][1] = groupKey;
    	                    if(budgetEstimate5.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate5) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[3][6] = total.toString();
    	                    }
                        }
                    }
                }
            }
            
            
            
            /*2020*/
            
            //获取2020年最新的总计划
            CommonFilter pf2 = new CommonFilter().addExact("planYear", "2020").addExact("project.id", projectId);
            List<Plan> planList2 = this.planService.list(pf2, orders);
            //获取2020年最新总计划里面的任务
            if(planList2.size()>0) {
            	CommonFilter tf2 = new CommonFilter().addExact("plan.id", planList2.get(0).getId()).addExact("taskLayer", 1);
                List<PlanTask> taskList2 = this.planTaskService.list(tf2, orders);
                //获取每个任务下的预算金额
                for(int i=0;i<taskList2.size();i++) {
                	String groupKey = taskList2.get(i).getTaskName();
                	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2020",projectId,virtualList.get(0).getId());
    	                    str[0][2] = groupKey;
    	                    if(budgetEstimate1.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate1) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[0][7] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("可编程")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2020",projectId,virtualList.get(0).getId());
    	                    str[1][2] = groupKey;
    	                    if(budgetEstimate3.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate3) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[1][7] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("SDN")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2020",projectId,virtualList.get(0).getId());
    	                    str[2][2] = groupKey;
    	                    if(budgetEstimate4.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate4) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[2][7] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("大数据")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2020",projectId,virtualList.get(0).getId());
    	                    str[3][2] = groupKey;
    	                    if(budgetEstimate5.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate5) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[3][7] = total.toString();
    	                    }
                        }
                    }
                }
            }
            
            
            
            /*2021*/
            
            //获取2021年最新的总计划
            CommonFilter pf3 = new CommonFilter().addExact("planYear", "2021").addExact("project.id", projectId);
            List<Plan> planList3 = this.planService.list(pf3, orders);
            //获取2021年最新总计划里面的任务
            if(planList3.size()>0) {
            	CommonFilter tf3 = new CommonFilter().addExact("plan.id", planList3.get(0).getId()).addExact("taskLayer", 1);
                List<PlanTask> taskList3 = this.planTaskService.list(tf3, orders);
                //获取每个任务下的预算金额
                for(int i=0;i<taskList3.size();i++) {
                	String groupKey = taskList3.get(i).getTaskName();
                	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2021",projectId,virtualList.get(0).getId());
    	                    str[0][3] = groupKey;
    	                    if(budgetEstimate1.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate1) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[0][8] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("可编程")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2021",projectId,virtualList.get(0).getId());
    	                    str[1][3] = groupKey;
    	                    if(budgetEstimate3.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate3) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[1][8] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("SDN")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2021",projectId,virtualList.get(0).getId());
    	                    str[2][3] = groupKey;
    	                    if(budgetEstimate4.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate4) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[2][8] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("大数据")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2021",projectId,virtualList.get(0).getId());
    	                    str[3][3] = groupKey;
    	                    if(budgetEstimate5.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate5) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[3][8] = total.toString();
    	                    }
                        }
                    }
                }
            }
            
            
            
            /*2022*/
            
            //获取2022年最新的总计划
            CommonFilter pf4 = new CommonFilter().addExact("planYear", "2022").addExact("project.id", projectId);
            List<Plan> planList4 = this.planService.list(pf4, orders);
            //获取2022年最新总计划里面的任务
            if(planList4.size()>0) {
            	CommonFilter tf4 = new CommonFilter().addExact("plan.id", planList4.get(0).getId()).addExact("taskLayer", 1);
                List<PlanTask> taskList4 = this.planTaskService.list(tf4, orders);
                //获取每个任务下的预算金额
                for(int i=0;i<taskList4.size();i++) {
                	String groupKey = taskList4.get(i).getTaskName();
                	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2022",projectId,virtualList.get(0).getId());
    	                    str[0][4] = groupKey;
    	                    if(budgetEstimate1.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate1) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[0][9] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("可编程")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2022",projectId,virtualList.get(0).getId());
    	                    str[1][4] = groupKey;
    	                    if(budgetEstimate3.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate3) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[1][9] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("SDN")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2022",projectId,virtualList.get(0).getId());
    	                    str[2][4] = groupKey;
    	                    if(budgetEstimate4.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate4) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[2][9] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("大数据")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2022",projectId,virtualList.get(0).getId());
    	                    str[3][4] = groupKey;
    	                    if(budgetEstimate5.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate5) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[3][9] = total.toString();
    	                    }
                        }
                    }
                }
            }
            
            
            
            
            /*2023*/
            
            //获取2023年最新的总计划
            CommonFilter pf5 = new CommonFilter().addExact("planYear", "2023").addExact("project.id", projectId);
            List<Plan> planList5 = this.planService.list(pf5, orders);
            //获取2023年最新总计划里面的任务
            if(planList5.size()>0) {
            	CommonFilter tf5 = new CommonFilter().addExact("plan.id", planList5.get(0).getId()).addExact("taskLayer", 1);
                List<PlanTask> taskList5 = this.planTaskService.list(tf5, orders);
                //获取每个任务下的预算金额
                for(int i=0;i<taskList5.size();i++) {
                	String groupKey = taskList5.get(i).getTaskName();
                	if(groupKey.contains("基础网络")||groupKey.contains("光传输")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "基础网络");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getLastBudgetByGroup("2023",projectId,virtualList.get(0).getId());
    	                    str[0][5] = groupKey;
    	                    if(budgetEstimate1.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate1) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[0][10] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("可编程")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "可编程");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getLastBudgetByGroup("2023",projectId,virtualList.get(0).getId());
    	                    str[1][5] = groupKey;
    	                    if(budgetEstimate3.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate3) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[1][10] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("SDN")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "SDN");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getLastBudgetByGroup("2023",projectId,virtualList.get(0).getId());
    	                    str[2][5] = groupKey;
    	                    if(budgetEstimate4.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate4) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[2][10] = total.toString();
    	                    }
                        }
                    }
                    else if(groupKey.contains("大数据")) {
                    	CommonFilter vf = new CommonFilter().addExact("project.id", projectId).addAnywhere("virtualOrgName", "大数据");
                        List<VirtualOrg> virtualList = this.virtualOrgService.list(vf, null);
                        if(virtualList.size()>0) {
                        	List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getLastBudgetByGroup("2023",projectId,virtualList.get(0).getId());
    	                    str[3][5] = groupKey;
    	                    if(budgetEstimate5.size()>0) {
    	                    	Double total = new Double(0);
                            	for(BudgetEstimate budget: budgetEstimate5) {
                            		total = total + budget.getPayAmountTotal();
                            	}
    	                    	str[3][10] = total.toString();
    	                    }
                        }
                    }
                }
            }
            
            
            
            for(int j=0;j<4;j++) {
            	
                Row row = sheet.createRow(j+2);
                row.createCell(0).setCellStyle(border);
                row.createCell(1).setCellStyle(border);
                row.createCell(2).setCellStyle(border);
                row.createCell(3).setCellStyle(border);
                row.createCell(4).setCellStyle(border);
                row.createCell(5).setCellStyle(border);
                row.createCell(6).setCellStyle(border);
                row.createCell(7).setCellStyle(border);
                row.createCell(8).setCellStyle(border);
                row.createCell(9).setCellStyle(border);
                row.createCell(10).setCellStyle(border);
                row.createCell(11).setCellStyle(border);
                row.getCell(0).setCellValue(j+1);
            	
            	if(j==0) {
            		row.getCell(1).setCellValue("基础网络");
            	}
            	else if(j==1) {
            		row.getCell(1).setCellValue("可编程网络");
            	}
            	else if(j==2) {
            		row.getCell(1).setCellValue("SDN网络");
            	}
            	else if(j==3) {
            		row.getCell(1).setCellValue("大数据");
            	}
            	
            	if(str[j][6]!=null) {
            		row.getCell(7).setCellValue(str[j][6]);
            	}
            	if(str[j][7]!=null) {
            		row.getCell(8).setCellValue(str[j][7]);
            	}
            	if(str[j][8]!=null) {
            		row.getCell(9).setCellValue(str[j][8]);
            	}
            	if(str[j][9]!=null) {
            		row.getCell(10).setCellValue(str[j][9]);
            	}
            	if(str[j][10]!=null) {
            		row.getCell(11).setCellValue(str[j][10]);
            	}
            	if(str[j][1]!=null) {
            		row.getCell(2).setCellValue(str[j][1]);
            	}
            	if(str[j][2]!=null) {
            		row.getCell(3).setCellValue(str[j][2]);
            	}
            	if(str[j][3]!=null) {
            		row.getCell(4).setCellValue(str[j][3]);
            	}
            	if(str[j][4]!=null) {
            		row.getCell(5).setCellValue(str[j][4]);
            	}
            	if(str[j][5]!=null) {
            		row.getCell(6).setCellValue(str[j][5]);
            	}
            }
            
            
			Date now = new Date();

            String fileName = "2019-2023年CENI项目资金计划"
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
