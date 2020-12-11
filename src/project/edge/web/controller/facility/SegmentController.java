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
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FacilityStatusEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.ProjectGroupEnum;
import project.edge.common.constant.SegmentTypeEnum;
import project.edge.common.excel.ExcelExporter;
import project.edge.common.excel.ExcelField;
import project.edge.common.exception.ExcelParseException;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.domain.converter.SegmentBeanConverter;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.SegmentSystemName;
import project.edge.domain.entity.Site;
import project.edge.domain.view.SegmentBean;
import project.edge.service.facility.SegmentService;
import project.edge.service.facility.SegmentSystemNameService;
import project.edge.service.facility.SiteService;
import project.edge.service.hr.PersonService;
import project.edge.service.schedule.PlanService;
import project.edge.service.system.DataOptionService;
import project.edge.web.apiService.facility.FacilityApiService;
import project.edge.web.apiService.plan.PlanApiService;
import project.edge.web.controller.common.SingleGridController;

/**
 * 中继段管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/facility/segment")
public class SegmentController extends SingleGridController<Segment, SegmentBean> {

    private static final Logger logger = LoggerFactory.getLogger(SegmentController.class);

    private static final String PID = "P110505";
    
    @Resource
    private FacilityApiService facilityApiService;

    @Resource
    private SegmentService segmentService;

    @Resource
    private SegmentSystemNameService segmentSystemNameService;
    
    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private PresetTaskProducer presetTaskProducer;

    @Resource
    private SiteService siteService;

    @Resource
    private PlanApiService planApiService;
    
    @Resource
    private PersonService personService;

    @Resource
    private PlanService planService;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.SEGMENT.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<Segment, String> getDataService() {
        return this.segmentService;
    }

    @Override
    protected ViewConverter<Segment, SegmentBean> getViewConverter() {
        return new SegmentBeanConverter();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/facility/segmentJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/facility/segmentHidden.jsp";
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);
        
     // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
                String.format("CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, SEGMENT);",
                        idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.FIND),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 上传文件
        jsMap.put(ControllerJsMapKeys.OPEN_IMPORT, "SEGMENT.openUploadFormDialog();");
        
        // 导出文件
        jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT, String.format("SEGMENT.exportExcel('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // add start by huang 20200412
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, SEGMENT);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, false, false, SEGMENT);",
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

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        List<ComboboxOptionBean> segmentTypeOptions = new ArrayList<>();
        for (SegmentTypeEnum s : SegmentTypeEnum.values()) {
            segmentTypeOptions.add(new ComboboxOptionBean(s.value().toString(),
                    this.messageSource.getMessage(s.resourceName(), null, locale)));
        }
        optionMap.put("segmentTypeOptions", segmentTypeOptions);

        List<ComboboxOptionBean> fibreTypeOptions = new ArrayList<>();
        CommonFilter f = new CommonFilter();
        f.addExact("dataType", DataTypeEnum.FIBRE_TYPE.value());
        List<DataOption> fibreTypeList = this.dataOptionService.list(f, null);
        if (fibreTypeList != null) {
            for (DataOption d : fibreTypeList) {
                fibreTypeOptions.add(new ComboboxOptionBean(d.getId(), d.getOptionName()));
            }
        }
        optionMap.put("fibreTypeOptions", fibreTypeOptions);
        
        List<ComboboxOptionBean> systemNameOptions = new ArrayList<>();
        List<SegmentSystemName> systemNameList = this.segmentSystemNameService.list(null, null);
        for (SegmentSystemName systemName: systemNameList) {
        	systemNameOptions.add(new ComboboxOptionBean(systemName.getId(), systemName.getName()));
        }
        optionMap.put("segmentSystemNameOptions", systemNameOptions);

        List<ComboboxOptionBean> segmentStatusTypeOptions = new ArrayList<>();
        for (FacilityStatusEnum s : FacilityStatusEnum.values()) {
            segmentStatusTypeOptions.add(new ComboboxOptionBean(s.value().toString(),
                    this.messageSource.getMessage(s.resourceName(), null, locale)));
        }
        optionMap.put("segmentStatusTypeOptions", segmentStatusTypeOptions);

        List<ComboboxOptionBean> manualSegmentStatusTypeOptions = new ArrayList<>();
        for (FacilityStatusEnum m : FacilityStatusEnum.values()) {
            manualSegmentStatusTypeOptions.add(new ComboboxOptionBean(m.value().toString(),
                    this.messageSource.getMessage(m.resourceName(), null, locale)));
        }
        optionMap.put("manualSegmentStatusTypeOptions", manualSegmentStatusTypeOptions);

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
     * 取得历史记录
     * @param commonFilterJson
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @param locale
     * @return
     */
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
    	filter.addExact("targetType", 1);
		
		return facilityApiService.getHistoryList(filter, page, rows, sort, order, locale);
    }
    
    @RequestMapping("/get-plan")
    @ResponseBody
    public JsonResultBean getTask(
    		@RequestParam(required = false, defaultValue = "") String segmentId) {
    	
    	CommonFilter filter = new CommonFilter().addExact("segment.id", segmentId);
    	List<Plan> planList = this.planService.list(filter, null);
    	
    	String planId = "";
    	if (planList.size() > 0) {
    		planId = planList.get(0).getId();
    	}
    	
    	Map<String, Object> dataMap = new HashMap<>();
    	dataMap.put("planId", planId);
    	
    	JsonResultBean jsonResult = new JsonResultBean();
    	jsonResult.setStatus(JsonStatus.OK);
    	jsonResult.setDataMap(dataMap);
    	
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
    @SysLogAnnotation(description = "中继段管理", action = "新增")
    public JsonResultBean create(@ModelAttribute SegmentBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	JsonResultBean jsonResult = super.create(bean, null, userBean, locale);
    	
    	// 添加历史记录
    	if (jsonResult.getStatus() != JsonStatus.ERROR) {
            ViewBean newBean = (ViewBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);
            facilityApiService.addHistoryRecord(newBean.getId(), 1, 0, "添加记录", userBean.getSessionUserId());
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
    @SysLogAnnotation(description = "中继段管理", action = "更新")
    public JsonResultBean update(@ModelAttribute SegmentBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	Segment prevSegment = segmentService.find(bean.getId());
    	SegmentBean prevBean = this.getViewConverter().fromEntity(prevSegment, messageSource, locale);
    	
    	JsonResultBean jsonResult =  super.update(bean, null, userBean, locale);
    	
    	// 添加历史记录
    	if (jsonResult.getStatus() != JsonStatus.ERROR) {
    		SegmentBean nextBean = (SegmentBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);
            String updateContents = facilityApiService.getUpdateContent(prevBean, nextBean, PID, this.getDataType(), locale);
            facilityApiService.addHistoryRecord(bean.getId(), 1, 1, updateContents, userBean.getSessionUserId());
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
    @SysLogAnnotation(description = "中继段管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	JsonResultBean jsonResult = super.delete(idsToDelete, null, userBean, locale);
    	
    	// 添加历史记录
    	if (jsonResult.getStatus() != JsonStatus.ERROR) {
    		String[] ids = StringUtils.commaDelimitedListToStringArray(idsToDelete);
        	for (int i = 0; i < ids.length; i++) {
        		Segment segment = segmentService.find(ids[i]);
        		if (segment != null) {
        			facilityApiService.addHistoryRecord(segment.getId(), 1, 2, "删除记录", userBean.getSessionUserId());
        		}
        	}
    	}
    	
    	return jsonResult;
    }

    @Override
    protected void beforeCreate(Segment entity, SegmentBean bean, Map<String, Object> paramMap)
            throws Exception {

        if ((entity.getEndA() == null) || (entity.getEndB() == null)) {
            return;
        }

        CommonFilter filter = new CommonFilter().addExact("endA.id", entity.getEndA().getId())
                .addExact("endB.id", entity.getEndB().getId());
        List<Segment> delSegments = this.segmentService.list(filter, null);
        if ((delSegments != null) && (delSegments.size() > 0)) {
            ArrayList<String> delSegmentids = new ArrayList<String>();
            for (Segment s : delSegments) {
                delSegmentids.add(s.getId());
            }

            if (delSegmentids.size() > 0) {
                filter = new CommonFilter().addWithin("se.id", delSegmentids);
                List<Plan> delPlans = this.planService.list(filter, null);

                if ((delPlans != null) && (delPlans.size() > 0)) {

                    ArrayList<String> delPlanids = new ArrayList<String>();
                    for (Plan p : delPlans) {
                        delPlanids.add(p.getId());
                    }

                    this.planService.delete(delPlanids);
                }

                this.segmentService.delete(delSegmentids);
            }
        }

    }


    @Override
    protected void postCreate(Segment entity, SegmentBean bean, Map<String, Object> paramMap,
            Locale locale) {

        Date now = entity.getcDatetime();

        if ((entity.getEndA() == null) || (entity.getEndB() == null)) {
            return;
        }

//        String planName =
//                "中继段_" + entity.getEndA().getStationName() + "_" + entity.getEndB().getStationName()
//                        + "_" + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
      String planName = entity.getEndA().getStationName() + "_" + entity.getEndB().getStationName() + "_中继段建设计划";

        CommonFilter filter = new CommonFilter();
        filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
        filter.addExact("isSiteTask", OnOffEnum.OFF.value());

        this.presetTaskProducer.generatePlanTask(entity, planName, filter, now,
                entity.getCreator(), 4);
    }

    @Override
    protected void postUpdate(Segment segment, Segment oldSegment, SegmentBean bean,
            Map<String, Object> paramMap) throws Exception {

        Date now = segment.getcDatetime();

        if ((oldSegment.getEndA() == null) || (oldSegment.getEndB() == null)) {
            return;
        }

        if ((segment.getEndA() == null) || (segment.getEndB() == null)) {
            return;
        }

        CommonFilter filter = new CommonFilter();

//        String planName = "中继段_" + oldSegment.getEndA().getStationName() + "_"
//                + oldSegment.getEndB().getStationName() + "_";
          String planName = oldSegment.getEndA().getStationName() + "_" + oldSegment.getEndB().getStationName() + "_中继段建设计划";

        filter.addAnywhere("planName", planName);
        filter.addExact("se.id", oldSegment.getId());
        List<Plan> delPlans = this.planService.list(filter, null);

        if ((delPlans != null) && (delPlans.size() > 0)) {

            ArrayList<String> delPlanids = new ArrayList<String>();
            for (Plan p : delPlans) {
                delPlanids.add(p.getId());
            }

            this.planService.delete(delPlanids);
        }

        filter = new CommonFilter();
        filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
        filter.addExact("isSiteTask", OnOffEnum.OFF.value());

//        planName = "中继段_" + segment.getEndA().getStationName() + "_"
//                + segment.getEndB().getStationName() + "_"
//                + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
          planName = segment.getEndA().getStationName() + "_" + segment.getEndB().getStationName() + "_中继段建设计划";

          // 更新时，不再重新生成计划任务（在界面的【重新生成】功能中实现）
//        this.presetTaskProducer.generatePlanTask(segment, planName, filter, now,
//                segment.getCreator());
    }
    
    @RequestMapping("/regenerate")
    @ResponseBody
    public JsonResultBean regenerate(@RequestParam String id, @RequestParam String groupIds,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	try {
    		
    		String[] groupIdArray = groupIds.split(",");
        	this.regeneratePlan(groupIdArray, id, locale);
    		
    	} catch (Exception e) {
            this.getLogger().error("Exception while regenerating plan for site, id= " + id, e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }
    	
    	return jsonResult;
    }
    
    @RequestMapping("/regenerateAll")
    @ResponseBody
    public JsonResultBean regenerateAll(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	List<Segment> segmentList = segmentService.list(null, null);
    	for (Segment segment: segmentList) {
    		SegmentBean bean = this.getViewConverter().fromEntity(segment, messageSource, locale);
    		this.postCreate(segment, bean, null, locale);
    	}
    	
    	JsonResultBean jsonResult = new JsonResultBean();
    	jsonResult.setStatus(JsonStatus.OK);
    	return jsonResult;
    }
    
    
    /**
     * 重新生成中继段的建设计划
     * @param segmentId
     * @param locale
     * @throws Exception 
     */
    private void regeneratePlan(String[] groupIds, String segmentId, Locale locale) throws Exception {
    	
    	Segment segment = segmentService.find(segmentId);
    	
		// 查询对应的建设计划
		CommonFilter filter = new CommonFilter();
        filter.addExact("segment.id", segmentId);
		List<Plan> planList = planService.list(filter, null);
		System.out.println("重新编制建设计划，当前计划数=" + planList.size());
		
		for (Plan plan: planList) {
			planApiService.removePlan(plan.getId());
		}
		
		// 重新创建当前计划
		this.postCreate(segment, this.getViewConverter().fromEntity(segment, messageSource, locale), null, locale);
    }
    
    /**
     * 查询该站点是否有已审核通过的建设计划
     * @param id
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/flow-status")
    @ResponseBody
    public JsonResultBean checkFlowStatus(@RequestParam String id,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	JsonResultBean jsonResult = new JsonResultBean();
    	
    	try {

    		// 查询对应中继段的建设计划列表
    		CommonFilter filter = new CommonFilter();
            filter.addExact("segment.id", id);
    		List<Plan> planList = planService.list(filter, null);
    		
    		// 判断是否有
    		boolean hasAuthorizedPlan = false;
    		for (Plan plan : planList) {
    			if (plan.getFlowStatus() == FlowStatusEnum.REVIEW_PASSED.value()) {
    				hasAuthorizedPlan = true;
    				break;
    			}
    		}
    		
    		Map<String, Integer> resultMap = new HashMap<String, Integer>();
    		resultMap.put("is_authorized", hasAuthorizedPlan ? 1 : 0);
    		jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, resultMap);
    		
    	} catch (Exception e) {
            this.getLogger().error("Exception while checking flow-status, id= " + id, e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
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
    	List<Segment> segmentList = segmentService.list(filter, null);
    	
    	// 导出为Excel
    	ExcelExporter<Segment> exporter = new ExcelExporter<>();
    	String title = "中继段导入模板";
    	String tempFolderPath = this.context.getRealPath("/中继段信息导出_") +  DateUtils.date2String(new Date(), Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
    	List<ExcelField> fieldList = this.getExportFieldList(locale);
    	
		File exportFile = exporter.export(segmentList, fieldList, tempFolderPath, title);
		exportFileList.add(exportFile);
    	
    	return exportFileList;
    }
    
    /**
     * 取得导出的字段定义列表
     * @return
     */
    private List<ExcelField> getExportFieldList(Locale locale) {
    	
    	List<ExcelField> fieldList = new ArrayList<>();
    	
    	HashMap<Integer, String> segmentTypeEnumMap = new HashMap<>();
    	for (SegmentTypeEnum enumValue: SegmentTypeEnum.values()) {
    		String resourceName = this.messageSource.getMessage(enumValue.resourceName(), null, locale);
    		segmentTypeEnumMap.put(enumValue.value(), resourceName);
    	}
    	
    	fieldList.add(new ExcelField("主键", "id"));
    	fieldList.add(new ExcelField("中继段类型", "segmentType", segmentTypeEnumMap));
    	fieldList.add(new ExcelField("中继段A端", "endA.stationName"));
    	fieldList.add(new ExcelField("中继段B端", "endB.stationName"));
    	fieldList.add(new ExcelField("所属复用段", "reuseSegment"));
    	fieldList.add(new ExcelField("所属系统名称", "systemName"));
    	fieldList.add(new ExcelField("光纤类型", "fibreType.optionName"));
    	fieldList.add(new ExcelField("中继段长度（Km）", "segmentLength"));
    	fieldList.add(new ExcelField("中继段衰耗", "segmentAttenuation"));
    	fieldList.add(new ExcelField("成端衰耗", "formedAttenuation"));
    	fieldList.add(new ExcelField("维护余量", "maintainanceRemain"));
    	fieldList.add(new ExcelField("设计衰耗", "designAttenuation"));
    	fieldList.add(new ExcelField("初验日期", "initMaterialArchieveDate"));
    	fieldList.add(new ExcelField("负责人", "personInCharge.personName"));
    	
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
                    List<SegmentBean> beans = this.parseExcelFileToSegmentBeans(tempFile, locale);

                    if (!beans.isEmpty()) {
                        SegmentBeanConverter converter = new SegmentBeanConverter();

                        Date now = new Date();

                        // String planName = "中继段_"
                        // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);

                        String planName;
                        CommonFilter filter = null;

                        for (SegmentBean bean : beans) {
                            Segment segment = converter.toEntity(bean, null, userBean, now);

                            if ((segment.getEndA() == null) || (segment.getEndB() == null)) {
                                continue;
                            }

                            filter = new CommonFilter()
                                    .addExact("endA.id", segment.getEndA().getId())
                                    .addExact("endB.id", segment.getEndB().getId());
                            
                         // 注释说明：导入功能只导入不存在的站点且不生成建设计划
                            
//                            List<Segment> delSegments = this.segmentService.list(filter, null);
//                            if ((delSegments != null) && (delSegments.size() > 0)) {
//                                ArrayList<String> delSegmentids = new ArrayList<String>();
//                                for (Segment s : delSegments) {
//                                    delSegmentids.add(s.getId());
//                                }
//
//                                if (delSegmentids.size() > 0) {
//                                    filter = new CommonFilter().addWithin("se.id", delSegmentids);
//                                    List<Plan> delPlans = this.planService.list(filter, null);
//
//                                    if ((delPlans != null) && (delPlans.size() > 0)) {
//
//                                        ArrayList<String> delPlanids = new ArrayList<String>();
//                                        for (Plan p : delPlans) {
//                                            delPlanids.add(p.getId());
//                                        }
//
//                                        this.planService.delete(delPlanids);
//                                    }
//
//                                    this.segmentService.delete(delSegmentids);
//                                }
//                            }

                            List<Segment> existSegmentList = this.segmentService.list(filter, null);
                            if (existSegmentList.size() == 0) {
                            	this.segmentService.create(segment);
                            }

//                            filter = new CommonFilter();
//                            filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
//                            filter.addExact("isSiteTask", OnOffEnum.OFF.value());
//
////                            planName = "中继段_" + bean.getEndAText() + "_" + bean.getEndBText() + "_"
////                                    + DateUtils.date2String(now,
////                                            Constants.FILE_DATE_TIME_CSV_FORMAT);
//                            planName = "中继段_" + bean.getEndAText() + "_" + bean.getEndBText();
//
//                            this.presetTaskProducer.generatePlanTask(segment, planName, filter, now,
//                                    segment.getCreator());
                        }

                        // 准备JSON结果
                        jsonResult.setStatus(JsonStatus.OK);
                        jsonResult.setMessage(this.messageSource
                                .getMessage("message.info.import.ok", null, locale));
                        jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beans);
                    } else {
                        // 准备JSON结果
                        jsonResult.setStatus(JsonStatus.ERROR);
                        jsonResult.setMessage(this.messageSource
                                .getMessage("message.info.error.no.data", null, locale));
                        // jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, beans);
                    }

                }
            }
        }
        // Excel解析错误，正常返回并显示错误详情列表
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

    private List<SegmentBean> parseExcelFileToSegmentBeans(File tempFile, Locale locale)
            throws FileNotFoundException, IOException, ExcelParseException {

        List<SegmentBean> beans = new ArrayList<>();

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
                        SegmentBean bean = new SegmentBean();

                        // if (StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(0)))) {
                        // continue;
                        // }

                        // TODO
                        // bean.setId(ExcelUtils.getCellValue(row.getCell(0)));
                        bean.setId(UUID.randomUUID().toString());

                        for (SegmentTypeEnum s : SegmentTypeEnum.values()) {
                            if (ExcelUtils.getCellValue(row.getCell(1)).equals(
                                    this.messageSource.getMessage(s.resourceName(), null, locale))) {
                                bean.setSegmentTypeText(ExcelUtils.getCellValue(row.getCell(1)));
                                bean.setSegmentType(s.value());
                                break;
                            }
                        }
                        if (StringUtils.isEmpty(bean.getSegmentTypeText())) {
                        	throw new ExcelParseException.Detail(1, i, "中继段类型未填！");
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(2)))) {
                            CommonFilter filter = new CommonFilter();
                            // filter.addExact("stationName", ExcelUtils.getCellValue(row.getCell(2)));
                            filter.addAnywhere("stationName", ExcelUtils.getCellValue(row.getCell(2)));

                            List<Site> endA = this.siteService.list(filter, null);
                            if ((endA != null) && (endA.size() > 0)) {
                                bean.setEndA_(endA.get(0).getId());
                                bean.setEndAText(endA.get(0).getStationName());
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(3)))) {
                            CommonFilter filter = new CommonFilter();
                            // filter.addExact("stationName", ExcelUtils.getCellValue(row.getCell(3)));
                            filter.addAnywhere("stationName", ExcelUtils.getCellValue(row.getCell(3)));

                            List<Site> endB = this.siteService.list(filter, null);
                            if ((endB != null) && (endB.size() > 0)) {
                                bean.setEndB_(endB.get(0).getId());
                                bean.setEndBText(endB.get(0).getStationName());
                            }
                        }

                        bean.setReuseSegment(ExcelUtils.getCellValue(row.getCell(4)));
                        bean.setSystemName(ExcelUtils.getCellValue(row.getCell(5)));

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(6)))) {
                            CommonFilter f = new CommonFilter();
                            f.addExact("optionName", ExcelUtils.getCellValue(row.getCell(6)));
                            List<DataOption> fibreTypeList = this.dataOptionService.list(f, null);
                            if ((fibreTypeList != null) && (fibreTypeList.size() > 0)) {
                                bean.setFibreTypeText(fibreTypeList.get(0).getOptionName());
                                bean.setFibreType_(fibreTypeList.get(0).getId());
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(7)))) {
                        	try {
                        		bean.setSegmentLength(Double.valueOf(ExcelUtils.getCellValue(row.getCell(7))));
                        	} catch (Exception e) {
                        		throw new ExcelParseException.Detail(7, i, "中继段长度应为数字！");
                        	}
                            
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(8)))) {
                        	try {
                        		bean.setSegmentAttenuation( Double.valueOf(ExcelUtils.getCellValue(row.getCell(8))));
                        	} catch (Exception e) {
                        		throw new ExcelParseException.Detail(8, i, "中继段衰耗应为数字！");
                        	}
                            
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(9)))) {
                        	try {
                        		bean.setFormedAttenuation(Double.valueOf(ExcelUtils.getCellValue(row.getCell(9))));
                        	} catch (Exception e) {
                        		throw new ExcelParseException.Detail(9, i, "成端衰耗应为数字！");
                        	}
                            
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(10)))) {
                        	try {
                        		bean.setMaintainanceRemain(Double.valueOf(ExcelUtils.getCellValue(row.getCell(10))));
                        	} catch (Exception e) {
                        		throw new ExcelParseException.Detail(9, i, "维护余量应为数字！");
                        	}
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(11)))) {
                        	try {
                        		bean.setDesignAttenuation(Double.valueOf(ExcelUtils.getCellValue(row.getCell(11))));
                        	} catch (Exception e) {
                        		throw new ExcelParseException.Detail(9, i, "设计衰耗应为数字！");
                        	}
                        }
                        
                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(12)))) {
                        	bean.setInitMaterialArchieveDate(ExcelUtils.getCellValue(row.getCell(12)));
                        } else {
                        	throw new ExcelParseException.Detail(12, i, "初验日期未填！");
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(13)))) {
                            CommonFilter f = new CommonFilter();
                            f.addExact("personName", ExcelUtils.getCellValue(row.getCell(13)));
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
