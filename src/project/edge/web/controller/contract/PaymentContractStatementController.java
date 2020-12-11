package project.edge.web.controller.contract;

import java.io.IOException;
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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.PaymentContractStatementBeanConverter;
import project.edge.domain.entity.ContractCategory;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.PaymentContractStatement;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PaymentContractStatementBean;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.contract.ContractCategoryService;
import project.edge.service.contract.PaymentContractService;
import project.edge.service.contract.PaymentContractStatementService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.DoubleGridUploadController;

/**
 * 支出合同结算画面
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/payment-contract-statement")
public class PaymentContractStatementController
        extends DoubleGridUploadController<PaymentContractStatement, PaymentContractStatementBean> {

    private static final Logger logger =
            LoggerFactory.getLogger(PaymentContractStatementController.class);

    private static final String PID = "P4010";

    private String VIEW_GRID_DIALOG_ID = "view-grid-dialog-id";
    private String VIEW_GRID_DIALOG_URL = "view-grid-dialog-url";

    @Resource
    private PaymentContractStatementService paymentContractStatementService;
    
    @Resource
    private PaymentContractService paymentContractService;

    @Resource
    private PurchaseOrderService purchaseOrderService;
    
    @Resource
    private ContractCategoryService contractCategoryService;
    
    @Resource
    private DataOptionService dataOptionService;
    
    @Resource
    private VirtualOrgService virtualOrgService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PAYMENT_CONTRACT_STATEMENT.value();
    }

    @Override
    protected Service<PaymentContractStatement, String> getDataService() {
        return this.paymentContractStatementService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/contract/paymentContractJs.jsp";
	}

    @Override
    protected ViewConverter<PaymentContractStatement, PaymentContractStatementBean> getViewConverter() {
        return new PaymentContractStatementBeanConverter();
    }
    

	@Override
	public String getSubDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.PAYMENT_CONTRACT.value();
	}

	@Override
	public String getGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.datatype.payment.contract.statement", null, locale);
	}

	@Override
	public String getSubGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.datatype.payment.contract", null, locale);
	}

	@Override
	public OrderByDto getSubGridDefaultOrder() {
		// TODO Auto-generated method stub
		return new OrderByDto("cDatetime", false);
	}

	@Override
	public String getLinkedFilterFieldName() {
		// TODO Auto-generated method stub
		return "paymentContract_";
	}
	
	@Override
	protected String getSubGridJsCallbackObjName() {
        return this.getSubDataType();
    }
	
	@Override
    protected boolean subGridUseFile() {
        return true;
    }
	
	@Override
	public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P4001";
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

		String pid = "P4001";
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
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        // 审核相关
 		//idMap.put(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG,
 		//		String.format(ControllerMapUtils.SUBMIT_AUDIT_FORM_DIALOG_ID, "P4001", this.getSubDataType()));

        idMap.put(VIEW_GRID_DIALOG_ID, PID + "_ViewGridDialog");
        idMap.put(VIEW_GRID_DIALOG_URL, "/project-edge/payment-contract-statement/list.json");
        
        return idMap;
    }
	
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        CommonFilter f = null;
        List<OrderByDto> orders = null;

        List<ComboboxOptionBean> paymentContractOptions = new ArrayList<>();

        List<PaymentContract> paymentContractList = this.paymentContractService.list(f, null);
        if (paymentContractList != null) {
            for (PaymentContract paymentContract : paymentContractList) {
            	paymentContractOptions.add(new ComboboxOptionBean(paymentContract.getId(),
            			paymentContract.getContractName()));
            }
        }

        optionMap.put("paymentContractOptions", paymentContractOptions);
        
        
        List<ComboboxOptionBean> purchaseOrderOptions = new ArrayList<>();
        List<String> purchaseTypeList = new ArrayList<>();
		//purchaseTypeList.add("PURCHASE_TYPE_2"); // 公开招标
		purchaseTypeList.add("PURCHASE_TYPE_3"); // 合同签订
		f = new CommonFilter()//.addExact("flowStatus", FlowStatusEnum.REVIEW_PASSED.value()) // 审批通过
				.addWithin("purchaseType.id", purchaseTypeList);
        List<PurchaseOrder> purchaseOrdersList = this.purchaseOrderService.list(null, null); // 暂时先放开合同关联申购号的判断
        if (purchaseOrdersList != null) {
            for (PurchaseOrder purchaseOrders : purchaseOrdersList) {
                purchaseOrderOptions.add(new ComboboxOptionBean(purchaseOrders.getId(),
                        purchaseOrders.getPurchaseName()));
            }
        }
        
        List<ComboboxOptionBean> contractCategoryOptions = new ArrayList<>();
        f = new CommonFilter().addExact("isLeaf", (short)1);
        orders = new ArrayList<>();
        orders.add(new OrderByDto("fieldOrder", true));
        List<ContractCategory> contractCategoryList = this.contractCategoryService.list(f, orders);
        if (contractCategoryList != null) {
        	for (ContractCategory item : contractCategoryList) {
        		contractCategoryOptions.add(new ComboboxOptionBean(item.getId(), item.getCategoryName()));
        	}
        }

        List<ComboboxOptionBean> sealTypeOptions = new ArrayList<>();
        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.SEAL_TYPE.value());
        
        f = new CommonFilter().addWithin("dataType", dataTypeList);
        List<DataOption> sealTypeList = this.dataOptionService.list(f, null);
        if (sealTypeList != null) {
	        for (DataOption o : sealTypeList) {
	            if (o.getDataType().equals(DataTypeEnum.SEAL_TYPE.value())) {
	            	sealTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
	            }
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
        optionMap.put("projectGroupOptions", projectGroupOptions);        
        optionMap.put("purchaseOrderOptions", purchaseOrderOptions);
        optionMap.put("sealTypeOptions", sealTypeOptions);
        optionMap.put("contractCategoryOptions", contractCategoryOptions);
        
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
    	
    	// SubGrid多选
     	model.addAttribute(ControllerModelKeys.IS_SUB_GRID_SINGLE_SELECT, false);
     	model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "PAYMENT_CONTRACT.handleSelect");
     	
     	String mainView = super.main(paramMap, model, userBean, locale);
     	
     	// 针对双Grid，添加审批通用界面的数据查询接口自定义（目前2类双grid查询url不一样）
    	Map<String, Object> modelMap = model.asMap();
        Map<String, String> urlMap = (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);
        String findUrl = urlMap.get(ControllerUrlMapKeys.SUB_GRID_FIND);
        urlMap.put("audit-findurl", findUrl);
     	
        return mainView;
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

        JsonResultBean jsonResult =
                super.list(commonFilterJson, null, page, rows, sort, order, locale);

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
     * 新建，返回Json格式。
     * 
     * @param bean 表现层对象
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/add")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute PaymentContractStatementBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
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
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute PaymentContractStatementBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(PaymentContractStatement entity, PaymentContractStatement oldEntity,
            PaymentContractStatementBean bean, java.util.Map<String, Object> paramMap)
            throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);
        this.deleteArchiveFiles(entity.getArchivesToDelete());
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
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
