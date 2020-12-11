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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
import garage.origin.common.constant.FieldValueType;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.common.util.ExcelUtils;
import garage.origin.common.util.ZipUtils;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.business.PageCtrlDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FieldBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.ViewBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.SysLogAnnotation;
import project.edge.common.constant.BasicNetworkTransmitStationTypeEnum;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.ControllerModelKeys;
import project.edge.common.constant.ControllerUrlMapKeys;
import project.edge.common.constant.DataNetworkNodeTypeEnum;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FacilityStatusEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.common.constant.ProgrammableNetworkTransmitStationTypeEnum;
import project.edge.common.constant.ProjectGroupEnum;
import project.edge.common.constant.SdnNetworkTransmitStationTypeEnum;
import project.edge.common.constant.SiteTypeEnum;
import project.edge.common.constant.StationDeviceTypeEnum;
import project.edge.common.excel.ExcelExporter;
import project.edge.common.excel.ExcelField;
import project.edge.common.exception.ExcelParseException;
import project.edge.common.util.ControllerDownloadUtils;
import project.edge.domain.converter.FacilityHistoryBeanConverter;
import project.edge.domain.converter.MapLayerListBeanConverter;
import project.edge.domain.converter.MapSitePieChartListBeanConvert;
import project.edge.domain.converter.SiteBeanConverter;
import project.edge.domain.entity.City;
import project.edge.domain.entity.DataOption;
import project.edge.domain.entity.FacilityHistory;
import project.edge.domain.entity.Link;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanTask;
import project.edge.domain.entity.Project;
import project.edge.domain.entity.Province;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.ArchiveBean;
import project.edge.domain.view.MapSiteInfoBean;
import project.edge.domain.view.MapSitePieChartInfoBean;
import project.edge.domain.view.SiteBean;
import project.edge.service.facility.FacilityHistoryService;
import project.edge.service.facility.LinkService;
import project.edge.service.facility.SegmentService;
import project.edge.service.facility.SiteService;
import project.edge.service.hr.PersonService;
import project.edge.service.project.ProjectService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanProgressService;
import project.edge.service.schedule.PlanService;
import project.edge.service.schedule.PlanTaskService;
import project.edge.service.system.CityService;
import project.edge.service.system.DataOptionService;
import project.edge.service.system.ProvinceService;
import project.edge.web.apiService.facility.FacilityApiService;
import project.edge.web.apiService.plan.PlanApiService;
import project.edge.web.controller.common.SingleGridUploadController;

