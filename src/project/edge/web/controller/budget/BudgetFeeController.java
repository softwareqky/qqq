package project.edge.web.controller.budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.BudgetFeeBeanConverter;
import project.edge.domain.entity.BudgetFee;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.view.BudgetFeeBean;
import project.edge.service.budget.BudgetFeeService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.DoubleGridUploadController;

/**
 * 预算费用报销记录
 *
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetfee")
public class BudgetFeeController extends DoubleGridUploadController<BudgetFee, BudgetFeeBean>{
 
    private static final Logger logger = LoggerFactory.getLogger(BudgetFeeController.class);
    
    private static final String PID ="P10011";
    
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
        return DataTypeEnum.BUDGET_FEE.value();
    }

    @Override
    protected Service<BudgetFee, String> getDataService() {
        return this.budgetFeeService;
    }
    
    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/budget/budgetFee.jsp";
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<BudgetFee, BudgetFeeBean> getViewConverter() {
        return new BudgetFeeBeanConverter();
    }


//	@Override
//	protected String getSubGridJsCallbackObjName() {
//		return this.getDataType();
//	}
	
	@Override
	public String getSubDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.BUDGET_MAINFEE.value();
	}

	@Override
	public String getGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.grid.title.budgetfee.detail", null, locale);
	}

	@Override
	public String getSubGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.grid.title.budgetfee", null, locale);
	}

	@Override
	public OrderByDto getSubGridDefaultOrder() {
		// TODO Auto-generated method stub
		return new OrderByDto("mainid", true);
	}

	@Override
	public String getLinkedFilterFieldName() {
		// TODO Auto-generated method stub
		return "mainfee_";
	}
	
	@Override
    protected boolean useFile() {
        return false;
    }
	
	@Override
    protected boolean subGridUseFile() {
        return false;
    }
	
	@Override
	protected String getJsCallbackObjName() {
		return this.getDataType();
	}
	
	@Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        CommonFilter f = null;
        
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
	@Override
	public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P10025";
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
	public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P10025";
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

    	// 覆盖默认的SubGrid选中事件处理，不再联动MainGrid
    	model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "BUDGET_FEE.handleSelect");
		model.addAttribute(ControllerModelKeys.SUB_GRID_SELECT_HANDLER, "BUDGET_FEE.handleSubGridSelect");
    			
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

    /**
     * 新建，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @SysLogAnnotation(description = "费用报销记录", action = "新增")
    public JsonResultBean create(@ModelAttribute BudgetFeeBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.create(bean, null, userBean, locale);
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
    @SysLogAnnotation(description = "费用报销记录", action = "更新")
    public JsonResultBean update(@ModelAttribute BudgetFeeBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.update(bean, null, userBean, locale);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @SysLogAnnotation(description = "费用报销记录", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
