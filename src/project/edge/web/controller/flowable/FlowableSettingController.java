package project.edge.web.controller.flowable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.flowable.task.api.history.HistoricTaskInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import garage.origin.common.constant.Constants;
import garage.origin.common.constant.FlowAuditResultEnum;
import garage.origin.common.constant.FlowNodeTypeEnum;
import garage.origin.common.constant.JsonIcon;
import garage.origin.common.constant.JsonStatus;
import garage.origin.common.constant.OnOffEnum;
import garage.origin.common.util.DateUtils;
import garage.origin.domain.business.FilterFieldInfoDto;
import garage.origin.domain.business.OrderByDto;
import garage.origin.domain.filter.CommonFilter;
import garage.origin.domain.view.ComboboxOptionBean;
import garage.origin.domain.view.FlowDetailBean;
import garage.origin.domain.view.JsonResultBean;
import garage.origin.domain.view.SessionUserBean;
import garage.origin.domain.view.TreeNodeBean;
import garage.origin.domain.view.converter.ViewConverter;
import garage.origin.service.Service;
import project.edge.common.constant.ControllerIdMapKeys;
import project.edge.common.constant.ControllerJsMapKeys;
import project.edge.common.constant.DataTypeEnum;
import project.edge.common.constant.FlowStatusEnum;
import project.edge.domain.converter.FlowableSettingBeanConverter;
import project.edge.domain.converter.FlowableSettingNodeBeanConverter;
import project.edge.domain.entity.FlowableSetting;
import project.edge.domain.entity.FlowableSettingNode;
import project.edge.domain.entity.Page;
import project.edge.domain.entity.Person;
import project.edge.domain.entity.ProjectPerson;
import project.edge.domain.entity.ProjectRole;
import project.edge.domain.entity.VirtualOrg;
import project.edge.domain.view.AuditLogBean;
import project.edge.domain.view.FlowableSettingBean;
import project.edge.domain.view.FlowableSettingNodeBean;
import project.edge.flowable.FlowableManager;
import project.edge.service.auth.PageService;
import project.edge.service.flowable.FlowableSettingNodeService;
import project.edge.service.flowable.FlowableSettingService;
import project.edge.service.hr.PersonService;
import project.edge.service.project.ProjectPersonService;
import project.edge.service.project.ProjectRoleService;
import project.edge.web.controller.common.TreeGridUploadController;

/**
 * 流程设置画面。
 *
 */
