package project.edge.web.controller.bidding;

import java.util.ArrayList;
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
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.PurchaseOrderDetailBeanConverter;
import project.edge.domain.entity.City;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Province;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.PurchaseOrderDetail;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PurchaseOrderDetailBean;
import project.edge.service.bidding.PurchaseOrderDetailService;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.CityService;
import project.edge.service.system.DataOptionService;
import project.edge.service.system.ProvinceService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;

/**
 * 采购订单明细画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/purchase-order-detail")
public class PurchaseOrderDetailController
        extends TreeDoubleGridUploadController<PurchaseOrderDetail, PurchaseOrderDetailBean> {

    private static final Logger logger =
            LoggerFactory.getLogger(PurchaseOrderDetailController.class);

    private static final String PID = "P301002";
    
    private String viewFilterStr = "";
    private String VIEW_GRID_DIALOG_ID = "view-grid-dialog-id";
    private String VIEW_GRID_DIALOG_URL = "view-grid-dialog-url";
    private String MODEL_KEY_PURCHASE_STATUS_FIELDS = "purchaseStatusFields";
    
    private String ID_MAP_KEY_PURCHASE_STATUS_DIALOG_ID = "edit-purchase-status-form-dialog"; 
    private String JS_MAP_KEY_EDIT_PURCHASESTATUS_SUBMIT = "edit-purchase-status-submit";

    @Resource
    private PurchaseOrderDetailService purchaseOrderDetailService;

    @Resource
    private PurchaseOrderService purchaseOrderService;
    
    @Resource
    private DataOptionService dataOptionService;
    
    @Resource
    private VirtualOrgService virtualOrgService;
    
    @Resource
    private ProvinceService provinceService;
    
    @Resource
    private CityService cityService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PURCHASE_ORDER_DETAIL.value();
    }

    @Override
    protected Service<PurchaseOrderDetail, String> getDataService() {
        return this.purchaseOrderDetailService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<PurchaseOrderDetail, PurchaseOrderDetailBean> getViewConverter() {
        return new PurchaseOrderDetailBeanConverter();
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
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/bidding/purchaseOrderDetailHidden.jsp";
    }
	
//    @Override
//	protected Map<String, String> prepareUrlMap() {
//
//		Map<String, String> urlMap = super.prepareUrlMap();
//
//		String contextPath = this.context.getContextPath();
//		urlMap.put(ControllerUrlMapKeys.SUB_GRID_LIST, contextPath + "/purchase-order/list.json");
//
//		return urlMap;
//	}

	@Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(VIEW_GRID_DIALOG_ID, "P301002_ViewGridDialog");
        idMap.put(VIEW_GRID_DIALOG_URL, "/project-edge/purchase-order-detail/list.json");
        idMap.put(ID_MAP_KEY_PURCHASE_STATUS_DIALOG_ID, "P301002-PURCHASE_ORDER-SubGridPurchaseStatusDialog");

        return idMap;
    }
	
	/**
     * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
     */
    @Override
    protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

        //if (field.isCommonVisible()) {
        //    return;
        //}
		
		// 项目评审结果更新编辑项
        if ((field.getFieldName().equals("purchaseType_"))) { // 申购状态
        	this.putFiledList(model, MODEL_KEY_PURCHASE_STATUS_FIELDS, field);
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
    
	@Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
        
        // OA审批
        jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT,
                String.format(ControllerMapUtils.OPEN_SUBMIT_AUDIT_JS, "P301002-PURCHASE_ORDER-SubDatagrid",
                        urlMap.get(ControllerUrlMapKeys.AUDIT_SUBMIT)));
        
        // OA审核日志
 		jsMap.put(ControllerJsMapKeys.OPEN_AUDIT_LOG, String.format(ControllerMapUtils.OPEN_AUDIT_LOG_JS,
 				idMap.get(ControllerIdMapKeys.OA_AUDIT_LOG_DIALOG), idMap.get(ControllerIdMapKeys.SUB_DATAGRID)));
        
 		// 打开查看
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_VIEW,
				String.format("PURCHASE_ORDER_DETAIL.openViewDialog('#%1$s', '%2$s', '#%3$s', %4$s)", 
						"P301002-PURCHASE_ORDER-SubGridViewGridDialog",
						urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND), idMap.get(ControllerIdMapKeys.SUB_DATAGRID),
						null));
		
		// 打开修改申购状态
		jsMap.put(ControllerJsMapKeys.SUB_GRID_OPEN_PURCHASE_STATUS,
	                String.format("PURCHASE_ORDER_DETAIL.openEditFormDialog('#%1$s', '%2$s');",
	                        "P301002-PURCHASE_ORDER-SubGridViewGridDialog",
	                        urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND)));
		
		// 修改保存申购状态
        jsMap.put(JS_MAP_KEY_EDIT_PURCHASESTATUS_SUBMIT, 
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_PURCHASE_STATUS_DIALOG_ID),
                        idMap.get(ControllerIdMapKeys.SUB_DATAGRID), false));
		
        return jsMap;
	}
	
	@Override
    protected void createFunctionBean(Page p, List<FunctionBean> functions,
            List<FunctionBean> searchFunctions, Map<String, String> funcGroupMap,
            Map<String, String> jsMap, Locale locale) {

        String pageId = p.getId();
        if (pageId.endsWith(ControllerFunctionIds.PURCHASE_STATUS)) { // 修改采购状态

			this.parseFunction(p, functions, funcGroupMap, "",
					jsMap.get(ControllerJsMapKeys.SUB_GRID_OPEN_PURCHASE_STATUS), locale);

        }
    }
	
	@Override
	public String getSubDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.PURCHASE_ORDER.value();
	}

	@Override
	public String getGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.grid.title.purchaseorder.detail", null, locale);
	}

	@Override
	public String getSubGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.menu.item.information.registration", null, locale);
	}

	@Override
	public OrderByDto getSubGridDefaultOrder() {
		// TODO Auto-generated method stub
		return new OrderByDto("cDatetime", false);
	}

	@Override
	public String getLinkedFilterFieldName() {
		// TODO Auto-generated method stub
		return "purchaseOrder_";
	}
	
	@Override
    protected boolean subGridUseFile() {
        return true;
    }
	
	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/bidding/purchaseOrderDetailJs.jsp";
	}
	
	@Override
	public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P301001";
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

		String pid = "P301001";
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
        // CommonFilter f = null;
        // f.addExact("projectRoleName", DataTypeEnum.PROJECT_ROLE.value());
        List<PurchaseOrder> purchaseOrdersList = this.purchaseOrderService.list(f, null);
        if (purchaseOrdersList != null) {
            for (PurchaseOrder purchaseOrders : purchaseOrdersList) {
                purchaseOrderOptions.add(new ComboboxOptionBean(purchaseOrders.getId(),
                        purchaseOrders.getPurchaseOrderNo()));
            }
        }
        
        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.PAYMENT_TYPE.value()); // 付款类型
        dataTypeList.add(DataTypeEnum.PURCHASE_TYPE.value()); // 申购类型
        dataTypeList.add(DataTypeEnum.PURCHASE_KIND.value()); // 申购类型
        
        f = new CommonFilter().addWithin("dataType", dataTypeList);

        // 付款类型
        List<ComboboxOptionBean> paymentTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> PurchaseTypeOptions = new ArrayList<>();
        List<ComboboxOptionBean> PurchaseKindOptions = new ArrayList<>();
        List<DataOption> list = this.dataOptionService.list(f, null);

        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.PAYMENT_TYPE.value())) {
            	paymentTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.PURCHASE_TYPE.value())) {
            	PurchaseTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.PURCHASE_KIND.value())) {
            	PurchaseKindOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            }
        }

		// 项目组
		CommonFilter virtualFilter = null;
		List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
		List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(virtualFilter, null);
		if (virtualOrgList != null) {
			for (VirtualOrg v : virtualOrgList) {
				projectGroupOptions.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
			}
		}
		
		// 省份
		List<ComboboxOptionBean> provinceOptions = new ArrayList<>();
        String format = "(%1$s) %2$s";
        List<Province> provinceList = this.provinceService.list(null, null);
        if (provinceList != null) {
            for (Province p : provinceList) {
            	provinceOptions.add(new ComboboxOptionBean(p.getId(),
                        String.format(format, p.getProvinceCode(), p.getProvinceName())));
            }
        }
  
        optionMap.put("PaymentTypeOptions", paymentTypeOptions);
        optionMap.put("PurchaseTypeOptions", PurchaseTypeOptions);
        optionMap.put("purchaseOrderOptions", purchaseOrderOptions);
		optionMap.put("projectGroupOptions", projectGroupOptions);
        optionMap.put("PurchaseKindOptions", PurchaseKindOptions);
        optionMap.put("provinceOptions", provinceOptions);

        return optionMap;
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
        
        // SubGrid多选
     	model.addAttribute(ControllerModelKeys.IS_SUB_GRID_SINGLE_SELECT, false);
     	model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "PURCHASE_ORDER_DETAIL.handleSelect");
     	model.addAttribute(ControllerModelKeys.IS_VIEW_HASGRID, true);
     	
		model.addAttribute(ControllerModelKeys.SUB_GRID_DBL_CLICK_ROW_HANDLER, "PURCHASE_ORDER_DETAIL.handleDblClickRow");
     	
        // 针对双Grid，添加审批通用界面的数据查询接口自定义（目前2类双grid查询url不一样）
    	Map<String, Object> modelMap = model.asMap();
        Map<String, String> urlMap =
                (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);
        String findUrl = urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND);
        urlMap.put("audit-findurl", findUrl);
        urlMap.put("edit-purchase-status-submit-url", this.context.getContextPath() + "/purchase-order-detail/sub-grid-edit-purchase-status.json");
        
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

    	CommonFilter f = null;
    	if (StringUtils.isEmpty(id)) {
	    	// 查看弹出层在未渲染的情况下无法修改queryParam，通过内部变量进行保存过滤条件
	    	if (StringUtils.isEmpty(commonFilterJson)) {
	    		commonFilterJson = viewFilterStr;
	    	} else {
	    		viewFilterStr = commonFilterJson;
	    	}
    	} else {
    		// 内部审批查看页在双Grid场合无法筛选数据，增加查询条件 (tags/viewGroupGridLayout)
    		f = new CommonFilter().addExact("purchaseOrder.id", id);
    	}
		
        JsonResultBean jsonResult =
                super.list(commonFilterJson, f, page, rows, sort, order, locale);

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
        JsonResultBean jsonResult = super.find(id, locale);
        try {
            PurchaseOrderDetailBean bean =
                    (PurchaseOrderDetailBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);

            // 获取城市下拉框的option，以便修改弹出画面上联动设置

            // 设置过滤信息
            CommonFilter filter = new CommonFilter().addExact("p.id", bean.getProvince_());

            // 获取数据
            List<City> list = this.cityService.list(filter, null);
            List<ComboboxOptionBean> resultList = new ArrayList<>();
            String format = "(%1$s) %2$s";
            for (City entity : list) {
                resultList.add(new ComboboxOptionBean(entity.getId(),
                        String.format(format, entity.getCityCode(), entity.getCityName())));
            }
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);
            
            // 获取申购列表
            List<ComboboxOptionBean> resultList2 = new ArrayList<>();
            List<PurchaseOrder> purchaseOrdersList = this.purchaseOrderService.list(null, null);
            if (purchaseOrdersList != null) {
                for (PurchaseOrder entity : purchaseOrdersList) {
                	resultList2.add(new ComboboxOptionBean(entity.getId(),
                            entity.getPurchaseOrderNo()));
                }
            }
            jsonResult.getDataMap().put("updatePruchaseNo", resultList2);

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
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    @SysLogAnnotation(description = "采购订单明细", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute PurchaseOrderDetailBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

    	if (StringUtils.isNotEmpty(bean.getPurchaseOrder_())) {
    		PurchaseOrder purchaseOrder = purchaseOrderService.find(bean.getPurchaseOrder_());
    		if (purchaseOrder != null) {
    			bean.setProject_(purchaseOrder.getProject().getId());
    		}
    	}
    	
        // 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
        // 参数在projectInitHidden.jsp中用GET的方式设定

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());
        // 实际进度的文件，位于/plan-progress/id文件夹内，id是项目组的主键值
        super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean,
                locale);
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
    @SysLogAnnotation(description = "采购订单明细", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute PurchaseOrderDetailBean bean,
            @RequestParam(required = false, defaultValue = "") String uploadType,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

    	if (StringUtils.isNotEmpty(bean.getPurchaseOrder_())) {
    		PurchaseOrder purchaseOrder = purchaseOrderService.find(bean.getPurchaseOrder_());
    		if (purchaseOrder != null) {
    			bean.setProject_(purchaseOrder.getProject().getId());
    		}
    	}
        // 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
        // 参数在projectInitHidden.jsp中用GET的方式设定

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目的文件，位于/project/id文件夹内，id是项目的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
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
    @SysLogAnnotation(description = "采购订单明细", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }

}
