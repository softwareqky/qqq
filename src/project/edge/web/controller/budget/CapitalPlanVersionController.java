package project.edge.web.controller.budget;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.CapitalPlanBeanConverter;
import project.edge.domain.entity.CapitalPlan;
import project.edge.domain.view.CapitalPlanBean;
import project.edge.service.budget.CapitalPlanService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;


/**
 * 预算费用报销记录
 *
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/capitalPlanVersion")
public class CapitalPlanVersionController extends TreeDoubleGridUploadController<CapitalPlan, CapitalPlanBean>{
 
    private static final Logger logger = LoggerFactory.getLogger(CapitalPlanVersionController.class);
    
    private static final String PID ="P10001";
    
    @Resource
    private CapitalPlanService capitalPlanService;
    
    
    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.CAPITAL_PLAN.value();
    }

    @Override
    protected Service<CapitalPlan, String> getDataService() {
        return this.capitalPlanService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<CapitalPlan, CapitalPlanBean> getViewConverter() {
        return new CapitalPlanBeanConverter();
    }
    
    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        // 版本一览的URL
        String contextPath = this.context.getContextPath();
        urlMap.put(ControllerUrlMapKeys.SUB_GRID_LIST,
                contextPath + "/budget/capitalVersion/list.json");
        
        urlMap.put(ControllerUrlMapKeys.LIST,
                contextPath + "/budget/capitalPlanVersion/list.json");
        
        urlMap.put(ControllerUrlMapKeys.FIND,
                contextPath + "/budget/capitalPlanVersion/find.json");
        
        return urlMap;
    }
    
    @Override
    public String getSubDataType() {
        // TODO Auto-generated method stub
        return DataTypeEnum.CAPITAL_PLAN_VERSION.value();
    }
    
    @Override
    public String getGridTitle(Locale locale) {
        // TODO Auto-generated method stub
        return this.messageSource.getMessage("ui.grid.title.budget_capitalplan", null, locale);
    }

    @Override
    public String getSubGridTitle(Locale locale) {
        // TODO Auto-generated method stub
        return this.messageSource.getMessage("ui.grid.title.budget_capitalplan.version", null, locale);
    }

    @Override
    public OrderByDto getSubGridDefaultOrder() {
        // TODO Auto-generated method stub
        return new OrderByDto("cDatetime", true);
    }

    @Override
    public String getLinkedFilterFieldName() {
        // TODO Auto-generated method stub
        return "versionId";
    }
    
    @Override
    public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        // TODO Auto-generated method stub
        List<FunctionBean> functions = new ArrayList<>();

        return functions;
    }
    
    @Override
    public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        // TODO Auto-generated method stub
        List<FunctionBean> searchFunctions = new ArrayList<>();

        return searchFunctions;
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
     */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        return super.list(commonFilterJson, null, page, rows, sort, order, locale);
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


}