@Controller
@SessionAttributes(Constants.SESSION_USER)
@RequestMapping("/flowable/setting")
public class FlowableSettingController
        extends TreeGridUploadController<FlowableSetting, FlowableSettingBean> {

    private static final Logger logger = LoggerFactory.getLogger(FlowableSettingController.class);

    private static final String PID = "P9501";
    private static final String SETTING_VIEW_NAME = "flowable/flowableSettingPage"; // 修改默认的JSP

    @Resource
    private FlowableSettingService flowableSettingService;

    @Resource
    private PersonService personService;

    @Resource
    private PageService pageService;

    @Resource
    private ProjectPersonService projectPersonService;

    @Resource
    private ProjectRoleService projectRoleService;

    @Resource
    private FlowableManager flowableManager;

    @Resource
    private FlowableSettingNodeService flowableSettingNodeService;

    public static final String DEPUTY_MANAGER_ROLE_ID = "DEPUTY_MANAGER_ROLE_ID";

    @Override
    protected String getJavascriptJspPath() {
        return "/WEB-INF/jsp/flowable/flowableSettingJs.jsp";
    }

    @Override
    protected String getHiddenContentJspPath() {
        return "/WEB-INF/jsp/flowable/flowableSettingHidden.jsp";
    }

    @Override
    protected String getMainView(Map<String, String> paramMap) {
        return SETTING_VIEW_NAME;
    }

    @Override
    protected Service<FlowableSetting, String> getDataService() {
        return this.flowableSettingService;
    }

    @Override
    protected ViewConverter<FlowableSetting, FlowableSettingBean> getViewConverter() {
        return new FlowableSettingBeanConverter();
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public String getDataType() {
        return DataTypeEnum.FLOWABLE_SETTING.value();
    }

    @Override
    protected String getPageId() {
        return PID;
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
    @ResponseBody
    public JsonResultBean create(@ModelAttribute FlowableSettingBean bean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        return super.create(bean, null, userBean, locale);
    }

    @Override
    protected void postCreate(FlowableSetting entity, FlowableSettingBean bean,
            Map<String, Object> paramMap, Locale locale) throws IOException {

        FlowableSettingNode settingNode = new FlowableSettingNode();

        settingNode.setFlowableSetting(entity);
        settingNode.setNodeType(FlowNodeTypeEnum.START_EVENT.value());
        settingNode.setNodeName(this.messageSource
                .getMessage("ui.fields.flowable.setting.node.start.event", null, locale));
        settingNode.setNodeOrder(1);
        this.flowableSettingNodeService.create(settingNode);
    }

    @RequestMapping("/get-flowable-setting-nodes")
    @ResponseBody
    public JsonResultBean getSetttingNodeBeans(String flowableSettingId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        JsonResultBean jsonResult = new JsonResultBean();
        try {
            CommonFilter filter = new CommonFilter();
            filter.addExact("flowableSetting.id", flowableSettingId);

            List<OrderByDto> orderList =
                    Collections.singletonList(new OrderByDto("nodeOrder", false));

            List<FlowableSettingNode> nodes =
                    this.flowableSettingNodeService.list(filter, orderList);

            List<FlowableSettingNodeBean> resultBeans = new ArrayList<>();

            FlowableSettingNodeBeanConverter converter = new FlowableSettingNodeBeanConverter();
            for (FlowableSettingNode node : nodes) {
                resultBeans.add(converter.fromEntity(node, messageSource, locale));
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultBeans);

        } catch (Exception e) {
            getLogger().error("Exception creating the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /*
     * 通过关联ID找到所属的流程ID
     */
    @RequestMapping("/get-flowable-setting-with-correlateDataId")
    @ResponseBody
    public JsonResultBean getSetttingIdWithCorrelateDataId(String correlateDataId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        JsonResultBean jsonResult = new JsonResultBean();
        try {

            String resultId =
                    this.flowableManager.queryFlowableSettingIdWithCorrelateDataId(correlateDataId);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_VALUE, resultId);
        } catch (Exception e) {
            getLogger().error(
                    "Exception get-flowable-setting-with-correlateDataId." + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /*
     * 通过关联ID撤回流程
     */
    @RequestMapping("/withdraw-with-correlateDataId")
    @ResponseBody
    public JsonResultBean withdrawWithCorrelateDataId(String correlateDataId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {
        JsonResultBean jsonResult = new JsonResultBean();
        try {

            Map<String, Object> varMap =
                    this.flowableManager.getRunVariablesWithCorrelateDataId(correlateDataId);

            // 设置实体的流程状态 未提交
            this.flowableManager.withdraw(correlateDataId);

            if (varMap.get("businessEntity") != null) {
                String businessEntity = (String) varMap.get("businessEntity");
                this.flowableSettingService.updateFlowableStatus(businessEntity, correlateDataId,
                        FlowStatusEnum.UNSUBMITTED.value());
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_RETURN_VALUE, correlateDataId);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
        } catch (Exception e) {
            getLogger().error("Exception withdraw-with-correlateDataId." + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
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
    public JsonResultBean update(@ModelAttribute FlowableSettingBean bean,
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
    public JsonResultBean delete(@RequestParam String idsToDelete,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            String[] idArray = StringUtils.commaDelimitedListToStringArray(idsToDelete);

            for (String id : idArray) {
                boolean isRunning = this.flowableManager.isFlowableHasRunning(id);

                if (isRunning) {
                    jsonResult.setStatus(JsonStatus.ERROR);
                    jsonResult.setMessage(this.messageSource
                            .getMessage("message.error.flowable.is.running.no.save", null, locale));
                    return jsonResult;
                }
            }

            CommonFilter filter = new CommonFilter();
            filter.addWithin("id", idArray);

            List<FlowableSetting> settings = this.flowableSettingService.list(filter, null);

            List<String> keys = new ArrayList<>();

            if (!settings.isEmpty()) {

                for (FlowableSetting fs : settings) {
                    keys.add(fs.getPage().getId() + Constants.UNDERSCORE + fs.getId());
                }
            }

            List<String> idList = Arrays.asList(idArray);

            // 删除数据库节点
            this.flowableSettingService.delete(idList);
            // 删除流程引擎节点
            this.flowableManager.deleteProcessDefintion(keys);

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.delete.ok", null, locale));

        } catch (DataIntegrityViolationException violationException) {
            this.getLogger().error("Exception deleting the " + this.getDataType(),
                    violationException);

            jsonResult.setStatus(JsonStatus.ERROR, JsonIcon.INFO);
            jsonResult.setMessage(this.messageSource
                    .getMessage("message.error.delete.failed.link.data", null, locale));

        } catch (Exception e) {
            this.getLogger().error("Exception deleting the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    @Override
    protected Map<String, String> prepareJsMap(Map<String, String> idMap,
            Map<String, String> urlMap) {
        Map<String, String> jsMap = super.prepareJsMap(idMap, urlMap);

        // 新建保存，isFile强制设为false
        jsMap.put(ControllerJsMapKeys.ADD_SUBMIT, String.format(
                "CrudUtils.submitAddEditFormData('#%1$s', '#%2$s', %3$s, true, false, %4$s);",
                idMap.get(ControllerIdMapKeys.ADD_FORM_DIALOG),
                idMap.get(ControllerIdMapKeys.MAIN_DATAGRID), this.useFile(),
                DataTypeEnum.FLOWABLE_SETTING.value()));

        // 流程权限
        jsMap.put(ControllerJsMapKeys.OPEN_FLOW_PERMISSION,
                String.format("FLOWABLE_SETTING.openFlowPermissionDialog('#%1$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        // 流程角色权限
        jsMap.put(ControllerJsMapKeys.OPEN_FLOW_ROLE_PERMISSION,
                String.format("FLOWABLE_SETTING.openFlowRolePermissionDialog('#%1$s');",
                        idMap.get(ControllerIdMapKeys.MAIN_DATAGRID)));

        return jsMap;
    }

    @Override
    protected boolean useFile() {
        return false;
    }

    @Override
    protected Map<String, List<ComboboxOptionBean>> prepareOptionMap(Locale locale) {

        Map<String, List<ComboboxOptionBean>> optionMap =
                new HashMap<String, List<ComboboxOptionBean>>();

        CommonFilter filter = new CommonFilter();
        filter.addExact("isHasFlowable", OnOffEnum.ON.value());

        List<Page> pages = this.pageService.list(filter, null);

        List<ComboboxOptionBean> businessCodeOptions = new ArrayList<ComboboxOptionBean>();

        for (Page p : pages) {
            if (StringUtils.isEmpty(p.getPid())) {
                businessCodeOptions.add(new ComboboxOptionBean(p.getId(),
                        messageSource.getMessage(p.getPageName(), null, locale)));
            }
        }

        optionMap.put("businessCodeOptions", businessCodeOptions);

        return optionMap;
    }

    /**
     * 获取画面左侧项目树显示的数据，返回JSON格式。
     * 
     * @param commonFilterJson JSON字符串形式的过滤条件
     * @param locale
     * @return
     */
    @RequestMapping("/tree-side")
    @ResponseBody
    public JsonResultBean getTreeSide(
            @RequestParam(required = false, defaultValue = "") String commonFilterJson,
            Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            CommonFilter filter = new CommonFilter();
            filter.addExact("isEnabled", OnOffEnum.ON.value());
            filter.addExact("isHasFlowable", OnOffEnum.ON.value());

            // 转换查询中的立项年份
            CommonFilter reqFilter = this.generateCommonFilter(null, commonFilterJson);
            if (reqFilter != null) {
                for (FilterFieldInfoDto field : reqFilter.getFilterFieldList()) {

                    filter.getFilterFieldList().add(field);
                }
            }

            List<OrderByDto> orderList =
                    Collections.singletonList(new OrderByDto("pageOrder", true));
            List<Page> pages = this.pageService.list(filter, orderList);

            // 借助map来转换并构建树形结构
            Map<String, TreeNodeBean> map = new HashMap<>(); // key: id
            List<TreeNodeBean> resultList = new ArrayList<>();

            TreeNodeBean root = new TreeNodeBean();
            root.setId(Constants.ALL);
            root.setText(this.messageSource
                    .getMessage("ui.menu.group.flowable.setting.business.code", null, locale));
            resultList.add(root);

            for (Page p : pages) {
                if (StringUtils.isEmpty(p.getPid())) {
                    continue;
                }

                TreeNodeBean n = new TreeNodeBean();

                n.setIconCls("tree-icon-fa fa fa-fw fa-sliders");

                n.setId(p.getId());
                n.setText(this.messageSource.getMessage(p.getPageName(), null, locale));

                Map<String, Object> attr = new HashMap<>();
                attr.put("fieldName", "page_");
                n.setAttributes(attr);

                map.put(n.getId(), n);
                root.getChildren().add(n);
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);
        } catch (Exception e) {
            this.getLogger().error("Exception side-listing the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    @RequestMapping("/access-process-tree")
    @ResponseBody
    public JsonResultBean getAccessProcessTree(String flowableSettingId, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            CommonFilter filter = new CommonFilter();
            filter.addExact("isDeleted", OnOffEnum.OFF.value());

            List<OrderByDto> orderList =
                    Collections.singletonList(new OrderByDto("projectRoleOrder", true));

            List<ProjectRole> projectRoles = this.projectRoleService.list(filter, orderList);

            List<TreeNodeBean> resultList = new ArrayList<>();

            FlowableSetting flowableSetting = this.flowableSettingService.find(flowableSettingId);

            List<String> roles = new ArrayList<>();

            if (flowableSetting != null) {

                for (ProjectRole p : flowableSetting.getProjectRoles()) {
                    roles.add(p.getId());
                }
            }

            for (ProjectRole pr : projectRoles) {

                TreeNodeBean node = new TreeNodeBean();

                node.setId(pr.getId());
                node.setText(pr.getProjectRoleName());
                node.setIconCls("tree-icon-fa fa fa-fw fa-sliders");

                if (roles.contains(pr.getId())) {
                    node.setChecked(true);
                }

                resultList.add(node);
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);
        } catch (Exception e) {
            this.getLogger().error("Exception get access-process-tree the " + this.getDataType(),
                    e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /**
     * 获致权限相关的流程
     * 
     * @return
     */
    @RequestMapping("/access-process")
    @ResponseBody
    public JsonResultBean getAccessProcess(String businessCode,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            List<ComboboxOptionBean> resultList = new ArrayList<ComboboxOptionBean>();

            CommonFilter settingFilter = new CommonFilter();

            settingFilter.addExact("isComplete", OnOffEnum.ON.value());
            settingFilter.addExact("isDisable", OnOffEnum.OFF.value());
            settingFilter.addExact("page.id", businessCode);

            List<FlowableSetting> flowableSettings =
                    this.flowableSettingService.list(settingFilter, null);

            String userId = userBean.getSessionUserId();

            // 角色权限过滤
            if (!flowableSettings.isEmpty() && !StringUtils.isEmpty(userId)) {

                CommonFilter personFilter = new CommonFilter();
                personFilter.addExact("person.id", userId);
                // 虚拟角色权限
                List<ProjectPerson> projectPersons =
                        this.projectPersonService.list(personFilter, null);

                Set<FlowableSetting> flowableSettingResults = new HashSet<>();

                if (!projectPersons.isEmpty()) {

                    for (ProjectPerson pp : projectPersons) {

                        ProjectRole projectRole = pp.getProjectRole();

                        if (projectRole != null) {

                            for (FlowableSetting setting : flowableSettings) {
                                Set<ProjectRole> projectRoles = setting.getProjectRoles();

                                if (!projectRoles.isEmpty()) {
                                    for (ProjectRole pr : projectRoles) {
                                        if (projectRole.getId().equals(pr.getId())) {
                                            flowableSettingResults.add(setting);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (!flowableSettingResults.isEmpty()) {

                    for (FlowableSetting fs : flowableSettingResults) {
                        resultList.add(new ComboboxOptionBean(fs.getId(), fs.getFlowName()));
                    }
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, resultList);
        } catch (Exception e) {
            getLogger().error("Exception while get access-process the " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /**
     * 保存流程权限信息
     * 
     * @param flowableSettingId
     * @param roles
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/save-access-process-data")
    @ResponseBody
    public JsonResultBean saveAccessProcess(String flowableSettingId, String roles,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            FlowableSetting entity = this.flowableSettingService.find(flowableSettingId);

            if (entity != null) {
                // 清空原始关联
                entity.setProjectRoles(new HashSet<ProjectRole>());
                String[] idArray = StringUtils.commaDelimitedListToStringArray(roles);

                for (String roleId : idArray) {

                    ProjectRole projectRole = new ProjectRole();
                    projectRole.setId(roleId);

                    entity.getProjectRoles().add(projectRole);
                }

                this.flowableSettingService.update(entity);
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
        } catch (Exception e) {
            getLogger().error("Exception while save access-process-data the " + this.getDataType(),
                    e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /**
     * 提交审核流程
     * 
     * @return
     */
    @RequestMapping("/submit-audit-process")
    @ResponseBody
    public JsonResultBean submitAuditProcess(@RequestBody FlowDetailBean flowDetailBean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            FlowableSetting setting =
                    this.flowableSettingService.find(flowDetailBean.getFlowableSettingId());

            flowDetailBean
                    .setId(setting.getPage().getId() + Constants.UNDERSCORE + setting.getId());

            flowDetailBean.setBusinessCode(setting.getPage().getId());
            flowDetailBean.setBusinessCodeUrl(setting.getPage().getUrl());
            flowDetailBean.setBusinessCodeName(
                    this.messageSource.getMessage(setting.getPage().getPageName(), null, locale));

            if (StringUtils.isEmpty(setting.getPage().getFlowableEntity())) {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource
                        .getMessage("message.info.no.flowable.business.entity", null, locale));
                return jsonResult;
            }

            flowDetailBean.setBusinessEntity(setting.getPage().getFlowableEntity());

            flowDetailBean.setStartUserId(userBean.getSessionUserId());

            Person person = this.personService.find(userBean.getSessionUserId());

            if (person != null) {
                flowDetailBean.setStartUserId(person.getId());
                flowDetailBean.setStartUserName(person.getPersonName());
            }

            Set<String> filterIds = new HashSet<>();

            String assigneeIds = this.flowableManager.getAssigneeIdList(flowDetailBean.getId());

            if (!StringUtils.isEmpty(assigneeIds)) {
                String[] tempIds = StringUtils.commaDelimitedListToStringArray(assigneeIds);

                for (String id : tempIds) {
                    filterIds.add(id);
                }
            }

            String candidateGroupsIds =
                    this.flowableManager.getCandidateGroupsIdList(flowDetailBean.getId());

            if (!StringUtils.isEmpty(candidateGroupsIds)) {
                String[] tempIds = StringUtils.commaDelimitedListToStringArray(candidateGroupsIds);

                CommonFilter pFilter = new CommonFilter();
                pFilter.addWithin("projectRole.id", tempIds);

                List<ProjectPerson> persons = this.projectPersonService.list(pFilter, null);

                CommonFilter groupFilter = new CommonFilter();
                groupFilter.addExact("person.id", userBean.getSessionUserId());

                // 发起人过滤
                List<ProjectPerson> groupPersons =
                        this.projectPersonService.list(groupFilter, null);

                for (ProjectPerson p : persons) {

                    for (ProjectPerson g : groupPersons) {

                        if (p.getVirtualOrg().getId().equals(g.getVirtualOrg().getId())) {
                            filterIds.add(p.getPerson().getId());
                        } else {

                            // 发起人的组信息
                            VirtualOrg org = g.getVirtualOrg();

                            // 该组的副经理与角色权限匹配时，加入审批人列表
                            if (org != null && org.getLeader() != null) {
                                if (org.getLeader().getId().equals(p.getPerson().getId())) {
                                    filterIds.add(org.getLeader().getId());
                                }
                            }
                        }
                    }
                }
            }

            if (!filterIds.isEmpty()) {

                CommonFilter filter = new CommonFilter();
                filter.addWithin("id", filterIds);

                List<Person> persons = this.personService.list(filter, null);
                StringBuffer sbId = new StringBuffer();
                StringBuffer sbName = new StringBuffer();

                for (Person p : persons) {
                    sbId.append(p.getId()).append(Constants.COMMA);
                    sbName.append(p.getPersonName()).append(Constants.COMMA);
                }

                if (sbName.length() > 0) {
                    flowDetailBean.setParticipantAuditNameList(
                            sbName.substring(0, sbName.length() - 1).toString());
                }

                if (sbId.length() > 0) {
                    flowDetailBean.setParticipantAuditIdList(
                            sbId.substring(0, sbId.length() - 1).toString());
                }
            }

            this.flowableManager.runProcessInstance(flowDetailBean);

            // 设置实体的流程状态 审核中
            this.flowableSettingService.updateFlowableStatus(flowDetailBean.getBusinessEntity(),
                    flowDetailBean.getCorrelateDataId(), FlowStatusEnum.REVIEWING.value());

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
        } catch (Exception e) {
            getLogger().error("Exception submit-audit-process " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /**
     * 提交审批结果
     * 
     * @param flowDetailBean
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/submit-audit")
    @ResponseBody
    public JsonResultBean submitAudit(FlowDetailBean flowDetailBean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();
        try {

            flowDetailBean.setAuditPersonId(userBean.getSessionUserId());

            Person person = this.personService.find(userBean.getSessionUserId());
            if (person != null) {
                flowDetailBean.setAuditPersonName(person.getPersonName());
            }

            this.flowableManager.completeTask(flowDetailBean);

            boolean isFinished =
                    this.flowableManager.isFlowableFinish(flowDetailBean.getCorrelateDataId());

            // 如果流程已经结束，更新数据的流程状态
            if (isFinished) {

                Integer flowStatus = FlowStatusEnum.REVIEWING.value();

                if (FlowAuditResultEnum.AGREE.value().equals(flowDetailBean.getAuditResult())) {
                    flowStatus = FlowStatusEnum.REVIEW_PASSED.value();
                } else if (FlowAuditResultEnum.REJECT.value()
                        .equals(flowDetailBean.getAuditResult())) {
                    flowStatus = FlowStatusEnum.REVIEW_UNPASSED.value();
                }

                this.flowableSettingService.updateFlowableStatus(flowDetailBean.getBusinessEntity(),
                        flowDetailBean.getCorrelateDataId(), flowStatus);
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.add.ok", null, locale));
        } catch (Exception e) {
            getLogger().error("Exception submit audit " + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    /**
     * 保存流程节点信息，并部署
     * 
     * @param flowableSettingBean
     * @param userBean
     * @param locale
     * @return
     */
    @RequestMapping("/save-flowable-setting-node")
    @ResponseBody
    public JsonResultBean saveFlowDetailData(@RequestBody FlowableSettingBean flowableSettingBean,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();

        try {

            boolean isRunning =
                    this.flowableManager.isFlowableHasRunning(flowableSettingBean.getId());

            if (isRunning) {
                jsonResult.setStatus(JsonStatus.ERROR);
                jsonResult.setMessage(this.messageSource
                        .getMessage("message.error.flowable.is.running.no.save", null, locale));
                return jsonResult;
            }

            List<FlowableSettingNode> settingNodes = new ArrayList<>();

            FlowableSettingNodeBeanConverter converter = new FlowableSettingNodeBeanConverter();

            Date now = new Date();

            for (int i = 0; i < flowableSettingBean.getFlowableSettingNodeBeans().size(); i++) {

                FlowableSettingNodeBean bean =
                        flowableSettingBean.getFlowableSettingNodeBeans().get(i);

                bean.setNodeOrder(i + 1);
                bean.setFlowableSetting_(flowableSettingBean.getId());
                FlowableSettingNode node = converter.toEntity(bean, null, userBean, now);

                settingNodes.add(node);
            }

            if (settingNodes.size() > 0) {

                this.flowableSettingNodeService.deleteAllAndCreate(flowableSettingBean.getId(),
                        settingNodes);

                boolean isDeploy = this.flowableManager.generateFlowFromDb(
                        userBean.getSessionUserId(), flowableSettingBean, settingNodes);

                if (isDeploy) {
                    FlowableSetting flowableSetting =
                            this.flowableSettingService.find(flowableSettingBean.getId());

                    if (flowableSetting != null) {
                        flowableSetting.setIsComplete(OnOffEnum.ON.value());
                        this.flowableSettingService.update(flowableSetting);
                    }
                }
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.setMessage(
                    this.messageSource.getMessage("message.info.record.modify.ok", null, locale));
        } catch (Exception e) {
            getLogger().error("Exception while save-flow-detail-data." + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    @RequestMapping("/audit-log-info")
    @ResponseBody
    public JsonResultBean getAuditLogInfo(String correlateDataId,
            @ModelAttribute(Constants.SESSION_USER) SessionUserBean userBean, Locale locale) {

        JsonResultBean jsonResult = new JsonResultBean();

        try {

            List<HistoricTaskInstance> instances =
                    this.flowableManager.getHistoricTaskInstances(correlateDataId);

            List<AuditLogBean> logBeans = new ArrayList<>();

            for (HistoricTaskInstance instance : instances) {

                AuditLogBean bean = new AuditLogBean();

                if (instance.getEndTime() != null) {
                    bean.setAuditDatetime(instance.getEndTime());
                    bean.setAuditDatetimeText(DateUtils.date2String(instance.getEndTime(),
                            Constants.SIMPLE_DATE_TIME_FORMAT));
                }

                Map<String, Object> pv = instance.getProcessVariables();

                if (pv != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> selfMap = (Map<String, Object>) pv.get(instance.getId());
                    if (selfMap != null) {

                        Integer selfAuditResult =
                                (Integer) selfMap.get(FlowableManager.KEY_SELF_AUDIT_RESULT);
                        if (selfAuditResult != null) {
                            bean.setAuditResult(selfAuditResult);
                            bean.setAuditResultText(this.messageSource.getMessage(
                                    FlowAuditResultEnum.getResouceName(selfAuditResult), null,
                                    locale));
                        }

                        bean.setAuditRemark(
                                (String) selfMap.get(FlowableManager.KEY_SELF_AUDIT_REMARK));
                        bean.setAuditPerson(
                                (String) selfMap.get(FlowableManager.KEY_SELF_AUDIT_PERSON_ID));
                        bean.setAuditPersonText(
                                (String) selfMap.get(FlowableManager.KEY_SELF_AUDIT_PERSON_NAME));
                    }
                }

                bean.setNodeName(instance.getName());

                logBeans.add(bean);
                // System.out.println(instance.getDeleteReason());
            }

            // 准备JSON结果
            jsonResult.setStatus(JsonStatus.OK);
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_TOTAL, logBeans.size());// easyui-datagrid分页用
            jsonResult.getDataMap().put(JsonResultBean.KEY_EASYUI_ROWS, logBeans);// easyui-datagrid分页用
        } catch (Exception e) {
            getLogger().error("Exception while audit-log-info" + this.getDataType(), e);

            // 在JSON对象内设定服务器处理结果状态
            jsonResult.setStatus(JsonStatus.ERROR);
        }

        return jsonResult;
    }

    // @RequestMapping("/generate-flow-detail")
    // public void generateKeyImage(String flowableSettingId, HttpServletResponse
    // response,
    // HttpServletRequest request, Locale locale) {
    //
    // FlowableSetting setting =
    // this.flowableSettingService.find(flowableSettingId);
    //
    // if (setting != null) {
    //
    // OutputStream os = null;
    // InputStream imageStream = this.flowableManager.generateDiagram(setting);
    //
    // try {
    // response.setContentType("image/jpg");
    //
    // os = response.getOutputStream();
    //
    // byte[] b = new byte[1024];
    // int len;
    // while ((len = imageStream.read(b, 0, 1024)) != -1) {
    // os.write(b, 0, len);
    // }
    //
    // os.flush();
    // } catch (Exception e) {
    // logger.error("Exception while converting object to image.", e);
    // } finally {
    //
    // try {
    // if (imageStream != null) {
    // try {
    // imageStream.close();
    // } catch (IOException e) {
    // logger.error("Exception while close IputStream.", e);
    // }
    // }
    // } finally {
    // if (os != null) {
    // try {
    // os.close();
    // } catch (IOException e) {
    // logger.error("Exception while close OutputStream.", e);
    // }
    // }
    // }
    // }
    // }
    // }
}