/**
 * 站点管理画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/facility/site")
public class SiteController extends SingleGridUploadController<Site, SiteBean> {

    private static final Logger logger = LoggerFactory.getLogger(SiteController.class);

    private static final String PID = "P110501";

    private static final String MODEL_KEY_DBL_CLICK_ROW_HANDLER = "dblClickRowHandler";

    private static final String MODEL_KEY_SITE_FIELDS = "siteFields";

    private static final String ID_MAP_KEY_SITE_DIALOG = "edit-site-form-dialog";

    private static final String JS_MAP_KEY_EDIT_SITE_SUBMIT = "edit-site-submit";

    // 未来网络试验设施国家重大科技基础设施
    private static final String PROJECT_NUM = "FNII20200103001";

    private boolean isSegment = false;

    private boolean isLink = false;
    
    @Resource
    private FacilityApiService facilityApiService;

    @Resource
    private SiteService siteService;

    @Resource
    private SegmentService segementService;
    
    @Resource
    private FacilityHistoryService siteHistoryService;

    @Resource
    private LinkService linkService;
    
    @Resource
    private PlanApiService planApiService;

    @Resource
    private ProvinceService provinceService;

    @Resource
    private CityService cityService;

    @Resource
    private DataOptionService dataOptionService;

    @Resource
    private PlanService planService;

    @Resource
    private PlanProgressService planProgressService;

    @Resource
    private PlanTaskService planTaskService;

    @Resource
    private PresetTaskProducer presetTaskProducer;

    @Resource
    private ProjectService projectService;

    @Resource
    private VirtualOrgService virtualOrgService;

    @Resource
    private PersonService personService;

    private String oldPlanId;

    private String newPlanId;

    private boolean search = false;

    private boolean record = true;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.SITE.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<Site, String> getDataService() {
        return this.siteService;
    }

    @Override
    protected ViewConverter<Site, SiteBean> getViewConverter() {
        return new SiteBeanConverter();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/facility/siteJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/facility/siteHidden.jsp";
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

    /**
     * 获取画面的各个下拉列表项，用于新增/修改/检索/批量修改画面。有下拉列表的子类必须重写此方法。
     * 
     * @param locale
     * @return key:[v_data_option].option_source，value:[v_data_option]
     */
    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap = new HashMap<>();

        List<ComboboxOptionBean> optProvinces = new ArrayList<>();
        String format = "(%1$s) %2$s";
        List<Province> provinceList = this.provinceService.list(null, null);
        if (provinceList != null) {
            for (Province p : provinceList) {
                optProvinces.add(new ComboboxOptionBean(p.getId(),
                        String.format(format, p.getProvinceCode(), p.getProvinceName())));
            }
        }
        optionMap.put("provinceOptions", optProvinces);

        List<ComboboxOptionBean> optSiteTypes = new ArrayList<>();
        for (SiteTypeEnum s : SiteTypeEnum.values()) {
            optSiteTypes.add(new ComboboxOptionBean(s.value().toString(),
                    this.messageSource.getMessage(s.resourceName(), null, locale)));
        }
        optionMap.put("siteTypeOptions", optSiteTypes);
        
        List<ComboboxOptionBean> dataNetworkNodeTypeOptions = new ArrayList<>();
        for (DataNetworkNodeTypeEnum s : DataNetworkNodeTypeEnum.values()) {
        	dataNetworkNodeTypeOptions.add(new ComboboxOptionBean(s.value().toString(),
                    this.messageSource.getMessage(s.resourceName(), null, locale)));
        }
        optionMap.put("dataNetworkNodeTypeOptions", dataNetworkNodeTypeOptions);

        List<ComboboxOptionBean> stationDeviceTypeOptions = new ArrayList<>();
        for (StationDeviceTypeEnum s : StationDeviceTypeEnum.values()) {
            stationDeviceTypeOptions.add(new ComboboxOptionBean(s.value().toString(),
                    this.messageSource.getMessage(s.resourceName(), null, locale)));
        }
        optionMap.put("stationDeviceTypeOptions", stationDeviceTypeOptions);

        List<ComboboxOptionBean> basicNetworkTransmitStationTypeOptions = new ArrayList<>();
        for (BasicNetworkTransmitStationTypeEnum t : BasicNetworkTransmitStationTypeEnum.values()) {
            basicNetworkTransmitStationTypeOptions.add(new ComboboxOptionBean(t.value().toString(),
                    this.messageSource.getMessage(t.resourceName(), null, locale)));
        }
        optionMap.put("basicNetworkTransmitStationTypeOptions",
                basicNetworkTransmitStationTypeOptions);

        List<ComboboxOptionBean> programmableNetworkTransmitStationTypeOptions = new ArrayList<>();
        for (ProgrammableNetworkTransmitStationTypeEnum t : ProgrammableNetworkTransmitStationTypeEnum
                .values()) {
            programmableNetworkTransmitStationTypeOptions
                    .add(new ComboboxOptionBean(t.value().toString(),
                            this.messageSource.getMessage(t.resourceName(), null, locale)));
        }
        optionMap.put("programmableNetworkTransmitStationTypeOptions",
                programmableNetworkTransmitStationTypeOptions);

        List<ComboboxOptionBean> sdnNetworkTransmitStationTypeOptions = new ArrayList<>();
        for (SdnNetworkTransmitStationTypeEnum t : SdnNetworkTransmitStationTypeEnum.values()) {
            sdnNetworkTransmitStationTypeOptions.add(new ComboboxOptionBean(t.value().toString(),
                    this.messageSource.getMessage(t.resourceName(), null, locale)));
        }
        optionMap.put("sdnNetworkTransmitStationTypeOptions", sdnNetworkTransmitStationTypeOptions);

        List<ComboboxOptionBean> siteStatusTypeOptions = new ArrayList<>();
        for (FacilityStatusEnum s : FacilityStatusEnum.values()) {
            siteStatusTypeOptions.add(new ComboboxOptionBean(s.value().toString(),
                    this.messageSource.getMessage(s.resourceName(), null, locale)));
        }
        optionMap.put("siteStatusTypeOptions", siteStatusTypeOptions);

        List<ComboboxOptionBean> manualSiteStatusTypeOptions = new ArrayList<>();
        for (FacilityStatusEnum m : FacilityStatusEnum.values()) {
            manualSiteStatusTypeOptions.add(new ComboboxOptionBean(m.value().toString(),
                    this.messageSource.getMessage(m.resourceName(), null, locale)));
        }
        optionMap.put("manualSiteStatusTypeOptions", manualSiteStatusTypeOptions);

        List<ComboboxOptionBean> basicManualSiteStatusTypeOptions = new ArrayList<>();
        for (FacilityStatusEnum m : FacilityStatusEnum.values()) {
            basicManualSiteStatusTypeOptions.add(new ComboboxOptionBean(m.value().toString(),
                    this.messageSource.getMessage(m.resourceName(), null, locale)));
        }
        optionMap.put("basicManualSiteStatusTypeOptions", basicManualSiteStatusTypeOptions);

        List<ComboboxOptionBean> programManualSiteStatusTypeOptions = new ArrayList<>();
        for (FacilityStatusEnum m : FacilityStatusEnum.values()) {
            programManualSiteStatusTypeOptions.add(new ComboboxOptionBean(m.value().toString(),
                    this.messageSource.getMessage(m.resourceName(), null, locale)));
        }
        optionMap.put("programManualSiteStatusTypeOptions", programManualSiteStatusTypeOptions);

        List<ComboboxOptionBean> sdnManualSiteStatusTypeOptions = new ArrayList<>();
        for (FacilityStatusEnum m : FacilityStatusEnum.values()) {
            sdnManualSiteStatusTypeOptions.add(new ComboboxOptionBean(m.value().toString(),
                    this.messageSource.getMessage(m.resourceName(), null, locale)));
        }
        optionMap.put("sdnManualSiteStatusTypeOptions", sdnManualSiteStatusTypeOptions);

        List<ComboboxOptionBean> edgeNodeManualSiteStatusTypeOptions = new ArrayList<>();
        for (FacilityStatusEnum m : FacilityStatusEnum.values()) {
            edgeNodeManualSiteStatusTypeOptions.add(new ComboboxOptionBean(m.value().toString(),
                    this.messageSource.getMessage(m.resourceName(), null, locale)));
        }
        optionMap.put("edgeNodeManualSiteStatusTypeOptions", edgeNodeManualSiteStatusTypeOptions);

        List<String> dataTypeList = new ArrayList<>();
        dataTypeList.add(DataTypeEnum.NETWORK_ROOM_OWNER_UNIT_TYPE.value());
        dataTypeList.add(DataTypeEnum.EDGE_NODE_TYPE.value());

        CommonFilter f = new CommonFilter().addWithin("dataType", dataTypeList);

        List<ComboboxOptionBean> optNetworkRoomOwnerUnitTypes = new ArrayList<>();
        List<ComboboxOptionBean> edgeNodeTypeOptions = new ArrayList<>();

        List<DataOption> list = this.dataOptionService.list(f, null);
        for (DataOption o : list) {
            if (o.getDataType().equals(DataTypeEnum.NETWORK_ROOM_OWNER_UNIT_TYPE.value())) {
                optNetworkRoomOwnerUnitTypes
                        .add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            } else if (o.getDataType().equals(DataTypeEnum.EDGE_NODE_TYPE.value())) {
                edgeNodeTypeOptions.add(new ComboboxOptionBean(o.getId(), o.getOptionName()));
            }
        }

        optionMap.put("networkRoomOwnerUnitTypeOptions", optNetworkRoomOwnerUnitTypes);
        optionMap.put("edgeNodeTypeOptions", edgeNodeTypeOptions);

        return optionMap;

    }

    @Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();

        idMap.put(ID_MAP_KEY_SITE_DIALOG, String.format("%1$s-%2$s-EditFormDialog-Site",
                this.getPageId(), this.getDataType()));

        return idMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 打开新建和修改画面时，增加回调，处理省市下拉框联动

        // // 打开新建
        // jsMap.put(ControllerJsMapKeys.OPEN_ADD,
        // String.format("CrudUtils.openAddFormDialog('#%1$s', ProvinceCityUtils);",
        // idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));
        //
        // // 打开修改
        // jsMap.put(ControllerJsMapKeys.OPEN_EDIT, String.format(
        // "CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, ProvinceCityUtils);",
        // idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
        // urlMap.get(ControllerUrlMapKeys.FIND),
        // idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        //
        // // 连续新建
        // jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT, String.format(
        // "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true,
        // ProvinceCityUtils);",
        // idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
        // idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 打开新建
        jsMap.put(ControllerJsMapKeys.OPEN_ADD,
                String.format("CrudUtils.openAddFormDialog('#%1$s', SITE);",
                        idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG)));

        // 打开修改
        jsMap.put(ControllerJsMapKeys.OPEN_EDIT,
                String.format("CrudUtils.openEditFormDialog('#%1$s', '%2$s', '#%3$s', null, SITE);",
                        idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.FIND),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 连续新建
        jsMap.put(ControllerJsMapKeys.CONTINUOUS_ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, true, SITE);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // add start by huang 20200412
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, SITE);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));

        jsMap.put(ControllerJsMapKeys.EDIT_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, false, false, SITE);",
                idMap.get(ControllerIdMapKeys.EDIT_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), false));
        // add end by huang 20200412

        // 修改站点-建议
        jsMap.put(JS_MAP_KEY_EDIT_SITE_SUBMIT,
                String.format("CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s);",
                        idMap.get(ID_MAP_KEY_SITE_DIALOG),
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile()));

        // 上传文件
        jsMap.put(ControllerJsMapKeys.OPEN_IMPORT, String
                .format("SITE.openUploadFormDialog('#%1$s');", idMap.get(ID_MAP_KEY_SITE_DIALOG)));
        
        // 导出文件
 		jsMap.put(ControllerJsMapKeys.EXPORT_SUBMIT,
 				String.format("SITE.exportExcel('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

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

        // Datagrid行双击事件，增加回调，处理省市下拉框联动
//        model.addAttribute(MODEL_KEY_DBL_CLICK_ROW_HANDLER, "ProvinceCityUtils.handleDblClickRow");
        model.addAttribute(MODEL_KEY_DBL_CLICK_ROW_HANDLER, "SITE.handleDblClickRow");
        model.addAttribute(ControllerModelKeys.SELECT_HANDLER, "SITE.handleSelect");
        
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
    	filter.addExact("targetType", 0);
		
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

    
    @RequestMapping("/get-plan")
    @ResponseBody
    public JsonResultBean getTask(
    		@RequestParam(required = false, defaultValue = "") String siteId,
            @RequestParam(required = false, defaultValue = "sdn") String type) {
    	
    	String planNameStart = "SDN";
    	if (type.equals("basic")) {
    		planNameStart = "基础网络";
    	} else if (type.equals("programmable")) {
    		planNameStart = "可编程";
    	}

    	CommonFilter filter = new CommonFilter().addExact("site.id", siteId).addAnywhere("v.virtualOrgName", planNameStart);
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

        JsonResultBean jsonResult = super.find(id, locale);
        try {
            SiteBean bean =
                    (SiteBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);

            // 获取城市下拉框的option，以便修改弹出画面上联动设置

            // 设置过滤信息
            CommonFilter filter = new CommonFilter().addExact("p.id", bean.getProvince_());

            // 获取数据
            List<City> list = this.cityService.list(filter, null);
            List<ComboboxOptionBean> resultList = new ArrayList<>();
            String format = "(%1$s) %2$s";
            for (City entity : list) {
                resultList.add(new ComboboxOptionBean(entity.getId(),
                        String.format(format, entity.getCityCode(), entity.getCityName())));
            }
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);

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
    @SysLogAnnotation(description = "站点管理", action = "新增")
    public JsonResultBean create(@ModelAttribute SiteBean bean,
    		HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
    	fieldNameArchiveListMap.put("sdnArchives_", bean.getSdnArchivesList());
    	fieldNameArchiveListMap.put("basicArchives_", bean.getBasicArchivesList());
    	fieldNameArchiveListMap.put("programmableArchives_", bean.getProgrammableArchivesList());
    	
    	Map<String, Object> paramMap = new HashMap<>();
        JsonResultBean jsonResult =
                this.parseMultipart(request, response, paramMap, fieldNameArchiveListMap);

        if (jsonResult.getStatus() != JsonStatus.ERROR) {
            jsonResult = super.create(bean, paramMap, userBean, locale);
            
            // 添加历史记录
            ViewBean newBean = (ViewBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);
            facilityApiService.addHistoryRecord(newBean.getId(), 0, 0, bean.getSiteNum() + " " + bean.getStationName(), userBean.getSessionUserId());
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
    @SysLogAnnotation(description = "站点管理", action = "更新")
    public JsonResultBean update(@ModelAttribute SiteBean bean,
    		HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	Map<String, List<ArchiveBean>> fieldNameArchiveListMap = new HashMap<>();
    	fieldNameArchiveListMap.put("sdnArchives_", bean.getSdnArchivesList());
    	fieldNameArchiveListMap.put("basicArchives_", bean.getBasicArchivesList());
    	fieldNameArchiveListMap.put("programmableArchives_", bean.getProgrammableArchivesList());
    	
    	Map<String, Object> paramMap = new HashMap<>();
        JsonResultBean jsonResult =
                this.parseMultipart(request, response, paramMap, fieldNameArchiveListMap);
        bean.setFlowStatus(0);

        if (jsonResult.getStatus() != JsonStatus.ERROR) {
        	
        	Site prevSite = siteService.find(bean.getId());
        	SiteBean prevBean = this.getViewConverter().fromEntity(prevSite, messageSource, locale);
        	
            jsonResult = super.update(bean, paramMap, userBean, locale);
            
            // 取得更新的内容，并添加历史记录
           SiteBean nextBean = (SiteBean) jsonResult.getDataMap().get(JsonResultBean.KEY_RETURN_OBJECT);
           String updateContents = facilityApiService.getUpdateContent(prevBean, nextBean, PID, this.getDataType(), locale);
           facilityApiService.addHistoryRecord(bean.getId(), 0, 1, updateContents, userBean.getSessionUserId());
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
    @SysLogAnnotation(description = "站点管理", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	JsonResultBean jsonResult =  super.delete(idsToDelete, null, userBean, locale);
    	
    	// 添加历史记录
    	if (jsonResult.getStatus() != JsonStatus.ERROR) {
    		String[] ids = StringUtils.commaDelimitedListToStringArray(idsToDelete);
        	for (int i = 0; i < ids.length; i++) {
        		Site site = siteService.find(ids[i]);
        		if (site != null) {
        			facilityApiService.addHistoryRecord(site.getId(), 0, 2, "删除记录", userBean.getSessionUserId());
        		}
        	}
    	}
    	
    	return jsonResult;
    }
    
    @RequestMapping("/regenerateAll")
    @ResponseBody
    public JsonResultBean regenerateAll(@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
    	
    	// 取得所有站点
    	List<Site> siteList = siteService.list(null, null);
    	for (Site site: siteList) {
    		SiteBean bean = this.getViewConverter().fromEntity(site, messageSource, locale);
    		this.postCreate(site, bean, null, locale);
    	}
    	
    	JsonResultBean jsonResult = new JsonResultBean();
    	jsonResult.setStatus(JsonStatus.OK);
    	return jsonResult;
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
    
    
    /**
     * 重新生成站点的建设计划
     * @param groupIds
     * @param siteId
     * @param locale
     * @throws Exception 
     */
    private void regeneratePlan(String[] groupIds, String siteId, Locale locale) throws Exception {
    	
    	Site site = siteService.find(siteId);
    	
    	// 查询对应的建设计划
		CommonFilter filter = new CommonFilter();
        filter.addExact("si.id", siteId);
//        filter.addStart("planName", planStartName);
		List<Plan> planList = planService.list(filter, null);
		System.out.println("重新编制建设计划，当前计划数=" + planList.size());
		
		for (Plan plan: planList) {
			planApiService.removePlan(plan.getId());
		}
    	
    	for (String groupId: groupIds) {
    		
    		String planStartName = "";
    		if (groupId.equals("1")) {
    			planStartName = "基础网络";
    		} else if (groupId.equals("2")) {
    			planStartName = "可编程";
    		} else if (groupId.equals("3")) {
    			planStartName = "SDN";
    		}
    		
    		// 重新创建当前计划
			this.regeneratePlanData(site, planStartName, this.getViewConverter().fromEntity(site, messageSource, locale), null, locale);
    	}
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

    		// 查询对应站点的建设计划列表
    		CommonFilter filter = new CommonFilter();
            filter.addExact("site.id", id);
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

    /**
     * 根据省份ID,用省份下拉框选择后，联动城市的值变更。
     * 
     * @param provinceId
     * @param locale
     * @return
     */
    @RequestMapping("/list-city-type")
    @ResponseBody
    public JsonResultBean getCityTypeCityList(@RequestParam String provinceId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            if (StringUtils.isEmpty(provinceId)) {

                this.getLogger().error("the provinceId is empty !");
                jsonResult.setStatus(JsonStatus.ERROR);
                return jsonResult;
            }

            CommonFilter filter = new CommonFilter();

            filter.addExact("p.id", provinceId);

            List<City> list = this.cityService.list(filter, null);
            List<ComboboxOptionBean> cityOptionList = new ArrayList<>();
            String format = "(%1$s) %2$s";

            for (City c : list) {
                cityOptionList.add(new ComboboxOptionBean(c.getId(),
                        String.format(format, c.getCityCode(), c.getCityName())));
            }
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, cityOptionList);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            this.getLogger().error("Exception while getting the provinceId " + provinceId, e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    // get site info
    /**
     * 获取站点信息。
     * 
     * @param provinceId
     * @param locale
     * @return
     */
    @RequestMapping("/list-site-info")
    @ResponseBody
    public JsonResultBean getSiteInfo(
            @RequestParam(required = false, defaultValue = "") String stationType,
            @RequestParam(required = false, defaultValue = "") String startTime,
            @RequestParam(required = false, defaultValue = "") String endTime,
            @RequestParam(required = false, defaultValue = "") String area,
            @RequestParam(required = true, defaultValue = "") String layer, Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            // 参数检查
            if (StringUtils.isEmpty(layer)) {
                jsonResult.setStatus(JsonStatus.ERROR);
                return jsonResult;
            }

            MapSiteInfoBean mapSiteInfoBean = new MapSiteInfoBean();

            CommonFilter filter = new CommonFilter();
            List<Plan> planList = null;
            List<Plan> progressList = null;
            List<Plan> problemList = null;
            List<Site> siteList = null;
            List<Segment> segmentList = this.segementService.list(null, null);
            List<Link> linkList = this.linkService.list(null, null);
            MapLayerListBeanConverter converter = new MapLayerListBeanConverter();

            if (layer.equals("plan")) {// 计划图层
            	
            	CommonFilter orgFilter = new CommonFilter();
                if (stationType.equals("1")) {
                	orgFilter.addAnywhere("virtualOrgName", "基础网络");
                } else if (stationType.equals("2")) {
                	orgFilter.addAnywhere("virtualOrgName", "可编程网络");
                } else if (stationType.equals("3")) {
                	orgFilter.addAnywhere("virtualOrgName", "SDN网络");
                }
                
                List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);
                if (virtualOrgs.size() > 0) {
                	VirtualOrg org = virtualOrgs.get(0);
                	filter.addExact("v.id", org.getId());
                }
                
                if (!StringUtils.isEmpty(area) && !area.equals("0")) {
                    filter.addExact("psp.id", area);
                }
//                if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
//                    // 设置过滤信息
//                    Date from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
//                    Date to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
//
//                    filter.addRange("planEndDate", FieldValueType.DATE, from, to);
//                }
                planList = this.planService.list(filter, null);
                if ((planList != null) && (planList.size() > 0)) {
                    for (Plan p : planList) {
                        if (p != null) {
                        	if (p.getSite() == null) {
                        		continue;
                        	}
                            Site site = p.getSite();
                            
                            Date from = null;
                        	if (!StringUtils.isEmpty(startTime)) {
                        		 from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
                            }
                        	
                        	Date to = null;
                        	if (!StringUtils.isEmpty(endTime)) {
                        		to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
                            }
                        	Date compareFrom = null;
                        	Date compareTo = null;
                        	if (stationType.equals("1")) {//光传输网络
                        		if (StringUtils.isEmpty(site.getInitMaterialArchieveDate())) {
                    				continue;
                    			}
                        		if (!StringUtils.isEmpty(startTime)) {
                    				compareFrom = site.getInitMaterialArchieveDate();
                                }
                    			if (!StringUtils.isEmpty(endTime)) {
                    				compareTo = site.getInitMaterialArchieveDate();
                                }
                        	} else if (stationType.equals("2")) {//可编程网络
                        		if (StringUtils.isEmpty(site.getProgrammableInitMaterialArchieveDate())) {
                    				continue;
                    			}
                    			if (!StringUtils.isEmpty(startTime)) {
                    				compareFrom = site.getProgrammableInitMaterialArchieveDate();
                                }
                    			if (!StringUtils.isEmpty(endTime)) {
                    				compareTo = site.getProgrammableInitMaterialArchieveDate();
                                }
                        	} else if (stationType.equals("3")) {//SDN网络
                        		if (StringUtils.isEmpty(site.getSdnInitMaterialArchieveDate())) {
                    				continue;
                    			}
                        		if (!StringUtils.isEmpty(startTime)) {
                    				compareFrom = site.getSdnInitMaterialArchieveDate();
                                }
                    			if (!StringUtils.isEmpty(endTime)) {
                    				compareTo = site.getSdnInitMaterialArchieveDate();
                                }
                        	}
                        	
                        	if (!StringUtils.isEmpty(startTime)) {
                                if (StringUtils.isEmpty(compareFrom) || compareFrom.before(from)) {
                              	    continue;
                                }
                            }
                			if (!StringUtils.isEmpty(endTime)) {
                                if (StringUtils.isEmpty(compareTo) || compareTo.after(to)) {
                              	    continue;
                                }
                            }
                            mapSiteInfoBean.getMapSiteList().add(converter.fromEntity(stationType,
                                    p.getId(), "", site, this.messageSource, locale));
                        }
                    }
                    if (stationType.equals("1")) {
                        if ((segmentList != null) && (segmentList.size() > 0)) {
                            for (Segment segment : segmentList) {
                            	Date from = null;
                            	if (!StringUtils.isEmpty(startTime)) {
                            		 from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
                                }
                            	
                            	Date to = null;
                            	if (!StringUtils.isEmpty(endTime)) {
                            		to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
                                }
                            	Date compareFrom = null;
                            	Date compareTo = null;
                            	if (StringUtils.isEmpty(segment.getInitMaterialArchieveDate())) {
                            		continue;
                            	}
                        		if (!StringUtils.isEmpty(startTime)) {
                    				compareFrom = segment.getInitMaterialArchieveDate();
                                }
                    			if (!StringUtils.isEmpty(endTime)) {
                    				compareTo = segment.getInitMaterialArchieveDate();
                                }
                            	
                            	
                            	if (!StringUtils.isEmpty(startTime)) {
                                    if (StringUtils.isEmpty(compareFrom) || compareFrom.before(from)) {
                                  	    continue;
                                    }
                                }
                    			if (!StringUtils.isEmpty(endTime)) {
                                    if (StringUtils.isEmpty(compareTo) || compareTo.after(to)) {
                                  	    continue;
                                    }
                                }
                                if (segment.getEndA() != null && segment.getEndB() != null) {
                                    for (Plan plan : planList) {
                                        Site site = plan.getSite();
                                        if (site != null) {
                                            if (segment.getEndA().getStationName()
                                                    .equals(site.getStationName())
                                                    || segment.getEndB().getStationName()
                                                            .equals(site.getStationName())) {
                                                isSegment = true;
                                            }
                                        }
                                    }
                                    if (isSegment) {
                                        mapSiteInfoBean.getMapSegmentList()
                                                .add(converter.fromEntitySeg(layer, segment, null,
                                                        this.messageSource, locale));
                                        isSegment = false;
                                    }
                                }
                            }
                        }
                    }

                    if (stationType.equals("2") | stationType.equals("3")) {
                        if ((linkList != null) && (linkList.size() > 0)) {
                            for (Link link : linkList) {
                                if (link.getEndA() != null && link.getEndB() != null) {
                                    for (Plan plan : planList) {
                                        Site site = plan.getSite();
                                        if (site != null) {
                                            if (link.getEndA().getStationName()
                                                    .equals(site.getStationName())
                                                    || link.getEndB().getStationName()
                                                            .equals(site.getStationName())) {
                                                isLink = true;
                                            }
                                        }
                                    }
                                    if (isLink) {
                                        mapSiteInfoBean.getMapLinkList().add(converter
                                                .fromEntityLink(link, this.messageSource, locale));
                                        isLink = false;
                                    }
                                }
                            }
                        }
                        // 筛选边缘节点
                        List<String> dataTypeList = new ArrayList<>();
                        dataTypeList.add(DataTypeEnum.EDGE_NODE_TYPE.value());

                        CommonFilter f = new CommonFilter();

                        f = new CommonFilter().addExact("dataType", dataTypeList.get(0));

                        List<DataOption> list = this.dataOptionService.list(f, null);


                        // 边缘节点
                        if (stationType.equals("2")) {
                            filter = new CommonFilter().addExact("sedge.id", list.get(1).getId());
                        } else {
                            filter = new CommonFilter().addExact("sedge.id", list.get(0).getId());
                        }
                        List<Plan> edgeSiteList = this.planService.list(filter, null);

                        if ((edgeSiteList != null) && (edgeSiteList.size() > 0)) {
                            for (Plan p : edgeSiteList) {
                                if (p != null) {
                                    Site site = p.getSite();
                                    Date from = null;
                                	if (!StringUtils.isEmpty(startTime)) {
                                		 from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
                                    }
                                	
                                	Date to = null;
                                	if (!StringUtils.isEmpty(endTime)) {
                                		to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
                                    }
                                	Date compareFrom = null;
                                	Date compareTo = null;
                                	if (stationType.equals("1")) {//光传输网络
                                		
                                	} else if (stationType.equals("2")) {//可编程网络
                                		if (StringUtils.isEmpty(site.getProgrammableInitMaterialArchieveDate())) {
                                			continue;
                                		}
                            			if (!StringUtils.isEmpty(startTime)) {
                            				compareFrom = site.getProgrammableEdgeNodeInitMaterialArchieveDate();
                                        }
                            			if (!StringUtils.isEmpty(endTime)) {
                            				compareTo = site.getProgrammableEdgeNodeInitMaterialArchieveDate();
                                        }
                                	} else if (stationType.equals("3")) {//SDN网络
                                		if (StringUtils.isEmpty(site.getSdnEdgeNodeInitMaterialArchieveDate())) {
                                			continue;
                                		}
                                		if (!StringUtils.isEmpty(startTime)) {
                            				compareFrom = site.getSdnEdgeNodeInitMaterialArchieveDate();
                                        }
                            			if (!StringUtils.isEmpty(endTime)) {
                            				compareTo = site.getSdnEdgeNodeInitMaterialArchieveDate();
                                        }
                                	}
                                	
                                	if (!StringUtils.isEmpty(startTime)) {
                                        if (StringUtils.isEmpty(compareFrom) || compareFrom.before(from)) {
                                      	    continue;
                                        }
                                    }
                        			if (!StringUtils.isEmpty(endTime)) {
                                        if (StringUtils.isEmpty(compareTo) || compareTo.after(to)) {
                                      	    continue;
                                        }
                                    }
                                    if (!site.getBasicNetworkTransmitStationType().equals("1")) {
                                        mapSiteInfoBean.getMapEdgeSiteList()
                                                .add(converter.fromEntity(stationType, p.getId(),
                                                        "", site, this.messageSource, locale));
                                    }

                                }
                            }
                        }
                    }
                }
            } else if (layer.equals("progress")) {// 实际进度图层
                Project project = null;

                CommonFilter projectFilter = new CommonFilter();
                projectFilter.addExact("projectNum", PROJECT_NUM);

                List<Project> projects = this.projectService.list(projectFilter, null);

                if (!projects.isEmpty()) {
                    project = projects.get(0);

                }
                CommonFilter orgFilter = new CommonFilter();
                orgFilter.addExact("project.id", project.getId());

//                CommonFilter progSegFilter = new CommonFilter();
//                progSegFilter.addAnywhere("planName", "中继段_");


                List<Plan> progSegmentList = this.planService.list(null, null);

                if (stationType.equals("1")) {

                    orgFilter.addAnywhere("virtualOrgName", "基础网络");

                    List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                    VirtualOrg v = null;

                    if (!virtualOrgs.isEmpty()) {
                        v = virtualOrgs.get(0);
                        filter = new CommonFilter()
//                                .addExact("si.isBasicNetworkTransmitStationType", (short) 1)
                                .addExact("v.id", v.getId());

//                        filter.addAnywhere("planName", "基础网络传输组站点计划_");

                    }
                    
//                    if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
//                        // 设置过滤信息
//                        Date from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
//                        Date to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
//
//                        filter.addRange("si.initMaterialArchieveDate", FieldValueType.DATE, from, to);
//                    }

                } else if (stationType.equals("2")) {
                    orgFilter.addAnywhere("virtualOrgName", "可编程");

                    List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                    VirtualOrg v = null;

                    if (!virtualOrgs.isEmpty()) {
                        v = virtualOrgs.get(0);
                        filter = new CommonFilter()
//                                .addExact("si.isProgrammableNetworkTransmitStationType", (short) 1)
                                .addExact("v.id", v.getId());

//                        filter.addAnywhere("planName", "可编程网络传输组站点计划_");

                    }
//                    if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
//                        // 设置过滤信息
//                        Date from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
//                        Date to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
//
//                        filter.addRange("si.programmableInitMaterialArchieveDate", FieldValueType.DATE, from, to);
////                        filter.addRange("si.programmableEdgeNodeInitMaterialArchieveDate", FieldValueType.DATE, from, to);
//                    }
                } else if (stationType.equals("3")) {
                    orgFilter.addAnywhere("virtualOrgName", "SDN");

                    List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                    VirtualOrg v = null;

                    if (!virtualOrgs.isEmpty()) {
                        v = virtualOrgs.get(0);
                        filter = new CommonFilter()
//                                .addExact("si.isSdnNetworkTransmitStationType", (short) 1)
                                .addExact("v.id", v.getId());

//                        filter.addAnywhere("planName", "SDN网络传输组站点计划_");

                    }
//                    if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
//                        // 设置过滤信息
//                        Date from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
//                        Date to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
//
//                        filter.addRange("si.sdnInitMaterialArchieveDate", FieldValueType.DATE, from, to);
//                    }
                }
                if (!StringUtils.isEmpty(area) && !area.equals("0")) {
                    filter.addExact("psp.id", area);
                }

                progressList = this.planService.list(filter, null);

                if ((progressList != null) && (progressList.size() > 0)) {

                    for (Plan plan : progressList) {
                    	if (plan.getSite() == null) {
                    		continue;
                    	}
                        Site site = plan.getSite();
                        if (site != null) {
//                            CommonFilter statusFilter =
//                                    new CommonFilter().addExact("p.id", plan.getId());
//
//                            List<OrderByDto> orders = new ArrayList<>();
//                            orders.add(new OrderByDto("taskNum", false));
//
//                            List<PlanTask> statusList =
//                                    this.planTaskService.list(statusFilter, orders);
//
//                            if ((statusList != null) && (statusList.size() > 0)) {
//                                int constructionStatus = 0;
//                                for (int i = 0; i < statusList.size(); i++) {
//                                    PlanTask plTask = new PlanTask();
//
//                                    plTask = statusList.get(i);
//                                    if (plTask != null) {
//
//                                        if (i == 0) {
//                                            if (statusList.get(0).getProgress() == null
//                                                    || statusList.get(0).getProgress() == 0.0) {
//                                                constructionStatus = 0;
//                                                break;
//                                            }
//
//                                        } else if (statusList.get(i).getProgress() == null
//                                                || statusList.get(i).getProgress() == 0) {
//                                            if (statusList.get(i - 1)
//                                                    .getConstructionStatus() != null) {
//                                                constructionStatus = statusList.get(i - 1)
//                                                        .getConstructionStatus();
//                                            } else {
//                                                constructionStatus = 0;
//                                            }
//
//                                            break;
//
//                                        } else if (statusList.get(statusList.size() - 1)
//                                                .getProgress() != null
//                                                && statusList.get(statusList.size() - 1)
//                                                        .getProgress() != 0.0) {
//                                            if (statusList.get(statusList.size() - 1)
//                                                    .getConstructionStatus() != null) {
//                                                constructionStatus =
//                                                        statusList.get(statusList.size() - 1)
//                                                                .getConstructionStatus();
//                                            } else {
//                                                constructionStatus = 0;
//                                            }
//
//                                            break;
//
//                                        }
//                                    }
//                                }
//                        		
//                                mapSiteInfoBean.getMapSiteList()
//                                        .add(converter.fromEntityPro(stationType,
//                                                constructionStatus, site, this.messageSource,
//                                                locale));
//                            }
                        	Date from = null;
                        	if (!StringUtils.isEmpty(startTime)) {
                        		 from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
                            }
                        	
                        	Date to = null;
                        	if (!StringUtils.isEmpty(endTime)) {
                        		to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
                            }
                        	Date compareFrom = null;
                        	Date compareTo = null;
                        	if (stationType.equals("1")) {//光传输网络
                        		if (StringUtils.isEmpty(site.getInitMaterialArchieveDate())) {
                    				continue;
                    			}
                        		if (!StringUtils.isEmpty(startTime)) {
                    				compareFrom = site.getInitMaterialArchieveDate();
                                }
                    			if (!StringUtils.isEmpty(endTime)) {
                    				compareTo = site.getInitMaterialArchieveDate();
                                }
                        	} else if (stationType.equals("2")) {//可编程网络
                        		if (StringUtils.isEmpty(site.getProgrammableInitMaterialArchieveDate())) {
                    				continue;
                    			}
                    			if (!StringUtils.isEmpty(startTime)) {
                    				compareFrom = site.getProgrammableInitMaterialArchieveDate();
                                }
                    			if (!StringUtils.isEmpty(endTime)) {
                    				compareTo = site.getProgrammableInitMaterialArchieveDate();
                                }
                        	} else if (stationType.equals("3")) {//SDN网络
                        		if (StringUtils.isEmpty(site.getSdnInitMaterialArchieveDate())) {
                    				continue;
                    			}
                        		if (!StringUtils.isEmpty(startTime)) {
                    				compareFrom = site.getSdnInitMaterialArchieveDate();
                                }
                    			if (!StringUtils.isEmpty(endTime)) {
                    				compareTo = site.getSdnInitMaterialArchieveDate();
                                }
                        	}
                        	
                        	if (!StringUtils.isEmpty(startTime)) {
                                if (StringUtils.isEmpty(compareFrom) || compareFrom.before(from)) {
                              	    continue;
                                }
                            }
                			if (!StringUtils.isEmpty(endTime)) {
                                if (StringUtils.isEmpty(compareTo) || compareTo.after(to)) {
                              	    continue;
                                }
                            }
                        	
                            Integer constructionStatus = site.getBasicNetworkManualSiteStatus();
                            if (constructionStatus == null) {
                            	constructionStatus = plan.getStatus();
                            }

                            mapSiteInfoBean.getMapSiteList()
                                    .add(converter.fromEntityPro(stationType,
                                            constructionStatus, site, this.messageSource,
                                            locale));
                        }

                    }

                    if (stationType.equals("1")) {
                        if ((progSegmentList != null) && (progSegmentList.size() > 0)) {
                            for (Plan segPlan : progSegmentList) {
                            	if (segPlan.getSegment() == null) {
                            		continue;
                            	}
                                Segment segment = segPlan.getSegment();
                                if (segment != null) {
//                                    int manualSegmentStatus = 0;
//                                    CommonFilter basicStatusFilter =
//                                            new CommonFilter().addExact("p.id", segPlan.getId());
//
//                                    List<OrderByDto> orders = new ArrayList<>();
//                                    orders.add(new OrderByDto("taskNum", false));
//
//                                    List<PlanTask> statusList =
//                                            this.planTaskService.list(basicStatusFilter, orders);
//
//                                    if ((statusList != null) && (statusList.size() > 0)) {
//
//                                        for (int i = 0; i < statusList.size(); i++) {
//                                            PlanTask plTask = new PlanTask();
//
//                                            plTask = statusList.get(i);
//
//                                            // System.out.println(
//                                            // "task_name::::" + plTask.getTaskName());
//                                            if (plTask != null) {
//
//                                                if (i == 0) {
//                                                    if (statusList.get(0).getProgress() == null
//                                                            || statusList.get(0)
//                                                                    .getProgress() == 0.0) {
//
//
//                                                        manualSegmentStatus = 0;
//                                                        // System.out.println(
//                                                        // "i==0" + manualSegmentStatus);
//                                                        break;
//                                                    }
//
//                                                } else if (statusList.get(i).getProgress() == null
//                                                        || statusList.get(i).getProgress() == 0) {
//
//
//                                                    if (statusList.get(i - 1)
//                                                            .getConstructionStatus() != null) {
//                                                        manualSegmentStatus = statusList.get(i - 1)
//                                                                .getConstructionStatus();
//                                                    } else {
//                                                        manualSegmentStatus = 0;
//                                                    }
//                                                    // System.out
//                                                    // .println("i==1" + manualSegmentStatus);
//                                                    break;
//
//                                                } else if (statusList.get(statusList.size() - 1)
//                                                        .getProgress() != null
//                                                        && statusList.get(statusList.size() - 1)
//                                                                .getProgress() != 0.0) {
//
//
//                                                    if (statusList.get(statusList.size() - 1)
//                                                            .getConstructionStatus() != null) {
//                                                        manualSegmentStatus = statusList
//                                                                .get(statusList.size() - 1)
//                                                                .getConstructionStatus();
//                                                    } else {
//                                                        manualSegmentStatus = 0;
//                                                    }
//                                                    // System.out
//                                                    // .println("i==2" + manualSegmentStatus);
//                                                    break;
//
//                                                }
//                                            }
//                                        }
//
//                                    }
                                	
                                	Date from = null;
                                	if (!StringUtils.isEmpty(startTime)) {
                                		 from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
                                    }
                                	
                                	Date to = null;
                                	if (!StringUtils.isEmpty(endTime)) {
                                		to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
                                    }
                                	Date compareFrom = null;
                                	Date compareTo = null;
                                	if (StringUtils.isEmpty(segment.getInitMaterialArchieveDate())) {
                                		continue;
                                	}
                            		if (!StringUtils.isEmpty(startTime)) {
                        				compareFrom = segment.getInitMaterialArchieveDate();
                                    }
                        			if (!StringUtils.isEmpty(endTime)) {
                        				compareTo = segment.getInitMaterialArchieveDate();
                                    }
                                	
                                	
                                	if (!StringUtils.isEmpty(startTime)) {
                                        if (StringUtils.isEmpty(compareFrom) || compareFrom.before(from)) {
                                      	    continue;
                                        }
                                    }
                        			if (!StringUtils.isEmpty(endTime)) {
                                        if (StringUtils.isEmpty(compareTo) || compareTo.after(to)) {
                                      	    continue;
                                        }
                                    }

	                                Integer manualSegmentStatus = segment.getManualSegmentStatus();
	                        		if (manualSegmentStatus == null) {
	                        			manualSegmentStatus = segPlan.getStatus();
	                        		}
	                        		
                                    if (segment.getEndA() != null && segment.getEndB() != null) {
                                        for (Plan p : progressList) {

                                            if (p != null) {
                                                Site site = p.getSite();
                                                if (site != null && site.getCity() != null) {
                                                    if (segment.getEndA().getStationName()
                                                            .equals(site.getStationName())
                                                            || segment.getEndB().getStationName()
                                                                    .equals(site
                                                                            .getStationName())) {
                                                        isSegment = true;
                                                    }
                                                }
                                            }
                                        }
                                        if (isSegment) {
                                            mapSiteInfoBean.getMapSegmentList()
                                                    .add(converter.fromEntitySeg(layer, segment,
                                                            manualSegmentStatus, this.messageSource,
                                                            locale));
                                            isSegment = false;
                                        }
                                    }
                                }

                            }
                        }
                    }

                    if (stationType.equals("2") | stationType.equals("3")) {
                        if ((linkList != null) && (linkList.size() > 0)) {
                            for (Link link : linkList) {
                                if (link.getEndA() != null && link.getEndB() != null) {
//                                    for (Plan plan : progressList) {
//                                        if (plan != null) {
//                                            Site site = plan.getSite();
//                                            if (site != null && site.getCity() != null) {
//                                                if (link.getEndA().getStationName()
//                                                        .equals(site.getStationName())
//                                                        || link.getEndB().getStationName()
//                                                                .equals(site.getStationName())) {
//                                                    isLink = true;
//                                                }
//                                            }
//                                        }
//                                    }
//                                    if (isLink) {
//                                        mapSiteInfoBean.getMapLinkList().add(converter
//                                                .fromEntityLink(link, this.messageSource, locale));
//                                        isLink = false;
//                                    }
                                	if ((stationType.equals("3") && link.getInvolvedProjectGroup() == 2) ||
                                		(stationType.equals("2") && link.getInvolvedProjectGroup() == 1)) {
                                		mapSiteInfoBean.getMapLinkList().add(converter.fromEntityLink(link, messageSource, locale));
                                	}
                                }
                            }
                        }
                        // 筛选边缘节点
                        List<String> dataTypeList = new ArrayList<>();
                        dataTypeList.add(DataTypeEnum.EDGE_NODE_TYPE.value());
                        CommonFilter edgeFilter = null;
                        if (stationType.equals("2")) {
                            orgFilter.addAnywhere("virtualOrgName", "可编程");

                            List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                            VirtualOrg v = null;

                            if (!virtualOrgs.isEmpty()) {
                                v = virtualOrgs.get(0);
                                edgeFilter =
                                        new CommonFilter().addWithin("sedge.dataType", dataTypeList)
                                                .addExact("v.id", v.getId());

                                edgeFilter.addAnywhere("planName", "边缘节点");

                            }

                        } else {
                            orgFilter.addAnywhere("virtualOrgName", "SDN");

                            List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                            VirtualOrg v = null;

                            if (!virtualOrgs.isEmpty()) {
                                v = virtualOrgs.get(0);
                                edgeFilter =
                                        new CommonFilter().addWithin("sedge.dataType", dataTypeList)
                                                .addExact("v.id", v.getId());

                                edgeFilter.addAnywhere("planName", "边缘节点");

                            }
                        }
                        List<Plan> edgeSiteList = this.planService.list(edgeFilter, null);

                        if ((edgeSiteList != null) && (edgeSiteList.size() > 0)) {
                            for (Plan p : edgeSiteList) {
                                if (p != null) {
                                    Site site = p.getSite();

                                    if (site != null) {
//                                        CommonFilter statusFilter =
//                                                new CommonFilter().addExact("p.id", p.getId());
//
//                                        List<OrderByDto> orders = new ArrayList<>();
//                                        orders.add(new OrderByDto("taskNum", false));
//
//                                        List<PlanTask> statusList =
//                                                this.planTaskService.list(statusFilter, orders);
//
//                                        if ((statusList != null) && (statusList.size() > 0)) {
//                                            int constructionStatus = 0;
//                                            for (int i = 0; i < statusList.size(); i++) {
//                                                PlanTask plTask = new PlanTask();
//
//                                                plTask = statusList.get(i);
//                                                if (plTask != null) {
//
//                                                    if (i == 0) {
//                                                        if (statusList.get(0).getProgress() == null
//                                                                || statusList.get(0)
//                                                                        .getProgress() == 0.0) {
//                                                            constructionStatus = 0;
//                                                            break;
//                                                        }
//
//                                                    } else if (statusList.get(i)
//                                                            .getProgress() == null
//                                                            || statusList.get(i)
//                                                                    .getProgress() == 0) {
//                                                        if (statusList.get(i - 1)
//                                                                .getConstructionStatus() != null) {
//                                                            constructionStatus = statusList
//                                                                    .get(i - 1)
//                                                                    .getConstructionStatus();
//                                                        } else {
//                                                            constructionStatus = 0;
//                                                        }
//
//                                                        break;
//
//                                                    } else if (statusList.get(statusList.size() - 1)
//                                                            .getProgress() != null
//                                                            && statusList.get(statusList.size() - 1)
//                                                                    .getProgress() != 0.0) {
//                                                        if (statusList.get(statusList.size() - 1)
//                                                                .getConstructionStatus() != null) {
//                                                            constructionStatus = statusList
//                                                                    .get(statusList.size() - 1)
//                                                                    .getConstructionStatus();
//                                                        } else {
//                                                            constructionStatus = 0;
//                                                        }
//
//                                                        break;
//
//                                                    }
//                                                }
//                                            }
//
//                                            mapSiteInfoBean.getMapEdgeSiteList()
//                                                    .add(converter.fromEntityPro(stationType,
//                                                            constructionStatus, site,
//                                                            this.messageSource, locale));
//                                        }
                                    	Date from = null;
                                    	if (!StringUtils.isEmpty(startTime)) {
                                    		 from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
                                        }
                                    	
                                    	Date to = null;
                                    	if (!StringUtils.isEmpty(endTime)) {
                                    		to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);
                                        }
                                    	Date compareFrom = null;
                                    	Date compareTo = null;
                                    	if (stationType.equals("1")) {//光传输网络
                                    		
                                    	} else if (stationType.equals("2")) {//可编程网络
                                    		if (StringUtils.isEmpty(site.getProgrammableInitMaterialArchieveDate())) {
                                    			continue;
                                    		}
                                			if (!StringUtils.isEmpty(startTime)) {
                                				compareFrom = site.getProgrammableEdgeNodeInitMaterialArchieveDate();
                                            }
                                			if (!StringUtils.isEmpty(endTime)) {
                                				compareTo = site.getProgrammableEdgeNodeInitMaterialArchieveDate();
                                            }
                                    	} else if (stationType.equals("3")) {//SDN网络
                                    		if (StringUtils.isEmpty(site.getSdnEdgeNodeInitMaterialArchieveDate())) {
                                    			continue;
                                    		}
                                    		if (!StringUtils.isEmpty(startTime)) {
                                				compareFrom = site.getSdnEdgeNodeInitMaterialArchieveDate();
                                            }
                                			if (!StringUtils.isEmpty(endTime)) {
                                				compareTo = site.getSdnEdgeNodeInitMaterialArchieveDate();
                                            }
                                    	}
                                    	
                                    	if (!StringUtils.isEmpty(startTime)) {
                                            if (StringUtils.isEmpty(compareFrom) || compareFrom.before(from)) {
                                          	    continue;
                                            }
                                        }
                            			if (!StringUtils.isEmpty(endTime)) {
                                            if (StringUtils.isEmpty(compareTo) || compareTo.after(to)) {
                                          	    continue;
                                            }
                                        }
                                    	Integer constructionStatus = site.getBasicNetworkManualSiteStatus();
                                		if (constructionStatus == null) {
                                			constructionStatus = p.getStatus();
                                		}
                                        
                                        mapSiteInfoBean.getMapEdgeSiteList()
                                        	.add(converter.fromEntityPro(stationType,
                                                constructionStatus, site,
                                                this.messageSource, locale));
                                    }

                                }
                            }
                        }
                    }
                }
            } else if (layer.equals("problem")) {// 问题预警

                CommonFilter problemSegFilter = new CommonFilter();
//                problemSegFilter.addAnywhere("planName", "中继段建设计划");


                List<Plan> problemSegmentList = this.planService.list(null, null);

                Project project = null;

                CommonFilter projectFilter = new CommonFilter();
                projectFilter.addExact("projectNum", PROJECT_NUM);

                List<Project> projects = this.projectService.list(projectFilter, null);

                if (!projects.isEmpty()) {
                    project = projects.get(0);

                }
                CommonFilter orgFilter = new CommonFilter();
                orgFilter.addExact("project.id", project.getId());

                if (stationType.equals("1")) {

                    orgFilter.addAnywhere("virtualOrgName", "基础网络");

                    List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                    VirtualOrg v = null;

                    if (!virtualOrgs.isEmpty()) {
                        v = virtualOrgs.get(0);
                        filter = new CommonFilter()
                                .addExact("si.isBasicNetworkTransmitStationType", (short) 1)
                                .addExact("v.id", v.getId());

//                        filter.addAnywhere("planName", "基础网络传输组站点计划_");

                    }

                } else if (stationType.equals("2")) {
                    orgFilter.addAnywhere("virtualOrgName", "可编程");

                    List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                    VirtualOrg v = null;

                    if (!virtualOrgs.isEmpty()) {
                        v = virtualOrgs.get(0);
                        filter = new CommonFilter()
                                .addExact("si.isProgrammableNetworkTransmitStationType", (short) 1)
                                .addExact("v.id", v.getId());

//                        filter.addAnywhere("planName", "可编程网络传输组站点计划_");

                    }
                } else if (stationType.equals("3")) {
                    orgFilter.addAnywhere("virtualOrgName", "SDN");

                    List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                    VirtualOrg v = null;

                    if (!virtualOrgs.isEmpty()) {
                        v = virtualOrgs.get(0);
                        filter = new CommonFilter()
                                .addExact("si.isSdnNetworkTransmitStationType", (short) 1)
                                .addExact("v.id", v.getId());

//                        filter.addAnywhere("planName", "SDN网络传输组站点计划_");

                    }
                }
                if (!StringUtils.isEmpty(area) && !area.equals("0")) {
                    filter.addExact("psp.id", area);
                }
                if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
                    // 设置过滤信息
                    Date from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
                    Date to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);

                    filter.addRange("actualEndDate", FieldValueType.DATE, from, to);
                }

                problemList = this.planService.list(filter, null);


                if ((problemList != null) && (problemList.size() > 0)) {

                    for (Plan plan : problemList) {
                        Site site = plan.getSite();
                        if (site != null) {
                            CommonFilter statusFilter =
                                    new CommonFilter().addExact("p.id", plan.getId());

                            List<OrderByDto> orders = new ArrayList<>();
                            orders.add(new OrderByDto("taskNum", false));

                            List<PlanTask> statusList =
                                    this.planTaskService.list(statusFilter, orders);

                            if ((statusList != null) && (statusList.size() > 0)) {

                                short isWarning = 0;
                                for (int i = 0; i < statusList.size(); i++) {
                                    PlanTask plTask = new PlanTask();

                                    plTask = statusList.get(i);
                                    if (plTask != null) {

                                        if (i == 0) {
                                            if (statusList.get(0).getIsWarning() == null
                                                    || statusList.get(0).getIsWarning() == 0.0) {
                                                isWarning = 0;
                                                break;
                                            }

                                        } else if (statusList.get(i).getIsWarning() == null
                                                || statusList.get(i).getIsWarning() == 0.0) {

                                            isWarning = statusList.get(i - 1).getIsWarning();
                                            if (isWarning == 1) {
                                                mapSiteInfoBean.getMapProblemSiteList()
                                                        .add(converter.fromEntityIssue(stationType,
                                                                isWarning, site, this.messageSource,
                                                                locale));
                                            }
                                            break;

                                        } else if (statusList.get(statusList.size() - 1)
                                                .getIsWarning() != null
                                                && statusList.get(statusList.size() - 1)
                                                        .getIsWarning() != 0) {

                                            isWarning = statusList.get(statusList.size() - 1)
                                                    .getIsWarning();
                                            if (isWarning == 1) {
                                                mapSiteInfoBean.getMapProblemSiteList()
                                                        .add(converter.fromEntityIssue(stationType,
                                                                isWarning, site, this.messageSource,
                                                                locale));
                                            }

                                            break;

                                        }
                                    }


                                }

                            }
                        }

                    }

                    if (stationType.equals("1")) {
                        if ((problemSegmentList != null) && (problemSegmentList.size() > 0)) {

                            for (Plan plan : problemSegmentList) {
                            	if (plan.getSegment() == null) {
                            		continue;
                            	}
                                Segment segment = plan.getSegment();
                                if (segment != null) {
                                    int manualSegmentStatus = 0;
                                    CommonFilter basicStatusFilter =
                                            new CommonFilter().addExact("p.id", plan.getId());

                                    List<OrderByDto> orders = new ArrayList<>();
                                    orders.add(new OrderByDto("taskNum", false));

                                    List<PlanTask> statusList =
                                            this.planTaskService.list(basicStatusFilter, orders);

                                    if ((statusList != null) && (statusList.size() > 0)) {

                                        for (int i = 0; i < statusList.size(); i++) {
                                            PlanTask plTask = new PlanTask();

                                            plTask = statusList.get(i);

                                            if (plTask != null) {

                                                if (i == 0) {
                                                    if (statusList.get(0).getProgress() == null
                                                            || statusList.get(0)
                                                                    .getProgress() == 0.0) {

                                                        manualSegmentStatus = 0;
                                                        break;
                                                    }

                                                } else if (statusList.get(i).getProgress() == null
                                                        || statusList.get(i).getProgress() == 0) {

                                                    if (statusList.get(i - 1)
                                                            .getConstructionStatus() != null) {
                                                        manualSegmentStatus = statusList.get(i - 1)
                                                                .getConstructionStatus();
                                                    } else {
                                                        manualSegmentStatus = 0;
                                                    }
                                                    break;

                                                } else if (statusList.get(statusList.size() - 1)
                                                        .getProgress() != null
                                                        && statusList.get(statusList.size() - 1)
                                                                .getProgress() != 0.0) {

                                                    if (statusList.get(statusList.size() - 1)
                                                            .getConstructionStatus() != null) {
                                                        manualSegmentStatus = statusList
                                                                .get(statusList.size() - 1)
                                                                .getConstructionStatus();
                                                    } else {
                                                        manualSegmentStatus = 0;
                                                    }
                                                    break;

                                                }
                                            }
                                        }

                                    }
                                    if (segment.getEndA() != null && segment.getEndB() != null) {
                                        for (Plan p : problemList) {
                                            Site site = p.getSite();
                                            if (site != null) {
                                                if (segment.getEndA().getStationName()
                                                        .equals(site.getStationName())
                                                        || segment.getEndB().getStationName()
                                                                .equals(site.getStationName())) {
                                                    isSegment = true;
                                                }
                                            }
                                        }
                                        if (isSegment) {
                                            mapSiteInfoBean.getMapProblemSegmentList()
                                                    .add(converter.fromEntitySeg(layer, segment,
                                                            manualSegmentStatus, this.messageSource,
                                                            locale));
                                            isSegment = false;
                                        }

                                    }
                                }

                            }
                        }
                    }

                    if (stationType.equals("2") | stationType.equals("3")) {
                        if ((linkList != null) && (linkList.size() > 0)) {
                            for (Link link : linkList) {
                                if (link.getEndA() != null && link.getEndB() != null) {
                                    for (Plan plan : problemList) {
                                        if (plan != null) {
                                            Site site = plan.getSite();
                                            if (site != null) {
                                                if (link.getEndA().getStationName()
                                                        .equals(site.getStationName())
                                                        || link.getEndB().getStationName()
                                                                .equals(site.getStationName())) {
                                                    isLink = true;
                                                }
                                            }
                                        }

                                    }
                                    if (isLink) {
                                        mapSiteInfoBean.getMapProblemLinkList().add(converter
                                                .fromEntityLink(link, this.messageSource, locale));
                                        isLink = false;

                                    }
                                }
                            }
                        }
                        // 筛选边缘节点
                        List<String> dataTypeList = new ArrayList<>();
                        dataTypeList.add(DataTypeEnum.EDGE_NODE_TYPE.value());

                        CommonFilter edgeFilter = null;
                        if (stationType.equals("2")) {
                            orgFilter.addAnywhere("virtualOrgName", "可编程");

                            List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                            VirtualOrg v = null;

                            if (!virtualOrgs.isEmpty()) {
                                v = virtualOrgs.get(0);
                                edgeFilter =
                                        new CommonFilter().addWithin("sedge.dataType", dataTypeList)
                                                .addExact("v.id", v.getId());

                                edgeFilter.addAnywhere("planName", "边缘节点");

                            }

                        } else {
                            orgFilter.addAnywhere("virtualOrgName", "SDN");

                            List<VirtualOrg> virtualOrgs = virtualOrgService.list(orgFilter, null);

                            VirtualOrg v = null;

                            if (!virtualOrgs.isEmpty()) {
                                v = virtualOrgs.get(0);
                                edgeFilter =
                                        new CommonFilter().addWithin("sedge.dataType", dataTypeList)
                                                .addExact("v.id", v.getId());

                                edgeFilter.addAnywhere("planName", "边缘节点");

                            }
                        }
                        List<Plan> edgeSiteList = this.planService.list(edgeFilter, null);

                        if ((edgeSiteList != null) && (edgeSiteList.size() > 0)) {
                            for (Plan p : edgeSiteList) {
                                if (p != null) {
                                    Site site = p.getSite();

                                    if (site != null) {
                                        CommonFilter statusFilter =
                                                new CommonFilter().addExact("p.id", p.getId());

                                        List<OrderByDto> orders = new ArrayList<>();
                                        orders.add(new OrderByDto("taskNum", false));

                                        List<PlanTask> statusList =
                                                this.planTaskService.list(statusFilter, orders);

                                        if ((statusList != null) && (statusList.size() > 0)) {

                                            short isWarning = 0;
                                            for (int i = 0; i < statusList.size(); i++) {
                                                PlanTask plTask = new PlanTask();

                                                plTask = statusList.get(i);
                                                if (plTask != null) {

                                                    if (i == 0) {
                                                        if (statusList.get(0).getIsWarning() == null
                                                                || statusList.get(0)
                                                                        .getIsWarning() == 0.0) {
                                                            isWarning = 0;
                                                            break;
                                                        }

                                                    } else if (statusList.get(i)
                                                            .getIsWarning() == null
                                                            || statusList.get(i)
                                                                    .getIsWarning() == 0.0) {

                                                        isWarning = statusList.get(i - 1)
                                                                .getIsWarning();
                                                        if (isWarning == 1) {
                                                            mapSiteInfoBean.getMapEdgeSiteList()
                                                                    .add(converter.fromEntityEdge(
                                                                            stationType, 0,
                                                                            isWarning, site,
                                                                            this.messageSource,
                                                                            locale));
                                                        }
                                                        break;

                                                    } else if (statusList.get(statusList.size() - 1)
                                                            .getIsWarning() != null
                                                            && statusList.get(statusList.size() - 1)
                                                                    .getIsWarning() != 0) {

                                                        isWarning = statusList
                                                                .get(statusList.size() - 1)
                                                                .getIsWarning();
                                                        if (isWarning == 1) {
                                                            mapSiteInfoBean.getMapEdgeSiteList()
                                                                    .add(converter.fromEntityEdge(
                                                                            stationType, 0,
                                                                            isWarning, site,
                                                                            this.messageSource,
                                                                            locale));

                                                        }

                                                        break;

                                                    }
                                                }

                                            }

                                        }
                                    }

                                }
                            }
                        }
                    }

                }
            } else if (layer.equals("server")) {// 服务提供能力

                if (!StringUtils.isEmpty(area) && !area.equals("0")) {
                    filter.addExact("p.id", area);
                }

                if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
                    // 设置过滤信息
                    Date from = DateUtils.string2Date(startTime, Constants.DATE_FORMAT);
                    Date to = DateUtils.string2Date(endTime, Constants.DATE_FORMAT);

                    filter.addRange("cDatetime", FieldValueType.DATE, from, to);
                }

                siteList = this.siteService.list(filter, null);
                if ((siteList != null) && (siteList.size() > 0)) {
                    for (Site site : siteList) {
                        if (site != null) {
                            if (site.getBasicNetworkManualSiteStatus() != null) {
                                if (site.getBasicNetworkManualSiteStatus() == 2) {
                                    mapSiteInfoBean.getMapSiteList()
                                            .add(converter.fromEntity(stationType, site.getId(),
                                                    "server", site, this.messageSource, locale));
                                }
                            } else if (site.getProgrammableNetworkManualSiteStatus() != null) {
                                if (site.getProgrammableNetworkManualSiteStatus() == 2) {
                                    mapSiteInfoBean.getMapSiteList()
                                            .add(converter.fromEntity(stationType, site.getId(),
                                                    "server", site, this.messageSource, locale));
                                }
                            } else if (site.getSdnNetworkManualSiteStatus() != null) {
                                if (site.getSdnNetworkManualSiteStatus() == 2) {
                                    mapSiteInfoBean.getMapSiteList()
                                            .add(converter.fromEntity(stationType, site.getId(),
                                                    "server", site, this.messageSource, locale));
                                }
                            } else if (site.getBasicNetworkManualSiteStatus() != null
                                    && site.getProgrammableNetworkManualSiteStatus() != null) {
                                if (site.getBasicNetworkManualSiteStatus() == 2
                                        && site.getProgrammableNetworkManualSiteStatus() == 2) {
                                    mapSiteInfoBean.getMapSiteList()
                                            .add(converter.fromEntity(stationType, site.getId(),
                                                    "server", site, this.messageSource, locale));
                                }
                            } else if (site.getBasicNetworkManualSiteStatus() != null
                                    && site.getSdnNetworkManualSiteStatus() != null) {
                                if (site.getBasicNetworkManualSiteStatus() == 2
                                        && site.getSdnNetworkManualSiteStatus() == 2) {
                                    mapSiteInfoBean.getMapSiteList()
                                            .add(converter.fromEntity(stationType, site.getId(),
                                                    "server", site, this.messageSource, locale));
                                }
                            } else if (site.getProgrammableNetworkManualSiteStatus() != null
                                    && site.getSdnNetworkManualSiteStatus() != null) {
                                if (site.getProgrammableNetworkManualSiteStatus() == 2
                                        && site.getSdnNetworkManualSiteStatus() == 2) {
                                    mapSiteInfoBean.getMapSiteList()
                                            .add(converter.fromEntity(stationType, site.getId(),
                                                    "server", site, this.messageSource, locale));
                                }
                            } else if (site.getBasicNetworkManualSiteStatus() != null
                                    && site.getProgrammableNetworkManualSiteStatus() != null
                                    && site.getSdnNetworkManualSiteStatus() != null) {
                                if (site.getBasicNetworkManualSiteStatus() == 2
                                        && site.getProgrammableNetworkManualSiteStatus() == 2
                                        && site.getSdnNetworkManualSiteStatus() == 2) {
                                    mapSiteInfoBean.getMapSiteList()
                                            .add(converter.fromEntity(stationType, site.getId(),
                                                    "server", site, this.messageSource, locale));
                                }
                            }
                        }

                    }
                }
            }

            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mapSiteInfoBean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            this.getLogger().error("Exception while getting the site info ", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    // 统计接口
    @RequestMapping("/list-piechart-info")
    @ResponseBody
    public JsonResultBean getPieChartInfo(Model model,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            MapSitePieChartInfoBean mapSitePieChartInfoBean = new MapSitePieChartInfoBean();

            MapSitePieChartListBeanConvert converter = new MapSitePieChartListBeanConvert();

//            int basicCodeUnfinish = 0, basicCodeOngoing = 0, basicCodeFinish = 0;
//            int basicHighCodeUnfinish = 0, basicHighCodeOngoing = 0, basicHighCodeFinish = 0;
//            int basicHighRelayUnfinish = 0, basicHighRelayOngoing = 0, basicHighRelayFinish = 0;
//            int programCoreUnfinish = 0, programCoreOngoing = 0, programCoreFinish = 0;
//            int programEdgeUnfinish = 0, programEdgeOngoing = 0, programEdgeFinish = 0;
//            int sdnCoreUnfinish = 0, sdnCoreOngoing = 0, sdnCoreFinish = 0;
//            int sdnEdgeUnfinish = 0, sdnEdgeOngoing = 0, sdnEdgeFinish = 0;
            
            int[] basicCoreCount = {0, 0, 0};
            int[] basicHighCoreCount = {0, 0, 0};
            int[] basicHighRelayCount = {0, 0, 0};
            int[] programmableCoreCount = {0, 0, 0};
            int[] programmableEdgeCount = {0, 0, 0};
            int[] sdnCoreCount = {0, 0, 0};
            int[] sdnEdgeCount = {0, 0, 0};
            
            // 基础网络组 核心节点统计
            
            // 统计建设计划中各类【未开始、进行中、已完成】的个数
            List<Plan> planList = planService.list(null, null);
            
            for (Plan plan: planList) {
            	
            	int status = plan.getStatus() == null ? 0 : plan.getStatus();
            	
            	Site site = plan.getSite();
            	if (site != null) {
            		
            		if (plan.getPlanName().contains("核心节点")) {
            			
            			// 统计核心节点
            			String stationType = site.getBasicNetworkTransmitStationType();
                		if ("1".equals(stationType)) {
                			
            				VirtualOrg org = plan.getVirtualOrg();
                			if (org.getVirtualOrgName().indexOf("可编程") >= 0) {
            					programmableCoreCount[status]++;
                			}
                			else if (org.getVirtualOrgName().indexOf("SDN") >= 0) {
            					sdnCoreCount[status]++;
                			}
                			else {
                				basicCoreCount[status]++;
                			}
                		}
                		// 统计传输骨干
                		else if ("2".equals(stationType)) {
                			basicHighCoreCount[status]++;
                		}
                		// 统计传输中继
                		else if ("3".equals(stationType)) {
                			basicHighRelayCount[status]++;
                		}
            		} else {
            			
            			// 统计边缘节点：SDN
            			DataOption nodeType = site.getEdgeNodeType();
                		if (nodeType != null) {
                			if (nodeType.getId().equals("EDGE_NODE_TYPE_0")) {
                    			sdnEdgeCount[status]++;
                    		}
                    		// 统计边缘节点：可编程
                    		else if (nodeType.getId().equals("EDGE_NODE_TYPE_1")) {
                    			programmableEdgeCount[status]++;
                    		}
                		}
            		}
            	}
            }
            
            mapSitePieChartInfoBean.getBasicCoreNodeSiteList()
            .add(converter.fromEntity(basicCoreCount[0], basicCoreCount[1], basicCoreCount[2],
                    this.messageSource, locale));

		    mapSitePieChartInfoBean.getBasicHighCoreSiteList()
		            .add(converter.fromEntity(basicHighCoreCount[0], basicHighCoreCount[1],
		                    basicHighCoreCount[2], this.messageSource, locale));
		
		    mapSitePieChartInfoBean.getBasicHighRelaySiteList()
		            .add(converter.fromEntity(basicHighRelayCount[0], basicHighRelayCount[1],
		                    basicHighRelayCount[2], this.messageSource, locale));
		
		    mapSitePieChartInfoBean.getProgrammableCoreSiteList()
		            .add(converter.fromEntity(programmableCoreCount[0], programmableCoreCount[1],
		            		programmableCoreCount[2], this.messageSource, locale));
		
		    mapSitePieChartInfoBean.getProgrammableEdgeSiteList()
		            .add(converter.fromEntity(programmableEdgeCount[0], programmableEdgeCount[1],
		            		programmableEdgeCount[2], this.messageSource, locale));
		
		    mapSitePieChartInfoBean.getSdnCoreSiteList().add(converter.fromEntity(sdnCoreCount[0],
		    		sdnCoreCount[1], sdnCoreCount[2], this.messageSource, locale));
		
		    mapSitePieChartInfoBean.getSdnEdgeSiteList().add(converter.fromEntity(sdnEdgeCount[0],
		    		sdnEdgeCount[1], sdnEdgeCount[2], this.messageSource, locale));

//            List<OrderByDto> orders = new ArrayList<>();
//            orders.add(new OrderByDto("p.id", false));
//            orders.add(new OrderByDto("taskNum", false));

//            List<PlanTask> codeList = this.planTaskService.list(null, orders);
//            
//            
//
//            if ((codeList != null) && (codeList.size() > 0)) {
//                for (int i = 0; i < codeList.size(); i++) {
//                    PlanTask plTask = new PlanTask();
//                    plTask = codeList.get(i);
//                    if (plTask != null) {
//                        int constructionStatus = 0;
//                        Plan plan = plTask.getPlan();
//
//                        if (plan != null) {
//                            newPlanId = plan.getId();
//                            if (i == 0) {
//                                oldPlanId = newPlanId;
//                            }
//
//                            if (!oldPlanId.equals(newPlanId)) {
//                                record = true;
//                            }
//
//                            if (oldPlanId.equals(newPlanId) && record) {
//
//                                if (i == 0) {
//
//                                    if (codeList.get(0).getProgress() == null
//                                            || codeList.get(0).getProgress() == 0.0) {
//                                        constructionStatus = 0;
//                                        search = true;
//                                    }
//
//                                } else if (codeList.get(i).getProgress() == null
//                                        || codeList.get(i).getProgress() == 0.0) {
//
//                                    if (codeList.get(i - 1).getConstructionStatus() != null) {
//                                        constructionStatus =
//                                                codeList.get(i - 1).getConstructionStatus();
//                                    } else {
//                                        constructionStatus = 0;
//                                    }
//
//                                    search = true;
//                                } else if (codeList.get(i).getProgress() != null
//                                        && codeList.get(i).getProgress() != 0.0) {
//                                    if (codeList.get(i).getConstructionStatus() != null) {
//
//                                        constructionStatus =
//                                                codeList.get(i).getConstructionStatus();
//                                    } else {
//                                        constructionStatus = 0;
//                                    }
//                                } else if (codeList.get(codeList.size() - 1).getProgress() != null
//                                        || codeList.get(codeList.size() - 1).getProgress() != 0.0) {
//                                    if (codeList.get(codeList.size() - 1)
//                                            .getConstructionStatus() != null) {
//                                        constructionStatus = codeList.get(codeList.size() - 1)
//                                                .getConstructionStatus();
//
//                                        search = true;
//                                    } else {
//                                        constructionStatus = 0;
//                                        search = true;
//                                    }
//                                }
//                            }
//
//                            if (search) {
//                                Site site = plan.getSite();
//                                if (site != null) {
//                                    // 基础网络组核心站点统计
//                                    if (site.getBasicNetworkTransmitStationType().equals("1")) {
//
//                                        if (constructionStatus == 0) {
//                                            basicCodeUnfinish++;
//                                        } else if (constructionStatus == 1) {
//                                            basicCodeOngoing++;
//                                        } else if (constructionStatus == 2) {
//                                            basicCodeFinish++;
//                                        }
//
//                                    } // 基础网络组高速传输核心站点统计
//                                    if (site.getBasicNetworkTransmitStationType().equals("2")) {
//
//                                        if (constructionStatus == 0) {
//                                            basicHighCodeUnfinish++;
//                                        } else if (constructionStatus == 1) {
//                                            basicHighCodeOngoing++;
//                                        } else if (constructionStatus == 2) {
//                                            basicHighCodeFinish++;
//                                        }
//
//                                    } // 基础网络组高速传输中继站点统计
//                                    if (site.getBasicNetworkTransmitStationType().equals("3")) {
//
//                                        if (constructionStatus == 0) {
//                                            basicHighRelayUnfinish++;
//                                        } else if (constructionStatus == 1) {
//                                            basicHighRelayOngoing++;
//                                        } else if (constructionStatus == 2) {
//                                            basicHighRelayFinish++;
//                                        }
//
//                                    } // 可编程核心站点统计
//                                    if (site.getBasicNetworkTransmitStationType().equals("1")) {
//
//                                        if (constructionStatus == 0) {
//                                            programCoreUnfinish++;
//                                        } else if (constructionStatus == 1) {
//                                            programCoreOngoing++;
//                                        } else if (constructionStatus == 2) {
//                                            programCoreFinish++;
//                                        }
//
//                                    } // 可编程边缘节点统计
//                                    if (site.getEdgeNodeType() != null) {
//
//                                        if (!StringUtils.isEmpty(
//                                                site.getProgrammableNetworkTransmitStationType())) {
//                                            if (constructionStatus == 0) {
//                                                programEdgeUnfinish++;
//                                            } else if (constructionStatus == 1) {
//                                                programEdgeOngoing++;
//                                            } else if (constructionStatus == 2) {
//                                                programEdgeFinish++;
//                                            }
//                                        }
//
//                                    } // sdn核心站点统计
//                                    if (site.getBasicNetworkTransmitStationType().equals("1")) {
//
//                                        if (constructionStatus == 0) {
//                                            sdnCoreUnfinish++;
//                                        } else if (constructionStatus == 1) {
//                                            sdnCoreOngoing++;
//                                        } else if (constructionStatus == 2) {
//                                            sdnCoreFinish++;
//                                        }
//
//                                    } // sdn边缘节点统计
//                                    if (site.getEdgeNodeType() != null) {
//
//                                        if (!StringUtils
//                                                .isEmpty(site.getSdnNetworkTransmitStationType())) {
//                                            if (constructionStatus == 0) {
//                                                sdnEdgeUnfinish++;
//                                            } else if (constructionStatus == 1) {
//                                                sdnEdgeOngoing++;
//                                            } else if (constructionStatus == 2) {
//                                                sdnEdgeFinish++;
//                                            }
//                                        }
//                                    }
//                                    search = false;
//                                    record = false;
//                                }
//
//                            }
//                            oldPlanId = newPlanId;
//                        }
//
//                    }
//                }
//            }

//            mapSitePieChartInfoBean.getBasicCoreNodeSiteList()
//                    .add(converter.fromEntity(basicCodeUnfinish, basicCodeOngoing, basicCodeFinish,
//                            this.messageSource, locale));
//
//            mapSitePieChartInfoBean.getBasicHighCoreSiteList()
//                    .add(converter.fromEntity(basicHighCodeUnfinish, basicHighCodeOngoing,
//                            basicHighCodeFinish, this.messageSource, locale));
//
//            mapSitePieChartInfoBean.getBasicHighRelaySiteList()
//                    .add(converter.fromEntity(basicHighRelayUnfinish, basicHighRelayOngoing,
//                            basicHighRelayFinish, this.messageSource, locale));
//
//            mapSitePieChartInfoBean.getProgrammableCoreSiteList()
//                    .add(converter.fromEntity(programCoreUnfinish, programCoreOngoing,
//                            programCoreFinish, this.messageSource, locale));
//
//            mapSitePieChartInfoBean.getProgrammableEdgeSiteList()
//                    .add(converter.fromEntity(programEdgeUnfinish, programEdgeOngoing,
//                            programEdgeFinish, this.messageSource, locale));
//
//            mapSitePieChartInfoBean.getSdnCoreSiteList().add(converter.fromEntity(sdnCoreUnfinish,
//                    sdnCoreOngoing, sdnCoreFinish, this.messageSource, locale));
//
//            mapSitePieChartInfoBean.getSdnEdgeSiteList().add(converter.fromEntity(sdnEdgeUnfinish,
//                    sdnEdgeOngoing, sdnEdgeFinish, this.messageSource, locale));
//
//            basicCodeUnfinish = 0;
//            basicCodeOngoing = 0;
//            basicCodeFinish = 0;
//            basicHighCodeUnfinish = 0;
//            basicHighCodeOngoing = 0;
//            basicHighCodeFinish = 0;
//            basicHighRelayUnfinish = 0;
//            basicHighRelayOngoing = 0;
//            basicHighRelayFinish = 0;
//            programCoreUnfinish = 0;
//            programCoreOngoing = 0;
//            programCoreFinish = 0;
//            programEdgeUnfinish = 0;
//            programEdgeOngoing = 0;
//            programEdgeFinish = 0;
//            sdnCoreUnfinish = 0;
//            sdnCoreOngoing = 0;
//            sdnCoreFinish = 0;
//            sdnEdgeUnfinish = 0;
//            sdnEdgeOngoing = 0;
//            sdnEdgeFinish = 0;


            // 基础网络组 高速传输核心节点统计
            // CommonFilter basicHighCodeUnfinishFilter =
            // new CommonFilter().addExact("si.isBasicNetworkTransmitStationType", (short) 1)
            // .addExact("si.basicNetworkTransmitStationType", "2")
            // .addExact("si.basicNetworkManualSiteStatus", 0);
            // List<Plan> highCodeUnfinishList =
            // this.planService.list(basicHighCodeUnfinishFilter, null);
            //
            // CommonFilter basicHighCodeOngoingFilter =
            // new CommonFilter().addExact("si.isBasicNetworkTransmitStationType", (short) 1)
            // .addExact("si.basicNetworkTransmitStationType", "2")
            // .addExact("si.basicNetworkManualSiteStatus", 1);
            // List<Plan> highCodeOngoingList =
            // this.planService.list(basicHighCodeOngoingFilter, null);
            //
            // CommonFilter basicHighCodeFinishFilter =
            // new CommonFilter().addExact("si.isBasicNetworkTransmitStationType", (short) 1)
            // .addExact("si.basicNetworkTransmitStationType", "2")
            // .addExact("si.basicNetworkManualSiteStatus", 2);
            // List<Plan> highCodeFinishList = this.planService.list(basicHighCodeFinishFilter,
            // null);


            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, mapSitePieChartInfoBean);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);

        } catch (Exception e) {
            this.getLogger().error("Exception while getting the site info ", e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }


    @Override
    protected void beforeCreate(Site site, SiteBean bean, Map<String, Object> paramMap)
            throws Exception {

        if (StringUtils.isEmpty(site.getStationName())) {
            return;
        }

        CommonFilter filter = null;
        filter = new CommonFilter().addExact("stationName", site.getStationName());

        List<Site> delSites = this.siteService.list(filter, null);
        if ((delSites != null) && (delSites.size() > 0)) {
            ArrayList<String> delSiteids = new ArrayList<String>();
            for (Site s : delSites) {
                delSiteids.add(s.getId());
            }

            if (delSiteids.size() > 0) {
                filter = new CommonFilter().addWithin("si.id", delSiteids);
                List<Plan> delPlans = this.planService.list(filter, null);

                if ((delPlans != null) && (delPlans.size() > 0)) {

                    ArrayList<String> delPlanids = new ArrayList<String>();
                    for (Plan p : delPlans) {
                        delPlanids.add(p.getId());
                    }

                    this.planService.delete(delPlanids);
                }

                this.siteService.delete(delSiteids);
            }
        }

    }
    
    private void regeneratePlanData(Site site, String planStartName, SiteBean bean, Map<String, Object> paramMap, Locale locale) {
    	
    	Date now = site.getcDatetime();

        String planName;

        CommonFilter filter = null;
        
        // 仅仅在是核心站点的前提下才会生成
        if ("1".equals(bean.getBasicNetworkTransmitStationType())) {
        	
        	if (planStartName.equals("基础网络")) {
//                if (bean.getIsBasicNetworkTransmitStationType() == 1) {
                    // 基础网络组计划
                    filter = new CommonFilter();
                    filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
                    filter.addExact("isSiteTask", OnOffEnum.ON.value());

                    // planName = "基础网络传输组站点计划_" + bean.getStationName() + "_"
                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                    planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            site.getCreator(), 1);
//                }
            }

            if (planStartName.equals("可编程")) {
//                if (bean.getIsProgrammableNetworkTransmitStationType() == 1) {
                    // 可编程组计划
                    filter = new CommonFilter();
                    filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
                    // planName = "可编程网络传输组站点计划_" + bean.getStationName() + "_"
                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                    planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            site.getCreator(), 2);
//                }
            }

            if (planStartName.equals("SDN")) {
//                if (bean.getIsSdnNetworkTransmitStationType() == 1) {
                    // sdn组计划
                    filter = new CommonFilter();
                    filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
                    // planName = "SDN网络传输组站点计划_" + bean.getStationName() + "_"
                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                    planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            site.getCreator(), 3);
//                }
            }
        } 
        // 非核心节点，则只生成基础
        else {
        	
        	if (planStartName.equals("基础网络")) {
//                if (bean.getIsBasicNetworkTransmitStationType() == 1) {
                    // 基础网络组计划
                    filter = new CommonFilter();
                    filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
                    filter.addExact("isSiteTask", OnOffEnum.ON.value());

                    // planName = "基础网络传输组站点计划_" + bean.getStationName() + "_"
                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                    planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            site.getCreator(), 1);
//                }
            }
        }

        if (!StringUtils.isEmpty(bean.getEdgeNodeType_())) {
            filter = new CommonFilter();
            if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_0") && planStartName.equals("SDN")) {
                filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
                // planName = "SDN边缘节点站点计划_" + bean.getStationName() + "_"
                // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                planName = bean.getStationName() + "_边缘节点_建设计划";
                filter.addExact("isSiteTask", OnOffEnum.ON.value());
                this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                        site.getCreator(), 3);
            }

            if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_1") && planStartName.equals("可编程")) {
                filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
                // planName = "可编程边缘节点站点计划_" + bean.getStationName() + "_"
                // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                planName = bean.getStationName() + "_边缘节点_建设计划";
                filter.addExact("isSiteTask", OnOffEnum.ON.value());
                this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                        site.getCreator(), 2);
            }

        }
        
        // 重新生成站点的进展计划
        planApiService.updateSiteProgress(site.getId());
    }

    @Override
    protected void postCreate(Site site, SiteBean bean, Map<String, Object> paramMap, Locale locale) {

        // Date date = site.getcDatetime();
        //
        // String planName = "站点_" + DateUtils.date2String(date,
        // Constants.FILE_DATE_TIME_CSV_FORMAT);
        //
        // CommonFilter filter = new CommonFilter();
        // filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
        // filter.addExact("isSiteTask", OnOffEnum.ON.value());
        //
        // this.presetTaskProducer.generatePlanTask(site, planName, filter, date,
        // site.getCreator());

        Date now = site.getcDatetime();

        String planName;

        CommonFilter filter = null;
        
        // 仅仅在是核心站点的前提下才会生成
        if ("1".equals(bean.getBasicNetworkTransmitStationType())) {
        	
//        	if (bean.getIsBasicNetworkTransmitStationType() != null) {
//                if (bean.getIsBasicNetworkTransmitStationType() == 1) {
                    // 基础网络组计划
                    filter = new CommonFilter();
                    filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
                    filter.addExact("isSiteTask", OnOffEnum.ON.value());

                    // planName = "基础网络传输组站点计划_" + bean.getStationName() + "_"
                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                    planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            site.getCreator(), 1);
//                }
//            }

//            if (bean.getIsProgrammableNetworkTransmitStationType() != null) {
//                if (bean.getIsProgrammableNetworkTransmitStationType() == 1) {
                    // 可编程组计划
                    filter = new CommonFilter();
                    filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
                    // planName = "可编程网络传输组站点计划_" + bean.getStationName() + "_"
                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                    planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            site.getCreator(), 2);
