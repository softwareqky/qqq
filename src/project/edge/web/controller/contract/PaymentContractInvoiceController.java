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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataSourceEnum;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.converter.PaymentContractBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ContractCategory;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.PaymentContractAttachment;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PaymentContractBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.contract.ContractCategoryService;
import project.edge.service.contract.PaymentContractService;
import project.edge.service.hr.PersonService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/payment-invoice")
public class PaymentContractInvoiceController
	extends TreeGridUploadController<PaymentContract, PaymentContractBean> {
	private static final Logger logger = LoggerFactory.getLogger(PaymentContractController.class);

    private static final String PID = "P4021";

    @Resource
    private PaymentContractService paymentContractService;

    @Resource
    private PurchaseOrderService purchaseOrderService;
    
    @Resource
    private ContractCategoryService contractCategoryService;
    
    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private PersonService personService;
    
    @Resource
    private VirtualOrgService virtualOrgService;
    
    @Autowired
	HttpServletRequest request;
    
	@Resource
	CreateWorkFlowManager createWorkFlowManager;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PAYMENT_CONTRACT.value();
    }

    @Override
    protected Service<PaymentContract, String> getDataService() {
        return this.paymentContractService;
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
	protected String getJsCallbackObjName() {
		return this.getDataType();
	}

    @Override
    protected ViewConverter<PaymentContract, PaymentContractBean> getViewConverter() {
        return new PaymentContractBeanConverter();
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
        List<OrderByDto> orders = null;

        List<ComboboxOptionBean> purchaseOrderOptions = new ArrayList<>();
        List<String> purchaseTypeList = new ArrayList<>();
		//purchaseTypeList.add("PURCHASE_TYPE_2"); // 公开招标
		purchaseTypeList.add("PURCHASE_TYPE_3"); // 合同签订
		f = new CommonFilter().addExact("flowStatus", FlowStatusEnum.REVIEW_PASSED.value()) // 审批通过
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
    @RequestMapping("/sub-grid-list")
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
    @RequestMapping("/sub-grid-find")
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
    @RequestMapping("/sub-grid-add")
    @SysLogAnnotation(description = "支出合同管理", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute PaymentContractBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());
        
        if (bean.getDataSource() == null) {
        	bean.setDataSource(DataSourceEnum.OWNER_CREATE.value());
        }

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
    @RequestMapping("/sub-grid-edit")
    @SysLogAnnotation(description = "支出合同管理", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute PaymentContractBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(PaymentContract entity, PaymentContract oldEntity,
            PaymentContractBean bean, java.util.Map<String, Object> paramMap) throws IOException {
        super.postUpdate(entity, oldEntity, bean, paramMap);
        //this.deleteArchiveFiles(entity.getArchivesToDelete());
        
        List<Archive> list = new ArrayList<>();
        for (PaymentContractAttachment attachment : entity.getAttachmentsToDelete()) {
            list.add(attachment.getArchive());
        }
        this.deleteArchiveFiles(list);
    }

    /**
     * 删除，返回Json格式。
     * 
     * @param idsToDelete 待删除的ID，CSV
     * @param userBean Session中的用户信息
     * @param locale
     * @return
     */
    @RequestMapping("/sub-grid-delete")
    @ResponseBody
    @SysLogAnnotation(description = "支出合同管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
