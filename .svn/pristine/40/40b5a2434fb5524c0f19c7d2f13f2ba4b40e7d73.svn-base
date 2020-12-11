package project.edge.web.controller.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FunctionBean;
import garage.origin.domain.view.JsonResultBean;
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
import project.edge.common.util.ControllerMapUtils;
import project.edge.domain.converter.PlanChangeBeanConverter;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Plan;
import project.edge.domain.entity.PlanChange;
import project.edge.domain.entity.Segment;
import project.edge.domain.entity.Site;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.PlanChangeBean;
import garage.origin.domain.view.SessionUserBean;
import project.edge.integration.oa.CreateWorkFlowManager;
import project.edge.service.facility.SegmentService;
import project.edge.service.facility.SiteService;
import project.edge.service.project.VirtualOrgService;
import project.edge.service.schedule.PlanChangeService;
import project.edge.service.schedule.PlanService;
import project.edge.web.controller.common.TreeDoubleGridUploadController;
import project.edge.web.controller.general.DataPermissionService;

/**
 * 计划变更画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/schedule/plan-change")
public class PlanChangeController
        extends TreeDoubleGridUploadController<PlanChange, PlanChangeBean> {

    private static final Logger logger = LoggerFactory.getLogger(PlanChangeController.class);

    private static final String PID = "P5005";
    
    private static final String LOCAL_AUDIT_SUBMIT_JS = "FlowableUtils.saveProcessInstanceFormData('#%1$s','%2$s','#%3$s');";
    private static final String OPEN_LOCAL_AUDIT_LOG_JS = "FlowableUtils.openAuditLogDialog('#%1$s','#%2$s')";
    private static final String OPEN_LOCAL_WITHDRAW_JS = "FlowableUtils.openWithdrawDialog('#%1$s','#%2$s')";
    private String VIEW_GRID_DIALOG_URL = "view-grid-dialog-url";
    
    @Resource
    CreateWorkFlowManager createWorkFlowManager;

    @Resource
    private PlanChangeService planChangeService;

    @Resource
    private PlanService planService;

    @Resource
    private VirtualOrgService virtualOrgService;

    @Resource
    private SiteService siteService;

    @Resource
    private SegmentService segmentService;
    
    @Resource
	private DataPermissionService dataPermissionService;

    @Autowired
    HttpServletRequest request;

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.PLAN_CHANGE.value();
    }

    @Override
    protected String getPageId() {
        return PID;
    }

    @Override
    protected Service<PlanChange, String> getDataService() {
        return this.planChangeService;
    }

    @Override
    protected ViewConverter<PlanChange, PlanChangeBean> getViewConverter() {
        return new PlanChangeBeanConverter();
    }

    @Override
    protected String getJsCallbackObjName() {
        return this.getDataType();
    }

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/schedule/planChangeJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/schedule/planChangeHidden.jsp";
    }

    @Override
    protected boolean useFile() {
        return false;
    }

    @Override
    protected Map<String, String> prepareUrlMap() {

        Map<String, String> urlMap = super.prepareUrlMap();

        // 计划一览的URL
        String contextPath = this.context.getContextPath();
        urlMap.put(ControllerUrlMapKeys.SUB_GRID_LIST, contextPath + "/schedule/plan/list2.json");

        return urlMap;
    }

    @Override
    public String getSubDataType() {
        return DataTypeEnum.PLAN.value();
    }

    @Override
    public String getGridTitle(Locale locale) {
        return this.messageSource.getMessage("ui.grid.title.plan.change", null, locale);
    }

    @Override
    public String getSubGridTitle(Locale locale) {
        return this.messageSource.getMessage("ui.grid.title.plan", null, locale);
    }

    @Override
    public OrderByDto getSubGridDefaultOrder() {
        return new OrderByDto("planName", false);
    }

    @Override
    public List<FunctionBean> getSubGridFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {

        List<FunctionBean> functions = new ArrayList<>();

        // "查看任务"按钮
        CommonFilter filter = new CommonFilter().addExact("id", "P5001F42");
        List<Page> pages = this.pageService.list(filter, null);

        functions.add(new FunctionBean(true));

        FunctionBean f = new FunctionBean(pages.get(0), this.messageSource, locale);
        f.setId(String.format(SUB_GRID_FUNCTION_ID_TEMPLATE, this.getPageId(),
                f.getId().substring(f.getId().indexOf("F")))); // 重新设定id
        String subGridId = String.format(ControllerMapUtils.SUB_DATAGRID_ID, this.getPageId(),
                this.getSubDataType());
        f.setHandler(String.format(ControllerMapUtils.OPEN_VIEW_TASK_JS, subGridId));
        functions.add(f);

        functions.add(new FunctionBean(true));

        return functions;
    }

    @Override
    public List<FunctionBean> getSubGridSearchFunctionList(Map<String, String> idMap,
            Map<String, String> urlMap, Map<String, String> jsMap, SessionUserBean userBean,
            Locale locale) {

        List<FunctionBean> searchFunctions = new ArrayList<>();

        // 检索和清空检索
        List<String> pageIdList = Arrays.asList("P5001F08", "P5001F09");
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

    @Override
    public String getLinkedFilterFieldName() {
        return "plan_";
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

        CommonFilter filter = new CommonFilter();
        filter.addExact("isDeleted", OnOffEnum.OFF.value());
        filter.addExact("isEchartsShow", OnOffEnum.ON.value().intValue());
        List<VirtualOrg> virtualOrgs = this.virtualOrgService.list(filter, null);
        String format = "%1$s|%2$s";
        List<ComboboxOptionBean> projectGroupOptions = new ArrayList<>();
        List<ComboboxOptionBean> projectGroupOptions2 = new ArrayList<>();
        if (virtualOrgs != null) {
            for (VirtualOrg v : virtualOrgs) {
                projectGroupOptions.add(new ComboboxOptionBean(v.getId(),
                        String.format(format, v.getProject().getId(), v.getVirtualOrgName())));
                projectGroupOptions2.add(new ComboboxOptionBean(v.getId(), v.getVirtualOrgName()));
            }
        }
        optionMap.put("groupOptions", projectGroupOptions);
        optionMap.put("groupOptions2", projectGroupOptions2);

        String[] planTypeArr = new String[] {"年度建设工作计划", "站点计划", "中继段计划"};
        List<ComboboxOptionBean> planTypeOptions = new ArrayList<>();
        for (int i = 0; i < planTypeArr.length; i++) {
            planTypeOptions.add(new ComboboxOptionBean(i + "", planTypeArr[i]));
        }
        optionMap.put("planTypeOptions", planTypeOptions);

        String[] planYearArr = new String[] {"2019", "2020", "2021", "2022", "2023", "2024", "2025",
                "2026", "2027", "2028", "2029", "2030"};

        List<ComboboxOptionBean> planYearOptions = new ArrayList<>();

        for (String s : planYearArr) {
            planYearOptions.add(new ComboboxOptionBean(s, s));
        }

        optionMap.put("planYearOptions", planYearOptions);

        CommonFilter pFilter = new CommonFilter();
        filter.addExact("isDeleted", OnOffEnum.OFF.value());
        List<Site> sites = this.siteService.list(pFilter, null);
        List<Segment> segments = this.segmentService.list(pFilter, null);
        pFilter.addNull("site.id");
        pFilter.addNull("segment.id");
        List<Plan> plans = this.planService.list(pFilter, null);
        List<ComboboxOptionBean> personOptions = new ArrayList<>();
        List<String> personIds = new ArrayList<>();
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getPersonInCharge() != null
                    && !personIds.contains(sites.get(i).getPersonInCharge().getId())) {
                personIds.add(sites.get(i).getPersonInCharge().getId());
                personOptions.add(new ComboboxOptionBean(sites.get(i).getPersonInCharge().getId(),
                        sites.get(i).getPersonInCharge().getPersonName()));
            }
            if (sites.get(i).getProgrammablePersonInCharge() != null
                    && !personIds.contains(sites.get(i).getProgrammablePersonInCharge().getId())) {
                personIds.add(sites.get(i).getProgrammablePersonInCharge().getId());
                personOptions.add(
                        new ComboboxOptionBean(sites.get(i).getProgrammablePersonInCharge().getId(),
                                sites.get(i).getProgrammablePersonInCharge().getPersonName()));
            }
            if (sites.get(i).getSdnPersonInCharge() != null
                    && !personIds.contains(sites.get(i).getSdnPersonInCharge().getId())) {
                personIds.add(sites.get(i).getSdnPersonInCharge().getId());
                personOptions
                        .add(new ComboboxOptionBean(sites.get(i).getSdnPersonInCharge().getId(),
                                sites.get(i).getSdnPersonInCharge().getPersonName()));
            }
        }
        for (int i = 0; i < segments.size(); i++) {
            if (segments.get(i).getPersonInCharge() != null
                    && !personIds.contains(segments.get(i).getPersonInCharge().getId())) {
                personIds.add(segments.get(i).getPersonInCharge().getId());
                personOptions
                        .add(new ComboboxOptionBean(segments.get(i).getPersonInCharge().getId(),
                                segments.get(i).getPersonInCharge().getPersonName()));
            }
        }
        for (int i = 0; i < plans.size(); i++) {
            if (plans.get(i).getCreator() != null
                    && !personIds.contains(plans.get(i).getCreator().getId())) {
                personIds.add(plans.get(i).getCreator().getId());
                personOptions.add(new ComboboxOptionBean(plans.get(i).getCreator().getId(),
                        plans.get(i).getCreator().getPersonName()));
            }
        }
        optionMap.put("personOptions", personOptions);
        return optionMap;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {

//        Map<String, String> jsMap = ControllerMapUtils.buildJsMap(idMap, urlMap, this.useFile(),
//                this.getJsCallbackObjName());
        
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 提交审核数据按钮
        jsMap.put(ControllerJsMapKeys.LOCAL_AUDIT_SUBMIT,
                String.format(LOCAL_AUDIT_SUBMIT_JS, idMap.get(ControllerIdMapKeys.SUBMIT_AUDIT_DIALOG),
                        urlMap.get(ControllerUrlMapKeys.SUBMIT_AUDIT), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 打开审核日志对话框
        jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_AUDIT_LOG, String.format(OPEN_LOCAL_AUDIT_LOG_JS,
                idMap.get(ControllerIdMapKeys.AUDIT_LOG_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 打开撤回对话框
        jsMap.put(ControllerJsMapKeys.OPEN_LOCAL_WITHDRAW, String.format(OPEN_LOCAL_WITHDRAW_JS,
                idMap.get(ControllerIdMapKeys.WITHDRAW_DIALOG), idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
        // 比较变更
  		jsMap.put(ControllerJsMapKeys.COMPARE,
      				String.format("PLAN_CHANGE.compare('#%1$s');", idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));
        
        return jsMap;
    }
    
	@Override
    protected Map<String, String> prepareIdMap() {

        Map<String, String> idMap = super.prepareIdMap();
        idMap.put(VIEW_GRID_DIALOG_URL, "/project-edge/schedule/plan-change/list.json");

        return idMap;
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
    	String mainView = super.main(paramMap, model, userBean, locale);

    	// 针对双Grid，添加审批通用界面的数据查询接口自定义（目前2类双grid查询url不一样）
    	Map<String, Object> modelMap = model.asMap();
        Map<String, String> urlMap =
                (Map<String, String>) modelMap.get(ControllerModelKeys.URL_MAP);
        String findUrl = urlMap.get(ControllerUrlMapKeys.FIND);
        urlMap.put("audit-findurl", findUrl);
    	
        return mainView;
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
			@ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            @RequestParam(required = false, defaultValue = "asc") String order,
			@RequestParam(required = false, defaultValue = "") String id, Locale locale) {

    	commonFilterJson = dataPermissionService.editFilterBySelfVirtualOrg(userBean, commonFilterJson);
    	
        CommonFilter f = new CommonFilter().addExact("isDeleted", OnOffEnum.OFF.value());
        if (!StringUtils.isEmpty(id)) {
        	// 内部审批查看页在双Grid场合无法显示计划变更一览，增加数据URL及查询条件
            f.addExact("id", id);
        }
        JsonResultBean jsonResult =
                super.list(commonFilterJson, f, page, rows, sort, order, locale);

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
        // return super.find(id, locale);
        JsonResultBean jsonResult = super.find(id, locale);
        try {
            List<ComboboxOptionBean> optionList = new ArrayList<>();
            String format = "%1$s";
            PlanChange entity = planChangeService.find(id);
            if (entity != null) {
                CommonFilter virtualFilter =
                        new CommonFilter().addExact("project.id", entity.getProject().getId());
                List<VirtualOrg> virtualOrgList = this.virtualOrgService.list(virtualFilter, null);
                if (virtualOrgList != null) {
                    for (VirtualOrg v : virtualOrgList) {
                        optionList.add(new ComboboxOptionBean(v.getId(),
                                String.format(format, v.getVirtualOrgName())));
                    }
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, optionList);

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
    @SysLogAnnotation(description = "计划变更", action = "新增")
    public JsonResultBean create(@ModelAttribute PlanChangeBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        String planid = bean.getPlan_();

        Plan p = this.planService.find(planid);
        if (p != null) {
            bean.setPlan_(p.getId());
        }
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
    @SysLogAnnotation(description = "计划变更", action = "更新")
    public JsonResultBean update(@ModelAttribute PlanChangeBean bean,
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
    @SysLogAnnotation(description = "计划变更", action = "删除")
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.delete(idsToDelete, null, userBean, locale);
    }

    @RequestMapping("/auditSubmit")
    @ResponseBody
    @SysLogAnnotation(description = "计划变更", action = "审核")
    public JsonResultBean auditSubmit(
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean,
            @RequestParam String id, Locale locale) {
        JsonResultBean jsonResult = new JsonResultBean();
        PlanChange entity = this.getDataService().find(id);
        if (entity == null) {
            jsonResult.setStatus(JsonStatus.ERROR);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.error.record.not.exist", null, locale));
            return jsonResult;
        }

        // 插入到t_oa_flow_history
        String dataId = entity.getId();
        String userName = userBean.getSessionLoginName();
        String creatorId = userBean.getSessionUserId();
        int oaType = OAAuditApiType.OA_TYPE_PLANCHANGE.value();

        HashMap<String, Object> reqMap = new HashMap<>();
        reqMap.put("planName", entity.getPlanName());
        reqMap.put("planVersion", entity.getPlanVersion());
        reqMap.put("changeReason", entity.getChangeReason());
        // reqMap.put("virtualOrgName", entity.getVirtualOrg().getVirtualOrgName());
        Plan planEntity = entity.getPlan();
        if (planEntity != null) {
            reqMap.put("planYear", planEntity.getPlanYear());
            
            String siteId = planEntity.getSite() == null ? "":planEntity.getSite().getId();
    		String segmentId = planEntity.getSegment()==null?"":planEntity.getSegment().getId();
            if (planEntity.getIsYear() == 1) {
    			reqMap.put("planType", "年度建设工作计划");
    		} else {
    			if (!StringUtils.isEmpty(siteId)) {
    				reqMap.put("planType", "站点计划");
    			} else if (!StringUtils.isEmpty(segmentId)) {
    				reqMap.put("planType", "中继段计划");
    			} else {
    				reqMap.put("planType", "");
    			}
    		}
        } else {
        	reqMap.put("planYear", "");
        	reqMap.put("planType", "");
        }
        //比较链接 
        //例：http://112.80.35.18:8010/project-edge/schedule/plan-task/main.htm?isModify=6&planChangeId=d5b28f9d-9d9a-432e-a91d-6d14d046f9bc
        String contextPath = request.getContextPath();
		String compareLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ contextPath + "/schedule/plan-task/main2.htm?isModify=6&planChangeId=" + entity.getId() + "&userid=" + creatorId;
        reqMap.put("compareLink", compareLink);
        reqMap.put("remark", entity.getRemark());

		String projectId = "";
		if (entity.getProject() != null) projectId = entity.getProject().getId();
		
        // 调用oa接口
        logger.info("[OA Audit] No.10 PlanChange");
        // 调用oa接口
        jsonResult = createWorkFlowManager.reqOaAudit(userName, dataId, creatorId, oaType, projectId, reqMap,
                request);
        // 如果请求审批成功更新表t_planchange 审批中
        if (jsonResult.getStatus() == JsonStatus.OK) {
            logger.info("[OA Audit] No.10 PlanChange Audit request success, history data id was {}",
                    dataId);
            PlanChange plan = this.getDataService().find(dataId);
            plan.setFlowStatus(FlowStatusEnum.REVIEWING.value());
            plan.setmDatetime(new Date());
            plan.setModifier(userName);
            this.planChangeService.setData(plan);
        }

        return jsonResult;
    }
}
