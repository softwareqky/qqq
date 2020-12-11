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
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.DataTypeEnum;
import project.edge.domain.converter.IncomeInvoiceBeanConverter;
import project.edge.domain.entity.IncomeInvoice;
import project.edge.domain.entity.Page;
import project.edge.domain.filter.PageFilter;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.IncomeInvoiceBean;
import project.edge.service.bidding.PurchaseOrderService;
import project.edge.service.contract.ContractCategoryService;
import project.edge.service.contract.IncomeInvoiceService;
import project.edge.service.contract.PaymentContractService;
import project.edge.service.contract.PaymentInvoiceService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.DoubleGridUploadController;

/**
 * 收入发票画面
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/income-invoice")
public class IncomeInvoiceController
        extends DoubleGridUploadController<IncomeInvoice, IncomeInvoiceBean> {

    private static final Logger logger = LoggerFactory.getLogger(IncomeInvoiceController.class);

    private static final String PID = "P4025";

    @Resource
    private IncomeInvoiceService incomeInvoiceService;

    @Resource
    private PaymentInvoiceService paymentInvoiceService;
    
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
        return DataTypeEnum.INCOME_INVOICE.value();
    }
	
    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/contract/incomeInvoiceJs.jsp";
    }
    
    @Override
	protected String getJsCallbackObjName() {
		return this.getDataType();
	}

    @Override
    protected Service<IncomeInvoice, String> getDataService() {
        return this.incomeInvoiceService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

	@Override
	public String getSubDataType() {
		// TODO Auto-generated method stub
		return DataTypeEnum.INCOME_CONTRACT.value();
	}

	@Override
	public String getGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.menu.item.income.invoice", null, locale);
	}

	@Override
	public String getSubGridTitle(Locale locale) {
		// TODO Auto-generated method stub
		return this.messageSource.getMessage("ui.datatype.income.contract", null, locale);
	}

	@Override
	public OrderByDto getSubGridDefaultOrder() {
		// TODO Auto-generated method stub
		return new OrderByDto("cDatetime", false);
	}

	@Override
	public String getLinkedFilterFieldName() {
		// TODO Auto-generated method stub
		return "incomeContract_";
	}

	@Override
	public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap, Map<String, String> urlMap,
			Map<String, String> jsMap, SessionUserBean userBean, Locale locale) {
		// TODO Auto-generated method stub
		PageFilter pageFilter = new PageFilter();

		String pid = "P4022";
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

		String pid = "P4022";
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
    protected ViewConverter<IncomeInvoice, IncomeInvoiceBean> getViewConverter() {
        return new IncomeInvoiceBeanConverter();
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
    @SysLogAnnotation(description = "收入发票", action = "新增")
    public void create(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute IncomeInvoiceBean bean,
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
    @SysLogAnnotation(description = "收入发票", action = "更新")
    public void update(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute IncomeInvoiceBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
        fieldNameArchiveListMap.put("archives_", bean.getArchivesList());

        // 项目组的文件，位于/project-group/id文件夹内，id是项目组的主键值
        super.updateWithUpload(request, response, bean, fieldNameArchiveListMap, userBean, locale);
    }

    @Override
    protected void postUpdate(IncomeInvoice entity, IncomeInvoice oldEntity, IncomeInvoiceBean bean,
            java.util.Map<String, Object> paramMap) throws IOException {
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
    @SysLogAnnotation(description = "收入发票", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
}
