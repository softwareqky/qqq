package project.edge.web.controller.bidding;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.common.constant.SystemConfigKeys;
import project.edge.domain.converter.PurchaseOrderBeanConverter;
import project.edge.domain.entity.BudgetMainfee;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.PurchaseOrder;
import project.edge.domain.entity.PurchaseOrderAttachment;
import project.edge.domain.entity.PurchaseOrderDetail;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.PurchaseOrderBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.bidding.PurchaseOrderAttachmentService;
import project.edge.service.bidding.PurchaseOrderDetailService;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.budget.BudgetMainfeeService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 招标申购信息登记
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/purchase-order-detail")
class PurchaseOrderController extends TreeGridUploadController<PurchaseOrder, PurchaseOrderBean> {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

	private static final String PID = "P301001";
	@Resource
	CreateWorkFlowManager createWorkFlowManager;
	@Resource
	private ProjectPersonService projectPersonService;
	@Autowired
	HttpServletRequest request;
	@Resource
	private PurchaseOrderService purchaseOrderService;
	@Resource
	private PurchaseOrderAttachmentService purchaseOrderAttachmentService;
	@Resource
	private PurchaseOrderDetailService purchaseOrderDetailService;
    @Resource
    private DataOptionService dataOptionService;
    @Resource
    private BudgetMainfeeService budgetMainfeeService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.PURCHASE_ORDER.value();
	}

	@Override
	protected Service<PurchaseOrder, String> getDataService() {
		return this.purchaseOrderService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<PurchaseOrder, PurchaseOrderBean> getViewConverter() {
		return new PurchaseOrderBeanConverter();
	}

//	/**
//     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
//     * 
//     * @param locale
//     * @return key:[v_data_option].option_source，value:[v_data_option]
//     */
//    @Override
//    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
//
//        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();
//        
//        List<String> dataTypeList = new ArrayList<>();
//        dataTypeList.add(DataTypeEnum.PAYMENT_TYPE.value()); // 付款类型
//        dataTypeList.add(DataTypeEnum.PURCHASE_TYPE.value()); // 申购类型
//        
//        CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);
//
//        // 付款类型
//        List<ComboboxOptionBean> paymentTypeOptions = new ArrayList<>();
//        List<ComboboxOptionBean> PurchaseTypeOptions = new ArrayList<>();
//        List<DataOption> list = this.dataOptionService.list(f, null);
//
//        for (DataOption o : list) {
//            if (o.getDataType().equals(DataTypeEnum.PAYMENT_TYPE.value())) {
//            	paymentTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
//            } else if (o.getDataType().equals(DataTypeEnum.PURCHASE_TYPE.value())) {
//            	PurchaseTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
//            }
//        }
//
//        optionMap.put("PaymentTypeOptions", paymentTypeOptions);
//        optionMap.put("PurchaseTypeOptions", PurchaseTypeOptions);
//
//        return optionMap;
//    }
//    
//	/**
//	 * 画面Open的入口方法，用于生成JSP。
//	 * 
//	 * @param paramMap
//	 *            画面请求中的任何参数，都会成为检索的字段
//	 * @param model
//	 *            model
//	 * @param userBean
//	 *            session中的当前登录的用户信息
//	 * @param locale
//	 *            locale
//	 * @return
//	 */
//	@RequestMapping("/main")
//	public String main(@RequestParam Map<String, String> paramMap, Model model,
//			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
//
//		return super.main(paramMap, model, userBean, locale);
//	}

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
	@RequestMapping("/sub-grid-list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		//JsonResultBean jsonResult = super.list(commonFilterJson, null, page, rows, sort, order, locale);

		//return jsonResult;
		
		JsonResultBean jsonResult = new JsonResultBean();
		try {

			// 设置过滤信息
			CommonFilter filter = this.generateCommonFilter(null, commonFilterJson);

			// 设置分页信息
			PageCtrlDto pageCtrl = new PageCtrlDto();
			if (page > 0 && rows > 0) {
				pageCtrl.setCurrentPage(page);
				pageCtrl.setPageSize(rows);
			}

			// 设置排序信息
			List<OrderByDto> orders = new ArrayList<OrderByDto>();
			if (!StringUtils.isEmpty(sort)) {
				String[] orderArray = org.springframework.util.StringUtils.commaDelimitedListToStringArray(order);
				String[] sortArray = org.springframework.util.StringUtils.commaDelimitedListToStringArray(sort);
				for (int i = 0; i < orderArray.length; i++) {

					// 将bean的Text后缀的名字改为对应的entity的名字，即去掉Text后缀，规则见[t_data_fields].field_name_view
					String beanSort = sortArray[i];
					if (beanSort.endsWith("Text")) {
						beanSort = beanSort.substring(0, beanSort.length() - 4);
					}
					orders.add(new OrderByDto(beanSort, orderArray[i].equalsIgnoreCase(OrderByDto.DESC)));
				}
			}

			// 获取分页后的数据
			List<PurchaseOrder> list = this.getDataService().list(filter, orders, pageCtrl);
			List<ViewBean> resultList = new ArrayList<>();
			for (PurchaseOrder entity : list) {
				PurchaseOrderBean bean = this.getViewConverter().fromEntity(entity, this.messageSource, locale);
				String extId = entity.getExtId(); // OA单号
				BigDecimal totalPaymentRate = BigDecimal.ZERO;
				BigDecimal totalPaymentAmount = BigDecimal.ZERO;
				if (StringUtils.isNotEmpty(extId)) {
					CommonFilter f = new CommonFilter().addExact("purchaseNo", extId);
					List<BudgetMainfee> budgetList = budgetMainfeeService.list(f, null);
					if (budgetList.size() > 0) {
						for (BudgetMainfee budget : budgetList) {
							if (budget.getPayRatio() != null)
								totalPaymentRate = totalPaymentRate.add(budget.getPayRatio());
							if (budget.getPayAmount() != null)
								totalPaymentAmount = totalPaymentAmount.add(budget.getPayAmount());
						}
					}
				}
				bean.setTotalPaymentRate(totalPaymentRate.toString());
				bean.setTotalPaymentAmount(totalPaymentAmount.doubleValue());
				resultList.add(bean);
			}
			long total = pageCtrl.getRecordAmount();

			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, total);// easyui-datagrid分页用
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);// easyui-datagrid分页用

		} catch (Exception e) {
			this.getLogger().error("Exception listing the " + this.getDataType(), e);

			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

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
	@RequestMapping("/sub-grid-find")
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
	@RequestMapping("/sub-grid-add")
	@SysLogAnnotation(description = "招标申购信息登记", action = "新增")
	public void create(HttpServletRequest request, HttpServletResponse response, @ModelAttribute PurchaseOrderBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 申购状态，新增时默认为（0：待确认）
		//bean.setPurchaseType_("PURCHASE_TYPE_0");
		
		// 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
		// 参数在projectInitHidden.jsp中用GET的方式设定

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archives_", bean.getArchivesList());
		// 实际进度的文件，位于/plan-progress/id文件夹内，id是项目组的主键值
		super.createWithUpload(request, response, bean, null, fieldNameArchiveListMap, userBean, locale);
	}

	/**
	 * 修改，返回Json格式。
	 * 
	 * @param bean
	 *            表现层对象
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/sub-grid-edit")
	@SysLogAnnotation(description = "招标申购信息登记", action = "更新")
	public void update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute PurchaseOrderBean bean,
			@RequestParam(required = false, defaultValue = "") String uploadType,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 也可以省去uploadType参数，直接用bean内的成员变量，这里是为了显示的说明用法
		// 参数在projectInitHidden.jsp中用GET的方式设定

		Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
		fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

		// 项目的文件，位于/project/id文件夹内，id是项目的主键值
		super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
	}
	
	/**
	 * 招标申购状态修改
	 * @param request
	 * @param response
	 * @param bean
	 * @param userBean
	 * @param locale
	 */
	@RequestMapping("/sub-grid-edit-purchase-status")
	@SysLogAnnotation(description = "招标申购信息登记", action = "更新采购状态")
	public void updatePurchaseStatus(HttpServletRequest request, HttpServletResponse response, @ModelAttribute PurchaseOrderBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		if (StringUtils.isEmpty(bean.getId()) || StringUtils.isEmpty(bean.getPurchaseType_())) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage("数据格式有误，申购状态修改失败");
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, null);
			
		} else {
			PurchaseOrder po = this.getDataService().find(bean.getId());
			DataOption d = dataOptionService.find(bean.getPurchaseType_());
			po.setPurchaseType(d);
			purchaseOrderService.setData(po);
			
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage("申购状态修改成功");
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, this.getViewConverter().fromEntity(po, this.messageSource, locale));
		}
		
		try {
			String result = new ObjectMapper().writeValueAsString(jsonResult);
	        response.getWriter().write(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	@RequestMapping("/sub-grid-delete")
	@ResponseBody
	@SysLogAnnotation(description = "招标申购信息登记", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}

	/**
	 * 管理平台提交审核
	 * 
	 * @param id
	 *            提交审核记录ID
	 * @param locale
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/auditSubmit")
	@ResponseBody
	@SysLogAnnotation(description = "招标申购信息登记", action = "审核")
	public JsonResultBean auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam String id, Locale locale) throws IOException {
		JsonResultBean jsonResult = new JsonResultBean();

		PurchaseOrder entity = this.getDataService().find(id);

		if (entity == null) {
			jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
			return jsonResult;
		}
		String dataId = entity.getId();
		String userName = userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_PURCHASEORDER.value();

		// 向OA提交审核请求
		HashMap<String, Object> reqMap = new HashMap<>();
		reqMap.put("purchaseOrderNo", entity.getPurchaseOrderNo());
		if (entity.getProject() != null) {
			reqMap.put("projectName", entity.getProject().getProjectName());
		} else {
			reqMap.put("projectName", "");
		}
		if (entity.getPurchaseKind() != null) {
		    reqMap.put("purchaseKind", entity.getPurchaseKind().getOptionName());
		} else {
			reqMap.put("purchaseKind", "");
		}
		reqMap.put("purchaseName", entity.getPurchaseName());
		if (entity.getVirtualOrg() != null) {
			reqMap.put("virtualOrg", entity.getVirtualOrg().getVirtualOrgName());
		} else {
			reqMap.put("virtualOrg", "");
		}

		reqMap.put("applicant", entity.getApplicant().getUser().getId());
		reqMap.put("applicantContact", entity.getApplicantContact());
		reqMap.put("purchaseReason", entity.getPurchaseReason());
		reqMap.put("receivableCompany", entity.getReceivableCompany());
		if (entity.getPaymentType() != null) {
			reqMap.put("paymentType", entity.getPaymentType().getOptionName());
		} else {
			reqMap.put("paymentType", "");
		}
		reqMap.put("totalAmount", entity.getTotalAmount());
		reqMap.put("remark", entity.getRemark());
		if (entity.getPaymentTime() != null) {
			reqMap.put("paymentTime", DateUtils.date2String(entity.getPaymentTime(), Constants.DATE_FORMAT));
		} else {
			reqMap.put("paymentTime", "");
		}
		if (entity.getPaymentRate() != null) {
			BigDecimal a = new BigDecimal(entity.getPaymentRate());
        	a = a.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
			reqMap.put("paymentRate", a.toString() + "%");
		} else {
			reqMap.put("paymentRate", "");
		}
		if (entity.getPaymentAmount() != null) {
			reqMap.put("paymentAmount", entity.getPaymentAmount());
		} else {
			reqMap.put("paymentAmount", "");
		}
		Set<PurchaseOrderAttachment> attchments = entity.getPurchaseOrderAttachments();
		String fileName = "";
		String filePath = "";
		List<HashMap<String, String>> archives = new ArrayList<>();
		if (attchments.size() > 0) {
			for (PurchaseOrderAttachment attchment : attchments) {
				fileName = attchment.getArchive().getArchiveName();
				String rootFolderPath = this.systemConfigService
						.getStringConfig(SystemConfigKeys.ARCHIVE_ROOT_FOLDER_PATH);

				filePath = rootFolderPath + attchment.getArchive().getRelativePath();
				File refereeFile = new File(filePath);
				byte[] refereeFileOriginalBytes = FileUtils.readFileToByteArray(refereeFile);
				byte[] refereeFileBase64Bytes = Base64.encodeBase64(refereeFileOriginalBytes);
				String UpFile = new String(refereeFileBase64Bytes, "UTF-8");
				HashMap<String, String> attMap = new HashMap<String, String>();
				attMap.put("attachmentFileName", fileName);
				attMap.put("attachmentFile", UpFile);
				archives.add(attMap);
			}

		}
		reqMap.put("archives", archives);
		CommonFilter f = new CommonFilter().addExact("purchaseOrder.id", entity.getId());
		List<PurchaseOrderDetail> purchaseOrderDetailList = purchaseOrderDetailService.list(f, null);
		if (purchaseOrderDetailList.size() > 0) {
			List<Object> dataDetailMap = new ArrayList<>();
			for (PurchaseOrderDetail data : purchaseOrderDetailList) {
				HashMap<String, Object> purchaseOrderDetail = new HashMap<>();
				purchaseOrderDetail.put("parentBudgetname", data.getParentBudgetname());
				purchaseOrderDetail.put("materialName", data.getMaterialName());
				purchaseOrderDetail.put("specificationType", data.getSpecificationType());
				purchaseOrderDetail.put("purchaseQuantity", data.getPurchaseQuantity());
				purchaseOrderDetail.put("measurementUnit", data.getMeasurementUnit());
				purchaseOrderDetail.put("unitPrice", data.getUnitPrice());
				purchaseOrderDetail.put("sumMoney", data.getSumMoney());
				purchaseOrderDetail.put("usage", data.getUsageInfo());
				if (data.getPaymentNum() != null) {
					purchaseOrderDetail.put("paymentNum", data.getPaymentNum());
				} else {
					purchaseOrderDetail.put("paymentNum", "");
				}
				if (data.getPaymentAmount() != null) {
					purchaseOrderDetail.put("paymentAmount", data.getPaymentAmount());
				} else {
					purchaseOrderDetail.put("paymentAmount", "");
				}
				if (data.getTotalPaymentNum() != null) {
					purchaseOrderDetail.put("totalPaymentNum", data.getTotalPaymentNum());
				} else {
					purchaseOrderDetail.put("totalPaymentNum", "");
				}
				if (data.getTotalPaymentAmount() != null) {
					purchaseOrderDetail.put("totalPaymentAmount", data.getTotalPaymentAmount());
				} else {
					purchaseOrderDetail.put("totalPaymentAmount", "");
				}
				dataDetailMap.add(purchaseOrderDetail);
			}
			List<Object> dataDetail = new ArrayList<>();
			dataDetail.add(dataDetailMap);
			reqMap.put("dataDetail", dataDetail);
		}

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();
		
		logger.info("[OA Audit] No.5 PurchaseOrder");
		// 调用oa接口
		jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
		if (jsonResult.getStatus() == JsonStatus.OK) {
			logger.info("[OA Audit] No.5 PurchaseOrder Audit request success, data id was " + dataId);
			PurchaseOrder po = this.getDataService().find(dataId);
			po.setFlowStatus(FlowStatusEnum.REVIEWING.value());
			po.setmDatetime(new Date());
			po.setModifier(userName);
			purchaseOrderService.setData(po);
		}


		return jsonResult;
	}
}