//                }
//            }

//            if (bean.getIsSdnNetworkTransmitStationType() != null) {
//                if (bean.getIsSdnNetworkTransmitStationType() == 1) {
                    // sdn组计划
                    filter = new CommonFilter();
                    filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
                    // planName = "SDN网络传输组站点计划_" + bean.getStationName() + "_"
                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                    planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            site.getCreator(), 3);
//                }
//            }
        }
        // 非核心节点，则只生成基础
        else if (!"4".equals(bean.getBasicNetworkTransmitStationType())) {
        	
//        	if (bean.getIsBasicNetworkTransmitStationType() != null) {
//                if (bean.getIsBasicNetworkTransmitStationType() == 1) {
                    // 基础网络组计划
                    filter = new CommonFilter();
                    filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
                    filter.addExact("isSiteTask", OnOffEnum.ON.value());

                    // planName = "基础网络传输组站点计划_" + bean.getStationName() + "_"
                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                    planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            site.getCreator(), 1);
//                }
//            }
        }

        if (!StringUtils.isEmpty(bean.getEdgeNodeType_())) {
            filter = new CommonFilter();
            if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_0")) {
                filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
                // planName = "SDN边缘节点站点计划_" + bean.getStationName() + "_"
                // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                planName = bean.getStationName() + "_边缘节点_建设计划";
                filter.addExact("isSiteTask", OnOffEnum.ON.value());
                this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                        site.getCreator(), 3);
            }

            if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_1")) {
                filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
                // planName = "可编程边缘节点站点计划_" + bean.getStationName() + "_"
                // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                planName = bean.getStationName() + "_边缘节点_建设计划";
                filter.addExact("isSiteTask", OnOffEnum.ON.value());
                this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                        site.getCreator(), 2);
            }

        }
        
        // 重新生成站点的进展计划
        planApiService.updateSiteProgress(site.getId());
    }

    @Override
    protected void postUpdate(Site site, Site oldSite, SiteBean bean, Map<String, Object> paramMap)
            {

    	// 本方法内的注释说明：将重新生成计划任务的处理注释掉，更新站点是不需要重新生成，在界面的【重新生成】功能中实现 
    	
        Date now = site.getcDatetime();
        String planName;
        CommonFilter filter = null;

        if (bean.getIsBasicNetworkTransmitStationType() != null) {
            if (bean.getIsBasicNetworkTransmitStationType() == 1) {
//                // 基础网络组计划
//                filter = new CommonFilter();
//
//                // planName = "基础网络传输组站点计划_" + oldSite.getStationName() + "_";
//                planName = "基础网络传输组站点计划_" + oldSite.getStationName();
//                filter.addAnywhere("planName", planName);
//                filter.addExact("si.id", site.getId());
//
//                List<Plan> plans = this.planService.list(filter, null);
//
//                if ((plans != null) && (plans.size() > 0)) {
//
//                    if ((plans != null) && (plans.size() > 0)) {
//
//                        ArrayList<String> delPlanids = new ArrayList<String>();
//                        for (Plan p : plans) {
//                            delPlanids.add(p.getId());
//                        }
//
//                        this.planService.delete(delPlanids);
//                    }
//
//                    filter = new CommonFilter();
//                    filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
//                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//
//                    // planName = "基础网络传输组站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "基础网络传输组站点计划_" + bean.getStationName();
//                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
//                            site.getCreator());
//
//                } else {
//
//                    filter = new CommonFilter();
//                    filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
//                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//
//                    // planName = "基础网络传输组站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "基础网络传输组站点计划_" + bean.getStationName();
//                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
//                            site.getCreator());
//                }

            } else {

                filter = new CommonFilter();

                // planName = "基础网络传输组站点计划_" + oldSite.getStationName() + "_";
                planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                filter.addAnywhere("planName", planName);
                filter.addExact("si.id", site.getId());

                List<Plan> delPlans = this.planService.list(filter, null);
                if ((delPlans != null) && (delPlans.size() > 0)) {

                    ArrayList<String> delPlanids = new ArrayList<String>();
                    for (Plan p : delPlans) {
                        delPlanids.add(p.getId());
                    }

                    this.planService.delete(delPlanids);
                }

            }
        }

        if (bean.getIsProgrammableNetworkTransmitStationType() != null) {
            if (bean.getIsProgrammableNetworkTransmitStationType() == 1) {
//                // 可编程组计划
//                filter = new CommonFilter();
//
//                // planName = "可编程网络传输组站点计划_" + oldSite.getStationName() + "_";
//                planName = "可编程网络传输组站点计划_" + oldSite.getStationName();
//                filter.addAnywhere("planName", planName);
//                filter.addExact("si.id", site.getId());
//
//                List<Plan> plans = this.planService.list(filter, null);
//
//                if ((plans != null) && (plans.size() > 0)) {
//
//                    if ((plans != null) && (plans.size() > 0)) {
//
//                        ArrayList<String> delPlanids = new ArrayList<String>();
//                        for (Plan p : plans) {
//                            delPlanids.add(p.getId());
//                        }
//
//                        this.planService.delete(delPlanids);
//                    }
//
//                    filter = new CommonFilter();
//                    filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
//                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                    // planName = "可编程网络传输组站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "可编程网络传输组站点计划_" + bean.getStationName();
//                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
//                            site.getCreator());
//
//                } else {
//
//                    filter = new CommonFilter();
//                    filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
//                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                    // planName = "可编程网络传输组站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "可编程网络传输组站点计划_" + bean.getStationName();
//                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
//                            site.getCreator());
//
//                }

            } else {

                filter = new CommonFilter();

                // planName = "可编程网络传输组站点计划_" + bean.getStationName() + "_";
                planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                filter.addAnywhere("planName", planName);
                filter.addExact("si.id", site.getId());

                List<Plan> delPlans = this.planService.list(filter, null);
                if ((delPlans != null) && (delPlans.size() > 0)) {

                    ArrayList<String> delPlanids = new ArrayList<String>();
                    for (Plan p : delPlans) {
                        delPlanids.add(p.getId());
                    }

                    this.planService.delete(delPlanids);
                }

            }
        }

        if (bean.getIsSdnNetworkTransmitStationType() != null) {
            if (bean.getIsSdnNetworkTransmitStationType() == 1) {
                // sdn组计划
                filter = new CommonFilter();

                // planName = "SDN网络传输组站点计划_" + oldSite.getStationName() + "_";
                planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                filter.addAnywhere("planName", planName);
                filter.addExact("si.id", site.getId());

                List<Plan> plans = this.planService.list(filter, null);

                if ((plans != null) && (plans.size() > 0)) {

                    if ((plans != null) && (plans.size() > 0)) {

                        ArrayList<String> delPlanids = new ArrayList<String>();
                        for (Plan p : plans) {
                            delPlanids.add(p.getId());
                        }

                        this.planService.delete(delPlanids);
                    }

//                    filter = new CommonFilter();
//                    filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
//                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                    // planName = "SDN网络传输组站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "SDN网络传输组站点计划_" + bean.getStationName();
//                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
//                            site.getCreator());

                } else {

//                    filter = new CommonFilter();
//                    filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
//                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                    // planName = "SDN网络传输组站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "SDN网络传输组站点计划_" + bean.getStationName();
//                    this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
//                            site.getCreator());
                }

            } else {

                filter = new CommonFilter();

                // planName = "SDN网络传输组站点计划_" + bean.getStationName() + "_";
                planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
                filter.addAnywhere("planName", planName);
                filter.addExact("si.id", site.getId());

                List<Plan> delPlans = this.planService.list(filter, null);
                if ((delPlans != null) && (delPlans.size() > 0)) {

                    ArrayList<String> delPlanids = new ArrayList<String>();
                    for (Plan p : delPlans) {
                        delPlanids.add(p.getId());
                    }

                    this.planService.delete(delPlanids);
                }
            }
        }


        if (!StringUtils.isEmpty(bean.getEdgeNodeType_())) {
            // sdn组计划
            filter = new CommonFilter();

            // planName = "边缘节点站点计划_" + oldSite.getStationName() + "_";
            planName = bean.getStationName() + "_边缘节点_建设计划";
            filter.addAnywhere("planName", planName);
            filter.addExact("si.id", site.getId());

            List<Plan> plans = this.planService.list(filter, null);

            if ((plans != null) && (plans.size() > 0)) {

                if ((plans != null) && (plans.size() > 0)) {

                    ArrayList<String> delPlanids = new ArrayList<String>();
                    for (Plan p : plans) {
                        delPlanids.add(p.getId());
                    }

                    this.planService.delete(delPlanids);
                }
//
//                filter = new CommonFilter();
//                if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_0")) {
//                    filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
//                    // planName = "SDN边缘节点站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "SDN边缘节点站点计划_" + bean.getStationName();
//                }
//
//                if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_1")) {
//                    filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
//                    // planName = "可编程边缘节点站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "可编程边缘节点站点计划_" + bean.getStationName();
//                }
//
//                filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
//                        site.getCreator());

            } else {

//                filter = new CommonFilter();
//                if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_0")) {
//                    filter.addExact("projectGroup", ProjectGroupEnum.SDN_NETWORK.value());
//                    // planName = "SDN边缘节点站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "SDN边缘节点站点计划_" + bean.getStationName();
//                }
//
//                if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_1")) {
//                    filter.addExact("projectGroup", ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
//                    // planName = "可编程边缘节点站点计划_" + bean.getStationName() + "_"
//                    // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
//                    planName = "可编程边缘节点站点计划_" + bean.getStationName();
//                }
//
//                filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
//                        site.getCreator());
            }

        } else {

            filter = new CommonFilter();

            // planName = "边缘节点站点计划_" + bean.getStationName() + "_";
            planName = bean.getStationName() + "_" + bean.getBasicNetworkTransmitStationTypeText() + "_建设计划";
            filter.addAnywhere("planName", planName);
            filter.addExact("si.id", site.getId());

            List<Plan> delPlans = this.planService.list(filter, null);
            if ((delPlans != null) && (delPlans.size() > 0)) {

                ArrayList<String> delPlanids = new ArrayList<String>();
                for (Plan p : delPlans) {
                    delPlanids.add(p.getId());
                }

                this.planService.delete(delPlanids);
            }
        }


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
     * 生成站点列表对应的Excel文件
     * @return
     */
    private List<File> createExportFiles(Locale locale) {
    	
    	List<File> exportFileList = new ArrayList<>();
    	
    	// 根据ID取得站点列表
    	CommonFilter filter = new CommonFilter();
    	List<Site> siteList = siteService.list(filter, null);
    	
    	// 导出为Excel
    	ExcelExporter<Site> exporter = new ExcelExporter<>();
    	String title = "局站导入模板";
    	String tempFolderPath = this.context.getRealPath("/站点信息导出_") +  DateUtils.date2String(new Date(), Constants.FILE_DATE_TIME_FORMAT) + ".xlsx";
    	List<ExcelField> fieldList = this.getExportFieldList(locale);
    	
		File exportFile = exporter.export(siteList, fieldList, tempFolderPath, title);
		exportFileList.add(exportFile);
    	
    	return exportFileList;
    }
    
    /**
     * 取得导出的字段定义列表
     * @return
     */
    private List<ExcelField> getExportFieldList(Locale locale) {
    	
    	List<ExcelField> fieldList = new ArrayList<>();
    	
    	HashMap<Integer, String> stationTypeEnumMap = new HashMap<>();
    	stationTypeEnumMap.put(1, this.messageSource.getMessage("site.type.core.node", null, locale));
    	stationTypeEnumMap.put(2, this.messageSource.getMessage("site.type.high.speed.core.node", null, locale));
    	stationTypeEnumMap.put(3, this.messageSource.getMessage("site.type.high.speed.relay.node", null, locale));
    	stationTypeEnumMap.put(4, this.messageSource.getMessage("site.type.edge.node", null, locale));
    	
    	fieldList.add(new ExcelField("主键", "id"));
    	fieldList.add(new ExcelField("省份基础网络组录入", "province.provinceName"));
    	fieldList.add(new ExcelField("城市", "city.cityName"));
    	fieldList.add(new ExcelField("初设编号", "initNum"));
    	fieldList.add(new ExcelField("初步设计文件局站名称", "initDesignFileStationName"));
    	fieldList.add(new ExcelField("施工图纸编号", "constructionDrawingDesignNum"));
    	fieldList.add(new ExcelField("局站名称", "stationName"));
    	fieldList.add(new ExcelField("机房业主单位类型", "networkRoomOwnerUnitType.optionName"));
    	fieldList.add(new ExcelField("机房地址", "networkRoomAddress"));
    	
    	fieldList.add(new ExcelField("是否基础网络组站点", "isBasicNetworkTransmitStationType", ExcelField.TYPE_BOOLEAN));   // Cell10
    	fieldList.add(new ExcelField("是否SDN组站点", "isSdnNetworkTransmitStationType", ExcelField.TYPE_BOOLEAN));
    	fieldList.add(new ExcelField("是否可编程站点", "isProgrammableNetworkTransmitStationType", ExcelField.TYPE_BOOLEAN));
    	fieldList.add(new ExcelField("基础网络组站点设备类型", "basicNetworkTransmitStationType", stationTypeEnumMap));
    	fieldList.add(new ExcelField("SDN组站点设备类型", "sdnNetworkTransmitStationType"));
    	fieldList.add(new ExcelField("可编程设备站型", "programmableNetworkTransmitStationType"));
    	fieldList.add(new ExcelField("可编程站型", "programmableNetworkTransmitStationType"));
    	fieldList.add(new ExcelField("SDN站型", "sdnNetworkTransmitStationType"));
    	fieldList.add(new ExcelField("边缘节点类型", "edgeNodeType.optionName"));
    	
    	fieldList.add(new ExcelField("光层机架（600*300）", "opticalLayerRack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("M24电光混机架（600*300）", "m24Rack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("U64电层机架（900*600）", "u64Rack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("U32电层机架（600*300）", "u32Rack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("传输设备总功耗（W）", "transmitDeviceTotalPower", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("传输设备63A直流熔丝需求数量", "transmitDevice63aReqAmount", ExcelField.TYPE_NUMBER));
    	
    	fieldList.add(new ExcelField("可编程核心机柜（600*1000）", "programmableRack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("可编程核心单机架功耗（W）", "programmableRackPower", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("SDN核心机柜（600*1000）", "sdnRack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("SDN核心单机柜功耗（W）", "sdnRackPower", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("边缘节点类型", "edgeNodeType.optionName"));
    	fieldList.add(new ExcelField("边缘设备机柜（600*1000）", "edgeNodeRack", ExcelField.TYPE_NUMBER));
//    	fieldList.add(new ExcelField("边缘设备单机架功耗（W）", "edgeNodeRackPower", ExcelField.TYPE_NUMBER));
    	
    	fieldList.add(new ExcelField("直连点传输机柜（600*300）", "directConnNodeTransmitRack", ExcelField.TYPE_NUMBER));
//    	fieldList.add(new ExcelField("直连点传输设备功耗（W）", "directConnNodeDataDevicePower", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("直联点传输设备63A直流熔丝需求数量", "directConnNode63aReqAmount", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("直连点路由器机柜（600*1000）", "directConnNodeRouterRack", ExcelField.TYPE_NUMBER));
//    	fieldList.add(new ExcelField("直连点数据功耗（W）", "directConnNodeDataDevicePower", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("信通院机柜（600*1000）", "caictRack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("信通院设备总功耗（W）", "caictDeviceTotalPower", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("信通院设备32A（2P）交流熔丝数量", "caictDevice32aAmount", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("综合柜（600*1000）", "compRack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("综合柜总功耗（W）", "compRackTotalPower", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("综合柜32A（2P）交流熔丝数量", "compRack32aAmount", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("其他设备（ODF架）数量600mm*300mm", "otherDevice", ExcelField.TYPE_NUMBER));
    	
    	fieldList.add(new ExcelField("备品备件柜数量", "spareRack", ExcelField.TYPE_NUMBER));
    	fieldList.add(new ExcelField("机房联系人及职务", "networkRoomContactTitle"));
    	fieldList.add(new ExcelField("联系方式", "networkRoomContactMobile"));
    	fieldList.add(new ExcelField("环评登记完成情况", "eiaRegister"));
    	fieldList.add(new ExcelField("商请函回函情况", "businessInvitationReply"));
    	fieldList.add(new ExcelField("框架协议签订关联任务备忘录", "frameworkAgreementRemark"));
    	
    	fieldList.add(new ExcelField("初验日期", "initMaterialArchieveDate"));
    	fieldList.add(new ExcelField("机房整改情况", "networkRoomImprove"));
    	fieldList.add(new ExcelField("国家验收", "nationalAcceptance"));
    	fieldList.add(new ExcelField("经度", "longitude"));
    	fieldList.add(new ExcelField("纬度", "latitude"));
    	fieldList.add(new ExcelField("节点负责人", "personInCharge.personName"));
    	
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
                    List<SiteBean> beans = this.parseExcelFileToSiteBeans(tempFile, locale);

                    if (!beans.isEmpty()) {
                        SiteBeanConverter converter = new SiteBeanConverter();

                        // Date now = new Date();
                        //
                        // String planName = "站点_"
                        // + DateUtils.date2String(now, Constants.FILE_DATE_TIME_CSV_FORMAT);
                        //
                        // CommonFilter filter = new CommonFilter();
                        // filter.addExact("projectGroup", ProjectGroupEnum.BASIC_NETWORK.value());
                        // filter.addExact("isSiteTask", OnOffEnum.ON.value());

                        Date now = new Date();

                        String planName;

                        CommonFilter filter = null;

                        for (SiteBean bean : beans) {
                            Site site = converter.toEntity(bean, null, userBean, now);

                            if (StringUtils.isEmpty(site.getStationName())) {
                                continue;
                            }

                            filter = new CommonFilter().addExact("stationName", site.getStationName());
                            
                            // 注释说明：导入功能只导入不存在的站点且不生成建设计划
                            
//                            List<Site> delSites = this.siteService.list(filter, null);
//                            if ((delSites != null) && (delSites.size() > 0)) {
//                                ArrayList<String> delSiteids = new ArrayList<String>();
//                                for (Site s : delSites) {
//                                    delSiteids.add(s.getId());
//                                }
//
//                                if (delSiteids.size() > 0) {
//                                    filter = new CommonFilter().addWithin("si.id", delSiteids);
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
//                                    this.siteService.delete(delSiteids);
//                                }
//                            }
                            
                            List<Site> exsitSiteList = this.siteService.list(filter, null);
                            if (exsitSiteList.size() == 0) {
                            	this.siteService.create(site);
                            }

//                            if (bean.getIsBasicNetworkTransmitStationType() != null) {
//                                if (bean.getIsBasicNetworkTransmitStationType() == 1) {
//                                    // 基础网络组计划
//                                    filter = new CommonFilter();
//                                    filter.addExact("projectGroup",
//                                            ProjectGroupEnum.BASIC_NETWORK.value());
//                                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//
//                                    // planName = "基础网络传输组站点计划_" + bean.getStationName() + "_"
//                                    // + DateUtils.date2String(now,
//                                    // Constants.FILE_DATE_TIME_CSV_FORMAT);
//                                    planName = "基础网络传输组站点计划_" + bean.getStationName();
//                                    this.presetTaskProducer.generatePlanTask(site, planName, filter,
//                                            now, site.getCreator());
//                                }
//                            }
//
//                            if (bean.getIsProgrammableNetworkTransmitStationType() != null) {
//                                if (bean.getIsProgrammableNetworkTransmitStationType() == 1) {
//                                    // 可编程组计划
//                                    filter = new CommonFilter();
//                                    filter.addExact("projectGroup",
//                                            ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
//                                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                                    // planName = "可编程网络传输组站点计划_" + bean.getStationName() + "_"
//                                    // + DateUtils.date2String(now,
//                                    // Constants.FILE_DATE_TIME_CSV_FORMAT);
//                                    planName = "可编程网络传输组站点计划_" + bean.getStationName();
//                                    this.presetTaskProducer.generatePlanTask(site, planName, filter,
//                                            now, site.getCreator());
//                                }
//                            }
//
//                            if (bean.getIsSdnNetworkTransmitStationType() != null) {
//                                if (bean.getIsSdnNetworkTransmitStationType() == 1) {
//                                    // sdn组计划
//                                    filter = new CommonFilter();
//                                    filter.addExact("projectGroup",
//                                            ProjectGroupEnum.SDN_NETWORK.value());
//                                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                                    // planName = "SDN网络传输组站点计划_" + bean.getStationName() + "_"
//                                    // + DateUtils.date2String(now,
//                                    // Constants.FILE_DATE_TIME_CSV_FORMAT);
//                                    planName = "SDN网络传输组站点计划_" + bean.getStationName();
//                                    this.presetTaskProducer.generatePlanTask(site, planName, filter,
//                                            now, site.getCreator());
//                                }
//                            }
//
//                            if (!StringUtils.isEmpty(bean.getEdgeNodeType_())) {
//
//                                filter = new CommonFilter();
//                                if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_0")) {
//                                    filter.addExact("projectGroup",
//                                            ProjectGroupEnum.SDN_NETWORK.value());
//                                    // planName = "SDN边缘节点站点计划_" + bean.getStationName() + "_"
//                                    // + DateUtils.date2String(now,
//                                    // Constants.FILE_DATE_TIME_CSV_FORMAT);
//                                    planName = "SDN边缘节点站点计划_" + bean.getStationName();
//                                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                                    this.presetTaskProducer.generatePlanTask(site, planName, filter,
//                                            now, site.getCreator());
//                                }
//
//                                if (bean.getEdgeNodeType_().equals("EDGE_NODE_TYPE_1")) {
//                                    filter.addExact("projectGroup",
//                                            ProjectGroupEnum.PROGRAMMABLE_NETWORK.value());
//                                    // planName = "可编程边缘节点站点计划_" + bean.getStationName() + "_"
//                                    // + DateUtils.date2String(now,
//                                    // Constants.FILE_DATE_TIME_CSV_FORMAT);
//                                    planName = "可编程边缘节点站点计划_" + bean.getStationName();
//                                    filter.addExact("isSiteTask", OnOffEnum.ON.value());
//                                    this.presetTaskProducer.generatePlanTask(site, planName, filter,
//                                            now, site.getCreator());
//                                }
//                            }


                            // this.presetTaskProducer.generatePlanTask(site, planName, filter, now,
                            // site.getCreator());
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
        } catch (ExcelParseException e) {
            logger.warn("Excel数据解析错误！", e);
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage("ParseError");
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_OBJECT, e.getDetails());
        } catch (Exception e) {
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

    private List<SiteBean> parseExcelFileToSiteBeans(File tempFile, Locale locale)
            throws FileNotFoundException, IOException, ExcelParseException {

        List<SiteBean> beans = new ArrayList<>();

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
                        SiteBean bean = new SiteBean();

                        // if (row == null ||
                        // StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(0)))
                        // || StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(1)))
                        // || StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(2)))) {
                        // continue;
                        // }

                        if (row == null
                                || StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(1)))
                                || StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(2)))) {
                            continue;
                        }

                        // TODO
                        // bean.setId(ExcelUtils.getCellValue(row.getCell(0)));
                        bean.setId(UUID.randomUUID().toString());

                        CommonFilter provinceFilter = new CommonFilter();
                        provinceFilter.addAnywhere("provinceName",
                                ExcelUtils.getCellValue(row.getCell(1)).trim());

                        List<Province> provinces = this.provinceService.list(provinceFilter, null);
                        if (!provinces.isEmpty()) {
                            bean.setProvince_(provinces.get(0).getId());
                            bean.setProvinceText(ExcelUtils.getCellValue(row.getCell(1)));
                        }

                        CommonFilter cityFilter = new CommonFilter();
                        cityFilter.addAnywhere("cityName",
                                ExcelUtils.getCellValue(row.getCell(2)).trim());

                        List<City> cities = this.cityService.list(cityFilter, null);

                        if (!cities.isEmpty()) {
                            bean.setCity_(cities.get(0).getId());
                            bean.setCityText(ExcelUtils.getCellValue(row.getCell(2)));
                        }

                        bean.setInitNum(ExcelUtils.getCellValue(row.getCell(3)));
                        bean.setInitDesignFileStationName(ExcelUtils.getCellValue(row.getCell(4)));
                        bean.setConstructionDrawingDesignNum(
                                ExcelUtils.getCellValue(row.getCell(5)));
                        bean.setStationName(ExcelUtils.getCellValue(row.getCell(6)));


                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(7)))) {
                            CommonFilter f = new CommonFilter();
                            f.addExact("optionName", ExcelUtils.getCellValue(row.getCell(7)));
                            List<DataOption> networkRoomOwnerUnitTypeList =
                                    this.dataOptionService.list(f, null);
                            if ((networkRoomOwnerUnitTypeList != null)
                                    && (networkRoomOwnerUnitTypeList.size() > 0)) {
                                bean.setNetworkRoomOwnerUnitTypeText(
                                        networkRoomOwnerUnitTypeList.get(0).getOptionName());
                                bean.setNetworkRoomOwnerUnitType_(
                                        networkRoomOwnerUnitTypeList.get(0).getId());
                            }
                        }

                        bean.setNetworkRoomAddress(ExcelUtils.getCellValue(row.getCell(8)));

                        Cell cellIsBasicNetworkTransmitStationType = row.getCell(9);
                        cellIsBasicNetworkTransmitStationType.setCellType(CellType.STRING);

                        if ("是".equals(
                                cellIsBasicNetworkTransmitStationType.getStringCellValue())) {
                            bean.setIsBasicNetworkTransmitStationType(OnOffEnum.ON.value());
                        } else {
                            bean.setIsBasicNetworkTransmitStationType(OnOffEnum.OFF.value());
                        }

                        Cell cellIsSdnNetworkTransmitStationType = row.getCell(10);
                        cellIsSdnNetworkTransmitStationType.setCellType(CellType.STRING);

                        if ("是".equals(cellIsSdnNetworkTransmitStationType.getStringCellValue())) {
                            bean.setIsSdnNetworkTransmitStationType(OnOffEnum.ON.value());
                        } else {
                            bean.setIsSdnNetworkTransmitStationType(OnOffEnum.OFF.value());
                        }

                        Cell cellIsProgrammableNetworkTransmitStationType = row.getCell(11);
                        cellIsProgrammableNetworkTransmitStationType.setCellType(CellType.STRING);

                        if ("是".equals(cellIsProgrammableNetworkTransmitStationType
                                .getStringCellValue())) {
                            bean.setIsProgrammableNetworkTransmitStationType(OnOffEnum.ON.value());
                        } else {
                            bean.setIsProgrammableNetworkTransmitStationType(OnOffEnum.OFF.value());
                        }

                        if (ExcelUtils.getCellValue(row.getCell(12)).equals("T0")) {
                            bean.setBasicNetworkTransmitStationTypeText(this.messageSource
                                    .getMessage("site.type.core.node", null, locale));
                            bean.setBasicNetworkTransmitStationType("1");
                        } else if (ExcelUtils.getCellValue(row.getCell(12)).equals("T1")) {
                            bean.setBasicNetworkTransmitStationTypeText(this.messageSource
                                    .getMessage("site.type.high.speed.core.node", null, locale));
                            bean.setBasicNetworkTransmitStationType("2");
                        } else if ((ExcelUtils.getCellValue(row.getCell(12)).equals("T2"))
                                || (ExcelUtils.getCellValue(row.getCell(12)).equals("T3"))) {
                            bean.setBasicNetworkTransmitStationTypeText(this.messageSource
                                    .getMessage("site.type.high.speed.relay.node", null, locale));
                            bean.setBasicNetworkTransmitStationType("3");
                        } else {
                            bean.setBasicNetworkTransmitStationTypeText(this.messageSource
                                    .getMessage("site.type.edge.node", null, locale));
                            bean.setBasicNetworkTransmitStationType("4");
                        }


                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(15)))) {
                            bean.setProgrammableNetworkTransmitStationTypeText(this.messageSource
                                    .getMessage("site.type.core.node", null, locale));
                            // bean.setProgrammableNetworkTransmitStationType("1");
                            bean.setProgrammableNetworkTransmitStationType(
                                    ExcelUtils.getCellValue(row.getCell(15)));
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(16)))) {

                            bean.setSdnNetworkTransmitStationTypeText(this.messageSource
                                    .getMessage("site.type.core.node", null, locale));
                            // bean.setSdnNetworkTransmitStationType("1");
                            bean.setSdnNetworkTransmitStationType(
                                    ExcelUtils.getCellValue(row.getCell(16)));
                        }

                        // for (BasicNetworkTransmitStationTypeEnum t :
                        // BasicNetworkTransmitStationTypeEnum
                        // .values()) {
                        //
                        // if (ExcelUtils.getCellValue(row.getCell(12)).equals(
                        // this.messageSource.getMessage(t.resourceName(), null, locale))) {
                        // bean.setBasicNetworkTransmitStationTypeText(
                        // ExcelUtils.getCellValue(row.getCell(12)));
                        // bean.setBasicNetworkTransmitStationType(t.value().toString());
                        // }
                        //
                        // }
                        //
                        // for (ProgrammableNetworkTransmitStationTypeEnum t :
                        // ProgrammableNetworkTransmitStationTypeEnum
                        // .values()) {
                        // if (ExcelUtils.getCellValue(row.getCell(15)).equals(
                        // this.messageSource.getMessage(t.resourceName(), null, locale))) {
                        // bean.setProgrammableNetworkTransmitStationTypeText(
                        // ExcelUtils.getCellValue(row.getCell(15)));
                        // bean.setProgrammableNetworkTransmitStationType(t.value().toString());
                        // }
                        //
                        // }
                        //
                        // for (SdnNetworkTransmitStationTypeEnum t :
                        // SdnNetworkTransmitStationTypeEnum
                        // .values()) {
                        // if (ExcelUtils.getCellValue(row.getCell(16)).equals(
                        // this.messageSource.getMessage(t.resourceName(), null, locale))) {
                        //
                        // bean.setSdnNetworkTransmitStationTypeText(
                        // ExcelUtils.getCellValue(row.getCell(16)));
                        // bean.setSdnNetworkTransmitStationType(t.value().toString());
                        // }
                        // }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(17)))) {
                            CommonFilter f = new CommonFilter();
                            f.addExact("optionName", ExcelUtils.getCellValue(row.getCell(17)));
                            List<DataOption> edgeNodeTypeList =
                                    this.dataOptionService.list(f, null);
                            if ((edgeNodeTypeList != null) && (edgeNodeTypeList.size() > 0)) {
                                bean.setEdgeNodeTypeText(edgeNodeTypeList.get(0).getOptionName());
                                bean.setEdgeNodeType_(edgeNodeTypeList.get(0).getId());
                            }
                        }

                        // bean.setSdnNetworkTransmitStationTypeText(
                        // ExcelUtils.getCellValue(row.getCell(13)));
                        // bean.setProgrammableNetworkTransmitStationTypeText(
                        // ExcelUtils.getCellValue(row.getCell(14)));

                        // add 15,16,17 可编程站型，sdn站型，边缘节点

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(18)))) {
                            try {
                                bean.setOpticalLayerRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(18))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(18, i, "光层机架（600*300）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(19)))) {
                            try {
                                bean.setM24Rack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(19))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(19, i,
                                        "M24电光混机架（600*300）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(20)))) {
                            try {
                                bean.setU64Rack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(20))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(20, i,
                                        "U64电层机架（900*600）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(21)))) {
                            try {
                                bean.setU32Rack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(21))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(21, i,
                                        "U32电层机架（600*300）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(22)))) {
                            try {
                                bean.setTransmitDeviceTotalPower(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(22))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(22, i, "传输设备总功耗（W）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(23)))) {
                            try {
                                bean.setTransmitDevice63aReqAmount(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(23))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(23, i, "传输设备63A直流熔丝需求数量应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(24)))) {
                            try {
                                bean.setProgrammableRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(24))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(24, i,
                                        "可编程核心机柜（600*1000）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(25)))) {
                            try {
                                bean.setProgrammableRackPower(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(25))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(25, i, "可编程核心单机架功耗（W）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(26)))) {
                            try {
                                bean.setSdnRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(26))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(26, i,
                                        "SDN核心机柜（600*1000）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(27)))) {
                            try {
                                bean.setSdnRackPower(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(27))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(27, i, "SDN核心单机柜功耗（W）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(28)))) {
                            bean.setEdgeNodeTypeText(ExcelUtils.getCellValue(row.getCell(28)));
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(29)))) {
                            try {
                                bean.setEdgeNodeRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(29))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(29, i,
                                        "边缘设备机柜（600*1000）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(30)))) {
                            try {
                                bean.setProgrammableEdgeNodeRackPower(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(30))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(30, i, "边缘设备单机架功耗（W）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(31)))) {
                            try {
                                bean.setDirectConnNodeTransmitRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(31))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(31, i,
                                        "直连点传输机柜（600*300）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(32)))) {
                            try {
                                bean.setProgrammableDirectConnNodeDataDevicePower(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(32))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(32, i, "直连点传输设备功耗（W）应为数字！");
                            }

                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(33)))) {
                            try {
                                bean.setDirectConnNode63aReqAmount(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(33))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(33, i,
                                        "直联点传输设备63A直流熔丝需求数量应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(34)))) {
                            try {
                                bean.setDirectConnNodeRouterRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(34))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(34, i,
                                        "直连点路由器机柜（600*1000）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(35)))) {
                            try {
                                bean.setProgrammableDirectConnNodeDataDevicePower(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(35))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(35, i, "直连点数据功耗（W）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(36)))) {
                            try {
                                bean.setCaictRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(36))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(36, i, "信通院机柜（600*1000）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(37)))) {
                            try {
                                bean.setCaictDeviceTotalPower(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(37))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(37, i, "信通院设备总功耗（W）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(38)))) {
                            try {
                                bean.setCaictDevice32aAmount(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(38))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(38, i,
                                        "信通院设备32A（2P）交流熔丝数量应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(39)))) {
                            try {
                                bean.setCompRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(39))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(39, i, "综合柜（600*1000）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(40)))) {
                            try {
                                bean.setCompRackTotalPower(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(40))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(40, i, "综合柜总功耗（W）应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(41)))) {
                            try {
                                bean.setCompRack32aAmount(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(41))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(41, i,
                                        "综合柜32A（2P）交流熔丝数量应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(42)))) {
                            try {
                                bean.setOtherDevice(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(42))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(42, i,
                                        "其他设备（ODF架）数量600mm*300mm应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(43)))) {
                            try {
                                bean.setSpareRack(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(43))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(43, i, "备品备件柜数量应为数字！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(44)))) {
                            bean.setNetworkRoomContactTitle(
                                    ExcelUtils.getCellValue(row.getCell(44)));
                        }

                        bean.setNetworkRoomContactMobile(ExcelUtils.getCellValue(row.getCell(45)));
                        bean.setEiaRegister(ExcelUtils.getCellValue(row.getCell(46)));
                        bean.setBusinessInvitationReply(ExcelUtils.getCellValue(row.getCell(47)));
                        bean.setFrameworkAgreementRemark(ExcelUtils.getCellValue(row.getCell(48)));
                        bean.setInitMaterialArchieveDate(ExcelUtils.getCellValue(row.getCell(49)));
                        bean.setSdnInitMaterialArchieveDate(ExcelUtils.getCellValue(row.getCell(49)));
                        bean.setProgrammableInitMaterialArchieveDate(ExcelUtils.getCellValue(row.getCell(49)));

                        bean.setNetworkRoomImprove(ExcelUtils.getCellValue(row.getCell(50)));
                        bean.setNationalAcceptance(ExcelUtils.getCellValue(row.getCell(51)));

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(52)))) {
                            try {
                                bean.setLongitude(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(52))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(52, i, "经度格式不正确！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(53)))) {
                            try {
                                bean.setLatitude(
                                        Double.valueOf(ExcelUtils.getCellValue(row.getCell(53))));
                            } catch (Exception e) {
                                throw new ExcelParseException.Detail(53, i, "纬度格式不正确！");
                            }
                        }

                        if (!StringUtils.isEmpty(ExcelUtils.getCellValue(row.getCell(54)))) {
                            CommonFilter f = new CommonFilter();
                            f.addExact("personName", ExcelUtils.getCellValue(row.getCell(54)));
                            List<Person> pList = this.personService.list(f, null);
                            if ((pList != null) && (pList.size() > 0)) {
                                bean.setPersonInCharge_(pList.get(0).getId());
                                bean.setPersonInChargeText(pList.get(0).getPersonName());
                            }
                        }
                        
                        // 数据网节点类型
                        String dataNetworkNodeTypeStr = ExcelUtils.getCellValue(row.getCell(55));
                        if (!StringUtils.isEmpty(dataNetworkNodeTypeStr)) {
                        	if (this.messageSource
                                    .getMessage(DataNetworkNodeTypeEnum.CORE_NODE.resourceName(), null, locale).equals(dataNetworkNodeTypeStr)) {
                        		bean.setDataNetworkNodeType(DataNetworkNodeTypeEnum.CORE_NODE.value().toString());
                        		bean.setDataNetworkNodeTypeText(dataNetworkNodeTypeStr);
                        	} else if (this.messageSource
                                    .getMessage(DataNetworkNodeTypeEnum.HIGH_SPEED_CORE_NODE.resourceName(), null, locale).equals(dataNetworkNodeTypeStr)) {
                        		bean.setDataNetworkNodeType(DataNetworkNodeTypeEnum.HIGH_SPEED_CORE_NODE.value().toString());
                        		bean.setDataNetworkNodeTypeText(dataNetworkNodeTypeStr);
                        	} else if (this.messageSource
                                    .getMessage(DataNetworkNodeTypeEnum.HIGH_SPEED_RELAY_NODE.resourceName(), null, locale).equals(dataNetworkNodeTypeStr)) {
                        		bean.setDataNetworkNodeType(DataNetworkNodeTypeEnum.HIGH_SPEED_RELAY_NODE.value().toString());
                        		bean.setDataNetworkNodeTypeText(dataNetworkNodeTypeStr);
                        	}
                        }
                        
                        // 可编程网络组机柜位置
                        System.out.println(ExcelUtils.getCellValue(row.getCell(56)));
                        bean.setBasicRackLocation(ExcelUtils.getCellValue(row.getCell(56)));
                        bean.setSdnRackLocation(ExcelUtils.getCellValue(row.getCell(57)));
                        bean.setProgrammableRackLocation(ExcelUtils.getCellValue(row.getCell(58)));

                        bean.setSiteType(SiteTypeEnum.CORE_NODE.value());

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
