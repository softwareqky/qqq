package project.edge.web.controller.budget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ExcelUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.ProjectGroupEnum;
import project.edge.domain.converter.ContractBudgetBeanConverter;
import project.edge.domain.entity.ContractBudget;
import project.edge.domain.entity.IncomeContract;
import project.edge.domain.entity.PaymentContract;
import project.edge.domain.view.ContractBudgetBean;
import project.edge.service.budget.ContractBudgetService;
import project.edge.service.contract.IncomeContractService;
import project.edge.service.contract.PaymentContractService;
import project.edge.web.controller.common.TreeGridController;

/**
 * 合同预算画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/contractBudget")
public class ContractBudgetController
        extends TreeGridController<ContractBudget, ContractBudgetBean> {

    private static final Logger logger = LoggerFactory.getLogger(ContractBudgetController.class);

    private static final String PID = "P10019";
    
	private static final String ID_MAP_KEY_BUDGET_DIALOG = "edit-budget-form-dialog";

	private static final String MODEL_KEY_SITE_FIELDS = "budgetFields";

    @Resource
    private ContractBudgetService contractBudgetService;
    
    @Resource
    private PaymentContractService paymentContractService;
    
    @Resource
    private IncomeContractService incomeContractService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.CONTRACT_BUDGET.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<ContractBudget, String> getDataService() {
        return this.contractBudgetService;
    }

    @Override
    protected ViewConverter<ContractBudget, ContractBudgetBean> getViewConverter() {
        return new ContractBudgetBeanConverter();
    }
    
	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/budget/contractBudgetJs.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/budget/contractBudgetHidden.jsp";
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

		CommonFilter f = null;
		List<ComboboxOptionBean> contractOptions = new ArrayList<>();
		List<PaymentContract> paymentContractList = this.paymentContractService.list(f, null);
		List<IncomeContract> incomeContractList = this.incomeContractService.list(f, null);
		
		if (paymentContractList != null) {
			for (PaymentContract v : paymentContractList) {
				contractOptions.add(new ComboboxOptionBean(v.getId(), v.getContractName()));
			}
		}
		if (incomeContractList != null) {
			for (IncomeContract v : incomeContractList) {
				contractOptions.add(new ComboboxOptionBean(v.getId(), v.getContractName()));
			}
		}

		optionMap.put("contractOptions", contractOptions);
		return optionMap;
	}
	

	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {
		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

		// 上传文件
		jsMap.put(ControllerJsMapKeys.OPEN_IMPORT,
				String.format("CONTRACTBUDGET.openUploadFormDialog('#%1$s');", idMap.get(ID_MAP_KEY_BUDGET_DIALOG)));
		
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
        
        JsonResultBean jsonResult = new JsonResultBean();
        try {
            // 检查是否存在记录
        	ContractBudget entity = this.getDataService().find(id);
            if (entity == null) {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource
                        .getMessage("message.error.record.not.exist", null, locale));
                return jsonResult;
            }
            
            ContractBudgetBean contractBudget = this.getViewConverter().fromEntity(entity, this.messageSource, locale);
            
            PaymentContract paymentContract = paymentContractService.find(contractBudget.getContract());
            IncomeContract incomeContract = incomeContractService.find(contractBudget.getContract());
            
            if (paymentContract != null) {
            	contractBudget.setContractName(paymentContract.getContractName());
            }
            else {
            	contractBudget.setContractName(incomeContract.getContractName());
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, contractBudget);

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
    @ResponseBody
    @SysLogAnnotation(description = "合同预算管理", action = "新增")
    public JsonResultBean create(@ModelAttribute ContractBudgetBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
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
    @SysLogAnnotation(description = "合同预算管理", action = "更新")
    public JsonResultBean update(@ModelAttribute ContractBudgetBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
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
    @SysLogAnnotation(description = "合同预算管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    
    @RequestMapping("/import-excel-file")
	public void validateSourceModelType(@RequestParam String projectId,HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		String contractId = request.getParameter("contract");
		JsonResultBean jsonResult = new JsonResultBean();

		// 当浏览器是IE时，如果返回application/json，IE会提示用户保存upload.json文件
		response.setContentType("text/html");

		try {

			if (request instanceof MultipartHttpServletRequest) {

				MultipartFile importFile = ((MultipartHttpServletRequest) request).getFile("importFile");

				String path = this.context.getRealPath("/");

				// mtstar\webapp\webapps\app
				File root = new File(path);
				String randomID = UUID.randomUUID().toString();

				File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
						"temp" + File.separator + randomID);
				FileUtils.forceMkdir(uploadPath);
				String uploadFilePath = uploadPath.getAbsolutePath() + File.separator + randomID;
				String templateExtension = FilenameUtils.getExtension(importFile.getOriginalFilename());
				String templateFilePath = uploadFilePath + Constants.DOT + templateExtension;
				if (templateFilePath != null && !importFile.isEmpty()) {
					File tempFile = new File(templateFilePath);

					FileUtils.writeByteArrayToFile(new File(templateFilePath), importFile.getBytes());

					// 解析excel文件，xls/xlsx需要分开处理
					List<ContractBudgetBean> beans = this.parseExcelFileToContractBudgetBeans(projectId, contractId, tempFile,
							userBean, locale);

					if (!beans.isEmpty()) {
						ContractBudgetBeanConverter converter = new ContractBudgetBeanConverter();

						Date now = new Date();

						String planName = "合同预算_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);

						CommonFilter filter = new CommonFilter();
						filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
						// filter.addExact("isSiteTask", OnOffEnum.ON.value());

						for (ContractBudgetBean bean : beans) {
							ContractBudget contractBudget = converter.toEntity(bean, null, userBean, now);
							this.contractBudgetService.create(contractBudget);
						}
					}
					// 准备JSON结果
					jsonResult.setStatus(JsonStatus.OK);
					jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beans);
				}
			}
		} catch (Exception e) {
			logger.error("Exception while import excel file.", e);
			// 在JSON对象内设定服务器处理结果状态
			jsonResult.setStatus(JsonStatus.ERROR);
		}

		try {
			if (jsonResult.getStatus() == 1) {
				jsonResult.setMessage("数据导入成功");
			} else {
				jsonResult.setMessage("数据导入失败");
			}
			String result = new ObjectMapper().writeValueAsString(jsonResult);
			response.getWriter().write("<textarea>" + result + "</textarea>");
		} catch (Exception e) {
			logger.error("Exception while converting object to json string.", e);
		}
	}

	private List<ContractBudgetBean> parseExcelFileToContractBudgetBeans(String projectId, String contractId, File tempFile,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale)
			throws FileNotFoundException, IOException {


		List<ContractBudgetBean> beans = new ArrayList<>();

		String extension = FilenameUtils.getExtension(tempFile.getName());

		Workbook wb = null;
		FileInputStream fileInputStream = null;

		try {
			fileInputStream = new FileInputStream(tempFile);

			if ("xlsx".equals(extension)) {
				wb = new XSSFWorkbook(fileInputStream);
			} else if ("xls".equals(extension)) {
				wb = new HSSFWorkbook(fileInputStream);
			}

			if (wb != null) {

				Sheet sheet = wb.getSheetAt(0);

				for (int i = 0; i <= sheet.getLastRowNum(); i++) {
					// 跳过第一二行
					if (i < 2) {
						continue;
					}

					Row row = sheet.getRow(i);

					ContractBudgetBean bean = new ContractBudgetBean();

					// TODO
					bean.setId(ExcelUtils.getCellValue(row.getCell(0)));
					bean.setName(ExcelUtils.getCellValue(row.getCell(0)));
					bean.setContent(ExcelUtils.getCellValue(row.getCell(1)));
					bean.setContract(contractId);
					bean.setProject_(projectId);

					beans.add(bean);
				}
			}
		} finally {
			if (fileInputStream != null) {
				fileInputStream.close();
			}

			if (wb != null) {
				wb.close();
			}
		}

		return beans;
	}

	 /**
     * 查找支出和收入合同，返回Json格式。
     * 
     * @param id ID
     * @param locale
     * @return
     */
    @RequestMapping("/getContracts")
    @ResponseBody
    public JsonResultBean getInfo(Locale locale) {
    	
        JsonResultBean jsonResult = new JsonResultBean();
        try {
        	
    		CommonFilter f = null;
    		List<ComboboxOptionBean> contractOptions = new ArrayList<>();
    		List<PaymentContract> paymentContractList = this.paymentContractService.list(f, null);
    		List<IncomeContract> incomeContractList = this.incomeContractService.list(f, null);
    		
    		if (paymentContractList != null) {
    			for (PaymentContract v : paymentContractList) {
    				contractOptions.add(new ComboboxOptionBean(v.getId(), v.getContractName()));
    			}
    		}
    		if (incomeContractList != null) {
    			for (IncomeContract v : incomeContractList) {
    				contractOptions.add(new ComboboxOptionBean(v.getId(), v.getContractName()));
    			}
    		}
    		
	    	if(contractOptions.size()>0) {
	            
	            // 准备JSON结果
	            jsonResult.setStatus(JsonStatus.OK);
	            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT,contractOptions);
	    	}
	    	else {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource
                        .getMessage("message.error.record.not.exist", null, locale));
                return jsonResult;
	    	}

        } catch (Exception e) {
            this.getLogger().error("Exception finding the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }
    
}
