package project.edge.web.controller.budget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.util.Base64;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.OAAuditApiType;
import project.edge.domain.converter.BudgetEstimateChangeBeanConverter;
import project.edge.domain.converter.BudgetEstimateVersionBeanConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateChange;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.view.BudgetEstimateChangeBean;
import project.edge.domain.view.BudgetEstimateVersionBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.budget.BudgetEstimateChangeService;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;

/**
 * 预算规划管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetEstimateChange")
public class BudgetEstimateChangeController
        extends TreeDoubleGridUploadController<BudgetEstimateChange, BudgetEstimateChangeBean> {

    private static final Logger logger =
            LoggerFactory.getLogger(BudgetEstimateChangeController.class);

    private static final String PID = "P10012";
    
    private static final String OA_TYPE_BUDGETCHANGE = "22";
    
    @Autowired
   	HttpServletRequest request;
       @Resource
   	CreateWorkFlowManager createWorkFlowManager;
    @Resource
    private BudgetEstimateChangeService budgetEstimateChangeService;

	@Resource
	private BudgetEstimateVersionService budgetEstimateVersionService;
	
	@Resource
	private BudgetEstimateService budgetEstimateService;
	
	@Resource
	private ProjectPersonService projectPersonService;
	
    @Resource
    private ProjectService projectService;


    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.BUDGET_ESTIMATE_CHANGE.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<BudgetEstimateChange, String> getDataService() {
        return this.budgetEstimateChangeService;
    }

    @Override
    protected ViewConverter<BudgetEstimateChange, BudgetEstimateChangeBean> getViewConverter() {
        return new BudgetEstimateChangeBeanConverter();
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开查看
        jsMap.put(ControllerJsMapKeys.OPEN_VIEW,
                String.format("CrudUtils.openViewDialog('#%1$s', '%2$s', '#%3$s', BUDGETCHANGE);",
                        idMap.get(ControllerIdMapKeys.VIEW_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.FIND),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
        if (isOaFlow()) {
    		// 提交审核，暂时不需要审核，直接生成版本
    		jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_SUBMIT_AUDIT,
    				String.format("BUDGETCHANGE.buildVersion('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
    		
    		// OA审批
    		jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT, String.format("BUDGETCHANGE.handleAuditClick('#%1$s', '%2$s');",
    				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), urlMap.get(ControllerUrlMapKeys.AUDIT_SUBMIT)));
        }

		
/*    	jsMap.put(ControllerJsMapKeys.OPEN_SUBMIT_AUDIT, String.format("BUDGETCHANGE.handleAuditClick('#%1$s', '%2$s');",
				idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), urlMap.get(ControllerUrlMapKeys.FIND)));*/
        return jsMap;
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/budget/budgetChange.jsp";
    }

    @Override
    protected String getJsCallbackObjName() {
        return this.getDataType();
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        // 预算规划变更前一览的URL
        String contextPath = this.context.getContextPath();
        urlMap.put(ControllerUrlMapKeys.SUB_GRID_LIST,
                contextPath + "/budget/budgetEstimate/changeList.json");

        return urlMap;
    }

    @Override
    public String getSubDataType() {
        // TODO Auto-generated method stub
        return DataTypeEnum.BUDGET_ESTIMATE.value();
    }

    @Override
    public String getGridTitle(Locale locale) {
        // TODO Auto-generated method stub
        return this.messageSource.getMessage("ui.grid.title.budget_estimate_change.after", null,
                locale);
    }

    @Override
    public String getSubGridTitle(Locale locale) {
        // TODO Auto-generated method stub
        return this.messageSource.getMessage("ui.grid.title.budget_estimate_change.before", null,
                locale);
    }

    @Override
    public OrderByDto getSubGridDefaultOrder() {
        // TODO Auto-generated method stub
        return new OrderByDto("code", true);
    }

    @Override
    public String getLinkedFilterFieldName() {
        // TODO Auto-generated method stub
        return "id";
    }

    @Override
    public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        // TODO Auto-generated method stub
        List<FunctionBean> functions = new ArrayList<>();

        return functions;
    }

    @Override
    public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        // TODO Auto-generated method stub
        List<FunctionBean> searchFunctions = new ArrayList<>();

        // 检索和清空检索
        List<String> pageIdList = Arrays.asList("P10003F08", "P10003F09");
        CommonFilter filter = new CommonFilter().addWithin("id", pageIdList);
        List<Page> pages = this.pageService.list(filter, null);
        for (Page p : pages) {
            FunctionBean f = new FunctionBean(p, this.messageSource, locale);
            f.setId(String.format(SUB_GRID_FUNCTION_ID_TEMPLATE, this.getPageId(),
                    f.getId().substring(f.getId().indexOf("F")))); // 重新设定id

            /*
             * // 检索
             * jsMap.put(ControllerJsMapKeys.SEARCH,
             * String.format("BUDGETCHANGE.searchSimple('#%1$s', '#%2$s');",
             * idMap.get(ControllerIdMapKeys.MAIN_DATAGRID),
             * idMap.get(ControllerIdMapKeys.FILTER_FORM)));
             */

            if (f.getId().endsWith(ControllerFunctionIds.SEARCH)) { // 检索
                f.setHandler(jsMap.get(ControllerJsMapKeys.SUB_GRID_SEARCH));
            } else if (f.getId().endsWith(ControllerFunctionIds.CLEAR_SEARCH)) { // 清空检索
                f.setHandler(jsMap.get(ControllerJsMapKeys.SUB_GRID_CLEAR_SEARCH));
            }

            searchFunctions.add(f);
        }

        return searchFunctions;
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();

        List<ComboboxOptionBean> yearOptions = new ArrayList<>();
        yearOptions.add(new ComboboxOptionBean("2019", "2019"));
        yearOptions.add(new ComboboxOptionBean("2020", "2020"));
        yearOptions.add(new ComboboxOptionBean("2021", "2021"));
        yearOptions.add(new ComboboxOptionBean("2022", "2022"));
        yearOptions.add(new ComboboxOptionBean("2023", "2023"));

        List<ComboboxOptionBean> yearChangeOptions = new ArrayList<>();
        yearChangeOptions.add(new ComboboxOptionBean("2019", "2019"));
        yearChangeOptions.add(new ComboboxOptionBean("2020", "2020"));
        yearChangeOptions.add(new ComboboxOptionBean("2021", "2021"));
        yearChangeOptions.add(new ComboboxOptionBean("2022", "2022"));
        yearChangeOptions.add(new ComboboxOptionBean("2023", "2023"));

        optionMap.put("yearOptions", yearOptions);
        optionMap.put("yearChangeOptions", yearChangeOptions);
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

		// 覆盖默认的SubGrid选中事件处理，不再联动MainGrid
		model.addAttribute(ControllerModelKeys.SUB_GRID_SELECT_HANDLER, "BUDGETCHANGE.handleSubGridSelect");
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
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    @RequestMapping("/list")
    @ResponseBody
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale)
            throws JsonParseException, JsonMappingException, IOException {

        JsonResultBean jsonResult = new JsonResultBean();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();

        if (commonFilterJson.length() == 0) {
            CommonFilter f =
                    new CommonFilter().addExact("year", Integer.parseInt(sdf.format(date)));
            jsonResult = super.list(null, f, page, rows, "code", "asc", locale);
        } else {
            CommonFilter filter = super.generateCommonFilter(null, commonFilterJson);
            String year = null;
            CommonFilter f = new CommonFilter();

            for (int i = 0; i < filter.getFilterFieldList().size(); i++) {
                if (filter.getFilterFieldList().get(i).getFieldName().equals("year")) {
                    year = filter.getFilterFieldList().get(i).getValue().toString();
                }
            }
            if (year == null) {
                f = new CommonFilter().addExact("year", Integer.parseInt(sdf.format(date)));
            }

            sort = "code";
            jsonResult = super.list(commonFilterJson, f, page, rows, sort, order, locale);
        }

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
    @ResponseBody
    @SysLogAnnotation(description = "预算规划变更", action = "新增")
    public JsonResultBean create(@ModelAttribute BudgetEstimateChangeBean bean,
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
    @SysLogAnnotation(description = "预算规划变更", action = "更新")
    public JsonResultBean update(@ModelAttribute BudgetEstimateChangeBean bean,
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
    @SysLogAnnotation(description = "预算规划变更", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }
    
    @RequestMapping("/auditSubmit")
  	@ResponseBody
  	@SysLogAnnotation(description = "预算规划变更", action = "审核")
  	public JsonResultBean
  		 auditSubmit(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
  		 @RequestParam String id, @RequestParam String year, @RequestParam String projectId, Locale locale) {
  		JsonResultBean jsonResult = new JsonResultBean();

		// 插入到t_oa_flow_history
		String userName=userBean.getSessionLoginName();
		String creatorId = userBean.getSessionUserId();
		int oaType = OAAuditApiType.OA_TYPE_BUDGETCHANGE.value();
  		
  		try {
  			
  			CommonFilter pf = new CommonFilter();
  			pf = new CommonFilter().addExact("person.id", creatorId).addExact("project.id", projectId);
  			List<ProjectPerson> projectPersonList = this.projectPersonService.list(pf, null);
  			String groupId = projectPersonList.get(0).getVirtualOrg().getId();
  			
  			String dataId = projectId + "," + year + "," + groupId;
  			
  			//将数据存到excel中
			File downloadFile = null;
			downloadFile = this.generateExcelFile(projectId, year, groupId);

  			HashMap<String, Object> reqMap = new HashMap<>();
  			
  			List<HashMap<String, String>> archives = new ArrayList<>();
  			HashMap<String, String> attMap = new HashMap<String, String>();
  			attMap.put("attachmentFileName", downloadFile.getName());
  			byte[] refereeFileOriginalBytes = FileUtils.readFileToByteArray(downloadFile);
  			byte[] refereeFileBase64Bytes = Base64.encodeBase64(refereeFileOriginalBytes);
  			String UpFile = new String(refereeFileBase64Bytes, "UTF-8");
  			attMap.put("attachmentFile", UpFile);
  			archives.add(attMap);
  			
  			Project entity = this.projectService.find(projectId);
  			reqMap.put("projectName", entity.getProjectName());
  			reqMap.put("projectNum", entity.getProjectNum());
  			reqMap.put("archives", archives);
  			reqMap.put("year", year);
  			reqMap.put("dataId", dataId);
  			
  			// 调用oa接口
  			logger.info("[OA Audit] No.22 BudgetEstimateChange");
  			// 调用oa接口
  			jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap, request);
  			
			if(jsonResult.getStatus() == JsonStatus.OK){
				//logger.info("[OA Audit] No.22 BudgetEstimateChange Audit request success, history data id was {}", dataId);
				logger.info("[OA Audit] No.22 BudgetEstimateChange Audit request success, history data groupId was {}", groupId);
				
	  			//根据当前用户所在专业组查找预算变更表里的初版数据
	  			CommonFilter f = new CommonFilter();
	  			f = new CommonFilter().addExact("project.id", projectId).addExact("group.id", groupId).addExact("year", Integer.parseInt(year));
	  			List<BudgetEstimateChange> budgetChangeList = this.budgetEstimateChangeService.list(f, null);
	  			
				for (BudgetEstimateChange budget : budgetChangeList) {
					budget.setAuditStatus(String.valueOf(FlowStatusEnum.REVIEWING.value()));
					budget.setModifier(userName);
					budget.setmDatetime(new Date());
					this.budgetEstimateChangeService.setData(budget);
				}
			}
  		} catch (Exception e) {
  			getLogger().error("Project audio error.", e);

  			// 在JSON对象内设定服务器处理结果状态
  			jsonResult.setStatus(JsonStatus.ERROR);
  		}

  		return jsonResult;
  	}
    
    
	//生成excel
	private File generateExcelFile(String projectId, String year, String groupId) throws IOException {
		FileOutputStream output = null;
		File file = null;
		try {

  			//根据当前用户所在专业组查找预算变更表里的初版数据
  			CommonFilter f = new CommonFilter();
  			f = new CommonFilter().addExact("project.id", projectId).addExact("group.id", groupId).addExact("year", Integer.parseInt(year));
  			List<BudgetEstimateChange> budgetChangeList = this.budgetEstimateChangeService.list(f, null);

			@SuppressWarnings("resource")
			Workbook wb = new XSSFWorkbook();

			XSSFCellStyle textCellStyle2 = (XSSFCellStyle) wb.createCellStyle();
			textCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中
			textCellStyle2.setBorderBottom(BorderStyle.THIN);
			textCellStyle2.setBorderLeft(BorderStyle.THIN);
			textCellStyle2.setBorderRight(BorderStyle.THIN);
			textCellStyle2.setBorderTop(BorderStyle.THIN);

			Sheet sheet = wb.createSheet("预算变更");

			XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();

			cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

			Row row1 = sheet.createRow(0);
			row1.createCell(0).setCellValue("编号");
			row1.getCell(0).setCellStyle(textCellStyle2);
			row1.createCell(1).setCellValue("序号");
			row1.getCell(1).setCellStyle(textCellStyle2);
			row1.createCell(2).setCellValue("设备名称");
			row1.getCell(2).setCellStyle(textCellStyle2);
			row1.createCell(3).setCellValue("主要性能指标");
			row1.getCell(3).setCellStyle(textCellStyle2);
			row1.createCell(4).setCellValue("参考品牌及型号");
			row1.getCell(4).setCellStyle(textCellStyle2);
			row1.createCell(5).setCellValue("单位");
			row1.getCell(5).setCellStyle(textCellStyle2);
			row1.createCell(6).setCellValue("数量");
			row1.getCell(6).setCellStyle(textCellStyle2);
			row1.createCell(7).setCellValue("含税单价（万元）");
			row1.getCell(7).setCellStyle(textCellStyle2);
			row1.createCell(8).setCellValue("含税总价（万元）");
			row1.getCell(8).setCellStyle(textCellStyle2);
			row1.createCell(9).setCellValue("备注");
			row1.getCell(9).setCellStyle(textCellStyle2);
			row1.createCell(10).setCellValue("专业组");
			row1.getCell(10).setCellStyle(textCellStyle2);
			row1.createCell(11).setCellValue("年份");
			row1.getCell(11).setCellStyle(textCellStyle2);

			for (int i = 0; i < budgetChangeList.size(); i++) {
				Row info = sheet.createRow(1 + i);
				String code = budgetChangeList.get(i).getCode();
				String orderNumber = budgetChangeList.get(i).getOrderNumber();
				String name = budgetChangeList.get(i).getName();
				String keyPerformance = budgetChangeList.get(i).getKeyPerformance();
				String brand = budgetChangeList.get(i).getBrand();
				String measurementUnit = budgetChangeList.get(i).getMeasurementUnit();
				Double count = budgetChangeList.get(i).getCount();
				Double taxInclusivePrice = budgetChangeList.get(i).getTaxInclusivePrice();
				Double taxExcludingPrice = budgetChangeList.get(i).getTaxExcludingPrice();
				String remarks = budgetChangeList.get(i).getRemarks();
				int budgetYear = 0;
				if(budgetChangeList.get(i).getYear() != null) {
					budgetYear = budgetChangeList.get(i).getYear();
				}
				
				if(code != null) {
					info.createCell(0).setCellValue(code);
				}
				if(orderNumber != null) {
					info.createCell(1).setCellValue(orderNumber);
				}
				if(name != null) {
					info.createCell(2).setCellValue(name);
				}
				if(keyPerformance != null) {
					info.createCell(3).setCellValue(keyPerformance);
				}
				if(brand != null) {
					info.createCell(4).setCellValue(brand);
				}
				if(measurementUnit != null) {
					info.createCell(5).setCellValue(measurementUnit);
				}
				if(count != null) {
					info.createCell(6).setCellValue(count);
				}
				if(taxInclusivePrice != null) {
					info.createCell(7).setCellValue(taxInclusivePrice);
				}
				if(taxExcludingPrice != null) {
					info.createCell(8).setCellValue(taxExcludingPrice);
				}
				if(remarks != null) {
					info.createCell(9).setCellValue(remarks);
				}
				if (budgetChangeList.get(i).getGroup() != null) {
					info.createCell(10).setCellValue(budgetChangeList.get(i).getGroup().getVirtualOrgName());
				} 
				if(!"".equals(budgetYear +"")) {
					info.createCell(11).setCellValue(budgetYear);
				}
			}

			Date now = new Date();

			String fileName = "预算变更_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
			String randomID = UUID.randomUUID().toString();
			String path = this.context.getRealPath("/");

			// mtstar\webapp\webapps\app
			File root = new File(path);

			File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
					"temp" + File.separator + "budgetChange" + File.separator + randomID);

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
    
    
    @RequestMapping("/buildVersion")
	@ResponseBody
	public JsonResultBean buildVersion(@RequestParam String projectId,@RequestParam String recordId,
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
		
    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	try {
		/*根据选中记录的id查找年份*/
		
        CommonFilter f = new CommonFilter().addExact("id", recordId);
		List<BudgetEstimateChange> budget = budgetEstimateChangeService.list(f, null);
		int year = budget.get(0).getYear();
		
		/*新增版本*/
		
        CommonFilter f1 = new CommonFilter().addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimateVersion> versionList = budgetEstimateVersionService.list(f1, null);
		
        //版本号——年月日+0000+_年份（例：202004130001）
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
        java.util.Date currTime = new java.util.Date();
        String curTime = formatter.format(currTime);
        String code = curTime+String.format("%04d", versionList.size()+1)+"_"+year;
        
		BudgetEstimateVersionBean budgetEstimateVersionBean = new BudgetEstimateVersionBean();
		budgetEstimateVersionBean.setYear(year);
		budgetEstimateVersionBean.setVersion(code);
		budgetEstimateVersionBean.setProject_(projectId);
		BudgetEstimateVersionBeanConverter converter = new BudgetEstimateVersionBeanConverter();
		BudgetEstimateVersion budgetEstimateVersion = converter.toEntity(budgetEstimateVersionBean, null, userBean, new Date());
		budgetEstimateVersionService.create(budgetEstimateVersion);
		
		/*根据版本号查找刚新增的版本id*/
		
        CommonFilter f2 = new CommonFilter().addExact("version", code);
		List<BudgetEstimateVersion> versionList1 = budgetEstimateVersionService.list(f2, null);
		String versionId = versionList1.get(0).getId();
		
		/*根据年份、项目获取初版预算*/
		
		CommonFilter f3 = new CommonFilter().addExact("version", "V0.0").addExact("year", year).addExact("project.id", projectId);
		List<BudgetEstimate> budgetList = budgetEstimateService.list(f3, null);
		
		/*将初版赋值版本号后新增到预算表中*/
		
		for(int i=0;i<budgetList.size();i++) {
			BudgetEstimate budget1 = new BudgetEstimate();
			budget1.setProject(budgetList.get(i).getProject());
			budget1.setCode(budgetList.get(i).getCode());
			budget1.setInnerCode(budgetList.get(i).getInnerCode());
			budget1.setOrderNumber(budgetList.get(i).getOrderNumber());
			budget1.setName(budgetList.get(i).getName());
			budget1.setVersion(versionId);
			budget1.setGroup(budgetList.get(i).getGroup());
			budget1.setMeasurementUnit(budgetList.get(i).getMeasurementUnit());
			budget1.setCount(budgetList.get(i).getCount());
			budget1.setTaxInclusivePrice(budgetList.get(i).getTaxInclusivePrice());
			budget1.setTaxExcludingPrice(budgetList.get(i).getTaxExcludingPrice());
			budget1.setTaxTotalPrice(budgetList.get(i).getTaxTotalPrice());
			budget1.setRemarks(budgetList.get(i).getRemarks());
			budget1.setYear(budgetList.get(i).getYear());
			budget1.setTaxPoint(budgetList.get(i).getTaxPoint());
			budget1.setDeviceClassify(budgetList.get(i).getDeviceClassify());
			budget1.setPersonMonth(budgetList.get(i).getPersonMonth());
			budget1.setPersonPrice(budgetList.get(i).getPersonPrice());
			budget1.setCreator(userBean.getSessionUserId());
			budget1.setcDatetime(new Date());
			budget1.setmDatetime(new Date());

			this.budgetEstimateService.create(budget1);
		}
		
		// 准备JSON结果
			jsonResult.setStatus(JsonStatus.OK);
			jsonResult.setMessage("版本已生成");
			
		} catch (Exception e) {
			logger.error("Exception while converting object to json string.", e);
		}
		return jsonResult;
	}
}
