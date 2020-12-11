package project.edge.web.controller.budget;

import java.io.IOException;
import java.math.BigDecimal;
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
import project.edge.domain.converter.BudgetMainfeeBeanConverter;
import project.edge.domain.entity.BudgetFee;
import project.edge.domain.entity.BudgetMainfee;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.BudgetMainfeeBean;
import project.edge.domain.view.PurchaseOrderBean;
import project.edge.service.budget.BudgetFeeService;
import project.edge.service.budget.BudgetMainfeeService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeGridUploadController;

@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetfee")
public class BudgetMainfeeController extends TreeGridUploadController<BudgetMainfee, BudgetMainfeeBean> {
	private static final Logger logger = LoggerFactory.getLogger(BudgetMainfeeController.class);

	private static final String PID = "P10025";
			
	@Resource
	private BudgetMainfeeService budgetMainfeeService;
	
    @Resource
    private DataOptionService dataOptionService;
    
    @Resource
    private BudgetFeeService budgetFeeService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.BUDGET_MAINFEE.value();
	}

	@Override
	protected Service<BudgetMainfee, String> getDataService() {
		return this.budgetMainfeeService;
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected ViewConverter<BudgetMainfee, BudgetMainfeeBean> getViewConverter() {
		return new BudgetMainfeeBeanConverter();
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
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping("/sub-grid-list")
	@ResponseBody
	public JsonResultBean list(@RequestParam(required = false, defaultValue = "") String commonFilterJson,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) throws JsonParseException, JsonMappingException, IOException {
		
		JsonResultBean jsonResult = new JsonResultBean();
		String workflowName = null, mainid = null;
		List<BudgetMainfee> budgetMainfees = new ArrayList<>();
		List<BudgetMainfee> budgetMainfeesCount = new ArrayList<>();
		List<BudgetMainfeeBean> budgetMainfeeBeans = new ArrayList<>();
		
		CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
		if (commonFilterJson.length() != 0) { 
			for (int i = 0; i < filter.getFilterFieldList().size(); i++) {
				if (filter.getFilterFieldList().get(i).getFieldName().equals("workflowName")) {
					workflowName = filter.getFilterFieldList().get(i).getValue().toString();
				} else if (filter.getFilterFieldList().get(i).getFieldName().equals("mainid")) {
					mainid = filter.getFilterFieldList().get(i).getValue().toString();
				}
			}
		}
		
		CommonFilter f = new CommonFilter();
		if(!StringUtils.isEmpty(workflowName)) {
			f = f.addAnywhere("workflowName", workflowName);
		}
		if (!StringUtils.isEmpty(mainid)) {
			f = f.addAnywhere("mainid", mainid);
		}
		
		PageCtrlDto pages = new PageCtrlDto();
		pages.setPageSize(30);
		pages.setCurrentPage(page);
		
		// 设置排序信息
		List<OrderByDto> orders = new ArrayList<OrderByDto>();
		if (!StringUtils.isEmpty(sort)) {
			String[] orderArray = StringUtils.commaDelimitedListToStringArray(order);
			String[] sortArray = StringUtils.commaDelimitedListToStringArray(sort);
			for (int i = 0; i < orderArray.length; i++) {

				// 将bean的Text后缀的名字改为对应的entity的名字，即去掉Text后缀，规则见[t_data_fields].field_name_view
				String beanSort = sortArray[i];
				if (beanSort.endsWith("Text")) {
					beanSort = beanSort.substring(0, beanSort.length() - 4);
				}
				orders.add(new OrderByDto(beanSort, orderArray[i].equalsIgnoreCase(OrderByDto.DESC)));
			}
		}

		
		budgetMainfees = this.budgetMainfeeService.list(f, orders, pages);
		budgetMainfeesCount = this.budgetMainfeeService.list(f, orders);
		
		for(BudgetMainfee b: budgetMainfees) {
			CommonFilter f1 = new CommonFilter().addExact("mainfee.id", b.getId());
			List<BudgetFee> budgetFees = this.budgetFeeService.list(f1, orders);
			BigDecimal totalPrice = new BigDecimal(0);
			
			for(BudgetFee bf: budgetFees) {
				if(bf.getBxjey()!=null) {
					totalPrice = totalPrice.add(bf.getBxjey());
				}
			}
			
			BudgetMainfeeBean feeBean = this.getViewConverter().fromEntity(b, this.messageSource, locale);
			feeBean.setTotalPrice(totalPrice);
			
			budgetMainfeeBeans.add(feeBean);
		}

		jsonResult.setStatus(JsonStatus.OK);
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, budgetMainfeesCount.size());
		jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, budgetMainfeeBeans);
		
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
	public JsonResultBean find(@RequestParam String id, Locale locale) {
		//return super.find(id, locale);
		JsonResultBean jsonResult = new JsonResultBean();
		try {
			// 检查是否存在记录
			BudgetMainfee entity = this.getDataService().find(id);
			if (entity == null) {
				jsonResult.setStatus(JsonStatus.ERROR);
				jsonResult.setMessage(this.messageSource.getMessage("message.error.record.not.exist", null, locale));
				return jsonResult;
			}

			// total price
			List<OrderByDto> orders = new ArrayList<>();
			OrderByDto timeOrder = new OrderByDto();
			timeOrder.setColumnName("cDatetime");
			timeOrder.setDesc(true);
			orders.add(timeOrder);
			CommonFilter f1 = new CommonFilter().addExact("mainfee.id", entity.getId());
			List<BudgetFee> budgetFees = this.budgetFeeService.list(f1, orders);
			BigDecimal totalPrice = new BigDecimal(0);
			
			for(BudgetFee bf: budgetFees) {
				if(bf.getBxjey()!=null) {
					totalPrice = totalPrice.add(bf.getBxjey());
				}
			}
			
			BudgetMainfeeBean feeBean = this.getViewConverter().fromEntity(entity, this.messageSource, locale);
			feeBean.setTotalPrice(totalPrice);
			
			// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, feeBean);

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
	 * @param bean
	 *            表现层对象
	 * @param userBean
	 *            Session中的用户信息
	 * @param locale
	 * @return
	 */
	@RequestMapping("/sub-grid-add")
	@ResponseBody
	@SysLogAnnotation(description = "费用支出登记", action = "新增")
	public JsonResultBean create(@ModelAttribute BudgetMainfeeBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		return super.create(bean, null, userBean, locale);
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
	public void update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute BudgetMainfeeBean bean,
			@RequestParam(required = false, defaultValue = "") String uploadType,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		// 项目的文件，位于/project/id文件夹内，id是项目的主键值
		super.updateWithUpload(request, response, bean, null, userBean, locale);
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
	
}
