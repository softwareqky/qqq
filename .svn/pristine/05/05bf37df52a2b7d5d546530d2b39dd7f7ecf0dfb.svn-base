package project.edge.web.controller.bidding;

import java.util.ArrayList;
import java.util.HashMap;
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
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.TenderingPurchaseBeanConverter;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.TenderingPurchase;
import project.edge.domain.view.TenderingPurchaseBean;
import project.edge.service.bidding.PurchaseOrderAttachmentService;
import project.edge.service.bidding.PurchaseOrderDetailService;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.bidding.TenderingPurchaseService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 招标申购绑定
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/purchase-order-sel")
class TenderingPurchaseController extends SingleGridController<TenderingPurchase, TenderingPurchaseBean> {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

	private static final String PID = "P301003";
	
	@Resource
	private ProjectPersonService projectPersonService;

	@Resource
	private PurchaseOrderService purchaseOrderService;
	
	@Resource
	private PurchaseOrderAttachmentService purchaseOrderAttachmentService;
	
	@Resource
	private PurchaseOrderDetailService purchaseOrderDetailService;
	
    @Resource
    private DataOptionService dataOptionService;
    
    @Resource
    private TenderingPurchaseService tenderingPurchaseService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.TENDERING_PURCHASE.value();
	}

	@Override
	protected Service<TenderingPurchase, String> getDataService() {
		return this.tenderingPurchaseService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<TenderingPurchase, TenderingPurchaseBean> getViewConverter() {
		return new TenderingPurchaseBeanConverter();
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
		List<String> purchaseTypeList = new ArrayList<>();
		purchaseTypeList.add("PURCHASE_TYPE_2"); // 公开招标
		f = new CommonFilter()//.addExact("flowStatus", FlowStatusEnum.REVIEW_PASSED.value()) // 审批通过
				.addWithin("purchaseType.id", purchaseTypeList);
		List<PurchaseOrder> purchaseOrdersList = this.purchaseOrderService.list(f, null);
		if (purchaseOrdersList != null) {
			for (PurchaseOrder purchaseOrders : purchaseOrdersList) {
				purchaseOrderOptions
						.add(new ComboboxOptionBean(purchaseOrders.getId(), purchaseOrders.getPurchaseName()));
			}
		}

		optionMap.put("purchaseOrderOptions", purchaseOrderOptions);

        return optionMap;
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
	public String main(@RequestParam Map<String, String> paramMap, 
			@RequestParam(required = true) String dataId, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		String r = super.main(paramMap, model, userBean, locale);
		
        Map<String, Object> modelMap = model.asMap();
        Map<String, String> urlMap = (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);

        String listUrl = urlMap.get(ControllerUrlMapKeys.LIST);
        String addUrl = urlMap.get(ControllerUrlMapKeys.ADD);
        urlMap.put(ControllerUrlMapKeys.LIST, listUrl + "?dataId=" + dataId);
        urlMap.put(ControllerUrlMapKeys.ADD, addUrl + "?dataId=" + dataId);

        return r;
	}

	/**
	 * 获取一览显示的数据，返回JSON格式。
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
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = true) String dataId,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		CommonFilter filter = new CommonFilter().addExact("tenderingPlan.id", dataId);
		JsonResultBean jsonResult = super.list(commonFilterJson, filter, page, rows, sort, order, locale);

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
    @SysLogAnnotation(description = "招标申购绑定", action = "新增")
    public JsonResultBean create(@ModelAttribute TenderingPurchaseBean bean,
            @RequestParam(required = true) String dataId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		// 设定招标记录ID
		bean.setTenderingPlan_(dataId);
		
		// 重复设定检查
		CommonFilter filter = new CommonFilter().addExact("purchaseOrder.id", bean.getPurchaseOrder_())
				.addExact("tenderingPlan.id", dataId);
		List<TenderingPurchase> dataList = tenderingPurchaseService.list(filter, null);
		if (dataList.size() > 0) {
			JsonResultBean ret = new JsonResultBean();
			ret.setStatus(JsonStatus.ERROR);
			ret.setMessage("请不要重复关联采购订单");
			return ret;
		}
    	
        return super.create(bean, null, userBean, locale);
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
	@SysLogAnnotation(description = "招标申购绑定", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}

}
