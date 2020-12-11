package project.edge.web.controller.budget;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.BudgetEstimateBeanConverter;
import project.edge.domain.converter.BudgetEstimateMainBeanConverter;
import project.edge.domain.converter.BudgetEstimateVersionBeanConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateMain;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.view.BudgetEstimateBean;
import project.edge.domain.view.BudgetEstimateMainBean;
import project.edge.domain.view.BudgetEstimateVersionBean;
import project.edge.service.budget.BudgetEstimateMainService;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.project.ProjectPersonService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 招标申购信息登记
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetEstimate")
class BudgetEstimateMainController extends TreeGridUploadController<BudgetEstimateMain, BudgetEstimateMainBean> {

	private static final Logger logger = LoggerFactory.getLogger(BudgetEstimateMainController.class);

	private static final String PID = "P10024";
	
    @Resource
    private BudgetEstimateMainService budgetEstimateMainService;
    
	@Resource
	private ProjectPersonService projectPersonService;
	
	@Resource
	private BudgetEstimateService budgetEstimateService;
	
    @Resource
    private BudgetEstimateVersionService budgetEstimateVersionService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.BUDGET_ESTIMATE_MAIN.value();
	}

	@Override
	protected Service<BudgetEstimateMain, String> getDataService() {
		return this.budgetEstimateMainService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<BudgetEstimateMain, BudgetEstimateMainBean> getViewConverter() {
		return new BudgetEstimateMainBeanConverter();
	}
	
	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/budget/budgetMainHidden.jsp";
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
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/sub-grid-list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "") String projectId,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) throws JsonParseException, JsonMappingException, IOException {
		
		JsonResultBean jsonResult = new JsonResultBean();
		List<BudgetEstimateMain> budgetEstimateMains = new ArrayList<>();
		List<BudgetEstimateMain> budgetEstimateMainCount = new ArrayList<>();
		List<BudgetEstimateMainBean> budgetEstimateMainBeans = new ArrayList<>();
		String year = null;
		String virtualOrg = null;
		
		CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
		if (commonFilterJson.length() != 0) {
			for (int i = 0; i < filter.getFilterFieldList().size(); i++) {
				if (filter.getFilterFieldList().get(i).getFieldName().equals("year")) {
					year = filter.getFilterFieldList().get(i).getValue().toString();
				} else if (filter.getFilterFieldList().get(i).getFieldName().equals("project.id")) {
					projectId = filter.getFilterFieldList().get(i).getValue().toString();
				} else if (filter.getFilterFieldList().get(i).getFieldName().equals("group.id")) {
					virtualOrg = filter.getFilterFieldList().get(i).getValue().toString();
				}
			}
		}
		
		//当前用户所在组
		CommonFilter rf = new CommonFilter();
		rf = new CommonFilter().addExact("person.id", userBean.getSessionUserId()).addExact("project.id", projectId);
		List<ProjectPerson> projectPersonList = this.projectPersonService.list(rf, null);
		
		if(projectPersonList.size()==0) {
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, 0);
			jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, budgetEstimateMainBeans);
			return jsonResult;
		}
		
		//是否有合并权限，0：无，1：有
		int isMerge = projectPersonList.get(0).getVirtualOrg().getIsBudgetMerge();
		//是否有查看所有预算权限，0：无，1：有
		int isGetAll = projectPersonList.get(0).getVirtualOrg().getIsGetAllBudget();
		
		CommonFilter f = new CommonFilter().addExact("project.id", projectId);
		if(year != null) {
			f = f.addExact("year",Integer.parseInt(year));
		}
		if(isMerge != 1 && isGetAll != 1) {
			//当前用户不是指定角色，只能查看本专业组数据
			f = f.addExact("group.id", projectPersonList.get(0).getVirtualOrg().getId());
		}
		if (virtualOrg != null) {
			f = f.addExact("group.id",virtualOrg);
		}
		
