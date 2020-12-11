package project.edge.web.controller.contract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import project.edge.common.constant.DataSourceEnum;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.domain.converter.PaymentContractBeanConverter;
import project.edge.domain.entity.Archive;
import project.edge.domain.entity.ContractCategory;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.entity.PaymentContractAttachment;
import project.edge.domain.entity.Person;
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

/**
 * 支出合同画面
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/payment-contract-statement")
public class PaymentContractController
        extends TreeGridUploadController<PaymentContract, PaymentContractBean> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentContractController.class);

    private static final String PID = "P4001";

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
     * 画面Open的入口方法，用于生成JSP。
     * 
     * @param paramMap 画面请求中的任何参数，都会成为检索的字段
     * @param model model
     * @param userBean session中的当前登录的用户信息
     * @param locale locale
     * @return
    @RequestMapping("/main")
    public String main(@RequestParam Map<String, String> paramMap, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        return super.main(paramMap, model, userBean, locale);
    }
     */

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
    
    /**
	 * 新建时自动显示当前用户
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/get-person-info")
	@ResponseBody
	public JsonResultBean getPersonInfo(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			Map<String, String> personInfo = new HashMap<String, String>();
			Person person = personService.find(userBean.getSessionUserId());
			if (person != null) {
				personInfo.put("entryPersonText", person.getPersonName());
				personInfo.put("entryPerson_", person.getId());
			} else {
				personInfo.put("entryPersonText", "");
				personInfo.put("entryPerson_", "");
			}
			
			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, personInfo);

		} catch (Exception e) {
			getLogger().error("Exception get PersonInfo.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}
	

	/**
	 * 生成合同流水
	 * @param userBean
	 * @param locale
	 * @return
	 */
	@RequestMapping("/generate-serial-no")
	@ResponseBody
	public JsonResultBean generateSerialNo(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			Locale locale) {
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			String template = "LS${yyyyMMdd}xxx";
			int elStart = template.indexOf("${");
			int elEnd = template.lastIndexOf("}");
			String el = template.substring(elStart + 2, elEnd);
			String v = DateUtils.date2String(new Date(), el);

			CommonFilter f = new CommonFilter().addAnywhere("serialNumber", v);
			List<PaymentContract> paymentContract = this.paymentContractService.list(f, null);
			String retstr = "";
			if (paymentContract != null) {
				retstr = String.format("%06d", (paymentContract.size() + 1));
			} else {
				retstr = "000001";
			}

			String projectNum = template.substring(0, elStart) + v + retstr;

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, projectNum);

		} catch (Exception e) {
			getLogger().error("Exception generate TenderingNo.", e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		return jsonResult;
	}
	
    /**
     * 提交OA审批
     * @param userBean
     * @param id
     * @param locale
     * @return
     */
	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "支出合同管理", action = "审核")
	public JsonResultBean auditSubmit(
			@ModelAttribute PaymentContract bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) throws IOException {

		JsonResultBean jsonResult = new JsonResultBean();
		PaymentContract entity = this.getDataService().find(bean.getId());
		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}

		String dataId = entity.getId();
		String userName=userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_CONTRACT.value();
		
		HashMap<String, Object> reqMap = new HashMap<>();
		if (entity.getPurchaseOrder() != null) {
			reqMap.put("purchaseOrder", entity.getPurchaseOrder().getPurchaseOrderNo());
		} else {
			reqMap.put("purchaseOrder", "");
		}
		reqMap.put("contractName", entity.getContractName());
		reqMap.put("serialNumber", entity.getSerialNumber());
		reqMap.put("startTime", DateUtils.date2String(entity.getStartTime(), Constants.DATE_FORMAT));
		reqMap.put("endTime", DateUtils.date2String(entity.getEndTime(), Constants.DATE_FORMAT));
		reqMap.put("contractAmount", entity.getContractAmount());
		if (entity.getSealType() != null) {
			reqMap.put("chapterType", entity.getSealType().getOptionName());
		} else {
			reqMap.put("chapterType", "");
		}
		reqMap.put("briefIntroduction", entity.getBriefIntroduction());
		reqMap.put("partyA", entity.getPartyA());
		reqMap.put("partyB", entity.getPartyB());
		if (entity.getContractKind() != null) {
			reqMap.put("contractKind", entity.getContractKind().getCategoryName());
		} else {
			reqMap.put("contractKind", "");
		}
//		reqMap.put("contractYear", entity.getContractYear());
//		reqMap.put("contractCount", entity.getContractCount());
//		reqMap.put("isTemporaryPricing", entity.getIsTemporaryPricing());
//		reqMap.put("amountExcludingTax", entity.getAmountExcludingTax());
//		reqMap.put("contractAttribute", entity.getContractAttribute());
//		reqMap.put("contractTime", entity.getContractTime());
//		reqMap.put("ontractAddress", entity.getOntractAddress());
//		reqMap.put("signingPeople", entity.getSigningPeople().getPersonName());
//		reqMap.put("partyBContact", entity.getPartyBContact());
//		reqMap.put("partyAContactInfo", entity.getPartyAContactInfo());
//		reqMap.put("partyBContactInfo", entity.getPartyBContactInfo());
//		reqMap.put("isIncludePendingData", entity.getIsIncludePendingData());
//		reqMap.put("virtualContract", entity.getVirtualContract());
//		reqMap.put("contractReturnInfo", entity.getContractReturnInfo());
//		reqMap.put("countersignatureReturn", entity.getCountersignatureReturn());
//		reqMap.put("mainProvisions", entity.getMainProvisions());
//		reqMap.put("remark", entity.getRemark());
//		reqMap.put("entryPerson", entity.getEntryPerson().getPersonName());
//		reqMap.put("entryTime", entity.getEntryTime());

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();
		
		// 调用oa接口
		String rtnObject = "";
		logger.info("[OA Audit] No.8 PaymentContract");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		// 如果请求审批成功更新表t_project 审批中
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.8 PaymentContract request success, data id was {}", dataId);
			PaymentContract po = this.getDataService().find(dataId);
			po.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			po.setmDatetime(new Date());
			po.setModifier(userName);
			paymentContractService.setData(po);
		}
		
		return jsonResult;
	}
}
