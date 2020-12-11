package project.edge.web.controller.facility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
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
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ExcelUtils;
import garage.origin.common.util.ZipUtils;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.InvolvedProjectGroupEnum;
import project.edge.common.constant.LinkStatusEnum;
import project.edge.common.constant.LinkTypeEnum;
import project.edge.common.constant.ProjectGroupEnum;
import project.edge.common.excel.ExcelExporter;
import project.edge.common.excel.ExcelField;
import project.edge.common.exception.ExcelParseException;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.domain.converter.LinkBeanConverter;
import project.edge.domain.entity.Link;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.view.LinkBean;
import project.edge.domain.view.SegmentBean;
import project.edge.service.facility.LinkService;
import project.edge.service.facility.SiteService;
import project.edge.service.hr.PersonService;
import project.edge.web.apiService.facility.FacilityApiService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 链路管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/facility/link")
public class LinkController extends SingleGridController<Link, LinkBean> {

    private static final Logger logger = LoggerFactory.getLogger(LinkController.class);

    private static final String PID = "P110510";
    
    @Resource
    private FacilityApiService facilityApiService;

    @Resource
    private LinkService linkService;

    @Resource
    private SiteService siteService;
    
    @Resource
    private PersonService personService;

    @Resource
    private PresetTaskProducer presetTaskProducer;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.LINK.value();
    }

    @Override
    protected Service<Link, String> getDataService() {
        return this.linkService;
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected ViewConverter<Link, LinkBean> getViewConverter() {
        return new LinkBeanConverter();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/facility/linkJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/facility/linkHidden.jsp";
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 上传文件
        jsMap.put(ControllerJsMapKeys.OPEN_IMPORT, "LINK.openUploadFormDialog();");
        
        // 导出文件
        jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT, String.format("LINK.exportExcel('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
        // add start by huang 20200412
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, LINK);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));
        
        jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, false, false, LINK);",
                idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));        
        // add end by huang 20200412

        return jsMap;
    }

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {
        // TODO Auto-generated method stub

        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();


        ArrayList<ComboboxOptionBean> dataLinkStatusOptions = new ArrayList<ComboboxOptionBean>();

        for (LinkStatusEnum l : LinkStatusEnum.values()) {
            dataLinkStatusOptions.add(new ComboboxOptionBean(l.value().toString(),
                    messageSource.getMessage(l.resourceName(), null, locale)));
        }

        optionMap.put("dataLinkStatusOptions", dataLinkStatusOptions);

        ArrayList<ComboboxOptionBean> involvedProjectGroupOptions =
                new ArrayList<ComboboxOptionBean>();

        for (InvolvedProjectGroupEnum p : InvolvedProjectGroupEnum.values()) {
            involvedProjectGroupOptions.add(new ComboboxOptionBean(p.value().toString(),
                    messageSource.getMessage(p.resourceName(), null, locale)));
        }

        optionMap.put("involvedProjectGroupOptions", involvedProjectGroupOptions);

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

        return super.main(paramMap, model, userBean, locale);
    }
    
    @RequestMapping("/history")
    @ResponseBody
    public JsonResultBean history(
    		@RequestParam(required = false, defaultValue = "") String targetId,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "999999999") int rows,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(required = false, defaultValue = "asc") String order, Locale locale) {
    	
    	CommonFilter filter = new CommonFilter();
    	filter.addExact("targetId", targetId);
    	filter.addExact("targetType", 2);
		
		return facilityApiService.getHistoryList(filter, page, rows, sort, order, locale);
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
    @ResponseBody
    @SysLogAnnotation(description = "链路管理", action = "新增")
    public JsonResultBean create(@ModelAttribute LinkBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	JsonResultBean jsonResult = super.create(bean, null, userBean, locale);
    	
    	// 添加历史记录
    	if (jsonResult.getStatus() != JsonStatus.ERROR) {
            ViewBean newBean = (ViewBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);
            facilityApiService.addHistoryRecord(newBean.getId(), 2, 0, "添加记录", userBean.getSessionUserId());
    	}
    	
    	return jsonResult;
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
    @SysLogAnnotation(description = "链路管理", action = "更新")
    public JsonResultBean update(@ModelAttribute LinkBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	Link prevLink = linkService.find(bean.getId());
    	LinkBean prevBean = this.getViewConverter().fromEntity(prevLink, messageSource, locale);
    	
    	JsonResultBean jsonResult = super.update(bean, null, userBean, locale);
    	
    	// 添加历史记录
    	if (jsonResult.getStatus() != JsonStatus.ERROR) {
    		LinkBean nextBean = (LinkBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);
            String updateContents = facilityApiService.getUpdateContent(prevBean, nextBean, PID, this.getDataType(), locale);
            facilityApiService.addHistoryRecord(bean.getId(), 2, 1, updateContents, userBean.getSessionUserId());
    	}
    	
    	return jsonResult;
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
    @SysLogAnnotation(description = "链路管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	JsonResultBean jsonResult = super.delete(idsToDelete, null, userBean, locale);
    	
    	// 添加历史记录
    	if (jsonResult.getStatus() != JsonStatus.ERROR) {
    		String[] ids = StringUtils.commaDelimitedListToStringArray(idsToDelete);
        	for (int i = 0; i < ids.length; i++) {
        		Link link = linkService.find(ids[i]);
        		if (link != null) {
        			facilityApiService.addHistoryRecord(link.getId(), 2, 2, "删除记录", userBean.getSessionUserId());
        		}
        	}
    	}
    	
    	return jsonResult;
    }

    @Override
    protected void beforeCreate(Link entity, LinkBean bean, Map<String, Object> paramMap)
            throws Exception {

        if ((entity.getEndA() == null) || (entity.getEndB() == null)) {
            return;
        }

        CommonFilter filter = new CommonFilter().addExact("endA.id", entity.getEndA().getId())
                .addExact("endB.id", entity.getEndB().getId());
        List<Link> delLinks = this.linkService.list(filter, null);
        if ((delLinks != null) && (delLinks.size() > 0)) {
            ArrayList<String> delLinkids = new ArrayList<String>();
            for (Link s : delLinks) {
                delLinkids.add(s.getId());
            }

            if (delLinkids.size() > 0) {

                this.linkService.delete(delLinkids);
            }
        }

    }

    @Override
    protected void postCreate(Link entity, LinkBean bean, Map<String, Object> paramMap,
            Locale locale) throws Exception {

        Date date = entity.getcDatetime();

        // tagStart 此处代码是否不需要
        String linkTypeString = entity.getLinkType();

        String planName = "链路_" + linkTypeString
                + DateUtils.date2String(date, Constants.FILE_DATE_TIME_CSV_FORMAT);

        CommonFilter filter = new CommonFilter();

        if (LinkTypeEnum.PROGRAMMABLE_NETWORK.value().equals(entity.getLinkType())) {
            filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
        } else if (LinkTypeEnum.SDN_NETWORK.value().equals(entity.getLinkType())) {
            filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
        }
        // tagEnd 此处代码是否不需要
        filter.addExact("isSiteTask", OnOffEnum.ON.value());
        // this.presetTaskProducer.generatePlanTask(entity, planName, filter, date,
        // entity.getCreator());
    }
    
    @RequestMapping("/export-excel")
    public void exportExcel(HttpServletResponse response, Locale locale) {
    	
    	try {
    		
    		// 生成需要导出的文件列表（每个站点单独一个）
    		List<File> exportFiles = this.createExportFiles(locale);
    		
    		// 打包压缩到zip文件中
    		String tempFolderPath = this.context.getRealPath("/temp");
            File zipFile = new File(tempFolderPath, UUID.randomUUID().toString() + ".zip");
            
            List<SimpleEntry<String, String>> files = new ArrayList<>();
            Map<String, String> entryNameMap = new HashMap<>();
            for (File exportFile : exportFiles) {
                String path = exportFile.getAbsolutePath();
                files.add(new SimpleEntry<>(path, ""));
                entryNameMap.put(path, exportFile.getName());
            }
            ZipUtils.zip(files, entryNameMap, zipFile.getAbsolutePath(), null);
    		
    		// 下载
            ControllerDownloadUtils.downloadFile(zipFile, response);

            // 删除已压缩的文件
    		for (File exportFile: exportFiles) {
    			FileUtils.deleteQuietly(exportFile);
    		}
    		FileUtils.deleteQuietly(zipFile);
    		
    	} catch (Exception e) {
    		logger.error("Exception while export site xls file", e);
    	}
    }
    
    /**
     * 生成站点中继段对应的Excel文件
     * @return
     */
    private List<File> createExportFiles(Locale locale) {
    	
    	List<File> exportFileList = new ArrayList<>();
    	
    	// 根据ID取得站点列表
    	CommonFilter filter = new CommonFilter();
    	List<Link> linkList = linkService.list(filter, null);
    	
    	// 导出为Excel
    	ExcelExporter<Link> exporter = new ExcelExporter<>();
    	String title = "链路导入模板";
    	String tempFolderPath = this.context.getRealPath("/链路信息导出_") +  DateUtils.date2String(new Date(), Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
    	List<ExcelField> fieldList = this.getExportFieldList(locale);
    	
		File exportFile = exporter.export(linkList, fieldList, tempFolderPath, title);
		exportFileList.add(exportFile);
    	
    	return exportFileList;
    }
    
    /**
     * 取得导出的字段定义列表
     * @return
     */
    private List<ExcelField> getExportFieldList(Locale locale) {
    	
    	List<ExcelField> fieldList = new ArrayList<>();
    	
    	HashMap<Integer, String> dataLinkStatusEnumMap = new HashMap<>();
    	for (LinkStatusEnum enumValue: LinkStatusEnum.values()) {
    		String resourceName = this.messageSource.getMessage(enumValue.resourceName(), null, locale);
    		dataLinkStatusEnumMap.put(enumValue.value(), resourceName);
    	}
    	
    	HashMap<Integer, String> groupEnumMap = new HashMap<>();
    	for (InvolvedProjectGroupEnum enumValue: InvolvedProjectGroupEnum.values()) {
    		String resourceName = this.messageSource.getMessage(enumValue.resourceName(), null, locale);
    		groupEnumMap.put(enumValue.value(), resourceName);
    	}
    	
    	fieldList.add(new ExcelField("主键", "id"));
    	fieldList.add(new ExcelField("链路名称", "linkName"));
    	fieldList.add(new ExcelField("链路A端", "endA.stationName"));
    	fieldList.add(new ExcelField("链路B端", "endB.stationName"));
    	fieldList.add(new ExcelField("链路类型", "linkType"));
    	fieldList.add(new ExcelField("数据链路状态", "dataLinkStatus", dataLinkStatusEnumMap));
    	fieldList.add(new ExcelField("距离", "distance"));
    	fieldList.add(new ExcelField("传输链路状态", "transmitLinkStatus"));
    	fieldList.add(new ExcelField("传输层链路带宽", "transmissionLayerBandwidth"));
    	fieldList.add(new ExcelField("数据层链路带宽", "dataLayerBandwidth"));
    	fieldList.add(new ExcelField("所属项目组", "involvedProjectGroup", groupEnumMap));
    	
    	return fieldList;
    }

    @RequestMapping("/import-excel-file")
    public void validateSourceModelType(HttpServletResponse response, HttpServletRequest request,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();

        // 当浏览器是IE时，如果返回application/json，IE会提示用户保存upload.json文件
        response.setContentType("text/html");

        try {

            if (request instanceof MultipartHttpServletRequest) {

                MultipartFile importFile =
                        ((MultipartHttpServletRequest) request).getFile("importFile");

                String path = this.context.getRealPath("/");

                // mtstar\webapp\webapps\app
                File root = new File(path);
                String randomID = UUID.randomUUID().toString();

                File uploadPath = new File(root.getParentFile().getParentFile().getParent(),
                        "temp" + File.separator + randomID);
                FileUtils.forceMkdir(uploadPath);
                String uploadFilePath = uploadPath.getAbsolutePath() + File.separator + randomID;
                String templateExtension =
                        FilenameUtils.getExtension(importFile.getOriginalFilename());
                String templateFilePath = uploadFilePath + Constants.DOT + templateExtension;
                if (templateFilePath != null && !importFile.isEmpty()) {
                    File tempFile = new File(templateFilePath);

                    FileUtils.writeByteArrayToFile(new File(templateFilePath),
                            importFile.getBytes());

                    // 解析excel文件，xls/xlsx需要分开处理
                    List<LinkBean> beans = this.parseExcelFileToLinkBeans(tempFile, locale);

                    if (!beans.isEmpty()) {
                        LinkBeanConverter converter = new LinkBeanConverter();

                        Date now = new Date();

                        for (LinkBean bean : beans) {
                            Link link = converter.toEntity(bean, null, userBean, now);

                            CommonFilter filter = null;

                            if ((link.getEndA() == null) || (link.getEndB() == null)) {
                                continue;
                            }

                            filter = new CommonFilter().addExact("endA.id", link.getEndA().getId())
                                    .addExact("endB.id", link.getEndB().getId());
                            List<Link> delLinks = this.linkService.list(filter, null);
                            if ((delLinks != null) && (delLinks.size() > 0)) {
                                ArrayList<String> delLinkids = new ArrayList<String>();
                                for (Link s : delLinks) {
                                    delLinkids.add(s.getId());
                                }

                                if (delLinkids.size() > 0) {

                                    this.linkService.delete(delLinkids);
                                }
                            }

                            filter = new CommonFilter();

                            // tagStart 此处代码是否不需要
                            String linkTypeString = link.getLinkType();

                            String planName = "链路_" + linkTypeString + DateUtils.date2String(now,
                                    Constants.FILE_DATE_TIME_CSV_FORMAT);

                            if (LinkTypeEnum.PROGRAMMABLE_NETWORK.value()
                                    .equals(link.getLinkType())) {
                                filter.addExact("projectGroup",
                                        ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
                            } else if (LinkTypeEnum.SDN_NETWORK.value()
                                    .equals(link.getLinkType())) {
                                filter.addExact("projectGroup",
                                        ProjectGroupEnum.SDN_NETWORK.value());
                            }
                            // tagEnd 此处代码是否不需要
                            filter.addExact("isSiteTask", OnOffEnum.ON.value());

                            this.linkService.create(link);
                            // this.presetTaskProducer.generatePlanTask(link, planName, filter, now,
                            // link.getCreator());
                        }
                        
                        // 准备JSON结果
                        jsonResult.setStatus(JsonStatus.OK);
                        jsonResult.setMessage(
                                this.messageSource.getMessage("message.info.import.ok", null, locale));
                        jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beans);
                    }else {
                        // 准备JSON结果
                        jsonResult.setStatus(JsonStatus.ERROR);
                        jsonResult.setMessage(
                                this.messageSource.getMessage("message.info.error.no.data", null, locale));
                        //jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beans);
                    }

                }
            }
        } 
        catch (ExcelParseException e) {
        	logger.warn("Excel数据解析错误！", e);
        	jsonResult.setStatus(JsonStatus.OK);
        	jsonResult.setMessage("ParseError");
        	jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, e.getDetails());
        }
        catch (Exception e) {
            logger.error("Exception while import excel file.", e);
            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        try {
            String result = new ObjectMapper().writeValueAsString(jsonResult);
            response.getWriter().write("<textarea>" + result + "</textarea>");
        } catch (Exception e) {
            logger.error("Exception while converting object to json string.", e);
        }
    }

    private List<LinkBean> parseExcelFileToLinkBeans(File tempFile, Locale locale)
            throws FileNotFoundException, IOException, ExcelParseException {

        List<LinkBean> beans = new ArrayList<>();

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

                ExcelParseException parseException = new ExcelParseException();
                for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                    // 跳过第一二三行
                    if (i < 2) {
                        continue;
                    }
                    
                    try {
                    	Row row = sheet.getRow(i);
                        LinkBean bean = new LinkBean();

                        // TODO
                        // bean.setId(ExcelUtils.getCellValue(row.getCell(0)));
                        bean.setId(UUID.randomUUID().toString());

                        // bean.setEndAText(ExcelUtils.getCellValue(row.getCell(1)));
                        // bean.setEndBText(ExcelUtils.getCellValue(row.getCell(2)));
                        bean.setLinkName(ExcelUtils.getCellValue(row.getCell(1)));
                        if (StringUtils.isEmpty(bean.getLinkName())) {
                        	throw new ExcelParseException.Detail(1, i, "链路名称未填写！");
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(2)))) {
                            CommonFilter filter = new CommonFilter();
                            filter.addExact("stationName", ExcelUtils.getCellValue(row.getCell(2)));

                            List<Site> endA = this.siteService.list(filter, null);
                            if ((endA != null) && (endA.size() > 0)) {
                                bean.setEndA_(endA.get(0).getId());
                                bean.setEndAText(endA.get(0).getStationName());
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(3)))) {
                            CommonFilter filter = new CommonFilter();
                            filter.addExact("stationName", ExcelUtils.getCellValue(row.getCell(3)));
                            List<Site> endB = this.siteService.list(filter, null);
                            if ((endB != null) && (endB.size() > 0)) {
                                bean.setEndB_(endB.get(0).getId());
                                bean.setEndBText(endB.get(0).getStationName());
                            }
                        }

                        if (StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(4)))) {
                            continue;
                        }

                        bean.setLinkType(ExcelUtils.getCellValue(row.getCell(4)));

                        for (LinkStatusEnum l : LinkStatusEnum.values()) {
                            if (ExcelUtils.getCellValue(row.getCell(5)).equals(
                                    this.messageSource.getMessage(l.resourceName(), null, locale))) {
                                bean.setDataLinkStatusText(ExcelUtils.getCellValue(row.getCell(1)));
                                bean.setDataLinkStatus(l.value());
                            }
                        }

                        bean.setDistance(ExcelUtils.getCellValue(row.getCell(6)));
                        // if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(6)))) {
                        // String value = ExcelUtils.getCellValue(row.getCell(6));
                        // if (value.endsWith("KM")) {
                        // bean.setDistance(
                        // Double.valueOf(value.substring(0, value.length() - 2)));
                        // }
                        // }

                        bean.setTransmitLinkStatus(ExcelUtils.getCellValue(row.getCell(7)));

                        bean.setTransmissionLayerBandwidth(ExcelUtils.getCellValue(row.getCell(8)));
                        // if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(8)))) {
                        // bean.setTransmissionLayerBandwidth(
                        // Double.valueOf(ExcelUtils.getCellValue(row.getCell(8))));
                        // }
                        bean.setDataLayerBandwidth(ExcelUtils.getCellValue(row.getCell(9)));
                        // if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(9)))) {
                        // bean.setDataLayerBandwidth(
                        // Double.valueOf(ExcelUtils.getCellValue(row.getCell(9))));
                        // }
                        // bean.set //所属项目组？？
                        for (InvolvedProjectGroupEnum l : InvolvedProjectGroupEnum.values()) {
                            if (ExcelUtils.getCellValue(row.getCell(10)).equals(
                                    this.messageSource.getMessage(l.resourceName(), null, locale))) {
                                bean.setInvolvedProjectGroupText(
                                        ExcelUtils.getCellValue(row.getCell(1)));
                                bean.setInvolvedProjectGroup(l.value());
                            }
                        }
                        
                        // 负责人
                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(11)))) {
                            CommonFilter f = new CommonFilter();
                            f.addExact("personName", ExcelUtils.getCellValue(row.getCell(11)));
                            List<Person> pList = this.personService.list(f, null);
                            if ((pList != null) && (pList.size() > 0)) {
                                bean.setPersonInCharge_(pList.get(0).getId());
                                bean.setPersonInChargeText(pList.get(0).getPersonName());
                            }
                        }

                        beans.add(bean);
                    	
                    } catch (ExcelParseException.Detail e) {
                    	parseException.add(e);
                    }
                }
                
                if (parseException.getDetails().size() > 0) {
                	throw parseException;
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
}