/*		else if(isMerge == 1 && userBean.getIsSuper() == false) {
			f = f.addExact("isAll", 1);
		}*/

		PageCtrlDto pages = new PageCtrlDto();
		pages.setPageSize(30);
		pages.setCurrentPage(page);
		List<OrderByDto> orders = new ArrayList<>();
		OrderByDto timeOrder = new OrderByDto();
		timeOrder.setColumnName("cDatetime");
		timeOrder.setDesc(true);
		orders.add(timeOrder);
		budgetEstimateMains = this.budgetEstimateMainService.list(f, orders, pages);
		budgetEstimateMainCount = this.budgetEstimateMainService.list(f, orders);
		
		for(BudgetEstimateMain b: budgetEstimateMains) {
			BudgetEstimateMainBean budgetBean = this.getViewConverter().fromEntity(b, this.messageSource, locale);
			if(userBean.getSessionUserId().equals("ADMIN_USER")) {
				budgetEstimateMainBeans.add(budgetBean);
			}
			else {
				if(!budgetBean.getCreatorText().equals("ADMIN_USER")) {
					budgetEstimateMainBeans.add(budgetBean);
				}
			}
		}
		
		jsonResult.setStatus(JsonStatus.OK);
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, budgetEstimateMainCount.size());
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, budgetEstimateMainBeans);
		
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
    @ResponseBody
	@SysLogAnnotation(description = "预算规划主表", action = "新增")
	public JsonResultBean create(@ModelAttribute BudgetEstimateMainBean bean,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
		JsonResultBean jsonResult = new JsonResultBean();
		List<BudgetEstimateMain> budgetMains = new ArrayList<>();
		BudgetEstimateBeanConverter converter = new BudgetEstimateBeanConverter();
		Date now = new Date();
		List<BudgetEstimate> budgetEstimates = new ArrayList<>();
		List<BudgetEstimateBean> budgetBeans = new ArrayList<>();
		double groupPayTotal = 0.0;
		
		//当前用户所在组
		CommonFilter rf = new CommonFilter();
		rf = new CommonFilter().addExact("person.id", userBean.getSessionUserId()).addExact("project.id", bean.getProject_());
		List<ProjectPerson> projectPersonList = this.projectPersonService.list(rf, null);
		//是否有合并权限，0：无，1：有
		int isMerge = projectPersonList.get(0).getVirtualOrg().getIsBudgetMerge();
		
		if(isMerge == 0 && bean.getIsAll() == 1) {
			// 准备JSON结果
    		jsonResult.setStatus(JsonStatus.ERROR);
			jsonResult.setMessage(this.messageSource.getMessage("无权限创建合并记录", null, locale));
			return jsonResult;
		}
			
		//每个专业组每年只能创建一条记录
		if(bean.getGroup_() != null) {
	    	CommonFilter f2 = new CommonFilter().addExact("year", bean.getYear()).addExact("group.id", bean.getGroup_()).addExact("project.id", bean.getProject_());
	    	budgetMains = this.budgetEstimateMainService.list(f2, null);
		}
		
		if(isMerge == 0 && budgetMains.size() > 0) {
	    	if(budgetMains.size() > 0) {
				// 准备JSON结果
	    		jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage(this.messageSource.getMessage("每年只能创建一条预算记录", null, locale));
				return jsonResult;
	    	}
		}
		else {
			//如果没权限，version不要存到表里
			if(isMerge == 0) {
				bean.setVersion("");
			}
			
			if(bean.getIsAll() == 1) {
				//项目办合并数据先查看是否还有专业组的预算是未审核或审核中，如果有，就提示无法创建记录
				ArrayList<Integer> flowList = new ArrayList<Integer>();
				flowList.add(0);
				flowList.add(1);
		    	CommonFilter f1 = new CommonFilter().addExact("year", bean.getYear()).addWithin("flowStatus", flowList).addExact("isAll", 0).addExact("project.id", bean.getProject_());
		    	List<BudgetEstimateMain> mains = this.budgetEstimateMainService.list(f1, null);
		    	if(mains.size() > 0) {
		    		String groupStr = "";
		    		for(BudgetEstimateMain main : mains) {
		    			if(main.getGroup() != null) {
		    				groupStr += main.getGroup().getVirtualOrgName()+"；";
		    			}
		    		}
		    		if(!StringUtils.isEmpty(groupStr)) {
						// 准备JSON结果
			    		jsonResult.setStatus(JsonStatus.ERROR);
						jsonResult.setMessage(this.messageSource.getMessage("当前不能合并该年预算，还有以下专业组的预算还未审核或在审核中：<br/>" + groupStr, null, locale));
						return jsonResult;
		    		}
		    	}
		    	
		    	//预算合并的支付金额
		    	CommonFilter f3 = new CommonFilter().addExact("year", bean.getYear()).addExact("flowStatus", 2).addExact("isAll", 0).addExact("project.id", bean.getProject_());
		    	List<BudgetEstimateMain> groupMains = this.budgetEstimateMainService.list(f3, null);
		    	for(BudgetEstimateMain main : groupMains) {
		    		if(main.getBudgetTotal() != null) {
		    			groupPayTotal = groupPayTotal + main.getBudgetTotal();
		    		}
	    		}
		    	
		    	bean.setBudgetTotal(groupPayTotal);
		    	jsonResult = super.create(bean, null, userBean, locale);
		    	
				if(jsonResult.getStatus()==1) {//创建成功就生成版本，将初版数据复制到新版数据中
					BudgetEstimateMainBean mainBean = (BudgetEstimateMainBean) jsonResult.getDataMap().get("returnObj");
					
			        //新增版本
					BudgetEstimateVersionBean budgetEstimateVersionBean = new BudgetEstimateVersionBean();
					budgetEstimateVersionBean.setYear(bean.getYear());
					budgetEstimateVersionBean.setVersion(bean.getVersion());
					budgetEstimateVersionBean.setBudgetMain_(mainBean.getId());
					budgetEstimateVersionBean.setName("1");
					budgetEstimateVersionBean.setProject_(bean.getProject_());
					BudgetEstimateVersionBeanConverter vConverter = new BudgetEstimateVersionBeanConverter();
					userBean.setSessionUserId(userBean.getSessionUserId());
					BudgetEstimateVersion budgetEstimateVersion = vConverter.toEntity(budgetEstimateVersionBean, null, userBean, new Date());
					budgetEstimateVersionService.create(budgetEstimateVersion);
					
					//获取V0.0版的数据
			    	CommonFilter f2 = new CommonFilter().addExact("version", "V0.0").addExact("year", bean.getYear()).addExact("project.id", bean.getProject_());
			    	budgetEstimates = this.budgetEstimateService.list(f2, null);
			    	
			    	for(BudgetEstimate budget: budgetEstimates) {
						BudgetEstimateBean budgetBean = converter.fromEntity(budget, this.messageSource, locale);
						budgetBean.setId(null);
	    				budgetBean.setVersion(budgetEstimateVersion.getId());
	    				budgetBean.setCreator(userBean.getSessionUserId());
	    				budgetBean.setcDatetime(new Date());
	    				budgetBean.setmDatetime(new Date());
						budgetBean.setBudgetEstimateMain_(mainBean.getId());
						BudgetEstimate budgetEstimate = converter.toEntity(budgetBean, null, userBean, now);
			    		this.budgetEstimateService.create(budgetEstimate);
			    		
			    		//updatePayAmountTotal(budgetBean.getInnerCode(), budgetBean.getYear(), budgetBean.getProject_(), budgetBean.getCode(),locale, userBean);
			    	}
				}
		    	
			}
			else {
				jsonResult = super.create(bean, null, userBean, locale);
			}
			
		}
		

		return jsonResult;
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
	@ResponseBody
	@SysLogAnnotation(description = "预算规划主表", action = "更新")
	public JsonResultBean update(@ModelAttribute BudgetEstimateMainBean bean,
			@RequestParam(required = false, defaultValue = "") String uploadType,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.update(bean, null, userBean, locale);
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
	@SysLogAnnotation(description = "预算规划主表", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
		JsonResultBean jsonResult = new JsonResultBean();
		
		//先更新子表数据，main_id设置成对应那年的初版main_id,几个加个和数量置空
		
		BudgetEstimateBeanConverter budgetEstimateBeanConverter = new BudgetEstimateBeanConverter();
		
		try {
		
			//获取要删除的主表记录信息
			BudgetEstimateMain budgetMain= this.budgetEstimateMainService.find(idsToDelete);
			
			//获取子表数据
			CommonFilter f = new CommonFilter().addExact("budgetEstimateMain.id", idsToDelete);
			List<BudgetEstimate> budgetEstimateList = this.budgetEstimateService.list(f, null);
			
			
			if(budgetMain.getIsAll() == 1) {//合并数据，直接删除，各专业组数据，就更新为出版
				for(BudgetEstimate budget: budgetEstimateList) {
					this.budgetEstimateService.delete(budget);
				}
			}
			else {
				//获取选中记录那年的初版数据（根据年份、创建人、项目找）
				CommonFilter f1 = new CommonFilter().addExact("year", budgetMain.getYear()).addExact("creator.id", "ADMIN_USER").addExact("project.id", budgetMain.getProject().getId());
				List<BudgetEstimateMain> budgetEstimateMainList = this.budgetEstimateMainService.list(f1, null);
				
				for(BudgetEstimate budget: budgetEstimateList) {
					BudgetEstimateBean bean = budgetEstimateBeanConverter.fromEntity(budget, this.messageSource, locale);
					bean.setBudgetEstimateMain_(budgetEstimateMainList.get(0).getId());
					bean.setCount(null);
					bean.setTaxExclusivePrice(null);
					bean.setTaxInclusivePrice(null);
					bean.setTaxInclusivePriceTotal(null);
					bean.setPaymentCount(null);
					bean.setPaymentPercent(null);
					bean.setPaymentAmount(null);
					
					String idBak = bean.getId();
					BudgetEstimate budgetEstimate = budgetEstimateBeanConverter.toEntity(bean, null, userBean, new Date());
					budgetEstimate.setId(idBak);
					this.budgetEstimateService.update(budgetEstimate);
					
					updateTotal(0, 1, null, budgetEstimate.getInnerCode(),budgetEstimate.getYear(), budgetEstimate.getProject().getId(), locale, userBean);
				}
			}
			
			return super.delete(idsToDelete, null, userBean, locale);
			
		}
		catch (Exception e) {
  			getLogger().error("Project audio error.", e);

  			// 在JSON对象内设定服务器处理结果状态
  			jsonResult.setStatus(JsonStatus.ERROR);
  		}
		
		return jsonResult;
		
	}

/*	*//**
	 * 管理平台提交审核
	 * 
	 * @param id
	 *            提交审核记录ID
	 * @param locale
	 * @return
	 * @throws IOException 
	 *//*
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
		reqMap.put("paymentType", entity.getPaymentType().getOptionName());
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
	}*/
	
	public boolean updatePayAmountTotal(String innerCode, int year, String projectId, String code,
			Locale locale, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
		
		// 获取上级节点
		CommonFilter f = null;
		f = new CommonFilter().addExact("code", innerCode).addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetEstimateList = this.budgetEstimateService.list(f, null);
		
		// 获取上级节点为innerCode的所有下级节点
		CommonFilter f1 = new CommonFilter().addExact("innerCode", innerCode).addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetDetailList = this.budgetEstimateService.list(f1, null);
		
		if(budgetEstimateList.size() > 0) {
			
			if(budgetDetailList.size()>0) {
				BigDecimal maxNode = new BigDecimal(0);
				for(BudgetEstimate detail : budgetDetailList) {
					//总价
					if (!StringUtils.isEmpty(detail.getPayAmountTotal())) {
						maxNode = maxNode.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(detail.getPayAmountTotal())?0:detail.getPayAmountTotal())));
					}
				}
				
				budgetEstimateList.get(0).setPayAmountTotal(maxNode.doubleValue());
				
				// 更新预算规划
				budgetEstimateService.update(budgetEstimateList.get(0));
			}

			if (!StringUtils.isEmpty(budgetEstimateList.get(0).getInnerCode())) {
				return updatePayAmountTotal(budgetEstimateList.get(0).getInnerCode(), year, projectId, code,
						locale, userBean);
			}
		}
		
		return false;
	}
	
	
	public boolean updateTotal(int versionFlag, int totalFlag, BigDecimal totalDiff, String innerCode, int year, String projectId,
			Locale locale, @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
		
		CommonFilter f = null;
		f = new CommonFilter().addExact("code", innerCode).addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetEstimateList = this.budgetEstimateService.list(f, null);
		
		CommonFilter f1 = new CommonFilter().addExact("innerCode", innerCode).addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetDetailList = this.budgetEstimateService.list(f1, null);
		
		if(budgetEstimateList.size() > 0) {
			if(budgetDetailList.size()>0) {
				BigDecimal maxNode = new BigDecimal(0);
				BigDecimal maxPaymentAmountTotal = new BigDecimal(0);
				for(BudgetEstimate detail : budgetDetailList) {
					//总价
					maxNode = maxNode.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(detail.getTaxTotalPrice())?0:detail.getTaxTotalPrice())));
					//当年支付总金额
					maxPaymentAmountTotal = maxNode.add(new BigDecimal(String.valueOf(StringUtils.isEmpty(detail.getPayAmountTotal())?0:detail.getPayAmountTotal())));
				}
				budgetEstimateList.get(0).setTaxTotalPrice(maxNode.doubleValue());
				budgetEstimateList.get(0).setPaymentAmount(maxPaymentAmountTotal.doubleValue());
				
				// 更新预算规划
				budgetEstimateService.update(budgetEstimateList.get(0));
			}
			
			if (!StringUtils.isEmpty(budgetEstimateList.get(0).getInnerCode())) {
				return updateTotal(versionFlag, totalFlag, totalDiff, budgetEstimateList.get(0).getInnerCode(), year, projectId, 
						locale, userBean);
			}
		}
		
		return false;
	}
	
}
