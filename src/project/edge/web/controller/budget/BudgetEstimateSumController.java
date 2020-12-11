package project.edge.web.controller.budget;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.NumberFormat;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
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
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.ProjectGroupEnum;
import project.edge.domain.converter.BudgetEstimateSumBeanConverter;
import project.edge.domain.entity.BudgetEstimateSum;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.BudgetEstimateSumBean;
import project.edge.domain.view.ProjectBean;
import project.edge.service.budget.BudgetEstimateSumService;
import project.edge.service.project.ProjectGroupService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.web.controller.common.TreeGridController;
import project.edge.web.controller.facility.PresetTaskProducer;

/**
 * 预算规划管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetEstimateSum")
public class BudgetEstimateSumController extends TreeGridController<BudgetEstimateSum, BudgetEstimateSumBean> {

	private static final Logger logger = LoggerFactory.getLogger(BudgetEstimateSumController.class);

	private static final String PID = "P10004";

	private static final String ID_MAP_KEY_BUDGETSUM_DIALOG = "edit-budgetsum-form-dialog";
	
	private static final String MODEL_KEY_SITE_FIELDS = "budgetsumFields";
	

	@Resource
	private BudgetEstimateSumService budgetEstimateSumService;

    @Resource
    private VirtualOrgService virtualOrgService;

	@Resource
	private ProjectService projectService;

	@Resource
	private PresetTaskProducer presetTaskProducer;

	@Resource
	private ProjectGroupService projectGroupService;

	@Override
	protected Logger getLogger() {
		return logger;
	}

	@Override
	public String getDataType() {
		return DataTypeEnum.BUDGET_ESTIMATE_SUM.value();
	}

	@Override
	protected String getPageId() {
		return PID;
	}

	@Override
	protected Service<BudgetEstimateSum, String> getDataService() {
		return this.budgetEstimateSumService;
	}

	@Override
	protected ViewConverter<BudgetEstimateSum, BudgetEstimateSumBean> getViewConverter() {
		return new BudgetEstimateSumBeanConverter();
	}

	@Override
	protected String getJavascriptJspPath() {
		return "/WEB-INF/jsp/budget/budgetsum.jsp";
	}

	@Override
	protected String getHiddenContentJspPath() {
		return "/WEB-INF/jsp/budget/budgetsumHidden.jsp";
	}

	/**
	 * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
	 */
	@Override
	protected void postFieldBeanInit(FieldBean field, Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

		if (field.isCommonVisible()) {
			return;
		}

		// 在[t_data_fields]中有且仅有这些附件字段是设为全局不可见
		if (field.getFieldName().equals("proposalArchive_")) { // 建议
			this.putFiledList(model, MODEL_KEY_SITE_FIELDS, field);
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
	protected List<CommonFilter> getUniqueFilter(BudgetEstimateSumBean bean) {
		// TODO Auto-generated method stub
		List<CommonFilter> list = new ArrayList<>();
		CommonFilter filter = new CommonFilter().addExact("id", bean.getId());
		list.add(filter);
		return list;
	}

	@Override
	protected List<CommonFilter> getUniqueFilter(BudgetEstimateSumBean bean, BudgetEstimateSum oldEntity) {
		// TODO Auto-generated method stub
		List<CommonFilter> list = new ArrayList<>();
		if (!bean.getId().equals(oldEntity.getId())) {
			CommonFilter filter = new CommonFilter().addExact("id", bean.getId());
			list.add(filter);
		}
		return list;
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
        List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
        // CommonFilter f = null;
        // f.addExact("projectRoleName", DataTypeEnum.PROJECT_ROLE.value());
        List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f, null);
        if (virtualOrgList != null) {
            for (VirtualOrg v : virtualOrgList) {
                projectGroupOptions.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
            }
        }
        
        optionMap.put("groupOptions", projectGroupOptions);

		return optionMap;
	}

	
	@Override
	protected Map<String, String> prepareJsMap(Map<String, String> idMap, Map<String, String> urlMap) {
		Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

		// 导入文件
        jsMap.put(ControllerJsMapKeys.OPEN_IMPORT,
                String.format("BUDGETSUM.openUploadFormDialog('#%1$s');",
                        idMap.get(ID_MAP_KEY_BUDGETSUM_DIALOG)));
        // 导出总表文件
        jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
                String.format("BUDGETSUM.openExportFormDialog('#%1$s');",
                        idMap.get(ID_MAP_KEY_BUDGETSUM_DIALOG)));
        
        // 导出明细表文件
//       jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
//                String.format("BUDGETSUM.openExportDetailFormDialog('#%1$s');",
//                        idMap.get(ID_MAP_KEY_BUDGETSUM_DIALOG)));
        
        return jsMap;
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
	public String main(@RequestParam Map<String, String> paramMap, Model model,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

		return super.main(paramMap, model, userBean, locale);
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
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "999999999") int rows,
			@RequestParam(required = false, defaultValue = "") String sort,
			@RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

		sort = "code";
		JsonResultBean jsonResult = super.list(commonFilterJson, null, page, rows, sort, order, locale);

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
	@SysLogAnnotation(description = "预算规划总表", action = "新增")
	public JsonResultBean create(@ModelAttribute BudgetEstimateSumBean bean,
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
	@RequestMapping("/edit")
	@ResponseBody
	@SysLogAnnotation(description = "预算规划总表", action = "更新")
	public JsonResultBean update(@ModelAttribute BudgetEstimateSumBean bean,
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
	@RequestMapping("/delete")
	@ResponseBody
	@SysLogAnnotation(description = "预算规划总表", action = "删除")
	public JsonResultBean delete(@RequestParam String idsToDelete,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		return super.delete(idsToDelete, null, userBean, locale);
	}

	@RequestMapping("/exportExcel")
	@ResponseBody
	public void exportExcel(String commonFilterJson, CommonFilter addedFilter, File serverFile,
			HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub
		super.exportExcel(commonFilterJson, addedFilter, serverFile, response, locale);
	}

	@RequestMapping("/import-excel-file")
	public void validateSourceModelType(@RequestParam String projectId,HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,Locale locale) {
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
					List<BudgetEstimateSumBean> beans = this.parseExcelFileToBudgetEstimateSumBeans(projectId,
							tempFile);

					if (!beans.isEmpty()) {
						BudgetEstimateSumBeanConverter converter = new BudgetEstimateSumBeanConverter();

						Date now = new Date();

						String planName = "预算规划总表_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);

						CommonFilter filter = new CommonFilter();
						filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
						// filter.addExact("isSiteTask", OnOffEnum.ON.value());

						for (BudgetEstimateSumBean bean : beans) {
							BudgetEstimateSum budgetEstimateSum = converter.toEntity(bean, null, userBean, now);
                            
							// 根据code查找是否存在相同的记录
                            CommonFilter f = new CommonFilter().addExact("code",bean.getCode());
                            JsonResultBean budgetJson = super.list(null, f, 1, 9999, "code", "asc", locale);
                            List<BudgetEstimateSumBean> resultList = new ArrayList<>();
                            resultList = (List<BudgetEstimateSumBean>) budgetJson.getDataMap().get(JsonResultBean.KEY_EASYUI_ROWS);
                            if (resultList.size() > 0) {
//                            	resultList = (List<BudgetEstimateBean>) ;
                            	this.delete(resultList.get(0).getId(), userBean, locale);
                            }
                            
							this.budgetEstimateSumService.create(budgetEstimateSum);
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

	private List<BudgetEstimateSumBean> parseExcelFileToBudgetEstimateSumBeans(String projectId, File tempFile)
			throws FileNotFoundException, IOException {

		List<BudgetEstimateSumBean> beans = new ArrayList<>();

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
				
                //专业组：对应到t_virtual_org的org_order
                CommonFilter f1 = new CommonFilter();
                if (!StringUtils.isEmpty(projectId)) {
                    f1 =  new CommonFilter().addExact("project.id", projectId);
                }
                List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(f1, null);

				for (int i = 0; i < sheet.getLastRowNum(); i++) {
					// 跳过第一二三行
					if (i < 4) {
						continue;
					}

					Row row = sheet.getRow(i);
					
					String orgName = "";
					if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(11)))) {
						orgName = ExcelUtils.getCellValue(row.getCell(11));
					}
					
					
					BudgetEstimateSumBean bean = new BudgetEstimateSumBean();

					// TODO
					bean.setId(ExcelUtils.getCellValue(row.getCell(0)));
					bean.setProject_(projectId);
					bean.setCode(ExcelUtils.getCellValue(row.getCell(0)));
					bean.setInnerCode(ExcelUtils.getCellValue(row.getCell(1)));

					Cell orderNumberCell = row.getCell(2);
					String orderNumberVal = null;
					switch (orderNumberCell.getCellTypeEnum()) {
					case STRING:
						orderNumberVal = orderNumberCell.getRichStringCellValue().getString();
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(orderNumberCell)) {
							orderNumberVal = DateUtils.date2String(orderNumberCell.getDateCellValue());
						} else {
							NumberFormat nf = NumberFormat.getInstance();
							String s = nf.format(row.getCell(2).getNumericCellValue());
							//这种方法对于自动加".0"的数字可直接解决
							//但如果是科学计数法的数字就转换成了带逗号的，例如：12345678912345的科学计数法是1.23457E+13，经过这个格式化后就变成了字符串“12,345,678,912,345”，这也并不是想要的结果，所以要将逗号去掉
							if (s.indexOf(",") >= 0) {
							    s = s.replace(",", "");
							}
							orderNumberVal = s;
						}
						break;
					case BOOLEAN:
						orderNumberVal = "" + orderNumberCell.getBooleanCellValue();
						break;
					case FORMULA:
						orderNumberVal = orderNumberCell.getCellFormula();
						break;
					case BLANK:
						orderNumberVal = "";
						break;
					default:
						orderNumberVal = orderNumberCell.getStringCellValue();
					}
					bean.setOrderNumber(String.valueOf(orderNumberVal));

					bean.setName(ExcelUtils.getCellValue(row.getCell(3)));
					bean.setKeyPerformance(ExcelUtils.getCellValue(row.getCell(4)));
					bean.setBrand(ExcelUtils.getCellValue(row.getCell(5)));
					bean.setUnit(ExcelUtils.getCellValue(row.getCell(6)));

					// excel中数量列有可能是公式，获取计算值
					Cell countCell = row.getCell(7);
					Double count;
					if (countCell != null) {
						switch (countCell.getCellTypeEnum()) {
						case STRING:
							count = Double.valueOf(countCell.getRichStringCellValue().toString());
							break;
						case FORMULA:
							if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(5)))) {
								count = Double.valueOf(String.valueOf(countCell.getNumericCellValue()));
								bean.setCount(count);
							}
							break;
						case NUMERIC:
							count = Double.valueOf(ExcelUtils.getCellValue(row.getCell(7)));
							bean.setCount(count);
						}
					}

					// excel中数量列有可能是公式，获取计算值
					Cell taxInclusivePriceCell = row.getCell(8);
					if (taxInclusivePriceCell != null) {
						switch (taxInclusivePriceCell.getCellTypeEnum()) {
						case FORMULA:
							if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(8)))) {
								Double taxInclusivePrice = Double
										.parseDouble(String.valueOf(taxInclusivePriceCell.getNumericCellValue()));
								bean.setTaxInclusivePrice(taxInclusivePrice);
							}
							break;
						case NUMERIC:
							Double taxInclusivePrice = Double.parseDouble(ExcelUtils.getCellValue(row.getCell(8)));
							bean.setTaxInclusivePrice(taxInclusivePrice);
						}
					}

					// excel中合计列是公式，获取计算值
					Cell totalPriceCell = row.getCell(9);
					if (totalPriceCell != null) {
						switch (totalPriceCell.getCellTypeEnum()) {
						case FORMULA:
							if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(9)))) {
								Double taxInclusivePriceTotal = Double
										.parseDouble(String.valueOf(totalPriceCell.getNumericCellValue()));
								bean.setTaxInclusivePriceTotal(taxInclusivePriceTotal);
							}
							break;
                        case NUMERIC:
							Double taxInclusivePriceTotal = Double
								.parseDouble(String.valueOf(totalPriceCell.getNumericCellValue()));
							bean.setTaxInclusivePriceTotal(taxInclusivePriceTotal);
						}
					}

					bean.setRemarks(ExcelUtils.getCellValue(row.getCell(10)));
                    
					for (int k = 0; k < virtualOrgList.size(); k++) {
						if (orgName.equals(virtualOrgList.get(k).getVirtualOrgName())) {
							bean.setGroup_(virtualOrgList.get(k).getId());
						}
					}
					
					if(ExcelUtils.getCellValue(row.getCell(12)) != ""){
						bean.setLevel(Integer.parseInt(ExcelUtils.getCellValue(row.getCell(12))));
					}
                    
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
	 * 导出预算规划明细表
	 * 
	 * @param response
	 * @param locale
	 */
	@RequestMapping("/export-detail-excel-file")
	public void exportBudgetSumDetailExcel(@RequestParam String projectId, @ModelAttribute ProjectBean bean, HttpServletResponse response, Locale locale) {

		JsonResultBean jsonResult = new JsonResultBean();
		try {

			File downloadFile = this.generateBudgetSumDetailExcelFile(projectId);

			String fileName = downloadFile.getName();

			String mimeType = URLConnection.guessContentTypeFromName(fileName);
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);

			// 解决中文文件名显示问题
			fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

			// response.setContentLength(downloadFile..getLabelFileContent().length);

			InputStream inputStream = new BufferedInputStream(new FileInputStream(downloadFile));

			// Copy bytes from source to destination, closes both streams.
			FileCopyUtils.copy(inputStream, response.getOutputStream());

			inputStream.close();
			response.getOutputStream().close();

			jsonResult.setStatus(JsonStatus.OK);
		} catch (Exception e) {
			logger.error("Exception while export plan task xls file", e);
			jsonResult.setStatus(JsonStatus.ERROR);
		}
	}

	private File generateBudgetSumDetailExcelFile(String projectId) throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {
			// Plan plan = this.planService.find(planId);

			CommonFilter filter = new CommonFilter().addExact("project.id", projectId);

			List<OrderByDto> orders = new ArrayList<>();
			orders.add(new OrderByDto("code", false));

			List<BudgetEstimateSum> BudgetEstimateSums = this.budgetEstimateSumService.list(filter, orders);

			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet();
			Row header = sheet.createRow(0);
			header.setHeight((short) 800);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));// 合并列(起始行，结束行，起始列，结束列)

			sheet.setColumnWidth(0, 10 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 10 * 256);
			sheet.setColumnWidth(3, 15 * 256);
			sheet.setColumnWidth(4, 15 * 256);
			sheet.setColumnWidth(5, 10 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256);
			sheet.setColumnWidth(8, 15 * 256);
			sheet.setColumnWidth(9, 15 * 256);

			Cell headCell = header.createCell(0);
			headCell.setCellValue("未来网络试验设施项目分专业概算明细表");

			CellStyle cellStyle = wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			Font font = wb.createFont();
			font.setBold(true); // 加粗
			font.setFontHeightInPoints((short) 15); // 设置标题字体大小
			cellStyle.setFont(font);

			headCell.setCellStyle(cellStyle);

			Row properties = sheet.createRow(1);
			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 9));
			/*
			 * Project p = this.projectService.find(plan.getProject().getId());
			 * 
			 * if (p != null) { properties.createCell(1).setCellValue(p.getProjectNum());
			 * properties.createCell(4).setCellValue(p.getProjectName()); }
			 */
			properties.createCell(0).setCellValue(" 建设单位名称:江苏省未来网络创新研究院");

            CellStyle border=wb.createCellStyle();
            //边框
            border.setBorderBottom(CellStyle.BORDER_THIN);
            border.setBorderLeft(CellStyle.BORDER_THIN);
            border.setBorderRight(CellStyle.BORDER_THIN);
            border.setBorderTop(CellStyle.BORDER_THIN);
            //边框颜色
            border.setBottomBorderColor(HSSFColor.BLACK.index);
            border.setRightBorderColor(HSSFColor.BLACK.index);
            border.setLeftBorderColor(HSSFColor.BLACK.index);
            border.setTopBorderColor(HSSFColor.BLACK.index);
			
			Row title = sheet.createRow(2);
			title.createCell(0).setCellValue("编号");
			//title.createCell(1).setCellValue("内部编号");
			title.createCell(1).setCellValue("序号");
			title.createCell(2).setCellValue("设备名称");
			title.createCell(3).setCellValue("单位");
			title.createCell(4).setCellValue("数量");
			title.createCell(5).setCellValue("含税单价（万元）");
			title.createCell(6).setCellValue("含税总价（万元）");
			title.createCell(7).setCellValue("备注");
			title.createCell(8).setCellValue("专业组");
			
            title.getCell(0).setCellStyle(border);
            title.getCell(1).setCellStyle(border);
            title.getCell(2).setCellStyle(border);
            title.getCell(3).setCellStyle(border);
            title.getCell(4).setCellStyle(border);
            title.getCell(5).setCellStyle(border);
            title.getCell(6).setCellStyle(border);
            title.getCell(7).setCellStyle(border);
            title.getCell(8).setCellStyle(border);

			Row num = sheet.createRow(3);
			num.createCell(0).setCellValue("0001");
			num.createCell(1).setCellValue("Ⅰ");
			num.createCell(2).setCellValue("Ⅱ");
			num.createCell(3).setCellValue("Ⅲ");
			num.createCell(4).setCellValue("Ⅳ");
			num.createCell(5).setCellValue("Ⅴ");
			num.createCell(6).setCellValue("Ⅵ");
			num.createCell(7).setCellValue("VII");
			
            num.getCell(0).setCellStyle(border);
            num.getCell(1).setCellStyle(border);
            num.getCell(2).setCellStyle(border);
            num.getCell(3).setCellStyle(border);
            num.getCell(4).setCellStyle(border);
            num.getCell(5).setCellStyle(border);
            num.getCell(6).setCellStyle(border);
            num.getCell(7).setCellStyle(border);
            num.createCell(8).setCellStyle(border);

			for (int i = 0; i < BudgetEstimateSums.size(); i++) {

				BudgetEstimateSum BudgetEstimateSum = BudgetEstimateSums.get(i);
				Row row = sheet.createRow(i + 4);
				
				row.createCell(0).setCellStyle(border);
				row.createCell(1).setCellStyle(border);
				row.createCell(2).setCellStyle(border);
				row.createCell(3).setCellStyle(border);
				row.createCell(4).setCellStyle(border);
				row.createCell(5).setCellStyle(border);
				row.createCell(6).setCellStyle(border);
				row.createCell(7).setCellStyle(border);
				row.createCell(8).setCellStyle(border);
				
				
				row.getCell(0).setCellValue(BudgetEstimateSum.getCode());
				//row.createCell(1).setCellValue(BudgetEstimateSum.getInnerCode());
				row.getCell(1).setCellValue(BudgetEstimateSum.getOrderNumber());
				row.getCell(2).setCellValue(BudgetEstimateSum.getName());
				row.getCell(3).setCellValue(BudgetEstimateSum.getMeasurementUnit());
				if (!StringUtils.isEmpty(BudgetEstimateSum.getCount())) {
					row.getCell(4).setCellValue(BudgetEstimateSum.getCount());
				}
				if (!StringUtils.isEmpty(BudgetEstimateSum.getTaxInclusivePrice())) {
					row.getCell(5).setCellValue(BudgetEstimateSum.getTaxInclusivePrice());
				}
				if (!StringUtils.isEmpty(BudgetEstimateSum.getTaxTotalPrice())) {
					row.getCell(6).setCellValue(BudgetEstimateSum.getTaxTotalPrice());
				}
				row.getCell(7).setCellValue(BudgetEstimateSum.getRemarks());
				if(BudgetEstimateSum.getGroup() != null) {
					row.getCell(8).setCellValue(BudgetEstimateSum.getGroup().getVirtualOrgName());
				}
				
			}

			Date now = new Date();

			String fileName = "未来网络试验设施项目分专业概算明细表" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
					+ ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + "sum" + File.separator + randomID);

			FileUtils.forceMkdir(uploadPath);

			file = new File(uploadPath.getAbsoluteFile(), fileName);

			if (!file.exists()) {
				file.createNewFile();
			}

			output = new FileOutputStream(file);
			wb.write(output);
			output.flush();
		} finally {

			if (output != null) {
				output.close();
			}
		}

		return file;
	}

	/**
     * 导出预算规划总表
     * 
     * @param response
     * @param locale
     */
    @RequestMapping("/export-excel-file")
    public void exportBudgetSumExcel(@RequestParam String projectId,@ModelAttribute ProjectBean bean, HttpServletResponse response, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            File downloadFile = this.generateBudgetSumExcelFile(projectId);

            String fileName = downloadFile.getName();

            String mimeType = URLConnection.guessContentTypeFromName(fileName);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);

            // 解决中文文件名显示问题
            fileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // response.setContentLength(downloadFile..getLabelFileContent().length);

            InputStream inputStream = new BufferedInputStream(new FileInputStream(downloadFile));

            // Copy bytes from source to destination, closes both streams.
            FileCopyUtils.copy(inputStream, response.getOutputStream());

            inputStream.close();
            response.getOutputStream().close();

            jsonResult.setStatus(JsonStatus.OK);
        } catch (Exception e) {
            logger.error("Exception while export plan task xls file", e);
            jsonResult.setStatus(JsonStatus.ERROR);
        }
    }

    private File generateBudgetSumExcelFile(String projectId) throws IOException {
        FileOutputStream output = null;
        File file = null;
        try {
            // Plan plan = this.planService.find(planId);

            CommonFilter filter = new CommonFilter().addExact("project.id", projectId);

            List<OrderByDto> orders = new ArrayList<>();
            orders.add(new OrderByDto("code", false));

            List<BudgetEstimateSum> BudgetEstimateSums = this.budgetEstimateSumService.list(filter, orders);

            @SuppressWarnings("resource")
            //HSSFWorkbook wb=new HSSFWorkbook();
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet();
            Row header = sheet.createRow(0);
            header.setHeight((short) 800);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));// 合并列(起始行，结束行，起始列，结束列)

            sheet.setColumnWidth(0, 10 * 256);
            sheet.setColumnWidth(1, 35 * 256);
            sheet.setColumnWidth(2, 35 * 256);
            sheet.setColumnWidth(3, 20 * 256);
            sheet.setColumnWidth(4, 15 * 256);
            sheet.setColumnWidth(5, 10 * 256);

            Cell headCell = header.createCell(0);
            headCell.setCellValue("未来网络试验设施项目分专业概算总表");

            CellStyle cellStyle = wb.createCellStyle();

            cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

            Font font = wb.createFont();
            font.setBold(true); // 加粗
            font.setFontHeightInPoints((short) 15); // 设置标题字体大小
            cellStyle.setFont(font);
            
            headCell.setCellStyle(cellStyle);

            Row properties = sheet.createRow(1);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
            /*
             * Project p = this.projectService.find(plan.getProject().getId());
             * 
             * if (p != null) { properties.createCell(1).setCellValue(p.getProjectNum());
             * properties.createCell(4).setCellValue(p.getProjectName()); }
             */
            properties.createCell(0).setCellValue(" 项目名称:未来网络试验设施项目    建设单位名称:江苏省未来网络创新研究院     表格编号:未来网络-01-I");

            
            CellStyle border=wb.createCellStyle();
            //边框
            border.setBorderBottom(CellStyle.BORDER_THIN);
            border.setBorderLeft(CellStyle.BORDER_THIN);
            border.setBorderRight(CellStyle.BORDER_THIN);
            border.setBorderTop(CellStyle.BORDER_THIN);
            //边框颜色
            border.setBottomBorderColor(HSSFColor.BLACK.index);
            border.setRightBorderColor(HSSFColor.BLACK.index);
            border.setLeftBorderColor(HSSFColor.BLACK.index);
            border.setTopBorderColor(HSSFColor.BLACK.index);
            
            Row title = sheet.createRow(2);
            title.createCell(0).setCellValue("序号");
            title.createCell(1).setCellValue("专业项目名称");
            title.createCell(2).setCellValue("专业子项名称");
            title.createCell(3).setCellValue("专业子项费用（万元）");
            title.createCell(4).setCellValue("含税总价（万元）");
            title.createCell(5).setCellValue("支出");
            
            title.getCell(0).setCellStyle(border);
            title.getCell(1).setCellStyle(border);
            title.getCell(2).setCellStyle(border);
            title.getCell(3).setCellStyle(border);
            title.getCell(4).setCellStyle(border);
            title.getCell(5).setCellStyle(border);
            
            Row num = sheet.createRow(3);
            num.createCell(0).setCellValue("Ⅰ");
            num.createCell(1).setCellValue("Ⅱ");
            num.createCell(2).setCellValue("Ⅲ");
            num.createCell(3).setCellValue("Ⅳ");
            num.createCell(4).setCellValue("Ⅴ");
            
            num.getCell(0).setCellStyle(border);
            num.getCell(1).setCellStyle(border);
            num.getCell(2).setCellStyle(border);
            num.getCell(3).setCellStyle(border);
            num.getCell(4).setCellStyle(border);
            num.createCell(5).setCellStyle(border);
            
            int k=5;
            double taxTotalPrice=0;
            DataFormat format = wb.createDataFormat();
            CellStyle numCellStyle = wb.createCellStyle();
            numCellStyle.setDataFormat(format.getFormat("0.00"));//设置单元类型
            numCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            numCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            numCellStyle.setBorderRight(CellStyle.BORDER_THIN);
            numCellStyle.setBorderTop(CellStyle.BORDER_THIN);
            numCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
            
            for (int i = 0; i < BudgetEstimateSums.size(); i++) {
                
                BudgetEstimateSum BudgetEstimateSum = BudgetEstimateSums.get(i);
                Row row = sheet.createRow(k);
                row.createCell(0).setCellStyle(border);
                row.createCell(1).setCellStyle(border);
                row.createCell(2).setCellStyle(border);
                row.createCell(3).setCellStyle(border);
                row.createCell(4).setCellStyle(border);
                row.createCell(5).setCellStyle(border);
                if (BudgetEstimateSum.getLevel()==2){
                    row.getCell(0).setCellValue(BudgetEstimateSum.getOrderNumber());
                    row.getCell(1).setCellValue(BudgetEstimateSum.getName());
                    if (!StringUtils.isEmpty(BudgetEstimateSum.getTaxTotalPrice())) {
                        row.getCell(4).setCellValue(BudgetEstimateSum.getTaxTotalPrice());
                        row.getCell(4).setCellStyle(numCellStyle);
                    }
                    k++;
                    if(BudgetEstimateSum.getTaxTotalPrice()==null) {
                    	taxTotalPrice+=0;
                    }
                    else {
                    	taxTotalPrice+=BudgetEstimateSum.getTaxTotalPrice();
                    }
                }
                if(BudgetEstimateSum.getLevel()==3){
                    row.getCell(0).setCellValue(BudgetEstimateSum.getOrderNumber());
                    row.getCell(2).setCellValue(BudgetEstimateSum.getName());
                    if (!StringUtils.isEmpty(BudgetEstimateSum.getTaxTotalPrice())) {
                        row.getCell(3).setCellValue(BudgetEstimateSum.getTaxTotalPrice());
                        row.getCell(3).setCellStyle(numCellStyle);
                    }
                    k++;
                }
                //row.createCell(8).setCellValue(BudgetEstimateSum.getRemarks());
                // row.createCell(9).setCellValue(BudgetEstimateSum.getGroup());
            }
            
            Row sum = sheet.createRow(4);
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 1, 3));
            sum.createCell(1).setCellValue("费用合计");
            sum.getCell(1).setCellStyle(border);
            sum.createCell(4).setCellValue(taxTotalPrice);
            sum.getCell(4).setCellStyle(numCellStyle);
            sum.createCell(5).setCellStyle(border);

            Date now = new Date();

            String fileName = "未来网络试验设施项目分专业概算总表" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT)
                    + ".xlsx";
            String randomID = UUID.randomUUID().toString();
            String path = this.context.getRealPath("/");

            // mtstar\webapp\webapps\app
            File root = new File(path);

            File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
                    "temp" + File.separator + "sum" + File.separator + randomID);

            FileUtils.forceMkdir(uploadPath);

            file = new File(uploadPath.getAbsoluteFile(), fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            output = new FileOutputStream(file);
            wb.write(output);
            output.flush();
        } finally {

            if (output != null) {
                output.close();
            }
        }

        return file;
    }
}
