package project.edge.web.controller.budget;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
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

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerFunctionIds;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.BudgetEstimateBeanConverter;
import project.edge.domain.entity.BudgetEstimate;
import project.edge.domain.entity.BudgetEstimateSum;
import project.edge.domain.entity.BudgetEstimateVersion;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.view.BudgetEstimateBean;
import project.edge.domain.view.ProjectBean;
import project.edge.service.budget.BudgetEstimateService;
import project.edge.service.budget.BudgetEstimateSumService;
import project.edge.service.budget.BudgetEstimateVersionService;
import project.edge.service.facility.SiteService;
import project.edge.service.system.DataOptionService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;

/**
 * 预算规划管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/budget/budgetEstimateVersion")
public class BudgetEstimateVersionController
        extends TreeDoubleGridUploadController<BudgetEstimate, BudgetEstimateBean> {

    private static final Logger logger =
            LoggerFactory.getLogger(BudgetEstimateVersionController.class);

    private static final String PID = "P10013";

    /*
     * private static final String ID_MAP_KEY_BUDGET_DIALOG =
     * "edit-budget-form-dialog";
     * 
     * private static final String MODEL_KEY_SITE_FIELDS = "budgetFields";
     */

    @Resource
    private BudgetEstimateService budgetEstimateService;

    @Resource
    private BudgetEstimateSumService budgetEstimateSumService;

    @Resource
    private BudgetEstimateVersionService budgetEstimateVersionService;

    @Resource
    private SiteService siteService;

    @Resource
    private DataOptionService dataOptionService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.BUDGET_ESTIMATE_PAGE.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<BudgetEstimate, String> getDataService() {
        return this.budgetEstimateService;
    }

    @Override
    protected ViewConverter<BudgetEstimate, BudgetEstimateBean> getViewConverter() {
        return new BudgetEstimateBeanConverter();
    }

    @Override
    protected String getJsCallbackObjName() {
        return this.getDataType();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/budget/budgetVersionJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/budget/budgetVersionHidden.jsp";
    }

    @Override
    protected boolean useFile() {
        return false;
    }

    /*    *//**
             * 特别处理多个附件字段，这些字段在[t_data_fields]中设为全局不可见。
             *//*
                * @Override protected void postFieldBeanInit(FieldBean field, Map<String,
                * String> paramMap, Model model,
                * 
                * @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {
                * 
                * if (field.isCommonVisible()) { return; }
                * 
                * // 在[t_data_fields]中有且仅有这些附件字段是设为全局不可见 if
                * (field.getFieldName().equals("proposalArchive_")) { // 建议
                * this.putFiledList(model, MODEL_KEY_SITE_FIELDS, field);
                * 
                * } }
                * 
                * @SuppressWarnings("unchecked") private void putFiledList(Model model, String
                * key, FieldBean f) { List<FieldBean> list = null; if
                * (model.containsAttribute(key)) { list = (List<FieldBean>)
                * model.asMap().get(key); } else { list = new ArrayList<>();
                * model.addAttribute(key, list); } list.add(f); }
                */

    /*
     * @Override protected List<CommonFilter> getUniqueFilter(BudgetEstimateBean
     * bean) { // TODO Auto-generated method stub List<CommonFilter> list = new
     * ArrayList<>(); CommonFilter filter = new CommonFilter().addExact("id",
     * bean.getId()); list.add(filter); return list; }
     * 
     * @Override protected List<CommonFilter> getUniqueFilter(BudgetEstimateBean
     * bean, BudgetEstimate oldEntity) { // TODO Auto-generated method stub
     * List<CommonFilter> list = new ArrayList<>(); if
     * (!bean.getId().equals(oldEntity.getId())) { CommonFilter filter = new
     * CommonFilter().addExact("id", bean.getId()); list.add(filter); } return list;
     * }
     */

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        // 版本一览的URL
        String contextPath = this.context.getContextPath();
        urlMap.put(ControllerUrlMapKeys.SUB_GRID_LIST,
                contextPath + "/budget/budgetVersion/list.json");

        return urlMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开查看
        jsMap.put(ControllerJsMapKeys.OPEN_VIEW,
                String.format("CrudUtils.openViewDialog('#%1$s', '%2$s', '#%3$s', BUDGETVERSION);",
                        idMap.get(ControllerIdMapKeys.VIEW_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.FIND),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        return jsMap;
    }

    @Override
    public String getSubDataType() {
        // TODO Auto-generated method stub
        return DataTypeEnum.BUDGET_ESTIMATE_VERSION.value();
    }

    @Override
    public String getGridTitle(Locale locale) {
        // TODO Auto-generated method stub
        return this.messageSource.getMessage("ui.grid.title.budget_estimate", null, locale);
    }

    @Override
    public String getSubGridTitle(Locale locale) {
        // TODO Auto-generated method stub
        return this.messageSource.getMessage("ui.grid.title.budget_estimate.version", null, locale);
    }

    @Override
    public OrderByDto getSubGridDefaultOrder() {
        // TODO Auto-generated method stub
        return new OrderByDto("cDatetime", true);
    }

    @Override
    public String getLinkedFilterFieldName() {
        // TODO Auto-generated method stub
        return "version";
    }

    @Override
    public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        // TODO Auto-generated method stub
        List<FunctionBean> functions = new ArrayList<>();

        // "导出"按钮
        CommonFilter filter = new CommonFilter().addExact("id", "P10010F06");
        List<Page> pages = this.pageService.list(filter, null);

        functions.add(new FunctionBean(true));
        FunctionBean f = new FunctionBean(pages.get(0), this.messageSource, locale);

        String subGridId = String.format(ControllerMapUtils.SUB_DATAGRID_ID, this.getPageId(),
                this.getSubDataType());
        f.setHandler(String.format(ControllerMapUtils.OPEN_VERSION_EXPORT_JS, subGridId));

        functions.add(f);
        functions.add(new FunctionBean(true));

        return functions;
    }

    @Override
    public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {
        // TODO Auto-generated method stub
        List<FunctionBean> searchFunctions = new ArrayList<>();

        // 检索和清空检索
        List<String> pageIdList = Arrays.asList("P10010F08", "P10010F09");
        CommonFilter filter = new CommonFilter().addWithin("id", pageIdList);
        List<Page> pages = this.pageService.list(filter, null);
        for (Page p : pages) {
            FunctionBean f = new FunctionBean(p, this.messageSource, locale);
            f.setId(String.format(SUB_GRID_FUNCTION_ID_TEMPLATE, this.getPageId(),
                    f.getId().substring(f.getId().indexOf("F")))); // 重新设定id

            if (f.getId().endsWith(ControllerFunctionIds.SEARCH)) { // 检索
                f.setHandler(jsMap.get(ControllerJsMapKeys.SUB_GRID_SEARCH));
            } else if (f.getId().endsWith(ControllerFunctionIds.CLEAR_SEARCH)) { // 清空检索
                f.setHandler(jsMap.get(ControllerJsMapKeys.SUB_GRID_CLEAR_SEARCH));
            }

            searchFunctions.add(f);
        }

        return searchFunctions;
    }

    /* *//**
          * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
          * 
          * @param locale
          * @return key:[v_data_option].option_source，value:[v_data_option]
          *//*
             * @Override protected Map<String, List<ComboboxOptionBean>>
             * prepareOptionMap(Locale locale) {
             * 
             * Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<String,
             * List<ComboboxOptionBean>>();
             * 
             * // ArrayList<ComboboxOptionBean> site = new ArrayList<ComboboxOptionBean>();
             * 
             * // String format = "(%1$s) %2$s"; CommonFilter f = null; // List<Site>
             * siteList = this.siteService.list(f, null); // if (siteList != null) { // for
             * (Site p : siteList) { // site.add(new ComboboxOptionBean(p.getId(), //
             * String.format(format, p.getSiteNum(), p.getStationName()))); // } // }
             * 
             * // optionMap.put("siteOptions", site);
             * 
             * ArrayList<ComboboxOptionBean> classify1Options = new
             * ArrayList<ComboboxOptionBean>();
             * 
             * f = new CommonFilter(); f.addExact("dataType",
             * DataTypeEnum.CLASSIFY_FIRST.value()); List<DataOption> classify1TypeList =
             * this.dataOptionService.list(f, null); if (classify1TypeList != null) { for
             * (DataOption d : classify1TypeList) { classify1Options.add(new
             * ComboboxOptionBean(d.getId(), d.getOptionName())); } }
             * 
             * optionMap.put("classify1Options", classify1Options);
             * 
             * ArrayList<ComboboxOptionBean> classify2Options = new
             * ArrayList<ComboboxOptionBean>();
             * 
             * f = new CommonFilter(); f.addExact("dataType",
             * DataTypeEnum.CLASSIFY_SECOND.value()); List<DataOption> classify2TypeList =
             * this.dataOptionService.list(f, null); if (classify2TypeList != null) { for
             * (DataOption d : classify2TypeList) { classify2Options.add(new
             * ComboboxOptionBean(d.getId(), d.getOptionName())); } }
             * 
             * optionMap.put("classify2Options", classify2Options);
             * 
             * ArrayList<ComboboxOptionBean> classify3Options = new
             * ArrayList<ComboboxOptionBean>();
             * 
             * f = new CommonFilter(); f.addExact("dataType",
             * DataTypeEnum.CLASSIFY_THIRD.value()); List<DataOption> classify3TypeList =
             * this.dataOptionService.list(f, null); if (classify3TypeList != null) { for
             * (DataOption d : classify3TypeList) { classify3Options.add(new
             * ComboboxOptionBean(d.getId(), d.getOptionName())); } }
             * 
             * optionMap.put("classify3Options", classify3Options);
             * 
             * ArrayList<ComboboxOptionBean> classify4Options = new
             * ArrayList<ComboboxOptionBean>();
             * 
             * f = new CommonFilter(); f.addExact("dataType",
             * DataTypeEnum.CLASSIFY_FOUR.value()); List<DataOption> classify4TypeList =
             * this.dataOptionService.list(f, null); if (classify4TypeList != null) { for
             * (DataOption d : classify4TypeList) { classify4Options.add(new
             * ComboboxOptionBean(d.getId(), d.getOptionName())); } }
             * 
             * optionMap.put("classify4Options", classify4Options);
             * 
             * ArrayList<ComboboxOptionBean> classify5Options = new
             * ArrayList<ComboboxOptionBean>();
             * 
             * f = new CommonFilter(); f.addExact("dataType",
             * DataTypeEnum.CLASSIFY_FIVE.value()); List<DataOption> classify5TypeList =
             * this.dataOptionService.list(f, null); if (classify5TypeList != null) { for
             * (DataOption d : classify5TypeList) { classify5Options.add(new
             * ComboboxOptionBean(d.getId(), d.getOptionName())); } }
             * 
             * optionMap.put("classify5Options", classify5Options);
             * 
             * return optionMap; }
             * 
             * 
             * @Override protected Map<String, String> prepareJsMap(Map<String, String>
             * idMap, Map<String, String> urlMap) { Map<String, String> jsMap =
             * super.prepareJsMap(idMap, urlMap);
             * 
             * // 上传文件 jsMap.put(ControllerJsMapKeys.OPEN_IMPORT,
             * String.format("BUDGET.openUploadFormDialog('#%1$s');",
             * idMap.get(ID_MAP_KEY_BUDGET_DIALOG)));
             * 
             * // 导出文件 jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
             * String.format("BUDGET.exportData();")); return jsMap; }
             */

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
    public JsonResultBean list(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "code") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {

        sort = "code";
        JsonResultBean jsonResult =
                super.list(commonFilterJson, null, page, rows, sort, order, locale);

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
     * 导出预算规划总表
     * 
     * @param response
     * @param locale
     */
    @RequestMapping("/export-excel-file")
    public void exportBudgetVersionExcel(@RequestParam String project_, @RequestParam String year,
            @ModelAttribute ProjectBean bean, HttpServletResponse response,
            HttpServletRequest request, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            File downloadFile =
                    this.generateBudgetVersionExcelFile(project_, Integer.parseInt(year));

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

    private File generateBudgetVersionExcelFile(String projectId, int year) throws IOException {
        FileOutputStream output = null;
        File file = null;
        try {

        	CommonFilter filter = new CommonFilter().addExact("project.id", projectId);

            List<OrderByDto> orders = new ArrayList<>();
            orders.add(new OrderByDto("code", false));

            List<BudgetEstimateSum> BudgetEstimateSums =
                    this.budgetEstimateSumService.list(filter, orders);

            List<BudgetEstimate> budget19 =
                    this.budgetEstimateService.getBudgetByYear(2019, projectId);
            List<BudgetEstimate> budget20 =
                    this.budgetEstimateService.getBudgetByYear(2020, projectId);
            List<BudgetEstimate> budget21 =
                    this.budgetEstimateService.getBudgetByYear(2021, projectId);
            List<BudgetEstimate> budget22 =
                    this.budgetEstimateService.getBudgetByYear(2022, projectId);
            List<BudgetEstimate> budget23 =
                    this.budgetEstimateService.getBudgetByYear(2023, projectId);

            @SuppressWarnings("resource")
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("年度使用计划表");

            sheet.setColumnWidth(0, 10 * 256);
            sheet.setColumnWidth(1, 35 * 256);
            sheet.setColumnWidth(2, 20 * 256);
            sheet.setColumnWidth(3, 20 * 256);
            sheet.setColumnWidth(4, 20 * 256);
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(6, 20 * 256);
            sheet.setColumnWidth(7, 20 * 256);

            CellStyle border = wb.createCellStyle();
            // 边框
            border.setBorderBottom(CellStyle.BORDER_THIN);
            border.setBorderLeft(CellStyle.BORDER_THIN);
            border.setBorderRight(CellStyle.BORDER_THIN);
            border.setBorderTop(CellStyle.BORDER_THIN);
            // 边框颜色
            border.setBottomBorderColor(HSSFColor.BLACK.index);
            border.setRightBorderColor(HSSFColor.BLACK.index);
            border.setLeftBorderColor(HSSFColor.BLACK.index);
            border.setTopBorderColor(HSSFColor.BLACK.index);

            Row title = sheet.createRow(0);
            title.createCell(0).setCellValue("序号");
            title.createCell(1).setCellValue("设备名称");
            title.createCell(2).setCellValue("含税总价（万元）");
            title.createCell(3).setCellValue("19年用款计划");
            title.createCell(4).setCellValue("20年用款计划");
            title.createCell(5).setCellValue("21年用款计划");
            title.createCell(6).setCellValue("22年用款计划");
            title.createCell(7).setCellValue("23年用款计划");

            title.getCell(0).setCellStyle(border);
            title.getCell(1).setCellStyle(border);
            title.getCell(2).setCellStyle(border);
            title.getCell(3).setCellStyle(border);
            title.getCell(4).setCellStyle(border);
            title.getCell(5).setCellStyle(border);
            title.getCell(6).setCellStyle(border);
            title.getCell(7).setCellStyle(border);

            int k = 2;
            double taxTotalPrice = 0;
            double taxTotalPrice19 = 0;
            double taxTotalPrice20 = 0;
            double taxTotalPrice21 = 0;
            double taxTotalPrice22 = 0;
            double taxTotalPrice23 = 0;
            DataFormat format = wb.createDataFormat();
            CellStyle numCellStyle = wb.createCellStyle();
            numCellStyle.setDataFormat(format.getFormat("0.00"));// 设置单元类型
            numCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            numCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            numCellStyle.setBorderRight(CellStyle.BORDER_THIN);
            numCellStyle.setBorderTop(CellStyle.BORDER_THIN);
            numCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
            numCellStyle.setTopBorderColor(HSSFColor.BLACK.index);

            for (int i = 0; i < BudgetEstimateSums.size(); i++) {
                BudgetEstimateSum budgetEstimateSum = BudgetEstimateSums.get(i);
                Row row = sheet.createRow(k);
                row.createCell(0).setCellStyle(border);
                row.createCell(1).setCellStyle(border);
                row.createCell(2).setCellStyle(border);
                row.createCell(3).setCellStyle(border);
                row.createCell(4).setCellStyle(border);
                row.createCell(5).setCellStyle(border);
                row.createCell(6).setCellStyle(border);
                row.createCell(7).setCellStyle(border);

                if (budget19.size() > 0) {
                    for (int a = 0; a < budget19.size(); a++) {
                        BudgetEstimate budgetRow = budget19.get(a);
                        if ("0002".equals(budgetRow.getCode())) {
                            taxTotalPrice19 = budgetRow.getTaxTotalPrice();
                        }
                        if (budgetRow.getCode().equals(budgetEstimateSum.getCode())) {
                            if (!StringUtils.isEmpty(budgetRow.getTaxTotalPrice())) {
                                row.createCell(3).setCellValue(budgetRow.getTaxTotalPrice());
                                row.getCell(3).setCellStyle(numCellStyle);
                            }
                        }
                    }
                }

                if (budget20.size() > 0) {
                    for (int a = 0; a < budget20.size(); a++) {
                        BudgetEstimate budgetRow = budget20.get(a);
                        if ("0002".equals(budgetRow.getCode())) {
                            taxTotalPrice20 = budgetRow.getTaxTotalPrice();
                        }
                        if (budgetRow.getCode().equals(budgetEstimateSum.getCode())) {
                            if (!StringUtils.isEmpty(budgetRow.getTaxTotalPrice())) {
                                row.createCell(4).setCellValue(budgetRow.getTaxTotalPrice());
                                row.getCell(4).setCellStyle(numCellStyle);
                            }
                        }
                    }
                }

                if (budget21.size() > 0) {
                    for (int a = 0; a < budget21.size(); a++) {
                        BudgetEstimate budgetRow = budget21.get(a);
                        if ("0002".equals(budgetRow.getCode())) {
                            taxTotalPrice21 = budgetRow.getTaxTotalPrice();
                        }
                        if (budgetRow.getCode().equals(budgetEstimateSum.getCode())) {
                            if (!StringUtils.isEmpty(budgetRow.getTaxTotalPrice())) {
                                row.createCell(5).setCellValue(budgetRow.getTaxTotalPrice());
                                row.getCell(5).setCellStyle(numCellStyle);
                            }
                        }
                    }
                }

                if (budget22.size() > 0) {
                    for (int a = 0; a < budget22.size(); a++) {
                        BudgetEstimate budgetRow = budget22.get(a);
                        if ("0002".equals(budgetRow.getCode())) {
                            taxTotalPrice22 = budgetRow.getTaxTotalPrice();
                        }
                        if (budgetRow.getCode().equals(budgetEstimateSum.getCode())) {
                            if (!StringUtils.isEmpty(budgetRow.getTaxTotalPrice())) {
                                row.createCell(6).setCellValue(budgetRow.getTaxTotalPrice());
                                row.getCell(6).setCellStyle(numCellStyle);
                            }
                        }
                    }
                }

                if (budget23.size() > 0) {
                    for (int a = 0; a < budget23.size(); a++) {
                        BudgetEstimate budgetRow = budget23.get(a);
                        if ("0002".equals(budgetRow.getCode())) {
                            taxTotalPrice23 = budgetRow.getTaxTotalPrice();
                        }
                        if (budgetRow.getCode().equals(budgetEstimateSum.getCode())) {
                            if (!StringUtils.isEmpty(budgetRow.getTaxTotalPrice())) {
                                row.createCell(7).setCellValue(budgetRow.getTaxTotalPrice());
                                row.getCell(7).setCellStyle(numCellStyle);
                            }
                        }
                    }
                }

                if ("0002".equals(budgetEstimateSum.getCode())) {
                    taxTotalPrice = budgetEstimateSum.getTaxTotalPrice();
                }

                if (budgetEstimateSum.getInnerCode().length() == 2
                        || budgetEstimateSum.getInnerCode().length() == 4) {
                    row.getCell(0).setCellValue(budgetEstimateSum.getOrderNumber());
                    row.getCell(1).setCellValue(budgetEstimateSum.getName());
                    if (!StringUtils.isEmpty(budgetEstimateSum.getTaxTotalPrice())) {
                        row.getCell(2).setCellValue(budgetEstimateSum.getTaxTotalPrice());
                        row.getCell(2).setCellStyle(numCellStyle);
                    }
                    k++;
                }

                if (budgetEstimateSum.getInnerCode().length() == 6
                        && budgetEstimateSum.getInnerCode().charAt(1) == '1') {
                    row.getCell(0).setCellValue(budgetEstimateSum.getOrderNumber());
                    row.getCell(1).setCellValue(budgetEstimateSum.getName());
                    if (!StringUtils.isEmpty(budgetEstimateSum.getTaxTotalPrice())) {
                        row.getCell(2).setCellValue(budgetEstimateSum.getTaxTotalPrice());
                        row.getCell(2).setCellStyle(numCellStyle);
                    }
                    k++;
                }

            }

            Row sum = sheet.createRow(1);
            sum.createCell(1).setCellValue("合计");
            sum.getCell(1).setCellStyle(border);
            sum.createCell(2).setCellValue(taxTotalPrice);
            sum.getCell(2).setCellStyle(border);
            sum.createCell(3).setCellStyle(border);
            sum.getCell(3).setCellValue(taxTotalPrice19);
            sum.createCell(4).setCellStyle(border);
            sum.getCell(4).setCellValue(taxTotalPrice20);
            sum.createCell(5).setCellStyle(border);
            sum.getCell(5).setCellValue(taxTotalPrice21);
            sum.createCell(6).setCellStyle(border);
            sum.getCell(6).setCellValue(taxTotalPrice22);
            sum.createCell(7).setCellStyle(border);
            sum.getCell(7).setCellValue(taxTotalPrice23);

            CellStyle cellStyle = wb.createCellStyle();

            cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直居中

            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            // 边框颜色
            cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
            cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
            cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
            cellStyle.setTopBorderColor(HSSFColor.BLACK.index);

            Row percent = sheet.createRow(k);
            sheet.addMergedRegion(new CellRangeAddress(k, k, 0, 2));
            percent.createCell(0).setCellValue("年度用款占比");
            percent.getCell(0).setCellStyle(cellStyle);
            percent.createCell(3).setCellStyle(border);
            percent.getCell(3).setCellValue(taxTotalPrice19 / taxTotalPrice);
            percent.createCell(4).setCellStyle(border);
            percent.getCell(4).setCellValue(taxTotalPrice20 / taxTotalPrice);
            percent.createCell(5).setCellStyle(border);
            percent.getCell(5).setCellValue(taxTotalPrice21 / taxTotalPrice);
            percent.createCell(6).setCellStyle(border);
            percent.getCell(6).setCellValue(taxTotalPrice22 / taxTotalPrice);
            percent.createCell(7).setCellStyle(border);
            percent.getCell(7).setCellValue(taxTotalPrice23 / taxTotalPrice);

            // 资金对照表
            List<BudgetEstimateVersion> versionList =
                    budgetEstimateVersionService.getVersionByYear(year, projectId);

            List<List<BudgetEstimate>> dataList = new ArrayList<List<BudgetEstimate>>();
            // b为列
            for (int b = 0; b < versionList.size(); b++) {
                BudgetEstimateVersion version = versionList.get(b);
                List<BudgetEstimate> budgetVersion =
                        budgetEstimateService.getBudgetByVersion(version.getId());
                dataList.add(budgetVersion);
            }

            Sheet sheet1 = wb.createSheet(year + "年资金对照表");
            Row header1 = sheet1.createRow(0);
            header1.setHeight((short) 800);

            sheet1.setColumnWidth(0, 10 * 256);
            sheet1.setColumnWidth(1, 35 * 256);
            sheet1.setColumnWidth(2, 20 * 256);
            sheet1.setColumnWidth(3, 20 * 256);
            sheet1.setColumnWidth(4, 20 * 256);
            sheet1.setColumnWidth(5, 20 * 256);

            Font font = wb.createFont();
            font.setBold(true); // 加粗
            font.setFontHeightInPoints((short) 15); // 设置标题字体大小
            cellStyle.setFont(font);

            Row title1 = sheet1.createRow(1);
            title1.createCell(0).setCellValue("序号");
            title1.createCell(1).setCellValue("设备名称");
            title1.createCell(2).setCellValue("含税总价（万元）");

            title1.getCell(0).setCellStyle(border);
            title1.getCell(1).setCellStyle(border);
            title1.getCell(2).setCellStyle(border);

            Row sum1 = sheet1.createRow(2);
            sum1.createCell(1).setCellValue("合计");
            sum1.getCell(1).setCellStyle(border);

            // 资金对照表预算总表数据
            int a = 3;
            for (int i = 0; i < BudgetEstimateSums.size(); i++) {

                BudgetEstimateSum budgetEstimateSum = BudgetEstimateSums.get(i);
                Row row = sheet1.createRow(a);
                row.createCell(0).setCellStyle(border);
                row.createCell(1).setCellStyle(border);
                row.createCell(2).setCellStyle(border);

                for (int b = 0; b < versionList.size(); b++) {
                    BudgetEstimateVersion version = versionList.get(b);
                    title1.createCell(b + 3).setCellValue(version.getVersion());
                    title1.getCell(b + 3).setCellStyle(border);
                    for (int j = 0; j < dataList.size(); j++) {
                        List<BudgetEstimate> budgetEstimates =
                                (List<BudgetEstimate>) dataList.get(j);
                        for (int c = 0; c < budgetEstimates.size(); c++) {
                            BudgetEstimate budgetEstimate = budgetEstimates.get(c);
                            if ("0002".equals(budgetEstimate.getCode())) {
                                sum1.createCell(j + 3)
                                        .setCellValue(budgetEstimate.getTaxTotalPrice());
                                sum1.getCell(j + 3).setCellStyle(border);
                                continue;
                            }

                            if (budgetEstimate.getCode().equals(budgetEstimateSum.getCode())) {
                                if (!StringUtils.isEmpty(budgetEstimate.getTaxTotalPrice())) {
                                    row.createCell(j + 3)
                                            .setCellValue(budgetEstimate.getTaxTotalPrice());
                                    row.getCell(j + 3).setCellStyle(numCellStyle);
                                }
                            }
                        }

                    }
                }

                if ("0002".equals(budgetEstimateSum.getCode())) {
                    sum1.createCell(2).setCellValue(budgetEstimateSum.getTaxTotalPrice());
                    continue;
                }

                if (budgetEstimateSum.getInnerCode().length() == 2
                        || budgetEstimateSum.getInnerCode().length() == 4) {
                    row.getCell(0).setCellValue(budgetEstimateSum.getOrderNumber());
                    row.getCell(1).setCellValue(budgetEstimateSum.getName());
                    if (!StringUtils.isEmpty(budgetEstimateSum.getTaxTotalPrice())) {
                        row.getCell(2).setCellValue(budgetEstimateSum.getTaxTotalPrice());
                        row.getCell(2).setCellStyle(numCellStyle);
                    }
                    a++;
                    continue;
                }

                if (budgetEstimateSum.getInnerCode().length() == 6
                        && budgetEstimateSum.getInnerCode().charAt(1) == '1') {
                    row.getCell(0).setCellValue(budgetEstimateSum.getOrderNumber());
                    row.getCell(1).setCellValue(budgetEstimateSum.getName());
                    if (!StringUtils.isEmpty(budgetEstimateSum.getTaxTotalPrice())) {
                        row.getCell(2).setCellValue(budgetEstimateSum.getTaxTotalPrice());
                        row.getCell(2).setCellStyle(numCellStyle);
                    }
                    a++;
                }
            }

            sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 2 + versionList.size()));// 合并列(起始行，结束行，起始列，结束列)
            Cell headCell = header1.createCell(0);
            headCell.setCellValue(year + "年资金计划对照表");
            headCell.setCellStyle(cellStyle);

			//资金计划对照表（上报）
			List<Plan> planVersionList = budgetEstimateVersionService.getPlanVersionByYear(year, projectId);
			
			Sheet sheet2 = wb.createSheet("资金对照表（上报）");
            Row header2 = sheet2.createRow(0);
            header2.setHeight((short) 800);
            
            Row hang1=sheet2.createRow(1);
            Row hang2=sheet2.createRow(2);
            Row hang3=sheet2.createRow(3);
            Row hang4=sheet2.createRow(4);
            Row hang5=sheet2.createRow(5);
            Row hang6=sheet2.createRow(6);
            Row hang7=sheet2.createRow(7);
            hang1.createCell(0).setCellValue("序号");
            hang1.getCell(0).setCellStyle(border);
            sheet2.addMergedRegion(new CellRangeAddress(1,2,0,0));
            hang3.createCell(0).setCellValue("1");
            hang3.getCell(0).setCellStyle(border);
            hang4.createCell(0).setCellValue("2");
            hang4.getCell(0).setCellStyle(border);
            hang5.createCell(0).setCellValue("3");
            hang5.getCell(0).setCellStyle(border);
            hang6.createCell(0).setCellValue("4");
            hang6.getCell(0).setCellStyle(border);
            hang7.createCell(1).setCellValue("合计");
            hang7.getCell(1).setCellStyle(border);
            
            for (int i = 0; i < planVersionList.size(); i++) {
                List<PlanTask> planTaskList = budgetEstimateVersionService.getTaskByPlanId(planVersionList.get(i).getId());
                
                hang1.createCell(2*i+1).setCellValue(year+"版本"+i);
                hang1.getCell(2*i+1).setCellStyle(border);
                hang2.createCell(2*i+1).setCellValue("建设目标");
                hang2.getCell(2*i+1).setCellStyle(border);
                hang2.createCell(2*i+2).setCellValue("资金数量(万元)");
                hang2.getCell(2*i+2).setCellStyle(border);
                
                for(int j=0;j<planTaskList.size();j++){
                    String groupKey = planTaskList.get(j).getTaskName();
                    if(groupKey.contains("光传输")) {
                        List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getbudgetByGroup(planTaskList.get(j).getcDatetime(),"光传输");
                            hang3.createCell(2*i+1).setCellValue(planTaskList.get(j).getTaskName());
                            hang3.getCell(2*i+1).setCellStyle(border);
                            hang3.createCell(2*i+2).setCellValue(budgetEstimate1.get(0).getTaxTotalPrice());
                            hang3.getCell(2*i+2).setCellStyle(border);
                    }
                    if(groupKey.contains("可编程")) {
                        List<BudgetEstimate> budgetEstimate2 = budgetEstimateVersionService.getbudgetByGroup(planTaskList.get(j).getcDatetime(),"可编程");
                            hang4.createCell(2*i+1).setCellValue(planTaskList.get(j).getTaskName());
                            hang4.getCell(2*i+1).setCellStyle(border);
                            hang4.createCell(2*i+2).setCellValue(budgetEstimate2.get(0).getTaxTotalPrice());
                            hang4.getCell(2*i+2).setCellStyle(border);
                    }
                    if(groupKey.contains("SDN")) {
                        List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getbudgetByGroup(planTaskList.get(j).getcDatetime(),"SDN");
                            hang5.createCell(2*i+1).setCellValue(planTaskList.get(j).getTaskName());
                            hang5.getCell(2*i+1).setCellStyle(border);
                            hang5.createCell(2*i+2).setCellValue(budgetEstimate3.get(0).getTaxTotalPrice());
                            hang5.getCell(2*i+2).setCellStyle(border);
                    }
                    if(groupKey.contains("基础网络")) {
                        List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getbudgetByGroup(planTaskList.get(j).getcDatetime(),"基础网络");
                            hang3.createCell(2*i+1).setCellValue(planTaskList.get(j).getTaskName());
                            hang3.getCell(2*i+1).setCellStyle(border);
                            hang3.createCell(2*i+2).setCellValue(budgetEstimate4.get(0).getTaxTotalPrice());
                            hang3.getCell(2*i+2).setCellStyle(border);
                    }
                    if(groupKey.contains("大数据")) {
                        List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getbudgetByGroup(planTaskList.get(j).getcDatetime(),"光传输");
                            hang6.createCell(2*i+1).setCellValue(planTaskList.get(j).getTaskName());
                            hang6.getCell(2*i+1).setCellStyle(border);
                            hang6.createCell(2*i+2).setCellValue(budgetEstimate5.get(0).getTaxTotalPrice());
                            hang6.getCell(2*i+2).setCellStyle(border);
                    }
                }
                double zjsl= hang3.getCell(2*i+2).getNumericCellValue()+hang4.getCell(2*i+2).getNumericCellValue()+hang5.getCell(2*i+2).getNumericCellValue()+hang6.getCell(2*i+2).getNumericCellValue();
                hang7.createCell(2*i+2).setCellValue(zjsl);
                hang7.getCell(2*i+2).setCellStyle(border);
            }
            if((planVersionList.size()-1)>=0) {
                List<PlanTask> lastPlanTask = budgetEstimateVersionService.getTaskByPlanId(planVersionList.get(planVersionList.size()-1).getId());
                hang1.createCell(2*planVersionList.size()+1).setCellValue(year+"最终版本");
                hang1.getCell(2*planVersionList.size()+1).setCellStyle(border);
                hang2.createCell(2*planVersionList.size()+1).setCellValue("实际完成");
                hang2.getCell(2*planVersionList.size()+1).setCellStyle(border);
                hang2.createCell(2*planVersionList.size()+2).setCellValue("资金数量(万元)");
                hang2.getCell(2*planVersionList.size()+2).setCellStyle(border);
                
                for(int i=0;i<lastPlanTask.size();i++) {
                    String groupKey = lastPlanTask.get(i).getTaskName();
                    if(groupKey.contains("光传输")) {
                        List<BudgetEstimate> budgetEstimate1 = budgetEstimateVersionService.getbudgetByGroup(lastPlanTask.get(i).getcDatetime(),"光传输");
                            hang3.createCell(2*planVersionList.size()+1).setCellValue(lastPlanTask.get(i).getTaskName());
                            hang3.getCell(2*planVersionList.size()+1).setCellStyle(border);
                            hang3.createCell(2*planVersionList.size()+2).setCellValue(budgetEstimate1.get(0).getTaxTotalPrice());
                            hang3.getCell(2*planVersionList.size()+2).setCellStyle(border);
                    }
                    if(groupKey.contains("可编程")) {
                        List<BudgetEstimate> budgetEstimate2 = budgetEstimateVersionService.getbudgetByGroup(lastPlanTask.get(i).getcDatetime(),"可编程");
                            hang4.createCell(2*planVersionList.size()+1).setCellValue(lastPlanTask.get(i).getTaskName());
                            hang4.getCell(2*planVersionList.size()+1).setCellStyle(border);
                            hang4.createCell(2*planVersionList.size()+2).setCellValue(budgetEstimate2.get(0).getTaxTotalPrice());
                            hang4.getCell(2*planVersionList.size()+2).setCellStyle(border);
                    }
                    if(groupKey.contains("SDN")) {
                        List<BudgetEstimate> budgetEstimate3 = budgetEstimateVersionService.getbudgetByGroup(lastPlanTask.get(i).getcDatetime(),"SDN");
                            hang5.createCell(2*planVersionList.size()+1).setCellValue(lastPlanTask.get(i).getTaskName());
                            hang5.getCell(2*planVersionList.size()+1).setCellStyle(border);
                            hang5.createCell(2*planVersionList.size()+2).setCellValue(budgetEstimate3.get(0).getTaxTotalPrice());
                            hang5.getCell(2*planVersionList.size()+2).setCellStyle(border);
                    }
                    if(groupKey.contains("基础网络")) {
                        List<BudgetEstimate> budgetEstimate4 = budgetEstimateVersionService.getbudgetByGroup(lastPlanTask.get(i).getcDatetime(),"基础网络");
                            hang3.createCell(2*planVersionList.size()+1).setCellValue(lastPlanTask.get(i).getTaskName());
                            hang3.getCell(2*planVersionList.size()+1).setCellStyle(border);
                            hang3.createCell(2*planVersionList.size()+2).setCellValue(budgetEstimate4.get(0).getTaxTotalPrice());
                            hang3.getCell(2*planVersionList.size()+2).setCellStyle(border);
                    }
                    if(groupKey.contains("大数据")) {
                        List<BudgetEstimate> budgetEstimate5 = budgetEstimateVersionService.getbudgetByGroup(lastPlanTask.get(i).getcDatetime(),"光传输");
                            hang6.createCell(2*planVersionList.size()+1).setCellValue(lastPlanTask.get(i).getTaskName());
                            hang6.getCell(2*planVersionList.size()+1).setCellStyle(border);
                            hang6.createCell(2*planVersionList.size()+2).setCellValue(budgetEstimate5.get(0).getTaxTotalPrice());
                            hang6.getCell(2*planVersionList.size()+2).setCellStyle(border);
                    }
                }
                double zijin= hang3.getCell(2*planVersionList.size()+2).getNumericCellValue()+hang4.getCell(2*planVersionList.size()+2).getNumericCellValue()+hang5.getCell(2*planVersionList.size()+2).getNumericCellValue()+hang6.getCell(2*planVersionList.size()+2).getNumericCellValue();
                hang7.createCell(2*planVersionList.size()+2).setCellValue(zijin);
                hang7.getCell(2*planVersionList.size()+2).setCellStyle(border);
            }
            sheet2.addMergedRegion(new CellRangeAddress(0, 0, 0, 2*planVersionList.size()+3));// 合并列(起始行，结束行，起始列，结束列)
            Cell headCell1 = header2.createCell(0);
            headCell1.setCellValue(year + "年资金计划对照表");
            headCell1.setCellStyle(cellStyle);
            
            
			Date now = new Date();

            String fileName = "CENI项目资金使用计划"
                    + DateUtils.date2String(now, Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (output != null) {
                output.close();
            }
        }

        return file;
    }
}
