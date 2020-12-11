package project.edge.web.controller.budget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.CapitalReceiveBeanConverter;
import project.edge.domain.entity.CapitalApply;
import project.edge.domain.entity.CapitalReceive;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.view.CapitalReceiveBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.budget.CapitalApplyAttachmentService;
import project.edge.service.budget.CapitalApplyService;
import project.edge.service.budget.CapitalReceiveService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;

/**
 * 资金下达管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/capitalReceive")
public class CapitalReceiveController
        extends TreeDoubleGridUploadController<CapitalReceive, CapitalReceiveBean> {

    private static final Logger logger = LoggerFactory.getLogger(CapitalReceiveController.class);

    private static final String PID = "P10018";
    
    private String VIEW_GRID_DIALOG_ID = "view-grid-dialog-id";
    private String VIEW_GRID_DIALOG_URL = "view-grid-dialog-url";
    
    @Autowired
	HttpServletRequest request;

    @Resource
    private CapitalReceiveService capitalReceiveService;
    
	@Resource
	private DataOptionService dataOptionService;
	
	@Resource
	private VirtualOrgService virtualOrgService;
	
    @Resource
    private CapitalApplyService capitalApplyService;
    
    @Resource
    private CapitalApplyAttachmentService capitalApplyAttachmentService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.CAPITAL_RECEIVE.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<CapitalReceive, String> getDataService() {
        return this.capitalReceiveService;
    }
    
	@Resource
	CreateWorkFlowManager createWorkFlowManager;

    @Override
    protected ViewConverter<CapitalReceive, CapitalReceiveBean> getViewConverter() {
        return new CapitalReceiveBeanConverter();
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
	public String getSubDataType() {
		return DataTypeEnum.CAPITAL_APPLY.value();
	}
	
	@Override
	public String getGridTitle(Locale locale) {
		return this.messageSource.getMessage("ui.menu.item.budget.capitalreceive", null, locale);
		// return null;
	}

	@Override
	public String getSubGridTitle(Locale locale) {
		return this.messageSource.getMessage("ui.menu.item.budget.capitalapply", null, locale);
	}
	
    @Override
    public OrderByDto getSubGridDefaultOrder() {
        // TODO Auto-generated method stub
        return new OrderByDto("cDatetime", true);
    }
	
    @Override
    public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        // TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P10023";
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
    public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        // TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P10023";
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
    
	@Override
	public String getLinkedFilterFieldName() {
		// 使用了linked-func，不再需要此值
		return "capitalApply_";
	}
	
	@Override
    protected boolean subGridUseFile() {
        return true;
    }

	
    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/budget/capitalReceiveJs.jsp";
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
		
		List<String> dataTypeList = new ArrayList<>();
		dataTypeList.add(DataTypeEnum.CAPITAL_SOURCE.value());

		CommonFilter f = null;
		f = new CommonFilter().addWithin("dataType", dataTypeList);
		
		// 来源类型
		List<ComboboxOptionBean> sourceOptions = new ArrayList<>();
		List<DataOption> list = this.dataOptionService.list(f, null);
		for (DataOption o : list) {
			if (o.getDataType().equals(DataTypeEnum.CAPITAL_SOURCE.value())) {
				sourceOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
			} 
		}
		
		//年份
		List<ComboboxOptionBean> yearOptions = new ArrayList<>();
		yearOptions.add(new ComboboxOptionBean("2019", "2019"));
		yearOptions.add(new ComboboxOptionBean("2020", "2020"));
		yearOptions.add(new ComboboxOptionBean("2021", "2021"));
		yearOptions.add(new ComboboxOptionBean("2022", "2022"));
		yearOptions.add(new ComboboxOptionBean("2023", "2023"));
		
		//专业组
		CommonFilter f1 = null;
		List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f1, null);
		if (virtualOrgList != null) {
			for (VirtualOrg v : virtualOrgList) {
				projectGroupOptions.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
			}
		}
		
		//资金申请
		CommonFilter f2 = new CommonFilter();
        List<ComboboxOptionBean> capitalApplyOptions = new ArrayList<>();
        List<CapitalApply> capitalApplysList = capitalApplyService.list(f2, null);
        if (capitalApplysList != null) {
            for (CapitalApply capitalApplys : capitalApplysList) {
            	capitalApplyOptions.add(new ComboboxOptionBean(capitalApplys.getId(),
            			capitalApplys.getApplyReason()));
            }
        }

		optionMap.put("groupOptions", projectGroupOptions);
		optionMap.put("yearOptions", yearOptions);
		optionMap.put("sourceOptions", sourceOptions);
		optionMap.put("applySourceOptions", sourceOptions);
		optionMap.put("capitalApplyOptions", capitalApplyOptions);
		return optionMap;
	}
	
	@Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(VIEW_GRID_DIALOG_ID, "P10018_ViewGridDialog");
        idMap.put(VIEW_GRID_DIALOG_URL, "/budget/capitalReceive/list.json");

        return idMap;
    }
	
    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
        
        // OA审批
        jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT,
                String.format(ControllerMapUtils.OPEN_SUBMIT_AUDIT_JS, "P10018-CAPITAL_APPLY-SubDatagrid",
                        urlMap.get(ControllerUrlMapKeys.AUDIT_SUBMIT)));

 		// 打开查看
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_VIEW,
				String.format("CAPITAL_RECEIVE.openViewDialog('#%1$s', '%2$s', '#%3$s', %4$s)", 
						"P10018-CAPITAL_APPLY-SubGridViewGridDialog",
						urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND), idMap.get(ControllerIdMapKeys.SUB_DATAGRID),
						null));
		
		// 新增保存，isFile强制设为false
		jsMap.put(ControllerJsMapKeys.ADD_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true);",
						idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						false));
		
		// 修改保存，isFile强制设为false
		jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT,
				String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true);",
						idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
						false));

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
     * @param folderNo 文件夹号
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
            @RequestParam(required = false, defaultValue = "asc") String order,
			@RequestParam(required = false, defaultValue = "") String id, Locale locale) {
    	
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
        JsonResultBean jsonResult = super.find(id, locale);
        
        // 获取申购列表
        List<ComboboxOptionBean> resultList2 = new ArrayList<>();
        List<CapitalApply> capitalApplysList = this.capitalApplyService.list(null, null);
        if (capitalApplysList != null) {
            for (CapitalApply entity : capitalApplysList) {
            	resultList2.add(new ComboboxOptionBean(entity.getId(),
                        entity.getApplyReason()));
            }
        }
        jsonResult.getDataMap().put("updatePruchaseNo", resultList2);

        
        return jsonResult;
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
    @SysLogAnnotation(description = "资金下达管理", action = "新增")
    public JsonResultBean create(@ModelAttribute CapitalReceiveBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	if (StringUtils.isNotEmpty(bean.getCapitalApply_())) {
    		CapitalApply capitalApply = capitalApplyService.find(bean.getCapitalApply_());
    		if (capitalApply != null) {
    			bean.setProject_(capitalApply.getProject().getId());
    		}
    	}
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
    @SysLogAnnotation(description = "资金下达管理", action = "更新")
    public JsonResultBean update(@ModelAttribute CapitalReceiveBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	if (StringUtils.isNotEmpty(bean.getCapitalApply_())) {
    		CapitalApply capitalApply = capitalApplyService.find(bean.getCapitalApply_());
    		if (capitalApply != null) {
    			bean.setProject_(capitalApply.getProject().getId());
    		}
    	}
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
    @SysLogAnnotation(description = "资金下达管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    

}
